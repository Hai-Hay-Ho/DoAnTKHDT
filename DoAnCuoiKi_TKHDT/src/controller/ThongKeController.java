package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ThongKeController {
    private String filePath;

    public ThongKeController(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, Object> getStatistics(String criteria, String selectedValue) {
        Map<String, Object> statistics = new HashMap<>();
        int totalCustomers = 0;
        double totalRevenue = 0.0;
        Map<String, Integer> menuCountMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length < 4) {
                    continue; 
                }
                String[] items = data[1].trim().split(", ");
                String date = data[3].trim();

                if (matchesCriteria(criteria, selectedValue, date)) {
                    totalCustomers++;
                    totalRevenue += Double.parseDouble(data[2].trim());

                    for (String item : items) {
                        String[] itemInfo = item.split(" - ");
                        if (itemInfo.length < 2) {
                            continue; // Skip processing this item
                        }
                        String menuItem = itemInfo[0].trim();
                        int quantity = Integer.parseInt(itemInfo[1].trim());
                        menuCountMap.put(menuItem, menuCountMap.getOrDefault(menuItem, 0) + quantity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        statistics.put("totalCustomers", totalCustomers);
        statistics.put("totalRevenue", totalRevenue);
        statistics.put("menuCountMap", menuCountMap);

        return statistics;
    }

    private boolean matchesCriteria(String criteria, String selectedValue, String date) {
        String[] dateParts = date.split("/");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];

        switch (criteria) {
            case "Ngày":
                return selectedValue.equals(day + "/" + month + "/" + year);
            case "Tháng":
                return selectedValue.equals(month + "/" + year);
            case "Năm":
                return selectedValue.equals(year);
            default:
                return false;
        }
    }

    public String formatCurrency(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormat.format(amount);
    }
}
