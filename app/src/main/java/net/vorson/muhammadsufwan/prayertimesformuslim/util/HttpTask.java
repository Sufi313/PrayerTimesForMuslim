package net.vorson.muhammadsufwan.prayertimesformuslim.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.ItemTouchHelper;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpTask extends AsyncTask<Void, Void, JSONObject> {
    JSONObject json;
    JSONObject json_result;
    private OnTaskCompleted listener;
//    private Context mContext;
    List<NameValuePair> nameValuePairs;
    private WeakReference<Context> mContext;
    private ProgressDialog pd;
    String result;
    Boolean show_progress;
    String url;
//    public static final String UTF8_NAME = "UTF-8";
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public interface OnTaskCompleted {
        void onTaskCompleted(JSONObject jSONObject);
    }

    public HttpTask(OnTaskCompleted listener, Context context, String url, List<NameValuePair> data, Boolean progress) {
        this.listener = listener;
        this.mContext = new WeakReference<>(context);
        this.nameValuePairs = data;
        this.url = url;
        this.show_progress = progress;
    }

    protected void onPostExecute(JSONObject result) {
        if (show_progress) {
            pd.dismiss();
        }
        OnTaskCompleted onTaskCompleted = listener;
        if (onTaskCompleted != null && result != null) {
            onTaskCompleted.onTaskCompleted(result);
        }
    }
    // 2354-17

    protected void onPreExecute() {
        Log.d("httpreq", "PREEXE");
        if (show_progress) {
            pd = new ProgressDialog(mContext.get());
            pd.setProgressStyle(0);
            pd.setMessage("Connection in Progress...");
            pd.setCancelable(false);
            pd.show();
        }
        super.onPreExecute();
    }

    protected JSONObject doInBackground(Void... params) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try {
            if (nameValuePairs != null) {
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs, UTF_8.toString()));
            }
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                result = new String(EntityUtils.toByteArray(response.getEntity()), UTF_8);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Server response:");
            stringBuilder.append(result);
            Log.d("httpreq", stringBuilder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return json_result;
    }
}
