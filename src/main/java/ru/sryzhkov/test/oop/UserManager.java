package ru.sryzhkov.test.oop;

import lombok.Data;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для работы с пользовательскими данными.
 * Публичные методы - API, приватные - внутренняя логика.
 * <p>
 * Инкапсуляция — это принцип ООП, который объединяет данные (поля)
 * и методы (функции) для работы с ними в единый объект, скрывая внутреннюю реализацию от внешнего использования.
 * <p>
 * Абстракция — это принцип ООП, позволяющий выделять существенные характеристики объекта, игнорируя нерелевантные детали
 */


public class UserManager {

    private List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate;
    private final String externalServiceUrl;

    public UserManager(RestTemplate restTemplate, String externalServiceUrl) {
        this.restTemplate = restTemplate;
        this.externalServiceUrl = externalServiceUrl;
    }

// === Публичное API ===

    /**
     * Добавляет пользователя, получая дополнительные данные из внешнего сервиса.
     *
     * @param name Имя пользователя.
     */
    public void addUserWithDetails(String name) {
        validateName(name);
        String formattedName = formatName(name);
        UserDetailsDto externalData = fetchUserDetails(formattedName);
        User user = mapToUser(formattedName, externalData);
        storeUser(user);
    }

    /**
     * Возвращает пользователей в формате DTO.
     */
    private List<UserDto> getUsersDto() {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

// === Приватные методы ===

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
    }

    private String formatName(String name) {
        return name.trim().substring(0, 1).toUpperCase()
                + name.trim().substring(1).toLowerCase();
    }

    private UserDetailsDto fetchUserDetails(String name) {
        String url = String.format("%s/users/%s", externalServiceUrl, name);
        return restTemplate.getForObject(url, UserDetailsDto.class);
    }

    private User mapToUser(String name, UserDetailsDto dto) {
        return new User(
                name,
                dto.getEmail(),
                dto.getAge(),
                dto.isPremium()
        );
    }

    private UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getAge());

    }

    private void storeUser(User user) {
        if (users.stream().noneMatch(u -> u.getName().equals(user.getName()))) {
            users.add(user);
        }
    }

    @Data
    public static class User {
        private final String name;
        private final String email;
        private final int age;
        private final boolean isPremium;

        public User(String name, String email, int age, boolean isPremium) {
            this.name = name;
            this.email = email;
            this.age = age;
            this.isPremium = isPremium;
        }

    }

    @Data
    public static class UserDetailsDto {
        private String email;
        private int age;
        private boolean premium;

    }

    @Data
    public static class UserDto {
        private final String name;
        private final String email;
        private final int tier;

        public UserDto(String name, String email, int tier) {
            this.name = name;
            this.email = email;
            this.tier = tier;
        }

    }
}