import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PubSub {

    public static void main(String[] args) throws IOException {

        BufferedReader input;
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true) {

            System.out.println("PubSub: Listening for incoming connections...");
            Socket connection = serverSocket.accept(); // waits here until a client connects

            System.out.println("Source connected!");

            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(input.readLine());
        }
    }
}
