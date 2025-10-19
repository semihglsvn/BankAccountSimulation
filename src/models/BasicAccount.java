package models;

public class BasicAccount extends Account {

    public BasicAccount(String accountNumber, Customer owner, double balance) {
        super(accountNumber, owner, balance);
    }

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
        System.out.println("Güncel bakiye: " + getBalance() + "₺");
    }

    // Ay geçmesi durumunda hiçbir değişiklik olmaz
    public void simulateMonthPass() {
    	
    }
    @Override
    public String toString() {
        return "BasicAccount | " + super.toString();
    }   
    
    
}
