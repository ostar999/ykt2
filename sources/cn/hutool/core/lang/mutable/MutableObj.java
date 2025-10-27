package cn.hutool.core.lang.mutable;

import cn.hutool.core.util.ObjectUtil;
import java.io.Serializable;

/* loaded from: classes.dex */
public class MutableObj<T> implements Mutable<T>, Serializable {
    private static final long serialVersionUID = 1;
    private T value;

    public MutableObj() {
    }

    public static <T> MutableObj<T> of(T t2) {
        return new MutableObj<>(t2);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() == obj.getClass()) {
            return ObjectUtil.equals(this.value, ((MutableObj) obj).value);
        }
        return false;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public T get() {
        return this.value;
    }

    public int hashCode() {
        T t2 = this.value;
        if (t2 == null) {
            return 0;
        }
        return t2.hashCode();
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(T t2) {
        this.value = t2;
    }

    public String toString() {
        T t2 = this.value;
        return t2 == null ? "null" : t2.toString();
    }

    public MutableObj(T t2) {
        this.value = t2;
    }
}
