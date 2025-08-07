package ru.sryzhkov.test.oop.questions;

//Происходит во время выполнения (Runtime) на основе фактического типа объекта (не ссылки).
//
//Когда используется?
//Вызов не-static, не-final, не-private методов (которые могут быть переопределены).


import java.util.ArrayList;
import java.util.List;

class Animal {
    List<Number> sound() {
        return new ArrayList<>();
    }
}

class Dog extends Animal {

//    List<? extends Number> sound() {
//        return new ArrayList<Number>();
//    }

    void bark() {
    }
}

public class Dynamic {
    public static void main(String[] args) {
        Animal myAnimal = new Dog(); // Переменная типа Animal, но объект Dog
        myAnimal.sound(); // Вызовется Dog.sound() (динамическое связывание)
    }
}

//Статическое связывание — компилятор заранее знает, какой метод вызвать.
//
//Динамическое связывание — JVM решает в runtime, какой метод вызвать (на основе реального объекта).
//
//Полиморфизм в Java работает благодаря динамическому связыванию.