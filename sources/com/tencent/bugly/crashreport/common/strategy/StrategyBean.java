package com.tencent.bugly.crashreport.common.strategy;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.proguard.ap;
import java.util.Map;

/* loaded from: classes6.dex */
public class StrategyBean implements Parcelable {
    public static final Parcelable.Creator<StrategyBean> CREATOR = new Parcelable.Creator<StrategyBean>() { // from class: com.tencent.bugly.crashreport.common.strategy.StrategyBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ StrategyBean createFromParcel(Parcel parcel) {
            return new StrategyBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ StrategyBean[] newArray(int i2) {
            return new StrategyBean[i2];
        }
    };

    /* renamed from: a, reason: collision with root package name */
    public static String f17336a = "https://android.bugly.qq.com/rqd/async";

    /* renamed from: b, reason: collision with root package name */
    public static String f17337b = "https://android.bugly.qq.com/rqd/async";

    /* renamed from: c, reason: collision with root package name */
    public static String f17338c;

    /* renamed from: d, reason: collision with root package name */
    public long f17339d;

    /* renamed from: e, reason: collision with root package name */
    public long f17340e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f17341f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f17342g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f17343h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f17344i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f17345j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f17346k;

    /* renamed from: l, reason: collision with root package name */
    public boolean f17347l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f17348m;

    /* renamed from: n, reason: collision with root package name */
    public boolean f17349n;

    /* renamed from: o, reason: collision with root package name */
    public long f17350o;

    /* renamed from: p, reason: collision with root package name */
    public long f17351p;

    /* renamed from: q, reason: collision with root package name */
    public String f17352q;

    /* renamed from: r, reason: collision with root package name */
    public String f17353r;

    /* renamed from: s, reason: collision with root package name */
    public String f17354s;

    /* renamed from: t, reason: collision with root package name */
    public Map<String, String> f17355t;

    /* renamed from: u, reason: collision with root package name */
    public int f17356u;

    /* renamed from: v, reason: collision with root package name */
    public long f17357v;

    /* renamed from: w, reason: collision with root package name */
    public long f17358w;

    public StrategyBean() {
        this.f17339d = -1L;
        this.f17340e = -1L;
        this.f17341f = true;
        this.f17342g = true;
        this.f17343h = true;
        this.f17344i = true;
        this.f17345j = false;
        this.f17346k = true;
        this.f17347l = true;
        this.f17348m = true;
        this.f17349n = true;
        this.f17351p = 30000L;
        this.f17352q = f17336a;
        this.f17353r = f17337b;
        this.f17356u = 10;
        this.f17357v = 300000L;
        this.f17358w = -1L;
        this.f17340e = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("S(@L@L@)");
        f17338c = sb.toString();
        sb.setLength(0);
        sb.append("*^@K#K@!");
        this.f17354s = sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.f17340e);
        parcel.writeByte(this.f17341f ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f17342g ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f17343h ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f17352q);
        parcel.writeString(this.f17353r);
        parcel.writeString(this.f17354s);
        ap.b(parcel, this.f17355t);
        parcel.writeByte(this.f17344i ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f17345j ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f17348m ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f17349n ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.f17351p);
        parcel.writeByte(this.f17346k ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f17347l ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.f17350o);
        parcel.writeInt(this.f17356u);
        parcel.writeLong(this.f17357v);
        parcel.writeLong(this.f17358w);
    }

    public StrategyBean(Parcel parcel) {
        this.f17339d = -1L;
        this.f17340e = -1L;
        boolean z2 = true;
        this.f17341f = true;
        this.f17342g = true;
        this.f17343h = true;
        this.f17344i = true;
        this.f17345j = false;
        this.f17346k = true;
        this.f17347l = true;
        this.f17348m = true;
        this.f17349n = true;
        this.f17351p = 30000L;
        this.f17352q = f17336a;
        this.f17353r = f17337b;
        this.f17356u = 10;
        this.f17357v = 300000L;
        this.f17358w = -1L;
        try {
            f17338c = "S(@L@L@)";
            this.f17340e = parcel.readLong();
            this.f17341f = parcel.readByte() == 1;
            this.f17342g = parcel.readByte() == 1;
            this.f17343h = parcel.readByte() == 1;
            this.f17352q = parcel.readString();
            this.f17353r = parcel.readString();
            this.f17354s = parcel.readString();
            this.f17355t = ap.b(parcel);
            this.f17344i = parcel.readByte() == 1;
            this.f17345j = parcel.readByte() == 1;
            this.f17348m = parcel.readByte() == 1;
            this.f17349n = parcel.readByte() == 1;
            this.f17351p = parcel.readLong();
            this.f17346k = parcel.readByte() == 1;
            if (parcel.readByte() != 1) {
                z2 = false;
            }
            this.f17347l = z2;
            this.f17350o = parcel.readLong();
            this.f17356u = parcel.readInt();
            this.f17357v = parcel.readLong();
            this.f17358w = parcel.readLong();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
