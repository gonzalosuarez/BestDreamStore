package com.bestdreamstore.cosmetics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bestdreamstore.cosmetics.Analytics.Analitycs_Resume;
import com.bestdreamstore.cosmetics.CONTROLLERS.Cart_Controller;
import com.bestdreamstore.cosmetics.CONTROLLERS.PoopUp_General;
import com.bestdreamstore.cosmetics.LIBS.DownloadImageTask;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.Login;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.widget.CheckBox;
import android.widget.Toast;




public class Delivery extends AppCompatActivity {


    RadioGroup radios_group;
    RadioButton oxxo_r, tcrdito_r, tdebito_r, extra_r, seven_eleven, telecom, banco_directo;
    Cart_Controller cart = new Cart_Controller(this);
    String m_pago_global = "OXXO";
    CheckBox seguro, terminos_condiciones, crear_cuenta_login, monedero_electronico_ch;

    PoopUp_General poop_up_general = new PoopUp_General(this);
    LinearLayout cupon_promocional_linear_layout, cupon_promocional_response;
    Typeface papyrus, panton;

    MenuItem menuItem;
    Button btnTag;
    EditText nombre_completo, email, calle, numero, colonia, municipio, cp, telefono, referencias, codigo_descuento;
    Spinner estado;
    HTMLTextView txt_general_response;
    ImageButton btn_search;
    Button code_discount_button;
    int descuento_code = 0;


    UserFunctions userFunction;
    DatabaseHandler db = new DatabaseHandler(this);
    Toolbar mToolbar;
    CoordinatorLayout cordinado_general;
    private static String KEY_SUCCESS = "success";
    private static String KEY_PEDIDO = "key_pedido";
    private static String KEY_ID_PEDIDO_INSERTADO = "id_pedido";
    private FirebaseAnalytics mFirebaseAnalytics;
    private static int TIME_OUT = 1000;
    int monedero_electronico_int = 0;
    int electronic_money_fin = 0;

    Date caducidad;

