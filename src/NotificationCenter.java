import java.util.HashMap;

/**
 * Created by Andrija on 6/2/16.
 */
public class NotificationCenter {

    HashMap<String, Notification> notificationHashMap = new HashMap<>();

    protected void addNotificationPage(String username, Notification notification){

        if(notificationHashMap.get(username) == null) {

            System.out.println("Adding " + username + "'s notification to notification center ");

            notificationHashMap.put(username, notification);
        }
        else{
            System.out.println("Not adding notification page, it already exists");
        }
    }

    protected Notification getNotificiationPageFrom(String username){

        System.out.println("getting notification page from "+ username);

        return notificationHashMap.get(username);
    }

    protected HashMap<String, Notification> getNotificationHashMap(){
        return notificationHashMap;
    }

}
