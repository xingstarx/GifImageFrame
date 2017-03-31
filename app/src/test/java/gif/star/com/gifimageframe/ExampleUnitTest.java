package gif.star.com.gifimageframe;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    class Producer implements Runnable {
        private final BlockingQueue queue;

        Producer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    queue.put(produce());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private Object produce() {
            System.out.println("produce invoked, ThreadName == " + Thread.currentThread().getName());
            return new Object();
        }
    }

    class Consumer implements Runnable {
        private final BlockingQueue queue;

        Consumer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    consume(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void consume(Object o) {
            System.out.println("consume o = " + o + ", ThreadName == " + Thread.currentThread().getName());
        }
    }

    @Test
    public void testProducerConsumer() {
        BlockingQueue q = new ArrayBlockingQueue(4);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        Consumer c3 = new Consumer(q);
        Consumer c4 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
        new Thread(c3).start();
        new Thread(c4).start();
    }
}