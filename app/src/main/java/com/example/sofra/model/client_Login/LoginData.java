
package com.example.sofra.model.client_Login;

import com.example.sofra.model.ClintUser;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginData {

    @SerializedName("api_token")
    private String mApiToken;
    @SerializedName("user")
    private ClintUser mClintUser;

    public String getApiToken() {
        return mApiToken;
    }

    public void setApiToken(String apiToken) {
        mApiToken = apiToken;
    }

    public ClintUser getUser() {
        return mClintUser;
    }

    public void setUser(ClintUser loginClintUser) {
        mClintUser = loginClintUser;
    }

}
