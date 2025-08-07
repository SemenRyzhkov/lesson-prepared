package ru.sryzhkov.test.oop.questions;

class One {
    public static int one = 1;
    public int two = 2;
    static {
        System.out.println("Static block Parent");
        System.out.println("one = " + one);

    }

    {
        System.out.println("Non-static block Parent");
        System.out.println("two = " + two);
    }

    One() {
        System.out.println("Parent constructor");
    }
}

class Two extends One {
    public static int tree = 3;
    public int four = 4;
    static {
        System.out.println("Static block Child");
        System.out.println("tree = " + tree);
    }

    {
        System.out.println("Non-static block Child");
        System.out.println("four = " + four);
    }

    Two() {
        System.out.println("Child constructor");
    }
}

public class CallOrders {
    public static void main(String[] args) {
        new Two();
        new Two();
    }
}