package TwinCitiesTransit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alex.zalewski on 6/19/18.
 *
 * Handles response schema 1 with GetDepartures and GetTimePointDepartures operations from
 * metro transit api.
 *
 * URL: http://svc.metrotransit.org/
 *
 */
public class NextTripDepartures {
    @SerializedName("Actual")
    boolean actual;

    @SerializedName("BlockNumber")
    long blockNumber;

    @SerializedName("DepartureText")
    String departureText;

    @SerializedName("DepartureTime")
    String departureTime;

    @SerializedName("Description")
    String description;

    @SerializedName("Gate")
    String gate;

    @SerializedName("Route")
    String route;

    @SerializedName("RouteDirection")
    String routeDirection;

    @SerializedName("Terminal")
    String terminal;

    @SerializedName("VehicleHeading")
    String vehicleHeading;

    @SerializedName("VehicleLatitude")
    double vehicleLatitude;

    @SerializedName("VehicleLongitude")
    double vehicleLongitude;

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getDepartureText() {
        return departureText;
    }

    public void setDepartureText(String departureText) {
        this.departureText = departureText;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteDirection() {
        return routeDirection;
    }

    public void setRouteDirection(String routeDirection) {
        this.routeDirection = routeDirection;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getVehicleHeading() {
        return vehicleHeading;
    }

    public void setVehicleHeading(String vehicleHeading) {
        this.vehicleHeading = vehicleHeading;
    }

    public double getVehicleLatitude() {
        return vehicleLatitude;
    }

    public void setVehicleLatitude(double vehicleLatitude) {
        this.vehicleLatitude = vehicleLatitude;
    }

    public double getVehicleLongitude() {
        return vehicleLongitude;
    }

    public void setVehicleLongitude(double vehicleLongitude) {
        this.vehicleLongitude = vehicleLongitude;
    }


}
