package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.model.detailsOffers.Data;
import com.example.sofra.model.detailsOffers.DetialsOffers;
import com.example.sofra.model.my_offers_rest.Datum;
import com.example.sofra.model.my_offers_rest.MyOffers;

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

public class AdapterMyOffersRest extends RecyclerView.Adapter<AdapterMyOffersRest.ViewHolder> {
    Activity activity;
    Context context;
    List<Datum> listOfMyOffers;
    int status;



    // private ApiServices apiServices;


    public AdapterMyOffersRest(Context context, List<Datum> listOfMyOffers, Activity activity1 , int status) {
        this.context = context;
        this.listOfMyOffers = listOfMyOffers;
        this.activity = activity1;
        this.status = status;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_offers, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {



        Datum datum = listOfMyOffers.get(position);
        String endingAt = datum.getEndingAt();
        String price = datum.getPrice();
        String name = datum.getName();
        String restaurant_name = datum.getRestaurant().getName();
        String description = datum.getDescription();
        String photo = datum.getPhoto();
        final Long id = datum.getId();
        Glide.with(context).load(photo).into(viewHolder.MyOffersImg);
        viewHolder.MyOffersTvDate.setText(endingAt);
        viewHolder.MyOffersTvName.setText(name);
        viewHolder.MyOffersTvNameRest.setText(restaurant_name);
        final ApiServices apiServices ;
        apiServices = getClint().create(ApiServices.class);

        if (status==2){
            viewHolder.MyOfferLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
apiServices.getOfferDetails(String.valueOf(id)).enqueue(new Callback<DetialsOffers>() {
    @Override
    public void onResponse(Call<DetialsOffers> call, Response<DetialsOffers> response) {

        Long status = response.body().getStatus();
        String msg = response.body().getMsg();
        Data data = response.body().getData();

        if (status==1){
            SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.NORMAL_TYPE);
            dialog.setTitleText(data.getDescription());
            dialog.setContentText(data.getName());
            dialog.show();


        }else {

            showSweetDialog(context, SweetAlertDialog.ERROR_TYPE
                    ,"#D42D2C","error"+msg,true);
        }

    }

    @Override
    public void onFailure(Call<DetialsOffers> call, Throwable t) {
        Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
});

                }
            });

        }


    }


    @Override
    public int getItemCount() {
        return listOfMyOffers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.My_Offers_img)
        ImageView MyOffersImg;
        @BindView(R.id.My_Offers_Tv_name)
        TextView MyOffersTvName;
        @BindView(R.id.My_Offers_Tv_name_rest)
        TextView MyOffersTvNameRest;
        @BindView(R.id.My_Offers_Tv_date)
        TextView MyOffersTvDate;
        @BindView(R.id.My_Offers_Tv_price)
        TextView MyOffersTvPrice;
        @BindView(R.id.My_Offers_Layout)
        RelativeLayout MyOfferLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
