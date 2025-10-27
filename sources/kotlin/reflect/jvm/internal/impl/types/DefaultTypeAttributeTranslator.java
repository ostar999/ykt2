package kotlin.reflect.jvm.internal.impl.types;

import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class DefaultTypeAttributeTranslator implements TypeAttributeTranslator {

    @NotNull
    public static final DefaultTypeAttributeTranslator INSTANCE = new DefaultTypeAttributeTranslator();

    private DefaultTypeAttributeTranslator() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeAttributeTranslator
    @NotNull
    public TypeAttributes toAttributes(@NotNull Annotations annotations, @Nullable TypeConstructor typeConstructor, @Nullable DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        return annotations.isEmpty() ? TypeAttributes.Companion.getEmpty() : TypeAttributes.Companion.create(CollectionsKt__CollectionsJVMKt.listOf(new AnnotationsTypeAttribute(annotations)));
    }
}
