package com.beizi.fusion.g;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes2.dex */
public class ae extends AsyncTask<String, Void, String> {
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public String doInBackground(String... strArr) throws IOException {
        String str = strArr[0];
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Log.d("NetworkRequestAsyncTask", "开始请求应用信息:" + str);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    inputStreamReader.close();
                    return sb.toString();
                }
                sb.append(line);
            }
        } catch (IOException e2) {
            Log.e("NetworkRequestAsyncTask", "请求应用信息错误", e2);
            return null;
        }
    }
}
