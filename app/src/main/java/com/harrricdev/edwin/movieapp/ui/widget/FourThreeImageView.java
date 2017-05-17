package com.harrricdev.edwin.movieapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by edwin on 5/17/17.
 */

public class FourThreeImageView extends ForegroundImageView {

    public FourThreeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec) * 3 / 4,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, fourThreeHeight);
    }
}
