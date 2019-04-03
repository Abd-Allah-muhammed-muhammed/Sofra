
package com.example.sofra.model.restaurant_login;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResraurantLoginData {

    @SerializedName("api_token")
    private String mApiToken;
    @SerializedName("user")
    private ResraurantUser mResraurantUser;

    public String getApiToken() {
        return mApiToken;
    }

    public void setApiToken(String apiToken) {
        mApiToken = apiToken;
    }

    public ResraurantUser getUser() {
        return mResraurantUser;
    }

    public void setUser(ResraurantUser resraurantUser) {
        mResraurantUser = resraurantUser;
    }

}
