package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.LoginControllerKhachHang;
import controller.LoginControllerNhanVien;
import model.KhachHang;
import model.NhanVien;
import model.TaiKhoan;

public class NhaHangPanel extends JPanel {
	JPanel logInPanel, signUpPanel, mainPanel, cardPanel;
	CardLayout cardLayout;
	JLabel tenDN, sdt, diachi;
	JTextField tenDNText, sdtText, diachiText, taikhoanText;
	GiaoDienNhanVien qly;
	GiaoDienKhachHang kh;
	GiaoDienDVOff off;
	JPasswordField matkhauText;
	String tenKH;
	String mkKH;
	String maNVien;

	public String getMaNVien() {
		return maNVien;
	}

	public void setMaNVien(String maNVien) {
		this.maNVien = maNVien;
	}

	public String getMkKH() {
		return mkKH;
	}

	public void setMkKH(String mkKH) {
		this.mkKH = mkKH;
	}

	public NhaHangPanel(String tenKH) {
		super();
		this.tenKH = tenKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	NhaHangPanel() {
		taikhoanText = new JTextField();
		setLayout(cardLayout = new CardLayout());
		qly = new GiaoDienNhanVien(this);
		kh = new GiaoDienKhachHang(this);
		off = new GiaoDienDVOff();
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		add(mainPanel = new mainPanel(), "giaodienchinh");
		add(logInPanel = new logInPanel(), "dangnhap");
		add(signUpPanel = new signUpPanel(), "dangki");
		add(qly, "giaodienquanly");
		add(kh, "giaodienkhachhang");
		add(off,"giaodienoff");
		cardLayout.show(this, "giaodienchinh");
	}

	private void resizeFrame(String panelName) {
		Dimension preferredSize;
		switch (panelName) {
		case "giaodienchinh":
			preferredSize = new Dimension(800, 600); // Change to desired size
			break;
		case "dangnhap":
			preferredSize = new Dimension(400, 300); // Change to desired size
			break;
		case "dangki":
			preferredSize = new Dimension(500, 400); // Change to desired size
			break;
		case "giaodienquanly":

			preferredSize = new Dimension(1500, 700); // Change to desired size
			break;
		case "giaodienkhachhang":
			preferredSize = new Dimension(800, 600); // Change to desired size
			break;
		default:
			preferredSize = new Dimension(800, 600); // Default size
			break;
		}

		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		if (topFrame != null) {
			topFrame.setSize(preferredSize);
			((NhaHangFrame) topFrame).centerFrame(); // Center the frame after resizing
		}
	}

	public void change(String panelName) {
		cardLayout.show(NhaHangPanel.this, panelName);
		resizeFrame(panelName);
	}

	// gioi han ki tu trong chuoi
	static class checkLenText {
		public static void checkLen(JTextField text, int maxLen) {
			text.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					if (text.getText().length() >= maxLen) {
						e.consume();// khong cho nhap ki tu vao
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

//giao dien khi run app
	class mainPanel extends JPanel {
		JPanel buttonPanel, titlePanel;
		JLabel mainLabel;
		JButton onButton, offButton;

		mainPanel() {
			setLayout(new BorderLayout());
			buttonPanel = new JPanel();
			titlePanel = new JPanel();
			titlePanel.setLayout(new BorderLayout());
			mainLabel = new JLabel("Quản Lý Nhà Hàng");
			mainLabel.setHorizontalAlignment(JLabel.CENTER);
			mainLabel.setFont(new Font(null, 3, 25));
			add(titlePanel, BorderLayout.CENTER);
			add(buttonPanel, BorderLayout.SOUTH);
			titlePanel.add(mainLabel, BorderLayout.CENTER);

			onButton = new JButton("Dịch vụ Online");
			onButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout.show(NhaHangPanel.this, "dangnhap");
					change("dangnhap");
				}
			});
			offButton = new JButton("Dịch vụ Offline");
			offButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					change("giaodienoff");
				}
			});
			buttonPanel.add(offButton);
			buttonPanel.add(onButton);
		}
	}

