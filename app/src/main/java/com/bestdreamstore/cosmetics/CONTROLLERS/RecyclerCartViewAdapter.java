package com.bestdreamstore.cosmetics.CONTROLLERS;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.recyclerview.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bestdreamstore.cosmetics.ADAPTERS.Get_Cart_Adapter;
import com.bestdreamstore.cosmetics.DATA_BASE.DatabaseHandler;
import com.bestdreamstore.cosmetics.R;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;




public class RecyclerCartViewAdapter extends RecyclerView.Adapter<RecyclerCartViewAdapter.ViewHolder> {

    List<Get_Cart_Adapter> getDataAdapter;
    ImageView iv_cart;

    private Context context;

    int qty;
    float precio_premium, SUBTOTAL_PROD;
    String precio_p, bar_code;




    boolean check_ini = false;







    public RecyclerCartViewAdapter(List<Get_Cart_Adapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;


    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclercartview_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }






    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        Get_Cart_Adapter getDataAdapter1 =  getDataAdapter.get(position);

        //Ion.with(image).load("http://mygifimage.gif");


        Picasso.get().load(getDataAdapter1.getimagen_comp()).into(Viewholder.iv_cart);






        qty = getDataAdapter1.getqty();

        //Viewholder.spinner.setSelection(qty);
        //Viewholder.change_qty.setText(getDataAdapter1.getqty());







        //multi_fin = qty+1;

        SUBTOTAL_PROD = getDataAdapter1.getprecio_premium()*qty;


        //SUBTOTAL_PROD += getDataAdapter1.getSub_Total();
        precio_p = String.valueOf(String.format("%.2f", SUBTOTAL_PROD));

        bar_code = getDataAdapter1.getbar_code();




        Viewholder.totales.setText("$"+precio_p);
        Viewholder.change_qty.setText("QTY: "+getDataAdapter1.getqty());


        Log.e("PRODUCTO PRECIO:", String.valueOf(precio_premium));
        Viewholder.ID_PRODUCTO.setText(getDataAdapter1.getid_producto());
        Viewholder.B_C.setText(bar_code);





    }



    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }






    class ViewHolder extends RecyclerView.ViewHolder{


        DatabaseHandler db;

        public TextView totales, B_C;
        public TextView ID_PRODUCTO;
        //public Spinner spinner;
        public ImageView iv_cart, delete;
        public Button change_qty;


        public NetworkImageView networkImageView;
        public Button button_cantidad;
        public PopupWindow popupWindow;
        LinearLayout content_window;



        public ViewHolder(final View itemView) {

            super(itemView);
            final Cart_Controller cart = new Cart_Controller(context);



            ID_PRODUCTO = (TextView) itemView.findViewById(R.id.textView_item);
            B_C = (TextView) itemView.findViewById(R.id.BAR_CODE);
            totales = (TextView) itemView.findViewById(R.id.totales_prodcuto);
            iv_cart = (ImageView) itemView.findViewById(R.id.iv_cart);


            change_qty = (Button)itemView.findViewById(R.id.change_qty);
            change_qty.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {


                   // ID_PRODUCTO.getText().toString();


                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                    builderSingle.setIcon(R.drawable.icon_cesta);
                    builderSingle.setTitle("Selecciona Cantidad");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
                    arrayAdapter.add("1");
                    arrayAdapter.add("2");
                    arrayAdapter.add("3");
                    arrayAdapter.add("4");
                    arrayAdapter.add("5");
                    arrayAdapter.add("+6");



                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String qty_change = arrayAdapter.getItem(which);

                            if(qty_change.equals("+6")){



                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setTitle("Que Cantidad Necesitas?");
                                alertDialog.setMessage("Cantidad Máxima: 30 ");

                                final EditText edit_text_qty = new EditText(context);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                edit_text_qty.setLayoutParams(lp);

                                edit_text_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                                edit_text_qty.setFilters( new InputFilter[]{ new Min_Max_EditText( "1" , "30" )}) ;

                                alertDialog.setView(edit_text_qty);
                                alertDialog.setIcon(R.drawable.icon_cesta);

                                alertDialog.setPositiveButton("Aceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String qty_2 = edit_text_qty.getText().toString();
                                                if (!qty_2.equals("")) {

                                                    cart.UPDATE_QTY(ID_PRODUCTO.getText().toString(), Integer.parseInt(qty_2), context);
                                                    notifyDataSetChanged();
                                                    dialog.dismiss();

                                                }else{

                                                    Toast.makeText(context,
                                                            "Ingresa Cantidad", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                                alertDialog.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                alertDialog.show();





                            }else{



                                cart.UPDATE_QTY(ID_PRODUCTO.getText().toString(), Integer.parseInt(qty_change), context);
                                notifyDataSetChanged();
                                dialog.dismiss();



                            }



                        }
                    });



                    builderSingle.setNegativeButton("CERRAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });



                    builderSingle.show();



                }

            });





            /*


            spinner = (Spinner) itemView.findViewById(R.id.cantidad);
            Integer[] items = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context,android.R.layout.simple_spinner_item, items);
            spinner.setAdapter(adapter);


            spinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    check_ini = true;
                    return false;

                }
            });







            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView,
                                           View selectedItemView, int position, long id) {


                   // DecimalFormat precision = new DecimalFormat("0.00");
                  //  String number = precision.format(spinner.getSelectedItem().toString());

                    int valor_spinner = Integer.parseInt(spinner.getSelectedItem().toString());

                    valor_spinner = (int) Math.floor(valor_spinner);

                    if(check_ini){

                        cart.UPDATE_QTY(ID_PRODUCTO.getText().toString(), valor_spinner, context);
                        notifyDataSetChanged();
                        check_ini = false;

                    }


                }

                public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                }


            });

*/
            //cantidad_txt.setText(String.valueOf(cantidad));





            delete = (ImageView) itemView.findViewById(R.id.delete) ;
            delete.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Cart_Controller db3 = new Cart_Controller(context);

                    db3.DELETE_ITEM(ID_PRODUCTO.getText().toString(), context);

                    notifyDataSetChanged();




                }

            });




            //networkImageView = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkCartImageView1);




        }
    }











}

