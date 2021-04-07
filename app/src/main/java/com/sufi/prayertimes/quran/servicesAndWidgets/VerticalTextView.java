package com.sufi.prayertimes.quran.servicesAndWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;
import io.reactivex.annotations.NonNull;

public class VerticalTextView extends AppCompatTextView {
    final boolean topDown;

    public VerticalTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int gravity = getGravity();
        if (Gravity.isVertical(gravity) && (gravity & 112) == 80) {
            setGravity((gravity & 7) | 48);
            this.topDown = false;
            return;
        }
        this.topDown = true;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i2, i);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(@NonNull Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        canvas.save();
        if (this.topDown) {
            canvas.translate(0.0f, ((float) (getHeight() + (getHeight() / 2))) - (paint.measureText(getText().toString()) / 2.0f));
            canvas.rotate(-90.0f);
        } else {
            canvas.translate((float) getWidth(), 0.0f);
            canvas.rotate(90.0f);
        }
        canvas.translate((float) getCompoundPaddingLeft(), (float) getExtendedPaddingTop());
        getLayout().draw(canvas);
        canvas.restore();
    }
}
