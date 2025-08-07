package ru.sryzhkov.test.oop.manager;

import java.util.List;


/**
 * «Класс должен иметь только одну причину для изменения, то есть выполнять лишь одну ответственность».
 */
public class UserManager {
    private final UserValidator validator;
    private final UserNameFormatter formatter;
    private final UserApiClient apiClient;
    private final UserMapper mapper;
    private final UserRepository repository;

    public UserManager(
            UserValidator validator,
            UserNameFormatter formatter,
            UserApiClient apiClient,
            UserMapper mapper,
            UserRepository repository
    ) {
        this.validator = validator;
        this.formatter = formatter;
        this.apiClient = apiClient;
        this.mapper = mapper;
        this.repository = repository;
    }

    /**
     *  КОД АПИ МЕТОДА ДОЛЖЕН ЧИТАТЬСЯ, КАК КНИГА
     */
    public void addUser(String name) {
        validator.validate(name);
        String formattedName = formatter.format(name);
        ru.sryzhkov.test.oop.UserManager.UserDetailsDto dto = apiClient.fetchUserDetails(formattedName);
        ru.sryzhkov.test.oop.UserManager.User user = mapper.mapToUser(formattedName, dto);
        repository.save(user);
    }

    public List<ru.sryzhkov.test.oop.UserManager.UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToUserDto)
                .toList();
    }
}