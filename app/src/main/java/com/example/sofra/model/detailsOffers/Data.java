
package com.example.sofra.model.detailsOffers;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("available")
    private Boolean mAvailable;
    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("ending_at")
    private String mEndingAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("restaurant")
    private Restaurant mRestaurant;
    @SerializedName("restaurant_id")
    private String mRestaurantId;
    @SerializedName("starting_at")
    private String mStartingAt;
    @SerializedName("updated_at")
    private Object mUpdatedAt;

    public Boolean getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Boolean available) {
        mAvailable = available;
    }

    public Object getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Object createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getEndingAt() {
        return mEndingAt;
    }

    public void setEndingAt(String endingAt) {
        mEndingAt = endingAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
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

    public String getStartingAt() {
        return mStartingAt;
    }

    public void setStartingAt(String startingAt) {
        mStartingAt = startingAt;
    }

    public Object getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
