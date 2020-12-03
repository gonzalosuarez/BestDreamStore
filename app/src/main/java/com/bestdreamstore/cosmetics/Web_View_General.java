package com.bestdreamstore.cosmetics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import com.bestdreamstore.cosmetics.R;
import java.util.HashMap;
import java.util.Map;

public class Web_View_General extends AppCompatActivity {



    String URL = "";



     @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.webview);




         Intent intent = getIntent();
         Bundle extras = intent.getExtras();

         if (extras != null) {


             URL = extras.getString("URL");


         }else{

             URL = extras.getString("URL");

         }






        WebView webView = (WebView) this.findViewById(R.id.webview);
        webView.loadUrl(URL);



    }














}
