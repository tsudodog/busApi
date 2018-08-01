import TwinCitiesTransit.NextTripRoute;

import java.util.List;

import static spark.Spark.*;

public class MTBotMain {
    //inputType can be either stopID, route, routeDir, or routeDirStop
     public static String inputType;

    public static void main(String[] args) {
        get("/mtbotmain", (req, res) -> "Hello WorldDDDDDDDDD");
//        String outMess = Message.getMessage("routes", BusInfo.getRoutes());
//        System.out.println(Message.getMessage("routes", BusInfo.getRoutes()));
//        System.out.println(outMess);
//        List<NextTripRoute> ntr = BusInfo.getRoutes();
//        StringBuffer mess = new StringBuffer();
//        for (NextTripRoute r : (List<NextTripRoute>) ntr) {
//            mess.append("Route Num: " + r.getRoute() + "\t" + " Description: " + r.getDescription() + "\n");
//        }
//        System.out.println(mess.toString());
//        BusInfo bus = new BusInfo();
//        System.out.println(bus.getDepartures("17025"));
//        System.out.println(bus.getDepartureTimes("5", "4", "7SOL"));
//        System.out.println(bus.getStops("5", "4"));
//        System.out.println(bus.getRoutes());
//        System.out.println(bus.getDirections("5"));

        System.out.println(Message.getMessage("departures", BusInfo.getDepartures("17025")));

        /**
         * TODO:
         * -BusInfo will not be instantiated. Will only have working methods, will not store information.
         * -BusInfo methods will not handle messages, create messages class to handle packaging
         * -
         */
    }
}