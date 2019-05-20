package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import android.content.Context;

import net.vorson.muhammadsufwan.prayertimesformuslim.App;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.StoragePath;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.SuraInfo;

import java.io.File;

public class Reciter {
    private final Context context;
    private String jsonDownloadLinkForReciter;
    private String reciterArName;
    private String reciterEnName;
    private int reciterId;

    public Reciter(Context context, int reciterId, String reciterArName, String reciterEnName, String jsonDownloadLinkForReciter) {
        this.context = context;
        this.reciterId = reciterId;
        this.reciterArName = reciterArName;
        this.reciterEnName = reciterEnName;
        this.jsonDownloadLinkForReciter = jsonDownloadLinkForReciter;
    }

    public int getReciterId() {
        return reciterId;
    }

    public String getReciterArName() {
        return reciterArName;
    }

    public String getReciterEnName() {
        return reciterEnName;
    }

    public String getJsonDownloadLinkForReciter() {
        return jsonDownloadLinkForReciter;
    }


    public String getMp3Path(int n) {
        final SuraInfo suraInfo = new SuraInfo(n, null, null, null);
        new SettingsManager(context);
        new StoragePath(context);
        for (int i = 0; i < App.getArrayOfPaths().length; ++i) {
            final StringBuilder sb = new StringBuilder();
            sb.append(App.getArrayOfPaths()[i]);
            sb.append(File.separator);
            sb.append("audios");
            sb.append(File.separator);
            sb.append(reciterId);
            final String string = sb.toString();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(File.separator);
            sb2.append(suraInfo.getThreeCharsSuraId());
            sb2.append(".mp3");
            final File file = new File(sb2.toString());
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        return "";
    }


    public String getDurFilePath(final int n) {
        final SuraInfo suraInfo = new SuraInfo(n, (String)null, (String)null, (String)null);
        new SettingsManager(context);
        new StoragePath(context);
        for (int i = 0; i < App.getArrayOfPaths().length; ++i) {
            final StringBuilder sb = new StringBuilder();
            sb.append(App.getArrayOfPaths()[i]);
            sb.append(File.separator);
            sb.append("audios");
            sb.append(File.separator);
            sb.append(reciterId);
            final String string = sb.toString();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(File.separator);
            sb2.append(suraInfo.getThreeCharsSuraId());
            sb2.append(".dur");
            final File file = new File(sb2.toString());
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        return "";
    }

}
