package net.vorson.muhammadsufwan.prayertimesformuslim.customWidget;

import android.text.Spannable;
import android.text.SpannableString;

import net.vorson.muhammadsufwan.prayertimesformuslim.constantAndInterfaces.SpinnerTextFormatter;

public class SimpleSpinnerTextFormatter implements SpinnerTextFormatter {

    @Override public Spannable format(String text) {
        return new SpannableString(text);
    }
}
