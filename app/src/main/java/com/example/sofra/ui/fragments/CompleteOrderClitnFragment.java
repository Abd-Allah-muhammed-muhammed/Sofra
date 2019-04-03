package com.example.sofra.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.complete_order.CompleteOrderClint;
import com.example.sofra.model.payment.Datum;
import com.example.sofra.model.payment.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteOrderClitnFragment extends Fragment {


    @BindView(R.id.Complete_order_tv_Add_notes)
    TextView CompleteOrderTvAddNotes;
    @BindView(R.id.Complete_order_Et_Add_notes)
    EditText CompleteOrderEtAddNotes;
    @BindView(R.id.Complete_order_tv_Address)
    TextView CompleteOrderTvAddress;
    @BindView(R.id.Complete_order_Et_Address)
    EditText CompleteOrderEtAddress;
    @BindView(R.id.complete_Order_Tv_pay)
    TextView completeOrderTvPay;
    @BindView(R.id.Complete_order_Rbtn_Cash)
    RadioButton CompleteOrderRbtnCash;
    @BindView(R.id.Complete_order_Rbtn_internet)
    RadioButton CompleteOrderRbtnInternet;
    @BindView(R.id.Complete_Order_Layout_pay)
    LinearLayout CompleteOrderLayoutPay;
    @BindView(R.id.Complete_Order_Tv_Total)
    TextView CompleteOrderTvTotal;
    @BindView(R.id.Complete_Order_Layout_total)
    LinearLayout CompleteOrderLayoutTotal;
    @BindView(R.id.Complete_Order_Tv_DeliveryCost)
    TextView CompleteOrderTvDeliveryCost;
    @BindView(R.id.Complete_Order_Layout_delveryCost)
    LinearLayout CompleteOrderLayoutDelveryCost;
    @BindView(R.id.Complete_Order_Tv_AllTotal)
    TextView CompleteOrderTvAllTotal;
    @BindView(R.id.Complete_Order_Layout_AllTotal)
    LinearLayout CompleteOrderLayoutAllTotal;
    @BindView(R.id.Complete_Order_btn_pay)
    Button CompleteOrderBtnPay;
    Unbinder unbinder;
    private String api_token_clint;
    ApiServices apiServices;
    private long restaurant_id;
    private long payment_id;
    private String name_clint;
    private String phone_clint;
    private String address_clint;
    private List<Integer> listOfItems = new ArrayList<>();
    private List<Integer> listOFQuantities = new ArrayList<>();
    private List<String> listOfNotes = new ArrayList<>();

    public CompleteOrderClitnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complete_order_clitn, container, false);
        unbinder = ButterKnife.bind(this, view);
        api_token_clint = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_clint");
         name_clint = SharedPreferencesManger.LoadStringData(getActivity(), "name_clint");
         phone_clint = SharedPreferencesManger.LoadStringData(getActivity(), "phone_clint");
         address_clint = SharedPreferencesManger.LoadStringData(getActivity(), "address_clint");


        restaurant_id = SharedPreferencesManger.LoadLongData(getActivity(), "id_restaurant");
        apiServices = getClint().create(ApiServices.class);

        getPayment();
        return view;
    }

    private void getPayment() {
apiServices.paymentMethods().enqueue(new Callback<PaymentMethod>() {
    @Override
    public void onResponse(Call<PaymentMethod> call, Response<PaymentMethod> response) {
        String msg = response.body().getMsg();
        Long status = response.body().getStatus();
        List<Datum> data = response.body().getData();
        List<String> paymentName = new ArrayList<>();
        final List<Long> paymentId = new ArrayList<>();
        if (status==1){
            for (int i = 0; i < data.size(); i++) {
                Datum datum = data.get(i);
                Long id = datum.getId();
                String name = datum.getName();
                paymentId.add(id);
                paymentName.add(name);
                CompleteOrderRbtnCash.setText(paymentName.get(0));
                CompleteOrderRbtnInternet.setText(paymentName.get(1));

                CompleteOrderRbtnCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked==true){
                            payment_id = paymentId.get(0);

                        }else {
                           payment_id = -1;
                        }
                    }
                });
                CompleteOrderRbtnInternet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked==true) {
                            payment_id = paymentId.get(1);
                        }else {
                            payment_id=-1;
                        }
                    }
                });
                
            }
        }else {}

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(Call<PaymentMethod> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Complete_Order_btn_pay)
    public void onViewClicked() {
        completOrder();
    }

    private void completOrder() {
        apiServices.completOrder(restaurant_id,CompleteOrderEtAddNotes.getText().toString(),
                CompleteOrderEtAddress.getText().toString(),payment_id,phone_clint,name_clint,
                api_token_clint,listOfItems,listOFQuantities,listOfNotes).enqueue(new Callback<CompleteOrderClint>() {
            @Override
            public void onResponse(Call<CompleteOrderClint> call, Response<CompleteOrderClint> response) {


            }

            @Override
            public void onFailure(Call<CompleteOrderClint> call, Throwable t) {

            }
        });
    }
}
