import java.util.HashMap;

/**
 * Created by Andrija on 6/3/16.
 */
public class Player {

    HashMap<String, String> userInfo = new HashMap<>();

    int id;

    public Player(int id){

        this.id = id;

    }

    public void setUserInfo(HashMap<String, String> userInfo){

        System.out.println("Setting users info");

        this.userInfo = userInfo;
    }

    public String getInfo(String key){
        return userInfo.get(key);
    }

    public int getId() {
        return id;
    }
}
