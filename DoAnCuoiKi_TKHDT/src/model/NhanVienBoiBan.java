package model;

import java.util.ArrayList;

public class NhanVienBoiBan extends NhanVien {
	private ArrayList<Ban> dsBanPhucVu;

	public NhanVienBoiBan(String maNV) {
		super(maNV);
		dsBanPhucVu = new ArrayList<>();
	}
	//thêm bàn
	public void addBan(Ban b) {
		dsBanPhucVu.add(b);
	}
	public void xoaBan(Ban b) {
		dsBanPhucVu.remove(b);
	}
	public void layDanhSachDatBan() {
		for(int i=0;i<dsBanPhucVu.size();i++){
			if(dsBanPhucVu.get(i).isCheckBan()==true) {
				dsBanPhucVu.add(dsBanPhucVu.get(i));
			}
		}
	}

}
