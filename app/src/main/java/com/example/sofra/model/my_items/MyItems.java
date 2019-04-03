
package com.example.sofra.model.my_items;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MyItems {

    @SerializedName("data")
    private MyItemsData mMyItemsData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public MyItemsData getData() {
        return mMyItemsData;
    }

    public void setData(MyItemsData myItemsData) {
        mMyItemsData = myItemsData;
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
