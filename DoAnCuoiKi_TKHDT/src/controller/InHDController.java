package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import model.HoaDon;
import model.MonAn;
import view.GiaoDienNhanVien;

public class InHDController {
    private DefaultTableModel inHDModel;
    private JTextField maHD;
    private JTextField ngayLap;
    private JTextField tongTien;
    private Map<String, HoaDon> data;
    private static int nextMaHoaDon = 1; 
    GiaoDienNhanVien view;

    public InHDController(DefaultTableModel inHDModel, JTextField maHD, JTextField ngayLap, JTextField tongTien, GiaoDienNhanVien view) {
        this.inHDModel = inHDModel;
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.data = new HashMap<>();
        this.view = view;
    }
    
    public void setFilePath(String filePath) {
        loadDataFromFile(filePath);
    }

    private void loadDataFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) { 
                    String tableNumber = parts[0];
                    String ngayLapHD = parts[1];
                    String[] dishes = parts[2].split(";");

                    double total = 0;
                    List<MonAn> danhSachMonAn = new ArrayList<>();
                    for (String dish : dishes) {
                        String[] dishInfo = dish.split(",");
                        if (dishInfo.length >= 4) { 
                            String maMonAn = dishInfo[0];
                            String tenMonAn = dishInfo[1];
                            double giaTien = Double.parseDouble(dishInfo[2]);
                            int soLuong = Integer.parseInt(dishInfo[3]);
                            danhSachMonAn.add(new MonAn(maMonAn, tenMonAn, giaTien, soLuong));
                            total += giaTien * soLuong;
                        }
                    }

                    // Sử dụng mã hóa đơn từ biến static
                    String maHoaDon = "HD" + nextMaHoaDon;
                    HoaDon hoaDon = new HoaDon(maHoaDon, ngayLapHD, total, danhSachMonAn);
                    data.put(tableNumber, hoaDon);
                    nextMaHoaDon++; // Tăng giá trị cho mã hóa đơn tiếp theo
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void addRowToTable(String maMonAn, String tenMonAn, double giaTien, int soLuong) {
        Object[] rowData = {maMonAn, tenMonAn, giaTien, soLuong};
        inHDModel.addRow(rowData);
    }

    public void showMenuButtonClicked(String tableNumber) {
        inHDModel.setRowCount(0);

        if (!isNumeric(tableNumber)) {
            JOptionPane.showMessageDialog(null, "Số bàn không hợp lệ. Vui lòng nhập số.");
            return;
        }

        HoaDon hoaDon = data.get(tableNumber);
        if (hoaDon != null) {
            maHD.setText(hoaDon.getMaHoaDon());
            ngayLap.setText(hoaDon.getNgayLap());

            for (MonAn monAn : hoaDon.getDanhSachMonAn()) {
                addRowToTable(monAn.getMaMonAn(), monAn.getTenMonAn(), monAn.getGia(), monAn.getSoLuong());
            }

            tongTien.setText(String.valueOf(hoaDon.getTongTien()));
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy bàn: " + tableNumber);
        }
    }


    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void printInvoiceButtonClicked() {
        String maHoaDon = maHD.getText();
        String ngayLapHoaDon = ngayLap.getText();
        String tongTienHoaDon = tongTien.getText();

        String nhanVienThucHien = view.getTenNV();

        String htmlContent = "<html>"
                + "<h1 style='text-align: center;'>Hóa Đơn</h1>"
                + "<p><b>Mã hóa đơn:</b> " + maHoaDon + "</p>"
                + "<p><b>Ngày lập:</b> " + ngayLapHoaDon + "</p>"
                + "<p><b>Nhân viên thực hiện:</b> " + nhanVienThucHien + "</p>"
                + "<table border='1' style='width: 100%; border-collapse: collapse;'>"
                + "<tr><th>Mã món ăn</th><th>Tên thực đơn</th><th>Đơn giá</th><th>Số lượng</th></tr>";
        
        for (int i = 0; i < inHDModel.getRowCount(); i++) {
            String maMonAn = (String) inHDModel.getValueAt(i, 0);
            String tenMonAn = (String) inHDModel.getValueAt(i, 1);
            double giaTien = (double) inHDModel.getValueAt(i, 2);
            int soLuong = (int) inHDModel.getValueAt(i, 3);
            htmlContent += "<tr><td>" + maMonAn + "</td><td>" + tenMonAn + "</td><td>" + giaTien + "</td><td>" + soLuong + "</td></tr>";
        }

        htmlContent += "</table>"
                + "<p><b>Tổng tiền:</b> " + tongTienHoaDon + " VND</p>"
                + "</html>";

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(htmlContent);
        textPane.setEditable(false);

        JOptionPane.showMessageDialog(null, new JScrollPane(textPane), "Hóa Đơn", JOptionPane.INFORMATION_MESSAGE);
    }

}