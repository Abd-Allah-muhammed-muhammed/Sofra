package com.example.sofra.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.AdapterMyOffersRest;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.my_offers_rest.Datum;
import com.example.sofra.model.my_offers_rest.MyOffers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.closeDialog;
import static com.example.sofra.helper.HelperMethod.showSweetDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClintOffersFragment extends Fragment {


    @BindView(R.id.ClintOffers_Rv)
    RecyclerView ClintOffersRv;
    Unbinder unbinder;
    private String api_token_restaurant;
    ApiServices apiServices;
    private List<Datum> listOfOffers = new ArrayList<>();
    private AdapterMyOffersRest adapterOffers;

    public ClintOffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clint_offers, container, false);
        unbinder = ButterKnife.bind(this, view);
        api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_restaurant");
        apiServices = getClint().create(ApiServices.class);
         adapterOffers = new AdapterMyOffersRest(getContext(),listOfOffers , getActivity(), 2);
        showSweetDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE
                ,"#D42D2C","Loading...",false);
        getOffers();
        return view;
    }

    private void getOffers() {
        apiServices.getOffers(String.valueOf(1)).enqueue(new Callback<MyOffers>() {
            @Override
            public void onResponse(Call<MyOffers> call, Response<MyOffers> response) {
                String msg = response.body().getMsg();
                Long status = response.body().getStatus();
                List<Datum> data = response.body().getData().getData();
                if (status==1){
                    ClintOffersRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    ClintOffersRv.setAdapter(adapterOffers);
                    listOfOffers.addAll(data);
                    adapterOffers.notifyDataSetChanged();
                    closeDialog();
                }else {
                    showSweetDialog(getContext(), SweetAlertDialog.ERROR_TYPE
                            ,"#D42D2C",msg,true);
                }

            }

            @Override
            public void onFailure(Call<MyOffers> call, Throwable t) {
closeDialog();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
