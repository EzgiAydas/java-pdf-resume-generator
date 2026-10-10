package com.cvgenerator;

import com.cvgenerator.model.Experience;
import com.cvgenerator.model.ResumeData;
import com.cvgenerator.service.PdfResumeGenerator;

public class Main {
    public static void main(String[] args) {
        String ozet = "Kırklareli Üniversitesi Yazılım Mühendisliği lisans mezunu "
                + "Düşük seviyeli gömülü sistem mimarileri, gerçek zamanlı sensör veri akışları ve veri analitiği alanlarında teknik çözümler üretmeye odaklanmaktadır. "
                + "Donanım-yazılım entegrasyonu, uç birimlerde veri önişleme ve istatistiksel analiz yöntemlerini birleştiren projeler geliştirmektedir.";

        ResumeData cv = new ResumeData(
                "Ezgi Aydaş",
                "Yazılım Mühendisi | Gömülü Sistemler & Veri Analisti",
                "aydasezgi@gmail.com",
                "+90 552 000 00 00",
                "İstanbul, Türkiye",
                ozet,
                "profile.jpg"
        );

        // 1. Deneyim: Gömülü Sistemler
        String aciklama1 = "Mikrodenetleyici tabanlı (STM32, ESP32) uç birimlerde sensör füzyonu (IMU, Barometre) algoritmaları üzerinde çalıştı. "
                + "UART, I2C ve CAN bus haberleşme protokolleri optimize edilerek telemetri aktarım gecikmesi minimize edildi ve FreeRTOS üzerinde görev zamanlama testleri tamamlandı.";

        cv.addExperience(new Experience(
                "AeroSense Savunma ve Havacılık Teknolojileri",
                "Gömülü Sistemler ve Uç Birim Yazılım Stajyeri",
                "06/2025 - 09/2025",
                aciklama1
        ));

        // 2. Deneyim: Veri Analistliği
        String aciklama2 = "Büyük ölçekli ilişkisel veritabanları üzerinde karmaşık SQL sorguları optimize edilerek haftalık performans göstergeleri ve trend analizleri modellendi. "
                + "Python (Pandas, Seaborn) kullanılarak veri temizleme, kayıp veri analizi ve otomatik KPI raporlama sistemleri geliştirildi.";

        cv.addExperience(new Experience(
                "VeriVizyon Bilişim ve Analitik Çözümleri",
                "Veri Analisti Stajyeri",
                "01/2025 - 04/2025",
                aciklama2
        ));

        // 3. Deneyim: Sensör Verisi Analitiği
        String aciklama3 = "Üretim bandındaki IoT cihazlarından toplanan gerçek zamanlı titreşim ve sıcaklık zaman serisi verileri analiz edildi. "
                + "Kestirimci bakım süreçleri kapsamında aykırı değer tespiti (anomaly detection) modelleri kurularak arıza teşhis mekanizmasının doğruluk oranının artırılmasına katkı sunuldu.";

        cv.addExperience(new Experience(
                "Endüstriyel Veri ve Otomasyon Sistemleri Ltd.",
                "Sensör Veri Analisti ve Yazılım Geliştirici Adayı",
                "07/2024 - 11/2024",
                aciklama3
        ));

        PdfResumeGenerator generator = new PdfResumeGenerator();
        generator.generate(cv, "Ozgecmis.pdf");
    }
}