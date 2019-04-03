
package com.example.sofra.model.client_register;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegisterClint {

    @SerializedName("data")
    private RegisterClientData mRegisterClientData;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Long mStatus;

    public RegisterClientData getData() {
        return mRegisterClientData;
    }

    public void setData(RegisterClientData registerClientData) {
        mRegisterClientData = registerClientData;
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
