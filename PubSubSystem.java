import java.net.*;
import java.io.*;

public class PubSubSystem {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            System.out.println("SERVER: Listening for incoming connections...");
            Socket connection = serverSocket.accept();
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
            this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        public void run() {
            String msgFromSource = null;
            try {
                msgFromSource = input.readLine();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(msgFromSource.getBytes());
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out
                    .println("SERVER: Received a message from client " + PubSubSystem.address(connection) + ": " + msgFromSource);
        }
    }
}
