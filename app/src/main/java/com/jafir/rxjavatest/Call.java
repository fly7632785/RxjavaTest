package com.jafir.rxjavatest;

/**
 * Created by jafir on 16/7/25.
 */
public class Call {



    private String name;
    private String phone;
    private String time;
    private String type;
    private String duration;


    @Override
    public String toString() {
        return "Call{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
