package interfaces;


//BANKA HESAPLARINDA YAPILABİLECEK TEMEL İŞLEMLERİ TANIMLAYAN ARAYÜZ.
// SOYUTLAMA (ABSTRACTİON) ÖRNEĞİDİR.

public interface IBankOperations {

    // Para yatırma işlemi
    void deposit(double amount);

    // Para çekme işlemi
    void withdraw(double amount);

    // Güncel bakiye görüntüleme işlemi
    void showBalance();
}
