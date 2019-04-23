package com.example.sofra.ui.fragments.clint.notifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.adapter.AdapterNotifications;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.list_of_notifications.Datum;
import com.example.sofra.model.list_of_notifications.ListOfNotifications;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsClintFragment extends Fragment {


    @BindView(R.id.Notification_rev)
    RecyclerView NotificationRev;
    Unbinder unbinder;
    List<Datum> notification_list = new ArrayList<>();
    AdapterNotifications adapterNotifications ;
    private ApiServices apiServices;
    private String api_token_clint;

    public NotificationsClintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications_clint, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiServices = getClint().create(ApiServices.class);
         api_token_clint = SharedPreferencesManger.LoadStringData(getActivity(), "api_token_clint");
        NotificationRev.setLayoutManager(new LinearLayoutManager(getContext()));

        getNotifications();
        return view;
    }

    private void getNotifications() {
        apiServices.getNotifications(api_token_clint).enqueue(new Callback<ListOfNotifications>() {
            @Override
            public void onResponse(Call<ListOfNotifications> call, Response<ListOfNotifications> response) {

                List<Datum> data = response.body().getData().getData();
                String msg = response.body().getMsg();
                Long status = response.body().getStatus();

                if (status==1){
                    adapterNotifications = new AdapterNotifications(getContext(),getActivity(),notification_list);
                    notification_list.addAll(data);
                    NotificationRev.setAdapter(adapterNotifications);
                    adapterNotifications.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "error"+msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListOfNotifications> call, Throwable t) {
                Toast.makeText(getContext(),"Failure"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
