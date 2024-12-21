package View;

import Controller.UserController;  // UserController import edildi
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class AdminView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // GridPane kullanılarak arayüz elemanları düzenleniyor
        GridPane grid = new GridPane();
        grid.setVgap(10);  // Yatay mesafe
        grid.setHgap(10);  // Dikey mesafe

        // E-posta alanı için etiket ve TextField
        Label emailLabel = new Label("E-posta:");
        TextField emailField = new TextField();

        // Şifre alanı için etiket ve PasswordField
        Label passwordLabel = new Label("Şifre:");
        PasswordField passwordField = new PasswordField();

        // Ad Soyad alanı için etiket ve TextField
        Label nameLabel = new Label("Ad Soyad:");
        TextField nameField = new TextField();

        // Rol seçimi için etiket ve ComboBox
        Label roleLabel = new Label("Rol Seç:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("admin", "user", "employee");  // Admin, user ve employee rollerini ekliyoruz
        roleComboBox.setValue("user");  // Varsayılan olarak 'user' rolü seçili

        // Kullanıcı ekleme butonu
        Button addUserButton = new Button("Kullanıcı Ekle");

        // Butona tıklanınca kullanıcı ekleme işlemi yapılır
        addUserButton.setOnAction(e -> {
            String fullName = nameField.getText();  // Ad soyad alınır
            String email = emailField.getText();  // E-posta alınır
            String password = passwordField.getText();  // Şifre alınır
            String role = roleComboBox.getValue();  // Seçilen rol alınır

            // UserController kullanarak kullanıcı ekleme işlemi yapılır
            UserController controller = new UserController();
            controller.addUser(fullName, email, password, role);  // Kullanıcı, belirlenen bilgilerle eklenir

            // Başarı mesajı gösterilir
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Kullanıcı başarıyla eklendi!");
            alert.show();
        });

        // Çıkış butonu ekleniyor
        Button logoutButton = new Button("Çıkış");

        // Çıkış butonuna tıklanınca, admin paneli kapanır ve giriş ekranı açılır
        logoutButton.setOnAction(e -> {
            primaryStage.close();  // Admin panelini kapat
            LoginFrame loginView = new LoginFrame();  // Giriş ekranını başlat
            loginView.start(new Stage());  // Yeni bir Stage ile giriş ekranını göster
        });

        // Arayüz elemanları grid'e ekleniyor
        grid.add(nameLabel, 0, 0);  // Ad soyad etiketi
        grid.add(nameField, 1, 0);  // Ad soyad input alanı
        grid.add(emailLabel, 0, 1);  // E-posta etiketi
        grid.add(emailField, 1, 1);  // E-posta input alanı
        grid.add(passwordLabel, 0, 2);  // Şifre etiketi
        grid.add(passwordField, 1, 2);  // Şifre input alanı
        grid.add(roleLabel, 0, 3);  // Rol seç etiketi
        grid.add(roleComboBox, 1, 3);  // Rol seçimi ComboBox'ı
        grid.add(addUserButton, 1, 4);  // Kullanıcı ekle butonu

        // Çıkış butonunun eklenmesi için HBox kullanıyoruz
        HBox hBox = new HBox(10, logoutButton);  // Çıkış butonunu bir HBox içine koyuyoruz
        grid.add(hBox, 1, 5);  // HBox'ı grid'e ekliyoruz

        // Scene oluşturuluyor ve ekranda gösteriliyor
        Scene scene = new Scene(grid, 400, 300);  // Scene oluşturuluyor ve boyutlandırılıyor
        primaryStage.setTitle("Admin Paneli");  // Başlık belirleniyor
        primaryStage.setScene(scene);  // Scene, Stage'e ekleniyor
        primaryStage.show();  // Admin paneli ekranda gösteriliyor
    }

}
