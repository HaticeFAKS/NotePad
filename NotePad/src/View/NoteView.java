package View;

import Model.ConcreteNote;
import Model.Factory.ConcreteNoteFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import Model.Strategy.*;

public class NoteView extends Application {

    private ConcreteNoteFactory noteFactory;  // Notları yönetmek için kullanılan fabrika sınıfı
    private TextField titleField;  // Not başlığını girebilmek için kullanılan TextField
    private TextArea contentArea;  // Not içeriğini girebilmek için kullanılan TextArea
    private ColorPicker colorPicker;  // Notun rengini seçmek için kullanılan ColorPicker
    private ToggleGroup statusGroup;  // Not durumunu seçmek için kullanılan ToggleGroup (Aktif/Arşivlenmiş)
    private DateTimeFormatter dateFormatter;  // Tarih formatını düzenlemek için kullanılan DateTimeFormatter
    private VBox activeNotesLayout;  // Aktif notların görüntüleneceği VBox
    private VBox archivedNotesLayout;  // Arşivlenmiş notların görüntüleneceği VBox
    private String userRole;  // Kullanıcı rolü (admin, user, employee gibi)
    private int userID;  // Kullanıcı ID'si
    private DeleteStrategy deleteStrategy;  // DeleteStrategy nesnesi, silme işlemine yetkili olup olmadığını kontrol eder

    // Yapıcı metot - Kullanıcı rolüne göre silme yetkisini belirler
    public NoteView(String userRole, int userID) {
        this.userRole = userRole;
        this.userID = userID;

        // Kullanıcı rolüne göre uygun DeleteStrategy'yi seç
        if ("admin".equals(userRole)) {
            this.deleteStrategy = new AdminDeleteStrategy();  // Admin rolü silme işlemine tamamen yetkilidir
        } else if ("user".equals(userRole)) {
            this.deleteStrategy = new UserDeleteStrategy();  // Normal kullanıcı sadece kendi notlarını silebilir
        } else if ("employee".equals(userRole)) {
            this.deleteStrategy = new EmployeeDeleteStrategy();  // Çalışanlar da sadece belirli yetkilerle silebilir
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Uygulama başlatılırken gerekli bileşenler oluşturulur
        noteFactory = new ConcreteNoteFactory();  // Not oluşturmak için fabrika sınıfı
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  // Tarih formatı ayarlandı

        primaryStage.setTitle("Kişiselleştirilmiş Not Defteri");  // Uygulamanın başlığı

        VBox mainLayout = new VBox(10);  // Ana layout VBox (dikey düzen)
        mainLayout.setPadding(new Insets(10));  // Layout'a iç boşluk ekleniyor

        // Not eklemek için gerekli bileşenler
        titleField = new TextField();
        titleField.setPromptText("Başlık");

        contentArea = new TextArea();
        contentArea.setPromptText("İçerik");

        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.WHITE);

        statusGroup = new ToggleGroup();  // Aktif/Arşivlenmiş durumunu seçmek için


        RadioButton archivedStatus = new RadioButton("Arşivlenmiş");
        archivedStatus.setToggleGroup(statusGroup);

        HBox statusBox = new HBox(10, archivedStatus);  // Durum butonları yatay olarak düzenleniyor

        Button addButton = new Button("Not Ekle");
        addButton.setOnAction(event -> addNote());  // Not eklemek için addNote metodu çağrılır

        VBox noteDetailsLayout = new VBox(10, titleField, contentArea, colorPicker, statusBox, addButton);
        noteDetailsLayout.setPadding(new Insets(10));  // Not detaylarının düzenlendiği VBox

        // Notları görüntülemek için iki ayrı layout: aktif ve arşivlenmiş
        activeNotesLayout = new VBox(10);
        activeNotesLayout.setPadding(new Insets(10));
        ScrollPane activeNotesScrollPane = new ScrollPane(activeNotesLayout);
        activeNotesScrollPane.setFitToWidth(true);
        activeNotesScrollPane.setPrefHeight(300);  // Aktif notlar için scrollable alan

        archivedNotesLayout = new VBox(10);
        archivedNotesLayout.setPadding(new Insets(10));
        ScrollPane archivedNotesScrollPane = new ScrollPane(archivedNotesLayout);
        archivedNotesScrollPane.setFitToWidth(true);
        archivedNotesScrollPane.setPrefHeight(200);  // Arşivlenmiş notlar için scrollable alan

        // SplitPane ile aktif notlar ve not ekleme alanı birleştirilir
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(activeNotesScrollPane, noteDetailsLayout);

        loadNotes();  // Başlangıçta notları yükle
        if (statusGroup.getSelectedToggle() != null) {
            if (statusGroup.getSelectedToggle().isSelected()) {
                statusGroup.getSelectedToggle().setSelected(false); // Tekrar tıklanırsa seçimi kaldır
            }
        }

        //Bileşenleri ana düzenin içine ekler
        mainLayout.getChildren().addAll(splitPane, new Label("Arşivlenmiş Notlar:"), archivedNotesScrollPane);

        Scene scene = new Scene(mainLayout, 800, 600);  // Sahne oluşturuluyor
        primaryStage.setScene(scene);
        primaryStage.show();  // Uygulama gösteriliyor
    }

    private void loadNotes() {
        activeNotesLayout.getChildren().clear();  // Önceki notlar temizleniyor
        archivedNotesLayout.getChildren().clear();

        // Kullanıcıya ait notlar yükleniyor
        List<ConcreteNote> notes = noteFactory.getNotesByUserID(userID);

        // Notları tarih sırasına göre tersten sıralıyoruz (en güncel olan en üstte)
        notes.sort((note1, note2) -> note2.getModifiedDate().compareTo(note1.getModifiedDate()));

        for (ConcreteNote note : notes) {
            VBox noteBox = createNoteBox(note);
            if (note.getStatus().equals("Arşivlenmiş")) {
                archivedNotesLayout.getChildren().add(noteBox);  // Arşivlenmiş notlar için ayrı bir düzenleme
            } else {
                activeNotesLayout.getChildren().add(noteBox);  // Aktif notlar için ayrı bir düzenleme
            }
        }
    }


