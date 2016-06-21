import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Andrija on 6/1/16.
 */
public class Executer {

    Datana datana;

    String errorMessage = "";

    public Executer(Datana datana){
        this.datana = datana;
    }

    //this returns an id , if id is == 404 that means that there is an error
    public Player logIn(String username, String password){

        Player player = null;

        int userId = 404;

        if(datana.checkIfUsernameExists(username)){

            if(datana.checkUsernamePassword(username, password)){

                userId = datana.returnUsersId(username);

                datana.setUserStatus(1, userId);

                player = new Player(userId);

                player.setUserInfo(datana.getAllUserInfo(userId));

            }
            else{
                errorMessage = "Username and password do not match";
            }

        }
        else{
            errorMessage = "Username does not exist";
        }
        return player;
    }

    public void logOut(int id){
        datana.setUserStatus(0, id);
    }

    public boolean error(){

        if(errorMessage == ""){
            return false;
        }
        else {
            return true;
        }
    }

    public String returnErrorMessage(){
        return errorMessage;
    }

    private void resetErrorMEssage(){
        errorMessage = "";
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public void sendError(ObjectOutputStream outputStream, Request request, String errorMessage)throws IOException{

        request.setRequestType(serverConstants.ERROR_MESSAGE);
        request.setErrorMessage(errorMessage);
        outputStream.writeObject(request);

        resetErrorMEssage();

    }

    public void sendRequest(ObjectOutputStream outputStream, Request request) throws IOException{

        outputStream.writeObject(request);
        outputStream.flush();

    }


}
