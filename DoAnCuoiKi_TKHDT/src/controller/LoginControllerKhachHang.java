package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.KhachHang;
import model.TaiKhoan;
import view.NhaHangPanel;

public class LoginControllerKhachHang {
	private NhaHangPanel view;
	private KhachHang model;
	private TaiKhoan tkModel;

	public LoginControllerKhachHang(NhaHangPanel view, KhachHang model, TaiKhoan tkModel) {
		super();
		this.view = view;
		this.model = model;
		this.tkModel = tkModel;
	}

	public void setView(NhaHangPanel view) {
	    this.view = view;
	}

	// check signUp
	public void handleSignUp() {
		// Kiểm tra xem tài khoản đã tồn tại trong file text chưa
		if (isAccountExisted(tkModel.getUserName())) {
			JOptionPane.showConfirmDialog(null, "Tài khoản đã tồn tại!", "Thông báo", JOptionPane.DEFAULT_OPTION);
			return;
		}

		// Ghi thông tin tài khoản vào file text
		try (FileWriter writer = new FileWriter("./src/controller/LuuTruThongTinKH.txt", true)) {
			writer.write(tkModel.getUserName() + ":" + tkModel.getPassW() + ":" + model.getTenKH() + ":"
					+ model.getSdt() + ":" + model.getDiaChi() + "\n");
			JOptionPane.showConfirmDialog(null, "Đăng ký thành công!", "Thông báo", JOptionPane.DEFAULT_OPTION);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "Kiểm tra lại tài khoản!", "Thông báo", JOptionPane.DEFAULT_OPTION);
		}
	}

	private boolean isAccountExisted(String username) {
		try (BufferedReader reader = new BufferedReader(new FileReader("./src/controller/LuuTruThongTinKH.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts[0].equals(username)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// check logIn
	public void handleLogin() {
		// Kiểm tra xem tài khoản có tồn tại và mật khẩu có đúng không
		if (isAccountValid(tkModel.getUserName(), tkModel.getPassW())) {
			// dùng cardLayout chuyển sang giao diện kế tiếp
			view.change("giaodienkhachhang");
			
		} else {
			// Nếu tài khoản không hợp lệ, hiển thị thông báo lỗi
			JOptionPane.showConfirmDialog(null, "Kiểm tra lại tài khoản!", "Thông báo", JOptionPane.DEFAULT_OPTION);
		}
	}

	public boolean isAccountValid(String username, String password) {
		try (BufferedReader br = new BufferedReader(new FileReader("./src/controller/LuuTruThongTinKH.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts.length == 5) {
					String storedUsername = parts[0];
					String storedPassword = parts[1];
					if (storedUsername.equals(username) && storedPassword.equals(password)) {
						return true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getTenKhachHang() {
	    String tenKH = view.getTenKH();
	    return tenKH;
	}
	
	public String getMKKhachHang() {
	    String mkKH = view.getMkKH();
	    return  mkKH;
	}

}

