# Java PDF Resume Generator (Özgeçmiş Oluşturucu)

Bu proje, Java programlama dili ve iText 7 kütüphanesi kullanılarak Nesne Yönelimli Programlama (OOP) prensiplerine tam uyumlu şekilde PDF formatında modern, iki sütunlu ve fotoğraflı özgeçmiş üreten bir konsol uygulamasıdır.

---

## 📌 Proje Hakkında ve Amaç

Bu uygulamanın amacı, özgeçmiş verilerini nesne yönelimli mimaride modeller üzerinden yöneterek stil ve sayfa yerleşim kurallarıyla birlikte PDF belgesine dönüştürmektir. 

Ödev yönergesi doğrultusunda:
- Grafiksel kullanıcı arayüzü (GUI) yerine nesne yönelimli servis mimarisi tercih edilmiştir.
- PDF oluşturma mantığı `Main` sınıfından tamamen soyutlanarak bağımsız bir servis sınıfı (`PdfResumeGenerator`) içinde kodlanmıştır.
- Belge içerisinde adayın profil fotoğrafı yer almaktadır.
- Gömülü sistemler ve veri analitiği alanlarına odaklanan **3 adet hayali iş deneyimi** kurgulanmıştır.
- Türkçe karakter desteği UTF-8 (`IDENTITY_H`) kodlama standardı ile entegre edilmiştir.

---

## 🤖 Yapay Zeka (Google Gemini) Destekli Geliştirme Süreci

Bu projenin mimari tasarımı, kütüphane seçimi, hata ayıklama ve mizanpaj optimizasyonu süreçlerinde **Google Gemini** bir eşlikçi yazılım mimarı (AI Pair Programmer) olarak kullanılmıştır. Yapay zeka araçlarından faydalanılan temel alanlar şunlardır:

1. **Mimari Tasarım ve Kütüphane Seçimi:**
   - PDF üretimi için Apache PDFBox ile iText 7 kütüphaneleri kıyaslanmış; iki sütunlu esnek tablo (`Table`, `Cell`) desteği ve modern layout yetenekleri nedeniyle Gemini'ın teknik yönlendirmesiyle **iText 7** tercih edilmiştir.
   - Projenin *Single Responsibility Principle* (Tek Sorumluluk Prensibi) uyarınca `model`, `service` ve `main` katmanlarına ayrılmasında Gemini ile mimari beyin fırtınası yapılmıştır.

2. **Türkçe Karakter (UTF-8 Encoding) Çözümü:**
   - iText 7'nin varsayılan `Helvetica` fontunda Türkçe karakterlerin (`ş, ğ, ı, ö, ü, ç, İ`) basılmaması ve boş çıkması sorunu yaşanmıştır.
   - Gemini aracılığıyla `PdfFontFactory` üzerinden Windows sistem Arial fontunun (`arial.ttf`) `PdfEncodings.IDENTITY_H` parametresiyle dinamik olarak projeye bağlanması sağlanarak Türkçe karakter sorunu çözülmüştür.

3. **Hata Ayıklama (Debugging) & Refactoring:**
   - Maven `pom.xml` dosyasındaki XML hiyerarşi hataları (`syntax error`) ve Java metin bloklarındaki tırnak birleştirme hataları (`unclosed string literal`) Gemini ile analiz edilerek hızlıca düzeltilmiştir.
   - Kodun okunabilirliğini ve OOP prensiplerine uyumunu artırmak adına encapsulation (kapsülleme) kontrolleri yapılmıştır.

4. **İki Sütunlu Mizanpaj & Tipografi:**
   - Sayfa marjinlerinin (26pt) ayarlanması, sol (%68) ve sağ (%32) sütun oranlarının belgenin tek sayfaya ideal şekilde sığacak biçimde dengelenmesinde Gemini'ın sunduğu layout hesaplamalarından yararlanılmıştır.

---

## 🏗️ Mimari ve Kullanılan Bileşenler (Detaylı Nesne Analizi)