    private void addNote() {
        String title = titleField.getText();
        String content = contentArea.getText();
        String color = colorPicker.getValue().toString();
        String status = (statusGroup.getSelectedToggle() == null) ? "Aktif": ((RadioButton) statusGroup.getSelectedToggle()).getText() ;

        if (!title.isEmpty() && !content.isEmpty()) {
            // Yeni not oluşturuluyor ve fabrika aracılığıyla ekleniyor
            ConcreteNote newNote = noteFactory.createNote(title, content, color, status, userID);
            noteFactory.addNote(newNote);

            loadNotes();  // Notlar tekrar yükleniyor
            if (statusGroup.getSelectedToggle() != null) {
                // Not durumunu değiştirdiğini kullanıcıya bildirir.
                newNote.setInitialState(newNote.getInactiveState());
                  // Durum değişikliği ve bildirim tetikleniyor
                if (statusGroup.getSelectedToggle().isSelected()) {
                    statusGroup.getSelectedToggle().setSelected(false); // Tekrar tıklanırsa seçimi kaldır
                }
            }
            else{
                newNote.setInitialState(newNote.getActiveState());
            }
            newNote.notifyState();
            showSuccessfulAlert("Başarılı", "Not başarıyla eklendi.");
        } else {
            showErrorAlert("Hata", "Başlık ve İçerik boş olamaz.");
        }
    }




    private VBox createNoteBox(ConcreteNote note) {
        // Her bir not için bir VBox kutusu oluşturuluyor
        VBox noteBox = new VBox(10);

        // Not rengini arka plana uygula
        String backgroundColor = note.getColor().replace("0x", "#");  // Renk kodu uygun şekilde değiştirilir
        noteBox.setStyle("-fx-background-color: " + backgroundColor + "; -fx-padding: 10;");

        Label titleLabel = new Label(note.getTitle());  // Başlık etiketi
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label contentLabel = new Label(note.getContent());  // İçerik etiketi
        Label dateLabel = new Label(note.getCreatedAt().format(dateFormatter));  // Tarih etiketi

        // Menü butonu (güncelle ve sil işlemleri)
        MenuButton menuButton = new MenuButton("...");
        MenuItem updateItem = new MenuItem("Güncelle");
        MenuItem deleteItem = new MenuItem("Sil");

        // Güncelleme ve silme işlemleri için eventler
        updateItem.setOnAction(event -> updateNote(note));
        deleteItem.setOnAction(event -> deleteNote(note));

        menuButton.getItems().addAll(updateItem, deleteItem);

        // Not kutusuna tüm bileşenler ekleniyor
        noteBox.getChildren().addAll(titleLabel, contentLabel, dateLabel, menuButton);
        return noteBox;
    }

    private void updateNote(ConcreteNote note) {
        // Güncelleme işlemi
        if (userRole.equals("admin") || userRole.equals("employee") || userRole.equals("user")) {
            String newTitle = titleField.getText();
            String newContent = contentArea.getText();
            String newColor = colorPicker.getValue().toString();
            String newStatus =  (statusGroup.getSelectedToggle() == null) ? "Aktif": ((RadioButton) statusGroup.getSelectedToggle()).getText() ;

            if (!newTitle.isEmpty() && !newContent.isEmpty()) {
                note.setTitle(newTitle);
                note.setContent(newContent);
                note.setColor(newColor);
                note.setStatus(newStatus);
                note.setModifiedDate(LocalDateTime.now());

                noteFactory.updateNote(note);  // Güncellenen not fabrika aracılığıyla kaydedilir
                loadNotes();
                if (statusGroup.getSelectedToggle() != null) {
                    // Not durumunu değiştirdiğini kullanıcıya bildirir.
                    note.setInitialState(note.getInactiveState());
                    // Durum değişikliği ve bildirim tetikleniyor
                    if (statusGroup.getSelectedToggle().isSelected()) {
                        statusGroup.getSelectedToggle().setSelected(false); // Tekrar tıklanırsa seçimi kaldır
                    }
                }
                else{
                    note.setInitialState(note.getActiveState());
                }
                note.notifyState();
                showSuccessfulAlert("Başarılı", "Not başarıyla güncellendi.");
            } else {
                showErrorAlert("Hata", "Başlık ve İçerik boş olamaz.");
            }
        } else {
            showErrorAlert("Hata", "Bu işlemi gerçekleştirme yetkiniz yok.");
        }
    }

    private void deleteNote(ConcreteNote note) {
        // Kullanıcıdan silme onayı al
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Onay");
        confirmationAlert.setHeaderText("Notu silmek istediğinize emin misiniz?");
        confirmationAlert.setContentText("Bu işlem geri alınamaz.");

        // Onay işlemi
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Silme işlemi
                if (deleteStrategy.canDelete(userRole)) {
                    noteFactory.deleteNote(note);  // Not silinir
                    loadNotes();  // Notlar tekrar yüklenir
                    showSuccessfulAlert("Başarılı", "Not başarıyla silindi.");
                } else {
                    showErrorAlert("Hata", "Bu işlemi gerçekleştirme yetkiniz yok.");
                }
            }
        });
    }


    private void showSuccessfulAlert(String title, String message) {
        // Hata mesajları için uyarı penceresi
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
    private void showErrorAlert(String title, String message) {
        // Hata mesajları için uyarı penceresi
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
