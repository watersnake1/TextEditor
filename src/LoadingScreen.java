/**
 * Created by cmurray17 on 1/25/16.
 */
import javax.swing.*;

public class LoadingScreen implements Runnable, Globals {
    @Override
    public void run() {
        showLoadingUI();
    }

    public static void main(String[] args) {
        Thread loading = new Thread(new LoadingScreen());
        loading.start();
    }

    public static showLoadingUI() {
        loadingSplash();
        Thread.sleep(1000);
        startSignal = true;

    }

    public void loadingSplash() {
        //some splash screen image code here
        //should include a label that goes . , .. , ... , and then back again
    }
}
