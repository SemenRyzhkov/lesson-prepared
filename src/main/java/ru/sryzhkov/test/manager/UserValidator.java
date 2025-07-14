package ru.sryzhkov.test.manager;

public class UserValidator {
    public void validate(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
    }
}