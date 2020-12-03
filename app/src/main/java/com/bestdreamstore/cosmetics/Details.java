package com.bestdreamstore.cosmetics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bestdreamstore.cosmetics.ADAPTERS.GetDataAdapter;
import com.bestdreamstore.cosmetics.Analytics.Analitycs_Resume;
import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;
import com.bestdreamstore.cosmetics.CONTROLLERS.RecyclerViewAdapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.SliderAdapter;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.bestdreamstore.cosmetics.LIBS.SliderUtils;
import com.bestdreamstore.cosmetics.LIBS.SliderVolleyRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.koushikdutta.async.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Details extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {


    private FirebaseAnalytics mFirebaseAnalytics;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    SliderAdapter viewPagerAdapter;
    JSONObject json_fin, json_json, images_extras;
    JSONArray feed_todos, feed_images_extras;
    String request_url, CATEGORIA;
    SliderUtils sliderUtils;
    int FEED_SIZE_GLOBAL;

    String ID_PRODUCTO_GLOBAL, NOMBRE_PRODUCTO_GLOBAL, IMAGEN_PRODUCTO_GLOBAL, CATEGORIA_PRODUCTO_GLOBAL, MARCA_PRODUCTO_GLOBAL, BAR_CODE_GLOBAL;

    String PRODUCTO_GLOBAL, PRECIO_PREMIUM_GLOBAL, COSTO_PRODUCTO_GLOBAL;
    int COSTO_ENVIO_GLOBAL, CANTIDAD_PRODUCTO_GLOBAL;



    boolean paginador = false;

    private Parcelable recyclerViewState;

    int LAST_POSITION_LIST = 0;


    GetDataAdapter GetDataAdapter2;
    Cart_Controller cart;


    List<GetDataAdapter> GetDataAdapter1;
    RecyclerView recyclerView;
    TextView num_img, categoria_titles;
    HTMLTextView precio_producto, datos_producto, encantar_show, tu_garantia_show, ver_mas_detalles_show, entregas_show;
    ImageView entregas, payments_method;
    Button ver_mas, add_cart_details, share_prod;





    String URL_GLOBAL = "";


    Toolbar mToolbar;
    Typeface papyrus, panton;



    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;


    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_2);


        /*ANALITYCS GOOGLE*/

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        /*ANALITYCS GOOGLE*/


        papyrus = ResourcesCompat.getFont(this, R.font.papyrus);
        panton = ResourcesCompat.getFont(this, R.font.panton_font);


        mToolbar = (Toolbar) findViewById(R.id.toolbar_details);
        mToolbar.inflateMenu(R.menu.menu_details);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        num_img = (TextView) findViewById(R.id.num_img);



        ver_mas = (Button) findViewById(R.id.ver_mas);
        ver_mas.setVisibility(View.GONE);



        add_cart_details = (Button) findViewById(R.id.add_cart_details);



        share_prod = (Button) findViewById(R.id.share_prod);








        precio_producto = (HTMLTextView) findViewById(R.id.precio_producto);
        precio_producto.setTypeface(panton);

        datos_producto = (HTMLTextView) findViewById(R.id.datos_producto);
        datos_producto.setTypeface(panton);


        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);

        String ID_PRODUCTO = getIntent().getExtras().getString("ID_PRODUCTO");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        rq = SliderVolleyRequest.getInstance(this).getRequestQueue();
        sliderImg = new ArrayList<>();
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_nonselected));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_selected));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        entregas = (ImageView)findViewById(R.id.entregas);
        Picasso.get().load("https://bestdream.store/Views/Default/img/app_android/payments_method.png").into(entregas);

        payments_method = (ImageView)findViewById(R.id.payments_method);
        Picasso.get().load("https://bestdream.store/Views/Default/img/app_android/entregas.png").into(payments_method);

        categoria_titles = (TextView)findViewById(R.id.categoria_titles);

        encantar_show = (HTMLTextView)findViewById(R.id.encantar_show);
        tu_garantia_show = (HTMLTextView)findViewById(R.id.tu_garantia_show);
        ver_mas_detalles_show = (HTMLTextView)findViewById(R.id.ver_mas_detalles_show);
        entregas_show = (HTMLTextView)findViewById(R.id.entregas_show);

        encantar_show.setTypeface(panton);
        tu_garantia_show.setTypeface(panton);
        ver_mas_detalles_show.setTypeface(panton);
        entregas_show.setTypeface(panton);
        categoria_titles.setTypeface(papyrus);




        sendRequest(ID_PRODUCTO);



    }






    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

    }






    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }







    public void sendRequest(String ID_PRODUCTO){

        request_url = "https://bestdream.store/Android/details_id/"+ID_PRODUCTO;

        Log.e("URL_DETAILS-----", request_url);


        final JsonArrayRequest searchMsg = new JsonArrayRequest(request_url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject obj = response.getJSONObject(i);


                        feed_todos = obj.getJSONArray("feed_todos");
                        json_fin = feed_todos.getJSONObject(0);
                        //Log.i("----feed_todos---", String.valueOf(feed_todos));
                        json_json = json_fin.getJSONObject("json");


                        /*IMAGEN PINCIPAL*/
                        sliderUtils = new SliderUtils();
                        String image_url_principal = json_json.getString("imagen");
                        ID_PRODUCTO_GLOBAL = json_json.getString("id");
                        NOMBRE_PRODUCTO_GLOBAL = json_json.getString("nombre");
                        IMAGEN_PRODUCTO_GLOBAL = json_json.getString("imagen");
                        CATEGORIA_PRODUCTO_GLOBAL = json_json.getString("categoria");
                        MARCA_PRODUCTO_GLOBAL = json_json.getString("marca");
                        BAR_CODE_GLOBAL = json_json.getString("bar_code");


                        int precio_mayoreo_url = json_json.getInt("precio_mayoreo");
                        int decimales_mayoreo_url = json_json.getInt("decimales_mayoreo");
                        PRECIO_PREMIUM_GLOBAL = String.valueOf(precio_mayoreo_url)+"."+String.valueOf(decimales_mayoreo_url);


                        int costo_producto = json_json.getInt("costo_producto");
                        int decimales_costo = json_json.getInt("decimales_costo");
                        COSTO_PRODUCTO_GLOBAL = String.valueOf(costo_producto)+"."+String.valueOf(decimales_costo);

                        Log.i("----COSTO PRODCUTO---", BAR_CODE_GLOBAL);



                        COSTO_ENVIO_GLOBAL = json_json.getInt("peso");
                        PRODUCTO_GLOBAL = json_json.getString("producto");
                        CANTIDAD_PRODUCTO_GLOBAL = 1;

                        sliderUtils.setSliderImageUrl(image_url_principal);

                        sliderImg.add(sliderUtils);
                        /*IMAGEN PINCIPAL*/

                        datos_producto.setText(NOMBRE_PRODUCTO_GLOBAL+"<br><hr>");
                        precio_producto.setText(
                                "<span style='color:red'>$"+PRECIO_PREMIUM_GLOBAL+ " - Tu Costo </span><br>"+
                                "<span style='font-size:13px;'>$"+json_json.getString("precio_publico")+ " - Precio Sugerido Reventa</span>"
                        );


                        encantar_show.setText(json_json.getString("materiales"));
                        tu_garantia_show.setText("¡Compra con confianza!</u><br><br>" +
                                "Queremos que estés completamente satisfecha(o) con tu compra. Devuelve cualquier producto dentro de los primeros 15 días de la entrega si no cumple con tu satisfacción.");
                        ver_mas_detalles_show.setText(json_json.getString("descripcion"));
                        entregas_show.setText("Entregas de 3-7 días<br>"+
                                "Una vez acreditado tu pedido, el tiempo de entrega es de 3 - 7 días hábiles. Tenemos cobertura en Todo México.<br>El costo de envío es de $120 MXN no importa el tamaño de tu Pedido<br>");





                        /*DETECTAMOS SI EL PRODUCTO ESTA EN CARRITO O NO*/
                        /*DETECTAMOS SI EL PRODUCTO ESTA EN CARRITO O NO*/
                        /*DETECTAMOS SI EL PRODUCTO ESTA EN CARRITO O NO*/


                        cart = new Cart_Controller(getApplicationContext());
                        if(cart.check_if_prod_in_cart(ID_PRODUCTO_GLOBAL)){


                            add_cart_details.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_ok, 0, 0, 0);


                        }else{


                            add_cart_details.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    cart = new Cart_Controller(getApplicationContext());
                                    boolean response = cart.ADD_CART_DETAILS_SKU(ID_PRODUCTO_GLOBAL);
                                    if (response) {

                                        add_cart_details.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_ok, 0, 0, 0);

                                    }
                                }
                            });


                        }




                        /*DETECTAMOS SI EL PRODUCTO ESTA EN CARRITO O NO*/
                        /*DETECTAMOS SI EL PRODUCTO ESTA EN CARRITO O NO*/
                        /*DETECTAMOS SI EL PRODUCTO ESTA EN CARRITO O NO*/




                        share_prod.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                share_img(IMAGEN_PRODUCTO_GLOBAL);


                            }
                        });



                        categoria_titles.setText(json_json.getString("categoria"));



                        images_extras = json_fin.getJSONObject("images");
                        feed_images_extras = images_extras.getJSONArray("feed");



                        for (int j = 0; j < feed_images_extras.length(); j++) {

                            sliderUtils = new SliderUtils();
                            JSONObject obj2 = feed_images_extras.getJSONObject(j);
                            sliderUtils.setSliderImageUrl(obj2.getString("imagen"));
                            sliderImg.add(sliderUtils);

                        }

                        sliderUtils = new SliderUtils();
                        sliderUtils.setSliderImageUrl("https://bestdream.store/Views/Default/img/img_slider_details2.jpg");
                        sliderImg.add(sliderUtils);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }




                num_img.setText(String.valueOf(sliderImg.size()));

                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/

                recyclerViewlayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                GetDataAdapter1 = new ArrayList<>();
                recyclerView = (RecyclerView) findViewById(R.id.recyclerview_2);
                recyclerView.setHasFixedSize(true);

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (!recyclerView.canScrollVertically(1)) {


                            /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/
                            recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                            /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/


                            ver_mas.setVisibility(View.VISIBLE);
                            ver_mas.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //Toast.makeText(Details.this, "Cargando..."+FEED_SIZE_GLOBAL, Toast.LENGTH_LONG).show();

                                    if(FEED_SIZE_GLOBAL == 0){


                                        URL_GLOBAL = "https://bestdream.store/Android/paginador_search/?";
                                        JSON_DATA_WEB_CALL(URL_GLOBAL , 10, 0);


                                    }else{

                                        JSON_DATA_WEB_CALL(URL_GLOBAL, 10, 0);

                                    }





                                }
                            });







                            //SI EL FEED ES DEMASIADO CHICO PARA SCROLL
                            //SI EL FEED ES DEMASIADO CHICO PARA SCROLL
                            //SI EL FEED ES DEMASIADO CHICO PARA SCROLL


                        }else{

                           ver_mas.setVisibility(View.GONE);

                        }
                    }
                });



                URL_GLOBAL = "https://bestdream.store/Android/ver_concidencias_details?categoria="+CATEGORIA_PRODUCTO_GLOBAL+"&";
                JSON_DATA_WEB_CALL(URL_GLOBAL , 10, 0);


                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/
                /*BUSQUEDA POR CATEGORIA*/





                viewPagerAdapter = new SliderAdapter(sliderImg, Details.this);
                viewPager.setAdapter(viewPagerAdapter);

                dotscount = viewPagerAdapter.getCount();




                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(Details.this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_nonselected));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_selected));



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error volley", "Error: " + error.getMessage());
                // hidePDialog();



            }
        });
        // Adding request to request queue


        SliderVolleyRequest.getInstance(this).addToRequestQueue(searchMsg);





    }










    public void JSON_DATA_WEB_CALL(String PETICION_URL, int limit_, int offset_){

        int offset_global = limit_ + offset_;
        String URL_FINAL = "";

        if(paginador){

            URL_FINAL = PETICION_URL+"limit="+limit_+"&offset="+offset_global;

        }else{

            URL_FINAL = PETICION_URL+"limit="+limit_+"&offset="+offset_;
            paginador = true;

        }

        Log.i("URL-FIN", URL_FINAL);
        String URL_ENCODE = reemplazar_espacios_blanco_url(URL_FINAL);


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
                            JSON_PARSE_DATA_AFTER_WEBCALL(array);
                            //Log.i("", "JSON" + array.length() + "---");

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










    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {


        Log.i("ARRAY_FIN", String.valueOf(array));
        Log.i("TAMAÑO_FEED", String.valueOf(array.length()));

        FEED_SIZE_GLOBAL = array.length();


        /*
        incrementar_contador();

        if(contador == 5){

            //Log.i("----CONTADOR:--", String.valueOf(contador));
            feed_marketing();
            contador = 0;


        }
        */


        for(int i=0;i<array.length();i++){
            // Get current json object



            GetDataAdapter2 = new GetDataAdapter();

            try {


                JSONObject json_base_2 = array.getJSONObject(i);

                String images_extras_txt = json_base_2.getString("images_extras");
                JSONObject images_extras = new JSONObject(images_extras_txt);
                JSONArray feed_img = images_extras.getJSONArray("feed");



                //Log.i("ID", json_base_2.getString("id")+" ::FEED"+feed_img);



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



                GetDataAdapter2.setmarketing("feed_normal");
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

                GetDataAdapter1.add(GetDataAdapter2);

            } catch (JSONException e) {

                e.printStackTrace();
            }




            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

            recyclerView.setAdapter(recyclerViewadapter);
            recyclerView.invalidate();
            recyclerViewadapter.notifyDataSetChanged();
            //recyclerView.smoothScrollToPosition(LAST_POSITION_LIST-7);



        }


    }







    public String reemplazar_espacios_blanco_url(String url){

        return url.replace(" ", "%20");


    }







    public void share_img(String url){

        Bitmap bitmap;

        bitmap = ConvertUrlToBitmap(url);

        try {

            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();


            File imagePath = new File(getApplicationContext().getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.bestdreamstore.cosmetics.fileprovider", newFile);

            if (contentUri != null) {


                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(Intent.createChooser(shareIntent, "Choose an app"));


            }

        } catch (IOException e) {
            e.printStackTrace();
        }




    }







    public static Bitmap ConvertUrlToBitmap(String src){
        try{
            URL url = new URL(src);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return image;
        }
        catch (IOException e)
        {
            return null;
        }

    }



}