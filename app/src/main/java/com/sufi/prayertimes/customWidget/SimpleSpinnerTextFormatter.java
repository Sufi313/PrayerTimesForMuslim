package com.sufi.prayertimes.customWidget;

import android.text.Spannable;
import android.text.SpannableString;

import com.sufi.prayertimes.constantAndInterfaces.SpinnerTextFormatter;

public class SimpleSpinnerTextFormatter implements SpinnerTextFormatter {

    @Override public Spannable format(String text) {
        return new SpannableString(text);
    }
}
