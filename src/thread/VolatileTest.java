package thread;

import java.util.ArrayList;
import java.util.List;

/**
 问题
 有两个线程，A 线程向一个集合里面依次添加元素“abc”字符串，一共添加十次，当添加到第五次的时候
 ，希望 B 线程能够收到 A 线程的通知，然后 B 线程执行相关的业务操作。
 线程间通信的模型有两种：共享内存和消息传递，以下方式都是基本这两种模型来实现的。
 *  一、使用 volatile 关键字
 * 基于 volatile 关键字来实现线程间相互通信是使用共享内存的思想。
 * 大致意思就是多个线程同时监听一个变量，当这个变量发生变化的时候 ，线程能够感知并执行相应的业务。这也是最简单的一种实现方式。
 */
public class VolatileTest {

    static volatile boolean notice = false;

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Thread threadA = new Thread(){
            @Override
            public void run() {

                for (int i = 0 ;i <=10;i++){
                    list.add("abc");
                    System.out.println("线程A添加元素,此时list的size 为："+list.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(list.size() == 5){
                        notice = true;
                        //break;
                    }
                }
            }
        };


        Thread threadB = new Thread(){
            @Override
            public void run() {
              while (true){
                  if (notice){
                      System.out.println("线程B收到通知,开始执行自己的业务");
                      break;
                  }
              }
            }
        };

        threadA.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();
        System.out.println("主线程---");

    }
}
