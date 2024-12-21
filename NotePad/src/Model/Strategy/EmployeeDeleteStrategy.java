package Model.Strategy;

public class EmployeeDeleteStrategy implements DeleteStrategy {
    @Override
    public boolean canDelete(String userRole) {
        // Çalışanlar notları silemez
        return false;
    }
}
