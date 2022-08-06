package jcip.examples.g5build_block.synchronizer.barrier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    private static class Worker extends Thread {
        private CyclicBarrier cyclicBarrier;
        private int index;

        public Worker(CyclicBarrier cyclicBarrier, int index) {
            this.cyclicBarrier = cyclicBarrier;
            this.index = index;
            System.out.println(Thread.currentThread().getName() + " index:" + index);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000 * index);
                System.out.println(Thread.currentThread().getName() + " enter");
                cyclicBarrier.await(); // will block until all thread arrived!
                System.out.println(Thread.currentThread().getName() + " exit");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);

        for (int i = 0; i < threadCount; i++) {
            Worker worker = new Worker(cyclicBarrier, i);
            worker.start();
        }
    }

}
