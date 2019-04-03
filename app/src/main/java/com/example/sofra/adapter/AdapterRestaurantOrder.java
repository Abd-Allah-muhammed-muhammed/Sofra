package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.accept_order.AcceptOrder;
import com.example.sofra.model.restayrant_order.Datum;
import com.example.sofra.ui.fragments.CompletRestaurantOrderFragment;
import com.example.sofra.ui.fragments.CurrentRestaurantOrderFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.replaceFragment;

public class AdapterRestaurantOrder extends RecyclerView.Adapter<AdapterRestaurantOrder.ViewHolder> {
    Activity activity;
    Context context;
    List<Datum> listOfOrders;
    int stat ;





    public AdapterRestaurantOrder(Activity activity, Context context, List<Datum> listOfOrders, int stat) {
        this.activity = activity;
        this.context = context;
        this.listOfOrders = listOfOrders;
        this.stat = stat;

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_submit_order, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Datum data = listOfOrders.get(i);
        String address = data.getAddress();
        final String cost = data.getCost();
        String deliveryCost = data.getDeliveryCost();
        final Long id = data.getId();
        final String phone = data.getClient().getPhone();
        String name = data.getClient().getName();
        String total = data.getTotal();
        viewHolder.SubmitOrderBtnNumber.setText(phone);
        viewHolder.SubmitOrderTvAddress.setText(address);
        viewHolder.SubmitOrderTvId.setText("رقم الطلب"+id);
        viewHolder.SubmitOrderTvDeliveryCost.setText(deliveryCost);
        viewHolder.SubmitOrderTvName.setText(name);
        viewHolder.SubmitOrderTvTotal.setText(total);
        viewHolder.SubmitOrderTvPrice.setText(cost);

        final String api_token_restaurant = SharedPreferencesManger.LoadStringData(activity, "api_token_restaurant");

        final ApiServices apiServices;
        apiServices = getClint().create(ApiServices.class);


 viewHolder.SubmitOrderBtnNumber.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {

         Intent intent = new Intent(Intent.ACTION_DIAL);
         intent.setData(Uri.parse("tel:"+Uri.encode(phone.trim())));
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
     }
 });

        if (stat==1){
          viewHolder.SubmitOrderTvCompleteOrder.setVisibility(View.GONE);
            viewHolder.SubmitOrderBtnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiServices.acceptOrderRest(api_token_restaurant,String.valueOf(id)).enqueue(new Callback<AcceptOrder>() {

                        @Override
                        public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                            if (response.body().getStatus()==1){
                                listOfOrders.remove(i);
                                replaceFragment(new CurrentRestaurantOrderFragment(),R.id.Home_replace_fragments,
                                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction());


                            }else {

                                Toast.makeText(activity, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AcceptOrder> call, Throwable t) {
                            Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });




                }
            });

            viewHolder.SubmitOrderBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
apiServices.rejectOrderRest(String.valueOf(id),api_token_restaurant).enqueue(new Callback<AcceptOrder>() {
    @Override
    public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
        if (response.body().getStatus()==1){
            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            listOfOrders.remove(i);
notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<AcceptOrder> call, Throwable t) {

    }
});

                }
            });
      }else if (stat==2){

            viewHolder.SubmitOrderTvCompleteOrder.setVisibility(View.GONE);
            viewHolder.SubmitOrderBtnCancel.setVisibility(View.GONE);

            viewHolder.SubmitOrderBtnAccept.setText("تاكيد التسليم");

            viewHolder.SubmitOrderBtnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
apiServices.confirmOrderRest(String.valueOf(id),api_token_restaurant).enqueue(new Callback<AcceptOrder>() {
    @Override
    public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
        String msg = response.body().getMsg();
        if (response.body().getStatus()==1){

            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            replaceFragment(new CompletRestaurantOrderFragment(), R.id.Home_replace_fragments,((FragmentActivity)context).getSupportFragmentManager().beginTransaction());
        }else {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<AcceptOrder> call, Throwable t) {

    }
});
                }
            });


      }else {
            viewHolder.SubmitOrderTvCompleteOrder.setVisibility(View.VISIBLE);
            viewHolder.SubmitOrderLayoutBtn.setVisibility(View.GONE);
            viewHolder.SubmitOrderTvCompleteOrder.setText("الطلب مكتمل");


        }

    }

    @Override
    public int getItemCount() {
        return listOfOrders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Submit_Order_img_item)
        ImageView SubmitOrderImgItem;
        @BindView(R.id.Submit_Order_Tv_name)
        TextView SubmitOrderTvName;
        @BindView(R.id.Submit_Order_tv_id)
        TextView SubmitOrderTvId;
        @BindView(R.id.Submit_Order_Tv_price)
        TextView SubmitOrderTvPrice;
        @BindView(R.id.Submit_Order_Tv_delivery_cost)
        TextView SubmitOrderTvDeliveryCost;
        @BindView(R.id.Submit_Order_Tv_total)
        TextView SubmitOrderTvTotal;
        @BindView(R.id.Submit_Order_Tv_address)
        TextView SubmitOrderTvAddress;
        @BindView(R.id.Submit_Order_Layout)
        LinearLayout SubmitOrderLayout;
        @BindView(R.id.Submit_order_btn_number)
        Button SubmitOrderBtnNumber;
        @BindView(R.id.Submit_order_btn_accept)
        Button SubmitOrderBtnAccept;
        @BindView(R.id.Submit_Order_btn_cancel)
        Button SubmitOrderBtnCancel;
        @BindView(R.id.Submit_Order_Layout_btn)
        LinearLayout SubmitOrderLayoutBtn;
        @BindView(R.id.Submit_order_tv_complete_order)
        TextView SubmitOrderTvCompleteOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
