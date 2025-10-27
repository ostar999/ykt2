package com.tencent.bugly.crashreport.crash;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.proguard.ap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes6.dex */
public class CrashDetailBean implements Parcelable, Comparable<CrashDetailBean> {
    public static final Parcelable.Creator<CrashDetailBean> CREATOR = new Parcelable.Creator<CrashDetailBean>() { // from class: com.tencent.bugly.crashreport.crash.CrashDetailBean.1
        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ CrashDetailBean createFromParcel(Parcel parcel) {
            return new CrashDetailBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ CrashDetailBean[] newArray(int i2) {
            return new CrashDetailBean[i2];
        }
    };
    public String A;
    public String B;
    public long C;
    public long D;
    public long E;
    public long F;
    public long G;
    public long H;
    public long I;
    public long J;
    public long K;
    public String L;
    public String M;
    public String N;
    public String O;
    public String P;
    public long Q;
    public boolean R;
    public Map<String, String> S;
    public Map<String, String> T;
    public int U;
    public int V;
    public Map<String, String> W;
    public Map<String, String> X;
    public byte[] Y;
    public String Z;

    /* renamed from: a, reason: collision with root package name */
    public long f17359a;
    public String aa;

    /* renamed from: b, reason: collision with root package name */
    public int f17360b;

    /* renamed from: c, reason: collision with root package name */
    public String f17361c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f17362d;

    /* renamed from: e, reason: collision with root package name */
    public String f17363e;

    /* renamed from: f, reason: collision with root package name */
    public String f17364f;

    /* renamed from: g, reason: collision with root package name */
    public String f17365g;

    /* renamed from: h, reason: collision with root package name */
    public Map<String, PlugInBean> f17366h;

    /* renamed from: i, reason: collision with root package name */
    public Map<String, PlugInBean> f17367i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f17368j;

    /* renamed from: k, reason: collision with root package name */
    public boolean f17369k;

    /* renamed from: l, reason: collision with root package name */
    public int f17370l;

    /* renamed from: m, reason: collision with root package name */
    public String f17371m;

    /* renamed from: n, reason: collision with root package name */
    public String f17372n;

    /* renamed from: o, reason: collision with root package name */
    public String f17373o;

    /* renamed from: p, reason: collision with root package name */
    public String f17374p;

    /* renamed from: q, reason: collision with root package name */
    public String f17375q;

    /* renamed from: r, reason: collision with root package name */
    public long f17376r;

    /* renamed from: s, reason: collision with root package name */
    public String f17377s;

    /* renamed from: t, reason: collision with root package name */
    public int f17378t;

    /* renamed from: u, reason: collision with root package name */
    public String f17379u;

    /* renamed from: v, reason: collision with root package name */
    public String f17380v;

    /* renamed from: w, reason: collision with root package name */
    public String f17381w;

    /* renamed from: x, reason: collision with root package name */
    public String f17382x;

    /* renamed from: y, reason: collision with root package name */
    public byte[] f17383y;

    /* renamed from: z, reason: collision with root package name */
    public Map<String, String> f17384z;

    public CrashDetailBean() {
        this.f17359a = -1L;
        this.f17360b = 0;
        this.f17361c = UUID.randomUUID().toString();
        this.f17362d = false;
        this.f17363e = "";
        this.f17364f = "";
        this.f17365g = "";
        this.f17366h = null;
        this.f17367i = null;
        this.f17368j = false;
        this.f17369k = false;
        this.f17370l = 0;
        this.f17371m = "";
        this.f17372n = "";
        this.f17373o = "";
        this.f17374p = "";
        this.f17375q = "";
        this.f17376r = -1L;
        this.f17377s = null;
        this.f17378t = 0;
        this.f17379u = "";
        this.f17380v = "";
        this.f17381w = null;
        this.f17382x = null;
        this.f17383y = null;
        this.f17384z = null;
        this.A = "";
        this.B = "";
        this.C = -1L;
        this.D = -1L;
        this.E = -1L;
        this.F = -1L;
        this.G = -1L;
        this.H = -1L;
        this.I = -1L;
        this.J = -1L;
        this.K = -1L;
        this.L = "";
        this.M = "";
        this.N = "";
        this.O = "";
        this.P = "";
        this.Q = -1L;
        this.R = false;
        this.S = null;
        this.T = null;
        this.U = -1;
        this.V = -1;
        this.W = null;
        this.X = null;
        this.Y = null;
        this.Z = null;
        this.aa = null;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(CrashDetailBean crashDetailBean) {
        CrashDetailBean crashDetailBean2 = crashDetailBean;
        if (crashDetailBean2 == null) {
            return 1;
        }
        long j2 = this.f17376r - crashDetailBean2.f17376r;
        if (j2 <= 0) {
            return j2 < 0 ? -1 : 0;
        }
        return 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f17360b);
        parcel.writeString(this.f17361c);
        parcel.writeByte(this.f17362d ? (byte) 1 : (byte) 0);
        parcel.writeString(this.f17363e);
        parcel.writeString(this.f17364f);
        parcel.writeString(this.f17365g);
        parcel.writeByte(this.f17368j ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.f17369k ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.f17370l);
        parcel.writeString(this.f17371m);
        parcel.writeString(this.f17372n);
        parcel.writeString(this.f17373o);
        parcel.writeString(this.f17374p);
        parcel.writeString(this.f17375q);
        parcel.writeLong(this.f17376r);
        parcel.writeString(this.f17377s);
        parcel.writeInt(this.f17378t);
        parcel.writeString(this.f17379u);
        parcel.writeString(this.f17380v);
        parcel.writeString(this.f17381w);
        ap.b(parcel, this.f17384z);
        parcel.writeString(this.A);
        parcel.writeString(this.B);
        parcel.writeLong(this.C);
        parcel.writeLong(this.D);
        parcel.writeLong(this.E);
        parcel.writeLong(this.F);
        parcel.writeLong(this.G);
        parcel.writeLong(this.H);
        parcel.writeString(this.L);
        parcel.writeString(this.M);
        parcel.writeString(this.N);
        parcel.writeString(this.O);
        parcel.writeString(this.P);
        parcel.writeLong(this.Q);
        parcel.writeByte(this.R ? (byte) 1 : (byte) 0);
        ap.b(parcel, this.S);
        ap.a(parcel, this.f17366h);
        ap.a(parcel, this.f17367i);
        parcel.writeInt(this.U);
        parcel.writeInt(this.V);
        ap.b(parcel, this.W);
        ap.b(parcel, this.X);
        parcel.writeByteArray(this.Y);
        parcel.writeByteArray(this.f17383y);
        parcel.writeString(this.Z);
        parcel.writeString(this.aa);
        parcel.writeString(this.f17382x);
        parcel.writeLong(this.I);
        parcel.writeLong(this.J);
        parcel.writeLong(this.K);
    }

    public CrashDetailBean(Parcel parcel) {
        this.f17359a = -1L;
        this.f17360b = 0;
        this.f17361c = UUID.randomUUID().toString();
        this.f17362d = false;
        this.f17363e = "";
        this.f17364f = "";
        this.f17365g = "";
        this.f17366h = null;
        this.f17367i = null;
        this.f17368j = false;
        this.f17369k = false;
        this.f17370l = 0;
        this.f17371m = "";
        this.f17372n = "";
        this.f17373o = "";
        this.f17374p = "";
        this.f17375q = "";
        this.f17376r = -1L;
        this.f17377s = null;
        this.f17378t = 0;
        this.f17379u = "";
        this.f17380v = "";
        this.f17381w = null;
        this.f17382x = null;
        this.f17383y = null;
        this.f17384z = null;
        this.A = "";
        this.B = "";
        this.C = -1L;
        this.D = -1L;
        this.E = -1L;
        this.F = -1L;
        this.G = -1L;
        this.H = -1L;
        this.I = -1L;
        this.J = -1L;
        this.K = -1L;
        this.L = "";
        this.M = "";
        this.N = "";
        this.O = "";
        this.P = "";
        this.Q = -1L;
        this.R = false;
        this.S = null;
        this.T = null;
        this.U = -1;
        this.V = -1;
        this.W = null;
        this.X = null;
        this.Y = null;
        this.Z = null;
        this.aa = null;
        this.f17360b = parcel.readInt();
        this.f17361c = parcel.readString();
        this.f17362d = parcel.readByte() == 1;
        this.f17363e = parcel.readString();
        this.f17364f = parcel.readString();
        this.f17365g = parcel.readString();
        this.f17368j = parcel.readByte() == 1;
        this.f17369k = parcel.readByte() == 1;
        this.f17370l = parcel.readInt();
        this.f17371m = parcel.readString();
        this.f17372n = parcel.readString();
        this.f17373o = parcel.readString();
        this.f17374p = parcel.readString();
        this.f17375q = parcel.readString();
        this.f17376r = parcel.readLong();
        this.f17377s = parcel.readString();
        this.f17378t = parcel.readInt();
        this.f17379u = parcel.readString();
        this.f17380v = parcel.readString();
        this.f17381w = parcel.readString();
        this.f17384z = ap.b(parcel);
        this.A = parcel.readString();
        this.B = parcel.readString();
        this.C = parcel.readLong();
        this.D = parcel.readLong();
        this.E = parcel.readLong();
        this.F = parcel.readLong();
        this.G = parcel.readLong();
        this.H = parcel.readLong();
        this.L = parcel.readString();
        this.M = parcel.readString();
        this.N = parcel.readString();
        this.O = parcel.readString();
        this.P = parcel.readString();
        this.Q = parcel.readLong();
        this.R = parcel.readByte() == 1;
        this.S = ap.b(parcel);
        this.f17366h = ap.a(parcel);
        this.f17367i = ap.a(parcel);
        this.U = parcel.readInt();
        this.V = parcel.readInt();
        this.W = ap.b(parcel);
        this.X = ap.b(parcel);
        this.Y = parcel.createByteArray();
        this.f17383y = parcel.createByteArray();
        this.Z = parcel.readString();
        this.aa = parcel.readString();
        this.f17382x = parcel.readString();
        this.I = parcel.readLong();
        this.J = parcel.readLong();
        this.K = parcel.readLong();
    }
}
