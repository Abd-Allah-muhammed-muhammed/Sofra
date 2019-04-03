
package com.example.sofra.model.comments_reviews;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("client")
    private Client mClient;
    @SerializedName("client_id")
    private String mClientId;
    @SerializedName("comment")
    private String mComment;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("rate")
    private String mRate;
    @SerializedName("restaurant")
    private Restaurant mRestaurant;
    @SerializedName("restaurant_id")
    private String mRestaurantId;
    @SerializedName("updated_at")
    private String mUpdatedAt;

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

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
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

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
