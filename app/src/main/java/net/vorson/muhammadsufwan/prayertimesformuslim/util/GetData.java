package net.vorson.muhammadsufwan.prayertimesformuslim.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

public class GetData extends AsyncTask<Void, Integer, String> {

    private RequestHandler requestHandler = new RequestHandler();
    private String url;
    private HashMap<String, String> params;
    private GetDataListener asyncResponse;
    private int requestCode;
    private ProgressDialog progressDialog;;


    public GetData(String url, HashMap<String, String> params, int requestCode, Context context) {
        this.url  = url;
        this.params = params;
        this.requestCode = requestCode;
        asyncResponse = (GetDataListener) context;
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("GetData----->","Service Start");
        progressDialog.setTitle("Loading..");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(Void... voids) {

        return requestHandler.sendPostRequest(url, params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Log.d("GetData----->","Service End");
        if (asyncResponse != null) {
            //post result with given requestCode
            this.asyncResponse.getDownloadData(s, requestCode);
            Log.d("GET DATA",s);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        for (int value : values){
            progressDialog.setProgress(value);
        }

    }

    public interface GetDataListener {

        void getDownloadData(String result, int request);

    }

}
