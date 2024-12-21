package View;

import Controller.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterFrame extends Application {

    private UserController controller;

    // Yapıcı metod, UserController nesnesini oluşturur.
    public RegisterFrame() {
        controller = new UserController();
    }

    @Override
    public void start(Stage primaryStage) {
        // GridPane, bileşenleri düzenlemek için kullanılan bir layout.
        GridPane grid = new GridPane();
        grid.setVgap(10); // Dikey boşluk
        grid.setHgap(10); // Yatay boşluk

        // Kullanıcıdan isim almak için etiket ve text field (girdi alanı) oluşturuluyor.
        Label nameLabel = new Label("Ad Soyad:");
        TextField nameField = new TextField();

        // Kullanıcıdan e-posta almak için etiket ve text field oluşturuluyor.
        Label emailLabel = new Label("E-posta:");
        TextField emailField = new TextField();

        // Kullanıcıdan şifre almak için etiket ve password field (şifre alanı) oluşturuluyor.
        Label passwordLabel = new Label("Şifre:");
        PasswordField passwordField = new PasswordField();

        // Kullanıcıdan şifre tekrarını almak için etiket ve password field oluşturuluyor.
        Label confirmPasswordLabel = new Label("Şifre Tekrarı:");
        PasswordField confirmPasswordField = new PasswordField();

        // Kayıt ol butonu
        Button registerButton = new Button("Kayıt Ol");

        // Butona tıklanıldığında yapılacak işlemler
        registerButton.setOnAction(e -> {
            // Kullanıcıdan alınan veriler
            String fullName = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Boş alan kontrolü yapılır.
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Eğer herhangi bir alan boşsa, kullanıcıyı uyar.
                Alert alert = new Alert(Alert.AlertType.ERROR, "Lütfen tüm alanları doldurun!", ButtonType.OK);
                alert.show();
            } else {
                // Şifrelerin eşleşip eşleşmediği kontrol edilir
                if (password.equals(confirmPassword)) {
                    // Eğer şifreler eşleşiyorsa, kullanıcıyı kaydetmeye çalış
                    if (controller.registerUser(email, password, fullName)) {
                        // Kayıt başarılı ise bilgilendirme mesajı gösterilir.
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Kayıt başarılı!");
                        alert.show();
                        // Başarılı kayıttan sonra login ekranını aç
                        new LoginFrame().start(new Stage());
                        // Mevcut kayıt ekranını kapat
                        primaryStage.close();
                    } else {
                        // Eğer e-posta zaten kayıtlı ise hata mesajı gösterilir
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Bu e-posta zaten kayıtlı!", ButtonType.OK);
                        alert.show();
                    }
                } else {
                    // Şifreler uyuşmuyorsa hata mesajı gösterilir.
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Şifreler uyuşmuyor!", ButtonType.OK);
                    alert.show();
                }
            }
        });

        // GridPane üzerinde bileşenler ekleniyor
        grid.add(nameLabel, 0, 0); // "Ad Soyad" etiketini (0,0) konumuna ekler
        grid.add(nameField, 1, 0);  // Ad soyad girişi için text field'ı (1,0) konumuna ekler
        grid.add(emailLabel, 0, 1); // "E-posta" etiketini (0,1) konumuna ekler
        grid.add(emailField, 1, 1); // E-posta girişi için text field'ı (1,1) konumuna ekler
        grid.add(passwordLabel, 0, 2); // "Şifre" etiketini (0,2) konumuna ekler
        grid.add(passwordField, 1, 2); // Şifre girişi için password field'ı (1,2) konumuna ekler
        grid.add(confirmPasswordLabel, 0, 3); // "Şifre Tekrarı" etiketini (0,3) konumuna ekler
        grid.add(confirmPasswordField, 1, 3); // Şifre tekrar girişi için password field'ı (1,3) konumuna ekler
        grid.add(registerButton, 1, 4); // "Kayıt Ol" butonunu (1,4) konumuna ekler

        // Sahneyi oluşturuyoruz ve uygulama penceresini ayarlıyoruz.
        Scene scene = new Scene(grid, 400, 300); // 400x300 boyutlarında bir sahne oluştur
        primaryStage.setTitle("Kayıt Ol"); // Pencere başlığı
        primaryStage.setScene(scene); // Sahneyi pencereye yerleştir
        primaryStage.show(); // Pencereyi göster
    }
}
