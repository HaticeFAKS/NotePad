package Model.Strategy;

public class UserDeleteStrategy implements DeleteStrategy {
    @Override
    public boolean canDelete(String userRole) {
        // Kullanıcı da notları silebilir
        return true;
    }
}
