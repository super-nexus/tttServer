/**
 * Created by Andrija on 6/3/16.
 */
public interface filter_to_thread {
    void OnPlayerLoggedIn(Player player);
    void OnPlayerLoggedOut(int id);
    void OnRequestRecieved(Request request);
}
