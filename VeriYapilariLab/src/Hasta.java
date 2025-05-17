public class Hasta implements Comparable<Hasta> {

    static int sayac = 1;

    private int hastaNo;
    private String hastaAdi;
    private int hastaYasi;
    private String cinsiyet;
    private boolean mahkumluk;
    private int engellilikOrani;
    private String kanamaDurumu;
    private double kayitSaati;
    private double muayeneSaati;
    private int muayeneSuresi;
    private int oncelikPuani;

    public Hasta(String hastaAdi, int hastaYasi, String cinsiyet,
                 boolean mahkumluk, int engellilikOrani,
                 String kanamaDurumu, double kayitSaati) {
        this.hastaNo = sayac++;
        this.hastaAdi = hastaAdi;
        this.hastaYasi = hastaYasi;
        this.cinsiyet = cinsiyet;
        this.mahkumluk = mahkumluk;
        this.engellilikOrani = engellilikOrani;
        this.kanamaDurumu = kanamaDurumu;
        this.kayitSaati = kayitSaati;

        hesaplaOncelikPuani();
        hesaplaMuayeneSuresi();
    }

    private void hesaplaOncelikPuani() {
        int yasPuani = 0;
        if (hastaYasi < 5) yasPuani = 20;
        else if (hastaYasi < 45) yasPuani = 0;
        else if (hastaYasi < 65) yasPuani = 15;
        else yasPuani = 25;

        int engelliPuani = engellilikOrani / 4;
        int mahkumPuani = mahkumluk ? 50 : 0;

        int kanamaPuani;
        String kanamaDurumuLower = kanamaDurumu.trim().toLowerCase();
        if (kanamaDurumuLower.contains("agirkanama")) {
            kanamaPuani = 50;
        } else if (kanamaDurumuLower.contains("kanama")) {
            kanamaPuani = 20;
        } else {
            kanamaPuani = 0;
        }

        oncelikPuani = yasPuani + engelliPuani + mahkumPuani + kanamaPuani;
    }

    private void hesaplaMuayeneSuresi() {
        int standartSüre = 10;

        int yasSuresi = hastaYasi > 65 ? 0 : 15;

        int engelliSuresi = engellilikOrani / 5;

        int kanamaSuresi = switch (kanamaDurumu.trim()) {
            case "agirKanama" -> 20;
            case "kanama" -> 10;
            default -> 0;
        };

        muayeneSuresi = standartSüre + yasSuresi + engelliSuresi + kanamaSuresi;
    }

    // Getter'lar
    public int getHastaNo() { return hastaNo; }
    public String getHastaAdi() { return hastaAdi; }
    public int getHastaYasi() { return hastaYasi; }
    public String getCinsiyet() { return cinsiyet; }
    public boolean isMahkumluk() { return mahkumluk; }
    public int getEngellilikOrani() { return engellilikOrani; }
    public String getKanamaDurumu() { return kanamaDurumu; }
    public double getKayitSaati() { return kayitSaati; }
    public double getMuayeneSaati() { return muayeneSaati; }
    public int getMuayeneSuresi() { return muayeneSuresi; }
    public int getOncelikPuani() { return oncelikPuani; }

    public void setMuayeneSaati(double muayeneSaati) {
        this.muayeneSaati = muayeneSaati;
    }

    @Override
    public int compareTo(Hasta h) {
        return Integer.compare(h.oncelikPuani, this.oncelikPuani);
    }

    @Override
    public String toString() {
        return hastaNo + " - " + hastaAdi + " | Öncelik: " + oncelikPuani +
                " | Süre: " + muayeneSuresi + " dk | Giriş: " + muayeneSaati;
    }
}