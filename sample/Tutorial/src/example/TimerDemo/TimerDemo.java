package example.TimerDemo;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask running!");
                //this.cancel();
            }
        };

        timer.schedule(task, 0, 2000);
    }
}
