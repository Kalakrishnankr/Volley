package com.beachpartnerllc.beachpartner;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by seq-kala on 7/2/18.
 */

public class CustomTextView extends TextView{
    public CustomTextView(Context context) {
        super(context);
        init();
    }



    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init() {

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/SanFranciscoTextRegular.ttf");
        //Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/copperplate.ttf");
        setTypeface(typeface);




    }
}
