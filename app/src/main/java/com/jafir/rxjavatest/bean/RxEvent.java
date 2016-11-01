package com.jafir.rxjavatest.bean;

/**
 * Created by jafir on 16/8/8.
 */
public class RxEvent{
    private String name;
    private int a;


    public RxEvent(String name, int a) {
        this.name = name;
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
