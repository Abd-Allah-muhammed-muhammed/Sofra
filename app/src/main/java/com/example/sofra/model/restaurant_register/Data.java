
package com.example.sofra.model.restaurant_register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("data")
    @Expose
    private RestaurantRegestrData data;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public RestaurantRegestrData getData() {
        return data;
    }

    public void setData(RestaurantRegestrData data) {
        this.data = data;
    }

}
