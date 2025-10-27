package com.huawei.hms.push;

import android.text.TextUtils;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.support.log.HMSLog;
import com.hyphenate.easeui.constants.EaseConstant;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class k {
    public int B;
    public String D;

    /* renamed from: b, reason: collision with root package name */
    public int f8004b;

    /* renamed from: c, reason: collision with root package name */
    public String f8005c;

    /* renamed from: d, reason: collision with root package name */
    public String f8006d;

    /* renamed from: l, reason: collision with root package name */
    public String f8014l;

    /* renamed from: m, reason: collision with root package name */
    public String f8015m;

    /* renamed from: n, reason: collision with root package name */
    public String f8016n;

    /* renamed from: o, reason: collision with root package name */
    public String f8017o;

    /* renamed from: p, reason: collision with root package name */
    public String f8018p;

    /* renamed from: r, reason: collision with root package name */
    public String f8020r;

    /* renamed from: s, reason: collision with root package name */
    public String f8021s;

    /* renamed from: z, reason: collision with root package name */
    public String f8028z;

    /* renamed from: a, reason: collision with root package name */
    public String f8003a = "";

    /* renamed from: e, reason: collision with root package name */
    public String f8007e = "";

    /* renamed from: f, reason: collision with root package name */
    public String f8008f = "";

    /* renamed from: g, reason: collision with root package name */
    public String f8009g = "";

    /* renamed from: h, reason: collision with root package name */
    public String f8010h = "";

    /* renamed from: i, reason: collision with root package name */
    public String f8011i = "";

    /* renamed from: j, reason: collision with root package name */
    public String f8012j = "";

    /* renamed from: k, reason: collision with root package name */
    public String f8013k = "";

    /* renamed from: q, reason: collision with root package name */
    public String f8019q = "";

    /* renamed from: t, reason: collision with root package name */
    public int f8022t = n.STYLE_DEFAULT.ordinal();

    /* renamed from: u, reason: collision with root package name */
    public String f8023u = "";

    /* renamed from: v, reason: collision with root package name */
    public String f8024v = "";

    /* renamed from: w, reason: collision with root package name */
    public String f8025w = "";

    /* renamed from: x, reason: collision with root package name */
    public int f8026x = 0;

    /* renamed from: y, reason: collision with root package name */
    public int f8027y = 0;
    public String A = "";
    public String C = "";
    public String E = "";
    public String F = "";

    public k(byte[] bArr, byte[] bArr2) {
        Charset charset = x.f8055a;
        this.f8020r = new String(bArr, charset);
        this.f8021s = new String(bArr2, charset);
    }

    public final JSONObject a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(RemoteMessageConst.MessageBody.MSG_CONTENT, jSONObject);
        jSONObject2.put("group", this.f8003a);
        jSONObject2.put("tag", this.A);
        jSONObject2.put(RemoteMessageConst.Notification.AUTO_CANCEL, this.f8026x);
        jSONObject2.put("visibility", this.f8027y);
        jSONObject2.put(RemoteMessageConst.Notification.WHEN, this.f8028z);
        return jSONObject2;
    }

    public final JSONObject b(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(EaseConstant.MESSAGE_TYPE_CMD, this.f8009g);
        jSONObject2.put("content", this.f8010h);
        jSONObject2.put(RemoteMessageConst.Notification.NOTIFY_ICON, this.f8011i);
        jSONObject2.put(RemoteMessageConst.Notification.NOTIFY_TITLE, this.f8012j);
        jSONObject2.put("notifySummary", this.f8013k);
        jSONObject2.put("param", jSONObject);
        return jSONObject2;
    }

    public final void c(JSONObject jSONObject) throws JSONException {
        if (jSONObject.has("ap")) {
            String string = jSONObject.getString("ap");
            StringBuilder sb = new StringBuilder();
            if (TextUtils.isEmpty(string) || string.length() >= 48) {
                this.f8006d = string.substring(0, 48);
                return;
            }
            int length = 48 - string.length();
            for (int i2 = 0; i2 < length; i2++) {
                sb.append("0");
            }
            sb.append(string);
            this.f8006d = sb.toString();
        }
    }

    public final boolean d(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return false;
        }
        if (jSONObject.has(RemoteMessageConst.Notification.CLICK_ACTION)) {
            this.f8015m = jSONObject.getString(RemoteMessageConst.Notification.CLICK_ACTION);
        }
        if (jSONObject.has(RemoteMessageConst.Notification.INTENT_URI)) {
            this.f8005c = jSONObject.getString(RemoteMessageConst.Notification.INTENT_URI);
        }
        if (jSONObject.has("appPackageName")) {
            this.f8014l = jSONObject.getString("appPackageName");
            return true;
        }
        HMSLog.d("PushSelfShowLog", "appPackageName is null");
        return false;
    }

    public final boolean e(JSONObject jSONObject) throws JSONException {
        if (!jSONObject.has(RemoteMessageConst.MSGID)) {
            HMSLog.i("PushSelfShowLog", "msgId == null");
            return false;
        }
        Object obj = jSONObject.get(RemoteMessageConst.MSGID);
        if (obj instanceof String) {
            this.f8007e = (String) obj;
            return true;
        }
        if (!(obj instanceof Integer)) {
            return true;
        }
        this.f8007e = String.valueOf(((Integer) obj).intValue());
        return true;
    }

    public final boolean f(JSONObject jSONObject) throws JSONException {
        HMSLog.d("PushSelfShowLog", "enter parseNotifyParam");
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(RemoteMessageConst.MessageBody.NOTIFY_DETAIL);
            if (jSONObject2.has(TtmlNode.TAG_STYLE)) {
                this.f8022t = jSONObject2.getInt(TtmlNode.TAG_STYLE);
            }
            this.f8023u = jSONObject2.optString("bigTitle");
            this.f8024v = jSONObject2.optString("bigContent");
            this.E = jSONObject2.optString(RemoteMessageConst.Notification.ICON);
            return true;
        } catch (JSONException e2) {
            HMSLog.i("PushSelfShowLog", e2.toString());
            return false;
        }
    }

    public final void g(JSONObject jSONObject) {
        this.f8003a = jSONObject.optString("group");
        HMSLog.d("PushSelfShowLog", "NOTIFY_GROUP:" + this.f8003a);
        this.f8026x = jSONObject.optInt(RemoteMessageConst.Notification.AUTO_CANCEL, 1);
        HMSLog.d("PushSelfShowLog", "autoCancel: " + this.f8026x);
        this.f8027y = jSONObject.optInt("visibility", 0);
        this.f8028z = jSONObject.optString(RemoteMessageConst.Notification.WHEN);
        this.A = jSONObject.optString("tag");
    }

    public final boolean h(JSONObject jSONObject) throws JSONException {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("param");
            if (jSONObject2.has("autoClear")) {
                this.f8004b = jSONObject2.getInt("autoClear");
            } else {
                this.f8004b = 0;
            }
            if ("app".equals(this.f8009g) || "cosa".equals(this.f8009g)) {
                d(jSONObject2);
                return true;
            }
            if ("url".equals(this.f8009g)) {
                k(jSONObject2);
                return true;
            }
            if (!AliyunLogKey.KEY_RESOURCE_PATH.equals(this.f8009g)) {
                return true;
            }
            j(jSONObject2);
            return true;
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", "ParseParam error ", e2);
            return false;
        }
    }

    public final boolean i(JSONObject jSONObject) throws JSONException {
        if (jSONObject.has(RemoteMessageConst.MessageBody.PS_CONTENT)) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(RemoteMessageConst.MessageBody.PS_CONTENT);
            this.f8009g = jSONObject2.getString(EaseConstant.MESSAGE_TYPE_CMD);
            this.f8010h = jSONObject2.optString("content");
            this.f8011i = jSONObject2.optString(RemoteMessageConst.Notification.NOTIFY_ICON);
            this.f8012j = jSONObject2.optString(RemoteMessageConst.Notification.NOTIFY_TITLE);
            this.f8013k = jSONObject2.optString("notifySummary");
            this.D = jSONObject2.optString(RemoteMessageConst.Notification.TICKER);
            if ((!jSONObject2.has(RemoteMessageConst.MessageBody.NOTIFY_DETAIL) || f(jSONObject2)) && jSONObject2.has("param")) {
                return h(jSONObject2);
            }
        }
        return false;
    }

    public final boolean j(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return false;
        }
        if (jSONObject.has("appPackageName")) {
            this.f8014l = jSONObject.getString("appPackageName");
        }
        if (!jSONObject.has("rpt") || !jSONObject.has("rpl")) {
            HMSLog.d("PushSelfShowLog", "rpl or rpt is null");
            return false;
        }
        this.f8017o = jSONObject.getString("rpl");
        this.f8018p = jSONObject.getString("rpt");
        if (!jSONObject.has("rpct")) {
            return true;
        }
        this.f8019q = jSONObject.getString("rpct");
        return true;
    }

    public final boolean k(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return false;
        }
        if (!jSONObject.has("url")) {
            HMSLog.d("PushSelfShowLog", "url is null");
            return false;
        }
        this.f8016n = jSONObject.getString("url");
        if (jSONObject.has("appPackageName")) {
            this.f8014l = jSONObject.getString("appPackageName");
        }
        if (!jSONObject.has("rpt") || !jSONObject.has("rpl")) {
            return true;
        }
        this.f8017o = jSONObject.getString("rpl");
        this.f8018p = jSONObject.getString("rpt");
        if (!jSONObject.has("rpct")) {
            return true;
        }
        this.f8019q = jSONObject.getString("rpct");
        return true;
    }

    public String l() {
        return this.f8003a;
    }

    public String m() {
        return this.E;
    }

    public String n() {
        return this.f8005c;
    }

    public byte[] o() {
        try {
            return a(a(b(v()), r())).toString().getBytes(x.f8055a);
        } catch (JSONException e2) {
            HMSLog.e("PushSelfShowLog", "getMsgData failed JSONException:", e2);
            return new byte[0];
        }
    }

    public String p() {
        HMSLog.d("PushSelfShowLog", "msgId =" + this.f8007e);
        return this.f8007e;
    }

    public String q() {
        return this.A;
    }

    public final JSONObject r() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(TtmlNode.TAG_STYLE, this.f8022t);
        jSONObject.put("bigTitle", this.f8023u);
        jSONObject.put("bigContent", this.f8024v);
        jSONObject.put("bigPic", this.f8025w);
        return jSONObject;
    }

    public int s() {
        return this.B;
    }

    public String t() {
        return this.f8013k;
    }

    public String u() {
        return this.f8012j;
    }

    public final JSONObject v() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("autoClear", this.f8004b);
        jSONObject.put("url", this.f8016n);
        jSONObject.put("rpl", this.f8017o);
        jSONObject.put("rpt", this.f8018p);
        jSONObject.put("rpct", this.f8019q);
        jSONObject.put("appPackageName", this.f8014l);
        jSONObject.put(RemoteMessageConst.Notification.CLICK_ACTION, this.f8015m);
        jSONObject.put(RemoteMessageConst.Notification.INTENT_URI, this.f8005c);
        return jSONObject;
    }

    public int w() {
        return this.f8022t;
    }

    public String x() {
        return this.D;
    }

    public byte[] y() {
        return this.f8021s.getBytes(x.f8055a);
    }

    public boolean z() throws JSONException {
        try {
            if (TextUtils.isEmpty(this.f8020r)) {
                HMSLog.d("PushSelfShowLog", "msg is null");
                return false;
            }
            JSONObject jSONObject = new JSONObject(this.f8020r);
            g(jSONObject);
            JSONObject jSONObject2 = jSONObject.getJSONObject(RemoteMessageConst.MessageBody.MSG_CONTENT);
            if (!e(jSONObject2)) {
                return false;
            }
            this.f8008f = jSONObject2.optString("dispPkgName");
            c(jSONObject2);
            this.B = jSONObject2.optInt(RemoteMessageConst.Notification.NOTIFY_ID, -1);
            this.C = jSONObject2.optString("data");
            this.F = jSONObject2.optString(RemoteMessageConst.ANALYTIC_INFO);
            return i(jSONObject2);
        } catch (JSONException unused) {
            HMSLog.d("PushSelfShowLog", "parse message exception.");
            return false;
        } catch (Exception e2) {
            HMSLog.d("PushSelfShowLog", e2.toString());
            return false;
        }
    }

    public final JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("dispPkgName", this.f8008f);
        jSONObject3.put(RemoteMessageConst.MSGID, this.f8007e);
        jSONObject3.put("ap", this.f8006d);
        jSONObject3.put(RemoteMessageConst.Notification.NOTIFY_ID, this.B);
        jSONObject3.put(RemoteMessageConst.MessageBody.PS_CONTENT, jSONObject);
        jSONObject3.put(RemoteMessageConst.MessageBody.NOTIFY_DETAIL, jSONObject2);
        jSONObject3.put(RemoteMessageConst.Notification.TICKER, this.D);
        jSONObject3.put("data", this.C);
        return jSONObject3;
    }

    public String b() {
        return this.F;
    }

    public String d() {
        return this.f8014l;
    }

    public String g() {
        return this.f8024v;
    }

    public int e() {
        return this.f8026x;
    }

    public int f() {
        return this.f8004b;
    }

    public String j() {
        return this.f8010h;
    }

    public String c() {
        return this.f8006d;
    }

    public String k() {
        return this.f8008f;
    }

    public String h() {
        return this.f8023u;
    }

    public String i() {
        return this.f8009g;
    }

    public String a() {
        return this.f8015m;
    }

    public void a(int i2) {
        this.B = i2;
    }
}
