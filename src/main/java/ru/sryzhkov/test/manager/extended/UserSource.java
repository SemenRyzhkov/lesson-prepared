package ru.sryzhkov.test.manager.extended;

import ru.sryzhkov.test.UserManager;

public interface UserSource {
    UserManager.UserDetailsDto getUserDetails(String name);
}