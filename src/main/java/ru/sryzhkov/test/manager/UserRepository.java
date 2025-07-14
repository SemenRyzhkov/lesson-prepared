package ru.sryzhkov.test.manager;
import ru.sryzhkov.test.UserManager;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<UserManager.User> users = new ArrayList<>();

    public void save(UserManager.User user) {
        if (users.stream().noneMatch(u -> u.getName().equals(user.getName()))) {
            users.add(user);
        }
    }

    public List<UserManager.User> findAll() {
        return new ArrayList<>(users); // Возвращаем копию
    }
}