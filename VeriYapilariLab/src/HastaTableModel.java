import javax.swing.table.AbstractTableModel;

/**
 * Bekleyen hastaları tutan tablo modeli.
 * Hücreler düzenlenemez.
 * Hazır koleksiyon kullanılmaz.
 */
public class HastaTableModel extends AbstractTableModel {

    private String[] kolonlar;
    private Hasta[] hastalar = new Hasta[0];

    public HastaTableModel(String[] kolonlar) {
        this.kolonlar = kolonlar;
    }

    @Override
    public int getRowCount() {
        return hastalar.length;
    }

    @Override
    public int getColumnCount() {
        return kolonlar.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolonlar[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hasta h = hastalar[rowIndex];
        return switch (columnIndex) {
            case 0 -> h.getHastaAdi();
            case 1 -> h.getHastaYasi();
            case 2 -> h.getCinsiyet().equalsIgnoreCase("E") ? "Erkek" : "Kadın";
            case 3 -> h.isMahkumluk() ? "Evet" : "Hayır";
            case 4 -> h.getEngellilikOrani() + "%";
            case 5 -> h.getKanamaDurumu();
            case 6 -> h.getOncelikPuani();
            case 7 -> h.getKayitSaati();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    // Dışarıdan dizi ile güncelleme
    public void setHastaListesi(Hasta[] yeniHastalar) {
        this.hastalar = new Hasta[yeniHastalar.length];
        for (int i = 0; i < yeniHastalar.length; i++) {
            this.hastalar[i] = yeniHastalar[i];
        }
        fireTableDataChanged();
    }

    // Tek tek hasta eklemek için (gerekirse)
    public void hastaEkle(Hasta h) {
        Hasta[] yeni = new Hasta[hastalar.length + 1];
        for (int i = 0; i < hastalar.length; i++) {
            yeni[i] = hastalar[i];
        }
        yeni[hastalar.length] = h;
        hastalar = yeni;
        fireTableDataChanged();
    }

    // Tüm hastaları almak için
    public Hasta[] getHastalar() {
        return hastalar;
    }
}