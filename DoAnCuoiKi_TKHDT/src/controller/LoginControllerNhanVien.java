package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.NhanVien;
import model.TaiKhoan;
import view.NhaHangPanel;

public class LoginControllerNhanVien {
	private NhaHangPanel view;
	private NhanVien model;

	public LoginControllerNhanVien(NhaHangPanel view, NhanVien model) {
		super();
		this.view = view;
		this.model = model;
	}

	public void setView(NhaHangPanel view) {
		this.view = view;
	}

	// check logIn
	public void handleLoginNV() {
		if (isAccountValid(model.getMaNV())) {
			// dùng cardLayout chuyển sang giao diện kế tiếp
			view.change("giaodienquanly");
		} else {
			// Nếu tài khoản không hợp lệ, hiển thị thông báo lỗi
			JOptionPane.showConfirmDialog(null, "Kiểm tra lại mã nhân viên!", "Thông báo", JOptionPane.DEFAULT_OPTION);
		}
	}

	private boolean isAccountValid(String maNV) {
		try {
			// Đọc nội dung file accounts.txt
			BufferedReader reader = new BufferedReader(new FileReader("./src/controller/LuuTruThongTinNV.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				// Chia mỗi dòng thành username và password
				String[] parts = line.split(":");
				if (parts.length == 1) {
					if (parts[0].equals(maNV)) {
						// Nếu tìm thấy mã nv khớp, trả về true
						return true;
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Nếu không tìm thấy tài khoản hợp lệ, trả về false
		return false; 
	}

	public String getMaNhanVien() {
		String maNV = view.getMaNVien();
		return maNV;
	}
	

	public String getEmployeeName(String maNV) {
		try (BufferedReader reader = new BufferedReader(new FileReader("./src/controller/dsNhanVien.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length >= 2 && parts[0].equals(maNV)) {
					return parts[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
