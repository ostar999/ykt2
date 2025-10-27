package cn.hutool.core.builder;

import java.io.Serializable;

/* loaded from: classes.dex */
final class IDKey implements Serializable {
    private static final long serialVersionUID = 1;
    private final int id;
    private final Object value;

    public IDKey(Object obj) {
        this.id = System.identityHashCode(obj);
        this.value = obj;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IDKey)) {
            return false;
        }
        IDKey iDKey = (IDKey) obj;
        return this.id == iDKey.id && this.value == iDKey.value;
    }

    public int hashCode() {
        return this.id;
    }
}
