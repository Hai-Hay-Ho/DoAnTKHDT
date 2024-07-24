package model;

import java.util.ArrayList;

public class NhanVienThuNgan extends NhanVien {
	private ArrayList<HoaDon> dsHoaDon;

	public NhanVienThuNgan(String maNV) {
		super(maNV);
		// TODO Auto-generated constructor stub
		dsHoaDon = new ArrayList<>();
	}

	public void inHoaDon(KhachHang kh, PaymentStrategy hinhThuc) {
		// in ra mon order
		for (int i = 0; i < kh.getOrder().size(); i++) {
			MonAn temp = kh.getOrder().get(i);
			System.out.println(i + ".\t" + temp.getTenMonAn() + "\t" + temp.getGia() + "\n");
		}
		System.out.println("Thanh tien:" + kh.thanhToan(hinhThuc));
	}
}
