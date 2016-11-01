package com.jafir.rxjavatest;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jafir on 16/8/1.
 */
public class Student  extends RealmObject{

    @PrimaryKey
    private String id;
    private String name;
    private int age;
    private RealmList<Teacher> teacher;
    private Classs cls;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RealmList<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(RealmList<Teacher> teacher) {
        this.teacher = teacher;
    }

    public Classs getCls() {
        return cls;
    }

    public void setCls(Classs cls) {
        this.cls = cls;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", teacher=" + teacher.toString() +
                ", cls=" + cls +
                '}';
    }
}
