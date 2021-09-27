package com.bestdreamstore.cosmetics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bestdreamstore.cosmetics.ADAPTERS.GetDataAdapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;
import com.bestdreamstore.cosmetics.CONTROLLERS.RecyclerViewAdapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.SliderAdapter;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.LIBS.BadgeCartIcon;
import com.bestdreamstore.cosmetics.LIBS.DownloadImageTask;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.bestdreamstore.cosmetics.LIBS.SliderUtils;
import com.bestdreamstore.cosmetics.LIBS.SliderVolleyRequest;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import android.os.StrictMode;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;



import com.google.firebase.analytics.FirebaseAnalytics;


public class Searchs extends AppCompatActivity {


    List<GetDataAdapter> GetDataAdapter1;
    GetDataAdapter GetDataAdapter2;


    RecyclerView recyclerView;


    private FirebaseAnalytics mFirebaseAnalytics;


    private Parcelable recyclerViewState;
    private ImageButton open_cart;






    int offset_global = 0;
    Toolbar mToolbar;
    boolean paginador = false;
    int FEED_SIZE_GLOBAL;
    int contador = 0;
    String URL_GLOBAL = "";

    RecyclerView.Adapter recyclerViewadapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchs);

        /*ANALITYCS GOOGLE*/
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        /*ANALITYCS GOOGLE*/


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

                Cart_Controller.SHOW_POOP_UP_CART(Searchs.this);

            }
        });





        GetDataAdapter1 = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_searchs);

        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (!recyclerView.canScrollVertically(1)) {

                    /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/
                    recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/

                    Toast.makeText(Searchs.this, "Cargando.....", Toast.LENGTH_LONG).show();

                    if(FEED_SIZE_GLOBAL == 0){


                        URL_GLOBAL = "https://bestdream.store/Android/paginador_search/?";
                        JSON_DATA_WEB_CALL(URL_GLOBAL , 20, offset_global);


                    }else{

                        JSON_DATA_WEB_CALL(URL_GLOBAL, 20, offset_global);

                    }




                }
            }
        });








        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/


        String get_extras = get_extras_and_search();

        if(!get_extras.equals("null")){

            Log.i("EXTRAS:::", "----" + get_extras + "---");
            URL_GLOBAL = get_extras;
            JSON_DATA_WEB_CALL(URL_GLOBAL, 10, 0);


        }else{


            Log.i("EXTRAS:::", "----NO HAY---");
            /*INICIAMOS SI NO HAY EXTRAS*/
            URL_GLOBAL = "https://bestdream.store/Android/paginador_search/?";
            JSON_DATA_WEB_CALL(URL_GLOBAL, 10, offset_global);
            /*INICIAMOS SI NO HAY EXTRAS*/


        }


        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/
        /*RECIBIMOS CATEGORIAS EN INTENT O INICIAMOS*/








    }









    @Override
    public void onBackPressed() {
        finish();

    }






    public void JSON_DATA_WEB_CALL(String PETICION_URL, int limit_, int offset_){


        String URL_FINAL = "";

        if(paginador){

            offset_global = limit_ + offset_;
            URL_FINAL = PETICION_URL+"limit="+limit_+"&offset="+offset_global;

            //https://bestdream.store/Android/paginador_search/?limit=7&offset=0

        }else{

            URL_FINAL = PETICION_URL+"limit="+limit_+"&offset="+offset_;
            paginador = true;

        }



        String URL_ENCODE = reemplazar_espacios_blanco_url(URL_FINAL);
        Log.i("URL_ENCODE", URL_FINAL);


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



                            JSONArray array = response.getJSONArray("feed_todos");
                            int cout_busqueda_total = response.getInt("num_rows");


                            JSON_PARSE_DATA_AFTER_WEBCALL(array, cout_busqueda_total);


                            Log.i("ARRAY_FINAL", "JSON" + array + "---");
                            //Log.i("ARRAY_COUNT", "count" + cout_busqueda_total + "---");


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








    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array, int count_busqueda_total) {



        Log.i("COUNTERS_COUNT_TOTAL", "count" + count_busqueda_total + "---");
        Log.i("COUNTERS_OFFSET_GLOBAL", "OFFSET" + offset_global + "---");






        if(offset_global >= count_busqueda_total){

           // GetDataAdapter2.setmarketing("te_podria_interesar");
            contador = 6;
            Log.i("COUNTERS_G_ES_MAYOR", "LISTO!!! " + offset_global + "-- >= --"+ count_busqueda_total);
            offset_global = 0;


        }



        FEED_SIZE_GLOBAL = array.length();

        incrementar_contador();

        for(int i=0;i<array.length();i++){
            // Get current json object


            GetDataAdapter2 = new GetDataAdapter();

            try {


                JSONObject json_base_2 = array.getJSONObject(i);

                String images_extras_txt = json_base_2.getString("images_extras");
                String categoria = json_base_2.getString("categoria");


                JSONObject images_extras = new JSONObject(images_extras_txt);
                JSONArray feed_img = images_extras.getJSONArray("feed");

                Log.i("ID", json_base_2.getString("id")+" ::FEED"+feed_img);

                ArrayList<String> arr_fin_extras = new ArrayList<String>();

                /*AGREGAMNOS LA PRINCIPAL*/
                arr_fin_extras.add(json_base_2.getString("imagen"));
                /*AGREGAMNOS LA PRINCIPAL*/


                Log.i("----BAR_CODE--", "-----"+json_base_2.getString("bar_code"));




                if(feed_img.length() > 0){

                    for(int x=0;x<feed_img.length();x++){

                        JSONObject json_base_3 = feed_img.getJSONObject(x);
                        //Log.i("LLEVA_IMAGEN_EXTRA::", json_base_3.getString("imagen"));
                        arr_fin_extras.add(json_base_3.getString("imagen"));

                    }

                }



                if(categoria.equals("LIVERPOOL")){


                    GetDataAdapter2.setmarketing("LIVERPOOL");



                }


                Random rand = new Random();
                String random = arr_fin_extras.get(rand.nextInt(arr_fin_extras.size()));


                if(contador == 5){

                    GetDataAdapter2.setmarketing("marketing");
                    Log.i("----CONTADOR:--", "Llego a: "+String.valueOf(contador));
                    contador = 0;


                }else if(contador == 6){


                    GetDataAdapter2.setmarketing("te_podria_interesar");
                    contador = 0;



                }else{


                    GetDataAdapter2.setmarketing("feed_normal");

                }


                GetDataAdapter2.setnombre(json_base_2.getString("nombre"));
                GetDataAdapter2.setImageServerUrl(random);
                GetDataAdapter2.setid(json_base_2.getString("id"));
                GetDataAdapter2.setprecio_mayoreo(json_base_2.getString("precio_mayoreo"));
                GetDataAdapter2.setdecimales_mayoreo(json_base_2.getString("decimales_mayoreo"));
                GetDataAdapter2.setcosto_producto(json_base_2.getString("costo_producto"));
                GetDataAdapter2.setdecimales_costo(json_base_2.getString("decimales_costo"));
                GetDataAdapter2.setpeso(json_base_2.getString("peso"));
                GetDataAdapter2.setmarca(json_base_2.getString("marca"));
                GetDataAdapter2.setproducto(json_base_2.getString("producto"));
                GetDataAdapter2.setcategoria(json_base_2.getString("categoria"));
                GetDataAdapter2.setbar_code(json_base_2.getString("bar_code"));
                GetDataAdapter2.setexistencia(json_base_2.getInt("existencia"));
                GetDataAdapter2.setinventario(json_base_2.getInt("inventario"));



            } catch (JSONException e) {

                e.printStackTrace();
            }



            GetDataAdapter1.add(GetDataAdapter2);


            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1,  this);

            recyclerView.setAdapter(recyclerViewadapter);



        }





    }









    public void incrementar_contador(){
        Log.i("----CONTADOR:--", String.valueOf(contador));
        contador++;
    }




    public String reemplazar_espacios_blanco_url(String url){

        return url.replace(" ", "%20");


    }




    public String get_extras_and_search(){

        String categoria_entrante = "null";
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("URL_GLOBAL")) {

                categoria_entrante = extras.getString("URL_GLOBAL");

            }
        }else{

            categoria_entrante = "null";

        }


        return categoria_entrante;

    }






}
