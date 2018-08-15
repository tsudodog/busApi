import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.api.objects.Update;

import java.io.IOException;


public class TelegramInfo {
    private static String offset = "233624013";
    private static final String offsetFormat = "?offset=";


    private static Update getUpdate()throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String inStr = offsetFormat.concat(offset);
        Updates ups = gson.fromJson(HandleRequest.makeTeleHTTPRequest(inStr).toString(), Updates.class);
        Update update = ups.getResult().get(0);
//       need to implement way to update offset
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
