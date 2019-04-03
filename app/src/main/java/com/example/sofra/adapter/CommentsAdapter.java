package com.example.sofra.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.model.comments_reviews.Datum;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Activity activity;
    Context context;
    List<Datum> listOfRestaurant;

    public CommentsAdapter(Activity activity, Context context, List<Datum> listOfRestaurant) {
        this.activity = activity;
        this.context = context;
        this.listOfRestaurant = listOfRestaurant;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Datum data = listOfRestaurant.get(i);
        viewHolder.commentsTextView.setText(data.getComment());
        viewHolder.dateTextView.setText(data.getUpdatedAt());
        viewHolder.ratingBar.setNumStars(Integer.parseInt(data.getRate()));
        viewHolder.userNameTextView.setText(data.getClientId());


    }

    @Override
    public int getItemCount() {
        return listOfRestaurant.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_name_text_view)
        TextView userNameTextView;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;
        @BindView(R.id.date_text_view)
        TextView dateTextView;
        @BindView(R.id.comments_text_view)
        TextView commentsTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
