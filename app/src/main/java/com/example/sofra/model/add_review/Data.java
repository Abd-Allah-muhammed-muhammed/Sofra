
package com.example.sofra.model.add_review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("review")
    @Expose
    private Review review;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}
