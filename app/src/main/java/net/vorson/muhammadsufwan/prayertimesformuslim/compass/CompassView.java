package net.vorson.muhammadsufwan.prayertimesformuslim.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;

import io.reactivex.annotations.NonNull;

public class CompassView extends View {
    private final Path mPath = new Path();
    private final Paint mPaint = new Paint();
    private final Drawable mKaabe;
    private float mAngle = -80;
    private float mqAngle;

    public CompassView(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mKaabe = context.getResources().getDrawable(R.drawable.ic_kaaba_mecca, null);
        } else {
            mKaabe = context.getResources().getDrawable(R.drawable.ic_kaaba_mecca);
        }

    }

    public CompassView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CompassView(@NonNull Context context) {
        this(context, null);

    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        int size = Math.min(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec));

        int center = size / 2;

        mPath.reset();
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPath.moveTo(center, center / 8);

        mPath.lineTo((center * 30) / 25, center / 3);

        mPath.lineTo(center, center / 5);

        mPath.lineTo((center * 25) / 30, center / 3);
        mPath.close();

        setMeasuredDimension(size, size);

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int width = getWidth();
        int center = width / 2;

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);

        mPaint.setStrokeWidth(center / 50);

        mPaint.setColor(Color.parseColor("#001931"));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(center, center, (center * 19) / 20, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setColor(Color.blue(R.color.colorPrimary));
        canvas.drawCircle(center, center, (center * 19) / 20, mPaint);
        mPaint.setStrokeWidth(1);

        mPaint.setColor(Color.parseColor("#001931"));

        mPaint.setTextSize((center * 2) / 8);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(Math.round(getAngle()) + "°", center, center + (center / 8), mPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.rotate(-mAngle, center, center);

        mPaint.setColor(Color.GRAY);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(mPath, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setTextSize(center / 5);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("N", center, (center * 9) / 20, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.rotate(mqAngle, center, center);

        if (mqAngle != 0) {
            int y = (center * 9) / 20;
            int size = center / 8;
            mKaabe.setBounds(center - size, y - size, center + size, y + size);
            mKaabe.draw(canvas);

            mPaint.setColor(Color.parseColor("#001931"));

            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);

        }

    }

    float getAngle() {
        float angle = mAngle;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public void setAngle(float rot) {
        mAngle = rot;
        invalidate();
    }

    public float getQiblaAngle() {
        float angle = mqAngle;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public void setQiblaAngle(int qiblaAngle) {
        mqAngle = qiblaAngle;
        invalidate();
    }
}