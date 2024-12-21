package View;

import Controller.UserController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginFrame extends Application {

    private UserController controller;

    // Yapıcı metod, UserController nesnesini oluşturur.
    public LoginFrame() {
        controller = new UserController();
    }

    // Kullanıcıyı doğrulamak için kullanılan metod(input)
    private String authenticateUser(String email, String password) {
        return controller.authenticateUser(email, password);  // Kullanıcı rolü döndürülür (admin, user vs.)
    }

    @Override
    public void start(Stage primaryStage) {
        // GridPane, bileşenleri düzenlemek için kullanılan bir layout.
        GridPane grid = new GridPane();
        grid.setVgap(10); // Dikey boşluk
        grid.setHgap(10); // Yatay boşluk

        // Kullanıcıdan e-posta almak için etiket ve text field (girdi alanı) oluşturuluyor.
        Label emailLabel = new Label("E-posta:");
        TextField emailField = new TextField();

        // Kullanıcıdan şifre almak için etiket ve password field (şifre alanı) oluşturuluyor.
        Label passwordLabel = new Label("Şifre:");
        PasswordField passwordField = new PasswordField();

        // Giriş yap butonu oluşturuluyor.
        Button loginButton = new Button("Giriş Yap");
        // Kayıt ol butonu oluşturuluyor.
        Button registerButton = new Button("Kayıt Ol");

        // Giriş yap butonuna tıklanması durumunda yapılacak işlemler.
        loginButton.setOnAction(e -> {
            // Kullanıcıdan alınan e-posta ve şifre
            String email = emailField.getText();
            String password = passwordField.getText();

            // Kullanıcıyı doğrulamak için authenticateUser metodunu çağırıyoruz.
            String role = authenticateUser(email, password);  // Kullanıcı rolü alınır

            // Eğer rol null değilse (yani giriş başarılıysa)
            if (role != null) {
                // Kullanıcıya ait userID alınır
                int userID = controller.getUserID(email);

                // Giriş başarılı mesajı gösterilir.
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Giriş başarılı!");
                alert.show();

                // Alert'ı 2 saniye sonra kapatmak için PauseTransition kullanıyoruz
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> alert.close());
                pause.play();

                // Eğer kullanıcı rolü "admin" ise, AdminView açılır
                if (role.equals("admin")) {
                    new AdminView().start(new Stage());
                } else {
                    // Kullanıcı rolüne göre NoteView açılır
                    new NoteView(role, userID).start(new Stage());
                }

                primaryStage.close();  // Login ekranını kapat
            } else {
                // Giriş başarısızsa hata mesajı gösterilir.
                Alert alert = new Alert(Alert.AlertType.ERROR, "Geçersiz e-posta veya şifre!", ButtonType.OK);
                alert.show();

                // Alert'ı 2 saniye sonra kapatmak için PauseTransition kullanıyoruz
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> alert.close());
                pause.play();
            }
        });

        // Kayıt ol butonuna tıklanması durumunda RegisterFrame açılır.
        registerButton.setOnAction(e -> {
            new RegisterFrame().start(new Stage());  // Kayıt ekranını aç
            primaryStage.close();  // Login ekranını kapat
        });

        // GridPane üzerine etiketler ve butonlar eklenir
        grid.add(emailLabel, 0, 0);  // E-posta etiketini (0,0) konumuna ekler
        grid.add(emailField, 1, 0);  // E-posta girişi için text field'ı (1,0) konumuna ekler
        grid.add(passwordLabel, 0, 1);  // Şifre etiketini (0,1) konumuna ekler
        grid.add(passwordField, 1, 1);  // Şifre girişi için password field'ı (1,1) konumuna ekler
        grid.add(loginButton, 1, 2);  // "Giriş Yap" butonunu (1,2) konumuna ekler
        grid.add(registerButton, 1, 3);  // "Kayıt Ol" butonunu (1,3) konumuna ekler

        // Sahneyi oluşturuyoruz ve uygulama penceresini ayarlıyoruz.
        Scene scene = new Scene(grid, 400, 250); // 400x250 boyutlarında bir sahne oluştur
        primaryStage.setTitle("Giriş Yap"); // Pencere başlığı
        primaryStage.setScene(scene); // Sahneyi pencereye yerleştir
        primaryStage.show(); // Pencereyi göster
    }
}
