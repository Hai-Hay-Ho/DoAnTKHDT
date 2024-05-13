package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class NhaHangFrame extends JFrame {
	NhaHangPanel panel;
	JMenuItem itemExit,itemRestart;
	JMenuBar menuBar;
	JMenu fileMenu;

	NhaHangFrame() {
		setTitle("Quản lý nhà hàng");
		setSize(600, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new NhaHangPanel();
		getContentPane().add(panel);
		menuBar = new JMenuBar();
		createMenu();
		createMenuItem();
		setJMenuBar(menuBar);
		pack();
		setVisible(true);
	}
	public void createMenuItem() {
		itemExit = new JMenuItem("Exit");
		itemExit.setFont(new Font(null,1,12));
		itemRestart = new JMenuItem("Restart");
		itemRestart.setFont(new Font(null,1,12));
		fileMenu.add(itemExit);
		fileMenu.add(itemRestart);
		itemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		itemRestart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.change(e.getActionCommand());
			}
		});
		itemRestart.setActionCommand("giaodienchinh");
	}
	public void createMenu() {
		fileMenu = new JMenu("File");
		fileMenu.setFont(new Font(null,1,12));
		menuBar.add(fileMenu);
		
	}
	public static void main(String[] args) {
		new NhaHangFrame();
	}
}
