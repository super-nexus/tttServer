/**
 * Created by Andrija on 5/16/16.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Datana {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/PLayers";

    private static final String DB_USER = "web";
    private static final String DB_PASSWORD = "web";

    Connection conn = null;
    Statement stmt = null;
    ResultSet results = null;

    Gui gui;

    public Datana(){

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            stmt = conn.createStatement();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public boolean checkIfUsernameExists(String givenUsername) {

        int affectedRows = 404;
        try {
            String query = "SELECT Username FROM PlayerF WHERE Username = '" + givenUsername + "'";

            results = stmt.executeQuery(query);

            //check if there is something under 'cursor' or not id there is its automaticly true else its false
            if (results.next()) {

                System.out.println("@checkIfUsernameExists: user exits");
                return true;
            } else {

                System.out.println("@checkIfUsernameExists: user does not exist");

                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUsernamePassword(String inputedUsername, String inputedPassword){

        String referenceUsername = null;
        String referencePassword = null;

        try {

            String query = "SELECT Username, Password FROM PlayerF WHERE Username = '" + inputedUsername + "'";

            ResultSet myResults = stmt.executeQuery(query);

            if (myResults.next()) {

                referenceUsername = myResults.getString("Username");
                referencePassword = myResults.getString("Password");

                if (inputedPassword.equals(referencePassword) && inputedUsername.equals(referenceUsername)) {
                    return true;
                }

            } else {
                System.out.println("@checKUsernamePassword: myResult has no next");
                return false;

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public int returnUsersId(String username){

        try {

            String query = "SELECT Id FROM PlayerF WHERE Username = '" + username + "'";

            results = stmt.executeQuery(query);

            if(results.next()){

                return results.getInt("Id");

            }

        }catch (SQLException e){

            e.printStackTrace();

        }
        return 404;
    }

    public HashMap<String, String> getAllUserInfo(int userId){

        HashMap<String, String> holder = new HashMap<>();


        try {

            String query = "SELECT * FROM PlayerF WHERE Id = " + userId;

            results = stmt.executeQuery(query);

            if(results.next()){

                holder.put("Username", results.getString("Username"));
                holder.put("Email", results.getString("Email"));
                holder.put("Password", results.getString("Password"));

            }
            else{

                System.out.println("@getAllUserInfo: invalid id");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return holder;

    }

    //this will just change the variable on mysql(online) to true or false
    public void setUserStatus(int online, int id){

        try{

            String query = "UPDATE PlayerF "
                    + "SET Online = " + online
                    + " WHERE Id = "+ id;

            int result = stmt.executeUpdate(query);

            if(result > 0){

                System.out.println("@setUserStatus: possible success");

            }
            else{
                System.out.println("@setUserStatus: Error result returned false");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public ArrayList<String> returnOnlineFriends(String[] friendUsernames){

        ArrayList<String> holder = new ArrayList<>();

        try{

            for(String username: friendUsernames){

                String query = "SELECT Online FROM PlayerF WHERE Username = '" + username + "'";

                results = stmt.executeQuery(query);

                if(results.next()){

                    if(results.getInt("Online") == 1){

                        holder.add(username);

                    }

                }
                else{
                    System.out.println("@returnOnlineFriends: error results has got no next");
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return holder;
    }



}
