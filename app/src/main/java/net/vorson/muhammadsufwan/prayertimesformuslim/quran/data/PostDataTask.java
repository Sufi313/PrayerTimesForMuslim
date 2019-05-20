package net.vorson.muhammadsufwan.prayertimesformuslim.quran.data;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;

import net.vorson.muhammadsufwan.prayertimesformuslim.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PostDataTask extends AsyncTask<String, Void, Boolean> {
    public static final String EMAIL_KEY = "entry.319564475";
    public static final MediaType FORM_DATA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    public static final String MESSAGE_KEY = "entry.1667929341";
    public static final String SUBJECT_KEY = "entry.886067842";
    public static final String URL = "https://docs.google.com/forms/d/1mJABY-nnLmhBu24usJq4jSWT49bJWHq52TeTxqcsAPM/formResponse";
    private final Context context;

    public PostDataTask(Context context) {
        this.context = context;
    }

    protected Boolean doInBackground(String... strArr) {
        Boolean valueOf = Boolean.valueOf(true);
        String str = strArr[0];
        String str2 = strArr[1];
        String str3 = strArr[2];
        String str4 = strArr[3];
        String str5 = "";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("entry.319564475=");
            stringBuilder.append(URLEncoder.encode(str2, "UTF-8"));
            stringBuilder.append("&");
            stringBuilder.append(SUBJECT_KEY);
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(str3, "UTF-8"));
            stringBuilder.append("&");
            stringBuilder.append(MESSAGE_KEY);
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(str4, "UTF-8"));
            str4 = stringBuilder.toString();
        } catch (UnsupportedEncodingException unused) {
            valueOf = Boolean.valueOf(false);
            str4 = str5;
        }
        try {
            new OkHttpClient().newCall(new Builder().url(str).post(RequestBody.create(FORM_DATA_TYPE, str4)).build()).execute();
            return valueOf;
        } catch (IOException unused2) {
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean bool) {
        Context context;
        int i;
        Context context2 = this.context;
        if (bool.booleanValue()) {
            context = this.context;
            i = R.string.send_successfully;
        } else {
            context = this.context;
            i = R.string.send_error;
        }
        Toast.makeText(context2, context.getString(i), Toast.LENGTH_LONG).show();
    }
}
