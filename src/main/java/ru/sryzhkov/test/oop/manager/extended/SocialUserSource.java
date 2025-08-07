package ru.sryzhkov.test.oop.manager.extended;

import ru.sryzhkov.test.oop.UserManager;

public class SocialUserSource implements UserSource {
    @Override
    public UserManager.UserDetailsDto getUserDetails(String name) {
        // Логика получения данных из Facebook/Google
        return new UserManager.UserDetailsDto();
    }
}