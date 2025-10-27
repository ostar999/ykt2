package com.hyphenate.chat.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.adapter.EMACallback;
import com.hyphenate.chat.adapter.EMAChatConfig;
import com.hyphenate.chat.adapter.message.EMAMessage;
import com.hyphenate.util.EMLog;
import java.io.File;
import java.util.List;

/* loaded from: classes4.dex */
public class EMChatConfigPrivate {

    /* renamed from: b, reason: collision with root package name */
    static final String f8535b = "uuid";

    /* renamed from: c, reason: collision with root package name */
    static final String f8536c = "share-secret";

    /* renamed from: d, reason: collision with root package name */
    static final String f8537d = "entities";

    /* renamed from: f, reason: collision with root package name */
    private static final String f8538f = "conf";

    /* renamed from: g, reason: collision with root package name */
    private static final String f8539g = "EASEMOB_APPKEY";

    /* renamed from: h, reason: collision with root package name */
    private static final String f8540h = "EASEMOB_CHAT_ADDRESS";

    /* renamed from: i, reason: collision with root package name */
    private static final String f8541i = "EASEMOB_CHAT_DOMAIN";

    /* renamed from: j, reason: collision with root package name */
    private static final String f8542j = "EASEMOB_GROUP_DOMAIN";

    /* renamed from: k, reason: collision with root package name */
    private static final String f8543k = "EASEMOB_CHAT_PORT";

    /* renamed from: l, reason: collision with root package name */
    private static final String f8544l = "EASEMOB_API_URL";

    /* renamed from: n, reason: collision with root package name */
    private String f8548n;

    /* renamed from: p, reason: collision with root package name */
    private String f8550p;

    /* renamed from: q, reason: collision with root package name */
    private String f8551q;

    /* renamed from: r, reason: collision with root package name */
    private String f8552r;

    /* renamed from: s, reason: collision with root package name */
    private String f8553s;

    /* renamed from: t, reason: collision with root package name */
    private EMOptions f8554t;

    /* renamed from: m, reason: collision with root package name */
    private String f8547m = null;

    /* renamed from: o, reason: collision with root package name */
    private int f8549o = -1;

    /* renamed from: u, reason: collision with root package name */
    private Context f8555u = null;

    /* renamed from: e, reason: collision with root package name */
    boolean f8546e = false;

    /* renamed from: a, reason: collision with root package name */
    public EMAChatConfig f8545a = new EMAChatConfig();

    public enum EMEnvMode {
        EMSandboxMode,
        EMProductMode,
        EMDevMode
    }

    public enum EMSDKMode {
        EMChatMode,
        EMHelpDeskMode
    }

    private void J() {
        try {
            String strE = EMAdvanceDebugManager.a().e();
            if (strE != null) {
                EMLog.debugMode = Boolean.parseBoolean(strE);
            }
            if (EMAdvanceDebugManager.a().d() != null) {
                this.f8547m = EMAdvanceDebugManager.a().d();
            }
            String strB = EMAdvanceDebugManager.a().b();
            String strC = EMAdvanceDebugManager.a().c();
            if (strB == null || strC == null) {
                return;
            }
            if (strB.contains(":")) {
                this.f8549o = Integer.valueOf(strB.split(":")[1]).intValue();
                strB = strB.split(":")[0];
            }
            this.f8548n = strB;
            this.f8550p = strC;
            this.f8546e = true;
        } catch (Exception unused) {
        }
    }

    private void K() {
        EMLog.d(f8538f, " APPKEY:" + this.f8547m + " CHATSERVER:" + this.f8545a.getChatAddress());
        StringBuilder sb = new StringBuilder();
        sb.append("STORAGE_URL:");
        sb.append(this.f8545a.getRestServer());
        EMLog.d(f8538f, sb.toString());
        EMLog.d(f8538f, "RTCSERVER: " + this.f8551q);
    }

