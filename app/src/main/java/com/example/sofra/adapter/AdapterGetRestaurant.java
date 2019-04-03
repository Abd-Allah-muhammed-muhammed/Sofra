package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.ui.fragments.Restaurants_detialsAndInfoFragment;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.list_of_restaurants.ListOfRestaurantsCategory;
import com.example.sofra.model.list_of_restaurants.ListOfRestaurantsDatum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.helper.HelperMethod.replaceFragment;
import static com.example.sofra.ui.fragments.RegistreResturantFragment.TAG;

public class AdapterGetRestaurant extends RecyclerView.Adapter<AdapterGetRestaurant.ViewHolder> {
    Activity activity;
    Context context;
    List<ListOfRestaurantsDatum> listOfRestaurant;

    // private ApiServices apiServices;


    public AdapterGetRestaurant(Context context, List<ListOfRestaurantsDatum> listOfRestaurant, Activity activity1) {
        this.context = context;
        this.listOfRestaurant = listOfRestaurant;
        this.activity = activity1;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_get_restaurants, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {


        final ListOfRestaurantsDatum data = listOfRestaurant.get(i);
        String name = data.getName();
        viewHolder.restaurantNameTv.setText(name);
        String availability = data.getAvailability();

        viewHolder.restaurantAvilbltyTv.setText(availability);
        String photo = data.getPhotoUrl();
        int rate = data.getRate();
        viewHolder.restaurantTvratingBar.setNumStars(rate);


        Glide.with(context).load(photo).into(viewHolder.restaurantImageView);
        final StringBuilder builder = new StringBuilder();
        List<ListOfRestaurantsCategory> categories = data.getCategories();
        for (int j = 0; j < categories.size(); j++) {
            builder.append(categories.get(j).getName()+",");

        }
        viewHolder.restaurantCategoriesTv.setText(builder.toString());
        Log.d(TAG, "onBindViewHolder: ");


        final String deliveryCost = data.getDeliveryCost();
        String minimumCharger = data.getMinimumCharger();
        viewHolder.restaurantDeliveryFeeTv.setText(deliveryCost);
        viewHolder.restaurantMinOrderTv.setText(minimumCharger);
        viewHolder.restaurantCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferencesManger.SaveData(activity,"id_restaurant",listOfRestaurant.get(i).getId());
                replaceFragment(new Restaurants_detialsAndInfoFragment(), R.id.Home_replace_fragments,
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction());
            }
        });

    }


    @Override
    public int getItemCount() {
        return listOfRestaurant.size();
    }

    @OnClick(R.id.restaurant_card_view)
    public void onViewClicked() {


    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurant_image_view)
        ImageView restaurantImageView;
        @BindView(R.id.restaurant_name_tv)
        TextView restaurantNameTv;
        @BindView(R.id.restaurant_categories_tv)
        TextView restaurantCategoriesTv;
        @BindView(R.id.restaurant_tvrating_bar)
        RatingBar restaurantTvratingBar;
        @BindView(R.id.layout_2)
        LinearLayout layout2;
        @BindView(R.id.restaurant_avilblty_tv)
        TextView restaurantAvilbltyTv;
        @BindView(R.id.restaurant_min_order_tv)
        TextView restaurantMinOrderTv;
        @BindView(R.id.restaurant_delivery_fee_tv)
        TextView restaurantDeliveryFeeTv;
        @BindView(R.id.restaurant_card_view)
        CardView restaurantCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
