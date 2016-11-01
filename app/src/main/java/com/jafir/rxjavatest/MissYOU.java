package com.jafir.rxjavatest;

/**
 * Created by jafir on 16/8/1.
 */
public class MissYOU {

    private void print() {

        String string = "zhangyang is a famous fool dog";
        int[] index = new int[]{17, 10, 20, 20, 9, 5, 18, 19};
        String truth = "";
        for (int i = 0; i < index.length; i++) {
            truth += string.charAt(index[i]);
        }
        System.out.print(truth);
    }


}
