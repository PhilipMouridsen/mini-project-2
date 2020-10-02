import java.util.*;
import java.net.*;
import java.io.*;

public class Sink {
  public static void main(String[] args){
    int port = 12345;
    while(true){
      try{
        Socket sink = new Socket("localhost", port);
        DataInputStream in = new DataInputStream(sink.getInputStream());
        System.out.println("Server says " + in.readUTF());

        in.close();
        sink.close();
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }
}
