package com.hyphenate.chat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.http.X509TrustManagerExtensions;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMLogListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMCheckType;
import com.hyphenate.chat.adapter.EMAChatClient;
import com.hyphenate.chat.adapter.EMAConnectionListener;
import com.hyphenate.chat.adapter.EMADeviceInfo;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.chat.adapter.EMALogCallbackListener;
import com.hyphenate.chat.adapter.EMAMultiDeviceListener;
import com.hyphenate.chat.adapter.EMANetCallback;
import com.hyphenate.chat.core.EMAdvanceDebugManager;
import com.hyphenate.chat.core.EMChatConfigPrivate;
import com.hyphenate.cloud.EMHttpClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.util.DeviceUuidFactory;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.NetUtils;
import com.hyphenate.util.PathUtil;
import com.hyphenate.util.Utils;
import internal.com.getkeepsafe.relinker.ReLinker;
import io.socket.client.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EMClient {
    public static final String TAG = "EMClient";
    public static final String VERSION = "3.9.7";
    private static EMClient instance = null;
    static boolean libraryLoaded = false;
    private AppStateListener appStateListener;
    private EMChatManager chatManager;
    private EMChatRoomManager chatroomManager;
    private ClientLogListener clientLogListener;
    private ConnectivityManager connManager;
    private MyConnectionListener connectionListener;
    private EMContactManager contactManager;
    private EMAChatClient emaObject;
    private EMGroupManager groupManager;
    private EMChatConfigPrivate mChatConfigPrivate;
    private Context mContext;
    private MyMultiDeviceListener multiDeviceListenerImpl;
    private volatile EMPresenceManager presenceManager;
    private EMPushManager pushManager;
    private EMChatThreadManager threadManager;
    private EMTranslationManager translationManager;
    private EMUserInfoManager userInfoManager;
    private PowerManager.WakeLock wakeLock;
    private ExecutorService executor = null;
    private ExecutorService mainQueue = Executors.newSingleThreadExecutor();
    private ExecutorService sendQueue = Executors.newSingleThreadExecutor();
    private EMEncryptProvider encryptProvider = null;
    private boolean sdkInited = false;
    private List<EMConnectionListener> connectionListeners = Collections.synchronizedList(new ArrayList());
    private List<EMLogListener> logListeners = Collections.synchronizedList(new ArrayList());
    private EMSmartHeartBeat smartHeartbeat = null;
    private List<EMMultiDeviceListener> multiDeviceListeners = Collections.synchronizedList(new ArrayList());
    private EMAChatClient.EMANetwork currentNetworkType = EMAChatClient.EMANetwork.NETWORK_NONE;
    private boolean mIsLoginWithAgoraToken = false;
    private BroadcastReceiver connectivityBroadcastReceiver = new BroadcastReceiver() { // from class: com.hyphenate.chat.EMClient.11
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                EMLog.d(EMClient.TAG, "skip no connectivity action");
                return;
            }
            EMLog.d(EMClient.TAG, "connectivity receiver onReceiver");
            int i2 = AnonymousClass15.$SwitchMap$com$hyphenate$util$NetUtils$Types[NetUtils.getNetworkTypes(EMClient.this.getContext()).ordinal()];
            EMAChatClient.EMANetwork eMANetwork = (i2 == 1 || i2 == 2) ? EMAChatClient.EMANetwork.NETWORK_WIFI : i2 != 3 ? i2 != 4 ? EMAChatClient.EMANetwork.NETWORK_NONE : EMAChatClient.EMANetwork.NETWORK_CABLE : EMAChatClient.EMANetwork.NETWORK_MOBILE;
            EMAChatClient.EMANetwork eMANetwork2 = EMClient.this.currentNetworkType;
            EMAChatClient.EMANetwork eMANetwork3 = EMAChatClient.EMANetwork.NETWORK_NONE;
            boolean z2 = eMANetwork2 != eMANetwork3;
            boolean z3 = eMANetwork != eMANetwork3;
            EMClient.this.currentNetworkType = eMANetwork;
            if (z2 == z3) {
                EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (EMClient.this.smartHeartbeat != null) {
                            EMLog.i(EMClient.TAG, "Network availability no change, just return. " + EMClient.this.currentNetworkType + ", but check ping");
                            EMClient.this.smartHeartbeat.sendPingCheckConnection();
                        }
                    }
                });
                return;
            }
            EMLog.i(EMClient.TAG, "Network availability changed, notify... " + EMClient.this.currentNetworkType);
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.11.2
                @Override // java.lang.Runnable
                public void run() {
                    EMClient.this.emaObject.onNetworkChanged(EMClient.this.currentNetworkType);
                }
            });
        }
    };
    private List<Activity> resumeActivityList = new ArrayList();
    private boolean duringChecking = false;

    /* renamed from: com.hyphenate.chat.EMClient$15, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass15 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$util$NetUtils$Types;

        static {
            int[] iArr = new int[NetUtils.Types.values().length];
            $SwitchMap$com$hyphenate$util$NetUtils$Types = iArr;
            try {
                iArr[NetUtils.Types.WIFI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$util$NetUtils$Types[NetUtils.Types.OTHERS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$util$NetUtils$Types[NetUtils.Types.MOBILE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$util$NetUtils$Types[NetUtils.Types.ETHERNET.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$hyphenate$util$NetUtils$Types[NetUtils.Types.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public interface AppStateListener {
        void onBackground();

        void onForeground();
    }

    public interface CheckResultListener {
        void onResult(@EMCheckType.CheckType int i2, int i3, String str);
    }

    public class ClientLogListener extends EMALogCallbackListener {
        public ClientLogListener() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLogCallback$0(String str) {
            synchronized (EMClient.this.logListeners) {
                Iterator it = EMClient.this.logListeners.iterator();
                while (it.hasNext()) {
                    try {
                        ((EMLogListener) it.next()).onLog(str);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMALogCallbackListener, com.hyphenate.chat.adapter.EMALogCallbackListenerInterface
        public void onLogCallback(final String str) {
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8584c.lambda$onLogCallback$0(str);
                }
            });
        }
    }

    public class MyConnectionListener extends EMAConnectionListener {
        public MyConnectionListener() {
        }

        @Override // com.hyphenate.chat.adapter.EMAConnectionListener, com.hyphenate.chat.adapter.EMAConnectionListenerInterface
        public void onConnected() {
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.MyConnectionListener.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (EMClient.this.connectionListeners) {
                        try {
                            Iterator it = EMClient.this.connectionListeners.iterator();
                            while (it.hasNext()) {
                                ((EMConnectionListener) it.next()).onConnected();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAConnectionListener, com.hyphenate.chat.adapter.EMAConnectionListenerInterface
        public void onDisconnected(final int i2) {
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.MyConnectionListener.2
                /* JADX WARN: Removed duplicated region for block: B:15:0x0042 A[Catch: all -> 0x00c4, TryCatch #1 {, blocks: (B:4:0x0009, B:18:0x006c, B:19:0x0078, B:21:0x007e, B:25:0x008e, B:41:0x00c2, B:34:0x00a0, B:35:0x00ac, B:37:0x00b2, B:40:0x00bf, B:24:0x008b, B:14:0x002c, B:15:0x0042, B:17:0x005a), top: B:48:0x0009, inners: #0, #2 }] */
                /* JADX WARN: Removed duplicated region for block: B:21:0x007e A[Catch: Exception -> 0x008a, all -> 0x00c4, LOOP:0: B:19:0x0078->B:21:0x007e, LOOP_END, TRY_LEAVE, TryCatch #2 {Exception -> 0x008a, blocks: (B:18:0x006c, B:19:0x0078, B:21:0x007e), top: B:50:0x006c, outer: #1 }] */
                /* JADX WARN: Removed duplicated region for block: B:37:0x00b2 A[Catch: Exception -> 0x00be, all -> 0x00c4, LOOP:1: B:35:0x00ac->B:37:0x00b2, LOOP_END, TRY_LEAVE, TryCatch #0 {Exception -> 0x00be, blocks: (B:34:0x00a0, B:35:0x00ac, B:37:0x00b2), top: B:46:0x00a0, outer: #1 }] */
                /* JADX WARN: Removed duplicated region for block: B:46:0x00a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void run() {
                    /*
                        r12 = this;
                        com.hyphenate.chat.EMClient$MyConnectionListener r0 = com.hyphenate.chat.EMClient.MyConnectionListener.this
                        com.hyphenate.chat.EMClient r0 = com.hyphenate.chat.EMClient.this
                        java.util.List r0 = com.hyphenate.chat.EMClient.access$600(r0)
                        monitor-enter(r0)
                        int r1 = r2     // Catch: java.lang.Throwable -> Lc4
                        r2 = 305(0x131, float:4.27E-43)
                        r3 = 220(0xdc, float:3.08E-43)
                        r4 = 217(0xd9, float:3.04E-43)
                        r5 = 216(0xd8, float:3.03E-43)
                        r6 = 214(0xd6, float:3.0E-43)
                        r7 = 213(0xd5, float:2.98E-43)
                        r8 = 207(0xcf, float:2.9E-43)
                        r9 = 206(0xce, float:2.89E-43)
                        if (r1 == r9) goto L42
                        if (r1 == r8) goto L2c
                        if (r1 == r7) goto L42
                        if (r1 == r6) goto L42
                        if (r1 == r5) goto L42
                        if (r1 == r4) goto L42
                        if (r1 == r3) goto L42
                        if (r1 == r2) goto L42
                        goto L6c
                    L2c:
                        com.hyphenate.chat.EMSessionManager r1 = com.hyphenate.chat.EMSessionManager.getInstance()     // Catch: java.lang.Throwable -> Lc4
                        r1.clearLastLoginUser()     // Catch: java.lang.Throwable -> Lc4
                        com.hyphenate.chat.EMSessionManager r1 = com.hyphenate.chat.EMSessionManager.getInstance()     // Catch: java.lang.Throwable -> Lc4
                        r1.clearLastLoginToken()     // Catch: java.lang.Throwable -> Lc4
                        com.hyphenate.chat.EMSessionManager r1 = com.hyphenate.chat.EMSessionManager.getInstance()     // Catch: java.lang.Throwable -> Lc4
                        r1.clearLastLoginPwd()     // Catch: java.lang.Throwable -> Lc4
                        goto L6c
                    L42:
                        com.hyphenate.chat.EMSessionManager r1 = com.hyphenate.chat.EMSessionManager.getInstance()     // Catch: java.lang.Throwable -> Lc4
                        r1.clearLastLoginToken()     // Catch: java.lang.Throwable -> Lc4
                        com.hyphenate.chat.EMSessionManager r1 = com.hyphenate.chat.EMSessionManager.getInstance()     // Catch: java.lang.Throwable -> Lc4
                        r1.clearLastLoginPwd()     // Catch: java.lang.Throwable -> Lc4
                        com.hyphenate.chat.EMClient$MyConnectionListener r1 = com.hyphenate.chat.EMClient.MyConnectionListener.this     // Catch: java.lang.Throwable -> Lc4
                        com.hyphenate.chat.EMClient r1 = com.hyphenate.chat.EMClient.this     // Catch: java.lang.Throwable -> Lc4
                        boolean r1 = r1.isSdkInited()     // Catch: java.lang.Throwable -> Lc4
                        if (r1 == 0) goto L6c
                        com.hyphenate.chat.core.a r1 = com.hyphenate.chat.core.a.a()     // Catch: java.lang.Throwable -> Lc4
                        java.lang.String r10 = ""
                        r1.i(r10)     // Catch: java.lang.Throwable -> Lc4
                        com.hyphenate.chat.core.a r1 = com.hyphenate.chat.core.a.a()     // Catch: java.lang.Throwable -> Lc4
                        java.lang.String r10 = ""
                        r1.h(r10)     // Catch: java.lang.Throwable -> Lc4
                    L6c:
                        com.hyphenate.chat.EMClient$MyConnectionListener r1 = com.hyphenate.chat.EMClient.MyConnectionListener.this     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        com.hyphenate.chat.EMClient r1 = com.hyphenate.chat.EMClient.this     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        java.util.List r1 = com.hyphenate.chat.EMClient.access$600(r1)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                    L78:
                        boolean r10 = r1.hasNext()     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        if (r10 == 0) goto L8e
                        java.lang.Object r10 = r1.next()     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        com.hyphenate.EMConnectionListener r10 = (com.hyphenate.EMConnectionListener) r10     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        int r11 = r2     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        r10.onDisconnected(r11)     // Catch: java.lang.Exception -> L8a java.lang.Throwable -> Lc4
                        goto L78
                    L8a:
                        r1 = move-exception
                        r1.printStackTrace()     // Catch: java.lang.Throwable -> Lc4
                    L8e:
                        int r1 = r2     // Catch: java.lang.Throwable -> Lc4
                        if (r1 == r9) goto La0
                        if (r1 == r8) goto La0
                        if (r1 == r7) goto La0
                        if (r1 == r3) goto La0
                        if (r1 == r2) goto La0
                        if (r1 == r6) goto La0
                        if (r1 == r5) goto La0
                        if (r1 != r4) goto Lc2
                    La0:
                        com.hyphenate.chat.EMClient$MyConnectionListener r1 = com.hyphenate.chat.EMClient.MyConnectionListener.this     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        com.hyphenate.chat.EMClient r1 = com.hyphenate.chat.EMClient.this     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        java.util.List r1 = com.hyphenate.chat.EMClient.access$600(r1)     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                    Lac:
                        boolean r2 = r1.hasNext()     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        if (r2 == 0) goto Lc2
                        java.lang.Object r2 = r1.next()     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        com.hyphenate.EMConnectionListener r2 = (com.hyphenate.EMConnectionListener) r2     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        int r3 = r2     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        r2.onLogout(r3)     // Catch: java.lang.Exception -> Lbe java.lang.Throwable -> Lc4
                        goto Lac
                    Lbe:
                        r1 = move-exception
                        r1.printStackTrace()     // Catch: java.lang.Throwable -> Lc4
                    Lc2:
                        monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc4
                        return
                    Lc4:
                        r1 = move-exception
                        monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc4
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMClient.MyConnectionListener.AnonymousClass2.run():void");
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAConnectionListener, com.hyphenate.chat.adapter.EMAConnectionListenerInterface
        public void onReceiveToken(String str, long j2) {
            if (TextUtils.isEmpty(str) || j2 <= 0) {
                EMLog.e(EMClient.TAG, "onReceiveToken: params received is invalid");
                return;
            }
            EMClient.this.initLoginWithAgoraData(true, String.valueOf(j2), j2 - System.currentTimeMillis());
            EMClient.this.saveToken();
        }

        @Override // com.hyphenate.chat.adapter.EMAConnectionListener, com.hyphenate.chat.adapter.EMAConnectionListenerInterface
        public void onTokenNotification(final int i2) {
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.MyConnectionListener.3
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (EMClient.this.connectionListeners) {
                        try {
                            int i3 = i2;
                            if (i3 == 108 || i3 == 401) {
                                EMClient.this.logout();
                                for (EMConnectionListener eMConnectionListener : EMClient.this.connectionListeners) {
                                    EMLog.d(EMClient.TAG, "MyConnectionListener onTokenExpired code: " + i2);
                                    eMConnectionListener.onTokenExpired();
                                }
                            } else {
                                for (EMConnectionListener eMConnectionListener2 : EMClient.this.connectionListeners) {
                                    EMLog.d(EMClient.TAG, "MyConnectionListener onTokenWillExpire code: " + i2);
                                    eMConnectionListener2.onTokenWillExpire();
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            EMLog.e(EMClient.TAG, "MyConnectionListener onTokenNotification Exception: " + e2.getMessage());
                        }
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAConnectionListener, com.hyphenate.chat.adapter.EMAConnectionListenerInterface
        public boolean verifyServerCert(List<String> list, String str) throws CertificateException {
            String str2;
            if (list == null) {
                str2 = "List<String> certschain : null ";
            } else if (TextUtils.isEmpty(str)) {
                str2 = "domain is empty or null ";
            } else {
                EMLog.d(EMClient.TAG, "domain = " + str);
                X509Certificate[] x509CertificateArrConvertToCerts = EasyUtils.convertToCerts(list);
                try {
                    new X509TrustManagerExtensions(EasyUtils.getSystemDefaultTrustManager()).checkServerTrusted(x509CertificateArrConvertToCerts, x509CertificateArrConvertToCerts[0].getType(), str);
                    return true;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    EMLog.e(EMClient.TAG, e2.getMessage());
                    str2 = "List<String> certschain :" + list.toString();
                }
            }
            EMLog.d(EMClient.TAG, str2);
            return false;
        }
    }

    public class MyMultiDeviceListener extends EMAMultiDeviceListener {
        public MyMultiDeviceListener() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onThreadEvent$0(int i2, String str, List list) {
            synchronized (EMClient.this.multiDeviceListeners) {
                Iterator it = EMClient.this.multiDeviceListeners.iterator();
                while (it.hasNext()) {
                    try {
                        ((EMMultiDeviceListener) it.next()).onChatThreadEvent(i2, str, list);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        @Override // com.hyphenate.chat.adapter.EMAMultiDeviceListener, com.hyphenate.chat.adapter.EMAMultiDeviceListenerInterface
        public void onContactEvent(final int i2, final String str, final String str2) {
            EMLog.d(EMClient.TAG, "onContactEvent:" + i2 + " target:" + str + " ext:" + str2);
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.MyMultiDeviceListener.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (EMClient.this.multiDeviceListeners) {
                        try {
                            Iterator it = EMClient.this.multiDeviceListeners.iterator();
                            while (it.hasNext()) {
                                ((EMMultiDeviceListener) it.next()).onContactEvent(i2, str, str2);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAMultiDeviceListener, com.hyphenate.chat.adapter.EMAMultiDeviceListenerInterface
        public void onGroupEvent(final int i2, final String str, final List<String> list) {
            EMLog.d(EMClient.TAG, "onGroupEvent:" + i2 + " target:" + str + " usernames:" + list);
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.MyMultiDeviceListener.2
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (EMClient.this.multiDeviceListeners) {
                        try {
                            Iterator it = EMClient.this.multiDeviceListeners.iterator();
                            while (it.hasNext()) {
                                ((EMMultiDeviceListener) it.next()).onGroupEvent(i2, str, list);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override // com.hyphenate.chat.adapter.EMAMultiDeviceListener, com.hyphenate.chat.adapter.EMAMultiDeviceListenerInterface
        public void onThreadEvent(final int i2, final String str, final List<String> list) {
            EMLog.d(EMClient.TAG, "onThreadEvent:" + i2 + " target:" + str + " usernames:" + list);
            EMClient.this.execute(new Runnable() { // from class: com.hyphenate.chat.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8586c.lambda$onThreadEvent$0(i2, str, list);
                }
            });
        }
    }

    private EMClient() {
    }

    private boolean _loadLibrary(String str) {
        return _loadLibrary(str, true);
    }

    private boolean _loadLibrary(String str, boolean z2) {
        try {
            ReLinker.loadLibrary(this.mContext, str);
            return true;
        } catch (Throwable th) {
            if (!z2) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLogout(CheckResultListener checkResultListener) {
        if (!isLoggedInBefore()) {
            logout();
            notifyCheckResult(checkResultListener, 5, 0, "");
        }
        this.duringChecking = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void getChatToken(String str, String str2, EMCallBack eMCallBack) {
        String str3;
        EMAError eMAError = new EMAError();
        String chatTokenbyAgoraToken = this.emaObject.getChatTokenbyAgoraToken(str2, eMAError);
        if (eMAError.errCode() == 0) {
            EMLog.d(TAG, "getChatTokenbyAgoraToken success");
            if (chatTokenbyAgoraToken == null || chatTokenbyAgoraToken.length() <= 0) {
                EMLog.e(TAG, "getChatTokenbyAgoraToken response is null");
                str3 = "getChatTokenbyAgoraToken response is null or empty!";
            } else {
                try {
                    JSONObject jSONObject = new JSONObject(chatTokenbyAgoraToken);
                    String strOptString = jSONObject.optString("access_token");
                    String strOptString2 = jSONObject.optString("expire_timestamp");
                    long jLongValue = Long.valueOf(strOptString2).longValue() - System.currentTimeMillis();
                    if (TextUtils.isEmpty(strOptString)) {
                        throw new Exception("chatToken  is null or empty!");
                    }
                    initLoginWithAgoraData(true, strOptString2, jLongValue);
                    _login(str.toLowerCase(), strOptString, eMCallBack, false, EMLoginType.LOGIN_AGORA_TOKEN);
                } catch (Exception e2) {
                    EMLog.e(TAG, "getChatTokenbyAgoraToken Exception:" + e2.getMessage());
                    str3 = "getChatTokenbyAgoraToken Exception:" + e2.getMessage();
                }
            }
            eMCallBack.onError(1, str3);
        } else {
            eMCallBack.onError(eMAError.errCode(), eMAError.errMsg());
            EMLog.e(TAG, "getChatTokenbyAgoraToken failed error:" + eMAError.errCode() + "  errorMessage:" + eMAError.errMsg());
        }
    }

    private String getDidInfo() {
        return Build.MANUFACTURER + "/" + Build.MODEL + "/" + Build.HARDWARE + "/" + Build.VERSION.SDK_INT + "/" + Build.VERSION.RELEASE;
    }

    public static EMClient getInstance() {
        if (instance == null) {
            synchronized (EMClient.class) {
                if (instance == null) {
                    instance = new EMClient();
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getLocationString(boolean z2) {
        JSONObject deviceInfo = !z2 ? getDeviceInfo() : null;
        return deviceInfo == null ? "" : deviceInfo.toString();
    }

    private void handleError(EMAError eMAError) throws HyphenateException {
        if (eMAError.errCode() != 0) {
            throw new HyphenateException(eMAError);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initLoginWithAgoraData(boolean z2, String str, long j2) {
        this.mIsLoginWithAgoraToken = z2;
        if (z2) {
            EMSessionManager.getInstance().setLoginWithAgoraData(z2, str, j2);
        } else {
            EMSessionManager.getInstance().clearLoginWithAgoraTokenData();
        }
    }

    private void initManagers() {
        EMHttpClient.getInstance().onInit(this.mChatConfigPrivate);
        chatManager();
        contactManager();
        groupManager();
        chatroomManager();
        presenceManager();
        chatThreadManager();
        setNatvieNetworkCallback();
        EMSessionManager.getInstance().init(this, this.emaObject.getSessionManager());
    }

    private void loadLibrary() {
        if (libraryLoaded) {
            return;
        }
        _loadLibrary("cipherdb", false);
        ReLinker.loadLibrary(this.mContext, "hyphenate");
        libraryLoaded = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCheckResult(CheckResultListener checkResultListener, @EMCheckType.CheckType int i2, int i3, String str) {
        if (checkResultListener == null) {
            return;
        }
        checkResultListener.onResult(i2, i3, str);
    }

    @TargetApi(14)
    private void registerActivityLifecycleCallbacks() {
        if (Utils.isSdk14()) {
            ((Application) this.mContext).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.hyphenate.chat.EMClient.12
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                    if (EMClient.this.resumeActivityList.contains(activity)) {
                        return;
                    }
                    EMClient.this.resumeActivityList.add(activity);
                    if (EMClient.this.resumeActivityList.size() != 1 || EMClient.this.appStateListener == null) {
                        return;
                    }
                    EMClient.this.appStateListener.onForeground();
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity) {
                    EMClient.this.resumeActivityList.remove(activity);
                    if (!EMClient.this.resumeActivityList.isEmpty() || EMClient.this.appStateListener == null) {
                        return;
                    }
                    EMClient.this.appStateListener.onBackground();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveToken() {
        if (this.emaObject == null) {
            return;
        }
        EMAError eMAError = new EMAError();
        String userToken = this.emaObject.getUserToken(false, eMAError);
        if (eMAError.errCode() == 0) {
            EMSessionManager.getInstance().setLastLoginToken(userToken);
            EMSessionManager.getInstance().setLastLoginWithToken(true);
            EMSessionManager.getInstance().clearLastLoginPwd();
            if (this.mIsLoginWithAgoraToken) {
                EMSessionManager.getInstance().startCountDownTokenAvailableTime(this.connectionListener);
            }
        }
    }

    public void _login(final String str, final String str2, final EMCallBack eMCallBack, final boolean z2, final EMLoginType eMLoginType) {
        if (getChatConfigPrivate() == null || !this.sdkInited) {
            eMCallBack.onError(1, "");
            return;
        }
        EMLog.e(TAG, "emchat manager login in process:" + Process.myPid());
        execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.8
            @Override // java.lang.Runnable
            public void run() {
                EMLog.e(EMClient.TAG, "emchat manager login in process:" + Process.myPid() + " threadName:" + Thread.currentThread().getName() + " ID:" + Thread.currentThread().getId());
                if (str == null) {
                    eMCallBack.onError(101, "Invalid user name");
                    return;
                }
                EMAError eMAError = new EMAError();
                EMClient.this.emaObject.login(str, str2, z2, eMLoginType.ordinal(), eMAError);
                if (eMAError.errCode() == 0) {
                    EMSessionManager.getInstance().setLastLoginUser(str);
                    EMLoginType eMLoginType2 = eMLoginType;
                    if (eMLoginType2 == EMLoginType.LOGIN_TOKEN || eMLoginType2 == EMLoginType.LOGIN_AGORA_TOKEN) {
                        EMClient.this.saveToken();
                    } else {
                        EMSessionManager.getInstance().setLastLoginPwd(str2);
                        EMSessionManager.getInstance().setLastLoginWithToken(false);
                        EMSessionManager.getInstance().clearLastLoginToken();
                    }
                    EMClient.this.onNewLogin();
                    EMPushHelper.getInstance().register();
                    eMCallBack.onSuccess();
                } else {
                    eMCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                    if (eMAError.errCode() == 200 && EMClient.this.mIsLoginWithAgoraToken) {
                        EMClient.this.emaObject.renewToken(str2);
                        EMSessionManager.getInstance().setLastLoginToken(str2);
                        EMSessionManager.getInstance().startCountDownTokenAvailableTime(EMClient.this.connectionListener);
                    }
                }
                if (eMAError.errCode() == 0) {
                    if (EMClient.this.getOptions().isEnableStatistics()) {
                        EMClient eMClient = EMClient.this;
                        eMClient.setPresence(eMClient.getLocationString(z2));
                    } else {
                        EMLog.d(EMClient.TAG, "statistics is not enabled");
                    }
                }
                if (eMAError.errCode() == 202) {
                    EMSessionManager.getInstance().clearLastLoginPwd();
                    EMSessionManager.getInstance().clearLastLoginToken();
                }
            }
        });
    }

    public void addConnectionListener(final EMConnectionListener eMConnectionListener) {
        if (eMConnectionListener == null) {
            return;
        }
        synchronized (this.connectionListeners) {
            if (!this.connectionListeners.contains(eMConnectionListener)) {
                this.connectionListeners.add(eMConnectionListener);
            }
        }
        execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.6
            @Override // java.lang.Runnable
            public void run() {
                if (EMClient.this.isConnected()) {
                    eMConnectionListener.onConnected();
                } else {
                    eMConnectionListener.onDisconnected(2);
                }
            }
        });
    }

    public void addLogListener(EMLogListener eMLogListener) {
        if (eMLogListener == null) {
            return;
        }
        synchronized (this.logListeners) {
            if (!this.logListeners.contains(eMLogListener)) {
                this.logListeners.add(eMLogListener);
            }
        }
    }

    public void addMultiDeviceListener(EMMultiDeviceListener eMMultiDeviceListener) {
        this.multiDeviceListeners.add(eMMultiDeviceListener);
    }

    public void changeAppkey(String str) throws HyphenateException {
        EMAError eMAErrorChangeAppkey = this.emaObject.changeAppkey(str);
        if (eMAErrorChangeAppkey.errCode() == 0) {
            getOptions().updatePath(str);
        }
        handleError(eMAErrorChangeAppkey);
    }

    public EMChatManager chatManager() {
        if (this.chatManager == null) {
            synchronized (EMClient.class) {
                if (this.chatManager == null) {
                    this.chatManager = new EMChatManager(this, this.emaObject.getChatManager(), this.emaObject.getReactionManager());
                }
            }
        }
        return this.chatManager;
    }

    public EMChatThreadManager chatThreadManager() {
        if (this.threadManager == null) {
            synchronized (EMClient.class) {
                if (this.threadManager == null) {
                    this.threadManager = new EMChatThreadManager(this, this.emaObject.getThreadManager());
                }
            }
        }
        return this.threadManager;
    }

    public EMChatRoomManager chatroomManager() {
        if (this.chatroomManager == null) {
            synchronized (EMClient.class) {
                if (this.chatroomManager == null) {
                    this.chatroomManager = new EMChatRoomManager(this, this.emaObject.getChatRoomManager());
                }
            }
        }
        return this.chatroomManager;
    }

    public void check(final String str, final String str2, final CheckResultListener checkResultListener) {
        if (this.duringChecking) {
            EMLog.i("EMServiceChecker", "During service checking, please hold on...");
            return;
        }
        this.duringChecking = true;
        if (isLoggedInBefore()) {
            str = getCurrentUser();
            str2 = EMSessionManager.getInstance().getLastLoginPwd();
        }
        new Thread(new Runnable() { // from class: com.hyphenate.chat.EMClient.14
            @Override // java.lang.Runnable
            public void run() {
                EMClient.this.emaObject.check(str, str2, new EMAChatClient.CheckResultListener() { // from class: com.hyphenate.chat.EMClient.14.1
                    @Override // com.hyphenate.chat.adapter.EMAChatClient.CheckResultListener
                    public void onResult(int i2, int i3, String str3) {
                        EMLog.i("EMServiceChecker", "type: " + i2 + ", result: " + i3 + ", desc: " + str3);
                        AnonymousClass14 anonymousClass14 = AnonymousClass14.this;
                        EMClient.this.notifyCheckResult(checkResultListener, i2, i3, str3);
                        if (i3 != 0) {
                            EMClient.this.duringChecking = false;
                        } else if (i2 == 3) {
                            AnonymousClass14 anonymousClass142 = AnonymousClass14.this;
                            EMClient.this.checkLogout(checkResultListener);
                        }
                    }
                });
            }
        }).start();
    }

    public void checkTokenAvailability() {
        if (this.mIsLoginWithAgoraToken) {
            EMSessionManager.getInstance().checkTokenAvailability(this.connectionListener);
        }
    }

    public String compressLogs() throws HyphenateException {
        EMAError eMAError = new EMAError();
        String strCompressLogs = this.emaObject.compressLogs(eMAError);
        handleError(eMAError);
        return strCompressLogs;
    }

    public EMContactManager contactManager() {
        if (this.contactManager == null) {
            synchronized (EMClient.class) {
                if (this.contactManager == null) {
                    this.contactManager = new EMContactManager(this, this.emaObject.getContactManager());
                }
            }
        }
        return this.contactManager;
    }

    public void createAccount(String str, String str2) throws HyphenateException {
        String lowerCase = str.toLowerCase();
        if (!Pattern.compile("^[a-zA-Z0-9_.-]+$").matcher(lowerCase).find()) {
            throw new HyphenateException(205, "illegal user name");
        }
        handleError(this.emaObject.createAccount(lowerCase, str2));
    }

    public void disconnect() {
        EMLog.d(TAG, Socket.EVENT_DISCONNECT);
        this.emaObject.disconnect();
    }

    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    public void executeOnMainQueue(Runnable runnable) {
        this.mainQueue.submit(runnable);
    }

    public void executeOnSendQueue(Runnable runnable) {
        this.sendQueue.submit(runnable);
    }

    public void forceReconnect() {
        EMLog.d(TAG, "forceReconnect");
        disconnect();
        reconnect();
    }

    public String getAccessToken() {
        return getChatConfigPrivate().m();
    }

    public EMChatConfigPrivate getChatConfigPrivate() {
        return this.mChatConfigPrivate;
    }

    public Context getContext() {
        return this.mContext;
    }

    public synchronized String getCurrentUser() {
        if (EMSessionManager.getInstance().currentUser != null && EMSessionManager.getInstance().currentUser.username != null && !EMSessionManager.getInstance().currentUser.username.equals("")) {
            return EMSessionManager.getInstance().currentUser.username;
        }
        return EMSessionManager.getInstance().getLastLoginUser();
    }

    public JSONObject getDeviceInfo() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("deviceid", new DeviceUuidFactory(this.mContext).getDeviceUuid().toString());
            jSONObject.put("app-id", this.mContext.getPackageName());
            jSONObject.put("hid", getInstance().getCurrentUser());
            jSONObject.put("os", "android");
            jSONObject.put("os-version", Build.VERSION.RELEASE);
            jSONObject.put("manufacturer", Build.MANUFACTURER);
            jSONObject.put("model", Build.MODEL);
        } catch (JSONException e2) {
            EMLog.d(TAG, e2.getMessage());
        }
        return jSONObject;
    }

    public EMEncryptProvider getEncryptProvider() {
        if (this.encryptProvider == null) {
            EMLog.d(TAG, "encrypt provider is not set, create default");
            this.encryptProvider = new EMEncryptProvider() { // from class: com.hyphenate.chat.EMClient.10
                @Override // com.hyphenate.chat.EMEncryptProvider
                public byte[] decrypt(byte[] bArr, String str) {
                    try {
                        return EMClient.this.emaObject.getSessionManager().decrypt(new String(bArr)).getBytes();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return bArr;
                    }
                }

                @Override // com.hyphenate.chat.EMEncryptProvider
                public byte[] encrypt(byte[] bArr, String str) {
                    try {
                        return EMClient.this.emaObject.getSessionManager().encrypt(new String(bArr)).getBytes();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return bArr;
                    }
                }
            };
        }
        return this.encryptProvider;
    }

    public List<EMDeviceInfo> getLoggedInDevicesFromServer(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        List<EMADeviceInfo> loggedInDevicesFromServer = this.emaObject.getLoggedInDevicesFromServer(str, str2, eMAError);
        handleError(eMAError);
        ArrayList arrayList = new ArrayList();
        Iterator<EMADeviceInfo> it = loggedInDevicesFromServer.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMDeviceInfo(it.next()));
        }
        return arrayList;
    }

    public EMOptions getOptions() {
        return this.mChatConfigPrivate.b();
    }

    public void getUserTokenFromServer(final String str, final String str2, final EMValueCallBack<String> eMValueCallBack) {
        execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.7
            @Override // java.lang.Runnable
            public void run() {
                EMAError eMAError = new EMAError();
                String userTokenFromServer = EMClient.this.emaObject.getUserTokenFromServer(str, str2, eMAError);
                if (eMValueCallBack == null) {
                    return;
                }
                if (eMAError.errCode() == 0) {
                    eMValueCallBack.onSuccess(userTokenFromServer);
                } else {
                    eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                }
            }
        });
    }

    public EMGroupManager groupManager() {
        if (this.groupManager == null) {
            synchronized (EMClient.class) {
                if (this.groupManager == null) {
                    this.groupManager = new EMGroupManager(this, this.emaObject.getGroupManager());
                }
            }
        }
        return this.groupManager;
    }

    public void init(Context context, EMOptions eMOptions) {
        if (this.sdkInited) {
            return;
        }
        final EMTimeTag eMTimeTag = new EMTimeTag();
        eMTimeTag.start();
        this.mContext = context.getApplicationContext();
        this.connManager = (ConnectivityManager) context.getSystemService("connectivity");
        registerActivityLifecycleCallbacks();
        loadLibrary();
        EMChatConfigPrivate eMChatConfigPrivate = new EMChatConfigPrivate();
        this.mChatConfigPrivate = eMChatConfigPrivate;
        eMChatConfigPrivate.a(context, eMOptions);
        eMOptions.setConfig(this.mChatConfigPrivate);
        getChatConfigPrivate().f(new DeviceUuidFactory(context).getDeviceUuid().toString());
        getChatConfigPrivate().i(Build.MANUFACTURER + Build.MODEL);
        getChatConfigPrivate().g(getDidInfo());
        getChatConfigPrivate().h(UUID.randomUUID().toString());
        EMPushConfig pushConfig = eMOptions.getPushConfig();
        if (pushConfig == null) {
            pushConfig = new EMPushConfig.Builder(this.mContext).build();
        }
        EMPushHelper.getInstance().init(context, new EMPushConfig.Builder(this.mContext, pushConfig).build());
        this.emaObject = EMAChatClient.create(this.mChatConfigPrivate.f8545a);
        ClientLogListener clientLogListener = new ClientLogListener();
        this.clientLogListener = clientLogListener;
        this.emaObject.addLogCallbackListener(clientLogListener);
        MyConnectionListener myConnectionListener = new MyConnectionListener();
        this.connectionListener = myConnectionListener;
        this.emaObject.addConnectionListener(myConnectionListener);
        MyMultiDeviceListener myMultiDeviceListener = new MyMultiDeviceListener();
        this.multiDeviceListenerImpl = myMultiDeviceListener;
        this.emaObject.addMultiDeviceListener(myMultiDeviceListener);
        this.executor = Executors.newCachedThreadPool();
        initManagers();
        this.mIsLoginWithAgoraToken = EMSessionManager.getInstance().getIsLoginWithAgoraToken();
        final String lastLoginUser = EMSessionManager.getInstance().getLastLoginUser();
        EMLog.e(TAG, "is autoLogin : " + eMOptions.getAutoLogin());
        EMLog.e(TAG, "lastLoginUser : " + lastLoginUser);
        EMLog.e(TAG, "hyphenate SDK is initialized with version : " + getChatConfigPrivate().e());
        this.wakeLock = ((PowerManager) this.mContext.getSystemService("power")).newWakeLock(1, "emclient");
        com.hyphenate.notification.core.b.a().a(context);
        this.sdkInited = true;
        this.mContext.registerReceiver(this.connectivityBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        onNetworkChanged();
        if (!eMOptions.getAutoLogin() || !isLoggedInBefore()) {
            eMTimeTag.stop();
            EMLog.d(TAG, "[Collector][sdk init]init time is : " + eMTimeTag.timeStr());
            return;
        }
        final String lastLoginToken = EMSessionManager.getInstance().getLastLoginToken();
        final String lastLoginPwd = EMSessionManager.getInstance().getLastLoginPwd();
        EMSessionManager.getInstance().currentUser = new EMContact(lastLoginUser);
        final EMCallBack eMCallBack = new EMCallBack() { // from class: com.hyphenate.chat.EMClient.1
            @Override // com.hyphenate.EMCallBack
            public void onError(int i2, String str) {
                Log.d(EMClient.TAG, "hyphenate login onError");
                eMTimeTag.stop();
                EMLog.d(EMClient.TAG, "[Collector][sdk init]init time is : " + eMTimeTag.timeStr());
            }

            @Override // com.hyphenate.EMCallBack
            public void onProgress(int i2, String str) {
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
                EMSessionManager.getInstance().currentUser = new EMContact(lastLoginUser);
                Log.d(EMClient.TAG, "hyphenate login onSuccess");
                eMTimeTag.stop();
                EMLog.d(EMClient.TAG, "[Collector][sdk init]init time is : " + eMTimeTag.timeStr());
            }
        };
        execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.2
            @Override // java.lang.Runnable
            public void run() {
                EMClient.this.getChatConfigPrivate().b(lastLoginUser);
                EMClient.this.groupManager().loadAllGroups();
                EMClient.this.chatManager().loadAllConversationsFromDB();
                EMClient.this._login(lastLoginUser, EMSessionManager.getInstance().isLastLoginWithToken() ? lastLoginToken : lastLoginPwd, eMCallBack, true, EMSessionManager.getInstance().isLastLoginWithToken() ? EMLoginType.LOGIN_TOKEN : EMLoginType.LOGIN_PASSWORD);
            }
        });
    }

    public boolean isConnected() {
        return this.emaObject.isConnected();
    }

    public boolean isFCMAvailable() {
        return EMPushHelper.getInstance().getPushType() == EMPushType.FCM;
    }

    public boolean isLoggedIn() {
        return this.emaObject.isLoggedIn();
    }

    public boolean isLoggedInBefore() {
        EMSessionManager eMSessionManager = EMSessionManager.getInstance();
        String lastLoginUser = eMSessionManager.getLastLoginUser();
        if (TextUtils.isEmpty(lastLoginUser)) {
            return false;
        }
        String lastLoginPwd = eMSessionManager.getLastLoginPwd();
        String lastLoginToken = eMSessionManager.getLastLoginToken();
        if (lastLoginUser != null && !lastLoginUser.isEmpty()) {
            if (lastLoginPwd != null && !lastLoginPwd.isEmpty()) {
                return true;
            }
            if (lastLoginToken != null && !lastLoginToken.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSdkInited() {
        return this.sdkInited;
    }

    public void kickAllDevices(String str, String str2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.kickAllDevices(str, str2, eMAError);
        handleError(eMAError);
    }

    public void kickDevice(String str, String str2, String str3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.kickDevice(str, str2, str3, eMAError);
        handleError(eMAError);
    }

    public void login(String str, String str2, @NonNull EMCallBack eMCallBack) {
        if (str == null || str2 == null || str.equals("") || str2.equals("")) {
            eMCallBack.onError(110, "username or password is null or empty!");
            return;
        }
        if (TextUtils.isEmpty(getChatConfigPrivate().k())) {
            eMCallBack.onError(110, "please setup your App Key  either in AndroidManifest.xml or through the EMOptions");
        } else {
            if (!this.sdkInited) {
                eMCallBack.onError(1, "sdk not initialized");
                return;
            }
            String lowerCase = str.toLowerCase();
            initLoginWithAgoraData(false, "", 0L);
            _login(lowerCase, str2, eMCallBack, false, EMLoginType.LOGIN_PASSWORD);
        }
    }

    public void loginWithAgoraToken(String str, String str2, @NonNull EMCallBack eMCallBack) {
        if (TextUtils.isEmpty(getChatConfigPrivate().k())) {
            eMCallBack.onError(110, "please setup your App Key  either in AndroidManifest.xml or through the EMOptions");
            return;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            eMCallBack.onError(110, "username or agoraToken is null or empty!");
        } else if (this.sdkInited) {
            _login(str.toLowerCase(), str2, eMCallBack, false, EMLoginType.LOGIN_AGORA_TOKEN);
        } else {
            eMCallBack.onError(1, "sdk not initialized");
        }
    }

    public void loginWithToken(String str, String str2, @NonNull EMCallBack eMCallBack) {
        if (TextUtils.isEmpty(getChatConfigPrivate().k())) {
            eMCallBack.onError(110, "please setup your App Key  either in AndroidManifest.xml or through the EMOptions");
            return;
        }
        if (str == null || str2 == null || str.equals("") || str2.equals("")) {
            eMCallBack.onError(110, "username or token is null or empty!");
        } else {
            if (!this.sdkInited) {
                eMCallBack.onError(1, "sdk not initialized");
                return;
            }
            String lowerCase = str.toLowerCase();
            initLoginWithAgoraData(false, "", 0L);
            _login(lowerCase, str2, eMCallBack, false, EMLoginType.LOGIN_TOKEN);
        }
    }

    public int logout(boolean z2) {
        if (this.emaObject.isLogout()) {
            EMPushHelper.getInstance().unregister(false);
            EMLog.e(TAG, "already logout, skip unbind token");
        } else {
            String strQ = com.hyphenate.chat.core.a.a().q();
            String strR = com.hyphenate.chat.core.a.a().r();
            if (!TextUtils.isEmpty(strQ) && !TextUtils.isEmpty(strR)) {
                try {
                    pushManager().unBindDeviceToken();
                } catch (Exception unused) {
                    return 212;
                }
            } else if (!EMPushHelper.getInstance().unregister(z2)) {
                return 212;
            }
        }
        logout();
        return 0;
    }

    public void logout() {
        EMLog.d(TAG, " SDK Logout");
        try {
            BroadcastReceiver broadcastReceiver = this.connectivityBroadcastReceiver;
            if (broadcastReceiver != null) {
                this.mContext.unregisterReceiver(broadcastReceiver);
            }
        } catch (Exception unused) {
        }
        EMSessionManager.getInstance().clearLastLoginUser();
        EMSessionManager.getInstance().clearLastLoginToken();
        EMSessionManager.getInstance().clearLoginWithAgoraTokenData();
        EMSmartHeartBeat eMSmartHeartBeat = this.smartHeartbeat;
        if (eMSmartHeartBeat != null) {
            eMSmartHeartBeat.stop();
        }
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        EMAChatClient eMAChatClient = this.emaObject;
        if (eMAChatClient != null) {
            eMAChatClient.logout();
        }
        EMChatManager eMChatManager = this.chatManager;
        if (eMChatManager != null) {
            eMChatManager.onLogout();
        }
        EMGroupManager eMGroupManager = this.groupManager;
        if (eMGroupManager != null) {
            eMGroupManager.onLogout();
        }
        EMContactManager eMContactManager = this.contactManager;
        if (eMContactManager != null) {
            eMContactManager.onLogout();
        }
        EMChatRoomManager eMChatRoomManager = this.chatroomManager;
        if (eMChatRoomManager != null) {
            eMChatRoomManager.onLogout();
        }
        try {
            EMAdvanceDebugManager.a().f();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (EMChatConfigPrivate.a()) {
            com.hyphenate.a.a.c();
        }
    }

    public void logout(final EMCallBack eMCallBack) {
        Thread thread = new Thread() { // from class: com.hyphenate.chat.EMClient.5
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                EMCallBack eMCallBack2 = eMCallBack;
                if (eMCallBack2 != null) {
                    eMCallBack2.onProgress(0, null);
                }
                EMClient.this.logout();
                EMCallBack eMCallBack3 = eMCallBack;
                if (eMCallBack3 != null) {
                    eMCallBack3.onSuccess();
                }
            }
        };
        thread.setPriority(9);
        thread.start();
    }

    public void logout(final boolean z2, final EMCallBack eMCallBack) {
        new Thread() { // from class: com.hyphenate.chat.EMClient.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int iLogout = EMClient.this.logout(z2);
                if (iLogout != 0) {
                    EMCallBack eMCallBack2 = eMCallBack;
                    if (eMCallBack2 != null) {
                        eMCallBack2.onError(iLogout, "faild to unbind device token");
                        return;
                    }
                    return;
                }
                EMCallBack eMCallBack3 = eMCallBack;
                if (eMCallBack3 != null) {
                    eMCallBack3.onSuccess();
                }
            }
        }.start();
    }

    public void notifyTokenExpired(String str) {
        if (this.connectionListener == null || !this.mIsLoginWithAgoraToken) {
            return;
        }
        try {
            String strOptString = new JSONObject(str).optString("error_description");
            EMLog.e(TAG, "notifyTokenExpired--errorDescription:" + strOptString);
            if (strOptString.contains("milliseconds ago") || strOptString.contains("has expired") || strOptString.contains("Unable to authenticate due to expired access Token")) {
                this.connectionListener.onTokenNotification(401);
                EMLog.e(TAG, "notifyTokenExpired--onTokenNotification(401) ");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void onNetworkChanged() {
        try {
            if (!NetUtils.isWifiConnected(this.mContext) && !NetUtils.isOthersConnected(this.mContext)) {
                if (NetUtils.isMobileConnected(this.mContext)) {
                    EMLog.d(TAG, "has mobile connection");
                    EMAChatClient.EMANetwork eMANetwork = EMAChatClient.EMANetwork.NETWORK_MOBILE;
                    this.currentNetworkType = eMANetwork;
                    this.emaObject.onNetworkChanged(eMANetwork);
                    return;
                }
                if (NetUtils.isEthernetConnected(this.mContext)) {
                    EMLog.d(TAG, "has ethernet connection");
                    EMAChatClient.EMANetwork eMANetwork2 = EMAChatClient.EMANetwork.NETWORK_CABLE;
                    this.currentNetworkType = eMANetwork2;
                    this.emaObject.onNetworkChanged(eMANetwork2);
                    return;
                }
                EMAChatClient.EMANetwork eMANetwork3 = EMAChatClient.EMANetwork.NETWORK_NONE;
                this.currentNetworkType = eMANetwork3;
                EMLog.d(TAG, "no data connection");
                this.emaObject.onNetworkChanged(eMANetwork3);
                return;
            }
            EMLog.d(TAG, "has wifi connection");
            EMAChatClient.EMANetwork eMANetwork4 = EMAChatClient.EMANetwork.NETWORK_WIFI;
            this.currentNetworkType = eMANetwork4;
            this.emaObject.onNetworkChanged(eMANetwork4);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onNetworkChanged(EMAChatClient.EMANetwork eMANetwork) {
        this.emaObject.onNetworkChanged(eMANetwork);
    }

    public void onNewLogin() {
        EMLog.d(TAG, "on new login created");
        PathUtil.getInstance().initDirs(getChatConfigPrivate().k(), EMSessionManager.getInstance().getLastLoginUser(), this.mContext);
        EMAdvanceDebugManager.a().a(this.mChatConfigPrivate);
        if (this.smartHeartbeat == null) {
            this.smartHeartbeat = EMSmartHeartBeat.create(this.mContext);
        }
        if (getChatConfigPrivate().f8545a.hasHeartBeatCustomizedParams()) {
            this.smartHeartbeat.setCustomizedParams(getChatConfigPrivate().f8545a.getWifiHeartBeatCustomizedParams(), getChatConfigPrivate().f8545a.getMobileHeartBeatCustomizedParams());
        }
        this.smartHeartbeat.onInit();
        if (getOptions().getFixedInterval() != -1) {
            this.smartHeartbeat.setFixedInterval(getOptions().getFixedInterval());
        }
    }

    public EMPresenceManager presenceManager() {
        if (this.presenceManager == null) {
            synchronized (EMClient.class) {
                if (this.presenceManager == null) {
                    this.presenceManager = new EMPresenceManager(this.emaObject.getPresenceManager());
                }
            }
        }
        return this.presenceManager;
    }

    public EMPushManager pushManager() {
        if (this.pushManager == null) {
            synchronized (EMClient.class) {
                if (this.pushManager == null) {
                    this.pushManager = new EMPushManager(this, this.emaObject.getPushMnager());
                }
            }
        }
        return this.pushManager;
    }

    public void reconnect() {
        EMLog.d(TAG, "reconnect");
        this.wakeLock.acquire();
        this.emaObject.reconnect();
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
    }

    public void removeConnectionListener(EMConnectionListener eMConnectionListener) {
        if (eMConnectionListener == null) {
            return;
        }
        synchronized (this.connectionListeners) {
            this.connectionListeners.remove(eMConnectionListener);
        }
    }

    public void removeLogListener(EMLogListener eMLogListener) {
        if (eMLogListener == null) {
            return;
        }
        synchronized (this.logListeners) {
            this.logListeners.remove(eMLogListener);
        }
    }

    public void removeMultiDeviceListener(EMMultiDeviceListener eMMultiDeviceListener) {
        this.multiDeviceListeners.remove(eMMultiDeviceListener);
    }

    public void renewToken(final String str) {
        if (this.mIsLoginWithAgoraToken && this.emaObject.isLoggedIn()) {
            execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.3
                @Override // java.lang.Runnable
                public void run() {
                    EMClient eMClient = EMClient.this;
                    eMClient.getChatToken(eMClient.getCurrentUser(), str, new EMCallBack() { // from class: com.hyphenate.chat.EMClient.3.1
                        @Override // com.hyphenate.EMCallBack
                        public void onError(int i2, String str2) {
                        }

                        @Override // com.hyphenate.EMCallBack
                        public void onProgress(int i2, String str2) {
                        }

                        @Override // com.hyphenate.EMCallBack
                        public void onSuccess() {
                        }
                    });
                }
            });
        } else {
            EMLog.e(TAG, "the method  excepted to be called when login by agoraToken and login state is loggeIn");
        }
    }

    public void sendFCMTokenToServer(String str) {
        EMLog.d(TAG, "sendFCMTokenToServer: " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (TextUtils.isEmpty(getCurrentUser())) {
            EMLog.i(TAG, "No user login currently, stop upload the token.");
            return;
        }
        EMPushType pushType = EMPushHelper.getInstance().getPushType();
        EMLog.i(TAG, "pushType: " + pushType);
        if (pushType == EMPushType.FCM) {
            EMPushHelper.getInstance().onReceiveToken(pushType, str);
        }
    }

    public void sendHMSPushTokenToServer(String str) {
        EMPushType pushType = EMPushHelper.getInstance().getPushType();
        EMPushType eMPushType = EMPushType.HMSPUSH;
        if (pushType == eMPushType) {
            EMPushHelper.getInstance().onReceiveToken(eMPushType, str);
        }
    }

    public boolean sendPing(boolean z2, long j2) {
        return this.emaObject.sendPing(z2, j2);
    }

    public void setAppStateListener(AppStateListener appStateListener) {
        this.appStateListener = appStateListener;
    }

    public void setDebugMode(boolean z2) {
        String strE;
        if (this.sdkInited && (strE = EMAdvanceDebugManager.a().e()) != null) {
            z2 = Boolean.parseBoolean(strE);
        }
        EMLog.debugMode = z2;
        getChatConfigPrivate().c(z2);
    }

    public void setEncryptProvider(EMEncryptProvider eMEncryptProvider) {
        this.encryptProvider = eMEncryptProvider;
    }

    public void setNatvieNetworkCallback() {
        this.mChatConfigPrivate.f8545a.setNetCallback(new EMANetCallback() { // from class: com.hyphenate.chat.EMClient.9
            /* JADX WARN: Removed duplicated region for block: B:4:0x000c  */
            @Override // com.hyphenate.chat.adapter.EMANetCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public int getNetState() {
                /*
                    r1 = this;
                    com.hyphenate.chat.EMClient r0 = com.hyphenate.chat.EMClient.this
                    android.content.Context r0 = com.hyphenate.chat.EMClient.access$1000(r0)
                    boolean r0 = com.hyphenate.util.NetUtils.hasDataConnection(r0)
                    if (r0 != 0) goto L13
                Lc:
                    com.hyphenate.chat.adapter.EMAChatClient$EMANetwork r0 = com.hyphenate.chat.adapter.EMAChatClient.EMANetwork.NETWORK_NONE
                Le:
                    int r0 = r0.ordinal()
                    return r0
                L13:
                    com.hyphenate.chat.EMClient r0 = com.hyphenate.chat.EMClient.this
                    android.content.Context r0 = com.hyphenate.chat.EMClient.access$1000(r0)
                    boolean r0 = com.hyphenate.util.NetUtils.isWifiConnected(r0)
                    if (r0 != 0) goto L4a
                    com.hyphenate.chat.EMClient r0 = com.hyphenate.chat.EMClient.this
                    android.content.Context r0 = com.hyphenate.chat.EMClient.access$1000(r0)
                    boolean r0 = com.hyphenate.util.NetUtils.isOthersConnected(r0)
                    if (r0 == 0) goto L2c
                    goto L4a
                L2c:
                    com.hyphenate.chat.EMClient r0 = com.hyphenate.chat.EMClient.this
                    android.content.Context r0 = com.hyphenate.chat.EMClient.access$1000(r0)
                    boolean r0 = com.hyphenate.util.NetUtils.isMobileConnected(r0)
                    if (r0 == 0) goto L3b
                    com.hyphenate.chat.adapter.EMAChatClient$EMANetwork r0 = com.hyphenate.chat.adapter.EMAChatClient.EMANetwork.NETWORK_MOBILE
                    goto Le
                L3b:
                    com.hyphenate.chat.EMClient r0 = com.hyphenate.chat.EMClient.this
                    android.content.Context r0 = com.hyphenate.chat.EMClient.access$1000(r0)
                    boolean r0 = com.hyphenate.util.NetUtils.isEthernetConnected(r0)
                    if (r0 == 0) goto Lc
                    com.hyphenate.chat.adapter.EMAChatClient$EMANetwork r0 = com.hyphenate.chat.adapter.EMAChatClient.EMANetwork.NETWORK_CABLE
                    goto Le
                L4a:
                    com.hyphenate.chat.adapter.EMAChatClient$EMANetwork r0 = com.hyphenate.chat.adapter.EMAChatClient.EMANetwork.NETWORK_WIFI
                    goto Le
                */
                throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMClient.AnonymousClass9.getNetState():int");
            }
        });
    }

    public void setPresence(final String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        execute(new Runnable() { // from class: com.hyphenate.chat.EMClient.13
            @Override // java.lang.Runnable
            public void run() {
                EMClient.this.emaObject.setPresence(str);
            }
        });
    }

    public EMTranslationManager translationManager() {
        if (this.translationManager == null) {
            synchronized (EMClient.class) {
                if (this.translationManager == null) {
                    this.translationManager = new EMTranslationManager(this.emaObject.getTranslateManager());
                }
            }
        }
        return this.translationManager;
    }

    public void uploadLog(EMCallBack eMCallBack) {
        chatManager().emaObject.uploadLog();
    }

    public EMUserInfoManager userInfoManager() {
        if (this.userInfoManager == null) {
            synchronized (EMClient.class) {
                if (this.userInfoManager == null) {
                    this.userInfoManager = new EMUserInfoManager(this.emaObject.getUserInfoManager());
                }
            }
        }
        return this.userInfoManager;
    }
}
