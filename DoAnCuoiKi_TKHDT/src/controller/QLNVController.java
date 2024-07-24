package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;
import model.TaiKhoan;
import view.GiaoDienNhanVien;

public class QLNVController {
	private NhanVien model;
	private GiaoDienNhanVien view;
	private String filePath = "./src/controller/dsNhanVien.txt";
	private List<NhanVien> dsNhanVien = new ArrayList<>();

	public QLNVController(NhanVien model, GiaoDienNhanVien view) {
		super();
		this.model = model;
		this.view = view;
	}

	public void setDsNhanVien(List<NhanVien> dsNhanVien) {
		this.dsNhanVien = dsNhanVien;
	}

	public List<NhanVien> getDsNhanVien() {
		return dsNhanVien;
	}

	public void themNhanVien() {

	}

	private void ghiDanhSachNhanVienVaoFile() {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	        for (NhanVien nv : dsNhanVien) {
	            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            String line = nv.getMaNV() + "|" + nv.getTenNV() + "|" + nv.getSdt() + "|" 
	                + dateFormat.format(nv.getNgaySinh()) + "|" + nv.getTaikhoan();
	            writer.write(line);
	            writer.newLine();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void saveDataToFile(NhanVien nhanVien) {
		String filePath = "./src/controller/dsNhanVien.txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String line = nhanVien.getMaNV() + "|" + nhanVien.getTenNV() + "|" + nhanVien.getSdt() + "|"
					+ dateFormat.format(nhanVien.getNgaySinh()) + "|" + nhanVien.getTaikhoan();
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void docDuLieuTuFile() {
		dsNhanVien.clear();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length == 5) {
					String maNV = parts[0];
					String tenNV = parts[1];
					String sdt = parts[2];
					String tK = parts[4];
					java.util.Date ngaySinh = null;

					try {
						ngaySinh = dateFormat.parse(parts[3]);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					NhanVien nv = new NhanVien(maNV, tenNV, sdt, ngaySinh, new TaiKhoan(tK));
					dsNhanVien.add(nv);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hienThiNhanVienTrongBang(DefaultTableModel tableModel) {
		tableModel.setRowCount(0); // Xóa tất cả các hàng hiện có
		this.docDuLieuTuFile(); // Đọc dữ liệu từ file và cập nhật dsNhanVien

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (NhanVien nv : dsNhanVien) {
			Object[] rowData = { nv.getMaNV(), nv.getTenNV(), nv.getSdt(),
					nv.getNgaySinh() != null ? dateFormat.format(nv.getNgaySinh()) : "N/A",
					nv.getTaikhoan() != null ? nv.getTaikhoan() : "N/A" };
			tableModel.addRow(rowData);
		}
	}

	public void themNhanVienMoi(String maNV, String tenNV, String sdt, String ngaySinhStr, String taiKhoan,
			DefaultTableModel tableModel) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ngaySinh = null;

		// Kiểm tra định dạng ngày sinh
		try {
			ngaySinh = dateFormat.parse(ngaySinhStr);
		} catch (ParseException e) {
			showErrorMessage("Định dạng ngày tháng k hợp lệ.");
			return;
		}

		// Kiểm tra các trường nhập liệu
		if (maNV.isEmpty() || tenNV.isEmpty() || sdt.isEmpty() || taiKhoan.isEmpty()) {
			showErrorMessage("Vui lòng điền đây đủ thông tin");
		}

		// Kiểm tra mã nhân viên hợp lệ
		if (!(maNV.length() == 6 && (maNV.startsWith("NVQL") || maNV.startsWith("NVTN") || maNV.startsWith("NVBB")))) {
			showErrorMessage("Mã NV không hợp lệ.");
			return;
		}

		// Kiểm tra tên nhân viên hợp lệ
		if (!tenNV.matches("[\\p{L}\\s]+")) {
			showErrorMessage("Tên NV không hợp lệ.");
			return;
		}

		// Kiểm tra mã nhân viên trùng lặp
		for (NhanVien nv : dsNhanVien) {
			if (nv.getMaNV().equals(maNV)) {
				showErrorMessage("Mã nhân viên đã tồn tại");
				return;
			}
		}

		// Tạo nhân viên mới và lưu vào file
		NhanVien newNV = new NhanVien(maNV, tenNV, sdt, ngaySinh, new TaiKhoan(taiKhoan));
		saveDataToFile(newNV);
		hienThiNhanVienTrongBang(tableModel);
	}

	public void traCuuNhanVien() {
		String maNV = view.getSearchMaNvField().getText();
		String tenNV = view.getSearchNameField().getText();

		if (maNV.isEmpty()) {
			showErrorMessage("Mã NV không được bỏ trống.");
			clearFields();
		}

		if (tenNV.isEmpty()) {
			showErrorMessage("Tên NV không được bỏ trống.");
			clearFields();

		}

		if (!tenNV.matches("[\\p{L}\\s]+")) {
			showErrorMessage("Tên NV không hợp lệ.");
			view.getSearchMaNvField().setText("");
			view.getSearchNameField().setText("");
		}

		if (!(maNV.length() == 6 && (maNV.startsWith("NVQL") || maNV.startsWith("NVTT") || maNV.startsWith("NVBB")))) {
			showErrorMessage("Mã NV không hợp lệ.");
			clearFields();
		}

		NhanVien foundNhanVien = null;
		for (NhanVien nv : dsNhanVien) {
			if (nv.getMaNV().equals(maNV) && nv.getTenNV().equals(tenNV)) {
				foundNhanVien = nv;
				break;
			}
		}

		if (foundNhanVien != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String ngaySinh = dateFormat.format(foundNhanVien.getNgaySinh());

			view.getTenNvField().setText(foundNhanVien.getTenNV());
			view.getMaNvField().setText(foundNhanVien.getMaNV());
			view.getSdtField().setText(foundNhanVien.getSdt());
			view.getNgaySinhField().setText(ngaySinh);
		} else {
			showErrorMessage("Không tìm thấy nhân viên.");
			clearFields();
		}
	}

	private void clearFields() {
		view.getSearchNameField().setText("");
		view.getSearchMaNvField().setText("");
	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
		return;
	}

	  public void xoaNhanVien(String maNV) {
	        List<NhanVien> tempNhanVienList = new ArrayList<>();
	        for (NhanVien nv : dsNhanVien) {
	            if (!nv.getMaNV().equals(maNV)) {
	                tempNhanVienList.add(nv);
	            }
	        }
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            for (NhanVien nv : tempNhanVienList) {
	                String line = nv.getMaNV() + "|" + nv.getTenNV() + "|" + nv.getSdt() + "|" +
	                        dateFormat.format(nv.getNgaySinh()) + "|" + nv.getTaikhoan();
	                writer.write(line);
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            showErrorMessage("Có lỗi xảy ra trong quá trình xóa nhân viên.");
	            return;
	        }

	        dsNhanVien.clear();
	        docDuLieuTuFile();
	    }
	
	public void capNhatThongTinNV(String maNV, String tenNV, String sdt, java.util.Date ngaySinh, String maNVMoi) {
	    boolean found = false;
	    boolean isDuplicate = false;

	    for (NhanVien n : dsNhanVien) {
	        if (n.getMaNV().equals(maNVMoi) && !n.getMaNV().equals(maNV)) {
	            isDuplicate = true;
	            break;
	        }
	    }

	    if (isDuplicate) {
	        showErrorMessage("Mã nhân viên mới đã tồn tại. Vui lòng chọn mã khác.");
	        return;
	    }

	    for (NhanVien n : dsNhanVien) {
	        if (n.getMaNV().equals(maNV)) {
	            n.setMaNV(maNVMoi);
	            n.setTenNV(tenNV);
	            n.setSdt(sdt);
	            n.setNgaySinh(ngaySinh);
	            found = true;
	            break;
	        }
	    }

	    if (!found) {
	        showErrorMessage("Không tìm thấy nhân viên.");
	        return;
	    }

	    ghiDanhSachNhanVienVaoFile();
	}

}
