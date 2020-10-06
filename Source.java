import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Source {

    public static void main(String[] args) throws IOException {

        Socket connection = new Socket("localhost", 12345);
        System.out.println("I'm the main for " + connection.getInetAddress() + ":" + connection.getLocalPort());

        Scanner scanner = new Scanner(System.in);

        OutputStream outputStream;

        while (true) {

            String message = scanner.nextLine();
            System.out.println(message);

            try {
                outputStream = connection.getOutputStream();
                outputStream.write(message.getBytes());
                outputStream.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    
    }

}
