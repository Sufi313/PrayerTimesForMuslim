package net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class CommonMethods {
    @NonNull
    public static String[] suras = new String[]{"all - الكل", "الفاتحة", "البقرة", "آل عمران", "النساء", "المائدة", "الأنعام", "الأعراف", "الأنفال", "التوبة", "يونس", "هود", "يوسف", "الرعد", "إبراهيم", "الحجر", "النحل", "الإسراء", "الكهف", "مريم", "طه", "الأنبياء", "الحج", "المؤمنون", "النور", "الفرقان", "الشعراء", "النمل", "القصص", "العنكبوت", "الروم", "لقمان", "السجدة", "الأحزاب", "سبأ", "فاطر", "يس", "الصافات", "ص", "الزمر", "غافر", "فصّلت", "الشورى", "الزخرف", "الدخان", "الجاثية", "الأحقاف", "محمد", "الفتح", "الحجرات", "ق", "الذاريات", "الطور", "النجم", "القمر", "الرحمن", "الواقعة", "الحديد", "المجادلة", "الحشر", "الممتحنة", "الصف", "الجمعة", "المنافقون", "التغابن", "الطلاق", "التحريم", "الملك", "القلم", "الحاقة", "المعارج", "نوح", "الجن", "المزمل", "المدثر", "القيامة", "الإنسان", "المرسلات", "النبأ", "النازعات", "عبس", "التكوير", "الانفطار", "المطففين", "الانشقاق", "البروج", "الطارق", "الأعلى", "الغاشية", "الفجر", "البلد", "الشمس", "الليل", "الضحى", "الشرح", "التين", "العلق", "القدر", "البيّنه", "الزلزلة", "العاديات", "القارعة", "التكاثر", "العصر", "الهمزة", "الفيل", "قريش", "الماعون", "الكوثر", "الكافرون", "النصر", "المسد", "الإخلاص", "الفلق", "الناس"};

    @Nullable
    public static Bitmap getBitmapFromDrawableId(@NonNull Context context, int i) {
        try {
            return ((BitmapDrawable) context.getResources().getDrawable(i)).getBitmap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    public static String[] getStringsFromRecourses(@NonNull Context context, @NonNull int[] iArr) {
        String[] strArr = new String[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            strArr[i] = context.getString(iArr[i]);
        }
        return strArr;
    }

    public static boolean isApplicationBroughtToBackground(@NonNull Context context) {
        List runningTasks = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
        if (runningTasks.isEmpty() || ((RunningTaskInfo) runningTasks.get(0)).topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        }
        return true;
    }

    public static String md5(@NonNull String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toHexString(b & 255));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int spToPixels(int i, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(2, (float) i, displayMetrics);
    }

    public static float spToPixels1(float f, @NonNull DisplayMetrics displayMetrics) {
        return f * displayMetrics.scaledDensity;
    }
}
