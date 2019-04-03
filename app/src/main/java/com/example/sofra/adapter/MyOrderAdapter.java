package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.accept_order.AcceptOrder;
import com.example.sofra.model.my_order.Datum;
import com.example.sofra.model.my_order.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.showSweetDialog;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    Activity activity;
    Context context;
    List<Datum> listOfOrders;
    int stat;



    public MyOrderAdapter(Activity activity, Context context, List<Datum> listOfOrders, int stat) {
        this.activity = activity;
        this.context = context;
        this.listOfOrders = listOfOrders;
        this.stat = stat;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_order, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Datum data = listOfOrders.get(i);
        String name = data.getRestaurant().getName();
        String cost = data.getCost();
        String total = data.getTotal();
        String deliveryCost = data.getRestaurant().getDeliveryCost();
        final Long id = data.getId();
        List<Item> items = data.getItems();
        for (Item item : items) {
            String photoUrl = item.getPhotoUrl();
            Glide.with(context).load(photoUrl).into(viewHolder.MyOrderImgName);

        }
        viewHolder.MyOrderTvName.setText(name);
        viewHolder.MyOrderTvId.setText("رقم الطلب : " + id);
        viewHolder.MyOrderTvPrice.setText("السعر:" + cost);
        viewHolder.MyOrderTvDelevary.setText("التوصيل" + deliveryCost);
        viewHolder.MyOrderTvTotal.setText("المجموع" + total);
        final String api_token_clint = SharedPreferencesManger.LoadStringData(activity, "api_token_clint");
        final ApiServices apiServices ;
        apiServices = getClint().create(ApiServices.class);
        if (stat == 1) {
            viewHolder.MyOrderLayoutCompleted.setVisibility(View.GONE);

        } else {
            viewHolder.MyOrderLayoutCompleted.setVisibility(View.VISIBLE);
viewHolder.MyOrderBtnLike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

//

        apiServices.confirmOrderClint(String.valueOf(id),api_token_clint).enqueue(new Callback<AcceptOrder>() {
            @Override
            public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                Long status = response.body().getStatus();
                String msg = response.body().getMsg();
                if (status==1){
                    showSweetDialog(context,SweetAlertDialog.SUCCESS_TYPE,
                            "#CC9540",msg,true);

                    listOfOrders.remove(i);
                    notifyDataSetChanged();
                }else{


                    showSweetDialog(context,SweetAlertDialog.ERROR_TYPE
                            ,"#D42D2C",msg,true);

                }
            }



            @Override
            public void onFailure(Call<AcceptOrder> call, Throwable t) {
                String message = t.getMessage();
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
});

viewHolder.MyOrderBtnDisLike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        apiServices.cancelOrderClint(api_token_clint,String.valueOf(id)).enqueue(new Callback<AcceptOrder>() {
            @Override
            public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {

                Long status = response.body().getStatus();
                String msg = response.body().getMsg();
                if (status==1){
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Won't be able to cancel this order!")
                            .setConfirmText("Yes,cancel it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog
                                            .setTitleText("canceled!")
                                            .setContentText("Your order  has been canceled!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    listOfOrders.remove(i);
                                    notifyDataSetChanged();
                                }
                            })
                            .show();


                }
                else {
                    showSweetDialog(context,SweetAlertDialog.ERROR_TYPE
                            ,"#D42D2C",msg,true);


                }
            }

            @Override
            public void onFailure(Call<AcceptOrder> call, Throwable t) {

            }
        });
    }
});

        }


    }

    @Override
    public int getItemCount() {
        return listOfOrders.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.My_Order_img_name)
        ImageView MyOrderImgName;
        @BindView(R.id.My_Order_tv_name)
        TextView MyOrderTvName;
        @BindView(R.id.My_Order_tv_price)
        TextView MyOrderTvPrice;
        @BindView(R.id.My_Order_tv_delevary)
        TextView MyOrderTvDelevary;
        @BindView(R.id.My_Order_tv_total)
        TextView MyOrderTvTotal;
        @BindView(R.id.My_Order_layout)
        RelativeLayout MyOrderLayout;
        @BindView(R.id.My_Order_tv_id)
        TextView MyOrderTvId;
        @BindView(R.id.My_Order_btn_like)
        Button MyOrderBtnLike;
        @BindView(R.id.My_Order_btn_DisLike)
        Button MyOrderBtnDisLike;
        @BindView(R.id.My_Order_layout_completed)
        RelativeLayout MyOrderLayoutCompleted;
        @BindView(R.id.restaurant_card_view_edit)
        RelativeLayout restaurantCardViewEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
