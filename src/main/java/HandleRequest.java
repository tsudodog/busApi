import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleRequest {
    private static final String BUS_URL = "http://svc.metrotransit.org/NexTrip/";

        /**
     * Created by Alex Zalewski on 6/19/2018.
     *
     * Makes a HTTP request, reads and returns information from url.
     *
     * @param inStr input url to request and read from.
     * @return json StringBuffer information to be handled with getter methods.
     * @throws IOException
     */
    public static StringBuffer makeHttpRequest(String inStr) throws IOException {
        URL url = new URL(BUS_URL.concat(inStr));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
}
