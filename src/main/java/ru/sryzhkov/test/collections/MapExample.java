package ru.sryzhkov.test.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

public final class MapExample {
    public static void main(String[] args) {
        Set<Obj> set = new HashSet<>();
        Obj obj = new Obj(10);
        set.add(obj);
        obj.setId(100);
        Obj obj1 = new Obj(100);
        set.add(obj1);

        System.out.println(set.contains(new Obj(10))); //false
        System.out.println(set.contains(obj)); //true
        System.out.println(set.size()); //2
        System.out.println(set); //100 100
    }
}
@Data
@AllArgsConstructor
@ToString
class Obj{
    int id;
    @Override
    public int hashCode() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        Obj obj = (Obj) o;
        return id == obj.id;
    }
}
