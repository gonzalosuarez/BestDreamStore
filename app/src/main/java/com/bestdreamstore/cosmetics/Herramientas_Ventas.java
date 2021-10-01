package com.bestdreamstore.cosmetics;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bestdreamstore.cosmetics.CONTROLLERS.PoopUp_General;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.DownloadImageTask;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Herramientas_Ventas extends Activity {


    UserFunctions userFunctions;
    PoopUp_General poop_up_general = new PoopUp_General(this);

    Toolbar mToolbar;
    HTMLTextView texto_herr_1, text2;
    Button ver_paginas_button;
    String email_string;


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


        userFunctions = new UserFunctions();



        email_string = userFunctions.get_user_email(getApplicationContext());




        ImageView image_ofertas = (ImageView)findViewById(R.id.pagina_web);
        new DownloadImageTask(getApplicationContext(), image_ofertas, "https://bestdream.store/Views/Default/img/reseller/1.jpg").execute();



        ver_paginas_button = (Button)findViewById(R.id.ver_paginas_button);
        ver_paginas_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AsyncTask<Object, Void, HashMap<String, String>>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        poop_up_general.SHOW_POOP_ESPERE_UN_MOMENTO(Herramientas_Ventas.this);

                    }

                    @SuppressLint("WrongThread")
                    @Override
                    protected HashMap<String, String> doInBackground(Object... params) {
                        HashMap<String, String> resultados_json = new HashMap();

                        try {

                            UserFunctions userFunction = new UserFunctions();
                            JSONObject json = userFunction.get_pedidos_reseller(email_string);
                            resultados_json.put("response", json.getString("response"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return resultados_json;

                    }


                    @Override
                    protected void onPostExecute(final HashMap<String, String> params) {
                        super.onPostExecute(params);

                        JSONArray jArray = null;

                        poop_up_general.CLOSE_POOP();

                        int response_int = params.get("response").length();


                        JSONObject obj = null;

                        try {

                            jArray = obj.getJSONArray("response");

                            for (int i=0; i < jArray.length(); i++) {
                                try {

                                    JSONObject oneObject = jArray.getJSONObject(i);


                                    String key_user = oneObject.getString("key_user");
                                    String id = oneObject.getString("id");

                                    Log.d("KEY_USER:", key_user);
                                    Log.d("ID:", id);



                                } catch (JSONException e) {
                                    // Oops
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }











                    } }.execute();



            }
        });




        texto_herr_1 = (HTMLTextView)findViewById(R.id.texto_herr_1);
        texto_herr_1.setText("Aprovecha los beneficios de tener Tu \"Propia Web\" que muestra todo el catálogo de productos y marcas para potencializar las redes sociales y su lista de contactos!\n" +
                "\n" + "\n" +
                "Protegemos Tu información de contacto, para que el cliente unicamente tenga acceso a la información del producto por medio de tu Whatsapp.");







    }






}
