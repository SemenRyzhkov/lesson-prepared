package ru.sryzhkov.test.oop.manager;

import org.springframework.web.client.RestTemplate;
import ru.sryzhkov.test.oop.UserManager;

public class UserApiClient {
    private final RestTemplate restTemplate;
    private final String apiUrl;

    public UserApiClient(RestTemplate restTemplate, String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public UserManager.UserDetailsDto fetchUserDetails(String name) {
        String url = String.format("%s/users/%s", apiUrl, name);
        return restTemplate.getForObject(url, UserManager.UserDetailsDto.class);
    }
}
