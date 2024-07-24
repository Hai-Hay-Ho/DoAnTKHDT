package view;

import java.awt.BorderLayout;
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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.DatBanKhachHangOnline;
import controller.GoiMonKhachHangOnline;
import controller.LoginControllerKhachHang;
import controller.ThongBaoKhuyenMai;
import controller.ThongTinKhachHangOnline;
import model.Ban;
import model.CardPayment;
import model.CashPayment;
import model.KhachHang;
import model.MonAn;
import model.HeThongNhaHang;
import model.ObserverKM;
import model.PaymentStrategy;
import model.TaiKhoan;

public class GiaoDienKhachHang extends JPanel implements ObserverKM {
	private DefaultTableModel tableModel;
	private JTabbedPane tabbedPane;
	private GoiMonKhachHangOnline goiMonKH;
	private DatBanKhachHangOnline dbKH;
	private Component parentComponent;
	private JLabel infoLabel;
	private ThongBaoKhuyenMai thongBaoKM;
	private HeThongNhaHang nhaHang;
	private LoginControllerKhachHang lgKH;
	private NhaHangPanel view;
	private TaiKhoan tk;
	private KhachHang kh;
	private ThongTinKhachHangOnline ttkh;
	private JTextField accountNameField, passwordField, customerNameField, phoneNumberField, addressField;

	public GiaoDienKhachHang(NhaHangPanel view) {
		this.view = view;
		setLayout(new BorderLayout(0, 0));
		Font tabFont = new Font("Arial", Font.BOLD, 16);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setFont(tabFont);
		tableModel = new DefaultTableModel();
		dbKH = new DatBanKhachHangOnline();
		goiMonKH = new GoiMonKhachHangOnline();
		nhaHang = new HeThongNhaHang("Công ty nhóm 5");
		this.thongBaoKM = new ThongBaoKhuyenMai(nhaHang);
		thongBaoKM.dangKyThongBaoKM(this);
		kh = new KhachHang();
		tk = new TaiKhoan();
		lgKH = new LoginControllerKhachHang(view, kh, tk);
		ttkh = new ThongTinKhachHangOnline();
		displayView();
	}

	public void displayView() {
		addGoiMon(tabbedPane);
		addDatBan(tabbedPane);
		addThongTinKhuyenMai(tabbedPane);
		addThongTinKhachHang(tabbedPane);
		add(tabbedPane, BorderLayout.CENTER);
	}

