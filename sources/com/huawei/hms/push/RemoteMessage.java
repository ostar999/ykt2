package com.huawei.hms.push;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.push.utils.DateUtil;
import com.huawei.hms.push.utils.JsonUtil;
import com.huawei.hms.support.api.push.PushException;
import com.huawei.hms.support.log.HMSLog;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class RemoteMessage implements Parcelable {
    public static final Parcelable.Creator<RemoteMessage> CREATOR;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;

    /* renamed from: a, reason: collision with root package name */
    public static final String[] f7956a;

    /* renamed from: b, reason: collision with root package name */
    public static final int[] f7957b;

    /* renamed from: c, reason: collision with root package name */
    public static final long[] f7958c;

    /* renamed from: d, reason: collision with root package name */
    public static final HashMap<String, Object> f7959d;

    /* renamed from: e, reason: collision with root package name */
    public static final HashMap<String, Object> f7960e;

    /* renamed from: f, reason: collision with root package name */
    public static final HashMap<String, Object> f7961f;

    /* renamed from: g, reason: collision with root package name */
    public static final HashMap<String, Object> f7962g;

    /* renamed from: h, reason: collision with root package name */
    public static final HashMap<String, Object> f7963h;

    /* renamed from: i, reason: collision with root package name */
    public Bundle f7964i;

    /* renamed from: j, reason: collision with root package name */
    public Notification f7965j;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        public final Bundle f7966a;

        /* renamed from: b, reason: collision with root package name */
        public final Map<String, String> f7967b;

        public Builder(String str) {
            Bundle bundle = new Bundle();
            this.f7966a = bundle;
            this.f7967b = new HashMap();
            bundle.putString("to", str);
        }

        public Builder addData(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("add data failed, key is null.");
            }
            this.f7967b.put(str, str2);
            return this;
        }

        public RemoteMessage build() throws JSONException {
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            try {
                for (Map.Entry<String, String> entry : this.f7967b.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
                try {
                    String string = jSONObject.toString();
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(RemoteMessageConst.COLLAPSE_KEY, this.f7966a.getString(RemoteMessageConst.COLLAPSE_KEY));
                    jSONObject2.put(RemoteMessageConst.TTL, this.f7966a.getInt(RemoteMessageConst.TTL));
                    jSONObject2.put(RemoteMessageConst.SEND_MODE, this.f7966a.getInt(RemoteMessageConst.SEND_MODE));
                    jSONObject2.put(RemoteMessageConst.RECEIPT_MODE, this.f7966a.getInt(RemoteMessageConst.RECEIPT_MODE));
                    JSONObject jSONObject3 = new JSONObject();
                    if (jSONObject.length() != 0) {
                        jSONObject3.put("data", string);
                    }
                    jSONObject3.put(RemoteMessageConst.MSGID, this.f7966a.getString(RemoteMessageConst.MSGID));
                    jSONObject2.put(RemoteMessageConst.MessageBody.MSG_CONTENT, jSONObject3);
                    bundle.putByteArray(RemoteMessageConst.MSGBODY, jSONObject2.toString().getBytes(x.f8055a));
                    bundle.putString("to", this.f7966a.getString("to"));
                    bundle.putString("message_type", this.f7966a.getString("message_type"));
                    return new RemoteMessage(bundle);
                } catch (JSONException unused) {
                    HMSLog.w("RemoteMessage", "JSONException: parse message body failed.");
                    throw new PushException(PushException.EXCEPTION_SEND_FAILED);
                }
            } catch (JSONException unused2) {
                HMSLog.w("RemoteMessage", "JSONException: parse data to json failed.");
                throw new PushException(PushException.EXCEPTION_SEND_FAILED);
            }
        }

        public Builder clearData() {
            this.f7967b.clear();
            return this;
        }

        public Builder setCollapseKey(String str) {
            this.f7966a.putString(RemoteMessageConst.COLLAPSE_KEY, str);
            return this;
        }

        public Builder setData(Map<String, String> map) {
            this.f7967b.clear();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.f7967b.put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public Builder setMessageId(String str) {
            this.f7966a.putString(RemoteMessageConst.MSGID, str);
            return this;
        }

        public Builder setMessageType(String str) {
            this.f7966a.putString("message_type", str);
            return this;
        }

        public Builder setReceiptMode(int i2) {
            if (i2 != 1 && i2 != 0) {
                throw new IllegalArgumentException("receipt mode can only be 0 or 1.");
            }
            this.f7966a.putInt(RemoteMessageConst.RECEIPT_MODE, i2);
            return this;
        }

        public Builder setSendMode(int i2) {
            if (i2 != 0 && i2 != 1) {
                throw new IllegalArgumentException("send mode can only be 0 or 1.");
            }
            this.f7966a.putInt(RemoteMessageConst.SEND_MODE, i2);
            return this;
        }

        public Builder setTtl(int i2) {
            if (i2 < 1 || i2 > 1296000) {
                throw new IllegalArgumentException("ttl must be greater than or equal to 1 and less than or equal to 1296000");
            }
            this.f7966a.putInt(RemoteMessageConst.TTL, i2);
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    public static class Notification implements Serializable {
        public final long[] A;
        public final String B;

        /* renamed from: a, reason: collision with root package name */
        public final String f7968a;

        /* renamed from: b, reason: collision with root package name */
        public final String f7969b;

        /* renamed from: c, reason: collision with root package name */
        public final String[] f7970c;

        /* renamed from: d, reason: collision with root package name */
        public final String f7971d;

        /* renamed from: e, reason: collision with root package name */
        public final String f7972e;

        /* renamed from: f, reason: collision with root package name */
        public final String[] f7973f;

        /* renamed from: g, reason: collision with root package name */
        public final String f7974g;

        /* renamed from: h, reason: collision with root package name */
        public final String f7975h;

        /* renamed from: i, reason: collision with root package name */
        public final String f7976i;

        /* renamed from: j, reason: collision with root package name */
        public final String f7977j;

        /* renamed from: k, reason: collision with root package name */
        public final String f7978k;

        /* renamed from: l, reason: collision with root package name */
        public final String f7979l;

        /* renamed from: m, reason: collision with root package name */
        public final String f7980m;

        /* renamed from: n, reason: collision with root package name */
        public final Uri f7981n;

        /* renamed from: o, reason: collision with root package name */
        public final int f7982o;

        /* renamed from: p, reason: collision with root package name */
        public final String f7983p;

        /* renamed from: q, reason: collision with root package name */
        public final int f7984q;

        /* renamed from: r, reason: collision with root package name */
        public final int f7985r;

        /* renamed from: s, reason: collision with root package name */
        public final int f7986s;

        /* renamed from: t, reason: collision with root package name */
        public final int[] f7987t;

        /* renamed from: u, reason: collision with root package name */
        public final String f7988u;

        /* renamed from: v, reason: collision with root package name */
        public final int f7989v;

        /* renamed from: w, reason: collision with root package name */
        public final String f7990w;

        /* renamed from: x, reason: collision with root package name */
        public final int f7991x;

        /* renamed from: y, reason: collision with root package name */
        public final String f7992y;

        /* renamed from: z, reason: collision with root package name */
        public final String f7993z;

        public /* synthetic */ Notification(Bundle bundle, b bVar) {
            this(bundle);
        }

        public final Integer a(String str) {
            if (str != null) {
                try {
                    return Integer.valueOf(str);
                } catch (NumberFormatException unused) {
                    HMSLog.w("RemoteMessage", "NumberFormatException: get " + str + " failed.");
                }
            }
            return null;
        }

        public Integer getBadgeNumber() {
            return a(this.f7990w);
        }

        public String getBody() {
            return this.f7971d;
        }

        public String[] getBodyLocalizationArgs() {
            String[] strArr = this.f7973f;
            return strArr == null ? new String[0] : (String[]) strArr.clone();
        }

        public String getBodyLocalizationKey() {
            return this.f7972e;
        }

        public String getChannelId() {
            return this.f7980m;
        }

        public String getClickAction() {
            return this.f7978k;
        }

        public String getColor() {
            return this.f7977j;
        }

        public String getIcon() {
            return this.f7974g;
        }

        public Uri getImageUrl() {
            String str = this.f7983p;
            if (str == null) {
                return null;
            }
            return Uri.parse(str);
        }

        public Integer getImportance() {
            return a(this.f7992y);
        }

        public String getIntentUri() {
            return this.f7979l;
        }

        public int[] getLightSettings() {
            int[] iArr = this.f7987t;
            return iArr == null ? new int[0] : (int[]) iArr.clone();
        }

        public Uri getLink() {
            return this.f7981n;
        }

        public int getNotifyId() {
            return this.f7982o;
        }

        public String getSound() {
            return this.f7975h;
        }

        public String getTag() {
            return this.f7976i;
        }

        public String getTicker() {
            return this.f7993z;
        }

        public String getTitle() {
            return this.f7968a;
        }

        public String[] getTitleLocalizationArgs() {
            String[] strArr = this.f7970c;
            return strArr == null ? new String[0] : (String[]) strArr.clone();
        }

        public String getTitleLocalizationKey() {
            return this.f7969b;
        }

        public long[] getVibrateConfig() {
            long[] jArr = this.A;
            return jArr == null ? new long[0] : (long[]) jArr.clone();
        }

        public Integer getVisibility() {
            return a(this.B);
        }

        public Long getWhen() {
            if (!TextUtils.isEmpty(this.f7988u)) {
                try {
                    return Long.valueOf(DateUtil.parseUtcToMillisecond(this.f7988u));
                } catch (StringIndexOutOfBoundsException unused) {
                    HMSLog.w("RemoteMessage", "StringIndexOutOfBoundsException: parse when failed.");
                } catch (ParseException unused2) {
                    HMSLog.w("RemoteMessage", "ParseException: parse when failed.");
                }
            }
            return null;
        }

        public boolean isAutoCancel() {
            return this.f7991x == 1;
        }

        public boolean isDefaultLight() {
            return this.f7984q == 1;
        }

        public boolean isDefaultSound() {
            return this.f7985r == 1;
        }

        public boolean isDefaultVibrate() {
            return this.f7986s == 1;
        }

        public boolean isLocalOnly() {
            return this.f7989v == 1;
        }

        public Notification(Bundle bundle) {
            this.f7968a = bundle.getString(RemoteMessageConst.Notification.NOTIFY_TITLE);
            this.f7971d = bundle.getString("content");
            this.f7969b = bundle.getString(RemoteMessageConst.Notification.TITLE_LOC_KEY);
            this.f7972e = bundle.getString(RemoteMessageConst.Notification.BODY_LOC_KEY);
            this.f7970c = bundle.getStringArray(RemoteMessageConst.Notification.TITLE_LOC_ARGS);
            this.f7973f = bundle.getStringArray(RemoteMessageConst.Notification.BODY_LOC_ARGS);
            this.f7974g = bundle.getString(RemoteMessageConst.Notification.ICON);
            this.f7977j = bundle.getString("color");
            this.f7975h = bundle.getString(RemoteMessageConst.Notification.SOUND);
            this.f7976i = bundle.getString("tag");
            this.f7980m = bundle.getString("channelId");
            this.f7978k = bundle.getString(RemoteMessageConst.Notification.CLICK_ACTION);
            this.f7979l = bundle.getString(RemoteMessageConst.Notification.INTENT_URI);
            this.f7982o = bundle.getInt(RemoteMessageConst.Notification.NOTIFY_ID);
            String string = bundle.getString("url");
            this.f7981n = !TextUtils.isEmpty(string) ? Uri.parse(string) : null;
            this.f7983p = bundle.getString(RemoteMessageConst.Notification.NOTIFY_ICON);
            this.f7984q = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_LIGHT_SETTINGS);
            this.f7985r = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_SOUND);
            this.f7986s = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_VIBRATE_TIMINGS);
            this.f7987t = bundle.getIntArray(RemoteMessageConst.Notification.LIGHT_SETTINGS);
            this.f7988u = bundle.getString(RemoteMessageConst.Notification.WHEN);
            this.f7989v = bundle.getInt("localOnly");
            this.f7990w = bundle.getString(RemoteMessageConst.Notification.BADGE_SET_NUM, null);
            this.f7991x = bundle.getInt(RemoteMessageConst.Notification.AUTO_CANCEL);
            this.f7992y = bundle.getString(RemoteMessageConst.Notification.PRIORITY, null);
            this.f7993z = bundle.getString(RemoteMessageConst.Notification.TICKER);
            this.A = bundle.getLongArray(RemoteMessageConst.Notification.VIBRATE_TIMINGS);
            this.B = bundle.getString("visibility", null);
        }
    }

    static {
        String[] strArr = new String[0];
        f7956a = strArr;
        int[] iArr = new int[0];
        f7957b = iArr;
        long[] jArr = new long[0];
        f7958c = jArr;
        HashMap<String, Object> map = new HashMap<>(8);
        f7959d = map;
        map.put("from", "");
        map.put(RemoteMessageConst.COLLAPSE_KEY, "");
        map.put(RemoteMessageConst.SEND_TIME, "");
        map.put(RemoteMessageConst.TTL, 86400);
        map.put("urgency", 2);
        map.put(RemoteMessageConst.ORI_URGENCY, 2);
        map.put(RemoteMessageConst.SEND_MODE, 0);
        map.put(RemoteMessageConst.RECEIPT_MODE, 0);
        HashMap<String, Object> map2 = new HashMap<>(8);
        f7960e = map2;
        map2.put(RemoteMessageConst.Notification.TITLE_LOC_KEY, "");
        map2.put(RemoteMessageConst.Notification.BODY_LOC_KEY, "");
        map2.put(RemoteMessageConst.Notification.NOTIFY_ICON, "");
        map2.put(RemoteMessageConst.Notification.TITLE_LOC_ARGS, strArr);
        map2.put(RemoteMessageConst.Notification.BODY_LOC_ARGS, strArr);
        map2.put(RemoteMessageConst.Notification.TICKER, "");
        map2.put(RemoteMessageConst.Notification.NOTIFY_TITLE, "");
        map2.put("content", "");
        HashMap<String, Object> map3 = new HashMap<>(8);
        f7961f = map3;
        map3.put(RemoteMessageConst.Notification.ICON, "");
        map3.put("color", "");
        map3.put(RemoteMessageConst.Notification.SOUND, "");
        map3.put(RemoteMessageConst.Notification.DEFAULT_LIGHT_SETTINGS, 1);
        map3.put(RemoteMessageConst.Notification.LIGHT_SETTINGS, iArr);
        map3.put(RemoteMessageConst.Notification.DEFAULT_SOUND, 1);
        map3.put(RemoteMessageConst.Notification.DEFAULT_VIBRATE_TIMINGS, 1);
        map3.put(RemoteMessageConst.Notification.VIBRATE_TIMINGS, jArr);
        HashMap<String, Object> map4 = new HashMap<>(8);
        f7962g = map4;
        map4.put("tag", "");
        map4.put(RemoteMessageConst.Notification.WHEN, "");
        map4.put("localOnly", 1);
        map4.put(RemoteMessageConst.Notification.BADGE_SET_NUM, "");
        map4.put(RemoteMessageConst.Notification.PRIORITY, "");
        map4.put(RemoteMessageConst.Notification.AUTO_CANCEL, 1);
        map4.put("visibility", "");
        map4.put("channelId", "");
        HashMap<String, Object> map5 = new HashMap<>(3);
        f7963h = map5;
        map5.put(RemoteMessageConst.Notification.CLICK_ACTION, "");
        map5.put(RemoteMessageConst.Notification.INTENT_URI, "");
        map5.put("url", "");
        CREATOR = new b();
    }

    public RemoteMessage(Bundle bundle) {
        this.f7964i = a(bundle);
    }

    public static JSONObject b(Bundle bundle) {
        try {
            return new JSONObject(w.a(bundle.getByteArray(RemoteMessageConst.MSGBODY)));
        } catch (JSONException unused) {
            HMSLog.w("RemoteMessage", "JSONException:parse message body failed.");
            return null;
        }
    }

    public static JSONObject c(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject("param");
        }
        return null;
    }

    public static JSONObject d(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.PS_CONTENT);
        }
        return null;
    }

    public final Bundle a(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        JSONObject jSONObjectB = b(bundle);
        JSONObject jSONObjectA = a(jSONObjectB);
        String string = JsonUtil.getString(jSONObjectA, "data", null);
        bundle2.putString(RemoteMessageConst.ANALYTIC_INFO, JsonUtil.getString(jSONObjectA, RemoteMessageConst.ANALYTIC_INFO, null));
        bundle2.putString(RemoteMessageConst.DEVICE_TOKEN, bundle.getString(RemoteMessageConst.DEVICE_TOKEN));
        JSONObject jSONObjectD = d(jSONObjectA);
        JSONObject jSONObjectB2 = b(jSONObjectD);
        JSONObject jSONObjectC = c(jSONObjectD);
        if (bundle.getInt(RemoteMessageConst.INPUT_TYPE) == 1 && s.a(jSONObjectA, jSONObjectD, string)) {
            bundle2.putString("data", w.a(bundle.getByteArray(RemoteMessageConst.MSGBODY)));
            return bundle2;
        }
        String string2 = bundle.getString("to");
        String string3 = bundle.getString("message_type");
        String string4 = JsonUtil.getString(jSONObjectA, RemoteMessageConst.MSGID, null);
        bundle2.putString("to", string2);
        bundle2.putString("data", string);
        bundle2.putString(RemoteMessageConst.MSGID, string4);
        bundle2.putString("message_type", string3);
        JsonUtil.transferJsonObjectToBundle(jSONObjectB, bundle2, f7959d);
        bundle2.putBundle(RemoteMessageConst.NOTIFICATION, a(jSONObjectB, jSONObjectA, jSONObjectD, jSONObjectB2, jSONObjectC));
        return bundle2;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public String getAnalyticInfo() {
        return this.f7964i.getString(RemoteMessageConst.ANALYTIC_INFO);
    }

    public Map<String, String> getAnalyticInfoMap() {
        HashMap map = new HashMap();
        String string = this.f7964i.getString(RemoteMessageConst.ANALYTIC_INFO);
        if (string != null && !string.trim().isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String strValueOf = String.valueOf(itKeys.next());
                    map.put(strValueOf, String.valueOf(jSONObject.get(strValueOf)));
                }
            } catch (JSONException unused) {
                HMSLog.w("RemoteMessage", "JSONException: get analyticInfo from map failed.");
            }
        }
        return map;
    }

    public String getCollapseKey() {
        return this.f7964i.getString(RemoteMessageConst.COLLAPSE_KEY);
    }

    public String getData() {
        return this.f7964i.getString("data");
    }

    public Map<String, String> getDataOfMap() {
        HashMap map = new HashMap();
        String string = this.f7964i.getString("data");
        if (string != null && !string.trim().isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String strValueOf = String.valueOf(itKeys.next());
                    map.put(strValueOf, String.valueOf(jSONObject.get(strValueOf)));
                }
            } catch (JSONException unused) {
                HMSLog.w("RemoteMessage", "JSONException: get data from map failed");
            }
        }
        return map;
    }

    public String getFrom() {
        return this.f7964i.getString("from");
    }

    public String getMessageId() {
        return this.f7964i.getString(RemoteMessageConst.MSGID);
    }

    public String getMessageType() {
        return this.f7964i.getString("message_type");
    }

    public Notification getNotification() {
        Bundle bundle = this.f7964i.getBundle(RemoteMessageConst.NOTIFICATION);
        b bVar = null;
        if (this.f7965j == null && bundle != null) {
            this.f7965j = new Notification(bundle, bVar);
        }
        if (this.f7965j == null) {
            this.f7965j = new Notification(new Bundle(), bVar);
        }
        return this.f7965j;
    }

    public int getOriginalUrgency() {
        int i2 = this.f7964i.getInt(RemoteMessageConst.ORI_URGENCY);
        if (i2 == 1 || i2 == 2) {
            return i2;
        }
        return 0;
    }

    public int getReceiptMode() {
        return this.f7964i.getInt(RemoteMessageConst.RECEIPT_MODE);
    }

    public int getSendMode() {
        return this.f7964i.getInt(RemoteMessageConst.SEND_MODE);
    }

    public long getSentTime() {
        try {
            String string = this.f7964i.getString(RemoteMessageConst.SEND_TIME);
            if (TextUtils.isEmpty(string)) {
                return 0L;
            }
            return Long.parseLong(string);
        } catch (NumberFormatException unused) {
            HMSLog.w("RemoteMessage", "NumberFormatException: get sendTime error.");
            return 0L;
        }
    }

    public String getTo() {
        return this.f7964i.getString("to");
    }

    public String getToken() {
        return this.f7964i.getString(RemoteMessageConst.DEVICE_TOKEN);
    }

    public int getTtl() {
        return this.f7964i.getInt(RemoteMessageConst.TTL);
    }

    public int getUrgency() {
        int i2 = this.f7964i.getInt("urgency");
        if (i2 == 1 || i2 == 2) {
            return i2;
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeBundle(this.f7964i);
        parcel.writeSerializable(this.f7965j);
    }

    public RemoteMessage(Parcel parcel) {
        this.f7964i = parcel.readBundle();
        this.f7965j = (Notification) parcel.readSerializable();
    }

    public static JSONObject b(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.NOTIFY_DETAIL);
        }
        return null;
    }

    public final Bundle a(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, JSONObject jSONObject4, JSONObject jSONObject5) {
        Bundle bundle = new Bundle();
        JsonUtil.transferJsonObjectToBundle(jSONObject3, bundle, f7960e);
        JsonUtil.transferJsonObjectToBundle(jSONObject4, bundle, f7961f);
        JsonUtil.transferJsonObjectToBundle(jSONObject, bundle, f7962g);
        JsonUtil.transferJsonObjectToBundle(jSONObject5, bundle, f7963h);
        bundle.putInt(RemoteMessageConst.Notification.NOTIFY_ID, JsonUtil.getInt(jSONObject2, RemoteMessageConst.Notification.NOTIFY_ID, 0));
        return bundle;
    }

    public static JSONObject a(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.MSG_CONTENT);
        }
        return null;
    }
}
