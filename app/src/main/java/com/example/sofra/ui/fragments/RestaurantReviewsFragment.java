package com.example.sofra.ui.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.CommentsAdapter;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.add_review.AddReviews;
import com.example.sofra.model.comments_reviews.CommentsReviews;
import com.example.sofra.model.comments_reviews.Datum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.ui.fragments.RegistreResturantFragment.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantReviewsFragment extends Fragment {

    ApiServices apiServices;
    @BindView(R.id.comments_fragment_btn_add_comment)
    Button commentsFragmentBtnAddComment;
    @BindView(R.id.comments_fragments_RV_reviews)
    RecyclerView commentsFragmentsRVReviews;
    Unbinder unbinder;

    private List<Datum> listOfComments = new ArrayList<>();
    private CommentsAdapter commentsAdapter;
    private int rating;
    private String comment;
    private String api_token_clint;
    private long id_restaurant;
    private AlertDialog alertDialog;

    public RestaurantReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_reviews, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiServices = getClint().create(ApiServices.class);

         api_token_clint = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_clint");
         id_restaurant = SharedPreferencesManger.LoadLongData(getActivity(), "id_restaurant");
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
alertDialog.show();
        getComments();

        return view;
    }

    private void setComment() {
        if (api_token_clint!=null) {
            apiServices.setComment(rating,comment,id_restaurant,api_token_clint)
                    .enqueue(new Callback<AddReviews>() {
                        @Override
                        public void onResponse(Call<AddReviews> call, Response<AddReviews> response) {
                            if (response.body().getStatus()==1) {
                                alertDialog.cancel();
                                Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                            }else {
                                alertDialog.cancel();
                                Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<AddReviews> call, Throwable t) {
                            alertDialog.cancel();
                            Toast.makeText(getContext(), "onFailure"+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }else {

            Toast.makeText(getContext(), "you Should sign up first", Toast.LENGTH_SHORT).show();
        }


    }

    private void getComments() {

        apiServices.getComments(
                 id_restaurant, 1).enqueue(new Callback<CommentsReviews>() {
            @Override
            public void onResponse(Call<CommentsReviews> call, Response<CommentsReviews> response) {
                if (response.body().getStatus() == 1) {
                    alertDialog.cancel();
                    Log.d(TAG, "getStatus: "+response.raw());
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    commentsFragmentsRVReviews.setLayoutManager(manager);
                    commentsAdapter = new CommentsAdapter(getActivity(), getContext(), listOfComments);
                    commentsFragmentsRVReviews.setAdapter(commentsAdapter);
                    List<Datum> data = response.body().getData().getData();
                    listOfComments.addAll(data);
                    commentsAdapter.notifyDataSetChanged();
                } else {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CommentsReviews> call, Throwable t) {

            }
        });
    }


    private void showRatingDialog() {
        Dialog rateDialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.rating_custom_dialog, null);
        rateDialog.setContentView(view);
        if (api_token_clint!=null) {
            rateDialog.show();

        }else {
            Toast.makeText(getContext(), "sign up first", Toast.LENGTH_SHORT).show();
        }

        final RatingBar ratingBar = view.findViewById(R.id.rating_bar_dialog);
        final Button commentButton = view.findViewById(R.id.comment_button);
        final EditText commentText = view.findViewById(R.id.comment_edit_text);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 rating = (int) ratingBar.getRating();
                 comment = getTextFromEditText(commentText);
                 setComment();


            }
        });
    }




    public static String getTextFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.comments_fragment_btn_add_comment)
    public void onViewClicked() {

showRatingDialog();

    }
}
