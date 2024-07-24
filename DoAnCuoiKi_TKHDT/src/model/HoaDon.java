package model;

import java.util.List;

public class HoaDon {
    private String maHoaDon;
    private String ngayLap;
    private double tongTien;
    private List<MonAn> danhSachMonAn;

    public HoaDon(String maHoaDon, String ngayLap, double tongTien, List<MonAn> danhSachMonAn) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.danhSachMonAn = danhSachMonAn;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public List<MonAn> getDanhSachMonAn() {
        return danhSachMonAn;
    }

    public void setDanhSachMonAn(List<MonAn> danhSachMonAn) {
        this.danhSachMonAn = danhSachMonAn;
    }
}
