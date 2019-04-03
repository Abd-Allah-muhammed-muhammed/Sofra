
package com.example.sofra.model.restaurant_login;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RestaurantLogin {

    @SerializedName("data")
    private ResraurantLoginData mResraurantLoginData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public ResraurantLoginData getData() {
        return mResraurantLoginData;
    }

    public void setData(ResraurantLoginData resraurantLoginData) {
        mResraurantLoginData = resraurantLoginData;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
