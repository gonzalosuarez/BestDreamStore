package com.bestdreamstore.cosmetics;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Pedidos_Adapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.RecyclerView_Pedidos_Adapter;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import com.bestdreamstore.cosmetics.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Web_View_General extends AppCompatActivity {


    LinearLayout contetn_relative_layout;
    String que_ver = "";
    UserFunctions userFunctions;
    Toolbar mToolbar;
    Typeface papyrus, panton;
    private Parcelable recyclerViewState;
    RecyclerView.Adapter recyclerViewadapter;

    RecyclerView recyclerView;


    List<Get_Pedidos_Adapter> Get_Pedidos_Adapter_1;
    Get_Pedidos_Adapter Get_Pedidos_Adapter_2;
    String email_string;
    boolean pagados = false;
    Button button_pagados;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.webview);


         userFunctions = new UserFunctions();


         contetn_relative_layout = (LinearLayout)findViewById(R.id.contetn_relative_layout);



         papyrus = ResourcesCompat.getFont(this, R.font.papyrus);
         panton = ResourcesCompat.getFont(this, R.font.panton_font);


         mToolbar = (Toolbar) findViewById(R.id.toolbar_web_view);
         mToolbar.setNavigationIcon(R.drawable.icon_back_white);
         setSupportActionBar(mToolbar);
         getSupportActionBar().setTitle("Buscando......");
         mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.theme_icons));

         mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });



         Intent intent = getIntent();
         Bundle extras = intent.getExtras();

         if (extras != null) {

             que_ver = extras.getString("que_ver");

             if(que_ver.equals("ESTATUS_PEDIDO")){


                 if(userFunctions.isUserLoggedIn(this)){


                     Get_Pedidos_Adapter_1 = new ArrayList<>();
                    recyclerView = new RecyclerView(this);

                     email_string = userFunctions.get_user_email(getApplicationContext());







                     button_pagados = new Button(this);
                     button_pagados.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                     button_pagados.setGravity(Gravity.CENTER_HORIZONTAL);
                     button_pagados.setBackgroundColor(Color.RED);
                     button_pagados.setTextColor(Color.parseColor("#FFFFFF"));
                     button_pagados.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {

                              ver_pedidos();

                         }
                     });




                     TextView sdCardInfo = new TextView(this);
                     sdCardInfo.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                     sdCardInfo.setGravity(Gravity.CENTER);



                    //email_string = "p_carlos84@hotmail.com";



                     contetn_relative_layout.addView(sdCardInfo);
                     contetn_relative_layout.addView(button_pagados);
                     contetn_relative_layout.addView(recyclerView);







                     sdCardInfo.setText("Pedido creado con usuario: "+email_string);


                     ver_pedidos();







                 }else{

                     Intent i = new Intent(Web_View_General.this, Login.class);
                     startActivity(i);


                 }




             }


         }else{

             que_ver = extras.getString("que_ver");


         }










    }



    public void ver_pedidos(){



         this.Get_Pedidos_Adapter_1.size();
         this.Get_Pedidos_Adapter_1.clear(); ;


        if(pagados){

            pagados = false;
            button_pagados.setText("Ver Todos");

            ver_pedidos_final("pagados");


        }else{

            pagados = true;
            button_pagados.setText("Ver Los Pagados");
            ver_pedidos_final("no_pagados");



        }



    }







    public void ver_pedidos_final(String estatus_pedido){




        String URL_ENCODE = "https://bestdream.store/Android/ver_pedidos_email/?email="+email_string+"&estatus_pedido="+estatus_pedido;


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_ENCODE,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            JSONArray array = response.getJSONArray("feed");
                            String num_rows = response.getString("num_rows");

                            Log.i("NUM_ROWS", " -- " + num_rows + "---");
                            getSupportActionBar().setTitle(num_rows+" Pedidos");



                            for(int i=0;i<array.length();i++){
                                // Get current json object


                                Get_Pedidos_Adapter_2 = new Get_Pedidos_Adapter();

                                try {


                                    JSONObject json_base_2 = array.getJSONObject(i);


                                    Get_Pedidos_Adapter_2.setid_pedido(json_base_2.getString("id_pedido"));
                                    Get_Pedidos_Adapter_2.setfecha_aprovacion(json_base_2.getString("fecha_aprovacion"));
                                    Get_Pedidos_Adapter_2.setrastreo(json_base_2.getString("rastreo"));
                                    Get_Pedidos_Adapter_2.setkey_pedido(json_base_2.getString("key_pedido"));
                                    Get_Pedidos_Adapter_2.setestatus_pedido(json_base_2.getString("estatus_pedido"));



                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }



                                Get_Pedidos_Adapter_1.add(Get_Pedidos_Adapter_2);
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
                                recyclerViewadapter = new RecyclerView_Pedidos_Adapter(Get_Pedidos_Adapter_1,  getApplicationContext());

                                recyclerView.setAdapter(recyclerViewadapter);



                            }







                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){


                    }
                }
        );

        requestQueue.add(jsonObjectRequest);







    }








}
