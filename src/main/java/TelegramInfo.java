import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.api.objects.Update;

import java.io.IOException;


public class TelegramInfo {
    private static String offset = "23362417";
    private static final String offsetFormat = "?offset=";


    public static Update getUpdate(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String inStr = offsetFormat.concat(offset);
//        String inStr = "?offset=23362416";
        Updates ups = gson.fromJson(HandleRequest.makeTeleHTTPRequest("").toString(), Updates.class);
        Update update = ups.getResult().get(0);
        return update;
    }


    public static String getUpMess() {
        try {
            Update update = getUpdate();
            return update.getMessage().getText();
        } catch (IOException E) {
            System.out.println("Failure: Could not retrieve update message");
            E.printStackTrace();
        }
        return null;
    }

}
