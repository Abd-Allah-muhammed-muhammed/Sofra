
package com.example.sofra.model.general.list_of_cities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ListOfCities {

    @SerializedName("data")
    private ListOfCitiesData mListOfCitiesData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public ListOfCitiesData getData() {
        return mListOfCitiesData;
    }

    public void setData(ListOfCitiesData listOfCitiesData) {
        mListOfCitiesData = listOfCitiesData;
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
