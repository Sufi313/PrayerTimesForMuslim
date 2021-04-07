package com.sufi.prayertimes.quran.data;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class DataManager {
    private Context mContext;

    public DataManager(Context context) {
        mContext = context;
    }

    @Nullable
    public String getQuranSuraContents(int i) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("data/");
            stringBuilder.append(i);
            stringBuilder.append(".dat");
            return loadFileFromAssets(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public String getQuranSuraContents(int i, int i2, int i3) {
        if (i == 1 && i2 == 1 && i3 == 1) {
            return "";
        }
        String quranSuraContents = getQuranSuraContents(i);
        String ayahNumberOfDataFromInt = getAyahNumberOfDataFromInt(i2 - 1);
        String ayahNumberOfDataFromInt2 = getAyahNumberOfDataFromInt(i3);
        if (ayahNumberOfDataFromInt == "") {
            i2 = 0;
        } else {
            i2 = (quranSuraContents.indexOf(ayahNumberOfDataFromInt) + ayahNumberOfDataFromInt.length()) + 1;
        }
        quranSuraContents = quranSuraContents.substring(i2);
        return quranSuraContents.substring(0, quranSuraContents.indexOf(ayahNumberOfDataFromInt2) + ayahNumberOfDataFromInt2.length());
    }

    @NonNull
    public String loadFileFromAssets(String str) throws IOException {
        InputStream open = mContext.getAssets().open(str);
        byte[] bArr = new byte[open.available()];
        open.read(bArr);
        open.close();
        return new String(bArr);
    }

    @Nullable
    public String loadArabicFile(String str) {
        try {
            InputStream open = mContext.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, "Windows-1256");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeTextFile(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        File file = new File(str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName(str3)));
            bufferedWriter.write(str2);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }


    public void writeUTFTextFile(String r5, String r6) {

        File file = new File(r6);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8"));

            out.write(r5);
            out.flush();
            out.close();

        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @NonNull
    public String readTextFile(@NonNull File file) {
        String str = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(readLine);
                stringBuilder.append("\n");
                str = stringBuilder.toString();
            }
            bufferedReader.close();
        } catch (Exception unused) {
            unused.printStackTrace();
        }
        return str;
    }

    @NonNull
    public Vector<String> readTextFileToVector(@NonNull File file) {
        Vector<String> vector = new Vector();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                vector.add(readLine);
            }
            bufferedReader.close();
        } catch (Exception unused) {
            unused.printStackTrace();
        }
        return vector;
    }

    @NonNull
    public List<List<Integer>> readTextFileToListOfList(@NonNull File file) {
        List<List<Integer>> arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        List arrayList3 = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split(",");
                arrayList3.add(Integer.valueOf(Integer.parseInt(split[0])));
                arrayList2.add(Integer.valueOf(Integer.parseInt(split[1])));
            }
            bufferedReader.close();
        } catch (Exception unused) {
            unused.printStackTrace();
        }
        arrayList.add(arrayList3);
        arrayList.add(arrayList2);
        return arrayList;
    }

    @NonNull
    public List[] readTextFileToListOfListNew(@NonNull File file) {
        List[] listArr = new List[3];
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split(",");
                arrayList2.add(Integer.valueOf(Integer.parseInt(split[0])));
                arrayList.add(Integer.valueOf(Integer.parseInt(split[1])));
            }
            bufferedReader.close();
        } catch (Exception unused) {
            unused.printStackTrace();
        }
        listArr[0] = arrayList2;
        listArr[1] = arrayList;
        return listArr;
    }

    @NonNull
    public List[] readTextFileToListOfListNew1(@NonNull File file, int i) {
        List[] listArr = new List[3];
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        List arrayList3 = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split(",");
                arrayList3.add(Integer.valueOf(Integer.parseInt(split[0])));
                arrayList.add(Integer.valueOf(Integer.parseInt(split[1])));
                arrayList2.add(Integer.valueOf(Integer.parseInt(split[1]) + i));
            }
            bufferedReader.close();
        } catch (Exception unused) {
            unused.printStackTrace();
        }
        listArr[0] = arrayList3;
        listArr[1] = arrayList;
        listArr[2] = arrayList2;
        return listArr;
    }

    @NonNull
    public String readUTFTextFile(@NonNull File file) {
        String str = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(readLine);
                stringBuilder.append("\n");
                str = stringBuilder.toString();
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void copyFile(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    @NonNull
    public String getDataAyaNumber(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append("");
        String stringBuilder2 = stringBuilder.toString();
        String str = "";
        for (int length = stringBuilder2.length() - 1; length >= 0; length--) {
            StringBuilder stringBuilder3;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(stringBuilder2.charAt(length));
            stringBuilder4.append("");
            int parseInt = Integer.parseInt(stringBuilder4.toString()) + 2201;
            if (length < stringBuilder2.length() - 1) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str);
                stringBuilder3.append(",");
                str = stringBuilder3.toString();
            }
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(str);
            stringBuilder3.append(parseInt);
            str = stringBuilder3.toString();
        }
        return str;
    }

    @NonNull
    public String getAyahNumberOfDataFromInt(int i) {
        if (i == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("2200,");
        stringBuilder.append(getDataAyaNumber(i));
        stringBuilder.append(",2199");
        return stringBuilder.toString();
    }
}
