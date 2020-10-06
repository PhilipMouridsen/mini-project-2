import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sink {

    public static void main(String[] args) {

        Socket socket;
        BufferedReader input;

        try {
            socket = new Socket("localhost", 12345);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("SINK: " + input.readLine());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}