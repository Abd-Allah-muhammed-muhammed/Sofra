package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofra.ui.fragments.clint.order.DisplayItemFragment;
import com.example.sofra.R;
import com.example.sofra.model.restuarant_food_items.Datum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.helper.HelperMethod.replaceFragment;

public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.ViewHolder> {
    Activity activity;
    Context context;
    List<Datum> listOfRestaurant;
    public String restaurantId;


    public FoodItemsAdapter(Activity activity, Context context, List<Datum> listOfRestaurant) {
        this.activity = activity;
        this.context = context;
        this.listOfRestaurant = listOfRestaurant;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Datum data = listOfRestaurant.get(i);
        final String name = data.getName();
        final String description = data.getDescription();
        final String price = data.getPrice();
        final String photoUrl = data.getPhotoUrl();
        viewHolder.foodItemDetailsTextView.setText(description);
        viewHolder.foodItemNameText.setText(name);
        viewHolder.foodPriceTextView.setText(price);
        final String preparingTime = data.getPreparingTime();
        Glide.with(context).load(photoUrl).into(viewHolder.restaurantImageView);
        final Long id = data.getId();
          restaurantId = data.getRestaurantId();
        viewHolder.restaurantCardViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DisplayItemFragment fragment = new DisplayItemFragment();
                fragment.display_photo = photoUrl;
                fragment.display_wait = preparingTime;
                fragment.display_price = price;
                fragment.display_name = name;
                fragment.display_desc = description;
                fragment.display_id  = id ;
                fragment.id_restaurant = restaurantId;

                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                replaceFragment(fragment,R.id.Home_replace_fragments,manager.beginTransaction());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfRestaurant.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurant_image_view)
        ImageView restaurantImageView;
        @BindView(R.id.food_item_name_text)
        TextView foodItemNameText;
        @BindView(R.id.food_item_details_text_view)
        TextView foodItemDetailsTextView;
        @BindView(R.id.food_price_text_view)
        TextView foodPriceTextView;


        @BindView(R.id.restaurant_card_view_edit)
        RelativeLayout restaurantCardViewEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
