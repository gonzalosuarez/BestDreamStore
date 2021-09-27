package com.bestdreamstore.cosmetics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import com.bestdreamstore.cosmetics.LIBS.Update_App_Google_Play;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;


public class Inicio extends Activity {


    UserFunctions userFunctions;

    Update_App_Google_Play Actualizar_App;




    TextView txt_versions;



    private AppUpdateManager appUpdateManager;



    int test;
    int version;
    String version_cd;

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


*/



        Intent i = new Intent(Inicio.this, MainActivity.class);
        startActivity(i);
        finish();








    }





}
