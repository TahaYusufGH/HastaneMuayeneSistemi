import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.*;

public class GuiEkrani extends JFrame {

    private JTable bekleyenTable, muayeneTable;
    private DefaultTableModel bekleyenModel, muayeneModel;
    private HastaHeap hastaHeap = new HastaHeap(100);
    private double simuleSaat = 9.00;
    private JLabel saatLabel;
    private static final double BASLANGIC_SAATI = 9.00;
    private static final double BITIS_SAATI = 17.00;

    public GuiEkrani() {
        setTitle("Muayene Sıra Sistemi");
        setSize(1200, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Üst panel
        JPanel ustPanel = new JPanel(new BorderLayout());
        ustPanel.setBackground(Color.WHITE);

        JLabel lblBaslik = new JLabel("HASTA MUAYENE TAKİP PANELİ", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBaslik.setForeground(new Color(0, 102, 204));
        lblBaslik.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        ustPanel.add(lblBaslik, BorderLayout.CENTER);

        saatLabel = new JLabel("Saat: " + formatSaat(simuleSaat));
        saatLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        saatLabel.setForeground(new Color(51, 51, 51));
        saatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        saatLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ustPanel.add(saatLabel, BorderLayout.EAST);

        add(ustPanel, BorderLayout.NORTH);

        // Bekleyenler tablosu
        String[] bekleyenKolonlar = {"Ad", "Yaş", "Cinsiyet", "Mahkum", "Engelli", "Kanama", "Öncelik", "Kayıt Saati"};
        bekleyenModel = new DefaultTableModel(bekleyenKolonlar, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        bekleyenTable = new JTable(bekleyenModel);
        stilUygula(bekleyenTable, new Color(255, 255, 204));
        JScrollPane scrollBekleyen = new JScrollPane(bekleyenTable);
        scrollBekleyen.setBorder(BorderFactory.createTitledBorder("Bekleyen Hastalar"));

        // Muayene tablosu
        String[] muayeneKolonlar = {"Ad", "Öncelik", "Giriş Saati", "Süre (dk)", "Bitiş Saati"};
        muayeneModel = new DefaultTableModel(muayeneKolonlar, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        muayeneTable = new JTable(muayeneModel);
        stilUygula(muayeneTable, new Color(204, 255, 204));
        JScrollPane scrollMuayene = new JScrollPane(muayeneTable);
        scrollMuayene.setBorder(BorderFactory.createTitledBorder("Muayene Edilen Hastalar"));

        JPanel tabloPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        tabloPanel.setBackground(Color.WHITE);
        tabloPanel.add(scrollBekleyen);
        tabloPanel.add(scrollMuayene);
        add(tabloPanel, BorderLayout.CENTER);

        // Butonlar
        JButton btnYukle = new JButton("Hastaları Yükle");
        JButton btnMuayene = new JButton("Muayeneye Al");
        JButton btnTemizle = new JButton("Temizle");
        JButton btnSaatIlerle = new JButton("Sonraki Kayıt Saatine Git");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        btnYukle.setFont(btnFont);
        btnMuayene.setFont(btnFont);
        btnTemizle.setFont(btnFont);
        btnSaatIlerle.setFont(btnFont);

        btnYukle.setBackground(new Color(102, 178, 255));
        btnMuayene.setBackground(new Color(0, 153, 76));
        btnTemizle.setBackground(new Color(255, 77, 77));
        btnSaatIlerle.setBackground(new Color(255, 204, 0));

        btnYukle.setForeground(Color.WHITE);
        btnMuayene.setForeground(Color.WHITE);
        btnTemizle.setForeground(Color.WHITE);
        btnSaatIlerle.setForeground(Color.BLACK);

        JPanel butonPanel = new JPanel(new FlowLayout());
        butonPanel.setBackground(Color.WHITE);
        butonPanel.add(btnYukle);
        butonPanel.add(btnMuayene);
        butonPanel.add(btnSaatIlerle);
        butonPanel.add(btnTemizle);
        add(butonPanel, BorderLayout.SOUTH);

        // Buton işlemleri
        btnYukle.addActionListener(e -> hastalariYukle("hasta.txt"));
        btnMuayene.addActionListener(e -> muayeneyeAl());
        btnSaatIlerle.addActionListener(e -> ileriSaatAta());
        btnTemizle.addActionListener(e -> {
            hastaHeap = new HastaHeap(100);
            simuleSaat = BASLANGIC_SAATI;
            Hasta.sayac = 1;
            bekleyenModel.setRowCount(0);
            muayeneModel.setRowCount(0);
            saatLabel.setText("Saat: " + formatSaat(simuleSaat));
        });
    }

    private void stilUygula(JTable table, Color rowColor) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(224, 224, 224));
        table.setGridColor(Color.LIGHT_GRAY);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (!isSelected) c.setBackground(rowColor);
                return c;
            }
        });
    }

    private void hastalariYukle(String dosyaAdi) {
        hastaHeap = new HastaHeap(100);
        bekleyenModel.setRowCount(0);
        muayeneModel.setRowCount(0);
        simuleSaat = BASLANGIC_SAATI;
        Hasta.sayac = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(dosyaAdi))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(",");
                if (p.length < 8) continue;

                String ad = p[1].trim();
                int yas = Integer.parseInt(p[2].trim());
                String cinsiyet = p[3].trim();
                boolean mahkum = Boolean.parseBoolean(p[4].trim());
                int engelli = Integer.parseInt(p[5].trim());
                String kanama = p[6].trim();
                double saat = Double.parseDouble(p[7].trim());

                Hasta h = new Hasta(ad, yas, cinsiyet, mahkum, engelli, kanama, saat);
                hastaHeap.ekle(h);
            }

            guncelleBekleyenTablo();
            saatLabel.setText("Saat: " + formatSaat(simuleSaat));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Dosya okunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void muayeneyeAl() {
        if (simuleSaat >= BITIS_SAATI) {
            JOptionPane.showMessageDialog(this, "Doktorun mesaisi sona erdi. Ertesi güne geçiliyor.");
            simuleSaat = BASLANGIC_SAATI;
            saatLabel.setText("Saat: " + formatSaat(simuleSaat));
            return;
        }

        Hasta secilen = hastaHeap.enOncelikliVeKayitSaatiUygun(simuleSaat);
        if (secilen == null) {
            return;
        }

        hastaHeap.hastaCikar(secilen);

        secilen.setMuayeneSaati(simuleSaat);
        double bitis = sonrakiSaat(simuleSaat, secilen.getMuayeneSuresi());
        simuleSaat = bitis;

        guncelleBekleyenTablo();

        muayeneModel.addRow(new Object[]{
                secilen.getHastaAdi(),
                secilen.getOncelikPuani(),
                formatSaat(secilen.getMuayeneSaati()),
                secilen.getMuayeneSuresi(),
                formatSaat(bitis)
        });

        saatLabel.setText("Saat: " + formatSaat(simuleSaat));
    }

    private void ileriSaatAta() {
        double enErkenSaat = Double.MAX_VALUE;
        Hasta[] hastalar = hastaHeap.tumHastalar();
        for (Hasta h : hastalar) {
            if (h.getKayitSaati() > simuleSaat) {
                enErkenSaat = Math.min(enErkenSaat, h.getKayitSaati());
            }
        }

        if (enErkenSaat != Double.MAX_VALUE) {
            simuleSaat = enErkenSaat;
            saatLabel.setText("Saat: " + formatSaat(simuleSaat));
        } else {
            JOptionPane.showMessageDialog(this, "Daha ileri kayıt saati olan hasta kalmadı.");
        }
    }

    private double sonrakiSaat(double baslangic, int dakika) {
        int saat = (int) baslangic;
        int dk = (int) ((baslangic - saat) * 100) + dakika;
        saat += dk / 60;
        dk %= 60;
        return saat + dk / 100.0;
    }

    private String formatSaat(double saat) {
        int saatInt = (int) saat;
        int dakika = (int) Math.round((saat - saatInt) * 100);
        if (dakika >= 60) {
            saatInt += dakika / 60;
            dakika = dakika % 60;
        }
        saatInt = saatInt % 24;
        return String.format("%02d:%02d", saatInt, dakika);
    }

    // HAZIR KÜTÜPHANE KULLANMADAN KAYIT SAATİNE GÖRE SIRALAMA
    private void guncelleBekleyenTablo() {
        bekleyenModel.setRowCount(0);
        Hasta[] hastalar = hastaHeap.tumHastalar();

        // Seçimli sıralama algoritması ile kayıt saatine göre küçükten büyüğe sırala
        for (int i = 0; i < hastalar.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < hastalar.length; j++) {
                if (hastalar[j].getKayitSaati() < hastalar[minIdx].getKayitSaati()) {
                    minIdx = j;
                }
            }
            // Swap
            Hasta temp = hastalar[i];
            hastalar[i] = hastalar[minIdx];
            hastalar[minIdx] = temp;
        }

        for (Hasta h : hastalar) {
            bekleyenModel.addRow(new Object[]{
                    h.getHastaAdi(), h.getHastaYasi(),
                    h.getCinsiyet().equals("E") ? "Erkek" : "Kadın",
                    h.isMahkumluk() ? "Evet" : "Hayır",
                    h.getEngellilikOrani() + "%",
                    h.getKanamaDurumu(),
                    h.getOncelikPuani(),
                    formatSaat(h.getKayitSaati())
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiEkrani().setVisible(true));
    }
}