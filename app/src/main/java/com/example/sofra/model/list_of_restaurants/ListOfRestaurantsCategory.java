
package com.example.sofra.model.list_of_restaurants;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ListOfRestaurantsCategory {

    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("pivot")
    private ListOfRestaurantsPivot mListOfRestaurantsPivot;
    @SerializedName("updated_at")
    private Object mUpdatedAt;

    public Object getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Object createdAt) {
        mCreatedAt = createdAt;
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

    public ListOfRestaurantsPivot getPivot() {
        return mListOfRestaurantsPivot;
    }

    public void setPivot(ListOfRestaurantsPivot listOfRestaurantsPivot) {
        mListOfRestaurantsPivot = listOfRestaurantsPivot;
    }

    public Object getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
