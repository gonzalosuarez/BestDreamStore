package com.bestdreamstore.cosmetics.ADAPTERS;


public class Get_Cart_Adapter {


    public String imagen_comp;
    public String nombre_producto;
    public String id_producto;
    public String bar_code;
    public int qty;
    public float precio_premium;
    public float Sub_Total;


    public String getimagen_comp() {
        return imagen_comp;
    }

    public void setimagen_comp(String imagen_comp) {
        this.imagen_comp = imagen_comp;
    }



    /*


    public float getSub_Total() {
        return Sub_Total;
    }

    public void setSub_Total(float Sub_Total) {
        this.Sub_Total = Sub_Total;
    }

    */


    public float getprecio_premium() {
        return precio_premium;
    }

    public void setprecio_premium(float precio_premium) {
        this.precio_premium = precio_premium;
    }




    public int getqty() {
        return qty;
    }

    public void setqty(int qty) {
        this.qty = qty;
    }



    public String getnombre_producto() {
        return nombre_producto;
    }

    public void setnombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }




    public String getbar_code() {
        return bar_code;
    }

    public void setbar_code(String bar_code) {
        this.bar_code = bar_code;
    }






    public String getid_producto() {
        return id_producto;
    }

    public void setgetid_producto(String id_producto) {
        this.id_producto = id_producto;
    }



}
