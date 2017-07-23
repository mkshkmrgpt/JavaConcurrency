import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by mukesh on 7/23/2017.
 */
public class ProducerConsumerBlockingQueque {

    static ArrayBlockingQueue queue = new ArrayBlockingQueue<Integer>(50);

    static class Producer implements Callable {

        int count = 0;

        @Override
        public Object call() {
            while (count++ < 50) {
                try {
                    queue.put(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Produced " + (count - 1);
        }
    }

    static class Consumer implements Callable {

        int count = 0;

        @Override
        public Object call() {
            while (count++ < 50) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Consumed " + (count - 1);
        }
    }

    public static void main(String[] args) {

        List<Callable<String>> producersAndConsumers = new ArrayList<>(8);

        for (int i = 0; i < 4; i++) {
            producersAndConsumers.add(new Producer());
            producersAndConsumers.add(new Consumer());
        }
        System.out.println("Producer and consumers launched");
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<Future<String>> futures;
        try {
            futures = executorService.invokeAll(producersAndConsumers);
            futures.forEach(future -> {
                try {
                    System.out.println(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            System.out.println("Producer consumer ended");
        }
    }
}
