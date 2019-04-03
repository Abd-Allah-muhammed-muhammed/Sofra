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
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.FoodItemsAdapter;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.restuarant_food_items.Datum;
import com.example.sofra.model.restuarant_food_items.FoodItems;

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
public class MenuFragment extends Fragment {


    @BindView(R.id.food_list_fragment_rv_food_list)
    RecyclerView foodListFragmentRvFoodList;
    Unbinder unbinder;
    private FoodItemsAdapter foodItemsAdapter;
    private List<Datum> listOfFood = new ArrayList<>();
    private ApiServices apiServices;
    private long id_restaurant;
    private int user_id;
    private AlertDialog alertDialog;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
          unbinder = ButterKnife.bind(this, view);
          apiServices = getClint().create(ApiServices.class);
          id_restaurant = SharedPreferencesManger.LoadLongData(getActivity(), "id_restaurant");
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        foodListFragmentRvFoodList.setLayoutManager(manager);
        alertDialog.show();
              getItemsFood();


        return view;
    }



    private void getItemsFood() {
        apiServices.getRestaurantFoodItems(id_restaurant,"1")
                .enqueue(new Callback<FoodItems>() {
                    @Override
                    public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {

                        Log.d(TAG, "rew: "+response.raw());
                       if (response.body().getStatus()==1){
alertDialog.cancel();
                           foodItemsAdapter = new FoodItemsAdapter(getActivity(),getContext(),listOfFood);
                           foodListFragmentRvFoodList.setAdapter(foodItemsAdapter);
                           List<Datum> data = response.body().getData().getData();
                           listOfFood.addAll(data);
                           foodItemsAdapter.notifyDataSetChanged();
                       }else {
                           alertDialog.cancel();
                           Log.d(TAG, "onResponse: "+response.body().getMsg());
                           Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                       }

                    }

                    @Override
                    public void onFailure(Call<FoodItems> call, Throwable t) {
                        alertDialog.cancel();
                        Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
