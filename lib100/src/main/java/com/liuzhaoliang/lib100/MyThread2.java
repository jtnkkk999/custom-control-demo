package com.liuzhaoliang.lib100;

/**
 * Created by liuzhaoliang on 2018/8/8.
 */

public class MyThread2 {
    public static int a = 0;
    public static void main(String[] args){
        MyThread3 my = new MyThread3();
        my.runTest();

    }


    static class MyThread3 implements TestDemo {


        @Override
        public void runTest() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        synchronized ( MyThread3.class ) {
                            if (a < 1000) {
                                a++;
                                System.out.println("线程1:" + a);
                            }  else {
                                break;
                            }
                        }
                    }

                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        synchronized ( MyThread3.class ) {
                            if (a < 1000) {
                                a++;
                                System.out.println("线程2:" + a);
                            }  else {
                                break;
                            }
                        }
                    }

                }
            }).start();
        }
    }

    interface TestDemo{
        void runTest();
    }

    //                    while(a<1000){
//                        synchronized(this) {
//                            a = a+1;
//
//                        }
//                    }
}
