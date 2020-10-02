import java.util.*;
import java.net.*;
import java.io.*;


public class PubSubSystem {
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
            System.out.println("SERVER: Connected to client: " + PubSubSystem.address(connection));
            this.connection = connection;
            this.input = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
        }

        public void run() {
            String clientMsg = null;
            try {
                //clientMsg = this.input.readLine();
                String msg = "from PubSub";
                OutputStream toSinks = connection.getOutputStream();
                DataOutputStream out = new DataOutputStream(toSinks);
                out.write(msg.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("SERVER: Received a message from client " + PubSubSystem.address(connection) + ": " + clientMsg);
        }
    }
}
