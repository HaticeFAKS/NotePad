package Model.Factory;

import Model.Singleton.DatabaseConnection;
import Model.ConcreteNote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConcreteNoteFactory extends AbstractNoteFactory {

    // SQL sorguları, veritabanı işlemleri için kullanılan stringler
    private static final String ADD_NOTE_SQL = "INSERT INTO note (title, content, color, status, isModified, createdAt, modifiedDate, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_NOTE_SQL = "UPDATE note SET title = ?, content = ?, color = ?, status = ?, isModified = ?, modifiedDate = ?, userId = ? WHERE id = ?";
    private static final String DELETE_NOTE_SQL = "DELETE FROM note WHERE id = ?";
    private static final String GET_NOTES_BY_USER_ID_SQL = "SELECT * FROM note WHERE userId = ?"; // Kullanıcıya ait notları getiren SQL

    private Connection connection; // Veritabanı bağlantısı

    public ConcreteNoteFactory() {
        this.connection = DatabaseConnection.getInstance().getConnection(); // Veritabanı bağlantısını alır
    }

    // Not ekleme işlemi
    @Override
    public void addNote(ConcreteNote note) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_NOTE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            // Notun verilerini SQL sorgusuna ekliyoruz
            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());
            statement.setString(3, note.getColor());
            statement.setString(4, note.getStatus());
            statement.setBoolean(5, note.isModified());
            statement.setObject(6, note.getCreatedAt());  // Notun oluşturulma tarihi
            statement.setObject(7, note.getModifiedDate());  // Notun güncellenme tarihi
            statement.setInt(8, note.getUserId());  // Kullanıcı ID'si burada doğru şekilde kullanılmalı

            int affectedRows = statement.executeUpdate();  // Veritabanına ekleme işlemi
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        note.setId(generatedKeys.getInt(1));  // Eğer başarılıysa, yeni notun ID'sini alır
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Hata durumunda hata mesajı basılır
        }
    }

    // Kullanıcıya ait notları getirme
    public List<ConcreteNote> getNotesByUserID(int userId) {
        List<ConcreteNote> notes = new ArrayList<>();  // Notları tutacak liste
        try (PreparedStatement statement = connection.prepareStatement(GET_NOTES_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);  // Kullanıcı ID'sini sorguya ekliyoruz
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notes.add(mapResultSetToConcreteNote(resultSet));  // Sonuçları ConcreteNote nesnelerine dönüştürerek listeye ekliyoruz
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Hata durumunda hata mesajı basılır
        }
        return notes;  // Kullanıcıya ait tüm notlar döndürülür
    }

    // Not güncelleme işlemi
    @Override
    public void updateNote(ConcreteNote note) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_NOTE_SQL)) {
            // Notun verilerini SQL sorgusuna ekliyoruz
            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());
            statement.setString(3, note.getColor());
            statement.setString(4, note.getStatus());
            statement.setBoolean(5, note.isModified());
            statement.setObject(6, note.getModifiedDate());  // Güncellenme tarihi
            statement.setInt(7, note.getUserId());  // Kullanıcı ID'si
            statement.setInt(8, note.getId());  // Güncellenmesi gereken notun ID'si
            statement.executeUpdate();  // Veritabanındaki notu günceller
        } catch (Exception e) {
            e.printStackTrace();  // Hata durumunda hata mesajı basılır
        }
    }

    // Not silme işlemi
    @Override
    public void deleteNote(ConcreteNote note) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_NOTE_SQL)) {
            statement.setInt(1, note.getId());  // Silinecek notun ID'si
            statement.executeUpdate();  // Veritabanından notu siler
        } catch (SQLException e) {
            e.printStackTrace();  // Hata durumunda hata mesajı basılır
        }
    }


    // Yeni bir not oluşturma metodu
    public ConcreteNote createNote(String title, String content, String color, String status, int userId) {
        LocalDateTime currentTime = LocalDateTime.now();  // Not oluşturulurken geçerli zaman kullanılır
        return new ConcreteNote(0, title, content, color, status, false, currentTime, null, userId);
    }

    // ResultSet'i ConcreteNote nesnesine dönüştürme
    private ConcreteNote mapResultSetToConcreteNote(ResultSet resultSet) throws SQLException {
        return new ConcreteNote(
                resultSet.getInt("id"),  // Not ID'si
                resultSet.getString("title"),  // Not başlığı
                resultSet.getString("content"),  // Not içeriği
                resultSet.getString("color"),  // Not rengi
                resultSet.getString("status"),  // Not durumu
                resultSet.getBoolean("isModified"),  // Notun değiştirilip değiştirilmediği
                resultSet.getTimestamp("createdAt").toLocalDateTime(),  // Notun oluşturulma tarihi
                resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null,  // Güncellenme tarihi
                resultSet.getInt("userId")  // Kullanıcı ID'si
        );
    }
}
