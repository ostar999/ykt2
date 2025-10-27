package com.meizu.cloud.pushsdk.platform.a;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.b.a.c;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.io.File;
import java.util.LinkedHashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public String f9504a;

    /* renamed from: b, reason: collision with root package name */
    private String f9505b;

    /* renamed from: c, reason: collision with root package name */
    private String f9506c;

    /* renamed from: d, reason: collision with root package name */
    private String f9507d;

    /* renamed from: e, reason: collision with root package name */
    private String f9508e;

    /* renamed from: f, reason: collision with root package name */
    private String f9509f;

    /* renamed from: g, reason: collision with root package name */
    private String f9510g;

    /* renamed from: h, reason: collision with root package name */
    private String f9511h;

    /* renamed from: i, reason: collision with root package name */
    private String f9512i;

    /* renamed from: j, reason: collision with root package name */
    private String f9513j;

    /* renamed from: k, reason: collision with root package name */
    private String f9514k;

    /* renamed from: l, reason: collision with root package name */
    private String f9515l;

    /* renamed from: m, reason: collision with root package name */
    private String f9516m;

    /* renamed from: n, reason: collision with root package name */
    private String f9517n;

    /* renamed from: o, reason: collision with root package name */
    private String f9518o;

    /* renamed from: p, reason: collision with root package name */
    private String f9519p;

    public a(Context context) {
        this.f9504a = "https://api-push.meizu.com/garcia/api/client/";
        this.f9505b = this.f9504a + "message/registerPush";
        this.f9506c = this.f9504a + "message/unRegisterPush";
        this.f9507d = this.f9504a + "advance/unRegisterPush";
        this.f9508e = this.f9504a + "message/getRegisterSwitch";
        this.f9509f = this.f9504a + "message/changeRegisterSwitch";
        this.f9510g = this.f9504a + "message/changeAllSwitch";
        this.f9511h = this.f9504a + "message/subscribeTags";
        this.f9512i = this.f9504a + "message/unSubscribeTags";
        this.f9513j = this.f9504a + "message/unSubAllTags";
        this.f9514k = this.f9504a + "message/getSubTags";
        this.f9515l = this.f9504a + "message/subscribeAlias";
        this.f9516m = this.f9504a + "message/unSubscribeAlias";
        this.f9517n = this.f9504a + "message/getSubAlias";
        this.f9518o = this.f9504a + "log/upload";
        this.f9519p = this.f9504a + "advance/changeRegisterSwitch";
        com.meizu.cloud.pushsdk.b.a.a();
        if (MzSystemUtils.isInternational() || MzSystemUtils.isIndiaLocal()) {
            this.f9504a = "https://api-push.in.meizu.com/garcia/api/client/";
            this.f9505b = this.f9504a + "message/registerPush";
            this.f9506c = this.f9504a + "message/unRegisterPush";
            this.f9507d = this.f9504a + "advance/unRegisterPush";
            this.f9508e = this.f9504a + "message/getRegisterSwitch";
            this.f9509f = this.f9504a + "message/changeRegisterSwitch";
            this.f9510g = this.f9504a + "message/changeAllSwitch";
            this.f9511h = this.f9504a + "message/subscribeTags";
            this.f9512i = this.f9504a + "message/unSubscribeTags";
            this.f9513j = this.f9504a + "message/unSubAllTags";
            this.f9514k = this.f9504a + "message/getSubTags";
            this.f9515l = this.f9504a + "message/subscribeAlias";
            this.f9516m = this.f9504a + "message/unSubscribeAlias";
            this.f9517n = this.f9504a + "message/getSubAlias";
            this.f9519p = this.f9504a + "advance/changeRegisterSwitch";
        }
    }

    public c a(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put("deviceId", str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "register post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9505b).a(linkedHashMap2).a().a();
    }

    public c a(String str, String str2, String str3, int i2, boolean z2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put(com.alipay.sdk.authjs.a.f3175h, String.valueOf(i2));
        linkedHashMap.put("subSwitch", z2 ? "1" : "0");
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", this.f9509f + " switchPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9509f).a(linkedHashMap2).a().a();
    }

    public c<String> a(String str, String str2, String str3, File file) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(RemoteMessageConst.MSGID, str);
        linkedHashMap.put("deviceId", str2);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, "4a2ca769d79f4856bb3bd982d30de790"));
        if (!TextUtils.isEmpty(str3)) {
            linkedHashMap2.put("errorMsg", str3);
        }
        DebugLogger.i("PushAPI", "uploadLogFile post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.c(this.f9518o).a(linkedHashMap2).a("logFile", file).a().a();
    }

    public c a(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("tags", str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9511h).a(linkedHashMap2).a().a();
    }

    public c a(String str, String str2, String str3, boolean z2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("subSwitch", z2 ? "1" : "0");
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", this.f9510g + " switchPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9510g).a(linkedHashMap2).a().a();
    }

    public c b(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put("deviceId", str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "unregister post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.a(this.f9506c).a(linkedHashMap2).a().a();
    }

    public c b(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("tags", str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9512i).a(linkedHashMap2).a().a();
    }

    public c c(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "checkPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.a(this.f9508e).a(linkedHashMap2).a().a();
    }

    public c c(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(com.heytap.mcssdk.constant.b.f7201z, str2);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("alias", str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9515l).a(linkedHashMap2).a().a();
    }

    public c d(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeAllTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9513j).a(linkedHashMap2).a().a();
    }

    public c d(String str, String str2, String str3, String str4) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        linkedHashMap.put("alias", str4);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "subScribeTags post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.b(this.f9516m).a(linkedHashMap2).a().a();
    }

    public c e(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appId", str);
        linkedHashMap.put(PushConstants.KEY_PUSH_ID, str3);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap2.putAll(linkedHashMap);
        linkedHashMap2.put("sign", com.meizu.cloud.pushsdk.platform.b.a(linkedHashMap, str2));
        DebugLogger.i("PushAPI", "checkPush post map " + linkedHashMap2);
        return com.meizu.cloud.pushsdk.b.a.a(this.f9514k).a(linkedHashMap2).a().a();
    }
}
