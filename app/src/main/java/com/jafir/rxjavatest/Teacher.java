package com.jafir.rxjavatest;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jafir on 16/8/1.
 */
public class Teacher extends RealmObject {

    private String name;
    @PrimaryKey
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
