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

/**
 * Created by Connor Hanlon on 6/19/2018
 *
 * BusInfo class interacts with MetroTransit API and stores temporary information as it is requested by user.
 *
 * MetroTransit API documentation can be found at http://svc.metrotransit.org/
 */
public class BusInfo {
    private final String BUS_URL = "http://svc.metrotransit.org/NexTrip/";
    private List<NextTripDepartures> departuresList;
    private List<NextTripRoute> routesList;
    private List<TextValuePair> directionsList;
    private List<TextValuePair> stopsList;


    /**
     * Created by Alex Zalewski on 6/19/2018.
     *
     * Makes a HTTP request, reads and returns information from url.
     *
     * @param inStr input url to request and read from.
     * @return json StringBuffer information to be handled with getter methods.
     * @throws IOException
     */
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


    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Method creates an object list of a desired type. If the input type is not recognized,
     * then null is returned.
     *
     * @param inStr the input string to be built into an object list
     * @param type used to determine which type of object list to construct
     * @return
     */
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
            return null;
        }
    }

    /**
     * Created by Connor Hanlon on 6/21/2018
     *
     * Formats a message as a string from a list of objects. If the type of message is not recognized,
     * then the return message defaults to failure to retreive.
     *
     * @param typeMess determines the type of responding message to create
     * @param busInfoList the list of objects to be utilized to construct return message
     * @return formatted message
     */
    private String getMessage(String typeMess, List busInfoList) {
        StringBuffer message = new StringBuffer();
        if (typeMess.equals("routes")) {
            for (NextTripRoute r : (List<NextTripRoute>)busInfoList) {
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


    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls directional information on the input route from Metro Transit API.
     *
     * @param route the route to pull information on and return to user
     * @return formatted directional message.
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

    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls general route information on all routes from Metro Transit API.
     *
     * @return formatted message displaying route number and the corresponding description of every route.
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


    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls the directional information on the desired input stop ID from Metro Transit API.
     *
     * @param stopID stop identification number used to pull info from api.
     * @return formatted departure message displaying route number, direction, and time of arrival.
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

    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls stop information on the desired route number and direction number from Metro Transit API.
     *
     * @param route route NUMBER  used to pull info from api.
     * @param directions direction used to pull from api. Must be the VALUE(1-4) as input and not the TEXT(NORTHBOUND,etc)
     * @return formatted stop information displaying the stop ID and the stop description.
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
        return message;
    }

    /**
     * Created by Connor Hanlon on 6/19/2018
     *
     * Pulls directional information on the desired input route number, direction number, and stop id number from
     * Metro Transit API.
     *
     * @param route route Number used to pull info from api.
     * @param directions direction used to pull from api. Must be the VALUE(1-4) as input and not the TEXT(NORTHBOUND,etc)
     * @param stopID stopid used to pull from api. Must be specific ID, which can be taken from a stop TextValuePair
     *               object using the VALUE method.
     * @return formatted departure message displaying route number, direction, and time of arrival.
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

