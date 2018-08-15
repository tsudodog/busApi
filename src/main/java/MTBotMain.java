import TwinCitiesTransit.NextTripRoute;
//import com.sun.java.util.jar.pack.Instruction;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.List;

import static spark.Spark.*;

public class MTBotMain {
    //inputType can be either stopID, route, routeDir, or routeDirStop
     public static String inputType;

    /**
     * Bot Name: TCMetroTransitBot
     * link: t.me/TCMetroTransitBot
     *
     * Use this token to access HTTP API:
     * 526452962:AAHN2Eu_oCVHevipOgearrFLRMCt-jOPYjA
     * 
     * @param args
     */
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

//        System.out.println(Message.getMessage("departures", BusInfo.getDepartures("17025")));
//        System.out.println(TelegramInfo.getUpMess());
        System.out.println(messageToTelegram(TelegramInfo.getUpMess()));

    }

    private static String messageToTelegram(String upMessage) {
        String[] messArr = upMessage.split("\\s+");
        String command = messArr[0];
        switch (command) {
            case "/departures":
                if (messArr.length < 2) {
                    return "The Stop ID is required to return departures. Please resend command with a Stop ID";
                } else {
                    return Message.getMessage("departures", BusInfo.getDepartures(messArr[1]));
                }
            case "/departuretimes":
                if (messArr.length < 4) {
                    return "Not enough information provided. Please resend command with Route, Direction, and Stop ID.";
                } else {
                    return Message.getMessage("departures", BusInfo.getDepartureTimes(messArr[1], messArr[2], messArr[3]));
                }
            case "/routes":
                return Message.getMessage("routes", BusInfo.getRoutes());
            case "/directions":
                if (messArr.length < 2) {
                    return "Not enough information provided. Please resend command with Route.";
                } else {
                    return Message.getMessage("direction", BusInfo.getDirections(messArr[1]));
                }
            case "/stops":
                if (messArr.length < 3) {
                    return " Not enough information provided. Please resend command with Route and Direction.";
                } else {
                    return Message.getMessage("stop", BusInfo.getStops(messArr[1], messArr[2]));
                }
            default: return "Unrecognized command, please resend command with appropriate inputs";
        }
    }
}