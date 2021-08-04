package com.bestdreamstore.cosmetics;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;
import com.bestdreamstore.cosmetics.CONTROLLERS.PoopUp_General;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.koushikdutta.ion.Ion;
import com.bestdreamstore.cosmetics.LIBS.DownloadImageTask;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static android.content.ContentValues.TAG;


public class Herramientas_Ventas extends Activity {


    UserFunctions userFunctions;
    PoopUp_General poop_up_general = new PoopUp_General(this);

    Toolbar mToolbar;
    HTMLTextView texto_herr_1, text2;
    Button ver_paginas_button;


    private static int TIME_OUT = 5000;

    int test;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.herramientas_ventas);



        mToolbar = (Toolbar) findViewById(R.id.toolbar_herramientas_ventas);
        mToolbar.inflateMenu(R.menu.menu_details);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        ImageView image_ofertas = (ImageView)findViewById(R.id.pagina_web);
        new DownloadImageTask(getApplicationContext(), image_ofertas, "https://bestdream.store/Views/Default/img/reseller/1.jpg").execute();



        ver_paginas_button = (Button)findViewById(R.id.ver_paginas_button);
        ver_paginas_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });




        texto_herr_1 = (HTMLTextView)findViewById(R.id.texto_herr_1);
        texto_herr_1.setText("Aprovecha los beneficios de tener Tu \"Propia Web\" que muestra todo el catálogo de productos y marcas para potencializar las redes sociales y su lista de contactos!\n" +
                "\n" + "\n" +
                "Protegemos Tu información de contacto, para que el cliente unicamente tenga acceso a la información del producto por medio de tu Whatsapp.");







    }






}
