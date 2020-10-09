import java.util.*;
import java.net.*;
import java.io.*;

public class Source {
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        Scanner sc = new Scanner(System.in);
        int port = 12345;

        // Connect to the PubSub.
        Socket source = new Socket("localhost", port);;

        while (true) {

            source = new Socket("localhost", port);

            try {

                // Start reading in lines.
                String input = sc.nextLine();
                DataOutputStream out = new DataOutputStream(source.getOutputStream());
                out.write(input.getBytes());
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    
}
