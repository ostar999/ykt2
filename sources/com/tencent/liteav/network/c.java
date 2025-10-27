package com.tencent.liteav.network;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class c {

    /* renamed from: b, reason: collision with root package name */
    private final String f19522b = "https://tcdns.myqcloud.com/queryip";

    /* renamed from: c, reason: collision with root package name */
    private final String f19523c = "https://tcdnsipv6.myqcloud.com/queryip";

    /* renamed from: d, reason: collision with root package name */
    private final String f19524d = "forward_stream";

    /* renamed from: e, reason: collision with root package name */
    private final String f19525e = "forward_num";

    /* renamed from: f, reason: collision with root package name */
    private final String f19526f = "request_type";

    /* renamed from: g, reason: collision with root package name */
    private final String f19527g = "sdk_version";

    /* renamed from: h, reason: collision with root package name */
    private final String f19528h = "sdk_type";

    /* renamed from: i, reason: collision with root package name */
    private final String f19529i = "use";

    /* renamed from: j, reason: collision with root package name */
    private final String f19530j = "51451748-d8f2-4629-9071-db2983aa7251";

    /* renamed from: k, reason: collision with root package name */
    private final int f19531k = 5;

    /* renamed from: l, reason: collision with root package name */
    private final int f19532l = 2;

    /* renamed from: a, reason: collision with root package name */
    public b f19521a = null;

    /* renamed from: m, reason: collision with root package name */
    private Thread f19533m = null;

    /* renamed from: n, reason: collision with root package name */
    private int f19534n = 5;

    private String b(String str, int i2, String str2) throws IOException {
        InputStream inputStreamC;
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            inputStreamC = c(str, i2, str2);
        } catch (IOException e2) {
            TXCLog.e("TXCIntelligentRoute", "get json string from url failed.", e2);
        }
        if (inputStreamC == null) {
            return "";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamC));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }

    private InputStream c(String str, int i2, String str2) throws IOException {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(str2).openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("forward_stream", str);
            httpsURLConnection.setRequestProperty("forward_num", "2");
            httpsURLConnection.setRequestProperty("sdk_version", TXCCommonUtil.getSDKVersionStr());
            if (i2 == 1) {
                httpsURLConnection.setRequestProperty("request_type", "1");
            } else if (i2 == 2) {
                httpsURLConnection.setRequestProperty("request_type", "2");
            } else {
                httpsURLConnection.setRequestProperty("request_type", "3");
            }
            int i3 = this.f19534n;
            if (i3 > 0) {
                httpsURLConnection.setConnectTimeout(i3 * 1000);
                httpsURLConnection.setReadTimeout(this.f19534n * 1000);
            }
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == 200) {
                return httpsURLConnection.getInputStream();
            }
            return null;
        } catch (Exception e2) {
            TXCLog.e("TXCIntelligentRoute", "https failed.", e2);
            return null;
        }
    }

    private boolean d(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public void a(final String str, final int i2) {
        Thread thread = new Thread("TXCPushRoute") { // from class: com.tencent.liteav.network.c.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws InterruptedException {
                if (c.this.f19521a == null) {
                    return;
                }
                int i3 = 7;
                while (i3 > 0) {
                    if (c.this.a(str, i2, i3 > 2 ? "https://tcdns.myqcloud.com/queryip" : "https://tcdnsipv6.myqcloud.com/queryip")) {
                        return;
                    }
                    if (i3 == 1) {
                        TXCLog.w("TXCIntelligentRoute", "fetchByUrl failed, bad response, no retryCount left, push directly to domain name");
                        c.this.f19521a.onFetchDone(-1, null);
                        return;
                    }
                    try {
                        Thread.sleep(1000L, 0);
                        StringBuilder sb = new StringBuilder();
                        sb.append("fetchByUrl failed, bad response, bad response, retryCount left:");
                        sb.append(i3 - 1);
                        TXCLog.w("TXCIntelligentRoute", sb.toString());
                    } catch (InterruptedException unused) {
                    }
                    i3--;
                }
            }
        };
        this.f19533m = thread;
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str, int i2, String str2) throws JSONException {
        ArrayList<a> arrayListA;
        try {
            String strB = b(str, i2, str2);
            JSONObject jSONObject = new JSONObject(strB);
            boolean z2 = !jSONObject.has("use");
            if (z2) {
                arrayListA = a(strB);
            } else {
                int i3 = jSONObject.getInt("use");
                if (i3 == -1) {
                    TXCLog.w("TXCIntelligentRoute", "server error, use = -1, but current SDK isn't international, push directly to domain name");
                } else if (i3 != 0) {
                    TXCLog.w("TXCIntelligentRoute", "unknown value for key:'use', push directly to domain name. use = " + i3);
                }
                arrayListA = null;
            }
            if (z2) {
                if (arrayListA == null || arrayListA.size() <= 0) {
                    return false;
                }
                this.f19521a.onFetchDone(0, arrayListA);
            } else {
                this.f19521a.onFetchDone(0, null);
            }
            return true;
        } catch (Exception e2) {
            TXCLog.e("TXCIntelligentRoute", "get value from json failed.", e2);
            return false;
        }
    }

    private boolean b(String str) {
        return str.split(":").length > 1;
    }

    private ArrayList<a> a(String str) {
        JSONArray jSONArray;
        ArrayList<a> arrayList = new ArrayList<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.getInt("state") != 0 || (jSONArray = jSONObject.getJSONObject("content").getJSONArray("list")) == null) {
                return null;
            }
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                a aVarA = a((JSONObject) jSONArray.opt(i2));
                if (aVarA != null && aVarA.f19498c) {
                    arrayList.add(aVarA);
                }
            }
            for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                a aVarA2 = a((JSONObject) jSONArray.opt(i3));
                if (aVarA2 != null && !aVarA2.f19498c) {
                    arrayList.add(aVarA2);
                }
            }
            if (com.tencent.liteav.basic.c.c.a().a("Network", "EnableRouteOptimize") == 1 && j.a().c()) {
                ArrayList<a> arrayListA = a(arrayList, true);
                a(arrayListA);
                return arrayListA;
            }
            long jA = com.tencent.liteav.basic.c.c.a().a("Network", "RouteSamplingMaxCount");
            if (jA >= 1) {
                long jA2 = j.a().a("51451748-d8f2-4629-9071-db2983aa7251");
                if (jA2 <= jA) {
                    arrayList = a(arrayList, false);
                    j.a().a("51451748-d8f2-4629-9071-db2983aa7251", jA2 + 1);
                }
            }
            a(arrayList);
            return arrayList;
        } catch (JSONException e2) {
            TXCLog.e("TXCIntelligentRoute", "get records from json string failed.", e2);
            return arrayList;
        }
    }

    private boolean c(String str) {
        if (!b(str) && str != null) {
            for (String str2 : str.split("[.]")) {
                if (!d(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private a a(JSONObject jSONObject) {
        a aVar = new a();
        try {
            aVar.f19496a = jSONObject.getString("ip");
            aVar.f19497b = jSONObject.getString("port");
            aVar.f19500e = 0;
            aVar.f19498c = false;
            aVar.f19499d = c(aVar.f19496a);
            if (jSONObject.has("type") && jSONObject.getInt("type") == 2) {
                aVar.f19498c = true;
            }
            return aVar;
        } catch (JSONException e2) {
            TXCLog.e("TXCIntelligentRoute", "get ip record from json object failed.", e2);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ArrayList<a> a(ArrayList<a> arrayList, boolean z2) {
        a aVar;
        a aVar2;
        a next = null;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator<a> it = arrayList.iterator();
        loop0: while (true) {
            aVar = next;
            while (it.hasNext()) {
                next = it.next();
                if (next.f19498c) {
                    arrayList2.add(next);
                } else {
                    if (next.f19499d) {
                        break;
                    }
                    arrayList3.add(next);
                }
            }
        }
        ArrayList<a> arrayList4 = new ArrayList<>();
        while (true) {
            if (arrayList2.size() <= 0 && arrayList3.size() <= 0) {
                break;
            }
            if (z2) {
                if (aVar != null) {
                    arrayList4.add(aVar);
                }
                if (arrayList2.size() > 0) {
                    arrayList4.add(arrayList2.get(0));
                    arrayList2.remove(0);
                }
            } else {
                if (arrayList2.size() > 0) {
                    arrayList4.add(arrayList2.get(0));
                    arrayList2.remove(0);
                }
                if (aVar != null) {
                    arrayList4.add(aVar);
                }
            }
            if (arrayList3.size() > 0) {
                arrayList4.add(arrayList3.get(0));
                arrayList3.remove(0);
            }
        }
        int size = arrayList4.size();
        if (size > 0 && (aVar2 = (a) arrayList4.get(size - 1)) != null && !c(aVar2.f19496a) && aVar != null) {
            arrayList4.add(aVar);
        }
        return arrayList4;
    }

    private void a(ArrayList<a> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator<a> it = arrayList.iterator();
        String str = "";
        while (it.hasNext()) {
            a next = it.next();
            str = str + " \n Nearest IP: " + next.f19496a + " Port: " + next.f19497b + " Q Channel: " + next.f19498c;
        }
        TXCLog.e("TXCIntelligentRoute", str);
    }
}
