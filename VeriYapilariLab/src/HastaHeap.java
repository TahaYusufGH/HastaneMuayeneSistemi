public class HastaHeap {
    private Hasta[] heap;
    private int size;

    public HastaHeap(int kapasite) {
        heap = new Hasta[kapasite];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void ekle(Hasta h) {
        if (size == heap.length) {
            genislet();
        }
        heap[size] = h;
        yukariTasi(size);
        size++;
    }

    public Hasta cikar() {
        if (isEmpty()) return null;
        Hasta max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        asagiTasi(0);
        return max;
    }

    public Hasta peek() {
        if (isEmpty()) return null;
        return heap[0];
    }

    private void yukariTasi(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            if (heap[idx].compareTo(heap[parent]) > 0) {
                Hasta tmp = heap[idx];
                heap[idx] = heap[parent];
                heap[parent] = tmp;
                idx = parent;
            } else break;
        }
    }

    private void asagiTasi(int idx) {
        while (2 * idx + 1 < size) {
            int sol = 2 * idx + 1;
            int sag = 2 * idx + 2;
            int buyuk = sol;
            if (sag < size && heap[sag].compareTo(heap[sol]) > 0) {
                buyuk = sag;
            }
            if (heap[buyuk].compareTo(heap[idx]) > 0) {
                Hasta tmp = heap[idx];
                heap[idx] = heap[buyuk];
                heap[buyuk] = tmp;
                idx = buyuk;
            } else break;
        }
    }

    private void genislet() {
        Hasta[] yeni = new Hasta[heap.length * 2];
        for (int i = 0; i < heap.length; i++) {
            yeni[i] = heap[i];
        }
        heap = yeni;
    }

    public int boyut() {
        return size;
    }

    // Kayıt saatine göre uygun hastaları döndür
    public Hasta enOncelikliVeKayitSaatiUygun(double simuleSaat) {
        Hasta secilen = null;
        for (int i = 0; i < size; i++) {
            if (heap[i].getKayitSaati() <= simuleSaat) {
                if (secilen == null || heap[i].getOncelikPuani() > secilen.getOncelikPuani()) {
                    secilen = heap[i];
                }
            }
        }
        return secilen;
    }

    // Heap'ten belirli bir hastayı çıkar
    public void hastaCikar(Hasta h) {
        for (int i = 0; i < size; i++) {
            if (heap[i] == h) {
                heap[i] = heap[size - 1];
                size--;
                yukariTasi(i);
                asagiTasi(i);
                break;
            }
        }
    }

    // Heap'teki tüm hastaları dizi olarak döndür
    public Hasta[] tumHastalar() {
        try {
            System.out.println("tumHastalar: Heap boyutu = " + size);

            if (size == 0) {
                return new Hasta[0];
            }

            Hasta[] arr = new Hasta[size];
            for (int i = 0; i < size; i++) {
                arr[i] = heap[i];
                System.out.println("  Hasta[" + i + "]: " +
                        (heap[i] != null ?
                                heap[i].getHastaAdi() + ", Öncelik: " + heap[i].getOncelikPuani() :
                                "null"));
            }
            return arr;
        } catch (Exception e) {
            System.err.println("tumHastalar hatası: " + e.getMessage());
            e.printStackTrace();
            return new Hasta[0]; // Hata durumunda boş dizi döndür
        }
    }
}