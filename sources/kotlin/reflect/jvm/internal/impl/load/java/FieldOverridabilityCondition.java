package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class FieldOverridabilityCondition implements ExternalOverridabilityCondition {
    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.BOTH;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Result isOverridable(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (!(subDescriptor instanceof PropertyDescriptor) || !(superDescriptor instanceof PropertyDescriptor)) {
            return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) subDescriptor;
        PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) superDescriptor;
        return !Intrinsics.areEqual(propertyDescriptor.getName(), propertyDescriptor2.getName()) ? ExternalOverridabilityCondition.Result.UNKNOWN : (JavaDescriptorUtilKt.isJavaField(propertyDescriptor) && JavaDescriptorUtilKt.isJavaField(propertyDescriptor2)) ? ExternalOverridabilityCondition.Result.OVERRIDABLE : (JavaDescriptorUtilKt.isJavaField(propertyDescriptor) || JavaDescriptorUtilKt.isJavaField(propertyDescriptor2)) ? ExternalOverridabilityCondition.Result.INCOMPATIBLE : ExternalOverridabilityCondition.Result.UNKNOWN;
    }
}
