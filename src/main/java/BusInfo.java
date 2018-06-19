public class BusInfo {
    private String departureTime;
    private String routeNum;
    private String direction;
    private String stopID;


    public BusInfo(){
        setDepartureTime("unknown departure time");
    }

//    needs routeNum, direction, and timepoint stop
//    url: http://svc.metrotransit.org/NexTrip/{ROUTE}/{DIRECTION}/{STOP}
//     to get json
//     the above returns array of nextripdeparture elements
    public String getDepartureTime() {

        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setRouteNum(String routeNum) {
        this.routeNum = routeNum;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }
}

