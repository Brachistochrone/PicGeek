package com.artemlikhomanov.picgeek.views;

import android.content.Context;
import android.util.AttributeSet;

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    private boolean isSquare = true;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSquare(boolean square) {
        if (square != isSquare) {
            isSquare = square;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isSquare) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        }
    }
}
