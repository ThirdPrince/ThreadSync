package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 是一种非常灵活的实现线程间阻塞和唤醒的工具，
 * 使用它不用关注是等待线程先进行还是唤醒线程先运行，但是得知道线程的名字。
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        final Thread threadB = new Thread(){
            @Override
            public void run() {
                LockSupport.park();
                System.out.println("线程B接到通知，开始执行自己的任务");
            }
        };

        Thread threadA = new Thread(){
            @Override
            public void run() {
                for (int i = 0;i<10;i++){
                    list.add("abc");
                    System.out.println("线程A添加元素,此时list 的size :"+list.size());
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(list.size() == 5 ){
                        LockSupport.unpark(threadB);
                    }
                }
            }
        };

        threadA.start();
        threadB.start();


    }
}
