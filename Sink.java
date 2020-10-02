import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sink implements Runnable {

    @Override
    public void run() {
        System.out.println("Sink running and subscribed to PubSub system.");
    }

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 12344);
            Sink sink = new Sink(socket);
            new Thread(sink);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}