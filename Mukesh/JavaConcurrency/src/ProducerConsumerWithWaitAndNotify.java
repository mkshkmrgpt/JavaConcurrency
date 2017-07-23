import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mukesh on 7/23/2017.
 */
public class ProducerConsumerWithWaitAndNotify {

    static Queue<Integer> list = new LinkedList();

    static class Producer implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    while (list.size() == 10) {
                        try {
                            System.out.println("Queue is full waiting for consumer to consumer");
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int value = new Random().nextInt();
                    System.out.println("Produced value " + value);
                    list.add(value);
                    list.notifyAll();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        int count = 0;

        @Override
        public void run() {

            while (true) {
                synchronized (list) {
                    while (list.isEmpty()) {
                        try {
                            System.out.println("Queque is empty waiting for producer to add");
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Consumed " + list.remove());
                    list.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Producer());
        executorService.submit(new Consumer());

    }
}
