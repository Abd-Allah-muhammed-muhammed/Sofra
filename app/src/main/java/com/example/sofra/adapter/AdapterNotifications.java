package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.model.list_of_notifications.Datum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.ViewHolder> {

    public AdapterNotifications(Context context, Activity activity, List<Datum> notification_list) {
        this.context = context;
        this.activity = activity;
        this.notification_list = notification_list;
    }

    private Context context;
    private Activity activity ;
    List<Datum> notification_list ;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_get_notifications, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Datum data = notification_list.get(position);
        String createdAt = data.getCreatedAt();
        String title = data.getTitle();
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        Date date = null;
        try {
            date = formatDate.parse(createdAt);
            String date1 = formatDate.format(date);
            viewHolder.notificationTvDate.setText(date1);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        DateFormat formatTime = new SimpleDateFormat("HH:mm:ss a",Locale.CANADA);
        Date time = null;
        try {
            time = formatTime.parse(createdAt);
            String time1 = formatTime.format(time);
            viewHolder.notificationTvTime.setText(time1);
        } catch (ParseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        viewHolder.notificationTvAddress.setText(title);




    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.notification_img)
        ImageView notificationImg;
        @BindView(R.id.notification_Tv_address)
        TextView notificationTvAddress;
        @BindView(R.id.notification_Tv_date)
        TextView notificationTvDate;
        @BindView(R.id.notification_Tv_time)
        TextView notificationTvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
