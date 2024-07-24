package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
import controller.ThongBaoKhuyenMai;

import model.CardPayment;
import model.CashPayment;
import model.MonAn;
import model.HeThongNhaHang;
import model.ObserverKM;
import model.PaymentStrategy;

public class GiaoDienDVOff extends JPanel implements ObserverKM {
	private DefaultTableModel tableModel;
	private JTabbedPane tabbedPane;
	private GoiMonKhachHangOnline goiMonKH;
	private Component parentComponent;
	private JLabel infoLabel;
	private ThongBaoKhuyenMai thongBaoKM;
	private HeThongNhaHang nhaHang;

	public GiaoDienDVOff() {
		setLayout(new BorderLayout(0, 0));
		Font tabFont = new Font("Arial", Font.BOLD, 16);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setFont(tabFont);
		tableModel = new DefaultTableModel();
		goiMonKH = new GoiMonKhachHangOnline();
		nhaHang = new HeThongNhaHang("Nhóm 11");
		this.thongBaoKM = new ThongBaoKhuyenMai(nhaHang);
		thongBaoKM.dangKyThongBaoKM(this);
		displayView();
	}

	public void displayView() {
		addGoiMon(tabbedPane);
		addThongTinKhuyenMai(tabbedPane);
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
		centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Chi Tiet Mon An",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

		JLabel maMonAnLabel = new JLabel("Ma mon an:");
		JTextField maMonAnJT = new JTextField(20);
		maMonAnJT.setEditable(false);
		maMonAnJT.setMaximumSize(new Dimension(300, 20));

		JLabel tableNumberLabel = new JLabel("Ten mon:");
		JTextField tableNumberField = new JTextField(20);
		tableNumberField.setEditable(false);
		tableNumberField.setMaximumSize(new Dimension(300, 20));

		JLabel unitPriceLabel = new JLabel("Don gia:");
		JTextField unitPriceField = new JTextField(20);
		unitPriceField.setEditable(false);
		unitPriceField.setMaximumSize(new Dimension(300, 20));

		JLabel quantityLabel = new JLabel("So luong:");
		JTextField quantityField = new JTextField(20);
		quantityField.setMaximumSize(new Dimension(300, 20));

		JLabel soBan = new JLabel("Số Bàn:");
		JTextField soBanDangNgoi = new JTextField(20);
		soBanDangNgoi.setText("1");
		soBanDangNgoi.setEditable(false);
		soBanDangNgoi.setMaximumSize(new Dimension(300, 20));

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
		inputPanel.add(soBan);
		inputPanel.add(soBanDangNgoi);
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

		JButton saveOrderButton = new JButton("Dat mon");
		centerPanel.add(saveOrderButton, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton thanhToanButton = new JButton("Thanh toan");
		JButton xoaMonButton = new JButton("Xoa mon");

		buttonPanel.add(xoaMonButton);
		buttonPanel.add(thanhToanButton);

		JPanel orderListPanel = new JPanel(new BorderLayout());
		orderListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Danh Sach Dat Mon", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
		/////////////////////////////////////////////
		String[] columnNames = { "Mã Món Ăn", "Tên Món Ăn", "Đơn Giá", "Số Lượng", "Số Bàn" };
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

		saveOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String maMonAn = maMonAnJT.getText();
				String tenMonAn = tableNumberField.getText();
				String donGiaStr = unitPriceField.getText();
				String soLuongStr = quantityField.getText();
				String soBanStr = soBanDangNgoi.getText();
				if (maMonAn.isEmpty() || tenMonAn.isEmpty() || donGiaStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui long chon mon an.");
					return;
				}
				if (soLuongStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui long nhap so luong.");
					return;
				}
				int soLuong = 0;
				try {
					soLuong = Integer.parseInt(soLuongStr);
					if (soLuong < 1) {
						JOptionPane.showMessageDialog(null, "Vui lòng nhap so luong hop le (>0).");
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "So luong khong hop le.Vui long nhap so nguyen duong.");
					return;
				}
				if (maMonAn.isEmpty() || tenMonAn.isEmpty() || donGiaStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui long chon mon an.");
					return;
				}
				if (soLuongStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui long nhap so luong.");
					return;
				}

				int soBan = 0;
				try {
					soBan = Integer.parseInt(soBanStr);
					if (soBan < 1) {
						JOptionPane.showMessageDialog(null, "Vui lòng nhap so luong hop le (>0).");
						return;
					} else if (soBan > 15) {
						JOptionPane.showMessageDialog(null, "Vui lòng nhap so luong hop le (<15).");
						return;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Ban chua chon ban");
					return;
				}

				double donGia = Double.parseDouble(donGiaStr);
				MonAn monAn = new MonAn(maMonAn, tenMonAn, donGia);
				goiMonKH.datMonOff(tableModel, monAn, soLuong, soBan);
				maMonAnJT.setText("");
				tableNumberField.setText("");
				unitPriceField.setText("");
				quantityField.setText("");

			}
		});
//đang vướng chỗ này
		thanhToanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String src = e.getActionCommand();
				if (src.equals("Thanh toan")) {
					int rowCount = tableModel.getRowCount();
					int columnCount = tableModel.getColumnCount();
					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter("./src/controller/LichSuHoaDonOffline.txt"))) {

						for (int column = 0; column < columnCount; column++) {
							writer.write(tableModel.getColumnName(column) + "\t" + "\t");
						}

						writer.newLine();
						for (int row = 0; row < rowCount; row++) {
							for (int column = 0; column < columnCount; column++) {
								writer.write(tableModel.getValueAt(row, column).toString() + "\t" + "\t");
							}
							writer.newLine();
						}

					} catch (IOException ie) {
						ie.printStackTrace();
					}

				}

				// Create a dialog to prompt for payment method selection
				JDialog paymentDialog = new JDialog();
				paymentDialog.setTitle("Chon Phuong Thuc Thanh Toan");
				paymentDialog.setModal(true);

				// Create buttons for cash and bank transfer payment
				JButton cashPaymentButton = new JButton("Thanh toan tien mat");
				JButton bankTransferButton = new JButton("Thanh toan chuyen khoan");

				// Add action listeners to handle payment selection
				cashPaymentButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PaymentStrategy cashPaymentStrategy = new CashPayment();
						goiMonKH.thanhToanOffline(tableModel, parentComponent, cashPaymentStrategy, "Tien mat",soBanDangNgoi.getText());
						paymentDialog.dispose(); // Close the dialog after selection
					}
				});

				bankTransferButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PaymentStrategy bankTransferStrategy = new CardPayment();
						goiMonKH.thanhToanOffline(tableModel, parentComponent, bankTransferStrategy,
								"Chuyen khoan",soBanDangNgoi.getText());
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

		xoaMonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = orderTable.getSelectedRow();
				if (selectedRow != -1) {
					int option = JOptionPane.showConfirmDialog(null, "Ban co muon xoa mon an nay khong?",
							"Xac nhan xoa mon", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						tableModel.removeRow(selectedRow);
					}
				}
			}
		});


	}


	private void addThongTinKhuyenMai(JTabbedPane tabbedPane) {
		JPanel khuyenMaiPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("Khuyen Mai", null, khuyenMaiPanel, null);

		JPanel promotionPanel = new JPanel(new BorderLayout());
		promotionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel headerLabel = new JLabel("Thong Tin Khuyen Mai", SwingConstants.CENTER);
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

}
