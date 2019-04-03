package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.Add_new_productFragment;
import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.accept_order.AcceptOrder;
import com.example.sofra.model.my_items.MyItemsDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.replaceFragment;
import static com.example.sofra.helper.HelperMethod.showSweetDialog;

public class FoodItemsAdapterEdit extends RecyclerView.Adapter<FoodItemsAdapterEdit.ViewHolder> {
    Activity activity;
    Context context;
    List<MyItemsDatum> listOfRestaurant;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public static MyItemsDatum data;
    public static String preparingTime;

    public FoodItemsAdapterEdit(Activity activity, Context context, List<MyItemsDatum> listOfRestaurant) {
        this.activity = activity;
        this.context = context;
        this.listOfRestaurant = listOfRestaurant;
    }



//    public void saveStates(Bundle outState) {
//        viewBinderHelper.saveStates(outState);
//    }
//
//    public void restoreStates(Bundle inState) {
//        viewBinderHelper.restoreStates(inState);
//    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_edit_food, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
         data = listOfRestaurant.get(i);
        viewBinderHelper.bind(viewHolder.swipe
                , data.getId().toString());

        final String name = data.getName();
        final String description = data.getDescription();
        final String price = data.getPrice();
        final String photoUrl = data.getPhotoUrl();
        viewHolder.foodItemTvInfo.setText(description);
        viewHolder.foodItemNameText.setText(name);
        viewHolder.foodItemTvPrice.setText(price);
        final ApiServices apiServices ;
        apiServices = getClint().create(ApiServices.class);
        final Long id = data.getId();
        final String api_token_restaurant = SharedPreferencesManger.LoadStringData(activity, "api_token_restaurant");
        Glide.with(context).load(photoUrl).into(viewHolder.FoodItemImg);
        viewHolder.FoodItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiServices.deleteItemrest(String.valueOf(id),api_token_restaurant).enqueue(new Callback<AcceptOrder>() {
                    @Override
                    public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {

                        Long status = response.body().getStatus();
                        String msg = response.body().getMsg();
                        if (status==1){

                            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Are you sure?")
                                    .setContentText("Won't be able to delete this item!")
                                    .setConfirmText("Yes,delete it!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog
                                                    .setTitleText("Deleted!")
                                                    .setContentText("Your item  has been deleted!")
                                                    .setConfirmText("OK")
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            listOfRestaurant.remove(i);
                                            notifyDataSetChanged();
                                        }
                                    })
                                    .show();



                        }else {

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
        preparingTime = data.getPreparingTime();
        viewHolder.FoodItemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "edit", Toast.LENGTH_SHORT).show();

                SharedPreferencesManger.SaveData(activity,"id_edit",3);
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                Add_new_productFragment add_new_productFragment = new Add_new_productFragment();

                replaceFragment(add_new_productFragment, R.id.Home_replace_fragments, manager.beginTransaction());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listOfRestaurant.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Food_Item_edit)
        Button FoodItemEdit;
        @BindView(R.id.Food_item_delete)
        Button FoodItemDelete;
        @BindView(R.id.Food_Item_Layout_Edit_)
        LinearLayout FoodItemLayoutEdit;
        @BindView(R.id.Food_Item_img)
        ImageView FoodItemImg;
        @BindView(R.id.food_item_name_text)
        TextView foodItemNameText;
        @BindView(R.id.food_item_tv_info)
        TextView foodItemTvInfo;
        @BindView(R.id.food_item_tv_price)
        TextView foodItemTvPrice;
        @BindView(R.id.Food_Item_Lyout)
        RelativeLayout FoodItemLyout;
        @BindView(R.id.swipe)
        SwipeRevealLayout swipe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
