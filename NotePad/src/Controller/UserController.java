package Controller;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import Model.Singleton.DatabaseConnection;
import Model.User; // User sınıfını import ettik

public class UserController {

    // Veritabanı bağlantısını tutacak değişken
    private Connection connection;

    // Yapıcı metod: Veritabanı bağlantısını başlatır
    public UserController() {
        this.connection = DatabaseConnection.getInstance().getConnection(); // Veritabanı bağlantısını alır
    }

    // Veritabanından kullanıcıyı e-posta ile arayıp getiren metod
    private User getUserByEmail(String email) {
        // Kullanıcıyı e-posta adresine göre arayan SQL sorgusu
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email); // Parametreyi e-posta ile set ediyoruz
            try (ResultSet resultSet = statement.executeQuery()) {
                // Eğer sonuç varsa
                if (resultSet.next()) {
                    // Kullanıcı bilgilerini alıyoruz
                    int userID = resultSet.getInt("userID");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    // User nesnesini döndürüyoruz
                    return new User(email, password, role, userID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hata durumunda stack trace yazdırılır
        }
        return null;  // Kullanıcı bulunamadıysa null döndürülür
    }

    // Kullanıcı kaydı işlemi
    public boolean registerUser(String email, String password, String fullName) {
        // Kullanıcının var olup olmadığını kontrol ediyoruz
        if (getUserByEmail(email) != null) {
            return false; // E-posta zaten kullanımda, kayıt yapılmaz
        }

        // Yeni kullanıcıyı veritabanına eklemek için SQL sorgusu
        String query = "INSERT INTO users (email, password, full_name, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email); // E-posta
            statement.setString(2, password); // Şifre
            statement.setString(3, fullName); // Tam ad
            statement.setString(4, "user"); // Varsayılan rol "user"
            int rowsAffected = statement.executeUpdate(); // Sorgu çalıştırılır
            return rowsAffected > 0; // Eğer en az bir satır etkilendiyse, kayıt başarılıdır
        } catch (SQLException e) {
            e.printStackTrace(); // Hata durumunda stack trace yazdırılır
            return false; // Kayıt başarısız
        }
    }

    // Kullanıcı doğrulama ve rolü alma işlemi
    public String authenticateUser(String email, String password) {
        User user = getUserByEmail(email); // Email ile kullanıcıyı bul
        if (user != null && user.getPassword().equals(password)) {
            return user.getRole(); // Eğer şifre doğruysa, kullanıcı rolünü döndür
        }
        return null; // Geçersiz kullanıcı veya şifre hatalıysa null döndür
    }

    // Kullanıcıya ait userID'yi döndüren metot
    public int getUserID(String email) {
        User user = getUserByEmail(email); // Email ile kullanıcıyı bul
        if (user != null) {
            return user.getUserID(); // Kullanıcının userID'sini döndür
        }
        return -1;  // Hatalı kullanıcı, -1 döndürülür
    }


    // Yeni kullanıcı ekleme işlemi
    public void addUser(String fullName, String email, String password, String role) {
        // Eğer rol boş veya null ise, varsayılan olarak 'user' rolü atanır
        if (role == null || role.isEmpty()) {
            role = "user"; // Varsayılan rol "user"
        }

        // Kullanıcının var olup olmadığını kontrol ediyoruz
        if (getUserByEmail(email) != null) {
            System.out.println("E-posta zaten kullanımda"); // E-posta zaten varsa, ekleme işlemi yapılmaz
            return; // E-posta zaten var, kullanıcı ekleme
        }

        // Yeni kullanıcıyı veritabanına eklemek için SQL sorgusu
        String query = "INSERT INTO users (email, password, full_name, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email); // E-posta
            statement.setString(2, password); // Şifre
            statement.setString(3, fullName); // Tam ad
            statement.setString(4, role); // Seçilen rol (Admin, User vb.)
            statement.executeUpdate(); // Kullanıcı veritabanına eklenir
        } catch (SQLException e) {
            e.printStackTrace(); // Hata durumunda stack trace yazdırılır
        }
    }
}
