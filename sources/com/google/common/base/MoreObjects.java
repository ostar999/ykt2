package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes3.dex */
public final class MoreObjects {

    public static final class ToStringHelper {
        private final String className;
        private final ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;

        public static final class ValueHolder {

            @NullableDecl
            String name;

            @NullableDecl
            ValueHolder next;

            @NullableDecl
            Object value;

            private ValueHolder() {
            }
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder = new ValueHolder();
            this.holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, @NullableDecl Object obj) {
            return addHolder(str, obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(@NullableDecl Object obj) {
            return addHolder(obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public String toString() {
            boolean z2 = this.omitNullValues;
            StringBuilder sb = new StringBuilder(32);
            sb.append(this.className);
            sb.append('{');
            String str = "";
            for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                Object obj = valueHolder.value;
                if (!z2 || obj != null) {
                    sb.append(str);
                    String str2 = valueHolder.name;
                    if (str2 != null) {
                        sb.append(str2);
                        sb.append('=');
                    }
                    if (obj == null || !obj.getClass().isArray()) {
                        sb.append(obj);
                    } else {
                        String strDeepToString = Arrays.deepToString(new Object[]{obj});
                        sb.append((CharSequence) strDeepToString, 1, strDeepToString.length() - 1);
                    }
                    str = ", ";
                }
            }
            sb.append('}');
            return sb.toString();
        }

        private ToStringHelper(String str) {
            ValueHolder valueHolder = new ValueHolder();
            this.holderHead = valueHolder;
            this.holderTail = valueHolder;
            this.omitNullValues = false;
            this.className = (String) Preconditions.checkNotNull(str);
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, boolean z2) {
            return addHolder(str, String.valueOf(z2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(boolean z2) {
            return addHolder(String.valueOf(z2));
        }

        private ToStringHelper addHolder(@NullableDecl Object obj) {
            addHolder().value = obj;
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, char c3) {
            return addHolder(str, String.valueOf(c3));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(char c3) {
            return addHolder(String.valueOf(c3));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, double d3) {
            return addHolder(str, String.valueOf(d3));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(double d3) {
            return addHolder(String.valueOf(d3));
        }

        private ToStringHelper addHolder(String str, @NullableDecl Object obj) {
            ValueHolder valueHolderAddHolder = addHolder();
            valueHolderAddHolder.value = obj;
            valueHolderAddHolder.name = (String) Preconditions.checkNotNull(str);
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, float f2) {
            return addHolder(str, String.valueOf(f2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(float f2) {
            return addHolder(String.valueOf(f2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, int i2) {
            return addHolder(str, String.valueOf(i2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(int i2) {
            return addHolder(String.valueOf(i2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, long j2) {
            return addHolder(str, String.valueOf(j2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(long j2) {
            return addHolder(String.valueOf(j2));
        }
    }

    private MoreObjects() {
    }

    public static <T> T firstNonNull(@NullableDecl T t2, @NullableDecl T t3) {
        if (t2 != null) {
            return t2;
        }
        if (t3 != null) {
            return t3;
        }
        throw new NullPointerException("Both parameters are null");
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj.getClass().getSimpleName());
    }

    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(cls.getSimpleName());
    }

    public static ToStringHelper toStringHelper(String str) {
        return new ToStringHelper(str);
    }
}
