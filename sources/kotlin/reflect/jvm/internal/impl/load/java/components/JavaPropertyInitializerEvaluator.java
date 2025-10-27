package kotlin.reflect.jvm.internal.impl.load.java.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public interface JavaPropertyInitializerEvaluator {

    public static final class DoNothing implements JavaPropertyInitializerEvaluator {

        @NotNull
        public static final DoNothing INSTANCE = new DoNothing();

        private DoNothing() {
        }

        @Override // kotlin.reflect.jvm.internal.impl.load.java.components.JavaPropertyInitializerEvaluator
        @Nullable
        public ConstantValue<?> getInitializerConstant(@NotNull JavaField field, @NotNull PropertyDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(field, "field");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return null;
        }
    }

    @Nullable
    ConstantValue<?> getInitializerConstant(@NotNull JavaField javaField, @NotNull PropertyDescriptor propertyDescriptor);
}
