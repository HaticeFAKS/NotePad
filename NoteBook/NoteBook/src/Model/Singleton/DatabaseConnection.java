package Model.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Singleton desenini uygulamak için instance değişkeni
    private static DatabaseConnection instance;
    // Veritabanı bağlantısı için Connection nesnesi
    private Connection connection;

    // Veritabanı bağlantısı için sabit parametreler (URL, kullanıcı adı ve şifre)
    private static final String URL = "jdbc:mysql://localhost:3306/hamzanotes";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Yapıcı metod, sınıfın dışından erişilemez. Bu sayede yalnızca bir nesne oluşturulabilir.
    private DatabaseConnection() {
        try {
            // MySQL JDBC sürücüsünü yükler
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Bağlantıyı oluşturur ve connection değişkenine atar
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Bağlantı başarılı bir şekilde sağlandı.");
        } catch (ClassNotFoundException | SQLException e) {
            // Bağlantı sırasında bir hata oluşursa, hata yazdırılır ve bir runtime istisnası fırlatılır
            e.printStackTrace();
            throw new RuntimeException("Veritabanı bağlantısı kurulamadı.", e);
        }
    }

    // Singleton örneğini döndüren metod
    public static DatabaseConnection getInstance() {
        // İlk defa instance null ise
        if (instance == null) {
            // Bu blok ile nesne oluşturulmasını sağlar
            synchronized (DatabaseConnection.class) {
                // Tekrar kontrol edilir çünkü başka bir thread instance nesnesini oluşturmuş olabilir
                if (instance == null) {
                    // Instance ilk defa oluşturuluyor
                    instance = new DatabaseConnection();
                }
            }
        }
        // Singleton örneği döndürülür
        return instance;
    }

    // Veritabanı bağlantısını döndüren metod
    public Connection getConnection() {
        return connection;
    }


    }

