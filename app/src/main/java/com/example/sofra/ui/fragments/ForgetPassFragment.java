package com.example.sofra.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.api.ApiServices;
import com.example.sofra.helper.SharedPreferencesManger;
import com.example.sofra.model.chang_password.ChangPassword;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.api.RetrofitClient.getClint;
import static com.example.sofra.helper.HelperMethod.hidView;
import static com.example.sofra.helper.HelperMethod.showView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPassFragment extends Fragment {


    @BindView(R.id.Forget_Pass_text)
    TextView ForgetPassText;
    @BindView(R.id.enter_ur_mail)
    TextView enterUrMail;
    @BindView(R.id.Forget_pass_Et_mail)
    EditText ForgetPassEtMail;
    @BindView(R.id.Forget_pass_btn_follow)
    Button ForgetPassBtnFollow;
    @BindView(R.id.Forget_pass_Layout_reset_password)
    RelativeLayout ForgetPassLayoutReset_password;
    @BindView(R.id.enter_the_code)
    TextView enterTheCode;
    @BindView(R.id.Forget_pass_Et_Code)
    EditText ForgetPassEtCode;
    @BindView(R.id.enter_new_pass)
    TextView enterNewPass;
    @BindView(R.id.Forget_pass_Et_new_pass)
    EditText ForgetPassEtNewPass;
    @BindView(R.id.Forget_pass_Et_re_new_pass)
    EditText ForgetPassEtReNewPass;
    @BindView(R.id.Forget_pass_btn_Change)
    Button ForgetPassBtnChange;
    @BindView(R.id.Forget_pass_Layout_new_password)
    RelativeLayout ForgetPassLayoutNewPassword;
    Unbinder unbinder;
    private int ID;
    private ApiServices apiServices;
    private AlertDialog alertDialog;

    public ForgetPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgit_pass, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            ID = getArguments().getInt("id_forget");
        }else {
            ID = 3;
        }
        hidView(ForgetPassLayoutNewPassword);
        showView(ForgetPassLayoutReset_password);
         apiServices = getClint().create(ApiServices.class);
        alertDialog = new SpotsDialog.Builder().setContext(getContext()).setTheme(R.style.Custom).build();


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Forget_pass_btn_follow, R.id.Forget_pass_btn_Change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Forget_pass_btn_follow:
                if (ID==5){
                    sendthCodClint();
                    alertDialog.show();

                }else {
                    sendthCodRestaurant();
                    alertDialog.show();
                }
                break;
            case R.id.Forget_pass_btn_Change:
                if (ID==5){
                    changPassClint();
                    alertDialog.show();
                }else {
                    changPassRestaurant();
                    alertDialog.show();
                }
                break;
        }
    }

    private void changPassRestaurant() {

        apiServices.restaurantNewPassword(ForgetPassEtCode.getText().toString(),ForgetPassEtNewPass.getText().toString()
                ,ForgetPassEtReNewPass.getText().toString()).enqueue(new Callback<ChangPassword>() {
            @Override
            public void onResponse(Call<ChangPassword> call, Response<ChangPassword> response) {
                if (response.body().getStatus()==1){
                    alertDialog.cancel();
                    Toast.makeText(getContext(),""+response.body().getMsg(),Toast.LENGTH_LONG).show();
                }else {
alertDialog.cancel();
                    Toast.makeText(getContext(),""+response.body().getMsg(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ChangPassword> call, Throwable t) {
                alertDialog.cancel();

            }
        });

    }

    private void changPassClint() {

        apiServices.clientNewPassword(ForgetPassEtCode.getText().toString(),
                ForgetPassEtNewPass.getText().toString()
        ,ForgetPassEtReNewPass.getText().toString()).enqueue(new Callback<ChangPassword>() {
            @Override
            public void onResponse(Call<ChangPassword> call, Response<ChangPassword> response) {

                if (response.body().getStatus()==1){
                    alertDialog.cancel();
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    showView (ForgetPassLayoutNewPassword);
                    hidView(ForgetPassLayoutReset_password);
                }else {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangPassword> call, Throwable t) {
                alertDialog.cancel();

            }
        });

    }

    private void sendthCodClint() {

        apiServices.clientResetPassword(ForgetPassEtMail.getText().toString()).enqueue(new Callback<ChangPassword>() {
            @Override
            public void onResponse(Call<ChangPassword> call, Response<ChangPassword> response) {
if (response.body().getStatus()==1){
    alertDialog.cancel();
    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

    showView (ForgetPassLayoutNewPassword);
    hidView(ForgetPassLayoutReset_password);
}else {
    alertDialog.cancel();
    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
}

            }

            @Override
            public void onFailure(Call<ChangPassword> call, Throwable t) {
                alertDialog.cancel();

            }
        });

    }

    private void sendthCodRestaurant() {



        apiServices.restaurantResetPassword(ForgetPassEtMail.getText().toString()).enqueue(new Callback<ChangPassword>() {
            @Override
            public void onResponse(Call<ChangPassword> call, Response<ChangPassword> response) {
                if (response.body().getStatus()==1){
                    alertDialog.cancel();
                    showView (ForgetPassLayoutNewPassword);
                    hidView(ForgetPassLayoutReset_password);
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }else {
                    alertDialog.cancel();
                    Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ChangPassword> call, Throwable t) {

            }
        });
    }
}
