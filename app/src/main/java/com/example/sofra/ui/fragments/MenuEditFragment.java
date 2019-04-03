package com.example.sofra.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.FoodItemsAdapterEdit;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.my_items.MyItems;
import com.example.sofra.model.my_items.MyItemsDatum;

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
public class MenuEditFragment extends Fragment {


    @BindView(R.id.food_list_edit_rv_food_list)
    RecyclerView foodListEditRvFoodList;
    Unbinder unbinder;
    private ApiServices apiServices;
    private String api_token_restaurant;
    private FoodItemsAdapterEdit foodItemsAdapterEdit;
    private  List<MyItemsDatum> listOfFood = new ArrayList<>();
    private AlertDialog alertDialog;

    public MenuEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_edit, container, false);
        unbinder = ButterKnife.bind(this, view);
        api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
        apiServices = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        getRestaurantFood();
        return view;
    }






    private void getRestaurantFood() {
        apiServices.myItems(api_token_restaurant, 1).enqueue(new Callback<MyItems>() {
            @Override
            public void onResponse(Call<MyItems> call, Response<MyItems> response) {
                Long status = response.body().getStatus();
                String msg = response.body().getMsg();
                if (status == 1) {
                    alertDialog.cancel();
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    foodListEditRvFoodList.setLayoutManager(manager);
                    foodItemsAdapterEdit = new FoodItemsAdapterEdit(getActivity(), getContext(), listOfFood);
                    foodListEditRvFoodList.setAdapter(foodItemsAdapterEdit);
                    List<MyItemsDatum> data = response.body().getData().getData();
                    listOfFood.addAll(data);
                    foodItemsAdapterEdit.notifyDataSetChanged();
                } else {
alertDialog.cancel();
                    Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyItems> call, Throwable t) {
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
