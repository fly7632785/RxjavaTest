package com.jafir.rxjavatest.utils;

/**
 * Created by jafir on 16/7/14.
 */
public class CommonUtil {


    public  static  <T> T  checkNotNull(T o){
        if(o == null)
            throw new NullPointerException();
        return o;
    }


    public  static  <T> T  checkNotNull(T o,String msg){
        if(o == null)
            throw new NullPointerException(msg);

        return o;
    }
}
