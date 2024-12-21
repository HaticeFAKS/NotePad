package Model;

public class User {

    // Kullanıcının e-posta adresi
    private String email;
    // Kullanıcının şifresi
    private String password;
    // Kullanıcının rolü (örneğin: "admin", "user" gibi)
    private String role;
    // Kullanıcının benzersiz kimliği (ID)
    private int userID;

    // Constructor (Yapıcı metod) - Kullanıcıyı oluşturmak için gerekli parametreler
    public User(String email, String password, String role, int userID) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.userID = userID;
    }

    // Getter ve Setter metodları

    // Kullanıcının şifresini döndürür
    public String getPassword() {
        return password;
    }

    // Kullanıcının şifresini günceller
    public void setPassword(String password) {
        this.password = password;
    }

    // Kullanıcının rolünü döndürür
    public String getRole() {
        return role;
    }

    // Kullanıcının rolünü günceller
    public void setRole(String role) {
        this.role = role;
    }

    // Kullanıcının benzersiz kimliğini döndürür
    public int getUserID() {
        return userID;
    }
}
