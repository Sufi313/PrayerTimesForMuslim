package net.vorson.muhammadsufwan.prayertimesformuslim.util;

import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1;
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void main(String[] args) {
        long num = 110110111;
        int decimal = convertBinaryToDecimal(num);
        System.out.printf("%d in binary = %d in decimal", num, decimal);

        System.out.println("\n Example 2");

        int num2 = 19;
        long binary = convertDecimalToBinary(num2);
        System.out.printf("%d in decimal = %d in binary", num, binary);
    }

    public static int convertBinaryToDecimal(long num)
    {
        int decimalNumber = 0, i = 0;
        long remainder;
        while (num != 0)
        {
            remainder = num % 10;
            num /= 10;
            decimalNumber += remainder * Math.pow(2, i);
            ++i;
        }
        return decimalNumber;
    }

    public static long convertDecimalToBinary(int n)
    {
        long binaryNumber = 0;
        int remainder, i = 1, step = 1;

        while (n!=0)
        {
            remainder = n % 2;
            System.out.printf("Step %d: %d/2, Remainder = %d, Quotient = %d\n", step++, n, remainder, n/2);
            n /= 2;
            binaryNumber += remainder * i;
            i *= 10;
        }
        return binaryNumber;
    }

    public static String getUserCountry(Context context) {
        StringBuilder ret = new StringBuilder();
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                ret.append(simCountry.toLowerCase(Locale.US));
                ret.append("\n");
                ret.append(tm.getNetworkOperatorName());
                ret.append("\n");
                ret.append(tm.getNetworkType());
                ret.append("\n");
                ret.append(tm.getPhoneType());
                return ret.toString();
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    ret.append(simCountry.toLowerCase(Locale.US));
                    ret.append("\n");
                    ret.append(tm.getNetworkOperatorName());
                    ret.append("\n");
                    ret.append(tm.getNetworkType());
                    ret.append("\n");
                    ret.append(tm.getPhoneType());
                    return ret.toString();

                }
            }
        }
        catch (Exception e) {
            ret.append(e.getMessage());
        }
        return ret.toString();
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

}
