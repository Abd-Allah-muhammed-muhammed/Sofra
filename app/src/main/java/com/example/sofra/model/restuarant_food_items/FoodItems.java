
package com.example.sofra.model.restuarant_food_items;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class FoodItems {

    @SerializedName("data")
    private Data mData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
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
