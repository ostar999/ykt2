package com.tencent.bugly.crashreport.common.info;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class PlugInBean implements Parcelable {
    public static final Parcelable.Creator<PlugInBean> CREATOR = new Parcelable.Creator<PlugInBean>() { // from class: com.tencent.bugly.crashreport.common.info.PlugInBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ PlugInBean createFromParcel(Parcel parcel) {
            return new PlugInBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ PlugInBean[] newArray(int i2) {
            return new PlugInBean[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    public final String f17333a;

    /* renamed from: b, reason: collision with root package name */
    public final String f17334b;

    /* renamed from: c, reason: collision with root package name */
    public final String f17335c;

    public PlugInBean(String str, String str2, String str3) {
        this.f17333a = str;
        this.f17334b = str2;
        this.f17335c = str3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "plid:" + this.f17333a + " plV:" + this.f17334b + " plUUID:" + this.f17335c;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f17333a);
        parcel.writeString(this.f17334b);
        parcel.writeString(this.f17335c);
    }

    public PlugInBean(Parcel parcel) {
        this.f17333a = parcel.readString();
        this.f17334b = parcel.readString();
        this.f17335c = parcel.readString();
    }
}
