
package com.example.sofra.model.status;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("activated")
    private String mActivated;
    @SerializedName("availability")
    private String mAvailability;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("delivery_cost")
    private String mDeliveryCost;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("minimum_charger")
    private String mMinimumCharger;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("photo_url")
    private String mPhotoUrl;
    @SerializedName("rate")
    private Long mRate;
    @SerializedName("region_id")
    private String mRegionId;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("whatsapp")
    private String mWhatsapp;

    public String getActivated() {
        return mActivated;
    }

    public void setActivated(String activated) {
        mActivated = activated;
    }

    public String getAvailability() {
        return mAvailability;
    }

    public void setAvailability(String availability) {
        mAvailability = availability;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDeliveryCost() {
        return mDeliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        mDeliveryCost = deliveryCost;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMinimumCharger() {
        return mMinimumCharger;
    }

    public void setMinimumCharger(String minimumCharger) {
        mMinimumCharger = minimumCharger;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
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

    public Long getRate() {
        return mRate;
    }

    public void setRate(Long rate) {
        mRate = rate;
    }

    public String getRegionId() {
        return mRegionId;
    }

    public void setRegionId(String regionId) {
        mRegionId = regionId;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getWhatsapp() {
        return mWhatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        mWhatsapp = whatsapp;
    }

}
