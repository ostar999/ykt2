package f;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes8.dex */
public class f implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    public String f26898a;

    /* renamed from: b, reason: collision with root package name */
    public Object f26899b;

    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f26900a;

        static {
            int[] iArr = new int[f.a.values().length];
            f26900a = iArr;
            try {
                iArr[f.a.BUSINESS_EVENT_STARTPREVIEW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        public f.a f26901a;

        /* renamed from: b, reason: collision with root package name */
        public ArrayList f26902b;

        public b(f.a aVar, ArrayList arrayList) {
            this.f26901a = aVar;
            this.f26902b = arrayList;
        }

        public f a() {
            f fVar = new f();
            int i2 = a.f26900a[this.f26901a.ordinal()];
            return fVar;
        }
    }

    public f(String str, Object obj) {
        this.f26898a = str;
        this.f26899b = obj;
    }

    public f() {
    }
}
