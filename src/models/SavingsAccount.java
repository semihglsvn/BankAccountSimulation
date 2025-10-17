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
            System.out.println("Faiz oranı negatif olamaz!");
            return;
        }
        this.interestRate = interestRate;
    }

    // --------------------- Arayüz (IBankOperations) metotları ---------------------
    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Yatırılacak tutar 0'dan büyük olmalıdır!");
            return;
        }
        setBalance(getBalance() + amount);
        System.out.println(amount + "₺ yatırıldı. Yeni bakiye: " + getBalance() + "₺");
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Çekilecek tutar 0'dan büyük olmalıdır!");
            return;
        }
        if (amount > getBalance()) {
            System.out.println("Yetersiz bakiye!");
            return;
        }
        setBalance(getBalance() - amount);
        System.out.println(amount + "₺ çekildi. Kalan bakiye: " + getBalance() + "₺");
    }

    @Override
    public void showBalance() {
        System.out.println("Güncel bakiye: " + getBalance() + "₺ (Faiz oranı: %" + (interestRate * 100) + ")");
    }

    // --------------------- Ek Metotlar ---------------------

    /**
     * Aylık faiz uygular.
     * Bakiye * faizOranı kadar para ekler.
     */
    public void applyInterest() {
        double interest = getBalance() * interestRate;
        setBalance(getBalance() + interest);
        System.out.println("Faiz uygulandı: +" + interest + "₺ → Yeni bakiye: " + getBalance() + "₺");
    }

    /**
     * Ay geçmesi durumunda otomatik faiz uygulaması yapılır.
     * Polymorphism örneği: BasicAccount'ta bu metod hiçbir şey yapmazken,
     * SavingsAccount'ta faiz eklenir.
     */
    public void simulateMonthPass() {
        System.out.println("Bir ay geçti → Faiz hesaplanıyor...");
        applyInterest();
    }
}
