package ui;

import javax.swing.*;
import java.awt.*;
import system.BankSystem;
import models.Account;

public class BankAppFrame extends JFrame {

    private BankSystem bankSystem;

    public BankAppFrame() {
        bankSystem = new BankSystem();

        setTitle("Simple Bank Account Simulation");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Simple Bank System", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(header, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton btnCreate = new JButton("Hesap Aç");
        JButton btnDeposit = new JButton("Para Yatır");
        JButton btnWithdraw = new JButton("Para Çek");
        JButton btnBalance = new JButton("Bakiye Görüntüle");
        JButton btnSimulate = new JButton("Ay Geçir");
        JButton btnClose = new JButton("Hesap Kapat");
        JButton btnList = new JButton("Tüm Hesapları Görüntüle");

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnDeposit);
        buttonPanel.add(btnWithdraw);
        buttonPanel.add(btnBalance);
        buttonPanel.add(btnSimulate);
        buttonPanel.add(btnClose);
        buttonPanel.add(btnList);
        add(buttonPanel, BorderLayout.CENTER);

        // ---------------- BUTTON ACTIONS ----------------

        // Hesap Aç
        btnCreate.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField addressField = new JTextField();
            String[] accountTypes = {"BasicAccount", "SavingsAccount"};
            JComboBox<String> typeBox = new JComboBox<>(accountTypes);
            JTextField balanceField = new JTextField();
            JTextField rateField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            panel.add(new JLabel("Müşteri ID:"));
            panel.add(idField);
            panel.add(new JLabel("Ad Soyad:"));
            panel.add(nameField);
            panel.add(new JLabel("Adres:"));
            panel.add(addressField);
            panel.add(new JLabel("Hesap Tipi:"));
            panel.add(typeBox);
            panel.add(new JLabel("Başlangıç Bakiyesi:"));
            panel.add(balanceField);
            panel.add(new JLabel("Faiz Oranı (sadece Savings için):"));
            panel.add(rateField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Yeni Hesap Aç",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText();
                    String address = addressField.getText();
                    String type = (String) typeBox.getSelectedItem();
                    double balance = Double.parseDouble(balanceField.getText());
                    double rate = rateField.getText().isEmpty() ? 0.0 : Double.parseDouble(rateField.getText());

                    boolean success = bankSystem.createAccount(id, name, address, type, balance, rate);

                    if (success) {
                        String accNumber = bankSystem.getAllAccounts()
                                .get(bankSystem.getAllAccounts().size() - 1)
                                .getAccountNumber();
                        JOptionPane.showMessageDialog(this,
                                "Hesap başarıyla oluşturuldu!\nHesap Numarası: " + accNumber,
                                "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Hesap oluşturulamadı.",
                                "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Geçersiz veri girdiniz!",
                            "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Para Yatır
        btnDeposit.addActionListener(e -> {
            String accNumber = JOptionPane.showInputDialog(this, "Hesap numarasını girin:");
            if (accNumber == null) return;
            String amtStr = JOptionPane.showInputDialog(this, "Yatırılacak miktar:");
            if (amtStr == null) return;

            try {
                double amount = Double.parseDouble(amtStr);
                boolean ok = bankSystem.depositToAccount(accNumber, amount);
                JOptionPane.showMessageDialog(this,
                        ok ? "Para yatırıldı." : "Hesap bulunamadı.",
                        ok ? "Başarılı" : "Hata",
                        ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz miktar!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Para Çek
        btnWithdraw.addActionListener(e -> {
            String accNumber = JOptionPane.showInputDialog(this, "Hesap numarasını girin:");
            if (accNumber == null) return;
            String amtStr = JOptionPane.showInputDialog(this, "Çekilecek miktar:");
            if (amtStr == null) return;

            try {
                double amount = Double.parseDouble(amtStr);
                boolean ok = bankSystem.withdrawFromAccount(accNumber, amount);
                JOptionPane.showMessageDialog(this,
                        ok ? "Para çekildi." : "Hesap bulunamadı veya bakiye yetersiz.",
                        ok ? "Başarılı" : "Hata",
                        ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçersiz miktar!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Bakiye Görüntüle
        btnBalance.addActionListener(e -> {
            String accNumber = JOptionPane.showInputDialog(this, "Hesap numarasını girin:");
            if (accNumber == null) return;
            double balance = bankSystem.getBalance(accNumber);
            if (balance >= 0) {
                JOptionPane.showMessageDialog(this,
                        "Hesap Bakiyesi: " + balance + " TL",
                        "Bakiye", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Hesap bulunamadı.",
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

     //Ay Geçir
        btnSimulate.addActionListener(e -> {
            bankSystem.simulateMonthPass();

            // After simulation, show updated balances
            java.util.List<Account> list = bankSystem.getAllAccounts();

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "📭 Hesap bulunamadı.");
                return;
            }

            StringBuilder sb = new StringBuilder("🕒 Bir ay geçti! Güncel hesap bakiyeleri:\n\n");
            for (Account acc : list) {
                sb.append(acc.getAccountNumber())
                  .append(" - ")
                  .append(acc.getOwner().getName())
                  .append(" | Yeni Bakiye: ")
                  .append(String.format("%.2f", acc.getBalance()))
                  .append(" TL\n");
            }

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 250));

            JOptionPane.showMessageDialog(this, scrollPane, "📈 Güncellenmiş Hesaplar", JOptionPane.INFORMATION_MESSAGE);
        });


        //Hesap Kapat
        btnClose.addActionListener(e -> {
            String accNumber = JOptionPane.showInputDialog(this, "Kapatılacak hesap numarasını girin:");
            if (accNumber == null) return;
            boolean ok = bankSystem.closeAccountByNumber(accNumber);
            JOptionPane.showMessageDialog(this,
                    ok ? "Hesap kapatıldı." : "Hesap bulunamadı.",
                    ok ? "Tamamlandı" : "Hata",
                    ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        });

        //Tüm Hesapları Görüntüle
        btnList.addActionListener(e -> {
            java.util.List<Account> list = bankSystem.getAllAccounts();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Henüz hesap bulunmuyor.");
                return;
            }

            StringBuilder sb = new StringBuilder("----- Mevcut Hesaplar -----\n\n");
            for (Account acc : list) {
                sb.append(acc.toString()).append("\n");
            }

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Hesap Listesi", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankAppFrame().setVisible(true));
    }
}
