package com.umeng.commonsdk.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.tencent.connect.common.Constants;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.internal.b;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.c;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.ULog;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
class a implements UMImprintChangeCallback {

    /* renamed from: a, reason: collision with root package name */
    private static HandlerThread f23141a = null;

    /* renamed from: b, reason: collision with root package name */
    private static Handler f23142b = null;

    /* renamed from: c, reason: collision with root package name */
    private static Handler f23143c = null;

    /* renamed from: d, reason: collision with root package name */
    private static final int f23144d = 200;

    /* renamed from: e, reason: collision with root package name */
    private static final int f23145e = 273;

    /* renamed from: f, reason: collision with root package name */
    private static final int f23146f = 274;

    /* renamed from: g, reason: collision with root package name */
    private static final int f23147g = 512;

    /* renamed from: h, reason: collision with root package name */
    private static final int f23148h = 769;

    /* renamed from: i, reason: collision with root package name */
    private static FileObserverC0382a f23149i = null;

    /* renamed from: j, reason: collision with root package name */
    private static ConnectivityManager f23150j = null;

    /* renamed from: k, reason: collision with root package name */
    private static NetworkInfo f23151k = null;

    /* renamed from: l, reason: collision with root package name */
    private static IntentFilter f23152l = null;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f23153m = false;

    /* renamed from: n, reason: collision with root package name */
    private static ArrayList<UMSenderStateNotify> f23154n = null;

    /* renamed from: q, reason: collision with root package name */
    private static final String f23157q = "report_policy";

    /* renamed from: r, reason: collision with root package name */
    private static final String f23158r = "report_interval";

    /* renamed from: t, reason: collision with root package name */
    private static final int f23160t = 15;

    /* renamed from: u, reason: collision with root package name */
    private static final int f23161u = 3;

    /* renamed from: v, reason: collision with root package name */
    private static final int f23162v = 90;

    /* renamed from: o, reason: collision with root package name */
    private static Object f23155o = new Object();

    /* renamed from: p, reason: collision with root package name */
    private static ReentrantLock f23156p = new ReentrantLock();

    /* renamed from: s, reason: collision with root package name */
    private static boolean f23159s = false;

    /* renamed from: w, reason: collision with root package name */
    private static int f23163w = 15;

    /* renamed from: x, reason: collision with root package name */
    private static Object f23164x = new Object();

