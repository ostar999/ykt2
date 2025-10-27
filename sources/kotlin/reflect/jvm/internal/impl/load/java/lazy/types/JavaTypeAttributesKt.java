package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeUsage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nJavaTypeAttributes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavaTypeAttributes.kt\norg/jetbrains/kotlin/load/java/lazy/types/JavaTypeAttributesKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,57:1\n1#2:58\n*E\n"})
/* loaded from: classes8.dex */
public final class JavaTypeAttributesKt {
    @NotNull
    public static final JavaTypeAttributes toAttributes(@NotNull TypeUsage typeUsage, boolean z2, boolean z3, @Nullable TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeUsage, "<this>");
        return new JavaTypeAttributes(typeUsage, null, z3, z2, typeParameterDescriptor != null ? SetsKt__SetsJVMKt.setOf(typeParameterDescriptor) : null, null, 34, null);
    }

    public static /* synthetic */ JavaTypeAttributes toAttributes$default(TypeUsage typeUsage, boolean z2, boolean z3, TypeParameterDescriptor typeParameterDescriptor, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        if ((i2 & 2) != 0) {
            z3 = false;
        }
        if ((i2 & 4) != 0) {
            typeParameterDescriptor = null;
        }
        return toAttributes(typeUsage, z2, z3, typeParameterDescriptor);
    }
}
