package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        List<String> list = new ArrayList<>();
        Thread threadA = new Thread(){
            @Override
            public void run() {

               for (int i = 0;i<10;i++){
                   list.add("abc");
                   System.out.println("线程A添加元素，此时的list的size 为："+list.size());
                   try {
                       Thread.sleep(400);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   reentrantLock.lock();
                   try {
                       if(list.size() ==5){
                           condition.signal();
                       }
                   }finally {
                       reentrantLock.unlock();
                   }


               }

            }
        };

        Thread threadB = new Thread(){
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    condition.await();
                    System.out.println("线程B收到通知，开始执行自己的业务：");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    reentrantLock.unlock();
                }

            }
        };
        threadB.start();
        threadA.start();
    }
}
