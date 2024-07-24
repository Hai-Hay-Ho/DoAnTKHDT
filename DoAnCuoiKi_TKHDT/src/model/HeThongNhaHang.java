package model;

import java.util.ArrayList;
import java.util.List;

public class HeThongNhaHang implements Subject {
    private String tenCH;
    private String thongBao;
    private final List<ObserverKM> dsKM;

    public HeThongNhaHang(String tenCH) {
        this.tenCH = tenCH;
        this.dsKM = new ArrayList<>();
    }


	public String getTenCH() {
        return tenCH;
    }

    public void setTenCH(String tenCH) {
        this.tenCH = tenCH;
    }

    public String getThongBao() {
        return thongBao;
    }

    public void setThongBao(String thongBao) {
        this.thongBao = thongBao;
        notifySubscribersKM(tenCH, thongBao);
    }

    @Override
    public synchronized void registerObserverKM(ObserverKM o) {
        if (!dsKM.contains(o)) {
            dsKM.add(o);
        }
    }

    @Override
    public synchronized void removeObserverKM(ObserverKM o) {
        dsKM.remove(o);
    }

    @Override
    public synchronized void notifySubscribersKM(String tenCongTy, String noidungchuongtrinh) {
        for (ObserverKM observerKM : dsKM) {
            observerKM.updateKM(tenCongTy, noidungchuongtrinh);
        }
    }
}
