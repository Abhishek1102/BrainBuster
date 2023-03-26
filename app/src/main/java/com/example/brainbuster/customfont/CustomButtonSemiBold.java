package com.example.brainbuster.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomButtonSemiBold extends AppCompatButton {

    public CustomButtonSemiBold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomButtonSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomButtonSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = com.example.brainbuster.customfont.FontCache.getTypeface("Montserrat-SemiBold.ttf", context);
        setTypeface(customFont);
    }
}
