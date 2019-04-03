
package com.example.sofra.model.my_order;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Pivot {

    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("note")
    private String mNote;
    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("quantity")
    private String mQuantity;

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

}
