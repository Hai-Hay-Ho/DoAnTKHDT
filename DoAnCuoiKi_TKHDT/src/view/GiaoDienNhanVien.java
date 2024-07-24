
package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import controller.DKLMController;
import controller.DatBanKhachHangOnline;
import controller.InHDController;
import controller.LoginControllerKhachHang;
import controller.LoginControllerNhanVien;
import controller.OrderController;
import controller.QLMAController;
import controller.QLNVController;
import controller.ThongKeController;
import controller.QuanLiDatBan;
import model.Ban;
import model.KhachHang;
import model.MonAn;
import model.NhanVien;
import model.TaiKhoan;

public class GiaoDienNhanVien extends JPanel {

	private JTabbedPane tabbedPane;
	private QLNVController ct;
	private QLMAController ctMA;
	private DKLMController ctDK;
	JTextField searchMaNvField, searchNameField;
	private JButton searchButton;
	private JTextField maNvField;
	private JTextField tenNvField;
	private JTextField sdtField;
	private JTextField ngaySinhField;
	private JButton addNVButton;
	private JButton deleteNVButton;
	private JButton updateNVButton;
	private NhanVien nv;
	private JTextField maMAField;
	private JTextField tenMAField;
	private JTextField dGiaField;
	private JTextField nameMAField;
	private JTextField searchMaMAField;
	private JButton searchMAButton;
	private MonAn ma;
	private JButton addMAButton;
	private JButton deleteMAButton;
	private JButton updateMAButton;
	private JPanel dkiPanel;
	private JCheckBox[][] checkboxes;
	private JButton saveBtn;
	private JButton inBtn;
	private JLabel loginLabel;
	private JTextField tenDNField;
	private String maNV1;
	private String tenNV;
	private DefaultTableModel tableNVModel;
	private DefaultTableModel tableMAModel;
	private DefaultTableModel inHDModel;
	private LoginControllerNhanVien lgNVien;
	private NhaHangPanel view;
	private DatBanKhachHangOnline controller;
	private LoginControllerKhachHang lgkh;
	private KhachHang kh ;
	private TaiKhoan tk;
	private QuanLiDatBan qlDB;

	public GiaoDienNhanVien(String maNV1) {
		this.maNV1 = maNV1;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public JLabel getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(JLabel loginLabel) {
		this.loginLabel = loginLabel;
	}

	public void setMaNV1(String maNV1) {
		this.maNV1 = maNV1;
		System.out.println("Set maNV1: " + maNV1);
	}

	public String getMaNV1() {
		return maNV1;
	}

	public GiaoDienNhanVien(NhaHangPanel view) {
		this.view = view;
		setLayout(new BorderLayout(0, 0));
		Font tabFont = new Font("Arial", Font.BOLD, 16);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setFont(tabFont);
		nv = new NhanVien();
		lgNVien = new LoginControllerNhanVien(view, nv);
		kh = new KhachHang();
		tk = new TaiKhoan();
		lgkh = new LoginControllerKhachHang(view, kh, tk);
		qlDB = new QuanLiDatBan(view);
		
	}

	public void displayView(String maNV) {
		if (maNV.substring(0, 4).equals("NVTN")) {
			addTBaoDatHang(tabbedPane);
			addTBDatBan(tabbedPane);
			addInHoaDonPanel(tabbedPane);
			addBottomPanel();
			addDKLM(tabbedPane);
		} else if (maNV.substring(0, 4).equals("NVQL")) {
			addQuanLyNhanVienPanel(tabbedPane);
			addQuanLyThucDonPanel(tabbedPane);
			addThongKeDoanhThuPanel(tabbedPane);
			addTBao(tabbedPane);
			addBottomPanel();
		} else if (maNV.substring(0, 4).equals("NVBB")) {
			addBottomPanel();
			addDKLM(tabbedPane);
		}

	}

	public JTextField getSearchMaNvField() {
		return searchMaNvField;
	}

	public JTextField getSearchNameField() {
		return searchNameField;
	}

	public JTextField getMaNvField() {
		return maNvField;
	}

	public JTextField getTenNvField() {
		return tenNvField;
	}

	public JTextField getSdtField() {
		return sdtField;
	}

	public JTextField getNgaySinhField() {
		return ngaySinhField;
	}

	public JTextField getMaMAField() {
		return maMAField;
	}

	public void setMaMAField(JTextField maMAField) {
		this.maMAField = maMAField;
	}

	public JTextField getdGiaField() {
		return dGiaField;
	}

	public void setdGiaField(JTextField dGiaField) {
		this.dGiaField = dGiaField;
	}

	public JTextField getNameMAField() {
		return nameMAField;
	}

	public void setNameMAField(JTextField nameMAField) {
		this.nameMAField = nameMAField;
	}

	public JTextField getSearchMaMAField() {
		return searchMaMAField;
	}

	public void setSearchMaMAField(JTextField searchMaMAField) {
		this.searchMaMAField = searchMaMAField;
	}

	public JButton getSearchMAButton() {
		return searchMAButton;
	}

	public void setSearchMAButton(JButton searchMAButton) {
		this.searchMAButton = searchMAButton;
	}

	public void setSearchNameField(JTextField searchNameField) {
		this.searchNameField = searchNameField;
	}

	public JTextField getTenMAField() {
		return tenMAField;
	}

	public void setTenMAField(JTextField tenMAField) {
		this.tenMAField = tenMAField;
	}

	private void addQuanLyNhanVienPanel(JTabbedPane tabbedPane) {
		JPanel qLNVPanel = new JPanel();
		tabbedPane.addTab("Quản lý nhân viên", null, qLNVPanel, null);
		qLNVPanel.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2, 10, 10));

