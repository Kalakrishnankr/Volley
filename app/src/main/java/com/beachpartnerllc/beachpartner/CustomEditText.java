package com.beachpartnerllc.beachpartner;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by seq-kala on 7/2/18.
 */

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
        init();
    }



    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        Typeface face = Typeface.createFromAsset(getContext().getAssets(),"fonts/SanFranciscoTextRegular.ttf");
        setTextColor(getResources().getColor(R.color.blueDark));

        setTypeface(face);

    }
}