    /* renamed from: y, reason: collision with root package name */
    private static BroadcastReceiver f23165y = new BroadcastReceiver() { // from class: com.umeng.commonsdk.framework.a.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int size;
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                Context appContext = UMModuleRegister.getAppContext();
                try {
                    if (a.f23150j != null) {
                        NetworkInfo unused = a.f23151k = a.f23150j.getActiveNetworkInfo();
                        if (a.f23151k == null || !a.f23151k.isAvailable()) {
                            ULog.i("--->>> network disconnected.");
                            boolean unused2 = a.f23153m = false;
                            return;
                        }
                        ULog.i("--->>> network isAvailable, check if there are any files to send.");
                        boolean unused3 = a.f23153m = true;
                        synchronized (a.f23155o) {
                            if (a.f23154n != null && (size = a.f23154n.size()) > 0) {
                                for (int i2 = 0; i2 < size; i2++) {
                                    ((UMSenderStateNotify) a.f23154n.get(i2)).onConnectionAvailable();
                                }
                            }
                        }
                        UMRTLog.e(UMRTLog.RTLOG_TAG, "网络状态通知：尝试发送 MSG_PROCESS_NEXT");
                        a.d();
                        if (a.f23151k.getType() != 1 || context == null) {
                            return;
                        }
                        try {
                            if (UMWorkDispatch.eventHasExist(32774)) {
                                return;
                            }
                            UMWorkDispatch.sendEvent(context, 32774, b.a(context).a(), null);
                        } catch (Throwable unused4) {
                        }
                    }
                } catch (Throwable th) {
                    UMCrashManager.reportCrash(appContext, th);
                }
            }
        }
    };

    /* renamed from: com.umeng.commonsdk.framework.a$a, reason: collision with other inner class name */
    public static class FileObserverC0382a extends FileObserver {
        public FileObserverC0382a(String str) {
            super(str);
        }

        @Override // android.os.FileObserver
        public void onEvent(int i2, String str) {
            if ((i2 & 8) != 8) {
                return;
            }
            ULog.d("--->>> envelope file created >>> " + str);
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> envelope file created >>> " + str);
            a.c(273);
        }
    }

    public a(Context context, Handler handler) {
        Context appContext = UMModuleRegister.getAppContext();
        f23150j = (ConnectivityManager) appContext.getSystemService("connectivity");
        f23143c = handler;
        try {
            if (f23141a == null) {
                HandlerThread handlerThread = new HandlerThread("NetWorkSender");
                f23141a = handlerThread;
                handlerThread.start();
                if (f23149i == null) {
                    FileObserverC0382a fileObserverC0382a = new FileObserverC0382a(UMFrUtils.getEnvelopeDirPath(context));
                    f23149i = fileObserverC0382a;
                    fileObserverC0382a.startWatching();
                    ULog.d("--->>> FileMonitor has already started!");
                }
                if (DeviceConfig.checkPermission(appContext, "android.permission.ACCESS_NETWORK_STATE") && f23150j != null && f23152l == null) {
                    IntentFilter intentFilter = new IntentFilter();
                    f23152l = intentFilter;
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    BroadcastReceiver broadcastReceiver = f23165y;
                    if (broadcastReceiver != null) {
                        appContext.registerReceiver(broadcastReceiver, f23152l);
                    }
                }
                n();
                if (f23142b == null) {
                    f23142b = new Handler(f23141a.getLooper()) { // from class: com.umeng.commonsdk.framework.a.2
                        @Override // android.os.Handler
                        public void handleMessage(Message message) {
                            int i2 = message.what;
                            if (i2 == 273) {
                                ULog.d("--->>> handleMessage: recv MSG_PROCESS_NEXT msg.");
                                try {
                                    a.f23156p.tryLock(1L, TimeUnit.SECONDS);
                                    try {
                                        a.r();
                                    } catch (Throwable unused) {
                                    }
                                    a.f23156p.unlock();
                                    return;
                                } catch (Throwable unused2) {
                                    return;
                                }
                            }
                            if (i2 == 274) {
                                a.p();
                            } else {
                                if (i2 != 512) {
                                    return;
                                }
                                a.q();
                            }
                        }
                    };
                }
                ImprintHandler.getImprintService(context).registImprintCallback(f23157q, this);
                ImprintHandler.getImprintService(context).registImprintCallback(f23158r, this);
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static int b() {
        int i2;
        synchronized (f23164x) {
            i2 = f23163w;
        }
        return i2;
    }

    public static void c() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(int i2) {
        Handler handler;
        if (!f23153m || (handler = f23142b) == null) {
            return;
        }
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = i2;
        f23142b.sendMessage(messageObtainMessage);
    }

    public static void d() {
        if (f23156p.tryLock()) {
            try {
                b(273);
            } finally {
                f23156p.unlock();
            }
        }
    }

    public static void e() {
        a(274, 3000);
    }

    private void n() {
        synchronized (f23164x) {
            if (Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE.equals(UMEnvelopeBuild.imprintProperty(UMModuleRegister.getAppContext(), f23157q, ""))) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> switch to report_policy 11");
                f23159s = true;
                f23163w = 15;
                int iIntValue = Integer.valueOf(UMEnvelopeBuild.imprintProperty(UMModuleRegister.getAppContext(), f23158r, Constants.VIA_REPORT_TYPE_WPA_STATE)).intValue();
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> set report_interval value to: " + iIntValue);
                if (iIntValue < 3 || iIntValue > 90) {
                    f23163w = 15;
                } else {
                    f23163w = iIntValue * 1000;
                }
            } else {
                f23159s = false;
            }
        }
    }

    private static void o() {
        if (f23141a != null) {
            f23141a = null;
        }
        if (f23142b != null) {
            f23142b = null;
        }
        if (f23143c != null) {
            f23143c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void p() {
        int size;
        synchronized (f23155o) {
            ArrayList<UMSenderStateNotify> arrayList = f23154n;
            if (arrayList != null && (size = arrayList.size()) > 0) {
                for (int i2 = 0; i2 < size; i2++) {
                    f23154n.get(i2).onSenderIdle();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void q() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void r() {
        ULog.d("--->>> handleProcessNext: Enter...");
        if (f23153m) {
            Context appContext = UMModuleRegister.getAppContext();
            try {
                if (UMFrUtils.envelopeFileNumber(appContext) > 0) {
                    ULog.d("--->>> The envelope file exists.");
                    if (UMFrUtils.envelopeFileNumber(appContext) > 200) {
                        ULog.d("--->>> Number of envelope files is greater than 200, remove old files first.");
                        UMFrUtils.removeRedundantEnvelopeFiles(appContext, 200);
                    }
                    File envelopeFile = UMFrUtils.getEnvelopeFile(appContext);
                    if (envelopeFile != null) {
                        String path = envelopeFile.getPath();
                        ULog.d("--->>> Ready to send envelope file [" + path + "].");
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> send envelope file [ " + path + "].");
                        if (!new c(appContext).a(envelopeFile)) {
                            ULog.d("--->>> Send envelope file failed, abandon and wait next trigger!");
                            return;
                        }
                        ULog.d("--->>> Send envelope file success, delete it.");
                        if (!UMFrUtils.removeEnvelopeFile(envelopeFile)) {
                            ULog.d("--->>> Failed to delete already processed file. We try again after delete failed.");
                            UMFrUtils.removeEnvelopeFile(envelopeFile);
                        }
                        c(273);
                        return;
                    }
                }
                e();
            } catch (Throwable th) {
                UMCrashManager.reportCrash(appContext, th);
            }
        }
    }

    @Override // com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback
    public void onImprintValueChanged(String str, String str2) {
        synchronized (f23164x) {
            if (f23157q.equals(str)) {
                if (Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE.equals(str2)) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> switch to report_policy 11");
                    f23159s = true;
                } else {
                    f23159s = false;
                }
            }
            if (f23158r.equals(str)) {
                int iIntValue = Integer.valueOf(str2).intValue();
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> set report_interval value to: " + iIntValue);
                if (iIntValue < 3 || iIntValue > 90) {
                    f23163w = 15000;
                } else {
                    f23163w = iIntValue * 1000;
                }
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> really set report_interval value to: " + f23163w);
            }
        }
    }

    public static void a(UMSenderStateNotify uMSenderStateNotify) {
        synchronized (f23155o) {
            try {
                if (f23154n == null) {
                    f23154n = new ArrayList<>();
                }
                if (uMSenderStateNotify != null) {
                    for (int i2 = 0; i2 < f23154n.size(); i2++) {
                        if (uMSenderStateNotify == f23154n.get(i2)) {
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> addConnStateObserver: input item has exist.");
                            return;
                        }
                    }
                    f23154n.add(uMSenderStateNotify);
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(UMModuleRegister.getAppContext(), th);
            }
        }
    }

    private static void b(int i2) {
        Handler handler;
        if (!f23153m || (handler = f23142b) == null || handler.hasMessages(i2)) {
            return;
        }
        Message messageObtainMessage = f23142b.obtainMessage();
        messageObtainMessage.what = i2;
        f23142b.sendMessage(messageObtainMessage);
    }

    public static boolean a() {
        boolean z2;
        synchronized (f23164x) {
            z2 = f23159s;
        }
        return z2;
    }

    private static void a(int i2, long j2) {
        Handler handler;
        if (!f23153m || (handler = f23142b) == null) {
            return;
        }
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = i2;
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> sendMsgDelayed: " + j2);
        f23142b.sendMessageDelayed(messageObtainMessage, j2);
    }

    private static void a(int i2, int i3) {
        Handler handler;
        if (!f23153m || (handler = f23142b) == null) {
            return;
        }
        handler.removeMessages(i2);
        Message messageObtainMessage = f23142b.obtainMessage();
        messageObtainMessage.what = i2;
        f23142b.sendMessageDelayed(messageObtainMessage, i3);
    }
}
