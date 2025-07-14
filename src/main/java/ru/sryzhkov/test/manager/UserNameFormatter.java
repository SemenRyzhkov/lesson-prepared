package ru.sryzhkov.test.manager;

public class UserNameFormatter {
    public String format(String name) {
        return name.trim().substring(0, 1).toUpperCase()
                + name.trim().substring(1).toLowerCase();
    }
}
