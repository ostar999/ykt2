package kotlin.reflect.jvm.internal.impl.serialization;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public class SerializerExtensionProtocol {

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation;

    @NotNull
    private final ExtensionRegistryLite extensionRegistry;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation;

    @Nullable
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionExtensionReceiverAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation;

    @Nullable
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyBackingFieldAnnotation;

    @Nullable
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyDelegatedFieldAnnotation;

    @Nullable
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyExtensionReceiverAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation;

    @NotNull
    private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation;

    public SerializerExtensionProtocol(@NotNull ExtensionRegistryLite extensionRegistry, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation, @Nullable GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> generatedExtension, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation, @Nullable GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension2, @Nullable GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension3, @Nullable GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> generatedExtension4, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation, @NotNull GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation) {
        Intrinsics.checkNotNullParameter(extensionRegistry, "extensionRegistry");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(constructorAnnotation, "constructorAnnotation");
        Intrinsics.checkNotNullParameter(classAnnotation, "classAnnotation");
        Intrinsics.checkNotNullParameter(functionAnnotation, "functionAnnotation");
        Intrinsics.checkNotNullParameter(propertyAnnotation, "propertyAnnotation");
        Intrinsics.checkNotNullParameter(propertyGetterAnnotation, "propertyGetterAnnotation");
        Intrinsics.checkNotNullParameter(propertySetterAnnotation, "propertySetterAnnotation");
        Intrinsics.checkNotNullParameter(enumEntryAnnotation, "enumEntryAnnotation");
        Intrinsics.checkNotNullParameter(compileTimeValue, "compileTimeValue");
        Intrinsics.checkNotNullParameter(parameterAnnotation, "parameterAnnotation");
        Intrinsics.checkNotNullParameter(typeAnnotation, "typeAnnotation");
        Intrinsics.checkNotNullParameter(typeParameterAnnotation, "typeParameterAnnotation");
        this.extensionRegistry = extensionRegistry;
        this.packageFqName = packageFqName;
        this.constructorAnnotation = constructorAnnotation;
        this.classAnnotation = classAnnotation;
        this.functionAnnotation = functionAnnotation;
        this.functionExtensionReceiverAnnotation = generatedExtension;
        this.propertyAnnotation = propertyAnnotation;
        this.propertyGetterAnnotation = propertyGetterAnnotation;
        this.propertySetterAnnotation = propertySetterAnnotation;
        this.propertyExtensionReceiverAnnotation = generatedExtension2;
        this.propertyBackingFieldAnnotation = generatedExtension3;
        this.propertyDelegatedFieldAnnotation = generatedExtension4;
        this.enumEntryAnnotation = enumEntryAnnotation;
        this.compileTimeValue = compileTimeValue;
        this.parameterAnnotation = parameterAnnotation;
        this.typeAnnotation = typeAnnotation;
        this.typeParameterAnnotation = typeParameterAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> getClassAnnotation() {
        return this.classAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> getCompileTimeValue() {
        return this.compileTimeValue;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> getConstructorAnnotation() {
        return this.constructorAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> getEnumEntryAnnotation() {
        return this.enumEntryAnnotation;
    }

    @NotNull
    public final ExtensionRegistryLite getExtensionRegistry() {
        return this.extensionRegistry;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> getFunctionAnnotation() {
        return this.functionAnnotation;
    }

    @Nullable
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> getFunctionExtensionReceiverAnnotation() {
        return this.functionExtensionReceiverAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> getParameterAnnotation() {
        return this.parameterAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyAnnotation() {
        return this.propertyAnnotation;
    }

    @Nullable
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyBackingFieldAnnotation() {
        return this.propertyBackingFieldAnnotation;
    }

    @Nullable
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyDelegatedFieldAnnotation() {
        return this.propertyDelegatedFieldAnnotation;
    }

    @Nullable
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyExtensionReceiverAnnotation() {
        return this.propertyExtensionReceiverAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyGetterAnnotation() {
        return this.propertyGetterAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertySetterAnnotation() {
        return this.propertySetterAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> getTypeAnnotation() {
        return this.typeAnnotation;
    }

    @NotNull
    public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> getTypeParameterAnnotation() {
        return this.typeParameterAnnotation;
    }
}
