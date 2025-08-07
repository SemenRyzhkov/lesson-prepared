package ru.sryzhkov.test.oop.manager.extended;

import ru.sryzhkov.test.oop.manager.UserMapper;
import ru.sryzhkov.test.oop.manager.UserNameFormatter;
import ru.sryzhkov.test.oop.manager.UserRepository;
import ru.sryzhkov.test.oop.manager.UserValidator;



/**
 * "Программные сущности должны быть открыты для расширения, но закрыты для модификации."
 * Это означает, что класс должен быть спроектирован так, чтобы:
 * Не изменять его исходный код при добавлении новой функциональности.
 * Расширять его поведение через наследование, композицию или интерфейсы.
 *
 * Принцип: «Предпочитайте композицию наследованию» (Favor Composition Over Inheritance, GoF).
 *
 * Плюсы:
 *  Гибкость: Легко заменить зависимость (например, UserApiClient → MockApiClient).
 *  Тестируемость: Зависимости можно мокать.
 *  Избегает хрупкости: Изменения в родительском классе не ломают наследников.
 *  Реализует принципы SOLID:
 *
 * "Выбирайте наследование, только если между классами есть отношение is-a (например, SocialUserManager — это разновидность BaseUserManager),
 * а не для простого переиспользования кода."
 *
 * Паттерн Стратегия
 *
 */
public class UserManager {
    private final UserValidator validator;
    private final UserNameFormatter formatter;
    private final UserMapper mapper;
    private final UserRepository repository;
    private final StrategyFabric fabric; // Зависимость через интерфейс

    public UserManager(
            UserValidator validator,
            UserNameFormatter formatter,
            UserMapper mapper,
            UserRepository repository,
            StrategyFabric fabric // Инъекция реализации
    ) {
        this.validator = validator;
        this.formatter = formatter;
        this.mapper = mapper;
        this.repository = repository;
        this.fabric = fabric;
    }

    public void addUser(String name) {
        validator.validate(name);
        String formattedName = formatter.format(name);
        ru.sryzhkov.test.oop.UserManager.UserDetailsDto dto = fabric.getUserSource(formattedName).getUserDetails(formattedName); // Используем интерфейс
        ru.sryzhkov.test.oop.UserManager.User user = mapper.mapToUser(formattedName, dto);
        repository.save(user);
    }
}
