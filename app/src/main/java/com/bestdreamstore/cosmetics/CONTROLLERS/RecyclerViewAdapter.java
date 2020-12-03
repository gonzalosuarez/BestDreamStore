package com.bestdreamstore.cosmetics.CONTROLLERS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bestdreamstore.cosmetics.ADAPTERS.GetDataAdapter;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.Details;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.LIBS.UserFunctions;
import com.bestdreamstore.cosmetics.MainActivity;
import com.bestdreamstore.cosmetics.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by JUNED on 6/16/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {




    Context context;
    List<GetDataAdapter> getDataAdapter;
    ImageLoader imageLoader1;
    Typeface typeface;
    UserFunctions userFunctions;

    String MARKETING_FEED;


    public String ID_PRODUCTO_GLOBAL, NOMBRE_PRODUCTO_GLOBAL, IMAGEN_PRODUCTO_GLOBAL, CATEGORIA_PRODUCTO_GLOBAL, MARCA_PRODUCTO_GLOBAL;

    public String PRODUCTO_GLOBAL, PRECIO_PREMIUM_GLOBAL, COSTO_PRODUCTO_GLOBAL, COSTO_ENVIO_GLOBAL, BAR_CODE_GLOBAL;



    public DatabaseHandler db;
    public Cart_Controller cart;




    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;


    }









    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }






    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {


        final StaggeredGridLayoutManager.LayoutParams layoutParams =  new StaggeredGridLayoutManager.LayoutParams(
                Viewholder.itemView.getLayoutParams());



        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);
        cart = new Cart_Controller(context);






        Viewholder.MARKETING = getDataAdapter1.getmarketing();
        MARKETING_FEED = getDataAdapter1.getmarketing();



        if(MARKETING_FEED.equals("marketing")){


            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/

            layoutParams.setFullSpan(true);

            userFunctions = new UserFunctions();
            JSONObject res_mkt = userFunctions.get_marketing_arr();


            JSONArray array = null;

            try {
                array = res_mkt.getJSONArray("feed_todos");

                for(int i=0;i<array.length();i++){

                    JSONObject json_base_2 = array.getJSONObject(i);

                    final String imagen_marketing = json_base_2.getString("imagen");
                    final String categoria = reemplazar_espacios_blanco_url(json_base_2.getString("categoria"));


                    Log.e("IMAGEN::", imagen_marketing);
                    Log.e("CATEGORIA::", categoria);


                    Picasso.get().load(imagen_marketing).into(Viewholder.iv);


                    Picasso.get().load(getDataAdapter1.getImageServerUrl()).into(Viewholder.iv);
                    Viewholder.iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                            intent.putExtra("categoria_extra", categoria);
                            context.startActivity(intent);

                        }
                    });


                    Viewholder.linearlayout_buttons.setVisibility(View.GONE);
                    CATEGORIA_PRODUCTO_GLOBAL = getDataAdapter1.getcategoria();



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/
            /*FEED MARKETING ACTIVADO*/





        }else if(MARKETING_FEED.equals("feed_normal")){



            /*FEED NORMAL*/
            /*FEED NORMAL*/
            /*FEED NORMAL*/
            /*FEED NORMAL*/
            /*FEED NORMAL*/



                layoutParams.setFullSpan(false);
                ID_PRODUCTO_GLOBAL = getDataAdapter1.getid();
                NOMBRE_PRODUCTO_GLOBAL = getDataAdapter1.getnombre();
                IMAGEN_PRODUCTO_GLOBAL = getDataAdapter1.getImageServerUrl();
                CATEGORIA_PRODUCTO_GLOBAL = getDataAdapter1.getcategoria();
                MARCA_PRODUCTO_GLOBAL = getDataAdapter1.getmarca();
                PRODUCTO_GLOBAL = getDataAdapter1.getproducto();
                COSTO_ENVIO_GLOBAL = getDataAdapter1.getpeso();
                BAR_CODE_GLOBAL = getDataAdapter1.getbar_code();


                int precio_mayoreo_url = Integer.parseInt(getDataAdapter1.getprecio_mayoreo());
                int decimales_mayoreo_url = Integer.parseInt(getDataAdapter1.getdecimales_mayoreo());
                PRECIO_PREMIUM_GLOBAL = String.valueOf(precio_mayoreo_url)+"."+String.valueOf(decimales_mayoreo_url);


                int costo_producto = Integer.parseInt(getDataAdapter1.getcosto_producto());
                int decimales_costo = Integer.parseInt(getDataAdapter1.getdecimales_costo());
                COSTO_PRODUCTO_GLOBAL = String.valueOf(costo_producto)+"."+String.valueOf(decimales_costo);






                Viewholder.id_text_global.setText(getDataAdapter1.getid());
                Viewholder.nombre.setText(
                        "<strong>$"+getDataAdapter1.getprecio_mayoreo()+"."+
                                getDataAdapter1.getdecimales_mayoreo()+"</strong><br>"+
                                getDataAdapter1.getnombre()+" -- ID:"+ID_PRODUCTO_GLOBAL
                );


            Picasso.get().load(getDataAdapter1.getImageServerUrl()).into(Viewholder.iv);






                if(cart.check_if_prod_in_cart(getDataAdapter1.getid())){


                    Viewholder.add_cart.setImageResource(R.drawable.ic_done_ok);


                }else{

                    Viewholder.add_cart.setImageResource(R.drawable.icon_cesta);

                }



            /*FEED NORMAL*/
            /*FEED NORMAL*/
            /*FEED NORMAL*/
            /*FEED NORMAL*/
            /*FEED NORMAL*/




            /*TE PODRIA INTERESAR*/
            /*TE PODRIA INTERESAR*/
            /*TE PODRIA INTERESAR*/
            /*TE PODRIA INTERESAR*/
            /*TE PODRIA INTERESAR*/


        }else if(MARKETING_FEED.equals("te_podria_interesar")){


            layoutParams.setFullSpan(true);



            Picasso.get().load("https://bestdream.store/Views/Default/img/app_android/te_podria_interesar.jpg").into(Viewholder.iv);

            Viewholder.linearlayout_buttons.setVisibility(View.GONE);





        }




        /*TE PODRIA INTERESAR*/
        /*TE PODRIA INTERESAR*/
        /*TE PODRIA INTERESAR*/
        /*TE PODRIA INTERESAR*/
        /*TE PODRIA INTERESAR*/








        Viewholder.itemView.setLayoutParams(layoutParams);






    }



    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }






    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView id_text_global;
        public HTMLTextView nombre;
        public ImageButton details;
        public ImageView add_cart, ic_details_go;
        public LinearLayout linearlayout_buttons;
        public String MARKETING;
        public ImageView iv;


        public ViewHolder(View itemView) {

            super(itemView);
            cart = new Cart_Controller(context);


                linearlayout_buttons = (LinearLayout)itemView.findViewById(R.id.linearlayout_buttons);

                typeface = ResourcesCompat.getFont(context, R.font.panton_font);


                nombre = (HTMLTextView)itemView.findViewById(R.id.textView_item);
                nombre.setTypeface(typeface);


                iv = (ImageView) itemView.findViewById(R.id.iv);


                id_text_global = (TextView)itemView.findViewById(R.id.id_text_global);



            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String ID_ = id_text_global.getText().toString();
                    Intent intent = new Intent(context.getApplicationContext(), Details.class);
                    intent.putExtra("ID_PRODUCTO", ID_);
                    context.startActivity(intent);

                }
            });



            /*

                if(!MARKETING.equals("marketing")){

                    networkImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String ID_ = id_text_global.getText().toString();
                            Intent intent = new Intent(context.getApplicationContext(), Details.class);
                            intent.putExtra("ID_PRODUCTO", ID_);
                            context.startActivity(intent);

                        }
                    });

                }



*/





                /*AGREGAR A CARRITO*/
                /*AGREGAR A CARRITO*/

                add_cart = (ImageView)itemView.findViewById(R.id.add_cart);
                add_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String ID_ = id_text_global.getText().toString();
                        boolean response = cart.ADD_CART_SKU(ID_);
                        if (response) {
                            add_cart.setImageResource(R.drawable.ic_done_ok);
                        }

                    }
                });




                /*AGREGAR A CARRITO*/
                /*AGREGAR A CARRITO*/


                ic_details_go = (ImageView)itemView.findViewById(R.id.details);
                ic_details_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String ID_ = id_text_global.getText().toString();
                        Intent intent = new Intent(context.getApplicationContext(), Details.class);
                        intent.putExtra("ID_PRODUCTO", ID_);
                        context.startActivity(intent);

                    }
                });






        }



    }




    public String reemplazar_espacios_blanco_url(String url){

        return url.replace(" ", "%20");


    }







}
