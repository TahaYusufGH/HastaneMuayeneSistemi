import javax.swing.table.DefaultTableModel;

/**
 * Muayene edilen hastaları tutan tablo modeli.
 * Hücreler düzenlenemez.
 */
public class MuayeneTableModel extends DefaultTableModel {

    public MuayeneTableModel() {
        super(new Object[][]{}, new String[]{"Ad", "Öncelik", "Giriş Saati", "Süre (dk)", "Bitiş Saati"});
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void hastaEkle(Hasta h) {
        addRow(new Object[]{
                h.getHastaAdi(),
                h.getOncelikPuani(),
                String.format("%.2f", h.getMuayeneSaati()),
                h.getMuayeneSuresi(),
                String.format("%.2f", h.getMuayeneSaati() + h.getMuayeneSuresi() / 60.0)
        });
    }
}
