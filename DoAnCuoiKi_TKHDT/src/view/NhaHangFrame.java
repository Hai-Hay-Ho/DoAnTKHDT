package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class NhaHangFrame extends JFrame {
    NhaHangPanel panel;
    JMenuItem itemExit;
    JMenuBar menuBar;
    JMenu fileMenu;

    NhaHangFrame() {
        setTitle("Quản lý nhà hàng");
        setSize(400, 350);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new NhaHangPanel();
        add(panel); // Add NhaHangPanel instance to the frame
        menuBar = new JMenuBar();
        createMenu();
        createMenuItem();
        setJMenuBar(menuBar);
        centerFrame(); // Center the frame when it's created
        setVisible(true);
    }


    public void createMenuItem() {
        itemExit = new JMenuItem("Exit");
        itemExit.setFont(new Font(null, 1, 12));
        fileMenu.add(itemExit);
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void createMenu() {
        fileMenu = new JMenu("File");
        fileMenu.setFont(new Font(null, 1, 12));
        menuBar.add(fileMenu);
    }

    public void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
    }

    public static void main(String[] args) {
        new NhaHangFrame();
    }
}
