package com.huawei.hms.core.aidl;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class DataBuffer implements Parcelable {
    public static final Parcelable.Creator<DataBuffer> CREATOR = new a();
    public String URI;

    /* renamed from: a, reason: collision with root package name */
    private int f7555a;

    /* renamed from: b, reason: collision with root package name */
    private Bundle f7556b;
    public Bundle header;

    public static class a implements Parcelable.Creator<DataBuffer> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataBuffer createFromParcel(Parcel parcel) {
            return new DataBuffer(parcel, (a) null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataBuffer[] newArray(int i2) {
            return new DataBuffer[i2];
        }
    }

    public /* synthetic */ DataBuffer(Parcel parcel, a aVar) {
        this(parcel);
    }

    private static ClassLoader a(Class cls) {
        return cls.getClassLoader();
    }

    public DataBuffer addBody(Bundle bundle) {
        this.f7556b = bundle;
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bundle getBody() {
        return this.f7556b;
    }

    public int getBodySize() {
        return this.f7556b == null ? 0 : 1;
    }

    public int getProtocol() {
        return this.f7555a;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f7555a);
        parcel.writeString(this.URI);
        parcel.writeBundle(this.header);
        parcel.writeBundle(this.f7556b);
    }

    private DataBuffer(Parcel parcel) {
        this.header = null;
        this.f7555a = 1;
        this.f7556b = null;
        a(parcel);
    }

    private void a(Parcel parcel) {
        this.f7555a = parcel.readInt();
        this.URI = parcel.readString();
        this.header = parcel.readBundle(a(Bundle.class));
        this.f7556b = parcel.readBundle(a(Bundle.class));
    }

    public DataBuffer() {
        this.header = null;
        this.f7555a = 1;
        this.f7556b = null;
    }

    public DataBuffer(String str) {
        this.header = null;
        this.f7555a = 1;
        this.f7556b = null;
        this.URI = str;
    }

    public DataBuffer(String str, int i2) {
        this.header = null;
        this.f7556b = null;
        this.URI = str;
        this.f7555a = i2;
    }
}
