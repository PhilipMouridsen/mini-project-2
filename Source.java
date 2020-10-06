import java.util.*;
import java.net.*;
import java.io.*;

public class Source {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            try {

                // Connect to PubSub.
                Socket socket = new Socket("localhost", 12345);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // Read and send out data.
                String input = scanner.nextLine();
                out.write(input.getBytes());
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
}
