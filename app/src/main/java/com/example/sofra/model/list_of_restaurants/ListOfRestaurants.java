
package com.example.sofra.model.list_of_restaurants;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ListOfRestaurants {

    @SerializedName("data")
    private ListOfRestaurantsData mListOfRestaurantsData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public ListOfRestaurantsData getData() {
        return mListOfRestaurantsData;
    }

    public void setData(ListOfRestaurantsData listOfRestaurantsData) {
        mListOfRestaurantsData = listOfRestaurantsData;
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
