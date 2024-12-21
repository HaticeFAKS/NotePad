package Model;

import java.time.LocalDateTime;
import Model.State.*;  // Durumları (State) içe aktarıyoruz

public class ConcreteNote {

    // Notun özellikleri
    private int id;  // Notun benzersiz kimliği
    private String title;  // Notun başlığı
    private String content;  // Notun içeriği
    private String color;  // Notun rengi (isteğe bağlı)
    private String status;  // Notun durumu (örneğin aktif veya pasif)
    private boolean isModified;  // Notun değişip değişmediğini gösteren durum
    private LocalDateTime createdAt;  // Notun oluşturulma tarihi
    private LocalDateTime modifiedDate;  // Notun son güncellenme tarihi
    private int userId;  // Notun ait olduğu kullanıcı ID'si
    private NoteState inactiveState;
    private NoteState activeState;  // Notun geçerli durumu (Active veya Inactive gibi)

    private NoteState currentState;
    // Constructor (Yapıcı metod)
    public ConcreteNote(int id, String title, String content, String color, String status, boolean isModified, LocalDateTime createdAt, LocalDateTime modifiedDate, int userId) {
        activeState = new ActiveNoteState(this);
        inactiveState = new InactiveNoteState(this);
        this.id = id;
        this.title = title;
        this.content = content;
        this.color = color;
        this.status = status;
        this.isModified = isModified;
        this.createdAt = createdAt;
        this.modifiedDate = modifiedDate != null ? modifiedDate : LocalDateTime.now();  // Eğer `modifiedDate` null ise, şu anki zamanı kullan
        this.userId = userId;

    }

    // Getter ve Setter'lar

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getUserId() {
        return userId;
    }

    // Durum objesinin getter ve setter'ı


    public NoteState getActiveState() {
        return activeState;
    }

    public NoteState getInactiveState() {
        return inactiveState;
    }

    public void setInitialState(NoteState initialState) {
        currentState = initialState;
    }

    // Notun durumunu değiştiren metot
    public void notifyState() {
        this.currentState.notifyState();  // Durum değişikliği yapılır, notifyUser() burada tetiklenir
        System.out.println("Yeni durum: " + this.status); // Yeni durumu kontrol et
    }


}
