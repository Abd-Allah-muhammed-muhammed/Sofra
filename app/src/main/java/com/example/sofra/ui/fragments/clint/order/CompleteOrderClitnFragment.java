package com.example.sofra.ui.fragments.clint.order;


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
import com.example.sofra.model.room.RoomCartModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
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

    private long payment_id;
    private String name_clint;
    private String phone_clint;
    private String address_clint;
    private List<Integer> listOfItems = new ArrayList<>();
    private List<Integer> listOFQuantities = new ArrayList<>();
    private List<String> listOfNotes = new ArrayList<>();
    boolean chicked = false  ;

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
        apiServices = getClint().create(ApiServices.class);

        CompleteOrderRbtnCash.setText("نقدا عند الاستلام");
        CompleteOrderRbtnInternet.setText("شبكة عند الاستلام");
        CompleteOrderRbtnCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    payment_id = 1 ;
                    chicked = true;
                }else {
                    chicked = false;
                    payment_id= 0;
                }
            }
        });
        CompleteOrderRbtnInternet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    payment_id = 1 ;
                    chicked = true;
                }else {
                    chicked = false;
                    payment_id= 0;
                }
            }
        });
        return view;
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

        RoomCartModel model = new RoomCartModel() ;
        for (int i = 0; i < listOfItems.size(); i++) {
            listOfItems.add(model.getId());
            listOfNotes.add(model.getNotes());
            listOFQuantities.add(Integer.valueOf(model.getQuantity()));

        }


        if (chicked == false){

            SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE);
            dialog.setTitleText("you have check on way to pay");
            dialog.show();
        }
        String restaurantId = model.getRestaurantId();
        listOfItems.add(model.getId());
        apiServices.completOrder(restaurantId,CompleteOrderEtAddNotes.getText().toString(),
                CompleteOrderEtAddress.getText().toString(),payment_id,phone_clint,name_clint,
                api_token_clint,listOfItems,listOFQuantities,listOfNotes).enqueue(new Callback<CompleteOrderClint>() {
            @Override
            public void onResponse(Call<CompleteOrderClint> call, Response<CompleteOrderClint> response) {

                String msg = response.body().getMsg();
                Long status = response.body().getStatus();
                if (status==1){

                    SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitleText(msg);
                    dialog.show();
                }else {

                    SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
                    dialog.setTitleText(msg);
                    dialog.show();
                }

            }

            @Override
            public void onFailure(Call<CompleteOrderClint> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
