import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            System.out.println("SERVER: Listening for incoming connections...");
            Socket connection = serverSocket.accept();  // waits here until a client connects
            new Thread(new ClientHandler(connection)).start();
        }
    }

    private static String address(Socket socket) {
        return socket.getInetAddress() + ":" + socket.getPort();
    }

    private static class ClientHandler implements Runnable {
        Socket connection;
        BufferedReader input;

        ClientHandler(Socket connection) throws IOException {
            System.out.println("SERVER: Connected to client: " + Main.address(connection));
            this.connection = connection;
            this.input = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
        }

        @Override
        public void run() {
            while (true) {
                String clientMsg = null;
                try {
                    clientMsg = this.input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("SERVER: Received a message from client " + Main.address(connection) + ": " + clientMsg);
            }
        }
    }
}