package com.aliyun.player.alivcplayerexpand.util;

import android.os.AsyncTask;
import com.aliyun.player.source.VidSts;
import com.cicada.player.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VidStsUtil {
    public static final String BASE_URL = "https://alivc-demo.aliyuncs.com";
    private static final String TAG = "VidStsUtil";

    public interface OnStsResultListener {
        void onFail();

        void onSuccess(String str, String str2, String str3, String str4);
    }

    public static VidSts getVidSts(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(HttpClientUtil.doGet("https://alivc-demo.aliyuncs.com/demo/getSts")).getJSONObject("data");
            if (jSONObject == null) {
                return null;
            }
            String string = jSONObject.getString("accessKeyId");
            String string2 = jSONObject.getString("accessKeySecret");
            String string3 = jSONObject.getString("securityToken");
            jSONObject.getString("expiration");
            VidSts vidSts = new VidSts();
            vidSts.setVid(str);
            vidSts.setAccessKeyId(string);
            vidSts.setAccessKeySecret(string2);
            vidSts.setSecurityToken(string3);
            return vidSts;
        } catch (Exception e2) {
            Logger.e(TAG, "e = " + e2.getMessage());
            return null;
        }
    }

    public static void getVidSts(final String str, final OnStsResultListener onStsResultListener) {
        new AsyncTask<Void, Void, VidSts>() { // from class: com.aliyun.player.alivcplayerexpand.util.VidStsUtil.1
            @Override // android.os.AsyncTask
            public VidSts doInBackground(Void... voidArr) {
                return VidStsUtil.getVidSts(str);
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(VidSts vidSts) {
                if (vidSts == null) {
                    onStsResultListener.onFail();
                } else {
                    onStsResultListener.onSuccess(vidSts.getVid(), vidSts.getAccessKeyId(), vidSts.getAccessKeySecret(), vidSts.getSecurityToken());
                }
            }
        }.execute(new Void[0]);
    }
}
