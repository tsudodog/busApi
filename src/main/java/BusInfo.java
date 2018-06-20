import TwinCitiesTransit.*;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class BusInfo {
    private final String BUS_URL = "http://svc.metrotransit.org/NexTrip/";
    private List<NextTripDepartures> departuresList;
    private int timeSinceLastReq = 0;
    //the nextTrips and nextInfo variables above store temporary information. If a request is made within the 30s
    // refresh of the metro transit system, then pointless to try to access new information again. Just pull old/temp
    //info from these stored variables.


    //makes an Http request with the input information such as stopid, route, and direction.\
    //returns stringbuffer that will be handled with (not yet named) method that will be sent to
    //telegram
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

    //used for testing JSON from Bus Api
    private void testJSON(StringBuffer str) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(str.toString());
        System.out.println(gson.toJson(jsonElement));
    }

    //use the GetRoutes operation
    public String getRoutes(){
        try {
            StringBuffer routes = makeHttpRequest("Routes?format=json");
//            testJSON(routes);
        } catch (IOException E) {
            System.out.println("Failure: getRoutes failed to open url");
        }
        //TODO
        return "";
    }

    //use GetDepartures operation
    //ONLY LIST NEXT 5 DEPARTURES!
    public String getDepartures(String stopID){
        try{
            String inStr = stopID + "?format=json";
            StringBuffer departs = makeHttpRequest(inStr);
            testJSON(departs);
        }catch(IOException E){
            System.out.println("Failure: getDepartures failed to open url");
        }
        //TODO
        return "";
    }

    public String getStops(String route, String directions) {
        try {
            String inStr = "Stops/" + route + "/" + directions + "?format=json";
            StringBuffer stops = makeHttpRequest(inStr);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArr = (jsonParser.parse(stops.toString())).getAsJsonArray();
            NextTrip[] tripArr = gson.fromJson(jsonArr, NextTrip[].class);
            List<NextTrip> nextTripList = Arrays.asList(tripArr);


        } catch (IOException E) {
            System.out.println("Failure: getStops failed to open URL");
        }
    //TODO
        return "";
    }


    public String getDepartureTimes(String route, String directions, String stopID) {
        try {
            String inStr = "Stops/" + route + "/" + directions + "/" + stopID + "?format=json";
            StringBuffer depTimes = makeHttpRequest(inStr);
//            testJSON(depTimes);
        } catch (IOException E) {
            System.out.println("Failure: getDepartureTimes failed to open URL");
        }

        //TODO
        return "";
    }

    private void setDepartures(List<NextTripDepartures> departures) {
        this.departuresList = departures;
    }



    private void setTimeSinceLastReq(int timeSinceLastReq) {
        this.timeSinceLastReq = timeSinceLastReq;
    }
}

