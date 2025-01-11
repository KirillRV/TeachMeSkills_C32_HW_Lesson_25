package repository;

import model.User;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {
        // Заполняем тестовыми данными (только для демонстрации!)
        // В реальном приложении данные должны загружаться из базы данных
        users.put("user1", new User("user1", "password123", "user"));
        users.put("admin", new User("admin", "adminpass", "admin"));
    }

    public User findUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}