package com.bestdreamstore.cosmetics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.TextView;

import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import com.bestdreamstore.cosmetics.LIBS.Update_App_Google_Play;
import com.google.android.play.core.appupdate.AppUpdateManager;

public class Inicio extends Activity {


    UserFunctions userFunctions;

    Update_App_Google_Play Actualizar_App;




    TextView txt_versions;



    private AppUpdateManager appUpdateManager;
    public static final String CAHNNEL_ID = "default_channel_id";




    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);










        /*
        appUpdateManager = AppUpdateManagerFactory.create(this);
        Actualizar_App.setFlexibleUpdate(appUpdateManager, this);

         txt_versions = (TextView) findViewById(R.id.txt_versions);




        userFunctions = new UserFunctions();


Cart_Controller.Build_Counter_Cart(getApplicationContext());

*/

 Intent i = new Intent(Inicio.this, MainActivity.class);
        startActivity(i);
        finish();








    }



}
