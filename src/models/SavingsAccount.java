package models;

/**
 * SavingsAccount sınıfı, faiz kazancı sağlayan bir banka hesabıdır.
 * Account sınıfından türemiştir (Kalıtım).
 * Faiz hesaplaması yaparak ay geçişinde bakiyeyi artırır (Polymorphism).
 */
public class SavingsAccount extends Account {

    private double interestRate;  // Faiz oranı (örnek: 0.02 → %2 aylık faiz)

    // --------------------- Kurucu Metot ---------------------
    public SavingsAccount(String accountNumber, Customer owner, double balance, double interestRate) {
        super(accountNumber, owner, balance);
        this.interestRate = interestRate;
    }

    // --------------------- Get / Set ---------------------
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            // Negatif faiz oranı geçersiz
            return;
        }
        this.interestRate = interestRate;
    }

    // --------------------- Arayüz (IBankOperations) metotları ---------------------
    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            return;
        }
        setBalance(getBalance() + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0 || amount > getBalance()) {
            return;
        }
        setBalance(getBalance() - amount);
    }

    @Override
    public void showBalance() {
        // GUI tarafında kullanılmayacak, ama konsolda test edilebilir.
        System.out.println("Bakiye: " + getBalance() + "₺ (Faiz oranı: %" + (interestRate * 100) + ")");
    }

    // --------------------- Ek Metotlar ---------------------

    /**
     * Aylık faiz uygular ve eklenen faiz miktarını döner.
     */
    public double applyInterest() {
        double interest = getBalance() * interestRate;
        setBalance(getBalance() + interest);
        return interest;
    }

    /**
     * Ay geçmesi durumunda otomatik faiz uygulaması yapılır.
     * Polymorphism örneği: BasicAccount'ta bu metod hiçbir şey yapmazken,
     * SavingsAccount'ta faiz eklenir.
     */
    @Override
    public void simulateMonthPass() {
    	
        applyInterest();
    }

    @Override
    public String toString() {
        return String.format("SavingsAccount | %s | Faiz Oranı: %.2f%%", 
                              super.toString(), interestRate * 100);
    }
}
