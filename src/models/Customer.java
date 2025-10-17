package models;

/**
 * Customer sınıfı, banka müşterilerinin bilgilerini tutar.
 * Kapsülleme (Encapsulation) örneğidir.
 */
public class Customer {
    private String customerId;  // Müşteri ID
    private String name;        // Müşteri adı
    private String address;     // Adres bilgisi

    // --------------------- Kurucu ---------------------
    public Customer(String customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    // --------------------- Getter / Setter ---------------------
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // --------------------- Temel Bilgi Yazdırma ---------------------
    @Override
    public String toString() {
        return "Müşteri ID: " + customerId + ", İsim: " + name + ", Adres: " + address;
    }
}
