package com.example.sofra.ui.fragments.clint.order;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.adapter.MyOrderAdapter;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.my_order.Datum;
import com.example.sofra.model.my_order.MyOrder;

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
import static com.example.sofra.ui.fragments.login.RegistreResturantFragment.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletOrderFragment extends Fragment {


    @BindView(R.id.MyCompletedOrder_RecV)
    RecyclerView MyCompletedOrderRecV;
    Unbinder unbinder;
    private ApiServices apiServices;
    private String api_token_clint;
    private MyOrderAdapter myOrderAdapter;
    private List<Datum> listofOrder = new ArrayList<>();
    private AlertDialog alertDialog;

    public CompletOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complet_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiServices = getClint().create(ApiServices.class);
        api_token_clint = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_clint");
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
        alertDialog.show();
        getComletdOrder();
        return view;
    }

    private void getComletdOrder() {

        apiServices.curentOrder(api_token_clint, "completed", 1).enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {

                Log.d(TAG, "rew: " + response.raw());
                if (response.body().getStatus() == 1) {
                    List<Datum> data = response.body().getData().getData();
                    myOrderAdapter = new MyOrderAdapter(getActivity(), getContext(), listofOrder, 2);
                    MyCompletedOrderRecV.setLayoutManager(new LinearLayoutManager(getContext()));
                    MyCompletedOrderRecV.setAdapter(myOrderAdapter);
                    listofOrder.addAll(data);
                    myOrderAdapter.notifyDataSetChanged();
                    alertDialog.cancel();
                    Log.d(TAG, "ok: " + response.body().getMsg());
                } else {
                    alertDialog.cancel();
                    Log.d(TAG, "no: " + response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<MyOrder> call, Throwable t) {
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
