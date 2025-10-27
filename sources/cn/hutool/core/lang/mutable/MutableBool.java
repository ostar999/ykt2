package cn.hutool.core.lang.mutable;

import java.io.Serializable;

/* loaded from: classes.dex */
public class MutableBool implements Comparable<MutableBool>, Mutable<Boolean>, Serializable {
    private static final long serialVersionUID = 1;
    private boolean value;

    public MutableBool() {
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableBool) && this.value == ((MutableBool) obj).value;
    }

    public int hashCode() {
        return (this.value ? Boolean.TRUE : Boolean.FALSE).hashCode();
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableBool(boolean z2) {
        this.value = z2;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableBool mutableBool) {
        return Boolean.compare(this.value, mutableBool.value);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.lang.mutable.Mutable
    public Boolean get() {
        return Boolean.valueOf(this.value);
    }

    public void set(boolean z2) {
        this.value = z2;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Boolean bool) {
        this.value = bool.booleanValue();
    }

    public MutableBool(String str) throws NumberFormatException {
        this.value = Boolean.parseBoolean(str);
    }
}
