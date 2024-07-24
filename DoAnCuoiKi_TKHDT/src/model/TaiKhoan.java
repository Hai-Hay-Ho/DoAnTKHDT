package model;

public class TaiKhoan {
	String userName;
	String passW;

	public TaiKhoan(String userName, String passW) {
		super();
		this.userName = userName;
		this.passW = passW;
	}

	
	
	public TaiKhoan(String userName) {
		super();
		this.userName = userName;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassW() {
		return passW;
	}

	public void setPassW(String passW) {
		this.passW = passW;
	}


	public TaiKhoan() {
        this.userName = "";
        this.passW = "";
    }
	@Override
	public String toString() {
		return userName;
	}
}
