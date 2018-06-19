import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BusInfo {
    public final String BUS_URL = "http://svc.metrotransit.org/NexTrip/";
    private String departureTime;
    private String routeNum;
    private String direction;
    private String stopID;

    //BusInfo interacts with metrotransit api to get and store bus information
    public BusInfo(){
        setDepartureTime("unknown departure time");
    }

    private StringBuffer makeHttpRequest(String inStr) throws IOException {
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

    //use the GetRoutes operation
    public String getRoutes(){
        //TODO
        return "";
    }

    //use GetDepartures operation
    //ONLY LIST NEXT 5 DEPARTURES!
    public String getDepartures(String stopID){
        try{

            StringBuffer departs = makeHttpRequest(stopID);
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            JsonParser jsonParser = new JsonParser();
//            JsonElement jsonElement = jsonParser.parse(departs.toString());
//            System.out.println(gson.toJson(jsonElement));
        }catch(IOException E){
            System.out.println("failed open");
        }
        //TODO
        return "";
    }

    public String getStops(String route, String directions) {
        //TODO
        return "";
    }

    public String getDepartureTimes(String route, String directions, String stopID) {
        //TODO
        return "";
    }

//    //only used within class, should have no external interference
    private  void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }


}

