import TwinCitiesTransit.*;
import com.google.gson.*;

import javax.xml.soap.Text;
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
    //the variables above store temporary info. If a request is made within the 30s
    // refresh of the metro transit system, then pointless to try to access new information again. Just pull old/temp
    //info from these stored variables.


    //makes an Http request with the input information such as stopid, route, and direction.\
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


    //returns a list of specified objects with the input stringbuffer
    private List getNextTripArray(StringBuffer inStr, String type) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (jsonParser.parse(inStr.toString())).getAsJsonArray();
        if (type.equals("stopOrDir")) {
            TextValuePair[] stopsArr = gson.fromJson(jsonArray, TextValuePair[].class);
            List<TextValuePair> nextStopList = Arrays.asList(stopsArr);
            return nextStopList;
        } else if (type.equals("departure")) {
            NextTripDepartures[] departuresArr = gson.fromJson(jsonArray, NextTripDepartures[].class);
            List<NextTripDepartures> nextTripDepartures = Arrays.asList(departuresArr);
            return nextTripDepartures;
        } else if (type.equals("routes")) {
            NextTripRoute[] routeArr = gson.fromJson(jsonArray, NextTripRoute[].class);
            List<NextTripRoute> nextTripRoutes = Arrays.asList(routeArr);
            return nextTripRoutes;
        } else {
            //throw some error or print error message
            return null;
        }
    }

    private String getMessage(String typeMess, List busInfoList) {
        StringBuffer message = new StringBuffer();
        if (typeMess.equals("routes")) {
            for (NextTripRoute r : (List<NextTripRoute>)busInfoList) {
//                String tester = "Route Number: " + r.getRoute() + " Description: " + r.getDescription() + "\n";
                message.append("Route Number: " + r.getRoute() + "\t" +  " Description: " + r.getDescription() + "\n");
            }
        } else if (typeMess.equals("departures")) {
            for (NextTripDepartures d : (List<NextTripDepartures>) busInfoList) {
                message.append("Route Number: " + d.getRoute() + "\t" + " Direction: " + d.getRouteDirection() + "\t" + " Time of Arrival: " + d.getDepartureText() + "\n");
            }
        } else if (typeMess.equals("stop")) {
            for (TextValuePair stop : (List<TextValuePair>) busInfoList) {
                message.append(" Stop ID: " + stop.getValue() + "\t" + "Stop name: " + stop.getText() +"\n");
            }
        } else if (typeMess.equals("direction")) {
            for (TextValuePair dir : (List<TextValuePair>) busInfoList) {
                message.append("Direction: " + dir.getText() + " - " + dir.getValue() + "\n");
            }
        } else {
            message.append("Failed to retrieve message, invalid type of message");
        }
        return message.toString();
    }

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
     * Also must add a string to return!
     */
    public String getDirections(String route) {
        String message = "Failed to get directions";
        try {
            String inStr = "Directions/" + route + "?format=json";
            StringBuffer dirs = makeHttpRequest(inStr);
            List<TextValuePair> directionArr = getNextTripArray(dirs, "stopOrDir");
            setDirectionsList(directionArr);

            message = getMessage("direction", directionsList);
        } catch (IOException e) {
            System.out.println("Failure: getDirections failed to open URL.");
            e.printStackTrace();
        }
        return message;
    }

    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     * Also must add a string to return!
     */
    public String getRoutes(){
        String message = " Failed to get routes";
        try {
            StringBuffer routes = makeHttpRequest("Routes?format=json");
            List<NextTripRoute> nextRoutes = getNextTripArray(routes, "routes");
            setRoutesList(nextRoutes);

            message = getMessage("routes", nextRoutes);
        } catch (IOException e) {
            System.out.println("Failure: getRoutes failed to open url");
            e.printStackTrace();
        }
        return message;
    }


    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     * ONLY LIST NEXT 5 DEPARTURES!
     *
     * Also must add a string to return!
     */
    public String getDepartures(String stopID){
        String message = "Failed to get departures";
        try{
            String inStr = stopID + "?format=json";
            StringBuffer departs = makeHttpRequest(inStr);
            List<NextTripDepartures> nextTripDepartures = getNextTripArray(departs, "departure");
            setDeparturesList(nextTripDepartures);

            message = getMessage("departures", nextTripDepartures);
        }catch(IOException e){
            System.out.println("Failure: getDepartures failed to open URL.");
            e.printStackTrace();
        }
        return message;
    }

    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     * Also must add a string to return!
     */
    public String getStops(String route, String directions) {
        String message = "Failed to get stops";
        try {
            String inStr = "Stops/" + route + "/" + directions + "?format=json";
            StringBuffer stops = makeHttpRequest(inStr);
            List<TextValuePair> nextStopList = getNextTripArray(stops, "stopOrDir");
            setStopsList(nextStopList);

            message = getMessage("stop", nextStopList);
        } catch (IOException e) {
            System.out.println("Failure: getStops failed to open URL.");
            e.printStackTrace();
        }
    //TODO
        return message;
    }

    /**  TODO: check the time since last request if time is greater than or equal to 30 seconds,
     *  then send request.
     *
     *  else pull from local information.
     *
     */
    public String getDepartureTimes(String route, String directions, String stopID) {
        String message = "Failed to get departure times.";
        try {
            String inStr = route + "/" + directions + "/" + stopID + "?format=json";
            StringBuffer depTimes = makeHttpRequest(inStr);
            List<NextTripDepartures> nextTripDepartures = getNextTripArray(depTimes, "departure");
            setDeparturesList(nextTripDepartures);

            message = getMessage("departures", nextTripDepartures);
        } catch (IOException e) {
            System.out.println("Failure: getDepartureTimes failed to open URL.");
            e.printStackTrace();
        }
        return message;
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
}

