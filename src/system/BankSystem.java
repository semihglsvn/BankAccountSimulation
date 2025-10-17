package system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.*;

/**
 * BankSystem sınıfı, tüm müşteri ve hesap işlemlerini yönetir.
 * Hesap açma, para yatırma, çekme, bakiye sorgulama ve ay geçirme işlemleri burada yapılır.
 */
public class BankSystem {
	private int nextAccountNumber = 10000000; // 8 basamaklı başlangıç


    private List<Account> accounts = new ArrayList<>();   // Hesap listesi
    private List<Customer> customers = new ArrayList<>(); // Müşteri listesi
    private Scanner scanner = new Scanner(System.in);

    // --------------------- Yeni Müşteri Ekleme ---------------------
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

    // --------------------- Yeni Hesap Açma ---------------------
    public void createAccount() {
        System.out.println("Hesap tipi seçin:");
        System.out.println("1. BasicAccount (faizsiz)");
        System.out.println("2. SavingsAccount (faizli)");
        System.out.print("Seçiminiz: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // satır sonu temizle

        System.out.print("Başlangıç bakiyesi girin: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        Customer owner = createCustomer();
        
     


        // ------------------- Otomatik hesap numarası -------------------
        String accNumber = String.valueOf(nextAccountNumber);
        nextAccountNumber++; // bir sonraki hesap için artır

        Account account;
        if (choice == 1) {
            account = new BasicAccount(accNumber, owner, balance);
        } else {
            System.out.print("Aylık faiz oranını girin (örnek: 0.02 = %2): ");
            double rate = scanner.nextDouble();
            account = new SavingsAccount(accNumber, owner, balance, rate);
        }

        accounts.add(account);
        System.out.println("✅ Hesap oluşturuldu. Hesap numarası: " + accNumber);
    }

 // --------------------- Hesap Kapatma ---------------------
    public void closeAccount() {
        System.out.print("Kapatılacak hesap numarasını girin: ");
        String accNumber = scanner.nextLine();
        Account acc = findAccount(accNumber);

        if (acc != null) {
            acc.closeAccount();          // Account sınıfındaki metot çağrılıyor
            accounts.remove(acc);        // Listeden sil
            System.out.println("Hesap silindi: " + accNumber);
        }
    }
    
    // --------------------- Hesap Bulma ---------------------
    public Account findAccount(String accNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNumber)) {
                return acc;
            }
        }
        System.out.println(" Hesap bulunamadı!");
        return null;
    }

    // --------------------- Para Yatırma ---------------------
    public void depositToAccount() {
        System.out.print("Hesap numarası girin: ");
        String accNumber = scanner.nextLine();
        Account acc = findAccount(accNumber);

        if (acc != null) {
            System.out.print("Yatırılacak miktar: ");
            double amount = scanner.nextDouble();
            acc.deposit(amount);
        }
    }

    // --------------------- Para Çekme ---------------------
    public void withdrawFromAccount() {
        System.out.print("Hesap numarası girin: ");
        String accNumber = scanner.nextLine();
        Account acc = findAccount(accNumber);

        if (acc != null) {
            System.out.print("Çekilecek miktar: ");
            double amount = scanner.nextDouble();
            acc.withdraw(amount);
        }
    }

    // --------------------- Bakiye Görüntüleme ---------------------
    public void showBalance() {
        System.out.print("Hesap numarası girin: ");
        String accNumber = scanner.nextLine();
        Account acc = findAccount(accNumber);

        if (acc != null) {
            acc.showBalance();
        }
    }

    // --------------------- Ay Geçme Simülasyonu ---------------------
    public void simulateMonthPass() {
        System.out.println("Bir ay geçiyor... Tüm hesaplar güncelleniyor:");
        for (Account acc : accounts) {
            // Her hesap kendi tipine göre işlem yapar (Polymorphism)
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
}
