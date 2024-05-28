import java.sql.*;

public class UserController {

    private int comuId;

    public int getComuId() {
        return this.comuId;
    }

    private String ad;
    private String soyad;
    private String sifre;
    private String kullaniciTipi;
    private String plaka;
    private int atp_id;// araç tanıtım pulu id'si
    
    //UserController class Constructure method - For car owners
    public UserController(int comuId, String ad, String soyad, String sifre, String kullaniciTipi, String plaka,
        int atp_id) {
        this.comuId = comuId;
        this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
        this.kullaniciTipi = kullaniciTipi;
        this.plaka = plaka;
        this.atp_id = atp_id;
    }
    //UserController class Constructure method - For non-car users
    public UserController(int comuId, String ad, String soyad, String sifre, String kullaniciTipi) {
        this.comuId = comuId;
        this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
        this.kullaniciTipi = kullaniciTipi;
    }
    
    //user registration method
      // Kullanıcı kayıt olma metodu
      public boolean kayitOl() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Kullanıcı bilgilerini ekleme (her iki tip için de geçerli)
            String kullaniciSql = String.format(
                "INSERT INTO kullanici (COMUID, ad, soyad, sifre) VALUES (%d, '%s', '%s', '%s')",
                this.comuId, this.ad, this.soyad,this.sifre
            );
            stmt.executeUpdate(kullaniciSql);

            // Eğer kullanıcı araç sahibi ise araç bilgilerini ekleme
            if ("arac_sahibi".equalsIgnoreCase(this.kullaniciTipi) && this.plaka != null) {
                String aracSahibiSql = String.format(
                    "INSERT INTO arac_sahibi (COMUID, plaka, atp_id) VALUES (%d, '%s', %d)",
                    this.comuId, this.plaka, this.atp_id
                );
                stmt.executeUpdate(aracSahibiSql);
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Kayıt olma sırasında hata oluştu: " + e.getMessage());
            return false;
        }
    }//Class Kayıtol
     
    // Kullanıcı giriş yapma metodu
    public static boolean girisYap(String comuId, String sifre) {
        String sql = String.format(
            "SELECT * FROM kullanici WHERE comuId = '%s' AND sifre = '%s'",
            comuId, sifre
        );

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
             
            ResultSet rs = stmt.executeQuery(sql);
            
            return rs.next(); // Eğer kayıt varsa true döner
            
        } catch (SQLException e) {
            System.err.println("Giriş yapma sırasında hata oluştu: " + e.getMessage());
            return false;
        }
    }//Girisyap class
    
    
}//Class Usercontroler
