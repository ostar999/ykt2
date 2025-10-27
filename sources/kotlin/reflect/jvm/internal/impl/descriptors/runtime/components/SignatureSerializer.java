package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
final class SignatureSerializer {

    @NotNull
    public static final SignatureSerializer INSTANCE = new SignatureSerializer();

    private SignatureSerializer() {
    }

    @NotNull
    public final String constructorDesc(@NotNull Constructor<?> constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Intrinsics.checkNotNullExpressionValue(parameterTypes, "constructor.parameterTypes");
        for (Class<?> parameterType : parameterTypes) {
            Intrinsics.checkNotNullExpressionValue(parameterType, "parameterType");
            sb.append(ReflectClassUtilKt.getDesc(parameterType));
        }
        sb.append(")V");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    @NotNull
    public final String fieldDesc(@NotNull Field field) {
        Intrinsics.checkNotNullParameter(field, "field");
        Class<?> type = field.getType();
        Intrinsics.checkNotNullExpressionValue(type, "field.type");
        return ReflectClassUtilKt.getDesc(type);
    }

    @NotNull
    public final String methodDesc(@NotNull Method method) {
        Intrinsics.checkNotNullParameter(method, "method");
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        Class<?>[] parameterTypes = method.getParameterTypes();
        Intrinsics.checkNotNullExpressionValue(parameterTypes, "method.parameterTypes");
        for (Class<?> parameterType : parameterTypes) {
            Intrinsics.checkNotNullExpressionValue(parameterType, "parameterType");
            sb.append(ReflectClassUtilKt.getDesc(parameterType));
        }
        sb.append(")");
        Class<?> returnType = method.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "method.returnType");
        sb.append(ReflectClassUtilKt.getDesc(returnType));
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }
}
