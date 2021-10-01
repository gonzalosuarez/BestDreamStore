package com.bestdreamstore.cosmetics.CONTROLLERS;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bestdreamstore.cosmetics.ADAPTERS.Get_Pedidos_Adapter;
import com.bestdreamstore.cosmetics.LIBS.HTMLTextView;
import com.bestdreamstore.cosmetics.R;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;

import java.util.List;



public class RecyclerView_Pedidos_Adapter extends RecyclerView.Adapter<com.bestdreamstore.cosmetics.CONTROLLERS.RecyclerView_Pedidos_Adapter.ViewHolder> {

    Context context;
    List<Get_Pedidos_Adapter> getDataAdapter;
    String id_pedido;




    public RecyclerView_Pedidos_Adapter(List<Get_Pedidos_Adapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;


    }





    @Override
    public RecyclerView_Pedidos_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pedidos, parent, false);
        RecyclerView_Pedidos_Adapter.ViewHolder viewHolder = new RecyclerView_Pedidos_Adapter.ViewHolder(v);

        return viewHolder;

    }






    @Override
    public void onBindViewHolder(RecyclerView_Pedidos_Adapter.ViewHolder Viewholder, int position) {

        Get_Pedidos_Adapter getDataAdapter1 =  getDataAdapter.get(position);

        Viewholder.id_pedido.setText(getDataAdapter1.getid_pedido());
        Viewholder.key_pedido.setText(getDataAdapter1.getkey_pedido());





        if(!getDataAdapter1.getestatus_pedido().equals("accredited")){

            Picasso.get().load("https://bestdream.store/Views/Default/img/pago_alert.jpg").into(Viewholder.image_cupon_promocional);

        }else{

            Picasso.get().load("https://bestdream.store/Views/Default/img/pago_ok.png").into(Viewholder.image_cupon_promocional);

        }









        Viewholder.id_pedido.setText(
                "Pedido:ID: <strong>"+getDataAdapter1.getid_pedido()+" -- "+
                        getDataAdapter1.getfecha_aprovacion()+"</strong><br> Rastreo Fedex: "+
                        getDataAdapter1.getrastreo()
        );



    }



    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }






    class ViewHolder extends RecyclerView.ViewHolder{

        public HTMLTextView id_pedido;
        public ImageButton button_detalles_pedido;
        public TextView key_pedido;

        public ImageView image_cupon_promocional;

        public ViewHolder(final View itemView) {

            super(itemView);


            image_cupon_promocional = (ImageView)itemView.findViewById(R.id.imagen_icono);

            id_pedido = (HTMLTextView) itemView.findViewById(R.id.id_pedido);
            key_pedido = (TextView) itemView.findViewById(R.id.key_pedido);
            button_detalles_pedido = (ImageButton) itemView.findViewById(R.id.ver_pedido);

            button_detalles_pedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String key_pedido_ = key_pedido.getText().toString();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bestdream.store/Index/details_order/"+key_pedido_));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);


                }
            });






        }
    }






}
