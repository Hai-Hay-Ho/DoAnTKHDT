package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.MonAn;
import model.NhanVien;
import model.TaiKhoan;
import view.GiaoDienNhanVien;

public class QLMAController {
	private MonAn model;
	private GiaoDienNhanVien view;

	private String filePath = "./src/controller/dsMonAn.txt";
	private List<MonAn> dsMonAn = new ArrayList<>();

	public QLMAController(MonAn model, GiaoDienNhanVien view) {
		super();
		this.model = model;
		this.view = view;
	}


	
	public List<MonAn> getDsMonAn() {
		return dsMonAn;
	}



	public void setDsMonAn(List<MonAn> dsMonAn) {
		this.dsMonAn = dsMonAn;
	}



	private void ghiDanhSachMonAnVaoFile() {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	        for (MonAn ma : dsMonAn) { 
	            String line = ma.getMaMonAn() + "|" + ma.getTenMonAn() + "|" + ma.getGia();
	            writer.write(line);
	            writer.newLine();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void saveDataToFile(MonAn monAn) {
	    String filePath = "./src/controller/dsMonAn.txt";
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
	        String line = monAn.getMaMonAn() + "|" + monAn.getTenMonAn() + "|" + monAn.getGia();
	        writer.write(line);
	        writer.newLine();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	public void docDuLieuTuFile() {
	    String filePath = "./src/controller/dsMonAn.txt";
	    dsMonAn.clear();
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts.length == 3) {
	                String maMA = parts[0];
	                String tenMA = parts[1];
	                double gia = Double.parseDouble(parts[2]);
	                MonAn monAn = new MonAn(maMA, tenMA, gia);
	                dsMonAn.add(monAn);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}



	public void hienThiMonAnTrongBang(DefaultTableModel tableModel) {
	    docDuLieuTuFile(); // Ensure this method correctly populates dsMonAn

	    tableModel.setRowCount(0); // Clear the table model

	    for (MonAn ma : dsMonAn) {
	        Object[] rowData = {
	            ma.getMaMonAn(),
	            ma.getTenMonAn(),
	            ma.getGia(),
	        };
	        tableModel.addRow(rowData);
	    }
	}


	
	public void themMonAnMoi(String maMA, String tenMonAn, double gia, DefaultTableModel tableModel) {
	    if (maMA.isEmpty() || tenMonAn.isEmpty() || gia <= 0) {
	        showErrorMessage("Vui lòng điền đầy đủ thông tin");
	        return;
	    }

	    if (!tenMonAn.matches("[\\p{L}\\s]+")) {
	        showErrorMessage("Tên món ăn không hợp lệ");
	        return;
	    }

	    for (MonAn ma : dsMonAn) {
	        if (ma.getMaMonAn().equals(maMA)) {
	            showErrorMessage("Mã món ăn đã tồn tại");
	            return;
	        }
	    }

	    MonAn newMA = new MonAn(maMA, tenMonAn, gia);
	    dsMonAn.add(newMA); // Add to the list
	    saveDataToFile(newMA);
		hienThiMonAnTrongBang(tableModel);
	}



	
	public void traCuuMonAn() {
	    String maMA = view.getSearchMaMAField().getText();
	    String tenMA = view.getNameMAField().getText();

	    if (maMA.isEmpty()) {
	        showErrorMessage("Mã món ăn không được bỏ trống.");
	        clearFields();
	    }

	    if (tenMA.isEmpty()) {
	        showErrorMessage("Tên món ăn không được bỏ trống.");
	        clearFields();
	    }

	    if (!tenMA.matches("[\\p{L}\\s]+")) {
	        showErrorMessage("Tên món ăn không hợp lệ.");
	        view.getSearchMaNvField().setText("");
	        view.getNameMAField().setText("");
	        return;
	    }

	    if (!(maMA.length() == 4 )) {
	        showErrorMessage("Mã món ăn không hợp lệ.");
	        clearFields();
	        return;
	    }

	    MonAn foundMonAn = null;
	    for (MonAn ma : dsMonAn) {
	        if (ma.getMaMonAn().equals(maMA) && ma.getTenMonAn().equals(tenMA)) {
	            foundMonAn = ma;
	            break;
	        }
	    }

	    if (foundMonAn != null) {
	        view.getMaMAField().setText(foundMonAn.getMaMonAn());
	        view.getTenMAField().setText(foundMonAn.getTenMonAn());
	        view.getdGiaField().setText(String.valueOf(foundMonAn.getGia()));
	    } else {
	        showErrorMessage("Không tìm thấy món ăn.");
	        clearFields();
	    }
	}

	public void xoaMonAn(String maMA) {
	    String tempFilePath = "./src/controller/dsMonAn_temp.txt";

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            // Nếu mã món ăn không trùng khớp với mã cần xóa, ghi lại vào tệp tin tạm
	            if (parts.length == 3 && !parts[0].equals(maMA)) {
	                writer.write(line);
	                writer.newLine();
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Xóa tệp tin gốc
	    if (!new File(filePath).delete()) {
	        System.out.println("Không thể xóa tệp tin gốc.");
	        return;
	    }

	    // Đổi tên tệp tin tạm thời thành tên tệp tin gốc
	    if (!new File(tempFilePath).renameTo(new File(filePath))) {
	        System.out.println("Không thể đổi tên tệp tin.");
	        return;
	    }
	}
	
	public void capNhatThongTinMA(String maMA, String tenMA, double gia, String maMaMoi) {
	    for (MonAn m : dsMonAn) {
	        if (m.getMaMonAn().equals(maMA)) {
	            m.setMaMonAn(maMaMoi);
	            m.setTenMonAn(tenMA);
	            m.setGia(gia);
	   
	        }
	    }
	    ghiDanhSachMonAnVaoFile();
	}
	
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
		return;
	}
	
	private void clearFields() {
		view.getSearchNameField().setText("");
		view.getSearchMaNvField().setText("");
	}
}
