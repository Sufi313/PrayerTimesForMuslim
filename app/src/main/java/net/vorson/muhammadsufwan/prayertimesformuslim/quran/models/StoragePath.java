package net.vorson.muhammadsufwan.prayertimesformuslim.quran.models;

import android.content.Context;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StoragePath {
    private String[] arrayOfDisplayName;
    private Context context;
    File[] getExternalFilesDirs;
    private String[] paths;

    public StoragePath(Context context) {
        this.context = context;
    }

    public String[] removeDuplicates(String[] strArr) {
        Set hashSet = new HashSet();
        String[] strArr2 = new String[strArr.length];
        int i = 0;
        for (Object obj : strArr) {
            if (hashSet.add(obj)) {
                int i2 = i + 1;
                strArr2[i] = (String) obj;
                i = i2;
            }
        }
        return (String[]) Arrays.copyOfRange(strArr2, 0, i);
    }

    private String[] addFullPackagePath(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(strArr[i]);
            stringBuilder.append(File.separator);
            stringBuilder.append("Android");
            stringBuilder.append(File.separator);
            stringBuilder.append("data");
            stringBuilder.append(File.separator);
            stringBuilder.append(context.getPackageName());
            strArr[i] = stringBuilder.toString();
        }
        return strArr;
    }
}
