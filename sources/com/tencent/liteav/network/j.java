package com.tencent.liteav.network;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.liteav.basic.log.TXCLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    protected static j f19602a = new j();

    /* renamed from: b, reason: collision with root package name */
    private Context f19603b = null;

    /* renamed from: c, reason: collision with root package name */
    private long f19604c = 3;

    public class a {

        /* renamed from: a, reason: collision with root package name */
        public float f19605a = 0.0f;

        /* renamed from: b, reason: collision with root package name */
        public float f19606b = 0.0f;

        /* renamed from: c, reason: collision with root package name */
        public float f19607c = 0.0f;

        /* renamed from: d, reason: collision with root package name */
        public float f19608d = 0.0f;

        /* renamed from: e, reason: collision with root package name */
        public long f19609e = 0;

        public a() {
        }
    }

    public static j a() {
        return f19602a;
    }

    private void d() {
        long jA = com.tencent.liteav.basic.c.c.a().a("Network", "QualityDataCacheCount");
        this.f19604c = jA;
        if (jA == -1 || jA < 3) {
            this.f19604c = 3L;
        }
    }

    public String b() {
        try {
            Context context = this.f19603b;
            if (context != null) {
                int iE = com.tencent.liteav.basic.util.h.e(context);
                return iE == 0 ? "" : iE == 1 ? "wifi:" : iE == 2 ? "4g:" : iE == 3 ? "3g:" : iE == 4 ? "2g:" : iE == 5 ? "ethernet:" : iE == 6 ? "5g:" : "xg:";
            }
        } catch (Exception e2) {
            TXCLog.e("UploadQualityData", "get network type failed." + e2.getMessage());
        }
        return "";
    }

    public boolean c() throws JSONException {
        d();
        String strB = b();
        String str = "isDomainAddressBetter: accessID = " + strB + " minQualityDataCount = " + this.f19604c;
        a aVarA = a(strB, true);
        a aVarA2 = a(strB, false);
        if (aVarA != null) {
            str = String.format("%s \n isDomainAddressBetter：domainQualityData count = %d avgNetworkRTT = %f avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", str, Long.valueOf(aVarA.f19609e), Float.valueOf(aVarA.f19605a), Float.valueOf(aVarA.f19606b), Float.valueOf(aVarA.f19607c), Float.valueOf(aVarA.f19608d));
        }
        if (aVarA2 != null) {
            str = String.format("%s \n isDomainAddressBetter：originQualityData count = %d avgNetworkRTT = %f avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", str, Long.valueOf(aVarA2.f19609e), Float.valueOf(aVarA2.f19605a), Float.valueOf(aVarA2.f19606b), Float.valueOf(aVarA2.f19607c), Float.valueOf(aVarA2.f19608d));
        }
        TXCLog.e("UploadQualityData", str);
        if (aVarA != null) {
            long j2 = aVarA.f19609e;
            long j3 = this.f19604c;
            if (j2 >= j3 && aVarA2 != null && aVarA2.f19609e >= j3 && aVarA.f19606b < aVarA2.f19606b && aVarA.f19607c < aVarA2.f19607c && aVarA.f19608d < aVarA2.f19608d) {
                return true;
            }
        }
        return false;
    }

    public void a(Context context) {
        if (this.f19603b == null) {
            this.f19603b = context.getApplicationContext();
        }
    }

    private boolean b(String str) {
        return str == null || str.length() == 0;
    }

    public void a(String str, long j2, long j3, long j4, float f2, float f3, float f4) throws JSONException {
        if (com.tencent.liteav.basic.c.c.a().a("Network", "QualityDataCacheCount") > 0) {
            TXCLog.e("UploadQualityData", String.format("updateQualityData: accessID = %s serverType = %d totalTime = %d networkRTT = %d avgBlockCnt = %f avgVideoQue = %f avgAudioQue = %f", str, Long.valueOf(j2), Long.valueOf(j3), Long.valueOf(j4), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4)));
            if (b(str)) {
                return;
            }
            try {
                SharedPreferences sharedPreferences = this.f19603b.getSharedPreferences("com.tencent.liteav.network", 0);
                JSONObject jSONObjectC = c(sharedPreferences.getString("34238512-C08C-4931-A000-40E1D8B5BA5B", ""));
                JSONObject jSONObjectOptJSONObject = jSONObjectC.optJSONObject(str);
                if (jSONObjectOptJSONObject == null) {
                    jSONObjectOptJSONObject = new JSONObject();
                }
                String str2 = j2 == 3 ? "DomainArrayData" : "OriginArrayData";
                JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray(str2);
                if (jSONArrayOptJSONArray == null) {
                    jSONArrayOptJSONArray = new JSONArray();
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("totalTime", j3);
                jSONObject.put("networkRTT", j4);
                jSONObject.put("avgBlockCnt", f2);
                jSONObject.put("avgVideoQue", f3);
                jSONObject.put("avgAudioQue", f4);
                jSONArrayOptJSONArray.put(jSONObject);
                int length = jSONArrayOptJSONArray.length();
                long j5 = length;
                if (j5 > this.f19604c) {
                    JSONArray jSONArray = new JSONArray();
                    for (int i2 = (int) (j5 - this.f19604c); i2 < length; i2++) {
                        jSONArray.put(jSONArrayOptJSONArray.get(i2));
                    }
                    jSONArrayOptJSONArray = jSONArray;
                }
                jSONObjectOptJSONObject.put(str2, jSONArrayOptJSONArray);
                jSONObjectC.put(str, jSONObjectOptJSONObject);
                sharedPreferences.edit().putString("34238512-C08C-4931-A000-40E1D8B5BA5B", jSONObjectC.toString()).commit();
            } catch (Exception e2) {
                TXCLog.e("UploadQualityData", "build json object failed.", e2);
            }
        }
    }

    private JSONObject c(String str) {
        if (!b(str)) {
            try {
                return new JSONObject(str);
            } catch (Exception e2) {
                TXCLog.e("UploadQualityData", "failed to parse json string", e2);
            }
        }
        return new JSONObject();
    }

    private a a(String str, boolean z2) throws JSONException {
        JSONObject jSONObjectOptJSONObject;
        String str2 = "";
        if (b(str)) {
            return null;
        }
        try {
            String string = this.f19603b.getSharedPreferences("com.tencent.liteav.network", 0).getString("34238512-C08C-4931-A000-40E1D8B5BA5B", "");
            if (b(string) || (jSONObjectOptJSONObject = new JSONObject(string).optJSONObject(str)) == null) {
                return null;
            }
            JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray(z2 ? "DomainArrayData" : "OriginArrayData");
            if (jSONArrayOptJSONArray == null) {
                return null;
            }
            long length = jSONArrayOptJSONArray.length();
            if (length == 0) {
                return null;
            }
            float fOptLong = 0.0f;
            float fOptDouble = 0.0f;
            float fOptDouble2 = 0.0f;
            float fOptDouble3 = 0.0f;
            int i2 = 0;
            while (i2 < length) {
                JSONObject jSONObject = jSONArrayOptJSONArray.getJSONObject(i2);
                fOptDouble = (float) (fOptDouble + jSONObject.optDouble("avgBlockCnt"));
                fOptDouble2 = (float) (fOptDouble2 + jSONObject.optDouble("avgVideoQue"));
                fOptDouble3 = (float) (fOptDouble3 + jSONObject.optDouble("avgAudioQue"));
                str2 = String.format("%s \n isDomainAddressBetter：itemData domain = %b NetworkRTT = %d avgBlockCount = %f avgVideoQueue = %f avgAudioQueue = %f", str2, Boolean.valueOf(z2), Long.valueOf(jSONObject.optLong("networkRTT")), Double.valueOf(jSONObject.optDouble("avgBlockCnt")), Double.valueOf(jSONObject.optDouble("avgVideoQue")), Double.valueOf(jSONObject.optDouble("avgAudioQue")));
                i2++;
                fOptLong += jSONObject.optLong("networkRTT");
                length = length;
            }
            long j2 = length;
            float f2 = j2;
            float f3 = fOptLong / f2;
            float f4 = fOptDouble / f2;
            float f5 = fOptDouble2 / f2;
            float f6 = fOptDouble3 / f2;
            a aVar = new a();
            aVar.f19605a = f3;
            aVar.f19606b = f4;
            aVar.f19607c = f5;
            aVar.f19608d = f6;
            aVar.f19609e = j2;
            return aVar;
        } catch (Exception e2) {
            TXCLog.e("UploadQualityData", "get quality data failed.", e2);
            return null;
        }
    }

    public long a(String str) {
        Context context = this.f19603b;
        if (context != null) {
            return context.getSharedPreferences("com.tencent.liteav.network", 0).getLong(str, 0L);
        }
        return 0L;
    }

    public void a(String str, long j2) {
        Context context = this.f19603b;
        if (context != null) {
            context.getSharedPreferences("com.tencent.liteav.network", 0).edit().putLong(str, j2).commit();
        }
    }
}
