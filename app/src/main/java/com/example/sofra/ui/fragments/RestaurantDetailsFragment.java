package com.example.sofra.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.model.City;
import com.example.sofra.model.Region;
import com.example.sofra.model.restaurant_details.Data;
import com.example.sofra.model.restaurant_details.RestuarantDetails;

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
public class RestaurantDetailsFragment extends Fragment {


    @BindView(R.id.restaurant_info_fragment_tv_status)
    TextView restaurantInfoFragmentTvStatus;
    @BindView(R.id.restaurant_info_tv_city)
    TextView restaurantInfoTvCity;
    @BindView(R.id.restaurant_info_fragment_tv_region)
    TextView restaurantInfoFragmentTvRegion;
    @BindView(R.id.restaurant_info_fragment_tv_min)
    TextView restaurantInfoFragmentTvMin;
    @BindView(R.id.restaurant_info_fragment_tv_delivery_cost)
    TextView restaurantInfoFragmentTvDeliveryCost;
    Unbinder unbinder;
    private ApiServices apiServices;
    private AlertDialog alertDialog;

    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        unbinder = ButterKnife.bind(this, view);
         apiServices = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
alertDialog.show();
        getDetrails();
        return view;

    }

    private void getDetrails() {
        apiServices.restaurantDetails(25).enqueue(new Callback<RestuarantDetails>() {
            @Override
            public void onResponse(Call<RestuarantDetails> call, Response<RestuarantDetails> response) {
                if (response.body().getStatus()==1){
                    alertDialog.cancel();
                    Data data = response.body().getData();
                    String availability = data.getAvailability();
                    String minimumCharger = data.getMinimumCharger();
                    Region region = data.getRegion();
                    String nameRegion = region.getName();
                    City city = region.getCity();
                    String nameCity = city.getName();
                    String deliveryCost = data.getDeliveryCost();
                    restaurantInfoFragmentTvDeliveryCost.setText(deliveryCost);
                    restaurantInfoFragmentTvMin.setText(minimumCharger);
                    restaurantInfoFragmentTvRegion.setText(nameRegion);
                    restaurantInfoFragmentTvStatus.setText(availability);
                    restaurantInfoTvCity.setText(nameCity);
                }else {
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RestuarantDetails> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