//log in
	class logInPanel extends JPanel {
		JPanel topPanel, centerPanel, botPanel;
		JLabel logInLabel, checkLabel, signUpLabel, taikhoanLabel, matkhauLabel, hideOrShowLabel;
		JCheckBox checkNhanVienBox, hideOrShowBox;
		JButton login;
		JTextField taikhoanText, sdtText;
		JPasswordField matkhauText;

		logInPanel() {
			setLayout(new BorderLayout());
			topPanel = new topPanel();
			centerPanel = new centerPanel();
			botPanel = new botPanel();
			sdtText = new JTextField();
			add(topPanel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
			add(botPanel, BorderLayout.SOUTH);
		}

		class topPanel extends JPanel {
			topPanel() {
				setLayout(new GridLayout(1, 2));
				logInLabel = new JLabel("Đăng Nhập");
				logInLabel.setFont(new Font(null, 1, 22));
				logInLabel.setHorizontalAlignment(JLabel.CENTER);
				add(logInLabel);
			}
		}

		class centerPanel extends JPanel {

			centerPanel() {
				setLayout(new GridLayout(4, 2));
				taikhoanLabel = new JLabel("Tài khoản");
				matkhauLabel = new JLabel("Mật khẩu");
				taikhoanText = new JTextField();
				taikhoanText.setPreferredSize(new Dimension(50, 20));
				matkhauText = new JPasswordField();
				matkhauText.setPreferredSize(new Dimension(50, 20));
				hideOrShowLabel = new JLabel("Ẩn/Hiện mật khẩu");
				hideOrShowLabel.setFont(new Font(null, 1, 11));
				hideOrShowBox = new JCheckBox();

				hideOrShowBox.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if (hideOrShowBox.isSelected()) {
							String password = new String(matkhauText.getPassword());
							JTextField tempTextField = new JTextField(password);
							remove(matkhauText);
							add(tempTextField, 3);
							checkLenText.checkLen(tempTextField, 8);
							hideOrShowLabel.setText("Hiện mật khẩu");
							hideOrShowLabel.setFont(new Font(null, 1, 11));
						} else {
							JTextField tempTextField = (JTextField) getComponent(3);
							String password = tempTextField.getText();
							remove(tempTextField);
							matkhauText = new JPasswordField(password);
							add(matkhauText, 3);
							checkLenText.checkLen(matkhauText, 8);
							hideOrShowLabel.setText("Ẩn mật khẩu");
							hideOrShowLabel.setFont(new Font(null, 1, 11));
						}
					}
				});

				add(taikhoanLabel);
				add(taikhoanText);
				add(matkhauLabel);
				add(matkhauText);
				add(hideOrShowLabel);
				add(hideOrShowBox);
				checkLenText.checkLen(taikhoanText, 15);
				matkhauText.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						if (matkhauText.getPassword().length >= 8) {
							e.consume();
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub

					}
				});
			}
		}

		class botPanel extends JPanel {
			JPanel checkBoxPanel, buttonLogPanel, labelSignUpLabel;
			JLabel maNV;
			JTextField tenDNText, sdtText, diachiText, maNVText;

			public JTextField getMaNVText() {
				return maNVText;
			}

			public void setMaNVText(JTextField maNVText) {
				this.maNVText = maNVText;
			}

			public JTextField getTenDNText() {
				return tenDNText;
			}

			public void setTenDNText(JTextField tenDNText) {
				this.tenDNText = tenDNText;
			}

			public JTextField getSdtText() {
				return sdtText;
			}

			public void setSdtText(JTextField sdtText) {
				this.sdtText = sdtText;
			}

			public JTextField getDiachiText() {
				return diachiText;
			}

			public void setDiachiText(JTextField diachiText) {
				this.diachiText = diachiText;
			}

			botPanel() {
				setLayout(new BorderLayout());
				checkBoxPanel = new JPanel();
				buttonLogPanel = new JPanel();
				labelSignUpLabel = new JPanel();
				maNV = new JLabel("Mã nhân viên");
				maNVText = new JTextField();
				add(checkBoxPanel, BorderLayout.NORTH);
				add(buttonLogPanel, BorderLayout.CENTER);
				add(labelSignUpLabel, BorderLayout.SOUTH);
				signUpLabel = new JLabel("Đăng ký");
				signUpLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cardLayout.show(NhaHangPanel.this, "dangki");
					}
				});
				signUpLabel.setFont(new Font(null, 2, 12));
				checkNhanVienBox = new JCheckBox("");
				checkNhanVienBox.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if (checkNhanVienBox.isSelected()) {
							centerPanel.remove(taikhoanLabel);
							centerPanel.remove(taikhoanText);
							centerPanel.remove(matkhauLabel);
							centerPanel.remove(matkhauText);
							centerPanel.remove(hideOrShowLabel);
							centerPanel.remove(hideOrShowBox);
							centerPanel.add(maNV);
							centerPanel.add(maNVText);
							checkLenText.checkLen(maNVText, 6);// id nhan vien gioi han 6 so
							centerPanel.revalidate();
							centerPanel.repaint();
						} else {
							if (maNVText != null) {
								centerPanel.remove(maNV);
								centerPanel.remove(maNVText);
								centerPanel.add(taikhoanLabel);
								centerPanel.add(taikhoanText);
								centerPanel.add(matkhauLabel);
								centerPanel.add(matkhauText);
								centerPanel.add(hideOrShowLabel);
								centerPanel.add(hideOrShowBox);
							}
						}
						centerPanel.revalidate();
						centerPanel.repaint();
					}

				});
				login = new JButton("Đăng nhập");
				login.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (checkNhanVienBox.isSelected()) {
							NhanVien nv = new NhanVien(maNVText.getText());
							maNVien = nv.getMaNV();
							qly.displayView(maNVText.getText());
							LoginControllerNhanVien controllerNV = new LoginControllerNhanVien(NhaHangPanel.this, nv);
							controllerNV.handleLoginNV();
						} else {
							char[] pwChar = matkhauText.getPassword();
							String pw = String.valueOf(pwChar);
							TaiKhoan tk = new TaiKhoan(taikhoanText.getText(), pw);
							KhachHang kh = new KhachHang(tk);
							LoginControllerKhachHang controllerKH = new LoginControllerKhachHang(NhaHangPanel.this, kh,
									tk);
							controllerKH.handleLogin();
							tenKH = tk.getUserName();
							mkKH = tk.getPassW();

						}
					}
				});
				login.setHorizontalAlignment(JButton.CENTER);
				checkLabel = new JLabel("Bạn là nhân viên?");
				checkBoxPanel.add(checkLabel);
				checkBoxPanel.add(checkNhanVienBox);
				buttonLogPanel.add(login);
				labelSignUpLabel.add(signUpLabel);

			}
		}
	}

