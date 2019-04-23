package com.example.sofra.ui.fragments.restaurant.offers;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.AdapterMyOffersRest;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.my_offers_rest.Datum;
import com.example.sofra.model.my_offers_rest.MyOffers;
import com.example.sofra.ui.fragments.restaurant.offers.AddNewOfferskFragment;

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
public class MyOffersRestFragment extends Fragment {


    @BindView(R.id.My_Offers_RV)
    RecyclerView MyOffersRV;
    @BindView(R.id.MyOffers_btn_add_new_Offer)
    Button MyOffersBtnAddNewOffer;
    Unbinder unbinder;
    private ApiServices apiServices;
    List<Datum> listOfOffers = new ArrayList<>();
    private String api_token_restaurant;
    private AlertDialog alertDialog;

    public MyOffersRestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_offers_rest, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();

        api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
    alertDialog.show();
        getMyOffers();
        return view;
    }

    private void getMyOffers() {
        apiServices.myOffersRestaurant(api_token_restaurant,1).enqueue(new Callback<MyOffers>() {
            @Override
            public void onResponse(Call<MyOffers> call, Response<MyOffers> response) {
                Long status = response.body().getStatus();
                String msg = response.body().getMsg();
                if (status==1){
                    alertDialog.cancel();
                    MyOffersRV.setLayoutManager(new LinearLayoutManager(getContext()));
                    List<Datum> data = response.body().getData().getData();
                    listOfOffers.addAll(data);
                    MyOffersRV.setAdapter(new AdapterMyOffersRest(getContext(),listOfOffers,getActivity(),1));

                }else {
alertDialog.cancel();
                    Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyOffers> call, Throwable t) {
                alertDialog.cancel();
                Log.d("tag", "onFailure: "+t.getMessage());

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.MyOffers_btn_add_new_Offer)
    public void onViewClicked() {
        replaceFragment(new AddNewOfferskFragment(),R.id.Home_replace_fragments,getFragmentManager().beginTransaction());
    }
}
