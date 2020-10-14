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
            DataInputStream in = new DataInputStream(connection.getInputStream());
            byte identifier = in.readByte();
            if(identifier == 0)
              connections.add(connection);
            new Thread(new ClientHandler(connection)).start();
        }
    }

    private static String address(Socket socket) {
        return socket.getInetAddress() + ":" + socket.getPort();
    }


    private static class ClientHandler implements Runnable {
        private Socket connection;
        private BufferedReader input;
        private List<Socket> snapshotCon;

        ClientHandler(Socket connection) throws IOException {
            System.out.println("SERVER: Connected to client: " + PubSubSystem.address(connection));
            this.connection = connection;
            this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        public void run() {
            snapshotCon = new ArrayList<Socket>(connections);
            String msg = null;
            try {
              msg = input.readLine();
              for(Socket con : snapshotCon){
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeUTF(msg);
                out.close();
                connections.remove(con);
              }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!msg.equals(null))
              System.out.println("SERVER: Received a message from client " + PubSubSystem.address(connection) + ": " + msg);
        }
    }
}
