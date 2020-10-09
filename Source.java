import java.util.*;
import java.net.*;
import java.io.*;

public class Source{
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    int port = 12345;
    Socket source = null;

    while(true){
      source = new Socket("localhost", port);
      try{
        String input = sc.nextLine();
        DataOutputStream out = new DataOutputStream(source.getOutputStream());
        out.write(input.getBytes());
        out.flush();
        out.close();
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }
}
