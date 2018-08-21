import javax.ws.rs.POST;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleRequest {
    private static final String BUS_URL = "http://svc.metrotransit.org/NexTrip/";
    private static final String TELEGRAM_UPDATE_URL = "https://api.telegram.org/bot526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA/getUpdates";
    private static final String TELEGRAM_SEND_MESSAGE_URL = "https://api.telegram.org/bot526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA/sendMessage?chat_id=";


        /**
     * Created by Alex Zalewski on 6/19/2018.
     *
     * Makes a HTTP request, reads and returns information from url.
     *
     * @param inStr input url to request and read from.
     * @return json StringBuffer information to be handled with getter methods.
     * @throws IOException
     */
    public static StringBuffer makeBusHttpRequest(String inStr) throws IOException {
        try {
            URL url = new URL(BUS_URL.concat(inStr));
            return makeHTTPRequest(url);
        } catch (IOException e) {
            System.out.println("Failure: Unable to make Bus HTTP Request");
            e.printStackTrace();
        }
        return null;
    }

    public static StringBuffer makeTeleHTTPRequest(String inStr) {
        try {
            URL url = new URL(TELEGRAM_UPDATE_URL.concat(inStr));
            return makeHTTPRequest(url);
        } catch (IOException e) {
            System.out.println("Failure: Unable to make Telegram HTTP Request");
            e.printStackTrace();
        }
        return null;
    }

    private static StringBuffer makeHTTPRequest(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer retString = new StringBuffer();
        while ((inputLine = input.readLine()) != null) {
            retString.append(inputLine);
        }
        input.close();
        return retString;
    }

    public static void sendToTelegram(String chat_id, String message) throws IOException{
        try {
            String mess = chat_id + "&text=" + message;
            URL url = new URL(TELEGRAM_SEND_MESSAGE_URL.concat(mess));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
        } catch (IOException e) {
            System.out.println("Failed to send to Telegram");
            e.printStackTrace();
        }
    }
}
