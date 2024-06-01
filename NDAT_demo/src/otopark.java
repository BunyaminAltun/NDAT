import java.sql.*;


public class otopark {
    public static void otoparkDurumuGoruntule() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT yer_id, dolu FROM otopark")) {

            System.out.println("Otopark Durumu:");
            while (rs.next()) {
                int yerId = rs.getInt("yer_id");
                boolean dolu = rs.getBoolean("dolu");
                String durum = dolu ? "Dolu" : "Boş";
                System.out.println("Yer ID: " + yerId + " - " + durum);
            }

        } catch (SQLException e) {
            System.err.println("Otopark durumu görüntülenirken hata oluştu: " + e.getMessage());
        }
    }//otoparkDurumuGoruntule


    public static boolean parkGirisiYap(int comuId, int yerId) {
        String updateOtoparkYerSql = String.format(
            "UPDATE otopark SET dolu = TRUE, comuid = %d, giris_saati = '%s' WHERE yer_id = %d AND dolu = FALSE",
            comuId, new Timestamp(System.currentTimeMillis()), yerId
        );

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsUpdated = stmt.executeUpdate(updateOtoparkYerSql);
            if (rowsUpdated == 0) {
                System.err.println("Seçilen park yeri dolu veya geçersiz.");
                return false;
            }

            System.out.println("Park giriş işlemi başarılı.");
            return true;

        } catch (SQLException e) {
            System.err.println("Park giriş işlemi sırasında hata oluştu: " + e.getMessage());
            return false;
        }
    }//parkGirisiYap

    public static boolean parkCikisiYap(int comuId, int yerId, Timestamp cikisSaati, String varıs_noktası) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);

            // Giriş saatini otopark_yerleri tablosundan alın
            ResultSet rs = stmt.executeQuery(
                String.format("SELECT giris_saati FROM otopark WHERE yer_id = %d AND comuid = %d", yerId, comuId)
            );

            if (!rs.next()) {
                throw new SQLException("Giriş saati bulunamadı veya park yeri sizin tarafınızdan dolu değil.");
            }

            Timestamp girisSaati = rs.getTimestamp("giris_saati");

            // Yolculuk kaydını ekleyin
            String insertYolculukSql = String.format(
                "INSERT INTO yolculuk_planlaması (comuid, giris_saati, cikis_saati, varıs_noktası) VALUES (%d, '%s', '%s', '%s')",
                comuId, girisSaati, cikisSaati, varıs_noktası
            );

            stmt.executeUpdate(insertYolculukSql);

            // Otopark yerini güncelleyin
            String updateOtoparkYerSql = String.format(
                "UPDATE otopark SET dolu = FALSE, comuid = NULL, giris_saati = NULL WHERE yer_id = %d AND comuid = %d",
                yerId, comuId
            );

            stmt.executeUpdate(updateOtoparkYerSql);

            conn.commit();
            System.out.println("Park çıkış işlemi ve yolculuk planlaması kaydı başarılı.");
            return true;

        } catch (SQLException e) {
            System.err.println("Park çıkış işlemi sırasında hata oluştu: " + e.getMessage());
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback sırasında hata oluştu: " + rollbackEx.getMessage());
            }
            return false;
        }
    }//parkCikisiYap



}//otopark class
