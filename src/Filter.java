import com.sun.tools.corba.se.idl.constExpr.Not;
import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Andrija on 6/1/16.
 */
public class Filter{

    Datana datana;

    Executer exec;

    Request request;

    Request requestToSend;

    ObjectOutputStream outputStream;

    filter_to_thread mCommander;

    NotificationCenter notificationCenter;

    Player player;

    public Filter(Request request , ObjectOutputStream output, NotificationCenter notificationCenter){

        datana = new Datana();

        exec = new Executer(datana);

        requestToSend = new Request();

        this.notificationCenter = notificationCenter;

        outputStream = output;

        player = null;

        this.request = request;
    }

    public Filter(Request request, ObjectOutputStream output){

        datana = new Datana();

        exec = new Executer(datana);

        requestToSend = new Request();

        outputStream = output;

        player = null;

        this.request = request;

    }

    public void setUpFilterToThreadCommunication(filter_to_thread commander){
        mCommander = commander;
    }

    public void filter(serverConstants requestType) throws IOException {

        switch (requestType){

            case LOGIN_REQUEST:

                System.out.println("Recieved Log in request");

                player = exec.logIn(request.getUsername(), request.getPassword());

                if(exec.error()){

                   exec.sendError(outputStream, requestToSend, exec.returnErrorMessage());

                }
                else {
                    mCommander.OnPlayerLoggedIn(player);
                    requestToSend.setRequestType(serverConstants.ID_RECIEVED);
                    requestToSend.setId(player.getId());

                    requestToSend.setErrorMessage("Logged in successfuly!");

                    exec.sendRequest(outputStream, request);
                }
                break;

            case DEAFAULT:

                System.out.println("recieved default");

                exec.sendError(outputStream, requestToSend, "Something went wrong with communication");
                break;

            case LOGOUT_REQUEST:

                System.out.println("Recieved Log out request");

                exec.logOut(request.getId());

                requestToSend.setErrorMessage("Logged out");

                exec.sendRequest(outputStream, requestToSend);

                mCommander.OnPlayerLoggedOut(request.getId());

                break;
            case CHECK_NOTIFICATION:

                System.out.println("Checking for notification");

                if(notificationCenter.getNotificiationPageFrom(request.getUsername()).inviteType != serverConstants.DEAFAULT){

                    filterNotification(notificationCenter.getNotificiationPageFrom(request.getUsername()));

                }

                break;

            case SEND_FRIEND_REQUEST:

                if(request.getWantedFriendName() == null || request.getUsername() == null){
                    exec.sendError(outputStream, requestToSend, "Error WantedFriendName must not be null");
                }
                else{

                    notificationCenter.getNotificiationPageFrom(request.getWantedFriendName())
                            .addUserToList(request.getUsername());
                    notificationCenter.getNotificiationPageFrom(request.getWantedFriendName())
                            .setInviteType(serverConstants.FRIEND_REQUEST_RECIEVED);
                    System.out.println("Notification Added");
                }

                break;

        }

    }

    public void filterNotification(Notification notification){

        try {

            System.out.println("Notification recieved filtering now");

            switch (notification.getInviteType()) {

                case FRIEND_REQUEST_RECIEVED:

                    System.out.println("Notification Recieved");

                    requestToSend.setRequestType(serverConstants.FRIEND_REQUEST_RECIEVED);

                    ArrayList<String> invites = notification.getFriendInvites();

                    String[] holder = new String[invites.size()];

                    for(int i = 0; i < invites.size(); i++){
                        holder[i] = invites.get(i);
                    }

                    requestToSend.setFriendInvites(holder);

                    requestToSend.setRequestType(serverConstants.FRIEND_REQUEST_RECIEVED);

                    exec.sendRequest(outputStream, requestToSend);

                    break;

                case GAME_INVITE_RECIEVED:

                    requestToSend.setRequestType(serverConstants.GAME_INVITE_RECIEVED);

                    //TODO: GAME INVITE RECIEVED


            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
