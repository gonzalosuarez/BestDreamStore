package com.bestdreamstore.cosmetics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bestdreamstore.cosmetics.ADAPTERS.GetDataAdapter;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Marca_Adapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;
import com.bestdreamstore.cosmetics.CONTROLLERS.RecyclerViewAdapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.RecyclerViewMarcasAdapter;
import com.bestdreamstore.cosmetics.CONTROLLERS.SliderAdapter;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.LIBS.DownloadImageTask;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.bestdreamstore.cosmetics.LIBS.SliderUtils;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import android.os.StrictMode;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.analytics.FirebaseAnalytics;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ImageView image_logo_menu;


    List<GetDataAdapter> GetDataAdapter1;
    List<Get_Marca_Adapter> Get_Marca_Adapter1;
    RecyclerView recyclerView, recyclerView_Marcas;
    UserFunctions userFunctions;
    EditText search;
    ImageButton btn_search;
    LinearLayout BARRA_BUSQUEDA;
    DatabaseHandler db;

    private FirebaseAnalytics mFirebaseAnalytics;
    Menu Menu_Global;


    private int count_cart = 0;
    private Parcelable recyclerViewState;


    int offset_global = 0;


    GetDataAdapter GetDataAdapter2;
    Get_Marca_Adapter Get_Marca_Adapter2;

    SliderAdapter viewPagerAdapter;
    LinearLayout sliderDotspanel, slider_layout;
    ViewPager viewPager;
    private int dotscount;
    private ImageView[] dots;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    SliderUtils sliderUtils;
    private CircleImageView labios, unas, eyes, rostro;


    boolean paginador = false;
    int FEED_SIZE_GLOBAL;

    MenuItem menuItem;
    RecyclerView.Adapter recyclerViewadapter;
    RecyclerView.Adapter recyclerViewadapter_marca;

    NavigationView navigationView;
    DrawerLayout drawer;
    int monedero_electronico_int = 0;


    int contador = 0;    String URL_GLOBAL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*INICIALIZAMOS CARRITO*/
           //Cart_Controller.update_icon_cart(getApplicationContext());
        /*INICIALIZAMOS CARRITO*/




        userFunctions = new UserFunctions();
        db = new DatabaseHandler(getApplicationContext());



        /*ANALITYCS GOOGLE*/
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        /*ANALITYCS GOOGLE*/




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        /*SLIDER PRINCIPAL*/
        /*SLIDER PRINCIPAL*/
        /*SLIDER PRINCIPAL*/
        /*SLIDER PRINCIPAL*/

        slider_layout = (LinearLayout) findViewById(R.id.slider_layout);
        sliderImg = new ArrayList<>();
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager_main);
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





        sliderUtils = new SliderUtils();
        sliderUtils.setSliderImageUrl("https://bestdream.store/Views/Default/img/app_android/banner_mainactivity/1.jpg");
        sliderImg.add(sliderUtils);

        sliderUtils = new SliderUtils();
        sliderUtils.setSliderImageUrl("https://bestdream.store/Views/Default/img/app_android/banner_mainactivity/2.jpg");
        sliderImg.add(sliderUtils);

        sliderUtils = new SliderUtils();
        sliderUtils.setSliderImageUrl("https://bestdream.store/Views/Default/img/app_android/banner_mainactivity/3.jpg");
        sliderImg.add(sliderUtils);


        sliderUtils = new SliderUtils();
        sliderUtils.setSliderImageUrl("https://bestdream.store/Views/Default/img/app_android/banner_mainactivity/4.jpg");
        sliderImg.add(sliderUtils);



        sliderUtils = new SliderUtils();
        sliderUtils.setSliderImageUrl("https://bestdream.store/Views/Default/img/app_android/banner_mainactivity/5.jpg");
        sliderImg.add(sliderUtils);



        viewPagerAdapter = new SliderAdapter(sliderImg, MainActivity.this);
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();

        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(MainActivity.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_nonselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 3, 8, 20);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_selected));





        /*SLIDER PRINCIPAL*/
        /*SLIDER PRINCIPAL*/
        /*SLIDER PRINCIPAL*/
        /*SLIDER PRINCIPAL*/






        GetDataAdapter1 = new ArrayList<>();
        Get_Marca_Adapter1 = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);









        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (!recyclerView.canScrollVertically(1)) {

                    /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/
                    recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    /*MANTENEMOS LA POSITION ORIGINAL DEL SCROLL*/

                    Toast.makeText(MainActivity.this, "Cargando.....", Toast.LENGTH_LONG).show();

                    if(FEED_SIZE_GLOBAL == 0){


                        URL_GLOBAL = "https://bestdream.store/Android/paginador_search/?";
                        JSON_DATA_WEB_CALL(URL_GLOBAL , 20, offset_global);


                    }else{

                        JSON_DATA_WEB_CALL(URL_GLOBAL, 20, offset_global);

                    }




                }
            }
        });





        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/




            /*INICIAMOS SI NO HAY EXTRAS*/
            URL_GLOBAL = "https://bestdream.store/Android/paginador_search/?";
            JSON_DATA_WEB_CALL(URL_GLOBAL, 20, offset_global);

            JSON_DATA_WEB_CALL_MARCAS("https://bestdream.store/Android/ver_todas_las_marcas", 100, offset_global);
            /*INICIAMOS SI NO HAY EXTRAS*/






        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/




        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        Button login_logout = (Button) header.findViewById(R.id.login_logout);

        Button monedero_get = (Button)header.findViewById(R.id.monedero_get);



        recyclerView_Marcas = (RecyclerView)header.findViewById(R.id.recyclerview_marcas);
        recyclerView_Marcas.setHasFixedSize(true);


        ImageView image_ofertas = (ImageView)header.findViewById(R.id.image_ofertas);
        new DownloadImageTask(getApplicationContext(), image_ofertas, "https://bestdream.store/Views/Default/img/oferta.png").execute();
        image_ofertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                URL_GLOBAL = "https://bestdream.store/Android/ver_concidencias_details?categoria=OFERTAS_SEMANALES&";
                drawer.closeDrawer(GravityCompat.START);
                intent_search(URL_GLOBAL);



            }
        });




        ImageView image_nuevas_llegadas = (ImageView)header.findViewById(R.id.nuevas_llegadas_img);
        new DownloadImageTask(getApplicationContext(), image_nuevas_llegadas, "https://bestdream.store/Views/Default/img/nuevas_llegadas.png").execute();
        image_nuevas_llegadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                URL_GLOBAL = "https://bestdream.store/Android/productos_nuevos/?";
                drawer.closeDrawer(GravityCompat.START);
                intent_search(URL_GLOBAL);



            }
        });








        ImageView image_outleet = (ImageView)header.findViewById(R.id.img_outleet);
        new DownloadImageTask(getApplicationContext(), image_outleet, "https://bestdream.store/Views/Default/img/outlet.jpg").execute();
        image_outleet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                URL_GLOBAL = "https://bestdream.store/Android/marca/PREMIUM?";
                drawer.closeDrawer(GravityCompat.START);
                intent_search(URL_GLOBAL);



            }
        });






        /*SI EL USUARIO ESTA LOGUEADO*/
        /*SI EL USUARIO ESTA LOGUEADO*/
        /*SI EL USUARIO ESTA LOGUEADO*/



        if(userFunctions.isUserLoggedIn(this)){


            /*MONEDERO ELECTRONICO*/
            /*MONEDERO ELECTRONICO*/
            /*MONEDERO ELECTRONICO*/


            String email_string = userFunctions.get_user_email(getApplicationContext());

            monedero_electronico_int = userFunctions.get_monedero(email_string);
            monedero_get.setText("Cuento con $"+monedero_electronico_int+" MXN");



            /*MONEDERO ELECTRONICO*/
            /*MONEDERO ELECTRONICO*/
            /*MONEDERO ELECTRONICO*/



            login_logout.setText("Cerrar Sessión");
            login_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(userFunctions.cerrar_session_login(getApplicationContext())){

                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);


                    }

                }
            });


        }else{


            login_logout.setText("Ingresar Ahora");
            login_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);


                }
            });

            //monedero_get.setText("Cuento con $"+monedero_electronico_int+" MXN");
            monedero_get.setVisibility(View.GONE);


        }



        navigationView.setNavigationItemSelectedListener(this);






        labios = findViewById(R.id.labios);
        labios.setClickable(true);
        labios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Labios&";
                intent_search(URL_GLOBAL);


            }
        });


        unas = findViewById(R.id.unas);
        unas.setClickable(true);
        unas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Unas&";
                intent_search(URL_GLOBAL);


            }
        });



        eyes = findViewById(R.id.eyes);
        eyes.setClickable(true);
        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Ojos&";
                intent_search(URL_GLOBAL);

            }
        });



        rostro = findViewById(R.id.rostro);
        rostro.setClickable(true);
        rostro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Rostro&";
                intent_search(URL_GLOBAL);

            }
        });







        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/
        /*HEADEER_MENU_DRAWER*/






    }









    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }



    }









    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.testAction);
        //update_cart_icon_principal();

        return true;

    }









    /*CONTADOR DE CARRITO*/
    /*CONTADOR DE CARRITO*/
    /*CONTADOR DE CARRITO*/




