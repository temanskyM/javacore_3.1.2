import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter toClient;
    private BufferedReader fromClient;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 20000;
        Server server = new Server(port);
        server.startServer();
    }

    public void startServer() {
        int port = 20000;
        try {
            System.out.println("Starting server");

            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();//Начинем слушать порт и ожидать подключения
            toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            startConversation();

            toClient.close();
            fromClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startConversation() {
        System.out.println("New connection!");
        String response;
        String name;
        int years;
        try {
            //First step
            response = fromClient.readLine();
            System.out.println("Message from client: " + response);
            toClient.println("Hello, client! What's your name?");

            //Second
            response = fromClient.readLine();
            System.out.println("Message from client: " + response);
            name = response;
            toClient.println("How old are you?");

            //Third
            response = fromClient.readLine();
            System.out.println("Message from client: " + response);
            years = Integer.parseInt(response);
            if (years > 17) {
                toClient.println(String.format("Nice to meet you, %s! Have a nice day at work! Bye!", name));
            } else {
                toClient.println(String.format("Nice to meet you, %s! Have a nice day at school! Bye!", name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
