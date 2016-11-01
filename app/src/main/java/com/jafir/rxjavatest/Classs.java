package com.jafir.rxjavatest;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jafir on 16/8/1.
 */
public class Classs extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {


        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Classs{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
