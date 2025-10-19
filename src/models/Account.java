package models;

import interfaces.IBankOperations;

/**
 * Account sınıfı, tüm hesap türleri için ortak alanları ve metotları içerir.
 * Bu sınıf soyut (abstract) olduğu için doğrudan nesne oluşturulamaz.
 * Kalıtım (Inheritance) ve Kapsülleme (Encapsulation) örneğidir.
 */
public abstract class Account implements IBankOperations {

    // --------------------- Alanlar (Fields) ---------------------
    private String accountNumber;   // Hesap numarası
    private double balance;         // Bakiye
    private Customer owner;         // Hesap sahibi (Customer sınıfı ile ilişki)

    // --------------------- Kurucu Metot (Constructor) ---------------------
    public Account(String accountNumber, Customer owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    // --------------------- Getter / Setter (Kapsülleme) ---------------------
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) { // protected: alt sınıflar erişebilir
        this.balance = balance;
    }

    public Customer getOwner() {
        return owner;
    }

    // --------------------- Ortak İşlevler ---------------------
    public void closeAccount() {
        System.out.println("Hesap kapatıldı: " + accountNumber);
        balance = 0;
    }

    @Override
    public String toString() {
        return "Hesap No: " + accountNumber + 
               ", Sahip: " + owner.getName() +
               ", Bakiye: " + balance + "₺";
    }
	public void simulateMonthPass() {
		
		
	}
   
	/**
	 * Ay geçmesi durumunda otomatik faiz uygulaması yapılır.
	 * Polymorphism örneği: BasicAccount'ta bu metod hiçbir şey yapmazken,
	 * SavingsAccount'ta faiz eklenir.
	 */
    // IBankOperations arayüzündeki metotlar burada tanımlanmaz.
    // Çünkü alt sınıflar (SavingsAccount, CheckingAccount) bu işlemleri kendine göre yapacak.
    // Yani soyutlama (Abstraction) burada devrede.
}
