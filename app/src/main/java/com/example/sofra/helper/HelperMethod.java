package com.example.sofra.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class HelperMethod {


    private static SweetAlertDialog pDialog;

    public static void replaceFragment(Fragment fragment, int id, FragmentTransaction fragmentTransaction) {
        FragmentTransaction transaction = fragmentTransaction;
            transaction.replace(id, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

    }

    public static RequestBody convertToRequestBody(String part) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
        return requestBody;
    }

    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {

        File file = new File(pathImageFile);

        RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);

        return Imagebody;
    }


    public static void hidView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }



    public static void openAlbum(int Counter, Context context, final ArrayList<AlbumFile> ImagesFiles, Action<ArrayList<AlbumFile>> action) {
        Album album = new Album();
        Album.initialize(AlbumConfig.newBuilder(context)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.ENGLISH).build());
        album.image(context)// Image and video mix options.
                .multipleChoice()// Multi-Mode, Single-Mode: singleChoice().
                .columnCount(3) // The number of columns in the page list.
                .selectCount(Counter)  // Choose up to a few images.
                .camera(true) // Whether the camera appears in the Item.
                .checkedList(ImagesFiles) // To reverse the list.
                .widget(
                        Widget.newLightBuilder(context)
                                .title("")
                                .statusBarColor(Color.WHITE) // StatusBar color.
                                .toolBarColor(Color.WHITE) // Toolbar color.
                                .navigationBarColor(Color.WHITE) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector(Color.BLUE, Color.GREEN) // Image or video selection box.
                                .bucketItemCheckSelector(Color.RED, Color.YELLOW) // Select the folder selection box.
                                .build()
                )
                .onResult(action)
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
// The user canceled the operation.
                    }
                })
                .start();
    }

public static void showSweetDialog(Context context , int PROGRESS_TYPE , String color ,
                                   String title,Boolean canceble){
     pDialog = new SweetAlertDialog(context,PROGRESS_TYPE);
    pDialog.getProgressHelper().setBarColor(Color.parseColor(color));
    pDialog.setTitleText(title);
    pDialog.setCancelable(canceble);
    pDialog.show();
}

public static void closeDialog (){

        pDialog.cancel();
}


}
