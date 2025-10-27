package com.tencent.tbs.one.impl.e.a;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.TBSOneTiming;
import com.tencent.tbs.one.impl.a.f;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.common.Statistics;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.d.a;
import com.tencent.tbs.one.impl.e.e;
import com.tencent.tbs.one.optional.TBSOneRuntimeExtension;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class b extends com.tencent.tbs.one.impl.a.b<e<d>> implements a.InterfaceC0368a {

    /* renamed from: b, reason: collision with root package name */
    public int f22095b;

    /* renamed from: c, reason: collision with root package name */
    public final Context f22096c;

    /* renamed from: d, reason: collision with root package name */
    public final String f22097d;

    /* renamed from: e, reason: collision with root package name */
    public final String f22098e;

    /* renamed from: f, reason: collision with root package name */
    public final String[] f22099f;

    /* renamed from: g, reason: collision with root package name */
    public final int f22100g;

    /* renamed from: h, reason: collision with root package name */
    public final File f22101h;

    /* renamed from: i, reason: collision with root package name */
    public com.tencent.tbs.one.impl.d.a f22102i;

    /* renamed from: j, reason: collision with root package name */
    public Bundle f22103j;

    public b(Context context, String str, String str2, String[] strArr, int i2, File file, Bundle bundle) {
        this.f22096c = context;
        this.f22097d = str;
        this.f22098e = str2;
        this.f22099f = strArr;
        this.f22100g = i2;
        this.f22101h = file;
        this.f22103j = bundle;
    }

    public static int a(Context context, String str) {
        return context.getSharedPreferences(String.format("com.tencent.tbs.one.%s.prefs", str), 4).getInt("in_use_deps_version", -1);
    }

    private JSONObject c() {
        try {
            return f.a(com.tencent.tbs.sdk.a.f22287a);
        } catch (Throwable th) {
            g.c("[%s] Failed to get component sdk versions", this.f22097d, th);
            return new JSONObject();
        }
    }

    private JSONObject d() throws JSONException {
        File[] fileArrListFiles = com.tencent.tbs.one.impl.common.f.d(com.tencent.tbs.one.impl.common.f.a(this.f22096c.getDir("tbs", 0), this.f22097d)).listFiles();
        JSONObject jSONObject = new JSONObject();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                f.a(jSONObject, file.getName(), Integer.valueOf(com.tencent.tbs.one.impl.e.f.c(file)));
            }
        }
        return jSONObject;
    }

    private JSONArray e() {
        JSONArray jSONArray = new JSONArray();
        Context context = this.f22096c;
        String str = this.f22097d;
        String[] strArr = this.f22099f;
        if (strArr != null) {
            for (String str2 : strArr) {
                if (!str2.equals(context.getPackageName())) {
                    try {
                        int iA = a(context.createPackageContext(str2, 2), str);
                        if (iA != -1) {
                            jSONArray.put(iA);
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
            }
        }
        return jSONArray;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:8|(1:10)|11|50|12|13|(2:44|14)|21|(1:(1:24))(1:25)|26|48|27|(2:32|(4:46|36|37|38))|42|43) */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00d6, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00d7, code lost:
    
        com.tencent.tbs.one.impl.a.g.c("Failed to get application info for %s", r3, r4);
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e4  */
    @Override // com.tencent.tbs.one.impl.a.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a() throws org.json.JSONException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 417
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.e.a.b.a():void");
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a(int i2, String str, Throwable th) {
        com.tencent.tbs.one.impl.e.f.a(this.f22101h, System.currentTimeMillis());
        super.a(i2, str, th);
    }

    @Override // com.tencent.tbs.one.impl.d.a.InterfaceC0368a
    public final void a(int i2, Map<String, List<String>> map, InputStream inputStream) throws Throwable {
        int iShouldInterceptDEPSResponse;
        Context context = this.f22096c;
        final String str = this.f22097d;
        String str2 = this.f22098e;
        final File file = this.f22101h;
        g.a("[%s] Receiving DEPS response: [%d] %s", str, Integer.valueOf(i2), map);
        if (i2 != 200 || inputStream == null) {
            a(210, "Invalid DEPS response stream, url: " + str2 + ", statusCode: " + i2, (Throwable) null);
            return;
        }
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(context, str);
        if (tBSOneRuntimeExtensionA != null && (iShouldInterceptDEPSResponse = tBSOneRuntimeExtensionA.shouldInterceptDEPSResponse(str, null, inputStream, file, new TBSOneCallback<Void>() { // from class: com.tencent.tbs.one.impl.e.a.b.3
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(Void r4) {
                g.a("[%s] Finished intercepting DEPS response stream by runtime extension", str);
                b.this.f22095b = 0;
                try {
                    b.this.a((b) e.a(e.a.EXTENSION, d.a(file)));
                } catch (TBSOneException e2) {
                    b.this.a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                }
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i3, String str3) {
                b bVar = b.this;
                bVar.f22095b = 0;
                bVar.a(i3, str3, (Throwable) null);
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onProgressChanged(int i3, int i4) {
                b.this.a(com.tencent.tbs.one.impl.e.f.a(i4, 50, 100));
            }
        })) != 0) {
            g.a("[%s] Intercepted DEPS response stream by runtime extension", str);
            this.f22095b = iShouldInterceptDEPSResponse;
            return;
        }
        try {
            String strA = com.tencent.tbs.one.impl.a.d.a(inputStream, "utf-8");
            try {
                JSONObject jSONObject = new JSONObject(strA);
                g.a("AutoDEPSInstallationJob onResponseReceived Receiving DEPS data is " + jSONObject, new Object[0]);
                g.a("[%s] Receiving DEPS data %s", str, jSONObject);
                int iOptInt = jSONObject.optInt(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_CODE, -1);
                if (iOptInt != 0) {
                    a(213, "Failed to request DEPS, url: " + str2 + ", response code: " + iOptInt + ", message: " + jSONObject.optString("MSG"), (Throwable) null);
                    return;
                }
                String strOptString = jSONObject.optString("DEPS");
                try {
                    d dVarA = d.a(strOptString);
                    com.tencent.tbs.one.impl.a.d.a(strOptString, "utf-8", file);
                    Statistics.create(Statistics.EVENT_ACTION, 1001).setDEPSVersion(dVarA.f21989a).report();
                    e eVarA = e.a(e.a.ONLINE, dVarA);
                    eVarA.f22178c = jSONObject;
                    TBSOneTiming.category(this.f22097d).end("depsRequest");
                    a((b) eVarA);
                } catch (TBSOneException e2) {
                    a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                } catch (IOException e3) {
                    a(305, "Failed to save online DEPS to " + file.getAbsolutePath(), e3);
                }
            } catch (JSONException e4) {
                a(212, "Failed to parse DEPS response " + strA + ", url: " + str2, e4);
            }
        } catch (IOException e5) {
            a(211, "Failed to read DEPS response, url:" + str2, e5);
        }
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        TBSOneRuntimeExtension tBSOneRuntimeExtensionA;
        super.b();
        com.tencent.tbs.one.impl.d.a aVar = this.f22102i;
        if (aVar != null) {
            aVar.b();
        }
        if (this.f22095b == 0 || (tBSOneRuntimeExtensionA = com.tencent.tbs.one.impl.common.a.a(this.f22096c, this.f22097d)) == null) {
            return;
        }
        tBSOneRuntimeExtensionA.cancel(this.f22095b);
    }
}
