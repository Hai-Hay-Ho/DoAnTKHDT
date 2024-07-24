package model;

public class CashPayment implements PaymentStrategy {
    private static final double CASH_FEE_RATE = 0.05;

    @Override
    public double calculateTotal(double amount) {
        double totalAmount = amount * (1 + CASH_FEE_RATE);
        return totalAmount;
    }
}
