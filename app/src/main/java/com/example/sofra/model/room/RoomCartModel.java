package com.example.sofra.model.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RoomCartModel {

    public RoomCartModel( String photo_Url, String quantity, String price, String name, String restaurantId) {

        this.photo_Url = photo_Url;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.restaurantId = restaurantId;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "photo_Url")
    private String photo_Url;

    @ColumnInfo(name = "quantity")
    private String quantity;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "RestaurantId")
    private String restaurantId;



    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto_Url() {
        return photo_Url;
    }

    public void setPhoto_Url(String photo_Url) {
        this.photo_Url = photo_Url;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
