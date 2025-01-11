package model;

import model.User;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {
        users.put("user1", new User("user", "password", "user"));
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
