
package com.example.sofra.model.general.list_of_cities.list_of_region;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ListOfRegion {

    @SerializedName("data")
    private ListOfRegionData mListOfRegionData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public ListOfRegionData getData() {
        return mListOfRegionData;
    }

    public void setData(ListOfRegionData listOfRegionData) {
        mListOfRegionData = listOfRegionData;
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
