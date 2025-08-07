package ru.sryzhkov.test.oop.manager.extended;

import ru.sryzhkov.test.oop.UserManager;

public interface UserSource {
    UserManager.UserDetailsDto getUserDetails(String name);
}