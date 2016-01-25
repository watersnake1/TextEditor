/**
 * Created by cmurray17 on 1/25/16.
 */
import javax.swing.*;

public class LoadingScreen implements Runnable{
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Thread loading = new Thread(new LoadingScreen());
        loading.start();
    }

    public static showLoadingUI() {
        JFrame frame = new JFrame("loading");

    }
}
