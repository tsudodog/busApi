import TwinCitiesTransit.*;

import java.util.List;

public class Message {

    /**
     * Created by Connor Hanlon on 6/21/2018
     *
     * Formats a message as a string from a list of objects. If the type of message is not recognized,
     * then the return message defaults to failure to retreive.
     *
     * @param typeMess determines the type of responding message to create
     * @param busInfoList the list of objects to be utilized to construct return message
     * @return type of return message described below-
     *      Routes: formatted message displaying route number and the corresponding description of every route.
     *
     *      Departures: reformatted departure message displaying route number, direction, and time of arrival.
     *
     *      Stops: formatted stop information displaying the stop ID and the stop description.
     *
     *      Direction: formatted directional information.
     */
//    public static String getMessage(String typeMess, List busInfoList) {
//        StringBuffer message = new StringBuffer();
//        if (typeMess.equals("routes")) {
//
//        } else if (typeMess.equals("departures")) {
//
//        } else if (typeMess.equals("stop")) {
//
//        } else if (typeMess.equals("direction")) {
//
//        } else {
//            message.append("Failed to retrieve message, invalid type of message");
//        }
//        return message.toString();
//    }

    public static String getDepartureMessage(List<NextTripDepartures> busList) {
        StringBuffer message = new StringBuffer();
        for (NextTripDepartures d : busList) {
            message.append("Route Number: " + d.getRoute() + "\t" + " Direction: " + d.getRouteDirection() + "\t" + " Time of Arrival: " + d.getDepartureText() + "\n");
        }
        return message.toString();
    }

    public static String getRouteMessage(List<NextTripRoute> busList) {
        StringBuffer message = new StringBuffer();
        for (NextTripRoute r : busList) {
            message.append("Route Number: " + r.getRoute() + "\t" +  " Description: " + r.getDescription() + "\n");
        }
        return message.toString();
    }

    public static String getStopMessage(List<TextValuePair> busList) {
        StringBuffer message = new StringBuffer();
        for (TextValuePair stop : busList) {
            message.append(" Stop ID: " + stop.getValue() + "\t" + "Stop name: " + stop.getText() +"\n");
        }
        return message.toString();
    }

    public static String getDirectionMessage(List<TextValuePair> busList) {
        StringBuffer message = new StringBuffer();
        for (TextValuePair dir : busList) {
            message.append("Direction: " + dir.getText() + " - " + dir.getValue() + "\n");
        }
        return message.toString();
    }

}
