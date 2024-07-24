package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Lich;
import view.GiaoDienNhanVien;

public class DKLMController {
    private GiaoDienNhanVien view;
    private Lich[][] lichLam;

    public DKLMController(GiaoDienNhanVien view) {
        this.view = view;
        this.lichLam = new Lich[3][6];
    }


    private void saveToFile(boolean[][] states) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/controller/lichLam.txt"))) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 6; col++) {
                    writer.write(states[row][col] ? "1" : "0");
                    if (col < 5) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Lưu thành công!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu dữ liệu: " + ex.getMessage());
        }
    }

	public void inLichLamTuFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/controller/lichLam.txt"))) {
          
            JTable table = new JTable();

  
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Ca");
            model.addColumn("Thứ 2");
            model.addColumn("Thứ 3");
            model.addColumn("Thứ 4");
            model.addColumn("Thứ 5");
            model.addColumn("Thứ 6");
            model.addColumn("Thứ 7");

            String line;
            int ca = 1;

      
            while ((line = reader.readLine()) != null) {
                String[] shifts = line.split(",");

                Object[] rowData = new Object[7];
                rowData[0] = "Ca " + ca;
                for (int i = 0; i < shifts.length; i++) {
                    rowData[i + 1] = shifts[i].equals("1") ? "X" : "-";
                }

                model.addRow(rowData);
                ca++;
            }

            table.setModel(model);

      
            JOptionPane.showMessageDialog(null, new JScrollPane(table), "Lịch làm", JOptionPane.PLAIN_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi đọc dữ liệu: " + ex.getMessage());
        }
    }

 
    public void luuLichLam(boolean[][] states) {
        saveToFile(states);
    }
    
    public void updateLichLam(int row, int col, Lich lich) {
        lichLam[row][col] = lich;
    }

    public Lich getLichLam(int row, int col) {
        return lichLam[row][col];
    }
}

