package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NhanVien {
	protected String maNV;
	protected String tenNV;
	protected String sdt;
	protected java.util.Date ngaySinh;
	protected TaiKhoan taikhoan;
	protected ArrayList<Lich> dsLichLam;
	protected ArrayList<MonAn> dsMonAn;

	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
		dsLichLam = new ArrayList<>();
		dsMonAn = new ArrayList<>();
	}

	public NhanVien() {
		super();
	}

	public NhanVien(String maNV, String tenNV, String sdt, java.util.Date ngaySinh, TaiKhoan taikhoan) {
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.sdt = sdt;
		this.ngaySinh = ngaySinh;
		this.taikhoan = taikhoan;
	}

	public ArrayList<Lich> getDsLichLam() {
		return dsLichLam;
	}

	public void setDsLichLam(ArrayList<Lich> dsLichLam) {
		this.dsLichLam = dsLichLam;
	}

	public ArrayList<MonAn> getDsMonAn() {
		return dsMonAn;
	}

	public void setDsMonAn(ArrayList<MonAn> dsMonAn) {
		this.dsMonAn = dsMonAn;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public TaiKhoan getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(TaiKhoan taikhoan) {
		this.taikhoan = taikhoan;
	}

	public void dangKiLich(String thu, int ca) {
		Lich o = new Lich(thu, ca);
		dsLichLam.add(o);
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(java.util.Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String formattedNgaySinh = (ngaySinh != null) ? dateFormat.format(ngaySinh) : "N/A";

		String taiKhoanInfo = (taikhoan != null ? taikhoan.toString() : "N/A");

		return "NhanVien {" + "maNV='" + maNV + '\'' + ", tenNV='" + tenNV + '\'' + ", sdt='" + sdt + '\''
				+ ", ngaySinh=" + formattedNgaySinh + ", taikhoan=" + taiKhoanInfo + // Thêm thông tin về tài khoản
				'}' + "\n";
	}
}
