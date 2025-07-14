package ru.sryzhkov.test;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Это упрощённый подход, при котором разработчик видит только два крайних варианта решения проблемы,
 * игнорируя нюансы и промежуточные варианты.
 * Примеры проявления:
 * «Или наследование, или композиция»
 * Чёрно-бело: «Наследование — зло, всегда используй композицию».
 * Реалистично: Наследование уместно для строгих иерархий (например, Animal → Cat), а композиция — для гибкости.
 *
 *
 * «Только ООП или только функциональщина»
 * Чёрно-бело: «Функциональное программирование лучше ООП».
 * Реалистично: Гибридный подход (например, ООП + лямбды в Java) часто эффективнее.
 *
 *
 * «Паттерны — панацея или бесполезны»
 * Чёрно-бело: «Всегда применяй паттерны» / «Паттерны — это overengineering».
 * Реалистично: Паттерны решают конкретные проблемы, но не нужны везде.
 *
 *
 * «Тесты должны покрывать 100% кода»
 * Чёрно-бело: «Без 100% coverage проект — мусор».
 * Реалистично: Важнее тестировать критичную логику, а не геттеры/сеттеры.
 *
 *
 * PROJECT CODE STYLE
 */
public class Executor {

    private List<User> users = new ArrayList<>();
    private RestTemplate restTemplate;
    private String externalServiceUrl;

    public void execute(String inputName) {
        if (inputName == null || inputName.trim().isEmpty()) {
            throw new RuntimeException("Error 123");
        }

        String formattedName;
        try {
            formattedName = inputName.trim().substring(0, 1).toUpperCase()
                    + inputName.trim().substring(1).toLowerCase();
        } catch (StringIndexOutOfBoundsException e) {
            formattedName = inputName.trim().toUpperCase(); // Костыль
        }

        UserDetailsDto externalData;
        try {
            String url = externalServiceUrl + "/user/" + formattedName;
            externalData = restTemplate.getForObject(url, UserDetailsDto.class);
        } catch (Exception e) {
            externalData = new UserDetailsDto(); // Дефолтные значения
        }

        User user = new User();
        user.setName(formattedName);
        if (externalData != null) {
            user.setEmail(externalData.getEmail() != null
                    ? externalData.getEmail() : "default@mail.com");
            user.setPremium(externalData.isPremium());
        }

        boolean exists = false;
        for (User u : users) {
            if (u.getName().equals(formattedName)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            users.add(user);
        }
    }


}

@Getter
@Setter
class User {
    private String name;
    private String email;
    private boolean isPremium;
}

@Getter
@Setter
class UserDetailsDto {
    private String email;
    private boolean premium;
}