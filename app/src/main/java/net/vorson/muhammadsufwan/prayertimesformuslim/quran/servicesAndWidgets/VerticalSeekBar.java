package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatSeekBar;
import io.reactivex.annotations.NonNull;

public class VerticalSeekBar extends AppCompatSeekBar {
    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i2, i, i4, i3);
    }

    public synchronized void setProgress(int i) {
        super.setProgress(i);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i2, i);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(@NonNull Canvas canvas) {
        canvas.translate((float) getWidth(), 0.0f);
        canvas.rotate(90.0f);
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
            case 1:
            case 2:
                setProgress((int) ((((float) getMax()) * motionEvent.getY()) / ((float) getHeight())));
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
        }
        return true;
    }
}
