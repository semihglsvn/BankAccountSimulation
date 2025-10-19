package test;

import system.BankSystem;
import java.util.Scanner;

/**
 * Main sınıfı, uygulamanın giriş noktasıdır.
 * Kullanıcı menüsü üzerinden tüm banka işlemleri yapılır.
 */
public class Main {

    public static void main(String[] args) {
        BankSystem bank = new BankSystem();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Basit Banka Hesap Simülasyonu Başladı!");

        while (!exit) {
            System.out.println("\n--- Ana Menü ---");
            System.out.println("1. Hesap aç");
            System.out.println("2. Para yatır");
            System.out.println("3. Para çek");
            System.out.println("4. Bakiye görüntüle");
            System.out.println("5. Bir ay geçir");
            System.out.println("6. Tüm hesapları göster");
            System.out.println("7. Hesap kapat");
            System.out.println("0. Çıkış");
            System.out.print(	"Seçiminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    bank.createAccount();
                    break;
                case 2:
                    bank.depositToAccount();
                    break;
                case 3:
                    bank.withdrawFromAccount();
                    break;
                case 4:
                    bank.showBalance();
                    break;
                case 5:
                    bank.simulateMonthPass();
                    break;
                case 6:
                    bank.showAllAccounts();
                    break;
                case 7:
                    bank.closeAccount();
                    break;

                case 0:
                    exit = true;
                    System.out.println(" Banka uygulamasından çıkılıyor...");
                    break;
                default:
                    System.out.println(" Geçersiz seçim, tekrar deneyin.");
            }
        }

        scanner.close();
    }
}
