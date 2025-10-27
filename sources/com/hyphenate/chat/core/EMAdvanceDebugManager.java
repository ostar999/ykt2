package com.hyphenate.chat.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import java.io.File;

/* loaded from: classes4.dex */
public class EMAdvanceDebugManager {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8525a = "EMAdvanceDebugManager";

    /* renamed from: b, reason: collision with root package name */
    private static String f8526b;

    /* renamed from: c, reason: collision with root package name */
    private static EMAdvanceDebugManager f8527c;

    /* renamed from: d, reason: collision with root package name */
    private BroadcastReceiver f8528d = null;

    /* renamed from: f, reason: collision with root package name */
    private EMChatConfigPrivate f8530f = null;

    /* renamed from: e, reason: collision with root package name */
    private Context f8529e = EMClient.getInstance().getContext();

    /* renamed from: com.hyphenate.chat.core.EMAdvanceDebugManager$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8534a;

        static {
            int[] iArr = new int[Type.values().length];
            f8534a = iArr;
            try {
                iArr[Type.em_retrieve_dns.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8534a[Type.em_upload_dns.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f8534a[Type.em_start_debug.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f8534a[Type.em_stop_debug.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f8534a[Type.em_upload_log.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f8534a[Type.em_print_user.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f8534a[Type.em_change_appkey.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f8534a[Type.em_change_servers.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f8534a[Type.em_dump_db.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public enum Type {
        em_retrieve_dns,
        em_upload_dns,
        em_start_debug,
        em_stop_debug,
        em_upload_log,
        em_print_user,
        em_change_appkey,
        em_change_servers,
        em_dump_db
    }

    private EMAdvanceDebugManager() {
        f8526b = this.f8529e.getPackageName() + ".debug.ipc.cmd";
    }

    public static synchronized EMAdvanceDebugManager a() {
        if (f8527c == null) {
            f8527c = new EMAdvanceDebugManager();
        }
        return f8527c;
    }

    private void h() {
        if (this.f8528d == null) {
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.hyphenate.chat.core.EMAdvanceDebugManager.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    Type typeValueOf;
                    if (intent.getAction().equals(EMAdvanceDebugManager.f8526b)) {
                        try {
                            typeValueOf = Type.valueOf(intent.getStringExtra("action"));
                        } catch (Exception unused) {
                            typeValueOf = null;
                        }
                        if (typeValueOf == null) {
                            EMLog.e(EMAdvanceDebugManager.f8525a, "unknow cmd action");
                            return;
                        }
                        EMMessage eMMessageCreateReceiveMessage = EMMessage.createReceiveMessage(EMMessage.Type.CMD);
                        if (intent.getStringExtra("appkey") != null) {
                            eMMessageCreateReceiveMessage.setAttribute("appkey", intent.getStringExtra("appkey"));
                        }
                        if (intent.getStringExtra("im_server") != null) {
                            eMMessageCreateReceiveMessage.setAttribute("im_server", intent.getStringExtra("im_server"));
                        }
                        if (intent.getStringExtra("rest_server") != null) {
                            eMMessageCreateReceiveMessage.setAttribute("rest_server", intent.getStringExtra("rest_server"));
                        }
                        if (intent.getBooleanExtra("enable_dns", false)) {
                            eMMessageCreateReceiveMessage.setAttribute("enable_dns", true);
                        }
                        EMAdvanceDebugManager.this.a(eMMessageCreateReceiveMessage, typeValueOf);
                    }
                }
            };
            this.f8528d = broadcastReceiver;
            this.f8529e.registerReceiver(broadcastReceiver, new IntentFilter(f8526b));
        }
    }

    public void a(EMMessage eMMessage, Type type) {
        String str;
        Intent intent;
        String str2;
        switch (AnonymousClass4.f8534a[type.ordinal()]) {
            case 1:
                new Thread(new Runnable() { // from class: com.hyphenate.chat.core.EMAdvanceDebugManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        EMLog.d(EMAdvanceDebugManager.f8525a, "retrieve_dns");
                        EMAdvanceDebugManager.this.f8530f.g();
                    }
                }).start();
                break;
            case 2:
                str = "upload dns";
                EMLog.d(f8525a, str);
                break;
            case 3:
                a(true);
                EMClient.getInstance().setDebugMode(true);
                str = "debugmode set to true";
                EMLog.d(f8525a, str);
                break;
            case 4:
                a(false);
                EMLog.d(f8525a, "debugmode set to false");
                EMClient.getInstance().setDebugMode(false);
                break;
            case 5:
                this.f8530f.a(new EMCallBack() { // from class: com.hyphenate.chat.core.EMAdvanceDebugManager.3
                    @Override // com.hyphenate.EMCallBack
                    public void onError(int i2, String str3) {
                        EMLog.d(EMAdvanceDebugManager.f8525a, "upload log fail, error: " + str3);
                    }

                    @Override // com.hyphenate.EMCallBack
                    public void onProgress(int i2, String str3) {
                    }

                    @Override // com.hyphenate.EMCallBack
                    public void onSuccess() {
                        EMLog.d(EMAdvanceDebugManager.f8525a, "upload log success");
                    }
                });
                break;
            case 6:
                boolean z2 = EMLog.debugMode;
                if (!z2) {
                    EMLog.debugMode = true;
                }
                EMLog.d(f8525a, " usename : " + EMClient.getInstance().getCurrentUser() + "\r\n appkey  : " + this.f8530f.k() + "\r\n SDK     : " + this.f8530f.e());
                EMLog.debugMode = z2;
                break;
            case 7:
                String stringAttribute = eMMessage.getStringAttribute("appkey", null);
                EMLog.d(f8525a, "received change appkey cmd, appkey: " + stringAttribute);
                if (stringAttribute != null) {
                    a(stringAttribute);
                    intent = new Intent(this.f8529e.getPackageName() + ".em_internal_debug");
                    str2 = "change_appkey";
                    intent.putExtra("debug_action", str2);
                    this.f8529e.sendBroadcast(intent);
                    break;
                }
                break;
            case 8:
                String stringAttribute2 = eMMessage.getStringAttribute("im_server", null);
                String stringAttribute3 = eMMessage.getStringAttribute("rest_server", null);
                if (!eMMessage.getBooleanAttribute("enable_dns", false)) {
                    EMLog.d(f8525a, "change servers to " + stringAttribute2 + " and " + stringAttribute3);
                    if (stringAttribute2 != null && stringAttribute3 != null) {
                        this.f8530f.a(false);
                        this.f8530f.c(stringAttribute2);
                        this.f8530f.d(stringAttribute3);
                        a(stringAttribute2, stringAttribute3);
                        if (stringAttribute2.contains(":")) {
                            this.f8530f.c(stringAttribute2.split(":")[0]);
                            this.f8530f.a(Integer.valueOf(stringAttribute2.split(":")[1]).intValue());
                        }
                    }
                } else if (!this.f8530f.h()) {
                    this.f8530f.a(true);
                    a((String) null, (String) null);
                }
                intent = new Intent(this.f8529e.getPackageName() + ".em_internal_debug");
                str2 = "change_servers";
                intent.putExtra("debug_action", str2);
                this.f8529e.sendBroadcast(intent);
                break;
            case 9:
                String absolutePath = this.f8529e.getFilesDir().getAbsolutePath();
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                if (externalStorageDirectory.exists()) {
                    EasyUtils.copyFolder(absolutePath + "/easemobDB", externalStorageDirectory + "/easemobDB");
                    break;
                }
                break;
        }
    }

    public void a(EMChatConfigPrivate eMChatConfigPrivate) {
        this.f8530f = eMChatConfigPrivate;
        h();
    }

    public void a(String str) {
        a.a().c(str);
    }

    public void a(String str, String str2) {
        a.a().a(str, str2);
    }

    public void a(boolean z2) {
        a.a().a(z2);
    }

    public String b() {
        return a.a().i();
    }

    public String c() {
        return a.a().j();
    }

    public String d() {
        return a.a().k();
    }

    public String e() {
        return a.a().l();
    }

    public void f() {
    }
}
