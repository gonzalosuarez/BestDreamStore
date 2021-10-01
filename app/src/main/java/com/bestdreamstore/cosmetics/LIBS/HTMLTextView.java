package com.bestdreamstore.cosmetics.LIBS;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;


public class HTMLTextView extends androidx.appcompat.widget.AppCompatTextView {
    public HTMLTextView(Context context) {
        super(context);
    }
    public HTMLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public HTMLTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        Spanned html = null;

        if(text != null) {
            html = Html.fromHtml(text.toString());
            super.setText(html, BufferType.SPANNABLE);
        } else {
            super.setText(text, type);
        }
    }
}