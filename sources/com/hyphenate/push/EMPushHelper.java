package com.hyphenate.push;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.hyphenate.chat.EMClient;
import com.hyphenate.cloud.EMHttpClient;
import com.hyphenate.push.platform.a;
import com.hyphenate.util.DeviceUuidFactory;
import com.hyphenate.util.EMLog;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EMPushHelper {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8851a = "EMPushHelper";

    /* renamed from: b, reason: collision with root package name */
    private static final int f8852b = 3;

    /* renamed from: c, reason: collision with root package name */
    private static final int f8853c = 0;

    /* renamed from: d, reason: collision with root package name */
    private static final int f8854d = 1;

    /* renamed from: e, reason: collision with root package name */
    private Context f8855e;

    /* renamed from: f, reason: collision with root package name */
    private EMPushConfig f8856f;

    /* renamed from: g, reason: collision with root package name */
    private a f8857g;

    /* renamed from: h, reason: collision with root package name */
    private Handler f8858h;

    /* renamed from: i, reason: collision with root package name */
    private EMPushType f8859i;

    /* renamed from: j, reason: collision with root package name */
    private String f8860j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f8861k;

    /* renamed from: l, reason: collision with root package name */
    private final Object f8862l;

    /* renamed from: m, reason: collision with root package name */
    private final Object f8863m;

    /* renamed from: n, reason: collision with root package name */
    private PushListener f8864n;

    /* renamed from: o, reason: collision with root package name */
    private boolean f8865o;

    /* renamed from: com.hyphenate.push.EMPushHelper$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8867a;

        static {
            int[] iArr = new int[EMPushType.values().length];
            f8867a = iArr;
            try {
                iArr[EMPushType.FCM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8867a[EMPushType.MIPUSH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f8867a[EMPushType.OPPOPUSH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f8867a[EMPushType.VIVOPUSH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f8867a[EMPushType.MEIZUPUSH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f8867a[EMPushType.HMSPUSH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f8867a[EMPushType.NORMAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static class InstanceHolder {

        /* renamed from: a, reason: collision with root package name */
        static EMPushHelper f8868a = new EMPushHelper();

        private InstanceHolder() {
        }
    }

    private EMPushHelper() {
        this.f8862l = new Object();
        this.f8863m = new Object();
        this.f8865o = false;
        HandlerThread handlerThread = new HandlerThread("token-uploader");
        handlerThread.start();
        this.f8858h = new Handler(handlerThread.getLooper()) { // from class: com.hyphenate.push.EMPushHelper.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 != 0) {
                    if (i2 != 1) {
                        super.handleMessage(message);
                        return;
                    }
                    EMPushHelper eMPushHelper = EMPushHelper.this;
                    eMPushHelper.f8861k = eMPushHelper.a(eMPushHelper.f8857g.a(), "");
                    if (!EMPushHelper.this.f8861k) {
                        EMPushHelper eMPushHelper2 = EMPushHelper.this;
                        eMPushHelper2.onErrorResponse(eMPushHelper2.f8859i, 1502L);
                    }
                    synchronized (EMPushHelper.this.f8863m) {
                        EMPushHelper.this.f8863m.notifyAll();
                    }
                    return;
                }
                synchronized (EMPushHelper.this.f8862l) {
                    String str = (String) message.obj;
                    EMPushHelper eMPushHelper3 = EMPushHelper.this;
                    if (eMPushHelper3.a(eMPushHelper3.f8857g.a(), str)) {
                        removeMessages(0);
                        return;
                    }
                    if (!hasMessages(0)) {
                        EMPushHelper eMPushHelper4 = EMPushHelper.this;
                        eMPushHelper4.onErrorResponse(eMPushHelper4.f8859i, 1501L);
                        EMPushHelper.this.a(EMPushType.NORMAL);
                    }
                }
            }
        };
    }

    private EMPushType a(EMPushConfig eMPushConfig) {
        EMPushType[] eMPushTypeArr = {EMPushType.FCM, EMPushType.MIPUSH, EMPushType.HMSPUSH, EMPushType.MEIZUPUSH, EMPushType.OPPOPUSH, EMPushType.VIVOPUSH};
        ArrayList<EMPushType> enabledPushTypes = eMPushConfig.getEnabledPushTypes();
        for (int i2 = 0; i2 < 6; i2++) {
            EMPushType eMPushType = eMPushTypeArr[i2];
            if (enabledPushTypes.contains(eMPushType) && a(eMPushType, eMPushConfig)) {
                return eMPushType;
            }
        }
        return EMPushType.NORMAL;
    }

    private void a() {
        this.f8858h.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull EMPushType eMPushType) {
        a aVar;
        if (this.f8859i == eMPushType) {
            EMLog.e(f8851a, "Push type " + eMPushType + " no change, return. ");
            return;
        }
        if (this.f8857g != null) {
            EMLog.e(f8851a, this.f8857g.b() + " push already exists, unregister it and change to " + eMPushType + " push.");
            this.f8857g.a(this.f8855e);
        }
        this.f8859i = eMPushType;
        switch (AnonymousClass2.f8867a[eMPushType.ordinal()]) {
            case 1:
                aVar = new com.hyphenate.push.platform.a.a();
                break;
            case 2:
                aVar = new com.hyphenate.push.platform.mi.a();
                break;
            case 3:
                aVar = new com.hyphenate.push.platform.oppo.a();
                break;
            case 4:
                aVar = new com.hyphenate.push.platform.vivo.a();
                break;
            case 5:
                aVar = new com.hyphenate.push.platform.meizu.a();
                break;
            case 6:
                aVar = new com.hyphenate.push.platform.b.a();
                break;
            default:
                aVar = new com.hyphenate.push.platform.c.a();
                break;
        }
        this.f8857g = aVar;
        this.f8865o = true;
        this.f8857g.a(this.f8855e, this.f8856f, this.f8864n);
    }

    private void a(String str) {
        this.f8858h.removeMessages(0);
        synchronized (this.f8862l) {
            for (int i2 = -1; i2 < 3; i2++) {
                Message messageObtainMessage = this.f8858h.obtainMessage(0, str);
                if (i2 == -1) {
                    this.f8858h.sendMessage(messageObtainMessage);
                } else {
                    int iRandomDelay = randomDelay(i2);
                    EMLog.i(f8851a, "Retry upload after " + iRandomDelay + "s if failed.");
                    this.f8858h.sendMessageDelayed(messageObtainMessage, (long) (iRandomDelay * 1000));
                }
            }
        }
    }

    private boolean a(EMPushType eMPushType, EMPushConfig eMPushConfig) {
        PushListener pushListener = this.f8864n;
        boolean zIsSupportPush = pushListener != null ? pushListener.isSupportPush(eMPushType, eMPushConfig) : com.hyphenate.push.a.a.a(eMPushType, eMPushConfig);
        EMLog.i(f8851a, "isSupportPush: " + eMPushType + " - " + zIsSupportPush);
        return zIsSupportPush;
    }

    private boolean a(EMPushType eMPushType, String str) {
        String pushTokenWithType = getInstance().getPushTokenWithType(eMPushType);
        if (pushTokenWithType != null && pushTokenWithType.equals(str)) {
            return false;
        }
        getInstance().setPushTokenWithType(eMPushType, str);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str, String str2) throws JSONException {
        int iIntValue;
        String str3;
        String str4 = EMClient.getInstance().getChatConfigPrivate().a(true, false) + "/users/" + EMClient.getInstance().getCurrentUser();
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(EMClient.getInstance().getContext());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(RemoteMessageConst.DEVICE_TOKEN, str2);
            jSONObject.put("notifier_name", str);
            jSONObject.put("device_id", deviceUuidFactory.getDeviceUuid().toString());
        } catch (Exception e2) {
            EMLog.e(f8851a, "uploadTokenInternal put json exception: " + e2.toString());
        }
        int i2 = 2;
        do {
            try {
                EMLog.e(f8851a, "uploadTokenInternal, token=" + str2 + ", url=" + str4 + ", notifier name=" + str);
                Pair<Integer, String> pairSendRequestWithToken = EMHttpClient.getInstance().sendRequestWithToken(str4, jSONObject.toString(), EMHttpClient.PUT);
                iIntValue = ((Integer) pairSendRequestWithToken.first).intValue();
                str3 = (String) pairSendRequestWithToken.second;
            } catch (Exception e3) {
                EMLog.e(f8851a, "uploadTokenInternal exception: " + e3.toString());
                str4 = EMClient.getInstance().getChatConfigPrivate().a(true, true) + "/users/" + EMClient.getInstance().getCurrentUser();
            }
            if (iIntValue == 200) {
                EMLog.e(f8851a, "uploadTokenInternal success.");
                return true;
            }
            EMLog.e(f8851a, "uploadTokenInternal failed: " + str3);
            str4 = EMClient.getInstance().getChatConfigPrivate().a(true, true) + "/users/" + EMClient.getInstance().getCurrentUser();
            i2--;
        } while (i2 > 0);
        return false;
    }

    public static EMPushHelper getInstance() {
        return InstanceHolder.f8868a;
    }

    public String getFCMPushToken() {
        return com.hyphenate.chat.core.a.a().m();
    }

    public String getPushToken() {
        return this.f8860j;
    }

    public String getPushTokenWithType(EMPushType eMPushType) {
        return com.hyphenate.chat.core.a.a().a(eMPushType);
    }

    public EMPushType getPushType() {
        return this.f8859i;
    }

    public void init(Context context, EMPushConfig eMPushConfig) {
        EMLog.e(f8851a, "EMPushHelper init, config: " + eMPushConfig.toString());
        if (context != null) {
            this.f8855e = context.getApplicationContext();
            this.f8856f = eMPushConfig;
            return;
        }
        throw new IllegalArgumentException("Null parameters, context=" + context + ", config=" + eMPushConfig);
    }

    public void onErrorResponse(EMPushType eMPushType, long j2) {
        EMLog.e(f8851a, "onErrorResponse: " + eMPushType + " - " + j2);
        if (!this.f8865o) {
            EMLog.e(f8851a, "EMPushHelper is not registered, abort error response action.");
            return;
        }
        if (j2 == 1500) {
            a(EMPushType.NORMAL);
        }
        PushListener pushListener = this.f8864n;
        if (pushListener != null) {
            pushListener.onError(eMPushType, j2);
        }
    }

    public void onReceiveToken(EMPushType eMPushType, String str) {
        EMLog.e(f8851a, "onReceiveToken: " + eMPushType + " - " + str);
        if (!this.f8865o) {
            EMLog.e(f8851a, "EMPushHelper is not registered, abort token upload action.");
            return;
        }
        this.f8860j = str;
        if (a(eMPushType, str)) {
            EMLog.d(f8851a, "push token changed, upload to server");
            a(str);
        } else if (!EMClient.getInstance().getChatConfigPrivate().I()) {
            EMLog.e(f8851a, "EMPushHelper not first login, ignore token upload action.");
        } else {
            EMLog.d(f8851a, "push token not change, but last login is not on this device, upload to server");
            a(str);
        }
    }

    public int randomDelay(int i2) {
        return i2 == 0 ? new Random().nextInt(5) + 1 : i2 == 1 ? new Random().nextInt(54) + 6 : new Random().nextInt(R2.attr.bl_checked_gradient_centerY) + 60;
    }

    public void register() {
        EMPushConfig eMPushConfig;
        if (this.f8855e == null || (eMPushConfig = this.f8856f) == null) {
            EMLog.e(f8851a, "EMPushHelper#init(Context, EMPushConfig) method not call previously.");
            return;
        }
        EMPushType eMPushTypeA = a(eMPushConfig);
        EMLog.e(f8851a, "EMPushHelper register, prefer push type: " + eMPushTypeA);
        a(eMPushTypeA);
    }

    public void setFCMPushToken(String str) {
        com.hyphenate.chat.core.a.a().d(str);
    }

    public void setPushListener(PushListener pushListener) {
        this.f8864n = pushListener;
    }

    public void setPushTokenWithType(EMPushType eMPushType, String str) {
        com.hyphenate.chat.core.a.a().a(eMPushType, str);
    }

    public boolean unregister(boolean z2) {
        EMLog.e(f8851a, "EMPushHelper unregister, unbind token: " + z2);
        if (!this.f8865o) {
            EMLog.e(f8851a, "EMPushHelper is not registered previously, return true directly.");
            return true;
        }
        this.f8865o = false;
        this.f8857g.a(this.f8855e);
        this.f8858h.removeMessages(0);
        if (!z2) {
            this.f8859i = null;
            return true;
        }
        a();
        synchronized (this.f8863m) {
            try {
                this.f8863m.wait();
            } catch (InterruptedException unused) {
            }
        }
        if (this.f8861k) {
            this.f8859i = null;
        }
        EMLog.e(f8851a, "Push type after unregister is " + this.f8859i);
        return this.f8861k;
    }
}
