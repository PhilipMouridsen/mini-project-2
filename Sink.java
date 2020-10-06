import java.net.*;
import java.io.*;

public class Sink {
    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket socket = new Socket("localhost", 12345);
        BufferedReader input = null;

        while (true) {

            try {
                // Read inputs from PubSub connection.
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Works?");
                System.out.println("PubSub: " + input.readLine());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
