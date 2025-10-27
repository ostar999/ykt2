package com.meizu.cloud.pushsdk.handler.a.b;

import android.os.Parcel;
import android.os.Parcelable;
import cn.hutool.core.text.CharPool;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e implements Parcelable {
    public static final Parcelable.Creator<e> CREATOR = new Parcelable.Creator<e>() { // from class: com.meizu.cloud.pushsdk.handler.a.b.e.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public e createFromParcel(Parcel parcel) {
            return new e(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public e[] newArray(int i2) {
            return new e[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private String f9460a;

    /* renamed from: b, reason: collision with root package name */
    private String f9461b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f9462c;

    /* renamed from: d, reason: collision with root package name */
    private String f9463d;

    /* renamed from: e, reason: collision with root package name */
    private String f9464e;

    public e() {
        this.f9462c = false;
    }

    public e(Parcel parcel) {
        this.f9462c = false;
        this.f9460a = parcel.readString();
        this.f9461b = parcel.readString();
        this.f9462c = parcel.readByte() != 0;
        this.f9463d = parcel.readString();
        this.f9464e = parcel.readString();
    }

    public static e a(JSONObject jSONObject) {
        String str;
        e eVar = new e();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull("taskId")) {
                    eVar.a(jSONObject.getString("taskId"));
                }
                if (!jSONObject.isNull(CrashHianalyticsData.TIME)) {
                    eVar.b(jSONObject.getString(CrashHianalyticsData.TIME));
                }
                if (!jSONObject.isNull("pushExtra")) {
                    eVar.a(jSONObject.getInt("pushExtra") != 0);
                }
            } catch (JSONException e2) {
                str = " parse statics message error " + e2.getMessage();
            }
            return eVar;
        }
        str = "no control statics can parse ";
        DebugLogger.e("statics", str);
        return eVar;
    }

    public String a() {
        return this.f9460a;
    }

    public void a(String str) {
        this.f9460a = str;
    }

    public void a(boolean z2) {
        this.f9462c = z2;
    }

    public String b() {
        return this.f9461b;
    }

    public void b(String str) {
        this.f9461b = str;
    }

    public void c(String str) {
        this.f9463d = str;
    }

    public boolean c() {
        return this.f9462c;
    }

    public String d() {
        return this.f9463d;
    }

    public void d(String str) {
        this.f9464e = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String e() {
        return this.f9464e;
    }

    public String toString() {
        return "Statics{taskId='" + this.f9460a + CharPool.SINGLE_QUOTE + ", time='" + this.f9461b + CharPool.SINGLE_QUOTE + ", pushExtra=" + this.f9462c + ", deviceId='" + this.f9463d + CharPool.SINGLE_QUOTE + ", seqId='" + this.f9464e + CharPool.SINGLE_QUOTE + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f9460a);
        parcel.writeString(this.f9461b);
        parcel.writeByte(this.f9462c ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f9463d);
        parcel.writeString(this.f9464e);
    }
}
