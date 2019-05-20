package net.vorson.muhammadsufwan.prayertimesformuslim.quran.data;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class HTTPConnection {
    private long cancelStatus;
    private static final int BUFFER_SIZE = 4096;
    private static final String TAG = HTTPConnection.class.getSimpleName();

    public HTTPConnection() {
        cancelStatus = 0L;
    }

    @Nullable
    public String getHTTPResponse(@NonNull String url, @NonNull String encodingCode) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), encodingCode), 4096);
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                encodingCode = bufferedReader.readLine();
                if (encodingCode != null) {
                    stringBuilder.append(encodingCode);
                } else {
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public String getXmlFromUrl(@NonNull String str) {
        return getHTTPResponse(str, "utf-8");
    }


    /**
     * Downloads a file from a URL
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    public void DownloadFile(String urlInput, String saveDir, DownloadListener downloadListener) {
        long contentLength;
        InputStream openStream;
        FileOutputStream fileOutputStream;
        long j = 0;
        long j2 = 0;
        File file;
        File file2 = null;
        try {
            downloadListener.onProgress(0);
            URL url = new URL(urlInput);
            URLConnection openConnection = url.openConnection();
            openConnection.connect();
            contentLength = (long) openConnection.getContentLength();
            openStream = url.openStream();
            if (openStream != null) {
                openStream.close();
                downloadListener.error();
            }
            File file3 = new File(saveDir);
            if (!file3.exists()) {
                file3.getParentFile().mkdirs();
                j2 = contentLength;
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file3);
            FileOutputStream fileOutputStream3;
            byte[] bArr = new byte[1024];
            long j3 = 0;
            int i = 0;
            while (true) {
                int read = openStream.read(bArr);
                if (read == -1) {
                    file = file3;
                    fileOutputStream3 = fileOutputStream2;
                    break;
                }
                file = file3;
                fileOutputStream = fileOutputStream2;
                if (cancelStatus != j2) {
                    fileOutputStream3 = fileOutputStream;
                    break;
                }
                int i2;
                long j4 = (long) read;
                j3 += j4;
                if (contentLength != j2) {
                    i2 = (((int) j3) * 100) / ((int) contentLength);
                    j2 = contentLength;
                    j = j3;
                    file2 = file;
                    file2.delete();
                    downloadListener.onComplete();
                    i2 = 0;
                    if (i != i2) {
                        int i3 = (i2 < 0 || i2 > 100) ? 0 : i2;
                        downloadListener.onProgress(i3);
                        i = i2;
                    }
                    fileOutputStream2 = fileOutputStream;
                    fileOutputStream2.write(bArr, 0, read);
                    cancelStatus = downloadListener.onPacketDownloaded(j4);
                    file3 = file;
                    fileOutputStream3 = fileOutputStream2;
                    j = j3;
                    file2 = file;
                    file2.delete();
                    downloadListener.onComplete();
                    if (cancelStatus != 0) {
                        file.delete();
                    }
                    if (openStream != null) {
                        openStream.close();
                    }
                    fileOutputStream3.close();
                    if (contentLength < j3) {
                        file.delete();
                        downloadListener.error();
                        return;
                    }
                    file = file3;
                    fileOutputStream = fileOutputStream2;
                    j2 = contentLength;
                    file2 = file;
                    file2.delete();
                    downloadListener.onComplete();
                }
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }catch (IOException io){
            Log.e(TAG, io.getMessage());
        }

    }


    public static String getUTF8StringFromZipURL(URL url){
        String str = null;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            try {
                ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new BufferedInputStream(httpURLConnection.getInputStream())));
                Object obj = new byte[1024];
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    System.out.println(nextEntry);
                    Object obj2 = new byte[((int) nextEntry.getSize())];
                    int i = 0;
                    while (true) {
                        int read = zipInputStream.read((byte[]) obj, 0, 1024);
                        if (read < 0) {
                            break;
                        }
                        System.arraycopy(obj, 0, obj2, i, read);
                        i += read;
                    }
                    str = new String((byte[]) obj2, Charset.forName("UTF-8"));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            httpURLConnection = null;
            e.printStackTrace();
        } catch (IOException e) {
            httpURLConnection = null;
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return str;
    }

}
