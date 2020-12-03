package com.bestdreamstore.cosmetics.CONTROLLERS;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bestdreamstore.cosmetics.Delivery;
import com.bestdreamstore.cosmetics.LIBS.DownloadImageTask;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import com.bestdreamstore.cosmetics.R;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.koushikdutta.ion.Ion;


public class PoopUp_General {


    Context context;
    public static UserFunctions userfunctions;
    public static View view;
    PopupWindow mRecordWindow;



    public PoopUp_General(Context context){

        super();
        this.context = context;

    }




    public void SHOW_POOP_ESPERE_UN_MOMENTO(final Context ctx) {


        view = View.inflate(ctx, R.layout.poopup_general, null);


        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(view, 17, 0, 0);
        mRecordWindow.setFocusable(true);
        mRecordWindow.setOutsideTouchable(false);
        mRecordWindow.setTouchable(false);



        ImageView img_espere = new ImageView(ctx);
        Ion.with(img_espere).load("https://bestdream.store/Views/Default/img/loading_3.gif");
        img_espere.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));



        LinearLayout content_window = (LinearLayout)view.findViewById(R.id.content_window_poopup_general);
        content_window.addView(img_espere);





    }







    public void CLOSE_POOP() {


        mRecordWindow.dismiss();


    }





}
