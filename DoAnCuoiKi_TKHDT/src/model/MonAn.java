package model;

public class MonAn {
	private String maMonAn;
	private String tenMonAn;
	private double gia;
	private int soLuong;
	private boolean checkOrder = false;

	public MonAn(String maMA, String tenMonAn, double gia) {
		super();
		this.maMonAn = maMA;
		this.tenMonAn = tenMonAn;
		this.gia = gia;
	}
	

	public MonAn(String maMonAn, String tenMonAn, double gia, int soLuong) {
		super();
		this.maMonAn = maMonAn;
		this.tenMonAn = tenMonAn;
		this.gia = gia;
		this.soLuong = soLuong;
	}


	public MonAn() {
		
	}
	@Override
	public String toString() {
		return "M� m�n:" + maMonAn + "\t T�n m�n: " + tenMonAn + "\t G�a:" + gia + "\n";
	}


	


	public String getMaMonAn() {
		return maMonAn;
	}


	public void setMaMonAn(String maMonAn) {
		this.maMonAn = maMonAn;
	}


	public String getTenMonAn() {
		return tenMonAn;
	}


	public void setTenMonAn(String tenMonAn) {
		this.tenMonAn = tenMonAn;
	}


	public double getGia() {
		return gia;
	}


	public void setGia(double gia) {
		this.gia = gia;
	}


	public boolean isCheckOrder() {
		return checkOrder;
	}


	public void setCheckOrder(boolean checkOrder) {
		this.checkOrder = checkOrder;
	}


	public int getSoLuong() {
		return soLuong;
	}


	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

}
