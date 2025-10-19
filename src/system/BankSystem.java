package system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.*;

/**
 * BankSystem sınıfı, tüm müşteri ve hesap işlemlerini yönetir.
 * Konsol ve GUI üzerinden hesap açma, para yatırma, çekme, bakiye sorgulama
 * ve ay geçirme işlemlerini destekler.
 */
public class BankSystem {
    private int nextAccountNumber = 10000000; // 8 basamaklı başlangıç
    private List<Account> accounts = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // --------------------- Konsol Müşteri Ekleme ---------------------
    public Customer createCustomer() {
        System.out.print("Müşteri ID girin: ");
        String id = scanner.nextLine();

        System.out.print("Müşteri adı girin: ");
        String name = scanner.nextLine();

        System.out.print("Adres girin: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(id, name, address);
        customers.add(customer);
        System.out.println("Yeni müşteri eklendi: " + customer.getName());
        return customer;
    }

    // --------------------- Konsol Hesap Açma ---------------------
    public void createAccount() {
        System.out.println("Hesap tipi seçin:");
        System.out.println("1. BasicAccount (faizsiz)");
        System.out.println("2. SavingsAccount (faizli)");
        System.out.print("Seçiminiz: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Başlangıç bakiyesi girin: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        Customer owner = createCustomer();

   
        String accNumber = String.valueOf(nextAccountNumber++);
        Account account;
        if (choice == 1) {
            account = new BasicAccount(accNumber, owner, balance);
        } else {
            System.out.print("Aylık faiz oranını girin (örnek: 0.02 = %2): ");
            double rate = scanner.nextDouble();
            scanner.nextLine();
            account = new SavingsAccount(accNumber, owner, balance, rate);
        }

        accounts.add(account);
        System.out.println("✅ Hesap oluşturuldu. Hesap numarası: " + accNumber);
    }

    // --------------------- GUI Hesap Açma (Overloadlama Örneği GUI) ---------------------
    public boolean createAccount(String id, String name, String address,
                                 String type, double initialBalance, double interestRate) {
        Customer owner = new Customer(id, name, address);
        customers.add(owner);

        String accNumber = String.valueOf(nextAccountNumber++);
        Account account;

        if (type.equalsIgnoreCase("savings") || type.equalsIgnoreCase("SavingsAccount")) {
            account = new SavingsAccount(accNumber, owner, initialBalance, interestRate);
        } else {
            account = new BasicAccount(accNumber, owner, initialBalance);
        }


        accounts.add(account);
        return true;
    }
    

    // --------------------- Hesap Kapatma (Konsol) ---------------------
    public void closeAccount() {
        System.out.print("Kapatılacak hesap numarasını girin: ");
        String accNumber = scanner.nextLine();
        closeAccountByNumber(accNumber);
    }

    // --------------------- Hesap Kapatma (GUI) ---------------------
    public boolean closeAccountByNumber(String accNumber) {
        Account acc = findAccount(accNumber);
        if (acc != null) {
            acc.closeAccount();
            accounts.remove(acc);
            return true;
        }
        return false;
    }

    // --------------------- Hesap Bulma ---------------------
    public Account findAccount(String accNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNumber)) {
                return acc;
            }
        }
        return null;
    }

    // --------------------- Para Yatırma (Konsol) ---------------------
    public void depositToAccount() {
        System.out.print("Hesap numarası girin: ");
        String accNumber = scanner.nextLine();
        Account acc = findAccount(accNumber);

        if (acc != null) {
            System.out.print("Yatırılacak miktar: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            acc.deposit(amount);
        } else {
            System.out.println("Hesap bulunamadı!");
        }
    }

    // --------------------- Para Yatırma (GUI) ---------------------
    public boolean depositToAccount(String accNumber, double amount) {
        Account acc = findAccount(accNumber);
        if (acc != null) {
            acc.deposit(amount);
            return true;
        }
        return false;
    }

    // --------------------- Para Çekme (Konsol) ---------------------
    public void withdrawFromAccount() {
        System.out.print("Hesap numarası girin: ");
        String accNumber = scanner.nextLine();
        Account acc = findAccount(accNumber);

        if (acc != null) {
            System.out.print("Çekilecek miktar: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            acc.withdraw(amount);
        } else {
            System.out.println("Hesap bulunamadı!");
        }
    }

    // --------------------- Para Çekme (GUI) ---------------------
    public boolean withdrawFromAccount(String accNumber, double amount) {
        Account acc = findAccount(accNumber);
        if (acc != null) {
        	acc.withdraw(amount);
            return true;
        }
        return false;
    }

    // --------------------- Bakiye Görüntüleme (Konsol) ---------------------
    public void showBalance() {
        System.out.print("Hesap numarası girin: ");
        String accNumber = scanner.nextLine();
        Account acc = findAccount(accNumber);

        if (acc != null) {
            acc.showBalance();
        } else {
            System.out.println("Hesap bulunamadı!");
        }
    }

    // --------------------- Bakiye Görüntüleme (GUI) ---------------------
    public double getBalance(String accNumber) {
        Account acc = findAccount(accNumber);
        if (acc != null) {
            return acc.getBalance();
        }
        return -1;
    }

    // --------------------- Ay Geçme Simülasyonu ---------------------
    public void simulateMonthPass() {
        for (Account acc : accounts) {
            if (acc instanceof SavingsAccount) {
                ((SavingsAccount) acc).simulateMonthPass();
            } else if (acc instanceof BasicAccount) {
                ((BasicAccount) acc).simulateMonthPass();
            }
        }
    }


    // --------------------- Tüm Hesapları Listeleme ---------------------
    public void showAllAccounts() {
        System.out.println("----- Mevcut Hesaplar -----");
        for (Account acc : accounts) {
            System.out.println(acc.toString());
        }
    }

    // --------------------- Yardımcı ---------------------
    public List<Account> getAllAccounts() {
        return accounts;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
