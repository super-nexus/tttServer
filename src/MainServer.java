import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Andrija on 5/16/16.
 */


public class MainServer {


    public static void main(String[] args)  {


        ServerSocket serverSocket = null;

        NotificationCenter notificationCenter = new NotificationCenter();

        boolean listen = true;

        try{
            serverSocket = new ServerSocket(4444);
        }catch (IOException e){

            e.printStackTrace();

        }


        while(listen){

            try {

                System.out.println("Server booting up");

                System.out.println("Listening for connections...");

                Socket clientSocket = serverSocket.accept();

                System.out.println("Connected to " + clientSocket.getInetAddress().getHostName());

                new  ServerThread(clientSocket, notificationCenter).run();

            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

}
