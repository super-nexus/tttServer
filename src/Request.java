import java.io.Serializable;

public class Request implements Serializable{

	serverConstants requestType;

	String username, password, email;

    String errorMessage = "";

    String[] friendInvites;

	String wantedFriendName;

	int id;

	int[] move = {404, 404};

	public void setRequestType(serverConstants requestType){
		this.requestType = requestType;
	}

	public serverConstants getRequestType(){
		return requestType;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setMove(int givenSign, int field){
		move[0] = givenSign;
		move[1] = field;
	}

	public int[] getMove(){
		return move;
	}

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

	public String[] getFriendInvites() {
		return friendInvites;
	}

	public void setFriendInvites(String[] friendInvites) {
		this.friendInvites = friendInvites;
	}

	public String getWantedFriendName() {
		return wantedFriendName;
	}

	public void setWantedFriendName(String wantedFriendName) {
		this.wantedFriendName = wantedFriendName;
	}
}