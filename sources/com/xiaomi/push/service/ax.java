package com.xiaomi.push.service;

/* loaded from: classes6.dex */
public abstract class ax {
    public static String A = "ext_notify_id";
    public static String B = "ext_notify_type";
    public static String C = "ext_session";
    public static String D = "sig";
    public static String E = "ext_notify_title";
    public static String F = "ext_notify_description";
    public static String G = "ext_messenger";
    public static String H = "title";
    public static String I = "description";
    public static String J = "notifyId";

    /* renamed from: a, reason: collision with root package name */
    public static String f25611a = "1";

    /* renamed from: b, reason: collision with root package name */
    public static String f25612b = "2";

    /* renamed from: c, reason: collision with root package name */
    public static String f25613c = "3";

    /* renamed from: d, reason: collision with root package name */
    public static String f25614d = "com.xiaomi.push.OPEN_CHANNEL";

    /* renamed from: e, reason: collision with root package name */
    public static String f25615e = "com.xiaomi.push.SEND_MESSAGE";

    /* renamed from: f, reason: collision with root package name */
    public static String f25616f = "com.xiaomi.push.SEND_IQ";

    /* renamed from: g, reason: collision with root package name */
    public static String f25617g = "com.xiaomi.push.BATCH_SEND_MESSAGE";

    /* renamed from: h, reason: collision with root package name */
    public static String f25618h = "com.xiaomi.push.SEND_PRES";

    /* renamed from: i, reason: collision with root package name */
    public static String f25619i = "com.xiaomi.push.CLOSE_CHANNEL";

    /* renamed from: j, reason: collision with root package name */
    public static String f25620j = "com.xiaomi.push.FORCE_RECONN";

    /* renamed from: k, reason: collision with root package name */
    public static String f25621k = "com.xiaomi.push.RESET_CONN";

    /* renamed from: l, reason: collision with root package name */
    public static String f25622l = "com.xiaomi.push.UPDATE_CHANNEL_INFO";

    /* renamed from: m, reason: collision with root package name */
    public static String f25623m = "com.xiaomi.push.SEND_STATS";

    /* renamed from: n, reason: collision with root package name */
    public static String f25624n = "com.xiaomi.push.CHANGE_HOST";

    /* renamed from: o, reason: collision with root package name */
    public static String f25625o = "com.xiaomi.push.PING_TIMER";

    /* renamed from: p, reason: collision with root package name */
    public static String f25626p = "ext_user_id";

    /* renamed from: q, reason: collision with root package name */
    public static String f25627q = "ext_user_res";

    /* renamed from: r, reason: collision with root package name */
    public static String f25628r = "ext_chid";

    /* renamed from: s, reason: collision with root package name */
    public static String f25629s = "ext_sid";

    /* renamed from: t, reason: collision with root package name */
    public static String f25630t = "ext_token";

    /* renamed from: u, reason: collision with root package name */
    public static String f25631u = "ext_auth_method";

    /* renamed from: v, reason: collision with root package name */
    public static String f25632v = "ext_security";

    /* renamed from: w, reason: collision with root package name */
    public static String f25633w = "ext_kick";

    /* renamed from: x, reason: collision with root package name */
    public static String f25634x = "ext_client_attr";

    /* renamed from: y, reason: collision with root package name */
    public static String f25635y = "ext_cloud_attr";

    /* renamed from: z, reason: collision with root package name */
    public static String f25636z = "ext_pkg_name";

    public static String a(int i2) {
        switch (i2) {
            case 0:
                return "ERROR_OK";
            case 1:
                return "ERROR_SERVICE_NOT_INSTALLED";
            case 2:
                return "ERROR_NETWORK_NOT_AVAILABLE";
            case 3:
                return "ERROR_NETWORK_FAILED";
            case 4:
                return "ERROR_ACCESS_DENIED";
            case 5:
                return "ERROR_AUTH_FAILED";
            case 6:
                return "ERROR_MULTI_LOGIN";
            case 7:
                return "ERROR_SERVER_ERROR";
            case 8:
                return "ERROR_RECEIVE_TIMEOUT";
            case 9:
                return "ERROR_READ_ERROR";
            case 10:
                return "ERROR_SEND_ERROR";
            case 11:
                return "ERROR_RESET";
            case 12:
                return "ERROR_NO_CLIENT";
            case 13:
                return "ERROR_SERVER_STREAM";
            case 14:
                return "ERROR_THREAD_BLOCK";
            case 15:
                return "ERROR_SERVICE_DESTROY";
            case 16:
                return "ERROR_SESSION_CHANGED";
            case 17:
                return "ERROR_READ_TIMEOUT";
            case 18:
                return "ERROR_CONNECTIING_TIMEOUT";
            case 19:
                return "ERROR_USER_BLOCKED";
            case 20:
                return "ERROR_REDIRECT";
            case 21:
                return "ERROR_BIND_TIMEOUT";
            case 22:
                return "ERROR_PING_TIMEOUT";
            default:
                return String.valueOf(i2);
        }
    }
}
