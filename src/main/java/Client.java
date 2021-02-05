import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 20000;

        try (Socket clientSocket = new Socket(host, port);
             PrintWriter toServer = new PrintWriter((clientSocket.getOutputStream()), true);
             BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            Scanner scanner = new Scanner(System.in);
            toServer.println("Hello, server!");
            while(clientSocket.isConnected())
            {
                String response = fromServer.readLine();
                System.out.println("Response from server: " + response);
                if(response.endsWith("Bye!"))
                    break;
                toServer.println(scanner.nextLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

