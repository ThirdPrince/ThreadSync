package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * jdk1.5 之后在java.util.concurrent包下提供了很多并发编程相关的工具类，
 * 简化了并发编程代码的书写，CountDownLatch 基于 AQS 框架，相当于也是维护了一个线程间共享变量 state。
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        List<String> list = new ArrayList<>();
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                for (int i = 0;i <=10;i++){
                    list.add("abc");
                    System.out.println("线程A添加元素，此时list的size 为："+list.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (list.size() == 5){
                        countDownLatch.countDown();
                    }
                }
            }
        };
        Thread threadB = new Thread(){
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程B收到消息,开始任务");
            }
        };
        threadB.start();
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        thread1.start();

    }
}
