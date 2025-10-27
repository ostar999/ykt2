package com.google.android.gms.common.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
/* loaded from: classes3.dex */
public final class Objects {

    @KeepForSdk
    public static final class ToStringHelper {
        private final List<String> zzer;
        private final Object zzes;

        private ToStringHelper(Object obj) {
            this.zzes = Preconditions.checkNotNull(obj);
            this.zzer = new ArrayList();
        }

        @KeepForSdk
        public final ToStringHelper add(String str, @Nullable Object obj) {
            List<String> list = this.zzer;
            String str2 = (String) Preconditions.checkNotNull(str);
            String strValueOf = String.valueOf(obj);
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + strValueOf.length());
            sb.append(str2);
            sb.append("=");
            sb.append(strValueOf);
            list.add(sb.toString());
            return this;
        }

        @KeepForSdk
        public final String toString() {
            StringBuilder sb = new StringBuilder(100);
            sb.append(this.zzes.getClass().getSimpleName());
            sb.append('{');
            int size = this.zzer.size();
            for (int i2 = 0; i2 < size; i2++) {
                sb.append(this.zzer.get(i2));
                if (i2 < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append('}');
            return sb.toString();
        }
    }

    private Objects() {
        throw new AssertionError("Uninstantiable");
    }

    @KeepForSdk
    public static boolean equal(@Nullable Object obj, @Nullable Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    @KeepForSdk
    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    @KeepForSdk
    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj);
    }
}
