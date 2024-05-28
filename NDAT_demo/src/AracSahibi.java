import java.sql.*;

public class AracSahibi extends UserController{
    private String plaka;
    private int atp_Id;

    
    public AracSahibi(int comuId, String ad, String soyad, String sifre, String kullaniciTipi, String plaka, int atp_id) {
        super(comuId, ad, soyad, sifre, "arac_sahibi", plaka, atp_id);
        this.plaka = plaka;
        this.atp_Id = atp_Id;
    }
    public String getPlaka() {
        return plaka;
    }
    public int getAtp_Id() {
        return atp_Id;
    }

    public void otoparkDurumuGoruntule() {
        otopark.otoparkDurumuGoruntule();
    }

    public void parkGirisiYap(int yerId) {
        otopark.parkGirisiYap(this.getComuId(), yerId);
    }

    public void parkCikisiYap(int yerId, Timestamp cikisSaati, String guzergah) {
        otopark.parkCikisiYap(this.getComuId(), yerId, cikisSaati, guzergah);
    }


    public static boolean otoparkDoluMu(int yerId) {
        String sql = String.format(
            "SELECT dolu FROM otopark WHERE yer_id = %d", yerId
        );
    
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            if (rs.next()) {
                boolean dolu = rs.getBoolean("dolu");
                return dolu;
            }
    
        } catch (SQLException e) {
            System.err.println("Otopark doluluğu kontrol edilirken bir hata oluştu: " + e.getMessage());
        }
    
        // Varsayılan olarak otopark dolu kabul edilir
        return true;
    }


//    public boolean parkYerindekiAracinSahibiMi(int yerId) {
//        try (Connection conn = DatabaseConnection.getConnection();
 //            Statement stmt = conn.createStatement()) {
//    
//            // Park yeri tablosundan yerin sahibinin comuid'sini sorgula
//            String sql = String.format("SELECT comuid FROM otopark WHERE yer_id = %d", yerId);
//            ResultSet rs = stmt.executeQuery(sql);
    
//            if (rs.next()) {
//                int sahibinComuId = rs.getInt("comuid");
//                UserController userController = new UserController();
//                return sahibinComuId == userController.getComuId(); // Eğer yerin sahibi kullanıcının ID'sine eşitse true döndür
//            } else {
//                System.out.println("Hata: Belirtilen park yeri bulunamadı.");
//                return false;
//            }
//        } catch (SQLException e) {
//            System.err.println("Veritabanı hatası: " + e.getMessage());
//            return false;
       // }
    //}
}
