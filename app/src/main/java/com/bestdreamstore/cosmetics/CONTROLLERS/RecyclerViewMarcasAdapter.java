package com.bestdreamstore.cosmetics.CONTROLLERS;



import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Cart_Adapter;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Marca_Adapter;
import com.bestdreamstore.cosmetics.CategoriasMarcas;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.Details;
import com.bestdreamstore.cosmetics.MainActivity;
import com.bestdreamstore.cosmetics.R;
import com.bestdreamstore.cosmetics.Searchs;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;




public class RecyclerViewMarcasAdapter extends RecyclerView.Adapter<RecyclerViewMarcasAdapter.ViewHolder> {

    private Context context;
    List<Get_Marca_Adapter> getDataAdapter;
    ImageView iv_cart;






    public RecyclerViewMarcasAdapter(List<Get_Marca_Adapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;


    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclermarcasview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }






    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        Get_Marca_Adapter getadapter_1 =  getDataAdapter.get(position);

        Picasso.get().load(getadapter_1.getmarca_image_url()).into(Viewholder.iv_cart);


        Viewholder.nombre_marca.setText(getadapter_1.getnombre_marca());
        Viewholder.categoria.setText(getadapter_1.getcategoria());






    }



    @Override
    public int getItemCount() {

        return getDataAdapter.size();

    }






    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView nombre_marca, categoria;
        public ImageView iv_cart;


        public ViewHolder(final View itemView) {

            super(itemView);

            nombre_marca = (TextView) itemView.findViewById(R.id.nombre_marca);
            categoria = (TextView) itemView.findViewById(R.id.categoria_m);




            iv_cart = (ImageView) itemView.findViewById(R.id.iv_cart);
            iv_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    String marca_ = nombre_marca.getText().toString();
                    String categoria_ = categoria.getText().toString();




                    if(categoria_.equals("1")){

                        Intent intent = new Intent(context.getApplicationContext(), CategoriasMarcas.class);
                        intent.putExtra("marca", marca_);
                        intent.putExtra("categoria", categoria_);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);


                    }else{



                        Log.i("MARCA-", marca_+" -- CATEGORIA-"+categoria_);

                        marca_.replace(" ", "20%");
                        String URL_GLOBAL = "https://bestdream.store/Android/marca/"+marca_+"?";

                        Intent intent = new Intent(context.getApplicationContext(), Searchs.class);
                        intent.putExtra("URL_GLOBAL", URL_GLOBAL);
                        intent.putExtra("marca", marca_);
                        intent.putExtra("categoria", categoria_);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);


                    }










                }
            });







        }
    }











}
