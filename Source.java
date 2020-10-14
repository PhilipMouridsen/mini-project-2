import java.util.*;
import java.net.*;
import java.io.*;

public class Source {
    public static void main(String[] args) throws UnknownHostException, IOException {

        Scanner sc = new Scanner(System.in);
        int port = 12345;

        // Connect to the PubSub.
        Socket source = null;

        while (true) {
            source = new Socket("localhost", port);
            try {
                DataOutputStream out = new DataOutputStream(source.getOutputStream());

                out.writeByte(1); // Identify as sink.
                out.flush();

                // Start reading in lines.
                String input = sc.nextLine();

                out.writeUTF(input);
                out.flush();

                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
