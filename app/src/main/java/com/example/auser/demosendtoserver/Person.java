package com.example.auser.demosendtoserver;

import java.util.ArrayList;

/**
 * Created by auser on 2017/11/27.
 */

public class Person {
    String name;
    int age;
    boolean isMale;
    ArrayList<String> favorites=new ArrayList<>();
    Data data;
    public Person() {
        this("邱小美", 18, false, new Data(),null);
    }

    public Person(String name, int age, boolean isMale, Data data, ArrayList<String> favorites) {
        this.name = name;
        this.age = age;
        this.isMale = isMale;

        this.data=data;
        if (favorites==null){
            this.favorites.add("看電影");
            this.favorites.add("上網");
            this.favorites.add("看電視");
            this.favorites.add("吃零食");
        }else
            this.favorites=favorites;

    }

    public String getName() {

        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isMale() {
        return isMale;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }




}
