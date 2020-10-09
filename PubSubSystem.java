import java.util.*;
import java.net.*;
import java.io.*;

public class PubSubSystem {
    private static List<Socket> connections = new ArrayList<Socket>();//connection need to be dynamic, possible to removed and added

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            System.out.println("SERVER: Listening for incoming connections...");
            Socket connection = serverSocket.accept();  // waits here until a client connects
            connections.add(connection);
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
            String msg = null;
            try {
              msg = input.readLine();
              for(Socket con : connections){ //delete from connections if isClosed is true
                if(!con.isClosed()){
                  DataOutputStream out = new DataOutputStream(con.getOutputStream());
                  out.writeUTF(msg);
                } else {
                  connections.remove(con);
                }
              }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("SERVER: Received a message from client " + PubSubSystem.address(connection) + ": " + msg);
        }
    }
}
