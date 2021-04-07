package com.sufi.prayertimes.quran.data;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

import io.reactivex.annotations.NonNull;

public class FileManager {
    public static void copyDirectory(@NonNull File file, @NonNull File file2) throws IOException {
        int i = 0;
        if (!file.isDirectory()) {
            File parentFile = file2.getParentFile();
            if (parentFile == null || parentFile.exists() || parentFile.mkdirs()) {
                InputStream fileInputStream = new FileInputStream(file);
                OutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        fileOutputStream.close();
                        return;
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot create dir ");
            stringBuilder.append(parentFile.getAbsolutePath());
            throw new IOException(stringBuilder.toString());
        } else if (file2.exists() || file2.mkdirs()) {
            String[] list = file.list();
            while (i < list.length) {
                copyDirectory(new File(file, list[i]), new File(file2, list[i]));
                i++;
            }
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Cannot create dir ");
            stringBuilder2.append(file2.getAbsolutePath());
            throw new IOException(stringBuilder2.toString());
        }
    }

    public static String readRawTextFile(Context context, int i) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(i), Charset.forName("UTF-8")));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return stringBuilder.toString().trim();
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(readLine);
                stringBuilder2.append("\n");
                stringBuilder.append(stringBuilder2.toString());
            } catch (IOException unused) {
                return null;
            }
        }
    }
}
