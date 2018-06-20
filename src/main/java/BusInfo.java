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
    private List<NextTripRoute> routesList;
    private List<TextValuePair> directionsList;
    private List<TextValuePair> stopsList;
    private int timeSinceLastRequest = 0;
    //the variables above store temporary info. If a request is made within the 30s
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

    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     */
    public String getRoutes(){
        try {
            StringBuffer routes = makeHttpRequest("Routes?format=json");
        } catch (IOException E) {
            System.out.println("Failure: getRoutes failed to open url");
        }
        //TODO
        return "";
    }


    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     * ONLY LIST NEXT 5 DEPARTURES!
     */
    public String getDepartures(String stopID){
        try{
            String inStr = stopID + "?format=json";
            StringBuffer departs = makeHttpRequest(inStr);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (jsonParser.parse(departs.toString())).getAsJsonArray();
            NextTripDepartures[] departuresArr = gson.fromJson(jsonArray, NextTripDepartures[].class);
            List<NextTripDepartures> nextTripDepartures = Arrays.asList(departuresArr);
            setDeparturesList(nextTripDepartures);

            nextTripDepartures.stream().forEach(dt -> System.out.println(dt.getDescription() + "\t" + dt.getDepartureText() + "\n"));
        }catch(IOException E){
            System.out.println("Failure: getDepartures failed to open url");
        }
        //TODO
        return "";
    }

    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     */
    public String getStops(String route, String directions) {
        try {
            String inStr = "Stops/" + route + "/" + directions + "?format=json";
            StringBuffer stops = makeHttpRequest(inStr);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArr = (jsonParser.parse(stops.toString())).getAsJsonArray();
            TextValuePair[] stopsArr = gson.fromJson(jsonArr, TextValuePair[].class);
            List<TextValuePair> nextStopList = Arrays.asList(stopsArr);
            setStopsList(nextStopList);

            nextStopList.stream().forEach(nt -> System.out.println(nt.getText() + "\t" + nt.getValue() + "\n"));
        } catch (IOException e) {
            System.out.println("Failure: getStops failed to open URL");
            e.printStackTrace();
        }
    //TODO
        return "";
    }

    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     */
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

    public List<NextTripDepartures> getDeparturesList() {
        return departuresList;
    }

    public void setDeparturesList(List<NextTripDepartures> departuresList) {
        this.departuresList = departuresList;
    }

    public List<NextTripRoute> getRoutesList() {
        return routesList;
    }

    public void setRoutesList(List<NextTripRoute> routesList) {
        this.routesList = routesList;
    }

    public List<TextValuePair> getDirectionsList() {
        return directionsList;
    }

    public void setDirectionsList(List<TextValuePair> directionsList) {
        this.directionsList = directionsList;
    }

    public List<TextValuePair> getStopsList() {
        return stopsList;
    }

    public void setStopsList(List<TextValuePair> stopsList) {
        this.stopsList = stopsList;
    }

    public int getTimeSinceLastRequest() {
        return timeSinceLastRequest;
    }

    public void setTimeSinceLastRequest(int timeSinceLastRequest) {
        this.timeSinceLastRequest = timeSinceLastRequest;
    }
}