	public void addGoiMon(JTabbedPane tabbedPane) {
		JPanel goiMonPanel = new JPanel(new BorderLayout(10, 10));
		tabbedPane.addTab("Đặt Món", null, goiMonPanel, null);

		JPanel menuPanel = new JPanel(new BorderLayout());
		menuPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Menu Thức Ăn",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
		JList<String> menuList = new JList<>(new DefaultListModel<>());
		JScrollPane menuScrollPane = new JScrollPane(menuList);
		menuPanel.add(menuScrollPane, BorderLayout.CENTER);

		JComboBox<String> categoryComboBox = new JComboBox<>(
				new String[] { "Món Chính", "Món khai vị", "Món Ăn Vặt", "Đồ uống" });
		menuPanel.add(categoryComboBox, BorderLayout.NORTH);

		categoryComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedCategory = (String) categoryComboBox.getSelectedItem();
				goiMonKH.updateFoodList(selectedCategory, menuList);
			}
		});

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chi Tiết Món Ăn",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

		JLabel maMonAnLabel = new JLabel("Mã món ăn:");
		JTextField maMonAnJT = new JTextField(20);
		maMonAnJT.setEditable(false);
		maMonAnJT.setMaximumSize(new Dimension(300, 20));

		JLabel tableNumberLabel = new JLabel("Tên món:");
		JTextField tableNumberField = new JTextField(20);
		tableNumberField.setEditable(false);
		tableNumberField.setMaximumSize(new Dimension(300, 20));

		JLabel unitPriceLabel = new JLabel("Đơn Giá:");
		JTextField unitPriceField = new JTextField(20);
		unitPriceField.setEditable(false);
		unitPriceField.setMaximumSize(new Dimension(300, 20));

		JLabel quantityLabel = new JLabel("Số Lượng:");
		JTextField quantityField = new JTextField(20);
		quantityField.setMaximumSize(new Dimension(300, 20));

		inputPanel.add(maMonAnLabel);
		inputPanel.add(maMonAnJT);
		inputPanel.add(Box.createVerticalStrut(10));
		inputPanel.add(tableNumberLabel);
		inputPanel.add(tableNumberField);
		inputPanel.add(Box.createVerticalStrut(10));
		inputPanel.add(unitPriceLabel);
		inputPanel.add(unitPriceField);
		inputPanel.add(Box.createVerticalStrut(10));
		inputPanel.add(quantityLabel);
		inputPanel.add(quantityField);
		inputPanel.add(Box.createVerticalStrut(10));

		// Chi tiết món ăn:
		menuList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String selectedFood = menuList.getSelectedValue();
					if (selectedFood != null) {
						MonAn monAn = goiMonKH.getMonAnByName(selectedFood);
						if (monAn != null) {
							maMonAnJT.setText(monAn.getMaMonAn());
							tableNumberField.setText(monAn.getTenMonAn());
							unitPriceField.setText(String.valueOf(monAn.getGia()));

						}
					}
				}
			}
		});

		centerPanel.add(inputPanel, BorderLayout.CENTER);

		JButton saveOrderButton = new JButton("Đặt Món");
		centerPanel.add(saveOrderButton, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton thanhToanButton = new JButton("Thanh toán");
		JButton xoaMonButton = new JButton("Xóa Món");
		buttonPanel.add(xoaMonButton);
		buttonPanel.add(thanhToanButton);

		JPanel orderListPanel = new JPanel(new BorderLayout());
		orderListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Danh Sách Đặt Món", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		String[] columnNames = { "Mã Món Ăn", "Tên Món Ăn", "Đơn Giá", "Số Lượng" };
		Object[][] data = {};
		tableModel = new DefaultTableModel(data, columnNames);
		JTable orderTable = new JTable(tableModel);
		JScrollPane orderScrollPane = new JScrollPane(orderTable);
		orderListPanel.add(orderScrollPane, BorderLayout.CENTER);

		orderListPanel.setPreferredSize(new Dimension(350, 0));

		menuPanel.setPreferredSize(new Dimension(250, 0));
		centerPanel.setPreferredSize(new Dimension(150, 0));

		goiMonPanel.add(menuPanel, BorderLayout.WEST);
		goiMonPanel.add(centerPanel, BorderLayout.CENTER);
		goiMonPanel.add(orderListPanel, BorderLayout.EAST);
		goiMonPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Đặt Món
		saveOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String maMonAn = maMonAnJT.getText();
				String tenMonAn = tableNumberField.getText();
				String donGiaStr = unitPriceField.getText();
				String soLuongStr = quantityField.getText();
				if (maMonAn.isEmpty() || tenMonAn.isEmpty() || donGiaStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn.");
					return;
				}
				if (soLuongStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng.");
					return;
				}
				int soLuong = 0;
				try {
					soLuong = Integer.parseInt(soLuongStr);
					if (soLuong < 1) {
						JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng hợp lệ (lớn hơn 0).");
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ. Vui lòng nhập số nguyên dương.");
					return;
				}
				double donGia = Double.parseDouble(donGiaStr);
				MonAn monAn = new MonAn(maMonAn, tenMonAn, donGia);
				goiMonKH.datMon(tableModel, monAn, soLuong);
				maMonAnJT.setText("");
				tableNumberField.setText("");
				unitPriceField.setText("");
				quantityField.setText("");
			}
		});

		// Xử lí sự kiện thanh toán:
		thanhToanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a dialog to prompt for payment method selection
				JDialog paymentDialog = new JDialog();
				paymentDialog.setTitle("Chọn Phương Thức Thanh Toán");
				paymentDialog.setModal(true);

				// Create buttons for cash and bank transfer payment
				JButton cashPaymentButton = new JButton("Thanh toán tiền mặt");
				JButton bankTransferButton = new JButton("Thanh toán chuyển khoản");

				// Add action listeners to handle payment selection
				cashPaymentButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String tenTaiKhoan = lgKH.getTenKhachHang();
						PaymentStrategy cashPaymentStrategy = new CashPayment();
						goiMonKH.thanhToan(tableModel, parentComponent, cashPaymentStrategy, "Tiền mặt", tenTaiKhoan);
						paymentDialog.dispose();
					}
				});

				bankTransferButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String tenTaiKhoan = lgKH.getTenKhachHang();

						PaymentStrategy bankTransferStrategy = new CardPayment();
						goiMonKH.thanhToan(tableModel, parentComponent, bankTransferStrategy, "Chuyển khoản",
								tenTaiKhoan);
						paymentDialog.dispose();
					}
				});

				// Create a panel to hold the payment buttons
				JPanel paymentPanel = new JPanel();
				paymentPanel.setLayout(new GridLayout(2, 1));
				paymentPanel.add(cashPaymentButton);
				paymentPanel.add(bankTransferButton);

				// Add the payment panel to the dialog
				paymentDialog.add(paymentPanel);
				paymentDialog.pack();
				paymentDialog.setLocationRelativeTo(null);
				paymentDialog.setVisible(true);
			}
		});

		// Xóa món ăn:
		xoaMonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = orderTable.getSelectedRow();
				if (selectedRow != -1) {
					int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa món ăn này không?",
							"Xác nhận xóa món", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						tableModel.removeRow(selectedRow);
					}
				}
			}
		});

	}

	private void addDatBan(JTabbedPane tabbedPane) {
		JPanel datBanPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Đặt Bàn", null, datBanPanel, null);

		JPanel tablePanel = new JPanel(new GridLayout(0, 3, 10, 10));
		tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		List<Ban> danhSachBan = dbKH.getDanhSachBan();

		for (Ban ban : danhSachBan) {
			JPanel tableInfoPanel = new JPanel(new GridBagLayout());
			tableInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					"Bàn " + ban.getSoBan(), TitledBorder.CENTER, TitledBorder.TOP));

			JLabel statusLabel = new JLabel(ban.isCheckBan() ? "Đã đặt" : "Trống");
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
					dbKH.datBan(ban.getSoBan());
					Ban updatedBan = dbKH.getDanhSachBan().get(ban.getSoBan() - 1);
					statusLabel.setText(updatedBan.isCheckBan() ? "Đã đặt" : "Trống");
					reserveButton.setText(updatedBan.isCheckBan() ? "Hủy" : "Đặt");
				}
			});

			tablePanel.add(tableInfoPanel);
		}

		datBanPanel.add(tablePanel, BorderLayout.CENTER);
	}

	private void addThongTinKhuyenMai(JTabbedPane tabbedPane) {
		JPanel khuyenMaiPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Khuyến Mãi", null, khuyenMaiPanel, null);

		JPanel promotionPanel = new JPanel(new BorderLayout());
		promotionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel headerLabel = new JLabel("Thông Tin Khuyến Mãi", SwingConstants.CENTER);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 18));

		infoLabel = new JLabel("<html>" + nhaHang.getThongBao().replaceAll("\n", "<br>") + "</html>",
				SwingConstants.CENTER);
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		promotionPanel.add(headerLabel, BorderLayout.NORTH);
		promotionPanel.add(infoLabel, BorderLayout.CENTER);

		khuyenMaiPanel.add(promotionPanel, BorderLayout.CENTER);
	}

	@Override
	public void updateKM(String tenCongTy, String noidungchuongtrinh) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String updatedPromotion = infoLabel.getText() + "<br>" + noidungchuongtrinh;
				infoLabel.setText("<html>" + updatedPromotion + "</html>");
			}
		});
	}

	private void addThongTinKhachHang(JTabbedPane jTabbedPane) {
	    JPanel panel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.anchor = GridBagConstraints.WEST;

	    JLabel accountNameLabel = new JLabel("Tên Tài Khoản:");
	    accountNameField = new JTextField(20);
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    panel.add(accountNameLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(accountNameField, gbc);

	    JLabel passwordLabel = new JLabel("Mật Khẩu:");
	    passwordField = new JPasswordField(20);
	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    panel.add(passwordLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(passwordField, gbc);

	    JLabel customerNameLabel = new JLabel("Tên Khách Hàng:");
	    customerNameField = new JTextField(20);
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    panel.add(customerNameLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(customerNameField, gbc);

	    JLabel phoneNumberLabel = new JLabel("Số Điện Thoại:");
	    phoneNumberField = new JTextField(20);
	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    panel.add(phoneNumberLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(phoneNumberField, gbc);

	    JLabel addressLabel = new JLabel("Địa Chỉ:");
	    addressField = new JTextField(20);
	    gbc.gridx = 0;
	    gbc.gridy = 4;
	    panel.add(addressLabel, gbc);
	    gbc.gridx = 1;
	    panel.add(addressField, gbc);

	    JButton submitButton = new JButton("Cập Nhật Thông Tin");
	    gbc.gridx = 1;
	    gbc.gridy = 5;
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    panel.add(submitButton, gbc);

	    JButton displayButton = new JButton("Hiển Thị Thông Tin");
	    gbc.gridx = 0;
	    gbc.gridy = 5;
	    panel.add(displayButton, gbc);

	    displayButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String tenTaiKhoan = lgKH.getTenKhachHang();
	            accountNameField.setText(tenTaiKhoan);
	            accountNameField.setEnabled(false);
	            String mk = new String(lgKH.getMKKhachHang());
	            passwordField.setText(mk);
	            KhachHang kh = ttkh.layTTKH(tenTaiKhoan, mk);
	            if (kh != null) {
	                customerNameField.setText(kh.getTenKH());
	                phoneNumberField.setText(kh.getSdt());
	                addressField.setText(kh.getDiaChi());
	            }
	        }
	    });

	    submitButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String tenTaiKhoan = accountNameField.getText();
	            if (tenTaiKhoan.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Vui lòng nhập tên tài khoản.");
	                return;
	            }

	            String matKhau = passwordField.getText();
	            String tenKhachHang = customerNameField.getText();
	            String soDienThoai = phoneNumberField.getText();
	            String diaChi = addressField.getText();

	            boolean result = ttkh.capNhatThongTinKhachHang(tenTaiKhoan, matKhau, tenKhachHang, soDienThoai, diaChi);
	            if (result) {
	                JOptionPane.showMessageDialog(null, "Thông tin đã được cập nhật thành công!");
	            } else {
	                JOptionPane.showMessageDialog(null, "Cập nhật thất bại. Vui lòng kiểm tra lại thông tin.");
	            }
	        }
	    });

	    jTabbedPane.addTab("Thông Tin Khách Hàng", panel);
	}




}
