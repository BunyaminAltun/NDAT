import  java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/PAYOPARK";
    private static final String USERNAME = "postgres"; // PostgreSQL kullanıcı adı
    private static final String PASSWORD = "root"; // PostgreSQL şifresi

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database bağlantısı başarılı.");
        } catch (SQLException e) {
            System.out.println("Database bağlantısı kurulamadı: " + e.getMessage());
        }
        return connection;
    }
}

