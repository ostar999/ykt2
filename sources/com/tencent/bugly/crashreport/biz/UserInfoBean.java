package com.tencent.bugly.crashreport.biz;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.proguard.ap;
import java.util.Map;

/* loaded from: classes6.dex */
public class UserInfoBean implements Parcelable {
    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() { // from class: com.tencent.bugly.crashreport.biz.UserInfoBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ UserInfoBean createFromParcel(Parcel parcel) {
            return new UserInfoBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ UserInfoBean[] newArray(int i2) {
            return new UserInfoBean[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    public long f17314a;

    /* renamed from: b, reason: collision with root package name */
    public int f17315b;

    /* renamed from: c, reason: collision with root package name */
    public String f17316c;

    /* renamed from: d, reason: collision with root package name */
    public String f17317d;

    /* renamed from: e, reason: collision with root package name */
    public long f17318e;

    /* renamed from: f, reason: collision with root package name */
    public long f17319f;

    /* renamed from: g, reason: collision with root package name */
    public long f17320g;

    /* renamed from: h, reason: collision with root package name */
    public long f17321h;

    /* renamed from: i, reason: collision with root package name */
    public long f17322i;

    /* renamed from: j, reason: collision with root package name */
    public String f17323j;

    /* renamed from: k, reason: collision with root package name */
    public long f17324k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f17325l;

    /* renamed from: m, reason: collision with root package name */
    public String f17326m;

    /* renamed from: n, reason: collision with root package name */
    public String f17327n;

    /* renamed from: o, reason: collision with root package name */
    public int f17328o;

    /* renamed from: p, reason: collision with root package name */
    public int f17329p;

    /* renamed from: q, reason: collision with root package name */
    public int f17330q;

    /* renamed from: r, reason: collision with root package name */
    public Map<String, String> f17331r;

    /* renamed from: s, reason: collision with root package name */
    public Map<String, String> f17332s;

    public UserInfoBean() {
        this.f17324k = 0L;
        this.f17325l = false;
        this.f17326m = "unknown";
        this.f17329p = -1;
        this.f17330q = -1;
        this.f17331r = null;
        this.f17332s = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f17315b);
        parcel.writeString(this.f17316c);
        parcel.writeString(this.f17317d);
        parcel.writeLong(this.f17318e);
        parcel.writeLong(this.f17319f);
        parcel.writeLong(this.f17320g);
        parcel.writeLong(this.f17321h);
        parcel.writeLong(this.f17322i);
        parcel.writeString(this.f17323j);
        parcel.writeLong(this.f17324k);
        parcel.writeByte(this.f17325l ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f17326m);
        parcel.writeInt(this.f17329p);
        parcel.writeInt(this.f17330q);
        ap.b(parcel, this.f17331r);
        ap.b(parcel, this.f17332s);
        parcel.writeString(this.f17327n);
        parcel.writeInt(this.f17328o);
    }

    public UserInfoBean(Parcel parcel) {
        this.f17324k = 0L;
        this.f17325l = false;
        this.f17326m = "unknown";
        this.f17329p = -1;
        this.f17330q = -1;
        this.f17331r = null;
        this.f17332s = null;
        this.f17315b = parcel.readInt();
        this.f17316c = parcel.readString();
        this.f17317d = parcel.readString();
        this.f17318e = parcel.readLong();
        this.f17319f = parcel.readLong();
        this.f17320g = parcel.readLong();
        this.f17321h = parcel.readLong();
        this.f17322i = parcel.readLong();
        this.f17323j = parcel.readString();
        this.f17324k = parcel.readLong();
        this.f17325l = parcel.readByte() == 1;
        this.f17326m = parcel.readString();
        this.f17329p = parcel.readInt();
        this.f17330q = parcel.readInt();
        this.f17331r = ap.b(parcel);
        this.f17332s = ap.b(parcel);
        this.f17327n = parcel.readString();
        this.f17328o = parcel.readInt();
    }
}
