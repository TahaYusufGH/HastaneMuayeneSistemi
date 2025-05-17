# Hasta Muayene Takip Sistemi

Bu proje, bir sağlık kurumundaki hasta muayene sistemini simüle etmektedir. Sistem, hastaların öncelik puanlarına ve kayıt saatlerine göre muayene sırasını belirlemektedir.

## Proje Açıklaması

Bu Java uygulaması, bir hastanenin muayene sistemini simüle etmektedir. Hastalar çeşitli özelliklere (yaş, engellilik durumu, mahkumluk durumu, kanama durumu vb.) göre önceliklendirilir ve bir öncelik kuyruğu (heap) veri yapısı kullanılarak sıraya alınır.

## Özellikler

- Hastaların öncelik puanlarını hesaplama:
  - Yaş faktörü (0-5 yaş ve 65+ yaş üstü hastalara daha yüksek öncelik)
  - Engellilik oranına göre öncelik
  - Mahkumlar için öncelik
  - Kanama durumuna göre öncelik
- Hastaların muayene sürelerini hesaplama
- Öncelik sırasına göre hastaları sıraya alma
- Görsel arayüz ile hasta takibi
- Hasta verilerini dosyadan yükleme
- Simüle edilmiş saat sistemi

## Kullanım

1. Uygulamayı başlatın
2. "Hastaları Yükle" butonuna tıklayarak hasta verilerini yükleyin
3. "Muayeneye Al" butonuna tıklayarak en yüksek öncelikli hastayı muayeneye alın
4. "Sonraki Kayıt Saatine Git" butonu ile simüle saati ilerletin
5. "Temizle" butonu ile sistemi sıfırlayın

## Veri Yapısı

Proje, öncelikli kuyruk işlevselliği için bir Heap (Yığın) veri yapısı kullanmaktadır. `HastaHeap` sınıfı, hastaları öncelik puanlarına göre sıralar ve en yüksek önceliğe sahip hastayı hızlı bir şekilde çıkarabilir.

## Dosya Yapısı

- `Main.java`: Uygulamanın başlangıç noktası
- `GuiEkrani.java`: Grafik kullanıcı arayüzü
- `Hasta.java`: Hasta sınıfı ve öncelik hesaplama mantığı
- `HastaHeap.java`: Öncelik kuyruğu veri yapısı
- `HastaTableModel.java`: Hasta tablosu için veri modeli
- `MuayeneTableModel.java`: Muayene tablosu için veri modeli
- `Hasta.txt`: Örnek hasta verileri

## Sistem Gereksinimleri

- Java 14 veya üzeri
- Swing desteği olan Java çalışma ortamı

## Başlatma

Projeyi başlatmak için:

```
java -jar HastaMuayeneTakip.jar
```

veya kaynak kodundan:

```
javac *.java
java Main
``` 
