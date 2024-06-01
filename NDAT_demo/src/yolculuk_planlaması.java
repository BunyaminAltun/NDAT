import java.sql.*;

public class yolculuk_planlaması {
    private int comuId;
    private Timestamp girisSaati;
    private Timestamp cikisSaati;
    private String varıs_noktası;

    public yolculuk_planlaması(int comuId, Timestamp girisSaati, Timestamp cikisSaati, String varıs_noktası) {
        this.comuId = comuId;
        this.girisSaati = girisSaati;
        this.cikisSaati = cikisSaati;
        this.varıs_noktası = varıs_noktası;
    }


    public boolean kaydet() {
        String insertYolculukSql = String.format(
            "INSERT INTO yolculuk_planlaması (comuid, giris_saati, cikis_saati, varıs_noktası) VALUES (%d, '%s', '%s', '%s')",
            this.comuId, this.girisSaati, this.cikisSaati, this.varıs_noktası
        );

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(insertYolculukSql);
            System.out.println("yolculuk planlaması kaydı başarılı.");
            return true;

        } catch (SQLException e) {
            System.err.println("yolculuk planlaması kaydı sırasında hata oluştu: " + e.getMessage());
            return false;
        }
    }


    public int getComuId() {
        return comuId;
    }


    public void setComuId(int comuId) {
        this.comuId = comuId;
    }


    public Timestamp getGirisSaati() {
        return girisSaati;
    }


    public void setGirisSaati(Timestamp girisSaati) {
        this.girisSaati = girisSaati;
    }


    public Timestamp getCikisSaati() {
        return cikisSaati;
    }


    public void setCikisSaati(Timestamp cikisSaati) {
        this.cikisSaati = cikisSaati;
    }


    public String getVarıs_noktası() {
        return varıs_noktası;
    }


    public void setVarıs_noktası(String varıs_noktası) {
        this.varıs_noktası = varıs_noktası;
    }
}
