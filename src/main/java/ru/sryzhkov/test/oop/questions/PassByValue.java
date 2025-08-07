package ru.sryzhkov.test.oop.questions;

public class PassByValue {
    public static void modify(Integer x) {
        x = 100;  // Меняется только локальная копия
    }

    public static void changeName(Person p) {
        p.name = "Alice";  // Меняем состояние объекта
    }

    public static void reassign(Person p) {
        p = new Person("Alice");  // Локальная ссылка меняется, оригинал — нет
    }


    public static void main(String[] args) {
        Integer num = 10;
        modify(num);
        System.out.println(num);

//        Person person = new Person("Bob");
//        changeName(person);
//        System.out.println(person.name);

        Person person = new Person("Bob");
        reassign(person);
        System.out.println(person.name);
    }


}

class Person {
    String name;
    Person(String name) { this.name = name; }
}