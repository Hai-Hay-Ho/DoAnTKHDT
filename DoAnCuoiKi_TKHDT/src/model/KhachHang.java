package model;

import java.util.ArrayList;

public class KhachHang implements ObserverKM {
    private String tenKH;
    private String sdt;
    private String diaChi;
    private ArrayList<MonAn> order;
    private TaiKhoan taikhoan;
    private boolean nhanKM;
    private PaymentStrategy hinhThucThanhToan;

    public KhachHang(TaiKhoan taikhoan, String tenKH, String sdt, String diaChi) {
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.taikhoan = taikhoan;
        this.order = new ArrayList<>();
        this.nhanKM = false;
    }


    public KhachHang(String tenKH, String sdt, String diaChi) {
		super();
		this.tenKH = tenKH;
		this.sdt = sdt;
		this.diaChi = diaChi;
	}


	public KhachHang(TaiKhoan taikhoan) {
		super();
		this.taikhoan = taikhoan;
	}
    public KhachHang() {
        this.tenKH = "";
        this.sdt = "";
        this.diaChi = "";
        this.taikhoan = new TaiKhoan(); 
    }


	public boolean isNhanKM() {
        return nhanKM;
    }

    public void setNhanKM(boolean nhanKM) {
        this.nhanKM = nhanKM;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public ArrayList<MonAn> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<MonAn> order) {
        this.order = order;
    }

    public TaiKhoan getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(TaiKhoan taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getUserName() {
        return this.taikhoan.getUserName();
    }

    public String getPassW() {
        return this.taikhoan.getPassW();
    }

    @Override
    public void updateKM(String tenCongTy, String noidungKhuyenMai) {
        nhanKM = true;
        System.out.printf("%s nhan tin : %s  - TIN: '%s'\n", getSdt(), "tu " + tenCongTy, noidungKhuyenMai);
    }

    public double thanhToanOnline(PaymentStrategy hinhthucTT) {
        double tongTien = 0;
        for (MonAn monAn : order) {
            tongTien += monAn.getGia();
        }
        double tongTienCanThanhToan = hinhthucTT.calculateTotal(tongTien);
        return tongTienCanThanhToan;
    }
    
    public double thanhToan(PaymentStrategy hinhthucTT) {
        double result = 0;
        return result;
    }

    public void datBan(int soBan, int soGhe) {
        Ban datBan = new Ban(soBan, soGhe);
        datBan.setCheckBan(true);
    }
}
