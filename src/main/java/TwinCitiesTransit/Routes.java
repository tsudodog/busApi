package TwinCitiesTransit;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Connor Hanlon on 6/20/2018
 */
public class Routes {
    @SerializedName("Description")
    String description;

    @SerializedName("ProviderID")
    String provider;

    @SerializedName("Route")
    String route;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
