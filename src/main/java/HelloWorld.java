import TwinCitiesTransit.NextTrip;
import com.google.common.collect.Lists;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

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
        // use jsonParser to parse text to JsonArray Object, jsonParser returns JsonElement, JsonElement has a getAsJsonArray() method
        JsonArray jsonArray = (jsonParser.parse(payload.toString())).getAsJsonArray();
        // now use gson to convert JSON into POJO (Plain old Java Object)
        NextTrip[] arrayOfTrips = gson.fromJson(jsonArray, NextTrip[].class);
        // I like lambdas take dumb array to List type
        List<NextTrip> nextTripList = Arrays.asList(arrayOfTrips);

        //now you can use the information as a java object if you want, I made the date a string because idk how to serialize it off the top of my head
        // something to note for later
        // normally in the NextTrip.java file you dont have to do the @SerializedName annotation for every field
        //but since the api capitalizes their first letter and that is not java convention i decided to keep coding convention
        //in favour of ease of programming
        nextTripList.stream().forEach(nt -> System.out.println(nt.getDescription() + "\t" + nt.getDepartureText() + "\n"));

    }
}
