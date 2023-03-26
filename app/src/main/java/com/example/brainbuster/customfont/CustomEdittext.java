package com.example.brainbuster.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

public class CustomEdittext extends EditText {

    public CustomEdittext(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = com.example.brainbuster.customfont.FontCache.getTypeface("Montserrat-Medium.ttf", context);
        setTypeface(customFont);
    }
}
