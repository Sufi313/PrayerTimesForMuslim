package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import net.vorson.muhammadsufwan.prayertimesformuslim.App;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.data.DataManager;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.data.HTTPConnection;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.StoragePath;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.SuraInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class AudioSettingsManager
{
    private Context context;

    public AudioSettingsManager(final Context context) {
        this.context = context;
        new SettingsManager(context);
    }

    @NonNull
    public Boolean copyDefaultReciterDesc() {
        final Boolean value = true;
        String[] list = new String[0];
        try {
            list = this.context.getAssets().list("audios");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        Boolean b = value;
        for (int i = 0; i < list.length; ++i) {
            try {
                final SettingsManager settingsManager = new SettingsManager(this.context);
                final AssetManager assets = this.context.getAssets();
                final StringBuilder sb = new StringBuilder();
                sb.append("audios/");
                sb.append(list[i]);
                final InputStream open = assets.open(sb.toString());
                final String s = (String)settingsManager.getSettings(SettingsManager.AUDIO_FILES_PATH, (Class)String.class);
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(s);
                sb2.append(File.separator);
                sb2.append("audios");
                sb2.append(File.separator);
                sb2.append(list[i].replace(".txt", ""));
                sb2.append(File.separator);
                sb2.append("reciter.txt");
                final File file = new File(sb2.toString());
                if (!file.exists()) {
                    final StringBuilder sb3 = new StringBuilder();
                    sb3.append(s);
                    sb3.append(File.separator);
                    sb3.append("audios");
                    sb3.append(File.separator);
                    sb3.append(list[i].replace(".txt", ""));
                    new File(sb3.toString()).mkdirs();
                    file.createNewFile();
                    final FileOutputStream fileOutputStream = new FileOutputStream(file);
                    new DataManager(this.context).copyFile(open, (OutputStream)fileOutputStream);
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    b = true;
                }
            }
            catch (Exception ex2) {
                Log.e("tag", ex2.getMessage());
                b = false;
            }
        }
        return b;
    }

    public boolean downloadAllRecitersProfiles(){
        try {
            final URLConnection openConnection = new URL("https://isysway.imfast.io/AlQuran-audio/Audio%20paths/Reciters.zip").openConnection();
            openConnection.connect();
            int n = openConnection.getContentLength();
            if (n < 0) {
                try {
                    n = Integer.parseInt(openConnection.getHeaderField("x-goog-stored-content-length"));
                }
                catch (Exception ex4) {
                    return false;
                }
            }
            if (n == this.context.getSharedPreferences("AlQuran_Audio", 0).getInt("storedFileSize", 0)) {
                return false;
            }
            final String saveingAudioPath = this.getSaveingAudioPath();
            final DataManager dataManager = new DataManager(this.context);
            String utf8StringFromZipURL;
            try {
                utf8StringFromZipURL = HTTPConnection.getUTF8StringFromZipURL(new URL("https://isysway.imfast.io/AlQuran-audio/Audio%20paths/Reciters.zip"));
                if (utf8StringFromZipURL == null) {
                    return false;
                }
                    final JSONArray jsonArray = new JSONArray(utf8StringFromZipURL);
                    int i = 0;
                    boolean b = false;

                        while (i < jsonArray.length()) {
                            final JSONObject jsonObject = jsonArray.getJSONObject(i);
                            final String string = jsonObject.getString("F1");
                            final String string2 = jsonObject.getString("F2");
                            final String string3 = jsonObject.getString("F3");
                            final String string4 = jsonObject.getString("F4");
                            final StringBuilder sb = new StringBuilder();
                            sb.append(string.trim());
                            sb.append("\n");
                            sb.append(string2.trim());
                            sb.append("\n");
                            sb.append(string3.trim());
                            sb.append("\n");
                            sb.append(string4.trim());
                            final String string5 = sb.toString();
                            final StringBuilder sb2 = new StringBuilder();
                            sb2.append(saveingAudioPath);
                            sb2.append(File.separator);
                            sb2.append("audios");
                            sb2.append(File.separator);
                            sb2.append(string.trim());
                            sb2.append(File.separator);
                            sb2.append("reciter.txt");
                            final File file = new File(sb2.toString());
                            if (file.exists()) {
                                file.delete();
                            }
                            final StringBuilder sb3 = new StringBuilder();
                            sb3.append(saveingAudioPath);
                            sb3.append(File.separator);
                            sb3.append("audios");
                            sb3.append(File.separator);
                            sb3.append(string.trim());
                            sb3.append(File.separator);
                            sb3.append("reciter.txt");
                            dataManager.writeUTFTextFile(sb3.toString(), string5);
                            ++i;
                            b = true;
                        }
                        final SharedPreferences.Editor edit = context.getSharedPreferences("AlQuran_Audio", 0).edit();
                        edit.putInt("storedFileSize", n);
                        edit.commit();
                        edit.apply();

            }
            catch (JSONException ex5) {
                utf8StringFromZipURL = null;
            }
            catch (IOException ex) {
                utf8StringFromZipURL = null;
            }
            boolean b = false;
            return utf8StringFromZipURL != null && b;
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
            return false;
        }
    }

    @Nullable
    public Reciter getCurrentReciter() {
        final Vector<Reciter> loadAllReciters = this.loadAllReciters();
        final String s = (String)new SettingsManager(this.context).getSettings(SettingsManager.DEFAULT_RECIETER, (Class)String.class);
        for (int i = 0; i < loadAllReciters.size(); ++i) {
            if (loadAllReciters.get(i).getReciterId() == Integer.parseInt(s)) {
                return loadAllReciters.get(i);
            }
        }
        return null;
    }

    public String getSaveingAudioPath() {
        return (String)new SettingsManager(context).getSettings(SettingsManager.AUDIO_FILES_PATH, String.class);
    }

    public boolean isSoundFilesExists(final int n, final int n2) {
        final SuraInfo suraInfo = new SuraInfo(n2, (String)null, (String)null, (String)null);
        new StoragePath(context);
        for (int i = 0; i < App.getArrayOfPaths().length; ++i) {
            final StringBuilder sb = new StringBuilder();
            sb.append(App.getArrayOfPaths()[i]);
            sb.append(File.separator);
            sb.append("audios");
            sb.append(File.separator);
            sb.append(n);
            final String string = sb.toString();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(string);
            sb2.append(File.separator);
            sb2.append(suraInfo.getThreeCharsSuraId());
            sb2.append(".mp3");
            final File file = new File(sb2.toString());
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(string);
            sb3.append(File.separator);
            sb3.append(suraInfo.getThreeCharsSuraId());
            sb3.append(".dur");
            final File file2 = new File(sb3.toString());
            if (file.exists() && file2.exists()) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public Vector<Reciter> loadAllReciters() {
        final DataManager dataManager = new DataManager(this.context);
        final Vector<Reciter> vector = new Vector<Reciter>();
        final SettingsManager settingsManager = new SettingsManager(this.context);
        final String s = (String)settingsManager.getSettings(SettingsManager.AUDIO_FILES_PATH, (Class)String.class);
        final StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.append(File.separator);
        sb.append("audios");
        final File file = new File(sb.toString());
        this.copyDefaultReciterDesc();
        File[] array = file.listFiles();
        if (array == null) {
            this.copyDefaultReciterDesc();
            final String s2 = (String)settingsManager.getSettings(SettingsManager.AUDIO_FILES_PATH, (Class)String.class);
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(s2);
            sb2.append(File.separator);
            sb2.append("audios");
            array = new File(sb2.toString()).listFiles();
        }
        if (array == null) {
            settingsManager.setDefaultStorageAfterRemoveSDCard();
            this.copyDefaultReciterDesc();
            final String s3 = (String)settingsManager.getSettings(SettingsManager.AUDIO_FILES_PATH, (Class)String.class);
            final StringBuilder sb3 = new StringBuilder();
            sb3.append(s3);
            sb3.append(File.separator);
            sb3.append("audios");
            array = new File(sb3.toString()).listFiles();
        }
        if (array == null) {
            return vector;
        }
        if (array.length == 0) {
            this.copyDefaultReciterDesc();
        }
        for (final File file2 : array) {
            final StringBuilder sb4 = new StringBuilder();
            sb4.append(file2.getPath());
            sb4.append(File.separator);
            sb4.append("reciter.txt");
            final File file3 = new File(sb4.toString());
            if (!file3.exists() && file2.getPath().indexOf("133") > 0) {
                this.copyDefaultReciterDesc();
            }
            final String[] split = dataManager.readUTFTextFile(file3).split("\n");
            try {
                vector.add(new Reciter(this.context, Integer.parseInt(split[0]), split[1], split[2], split[3]));
            }
            catch (Exception ex) {}
        }
        return vector;
    }
}