package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaEnumValueAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class JavaTargetAnnotationDescriptor extends JavaAnnotationDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JavaTargetAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};

    @NotNull
    private final NotNullLazyValue allValueArguments$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaTargetAnnotationDescriptor(@NotNull JavaAnnotation annotation, @NotNull LazyJavaResolverContext c3) {
        super(c3, annotation, StandardNames.FqNames.target);
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(c3, "c");
        this.allValueArguments$delegate = c3.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends ConstantValue<? extends Object>>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.components.JavaTargetAnnotationDescriptor$allValueArguments$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Map<Name, ? extends ConstantValue<? extends Object>> invoke() {
                JavaAnnotationArgument firstArgument = this.this$0.getFirstArgument();
                ConstantValue<?> constantValueMapJavaTargetArguments$descriptors_jvm = firstArgument instanceof JavaArrayAnnotationArgument ? JavaAnnotationTargetMapper.INSTANCE.mapJavaTargetArguments$descriptors_jvm(((JavaArrayAnnotationArgument) this.this$0.getFirstArgument()).getElements()) : firstArgument instanceof JavaEnumValueAnnotationArgument ? JavaAnnotationTargetMapper.INSTANCE.mapJavaTargetArguments$descriptors_jvm(CollectionsKt__CollectionsJVMKt.listOf(this.this$0.getFirstArgument())) : null;
                Map<Name, ? extends ConstantValue<? extends Object>> mapMapOf = constantValueMapJavaTargetArguments$descriptors_jvm != null ? MapsKt__MapsJVMKt.mapOf(TuplesKt.to(JavaAnnotationMapper.INSTANCE.getTARGET_ANNOTATION_ALLOWED_TARGETS$descriptors_jvm(), constantValueMapJavaTargetArguments$descriptors_jvm)) : null;
                return mapMapOf == null ? MapsKt__MapsKt.emptyMap() : mapMapOf;
            }
        });
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor
    @NotNull
    public Map<Name, ConstantValue<Object>> getAllValueArguments() {
        return (Map) StorageKt.getValue(this.allValueArguments$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }
}
