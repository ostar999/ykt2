package com.google.firebase.components;

/* loaded from: classes4.dex */
public final class Dependency {
    private final Class<?> anInterface;
    private final int injection;
    private final int type;

    private Dependency(Class<?> cls, int i2, int i3) {
        this.anInterface = (Class) Preconditions.checkNotNull(cls, "Null dependency anInterface.");
        this.type = i2;
        this.injection = i3;
    }

    public static Dependency optional(Class<?> cls) {
        return new Dependency(cls, 0, 0);
    }

    public static Dependency optionalProvider(Class<?> cls) {
        return new Dependency(cls, 0, 1);
    }

    public static Dependency required(Class<?> cls) {
        return new Dependency(cls, 1, 0);
    }

    public static Dependency requiredProvider(Class<?> cls) {
        return new Dependency(cls, 1, 1);
    }

    public static Dependency setOf(Class<?> cls) {
        return new Dependency(cls, 2, 0);
    }

    public static Dependency setOfProvider(Class<?> cls) {
        return new Dependency(cls, 2, 1);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Dependency)) {
            return false;
        }
        Dependency dependency = (Dependency) obj;
        return this.anInterface == dependency.anInterface && this.type == dependency.type && this.injection == dependency.injection;
    }

    public Class<?> getInterface() {
        return this.anInterface;
    }

    public int hashCode() {
        return ((((this.anInterface.hashCode() ^ 1000003) * 1000003) ^ this.type) * 1000003) ^ this.injection;
    }

    public boolean isDirectInjection() {
        return this.injection == 0;
    }

    public boolean isRequired() {
        return this.type == 1;
    }

    public boolean isSet() {
        return this.type == 2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Dependency{anInterface=");
        sb.append(this.anInterface);
        sb.append(", type=");
        int i2 = this.type;
        sb.append(i2 == 1 ? "required" : i2 == 0 ? "optional" : "set");
        sb.append(", direct=");
        sb.append(this.injection == 0);
        sb.append("}");
        return sb.toString();
    }
}
