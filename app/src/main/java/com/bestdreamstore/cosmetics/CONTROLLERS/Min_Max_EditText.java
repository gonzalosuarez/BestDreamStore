package com.bestdreamstore.cosmetics.CONTROLLERS;

import android.text.InputFilter ;
import android.text.Spanned ;
public class Min_Max_EditText implements InputFilter {
    private int mIntMin , mIntMax ;
    public Min_Max_EditText ( int minValue , int maxValue) {
        this . mIntMin = minValue ;
        this . mIntMax = maxValue ;
    }
    public Min_Max_EditText (String minValue , String maxValue) {
        this . mIntMin = Integer. parseInt (minValue) ;
        this . mIntMax = Integer. parseInt (maxValue) ;
    }
    @Override
    public CharSequence filter (CharSequence source , int start , int end , Spanned dest ,int dstart , int dend) {
        try {
            int input = Integer. parseInt (dest.toString() + source.toString()) ;
            if (isInRange( mIntMin , mIntMax , input))
                return null;
        } catch (NumberFormatException e) {
            e.printStackTrace() ;
        }
        return "" ;
    }
    private boolean isInRange ( int a , int b , int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a ;
    }
}