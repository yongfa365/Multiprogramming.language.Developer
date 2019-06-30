package yongfa365;


import java.util.Random;

public class Helper {
    public static Random RANDOM = new Random();

    public static void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void maxSleep(int i) {
        try {
            Thread.sleep(RANDOM.nextInt(i));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
