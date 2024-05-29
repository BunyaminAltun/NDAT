import  java.sql.*;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        Connection conn = DatabaseConnection.getConnection();//Database nesnesi yaratıldı
        if (conn != null) {
            System.out.println("Veritabanı bağlantısı başarıyla test edildi.");
        } else {
            System.out.println("Veritabanı bağlantısı testi başarısız oldu.");
        }

        //UserController kullanici2 = new UserController(2, "Ahmet", "Mehmet", "sifre456", "arac_sahibi", "34ABC123", 1234);
        
        //UserController kullanici3 = new UserController(2,"Ahmet", "Mehmet", "sifre456","kullanici");
        // Kullanıcıyı kaydet
        //if (kullanici3.kayitOl()) {
        //   System.out.println("kullanıcı başarıyla kaydedildi.");
        //} else {
        //    System.out.println("kullanıcı kaydedilirken hata oluştu.");
        //}
        

        usertransactions();

    }//Main

    public static void usertransactions() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("1. Kayıt Ol");
        System.out.println("2. Giriş Yap");
        System.out.print("Seçiminiz: ");
        int secim = scanner.nextInt();
        scanner.nextLine(); // Yeni satırı temizle
    
        if (secim == 1) {
            // Kayıt olma işlemi
            System.out.print("COMUID: ");
            int comuId = scanner.nextInt();
            scanner.nextLine(); // Yeni satırı temizle
    
            System.out.print("Ad: ");
            String ad = scanner.nextLine();
    
            System.out.print("Soyad: ");
            String soyad = scanner.nextLine();
    
            System.out.print("Şifre: ");
            String sifre = scanner.nextLine();
    
            System.out.print("Kullanıcı Tipi (ogrenci veya arac_sahibi): ");
            String tip = scanner.nextLine();
    
            UserController yeniKullanici;
    
            if (tip.equalsIgnoreCase("arac_sahibi")) {
                System.out.print("Plaka: ");
                String plaka = scanner.nextLine();
    
                System.out.print("Araç Tanıtım Pul ID: ");
                int atp_id = scanner.nextInt();
                scanner.nextLine(); // Yeni satırı temizle
    
                yeniKullanici = new UserController(comuId, ad, soyad, sifre, tip, plaka, atp_id);
            } else {
                yeniKullanici = new UserController(comuId, ad, soyad, sifre, tip);
            }
    
            boolean kayitBasarili = yeniKullanici.kayitOl();
            System.out.println("Kayıt başarılı mı? " + kayitBasarili);
    
        } else if (secim == 2) {
        // Giriş yapma işlemi
        System.out.print("Comu ID: ");
        String comu_id = scanner.nextLine();

        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();

        boolean girisBasarili = UserController.girisYap(comu_id, sifre);

        if (girisBasarili) {
            boolean aracSahibiMi = aracSahibiKontrol(comu_id);

            if (aracSahibiMi) {
                System.out.println("Araç sahibi olarak giriş yaptınız.");
                AracSahibi aracSahibi = getAracSahibi(comu_id);
                aracSahibiIslemleri(aracSahibi, scanner);
            } else {
                System.out.println("Öğrenci olarak giriş yaptınız. Şu anda sadece araç sahipleri için işlemler mevcut.");
            }
        } else {
            System.out.println("Giriş başarısız.");
        }
    } else {
            System.out.println("Geçersiz seçim.");
        }
    
        scanner.close();
    }//usertransactions
    


    private static void aracSahibiIslemleri(AracSahibi aracSahibi, Scanner scanner) {

        System.out.println("Araç sahibi olarak giriş yaptınız.");
    
        aracSahibi.otoparkDurumuGoruntule();
    
        System.out.print("Park yeri seçin (1-5): ");
        int yerId = scanner.nextInt();
        scanner.nextLine(); // Yeni satırı temizle
        //if (aracSahibi.otoparkDoluMu(yerId)) {
        //    if (aracSahibi.parkYerindekiAracinSahibiMi(yerId)) {
        //        System.out.println("Otopaktan Çıkış yapmak istiyor musunuz? (E/H): ");
         //       String cikisIstegi = scanner.nextLine();

        //        if (cikisIstegi.equalsIgnoreCase("E")) {
        //            aracSahibi.parkCikisiYap(yerId);
        //        } else {
        //            System.out.println("Giriş ekranına yönlendiriliyorsunuz...");
        //    }
        //}
       // else {
        //        System.out.println("Hata: Bu park yeri size ait değil. Lütfen başka bir park yeri seçin.");
        //        System.out.println("Giriş ekranına yönlendiriliyorsunuz...");
        //        usertransactions();
        //}
        //}
        //else{
        aracSahibi.parkGirisiYap(yerId);
    
        System.out.print("Çıkış saati (yyyy-mm-dd hh:mm:ss): ");
        String cikisSaatiStr = scanner.nextLine();
        System.out.println("Girilen çıkış saati: " + cikisSaatiStr);
        scanner.nextLine(); // Yeni satırı temizle

        Timestamp cikisSaati = null;       
        try {
            cikisSaati = Timestamp.valueOf(cikisSaatiStr);
            System.out.println("Geçerli çıkış saati: " + cikisSaati);
        } catch (IllegalArgumentException e) {
            System.out.println("Hatalı format! Lütfen 'yyyy-MM-dd HH:mm:ss' formatında bir tarih giriniz.");
        }
        System.out.println("Varış Noktası seçin:");
        System.out.println("1. Kampüs A Girişi");
        System.out.println("2. Kampüs B Girişi");
        System.out.println("3. Kampüs C Girişi");
        int varısnoktasısecim = scanner.nextInt();
        scanner.nextLine(); // Yeni satırı temizle
    
        String varıs_noktası;
        switch (varısnoktasısecim) {
            case 1:
            varıs_noktası = "Kampüs A Girişi";
                break;
            case 2:
            varıs_noktası = "Kampüs B Girişi";
                break;
            case 3:
            varıs_noktası = "Kampüs C Girişi";
                break;
            default:
            varıs_noktası = "Bilinmeyen Güzergah";
        }
        
        yolculuk_planlaması  yolculuk = new yolculuk_planlaması(aracSahibi.getComuId(), new Timestamp(System.currentTimeMillis()), cikisSaati, varıs_noktası);
        boolean yolculukKaydedildi = yolculuk.kaydet();

        if (yolculukKaydedildi) {
            System.out.println("Yolculuk kaydedildi.");

            System.out.print("Parktan çıkış yapmak istiyor musunuz? (E/H): ");
            String cikisIstegi = scanner.nextLine();

            if (cikisIstegi.equalsIgnoreCase("E") && cikisSaati != null) {
                aracSahibi.parkCikisiYap(yerId, cikisSaati, varıs_noktası);
            } else {
                System.out.println("Giriş ekranına yönlendiriliyorsunuz...");
                usertransactions();
            }
        } else {
            System.out.println("Yolculuk kaydedilemedi.");
        }
    //}
    }



    private static boolean aracSahibiKontrol(String comuId) {
        String sql = String.format("SELECT * FROM arac_sahibi WHERE comuId = '%s'", comuId);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            return rs.next(); // Eğer kayıt varsa araç sahibi demektir
    
        } catch (SQLException e) {
            System.err.println("Araç sahibi kontrolü sırasında hata oluştu: " + e.getMessage());
            return false;
        }
    }


    private static AracSahibi getAracSahibi(String comuId) {
        String sql = String.format("SELECT * FROM kullanici JOIN arac_sahibi ON kullanici.comuid = arac_sahibi.comuid WHERE kullanici.comuid = '%s'", comuId);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            if (rs.next()) {
                int comuIdInt = rs.getInt("comuid");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String sifre = rs.getString("sifre");
                String plaka = rs.getString("plaka");
                int atpId = rs.getInt("atp_id");
                return new AracSahibi(comuIdInt, ad, soyad, comuId, sifre, plaka, atpId);
            }
        } catch (SQLException e) {
            System.err.println("Araç sahibi bilgileri alınırken hata oluştu: " + e.getMessage());
        }
    
        return null;
    }

    


}

//root:5432




