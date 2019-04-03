
package com.example.sofra.model;

import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class City {

    @SerializedName("created_at")
    private Object mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
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

    public Object getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