		JPanel infoNv = new JPanel();
		infoNv.setLayout(new GridLayout(4, 2, 10, 10));
		infoNv.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông tin nhân viên",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JLabel maNvLabel = new JLabel("Ma NV:");
		maNvField = new JTextField(20);
		maNvField.setEditable(false);
		maNvField.setPreferredSize(new Dimension(200, 30));
		JLabel tenNvLabel = new JLabel("Tên NV:");
		tenNvField = new JTextField(20);
		tenNvField.setPreferredSize(new Dimension(200, 30));
		tenNvField.setEditable(false);
		JLabel sdtLabel = new JLabel("SDT:");
		sdtField = new JTextField(20);
		sdtField.setPreferredSize(new Dimension(200, 30));
		sdtField.setEditable(false);
		JLabel ngaySinhLabel = new JLabel("Ngày Sinh:");
		ngaySinhField = new JTextField(20);
		ngaySinhField.setEditable(false);
		ngaySinhField.setPreferredSize(new Dimension(200, 30));

		infoNv.add(maNvLabel);
		infoNv.add(maNvField);
		infoNv.add(tenNvLabel);
		infoNv.add(tenNvField);
		infoNv.add(sdtLabel);
		infoNv.add(sdtField);
		infoNv.add(ngaySinhLabel);
		infoNv.add(ngaySinhField);

		JPanel findNv = new JPanel();
		findNv.setLayout(new BorderLayout());
		findNv.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tra cứu",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JLabel nameLabel = new JLabel("Tên: ");
		searchNameField = new JTextField(20);
		searchNameField.setPreferredSize(new Dimension(200, 30));
		JLabel searchMaNvLabel = new JLabel("Mã NV: ");
		searchMaNvField = new JTextField(20);
		searchMaNvField.setPreferredSize(new Dimension(200, 30));
		searchButton = new JButton("Tra Cứu");

