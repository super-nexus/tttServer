import java.util.ArrayList;

/**
 * Created by Andrija on 6/2/16.
 */
public class Notification {

    String storedUsername;

    serverConstants inviteType;

    ArrayList<String> FriendInvites = new ArrayList<>();

    public Notification(String Username){
        storedUsername = Username;

        inviteType = serverConstants.DEAFAULT;
    }

    public serverConstants getInviteType() {
        return inviteType;
    }

    public ArrayList<String> getFriendInvites() {
        return FriendInvites;
    }

    public void addUserToList(String username){
        FriendInvites.add(username);
    }

    public void setInviteType(serverConstants inviteType) {
        this.inviteType = inviteType;
    }
}
