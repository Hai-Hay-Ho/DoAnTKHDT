package model;

public class Ban {
	private TaiKhoan taiKhoan;
	private KhachHang khang;
	private int soBan;
	private int soGhe;
	private boolean checkBan;
	private String tenKhachHang;

	public Ban(int soBan, int soGhe) {
		super();
		this.soBan = soBan;
		this.soGhe = soGhe;
		checkBan = false;
	}

	public Ban(int soBan, boolean checkBan) {
		super();
		this.soBan = soBan;
		this.checkBan = checkBan;
	}

	public Ban(TaiKhoan taiKhoan, int soBan, int soGhe, boolean checkBan) {
		super();
		this.taiKhoan = taiKhoan;
		this.soBan = soBan;
		this.soGhe = soGhe;
		this.checkBan = checkBan;
	}

	@Override
	public String toString() {
		return soBan + "\t" + soGhe + "\n";
	}

	public int getSoBan() {
		return soBan;
	}

	public void setSoBan(int soBan) {
		this.soBan = soBan;
	}

	public int getSoGhe() {
		return soGhe;
	}

	public void setSoGhe(int soGhe) {
		this.soGhe = soGhe;
	}

	public boolean isCheckBan() {
		return checkBan;
	}

	public void setCheckBan(boolean checkBan) {
		this.checkBan = checkBan;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public KhachHang getKhang() {
		return khang;
	}

	public void setKhang(KhachHang khang) {
		this.khang = khang;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

}
