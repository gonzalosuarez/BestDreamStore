package com.bestdreamstore.cosmetics;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bestdreamstore.cosmetics.ADAPTERS.GetDataAdapter;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Marca_Adapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CategoriasMarcas extends AppCompatActivity {


    List<GetDataAdapter> GetDataAdapter1;
    GetDataAdapter GetDataAdapter2;


    RecyclerView recyclerView;


    private FirebaseAnalytics mFirebaseAnalytics;


    private Parcelable recyclerViewState;
    private ImageButton open_cart;


    String marca, categoria_estatus;
    Toolbar mToolbar;
    ImageView logo_marca;
    Button btn_menu_principal, btn_menu_marca;



    TableLayout table_base;
    LinearLayout menu_content;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias_marcas);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mToolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        open_cart = (ImageButton) findViewById(R.id.open_cart);
        open_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Cart_Controller.SHOW_POOP_UP_CART(CategoriasMarcas.this);

            }
        });



        table_base = (TableLayout) findViewById(R.id.tabla_datos);
        menu_content = (LinearLayout) findViewById(R.id.menu_content);









        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if (extras != null) {

                categoria_estatus = extras.getString("categoria");
                marca = extras.getString("marca");


        }else{

            categoria_estatus = "null";

        }


        Log.i("CATEGORIA::", categoria_estatus);
        Log.i("MARCA::", marca);



        btn_menu_principal = new Button(this);
        btn_menu_principal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        btn_menu_principal.setTextColor(Color.parseColor("#ffffff"));
        btn_menu_principal.setText(" << Regresar a Menú Principal");
        btn_menu_principal.setBackgroundResource(R.drawable.style_button_base);
        btn_menu_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        btn_menu_marca = new Button(this);
        btn_menu_marca.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        btn_menu_marca.setTextColor(Color.parseColor("#ffffff"));
        btn_menu_marca.setText(" << Menú "+marca);
        btn_menu_marca.setBackgroundResource(R.drawable.style_button_base);
        btn_menu_marca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSON_DATA_WEB_CALL_CATEGORIAS("https://bestdream.store/Android/ver_categorias_marca/"+marca);

            }
        });




        logo_marca = (ImageView) findViewById(R.id.logo_marca);

        if(marca.equals("BISSU")){

            Picasso.get().load("https://bestdream.store/Views/Default/img/marcas/bissu.jpg").into(logo_marca);

        }else if(marca.equals("Beauty Creations")){

            Picasso.get().load("https://bestdream.store/Views/Default/img/marcas/bc.jpg").into(logo_marca);

        }






        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/



        JSON_DATA_WEB_CALL_CATEGORIAS("https://bestdream.store/Android/ver_categorias_marca/"+marca);



    }








    @SuppressLint("ResourceAsColor")
    public void JSON_DATA_WEB_CALL_CATEGORIAS(String PETICION_URL){


        menu_content.removeAllViews();

        menu_content.addView(btn_menu_principal);
        btn_menu_principal.setText("Buscando............");



        table_base.removeAllViews();


        String URL_ENCODE = reemplazar_espacios_blanco_url(PETICION_URL);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_ENCODE,
                null,
                new Response.Listener<JSONObject>() {

                    @SuppressLint("ResourceAsColor")
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray array = response.getJSONArray("feed_categorias");


                            for(int i=0;i<array.length();i++){

                                JSONObject json_base_2 = array.getJSONObject(i);

                                final String CATEGORIA_ = json_base_2.getString("nombre_categoria");
                                final String MARCA_ = json_base_2.getString("marca_perteneciente");


                                table_base = (TableLayout) findViewById(R.id.tabla_datos);
                                TableRow tr =  new TableRow(getApplicationContext());
                                tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

                                TextView txtGeneric = new TextView(getApplicationContext());
                                txtGeneric.setText(CATEGORIA_);
                                txtGeneric.setTextSize(30);
                                tr.addView(txtGeneric);
                                table_base.addView(tr);



                                btn_menu_principal.setText("Regresar a Menú Principal");


                                txtGeneric.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        JSON_DATA_WEB_CALL_SUBCATEGORIAS(CATEGORIA_, MARCA_);


                                    }
                                });



                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Log.e("ARRAY OPBJECT ERROR", String.valueOf(error));

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);



    }










    public void JSON_DATA_WEB_CALL_SUBCATEGORIAS(String categoria, String marca){

        menu_content.removeAllViews();

        menu_content.addView(btn_menu_principal);
        menu_content.addView(btn_menu_marca);


        btn_menu_principal.setText("Buscando............");



        table_base.removeAllViews();

        String categoria_fin = reemplazar_espacios_blanco_url(categoria);
        String marca_fin = reemplazar_espacios_blanco_url(marca);

        String URL_ = "https://bestdream.store/Android/ver_subcategorias_categoria/"+categoria_fin+"?marca="+marca_fin;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_,
                null,
                new Response.Listener<JSONObject>() {

                    @SuppressLint("ResourceAsColor")
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray array = response.getJSONArray("feed_sub_categorias");


                            for(int i=0;i<array.length();i++){

                                JSONObject json_base_2 = array.getJSONObject(i);

                                final String NOMBR_SUBCATEGORIA = json_base_2.getString("nombre_subcategoria");


                                table_base = (TableLayout) findViewById(R.id.tabla_datos);

                                TableRow tr =  new TableRow(getApplicationContext());
                                tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

                                TextView txtGeneric = new TextView(getApplicationContext());
                                txtGeneric.setText(json_base_2.getString("nombre_subcategoria"));
                                txtGeneric.setTextSize(30);
                                tr.addView(txtGeneric);
                                table_base.addView(tr);


                                btn_menu_principal.setText("Regresar a Menú Principal");




                                txtGeneric.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String search_cat = reemplazar_espacios_blanco_url(NOMBR_SUBCATEGORIA);
                                        String URL_GLOBAL = "https://bestdream.store/Android/ver_concidencias_details?categoria="+search_cat+"&";

                                        intent_search(URL_GLOBAL);
                                        finish();



                                    }
                                });



                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Log.e("ARRAY OPBJECT ERROR", String.valueOf(error));

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);



    }












    public String reemplazar_espacios_blanco_url(String url){

        return url.replace(" ", "%20");


    }





    public void  intent_search(String URL_){

        Intent i = new Intent(CategoriasMarcas.this, Searchs.class);
        i.putExtra("URL_GLOBAL", URL_);
        startActivity(i);



    }





}
