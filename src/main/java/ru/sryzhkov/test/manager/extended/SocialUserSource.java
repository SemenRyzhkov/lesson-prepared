package ru.sryzhkov.test.manager.extended;

import ru.sryzhkov.test.UserManager;

public class SocialUserSource implements UserSource {
    @Override
    public UserManager.UserDetailsDto getUserDetails(String name) {
        // Логика получения данных из Facebook/Google
        return new UserManager.UserDetailsDto();
    }
}