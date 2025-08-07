package ru.sryzhkov.test.oop.questions;

public class Enumm {
    public static final String RED = "RED";
    public static final String GREEN = "GREEN";

    static void setColor(String color) {
        System.out.println("Need only red or green, but color is " + color);
    }

    static void setColorEnum(Color color) {

    }


    public static void main(String[] args) {
        setColor("BLUE");
    }
}

enum Color {RED, GREEN, BLUE}

