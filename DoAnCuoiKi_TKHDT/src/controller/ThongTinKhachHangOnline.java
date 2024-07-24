package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.KhachHang;

public class ThongTinKhachHangOnline {
	public KhachHang layTTKH(String username, String passW) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./src/controller/LuuTruThongTinKH.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] details = line.split(":");
				if (details.length == 5) {
					String fileUsername = details[0];
					String filePassword = details[1];
					if (fileUsername.equals(username) && filePassword.equals(passW)) {
						String fullname = details[2];
						String phone = details[3];
						String address = details[4];
						KhachHang khachHang = new KhachHang(fullname, phone, address);
						reader.close();
						return khachHang;
					}
				}
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean capNhatThongTinKhachHang(String tenTaiKhoan, String matKhau, String tenKhachHang, String soDienThoai,
			String diaChi) {
		if (tenTaiKhoan == null || tenTaiKhoan.isEmpty() || matKhau == null || matKhau.isEmpty() || tenKhachHang == null
				|| tenKhachHang.isEmpty() || soDienThoai == null || soDienThoai.isEmpty() || diaChi == null
				|| diaChi.isEmpty()) {

			return false;
		}

		try {
			File file = new File("./src/controller/LuuTruThongTinKH.txt");
			if (!file.exists()) {
				return false;
			}

			List<String> lines = new ArrayList<>();
			boolean updated = false;

			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {

					String[] parts = line.split(":");
					if (parts.length == 5 && parts[0].equals(tenTaiKhoan)) {
						line = tenTaiKhoan + ":" + matKhau + ":" + tenKhachHang + ":" + soDienThoai + ":" + diaChi;
						updated = true;
					}
					lines.add(line);
				}
			}

			if (updated) {
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
					for (String l : lines) {
						writer.write(l);
						writer.newLine();
					}
				}
				return true;
			} else {

				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
