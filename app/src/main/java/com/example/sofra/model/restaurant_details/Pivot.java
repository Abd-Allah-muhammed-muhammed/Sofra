
package com.example.sofra.model.restaurant_details;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Pivot {

    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("restaurant_id")
    private String mRestaurantId;

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getRestaurantId() {
        return mRestaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        mRestaurantId = restaurantId;
    }

}
