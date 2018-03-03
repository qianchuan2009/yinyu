package com.lyz.wayy.pub;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by helch on 2018/3/3.
 */
public class TextViewWithFont extends AppCompatTextView {

    public TextViewWithFont(Context context) {
        super(context);
        init(context);
    }

    public TextViewWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewWithFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), "fonts/FORTEMT.TTF");
        setTypeface(newFont);
    }

}