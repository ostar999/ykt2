package com.psychiatrygarden.platforms;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class OppoDevUtils {
    private static final String API_TOKEN = "https://openapi.heytapmobi.com/oauth2/v1/token";
    private static final String CLIENT_ID = "1898724953138073779";
    private static final String CLIENT_SECRET = "0f5d60de92acbde2fba5f5353603628424339efb2572d9b1c41308f7adfa0db6";
    private static final String DOMAIN = "https://openapi.heytapmobi.com";
    private static final String SELF_UPDATE_API = "https://openapi.heytapmobi.com/selfupdate/query/version";

    public static boolean checkHasUpdate() throws PackageManager.NameNotFoundException, IOException {
        JSONObject jSONObjectOptJSONObject;
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://openapi.heytapmobi.com/selfupdate/query/version?pkg=" + ProjectApp.instance().getPackageName());
        httpGet.addHeader("Authorization", getAuthToken());
        httpGet.addHeader("X-Client-Send-Utc-Ms", System.currentTimeMillis() + "");
        try {
            InputStream content = defaultHttpClient.execute(httpGet).getEntity().getContent();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(content.available());
            byte[] bArr = new byte[1024];
            while (content.read(bArr) != -1) {
                byteArrayOutputStream.write(bArr);
            }
            JSONObject jSONObject = new JSONObject(byteArrayOutputStream.toString("UTF-8"));
            if (TextUtils.equals("200", jSONObject.optString("status", "0")) && (jSONObjectOptJSONObject = jSONObject.optJSONObject("result")) != null && TextUtils.equals(jSONObjectOptJSONObject.optString("pkg"), ProjectApp.instance().getPackageName())) {
                int iOptInt = jSONObjectOptJSONObject.optInt("fullVersionCode", -1);
                PackageInfo packageInfo = ProjectApp.instance().getPackageManager().getPackageInfo(ProjectApp.instance().getPackageName(), 1);
                if (iOptInt > 0) {
                    return iOptInt > packageInfo.versionCode;
                }
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private static String getAuthToken() throws IOException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jLongValue = SharePreferencesUtils.readLongConfig(CommonParameter.OPPO_API_ACCESS_TOKEN_EXP, ProjectApp.instance(), 0L).longValue();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.OPPO_API_ACCESS_TOKEN, ProjectApp.instance());
        if (!TextUtils.isEmpty(strConfig) && jLongValue > 0 && jCurrentTimeMillis - 600000 <= jLongValue) {
            return strConfig;
        }
        try {
            HttpResponse httpResponseExecute = new DefaultHttpClient().execute(new HttpGet("https://openapi.heytapmobi.com/oauth2/v1/token?client_id=1898724953138073779&client_secret=0f5d60de92acbde2fba5f5353603628424339efb2572d9b1c41308f7adfa0db6&grant_type=client_credentials"));
            if (httpResponseExecute.getStatusLine().getStatusCode() == 200) {
                InputStream content = httpResponseExecute.getEntity().getContent();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                        sb.append("\n");
                    } finally {
                    }
                }
                if (!TextUtils.isEmpty(sb)) {
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    if (jSONObject.optInt("code", 1) == 0) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            bufferedReader.close();
                            return null;
                        }
                        String strOptString = jSONObjectOptJSONObject.optString("access_token", null);
                        if (!TextUtils.isEmpty(strOptString)) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.OPPO_API_ACCESS_TOKEN, strOptString + "", ProjectApp.instance());
                            SharePreferencesUtils.writeLongConfig(CommonParameter.OPPO_API_ACCESS_TOKEN_EXP, Long.valueOf(System.currentTimeMillis() + (((long) jSONObjectOptJSONObject.optInt("expire_in", 0)) * 1000)), ProjectApp.instance());
                            bufferedReader.close();
                            return strOptString;
                        }
                    }
                }
                bufferedReader.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }
}
