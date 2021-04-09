package com.bestdreamstore.cosmetics;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
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


    List<Get_Pedidos_Adapter> Get_Pedidos_Adapter_1;
    Get_Pedidos_Adapter Get_Pedidos_Adapter_2;



     @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.webview);


         userFunctions = new UserFunctions();


         contetn_relative_layout = (LinearLayout)findViewById(R.id.contetn_relative_layout);



         papyrus = ResourcesCompat.getFont(this, R.font.papyrus);
         panton = ResourcesCompat.getFont(this, R.font.panton_font);


         mToolbar = (Toolbar) findViewById(R.id.toolbar_web_view);
         mToolbar.setNavigationIcon(R.drawable.icon_back);
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
                     final RecyclerView recyclerView = new RecyclerView(this);



                     String email_string = userFunctions.get_user_email(getApplicationContext());


                     EditText id_pedido_edit_text = new EditText(this);
                     id_pedido_edit_text.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                     id_pedido_edit_text.setGravity(Gravity.CENTER);


                     Button button_search = new Button(this);
                     button_search.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                     button_search.setText("Buscar");
                     button_search.setGravity(Gravity.CENTER);


                     TextView sdCardInfo = new TextView(this);
                     sdCardInfo.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                     sdCardInfo.setGravity(Gravity.CENTER);



                     String URL_ENCODE = "https://bestdream.store/Android/ver_pedidos_email/?email="+email_string;


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

                                         Log.i("ARRAY_FINAL", "JSON" + array + "---");



                                         for(int i=0;i<array.length();i++){
                                             // Get current json object


                                             Get_Pedidos_Adapter_2 = new Get_Pedidos_Adapter();

                                             try {


                                                 JSONObject json_base_2 = array.getJSONObject(i);

                                                 String id_pedido = json_base_2.getString("id_pedido");

                                                 Get_Pedidos_Adapter_2.setid_pedido(json_base_2.getString("id_pedido"));



                                             } catch (JSONException e) {

                                                 e.printStackTrace();
                                             }



                                             Get_Pedidos_Adapter_1.add(Get_Pedidos_Adapter_2);


                                             recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
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










                     contetn_relative_layout.addView(sdCardInfo);

                     contetn_relative_layout.addView(id_pedido_edit_text);
                     contetn_relative_layout.addView(button_search);
                     contetn_relative_layout.addView(recyclerView);



                     sdCardInfo.setText("Pedido creado con usuario: "+email_string);









                 }else{




                 }




             }


         }else{

             que_ver = extras.getString("que_ver");


         }










    }














}