		// Customizing the search button
		searchButton.setBackground(new Color(70, 130, 180)); // Set background color
		searchButton.setForeground(Color.WHITE); // Set text color
		searchButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
		searchButton.setFocusPainted(false); // Remove focus border
		searchButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding
		nv = new NhanVien(maNvField.getText());
		ct = new QLNVController(nv, GiaoDienNhanVien.this);
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ct.traCuuNhanVien();
			}
		});

		JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		inputPanel.add(nameLabel);
		inputPanel.add(searchNameField);
		inputPanel.add(searchMaNvLabel);
		inputPanel.add(searchMaNvField);
		

		findNv.add(inputPanel, BorderLayout.NORTH);
		findNv.add(searchButton, BorderLayout.SOUTH);

		topPanel.add(infoNv);
		topPanel.add(findNv);

		qLNVPanel.add(topPanel, BorderLayout.NORTH);

		JPanel bodyPanel = new JPanel();
		bodyPanel.setLayout(new BorderLayout());

		JPanel dsNv = new JPanel(new BorderLayout());
		dsNv.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Danh sách nhân viên",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		String[] columnNames = { "Mã NV", "Tên NV", "SĐT", "Ngày sinh", "Tài khoản" };

		tableNVModel = new DefaultTableModel(null, columnNames);
		JTable table = new JTable(tableNVModel);
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(0, 1, 30, 30));

		addNVButton = new JButton("Thêm");
		deleteNVButton = new JButton("Xóa   ");
		updateNVButton = new JButton("Sửa   ");

		// Customizing the buttons
		addNVButton.setBackground(new Color(60, 179, 113));
		addNVButton.setForeground(Color.WHITE);
		addNVButton.setFont(new Font("Arial", Font.BOLD, 14));
		addNVButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddNhanVienFrame();
			}
		});

		deleteNVButton.setBackground(new Color(255, 69, 0));
		deleteNVButton.setForeground(Color.WHITE);
		deleteNVButton.setFont(new Font("Arial", Font.BOLD, 14));
		deleteNVButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để xóa.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String maNV = (String) tableNVModel.getValueAt(selectedRow, 0);
					int option = JOptionPane.showConfirmDialog(null,
							"Bạn có chắc chắn muốn xóa nhân viên có mã " + maNV + " không?", "Xác nhận xóa",
							JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						ct.xoaNhanVien(maNV);
						ct.hienThiNhanVienTrongBang(tableNVModel);
					}
				}
			}
		});

		updateNVButton.setBackground(new Color(70, 130, 180));
		updateNVButton.setForeground(Color.WHITE);
		updateNVButton.setFont(new Font("Arial", Font.BOLD, 14));
		updateNVButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lấy thông tin nhân viên từ hàng đã chọn trong bảng
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để cập nhật.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String maNV = (String) table.getValueAt(selectedRow, 0);
					String tenNV = (String) table.getValueAt(selectedRow, 1);
					String sdt = (String) table.getValueAt(selectedRow, 2);
					String ngaySinh = (String) table.getValueAt(selectedRow, 3);

					// Hiển thị cửa sổ cập nhật với thông tin của hàng được chọn
					showUpdateDialog(maNV, tenNV, sdt, ngaySinh);
				}
			}

			private void showUpdateDialog(String maNV, String tenNV, String sdt, String ngaySinh) {
				// Tạo cửa sổ dialog
				JDialog updateDialog = new JDialog();
				updateDialog.setTitle("Cập nhật thông tin nhân viên");
				updateDialog.setSize(400, 300);
				updateDialog.setLocationRelativeTo(null);

				JLabel maNvLabel = new JLabel("Mã NV:");
				JTextField maNvField = new JTextField(20);
				maNvField.setText(maNV);

				JLabel tenNvLabel = new JLabel("Tên NV:");
				JTextField tenNvField = new JTextField(20);
				tenNvField.setText(tenNV);

				JLabel sdtLabel = new JLabel("SĐT:");
				JTextField sdtField = new JTextField(20);
				sdtField.setText(sdt);

				JLabel ngaySinhLabel = new JLabel("Ngày Sinh:");
				JTextField ngaySinhField = new JTextField(20);
				ngaySinhField.setText(ngaySinh);

				// Thêm các thành phần vào cửa sổ dialog
				JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
				panel.add(maNvLabel);
				panel.add(maNvField);
				panel.add(tenNvLabel);
				panel.add(tenNvField);
				panel.add(sdtLabel);
				panel.add(sdtField);
				panel.add(ngaySinhLabel);
				panel.add(ngaySinhField);

				updateDialog.add(panel);

				// Tạo nút "Lưu" để lưu thông tin cập nhật
				JButton saveButton = new JButton("Lưu");
				saveButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Lấy thông tin từ các trường nhập liệu
						String tenNV = tenNvField.getText();
						String sdt = sdtField.getText();
						String maNVMoi = maNvField.getText(); 

						Date ngaySinh = null;
						try {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
							java.util.Date parsedDate = dateFormat.parse(ngaySinhField.getText());
							ngaySinh = new Date(parsedDate.getTime());
						} catch (ParseException ex) {
							// Xử lý nếu chuỗi ngày sinh không đúng định dạng
							ex.printStackTrace();
						}

						// Kiểm tra nếu ngày sinh đã được chuyển đổi thành kiểu Date
						if (ngaySinh != null) {

							ct.capNhatThongTinNV(maNV, tenNV, sdt, ngaySinh, maNVMoi);

							ct.hienThiNhanVienTrongBang(tableNVModel);

							// Đóng cửa sổ dialog sau khi đã cập nhật thông tin
							updateDialog.dispose();
						} else {
							// Xử lý khi ngày sinh không hợp lệ
							JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ", "Lỗi",
									JOptionPane.ERROR_MESSAGE);
						}

					}
				});

				// Thêm nút "Lưu" vào cửa sổ dialog
				updateDialog.add(saveButton, BorderLayout.SOUTH);

				// Hiển thị cửa sổ dialog
				updateDialog.setVisible(true);
			}
		});

		buttonsPanel.add(addNVButton);

		buttonsPanel.add(deleteNVButton);

		buttonsPanel.add(updateNVButton);

		dsNv.add(scrollPane, BorderLayout.CENTER);
		bodyPanel.add(dsNv, BorderLayout.CENTER);
		bodyPanel.add(buttonsPanel, BorderLayout.EAST);

		qLNVPanel.add(bodyPanel, BorderLayout.CENTER);

		ct.hienThiNhanVienTrongBang(tableNVModel);
	}	

	private void addQuanLyThucDonPanel(JTabbedPane tabbedPane) {
		ma = new MonAn();
		ctMA = new QLMAController(ma, GiaoDienNhanVien.this);
		JPanel qLTDPanel = new JPanel();
		tabbedPane.addTab("Quản lý thực đơn", null, qLTDPanel, null);
		qLTDPanel.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns, with gaps

		JPanel infoMAn = new JPanel();
		infoMAn.setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns, with gaps
		infoMAn.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông tin món ăn",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		// ThÃ´ng tin nhÃ¢n viÃªn
		JLabel maMALabel = new JLabel("Mã món ăn:");
		maMAField = new JTextField(20);
		maMAField.setPreferredSize(new Dimension(200, 30));
		JLabel tenMALabel = new JLabel("Tên món ăn:");
		tenMAField = new JTextField(20);
		tenMAField.setPreferredSize(new Dimension(200, 30));
		JLabel dGiaLabel = new JLabel("Đơn giá¡:");
		dGiaField = new JTextField(20);
		dGiaField.setPreferredSize(new Dimension(200, 30));

		infoMAn.add(maMALabel);
		infoMAn.add(maMAField);
		infoMAn.add(tenMALabel);
		infoMAn.add(tenMAField);
		infoMAn.add(dGiaLabel);
		infoMAn.add(dGiaField);

		JPanel findMA = new JPanel();
		findMA.setLayout(new BorderLayout());
		findMA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tra cứu",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JLabel nameMALabel = new JLabel("Tên Món Ăn: ");
		nameMAField = new JTextField(20);
		nameMAField.setPreferredSize(new Dimension(200, 30));
		JLabel searchMaMALabel = new JLabel("Mã món ăn: ");
		searchMaMAField = new JTextField(20);
		searchMaMAField.setPreferredSize(new Dimension(200, 30));
		searchMAButton = new JButton("Tra cứu");

		searchMAButton.setBackground(new Color(70, 130, 180));
		searchMAButton.setForeground(Color.WHITE);
		searchMAButton.setFont(new Font("Arial", Font.BOLD, 14));
		searchMAButton.setFocusPainted(false);
		searchMAButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		searchMAButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ctMA.traCuuMonAn();
			}
		});

		JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		inputPanel.add(nameMALabel);
		inputPanel.add(nameMAField);
		inputPanel.add(searchMaMALabel);
		inputPanel.add(searchMaMAField);
		

		findMA.add(inputPanel, BorderLayout.NORTH);
		findMA.add(searchMAButton, BorderLayout.SOUTH);

		topPanel.add(infoMAn);
		topPanel.add(findMA);

		qLTDPanel.add(topPanel, BorderLayout.NORTH);

		JPanel bodyPanel = new JPanel();
		bodyPanel.setLayout(new BorderLayout());

		JPanel dsMA = new JPanel(new BorderLayout());
		dsMA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Danh sách món ăn",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		String[] columnNames = { "Mã món ăn", "Tên món ăn", "Đơn giá" };
		Object[][] dataMA = {};

		tableMAModel = new DefaultTableModel(dataMA, columnNames);
		JTable table = new JTable(tableMAModel);
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(0, 1, 30, 30));

		addMAButton = new JButton("Thêm");
		deleteMAButton = new JButton("Xóa   ");
		updateMAButton = new JButton("Sửa   ");

		// Customizing the buttons
		addMAButton.setBackground(new Color(60, 179, 113));
		addMAButton.setForeground(Color.WHITE);
		addMAButton.setFont(new Font("Arial", Font.BOLD, 14));
		addMAButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddMonAnFrame();
			}
		});

		deleteMAButton.setBackground(new Color(255, 69, 0));
		deleteMAButton.setForeground(Color.WHITE);
		deleteMAButton.setFont(new Font("Arial", Font.BOLD, 14));
		deleteMAButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn một món ăn để xóa.", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String maMA = (String) tableMAModel.getValueAt(selectedRow, 0);
					int option = JOptionPane.showConfirmDialog(null,
							"Bạn có chắc chắn muốn xóa món ăn có mã " + maMA + " không?", "Xác nhận xóa",
							JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						ctMA.xoaMonAn(maMA);
						ctMA.hienThiMonAnTrongBang(tableMAModel);
					}
				}
			}
		});

		updateMAButton.setBackground(new Color(70, 130, 180));
		updateMAButton.setForeground(Color.WHITE);
		updateMAButton.setFont(new Font("Arial", Font.BOLD, 14));
		updateMAButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the selected food item from the table
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Please select a food item to update.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String itemId = (String) table.getValueAt(selectedRow, 0);
					String itemName = (String) table.getValueAt(selectedRow, 1);
					String price = table.getValueAt(selectedRow, 2).toString(); // Ensure price is converted to String

					// Display the update dialog with the selected food item information
					showUpdateDialog(itemId, itemName, price);
				}
			}

			private void showUpdateDialog(String itemId, String itemName, String price) {
				// Create the dialog window
				JDialog updateDialog = new JDialog();
				updateDialog.setTitle("Update Food Item");
				updateDialog.setSize(400, 300);
				updateDialog.setLocationRelativeTo(null);

				JLabel itemIdLabel = new JLabel("Item ID:");
				JTextField itemIdField = new JTextField(20);
				itemIdField.setText(itemId);

				JLabel itemNameLabel = new JLabel("Item Name:");
				JTextField itemNameField = new JTextField(20);
				itemNameField.setText(itemName);

				JLabel priceLabel = new JLabel("Price:");
				JTextField priceField = new JTextField(20);
				priceField.setText(price);

				// Add the components to the dialog panel
				JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
				panel.add(itemIdLabel);
				panel.add(itemIdField);
				panel.add(itemNameLabel);
				panel.add(itemNameField);
				panel.add(priceLabel);
				panel.add(priceField);

				updateDialog.add(panel, BorderLayout.CENTER);

				// Create the "Save" button to save the updated information
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Get the updated information from the input fields
						String newItemName = itemNameField.getText();
						String newPriceStr = priceField.getText();
						String newItemId = itemIdField.getText(); // Get the new item ID

						double newPrice;
						try {
							newPrice = Double.parseDouble(newPriceStr);
						} catch (NumberFormatException ex) {
							showErrorMessage("Price must be a valid number.");
							return;
						}

						// Update the food item information
						ctMA.capNhatThongTinMA(itemId, newItemName, newPrice, newItemId);

						// Refresh the food item table
						ctMA.hienThiMonAnTrongBang(tableMAModel);

						// Close the dialog window after updating the information
						updateDialog.dispose();
					}
				});

				// Add the "Save" button to the dialog
				updateDialog.add(saveButton, BorderLayout.SOUTH);

				// Display the dialog window
				updateDialog.setVisible(true);
			}

			private void showErrorMessage(String message) {
				JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		buttonsPanel.add(addMAButton);

		buttonsPanel.add(deleteMAButton);

		buttonsPanel.add(updateMAButton);

		dsMA.add(scrollPane, BorderLayout.CENTER);
		bodyPanel.add(dsMA, BorderLayout.CENTER);
		bodyPanel.add(buttonsPanel, BorderLayout.EAST);

		qLTDPanel.add(bodyPanel, BorderLayout.CENTER);
		ctMA.hienThiMonAnTrongBang(tableMAModel);

	}

	private static void addThongKeDoanhThuPanel(JTabbedPane tabbedPane) {
		JPanel tKDTPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Thống kê doanh thu", null, tKDTPanel, null);

		JPanel criteriaPanel = new JPanel();
		criteriaPanel.setLayout(new GridLayout(2, 1));
		criteriaPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Tiêu chuẩn thống kê", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chọn tiêu chí"));
		ButtonGroup criteriaGroup = new ButtonGroup();
		JRadioButton dayRadioButton = new JRadioButton("Ngày");
		JRadioButton monthRadioButton = new JRadioButton("Tháng");
		JRadioButton yearRadioButton = new JRadioButton("Năm");
		criteriaGroup.add(dayRadioButton);
		criteriaGroup.add(monthRadioButton);
		criteriaGroup.add(yearRadioButton);
		radioPanel.add(dayRadioButton);
		radioPanel.add(monthRadioButton);
		radioPanel.add(yearRadioButton);
		criteriaPanel.add(radioPanel);

		JPanel inputPanel = new JPanel(new CardLayout());
		inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nhập thông tin"));

		JPanel dayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dayPanel.add(new JLabel("Ngày: "));
		String[] days = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		JComboBox<String> dayComboBox = new JComboBox<>(days);
		dayComboBox.setPreferredSize(new Dimension(60, 25)); // Kích thước có thể điều chỉnh
		dayPanel.add(dayComboBox);

		String[] monthsd = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		JComboBox<String> monthComboBoxd = new JComboBox<>(monthsd);
		monthComboBoxd.setPreferredSize(new Dimension(60, 25)); // Kích thước có thể điều chỉnh
		dayPanel.add(monthComboBoxd);

		String[] yearsd = { "2022", "2023", "2024", "2025", "2026" };
		JComboBox<String> yearComboBoxd = new JComboBox<>(yearsd);
		yearComboBoxd.setPreferredSize(new Dimension(80, 25)); // Kích thước có thể điều chỉnh
		dayPanel.add(yearComboBoxd);

		inputPanel.add(dayPanel, "Ngày");

		JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		monthPanel.add(new JLabel("Tháng: "));
		String[] months = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		JComboBox<String> monthComboBox = new JComboBox<>(months);
		monthComboBox.setPreferredSize(new Dimension(150, 25));
		monthPanel.add(monthComboBox);
		String[] years = { "2022", "2023", "2024", "2025", "2026" };
		monthPanel.add(new JLabel("Năm: "));
		JComboBox<String> yearComboBox = new JComboBox<>(years);
		yearComboBox.setPreferredSize(new Dimension(150, 25));
		monthPanel.add(yearComboBox);
		inputPanel.add(monthPanel, "Tháng");

		JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		yearPanel.add(new JLabel("Năm: "));
		JComboBox<String> yearComboBox2 = new JComboBox<>(years);
		yearComboBox2.setPreferredSize(new Dimension(150, 25));
		yearPanel.add(yearComboBox2);
		inputPanel.add(yearPanel, "Năm");

		criteriaPanel.add(inputPanel);

		JButton showTableButton = new JButton("Hiển thị bảng thống kê");
		showTableButton.setPreferredSize(new Dimension(200, 30));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(showTableButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bảng thống kê",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JLabel bestSellingMenuLabel = new JLabel("Món ăn bán nhiều nhất: ");
		JLabel totalCustomersLabel = new JLabel("Số lượng khách hàng: ");
		JLabel totalRevenueLabel = new JLabel("Tổng doanh thu: ");

		bestSellingMenuLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		totalCustomersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		totalRevenueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

		tablePanel.add(bestSellingMenuLabel);
		tablePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		tablePanel.add(totalCustomersLabel);
		tablePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		tablePanel.add(totalRevenueLabel);

		tKDTPanel.add(criteriaPanel, BorderLayout.NORTH);
		tKDTPanel.add(buttonPanel, BorderLayout.WEST);
		tKDTPanel.add(tablePanel, BorderLayout.CENTER);

		tablePanel.setVisible(false);

		dayRadioButton.setSelected(true);
		CardLayout cardLayout = (CardLayout) inputPanel.getLayout();
		cardLayout.show(inputPanel, "Ngày");

		dayRadioButton.addActionListener(e -> {
			cardLayout.show(inputPanel, "Ngày");
		});
		monthRadioButton.addActionListener(e -> {
			cardLayout.show(inputPanel, "Tháng");
		});
		yearRadioButton.addActionListener(e -> {
			cardLayout.show(inputPanel, "Năm");
		});

		showTableButton.addActionListener(e -> {
			String criteria = "";
			String selectedValue = "";

			if (dayRadioButton.isSelected()) {
				criteria = "Ngày";
				String day = (String) dayComboBox.getSelectedItem();
				String month = (String) monthComboBoxd.getSelectedItem();
				String year = (String) yearComboBoxd.getSelectedItem();
				selectedValue = day + "/" + month + "/" + year;
			} else if (monthRadioButton.isSelected()) {
				criteria = "Tháng";
				String month = (String) monthComboBox.getSelectedItem();
				String year = (String) yearComboBox.getSelectedItem();
				selectedValue = month + "/" + year;
			} else if (yearRadioButton.isSelected()) {
				criteria = "Năm";
				selectedValue = (String) yearComboBox2.getSelectedItem();
			}

			ThongKeController controller = new ThongKeController("./src/controller/LichSuDonHang.txt");
			Map<String, Object> statistics = controller.getStatistics(criteria, selectedValue);

			int totalCustomers = (int) statistics.get("totalCustomers");
			double totalRevenue = (double) statistics.get("totalRevenue");
			Map<String, Integer> menuCountMap = (Map<String, Integer>) statistics.get("menuCountMap");

			totalCustomersLabel.setText("Số lượng khách hàng: " + totalCustomers);
			totalRevenueLabel.setText("Tổng doanh thu: " + controller.formatCurrency(totalRevenue));
			if (!menuCountMap.isEmpty()) {
				String bestSellingMenu = menuCountMap.entrySet().stream().max(Map.Entry.comparingByValue())
						.map(Map.Entry::getKey).orElse("N/A");
				bestSellingMenuLabel.setText("Món ăn bán nhiều nhất: " + bestSellingMenu);

			} else {
				bestSellingMenuLabel.setText("Không có dữ liệu để hiển thị món ăn bán nhiều nhất.");
			}

			// Hiển thị panel chứa bảng thống kê
			tablePanel.setVisible(true);
		});
	}

	private void addInHoaDonPanel(JTabbedPane tabbedPane) {
		JPanel inHDPanel = new JPanel(new GridLayout(1, 2, 20, 20)); // 1 row, 2 columns, gap: 20px
		inHDPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

		// Left panel: Table information
		JPanel thongTinBanPanel = new JPanel(new BorderLayout(20, 20)); // 20px gap
		thongTinBanPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Thông tin bàn cần lập", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.DARK_GRAY));
		thongTinBanPanel.setBackground(Color.WHITE);

		// Panel for table name information
		JPanel banInfoPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Padding for components
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel tenBanLabel = new JLabel("Nhập bàn:");
		banInfoPanel.add(tenBanLabel, gbc);

		gbc.gridx = 1;
		JTextField tenBanField = new JTextField(15);
		tenBanField.setPreferredSize(new Dimension(200, 30));
		banInfoPanel.add(tenBanField, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		JButton showMenuButton = new JButton("Hiển thị thông tin");
		showMenuButton.setPreferredSize(new Dimension(150, 40));
		showMenuButton.setBackground(new Color(0, 153, 204));
		showMenuButton.setForeground(Color.WHITE);
		showMenuButton.setFont(new Font("Arial", Font.BOLD, 14));
		banInfoPanel.add(showMenuButton, gbc);

		thongTinBanPanel.add(banInfoPanel, BorderLayout.CENTER);

		// Create table
		String[] columnNames = { "Mã thực đơn", "Tên thực đơn", "Đơn giá", "Số lượng" };
		DefaultTableModel inHDModel = new DefaultTableModel(null, columnNames);
		JTable table = new JTable(inHDModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		thongTinBanPanel.add(scrollPane, BorderLayout.SOUTH);

		// Right panel: Invoice information
		JPanel thongTinHoaDonPanel = new JPanel(new BorderLayout(20, 20)); // 20px gap
		thongTinHoaDonPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Thông tin hóa đơn", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.DARK_GRAY));
		thongTinHoaDonPanel.setBackground(Color.WHITE);

		JPanel invoiceInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		invoiceInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
		invoiceInfoPanel.setBackground(Color.WHITE);

		invoiceInfoPanel.add(new JLabel("Mã hóa đơn:"));
		JTextField maHD = new JTextField(15);
		maHD.setPreferredSize(new Dimension(200, 30));
		invoiceInfoPanel.add(maHD);

		invoiceInfoPanel.add(new JLabel("Ngày lập:"));
		JTextField ngayLap = new JTextField(15);
		ngayLap.setPreferredSize(new Dimension(200, 30));
		invoiceInfoPanel.add(ngayLap);

		invoiceInfoPanel.add(new JLabel("Tổng tiền:"));
		JTextField tongTien = new JTextField(15);
		tongTien.setPreferredSize(new Dimension(200, 30));
		invoiceInfoPanel.add(tongTien);

		thongTinHoaDonPanel.add(invoiceInfoPanel, BorderLayout.NORTH);

		// Buttons to show invoice information and print invoice
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		buttonsPanel.setBackground(Color.WHITE);

		JButton printInvoiceButton = new JButton("In hóa đơn");
		printInvoiceButton.setPreferredSize(new Dimension(200, 40));
		printInvoiceButton.setBackground(new Color(0, 153, 204));
		printInvoiceButton.setForeground(Color.WHITE);
		printInvoiceButton.setFont(new Font("Arial", Font.BOLD, 14));
		buttonsPanel.add(printInvoiceButton);

		thongTinHoaDonPanel.add(buttonsPanel, BorderLayout.CENTER);

		inHDPanel.add(thongTinBanPanel);
		inHDPanel.add(thongTinHoaDonPanel);

		tabbedPane.addTab("In hóa đơn", null, inHDPanel, null);

		InHDController hDCT = new InHDController(inHDModel, maHD, ngayLap, tongTien, this);
		hDCT.setFilePath("./src/controller/HDData.txt");
		// Add action listeners for buttons
		showMenuButton.addActionListener(e -> {
			// Call method in controller to show menu
			hDCT.showMenuButtonClicked(tenBanField.getText());
		});

		printInvoiceButton.addActionListener(e -> {
			// Call method in controller to print invoice
			hDCT.printInvoiceButtonClicked();
		});
	}

	public static void addTBaoDatHang(JTabbedPane tabbedPane) {
		JPanel tBDHPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Thông báo đặt hàng", null, tBDHPanel, null);

		JPanel accountPanel = new JPanel(new BorderLayout());
		accountPanel.setBorder(BorderFactory.createTitledBorder("Tài khoản đặt hàng"));

		DefaultListModel<String> accountListModel = new DefaultListModel<>();
		JList<String> accountList = new JList<>(accountListModel);
		accountList.setFixedCellWidth(150);
		accountList.setVisibleRowCount(10);

		JScrollPane accountScrollPane = new JScrollPane(accountList);
		accountScrollPane.setPreferredSize(new Dimension(200, 0));

		JPanel buttonPanel = new JPanel();
		JButton confirmButton = new JButton("Xác nhận");
		buttonPanel.add(confirmButton);

		JPanel accountAndButtonPanel = new JPanel(new BorderLayout());
		accountAndButtonPanel.add(accountScrollPane, BorderLayout.CENTER);
		accountAndButtonPanel.add(buttonPanel, BorderLayout.SOUTH);

		accountPanel.add(accountAndButtonPanel, BorderLayout.CENTER);

		JPanel orderPanel = new JPanel(new BorderLayout());
		orderPanel.setBorder(BorderFactory.createTitledBorder("Danh sách món đã đặt"));

		DefaultTableModel orderTableModel = new DefaultTableModel(new String[] { "Món ăn", "Số lượng", "Giá" }, 0);
		JTable orderTable = new JTable(orderTableModel);
		JScrollPane orderScrollPane = new JScrollPane(orderTable);

		orderPanel.add(orderScrollPane, BorderLayout.CENTER);

		// Add the total amount panel
		JPanel totalAmountPanel = new JPanel(new BorderLayout());
		totalAmountPanel.setBorder(BorderFactory.createTitledBorder("Tổng tiền"));

		JLabel totalAmountLabel = new JLabel("Tổng tiền: 0 VND");
		totalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalAmountPanel.add(totalAmountLabel, BorderLayout.EAST);

		orderPanel.add(totalAmountPanel, BorderLayout.SOUTH);

		tBDHPanel.add(accountPanel, BorderLayout.WEST);
		tBDHPanel.add(orderPanel, BorderLayout.CENTER);

		OrderController controller = new OrderController(accountListModel, accountList, orderTableModel,
				totalAmountLabel);
		controller.addListeners(confirmButton);
		controller.loadOrdersData("./src/controller/DonThanhToanOnline.txt");
	}

	private void addTBao(JTabbedPane tabbedPane) {
		JPanel tbaoPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Thông Báo", null, tbaoPanel, null);

		JComboBox<String> recipientComboBox = new JComboBox<>(new String[] { "Khach Hang" });
		recipientComboBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Người nhận",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JTextArea inputArea = new JTextArea(5, 30);
		inputArea.setText("Khuyen mai " + ":");
		inputArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông Báo",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);

		JButton sendButton = new JButton("Gửi Thông Báo");
		JTextArea displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		displayArea.setWrapStyleWord(true);
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lấy nội dung từ JTextArea
				String content = inputArea.getText();
				inputArea.setText("Khuyen mai " + ":");
				JOptionPane.showMessageDialog(tbaoPanel, "Thông báo đã được gửi thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
				// Lưu nội dung vào file txt
				saveToTxtFile(content);
			}
		});
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.add(recipientComboBox, BorderLayout.NORTH);
		inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
		inputPanel.add(sendButton, BorderLayout.SOUTH);

		tbaoPanel.add(inputPanel, BorderLayout.NORTH);
		tbaoPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
	}

	public static void saveToTxtFile(String newPromotion) {
		try {
			// Tạo một file mới nếu file chưa tồn tại
			File file = new File("./src/controller/thongTinKhuyenMai.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			// Đọc toàn bộ nội dung hiện tại của file
			StringBuilder existingContent = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					existingContent.append(line).append("\n");
				}
			}

			// Thêm khuyến mãi mới vào cuối file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(existingContent.toString());
				writer.write(newPromotion + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addDKLM(JTabbedPane tabbedPane) {
		ctDK = new DKLMController(GiaoDienNhanVien.this);
		dkiPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Đăng kí lịch làm", null, dkiPanel, null);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bảng đăng kí",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
		dkiPanel.add(mainPanel, BorderLayout.CENTER);

		checkboxes = new JCheckBox[3][6];

		JLabel[] dayLabels = new JLabel[6];
		for (int i = 0; i < 6; i++) {
			dayLabels[i] = new JLabel("T" + (i + 2));
		}

		JPanel tablePanel = new JPanel(new GridLayout(4, 6));
		tablePanel.add(new JLabel());
		for (int i = 0; i < 6; i++) {
			tablePanel.add(dayLabels[i]);
		}
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 6; col++) {
				if (col == 0) {
					tablePanel.add(new JLabel("Ca " + (row + 1)));
				}
				checkboxes[row][col] = new JCheckBox();
				tablePanel.add(checkboxes[row][col]);
			}
		}

		JPanel panel = new JPanel();
		saveBtn = new JButton("Lưu lịch làm");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean[][] states = getCheckboxesState();
				ctDK.luuLichLam(states);

			}
		});
		inBtn = new JButton("In lịch làm");
		inBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ctDK.inLichLamTuFile();
			}
		});
		panel.add(saveBtn);
		panel.add(inBtn);
		dkiPanel.add(panel, BorderLayout.SOUTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
	}

	public boolean[][] getCheckboxesState() {
		boolean[][] states = new boolean[3][6];
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 6; col++) {
				states[row][col] = checkboxes[row][col].isSelected();
			}
		}
		return states;
	}

	public void addBottomPanel() {
		JPanel bottomPanel = new JPanel(new BorderLayout());
		loginLabel = new JLabel("Tên đăng nhập: " + tenNV);
		loginLabel.setFont(new Font("Arial", Font.BOLD, 16));
		bottomPanel.add(loginLabel, BorderLayout.WEST);
		add(bottomPanel, BorderLayout.SOUTH);

		Timer timer = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String maNhanVien = lgNVien.getMaNhanVien();
				tenNV = lgNVien.getEmployeeName(maNhanVien);
				String labelText = "Tên đăng nhập: " + tenNV;
				loginLabel.setText(labelText);
				if (tenNV != null) {
					((Timer) evt.getSource()).stop();
				}
			}
		});
		timer.start();
	}

	private void showAddNhanVienFrame() {
		JFrame addNhanVienFrame = new JFrame("Thêm Nhân Viên");
		addNhanVienFrame.setSize(400, 300);
		addNhanVienFrame.setLayout(new GridLayout(6, 2, 10, 10));
		addNhanVienFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel maNvLabel = new JLabel("Mã NV:");
		JTextField maNVField = new JTextField(20);
		JLabel tenNVLabel = new JLabel("Tên NV:");
		JTextField tenNVField = new JTextField(20);
		JLabel sdtLabel = new JLabel("SĐT:");
		JTextField sdtField = new JTextField(20);
		JLabel ngaySinhLabel = new JLabel("Ngày Sinh:");

		// Setup the date field with a date formatter
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormatter dateFormatter = new DateFormatter(dateFormat);
		JFormattedTextField ngaySinhField = new JFormattedTextField(dateFormatter);
		ngaySinhField.setColumns(20);
		ngaySinhField.setPreferredSize(new Dimension(200, 30));
		ngaySinhField.setValue(new Date());
		ngaySinhField.setFocusLostBehavior(JFormattedTextField.COMMIT);

		JLabel tKLabel = new JLabel("Tài khoản:");
		JTextField tKField = new JTextField(20);

		JButton saveButton = new JButton("Lưu");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String maNV = maNVField.getText();
				String tenNV = tenNVField.getText();
				String sdt = sdtField.getText();
				String ngaySinh = ngaySinhField.getText();
				String taiKhoan = tKField.getText();
				ct.themNhanVienMoi(maNV, tenNV, sdt, ngaySinh, taiKhoan, tableNVModel);
				addNhanVienFrame.dispose();
			}
		});

		addNhanVienFrame.add(maNvLabel);
		addNhanVienFrame.add(maNVField);
		addNhanVienFrame.add(tenNVLabel);
		addNhanVienFrame.add(tenNVField);
		addNhanVienFrame.add(sdtLabel);
		addNhanVienFrame.add(sdtField);
		addNhanVienFrame.add(ngaySinhLabel);
		addNhanVienFrame.add(ngaySinhField);
		addNhanVienFrame.add(tKLabel);
		addNhanVienFrame.add(tKField);
		addNhanVienFrame.add(new JLabel());
		addNhanVienFrame.add(saveButton);

		addNhanVienFrame.setLocationRelativeTo(null);
		addNhanVienFrame.setVisible(true);
	}

	private void showAddMonAnFrame() {
		JFrame addMonAnFrame = new JFrame("Thêm Món Ăn");
		addMonAnFrame.setSize(400, 300);
		addMonAnFrame.setLayout(new GridLayout(6, 2, 10, 10));
		addMonAnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel maMALabel = new JLabel("Mã Món Ăn:");
		JTextField maMAField = new JTextField(20);
		JLabel tenMALabel = new JLabel("Tên Món Ăn:");
		JTextField tenMAField = new JTextField(20);
		JLabel giaLabel = new JLabel("Giá:");
		JTextField giaField = new JTextField(20);
		JButton saveButton = new JButton("Lưu");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String maMA = maMAField.getText();
				String tenMA = tenMAField.getText();
				double gia;
				try {
					gia = Double.parseDouble(giaField.getText());
				} catch (NumberFormatException ex) {
					return;
				}

				ctMA.themMonAnMoi(maMA, tenMA, gia, tableMAModel);
				ctMA.hienThiMonAnTrongBang(tableMAModel);
				addMonAnFrame.dispose();
			}
		});

		addMonAnFrame.add(maMALabel);
		addMonAnFrame.add(maMAField);
		addMonAnFrame.add(tenMALabel);
		addMonAnFrame.add(tenMAField);
		addMonAnFrame.add(giaLabel);
		addMonAnFrame.add(giaField);
		addMonAnFrame.add(new JLabel());
		addMonAnFrame.add(saveButton);

		addMonAnFrame.setLocationRelativeTo(null);
		addMonAnFrame.setVisible(true);
	}
	
	 private void addTBDatBan(JTabbedPane tabbedPane) {
	        JPanel datBanPanel = new JPanel(new BorderLayout());
	        tabbedPane.addTab("Quản Lí Đặt Bàn", null, datBanPanel, null);

	        JPanel tablePanel = new JPanel(new GridLayout(0, 3, 10, 10));
	        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        List<Ban> danhSachBan = qlDB.getDanhSachBan();

	        for (Ban ban : danhSachBan) {
	            JPanel tableInfoPanel = new JPanel(new GridBagLayout());
	            tableInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
	                    "Bàn " + ban.getSoBan(), TitledBorder.CENTER, TitledBorder.TOP));

	            String statusText = ban.isCheckBan() ? "Đã đặt bởi " + ban.getTenKhachHang() : "Trống";
	            JLabel statusLabel = new JLabel(statusText);
	            JLabel seatsLabel = new JLabel("Số ghế: " + ban.getSoGhe());
	            JButton reserveButton = new JButton(ban.isCheckBan() ? "Hủy" : "Đặt");

	            GridBagConstraints gbcStatusLabel = new GridBagConstraints();
	            gbcStatusLabel.gridx = 0;
	            gbcStatusLabel.gridy = 0;
	            gbcStatusLabel.weightx = 1.0;
	            gbcStatusLabel.weighty = 1.0;
	            gbcStatusLabel.anchor = GridBagConstraints.CENTER;

	            GridBagConstraints gbcSeatsLabel = new GridBagConstraints();
	            gbcSeatsLabel.gridx = 0;
	            gbcSeatsLabel.gridy = 1;
	            gbcSeatsLabel.weightx = 1.0;
	            gbcSeatsLabel.weighty = 1.0;
	            gbcSeatsLabel.anchor = GridBagConstraints.CENTER;

	            GridBagConstraints gbcButton = new GridBagConstraints();
	            gbcButton.gridx = 0;
	            gbcButton.gridy = 2;
	            gbcButton.weightx = 1.0;
	            gbcButton.weighty = 1.0;
	            gbcButton.anchor = GridBagConstraints.CENTER;

	            tableInfoPanel.add(statusLabel, gbcStatusLabel);
	            tableInfoPanel.add(seatsLabel, gbcSeatsLabel);
	            tableInfoPanel.add(reserveButton, gbcButton);

	            reserveButton.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    qlDB.datBan(ban.getSoBan());
	                    Ban updatedBan = qlDB.getDanhSachBan().get(ban.getSoBan() - 1);
	                    String updatedStatusText = updatedBan.isCheckBan() ? "Đã đặt bởi " + updatedBan.getTenKhachHang() : "Trống";
	                    statusLabel.setText(updatedStatusText);
	                    reserveButton.setText(updatedBan.isCheckBan() ? "Hủy" : "Đặt");
	                }
	            });

	            tablePanel.add(tableInfoPanel);
	        }

	        datBanPanel.add(tablePanel, BorderLayout.CENTER);
	    }
}
