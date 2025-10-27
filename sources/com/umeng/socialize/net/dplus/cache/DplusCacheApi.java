package com.umeng.socialize.net.dplus.cache;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.dplus.db.DBConfig;
import com.umeng.socialize.net.dplus.db.DBManager;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class DplusCacheApi {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23773a = "DplusCacheApi";

    /* renamed from: b, reason: collision with root package name */
    private HandlerThread f23774b;

    /* renamed from: c, reason: collision with root package name */
    private Handler f23775c;

    /* renamed from: d, reason: collision with root package name */
    private final int f23776d;

    /* renamed from: e, reason: collision with root package name */
    private ArrayList<Integer> f23777e;

    /* renamed from: f, reason: collision with root package name */
    private ArrayList<Integer> f23778f;

    /* renamed from: g, reason: collision with root package name */
    private ArrayList<Integer> f23779g;

    /* renamed from: h, reason: collision with root package name */
    private ArrayList<Integer> f23780h;

    /* renamed from: i, reason: collision with root package name */
    private ArrayList<Integer> f23781i;

    public static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        private static final DplusCacheApi f23796a = new DplusCacheApi();

        private SingletonHolder() {
        }
    }

    public static double checkFile() {
        File dataFile = ContextUtil.getDataFile(DBConfig.DB_NAME);
        if (dataFile == null || !dataFile.exists()) {
            return 0.0d;
        }
        return dataFile.length();
    }

    public static final DplusCacheApi getInstance() {
        return SingletonHolder.f23796a;
    }

    public void closeDBConnection(final Context context) {
        this.f23775c.post(new Runnable() { // from class: com.umeng.socialize.net.dplus.cache.DplusCacheApi.1
            @Override // java.lang.Runnable
            public void run() {
                DBManager.get(context).closeDatabase();
            }
        });
    }

    public void deleteAll(Context context) {
        this.f23775c.post(new Runnable() { // from class: com.umeng.socialize.net.dplus.cache.DplusCacheApi.5
            @Override // java.lang.Runnable
            public void run() {
                DBManager.get(ContextUtil.getContext()).deleteTable("stats");
            }
        });
    }

    public void deleteAllAsnc(Context context) {
        DBManager.get(ContextUtil.getContext()).deleteTable("stats");
    }

    public void deleteFile(final Context context) {
        this.f23775c.post(new Runnable() { // from class: com.umeng.socialize.net.dplus.cache.DplusCacheApi.4
            @Override // java.lang.Runnable
            public void run() {
                if (DplusCacheApi.this.f23777e.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.f23777e, "s_e");
                    DplusCacheApi.this.f23777e.clear();
                }
                if (DplusCacheApi.this.f23778f.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.f23778f, "auth");
                    DplusCacheApi.this.f23778f.clear();
                }
                if (DplusCacheApi.this.f23780h.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.f23780h, "dau");
                    DplusCacheApi.this.f23780h.clear();
                }
                if (DplusCacheApi.this.f23779g.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.f23779g, "userinfo");
                    DplusCacheApi.this.f23779g.clear();
                }
                if (DplusCacheApi.this.f23781i.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.f23781i, "stats");
                    DplusCacheApi.this.f23781i.clear();
                }
            }
        });
    }

    public void deleteFileAsnc(Context context) {
        if (this.f23777e.size() > 0) {
            DBManager.get(context).delete(this.f23777e, "s_e");
            this.f23777e.clear();
        }
        if (this.f23778f.size() > 0) {
            DBManager.get(context).delete(this.f23778f, "auth");
            this.f23778f.clear();
        }
        if (this.f23780h.size() > 0) {
            DBManager.get(context).delete(this.f23780h, "dau");
            this.f23780h.clear();
        }
        if (this.f23779g.size() > 0) {
            DBManager.get(context).delete(this.f23779g, "userinfo");
            this.f23779g.clear();
        }
        if (this.f23781i.size() > 0) {
            DBManager.get(context).delete(this.f23781i, "stats");
            this.f23781i.clear();
        }
    }

    public void readFile(final Context context, final int i2, final DplusCacheListener dplusCacheListener) {
        this.f23775c.post(new Runnable() { // from class: com.umeng.socialize.net.dplus.cache.DplusCacheApi.3
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                JSONArray jSONArray;
                JSONArray jSONArray2;
                JSONArray jSONArray3;
                JSONArray jSONArray4;
                JSONArray jSONArray5;
                double dCheckFile = DplusCacheApi.checkFile();
                if (dCheckFile >= 5242880.0d) {
                    DBManager.get(ContextUtil.getContext()).deleteTable("stats");
                    return;
                }
                boolean z2 = 1048576.0d <= dCheckFile + 24576.0d;
                JSONObject jSONObject = new JSONObject();
                try {
                    new JSONObject();
                    JSONObject jSONObject2 = new JSONObject();
                    JSONArray jSONArraySelect = DBManager.get(context).select("s_e", DplusCacheApi.this.f23777e, 1047552.0d, z2);
                    double length = jSONArraySelect.toString().getBytes().length + 1024.0d;
                    JSONArray jSONArraySelect2 = DBManager.get(context).select("auth", DplusCacheApi.this.f23778f, 1048576.0d - length, z2);
                    double length2 = length + jSONArraySelect2.toString().getBytes().length;
                    JSONArray jSONArraySelect3 = DBManager.get(context).select("userinfo", DplusCacheApi.this.f23779g, 1048576.0d - length2, z2);
                    double length3 = length2 + jSONArraySelect3.toString().getBytes().length;
                    JSONArray jSONArraySelect4 = DBManager.get(context).select("dau", DplusCacheApi.this.f23780h, 1048576.0d - length3, z2);
                    double length4 = length3 + jSONArraySelect4.toString().getBytes().length;
                    double dCheckFile2 = DplusCacheApi.checkFile();
                    if (jSONArraySelect4.length() != 0) {
                        jSONObject2.put("dau", jSONArraySelect4);
                    }
                    if (jSONArraySelect.length() != 0) {
                        jSONObject2.put("s_e", jSONArraySelect);
                    }
                    if (jSONArraySelect2.length() != 0) {
                        jSONArray = jSONArraySelect2;
                        jSONObject2.put("auth", jSONArray);
                    } else {
                        jSONArray = jSONArraySelect2;
                    }
                    if (jSONArraySelect3.length() != 0) {
                        jSONArray2 = jSONArraySelect3;
                        jSONObject2.put("userinfo", jSONArray2);
                    } else {
                        jSONArray2 = jSONArraySelect3;
                    }
                    JSONArray jSONArray6 = new JSONArray();
                    if (dCheckFile2 >= 524288.0d || i2 == 24583) {
                        double d3 = 1048576.0d - length4;
                        jSONArray3 = jSONArray;
                        jSONArray4 = jSONArray2;
                        jSONArray5 = jSONArraySelect4;
                        jSONArray6 = DBManager.get(context).select("stats", DplusCacheApi.this.f23781i, d3, z2);
                        if (jSONArray6.length() != 0) {
                            jSONObject2.put("stats", jSONArray6);
                        }
                    } else {
                        jSONArray3 = jSONArray;
                        jSONArray4 = jSONArray2;
                        jSONArray5 = jSONArraySelect4;
                    }
                    jSONObject.put("share", jSONObject2);
                    if (jSONArraySelect.length() == 0 && jSONArray3.length() == 0 && jSONArray4.length() == 0 && jSONArray5.length() == 0) {
                        if (jSONArray6.length() == 0) {
                            jSONObject = null;
                        }
                    }
                } catch (JSONException e2) {
                    SLog.error(UmengText.CACHE.CACHEFILE, e2);
                }
                if (jSONObject != null && jSONObject.toString().getBytes().length > 1048576.0d) {
                    dplusCacheListener.onResult(null);
                }
                dplusCacheListener.onResult(jSONObject);
            }
        });
    }

    public JSONObject readFileAsnc(Context context, int i2) throws JSONException {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        JSONArray jSONArray3;
        JSONArray jSONArray4;
        JSONArray jSONArray5;
        double dCheckFile = checkFile();
        if (dCheckFile >= 5242880.0d) {
            DBManager.get(ContextUtil.getContext()).deleteTable("stats");
            return null;
        }
        boolean z2 = 1048576.0d <= dCheckFile + 24576.0d;
        JSONObject jSONObject = new JSONObject();
        try {
            new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONArray jSONArraySelect = DBManager.get(context).select("s_e", this.f23777e, 1047552.0d, z2);
            double length = jSONArraySelect.toString().getBytes().length + 1024.0d;
            JSONArray jSONArraySelect2 = DBManager.get(context).select("auth", this.f23778f, 1048576.0d - length, z2);
            double length2 = length + jSONArraySelect2.toString().getBytes().length;
            JSONArray jSONArraySelect3 = DBManager.get(context).select("userinfo", this.f23779g, 1048576.0d - length2, z2);
            double length3 = length2 + jSONArraySelect3.toString().getBytes().length;
            JSONArray jSONArraySelect4 = DBManager.get(context).select("dau", this.f23780h, 1048576.0d - length3, z2);
            double length4 = length3 + jSONArraySelect4.toString().getBytes().length;
            double dCheckFile2 = checkFile();
            if (jSONArraySelect4.length() != 0) {
                jSONObject2.put("dau", jSONArraySelect4);
            }
            if (jSONArraySelect.length() != 0) {
                jSONObject2.put("s_e", jSONArraySelect);
            }
            if (jSONArraySelect2.length() != 0) {
                jSONArray = jSONArraySelect2;
                jSONObject2.put("auth", jSONArray);
            } else {
                jSONArray = jSONArraySelect2;
            }
            if (jSONArraySelect3.length() != 0) {
                jSONArray2 = jSONArraySelect3;
                jSONObject2.put("userinfo", jSONArray2);
            } else {
                jSONArray2 = jSONArraySelect3;
            }
            JSONArray jSONArray6 = new JSONArray();
            if (dCheckFile2 >= 524288.0d || i2 == 24583) {
                double d3 = 1048576.0d - length4;
                jSONArray3 = jSONArray;
                jSONArray4 = jSONArray2;
                jSONArray5 = jSONArraySelect4;
                jSONArray6 = DBManager.get(context).select("stats", this.f23781i, d3, z2);
                if (jSONArray6.length() != 0) {
                    jSONObject2.put("stats", jSONArray6);
                }
            } else {
                jSONArray3 = jSONArray;
                jSONArray4 = jSONArray2;
                jSONArray5 = jSONArraySelect4;
            }
            jSONObject.put("share", jSONObject2);
            if (jSONArraySelect.length() == 0 && jSONArray3.length() == 0 && jSONArray4.length() == 0 && jSONArray5.length() == 0) {
                if (jSONArray6.length() == 0) {
                    jSONObject = null;
                }
            }
        } catch (JSONException e2) {
            SLog.error(UmengText.CACHE.CACHEFILE, e2);
        }
        if (jSONObject == null || jSONObject.toString().getBytes().length <= 1048576.0d) {
            return jSONObject;
        }
        return null;
    }

    public void saveFile(final Context context, final JSONObject jSONObject, final int i2, final DplusCacheListener dplusCacheListener) {
        this.f23775c.post(new Runnable() { // from class: com.umeng.socialize.net.dplus.cache.DplusCacheApi.2
            @Override // java.lang.Runnable
            public void run() {
                switch (i2) {
                    case 24577:
                        DBManager.get(context).insertDau(jSONObject);
                        break;
                    case 24578:
                        DBManager.get(context).insertS_E(jSONObject);
                        break;
                    case 24579:
                        DBManager.get(context).insertAuth(jSONObject);
                        break;
                    case 24580:
                        DBManager.get(context).insertUserInfo(jSONObject);
                        break;
                    case 24581:
                    case 24583:
                        DBManager.get(context).insertStats(jSONObject);
                        break;
                    case 24582:
                    default:
                        DBManager.get(context).insertStats(jSONObject);
                        break;
                }
                dplusCacheListener.onResult(null);
            }
        });
    }

    private DplusCacheApi() {
        this.f23776d = 1048576;
        this.f23777e = new ArrayList<>();
        this.f23778f = new ArrayList<>();
        this.f23779g = new ArrayList<>();
        this.f23780h = new ArrayList<>();
        this.f23781i = new ArrayList<>();
        HandlerThread handlerThread = new HandlerThread(f23773a, 10);
        this.f23774b = handlerThread;
        handlerThread.start();
        this.f23775c = new Handler(this.f23774b.getLooper());
    }

    private static JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("s_sdk_v", "7.1.7");
        jSONObject.put(CommonNetImpl.PCV, SocializeConstants.PROTOCOL_VERSON);
        return jSONObject;
    }
}