    int test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);

        userFunction = new UserFunctions();

        panton = ResourcesCompat.getFont(this, R.font.panton_font);



        mToolbar = (Toolbar) findViewById(R.id.tool_bar_delivery);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        cordinado_general = (CoordinatorLayout)findViewById(R.id.cordinado_general);



        ImageView image_cupon_promocional = (ImageView)findViewById(R.id.image_cupon_promocional);
        Picasso.get().load("https://bestdream.store/Views/Default/img//redes_sociales.png").into(image_cupon_promocional);


        radios_group = (RadioGroup)findViewById(R.id.radios_group);

        oxxo_r = (RadioButton)findViewById(R.id.oxxo);
        tcrdito_r = (RadioButton)findViewById(R.id.tcredito);
        tdebito_r = (RadioButton)findViewById(R.id.tdebito);
        extra_r = (RadioButton)findViewById(R.id.extra);
        seven_eleven = (RadioButton)findViewById(R.id.seven_eleven);
        telecom = (RadioButton)findViewById(R.id.telecom);
        banco_directo = (RadioButton)findViewById(R.id.banco_directo);



        seguro = (CheckBox)findViewById(R.id.seguro);
        terminos_condiciones = (CheckBox)findViewById(R.id.terminos_condiciones);
        crear_cuenta_login = (CheckBox)findViewById(R.id.crear_cuenta_login);
        monedero_electronico_ch = (CheckBox)findViewById(R.id.monedero_electronico);



        nombre_completo = (EditText)findViewById(R.id.nombre_completo);
        email = (EditText)findViewById(R.id.email);
        calle = (EditText)findViewById(R.id.calle);
        numero = (EditText)findViewById(R.id.numero);
        colonia = (EditText)findViewById(R.id.colonia);
        municipio = (EditText)findViewById(R.id.municipio);
        cp = (EditText)findViewById(R.id.cp);
        telefono = (EditText)findViewById(R.id.telefono);
        referencias = (EditText)findViewById(R.id.referencias);


        cupon_promocional_linear_layout = (LinearLayout)findViewById((R.id.cupon_promocional_linear_layout));
        cupon_promocional_response = (LinearLayout)findViewById((R.id.cupon_promocional_response));
        cupon_promocional_response.setGravity(Gravity.CENTER);


        txt_general_response = (HTMLTextView)findViewById(R.id.txt_general_response);
        txt_general_response.setTypeface(panton);



        codigo_descuento = (EditText)findViewById(R.id.codigo_descuento);
        code_discount_button = (Button)findViewById((R.id.code_discount_btn));
        code_discount_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String codigo_des_fin = (String) codigo_descuento.getText().toString();

                if(codigo_des_fin.length() == 0){

                    codigo_descuento.requestFocus();
                    codigo_descuento.setError("Incluye Código");

                }else{


                    if(userFunction.isUserLoggedIn(getApplicationContext())){

                        code_descuento_en_uso();

                    }else{

                        txt_general_response.setText("<span style='color:red;'>Para usar el código<br>debes loguearte previamente!</span><br><br>");

                    }



                }




            }
        });



        if(userFunction.isUserLoggedIn(getApplicationContext())){

            String email_string = userFunction.get_user_email(getApplicationContext());

            monedero_electronico_int = userFunction.get_monedero(email_string);


            if(monedero_electronico_int > 0){

                monedero_electronico_ch.setText("Usar Mis $"+monedero_electronico_int+" en monedero");
                monedero_electronico_ch.setVisibility(View.VISIBLE);

            }else{

                monedero_electronico_ch.setVisibility(View.GONE);

            }

            email.setText(email_string);
            email.setFocusable(false);
            email.setTextColor(Color.RED);





        }else{

            monedero_electronico_ch.setVisibility(View.GONE);

        }



        /*SPINNER ESTADO*/
        /*SPINNER ESTADO*/
        /*SPINNER ESTADO*/

        List<String> spinner_estado_array =  new ArrayList<String>();
        spinner_estado_array.add("Selecciona Estado");
        spinner_estado_array.add("Aguascalientes");
        spinner_estado_array.add("Baja California");
        spinner_estado_array.add("Baja California Sur");
        spinner_estado_array.add("Campeche");
        spinner_estado_array.add("Coahuila");
        spinner_estado_array.add("Colima");
        spinner_estado_array.add("Chiapas");
        spinner_estado_array.add("Chihuahua");
        spinner_estado_array.add("CDMX");
        spinner_estado_array.add("Durango");
        spinner_estado_array.add("Guanajuato");
        spinner_estado_array.add("Guerrero");
        spinner_estado_array.add("Hidalgo");
        spinner_estado_array.add("Jalisco");
        spinner_estado_array.add("Edo de México");
        spinner_estado_array.add("Michoacán");
        spinner_estado_array.add("Morelos");
        spinner_estado_array.add("Nayarit");
        spinner_estado_array.add("Nuevo León");
        spinner_estado_array.add("Oaxaca");
        spinner_estado_array.add("Puebla");
        spinner_estado_array.add("Querétaro");
        spinner_estado_array.add("Quintana Roo");
        spinner_estado_array.add("San Luis Potosí");
        spinner_estado_array.add("Sinaloa");
        spinner_estado_array.add("Sonora");
        spinner_estado_array.add("Tabasco");
        spinner_estado_array.add("Tamaulipas");
        spinner_estado_array.add("Tlaxcala");
        spinner_estado_array.add("Veracruz");
        spinner_estado_array.add("Yucatán");
        spinner_estado_array.add("Zacatecas");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinner_estado_array);


        estado = (Spinner)findViewById(R.id.estado);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estado.setAdapter(adapter);


        adapter.setDropDownViewResource(R.layout.spinner_txt_size);

        /*SPINNER ESTADO*/
        /*SPINNER ESTADO*/
        /*SPINNER ESTADO*/




        final RadioButton oxxo = (RadioButton)findViewById(R.id.oxxo);
        final RadioButton tdebito = (RadioButton)findViewById(R.id.tdebito);
        final RadioButton tcredito = (RadioButton)findViewById(R.id.tcredito);
        final RadioButton extra = (RadioButton)findViewById(R.id.extra);









        Button procesar_pedido = (Button)findViewById(R.id.procesar_pedido);
        procesar_pedido.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                PROCESAR_PEDIDO_FIN();

            }
        });







    }















    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_delivery, menu);
        menuItem = menu.findItem(R.id.testAction);
        //update_cart_icon_principal();

        return true;

    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.testAction) {

            Cart_Controller.SHOW_POOP_UP_CART(Delivery.this);

        }
        return super.onOptionsItemSelected(item);

    }











    @SuppressLint("StaticFieldLeak")
    public void code_descuento_en_uso(){


        new AsyncTask<Object, Void, HashMap<String, String>>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                txt_general_response.setText("Verificar Cupón en Sistema");
            }


            @Override
            protected HashMap<String, String> doInBackground(Object... params) {
                HashMap<String, String> resultados_json = new HashMap();

                userFunction = new UserFunctions();

                String email_string = userFunction.get_user_email(getApplicationContext());
                String codigo_des_fin = (String) codigo_descuento.getText().toString();

                try {

                    JSONObject json = userFunction.code_descuento_en_uso(codigo_des_fin, email_string);

                    resultados_json.put("num_rows", json.getString("num_rows"));
                    resultados_json.put("response", json.getString("response"));
                    resultados_json.put("code", json.getString("code"));
                    resultados_json.put("email_cliente", json.getString("email_cliente"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return resultados_json;

            }


            @Override
            protected void onPostExecute(final HashMap<String, String> params) {
                super.onPostExecute(params);

                String response = params.get("response");
                String num_rows = params.get("num_rows");
                String code = params.get("code");
                String email_cliente = params.get("email_cliente");

                if(response.equals("1")){

                    txt_general_response.setText("<span style='color:red;'>Este cupón ya lo haz utilizado con Anterioridad. Gracias.<br><br>Vuelve a Intentar con otro cupón</span>");

                }else{

                    code_descuento();

                }

            } }.execute();



    }















    @SuppressLint("StaticFieldLeak")
    public void code_descuento(){

        new AsyncTask<Object, Void, HashMap<String, String>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                txt_general_response.setText("Espere un momento. Validando Cupón");
             }

            @Override
            protected HashMap<String, String> doInBackground(Object... params) {
                HashMap<String, String> resultados_json = new HashMap();

                userFunction = new UserFunctions();

                String codigo_des_fin = (String) codigo_descuento.getText().toString();

                try {
                    JSONObject json = userFunction.ver_codigo_descuento(codigo_des_fin);

                    resultados_json.put("monto", json.getString("monto"));
                    resultados_json.put("response", json.getString("response"));
                    resultados_json.put("code_return", json.getString("code_return"));
                    resultados_json.put("fecha_caducidad", json.getString("fecha_caducidad"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return resultados_json;

            }


            @Override
            protected void onPostExecute(final HashMap<String, String> params) {
                super.onPostExecute(params);

                String response = params.get("response");
                final String monto = params.get("monto");
                String code_return = params.get("code_return");
                String fecha_caducidad = params.get("fecha_caducidad");


                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Date hoy = Calendar.getInstance().getTime();

                try {

                    caducidad = format.parse(fecha_caducidad);

                } catch (ParseException e) {
                    e.printStackTrace();
                }



                if(hoy.after(caducidad)){

                    txt_general_response.setText("<span style='color:red;'>Este cupón ha caducado. Gracias.<br><br>Vuelve a Intentar con otro cupón</span>");


                }else{

                    cupon_promocional_linear_layout.setVisibility(View.INVISIBLE);





                    txt_general_response.setText("<span style='color:blue;'>¿Estás seguro en activar el cupón en este pedido?</span><br><br>");

                    btnTag = new Button(getApplicationContext());
                    btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    btnTag.setText("Activar Cupón Ahora");
                    btnTag.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            insert_descuento_cupon(Integer.parseInt(monto));

                        }
                    });

                    cupon_promocional_response.setGravity(Gravity.CENTER);
                    cupon_promocional_response.addView(btnTag);

                }

                //txt_general_response.setText("Response:"+response+" --  Fecha Caducidad db:"+fecha_caducidad+ " --  FECHA CADUCIDAD_CORR:"+caducidad+" -- HOY:"+hoy);
                //

            } }.execute();



    }









    @SuppressLint("StaticFieldLeak")
    public void insert_descuento_cupon(final int monto){

        new AsyncTask<Object, Void, HashMap<String, String>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                txt_general_response.setText("Espere.Realizando Descuento.....");
            }

            @Override
            protected HashMap<String, String> doInBackground(Object... params) {
                HashMap<String, String> resultados_json = new HashMap();

                userFunction = new UserFunctions();


                String email_string = userFunction.get_user_email(getApplicationContext());
                String codigo_des_fin = (String) codigo_descuento.getText().toString();

                try {
                    JSONObject json = userFunction.insert_code_descuento(codigo_des_fin, email_string);

                    resultados_json.put("response", json.getString("response"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return resultados_json;

            }


            @Override
            protected void onPostExecute(final HashMap<String, String> params) {
                super.onPostExecute(params);

                String response = params.get("response");

                if(response.equals("1")){

                    btnTag.setVisibility(View.INVISIBLE);
                    txt_general_response.setText("<span style='color:blue;'>Listo Agregado con Éxito!</span><br><br>");
                    descuento_code = monto;




                }


            } }.execute();



    }







    @SuppressLint("StaticFieldLeak")
    public void PROCESAR_PEDIDO_FIN(){

        txt_general_response.setText("<span style='color:blue;'>Cupon con valor de: "+descuento_code+"</span><br><br>");



        String nombre_completo_string = (String) nombre_completo.getText().toString();
        String referencias_string = (String) referencias.getText().toString();
        String email_string = (String) email.getText().toString();
        String calle_string = (String) calle.getText().toString();
        String numero_string = (String) numero.getText().toString();
        String colonia_string = (String) colonia.getText().toString();
        String municipio_string = (String) municipio.getText().toString();
        final String cp_string = (String) cp.getText().toString();
        String telefono_string = (String) telefono.getText().toString();



        Log.d("NOMBRE_LENGH:", String.valueOf(nombre_completo_string.length()+"--"+nombre_completo_string));
        Log.d("EMAIL_LENGH:", String.valueOf(email_string.length()+"--"+email_string));
        Log.d("CALLE_LENGH:", String.valueOf(calle_string.length()+"--"+calle_string));
        Log.d("NUMERO_LENGH:", String.valueOf(numero_string.length()+"--"+numero_string));
        Log.d("COLONIE_LENGH:", String.valueOf(colonia_string.length()+"--"+colonia_string));
        Log.d("MUNICIPIO_LENGH:", String.valueOf(municipio_string.length()+"--"+municipio_string));
        Log.d("CP_LENGH:", String.valueOf(cp_string.length()+"--"+cp_string));
        Log.d("TELEFONO_LENGH:", String.valueOf(telefono_string.length()+"--"+telefono_string));
        Log.d("REFERENCIAS_LENGH:", String.valueOf(referencias_string.length()+"--"+referencias_string));




        if(nombre_completo_string.length() == 0){

            nombre_completo.requestFocus();
            nombre_completo.setError("Incluye Nombre");

        }else if(email_string.length() == 0){

            email.requestFocus();
            email.setError("Incluye Email");

        }else if(calle_string.length() == 0){

            calle.requestFocus();
            calle.setError("Incluye Calle");

        }else if(numero_string.length() == 0){

            numero.requestFocus();
            numero.setError("Incluye Númedro");

        }else if(colonia_string.length() == 0){

            colonia.requestFocus();
            colonia.setError("Incluye Colonia");

        }else if(municipio_string.length() == 0){

            municipio.requestFocus();
            municipio.setError("Incluye Municipio");

        }else if(estado.getSelectedItem().toString().equals("")){
            estado.requestFocus();
        }else if(estado.getSelectedItem().toString().equals("Selecciona Estado ")){
            estado.requestFocus();

        }else if(cp_string.length() == 0){

            cp.requestFocus();
            cp.setError("Incluye Codigo Postal");

        }else if(telefono_string.length() == 0){

            telefono.requestFocus();
            telefono.setError("Incluye Número Telefónico");

        }else if(referencias_string.length() == 0){

            referencias.requestFocus();
            referencias.setError("Incluye Referencias");


        }else if(!terminos_condiciones.isChecked()){

            terminos_condiciones.requestFocus();
            Toast.makeText(Delivery.this,
                    "FALTA TERMINOS Y CONDICIONES", Toast.LENGTH_LONG).show();



        }else{



            electronic_money_fin = 0;

            final float subtotal =  Cart_Controller.GET_SUBTOTAL(Delivery.this);
            if(monedero_electronico_ch.isChecked()){

                electronic_money_fin = monedero_electronico_int;

            }




            new AsyncTask<Object, Void, HashMap<String, String>>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    poop_up_general.SHOW_POOP_ESPERE_UN_MOMENTO(Delivery.this);

                }

                @SuppressLint("WrongThread")
                @Override
                protected HashMap<String, String> doInBackground(Object... params) {
                    HashMap<String, String> resultados_json = new HashMap();


                    userFunction = new UserFunctions();

                    int PRECIO_ENVIO_FIN = 149;
                    int precio_envio = userFunction.get_envio_costo();
                    int cotizacion_fedex = userFunction.get_precio_envio_fedex(cp_string);


                    if(cotizacion_fedex > 150){


                        if(subtotal >= 2500){

                            PRECIO_ENVIO_FIN = precio_envio;

                        }else{

                            PRECIO_ENVIO_FIN = cotizacion_fedex;

                        }


                    }else{

                        PRECIO_ENVIO_FIN = precio_envio;

                    }



                    Log.e("PRECIO_ENVIO_FIN", String.valueOf(PRECIO_ENVIO_FIN));



                    try {


                        JSONObject totales_2 = new JSONObject();
                        JSONObject datos_cliente_2 = new JSONObject();
                        JSONObject pedido_tipo = new JSONObject();
                        JSONObject PEDIDO_COMPLETO = new JSONObject();
                        JSONArray cart_json = Cart_Controller.GET_CART_JSON(Delivery.this);


                        pedido_tipo = userFunction.convert_pedido_tipo();
                        totales_2 = userFunction.convert_json_totales(subtotal,PRECIO_ENVIO_FIN,subtotal+PRECIO_ENVIO_FIN, electronic_money_fin, descuento_code, 16);
                        datos_cliente_2 = convert_json_cliente();
                        PEDIDO_COMPLETO = userFunction.convert_pedido_completo (cart_json, totales_2, datos_cliente_2, pedido_tipo);





                        JSONObject json = userFunction.subir_pedido(String.valueOf(PEDIDO_COMPLETO));

                        resultados_json.put("success", json.getString("success"));
                        resultados_json.put("id_insertado", json.getString("id_insertado"));
                        resultados_json.put("key_pedido", json.getString("key_pedido"));



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    return resultados_json;

                }


                @Override
                protected void onPostExecute(final HashMap<String, String> params) {
                    super.onPostExecute(params);



                    Log.d("PARAMS_PEDIDO:", String.valueOf(params));


                    String success = params.get("success");
                    String id_insertado = params.get("id_insertado");
                    String key_pedido = params.get("key_pedido");



                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bestdream.store/Pago/pedido/"+key_pedido));
                    startActivity(browserIntent);
                    finish();



                    poop_up_general.CLOSE_POOP();

                } }.execute();





        }




    }








    private JSONObject convert_json_cliente() throws JSONException {

        JSONObject DATOS_CLI = new JSONObject();


        DATOS_CLI.put("descuento", "0");
        DATOS_CLI.put("tipo_envio", "DOMICILIO");
        DATOS_CLI.put("full_name", nombre_completo.getText().toString());
        DATOS_CLI.put("email", email.getText().toString());
        DATOS_CLI.put("calle", calle.getText().toString());
        DATOS_CLI.put("numero", numero.getText().toString());
        DATOS_CLI.put("colonia", colonia.getText().toString());
        DATOS_CLI.put("municipio", municipio.getText().toString());
        DATOS_CLI.put("estado", estado.getSelectedItem().toString());
        DATOS_CLI.put("cp", cp.getText().toString());
        DATOS_CLI.put("telefono", telefono.getText().toString());
        DATOS_CLI.put("referencias", referencias.getText().toString());
        DATOS_CLI.put("monedero", 0);
        DATOS_CLI.put("checkbox_auto_register", true);



        return DATOS_CLI;
    }
















    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();


        switch(view.getId()) {
            case R.id.oxxo:
                if(checked)

                    m_pago_global = "OXXO";

                    oxxo_r.setChecked(true);
                    tcrdito_r.setChecked(false);
                    tdebito_r.setChecked(false);
                    extra_r.setChecked(false);

                break;
            case R.id.tcredito:
                if(checked)

                    m_pago_global = "TCREDITO";

                    oxxo_r.setChecked(false);
                    tcrdito_r.setChecked(true);
                    tdebito_r.setChecked(false);
                    extra_r.setChecked(false);
                    seven_eleven.setChecked(false);
                    telecom.setChecked(false);

                break;
            case R.id.tdebito:
                if(checked)

                    m_pago_global = "TDEBITO";

                    oxxo_r.setChecked(false);
                    tcrdito_r.setChecked(false);
                    tdebito_r.setChecked(true);
                    extra_r.setChecked(false);
                    seven_eleven.setChecked(false);
                    telecom.setChecked(false);

                break;
            case R.id.extra:
                if(checked)

                    m_pago_global = "EXTRA";

                    oxxo_r.setChecked(false);
                    tcrdito_r.setChecked(false);
                    tdebito_r.setChecked(false);
                    extra_r.setChecked(true);
                    seven_eleven.setChecked(false);
                    telecom.setChecked(false);

                break;

            case R.id.seven_eleven:
                if(checked)

                    m_pago_global = "SEVEN_ELEVEN";

                oxxo_r.setChecked(false);
                tcrdito_r.setChecked(false);
                tdebito_r.setChecked(false);
                extra_r.setChecked(false);
                seven_eleven.setChecked(true);
                telecom.setChecked(false);


                break;


            case R.id.telecom:
                if(checked)

                    m_pago_global = "TELECOM";

                oxxo_r.setChecked(false);
                tcrdito_r.setChecked(false);
                tdebito_r.setChecked(false);
                extra_r.setChecked(false);
                seven_eleven.setChecked(false);
                telecom.setChecked(true);


                break;
        }


       // Toast.makeText(getApplicationContext(), m_pago_global, Toast.LENGTH_SHORT).show();

    }










}
