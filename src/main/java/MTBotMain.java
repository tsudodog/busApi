import static spark.Spark.*;

public class MTBotMain {
    //inputType can be either stopID, route, routeDir, or routeDirStop
     public static String inputType;

    public static void main(String[] args) {
        get("/mtbotmain", (req, res) -> "Hello WorldDDDDDDDDD");
        BusInfo bus = new BusInfo();
        bus.getDepartures("17025");
        bus.getStops("5", "4");
        bus.getDepartureTimes("5", "4","7SOL");
        bus.getRoutes();
//        bus.getDirection();

        /**
         * TODO: Create getDirection method in BusInfo class
         * TODO: Finish returns of getters route, departures, departuretimes,stops
         */
    }
}