//sign up
	class signUpPanel extends JPanel {
		JPanel topPanel, centerPanel, botPanel;
		JLabel signUpLabel, logInLabel, taikhoan, matkhau, xacthucmatkhau;
		JTextField taikhoanText, sdtText, tenDNText, diachiText; // Thêm sdtText vào đây
		JPasswordField matkhauText, xtmkText;

		signUpPanel() {
			setLayout(new BorderLayout());
			topPanel = new topPanel();
			centerPanel = new centerPanel();
			botPanel = new botPanel();
			//sdtText = new JTextField(); // Khởi tạo sdtText ở đây
			add(topPanel, BorderLayout.NORTH);
			add(centerPanel, BorderLayout.CENTER);
			add(botPanel, BorderLayout.SOUTH);
		}

		class topPanel extends JPanel {

			topPanel() {
				signUpLabel = new JLabel("Đăng Ký");
				signUpLabel.setFont(new Font(null, 1, 22));
				add(signUpLabel);
			}

		}

		class centerPanel extends JPanel {
			JLabel hideOrShowLabel;
			JCheckBox hideOrShowBox;

			centerPanel() {
				setLayout(new GridLayout(8, 2));
				taikhoan = new JLabel("Tài khoản");
				taikhoanText = new JTextField();
				matkhau = new JLabel("Mật khẩu");
				matkhauText = new JPasswordField();
				xacthucmatkhau = new JLabel("Xác thực mật khẩu");
				xtmkText = new JPasswordField();
				tenDN = new JLabel("Tên đăng nhập");
				tenDNText = new JTextField();
				sdt = new JLabel("Số điện thoại");
				sdtText = new JTextField();
				diachi = new JLabel("Địa chỉ");
				diachiText = new JTextField();
				hideOrShowLabel = new JLabel("Ẩn/Hiện mật khẩu");
				hideOrShowLabel.setFont(new Font(null, 1, 11));
				hideOrShowBox = new JCheckBox();

				hideOrShowBox.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if (hideOrShowBox.isSelected()) {
							String password = new String(matkhauText.getPassword());
							String passwordXT = new String(xtmkText.getPassword());
							JTextField tempTextField = new JTextField(password);
							JTextField tempXTTextField = new JTextField(passwordXT);
							remove(matkhauText);
							remove(xtmkText);
							add(tempTextField, 3);
							add(tempXTTextField, 5);
							checkLenText.checkLen(tempTextField, 8);
							checkLenText.checkLen(tempXTTextField, 8);
							hideOrShowLabel.setText("Hiện mật khẩu");
							hideOrShowLabel.setFont(new Font(null, 1, 11));
						} else {
							JTextField tempTextField = (JTextField) getComponent(3);
							JTextField tempXTTextField = (JTextField) getComponent(5);
							String password = tempTextField.getText();
							String passwordXT = tempXTTextField.getText();
							remove(tempTextField);
							remove(tempXTTextField);
							matkhauText = new JPasswordField(password);
							xtmkText = new JPasswordField(passwordXT);
							add(matkhauText, 3);
							add(xtmkText, 5);
							checkLenText.checkLen(matkhauText, 8);
							checkLenText.checkLen(xtmkText, 8);
							hideOrShowLabel.setText("Ẩn mật khẩu");
							hideOrShowLabel.setFont(new Font(null, 1, 11));
						}
					}

				});
				checkLenText.checkLen(taikhoanText, 15);
				checkLenText.checkLen(matkhauText, 8);
				checkLenText.checkLen(sdtText, 10);
				checkLenText.checkLen(xtmkText, 8);
				checkLenText.checkLen(diachiText, 50);
				add(taikhoan);
				add(taikhoanText);
				add(matkhau);
				add(matkhauText);
				add(xacthucmatkhau);
				add(xtmkText);
				add(hideOrShowLabel);
				add(hideOrShowBox);
				add(tenDN);
				add(tenDNText);
				add(sdt);
				add(sdtText);
				add(diachi);
				add(diachiText);
			}
		}

		class botPanel extends JPanel {
			JPanel logChild, signChild;
			JButton dangki;

			botPanel() {
				setLayout(new BorderLayout());
				logChild = new JPanel();
				signChild = new JPanel();
				add(signChild, BorderLayout.CENTER);
				add(logChild, BorderLayout.SOUTH);
				logInLabel = new JLabel("Đăng nhập");
				logInLabel.setFont(new Font(null, 2, 12));
				logInLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cardLayout.show(NhaHangPanel.this, "dangnhap");
					}
				});

				dangki = new JButton("Đăng ký");
				dangki.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						char[] pwChar = matkhauText.getPassword();
						String pw = String.valueOf(pwChar);
						TaiKhoan tk = new TaiKhoan(taikhoanText.getText(), pw);
						KhachHang kh = new KhachHang(tk, tenDNText.getText(), sdtText.getText(), diachiText.getText());
						LoginControllerKhachHang controller = new LoginControllerKhachHang(NhaHangPanel.this, kh, tk);
						char[] pwCheck = matkhauText.getPassword();
						char[] pwxtCheck = xtmkText.getPassword();
						String password = new String(pw);
						String confirmPassword = new String(pwxtCheck);
						boolean areEqual = password.equals(confirmPassword);// kiem tra mat khau va xac thuc mat khau

						if (taikhoanText.getText().equals("") || sdtText.getText().equals("")
								|| diachiText.getText().equals("") || pwCheck.length == 0 || pwxtCheck.length == 0
								|| !areEqual) {

							JOptionPane.showConfirmDialog(null, "Vui lòng kiểm tra lại thông tin", "Thông báo",
									JOptionPane.DEFAULT_OPTION);
							taikhoanText.setText("");
							sdtText.setText("");
							diachiText.setText("");
							matkhauText.setText("");
							xtmkText.setText("");
							return;
						} else {
							try {
								int number1 = Integer.parseInt(sdtText.getText());

							} catch (NumberFormatException u) {
								JOptionPane.showConfirmDialog(null,
										"Số điện thoại phải là con số bắt đầu từ 0\nVí dụ:0123456789", "Lỗi",
										JOptionPane.DEFAULT_OPTION);
								return;
							}
							controller.handleSignUp();
						}
						change("dangnhap");
						taikhoanText.setText("");
						sdtText.setText("");
						diachiText.setText("");
						matkhauText.setText("");
						xtmkText.setText("");
					}
				});
				signChild.add(dangki);
				logChild.add(logInLabel);

			}
		}
	}

}
