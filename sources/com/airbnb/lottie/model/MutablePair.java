package com.airbnb.lottie.model;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Pair;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes2.dex */
public class MutablePair<T> {

    @Nullable
    T first;

    @Nullable
    T second;

    private static boolean objectsEqual(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return objectsEqual(pair.first, this.first) && objectsEqual(pair.second, this.second);
    }

    public int hashCode() {
        T t2 = this.first;
        int iHashCode = t2 == null ? 0 : t2.hashCode();
        T t3 = this.second;
        return iHashCode ^ (t3 != null ? t3.hashCode() : 0);
    }

    public void set(T t2, T t3) {
        this.first = t2;
        this.second = t3;
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.first) + " " + String.valueOf(this.second) + "}";
    }
}
