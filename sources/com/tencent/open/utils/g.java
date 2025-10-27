package com.tencent.open.utils;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import cn.hutool.core.text.StrPool;
import com.tencent.connect.common.Constants;
import com.tencent.open.log.SLog;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, g> f20668a = Collections.synchronizedMap(new HashMap());

    /* renamed from: b, reason: collision with root package name */
    private static String f20669b = null;

    /* renamed from: c, reason: collision with root package name */
    private Context f20670c;

    /* renamed from: d, reason: collision with root package name */
    private String f20671d;

    /* renamed from: e, reason: collision with root package name */
    private JSONObject f20672e = null;

    /* renamed from: f, reason: collision with root package name */
    private long f20673f = 0;

    /* renamed from: g, reason: collision with root package name */
    private int f20674g = 0;

    /* renamed from: h, reason: collision with root package name */
    private boolean f20675h = true;

    private g(Context context, String str) {
        this.f20670c = null;
        this.f20671d = null;
        this.f20670c = context.getApplicationContext();
        this.f20671d = str;
        a();
        b();
    }

    private void b() {
        if (this.f20674g != 0) {
            d("update thread is running, return");
            return;
        }
        this.f20674g = 1;
        final HashMap map = new HashMap();
        map.put("appid", this.f20671d);
        map.put("status_os", Build.VERSION.RELEASE);
        map.put("status_machine", Build.MODEL);
        map.put("status_version", Build.VERSION.SDK);
        map.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        map.put("sdkp", am.av);
        j.a(new Runnable() { // from class: com.tencent.open.utils.g.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    com.tencent.open.a.b bVarA = com.tencent.open.a.a.a().a("https://cgi.connect.qq.com/qqconnectopen/openapi/policy_conf", map);
                    String strA = bVarA.a();
                    SLog.i("openSDK_LOG.OpenConfig", "update: get config statusCode " + bVarA.d());
                    g.this.a(k.d(strA));
                } catch (Exception e2) {
                    SLog.e("openSDK_LOG.OpenConfig", "get config error ", e2);
                }
                g.this.f20674g = 0;
            }
        });
    }

    private String c(String str) throws IOException {
        InputStream inputStreamOpen;
        String str2;
        String string = "";
        try {
            try {
                if (this.f20671d != null) {
                    str2 = str + StrPool.DOT + this.f20671d;
                } else {
                    str2 = str;
                }
                inputStreamOpen = this.f20670c.openFileInput(str2);
            } catch (FileNotFoundException unused) {
                inputStreamOpen = this.f20670c.getAssets().open(str);
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpen, Charset.forName("UTF-8")));
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    try {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            stringBuffer.append(line);
                        } catch (Throwable th) {
                            try {
                                inputStreamOpen.close();
                                bufferedReader.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        inputStreamOpen.close();
                        bufferedReader.close();
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            string = stringBuffer.toString();
            inputStreamOpen.close();
            bufferedReader.close();
            return string;
        } catch (IOException e5) {
            e5.printStackTrace();
            return "";
        }
    }

    private void d(String str) {
        if (this.f20675h) {
            SLog.v("openSDK_LOG.OpenConfig", str + "; appid: " + this.f20671d);
        }
    }

    public static g a(Context context, String str) {
        g gVar;
        synchronized (f20668a) {
            SLog.v("openSDK_LOG.OpenConfig", "getInstance begin");
            if (str != null) {
                f20669b = str;
            }
            if (str == null && (str = f20669b) == null) {
                str = "0";
            }
            gVar = f20668a.get(str);
            if (gVar == null) {
                gVar = new g(context, str);
                f20668a.put(str, gVar);
            }
            SLog.v("openSDK_LOG.OpenConfig", "getInstance end");
        }
        return gVar;
    }

    public boolean b(String str) {
        d("get " + str);
        c();
        Object objOpt = this.f20672e.opt(str);
        if (objOpt == null) {
            return false;
        }
        if (objOpt instanceof Integer) {
            return !objOpt.equals(0);
        }
        if (objOpt instanceof Boolean) {
            return ((Boolean) objOpt).booleanValue();
        }
        return false;
    }

    private void a() {
        try {
            this.f20672e = new JSONObject(c("com.tencent.open.config.json"));
        } catch (JSONException unused) {
            this.f20672e = new JSONObject();
        }
    }

    private void a(String str, String str2) throws IOException {
        try {
            if (this.f20671d != null) {
                str = str + StrPool.DOT + this.f20671d;
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.f20670c.openFileOutput(str, 0), Charset.forName("UTF-8"));
            outputStreamWriter.write(str2);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void c() {
        int iOptInt = this.f20672e.optInt("Common_frequency");
        if (iOptInt == 0) {
            iOptInt = 1;
        }
        if (SystemClock.elapsedRealtime() - this.f20673f >= iOptInt * 3600000) {
            b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(JSONObject jSONObject) throws IOException {
        d("cgi back, do update");
        this.f20672e = jSONObject;
        a("com.tencent.open.config.json", jSONObject.toString());
        this.f20673f = SystemClock.elapsedRealtime();
    }

    public int a(String str) {
        d("get " + str);
        c();
        return this.f20672e.optInt(str);
    }
}
