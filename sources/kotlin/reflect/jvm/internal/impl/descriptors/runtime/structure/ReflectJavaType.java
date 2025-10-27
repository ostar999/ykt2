package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public abstract class ReflectJavaType implements JavaType {

    @NotNull
    public static final Factory Factory = new Factory(null);

    public static final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final ReflectJavaType create(@NotNull Type type) {
            Intrinsics.checkNotNullParameter(type, "type");
            boolean z2 = type instanceof Class;
            if (z2) {
                Class cls = (Class) type;
                if (cls.isPrimitive()) {
                    return new ReflectJavaPrimitiveType(cls);
                }
            }
            return ((type instanceof GenericArrayType) || (z2 && ((Class) type).isArray())) ? new ReflectJavaArrayType(type) : type instanceof WildcardType ? new ReflectJavaWildcardType((WildcardType) type) : new ReflectJavaClassifierType(type);
        }
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ReflectJavaType) && Intrinsics.areEqual(getReflectType(), ((ReflectJavaType) obj).getReflectType());
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner
    @Nullable
    public JavaAnnotation findAnnotation(FqName fqName) {
        Object obj;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = getAnnotations().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            ClassId classId = ((JavaAnnotation) next).getClassId();
            if (Intrinsics.areEqual(classId != null ? classId.asSingleFqName() : null, fqName)) {
                obj = next;
                break;
            }
        }
        return (JavaAnnotation) obj;
    }

    @NotNull
    public abstract Type getReflectType();

    public int hashCode() {
        return getReflectType().hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + getReflectType();
    }
}
