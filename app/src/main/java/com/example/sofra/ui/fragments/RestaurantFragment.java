package com.example.sofra.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.AdapterGetRestaurant;
import com.example.sofra.api.ApiServices;
import com.example.sofra.model.list_of_restaurants.ListOfRestaurants;
import com.example.sofra.model.list_of_restaurants.ListOfRestaurantsDatum;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {


    @BindView(R.id.Get_Rest_sp_city)
    Spinner GetRestSpCity;
    @BindView(R.id.Get_Rest_Et_search)
    EditText GetRestEtSearch;
    @BindView(R.id.layout_shearch)
    RelativeLayout layoutShearch;
    @BindView(R.id.Get_Rest_rcy_restaurants)
    RecyclerView GetRestRcyRestaurants;
    Unbinder unbinder;
    AdapterGetRestaurant adapterRestaurant;
    private List<ListOfRestaurantsDatum> listOfRestaurant = new ArrayList<>();
    private ApiServices apiServices;
    private AlertDialog alertDialog;


    public RestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        apiServices = getClint().create(ApiServices.class);
        alertDialog.show();
        getRestaurants();

        return view;
    }

    private void getRestaurants() {

        apiServices.getRestaurants(1).enqueue(new Callback<ListOfRestaurants>() {
            @Override
            public void onResponse(Call<ListOfRestaurants> call, Response<ListOfRestaurants> response) {

                if (response.body().getStatus() == 1) {
                    alertDialog.cancel();
                    GetRestRcyRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRestaurant = new AdapterGetRestaurant(getContext(), listOfRestaurant, getActivity());
                    GetRestRcyRestaurants.setAdapter(adapterRestaurant);

                    List<ListOfRestaurantsDatum> data = response.body().getData().getData();
                    listOfRestaurant.addAll(data);
                    adapterRestaurant.notifyDataSetChanged();


                } else {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListOfRestaurants> call, Throwable t) {
                alertDialog.cancel();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
