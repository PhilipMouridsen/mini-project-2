import java.util.*;
import java.net.*;
import java.io.*;

public class Sink {
  public static void main(String[] args) throws IOException {
    int port = 12345;
    Socket sink = null;

    while(true){
      try{
        sink = new Socket("localhost", port);
        DataInputStream in = new DataInputStream(sink.getInputStream());
        System.out.println("Server says " + in.readUTF());
        in.close();
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }
}
