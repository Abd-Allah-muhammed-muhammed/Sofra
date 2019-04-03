
package com.example.sofra.model.restayrant_order;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("client")
    private Client mClient;
    @SerializedName("client_id")
    private String mClientId;
    @SerializedName("commission")
    private String mCommission;
    @SerializedName("cost")
    private String mCost;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("delivered_at")
    private Object mDeliveredAt;
    @SerializedName("delivery_cost")
    private String mDeliveryCost;
    @SerializedName("id")
    private Long mId;
    @SerializedName("items")
    private List<Object> mItems;
    @SerializedName("net")
    private String mNet;
    @SerializedName("note")
    private String mNote;
    @SerializedName("payment_method_id")
    private String mPaymentMethodId;
    @SerializedName("restaurant")
    private Restaurant mRestaurant;
    @SerializedName("restaurant_id")
    private String mRestaurantId;
    @SerializedName("state")
    private String mState;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Client getClient() {
        return mClient;
    }

    public void setClient(Client client) {
        mClient = client;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getCommission() {
        return mCommission;
    }

    public void setCommission(String commission) {
        mCommission = commission;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        mCost = cost;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Object getDeliveredAt() {
        return mDeliveredAt;
    }

    public void setDeliveredAt(Object deliveredAt) {
        mDeliveredAt = deliveredAt;
    }

    public String getDeliveryCost() {
        return mDeliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        mDeliveryCost = deliveryCost;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<Object> getItems() {
        return mItems;
    }

    public void setItems(List<Object> items) {
        mItems = items;
    }

    public String getNet() {
        return mNet;
    }

    public void setNet(String net) {
        mNet = net;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getPaymentMethodId() {
        return mPaymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        mPaymentMethodId = paymentMethodId;
    }

    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;
    }

    public String getRestaurantId() {
        return mRestaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        mRestaurantId = restaurantId;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
