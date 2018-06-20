package TwinCitiesTransit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Connor Hanlon on 6/20/2018
 */
public class NextTripDirections {
    @SerializedName("Text")
    String text;

    @SerializedName("Value")
    String value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
