package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.util.Base64;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b extends AsyncTask<Void, Void, a> {

    /* renamed from: a, reason: collision with root package name */
    private String f20428a;

    /* renamed from: b, reason: collision with root package name */
    private String f20429b;

    /* renamed from: c, reason: collision with root package name */
    private String f20430c;

    /* renamed from: d, reason: collision with root package name */
    private String f20431d;

    /* renamed from: e, reason: collision with root package name */
    private String f20432e;

    /* renamed from: f, reason: collision with root package name */
    private OAuthListener f20433f;

    /* renamed from: g, reason: collision with root package name */
    private c f20434g;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public OAuthErrCode f20435a;

        /* renamed from: b, reason: collision with root package name */
        public String f20436b;

        /* renamed from: c, reason: collision with root package name */
        public String f20437c;

        /* renamed from: d, reason: collision with root package name */
        public String f20438d;

        /* renamed from: e, reason: collision with root package name */
        public byte[] f20439e;

        private a() {
        }

        public static a a(byte[] bArr) throws JSONException {
            OAuthErrCode oAuthErrCode;
            String str;
            a aVar = new a();
            if (bArr != null && bArr.length != 0) {
                try {
                } catch (Exception e2) {
                    str = String.format("parse fail, build String fail, ex = %s", e2.getMessage());
                }
                try {
                    JSONObject jSONObject = new JSONObject(new String(bArr, "utf-8"));
                    int i2 = jSONObject.getInt("errcode");
                    if (i2 != 0) {
                        Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("resp errcode = %d", Integer.valueOf(i2)));
                        aVar.f20435a = OAuthErrCode.WechatAuth_Err_NormalErr;
                        jSONObject.optString("errmsg");
                        return aVar;
                    }
                    String string = jSONObject.getJSONObject("qrcode").getString("qrcodebase64");
                    if (string != null && string.length() != 0) {
                        byte[] bArrDecode = Base64.decode(string, 0);
                        if (bArrDecode != null && bArrDecode.length != 0) {
                            aVar.f20435a = OAuthErrCode.WechatAuth_Err_OK;
                            aVar.f20439e = bArrDecode;
                            aVar.f20436b = jSONObject.getString(AliyunLogKey.KEY_UUID);
                            String string2 = jSONObject.getString("appname");
                            aVar.f20437c = string2;
                            Log.d("MicroMsg.SDK.GetQRCodeResult", String.format("parse succ, save in memory, uuid = %s, appname = %s, imgBufLength = %d", aVar.f20436b, string2, Integer.valueOf(aVar.f20439e.length)));
                            return aVar;
                        }
                        Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBuf is null");
                        aVar.f20435a = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                        return aVar;
                    }
                    Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBase64 is null");
                    aVar.f20435a = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                    return aVar;
                } catch (Exception e3) {
                    str = String.format("parse json fail, ex = %s", e3.getMessage());
                    Log.e("MicroMsg.SDK.GetQRCodeResult", str);
                    oAuthErrCode = OAuthErrCode.WechatAuth_Err_NormalErr;
                    aVar.f20435a = oAuthErrCode;
                    return aVar;
                }
            }
            Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, buf is null");
            oAuthErrCode = OAuthErrCode.WechatAuth_Err_NetworkErr;
            aVar.f20435a = oAuthErrCode;
            return aVar;
        }
    }

    public b(String str, String str2, String str3, String str4, String str5, OAuthListener oAuthListener) {
        this.f20428a = str;
        this.f20429b = str2;
        this.f20430c = str3;
        this.f20431d = str4;
        this.f20432e = str5;
        this.f20433f = oAuthListener;
    }

    public boolean a() {
        Log.i("MicroMsg.SDK.GetQRCodeTask", "cancelTask");
        c cVar = this.f20434g;
        return cVar == null ? cancel(true) : cVar.cancel(true);
    }

    @Override // android.os.AsyncTask
    public a doInBackground(Void[] voidArr) throws Throwable {
        Thread.currentThread().setName("OpenSdkGetQRCodeTask");
        Log.i("MicroMsg.SDK.GetQRCodeTask", "doInBackground");
        String str = String.format("https://open.weixin.qq.com/connect/sdk/qrconnect?appid=%s&noncestr=%s&timestamp=%s&scope=%s&signature=%s", this.f20428a, this.f20430c, this.f20431d, this.f20429b, this.f20432e);
        long jCurrentTimeMillis = System.currentTimeMillis();
        byte[] bArrA = com.tencent.mm.opensdk.channel.a.a.a(str, 60000);
        Log.d("MicroMsg.SDK.GetQRCodeTask", String.format("doInBackground, url = %s, time consumed = %d(ms)", str, Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis)));
        return a.a(bArrA);
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(a aVar) {
        a aVar2 = aVar;
        OAuthErrCode oAuthErrCode = aVar2.f20435a;
        if (oAuthErrCode != OAuthErrCode.WechatAuth_Err_OK) {
            Log.e("MicroMsg.SDK.GetQRCodeTask", String.format("onPostExecute, get qrcode fail, OAuthErrCode = %s", oAuthErrCode));
            this.f20433f.onAuthFinish(aVar2.f20435a, null);
            return;
        }
        Log.d("MicroMsg.SDK.GetQRCodeTask", "onPostExecute, get qrcode success imgBufSize = " + aVar2.f20439e.length);
        this.f20433f.onAuthGotQrcode(aVar2.f20438d, aVar2.f20439e);
        c cVar = new c(aVar2.f20436b, this.f20433f);
        this.f20434g = cVar;
        cVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }
}
