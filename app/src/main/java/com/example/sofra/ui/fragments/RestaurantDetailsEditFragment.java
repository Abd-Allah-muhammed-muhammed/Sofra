package com.example.sofra.ui.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.City;
import com.example.sofra.model.Region;
import com.example.sofra.model.restaurant_details.Data;
import com.example.sofra.model.restaurant_details.RestuarantDetails;
import com.example.sofra.model.status.Status;

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
public class RestaurantDetailsEditFragment extends Fragment {


    @BindView(R.id.restaurant_info_fragment_tv_status)
    Switch restaurantInfoSwchStatus;
    @BindView(R.id.restaurant_info_tv_city)
    TextView restaurantInfoTvCity;
    @BindView(R.id.restaurant_info_fragment_tv_region)
    TextView restaurantInfoTvRegion;
    @BindView(R.id.restaurant_info_fragment_tv_min)
    TextView restaurantInfoeditTvMin;
    @BindView(R.id.restaurant_info_fragment_tv_delivery_cost)
    TextView restaurantInfoeditTvDeliveryCost;
    Unbinder unbinder;
    private ApiServices apiServices;
    private long id_restaurant;
    private String api_token_restaurant;
    public  final String open = "open";
    public  final String close = "closed";
    private AlertDialog alertDialog;


    public RestaurantDetailsEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_details_edit, container, false);
        unbinder = ButterKnife.bind(this, view);
         apiServices = getClint().create(ApiServices.class);
        id_restaurant = SharedPreferencesManger.LoadLongData(getActivity(), "id_res");
         api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
alertDialog.show();
        getDetails();

        return view;
    }

    private void getDetails() {
        apiServices.restaurantDetails(id_restaurant).enqueue(new Callback<RestuarantDetails>() {
            @Override
            public void onResponse(Call<RestuarantDetails> call, Response<RestuarantDetails> response) {
                if (response.body().getStatus()==1){
                    alertDialog.cancel();
                    final Data data = response.body().getData();
                    String minimumCharger = data.getMinimumCharger();
                    final Region region = data.getRegion();
                    final String nameRegion = region.getName();
                    City city = region.getCity();
                    String nameCity = city.getName();
                    String deliveryCost = data.getDeliveryCost();
                    restaurantInfoeditTvDeliveryCost.setText(deliveryCost);
                    restaurantInfoeditTvMin.setText(minimumCharger);
                    restaurantInfoTvRegion.setText(nameRegion);
                    restaurantInfoSwchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {

                                changeStatus(open);
                                restaurantInfoSwchStatus.setText(open);


                            }else {
                                restaurantInfoSwchStatus.setText(close);
                                changeStatus(close);
                            }
                        }
                    });
                    restaurantInfoTvCity.setText(nameCity);
                }else {
alertDialog.cancel();
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RestuarantDetails> call, Throwable t) {
alertDialog.cancel();
            }
        });
    }

    private void changeStatus(String status) {


        apiServices.changStatus(status,api_token_restaurant).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.body().getStatus()==1){
                    Toast.makeText(getContext(), ""+response.body().getData().getAvailability(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), ""+response.body().getData().getAvailability(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
