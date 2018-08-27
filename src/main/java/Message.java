import TwinCitiesTransit.*;

import java.util.List;

public class Message {

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
