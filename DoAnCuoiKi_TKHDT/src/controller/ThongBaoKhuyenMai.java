package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.HeThongNhaHang;
import model.ObserverKM;

public class ThongBaoKhuyenMai {
    private HeThongNhaHang nhaHang;
    public ThongBaoKhuyenMai(HeThongNhaHang nhaHang) {
        this.nhaHang = nhaHang;
        docThongBaoTuFile("./src/controller/thongTinKhuyenMai.txt");
    }

    public void guiThongBao(String noidungkhuyenmai) {
        nhaHang.setThongBao(noidungkhuyenmai);
    }

    public void dangKyThongBaoKM(ObserverKM observer) {
        nhaHang.registerObserverKM(observer);
    }

    public void huyDangKyThongBaoKM(ObserverKM observer) {
        nhaHang.removeObserverKM(observer);
    }

    public void docThongBaoTuFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/controller/thongTinKhuyenMai.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        guiThongBao(content.toString());
    }

}