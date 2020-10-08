import java.util.*;
import java.net.*;
import java.io.*;

public class Source{
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int port = 12345;
    while(true){
      try{
        String input = sc.nextLine();
        Socket source = new Socket("localhost", port);
        DataOutputStream out = new DataOutputStream(source.getOutputStream());
        out.write(input.getBytes());
        out.flush();
        out.close();
        //source.close();
      } catch (IOException e){
        e.printStackTrace();
      }
    }
  }
}
