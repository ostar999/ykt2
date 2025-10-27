package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.util.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nmodifierChecks.kt\nKotlin\n*S Kotlin\n*F\n+ 1 modifierChecks.kt\norg/jetbrains/kotlin/util/NoDefaultAndVarargsCheck\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,264:1\n1726#2,3:265\n*S KotlinDebug\n*F\n+ 1 modifierChecks.kt\norg/jetbrains/kotlin/util/NoDefaultAndVarargsCheck\n*L\n105#1:265,3\n*E\n"})
/* loaded from: classes8.dex */
final class NoDefaultAndVarargsCheck implements Check {

    @NotNull
    public static final NoDefaultAndVarargsCheck INSTANCE = new NoDefaultAndVarargsCheck();

    @NotNull
    private static final String description = "should not have varargs or parameters with default values";

    private NoDefaultAndVarargsCheck() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.Check
    public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "functionDescriptor.valueParameters");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (ValueParameterDescriptor it : list) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (!(!DescriptorUtilsKt.declaresOrInheritsDefaultValue(it) && it.getVarargElementType() == null)) {
                return false;
            }
        }
        return true;
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.Check
    @NotNull
    public String getDescription() {
        return description;
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.Check
    @Nullable
    public String invoke(@NotNull FunctionDescriptor functionDescriptor) {
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }
}