    private void a(EMOptions eMOptions) {
        this.f8554t = eMOptions;
        this.f8545a.setRequireReadAck(eMOptions.getRequireAck());
        this.f8545a.setRequireDeliveryAck(eMOptions.getRequireDeliveryAck());
        this.f8545a.setAutoAccept(eMOptions.getAcceptInvitationAlways());
        this.f8545a.setDeleteMessageAsExitGroup(eMOptions.isDeleteMessagesAsExitGroup());
        this.f8545a.setIsChatroomOwnerLeaveAllowed(eMOptions.isChatroomOwnerLeaveAllowed());
        this.f8545a.setDeleteMessageAsExitChatRoom(eMOptions.isDeleteMessagesAsExitChatRoom());
        this.f8545a.setAutoAcceptGroupInvitation(eMOptions.isAutoAcceptGroupInvitation());
        this.f8545a.enableDnsConfig(eMOptions.getEnableDNSConfig());
        this.f8545a.setSortMessageByServerTime(eMOptions.isSortMessageByServerTime());
        this.f8545a.setUsingHttpsOnly(eMOptions.getUsingHttpsOnly());
        this.f8545a.setTransferAttachments(eMOptions.getAutoTransferMessageAttachments());
        this.f8545a.setAutodownloadThumbnail(eMOptions.getAutodownloadThumbnail());
        if (eMOptions.getDnsUrl() != null && !eMOptions.getDnsUrl().isEmpty()) {
            this.f8545a.setDnsUrl(eMOptions.getDnsUrl());
        }
        if (eMOptions.getRestServer() != null && eMOptions.getImServer() != null) {
            this.f8545a.enableDnsConfig(false);
            this.f8550p = eMOptions.getRestServer();
            this.f8548n = eMOptions.getImServer();
            if (eMOptions.getImPort() > 0) {
                this.f8549o = eMOptions.getImPort();
            }
        }
        this.f8545a.setFpaEnable(eMOptions.getFpaEnable());
        this.f8545a.setAreaCode(eMOptions.getAreaCode());
    }

    public static boolean a() {
        return false;
    }

    public boolean A() {
        return this.f8545a.getTransferAttachments();
    }

    public boolean B() {
        return this.f8545a.getAutodownloadThumbnail();
    }

    public boolean C() {
        return this.f8545a.getUseRtcConfig();
    }

    public String D() {
        return this.f8545a.getRtcConfigUrl();
    }

    public String E() {
        return this.f8545a.getDownloadPath();
    }

    public boolean F() {
        return this.f8545a.getFpaEnable();
    }

    public String G() {
        return this.f8545a.getDnsUrl();
    }

    public boolean H() {
        return this.f8545a.getUsingSQLCipher();
    }

    public boolean I() {
        return this.f8545a.isNewLoginOnDevice();
    }

    public String a(boolean z2, boolean z3) {
        return this.f8545a.getBaseUrl(z2, z3);
    }

    public void a(int i2) {
        this.f8545a.setChatPort(i2);
    }

    public void a(EMCallBack eMCallBack) {
        this.f8545a.uploadLog(new EMACallback(eMCallBack));
    }

    public void a(EMSDKMode eMSDKMode) {
    }

    public void a(String str) {
        String str2;
        Context context = this.f8555u;
        if (context == null) {
            return;
        }
        String absolutePath = context.getFilesDir().getAbsolutePath();
        File externalFilesDir = this.f8555u.getExternalFilesDir(null);
        if (externalFilesDir != null && externalFilesDir.exists() && externalFilesDir.canWrite()) {
            String absolutePath2 = externalFilesDir.getAbsolutePath();
            String strSubstring = absolutePath2.substring(0, absolutePath2.indexOf("/files"));
            String str3 = strSubstring + "/" + str + "/core_log";
            String str4 = strSubstring + "/" + str + "/files";
            new File(str3).mkdirs();
            new File(str4).mkdirs();
            str2 = str4;
            absolutePath = str3;
        } else {
            str2 = absolutePath;
        }
        this.f8545a.setLogPath(absolutePath);
        this.f8545a.setDownloadPath(str2);
    }

    public void a(String str, int i2) {
        this.f8545a.updateConversationUnreadCount(str, i2);
    }

    public void a(String str, int i2, String str2) {
        this.f8545a.importConversation(str, i2, str2);
    }

    public void a(String str, int i2, String str2, String str3, String str4, List<String> list, boolean z2, int i3) {
        this.f8545a.importGroup(str, i2, str2, str3, str4, list, z2, i3);
    }

