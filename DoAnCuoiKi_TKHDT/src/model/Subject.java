package model;

public interface Subject {
	public void registerObserverKM(ObserverKM o);

	public void removeObserverKM(ObserverKM o);

	public void notifySubscribersKM(String tenCongTy, String noidungchuongtrinh);
}