/*

    public void update_cart_icon_principal(){

        menuItem.setIcon(Build_Counter_Cart());

    }





    public Drawable Build_Counter_Cart() {


       int res = Cart_Controller.total_products_in_cart(getApplicationContext());


        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.menu_cart, null);
        view.setBackgroundResource(R.drawable.icon_cart_white);



        View target = view.findViewById(R.id.count);
        BadgeCartIcon badge = new BadgeCartIcon(getApplicationContext(), target);
        badge.setText(String.valueOf(res));
        badge.setTextSize(9);
        badge.show();


        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

        view.setDrawingCacheEnabled(false);

     return new BitmapDrawable(getApplicationContext().getResources(), bitmap);


    }


*/

    /*CONTADOR DE CARRITO*/
    /*CONTADOR DE CARRITO*/
    /*CONTADOR DE CARRITO*/








    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.testAction) {


            Cart_Controller.SHOW_POOP_UP_CART(MainActivity.this);



        }else if(id == R.id.action_search){


            Intent i = new Intent(MainActivity.this, Buscador.class);
            i.putExtra("URL_GLOBAL", URL_GLOBAL);
            startActivity(i);




        }

        return super.onOptionsItemSelected(item);

    }











    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if (id == R.id.nav_ligas) {

            URL_GLOBAL = "https://bestdream.store/Android/marca/BELLE%20SCOTT?";
            drawer.closeDrawer(GravityCompat.START);
            intent_search(URL_GLOBAL);


        }else if (id == R.id.nav_labios) {


            URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Labios&";
            drawer.closeDrawer(GravityCompat.START);
            intent_search(URL_GLOBAL);


        } else if (id == R.id.nav_ojos) {


            URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Ojos&";
            drawer.closeDrawer(GravityCompat.START);
            intent_search(URL_GLOBAL);


        } else if (id == R.id.nav_unas) {


            URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Uñas&";
            drawer.closeDrawer(GravityCompat.START);
            intent_search(URL_GLOBAL);


        } else if (id == R.id.nav_rostro) {


            URL_GLOBAL = "https://bestdream.store/Android/objetivo/?objetivo=Rostro&";
            drawer.closeDrawer(GravityCompat.START);
            intent_search(URL_GLOBAL);


        }else if (id == R.id.estatus_mi_pedido){


            /*
            drawer.closeDrawer(GravityCompat.START);
            intent_browser("https://bestdream.store/Index/status_order");
            */

            Intent i = new Intent(MainActivity.this, Web_View_General.class);
            i.putExtra("que_ver", "ESTATUS_PEDIDO");
            startActivity(i);


        } else if (id == R.id.resenas){


            drawer.closeDrawer(GravityCompat.START);
            intent_browser("https://bestdream.store/Index/resenas");


        } else if (id == R.id.herramientas_ventas){


            Intent i = new Intent(MainActivity.this, Herramientas_Ventas.class);
            startActivity(i);


        } else if (id == R.id.terminos_condiciones){


            drawer.closeDrawer(GravityCompat.START);
            intent_browser("https://bestdream.store/Index/privacidad_terminos");


        } else if (id == R.id.descargar_catalogos){


            drawer.closeDrawer(GravityCompat.START);
            intent_browser("https://bestdream.store/Index/descargar_catalogo");



        }else{


                String title = (String) item.getTitleCondensed();
                Log.i("RESULTADO DE BUSQUEDA::", title);

                URL_GLOBAL = "https://bestdream.store/Android/ver_concidencias_details?categoria="+title+"&";
                drawer.closeDrawer(GravityCompat.START);
                intent_search(URL_GLOBAL);


        }




        return true;


    }






    public void  intent_search(String URL_){

        Intent i = new Intent(MainActivity.this, Searchs.class);
        i.putExtra("URL_GLOBAL", URL_GLOBAL);
        startActivity(i);

    }





    public void show_hide_logo_buttons_login(String func){

        LinearLayout menu_logo_botones = findViewById(R.id.menu_logo_botones);

        if(func.equals("show")){
            menu_logo_botones.setVisibility(View.VISIBLE);
        }else if(func.equals("hidden")){
            menu_logo_botones.setVisibility(View.GONE);
        }


    }





    public void JSON_DATA_WEB_CALL_MARCAS(String PETICION_URL, int limit_, int offset_){

        String URL_ENCODE = reemplazar_espacios_blanco_url(PETICION_URL);
        Log.i("URL_ENCODE", PETICION_URL);



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_ENCODE,
                null,
                new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {



                        Log.e("ARRAY OPBJECT", String.valueOf(response));


                        try {

                            JSONArray array = response.getJSONArray("feed_marcas");

                            for(int i=0;i<array.length();i++){
                                // Get current json object

                                Get_Marca_Adapter2 = new Get_Marca_Adapter();

                                    JSONObject json_base_2 = array.getJSONObject(i);


                                Log.e("NOMBRE_MARCA", json_base_2.getString("nombre_marca"));



                                    Get_Marca_Adapter2.setmarca_image_url(json_base_2.getString("marca_image_url"));
                                    Get_Marca_Adapter2.setnombre_marca(json_base_2.getString("nombre_marca"));
                                    Get_Marca_Adapter2.setid_marca(json_base_2.getInt("id_marca"));
                                    Get_Marca_Adapter2.setcategoria(json_base_2.getString("categorias"));




                                Get_Marca_Adapter1.add(Get_Marca_Adapter2);
                                recyclerViewadapter_marca = new RecyclerViewMarcasAdapter(Get_Marca_Adapter1,  getApplicationContext());
                                recyclerView_Marcas.setAdapter(recyclerViewadapter_marca);






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






    public void JSON_DATA_WEB_CALL(String PETICION_URL, int limit_, int offset_){


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
                            //Log.i("ARRAY_FINAL", "JSON" + array + "---");
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

            GetDataAdapter2.setmarketing("te_podria_interesar");
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









    public void intent_browser(String URL){

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(browserIntent);


    }







}
