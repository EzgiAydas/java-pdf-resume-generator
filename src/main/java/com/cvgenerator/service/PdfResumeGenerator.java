package com.cvgenerator.service;

import com.cvgenerator.model.Experience;
import com.cvgenerator.model.ResumeData;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.FileNotFoundException;

public class PdfResumeGenerator {

    private static final DeviceRgb PRIMARY_COLOR = new DeviceRgb(26, 54, 93);
    private static final DeviceRgb TEXT_MUTED = new DeviceRgb(95, 105, 120);

    public void generate(ResumeData resume, String outputPath) {
        try {
            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(26, 26, 26, 26);

            // Türkçe Karakter (UTF-8) Desteği
            PdfFont regularFont = null;
            PdfFont boldFont = null;

            try {
                String fontPath = "C:/Windows/Fonts/arial.ttf";
                String fontBoldPath = "C:/Windows/Fonts/arialbd.ttf";
                if (new File(fontPath).exists()) {
                    regularFont = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
                    boldFont = new File(fontBoldPath).exists()
                            ? PdfFontFactory.createFont(fontBoldPath, PdfEncodings.IDENTITY_H)
                            : regularFont;
                    document.setFont(regularFont);
                }
            } catch (Exception e) {
                System.out.println("Özel font yüklenemedi, varsayılan font devrede.");
            }

            // 2 Sütunlu Ana Tablo (%68 Sol - %32 Sağ)
            Table mainTable = new Table(UnitValue.createPercentArray(new float[]{68, 32}));
            mainTable.setWidth(UnitValue.createPercentValue(100));

            // ==========================================
            // SOL SÜTUN (Özet, İş Deneyimleri, TEKNOFEST Projeleri)
            // ==========================================
            Cell leftCell = new Cell().setBorder(Border.NO_BORDER).setPaddingRight(16);

            // İsim ve Unvan
            Paragraph namePara = new Paragraph(resume.getFullName().toUpperCase())
                    .setFontSize(20)
                    .setFontColor(PRIMARY_COLOR)
                    .setMarginBottom(0);
            if (boldFont != null) namePara.setFont(boldFont);
            leftCell.add(namePara);

            leftCell.add(new Paragraph(resume.getTitle())
                    .setFontSize(9.5f)
                    .setFontColor(TEXT_MUTED)
                    .setItalic()
                    .setMarginBottom(12));

            // PROFESYONEL ÖZET
            addSectionHeader(leftCell, "PROFESYONEL ÖZET", boldFont);
            leftCell.add(new Paragraph(resume.getSummary())
                    .setFontSize(8.2f)
                    .setMultipliedLeading(1.18f)
                    .setMarginBottom(12));

            // İŞ DENEYİMİ (3 Hayali İş Deneyimi)
            addSectionHeader(leftCell, "İŞ DENEYİMİ", boldFont);
            for (Experience exp : resume.getExperiences()) {
                Paragraph rolePara = new Paragraph(exp.getRole())
                        .setFontSize(9f)
                        .setFontColor(ColorConstants.BLACK)
                        .setMarginBottom(1);
                if (boldFont != null) rolePara.setFont(boldFont);
                leftCell.add(rolePara);

                leftCell.add(new Paragraph(exp.getCompany() + " | " + exp.getPeriod())
                        .setFontSize(8f)
                        .setFontColor(PRIMARY_COLOR)
                        .setItalic()
                        .setMarginBottom(2));

                leftCell.add(new Paragraph(exp.getDescription())
                        .setFontSize(7.8f)
                        .setMultipliedLeading(1.15f)
                        .setMarginBottom(7));
            }

            // TEKNOFEST VE TEKNİK PROJELER
            addSectionHeader(leftCell, "PROJELER (TEKNOFEST & AR-GE)", boldFont);

            // 1. Proje: Steeldome Hava Savunma
            Paragraph p1Title = new Paragraph("TEKNOFEST Çelik Kubbe Hava Savunma Sistemleri (T-Defend)")
                    .setFontSize(8.7f).setMarginBottom(1);
            if (boldFont != null) p1Title.setFont(boldFont);
            leftCell.add(p1Title);
            leftCell.add(new Paragraph("• C++ ve Python kullanarak çok katmanlı savunma ekosistemi için yüksek performanslı yazılım mimarisi tasarlandı. Teorik modeller ile gerçek zamanlı uygulamalar arasındaki veri işleme algoritmaları optimize edildi.")
                    .setFontSize(7.8f).setMultipliedLeading(1.15f).setMarginBottom(5));

            // 2. Proje: FPV Drone Takip
            Paragraph p2Title = new Paragraph("TEKNOFEST FPV Drone Takip Yarışması")
                    .setFontSize(8.7f).setMarginBottom(1);
            if (boldFont != null) p2Title.setFont(boldFont);
            leftCell.add(p2Title);
            leftCell.add(new Paragraph("• Yüksek hızlı FPV dronları tespit ve takip etmek için Python ve OpenCV ile gerçek zamanlı nesne takip algoritmaları geliştirildi. Görüntü işleme pipeline'ları optimize edilerek yüksek FPS ve düşük gecikme sağlandı; uçuş kontrolcüsü ile donanım entegrasyonu kuruldu.")
                    .setFontSize(7.8f).setMultipliedLeading(1.15f).setMarginBottom(5));

            // 3. Proje: DynamicTensor
            Paragraph p3Title = new Paragraph("DynamicTensor - Özel Tensör Mimarisi")
                    .setFontSize(8.7f).setMarginBottom(1);
            if (boldFont != null) p3Title.setFont(boldFont);
            leftCell.add(p3Title);
            leftCell.add(new Paragraph("• Soyutlama maliyetini düşürerek C Union ve Enum yapılarıyla FLOAT32, FLOAT16 ve INT8 tiplerini tek bir bellek alanında yöneten çok tipli düşük seviyeli veri yapısı ve matris işlemleri geliştirildi.")
                    .setFontSize(7.8f).setMultipliedLeading(1.15f).setMarginBottom(5));


            // ==========================================
            // SAĞ SÜTUN (Fotoğraf, İletişim, Eğitim, Beceriler, Kulüpler)
            // ==========================================
            Cell rightCell = new Cell().setBorder(Border.NO_BORDER).setPaddingLeft(10);

            // Fotoğraf
            if (resume.getPhotoPath() != null && new File(resume.getPhotoPath()).exists()) {
                Image img = new Image(ImageDataFactory.create(resume.getPhotoPath()));
                img.setWidth(100);
                img.setHeight(120);
                img.setHorizontalAlignment(HorizontalAlignment.CENTER);
                img.setMarginBottom(12);
                rightCell.add(img);
            }

            // İLETİŞİM
            addSectionHeader(rightCell, "İLETİŞİM", boldFont);
            rightCell.add(new Paragraph("Telefon: " + resume.getPhone()).setFontSize(7.8f).setMarginBottom(2));
            rightCell.add(new Paragraph("E-posta: " + resume.getEmail()).setFontSize(7.8f).setMarginBottom(2));
            rightCell.add(new Paragraph("Konum: " + resume.getLocation()).setFontSize(7.8f).setMarginBottom(2));
            rightCell.add(new Paragraph("LinkedIn: linkedin.com/in/ezgiaydas").setFontSize(7.8f).setMarginBottom(2));
            rightCell.add(new Paragraph("GitHub: github.com/EzgiAydas").setFontSize(7.8f).setMarginBottom(10));

            // EĞİTİM
            addSectionHeader(rightCell, "EĞİTİM", boldFont);
            Paragraph eduTitle = new Paragraph("Yazılım Mühendisliği (Lisans)")
                    .setFontSize(8.2f).setMarginBottom(1);
            if (boldFont != null) eduTitle.setFont(boldFont);
            rightCell.add(eduTitle);
            rightCell.add(new Paragraph("Kırklareli Üniversitesi\n2020 - 2024")
                    .setFontSize(7.6f).setFontColor(TEXT_MUTED).setMarginBottom(10));

            // TEKNİK YETKİNLİKLER
            addSectionHeader(rightCell, "YETKİNLİKLER", boldFont);
            rightCell.add(new Paragraph("• C, C++, Python, Java\n• OpenCV, Jetson, ESP32\n• Pandas, Scikit-learn, SQL\n• UART, MAVLink, FreeRTOS\n• Git, Linux Dağıtımları")
                    .setFontSize(7.6f).setMultipliedLeading(1.2f).setMarginBottom(10));

            // SERTİFİKALAR
            addSectionHeader(rightCell, "SERTİFİKALAR", boldFont);
            rightCell.add(new Paragraph("• Veri Yapıları & Algoritmalar (BTK)\n• Veri Bilimi & Makine Öğrenmesi (TRAI)\n• Veri Modelleme (BTK)")
                    .setFontSize(7.6f).setMultipliedLeading(1.2f).setMarginBottom(10));

            // KULÜP FAALİYETLERİ
            addSectionHeader(rightCell, "KULÜP & ETKİNLİK", boldFont);
            rightCell.add(new Paragraph("• TEKNOFEST Kulübü Proje Ekibi\n• Oyun Tasarımı ve Geliştirme Kulübü Sponsorluk Ekibi")
                    .setFontSize(7.6f).setMultipliedLeading(1.2f).setMarginBottom(10));

            // DİLLER
            addSectionHeader(rightCell, "YABANCI DİLLER", boldFont);
            rightCell.add(new Paragraph("• Türkçe (Anadil)\n• İngilizce (B2)\n• Rusça (A2)")
                    .setFontSize(7.6f).setMultipliedLeading(1.2f));

            // Tabloyu dökümana ekle
            mainTable.addCell(leftCell);
            mainTable.addCell(rightCell);
            document.add(mainTable);

            document.close();
            System.out.println("CV başarıyla oluşturuldu: " + outputPath);

        } catch (FileNotFoundException e) {
            System.err.println("Dosya yazma hatası (Açık olan PDF'i kapatıp tekrar deneyin): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addSectionHeader(Cell cell, String title, PdfFont boldFont) {
        Paragraph header = new Paragraph(title)
                .setFontSize(9.5f)
                .setFontColor(PRIMARY_COLOR)
                .setMarginTop(4)
                .setMarginBottom(2);
        if (boldFont != null) header.setFont(boldFont);
        cell.add(header);

        Table divider = new Table(UnitValue.createPercentArray(new float[]{100}));
        divider.setWidth(UnitValue.createPercentValue(100));
        divider.setBorderBottom(new SolidBorder(PRIMARY_COLOR, 1f));
        divider.setMarginBottom(4);
        cell.add(divider);
    }
}