package Model.Strategy;

public class AdminDeleteStrategy implements DeleteStrategy {
    @Override
    public boolean canDelete(String userRole) {
        // Admin her zaman silme işlemi yapabilir
        return true;
    }
}
