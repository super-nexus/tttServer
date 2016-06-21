import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Andrija on 5/16/16.
 */
public class ServerThread extends Thread implements filter_to_thread {

    private Socket conn;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private NotificationCenter notificationCenter;

    Notification notificationPage;

    Request request;
    public Player player = null;

    boolean connected = true;

    boolean notificationExists = false;

    public ServerThread(Socket socket, NotificationCenter notificationCenter){

        System.out.println("Initializing serverThread");

        conn = socket;

        request = new Request();

        this.notificationCenter = notificationCenter;

    }


    @Override
    public void OnPlayerLoggedIn(Player player){

        this.player = player;

        String username = player.getInfo("Username");

        notificationPage = new Notification(username);

        notificationCenter.addNotificationPage(username, notificationPage);

        notificationExists = true;

        System.out.println("Player has successfuly logged in");

    }


    @Override
    public void OnPlayerLoggedOut(int id) {

        closeDownEverything();

        notificationCenter.getNotificationHashMap().remove(player.getInfo("Username"));

        System.out.println("Player has successfuly logged out");
    }

    @Override
    public void OnRequestRecieved(Request request) {

        try {
            System.out.println("Sending request to filter");

            Filter filter = new Filter(request, output);

            filter.setUpFilterToThreadCommunication(this);

            System.out.println("filter to thread communication has been set up");

            filter.filter(request.getRequestType());
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    @Override
    public void run() {

        System.out.println("Server thread started running");

        try {

            setUpStreams();


            while (connected){

                exchangeInformation();

            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private void setUpStreams() throws IOException{

        System.out.println("Setting up streams");

        output = new ObjectOutputStream(conn.getOutputStream());

        input = new ObjectInputStream(conn.getInputStream());

        output.flush();

        System.out.println("Streams have been set up");

    }


    private void closeDownEverything(){

        try{

            output.close();
            input.close();
            conn.close();
            this.connected = false;

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void exchangeInformation()throws IOException{
        recieveInformation();
    }


    private void recieveInformation()throws IOException{

        try {

            request = (Request) input.readObject();

            System.out.println("Sending request to filter");

            Filter filter = new Filter(request, output, notificationCenter);

            filter.setUpFilterToThreadCommunication(this);

            System.out.println("filter to thread communication has been set up");

            filter.filter(request.getRequestType());


        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
