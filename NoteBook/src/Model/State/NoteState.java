package Model.State;

import Model.ConcreteNote;
import javafx.scene.control.Alert;  // Alert importu
import javafx.application.Platform;


public abstract class NoteState {
    protected ConcreteNote note;//aynı paketteki tüm sınıflardan erişilebilir.

    public NoteState(ConcreteNote note) {

        this.note = note;
    }

    // Durum değiştiğinde döndüren metot
    public abstract void notifyState();


    // Kullanıcıyı bilgilendirme metodu
    protected void notifyUser(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);  // Bildirim tipi
            alert.setTitle("Durum Değişikliği");  // Başlık
            alert.setHeaderText(null);  // Başlık altında bir açıklama yok
            alert.setContentText(message);  // Mesaj içeriği
            alert.showAndWait();  // Bildirimi göster
        });
    }



}
