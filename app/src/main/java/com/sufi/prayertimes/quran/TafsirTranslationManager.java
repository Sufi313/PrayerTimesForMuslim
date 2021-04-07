package com.sufi.prayertimes.quran;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.sufi.prayertimes.quran.data.DataManager;
import com.sufi.prayertimes.quran.data.DownloadListener;
import com.sufi.prayertimes.quran.data.HTTPConnection;
import com.sufi.prayertimes.quran.data.TafsirTranslationObj;
import com.sufi.prayertimes.quran.data.XMLParser;
import com.sufi.prayertimes.quran.interfaces.IDownloadTafsirManagerListener;
import com.sufi.prayertimes.quran.models.StoragePath;
import com.sufi.prayertimes.settingsAndPreferences.AppSettings;
import com.sufi.prayertimes.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipFile;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class TafsirTranslationManager implements DownloadListener {
    private Context context;
    String[] currentSuraData;
    int currentSuraId;
    private TafsirTranslationObj currentTafsirTranslationObj;
    private IDownloadTafsirManagerListener iDownloadTafsirManagerListener;
    List<TafsirTranslationObj> tafsirTranslationObjs;

    public void error() {
    }

    public void onComplete() {
    }

    public void onProgress(int i) {
    }

    public TafsirTranslationManager(Context context) {
        this.context = context;
        copyDefaultReciterDesc();
        loadAllTafsirs();
        removeDublecatesAndSort();
    }

    public String getTranslation(String str, int i, int i2) {
        if (i2 == -1) {
            return "";
        }
        if (currentTafsirTranslationObj == null || currentTafsirTranslationObj.getId() != str) {
            for (int i3 = 0; i3 < tafsirTranslationObjs.size(); i3++) {
                if (((TafsirTranslationObj) tafsirTranslationObjs.get(i3)).getId().equalsIgnoreCase(str)) {
                    currentTafsirTranslationObj = (TafsirTranslationObj) tafsirTranslationObjs.get(i3);
                    break;
                }
            }
            if (currentTafsirTranslationObj == null || !currentTafsirTranslationObj.getId().equals(str)) {
                return "";
            }
        }
        if (currentSuraId != i && !loadTafsirSuraData(i)) {
            return "";
        }
        if (currentSuraData == null) {
            return "";
        }
        i2--;
        return i2 < currentSuraData.length ? currentSuraData[i2] : "";
    }

    public boolean loadTafsirSuraData(int i) {
        ZipFile zipFile = currentTafsirTranslationObj.getZipFile();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(".txt");
        try {
            InputStream inputStream = currentTafsirTranslationObj.getZipFile().getInputStream(zipFile.getEntry(stringBuilder.toString()));
            byte[] bArr = new byte[inputStream.available()];
            inputStream.read(bArr);
            inputStream.close();
            currentSuraData = new String(bArr, currentTafsirTranslationObj.getLangauge().equals("عربي") ? "Windows-1256" : "UTF-8").split("\n");
            currentSuraId = i;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadAllTafsirs() {
        tafsirTranslationObjs = new ArrayList();
        AppSettings settingsManager = AppSettings.getInstance(context);
        StoragePath storagePath = new StoragePath(context);
        for (String append : App.getArrayOfPaths()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(append);
            stringBuilder.append(File.separator);
            stringBuilder.append("tafsir");
            File file = new File(stringBuilder.toString());
            if (file.exists()) {
                File[] listFiles = file.listFiles();
                for (File zipFile : listFiles) {
                    try {
                        tafsirTranslationObjs.add(getTrfsirObjFromZipFile(new ZipFile(zipFile)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public List<TafsirTranslationObj> getAllTafsir() {
        return tafsirTranslationObjs;
    }

    private TafsirTranslationObj getTrfsirObjFromZipFile(@NonNull ZipFile zipFile) {
        try {
            InputStream inputStream = zipFile.getInputStream(zipFile.getEntry("info.txt"));
            byte[] bArr = new byte[inputStream.available()];
            inputStream.read(bArr);
            inputStream.close();
            String[] split = new String(bArr, "UTF-8").split("\r\n");
            return new TafsirTranslationObj(split[0], split[2], split[1], split[3], zipFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void removeDublecatesAndSort() {
        Collections.sort(tafsirTranslationObjs, new Comparator<TafsirTranslationObj>() {
            public int compare(@NonNull TafsirTranslationObj tafsirTranslationObj, @NonNull TafsirTranslationObj tafsirTranslationObj2) {
                return tafsirTranslationObj.getLangauge().compareToIgnoreCase(tafsirTranslationObj2.getLangauge());
            }
        });
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        for (int i = 0; i < tafsirTranslationObjs.size(); i++) {
            if (!arrayList2.contains(((TafsirTranslationObj) tafsirTranslationObjs.get(i)).getId())) {
                arrayList.add(tafsirTranslationObjs.get(i));
                arrayList2.add(((TafsirTranslationObj) tafsirTranslationObjs.get(i)).getId());
            }
        }
        tafsirTranslationObjs = arrayList;
    }

    @NonNull
    public Boolean copyDefaultReciterDesc() {
        Boolean valueOf = Boolean.valueOf(true);
        String[] strArr = new String[0];
        try {
            strArr = context.getAssets().list("tafsir");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Boolean bool = valueOf;
        int i = 0;
        while (i < strArr.length) {
            AppSettings settingsManager = AppSettings.getInstance(context);
            StoragePath storagePath = new StoragePath(context);
            try {
                AssetManager assets = context.getAssets();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("tafsir/");
                stringBuilder.append(strArr[i]);
                InputStream open = assets.open(stringBuilder.toString());
                if (App.getArrayOfPaths().length < 1) {
                    return Boolean.valueOf(false);
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(App.getArrayOfPaths()[0]);
                stringBuilder2.append(File.separator);
                stringBuilder2.append("tafsir");
                stringBuilder2.append(File.separator);
                stringBuilder2.append(strArr[i]);
                File file = new File(stringBuilder2.toString());
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    OutputStream fileOutputStream = new FileOutputStream(file);
                    new DataManager(context).copyFile(open, fileOutputStream);
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    bool = Boolean.valueOf(true);
                }
                i++;
            } catch (Exception e2) {
                Log.e("tag", e2.getMessage());
                bool = Boolean.valueOf(false);
            }
        }
        return bool;
    }

    @Nullable
    public List<TafsirTranslationObj> downloadAllTafsirProfiles() {
        String hTTPResponse;
        List<TafsirTranslationObj> arrayList = new ArrayList();
        HTTPConnection hTTPConnection = new HTTPConnection();
        XMLParser xMLParser = new XMLParser();
        DataManager dataManager = new DataManager(context);
        hTTPResponse = hTTPConnection.getHTTPResponse("http://www.isysway.com/backends/quran/tafsir-translation.xml", "utf-8");
        if (hTTPResponse == null) {
            return null;
        }
        NodeList elementsByTagName = xMLParser.getDomElement(hTTPResponse).getElementsByTagName("Table");
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Element element = (Element) elementsByTagName.item(i);
            TafsirTranslationObj tafsirTranslationObj = new TafsirTranslationObj(xMLParser.getValue(element, "tafsir_id"), xMLParser.getValue(element, "tafsir_name"), xMLParser.getValue(element, "language"), xMLParser.getValue(element, "translator"), xMLParser.getValue(element, "link"));
            if (findObjectInList(tafsirTranslationObjs, tafsirTranslationObj)) {
                tafsirTranslationObj.setIsSelected(true);
                tafsirTranslationObj.setIsEnabled(false);
            } else {
                tafsirTranslationObj.setIsSelected(false);
                tafsirTranslationObj.setIsEnabled(true);
            }
            arrayList.add(tafsirTranslationObj);
        }
        Collections.sort(arrayList, new Comparator<TafsirTranslationObj>() {
            public int compare(@NonNull TafsirTranslationObj tafsirTranslationObj, @NonNull TafsirTranslationObj tafsirTranslationObj2) {
                return tafsirTranslationObj.getLangauge().compareToIgnoreCase(tafsirTranslationObj2.getLangauge());
            }
        });
        return arrayList;
    }

    private boolean findObjectInList(@NonNull List<TafsirTranslationObj> list, @NonNull TafsirTranslationObj tafsirTranslationObj) {
        for (TafsirTranslationObj id : list) {
            if (id.getId().equalsIgnoreCase(tafsirTranslationObj.getId())) {
                return true;
            }
        }
        return false;
    }

    public int downloadTafsirNow(@NonNull List<TafsirTranslationObj> list, @NonNull IDownloadTafsirManagerListener iDownloadTafsirManagerListener) {
        this.iDownloadTafsirManagerListener = iDownloadTafsirManagerListener;
        int calculateFilesLingths = calculateFilesLingths(list);
        if (calculateFilesLingths == -1) {
            return -1;
        }
        iDownloadTafsirManagerListener.onCalculateFileLengthesFinished(calculateFilesLingths);
        AppSettings settingsManager = AppSettings.getInstance(context);
        StoragePath storagePath = new StoragePath(context);
        for (int i = 0; i < list.size(); i++) {
            HTTPConnection hTTPConnection = new HTTPConnection();
            String link = (list.get(i)).getLink();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(App.getArrayOfPaths()[0]);
            stringBuilder.append(File.separator);
            stringBuilder.append("tafsir");
            stringBuilder.append(File.separator);
            stringBuilder.append(((TafsirTranslationObj) list.get(i)).getId());
            stringBuilder.append(".zip");
            hTTPConnection.DownloadFile(link, stringBuilder.toString(), this);
            double d = (double) i;
            Double.isNaN(d);
            d += 1.0d;
            double size = (double) list.size();
            Double.isNaN(size);
            iDownloadTafsirManagerListener.onTotalDownloadProgress((int) ((d / size) * 100.0d));
        }
        iDownloadTafsirManagerListener.onTotalDownloadCompleted();
        return 0;
    }

    public long onPacketDownloaded(long j) {
        return iDownloadTafsirManagerListener.onTotalDownloadPacket(j);
    }

    private int calculateFilesLingths(@NonNull List<TafsirTranslationObj> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            try {
                URLConnection openConnection = new URL(((TafsirTranslationObj) list.get(i2)).getLink()).openConnection();
                openConnection.connect();
                i += openConnection.getContentLength();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                i = -1;
            } catch (IOException e2) {
                e2.printStackTrace();
                i = -1;
            }
        }
        return i;
    }

}
