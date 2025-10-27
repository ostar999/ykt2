package com.alibaba.sdk.android.httpdns.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class c implements com.alibaba.sdk.android.httpdns.net.a {

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<b> f2820b;
    private Context context;

    /* renamed from: k, reason: collision with root package name */
    private String f2821k;

    /* renamed from: l, reason: collision with root package name */
    private String f2822l;

    /* renamed from: m, reason: collision with root package name */
    private String f2823m;

    public static class a {

        /* renamed from: b, reason: collision with root package name */
        private static final c f2825b = new c();
    }

    public interface b {
        void f(String str);
    }

    private c() {
        this.f2823m = "None_Network";
        this.f2820b = new ArrayList<>();
    }

    private static int a(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid());
    }

    public static c a() {
        return a.f2825b;
    }

    private static String a(Context context) {
        WifiInfo connectionInfo;
        try {
            if (a(context, Permission.ACCESS_FINE_LOCATION) != 0 || (connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo()) == null) {
                return null;
            }
            return connectionInfo.getSSID();
        } catch (Throwable th) {
            HttpDnsLog.w("get ssid fail", th);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a, reason: collision with other method in class */
    public static boolean m59a(Context context) {
        try {
            return a(context, "android.permission.ACCESS_NETWORK_STATE") == 0;
        } catch (Throwable th) {
            HttpDnsLog.w("check network info permission fail", th);
            return false;
        }
    }

    private static String b(Context context) {
        try {
            if (a(context, Permission.READ_PHONE_STATE) != 0) {
                return "UNKNOW";
            }
            String simOperator = ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimOperator();
            return !TextUtils.isEmpty(simOperator) ? simOperator : "UNKNOW";
        } catch (Throwable th) {
            HttpDnsLog.w("getCellSP fail", th);
            return "UNKNOW";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b, reason: collision with other method in class */
    public void m60b(Context context) {
        String str;
        this.f2822l = "unknown";
        this.f2821k = "unknown";
        try {
            if (!m59a(context)) {
                if (str == null) {
                    return;
                } else {
                    return;
                }
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                if (this.f2822l == null) {
                    this.f2822l = "unknown";
                }
                if (this.f2821k == null) {
                    this.f2821k = "unknown";
                    return;
                }
                return;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                if (this.f2822l == null) {
                    this.f2822l = "unknown";
                }
                if (this.f2821k == null) {
                    this.f2821k = "unknown";
                    return;
                }
                return;
            }
            if (activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == 1) {
                    String strA = a(context);
                    this.f2822l = strA;
                    this.f2821k = "wifi";
                    if (strA == null) {
                        this.f2822l = "unknown";
                        return;
                    }
                    return;
                }
                if (activeNetworkInfo.getType() != 0) {
                    if (this.f2822l == null) {
                        this.f2822l = "unknown";
                    }
                    if (this.f2821k == null) {
                        this.f2821k = "unknown";
                        return;
                    }
                    return;
                }
                this.f2822l = b(context);
                int subtype = activeNetworkInfo.getSubtype();
                if (subtype == 13) {
                    this.f2821k = "4g";
                    if (this.f2822l == null) {
                        this.f2822l = "unknown";
                        return;
                    }
                    return;
                }
                if (subtype != 15) {
                    if (subtype == 20) {
                        this.f2821k = "5g";
                        if (this.f2822l == null) {
                            this.f2822l = "unknown";
                            return;
                        }
                        return;
                    }
                    switch (subtype) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            this.f2821k = "2g";
                            if (this.f2822l == null) {
                                this.f2822l = "unknown";
                                return;
                            }
                            return;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                            break;
                        default:
                            if (this.f2822l == null) {
                                this.f2822l = "unknown";
                            }
                            if (this.f2821k == null) {
                                this.f2821k = "unknown";
                                return;
                            }
                            return;
                    }
                }
                this.f2821k = "3g";
                if (this.f2822l == null) {
                    this.f2822l = "unknown";
                    return;
                }
                return;
            }
            if (this.f2822l == null) {
                this.f2822l = "unknown";
            }
            if (this.f2821k == null) {
                this.f2821k = "unknown";
            }
        } catch (Throwable th) {
            try {
                HttpDnsLog.w("getNetType fail", th);
                if (this.f2822l == null) {
                    this.f2822l = "unknown";
                }
                if (this.f2821k == null) {
                    this.f2821k = "unknown";
                }
            } finally {
                if (this.f2822l == null) {
                    this.f2822l = "unknown";
                }
                if (this.f2821k == null) {
                    this.f2821k = "unknown";
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String j() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                String typeName = activeNetworkInfo.getTypeName();
                HttpDnsLog.d("[detectCurrentNetwork] - Network name:" + typeName + " subType name: " + activeNetworkInfo.getSubtypeName());
                return typeName == null ? "None_Network" : typeName;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return "None_Network";
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m62a(Context context) {
        if (context == null) {
            throw new IllegalStateException("Context can't be null");
        }
        if (this.context != null) {
            return;
        }
        this.context = context.getApplicationContext();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.alibaba.sdk.android.httpdns.net.c.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                try {
                    if (!isInitialStickyBroadcast() && "android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && c.m59a(context2)) {
                        c.this.m60b(context2);
                        String strJ = c.this.j();
                        Inet64Util.startIpStackDetect();
                        if (!strJ.equals("None_Network") && !strJ.equalsIgnoreCase(c.this.f2823m)) {
                            Iterator it = c.this.f2820b.iterator();
                            while (it.hasNext()) {
                                ((b) it.next()).f(strJ);
                            }
                        }
                        if (strJ.equals("None_Network")) {
                            return;
                        }
                        c.this.f2823m = strJ;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.context.registerReceiver(broadcastReceiver, intentFilter);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        m60b(this.context);
        HttpDnsLog.i("NetworkStateManager init " + k());
    }

    public void a(b bVar) {
        this.f2820b.add(bVar);
    }

    @Override // com.alibaba.sdk.android.httpdns.net.a
    public boolean c() {
        return this.f2821k == "wifi";
    }

    @Override // com.alibaba.sdk.android.httpdns.net.a
    public boolean d() {
        String str = this.f2821k;
        return (str == "unknown" || str == "wifi") ? false : true;
    }

    public String g() {
        return this.f2821k;
    }

    @Override // com.alibaba.sdk.android.httpdns.net.a
    public String i() {
        return this.f2821k + "$" + this.f2822l;
    }

    public String k() {
        return this.f2822l;
    }
}
