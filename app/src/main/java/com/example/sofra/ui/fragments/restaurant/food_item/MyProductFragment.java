package com.example.sofra.ui.fragments.restaurant.food_item;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.FoodItemsAdapterEdit;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.my_items.MyItems;
import com.example.sofra.model.my_items.MyItemsDatum;
import com.example.sofra.ui.fragments.restaurant.food_item.Add_new_productFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.replaceFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProductFragment extends Fragment {


    @BindView(R.id.MyProducts_Rv)
    RecyclerView MyProductsRv;
    @BindView(R.id.MyProducts_btn_add_new_item)
    Button MyProductsBtnAddNewItem;
    private Unbinder unbinder;
    private String api_token_restaurant;
    private ApiServices apiServices;
    private FoodItemsAdapterEdit foodItemsAdapterEdit;
    private List<MyItemsDatum> listOfFood = new ArrayList<>();
    private AlertDialog alertDialog;

    public MyProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_product, container, false);
        unbinder = ButterKnife.bind(this, view);
        api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
        apiServices = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
alertDialog.show();
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
                    MyProductsRv.setLayoutManager(manager);
                    foodItemsAdapterEdit = new FoodItemsAdapterEdit(getActivity(), getContext(), listOfFood);
                    MyProductsRv.setAdapter(foodItemsAdapterEdit);
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


    @OnClick(R.id.MyProducts_btn_add_new_item)
    public void onViewClicked() {
        FragmentManager manager = getFragmentManager();

replaceFragment(new Add_new_productFragment(),R.id.Home_replace_fragments,manager.beginTransaction());
    }
}
