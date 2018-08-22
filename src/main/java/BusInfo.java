import TwinCitiesTransit.*;
import com.google.gson.*;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Connor Hanlon on 6/19/2018
 *
 * BusInfo class interacts with MetroTransit API and stores temporary information as it is requested by user.
 *
 * MetroTransit API documentation can be found at http://svc.metrotransit.org/
 */
public class BusInfo {
//    private static final String BUS_URL = "http://svc.metrotransit.org/NexTrip/";
//
//
//    /**
//     * Created by Alex Zalewski on 6/19/2018.
//     *
//     * Makes a HTTP request, reads and returns information from url.
//     *
//     * @param inStr input url to request and read from.
//     * @return json StringBuffer information to be handled with getter methods.
//     * @throws IOException
//     */
//    private static StringBuffer makeHttpRequest(String inStr) throws IOException {
//        URL url = new URL(BUS_URL.concat(inStr));
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer retString = new StringBuffer();
//        while ((inputLine = input.readLine()) != null) {
//            retString.append(inputLine);
//        }
//        input.close();
//        return retString;
//    }



    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls directional information on the input route from Metro Transit API.
     *
     * @param route the route to pull information on and return to user
     * @return TextValuePair List that describes bus directions.
     */
    public static List<TextValuePair> getDirections(String route) {
        try {
            String inStr = "Directions/" + route + "?format=json";
            StringBuffer dirs = HandleRequest.makeBusHttpRequest(inStr);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (jsonParser.parse(dirs.toString())).getAsJsonArray();
            TextValuePair[] dirArray= gson.fromJson(jsonArray, TextValuePair[].class);
            return Arrays.asList(dirArray);

        } catch (IOException e) {
            System.out.println("Failure: getDirections failed to open URL.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls general route information on all routes from Metro Transit API.
     *
     * @return List containing route number and description of every route.
     */
    public static List<NextTripRoute> getRoutes(){
        try {
            StringBuffer routes = HandleRequest.makeBusHttpRequest("Routes?format=json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (jsonParser.parse(routes.toString())).getAsJsonArray();
            NextTripRoute[] routeArr = gson.fromJson(jsonArray, NextTripRoute[].class);
            return Arrays.asList(routeArr);
        } catch (IOException e) {
            System.out.println("Failure: getRoutes failed to open url");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls the directional information on the desired input stop ID from Metro Transit API.
     *
     * @param stopID stop identification number used to pull info from api.
     * @return List containing route number, direction, and time of arrival information.
     */
    public static List<NextTripDepartures> getDepartures(String stopID){
        try{
            String inStr = stopID + "?format=json";
            StringBuffer departs = HandleRequest.makeBusHttpRequest(inStr);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (jsonParser.parse(departs.toString())).getAsJsonArray();
            NextTripDepartures[] departuresArr = gson.fromJson(jsonArray, NextTripDepartures[].class);
            return Arrays.asList(departuresArr);
        }catch(IOException e){
            System.out.println("Failure: getDepartures failed to open URL.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls stop information on the desired route number and direction number from Metro Transit API.
     *
     * @param route route NUMBER  used to pull info from api.
     * @param directions direction used to pull from api. Must be the VALUE(1-4) as input and not the TEXT(NORTHBOUND,etc)
     * @return List containing stop information displaying the stop ID and the stop description.
     */
    public static List<TextValuePair> getStops(String route, String directions) {
        try {
            String inStr = "Stops/" + route + "/" + directions + "?format=json";
            StringBuffer stops = HandleRequest.makeBusHttpRequest(inStr);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (jsonParser.parse(stops.toString())).getAsJsonArray();
            TextValuePair[] stopsArr = gson.fromJson(jsonArray, TextValuePair[].class);
            return Arrays.asList(stopsArr);
        } catch (IOException e) {
            System.out.println("Failure: getStops failed to open URL.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls directional information on the desired input route number, direction number, and stop id number from
     * Metro Transit API.
     *
     * @param route      route Number used to pull info from api.
     * @param directions direction used to pull from api. Must be the VALUE(1-4) as input and not the TEXT(NORTHBOUND,etc)
     * @param stopID     stopid used to pull from api. Must be specific ID, which can be taken from a stop TextValuePair
     *                   object using the VALUE method.
     * @return List containing departure message displaying route number, direction, and time of arrival information.
     */
    public static List<NextTripDepartures> getDepartureTimes(String route, String directions, String stopID) {
        try {
            String inStr = route + "/" + directions + "/" + stopID + "?format=json";
            StringBuffer depTimes = HandleRequest.makeBusHttpRequest(inStr);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (jsonParser.parse(depTimes.toString())).getAsJsonArray();
            NextTripDepartures[] departuresArr = gson.fromJson(jsonArray, NextTripDepartures[].class);
            return Arrays.asList(departuresArr);
        } catch (IOException e) {
            System.out.println("Failure: getDepartureTimes failed to open URL.");
            e.printStackTrace();
        }
        return null;
    }
}

