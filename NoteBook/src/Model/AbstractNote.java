package Model;

import java.time.LocalDateTime;

public abstract class AbstractNote {

    // Notun temel özelliklerini tanımlar
    private int id;  // Notun benzersiz kimliği
    private String title;  // Notun başlığı
    private String content;  // Notun içeriği
    private boolean isModified;  // Notun değişip değişmediğini belirten durum


    // Getter ve Setter'lar

    // Notun id'sini alır
    public int getId() {
        return id;
    }

    // Notun id'sini ayarlar
    public void setId(int id) {
        this.id = id;
    }

    // Notun başlığını alır
    public String getTitle() {
        return title;
    }

    // Notun başlığını ayarlar
    public void setTitle(String title) {
        this.title = title;
    }

    // Notun içeriğini alır
    public String getContent() {
        return content;
    }

    // Notun içeriğini ayarlar
    public void setContent(String content) {
        this.content = content;
    }

    // Notun değiştirilip değiştirilmediğini kontrol eder
    public boolean isModified() {
        return isModified;
    }

    // Notun değiştirilme durumunu ayarlar
    public void setModified(boolean modified) {
        isModified = modified;
    }

    // `modifiedDate` (güncellenme tarihi) için getter ve setter eklenebilir, ancak şimdilik eksik bırakılmıştır.
}
