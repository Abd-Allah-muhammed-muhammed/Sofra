
package com.example.sofra.model.complete_order;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("order")
    private Order mOrder;

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
    }

}
