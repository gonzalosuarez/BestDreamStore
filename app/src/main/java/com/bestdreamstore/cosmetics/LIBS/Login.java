package com.bestdreamstore.cosmetics.LIBS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bestdreamstore.cosmetics.CONTROLLERS.PoopUp_General;
import com.bestdreamstore.cosmetics.MainActivity;
import com.bestdreamstore.cosmetics.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.login.widget.ProfilePictureView.TAG;


public class Login extends Activity {


    private static final int RC_SIGN_IN = 9001;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions  gso;
    String  email_global;
    UserFunctions userfunctions;

    PoopUp_General poop_up_general = new PoopUp_General(this);


    EditText email_nueva_cuenta, password_nueva_cuenta, nombre_nueva_cuenta; //NUEVA CUENTA
    EditText email_edittext, password_edittext; //LOGIN
    EditText email_send_recuperar;  //RECUPERAR PASSWORD


    Button send_recuperar_password;  //recuperar password
    HTMLTextView html_textview_rec_pass_response; //recuperar pass





    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




        ImageView logo = (ImageView)findViewById(R.id.logo);
        new DownloadImageTask(getApplicationContext(), logo, "https://bestdream.store/Views/Default/img/logo_bestdream_dark.png").execute();


        ImageButton closePopupBtn = (ImageButton)findViewById(R.id.closePopupBtn);
        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });





        /*GOOGLE*/
        /*GOOGLE*/
        /*GOOGLE*/
        /*GOOGLE*/
        /*GOOGLE*/


        callbackManager = CallbackManager.Factory.create();



        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();





        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                Toast.makeText(Login.this, "GOOGLE:", Toast.LENGTH_SHORT).show();


            }
        });


        /*GOOGLE*/
        /*GOOGLE*/
        /*GOOGLE*/
        /*GOOGLE*/
        /*GOOGLE*/






        /*FACEBOOK*/
        /*FACEBOOK*/
        /*FACEBOOK*/
        /*FACEBOOK*/

        LoginButton loginButton = (LoginButton)findViewById(R.id.fb);
        loginButton.setReadPermissions(Arrays.asList( "public_profile", "email", "user_birthday", "user_friends"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {

                                    //Log.d("JSON_FB1", "fb json object: " + object);
                                    //Log.d("JSON_FB2", "fb graph response: " + response);
                                    //Log.d("JSON_FB3", "EMAIL: " + object.getString("email"));

                                    String id = object.getString("id");
                                    String full_name = object.getString("first_name")+" "+object.getString("last_name");
                                    String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";

                                    login_url_redes_sociales(object.getString("email"), image_url, full_name);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
                request.setParameters(parameters);
                request.executeAsync();





            }


            @Override
            public void onCancel() {
                //Toast.makeText(Login.this,getResources().getString(R.string.login_canceled_facebooklogin),Toast.LENGTH_SHORT).show();
               // progress.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                //Toast.makeText(FacebookLogin.this,getResources().getString(R.string.login_failed_facebooklogin),Toast.LENGTH_SHORT).show();
               // progress.dismiss();
            }
        });



        /*FACEBOOK*/
        /*FACEBOOK*/
        /*FACEBOOK*/
        /*FACEBOOK*/





        /*EMAIL*/
        /*EMAIL*/
        /*EMAIL*/
        /*EMAIL*/

        final LinearLayout vista_login = (LinearLayout)findViewById(R.id.vista_login);
        final LinearLayout vista_password_back = (LinearLayout)findViewById(R.id.vista_password_back);
        final LinearLayout vista_crear_cuenta = (LinearLayout)findViewById(R.id.vista_crear_cuenta);






        vista_login.setVisibility(View.VISIBLE);
        vista_password_back.setVisibility(View.GONE);
        vista_crear_cuenta.setVisibility(View.GONE);



        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/


                email_edittext = (EditText)findViewById(R.id.email_edittext);
                password_edittext = (EditText)findViewById(R.id.password_edittext);


                Button send_login = (Button)findViewById(R.id.send_login);
                send_login.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void onClick(View v) {

                        login_normal();

                    }
                });




                Button olvide_pass = (Button)findViewById(R.id.olvide_pass);
                olvide_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        vista_login.setVisibility(View.GONE);
                        vista_password_back.setVisibility(View.VISIBLE);
                        vista_crear_cuenta.setVisibility(View.GONE);

                    }
                });



            Button crear_cuenta = (Button)findViewById(R.id.crear_cuenta);
            crear_cuenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    vista_login.setVisibility(View.GONE);
                    vista_password_back.setVisibility(View.GONE);
                    vista_crear_cuenta.setVisibility(View.VISIBLE);

                }
            });



        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/
        /*VISTA LOGIN*/




        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/


        email_send_recuperar = (EditText)findViewById(R.id.email_send_recuperar);
        send_recuperar_password = (Button)findViewById(R.id.send_recuperar_password);
        html_textview_rec_pass_response = (HTMLTextView)findViewById(R.id.response_service);

        send_recuperar_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email_s = (String) email_send_recuperar.getText().toString();
                if(email_s.length() == 0){

                    email_send_recuperar.requestFocus();
                    email_send_recuperar.setError("Ingresa Tu Email");

                }else{

                    recuperar_pass_send(email_s);
                }






            }
        });



        final Button regresar_login = (Button)findViewById(R.id.regresar_login);
        regresar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vista_login.setVisibility(View.VISIBLE);
                vista_password_back.setVisibility(View.GONE);
                vista_crear_cuenta.setVisibility(View.GONE);

            }
        });




        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/
        /*VISTA_RECUPERAR_PASSWORD*/




        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/




        email_nueva_cuenta = (EditText)findViewById(R.id.email_nueva_cuenta);
        password_nueva_cuenta = (EditText)findViewById(R.id.password_nueva_cuenta);
        nombre_nueva_cuenta = (EditText)findViewById(R.id.nombre_nueva_cuenta);



        Button send_nueva_cuenta = (Button)findViewById(R.id.send_nueva_cuenta);
        send_nueva_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_fin = (String) email_nueva_cuenta.getText().toString();
                String pass_fin = (String) password_nueva_cuenta.getText().toString();
                String nombre_fin = (String) nombre_nueva_cuenta.getText().toString();


                crear_cuenta_nueva_url(nombre_fin, email_fin, pass_fin);

            }
        });




        Button regresar_login_nueva_cuenta = (Button)findViewById(R.id.regresar_login_nueva_cuenta);
        regresar_login_nueva_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vista_login.setVisibility(View.VISIBLE);
                vista_password_back.setVisibility(View.GONE);
                vista_crear_cuenta.setVisibility(View.GONE);

            }
        });





        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/
        /*VISTA CREAR NUEVA CUENTA*/


    }














    @SuppressLint("StaticFieldLeak")
    public void login_normal(){
        final String email_fin = (String) email_edittext.getText().toString();
        final String pass_fin = (String) password_edittext.getText().toString();


        if(email_fin.length() == 0){

            email_edittext.requestFocus();
            email_edittext.setError("Ingresa Tu Email");

        }else if(pass_fin.length() == 0){

            password_edittext.requestFocus();
            password_edittext.setError("Ingresa Tu Password");

        }else{



            new AsyncTask<Object, Void, HashMap<String, String>>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    poop_up_general.SHOW_POOP_ESPERE_UN_MOMENTO(Login.this);

                }

                @SuppressLint("WrongThread")
                @Override
                protected HashMap<String, String> doInBackground(Object... params) {
                    HashMap<String, String> resultados_json = new HashMap();

                    try {

                        UserFunctions userFunction = new UserFunctions();
                        JSONObject json = userFunction.login_service(email_fin, pass_fin);
                        resultados_json.put("success", json.getString("success"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return resultados_json;

                }


                @Override
                protected void onPostExecute(final HashMap<String, String> params) {
                    super.onPostExecute(params);

                    poop_up_general.CLOSE_POOP();

                    String success = params.get("success");
                    int succes_int = Integer.parseInt(success);

                    Log.d("RES_LOG:", String.valueOf(success));

                    if(succes_int == 1) {

                        add_user_database(email_fin, "https://bestdream.store/Views/Default/img/app_android/icon_user.png");

                    }else{

                        password_edittext.getText().clear();
                        email_edittext.getText().clear();

                        HTMLTextView response_login_email = (HTMLTextView)findViewById(R.id.response_login_email);
                        response_login_email.setText("<span style='color:red;'>UPS! Usuario o Password Incorrectos. Intenta Nuevamente</span>");

                    } } }.execute();

        }



    }










    public void login_url_redes_sociales(final String email, final String url_img, final String nombre){


        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://bestdream.store/Inside/login_reg_fb_google";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("LOGIN_REDES_SOCIALS::::", response);
                try {

                    JSONObject res_fin = new JSONObject(response);
                    int success = Integer.parseInt(res_fin.getString("success"));

                    if(success == 1){
                        add_user_database(email, url_img);
                    }

                    Log.d("SUCCESS::::", String.valueOf(success));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("tag", "ANDROID");
                MyData.put("nombre", nombre);
                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }












    @SuppressLint("StaticFieldLeak")
    public void crear_cuenta_nueva_url(final String nombre, final String email, final String password){


        if(nombre.length() == 0){

            nombre_nueva_cuenta.requestFocus();
            nombre_nueva_cuenta.setError("Incluye Nombre");

        }else if(email.length() == 0){

            email_nueva_cuenta.requestFocus();
            email_nueva_cuenta.setError("Incluye Un Email");

        }else if(password.length() == 0){

            password_nueva_cuenta.requestFocus();
            password_nueva_cuenta.setError("Incluye Un Password");

        }else{



            new AsyncTask<Object, Void, HashMap<String, String>>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    poop_up_general.SHOW_POOP_ESPERE_UN_MOMENTO(Login.this);

                }

                @SuppressLint("WrongThread")
                @Override
                protected HashMap<String, String> doInBackground(Object... params) {
                    HashMap<String, String> resultados_json = new HashMap();

                    try {

                        UserFunctions userFunction = new UserFunctions();
                        JSONObject json = userFunction.crear_cuenta_nueva(email, password, nombre);
                        resultados_json.put("success", json.getString("success"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return resultados_json;

                }


                @Override
                protected void onPostExecute(final HashMap<String, String> params) {
                    super.onPostExecute(params);

                    poop_up_general.CLOSE_POOP();

                    String success = params.get("success");
                    int succes_int = Integer.parseInt(success);

                    Log.d("RES_LOG:", String.valueOf(success));

                    email_nueva_cuenta.getText().clear();
                    password_nueva_cuenta.getText().clear();
                    nombre_nueva_cuenta.getText().clear();


                    if(succes_int == 1){
                        add_user_database(email, "https://bestdream.store/Views/Default/img/app_android/icon_user.png");
                    }else if(succes_int == 3){

                        HTMLTextView response_create_cuenta = (HTMLTextView)findViewById(R.id.response_create_cuenta);
                        response_create_cuenta.setText("<p align='center' style='color:red;'>"+
                                "Lo sentimos. Este usuario ya ha sido registrado anteriormente<br><br>Gracias.</p>");

                    }

                } }.execute();

        }


    }






    private void add_user_database(String email, String image_url){

            Log.d("EMAIL_USER", email);
            userfunctions = new UserFunctions();
            int res = userfunctions.addUser(email, image_url, this);
            if(res == 1){

                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();

            }else{


            }

    }





    public void  recuperar_pass_send(final String email_s){


        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://bestdream.store/Index/olvidar_pass";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                email_send_recuperar.getText().clear();

                Log.d("RES_OLVIDAR_PASS::::", response);
                email_send_recuperar.setVisibility(View.GONE);
                send_recuperar_password.setVisibility(View.GONE);
                html_textview_rec_pass_response.setText("<br><br><br><br><span style=color:red;>En breve estarás recibiendo instrucciones"+
                        " para reestablecer tu password.</span><br><br><br>Gracias");


                finish_few_seconds();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email_s);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);




    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        /*GOOGLE_RESULT*/
        /*GOOGLE_RESULT*/

        Log.w(TAG, "GOOGLE =" + requestCode);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);



            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                //Toast.makeText(Login.this, "EMAIL_GOOGLE:"+email_google, Toast.LENGTH_SHORT).show(); //Correcto
                if (account != null) {

                    String personName = account.getDisplayName();
                    String email_google = account.getEmail();

                    login_url_redes_sociales(email_google, "https://bestdream.store/Views/Default/img/app_android/icon_google.png", personName);

                }

            } catch (ApiException e) {

                Log.w(TAG, "RESPONSE_GOOGLE_LOGIN code=" + e.getStatusCode());

            }

        }

        /*GOOGLE_RESULT*/
        /*GOOGLE_RESULT*/



    }







    public void finish_few_seconds(){
        Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            public void run() {
                finish();

            }
        }, 2000); // 2 segundos de "delay"

    }







}
