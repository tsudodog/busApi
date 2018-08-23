import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.telegram.telegrambots.api.objects.Update;
import static spark.Spark.*;

public class MTBotMain {

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
        port(9000);
        post("/mtbotmain", (req, res) -> {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Update update = gson.fromJson(req.body(), Update.class);
                    System.out.println(update.getMessage().getText());
                    String chatID = getChatID(update);
                    if (!update.hasMessage()) {
                        HandleRequest.sendToTelegram(chatID, "Error: no message received");
                    } else {
                        System.out.println("2");
                        System.out.println(chatID);
                        handleCommand(chatID, getText(update));
                    }

                    System.out.println("done");
                    System.out.println(req.headers());
                    res.status(200);
                    return res;
        }
        );

//        System.out.println(bus.getDepartures("17025"));
//        System.out.println(bus.getDepartureTimes("5", "4", "7SOL"));
//        System.out.println(bus.getStops("5", "4"));
//        System.out.println(bus.getRoutes());
//        System.out.println(bus.getDirections("5"));

    }



    private static void handleCommand(String chatID, String text) {
        String[] reqArr = text.split("\\s+");
        String cmd = reqArr[0];
        String retMess;
        switch (cmd) {
            case "/departures":
                retMess = getDepartures(reqArr);
                break;
            case "/departuretimes":
                retMess = getDepartureTimes(reqArr);
                break;
            case "/routes":
                retMess = getRoutes();
                break;
            case "/directions":
                retMess = getDirections(reqArr);
                break;
            case "/stops":
                retMess = getStops(reqArr);
                break;
            default:
                retMess = "Unrecognized command, please resend command with appropriate inputs";
        }
        String[] lines = retMess.split("\n", 6);
//        for (int i = 0; i < 5; i++) {
//            System.out.println(lines[1]);
//            HandleRequest.sendToTelegram(chatID, lines[i]);
//        }
        HandleRequest.sendToTelegram(chatID, lines[1]);

    }

    private static String getDepartures(String[] reqArr) {
        if (reqArr.length < 2) {
            return  "The Stop ID is required to return departures. Please resend command with a Stop ID";
        } else {
            String message = Message.getDepartureMessage(BusInfo.getDepartures(reqArr[1]));
            return message;
        }
    }

    private static String getDepartureTimes(String[] reqArr) {
        if (reqArr.length < 4) {
           return "Not enough information provided. Please resend command with Route, Direction, and Stop ID.";
        } else {
            String message = Message.getDepartureMessage(BusInfo.getDepartureTimes(reqArr[1], reqArr[2], reqArr[3]));
            return message;
        }
    }

    private static String getRoutes() {
        String message = Message.getRouteMessage(BusInfo.getRoutes());
        return message;
    }

    private static String getDirections(String[] reqArr) {
        if (reqArr.length < 2) {
            return "Not enough information provided. Please resend command with Route.";
        } else {
            String message = Message.getDirectionMessage(BusInfo.getDirections(reqArr[1]));
            return message;
        }
    }

    private static String getStops(String[] reqArr) {
        if (reqArr.length < 3) {
           return "Not enough information provided. Please resend command with Route and Direction.";
        } else {
            String message = Message.getStopMessage(BusInfo.getStops(reqArr[1], reqArr[2]));
            return message;
        }
    }


    private static String getText(Update update) {
        return update.getMessage().getText();
    }

    private static String getChatID(Update update) {
        return update.getMessage().getChatId().toString();
    }


}