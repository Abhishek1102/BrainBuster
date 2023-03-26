package com.example.brainbuster.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Montserrat-Bold.ttf", context);
        setTypeface(customFont);
    }
}
