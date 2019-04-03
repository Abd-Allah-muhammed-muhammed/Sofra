package com.example.sofra.adapter;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
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
import com.example.sofra.model.list_of_restaurants.ListOfRestaurantsDatum;
import com.example.sofra.model.room.AppDatabase;
import com.example.sofra.model.room.RoomCartModel;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRoomCart extends RecyclerView.Adapter<AdapterRoomCart.ViewHolder> {
 public static float totalAll;
    Activity activity;
    Context context;
    List<RoomCartModel> listOfDataCart;
    TextView totalTV;



    public AdapterRoomCart(Context context, List<RoomCartModel> listOfDataCart, Activity activity1, TextView totalTV) {
        this.context = context;
        this.listOfDataCart = listOfDataCart;
        this.activity = activity1;
        this.totalTV = totalTV;
        notifyDataSetChanged();


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room_cart, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        Glide.with(context).load(listOfDataCart.get(i).getPhoto_Url()).into(viewHolder.RoomImg);
        viewHolder.RoomTvName.setText(listOfDataCart.get(i).getName());
        viewHolder.RoomTvQonitityNum.setText(listOfDataCart.get(i).getQuantity());
        viewHolder.RoomTvPrice.setText(listOfDataCart.get(i).getPrice());
        final float price = Float.parseFloat(listOfDataCart.get(i).getPrice());
        final int counter = Integer.parseInt(listOfDataCart.get(i).getQuantity());
        String total = String.valueOf(price * counter);
        viewHolder.RoomTvTotel.setText("المجموع"+total);
        totalAll = Float.parseFloat(total)+ totalAll;
      totalTV.setText(""+totalAll);
        viewHolder.RoomBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(listOfDataCart.get(i).getQuantity());
                listOfDataCart.get(i).setQuantity(String.valueOf(++count));
                AppDatabase database = Room.databaseBuilder(context,AppDatabase.class,"db").allowMainThreadQueries().build();

              //  roomDao = RoomManger.getInstance(context).roomDao();
                final int finalCount = count;
                database.cartDeo().update(listOfDataCart.get(i));

                        viewHolder.RoomTvQonitityNum.setText(listOfDataCart.get(i).getQuantity());
                        String totalPlus = String.valueOf(price * finalCount);
                        viewHolder.RoomTvTotel.setText("المجموع = " + totalPlus );
                        totalAll = (price)+ totalAll;
                        totalTV.setText(""+totalAll);


            }
        });
        viewHolder.RoomBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(listOfDataCart.get(i).getQuantity());
                listOfDataCart.get(i).setQuantity(String.valueOf(--count));
              AppDatabase database = Room.databaseBuilder(context,AppDatabase.class,"db").allowMainThreadQueries().build();

                final int finalCount = count;

                        database.cartDeo().update(listOfDataCart.get(i));
                        viewHolder.RoomTvQonitityNum.setText(listOfDataCart.get(i).getQuantity());
                        String totalM = String.valueOf(price * finalCount);
                        viewHolder.RoomTvTotel.setText("المجموع = " + totalM );
                        totalAll = totalAll - price;
                        totalTV.setText(""+totalAll);

            }
        });


    }


    private void displayQuantity(int QUANTIT, ViewHolder viewHolder) {
        viewHolder.RoomTvQonitityNum.setText("" + QUANTIT);

    }


    @Override
    public int getItemCount() {
        return listOfDataCart.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Room_img)
        ImageView RoomImg;
        @BindView(R.id.Room_tv_name)
        TextView RoomTvName;
        @BindView(R.id.Room_Tv_Qonitity)
        TextView RoomTvQonitity;
        @BindView(R.id.Room_btn_plus)
        Button RoomBtnPlus;
        @BindView(R.id.Room_Tv_Qonitity_num)
        TextView RoomTvQonitityNum;
        @BindView(R.id.Room_btn_minus)
        Button RoomBtnMinus;
        @BindView(R.id.Room_Layout)
        RelativeLayout RoomLayout;
        @BindView(R.id.Room_tv_price)
        TextView RoomTvPrice;
        @BindView(R.id.Room_tv_totel)
        TextView RoomTvTotel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
