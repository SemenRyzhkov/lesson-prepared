package ru.sryzhkov.test.manager.extended;

import ru.sryzhkov.test.UserManager;
import ru.sryzhkov.test.manager.UserApiClient;

public class ApiUserSource implements UserSource {
    private  UserApiClient apiClient;

    @Override
    public UserManager.UserDetailsDto getUserDetails(String name) {
        return apiClient.fetchUserDetails(name);
    }
}