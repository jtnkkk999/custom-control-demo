package com.liuzhaoliang.lib100;

/**
 * Created by liuzhaoliang on 2018/8/14.
 */

public class myClass2 {
    public static volatile int a = 0;
    public static void main(String[] args){

        for(int i = 0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (myClass2.class) {
                        a = a + 1;
                        System.out.print(a+" ");
                    }
                }
            }).start();
        }
    }
}
