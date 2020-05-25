package com.coolcode.jittranslate.views.bookview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

public class LongTouchTextView extends AppCompatTextView {

    private float touchX = 0;
    private float touchY = 0;

    public LongTouchTextView(Context context) {
        super(context);
    }

    public LongTouchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                return true;

            case MotionEvent.CLASSIFICATION_DEEP_PRESS:
                performClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public float getTouchX() {
        return touchX;
    }

    public float getTouchY() {
        return touchY;
    }
}