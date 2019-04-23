package com.example.sofra.ui.fragments.restaurant;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.CommentsAdapter;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.comments_reviews.CommentsReviews;
import com.example.sofra.model.comments_reviews.Datum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantReviewsEditFragment extends Fragment {


    @BindView(R.id.comments_edit_RV_reviews)
    RecyclerView commentsEditRVReviews;
    Unbinder unbinder;
    private ApiServices apiServices;
    private String api_token_restaurant;
    private long id_restaurant;
    private CommentsAdapter commentsAdapter;
    private List<Datum> listOfComments =  new ArrayList<>();
    private String MYTAG = "mytag";
    private AlertDialog alertDialog;

    public RestaurantReviewsEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_reviews_edit, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiServices = getClint().create(ApiServices.class);

        api_token_restaurant = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_clint");
        id_restaurant = SharedPreferencesManger.LoadLongData(getActivity(), "id_res");
        Log.d(MYTAG, "onCreateView: "+id_restaurant);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();
alertDialog.show();
        getComments();
        return view;
    }

    private void getComments() {
        apiServices.getComments(id_restaurant,1).enqueue(new Callback<CommentsReviews>() {
            @Override
            public void onResponse(Call<CommentsReviews> call, Response<CommentsReviews> response) {
                String msg = response.body().getMsg();
                Long status = response.body().getStatus();
                if (status!=1){
alertDialog.cancel();
                    Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();
                }else {
alertDialog.cancel();
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    commentsEditRVReviews.setLayoutManager(manager);
                    commentsAdapter = new CommentsAdapter(getActivity(), getContext(), listOfComments);
                    commentsEditRVReviews.setAdapter(commentsAdapter);
                    List<Datum> data = response.body().getData().getData();
                    listOfComments.addAll(data);
                    commentsAdapter.notifyDataSetChanged();




                }
            }

            @Override
            public void onFailure(Call<CommentsReviews> call, Throwable t) {
alertDialog.cancel();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
