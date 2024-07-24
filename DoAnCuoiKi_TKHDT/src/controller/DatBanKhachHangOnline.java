package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Ban;

public class DatBanKhachHangOnline {
	private List<Ban> danhSachBan;
	
	public DatBanKhachHangOnline() {
		this.danhSachBan = khoiTaoBanAn();
		loadReservationStatus();
	}
	// Khoi tao so ghe
	public static List<Ban> khoiTaoBanAn() {
		List<Ban> danhSachBan = new ArrayList<>();
		int[] soGheList = { 2, 2, 2, 2, 2, 4, 4, 4, 4, 5, 5, 5, 6, 8, 10 };
		for (int i = 0; i < soGheList.length; i++) {
			danhSachBan.add(new Ban(i + 1, soGheList[i]));
		}

		return danhSachBan;
	}

	public void datBan(int soBan) {
		Ban ban = danhSachBan.get(soBan - 1);
		if (ban.isCheckBan()) {
			int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn hủy đặt bàn " + soBan + " không?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				ban.setCheckBan(false);
				JOptionPane.showMessageDialog(null, "Đã hủy đặt bàn " + soBan);
				saveReservationStatus();
			}
		} else {
			ban.setCheckBan(true);
			JOptionPane.showMessageDialog(null, "Đã đặt bàn " + soBan);
			saveReservationStatus();
		}
	}

	public List<Ban> getDanhSachBan() {
		return danhSachBan;
	}

	private void saveReservationStatus() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/controller/dsBanDaDat.txt"))) {
			for (Ban ban : danhSachBan) {
				writer.write(ban.getSoBan() + "," + ban.isCheckBan() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadReservationStatus() {
		try (BufferedReader reader = new BufferedReader(new FileReader("./src/controller/dsBanDaDat.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int soBan = Integer.parseInt(parts[0]);
				boolean checkBan = Boolean.parseBoolean(parts[1]);
				danhSachBan.get(soBan - 1).setCheckBan(checkBan);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
