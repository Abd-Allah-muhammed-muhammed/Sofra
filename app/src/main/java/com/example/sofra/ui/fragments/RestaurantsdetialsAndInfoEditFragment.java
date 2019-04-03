package com.example.sofra.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.adapter.TapLayoutAdapterEdit;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.restaurant_details.Category;
import com.example.sofra.model.restaurant_details.Data;
import com.example.sofra.model.restaurant_details.RestuarantDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsdetialsAndInfoEditFragment extends Fragment {


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
    @BindView(R.id.restaurant_fragment_detials_tabs_Edit)
    TabLayout restaurantFragmentDetialsTabsEdit;
    @BindView(R.id.restaurant_detials_view_pager_Edit)
    ViewPager restaurantDetialsViewPagerEdit;
    Unbinder unbinder;
    private ApiServices apiServices;
    private long id_restaurant;
    private AlertDialog alertDialog;

    public RestaurantsdetialsAndInfoEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurantsdetials_and_info_edit, container, false);
        unbinder = ButterKnife.bind(this, view);
        restaurantDetialsViewPagerEdit.setAdapter(new TapLayoutAdapterEdit(getActivity().getSupportFragmentManager(), 1));
        restaurantFragmentDetialsTabsEdit.setupWithViewPager(restaurantDetialsViewPagerEdit);
        apiServices = getClint().create(ApiServices.class);
        id_restaurant = SharedPreferencesManger.LoadLongData(getActivity(), "id_res");
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
        alertDialog.show();
        getRestaurantsDetails();
        return view;
    }

    private void getRestaurantsDetails() {
        apiServices.restaurantDetails(id_restaurant).enqueue(new Callback<RestuarantDetails>() {
            @Override
            public void onResponse(Call<RestuarantDetails> call, Response<RestuarantDetails> response) {

                if (response.body().getStatus() == 1) {
                    Data data = response.body().getData();
                    String name = data.getName();
                    String availability = data.getAvailability();
                    String deliveryCost = data.getDeliveryCost();
                    String minimumCharger = data.getMinimumCharger();
                    String photoUrl = data.getPhotoUrl();
                    StringBuilder builder = new StringBuilder();
                    List<Category> categories = data.getCategories();
                    for (int j = 0; j < categories.size(); j++) {
                        builder.append(categories.get(j).getName()).append(",");

                    }
                    // there an error here
                    // don't forget

                    restaurantCategoriesTv.setText(builder.toString());
                    restaurantAvilbltyTv.setText(availability);
                    restaurantDeliveryFeeTv.setText(deliveryCost);
                    restaurantMinOrderTv.setText(minimumCharger);
                    restaurantNameTv.setText(name);
                    Glide.with(getContext()).load(photoUrl).into(restaurantImageView);
                    int rate = data.getRate();
                    restaurantTvratingBar.setNumStars(rate);
                    alertDialog.cancel();
                }

            }


            @Override
            public void onFailure(Call<RestuarantDetails> call, Throwable t) {
                alertDialog.cancel();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
