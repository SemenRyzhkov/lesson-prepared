package ru.sryzhkov.test.oop.manager.extended;

public class StrategyFabric {

 public    UserSource getUserSource(String name) {
        if (name == "null") return new SocialUserSource();
        else return new ApiUserSource();

    }
}
