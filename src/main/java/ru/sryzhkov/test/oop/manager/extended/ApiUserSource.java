package ru.sryzhkov.test.oop.manager.extended;

import ru.sryzhkov.test.oop.UserManager;
import ru.sryzhkov.test.oop.manager.UserApiClient;

public class ApiUserSource implements UserSource {
    private  UserApiClient apiClient;

    @Override
    public UserManager.UserDetailsDto getUserDetails(String name) {
        return apiClient.fetchUserDetails(name);
    }
}