import java.sql.*;

public class yolculuk {
    private int comuId;
    private Timestamp girisSaati;
    private Timestamp cikisSaati;
    private String varıs_noktası;

    public yolculuk(int comuId, Timestamp girisSaati, Timestamp cikisSaati, String varıs_noktası) {
        this.comuId = comuId;
        this.girisSaati = girisSaati;
        this.cikisSaati = cikisSaati;
        this.varıs_noktası = varıs_noktası;
    }


    public boolean kaydet() {
        String insertYolculukSql = String.format(
            "INSERT INTO yolculuk (comuid, giris_saati, cikis_saati, varıs_noktası) VALUES (%d, '%s', '%s', '%s')",
            this.comuId, this.girisSaati, this.cikisSaati, this.varıs_noktası
        );

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(insertYolculukSql);
            System.out.println("Yolculuk kaydı başarılı.");
            return true;

        } catch (SQLException e) {
            System.err.println("Yolculuk kaydı sırasında hata oluştu: " + e.getMessage());
            return false;
        }
    }
}
