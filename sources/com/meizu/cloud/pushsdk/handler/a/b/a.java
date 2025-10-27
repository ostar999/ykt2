package com.meizu.cloud.pushsdk.handler.a.b;

import android.os.Parcel;
import android.os.Parcelable;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a implements Parcelable {
    public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator<a>() { // from class: com.meizu.cloud.pushsdk.handler.a.b.a.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a createFromParcel(Parcel parcel) {
            return new a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a[] newArray(int i2) {
            return new a[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private int f9442a;

    /* renamed from: b, reason: collision with root package name */
    private int f9443b;

    /* renamed from: c, reason: collision with root package name */
    private int f9444c;

    public a() {
    }

    public a(Parcel parcel) {
        this.f9442a = parcel.readInt();
        this.f9443b = parcel.readInt();
        this.f9444c = parcel.readInt();
    }

    public static a a(JSONObject jSONObject) {
        String str;
        a aVar = new a();
        if (jSONObject != null) {
            try {
                if (!jSONObject.isNull("pushType")) {
                    aVar.a(jSONObject.getInt("pushType"));
                }
                if (!jSONObject.isNull("cached")) {
                    aVar.b(jSONObject.getInt("cached"));
                }
                if (!jSONObject.isNull("cacheNum")) {
                    aVar.c(jSONObject.getInt("cacheNum"));
                }
            } catch (JSONException e2) {
                str = " parse control message error " + e2.getMessage();
            }
            return aVar;
        }
        str = "no control message can parse ";
        DebugLogger.e("ctl", str);
        return aVar;
    }

    public int a() {
        return this.f9442a;
    }

    public void a(int i2) {
        this.f9442a = i2;
    }

    public void b(int i2) {
        this.f9443b = i2;
    }

    public void c(int i2) {
        this.f9444c = i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "Control{pushType=" + this.f9442a + ", cached=" + this.f9443b + ", cacheNum=" + this.f9444c + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f9442a);
        parcel.writeInt(this.f9443b);
        parcel.writeInt(this.f9444c);
    }
}
