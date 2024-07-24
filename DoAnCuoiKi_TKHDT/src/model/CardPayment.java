package model;

public class CardPayment  implements PaymentStrategy {

    @Override
    public double calculateTotal(double amount) {
        return amount ;
    }
}

