package androidx.camera.core.impl;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
final class AutoValue_Identifier extends Identifier {
    private final Object value;

    public AutoValue_Identifier(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Null value");
        }
        this.value = obj;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Identifier) {
            return this.value.equals(((Identifier) obj).getValue());
        }
        return false;
    }

    @Override // androidx.camera.core.impl.Identifier
    @NonNull
    public Object getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode() ^ 1000003;
    }

    public String toString() {
        return "Identifier{value=" + this.value + "}";
    }
}
