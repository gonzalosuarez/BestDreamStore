package com.bestdreamstore.cosmetics.CONTROLLERS;

import android.content.Context;
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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Cart_Adapter;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Pedidos_Adapter;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.R;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;

import java.text.DecimalFormat;
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


        id_pedido = getDataAdapter1.getid_pedido();





    }



    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }






    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView id_pedido;

        public ViewHolder(final View itemView) {

            super(itemView);



            id_pedido = (TextView) itemView.findViewById(R.id.id_pedido);





        }
    }






}
