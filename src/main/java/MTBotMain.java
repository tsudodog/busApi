import static spark.Spark.*;

public class MTBotMain {
    //inputType can be either stopID, route, routeDir, or routeDirStop
     public static String inputType;

    public static void main(String[] args) {
        get("/mtbotmain", (req, res) -> "Hello WorldDDDDDDDDD");
        BusInfo bus = new BusInfo();
//        System.out.println(bus.getDepartures("17025"));
//        System.out.println(bus.getDepartureTimes("5", "4", "7SOL"));
//        System.out.println(bus.getStops("5", "4"));
//        System.out.println(bus.getRoutes());
        System.out.println(bus.getDirections("5"));


        /**
         * TODO: Create getDirection method in BusInfo class
         * TODO: Finish returns of getters route, departures, departuretimes,stops
         * TODO: handle timesincelastrequest not inside of businfo
         *      If time elapsed since last request is less than 30s, just
         *      obtain temporarily stored info from the bus object.
         */
    }
}