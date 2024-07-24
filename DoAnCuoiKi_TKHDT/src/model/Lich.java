package model;

public class Lich {
	private String thu;
	private int ca;

	public Lich(String thu, int ca) {
		super();
		this.thu = thu;
		this.ca = ca;
	}

	@Override
	public String toString() {
		return "Thứ:" + thu + "\t ca:" + ca + "\n";
	}

}
