package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public abstract class ConstantValue<T> {
    private final T value;

    public ConstantValue(T t2) {
        this.value = t2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            T value = getValue();
            ConstantValue constantValue = obj instanceof ConstantValue ? (ConstantValue) obj : null;
            if (!Intrinsics.areEqual(value, constantValue != null ? constantValue.getValue() : null)) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public abstract KotlinType getType(@NotNull ModuleDescriptor moduleDescriptor);

    public T getValue() {
        return this.value;
    }

    public int hashCode() {
        T value = getValue();
        if (value != null) {
            return value.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return String.valueOf(getValue());
    }
}
