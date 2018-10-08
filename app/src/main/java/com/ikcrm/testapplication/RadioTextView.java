package com.ikcrm.testapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by furuoxuan on 2018-09-12.
 */
public class RadioTextView extends android.support.v7.widget.AppCompatTextView {

    private Paint paint;
    private String bgColor = "#8F8F8F";

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
        postInvalidate();
    }

    public void setBgColor(String bgColor, boolean def) {
        if (def || bgColor.length() == 0) {
            this.bgColor = "#8F8F8F";
        } else {
            this.bgColor = bgColor;
        }
        postInvalidate();
    }

    public RadioTextView(Context context) {
        this(context, null);
    }

    public RadioTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setDither(true);
        paint.setAntiAlias(true);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.parseColor(bgColor));
        int radio = getMeasuredHeight() / 2;
        RectF rec = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rec, radio, radio, paint);
        super.onDraw(canvas);
    }
}
