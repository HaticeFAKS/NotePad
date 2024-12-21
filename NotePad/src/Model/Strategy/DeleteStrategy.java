package Model.Strategy;

public interface DeleteStrategy {
    boolean canDelete(String userRole);  // Kullanıcının rolüne göre silme işlemi yapılabilir mi kontrolü
}
