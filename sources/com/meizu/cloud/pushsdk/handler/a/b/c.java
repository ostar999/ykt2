package com.meizu.cloud.pushsdk.handler.a.b;

import android.os.Parcel;
import android.os.Parcelable;
import cn.hutool.core.text.CharPool;
import com.meizu.cloud.pushsdk.handler.MessageV3;

/* loaded from: classes4.dex */
public class c implements Parcelable {
    public static final Parcelable.Creator<c> CREATOR = new Parcelable.Creator<c>() { // from class: com.meizu.cloud.pushsdk.handler.a.b.c.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c createFromParcel(Parcel parcel) {
            return new c(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c[] newArray(int i2) {
            return new c[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private MessageV3 f9448a;

    /* renamed from: b, reason: collision with root package name */
    private String f9449b;

    /* renamed from: c, reason: collision with root package name */
    private int f9450c;

    /* renamed from: d, reason: collision with root package name */
    private int f9451d;

    public c(Parcel parcel) {
        this.f9448a = (MessageV3) parcel.readParcelable(MessageV3.class.getClassLoader());
        this.f9449b = parcel.readString();
        this.f9450c = parcel.readInt();
        this.f9451d = parcel.readInt();
    }

    public c(MessageV3 messageV3) {
        this.f9448a = messageV3;
    }

    public MessageV3 a() {
        return this.f9448a;
    }

    public void a(int i2) {
        this.f9450c = i2;
    }

    public void a(String str) {
        this.f9449b = str;
    }

    public int b() {
        return this.f9450c;
    }

    public void b(int i2) {
        this.f9451d = i2;
    }

    public int c() {
        return this.f9451d;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "NotificationState{messageV3=" + this.f9448a + ", notificationPkg='" + this.f9449b + CharPool.SINGLE_QUOTE + ", notificationId='" + this.f9450c + CharPool.SINGLE_QUOTE + ", state='" + this.f9451d + CharPool.SINGLE_QUOTE + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.f9448a, i2);
        parcel.writeString(this.f9449b);
        parcel.writeInt(this.f9450c);
        parcel.writeInt(this.f9451d);
    }
}