    public void a(String str, String str2, String str3, String str4, List<String> list, int i2) {
        this.f8545a.importChatRoom(str, str2, str3, str4, list, i2);
    }

    public void a(List<String> list) {
        this.f8545a.importBlackList(list);
    }

    public void a(boolean z2) {
        this.f8545a.enableDnsConfig(z2);
    }

    public boolean a(Context context, EMOptions eMOptions) {
        ApplicationInfo applicationInfo;
        String absolutePath = context.getFilesDir().getAbsolutePath();
        this.f8555u = context;
        String appKey = null;
        try {
            applicationInfo = this.f8555u.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e2) {
            EMLog.e(f8538f, e2.getMessage());
            EMLog.e(f8538f, "找不到ApplicationInfo");
            applicationInfo = null;
        }
        if (eMOptions != null && !TextUtils.isEmpty(eMOptions.getAppKey())) {
            appKey = eMOptions.getAppKey();
        }
        this.f8547m = appKey;
        if (applicationInfo != null) {
            Bundle bundle = applicationInfo.metaData;
            if (bundle == null) {
                EMLog.w(f8538f, "请确认meta属性写在清单文件里的application节点以内");
            } else {
                String string = bundle.getString(f8539g);
                if (string == null && this.f8547m == null) {
                    Log.e(f8538f, "EASEMOB_APPKEY is not set in AndroidManifest file");
                    System.exit(-1);
                } else if (TextUtils.isEmpty(this.f8547m)) {
                    this.f8547m = string;
                }
                String string2 = bundle.getString(f8540h);
                if (string2 != null) {
                    this.f8548n = string2;
                }
                int i2 = bundle.getInt(f8543k, -1);
                if (i2 != -1) {
                    this.f8549o = i2;
                }
                String string3 = bundle.getString(f8544l);
                if (string3 != null) {
                    this.f8550p = string3;
                }
                String string4 = bundle.getString(f8541i);
                if (string4 != null) {
                    this.f8552r = string4;
                }
                String string5 = bundle.getString(f8542j);
                if (string5 != null) {
                    this.f8553s = string5;
                }
            }
        }
        this.f8545a.init(absolutePath, absolutePath, this.f8547m);
        a(eMOptions);
        J();
        a(this.f8547m);
        EMLog.i(f8538f, "EASEMOB_APPKEY is set to:" + this.f8547m);
        String str = this.f8548n;
        if (str != null && !str.equals("")) {
            this.f8545a.setChatServer(this.f8548n);
        }
        String str2 = this.f8550p;
        if (str2 != null && !str2.equals("")) {
            this.f8545a.setRestServer(this.f8550p);
        }
        String str3 = this.f8551q;
        if (str3 != null && !str3.equals("")) {
            this.f8545a.setRtcServer(this.f8551q);
        }
        String str4 = this.f8552r;
        if (str4 != null && !str4.equals("")) {
            this.f8545a.setChatDomain(this.f8552r);
        }
        String str5 = this.f8553s;
        if (str5 != null && !str5.equals("")) {
            this.f8545a.setGroupDomain(this.f8553s);
        }
        int i3 = this.f8549o;
        if (i3 != -1) {
            this.f8545a.setChatPort(i3);
        }
        if (this.f8546e) {
            this.f8545a.enableDnsConfig(false);
        }
        this.f8545a.setSDKVersion(EMClient.VERSION);
        try {
            this.f8545a.setOSVersion(Build.VERSION.RELEASE);
        } catch (Exception e3) {
            EMLog.e(f8538f, e3.getMessage());
        }
        this.f8545a.setAppId(context.getPackageName());
        K();
        return true;
    }

    public EMOptions b() {
        return this.f8554t;
    }

    public String b(boolean z2) {
        return this.f8545a.getAccessToken(z2);
    }

    public void b(int i2) {
        this.f8545a.setAreaCode(i2);
    }

    public void b(List<String> list) {
        this.f8545a.importContacts(list);
    }

    public boolean b(String str) {
        return this.f8545a.openDatabase(str);
    }

    public EMSDKMode c() {
        return EMSDKMode.EMChatMode;
    }

