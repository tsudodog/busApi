import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static spark.Spark.*;

public class HelloWorld {

    public static void main(String[] args) throws IOException {
        makeHttpRequest();


        get("/hello", (req, res) -> "Hello WorldDDDDDDDDD");

        post("/thisIsAPost", (req,res)-> {
            String o = req.attribute("bannerid");

            System.out.println("I got a post");
            return res;
        });
    }

    /**
     *  check out this website for useful info regarding http requests in java
     *  http://www.baeldung.com/java-http-request
     */
    public static final String SAMPLE_URL = "http://svc.metrotransit.org/NexTrip/17025?format=json";
    public static void makeHttpRequest() throws IOException {
        URL url = new URL(SAMPLE_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer payload = new StringBuffer();
        while ((inputLine = input.readLine()) != null) {
            payload.append(inputLine);

        }
        input.close();
        System.out.println(payload);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(payload.toString());
        System.out.println(gson.toJson(jsonElement));

    }
}
