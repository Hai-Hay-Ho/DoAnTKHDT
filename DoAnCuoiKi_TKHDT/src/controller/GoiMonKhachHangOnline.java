package controller;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Ban;
import model.MonAn;
import model.PaymentStrategy;
import view.GiaoDienKhachHang;

public class GoiMonKhachHangOnline {
	private DefaultTableModel tableModel;
	private GiaoDienKhachHang kh;
	private PaymentStrategy cashPaymentStrategy;
	private PaymentStrategy bankTransferStrategy;


	public GoiMonKhachHangOnline() {
	}

	public void setCashPaymentStrategy(PaymentStrategy cashPaymentStrategy) {
		this.cashPaymentStrategy = cashPaymentStrategy;
	}

	public void setBankTransferStrategy(PaymentStrategy bankTransferStrategy) {
		this.bankTransferStrategy = bankTransferStrategy;
	}

	// Cập nhật danh sách món ăn:
	public void updateFoodList(String category, JList<String> menuList) {
		// Tạo một map để lưu trữ danh sách món ăn theo loại
		Map<String, List<String>> foodMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("./src/controller/dsMonAn.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length == 3) {
					String maMon = parts[0];
					String tenMon = parts[1];
					String loaiMon = maMon.substring(0, 2);
					foodMap.computeIfAbsent(loaiMon, k -> new ArrayList<>()).add(tenMon);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Cập nhật danh sách món ăn dựa trên loại món
		DefaultListModel<String> model = new DefaultListModel<>();
		List<String> foods = foodMap.getOrDefault(getLoaiMonCode(category), Collections.emptyList());
		for (String food : foods) {
			model.addElement(food);
		}
		menuList.setModel(model);
	}

// Phương thức này trả về mã loại món dựa trên tên loại món
	private String getLoaiMonCode(String category) {
		switch (category) {
		case "Món khai vị":
			return "KV";
		case "Món Chính":
			return "MA";
		case "Món Ăn Vặt":
			return "AV";
		case "Đồ uống":
			return "DU";
		default:
			return "Khác";
		}
	}

	// Lấy thông tin món ăn từ file:
	public MonAn getMonAnByName(String tenMon) {
		MonAn monAn = null;
		try (BufferedReader reader = new BufferedReader(new FileReader("./src/controller/dsMonAn.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length == 3 && parts[1].equals(tenMon)) {
					String maMon = parts[0];
					String ten = parts[1];
					double donGia = Double.parseDouble(parts[2]);
					monAn = new MonAn(maMon, ten, donGia);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return monAn;
	}

	// Đặt món:
	public void datMon(DefaultTableModel tableModel, MonAn monAn, int soLuong) {
		boolean daTonTai = false;
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, 0).equals(monAn.getMaMonAn())) {
				int soLuongHienTai = (int) tableModel.getValueAt(i, 3);
				tableModel.setValueAt(soLuongHienTai + soLuong, i, 3);
				daTonTai = true;
				break;
			}
		}
		if (!daTonTai) {
			tableModel.addRow(new Object[] { monAn.getMaMonAn(), monAn.getTenMonAn(), monAn.getGia(), soLuong });
		}
	}
	public void datMonOff(DefaultTableModel tableModel, MonAn monAn, int soLuong, int soBan) {
		boolean daTonTai = false;
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, 0).equals(monAn.getMaMonAn())) {
				int soLuongHienTai = (int) tableModel.getValueAt(i, 3);
				tableModel.setValueAt(soLuongHienTai + soLuong + soBan, i, 3);
				daTonTai = true;
				break;
			}
		}
		if (!daTonTai) {
			tableModel.addRow(new Object[] { monAn.getMaMonAn(), monAn.getTenMonAn(), monAn.getGia(), soLuong , soBan });
		}
	}


	// Xóa món:
	public void xoaMon(int rowIndex) {
		if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
			tableModel.removeRow(rowIndex);
		}
	}public void thanhToanOffline(DefaultTableModel tableModel, Component parentComponent, PaymentStrategy pttt, String paymentMethod,String tenBan) {
		double totalAmount = calculateTotalAmount(tableModel);
	    double totalAmountAfterDiscount = pttt.calculateTotal(totalAmount);

	    String message = "Tổng số tiền cần thanh toán là: " + totalAmountAfterDiscount + " VNĐ.\nBạn có chắc chắn muốn thanh toán?";
	    int option = JOptionPane.showConfirmDialog(parentComponent, message, "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);

	    if (option == JOptionPane.YES_OPTION) {
	        try {
	            StringBuilder sb = new StringBuilder();
	            sb.append(tenBan).append("| "); // Thêm tên tài khoản và ký tự "| "

	            for (int i = 0; i < tableModel.getRowCount(); i++) {
	                String tenMonAn = (String) tableModel.getValueAt(i, 1);
	                int soLuong = (int) tableModel.getValueAt(i, 3);
	                double giaTien = (double) tableModel.getValueAt(i, 2); // Giả sử cột giá tiền ở vị trí thứ 2

	                sb.append(tenMonAn).append(" - ")
	                  .append(soLuong).append(" - ")
	                  .append(giaTien);
	                if (i < tableModel.getRowCount() - 1) {
	                    sb.append(", "); // Thêm dấu phẩy và khoảng trắng giữa các món ăn
	                }
	            }

	            sb.append("| ").append(totalAmountAfterDiscount); // Thêm tổng tiền vào cuối

	            BufferedWriter writer = new BufferedWriter(new FileWriter("./src/controller/HDData.txt", true));
	            writer.write(sb.toString());
	            writer.newLine();
	            writer.close();

	            JOptionPane.showMessageDialog(parentComponent, "Thanh toán thành công. Cảm ơn quý khách!");
	            tableModel.setRowCount(0);
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(parentComponent, "Lỗi khi ghi vào file.");
	            e.printStackTrace();
	        }
	    } else {
	        JOptionPane.showMessageDialog(parentComponent, "Thanh toán bị hủy bỏ.");
	    }
	}


	public void thanhToan(DefaultTableModel tableModel, Component parentComponent, PaymentStrategy pttt, String paymentMethod, String tentaiKhoan) {
	    double totalAmount = calculateTotalAmount(tableModel);
	    double totalAmountAfterDiscount = pttt.calculateTotal(totalAmount);

	    String message = "Tổng số tiền cần thanh toán là: " + totalAmountAfterDiscount + " VNĐ.\nBạn có chắc chắn muốn thanh toán?";
	    int option = JOptionPane.showConfirmDialog(parentComponent, message, "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);

	    if (option == JOptionPane.YES_OPTION) {
	        try {
	            StringBuilder sb = new StringBuilder();
	            sb.append(tentaiKhoan).append("| "); // Thêm tên tài khoản và ký tự "| "

	            for (int i = 0; i < tableModel.getRowCount(); i++) {
	                String tenMonAn = (String) tableModel.getValueAt(i, 1);
	                int soLuong = (int) tableModel.getValueAt(i, 3);
	                double giaTien = (double) tableModel.getValueAt(i, 2); // Giả sử cột giá tiền ở vị trí thứ 2

	                sb.append(tenMonAn).append(" - ")
	                  .append(soLuong).append(" - ")
	                  .append(giaTien);
	                if (i < tableModel.getRowCount() - 1) {
	                    sb.append(", "); // Thêm dấu phẩy và khoảng trắng giữa các món ăn
	                }
	            }

	            sb.append("| ").append(totalAmountAfterDiscount); // Thêm tổng tiền vào cuối

	            BufferedWriter writer = new BufferedWriter(new FileWriter("./src/controller/DonThanhToanOnline.txt", true));
	            writer.write(sb.toString());
	            writer.newLine();
	            writer.close();

	            JOptionPane.showMessageDialog(parentComponent, "Thanh toán thành công. Cảm ơn quý khách!");
	            tableModel.setRowCount(0);
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(parentComponent, "Lỗi khi ghi vào file.");
	            e.printStackTrace();
	        }
	    } else {
	        JOptionPane.showMessageDialog(parentComponent, "Thanh toán bị hủy bỏ.");
	    }
	}

	// Hàm tính tổng tiền có thể trông như sau:
	private double calculateTotalAmount(DefaultTableModel tableModel) {
	    double total = 0;
	    for (int i = 0; i < tableModel.getRowCount(); i++) {
	        double price = (double) tableModel.getValueAt(i, 2); // Giả sử cột giá tiền ở vị trí thứ 2
	        int quantity = (int) tableModel.getValueAt(i, 3); // Giả sử cột số lượng ở vị trí thứ 3
	        total += price * quantity;
	    }
	    return total;
	}

}

