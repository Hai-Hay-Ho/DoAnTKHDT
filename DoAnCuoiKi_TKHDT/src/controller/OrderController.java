package controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    private DefaultListModel<String> accountListModel;
    private DefaultTableModel orderTableModel;
    private JList<String> accountList;
    private Map<String, Object[][]> ordersData;
    private Map<String, String> totalAmountData; 
    private JLabel totalAmountLabel;

    public OrderController(DefaultListModel<String> accountListModel, JList<String> accountList, DefaultTableModel orderTableModel, JLabel totalAmountLabel) {
        this.accountListModel = accountListModel;
        this.accountList = accountList;
        this.orderTableModel = orderTableModel;
        this.ordersData = new HashMap<>();
        this.totalAmountData = new HashMap<>(); 
        this.totalAmountLabel = totalAmountLabel;
    }

    public void addListeners(JButton confirmButton) {
    	
    	// Hiá»ƒn thá»‹ mÃ³n Äƒn ngÆ°á»�i click vÃ o tÃ i khoáº£n Ä‘Ã³
        accountList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedAccount = accountList.getSelectedValue();
                    if (selectedAccount != null) {
                        Object[][] selectedOrders = ordersData.get(selectedAccount);

                        orderTableModel.setRowCount(0);
                        if (selectedOrders != null) {
                            for (Object[] row : selectedOrders) {
                                orderTableModel.addRow(new Object[]{row[0], row[1], row[2]});
                            }
                        }
                        
                        // Hiá»ƒn thá»‹ tá»•ng tiá»�n cho tÃ i khoáº£n Ä‘Ã£ chá»�n
                        String totalAmount = totalAmountData.get(selectedAccount);
                        totalAmountLabel.setText("Tổng tiền: " + totalAmount);
                    }
                }
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAccount = accountList.getSelectedValue();
                if (selectedAccount != null) {
                    ordersData.remove(selectedAccount);
                    totalAmountData.remove(selectedAccount); // XÃ³a tá»•ng tiá»�n cá»§a tÃ i khoáº£n Ä‘Ã£ chá»�n
                    updateOrdersFile();
                    accountListModel.removeElement(selectedAccount);
                    orderTableModel.setRowCount(0);
                    totalAmountLabel.setText("Tổng tiền: "); // XÃ³a nhÃ£n tá»•ng tiá»�n khi khÃ´ng cÃ³ tÃ i khoáº£n Ä‘Æ°á»£c chá»�n
                }
            }
        });
    }

    public void loadOrdersData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String[] items = parts[1].split(", ");
                    Object[][] orders = new Object[items.length][3];

                    for (int i = 0; i < items.length; i++) {
                        String[] itemParts = items[i].split(" - ");
                        String item = itemParts[0].trim();
                        int quantity = Integer.parseInt(itemParts[1].trim());
                        String price = itemParts[2].trim();
                        orders[i] = new Object[]{item, quantity, price};
                    }

                    ordersData.put(username, orders);
                    totalAmountData.put(username, parts[2].trim()); // LÆ°u tá»•ng tiá»�n vÃ o map

                    if (!accountListModel.contains(username)) {
                        accountListModel.addElement(username);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateOrdersFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/controller/DonThanhToanOnline.txt"))) {
            for (Map.Entry<String, Object[][]> entry : ordersData.entrySet()) {
                String username = entry.getKey();
                Object[][] orders = entry.getValue();
                StringBuilder ordersBuilder = new StringBuilder();
                String totalAmount = totalAmountData.get(username); 

                for (Object[] order : orders) {
                    String item = (String) order[0];
                    int quantity = (int) order[1];
                    String price = (String) order[2];
                    ordersBuilder.append(item).append(" - ").append(quantity).append(" - ").append(price).append(", ");
                }

         
                if (ordersBuilder.length() > 0) {
                    ordersBuilder.setLength(ordersBuilder.length() - 2); // Remove the last comma and space
                }

                writer.write(username + "|" + ordersBuilder.toString() + "|" + totalAmount + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
