import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class HandleRequest {
    private static final String BUS_URL = "http://svc.metrotransit.org/NexTrip/";
    private static final String TELEGRAM_UPDATE_URL = "https://api.telegram.org/bot526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA/getUpdates";
    private static final String TELEGRAM_SEND_MESSAGE_URL = "https://api.telegram.org/bot526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA/sendMessage?chat_id=";
    private static final String DEL_WEBHOOK_URL = "https://api.telegram.org/bot526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA/deleteWebhook";
    private static final String BOT_TOKEN = "526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA";
    private static final String SET_WEBHOOK_URL = "https://api.telegram.org/bot526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA/setWebhook?url=https://a8051178.ngrok.io/mtbotmain";
    private static final String WEBHOOK_INFO_URL = "https://api.telegram.org/bot526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA/getWebhookInfo";

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

    public static void sendToTelegram(String chat_id, String message){
        try {
            String mess = chat_id + "&text=" + message ;
            URL url = new URL(TELEGRAM_SEND_MESSAGE_URL.concat(mess));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.getInputStream();
            con.disconnect();
        } catch (IOException e) {
            System.out.println(message);
            System.out.println("Failed to send to Telegram");
            e.printStackTrace();
        }
    }
    private static void webhookSetup(URL url)throws IOException {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.getInputStream();
            con.disconnect();
        } catch (IOException e) {
            System.out.println("Error: failed to delete webhook.");
            e.printStackTrace();
        }
    }

    public static void deleteWebhook(){
        try {
            URL url = new URL(DEL_WEBHOOK_URL);
            webhookSetup(url);
        } catch (IOException e) {
            System.out.println("Error: failed to delete webhook.");
            e.printStackTrace();
        }
    }

    public static void setWebhook() {
        try {
            URL url = new URL(SET_WEBHOOK_URL);
            webhookSetup(url);
        } catch (IOException e) {
            System.out.println("Error: failed to set webhook");
            e.printStackTrace();
        }
    }


//    public static boolean hasUpdates() {
//        try {
//            URL url = new URL(WEBHOOK_INFO_URL);
//            Boolean hasMoreUpdates;
//            webhookSetup(url);
//            //get json and check that "pending_update_count" is zero.
//            //if not then return true
//            return true;
//        } catch (IOException e) {
//            System.out.println("Error: failed to check for updates");
//            e.printStackTrace();
//        }
//    }
}
