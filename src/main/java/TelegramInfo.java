import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.api.objects.Update;


public class TelegramInfo {
    private static String offset = "23362417";
    private static final String offsetFormat = "?offset=";


    public static Update getUpdate(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Updates ups = gson.fromJson(HandleRequest.makeTeleHTTPRequest("").toString(), Updates.class);
        Update update = ups.getResult().get(0);
        return update;
    }

}
