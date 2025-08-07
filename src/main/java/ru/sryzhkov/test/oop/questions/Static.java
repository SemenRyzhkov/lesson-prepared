package ru.sryzhkov.test.oop.questions;

//1. Статическое связывание (Static Binding)
//Происходит на этапе компиляции. Компилятор однозначно определяет, какой метод или переменная должны быть вызваны.
//
//Когда используется?
//Вызов статических методов (static).
//
//Вызов final-методов (не могут быть переопределены).
//
//Вызов private-методов (не наследуются).
//
//Работа с переменными (полями класса).
class Parent {
     void show() {
        System.out.println("Parent's static method");
    }
}

class Child extends Parent {
     void show() {
        System.out.println("Child's static method");
    }
}

public class Static {
    public static void main(String[] args) {
        Parent obj = new Child();
        obj.show();
    }
}