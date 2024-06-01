import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Yolcu extends UserController{

    public Yolcu(int comuId, String ad, String soyad, String sifre, String kullaniciTipi) {
        super(comuId, ad, soyad, sifre, kullaniciTipi);
    }



    // Yolculuk Planlamalarını Listeleme Metodu
    public List<yolculuk_planlaması> yolculukPlanlamalariniListele(String varisNoktasi) {
        List<yolculuk_planlaması> yolculuklar = new ArrayList<>();
        String sql = "SELECT comuid, giris_saati, cikis_saati, varıs_noktası " +
                     "FROM yolculuk_planlaması " +
                     "WHERE varıs_noktası = '" + varisNoktasi + "'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int comuId = rs.getInt("comuid");
                Timestamp girisSaati = rs.getTimestamp("giris_saati");
                Timestamp cikisSaati = rs.getTimestamp("cikis_saati");
                String varisNoktasiDb = rs.getString("varıs_noktası");

                yolculuk_planlaması yolculuk = new yolculuk_planlaması(comuId, girisSaati, cikisSaati, varisNoktasiDb);
                yolculuklar.add(yolculuk);
            }

        } catch (SQLException e) {
            System.err.println("Yolculuk planlamaları listeleme sırasında hata oluştu: " + e.getMessage());
        }

        return yolculuklar;
    }

    public void yolculukPlanlamasinaKatil(int comuId, String yolcuAdi) {
        String sqlSelectYolcuSayisi = "SELECT COUNT(*) AS yolcu_sayisi FROM yolculuk WHERE comuid = " + comuId;
        String sqlSelectYolculuk = "SELECT comuid, giris_saati, cikis_saati, varıs_noktası " +
                                   "FROM yolculuk_planlaması " +
                                   "WHERE comuid = " + comuId;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rsYolcuSayisi = stmt.executeQuery(sqlSelectYolcuSayisi)) {
    
            if (rsYolcuSayisi.next() && rsYolcuSayisi.getInt("yolcu_sayisi") < 3) {
                try (ResultSet rs = stmt.executeQuery(sqlSelectYolculuk)) {
                    if (rs.next()) {
                        Timestamp girisSaati = rs.getTimestamp("giris_saati");
                        Timestamp cikisSaati = rs.getTimestamp("cikis_saati");
                        String varisNoktasi = rs.getString("varıs_noktası");
    
                        // Yolcunun adını kullanıcı tablosundan alma
                        String sqlSelectarac_sahibi_adi = "SELECT ad, soyad FROM kullanici WHERE comuid = " + comuId;
                        String arac_sahibi_adi = "";
                        try (ResultSet rsarac_sahibi_adi = stmt.executeQuery(sqlSelectarac_sahibi_adi)) {
                            if (rsarac_sahibi_adi.next()) {
                                arac_sahibi_adi = rsarac_sahibi_adi.getString("ad");
                            }
                        }
                        
                        // Arac sahibinin adını almadan, yolcu adını kullanarak yeni bir yolculuk kaydı oluştur
                        String sqlInsert = "INSERT INTO yolculuk (comuid, yolcu_adi, arac_sahibi_adi, giris_saati, cikis_saati, varıs_noktası) " +
                                           "VALUES (" + comuId + ", '" + yolcuAdi + "', '" + arac_sahibi_adi + "', '" + girisSaati + "', '" + cikisSaati + "', '" + varisNoktasi + "')";

                        int rowsAffected = stmt.executeUpdate(sqlInsert);
                        if (rowsAffected > 0) {
                            System.out.println("Yolculuk planlamasına başarıyla katıldınız.");
                        } else {
                            System.out.println("Yolculuk planlamasına katılma başarısız.");
                        }
                    } else {
                        System.out.println("Yolculuk planlaması bulunamadı.");
                    }
                }
            } else {
                System.out.println("Yolculuk planlaması zaten dolu.");
            }
    
        } catch (SQLException e) {
            System.err.println("Yolculuk planlamasına katılma sırasında hata oluştu: " + e.getMessage());
        }
    }
}
