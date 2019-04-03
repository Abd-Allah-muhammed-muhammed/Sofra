
package com.example.sofra.model.update_items;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("photo_url")
    private String mPhotoUrl;
    @SerializedName("preparing_time")
    private String mPreparingTime;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("restaurant_id")
    private String mRestaurantId;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public String getPreparingTime() {
        return mPreparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        mPreparingTime = preparingTime;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
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
