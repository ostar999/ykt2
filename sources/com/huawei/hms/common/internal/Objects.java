package com.huawei.hms.common.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public final class Objects {

    public static final class ToStringHelper {

        /* renamed from: a, reason: collision with root package name */
        public final List<String> f7553a;

        /* renamed from: b, reason: collision with root package name */
        public final Object f7554b;

        public final ToStringHelper add(String str, Object obj) {
            String str2 = (String) Preconditions.checkNotNull(str);
            String strValueOf = String.valueOf(obj);
            StringBuilder sb = new StringBuilder(str2.length() + strValueOf.length() + 1);
            sb.append(str2);
            sb.append("=");
            sb.append(strValueOf);
            this.f7553a.add(sb.toString());
            return this;
        }

        public final String toString() {
            String simpleName = this.f7554b.getClass().getSimpleName();
            StringBuilder sb = new StringBuilder(100);
            sb.append(simpleName);
            sb.append('{');
            int size = this.f7553a.size();
            for (int i2 = 0; i2 < size; i2++) {
                sb.append(this.f7553a.get(i2));
                if (i2 < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append('}');
            return sb.toString();
        }

        public ToStringHelper(Object obj) {
            this.f7554b = Preconditions.checkNotNull(obj);
            this.f7553a = new ArrayList();
        }
    }

    public Objects() {
        throw new AssertionError("Uninstantiable");
    }

    public static boolean equal(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        return obj != null && obj.equals(obj2);
    }

    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj);
    }
}
