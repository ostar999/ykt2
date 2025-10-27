package com.meizu.cloud.pushsdk.handler.a.b;

import android.os.Parcel;
import android.os.Parcelable;
import cn.hutool.core.text.CharPool;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class g implements Parcelable {
    public static final Parcelable.Creator<g> CREATOR = new Parcelable.Creator<g>() { // from class: com.meizu.cloud.pushsdk.handler.a.b.g.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public g createFromParcel(Parcel parcel) {
            return new g(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public g[] newArray(int i2) {
            return new g[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private b f9470a;

    /* renamed from: b, reason: collision with root package name */
    private String f9471b;

    /* renamed from: c, reason: collision with root package name */
    private int f9472c;

    public g(Parcel parcel) {
        this.f9470a = (b) parcel.readParcelable(b.class.getClassLoader());
        this.f9471b = parcel.readString();
        this.f9472c = parcel.readInt();
    }

    public g(String str, String str2, String str3, String str4, String str5) {
        this.f9471b = str2;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull(RemoteMessageConst.Notification.NOTIFY_ID)) {
                this.f9472c = jSONObject.getInt(RemoteMessageConst.Notification.NOTIFY_ID);
            }
        } catch (JSONException e2) {
            DebugLogger.e("WithDrawMessage", "parse WithDrawMessage error " + e2.getMessage());
        }
        this.f9470a = new b(str3, str4, str5);
    }

    public b a() {
        return this.f9470a;
    }

    public int b() {
        return this.f9472c;
    }

    public String c() {
        return this.f9471b;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "WithDrawMessage{controlMessage=" + this.f9470a + ", revokePackageName='" + this.f9471b + CharPool.SINGLE_QUOTE + ", notifyId=" + this.f9472c + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.f9470a, i2);
        parcel.writeString(this.f9471b);
        parcel.writeInt(this.f9472c);
    }
}
