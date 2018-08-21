package com.liuzhaoliang.lib100;

public class myClass {
    public static void main(String[] args){
        MyThread[] thread = new MyThread[10];
        for(int i =0;i<10;i++){
            thread[i] = new MyThread(i);
        }
        for(int i =0;i<1000;i++){
            int count = i%10;
//            System.out.println(i+"-"+count);
            thread[count].runTest();
        }

    }
    volatile static int a = 0;
    public static void run(int myCount){
        a++;
        System.out.println("线程"+myCount+":"+a);
    }
    static class MyThread implements TestDemo {
        int myCount;
        MyThread(int count){
            myCount =count;
        }
        @Override
        public void runTest() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myClass.run(myCount);

                }
            }).start();
        }
    }

    interface TestDemo{
        void runTest();
    }
}
