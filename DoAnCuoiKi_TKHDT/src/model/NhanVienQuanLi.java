package model;

import java.util.ArrayList;

public class NhanVienQuanLi extends NhanVien {
	private ArrayList<NhanVien> listNV;

	public NhanVienQuanLi(String maNV) {
		super(maNV);
		listNV = new ArrayList<>();
	}

	public void themMonAn(String maMon, String tenMon, double gia) {
		MonAn m = new MonAn(maMon, tenMon, gia);
		if (!super.getDsMonAn().contains(m)) {
			super.getDsMonAn().add(m);
		}
	}
	public void themNhanVien(NhanVien nv) {
		listNV.add(nv);
	}
	public void xoaNV(NhanVien nv) {
		listNV.remove(nv);
	}

}
