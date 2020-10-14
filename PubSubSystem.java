import java.util.*;
import java.net.*;
import java.io.*;

public class PubSubSystem {
    private static List<Socket> sinks = Collections.synchronizedList(new ArrayList<>());// Connections need to be dynamic, possible to removed and added.

    public static void main(String[] args) throws IOException {

        // Start the PubSub System.
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true) {
            System.out.println("PubSub: Listening for incoming connections...");

            Socket connection = serverSocket.accept(); // Waits here until a client connects.
            DataInputStream in = new DataInputStream(connection.getInputStream());

            switch (in.readByte()) {
                case 1:
                    System.out.println("It's a source!");
                    break;

                case 2:
                    System.out.println("It's a sink!");
                    sinks.add(connection);
                    break;

                default:
                    System.out.println("Didn't receive shit.");
            }

            new Thread(new ClientHandler(connection)).start();
        }
    }

    static String address(Socket socket) {
        return socket.getInetAddress() + ":" + socket.getPort();
    }

    static class ClientHandler implements Runnable {

        Socket connection;
        BufferedReader input;

        ClientHandler(Socket connection) throws IOException {
            System.out.println("PubSub: Connected to source: " + PubSubSystem.address(connection));
            this.connection = connection;
            this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        public void run() {

            String msg = null;

            try {
                msg = input.readLine();

                for (Socket sink : sinks) {

                    if (!sink.isClosed()) {
                        DataOutputStream out = new DataOutputStream(sink.getOutputStream());
                        out.writeUTF(msg);
                        out.close();
                    }
                }
            } catch (IOException e) {
                System.out.println("Caught exception!!!!");
                e.printStackTrace();
            }

            // Do some cleaning, by removing closed sources and sinks...
            synchronized (sinks) {
                Iterator<Socket> sinkIterator = sinks.listIterator();
                while (sinkIterator.hasNext()) {
                    if(sinkIterator.next().isClosed()) {
                        sinkIterator.remove();
                    }
                }
            }

            // System.out.println(
            //         "PubSub: Received a message from source: " + PubSubSystem.address(connection) + ": " + msg);
        }
    }
}
