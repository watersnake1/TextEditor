import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by cmurray17 on 12/14/15.
 */
public class MainRunnable implements Runnable, Globals {
    @Override
    public void run() {
        View window = new View();
        window.createAndShowGUI();
        window.buttonHandler();
    }

    public static void main(String[] args) {
        Thread textEdit = new Thread(new MainRunnable());
        textEdit.start();
    }
}
