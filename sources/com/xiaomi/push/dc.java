package com.xiaomi.push;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Process;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.foundationsdk.web.PLVWebview;
import com.xiaomi.push.service.module.PushChannelRegion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class dc {

    /* renamed from: a, reason: collision with root package name */
    protected static Context f24717a;

    /* renamed from: a, reason: collision with other field name */
    private static a f295a;

    /* renamed from: a, reason: collision with other field name */
    private static dc f296a;

    /* renamed from: c, reason: collision with root package name */
    private static String f24719c;

    /* renamed from: d, reason: collision with root package name */
    private static String f24720d;

    /* renamed from: a, reason: collision with other field name */
    private long f298a;

    /* renamed from: a, reason: collision with other field name */
    private db f299a;

    /* renamed from: a, reason: collision with other field name */
    protected b f300a;

    /* renamed from: a, reason: collision with other field name */
    private String f301a;

    /* renamed from: a, reason: collision with other field name */
    protected Map<String, cz> f302a;

    /* renamed from: b, reason: collision with other field name */
    private final long f303b;

    /* renamed from: b, reason: collision with other field name */
    private String f304b;

    /* renamed from: c, reason: collision with other field name */
    private long f305c;

    /* renamed from: b, reason: collision with root package name */
    protected static Map<String, cy> f24718b = new HashMap();

    /* renamed from: a, reason: collision with other field name */
    protected static boolean f297a = false;

    public interface a {
        dc a(Context context, db dbVar, b bVar, String str);
    }

    public interface b {
        String a(String str);
    }

    public dc(Context context, db dbVar, b bVar, String str) {
        this(context, dbVar, bVar, str, null, null);
    }

    public dc(Context context, db dbVar, b bVar, String str, String str2, String str3) {
        this.f302a = new HashMap();
        this.f301a = "0";
        this.f298a = 0L;
        this.f303b = 15L;
        this.f305c = 0L;
        this.f304b = "isp_prov_city_country_ip";
        this.f300a = bVar;
        this.f299a = dbVar == null ? new dd(this) : dbVar;
        this.f301a = str;
        f24719c = str2 == null ? context.getPackageName() : str2;
        f24720d = str3 == null ? f() : str3;
    }

    public static synchronized dc a() {
        dc dcVar;
        dcVar = f296a;
        if (dcVar == null) {
            throw new IllegalStateException("the host manager is not initialized yet.");
        }
        return dcVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m313a() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        Context context = f24717a;
        if (context == null) {
            return "unknown";
        }
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Throwable unused) {
        }
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return "unknown";
        }
        if (activeNetworkInfo.getType() != 1) {
            return activeNetworkInfo.getTypeName() + "-" + activeNetworkInfo.getSubtypeName();
        }
        WifiManager wifiManager = (WifiManager) f24717a.getSystemService("wifi");
        if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
            return "WIFI-" + wifiManager.getConnectionInfo().getSSID();
        }
        return "unknown";
    }

    public static String a(String str) throws UnsupportedEncodingException {
        try {
            int length = str.length();
            byte[] bytes = str.getBytes("UTF-8");
            for (int i2 = 0; i2 < bytes.length; i2++) {
                byte b3 = bytes[i2];
                int i3 = b3 & 240;
                if (i3 != 240) {
                    bytes[i2] = (byte) (((b3 & 15) ^ ((byte) (((b3 >> 4) + length) & 15))) | i3);
                }
            }
            return new String(bytes);
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    private ArrayList<cy> a(ArrayList<String> arrayList) throws JSONException {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        m322d();
        synchronized (this.f302a) {
            m318a();
            for (String str : this.f302a.keySet()) {
                if (!arrayList.contains(str)) {
                    arrayList.add(str);
                }
            }
        }
        boolean zIsEmpty = f24718b.isEmpty();
        synchronized (f24718b) {
            for (Object obj : f24718b.values().toArray()) {
                cy cyVar = (cy) obj;
                if (!cyVar.b()) {
                    f24718b.remove(cyVar.f292b);
                    zIsEmpty = true;
                }
            }
        }
        if (!arrayList.contains(b())) {
            arrayList.add(b());
        }
        ArrayList<cy> arrayList2 = new ArrayList<>(arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(null);
        }
        try {
            String str2 = as.d(f24717a) ? "wifi" : "wap";
            String strA = a(arrayList, str2, this.f301a, zIsEmpty);
            if (!TextUtils.isEmpty(strA)) {
                JSONObject jSONObject3 = new JSONObject(strA);
                com.xiaomi.channel.commonutils.logger.b.b(strA);
                if (PLVWebview.MESSAGE_OK.equalsIgnoreCase(jSONObject3.getString(ExifInterface.LATITUDE_SOUTH))) {
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("R");
                    String string = jSONObject4.getString("province");
                    String string2 = jSONObject4.getString("city");
                    String string3 = jSONObject4.getString("isp");
                    String string4 = jSONObject4.getString("ip");
                    String string5 = jSONObject4.getString(com.umeng.analytics.pro.am.O);
                    JSONObject jSONObject5 = jSONObject4.getJSONObject(str2);
                    com.xiaomi.channel.commonutils.logger.b.c("get bucket: ip = " + string4 + " net = " + string3 + " hosts = " + jSONObject5.toString());
                    int i3 = 0;
                    while (i3 < arrayList.size()) {
                        String str3 = arrayList.get(i3);
                        JSONArray jSONArrayOptJSONArray = jSONObject5.optJSONArray(str3);
                        if (jSONArrayOptJSONArray == null) {
                            com.xiaomi.channel.commonutils.logger.b.m117a("no bucket found for " + str3);
                            jSONObject = jSONObject5;
                        } else {
                            cy cyVar2 = new cy(str3);
                            int i4 = 0;
                            while (i4 < jSONArrayOptJSONArray.length()) {
                                String string6 = jSONArrayOptJSONArray.getString(i4);
                                if (TextUtils.isEmpty(string6)) {
                                    jSONObject2 = jSONObject5;
                                } else {
                                    jSONObject2 = jSONObject5;
                                    cyVar2.a(new dh(string6, jSONArrayOptJSONArray.length() - i4));
                                }
                                i4++;
                                jSONObject5 = jSONObject2;
                            }
                            jSONObject = jSONObject5;
                            arrayList2.set(i3, cyVar2);
                            cyVar2.f24711g = string5;
                            cyVar2.f24707c = string;
                            cyVar2.f24709e = string3;
                            cyVar2.f24710f = string4;
                            cyVar2.f24708d = string2;
                            if (jSONObject4.has("stat-percent")) {
                                cyVar2.a(jSONObject4.getDouble("stat-percent"));
                            }
                            if (jSONObject4.has("stat-domain")) {
                                cyVar2.b(jSONObject4.getString("stat-domain"));
                            }
                            if (jSONObject4.has(RemoteMessageConst.TTL)) {
                                cyVar2.a(jSONObject4.getInt(RemoteMessageConst.TTL) * 1000);
                            }
                            m317a(cyVar2.a());
                        }
                        i3++;
                        jSONObject5 = jSONObject;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject4.optJSONObject("reserved");
                    if (jSONObjectOptJSONObject != null) {
                        long j2 = jSONObject4.has("reserved-ttl") ? jSONObject4.getInt("reserved-ttl") * 1000 : 604800000L;
                        Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray(next);
                            if (jSONArrayOptJSONArray2 == null) {
                                com.xiaomi.channel.commonutils.logger.b.m117a("no bucket found for " + next);
                            } else {
                                cy cyVar3 = new cy(next);
                                cyVar3.a(j2);
                                for (int i5 = 0; i5 < jSONArrayOptJSONArray2.length(); i5++) {
                                    String string7 = jSONArrayOptJSONArray2.getString(i5);
                                    if (!TextUtils.isEmpty(string7)) {
                                        cyVar3.a(new dh(string7, jSONArrayOptJSONArray2.length() - i5));
                                    }
                                }
                                synchronized (f24718b) {
                                    if (this.f299a.a(next)) {
                                        f24718b.put(next, cyVar3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("failed to get bucket " + e2.getMessage());
        }
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            cy cyVar4 = arrayList2.get(i6);
            if (cyVar4 != null) {
                a(arrayList.get(i6), cyVar4);
            }
        }
        m321c();
        return arrayList2;
    }

    public static synchronized void a(Context context, db dbVar, b bVar, String str, String str2, String str3) {
        Context applicationContext = context.getApplicationContext();
        f24717a = applicationContext;
        if (applicationContext == null) {
            f24717a = context;
        }
        if (f296a == null) {
            a aVar = f295a;
            if (aVar == null) {
                f296a = new dc(context, dbVar, bVar, str, str2, str3);
            } else {
                f296a = aVar.a(context, dbVar, bVar, str);
            }
        }
    }

    public static synchronized void a(a aVar) {
        f295a = aVar;
        f296a = null;
    }

    public static void a(String str, String str2) {
        cy cyVar = f24718b.get(str);
        synchronized (f24718b) {
            if (cyVar == null) {
                cy cyVar2 = new cy(str);
                cyVar2.a(604800000L);
                cyVar2.m306a(str2);
                f24718b.put(str, cyVar2);
            } else {
                cyVar.m306a(str2);
            }
        }
    }

    private String f() throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = f24717a.getPackageManager().getPackageInfo(f24717a.getPackageName(), 16384);
            return packageInfo != null ? packageInfo.versionName : "0";
        } catch (Exception unused) {
            return "0";
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public cy m314a(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the url is empty");
        }
        return a(new URL(str).getHost(), true);
    }

    public cy a(String str, boolean z2) {
        cy cyVarD;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        }
        if (!this.f299a.a(str)) {
            return null;
        }
        cy cyVarC = c(str);
        return (cyVarC == null || !cyVarC.b()) ? (z2 && as.b(f24717a) && (cyVarD = d(str)) != null) ? cyVarD : new de(this, str, cyVarC) : cyVarC;
    }

    public String a(ArrayList<String> arrayList, String str, String str2, boolean z2) throws IOException {
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<ar> arrayList3 = new ArrayList();
        arrayList3.add(new ap("type", str));
        if (str.equals("wap")) {
            arrayList3.add(new ap("conpt", a(as.m201a(f24717a))));
        }
        if (z2) {
            arrayList3.add(new ap("reserved", "1"));
        }
        arrayList3.add(new ap(AliyunLogKey.KEY_UUID, str2));
        arrayList3.add(new ap("list", ay.a(arrayList, ",")));
        cy cyVarC = c(b());
        String str3 = String.format(Locale.US, "http://%1$s/gslb/?ver=4.0", b());
        if (cyVarC == null) {
            arrayList2.add(str3);
            synchronized (f24718b) {
                cy cyVar = f24718b.get("resolver.msg.xiaomi.net");
                if (cyVar != null) {
                    Iterator<String> it = cyVar.a(true).iterator();
                    while (it.hasNext()) {
                        arrayList2.add(String.format(Locale.US, "http://%1$s/gslb/?ver=4.0", it.next()));
                    }
                }
            }
        } else {
            arrayList2 = cyVarC.a(str3);
        }
        Iterator<String> it2 = arrayList2.iterator();
        IOException e2 = null;
        while (it2.hasNext()) {
            Uri.Builder builderBuildUpon = Uri.parse(it2.next()).buildUpon();
            for (ar arVar : arrayList3) {
                builderBuildUpon.appendQueryParameter(arVar.a(), arVar.b());
            }
            try {
                b bVar = this.f300a;
                return bVar == null ? as.a(f24717a, new URL(builderBuildUpon.toString())) : bVar.a(builderBuildUpon.toString());
            } catch (IOException e3) {
                e2 = e3;
            }
        }
        if (e2 == null) {
            return null;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("network exception: " + e2.getMessage());
        throw e2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public JSONObject m315a() {
        JSONObject jSONObject;
        synchronized (this.f302a) {
            jSONObject = new JSONObject();
            jSONObject.put("ver", 2);
            JSONArray jSONArray = new JSONArray();
            Iterator<cz> it = this.f302a.values().iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().m310a());
            }
            jSONObject.put("data", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            Iterator<cy> it2 = f24718b.values().iterator();
            while (it2.hasNext()) {
                jSONArray2.put(it2.next().m305a());
            }
            jSONObject.put("reserved", jSONArray2);
        }
        return jSONObject;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m316a() {
        synchronized (this.f302a) {
            this.f302a.clear();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m317a(String str) {
        this.f304b = str;
    }

    public void a(String str, cy cyVar) {
        if (TextUtils.isEmpty(str) || cyVar == null) {
            throw new IllegalArgumentException("the argument is invalid " + str + ", " + cyVar);
        }
        if (this.f299a.a(str)) {
            synchronized (this.f302a) {
                m318a();
                if (this.f302a.containsKey(str)) {
                    this.f302a.get(str).a(cyVar);
                } else {
                    cz czVar = new cz(str);
                    czVar.a(cyVar);
                    this.f302a.put(str, czVar);
                }
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m318a() {
        synchronized (this.f302a) {
            if (f297a) {
                return true;
            }
            f297a = true;
            this.f302a.clear();
            try {
                String strD = d();
                if (!TextUtils.isEmpty(strD)) {
                    m320b(strD);
                    com.xiaomi.channel.commonutils.logger.b.b("loading the new hosts succeed");
                    return true;
                }
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.m117a("load bucket failure: " + th.getMessage());
            }
            return false;
        }
    }

    public cy b(String str) {
        return a(str, true);
    }

    public String b() {
        String strA = com.xiaomi.push.service.a.a(f24717a).a();
        return (TextUtils.isEmpty(strA) || PushChannelRegion.China.name().equals(strA)) ? "resolver.msg.xiaomi.net" : "resolver.msg.global.xiaomi.net";
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m319b() throws JSONException {
        ArrayList<String> arrayList;
        synchronized (this.f302a) {
            m318a();
            arrayList = new ArrayList<>(this.f302a.keySet());
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                cz czVar = this.f302a.get(arrayList.get(size));
                if (czVar != null && czVar.a() != null) {
                    arrayList.remove(size);
                }
            }
        }
        ArrayList<cy> arrayListA = a(arrayList);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (arrayListA.get(i2) != null) {
                a(arrayList.get(i2), arrayListA.get(i2));
            }
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m320b(String str) {
        synchronized (this.f302a) {
            this.f302a.clear();
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("ver") != 2) {
                throw new JSONException("Bad version");
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                cz czVarA = new cz().a(jSONArrayOptJSONArray.getJSONObject(i2));
                this.f302a.put(czVarA.m308a(), czVarA);
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("reserved");
            for (int i3 = 0; i3 < jSONArrayOptJSONArray2.length(); i3++) {
                cy cyVarA = new cy("").a(jSONArrayOptJSONArray2.getJSONObject(i3));
                f24718b.put(cyVarA.f292b, cyVarA);
            }
        }
    }

    public cy c(String str) {
        cz czVar;
        cy cyVarA;
        synchronized (this.f302a) {
            m318a();
            czVar = this.f302a.get(str);
        }
        if (czVar == null || (cyVarA = czVar.a()) == null) {
            return null;
        }
        return cyVarA;
    }

    public String c() {
        StringBuilder sb = new StringBuilder();
        synchronized (this.f302a) {
            for (Map.Entry<String, cz> entry : this.f302a.entrySet()) {
                sb.append(entry.getKey());
                sb.append(":\n");
                sb.append(entry.getValue().toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m321c() {
        synchronized (this.f302a) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(f24717a.openFileOutput(e(), 0)));
                String string = m315a().toString();
                if (!TextUtils.isEmpty(string)) {
                    bufferedWriter.write(string);
                }
                bufferedWriter.close();
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.m117a("persist bucket failure: " + e2.getMessage());
            }
        }
    }

    public cy d(String str) {
        if (System.currentTimeMillis() - this.f305c <= this.f298a * 60 * 1000) {
            return null;
        }
        this.f305c = System.currentTimeMillis();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        cy cyVar = a(arrayList).get(0);
        if (cyVar != null) {
            this.f298a = 0L;
            return cyVar;
        }
        long j2 = this.f298a;
        if (j2 >= 15) {
            return null;
        }
        this.f298a = j2 + 1;
        return null;
    }

    public String d() {
        BufferedReader bufferedReader;
        File file;
        try {
            file = new File(f24717a.getFilesDir(), e());
        } catch (Throwable th) {
            th = th;
            bufferedReader = null;
        }
        if (!file.isFile()) {
            y.a((Closeable) null);
            return null;
        }
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        try {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return sb.toString();
                }
                sb.append(line);
            }
        } catch (Throwable th2) {
            th = th2;
            try {
                com.xiaomi.channel.commonutils.logger.b.m117a("load host exception " + th.getMessage());
                return null;
            } finally {
                y.a(bufferedReader);
            }
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    public void m322d() {
        String next;
        synchronized (this.f302a) {
            Iterator<cz> it = this.f302a.values().iterator();
            while (it.hasNext()) {
                it.next().a(true);
            }
            while (true) {
                for (boolean z2 = false; !z2; z2 = true) {
                    Iterator<String> it2 = this.f302a.keySet().iterator();
                    while (it2.hasNext()) {
                        next = it2.next();
                        if (this.f302a.get(next).m309a().isEmpty()) {
                            break;
                        }
                    }
                }
                this.f302a.remove(next);
            }
        }
    }

    public String e() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) f24717a.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return "com.xiaomi";
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == Process.myPid()) {
                return runningAppProcessInfo.processName;
            }
        }
        return "com.xiaomi";
    }
}
