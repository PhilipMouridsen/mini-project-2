import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Source implements Runnable {

    Socket connection;

    Source(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("I'm the runnable for " + connection.getInetAddress() + ":" + connection.getLocalPort());

        OutputStream outputStream;

        while (true) {
            String message = scanner.nextLine();
            System.out.println(message);
            try {
                outputStream = connection.getOutputStream();
                outputStream.write(message.getBytes());
                
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {

        Socket connection = new Socket("localhost", 12345);
        Source source = new Source(connection);
        new Thread(source).start();
        
    }
}
