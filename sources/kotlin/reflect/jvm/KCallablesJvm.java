package kotlin.reflect.jvm;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\",\u0010\u0002\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"value", "", "isAccessible", "Lkotlin/reflect/KCallable;", "(Lkotlin/reflect/KCallable;)Z", "setAccessible", "(Lkotlin/reflect/KCallable;Z)V", "kotlin-reflection"}, k = 2, mv = {1, 8, 0}, xi = 48)
@JvmName(name = "KCallablesJvm")
/* loaded from: classes8.dex */
public final class KCallablesJvm {
    public static final boolean isAccessible(@NotNull KCallable<?> kCallable) {
        Caller<?> defaultCaller;
        Intrinsics.checkNotNullParameter(kCallable, "<this>");
        if (kCallable instanceof KMutableProperty) {
            KProperty kProperty = (KProperty) kCallable;
            Field javaField = ReflectJvmMapping.getJavaField(kProperty);
            if (!(javaField != null ? javaField.isAccessible() : true)) {
                return false;
            }
            Method javaGetter = ReflectJvmMapping.getJavaGetter(kProperty);
            if (!(javaGetter != null ? javaGetter.isAccessible() : true)) {
                return false;
            }
            Method javaSetter = ReflectJvmMapping.getJavaSetter((KMutableProperty) kCallable);
            if (!(javaSetter != null ? javaSetter.isAccessible() : true)) {
                return false;
            }
        } else if (kCallable instanceof KProperty) {
            KProperty kProperty2 = (KProperty) kCallable;
            Field javaField2 = ReflectJvmMapping.getJavaField(kProperty2);
            if (!(javaField2 != null ? javaField2.isAccessible() : true)) {
                return false;
            }
            Method javaGetter2 = ReflectJvmMapping.getJavaGetter(kProperty2);
            if (!(javaGetter2 != null ? javaGetter2.isAccessible() : true)) {
                return false;
            }
        } else if (kCallable instanceof KProperty.Getter) {
            Field javaField3 = ReflectJvmMapping.getJavaField(((KProperty.Getter) kCallable).getProperty());
            if (!(javaField3 != null ? javaField3.isAccessible() : true)) {
                return false;
            }
            Method javaMethod = ReflectJvmMapping.getJavaMethod((KFunction) kCallable);
            if (!(javaMethod != null ? javaMethod.isAccessible() : true)) {
                return false;
            }
        } else if (kCallable instanceof KMutableProperty.Setter) {
            Field javaField4 = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter) kCallable).getProperty());
            if (!(javaField4 != null ? javaField4.isAccessible() : true)) {
                return false;
            }
            Method javaMethod2 = ReflectJvmMapping.getJavaMethod((KFunction) kCallable);
            if (!(javaMethod2 != null ? javaMethod2.isAccessible() : true)) {
                return false;
            }
        } else {
            if (!(kCallable instanceof KFunction)) {
                throw new UnsupportedOperationException("Unknown callable: " + kCallable + " (" + kCallable.getClass() + ')');
            }
            KFunction kFunction = (KFunction) kCallable;
            Method javaMethod3 = ReflectJvmMapping.getJavaMethod(kFunction);
            if (!(javaMethod3 != null ? javaMethod3.isAccessible() : true)) {
                return false;
            }
            KCallableImpl<?> kCallableImplAsKCallableImpl = UtilKt.asKCallableImpl(kCallable);
            Object objMo2078getMember = (kCallableImplAsKCallableImpl == null || (defaultCaller = kCallableImplAsKCallableImpl.getDefaultCaller()) == null) ? null : defaultCaller.mo2078getMember();
            AccessibleObject accessibleObject = objMo2078getMember instanceof AccessibleObject ? (AccessibleObject) objMo2078getMember : null;
            if (!(accessibleObject != null ? accessibleObject.isAccessible() : true)) {
                return false;
            }
            Constructor javaConstructor = ReflectJvmMapping.getJavaConstructor(kFunction);
            if (!(javaConstructor != null ? javaConstructor.isAccessible() : true)) {
                return false;
            }
        }
        return true;
    }

    public static final void setAccessible(@NotNull KCallable<?> kCallable, boolean z2) throws SecurityException {
        Caller<?> defaultCaller;
        Intrinsics.checkNotNullParameter(kCallable, "<this>");
        if (kCallable instanceof KMutableProperty) {
            KProperty kProperty = (KProperty) kCallable;
            Field javaField = ReflectJvmMapping.getJavaField(kProperty);
            if (javaField != null) {
                javaField.setAccessible(z2);
            }
            Method javaGetter = ReflectJvmMapping.getJavaGetter(kProperty);
            if (javaGetter != null) {
                javaGetter.setAccessible(z2);
            }
            Method javaSetter = ReflectJvmMapping.getJavaSetter((KMutableProperty) kCallable);
            if (javaSetter == null) {
                return;
            }
            javaSetter.setAccessible(z2);
            return;
        }
        if (kCallable instanceof KProperty) {
            KProperty kProperty2 = (KProperty) kCallable;
            Field javaField2 = ReflectJvmMapping.getJavaField(kProperty2);
            if (javaField2 != null) {
                javaField2.setAccessible(z2);
            }
            Method javaGetter2 = ReflectJvmMapping.getJavaGetter(kProperty2);
            if (javaGetter2 == null) {
                return;
            }
            javaGetter2.setAccessible(z2);
            return;
        }
        if (kCallable instanceof KProperty.Getter) {
            Field javaField3 = ReflectJvmMapping.getJavaField(((KProperty.Getter) kCallable).getProperty());
            if (javaField3 != null) {
                javaField3.setAccessible(z2);
            }
            Method javaMethod = ReflectJvmMapping.getJavaMethod((KFunction) kCallable);
            if (javaMethod == null) {
                return;
            }
            javaMethod.setAccessible(z2);
            return;
        }
        if (kCallable instanceof KMutableProperty.Setter) {
            Field javaField4 = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter) kCallable).getProperty());
            if (javaField4 != null) {
                javaField4.setAccessible(z2);
            }
            Method javaMethod2 = ReflectJvmMapping.getJavaMethod((KFunction) kCallable);
            if (javaMethod2 == null) {
                return;
            }
            javaMethod2.setAccessible(z2);
            return;
        }
        if (!(kCallable instanceof KFunction)) {
            throw new UnsupportedOperationException("Unknown callable: " + kCallable + " (" + kCallable.getClass() + ')');
        }
        KFunction kFunction = (KFunction) kCallable;
        Method javaMethod3 = ReflectJvmMapping.getJavaMethod(kFunction);
        if (javaMethod3 != null) {
            javaMethod3.setAccessible(z2);
        }
        KCallableImpl<?> kCallableImplAsKCallableImpl = UtilKt.asKCallableImpl(kCallable);
        Object objMo2078getMember = (kCallableImplAsKCallableImpl == null || (defaultCaller = kCallableImplAsKCallableImpl.getDefaultCaller()) == null) ? null : defaultCaller.mo2078getMember();
        AccessibleObject accessibleObject = objMo2078getMember instanceof AccessibleObject ? (AccessibleObject) objMo2078getMember : null;
        if (accessibleObject != null) {
            accessibleObject.setAccessible(true);
        }
        Constructor javaConstructor = ReflectJvmMapping.getJavaConstructor(kFunction);
        if (javaConstructor == null) {
            return;
        }
        javaConstructor.setAccessible(z2);
    }
}