    public void c(String str) {
        this.f8545a.setChatServer(str);
    }

    public void c(List<EMAMessage> list) {
        this.f8545a.importMessages(list);
    }

    public void c(boolean z2) {
        this.f8545a.setDebugMode(z2);
    }

    public EMEnvMode d() {
        return this.f8545a.getIsSandboxMode() ? EMEnvMode.EMSandboxMode : EMEnvMode.EMProductMode;
    }

    public void d(String str) {
        this.f8545a.setRestServer(str);
    }

    public void d(boolean z2) {
        this.f8545a.setRequireDeliveryAck(z2);
    }

    public String e() {
        return EMClient.VERSION;
    }

    public void e(String str) {
        this.f8545a.setRtcConfigUrl(str);
    }

    public void e(boolean z2) {
        this.f8545a.setRequireReadAck(z2);
    }

    public void f(String str) {
        this.f8545a.setDeviceUuid(str);
    }

    public void f(boolean z2) {
        this.f8545a.setAutoAccept(z2);
    }

    public boolean f() {
        return this.f8545a.useHttps();
    }

    public void g() {
        this.f8545a.retrieveDNSConfig();
    }

    public void g(String str) {
        this.f8545a.setDid(str);
    }

    public void g(boolean z2) {
        this.f8545a.setDeleteMessageAsExitGroup(z2);
    }

    public void h(String str) {
        this.f8545a.setServiceId(str);
    }

    public void h(boolean z2) {
        this.f8545a.setAutoAcceptGroupInvitation(z2);
    }

    public boolean h() {
        return this.f8545a.isEnableDnsConfig();
    }

    public void i(String str) {
        this.f8545a.setDeviceName(str);
    }

    public void i(boolean z2) {
        this.f8545a.setIsChatroomOwnerLeaveAllowed(z2);
    }

    public boolean i() {
        return this.f8545a.isGcmEnabled();
    }

    public String j() {
        return this.f8545a.getRestServer();
    }

    public void j(String str) {
        this.f8545a.setDnsUrl(str);
    }

    public void j(boolean z2) {
        this.f8545a.setDeleteMessageAsExitChatRoom(z2);
    }

    public String k() {
        return this.f8545a.getAppKey();
    }

    public void k(boolean z2) {
        this.f8545a.setSortMessageByServerTime(z2);
    }

    public String l() {
        return this.f8545a.getNextAvailableBaseUrl();
    }

    public void l(boolean z2) {
        this.f8545a.setUseHttps(z2);
    }

    public String m() {
        return this.f8545a.getAccessToken();
    }

    public void m(boolean z2) {
        this.f8545a.setUsingHttpsOnly(z2);
    }

    public long n() {
        return this.f8545a.getTokenSaveTime();
    }

    public void n(boolean z2) {
        this.f8545a.setTransferAttachments(z2);
    }

    public void o(boolean z2) {
        this.f8545a.setAutodownloadThumbnail(z2);
    }

    public boolean o() {
        return this.f8545a.getRequireDeliveryAck();
    }

    public void p(boolean z2) {
        this.f8545a.setUseRtcConfig(z2);
    }

    public boolean p() {
        return this.f8545a.getRequireReadAck();
    }

    public void q(boolean z2) {
        this.f8545a.setFpaEnable(z2);
    }

    public boolean q() {
        return this.f8545a.getAutoAccept();
    }

    public boolean r() {
        return this.f8545a.getDeleteMessageAsExitGroup();
    }

    public boolean s() {
        return this.f8545a.getAutoAcceptGroupInvitation();
    }

    public boolean t() {
        return this.f8545a.getIsChatroomOwnerLeaveAllowed();
    }

    public boolean u() {
        return this.f8545a.getDeleteMessageAsExitChatRoom();
    }

    public void v() {
        this.f8545a.reloadAll();
    }

    public boolean w() {
        return this.f8545a.getSortMessageByServerTime();
    }

    public String x() {
        return this.f8545a.getGaoDeDiscoverKey();
    }

    public String y() {
        return this.f8545a.getGaoDeLocationKey();
    }

    public boolean z() {
        return this.f8545a.getUsingHttpsOnly();
    }
}
