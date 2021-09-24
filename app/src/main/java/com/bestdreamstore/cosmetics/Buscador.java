package com.bestdreamstore.cosmetics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bestdreamstore.cosmetics.ADAPTERS.GetDataAdapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.RecyclerViewAdapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.SliderAdapter;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.LIBS.DownloadImageTask;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.SliderUtils;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Buscador extends AppCompatActivity {


    Toolbar mToolbar;

    private static final int CODIGO_VOZ = 3;
    List<GetDataAdapter> GetDataAdapter1;
    RecyclerView recyclerView;
    UserFunctions userFunctions;
    EditText search;
    ImageView buscador_icon, voz_texto;
    LinearLayout BARRA_BUSQUEDA;
    DatabaseHandler db;


    int offset_global = 0;
    private Parcelable recyclerViewState;

    GetDataAdapter GetDataAdapter2;

    SliderAdapter viewPagerAdapter;
    LinearLayout sliderDotspanel, slider_layout;
    ViewPager viewPager;
    private int dotscount;
    private ImageView[] dots;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    SliderUtils sliderUtils;
    HTMLTextView negative_result;
    EditText palabra_clave_edittext;

    boolean paginador = false;
    int FEED_SIZE_GLOBAL;

    MenuItem menuItem;
    RecyclerView.Adapter recyclerViewadapter;
    NavigationView navigationView;
    DrawerLayout drawer;


    int contador = 0;
    String URL_GLOBAL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscador);

        Typeface panton;
        panton = ResourcesCompat.getFont(this, R.font.panton_font);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar_buscador);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        negative_result = (HTMLTextView)findViewById(R.id.negative_result);
        negative_result.setTypeface(panton);


        palabra_clave_edittext = (EditText)findViewById(R.id.palabra_clave_edittext);




        /*
        buscador_icon = (ImageView)findViewById(R.id.buscador_icon);
        buscador_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                limpiar_busqueda();

                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                URL_GLOBAL = "https://bestdream.store/Android/like/?like="+palabra_clave_edittext.getText().toString()+"&";
                JSON_DATA_WEB_CALL(URL_GLOBAL, 20, offset_global);


            }
        });


         */



        voz_texto = (ImageView)findViewById(R.id.voz_texto);
        voz_texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                capturarVoz();


            }
        });

















        palabra_clave_edittext.addTextChangedListener(new TextWatcher() {

            private Timer timer=new Timer();
            private final long DELAY = 1000; // milliseconds

            @Override
            public void afterTextChanged(final Editable s) {


                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                limpiar_busqueda();
                                URL_GLOBAL = "https://bestdream.store/Android/like/?like="+s.toString()+"&";
                                JSON_DATA_WEB_CALL(URL_GLOBAL, 20, offset_global);


                            }
                        },
                        DELAY
                );

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {




            }
        });








        GetDataAdapter1 = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(recyclerViewlayoutManager);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (!recyclerView.canScrollVertically(1)) {

                    /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/
                    recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/

                    Toast.makeText(Buscador.this, "Cargando.....", Toast.LENGTH_LONG).show();

                    if(FEED_SIZE_GLOBAL == 0){


                        URL_GLOBAL = "https://bestdream.store/Android/paginador_search/?";
                        JSON_DATA_WEB_CALL(URL_GLOBAL , 20, offset_global);


                    }else{

                        JSON_DATA_WEB_CALL(URL_GLOBAL, 20, offset_global);

                    }




                }
            }
        });





    }








    @Override
    public void onBackPressed() {
        finish();
    }





    public void JSON_DATA_WEB_CALL(String PETICION_URL, int limit_, int offset_){

        negative_result.setText("Buscando......");


        String URL_FINAL = "";

        if(paginador){

            offset_global = limit_ + offset_;
            URL_FINAL = PETICION_URL+"limit="+limit_+"&offset="+offset_global;

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

                            Log.i("ARRAY_FINAL", "JSON" + array + "---");
                            Log.i("ARRAY_COUNT", "count" + cout_busqueda_total + "---");


                            JSON_PARSE_DATA_AFTER_WEBCALL(array, cout_busqueda_total);


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

        negative_result.setText("");

        Log.i("COUNTERS_COUNT_TOTAL", "count" + count_busqueda_total + "---");
        Log.i("COUNTERS_OFFSET_GLOBAL", "OFFSET" + offset_global + "---");



        if(offset_global >= count_busqueda_total){

            negative_result.setText("No Hay Resultados de tu Busqueda");

        }else{

            negative_result.setText("");

        }


        FEED_SIZE_GLOBAL = array.length();

        incrementar_contador();



        for(int i=0;i<array.length();i++){
            // Get current json object


            GetDataAdapter2 = new GetDataAdapter();

            try {


                JSONObject json_base_2 = array.getJSONObject(i);

                String images_extras_txt = json_base_2.getString("images_extras");
                JSONObject images_extras = new JSONObject(images_extras_txt);
                JSONArray feed_img = images_extras.getJSONArray("feed");

                Log.i("ID", json_base_2.getString("id")+" ::FEED"+feed_img);

                ArrayList<String> arr_fin_extras = new ArrayList<String>();

                /*AGREGAMNOS LA PRINCIPAL*/
                arr_fin_extras.add(json_base_2.getString("imagen"));
                /*AGREGAMNOS LA PRINCIPAL*/



                if(feed_img.length() > 0){

                    for(int x=0;x<feed_img.length();x++){

                        JSONObject json_base_3 = feed_img.getJSONObject(x);
                        //Log.i("LLEVA_IMAGEN_EXTRA::", json_base_3.getString("imagen"));
                        arr_fin_extras.add(json_base_3.getString("imagen"));

                    }

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






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == CODIGO_VOZ  && resultCode == RESULT_OK && data != null){


            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String txt_fin = result.get(0);
            palabra_clave_edittext.setText(txt_fin);

            limpiar_busqueda();

            URL_GLOBAL = "https://bestdream.store/Android/like/?like="+txt_fin+"&";
            JSON_DATA_WEB_CALL(URL_GLOBAL, 20, offset_global);


        }
    }







    public void incrementar_contador(){
        Log.i("----CONTADOR:--", String.valueOf(contador));
        contador++;
    }




    public String reemplazar_espacios_blanco_url(String url){

        return url.replace(" ", "%20");


    }



    public void limpiar_busqueda(){


        offset_global = 0;
        GetDataAdapter1.clear();
        paginador = false;


    }






    private void capturarVoz(){
        Intent intent = new Intent(RecognizerIntent
                .ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, CODIGO_VOZ);
        } else
        {
            Log.e("ERROR","Su dispositivo no admite entrada de voz");
        }
    }










}



