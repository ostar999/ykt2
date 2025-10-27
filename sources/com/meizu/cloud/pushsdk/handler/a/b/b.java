package com.meizu.cloud.pushsdk.handler.a.b;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b implements Parcelable {
    public static final Parcelable.Creator<b> CREATOR = new Parcelable.Creator<b>() { // from class: com.meizu.cloud.pushsdk.handler.a.b.b.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public b createFromParcel(Parcel parcel) {
            return new b(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public b[] newArray(int i2) {
            return new b[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private String f9445a;

    /* renamed from: b, reason: collision with root package name */
    private a f9446b;

    /* renamed from: c, reason: collision with root package name */
    private e f9447c;

    public b() {
    }

    public b(Parcel parcel) {
        this.f9445a = parcel.readString();
        this.f9446b = (a) parcel.readParcelable(a.class.getClassLoader());
        this.f9447c = (e) parcel.readParcelable(e.class.getClassLoader());
    }

    public b(String str, String str2, String str3) {
        this.f9445a = str;
        if (TextUtils.isEmpty(str)) {
            this.f9446b = new a();
            this.f9447c = new e();
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f9446b = a.a(jSONObject.getJSONObject("ctl"));
            e eVarA = e.a(jSONObject.getJSONObject("statics"));
            this.f9447c = eVarA;
            eVarA.c(str2);
            this.f9447c.d(str3);
        } catch (JSONException e2) {
            this.f9446b = new a();
            this.f9447c = new e();
            DebugLogger.e("ControlMessage", "parse control message error " + e2.getMessage());
        }
    }

    public static b a(String str) {
        b bVar = new b();
        try {
            JSONObject jSONObject = new JSONObject(str);
            bVar.a(a.a(jSONObject.getJSONObject("ctl")));
            bVar.a(e.a(jSONObject.getJSONObject("statics")));
        } catch (Exception e2) {
            DebugLogger.e("ControlMessage", "parse control message error " + e2.getMessage());
            bVar.a(new e());
            bVar.a(new a());
        }
        return bVar;
    }

    public a a() {
        return this.f9446b;
    }

    public void a(a aVar) {
        this.f9446b = aVar;
    }

    public void a(e eVar) {
        this.f9447c = eVar;
    }

    public e b() {
        return this.f9447c;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ControlMessage{controlMessage='" + this.f9445a + CharPool.SINGLE_QUOTE + ", control=" + this.f9446b + ", statics=" + this.f9447c + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f9445a);
        parcel.writeParcelable(this.f9446b, i2);
        parcel.writeParcelable(this.f9447c, i2);
    }
}
