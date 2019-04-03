package com.example.sofra.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.AdapterRestaurantOrder;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.restayrant_order.Datum;
import com.example.sofra.model.restayrant_order.RestaurantOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.ui.fragments.RegistreResturantFragment.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewRestaurantOrderFragment extends Fragment {


    @BindView(R.id.Restaurant_NewOrder_RV)
    RecyclerView RestaurantNewOrderRV;
    Unbinder unbinder;
    @BindView(android.R.id.empty)
    TextView empty;
    private ApiServices apiServices;
    private String api_token_restaurant;
    private List<Datum> listOfOrder = new ArrayList<>();
    private AdapterRestaurantOrder adapterRestaurantOrder;
    private AlertDialog alertDialog;

    public NewRestaurantOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_restaurant_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapterRestaurantOrder = new AdapterRestaurantOrder(getActivity(), getContext(), listOfOrder, 1);
        api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
        apiServices = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
alertDialog.show();
        getNewOrder();


        return view;
    }

    private void getNewOrder() {
        apiServices.restaurantMyOrders(api_token_restaurant, "pending", 1).enqueue(new Callback<RestaurantOrder>() {
            @Override
            public void onResponse(Call<RestaurantOrder> call, Response<RestaurantOrder> response) {
                String msg = response.body().getMsg();
                Long status = response.body().getStatus();
                if (status == 1) {
                    alertDialog.cancel();
                    List<Datum> data = response.body().getData().getData();
                    RestaurantNewOrderRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                    RestaurantNewOrderRV.setAdapter(adapterRestaurantOrder);
                    listOfOrder.addAll(data);
                    adapterRestaurantOrder.notifyDataSetChanged();

                    if (listOfOrder.isEmpty()) {
                        empty.setVisibility(View.VISIBLE);
                    }else {
                        empty.setVisibility(View.GONE);
                    }
                } else {
                    alertDialog.cancel();
                    Toast.makeText(getActivity(), "error" + msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantOrder> call, Throwable t) {
                alertDialog.cancel();
                Toast.makeText(getActivity(), "onFailure" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