Proje, **Single Responsibility Principle** ve **Separation of Concerns** prensiplerine uygun olarak katmanlı mimaride yapılandırılmıştır:

### 1. Veri Modeli Katmanı (Data Model Layer)

* **`Experience` Sınıfı:**
  * **Sorumluluğu:** Adayın geçmiş iş deneyimlerine ait şirket adı, görev/unvan, çalışma periyodu ve iş tanımı detaylarını tutar.
  * **OOP Kararı:** Kapsülleme (Encapsulation) ilkesi gereği tüm alanlar `private` tutulmuş; dış dünyadan erişim kontrollü `public getter` metotlarıyla sağlanmıştır.
* **`ResumeData` Sınıfı:**
  * **Sorumluluğu:** Adayın ad-soyad, unvan, e-posta, telefon, konum, profesyonel özet, fotoğraf dosya yolu ve iş deneyimleri koleksiyonunu barındırır.
  * **OOP Kararı:** İş deneyimlerini `List<Experience>` tipinde saklayarak **Kompozisyon (Composition)** ilişkisi kurar. Bilgilerin atomik ve tutarlı kalmasını sağlar.

### 2. Servis Katmanı (Service Layer)

* **`PdfResumeGenerator` Sınıfı:**
  * **Sorumluluğu:** `ResumeData` nesnesini girdi olarak alıp iText motoru ile fiziksel bir PDF dosyasına dönüştüren çekirdek servis sınıfıdır.
  * **Tasarım Kararı:** PDF'in tüm mizanpaj, renk, font ve çizim kararları bu sınıfta toplanarak `Main` sınıfının sadece veri başlatma ile ilgilenmesi sağlanmıştır.

### 3. Kullanılan Harici iText 7 Nesneleri ve Tercih Nedenleri

* **`PdfWriter`:** Belirtilen dosya yoluna (`Ozgecmis.pdf`) byte akışı (stream) açarak fiziksel PDF dokümanının diske yazılmasını yönetir.
* **`PdfDocument`:** PDF dosyasının düşük seviyeli sayfa geometrisini, doküman kataloğunu ve nesne hiyerarşisini kontrol eder.
* **`Document`:** Sayfa kenar boşluklarını (margins) ve elemanların akışkan yerleşimini koordine eden üst düzey tuvaldir (layout container).
* **`Table` & `Cell`:** Özgeçmişin sol (%68 ana içerik) ve sağ (%32 yan bilgi paneli) olmak üzere asimetrik iki sütun halinde profesyonelce hizalanması için kullanılmıştır.
* **`Image` & `ImageDataFactory`:** Adayın profil fotoğrafını yerel dosya sisteminden okuyarak çözünürlük kaybı olmadan PDF hücre boyutuna ölçekler ve ortalar.
* **`PdfFontFactory` & `PdfEncodings.IDENTITY_H`:** iText'in varsayılan font motorundaki Türkçe karakter uyumsuzluğunu gidermek için Arial yazı tipini UTF-8 haritalaması ile dokümana bağlar.

---

## 🛠️ Kullanılan Teknolojiler

| Bileşen | Detay |
| :--- | :--- |
| **Programlama Dili** | Java 17+ |
| **Bağımlılık Yöneticisi** | Apache Maven |
| **PDF Motoru** | iText 7 Core (7.2.5) |
| **AI Asistanı** | Google Gemini |
| **Karakter Seti** | UTF-8 (Identity-H) |

---

## 📁 Proje Dizin Yapısı

```text
cv-generator/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── cvgenerator/
│                   ├── model/
│                   │   ├── Experience.java       # İş deneyimi veri sınıfı
│                   │   └── ResumeData.java       # Özgeçmiş ana veri modeli
│                   ├── service/
│                   │   └── PdfResumeGenerator.java # PDF üretim servisi
│                   └── Main.java                 # Uygulama başlangıç noktası
│
├── pom.xml                                       # Maven bağımlılık dosyası
├── profile.jpg                                   # CV profil fotoğrafı
