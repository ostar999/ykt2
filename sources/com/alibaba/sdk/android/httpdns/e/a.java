package com.alibaba.sdk.android.httpdns.e;

/* loaded from: classes2.dex */
public class a implements q {

    /* renamed from: a, reason: collision with root package name */
    private EnumC0018a f2728a = EnumC0018a.NORMAL;

    /* renamed from: a, reason: collision with other field name */
    private j f34a;

    /* renamed from: a, reason: collision with other field name */
    private o f35a;

    /* renamed from: com.alibaba.sdk.android.httpdns.e.a$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: c, reason: collision with root package name */
        static final /* synthetic */ int[] f2729c;

        static {
            int[] iArr = new int[EnumC0018a.values().length];
            f2729c = iArr;
            try {
                iArr[EnumC0018a.DISABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2729c[EnumC0018a.NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2729c[EnumC0018a.PRE_DISABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* renamed from: com.alibaba.sdk.android.httpdns.e.a$a, reason: collision with other inner class name */
    public enum EnumC0018a {
        NORMAL,
        PRE_DISABLE,
        DISABLE
    }

    public a(com.alibaba.sdk.android.httpdns.h.a aVar) {
        this.f34a = new j(aVar, this);
        this.f35a = new o(aVar, this);
    }

    public c a() {
        return AnonymousClass1.f2729c[this.f2728a.ordinal()] != 1 ? this.f34a : this.f35a;
    }

    @Override // com.alibaba.sdk.android.httpdns.e.q
    public void c() {
        EnumC0018a enumC0018a;
        int i2 = AnonymousClass1.f2729c[this.f2728a.ordinal()];
        if (i2 == 2) {
            enumC0018a = EnumC0018a.PRE_DISABLE;
        } else if (i2 != 3) {
            return;
        } else {
            enumC0018a = EnumC0018a.DISABLE;
        }
        this.f2728a = enumC0018a;
    }

    @Override // com.alibaba.sdk.android.httpdns.e.q
    public void d() {
        this.f2728a = EnumC0018a.NORMAL;
    }

    public void reset() {
        this.f2728a = EnumC0018a.NORMAL;
        this.f35a.reset();
    }
}
