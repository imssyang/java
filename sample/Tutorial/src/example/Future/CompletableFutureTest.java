package example.Future;

import java.util.ArrayList;
import java.util.concurrent.*;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));

        ArrayList<CompletableFuture<String>> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();
            int finalI = i;
            stringCompletableFuture.whenCompleteAsync((e, a) -> {
                System.out.println(finalI + ": Complete " + e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {e1.printStackTrace();}
            }, executorService);

            list.add(stringCompletableFuture);
        }

        System.out.println("list.size:" + list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            list.get(i).complete(i + "-argument");
        }
        */

        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName()+"\t completableFuture1");
        });
        completableFuture1.get();

        //异步回调
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"\t completableFuture2");
            //int i = 10/0;
            return 1024;
        });
        completableFuture2.whenComplete((t,u)->{
            System.out.println("-------t="+t); // 1024
            System.out.println("-------u="+u); // null
        }).exceptionally(f->{
            System.out.println("-----exception:"+f.getMessage());
            return 444;
        });
    }
}
