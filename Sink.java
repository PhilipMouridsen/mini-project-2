import java.util.*;
import java.net.*;
import java.io.*;

public class Sink {
  public static void main(String[] args) throws UnknownHostException, IOException {

    int port = 12345;
    Socket sink = null;

    while (true) {
      sink = new Socket("localhost", port);

      try {
        DataOutputStream out = new DataOutputStream(sink.getOutputStream());
        out.writeByte(2); // Identify as sink.
        out.flush();

        DataInputStream in = new DataInputStream(sink.getInputStream());
        System.out.println("Source says via PubSub: " + in.readUTF());
        in.close();

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

  }

}
