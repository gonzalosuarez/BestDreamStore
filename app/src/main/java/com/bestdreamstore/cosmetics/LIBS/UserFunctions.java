package com.bestdreamstore.cosmetics.LIBS;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonArray;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserFunctions {


    private JSONParser jsonParser;

    String GLOBAL_STRING_VOLLEY_RETURN;



    // TESTEO EN SERVIDOR
    private static String loginURL = "https://bestdream.store/Inside/userLogin/";
    private static String test_arr = "https://bestdream.store/Inside/test_android_object";
    private static String subir_pedido = "https://bestdream.store/Android/alta_pedido_android";
    private static String details_id_product = "https://bestdream.store/Productos/details/";
    private static String sku_details_url = "https://bestdream.store/Android/details_id/";

    public UserFunctions(){
        jsonParser = new JSONParser();
    }










    public int addUser(String nombre, String url_image, Context context) {

        DatabaseHandler db = new DatabaseHandler(context);
        int res = db.addUser(nombre, url_image);

        return res;

    }




    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);

        int count = db.getRowCount();
        if(count > 0){

            return true;

        }
        return false;

    }





    public String get_user_email(Context context){

        DatabaseHandler db = new DatabaseHandler(context);

        String email = "";


        JSONArray USER = null;
        JSONArray feed = null;
        JSONArray user_arr = null;

        try {
            USER = db.getUserDetails();
            for(int j = 0; j<USER.length(); j++) {

                JSONObject json_base = null;
                JSONObject json_2 = null;
                try {

                    json_base = USER.getJSONObject(j);

                    feed = new JSONArray(json_base.getString("user_login"));

                    JSONObject jsonObject = feed.getJSONObject(0);
                    email = jsonObject.getString("name");



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return email;

    }








    public int get_envio_costo(){


        String URL = "https://bestdream.store/Admin/precio_envio";
        int fin = 149;

        HashMap<String, String> params = new HashMap<>();
        params.put("params", "0");

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST",params);
            try {

                fin = Integer.parseInt(json.getString("envio"));

            } catch (JSONException e) {
                e.printStackTrace();
            }



        return fin;

    }




    public int get_seguro_costo(){


        String URL = "https://bestdream.store/Admin/precio_envio";
        int fin = 16;

        HashMap<String, String> params = new HashMap<>();
        params.put("params", "0");

        JSONObject json = jsonParser.makeHttpRequest(URL, "POST",params);
        try {

            fin = Integer.parseInt(json.getString("seguro"));

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return fin;

    }




    public boolean cerrar_session_login(Context context){

        DatabaseHandler db = new DatabaseHandler(context);

        int count = db.reset_login();
        if(count > 0){

            Cerrar_Google();
            Cerrar_Facebook();
            return true;

        }else{

            return false;

        }



    }








    public JSONObject subir_pedido(String PEDIDO_TOTAL){

        HashMap<String, String> params = new HashMap<>();
        params.put("pedido_total", PEDIDO_TOTAL);

        JSONObject json = jsonParser.makeHttpRequest(subir_pedido, "POST",params);
        // RETORNO JSON
        //Log.e("JSON----", "Es un Objeto");
        Log.e("TEST PEDIDO ALTA", json.toString());
        return json;


    }




    public Integer get_precio_envio_fedex(String CP){

        int fin = 139;
        HashMap<String, String> params = new HashMap<>();
        params.put("cp", CP);

        String url_1 = "https://bestdream.store/Fedex/cotizar_envio/cotizacion/";
        String url_2 = "?cp_origen=72110&cp_destino="+CP+"&";
        String url_3 = "alto=18&ancho=18&largo=8&peso_kg=1";

        JSONObject json = jsonParser.makeHttpRequest(url_1+url_2+url_3, "POST",params);
        try {


            if(json.getBoolean("success")){


                fin = json.getInt("monto_pagado");

            }else{

                fin = 139;

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

         Log.e("COTIZACION_FEDEX", String.valueOf(fin));
        return fin;


    }





    public int get_monedero(String email){

        int monedero_fin = 0;
        String URL = "https://bestdream.store/Inside/check_if_modedero/";
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);

        JSONObject json = jsonParser.makeHttpRequest(URL, "POST",params);
        try {

            monedero_fin = json.getInt("monedero");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("TEST::MONEDERO", String.valueOf(json));

        return monedero_fin;
    }










    public JSONObject login_service(String email, String pass){

        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", pass);

        JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST",params);
        // RETORNO JSON
        //Log.e("JSON----", "Es un Objeto");
        Log.e("TEST::LOGIN", String.valueOf(json));

        return json;


    }









    public JSONObject crear_cuenta_nueva(String email, String pass, String nombre){

        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", pass);
        params.put("nombre", nombre);
        params.put("tag", "ANDROID_NORMAL");

        JSONObject json = jsonParser.makeHttpRequest("https://bestdream.store/Inside/registroNormal", "POST",params);
        // RETORNO JSON
        //Log.e("JSON----", "Es un Objeto");
        Log.e("TEST::CREATE_NEW_CUEMTA", String.valueOf(json));

        return json;


    }



    public void Cerrar_Facebook() {

            if (AccessToken.getCurrentAccessToken() == null) {
                return; // already logged out
            }

            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                    .Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {

                    LoginManager.getInstance().logOut();

                }
            }).executeAsync();

    }




    private void Cerrar_Google() {

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }

    }









    public JSONObject get_marketing_arr(){

        HashMap<String, String> params = new HashMap<>();
        params.put("limit", "1");

        JSONObject json = jsonParser.makeHttpRequest("https://bestdream.store/Android/feeds_marketing", "POST",params);
        // RETORNO JSON
        //Log.e("JSON----", "Es un Objeto");
        //Log.e("FEED_MARKETING::", String.valueOf(json));

        return json;


    }






    public void alert_func(String txt, Context ctx){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setMessage(txt);
        alertDialogBuilder.setPositiveButton("Aceptar",  new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }






    public JSONObject convert_pedido_tipo() throws JSONException {

        JSONObject P_COMPLETO = new JSONObject();

        P_COMPLETO.put("tipo", "ANDROID");



        return P_COMPLETO;


    }




    public JSONObject convert_json_totales(float total, int envio, float total_con_envio, int electronic_money, int descuento_code, int seguro) throws JSONException {

        JSONObject TOTALES = new JSONObject();

        int activ_money_elec = 0;
        if(electronic_money != 0){
            activ_money_elec = 1;
        }

        TOTALES.put("total", total);
        TOTALES.put("envio", envio);
        TOTALES.put("total_con_envio", total+envio);
        TOTALES.put("code_discount", 0);
        TOTALES.put("activar_monedero_electronico", activ_money_elec);
        TOTALES.put("electronic_money", electronic_money);
        TOTALES.put("seguro", seguro);
        TOTALES.put("total_pagado", total+envio+electronic_money+descuento_code);


        return TOTALES;
    }









    public JSONObject convert_pedido_completo(JSONArray productos, JSONObject totales, JSONObject datos_cliente, JSONObject pedido_tipo) throws JSONException {

        JSONObject P_COMPLETO = new JSONObject();

        P_COMPLETO.put("totales", totales);
        P_COMPLETO.put("productos", productos);
        P_COMPLETO.put("datos_cliente", datos_cliente);
        P_COMPLETO.put("pedido_tipo", pedido_tipo);

        JSONObject obj = new JSONObject();
        obj.put("pedido", P_COMPLETO);

        return obj;


    }








}
