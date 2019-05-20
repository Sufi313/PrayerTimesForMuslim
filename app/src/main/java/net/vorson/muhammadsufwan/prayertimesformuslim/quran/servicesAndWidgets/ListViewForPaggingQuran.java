package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import io.reactivex.annotations.NonNull;

public class ListViewForPaggingQuran extends ListView {
    public ListViewForPaggingQuran(Context context) {
        super(context);
    }

    public ListViewForPaggingQuran(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean dispatchTouchEvent(@NonNull MotionEvent motionEvent) {
        if (motionEvent.getAction() == 2) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
