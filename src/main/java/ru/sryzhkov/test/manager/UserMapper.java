package ru.sryzhkov.test.manager;

import ru.sryzhkov.test.UserManager;

public class UserMapper {
    public UserManager.User mapToUser(String name, UserManager.UserDetailsDto dto) {
        return new UserManager.User(
                name,
                dto != null ? dto.getEmail() : null,
                dto.getAge(),
                dto.isPremium()
        );
    }

    public UserManager.UserDto mapToUserDto(UserManager.User user) {
        return new UserManager.UserDto(
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }
}