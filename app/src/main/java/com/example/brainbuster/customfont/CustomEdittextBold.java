package com.example.brainbuster.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEdittextBold extends EditText {


    public CustomEdittextBold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomEdittextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomEdittextBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public CustomEdittextBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = com.example.brainbuster.customfont.FontCache.getTypeface("Montserrat-Bold.ttf", context);
        setTypeface(customFont);
    }
}
