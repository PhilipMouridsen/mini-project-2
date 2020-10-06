import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PubSub {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            System.out.println("PubSub: Listening for incoming connections...");
            Socket connection = serverSocket.accept();
            new Thread(new PubSubWorker(connection)).start();
        }

        // BufferedReader input;
        // ServerSocket serverSocket = new ServerSocket(12345);

        // while (true) {

        // System.out.println("PubSub: Listening for incoming connections...");

        // Socket connection = serverSocket.accept(); // Wait until connected.

        // System.out.println("Source connected!");

        // input = new BufferedReader(new
        // InputStreamReader(connection.getInputStream()));
        // System.out.println(input.readLine());
        // }
    }

    private static String address(Socket socket) {
        return socket.getInetAddress() + ":" + socket.getPort();
    }

    private static class PubSubWorker implements Runnable {
        Socket connection;
        BufferedReader input;

        PubSubWorker(Socket connection) throws IOException {
            System.out.println("PubSubWorker: Connected to: " + PubSub.address(connection));
            this.connection = connection;
            this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        public void run() {
            String clientMsg = null;

            try {

                String msg = "Tester from PubSubWorker!";
                
                clientMsg = input.readLine();

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(clientMsg.getBytes());
                outputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(
                    "PubSubWorker: Received a message from client " + PubSub.address(connection) + ": " + clientMsg);
        }
    }
}
