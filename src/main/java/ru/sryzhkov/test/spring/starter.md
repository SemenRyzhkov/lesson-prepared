### Структура

my-spring-boot-starter/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── autoconfigure/
│   │   │           │   ├── MyServiceAutoConfiguration.java
│   │   │           │   └── MyServiceProperties.java
│   │   │           └── MyService.java
│   │   └── resources/
│   │       └── META-INF/
│   │           └── spring/
│   │               └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
│   └── test/
│       └── java/
└── pom.xml (или build.gradle)

### Завиcимости

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
        <version>3.2.0</version> <!-- Используйте актуальную версию -->
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <version>3.2.0</version>
        <optional>true</optional>
    </dependency>
</dependencies>


### Функционал

```java
package com.example;

public class MyService {

}
```


### Проперти

```java
package com.example.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "my.service")
public class MyServiceProperties {
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
```
### Автоконфиг

```java

package com.example.autoconfigure;

import com.example.MyService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(MyService.class)
@EnableConfigurationProperties(MyServiceProperties.class)
public class MyServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingProperty
    public MyService myService(MyServiceProperties properties) {
        return new MyService(properties.getGreeting());
    }
}
```

### Создание файла автоматической конфигурации

В src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports добавить:

com.example.autoconfigure.MyServiceAutoConfiguration


### Сборка и использование
Собрать проект и добавить его в целевой проект
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```