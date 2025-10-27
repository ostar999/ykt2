package kotlin.reflect.jvm.internal.impl.renderer;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.URLUtil;
import com.umeng.analytics.pro.am;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUseSiteTarget;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbbreviatedType;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StubTypeForBuilderInference;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.WrappedType;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorType;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nDescriptorRendererImpl.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DescriptorRendererImpl.kt\norg/jetbrains/kotlin/renderer/DescriptorRendererImpl\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1205:1\n2624#2,3:1206\n766#2:1209\n857#2,2:1210\n1549#2:1212\n1620#2,3:1213\n766#2:1216\n857#2,2:1217\n1549#2:1219\n1620#2,3:1220\n1549#2:1223\n1620#2,3:1224\n2624#2,3:1228\n2624#2,3:1231\n766#2:1234\n857#2,2:1235\n1620#2,3:1237\n1#3:1227\n*S KotlinDebug\n*F\n+ 1 DescriptorRendererImpl.kt\norg/jetbrains/kotlin/renderer/DescriptorRendererImpl\n*L\n183#1:1206,3\n483#1:1209\n483#1:1210,2\n484#1:1212\n484#1:1213,3\n486#1:1216\n486#1:1217,2\n486#1:1219\n486#1:1220,3\n488#1:1223\n488#1:1224,3\n587#1:1228,3\n589#1:1231,3\n805#1:1234\n805#1:1235,2\n828#1:1237,3\n*E\n"})
/* loaded from: classes8.dex */
public final class DescriptorRendererImpl extends DescriptorRenderer implements DescriptorRendererOptions {

    @NotNull
    private final Lazy functionTypeAnnotationsRenderer$delegate;

    @NotNull
    private final DescriptorRendererOptionsImpl options;

    public final class RenderDeclarationDescriptorVisitor implements DeclarationDescriptorVisitor<Unit, StringBuilder> {

        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[PropertyAccessorRenderingPolicy.values().length];
                try {
                    iArr[PropertyAccessorRenderingPolicy.PRETTY.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[PropertyAccessorRenderingPolicy.DEBUG.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[PropertyAccessorRenderingPolicy.NONE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public RenderDeclarationDescriptorVisitor() {
        }

        private final void visitPropertyAccessorDescriptor(PropertyAccessorDescriptor propertyAccessorDescriptor, StringBuilder sb, String str) throws IOException {
            int i2 = WhenMappings.$EnumSwitchMapping$0[DescriptorRendererImpl.this.getPropertyAccessorRenderingPolicy().ordinal()];
            if (i2 != 1) {
                if (i2 != 2) {
                    return;
                }
                visitFunctionDescriptor2((FunctionDescriptor) propertyAccessorDescriptor, sb);
                return;
            }
            DescriptorRendererImpl.this.renderAccessorModifiers(propertyAccessorDescriptor, sb);
            sb.append(str + " for ");
            DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
            PropertyDescriptor correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "descriptor.correspondingProperty");
            descriptorRendererImpl.renderProperty(correspondingProperty, sb);
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitClassDescriptor(ClassDescriptor classDescriptor, StringBuilder sb) throws IOException {
            visitClassDescriptor2(classDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, StringBuilder sb) throws IOException {
            visitConstructorDescriptor2(constructorDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionDescriptor(FunctionDescriptor functionDescriptor, StringBuilder sb) throws IOException {
            visitFunctionDescriptor2(functionDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitModuleDeclaration(ModuleDescriptor moduleDescriptor, StringBuilder sb) {
            visitModuleDeclaration2(moduleDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
            visitPackageFragmentDescriptor2(packageFragmentDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
            visitPackageViewDescriptor2(packageViewDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, StringBuilder sb) throws IOException {
            visitPropertyDescriptor2(propertyDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, StringBuilder sb) throws IOException {
            visitPropertyGetterDescriptor2(propertyGetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, StringBuilder sb) throws IOException {
            visitPropertySetterDescriptor2(propertySetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, StringBuilder sb) {
            visitReceiverParameterDescriptor2(receiverParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
            visitTypeAliasDescriptor2(typeAliasDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb) {
            visitTypeParameterDescriptor2(typeParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, StringBuilder sb) {
            visitValueParameterDescriptor2(valueParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        /* renamed from: visitClassDescriptor, reason: avoid collision after fix types in other method */
        public void visitClassDescriptor2(@NotNull ClassDescriptor descriptor, @NotNull StringBuilder builder) throws IOException {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderClass(descriptor, builder);
        }

        /* renamed from: visitConstructorDescriptor, reason: avoid collision after fix types in other method */
        public void visitConstructorDescriptor2(@NotNull ConstructorDescriptor constructorDescriptor, @NotNull StringBuilder builder) throws IOException {
            Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderConstructor(constructorDescriptor, builder);
        }

        /* renamed from: visitFunctionDescriptor, reason: avoid collision after fix types in other method */
        public void visitFunctionDescriptor2(@NotNull FunctionDescriptor descriptor, @NotNull StringBuilder builder) throws IOException {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderFunction(descriptor, builder);
        }

        /* renamed from: visitModuleDeclaration, reason: avoid collision after fix types in other method */
        public void visitModuleDeclaration2(@NotNull ModuleDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderName(descriptor, builder, true);
        }

        /* renamed from: visitPackageFragmentDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageFragmentDescriptor2(@NotNull PackageFragmentDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageFragment(descriptor, builder);
        }

        /* renamed from: visitPackageViewDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageViewDescriptor2(@NotNull PackageViewDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderPackageView(descriptor, builder);
        }

        /* renamed from: visitPropertyDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyDescriptor2(@NotNull PropertyDescriptor descriptor, @NotNull StringBuilder builder) throws IOException {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderProperty(descriptor, builder);
        }

        /* renamed from: visitPropertyGetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyGetterDescriptor2(@NotNull PropertyGetterDescriptor descriptor, @NotNull StringBuilder builder) throws IOException {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "getter");
        }

        /* renamed from: visitPropertySetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertySetterDescriptor2(@NotNull PropertySetterDescriptor descriptor, @NotNull StringBuilder builder) throws IOException {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "setter");
        }

        /* renamed from: visitReceiverParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitReceiverParameterDescriptor2(@NotNull ReceiverParameterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            builder.append(descriptor.getName());
        }

        /* renamed from: visitTypeAliasDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeAliasDescriptor2(@NotNull TypeAliasDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeAlias(descriptor, builder);
        }

        /* renamed from: visitTypeParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeParameterDescriptor2(@NotNull TypeParameterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderTypeParameter(descriptor, builder, true);
        }

        /* renamed from: visitValueParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitValueParameterDescriptor2(@NotNull ValueParameterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(builder, "builder");
            DescriptorRendererImpl.this.renderValueParameter(descriptor, true, builder, true);
        }
    }

    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[RenderingFormat.values().length];
            try {
                iArr[RenderingFormat.PLAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RenderingFormat.HTML.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ParameterNameRenderingPolicy.values().length];
            try {
                iArr2[ParameterNameRenderingPolicy.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ParameterNameRenderingPolicy.ONLY_NON_SYNTHESIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ParameterNameRenderingPolicy.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public DescriptorRendererImpl(@NotNull DescriptorRendererOptionsImpl options) {
        Intrinsics.checkNotNullParameter(options, "options");
        this.options = options;
        options.isLocked();
        this.functionTypeAnnotationsRenderer$delegate = LazyKt__LazyJVMKt.lazy(new Function0<DescriptorRendererImpl>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final DescriptorRendererImpl invoke() {
                DescriptorRenderer descriptorRendererWithOptions = this.this$0.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                        invoke2(descriptorRendererOptions);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull DescriptorRendererOptions withOptions) {
                        Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                        withOptions.setExcludedTypeAnnotationClasses(SetsKt___SetsKt.plus((Set) withOptions.getExcludedTypeAnnotationClasses(), (Iterable) CollectionsKt__CollectionsKt.listOf((Object[]) new FqName[]{StandardNames.FqNames.extensionFunctionType, StandardNames.FqNames.contextFunctionTypeParams})));
                    }
                });
                Intrinsics.checkNotNull(descriptorRendererWithOptions, "null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
                return (DescriptorRendererImpl) descriptorRendererWithOptions;
            }
        });
    }

    private final void appendDefinedIn(StringBuilder sb, DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor containingDeclaration;
        String name;
        if ((declarationDescriptor instanceof PackageFragmentDescriptor) || (declarationDescriptor instanceof PackageViewDescriptor) || (containingDeclaration = declarationDescriptor.getContainingDeclaration()) == null || (containingDeclaration instanceof ModuleDescriptor)) {
            return;
        }
        sb.append(" ");
        sb.append(renderMessage("defined in"));
        sb.append(" ");
        FqNameUnsafe fqName = DescriptorUtils.getFqName(containingDeclaration);
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(containingDeclaration)");
        sb.append(fqName.isRoot() ? "root package" : renderFqName(fqName));
        if (getWithSourceFileForTopLevel() && (containingDeclaration instanceof PackageFragmentDescriptor) && (declarationDescriptor instanceof DeclarationDescriptorWithSource) && (name = ((DeclarationDescriptorWithSource) declarationDescriptor).getSource().getContainingFile().getName()) != null) {
            sb.append(" ");
            sb.append(renderMessage("in file"));
            sb.append(" ");
            sb.append(name);
        }
    }

    private final void appendTypeProjections(StringBuilder sb, List<? extends TypeProjection> list) throws IOException {
        CollectionsKt___CollectionsKt.joinTo(list, sb, (124 & 2) != 0 ? ", " : ", ", (124 & 4) != 0 ? "" : null, (124 & 8) == 0 ? null : "", (124 & 16) != 0 ? -1 : 0, (124 & 32) != 0 ? "..." : null, (124 & 64) != 0 ? null : new Function1<TypeProjection, CharSequence>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.appendTypeProjections.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull TypeProjection it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.isStarProjection()) {
                    return "*";
                }
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                KotlinType type = it.getType();
                Intrinsics.checkNotNullExpressionValue(type, "it.type");
                String strRenderType = descriptorRendererImpl.renderType(type);
                if (it.getProjectionKind() == Variance.INVARIANT) {
                    return strRenderType;
                }
                return it.getProjectionKind() + ' ' + strRenderType;
            }
        });
    }

    private final String arrow() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i2 == 1) {
            return escape("->");
        }
        if (i2 == 2) {
            return "&rarr;";
        }
        throw new NoWhenBranchMatchedException();
    }

    private final String escape(String str) {
        return getTextFormat().escape(str);
    }

    private final DescriptorRendererImpl getFunctionTypeAnnotationsRenderer() {
        return (DescriptorRendererImpl) this.functionTypeAnnotationsRenderer$delegate.getValue();
    }

    private final String gt() {
        return escape(">");
    }

    private final boolean hasModifiersOrAnnotations(KotlinType kotlinType) {
        return FunctionTypesKt.isSuspendFunctionType(kotlinType) || !kotlinType.getAnnotations().isEmpty();
    }

    private final Modality implicitModalityWithoutExtensions(MemberDescriptor memberDescriptor) {
        if (memberDescriptor instanceof ClassDescriptor) {
            return ((ClassDescriptor) memberDescriptor).getKind() == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
        }
        DeclarationDescriptor containingDeclaration = memberDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null) {
            return Modality.FINAL;
        }
        if (!(memberDescriptor instanceof CallableMemberDescriptor)) {
            return Modality.FINAL;
        }
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptor;
        Intrinsics.checkNotNullExpressionValue(callableMemberDescriptor.getOverriddenDescriptors(), "this.overriddenDescriptors");
        if ((!r1.isEmpty()) && classDescriptor.getModality() != Modality.FINAL) {
            return Modality.OPEN;
        }
        if (classDescriptor.getKind() != ClassKind.INTERFACE || Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), DescriptorVisibilities.PRIVATE)) {
            return Modality.FINAL;
        }
        Modality modality = callableMemberDescriptor.getModality();
        Modality modality2 = Modality.ABSTRACT;
        return modality == modality2 ? modality2 : Modality.OPEN;
    }

    private final boolean isParameterName(AnnotationDescriptor annotationDescriptor) {
        return Intrinsics.areEqual(annotationDescriptor.getFqName(), StandardNames.FqNames.parameterName);
    }

    private final String lt() {
        return escape("<");
    }

    private final boolean overridesSomething(CallableMemberDescriptor callableMemberDescriptor) {
        return !callableMemberDescriptor.getOverriddenDescriptors().isEmpty();
    }

    private final void renderAbbreviatedTypeExpansion(StringBuilder sb, AbbreviatedType abbreviatedType) {
        RenderingFormat textFormat = getTextFormat();
        RenderingFormat renderingFormat = RenderingFormat.HTML;
        if (textFormat == renderingFormat) {
            sb.append("<font color=\"808080\"><i>");
        }
        sb.append(" /* = ");
        renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
        sb.append(" */");
        if (getTextFormat() == renderingFormat) {
            sb.append("</i></font>");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderAccessorModifiers(PropertyAccessorDescriptor propertyAccessorDescriptor, StringBuilder sb) {
        renderMemberModifiers(propertyAccessorDescriptor, sb);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void renderAdditionalModifiers(kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r6, java.lang.StringBuilder r7) {
        /*
            r5 = this;
            boolean r0 = r6.isOperator()
            java.lang.String r1 = "functionDescriptor.overriddenDescriptors"
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L3f
            java.util.Collection r0 = r6.getOverriddenDescriptors()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r4 = r0
            java.util.Collection r4 = (java.util.Collection) r4
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L1e
        L1c:
            r0 = r3
            goto L35
        L1e:
            java.util.Iterator r0 = r0.iterator()
        L22:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L1c
            java.lang.Object r4 = r0.next()
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r4 = (kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor) r4
            boolean r4 = r4.isOperator()
            if (r4 == 0) goto L22
            r0 = r2
        L35:
            if (r0 != 0) goto L3d
            boolean r0 = r5.getAlwaysRenderModifiers()
            if (r0 == 0) goto L3f
        L3d:
            r0 = r3
            goto L40
        L3f:
            r0 = r2
        L40:
            boolean r4 = r6.isInfix()
            if (r4 == 0) goto L7a
            java.util.Collection r4 = r6.getOverriddenDescriptors()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r1)
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            r1 = r4
            java.util.Collection r1 = (java.util.Collection) r1
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L5a
        L58:
            r1 = r3
            goto L71
        L5a:
            java.util.Iterator r1 = r4.iterator()
        L5e:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L58
            java.lang.Object r4 = r1.next()
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r4 = (kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor) r4
            boolean r4 = r4.isInfix()
            if (r4 == 0) goto L5e
            r1 = r2
        L71:
            if (r1 != 0) goto L79
            boolean r1 = r5.getAlwaysRenderModifiers()
            if (r1 == 0) goto L7a
        L79:
            r2 = r3
        L7a:
            boolean r1 = r6.isTailrec()
            java.lang.String r3 = "tailrec"
            r5.renderModifier(r7, r1, r3)
            r5.renderSuspendModifier(r6, r7)
            boolean r6 = r6.isInline()
            java.lang.String r1 = "inline"
            r5.renderModifier(r7, r6, r1)
            java.lang.String r6 = "infix"
            r5.renderModifier(r7, r2, r6)
            java.lang.String r6 = "operator"
            r5.renderModifier(r7, r0, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderAdditionalModifiers(kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, java.lang.StringBuilder):void");
    }

    private final List<String> renderAndSortAnnotationArguments(AnnotationDescriptor annotationDescriptor) {
        ClassConstructorDescriptor classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor;
        List<ValueParameterDescriptor> valueParameters;
        Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptor.getAllValueArguments();
        List listEmptyList = null;
        ClassDescriptor annotationClass = getRenderDefaultAnnotationArguments() ? DescriptorUtilsKt.getAnnotationClass(annotationDescriptor) : null;
        if (annotationClass != null && (classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor = annotationClass.mo2080getUnsubstitutedPrimaryConstructor()) != null && (valueParameters = classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor.getValueParameters()) != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : valueParameters) {
                if (((ValueParameterDescriptor) obj).declaresDefaultValue()) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(((ValueParameterDescriptor) it.next()).getName());
            }
            listEmptyList = arrayList2;
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : listEmptyList) {
            Name it2 = (Name) obj2;
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            if (!allValueArguments.containsKey(it2)) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList4.add(((Name) it3.next()).asString() + " = ...");
        }
        Set<Map.Entry<Name, ConstantValue<?>>> setEntrySet = allValueArguments.entrySet();
        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it4 = setEntrySet.iterator();
        while (it4.hasNext()) {
            Map.Entry entry = (Map.Entry) it4.next();
            Name name = (Name) entry.getKey();
            ConstantValue<?> constantValue = (ConstantValue) entry.getValue();
            StringBuilder sb = new StringBuilder();
            sb.append(name.asString());
            sb.append(" = ");
            sb.append(!listEmptyList.contains(name) ? renderConstant(constantValue) : "...");
            arrayList5.add(sb.toString());
        }
        return CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.plus((Collection) arrayList4, (Iterable) arrayList5));
    }

    private final void renderAnnotations(StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            Set<FqName> excludedTypeAnnotationClasses = annotated instanceof KotlinType ? getExcludedTypeAnnotationClasses() : getExcludedAnnotationClasses();
            Function1<AnnotationDescriptor, Boolean> annotationFilter = getAnnotationFilter();
            for (AnnotationDescriptor annotationDescriptor : annotated.getAnnotations()) {
                if (!CollectionsKt___CollectionsKt.contains(excludedTypeAnnotationClasses, annotationDescriptor.getFqName()) && !isParameterName(annotationDescriptor) && (annotationFilter == null || annotationFilter.invoke(annotationDescriptor).booleanValue())) {
                    sb.append(renderAnnotation(annotationDescriptor, annotationUseSiteTarget));
                    if (getEachAnnotationOnNewLine()) {
                        sb.append('\n');
                        Intrinsics.checkNotNullExpressionValue(sb, "append('\\n')");
                    } else {
                        sb.append(" ");
                    }
                }
            }
        }
    }

    public static /* synthetic */ void renderAnnotations$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        descriptorRendererImpl.renderAnnotations(sb, annotated, annotationUseSiteTarget);
    }

    private final void renderCapturedTypeParametersIfRequired(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters, StringBuilder sb) {
        List<TypeParameterDescriptor> declaredTypeParameters = classifierDescriptorWithTypeParameters.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "classifier.declaredTypeParameters");
        List<TypeParameterDescriptor> parameters = classifierDescriptorWithTypeParameters.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "classifier.typeConstructor.parameters");
        if (getVerbose() && classifierDescriptorWithTypeParameters.isInner() && parameters.size() > declaredTypeParameters.size()) {
            sb.append(" /*captured type parameters: ");
            renderTypeParameterList(sb, parameters.subList(declaredTypeParameters.size(), parameters.size()));
            sb.append(URLUtil.WAR_URL_SEPARATOR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderClass(ClassDescriptor classDescriptor, StringBuilder sb) throws IOException {
        ClassConstructorDescriptor classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor;
        boolean z2 = classDescriptor.getKind() == ClassKind.ENUM_ENTRY;
        if (!getStartFromName()) {
            renderAnnotations$default(this, sb, classDescriptor, null, 2, null);
            List<ReceiverParameterDescriptor> contextReceivers = classDescriptor.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "klass.contextReceivers");
            renderContextReceivers(contextReceivers, sb);
            if (!z2) {
                DescriptorVisibility visibility = classDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "klass.visibility");
                renderVisibility(visibility, sb);
            }
            if ((classDescriptor.getKind() != ClassKind.INTERFACE || classDescriptor.getModality() != Modality.ABSTRACT) && (!classDescriptor.getKind().isSingleton() || classDescriptor.getModality() != Modality.FINAL)) {
                Modality modality = classDescriptor.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "klass.modality");
                renderModality(modality, sb, implicitModalityWithoutExtensions(classDescriptor));
            }
            renderMemberModifiers(classDescriptor, sb);
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.INNER) && classDescriptor.isInner(), am.au);
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.DATA) && classDescriptor.isData(), "data");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.INLINE) && classDescriptor.isInline(), "inline");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.VALUE) && classDescriptor.isValue(), "value");
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.FUN) && classDescriptor.isFun(), "fun");
            renderClassKindPrefix(classDescriptor, sb);
        }
        if (DescriptorUtils.isCompanionObject(classDescriptor)) {
            renderCompanionObjectName(classDescriptor, sb);
        } else {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(sb);
            }
            renderName(classDescriptor, sb, true);
        }
        if (z2) {
            return;
        }
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "klass.declaredTypeParameters");
        renderTypeParameters(declaredTypeParameters, sb, false);
        renderCapturedTypeParametersIfRequired(classDescriptor, sb);
        if (!classDescriptor.getKind().isSingleton() && getClassWithPrimaryConstructor() && (classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor = classDescriptor.mo2080getUnsubstitutedPrimaryConstructor()) != null) {
            sb.append(" ");
            renderAnnotations$default(this, sb, classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor, null, 2, null);
            DescriptorVisibility visibility2 = classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility2, "primaryConstructor.visibility");
            renderVisibility(visibility2, sb);
            sb.append(renderKeyword("constructor"));
            List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "primaryConstructor.valueParameters");
            renderValueParameters(valueParameters, classConstructorDescriptorMo2080getUnsubstitutedPrimaryConstructor.hasSynthesizedParameterNames(), sb);
        }
        renderSuperTypes(classDescriptor, sb);
        renderWhereSuffix(declaredTypeParameters, sb);
    }

    private final void renderClassKindPrefix(ClassDescriptor classDescriptor, StringBuilder sb) {
        sb.append(renderKeyword(DescriptorRenderer.Companion.getClassifierKindPrefix(classDescriptor)));
    }

    private final void renderCompanionObjectName(DeclarationDescriptor declarationDescriptor, StringBuilder sb) {
        if (getRenderCompanionObjectName()) {
            if (getStartFromName()) {
                sb.append("companion object");
            }
            renderSpaceIfNeeded(sb);
            DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingDeclaration();
            if (containingDeclaration != null) {
                sb.append("of ");
                Name name = containingDeclaration.getName();
                Intrinsics.checkNotNullExpressionValue(name, "containingDeclaration.name");
                sb.append(renderName(name, false));
            }
        }
        if (getVerbose() || !Intrinsics.areEqual(declarationDescriptor.getName(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT)) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(sb);
            }
            Name name2 = declarationDescriptor.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "descriptor.name");
            sb.append(renderName(name2, true));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderConstant(ConstantValue<?> constantValue) {
        if (constantValue instanceof ArrayValue) {
            return CollectionsKt___CollectionsKt.joinToString$default(((ArrayValue) constantValue).getValue(), ", ", StrPool.DELIM_START, "}", 0, null, new Function1<ConstantValue<?>, CharSequence>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderConstant.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final CharSequence invoke(@NotNull ConstantValue<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return DescriptorRendererImpl.this.renderConstant(it);
                }
            }, 24, null);
        }
        if (constantValue instanceof AnnotationValue) {
            return StringsKt__StringsKt.removePrefix(DescriptorRenderer.renderAnnotation$default(this, ((AnnotationValue) constantValue).getValue(), null, 2, null), (CharSequence) "@");
        }
        if (!(constantValue instanceof KClassValue)) {
            return constantValue.toString();
        }
        KClassValue.Value value = ((KClassValue) constantValue).getValue();
        if (value instanceof KClassValue.Value.LocalClass) {
            return ((KClassValue.Value.LocalClass) value).getType() + "::class";
        }
        if (!(value instanceof KClassValue.Value.NormalClass)) {
            throw new NoWhenBranchMatchedException();
        }
        KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value;
        String strAsString = normalClass.getClassId().asSingleFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "classValue.classId.asSingleFqName().asString()");
        int arrayDimensions = normalClass.getArrayDimensions();
        for (int i2 = 0; i2 < arrayDimensions; i2++) {
            strAsString = "kotlin.Array<" + strAsString + Typography.greater;
        }
        return strAsString + "::class";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void renderConstructor(kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor r18, java.lang.StringBuilder r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderConstructor(kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor, java.lang.StringBuilder):void");
    }

    private final void renderContextReceivers(List<? extends ReceiverParameterDescriptor> list, StringBuilder sb) {
        if (!list.isEmpty()) {
            sb.append("context(");
            int i2 = 0;
            for (ReceiverParameterDescriptor receiverParameterDescriptor : list) {
                int i3 = i2 + 1;
                renderAnnotations(sb, receiverParameterDescriptor, AnnotationUseSiteTarget.RECEIVER);
                KotlinType type = receiverParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "contextReceiver.type");
                sb.append(renderForReceiver(type));
                if (i2 == CollectionsKt__CollectionsKt.getLastIndex(list)) {
                    sb.append(") ");
                } else {
                    sb.append(", ");
                }
                i2 = i3;
            }
        }
    }

    private final void renderDefaultType(StringBuilder sb, KotlinType kotlinType) {
        renderAnnotations$default(this, sb, kotlinType, null, 2, null);
        DefinitelyNotNullType definitelyNotNullType = kotlinType instanceof DefinitelyNotNullType ? (DefinitelyNotNullType) kotlinType : null;
        SimpleType original = definitelyNotNullType != null ? definitelyNotNullType.getOriginal() : null;
        if (KotlinTypeKt.isError(kotlinType)) {
            if (TypeUtilsKt.isUnresolvedType(kotlinType) && getPresentableUnresolvedTypes()) {
                sb.append(renderError(ErrorUtils.INSTANCE.unresolvedTypeAsItIs(kotlinType)));
            } else {
                if (!(kotlinType instanceof ErrorType) || getInformativeErrorType()) {
                    sb.append(kotlinType.getConstructor().toString());
                } else {
                    sb.append(((ErrorType) kotlinType).getDebugMessage());
                }
                sb.append(renderTypeArguments(kotlinType.getArguments()));
            }
        } else if (kotlinType instanceof StubTypeForBuilderInference) {
            sb.append(((StubTypeForBuilderInference) kotlinType).getOriginalTypeVariable().toString());
        } else if (original instanceof StubTypeForBuilderInference) {
            sb.append(((StubTypeForBuilderInference) original).getOriginalTypeVariable().toString());
        } else {
            renderTypeConstructorAndArguments$default(this, sb, kotlinType, null, 2, null);
        }
        if (kotlinType.isMarkedNullable()) {
            sb.append("?");
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(kotlinType)) {
            sb.append(" & Any");
        }
    }

    private final String renderError(String str) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i2 == 1) {
            return str;
        }
        if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        return "<font color=red><b>" + str + "</b></font>";
    }

    private final String renderForReceiver(KotlinType kotlinType) {
        String strRenderType = renderType(kotlinType);
        if ((!shouldRenderAsPrettyFunctionType(kotlinType) || TypeUtils.isNullableType(kotlinType)) && !(kotlinType instanceof DefinitelyNotNullType)) {
            return strRenderType;
        }
        return '(' + strRenderType + ')';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderFunction(FunctionDescriptor functionDescriptor, StringBuilder sb) throws IOException {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderAnnotations$default(this, sb, functionDescriptor, null, 2, null);
                List<ReceiverParameterDescriptor> contextReceiverParameters = functionDescriptor.getContextReceiverParameters();
                Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "function.contextReceiverParameters");
                renderContextReceivers(contextReceiverParameters, sb);
                DescriptorVisibility visibility = functionDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "function.visibility");
                renderVisibility(visibility, sb);
                renderModalityForCallable(functionDescriptor, sb);
                if (getIncludeAdditionalModifiers()) {
                    renderMemberModifiers(functionDescriptor, sb);
                }
                renderOverride(functionDescriptor, sb);
                if (getIncludeAdditionalModifiers()) {
                    renderAdditionalModifiers(functionDescriptor, sb);
                } else {
                    renderSuspendModifier(functionDescriptor, sb);
                }
                renderMemberKind(functionDescriptor, sb);
                if (getVerbose()) {
                    if (functionDescriptor.isHiddenToOvercomeSignatureClash()) {
                        sb.append("/*isHiddenToOvercomeSignatureClash*/ ");
                    }
                    if (functionDescriptor.isHiddenForResolutionEverywhereBesideSupercalls()) {
                        sb.append("/*isHiddenForResolutionEverywhereBesideSupercalls*/ ");
                    }
                }
            }
            sb.append(renderKeyword("fun"));
            sb.append(" ");
            List<TypeParameterDescriptor> typeParameters = functionDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "function.typeParameters");
            renderTypeParameters(typeParameters, sb, true);
            renderReceiver(functionDescriptor, sb);
        }
        renderName(functionDescriptor, sb, true);
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "function.valueParameters");
        renderValueParameters(valueParameters, functionDescriptor.hasSynthesizedParameterNames(), sb);
        renderReceiverAfterName(functionDescriptor, sb);
        KotlinType returnType = functionDescriptor.getReturnType();
        if (!getWithoutReturnType() && (getUnitReturnType() || returnType == null || !KotlinBuiltIns.isUnit(returnType))) {
            sb.append(": ");
            sb.append(returnType == null ? "[NULL]" : renderType(returnType));
        }
        List<TypeParameterDescriptor> typeParameters2 = functionDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "function.typeParameters");
        renderWhereSuffix(typeParameters2, sb);
    }

    private final void renderFunctionType(StringBuilder sb, KotlinType kotlinType) {
        Name nameExtractParameterNameFromFunctionTypeArgument;
        int length = sb.length();
        renderAnnotations$default(getFunctionTypeAnnotationsRenderer(), sb, kotlinType, null, 2, null);
        boolean z2 = sb.length() != length;
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(kotlinType);
        List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(kotlinType);
        if (!contextReceiverTypesFromFunctionType.isEmpty()) {
            sb.append("context(");
            Iterator<KotlinType> it = contextReceiverTypesFromFunctionType.subList(0, CollectionsKt__CollectionsKt.getLastIndex(contextReceiverTypesFromFunctionType)).iterator();
            while (it.hasNext()) {
                renderNormalizedType(sb, it.next());
                sb.append(", ");
            }
            renderNormalizedType(sb, (KotlinType) CollectionsKt___CollectionsKt.last((List) contextReceiverTypesFromFunctionType));
            sb.append(") ");
        }
        boolean zIsSuspendFunctionType = FunctionTypesKt.isSuspendFunctionType(kotlinType);
        boolean zIsMarkedNullable = kotlinType.isMarkedNullable();
        boolean z3 = zIsMarkedNullable || (z2 && receiverTypeFromFunctionType != null);
        if (z3) {
            if (zIsSuspendFunctionType) {
                sb.insert(length, '(');
            } else {
                if (z2) {
                    CharsKt__CharJVMKt.isWhitespace(StringsKt___StringsKt.last(sb));
                    if (sb.charAt(StringsKt__StringsKt.getLastIndex(sb) - 1) != ')') {
                        sb.insert(StringsKt__StringsKt.getLastIndex(sb), "()");
                    }
                }
                sb.append("(");
            }
        }
        renderModifier(sb, zIsSuspendFunctionType, "suspend");
        if (receiverTypeFromFunctionType != null) {
            boolean z4 = (shouldRenderAsPrettyFunctionType(receiverTypeFromFunctionType) && !receiverTypeFromFunctionType.isMarkedNullable()) || hasModifiersOrAnnotations(receiverTypeFromFunctionType) || (receiverTypeFromFunctionType instanceof DefinitelyNotNullType);
            if (z4) {
                sb.append("(");
            }
            renderNormalizedType(sb, receiverTypeFromFunctionType);
            if (z4) {
                sb.append(")");
            }
            sb.append(StrPool.DOT);
        }
        sb.append("(");
        if (!FunctionTypesKt.isBuiltinExtensionFunctionalType(kotlinType) || kotlinType.getArguments().size() > 1) {
            int i2 = 0;
            for (TypeProjection typeProjection : FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType)) {
                int i3 = i2 + 1;
                if (i2 > 0) {
                    sb.append(", ");
                }
                if (getParameterNamesInFunctionalTypes()) {
                    KotlinType type = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "typeProjection.type");
                    nameExtractParameterNameFromFunctionTypeArgument = FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type);
                } else {
                    nameExtractParameterNameFromFunctionTypeArgument = null;
                }
                if (nameExtractParameterNameFromFunctionTypeArgument != null) {
                    sb.append(renderName(nameExtractParameterNameFromFunctionTypeArgument, false));
                    sb.append(": ");
                }
                sb.append(renderTypeProjection(typeProjection));
                i2 = i3;
            }
        } else {
            sb.append("???");
        }
        sb.append(") ");
        sb.append(arrow());
        sb.append(" ");
        renderNormalizedType(sb, FunctionTypesKt.getReturnTypeFromFunctionType(kotlinType));
        if (z3) {
            sb.append(")");
        }
        if (zIsMarkedNullable) {
            sb.append("?");
        }
    }

    private final void renderInitializer(VariableDescriptor variableDescriptor, StringBuilder sb) {
        ConstantValue<?> constantValueMo2082getCompileTimeInitializer;
        if (!getIncludePropertyConstant() || (constantValueMo2082getCompileTimeInitializer = variableDescriptor.mo2082getCompileTimeInitializer()) == null) {
            return;
        }
        sb.append(" = ");
        sb.append(escape(renderConstant(constantValueMo2082getCompileTimeInitializer)));
    }

    private final String renderKeyword(String str) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i2 == 1) {
            return str;
        }
        if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        if (getBoldOnlyForNamesInHtml()) {
            return str;
        }
        return "<b>" + str + "</b>";
    }

    private final void renderMemberKind(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.MEMBER_KIND) && getVerbose() && callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
            sb.append("/*");
            sb.append(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(callableMemberDescriptor.getKind().name()));
            sb.append("*/ ");
        }
    }

    private final void renderMemberModifiers(MemberDescriptor memberDescriptor, StringBuilder sb) {
        renderModifier(sb, memberDescriptor.isExternal(), "external");
        renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.EXPECT) && memberDescriptor.isExpect(), "expect");
        renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.ACTUAL) && memberDescriptor.isActual(), "actual");
    }

    private final void renderModality(Modality modality, StringBuilder sb, Modality modality2) {
        if (getRenderDefaultModality() || modality != modality2) {
            renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.MODALITY), CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(modality.name()));
        }
    }

    private final void renderModalityForCallable(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (DescriptorUtils.isTopLevelDeclaration(callableMemberDescriptor) && callableMemberDescriptor.getModality() == Modality.FINAL) {
            return;
        }
        if (getOverrideRenderingPolicy() == OverrideRenderingPolicy.RENDER_OVERRIDE && callableMemberDescriptor.getModality() == Modality.OPEN && overridesSomething(callableMemberDescriptor)) {
            return;
        }
        Modality modality = callableMemberDescriptor.getModality();
        Intrinsics.checkNotNullExpressionValue(modality, "callable.modality");
        renderModality(modality, sb, implicitModalityWithoutExtensions(callableMemberDescriptor));
    }

    private final void renderModifier(StringBuilder sb, boolean z2, String str) {
        if (z2) {
            sb.append(renderKeyword(str));
            sb.append(" ");
        }
    }

    private final void renderNormalizedType(StringBuilder sb, KotlinType kotlinType) {
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        AbbreviatedType abbreviatedType = unwrappedTypeUnwrap instanceof AbbreviatedType ? (AbbreviatedType) unwrappedTypeUnwrap : null;
        if (abbreviatedType == null) {
            renderNormalizedTypeAsIs(sb, kotlinType);
            return;
        }
        if (getRenderTypeExpansions()) {
            renderNormalizedTypeAsIs(sb, abbreviatedType.getExpandedType());
            return;
        }
        renderNormalizedTypeAsIs(sb, abbreviatedType.getAbbreviation());
        if (getRenderUnabbreviatedType()) {
            renderAbbreviatedTypeExpansion(sb, abbreviatedType);
        }
    }

    private final void renderNormalizedTypeAsIs(StringBuilder sb, KotlinType kotlinType) {
        if ((kotlinType instanceof WrappedType) && getDebugMode() && !((WrappedType) kotlinType).isComputed()) {
            sb.append("<Not computed yet>");
            return;
        }
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            sb.append(((FlexibleType) unwrappedTypeUnwrap).render(this, this));
        } else if (unwrappedTypeUnwrap instanceof SimpleType) {
            renderSimpleType(sb, (SimpleType) unwrappedTypeUnwrap);
        }
    }

    private final void renderOverride(CallableMemberDescriptor callableMemberDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.OVERRIDE) && overridesSomething(callableMemberDescriptor) && getOverrideRenderingPolicy() != OverrideRenderingPolicy.RENDER_OPEN) {
            renderModifier(sb, true, "override");
            if (getVerbose()) {
                sb.append("/*");
                sb.append(callableMemberDescriptor.getOverriddenDescriptors().size());
                sb.append("*/ ");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageFragment(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
        renderPackageHeader(packageFragmentDescriptor.getFqName(), "package-fragment", sb);
        if (getDebugMode()) {
            sb.append(" in ");
            renderName(packageFragmentDescriptor.getContainingDeclaration(), sb, false);
        }
    }

    private final void renderPackageHeader(FqName fqName, String str, StringBuilder sb) {
        sb.append(renderKeyword(str));
        FqNameUnsafe unsafe = fqName.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "fqName.toUnsafe()");
        String strRenderFqName = renderFqName(unsafe);
        if (strRenderFqName.length() > 0) {
            sb.append(" ");
            sb.append(strRenderFqName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageView(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
        renderPackageHeader(packageViewDescriptor.getFqName(), "package", sb);
        if (getDebugMode()) {
            sb.append(" in context of ");
            renderName(packageViewDescriptor.getModule(), sb, false);
        }
    }

    private final void renderPossiblyInnerType(StringBuilder sb, PossiblyInnerType possiblyInnerType) {
        PossiblyInnerType outerType = possiblyInnerType.getOuterType();
        if (outerType != null) {
            renderPossiblyInnerType(sb, outerType);
            sb.append('.');
            Name name = possiblyInnerType.getClassifierDescriptor().getName();
            Intrinsics.checkNotNullExpressionValue(name, "possiblyInnerType.classifierDescriptor.name");
            sb.append(renderName(name, false));
        } else {
            TypeConstructor typeConstructor = possiblyInnerType.getClassifierDescriptor().getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor, "possiblyInnerType.classi…escriptor.typeConstructor");
            sb.append(renderTypeConstructor(typeConstructor));
        }
        sb.append(renderTypeArguments(possiblyInnerType.getArguments()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderProperty(PropertyDescriptor propertyDescriptor, StringBuilder sb) throws IOException {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderPropertyAnnotations(propertyDescriptor, sb);
                List<ReceiverParameterDescriptor> contextReceiverParameters = propertyDescriptor.getContextReceiverParameters();
                Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "property.contextReceiverParameters");
                renderContextReceivers(contextReceiverParameters, sb);
                DescriptorVisibility visibility = propertyDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "property.visibility");
                renderVisibility(visibility, sb);
                boolean z2 = false;
                renderModifier(sb, getModifiers().contains(DescriptorRendererModifier.CONST) && propertyDescriptor.isConst(), "const");
                renderMemberModifiers(propertyDescriptor, sb);
                renderModalityForCallable(propertyDescriptor, sb);
                renderOverride(propertyDescriptor, sb);
                if (getModifiers().contains(DescriptorRendererModifier.LATEINIT) && propertyDescriptor.isLateInit()) {
                    z2 = true;
                }
                renderModifier(sb, z2, "lateinit");
                renderMemberKind(propertyDescriptor, sb);
            }
            renderValVarPrefix$default(this, propertyDescriptor, sb, false, 4, null);
            List<TypeParameterDescriptor> typeParameters = propertyDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "property.typeParameters");
            renderTypeParameters(typeParameters, sb, true);
            renderReceiver(propertyDescriptor, sb);
        }
        renderName(propertyDescriptor, sb, true);
        sb.append(": ");
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "property.type");
        sb.append(renderType(type));
        renderReceiverAfterName(propertyDescriptor, sb);
        renderInitializer(propertyDescriptor, sb);
        List<TypeParameterDescriptor> typeParameters2 = propertyDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "property.typeParameters");
        renderWhereSuffix(typeParameters2, sb);
    }

    private final void renderPropertyAnnotations(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            renderAnnotations$default(this, sb, propertyDescriptor, null, 2, null);
            FieldDescriptor backingField = propertyDescriptor.getBackingField();
            if (backingField != null) {
                renderAnnotations(sb, backingField, AnnotationUseSiteTarget.FIELD);
            }
            FieldDescriptor delegateField = propertyDescriptor.getDelegateField();
            if (delegateField != null) {
                renderAnnotations(sb, delegateField, AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
            }
            if (getPropertyAccessorRenderingPolicy() == PropertyAccessorRenderingPolicy.NONE) {
                PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                if (getter != null) {
                    renderAnnotations(sb, getter, AnnotationUseSiteTarget.PROPERTY_GETTER);
                }
                PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                if (setter != null) {
                    renderAnnotations(sb, setter, AnnotationUseSiteTarget.PROPERTY_SETTER);
                    List<ValueParameterDescriptor> valueParameters = setter.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "setter.valueParameters");
                    ValueParameterDescriptor it = (ValueParameterDescriptor) CollectionsKt___CollectionsKt.single((List) valueParameters);
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    renderAnnotations(sb, it, AnnotationUseSiteTarget.SETTER_PARAMETER);
                }
            }
        }
    }

    private final void renderReceiver(CallableDescriptor callableDescriptor, StringBuilder sb) {
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            renderAnnotations(sb, extensionReceiverParameter, AnnotationUseSiteTarget.RECEIVER);
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "receiver.type");
            sb.append(renderForReceiver(type));
            sb.append(StrPool.DOT);
        }
    }

    private final void renderReceiverAfterName(CallableDescriptor callableDescriptor, StringBuilder sb) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        if (getReceiverAfterName() && (extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter()) != null) {
            sb.append(" on ");
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "receiver.type");
            sb.append(renderType(type));
        }
    }

    private final void renderSimpleType(StringBuilder sb, SimpleType simpleType) {
        if (Intrinsics.areEqual(simpleType, TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE) || TypeUtils.isDontCarePlaceholder(simpleType)) {
            sb.append("???");
            return;
        }
        if (ErrorUtils.isUninferredTypeVariable(simpleType)) {
            if (!getUninferredTypeParameterAsName()) {
                sb.append("???");
                return;
            }
            TypeConstructor constructor = simpleType.getConstructor();
            Intrinsics.checkNotNull(constructor, "null cannot be cast to non-null type org.jetbrains.kotlin.types.error.ErrorTypeConstructor");
            sb.append(renderError(((ErrorTypeConstructor) constructor).getParam(0)));
            return;
        }
        if (KotlinTypeKt.isError(simpleType)) {
            renderDefaultType(sb, simpleType);
        } else if (shouldRenderAsPrettyFunctionType(simpleType)) {
            renderFunctionType(sb, simpleType);
        } else {
            renderDefaultType(sb, simpleType);
        }
    }

    private final void renderSpaceIfNeeded(StringBuilder sb) {
        int length = sb.length();
        if (length == 0 || sb.charAt(length - 1) != ' ') {
            sb.append(' ');
        }
    }

    private final void renderSuperTypes(ClassDescriptor classDescriptor, StringBuilder sb) throws IOException {
        if (getWithoutSuperTypes() || KotlinBuiltIns.isNothing(classDescriptor.getDefaultType())) {
            return;
        }
        Collection<KotlinType> collectionMo2087getSupertypes = classDescriptor.getTypeConstructor().mo2087getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo2087getSupertypes, "klass.typeConstructor.supertypes");
        if (collectionMo2087getSupertypes.isEmpty()) {
            return;
        }
        if (collectionMo2087getSupertypes.size() == 1 && KotlinBuiltIns.isAnyOrNullableAny(collectionMo2087getSupertypes.iterator().next())) {
            return;
        }
        renderSpaceIfNeeded(sb);
        sb.append(": ");
        CollectionsKt___CollectionsKt.joinTo(collectionMo2087getSupertypes, sb, (124 & 2) != 0 ? ", " : ", ", (124 & 4) != 0 ? "" : null, (124 & 8) == 0 ? null : "", (124 & 16) != 0 ? -1 : 0, (124 & 32) != 0 ? "..." : null, (124 & 64) != 0 ? null : new Function1<KotlinType, CharSequence>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderSuperTypes.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(KotlinType it) {
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                return descriptorRendererImpl.renderType(it);
            }
        });
    }

    private final void renderSuspendModifier(FunctionDescriptor functionDescriptor, StringBuilder sb) {
        renderModifier(sb, functionDescriptor.isSuspend(), "suspend");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeAlias(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
        renderAnnotations$default(this, sb, typeAliasDescriptor, null, 2, null);
        DescriptorVisibility visibility = typeAliasDescriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "typeAlias.visibility");
        renderVisibility(visibility, sb);
        renderMemberModifiers(typeAliasDescriptor, sb);
        sb.append(renderKeyword("typealias"));
        sb.append(" ");
        renderName(typeAliasDescriptor, sb, true);
        List<TypeParameterDescriptor> declaredTypeParameters = typeAliasDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "typeAlias.declaredTypeParameters");
        renderTypeParameters(declaredTypeParameters, sb, false);
        renderCapturedTypeParametersIfRequired(typeAliasDescriptor, sb);
        sb.append(" = ");
        sb.append(renderType(typeAliasDescriptor.getUnderlyingType()));
    }

    private final void renderTypeConstructorAndArguments(StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor) {
        PossiblyInnerType possiblyInnerTypeBuildPossiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(kotlinType);
        if (possiblyInnerTypeBuildPossiblyInnerType != null) {
            renderPossiblyInnerType(sb, possiblyInnerTypeBuildPossiblyInnerType);
        } else {
            sb.append(renderTypeConstructor(typeConstructor));
            sb.append(renderTypeArguments(kotlinType.getArguments()));
        }
    }

    public static /* synthetic */ void renderTypeConstructorAndArguments$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            typeConstructor = kotlinType.getConstructor();
        }
        descriptorRendererImpl.renderTypeConstructorAndArguments(sb, kotlinType, typeConstructor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeParameter(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb, boolean z2) {
        if (z2) {
            sb.append(lt());
        }
        if (getVerbose()) {
            sb.append("/*");
            sb.append(typeParameterDescriptor.getIndex());
            sb.append("*/ ");
        }
        renderModifier(sb, typeParameterDescriptor.isReified(), "reified");
        String label = typeParameterDescriptor.getVariance().getLabel();
        boolean z3 = true;
        renderModifier(sb, label.length() > 0, label);
        renderAnnotations$default(this, sb, typeParameterDescriptor, null, 2, null);
        renderName(typeParameterDescriptor, sb, z2);
        int size = typeParameterDescriptor.getUpperBounds().size();
        if ((size > 1 && !z2) || size == 1) {
            KotlinType upperBound = typeParameterDescriptor.getUpperBounds().iterator().next();
            if (!KotlinBuiltIns.isDefaultBound(upperBound)) {
                sb.append(" : ");
                Intrinsics.checkNotNullExpressionValue(upperBound, "upperBound");
                sb.append(renderType(upperBound));
            }
        } else if (z2) {
            for (KotlinType upperBound2 : typeParameterDescriptor.getUpperBounds()) {
                if (!KotlinBuiltIns.isDefaultBound(upperBound2)) {
                    if (z3) {
                        sb.append(" : ");
                    } else {
                        sb.append(" & ");
                    }
                    Intrinsics.checkNotNullExpressionValue(upperBound2, "upperBound");
                    sb.append(renderType(upperBound2));
                    z3 = false;
                }
            }
        }
        if (z2) {
            sb.append(gt());
        }
    }

    private final void renderTypeParameterList(StringBuilder sb, List<? extends TypeParameterDescriptor> list) {
        Iterator<? extends TypeParameterDescriptor> it = list.iterator();
        while (it.hasNext()) {
            renderTypeParameter(it.next(), sb, false);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
    }

    private final void renderTypeParameters(List<? extends TypeParameterDescriptor> list, StringBuilder sb, boolean z2) {
        if (!getWithoutTypeParameters() && (!list.isEmpty())) {
            sb.append(lt());
            renderTypeParameterList(sb, list);
            sb.append(gt());
            if (z2) {
                sb.append(" ");
            }
        }
    }

    private final void renderValVarPrefix(VariableDescriptor variableDescriptor, StringBuilder sb, boolean z2) {
        if (z2 || !(variableDescriptor instanceof ValueParameterDescriptor)) {
            sb.append(renderKeyword(variableDescriptor.isVar() ? "var" : "val"));
            sb.append(" ");
        }
    }

    public static /* synthetic */ void renderValVarPrefix$default(DescriptorRendererImpl descriptorRendererImpl, VariableDescriptor variableDescriptor, StringBuilder sb, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        descriptorRendererImpl.renderValVarPrefix(variableDescriptor, sb, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void renderValueParameter(kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r10, boolean r11, java.lang.StringBuilder r12, boolean r13) {
        /*
            r9 = this;
            if (r13 == 0) goto L10
            java.lang.String r0 = "value-parameter"
            java.lang.String r0 = r9.renderKeyword(r0)
            r12.append(r0)
            java.lang.String r0 = " "
            r12.append(r0)
        L10:
            boolean r0 = r9.getVerbose()
            if (r0 == 0) goto L27
            java.lang.String r0 = "/*"
            r12.append(r0)
            int r0 = r10.getIndex()
            r12.append(r0)
        */
        //  java.lang.String r0 = "*/ "
        /*
            r12.append(r0)
        L27:
            r4 = 0
            r5 = 2
            r6 = 0
            r1 = r9
            r2 = r12
            r3 = r10
            renderAnnotations$default(r1, r2, r3, r4, r5, r6)
            boolean r0 = r10.isCrossinline()
            java.lang.String r1 = "crossinline"
            r9.renderModifier(r12, r0, r1)
            boolean r0 = r10.isNoinline()
            java.lang.String r1 = "noinline"
            r9.renderModifier(r12, r0, r1)
            boolean r0 = r9.getRenderPrimaryConstructorParametersAsProperties()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L65
            kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor r0 = r10.getContainingDeclaration()
            boolean r3 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor
            if (r3 == 0) goto L55
            kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor) r0
            goto L56
        L55:
            r0 = 0
        L56:
            if (r0 == 0) goto L60
            boolean r0 = r0.isPrimary()
            if (r0 != r1) goto L60
            r0 = r1
            goto L61
        L60:
            r0 = r2
        L61:
            if (r0 == 0) goto L65
            r8 = r1
            goto L66
        L65:
            r8 = r2
        L66:
            if (r8 == 0) goto L71
            boolean r0 = r9.getActualPropertiesInPrimaryConstructor()
            java.lang.String r3 = "actual"
            r9.renderModifier(r12, r0, r3)
        L71:
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r3.renderVariable(r4, r5, r6, r7, r8)
            kotlin.jvm.functions.Function1 r11 = r9.getDefaultParameterValueRenderer()
            if (r11 == 0) goto L91
            boolean r11 = r9.getDebugMode()
            if (r11 == 0) goto L8a
            boolean r11 = r10.declaresDefaultValue()
            goto L8e
        L8a:
            boolean r11 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.declaresOrInheritsDefaultValue(r10)
        L8e:
            if (r11 == 0) goto L91
            goto L92
        L91:
            r1 = r2
        L92:
            if (r1 == 0) goto Lb5
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r13 = " = "
            r11.append(r13)
            kotlin.jvm.functions.Function1 r13 = r9.getDefaultParameterValueRenderer()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r13)
            java.lang.Object r10 = r13.invoke(r10)
            java.lang.String r10 = (java.lang.String) r10
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            r12.append(r10)
        Lb5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderValueParameter(kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor, boolean, java.lang.StringBuilder, boolean):void");
    }

    private final void renderValueParameters(Collection<? extends ValueParameterDescriptor> collection, boolean z2, StringBuilder sb) {
        boolean zShouldRenderParameterNames = shouldRenderParameterNames(z2);
        int size = collection.size();
        getValueParametersHandler().appendBeforeValueParameters(size, sb);
        int i2 = 0;
        for (ValueParameterDescriptor valueParameterDescriptor : collection) {
            getValueParametersHandler().appendBeforeValueParameter(valueParameterDescriptor, i2, size, sb);
            renderValueParameter(valueParameterDescriptor, zShouldRenderParameterNames, sb, false);
            getValueParametersHandler().appendAfterValueParameter(valueParameterDescriptor, i2, size, sb);
            i2++;
        }
        getValueParametersHandler().appendAfterValueParameters(size, sb);
    }

    private final void renderVariable(VariableDescriptor variableDescriptor, boolean z2, StringBuilder sb, boolean z3, boolean z4) {
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "variable.type");
        ValueParameterDescriptor valueParameterDescriptor = variableDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) variableDescriptor : null;
        KotlinType varargElementType = valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null;
        KotlinType kotlinType = varargElementType == null ? type : varargElementType;
        renderModifier(sb, varargElementType != null, "vararg");
        if (z4 || (z3 && !getStartFromName())) {
            renderValVarPrefix(variableDescriptor, sb, z4);
        }
        if (z2) {
            renderName(variableDescriptor, sb, z3);
            sb.append(": ");
        }
        sb.append(renderType(kotlinType));
        renderInitializer(variableDescriptor, sb);
        if (!getVerbose() || varargElementType == null) {
            return;
        }
        sb.append(" /*");
        sb.append(renderType(type));
        sb.append(URLUtil.WAR_URL_SEPARATOR);
    }

    private final boolean renderVisibility(DescriptorVisibility descriptorVisibility, StringBuilder sb) {
        if (!getModifiers().contains(DescriptorRendererModifier.VISIBILITY)) {
            return false;
        }
        if (getNormalizedVisibilities()) {
            descriptorVisibility = descriptorVisibility.normalize();
        }
        if (!getRenderDefaultVisibility() && Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.DEFAULT_VISIBILITY)) {
            return false;
        }
        sb.append(renderKeyword(descriptorVisibility.getInternalDisplayName()));
        sb.append(" ");
        return true;
    }

    private final void renderWhereSuffix(List<? extends TypeParameterDescriptor> list, StringBuilder sb) throws IOException {
        if (getWithoutTypeParameters()) {
            return;
        }
        ArrayList arrayList = new ArrayList(0);
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "typeParameter.upperBounds");
            for (KotlinType it : CollectionsKt___CollectionsKt.drop(upperBounds, 1)) {
                StringBuilder sb2 = new StringBuilder();
                Name name = typeParameterDescriptor.getName();
                Intrinsics.checkNotNullExpressionValue(name, "typeParameter.name");
                sb2.append(renderName(name, false));
                sb2.append(" : ");
                Intrinsics.checkNotNullExpressionValue(it, "it");
                sb2.append(renderType(it));
                arrayList.add(sb2.toString());
            }
        }
        if (!arrayList.isEmpty()) {
            sb.append(" ");
            sb.append(renderKeyword("where"));
            sb.append(" ");
            CollectionsKt___CollectionsKt.joinTo(arrayList, sb, (124 & 2) != 0 ? ", " : ", ", (124 & 4) != 0 ? "" : null, (124 & 8) == 0 ? null : "", (124 & 16) != 0 ? -1 : 0, (124 & 32) != 0 ? "..." : null, (124 & 64) != 0 ? null : null);
        }
    }

    private final boolean shouldRenderAsPrettyFunctionType(KotlinType kotlinType) {
        boolean z2;
        if (!FunctionTypesKt.isBuiltinFunctionalType(kotlinType)) {
            return false;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        if ((arguments instanceof Collection) && arguments.isEmpty()) {
            z2 = true;
        } else {
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                if (((TypeProjection) it.next()).isStarProjection()) {
                    z2 = false;
                    break;
                }
            }
            z2 = true;
        }
        return z2;
    }

    private final boolean shouldRenderParameterNames(boolean z2) {
        int i2 = WhenMappings.$EnumSwitchMapping$1[getParameterNameRenderingPolicy().ordinal()];
        if (i2 == 1) {
            return true;
        }
        if (i2 != 2) {
            if (i2 != 3) {
                throw new NoWhenBranchMatchedException();
            }
        } else if (!z2) {
            return true;
        }
        return false;
    }

    public boolean getActualPropertiesInPrimaryConstructor() {
        return this.options.getActualPropertiesInPrimaryConstructor();
    }

    public boolean getAlwaysRenderModifiers() {
        return this.options.getAlwaysRenderModifiers();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    @NotNull
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return this.options.getAnnotationArgumentsRenderingPolicy();
    }

    @Nullable
    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return this.options.getAnnotationFilter();
    }

    public boolean getBoldOnlyForNamesInHtml() {
        return this.options.getBoldOnlyForNamesInHtml();
    }

    public boolean getClassWithPrimaryConstructor() {
        return this.options.getClassWithPrimaryConstructor();
    }

    @NotNull
    public ClassifierNamePolicy getClassifierNamePolicy() {
        return this.options.getClassifierNamePolicy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getDebugMode() {
        return this.options.getDebugMode();
    }

    @Nullable
    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return this.options.getDefaultParameterValueRenderer();
    }

    public boolean getEachAnnotationOnNewLine() {
        return this.options.getEachAnnotationOnNewLine();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getEnhancedTypes() {
        return this.options.getEnhancedTypes();
    }

    @NotNull
    public Set<FqName> getExcludedAnnotationClasses() {
        return this.options.getExcludedAnnotationClasses();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    @NotNull
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return this.options.getExcludedTypeAnnotationClasses();
    }

    public boolean getIncludeAdditionalModifiers() {
        return this.options.getIncludeAdditionalModifiers();
    }

    public boolean getIncludeAnnotationArguments() {
        return this.options.getIncludeAnnotationArguments();
    }

    public boolean getIncludeEmptyAnnotationArguments() {
        return this.options.getIncludeEmptyAnnotationArguments();
    }

    public boolean getIncludePropertyConstant() {
        return this.options.getIncludePropertyConstant();
    }

    public boolean getInformativeErrorType() {
        return this.options.getInformativeErrorType();
    }

    @NotNull
    public Set<DescriptorRendererModifier> getModifiers() {
        return this.options.getModifiers();
    }

    public boolean getNormalizedVisibilities() {
        return this.options.getNormalizedVisibilities();
    }

    @NotNull
    public final DescriptorRendererOptionsImpl getOptions() {
        return this.options;
    }

    @NotNull
    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return this.options.getOverrideRenderingPolicy();
    }

    @NotNull
    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return this.options.getParameterNameRenderingPolicy();
    }

    public boolean getParameterNamesInFunctionalTypes() {
        return this.options.getParameterNamesInFunctionalTypes();
    }

    public boolean getPresentableUnresolvedTypes() {
        return this.options.getPresentableUnresolvedTypes();
    }

    @NotNull
    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return this.options.getPropertyAccessorRenderingPolicy();
    }

    public boolean getReceiverAfterName() {
        return this.options.getReceiverAfterName();
    }

    public boolean getRenderCompanionObjectName() {
        return this.options.getRenderCompanionObjectName();
    }

    public boolean getRenderConstructorDelegation() {
        return this.options.getRenderConstructorDelegation();
    }

    public boolean getRenderConstructorKeyword() {
        return this.options.getRenderConstructorKeyword();
    }

    public boolean getRenderDefaultAnnotationArguments() {
        return this.options.getRenderDefaultAnnotationArguments();
    }

    public boolean getRenderDefaultModality() {
        return this.options.getRenderDefaultModality();
    }

    public boolean getRenderDefaultVisibility() {
        return this.options.getRenderDefaultVisibility();
    }

    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return this.options.getRenderPrimaryConstructorParametersAsProperties();
    }

    public boolean getRenderTypeExpansions() {
        return this.options.getRenderTypeExpansions();
    }

    public boolean getRenderUnabbreviatedType() {
        return this.options.getRenderUnabbreviatedType();
    }

    public boolean getSecondaryConstructorsAsPrimary() {
        return this.options.getSecondaryConstructorsAsPrimary();
    }

    public boolean getStartFromDeclarationKeyword() {
        return this.options.getStartFromDeclarationKeyword();
    }

    public boolean getStartFromName() {
        return this.options.getStartFromName();
    }

    @NotNull
    public RenderingFormat getTextFormat() {
        return this.options.getTextFormat();
    }

    @NotNull
    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return this.options.getTypeNormalizer();
    }

    public boolean getUninferredTypeParameterAsName() {
        return this.options.getUninferredTypeParameterAsName();
    }

    public boolean getUnitReturnType() {
        return this.options.getUnitReturnType();
    }

    @NotNull
    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return this.options.getValueParametersHandler();
    }

    public boolean getVerbose() {
        return this.options.getVerbose();
    }

    public boolean getWithDefinedIn() {
        return this.options.getWithDefinedIn();
    }

    public boolean getWithSourceFileForTopLevel() {
        return this.options.getWithSourceFileForTopLevel();
    }

    public boolean getWithoutReturnType() {
        return this.options.getWithoutReturnType();
    }

    public boolean getWithoutSuperTypes() {
        return this.options.getWithoutSuperTypes();
    }

    public boolean getWithoutTypeParameters() {
        return this.options.getWithoutTypeParameters();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String render(@NotNull DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        StringBuilder sb = new StringBuilder();
        declarationDescriptor.accept(new RenderDeclarationDescriptorVisitor(), sb);
        if (getWithDefinedIn()) {
            appendDefinedIn(sb, declarationDescriptor);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderAnnotation(@NotNull AnnotationDescriptor annotation, @Nullable AnnotationUseSiteTarget annotationUseSiteTarget) throws IOException {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        if (annotationUseSiteTarget != null) {
            sb.append(annotationUseSiteTarget.getRenderName() + ':');
        }
        KotlinType type = annotation.getType();
        sb.append(renderType(type));
        if (getIncludeAnnotationArguments()) {
            List<String> listRenderAndSortAnnotationArguments = renderAndSortAnnotationArguments(annotation);
            if (getIncludeEmptyAnnotationArguments() || (!listRenderAndSortAnnotationArguments.isEmpty())) {
                CollectionsKt___CollectionsKt.joinTo(listRenderAndSortAnnotationArguments, sb, (124 & 2) != 0 ? ", " : ", ", (124 & 4) != 0 ? "" : "(", (124 & 8) == 0 ? ")" : "", (124 & 16) != 0 ? -1 : 0, (124 & 32) != 0 ? "..." : null, (124 & 64) != 0 ? null : null);
            }
        }
        if (getVerbose() && (KotlinTypeKt.isError(type) || (type.getConstructor().mo2086getDeclarationDescriptor() instanceof NotFoundClasses.MockClassDescriptor))) {
            sb.append(" /* annotation class not found */");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public String renderClassifierName(@NotNull ClassifierDescriptor klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return ErrorUtils.isError(klass) ? klass.getTypeConstructor().toString() : getClassifierNamePolicy().renderClassifier(klass, this);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderFlexibleType(@NotNull String lowerRendered, @NotNull String upperRendered, @NotNull KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(lowerRendered, "lowerRendered");
        Intrinsics.checkNotNullParameter(upperRendered, "upperRendered");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        if (RenderingUtilsKt.typeStringsDifferOnlyInNullability(lowerRendered, upperRendered)) {
            if (!StringsKt__StringsJVMKt.startsWith$default(upperRendered, "(", false, 2, null)) {
                return lowerRendered + '!';
            }
            return '(' + lowerRendered + ")!";
        }
        ClassifierNamePolicy classifierNamePolicy = getClassifierNamePolicy();
        ClassDescriptor collection = builtIns.getCollection();
        Intrinsics.checkNotNullExpressionValue(collection, "builtIns.collection");
        String strSubstringBefore$default = StringsKt__StringsKt.substringBefore$default(classifierNamePolicy.renderClassifier(collection, this), "Collection", (String) null, 2, (Object) null);
        String strReplacePrefixesInTypeRepresentations = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default + "Mutable", upperRendered, strSubstringBefore$default, strSubstringBefore$default + "(Mutable)");
        if (strReplacePrefixesInTypeRepresentations != null) {
            return strReplacePrefixesInTypeRepresentations;
        }
        String strReplacePrefixesInTypeRepresentations2 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default + "MutableMap.MutableEntry", upperRendered, strSubstringBefore$default + "Map.Entry", strSubstringBefore$default + "(Mutable)Map.(Mutable)Entry");
        if (strReplacePrefixesInTypeRepresentations2 != null) {
            return strReplacePrefixesInTypeRepresentations2;
        }
        ClassifierNamePolicy classifierNamePolicy2 = getClassifierNamePolicy();
        ClassDescriptor array = builtIns.getArray();
        Intrinsics.checkNotNullExpressionValue(array, "builtIns.array");
        String strSubstringBefore$default2 = StringsKt__StringsKt.substringBefore$default(classifierNamePolicy2.renderClassifier(array, this), "Array", (String) null, 2, (Object) null);
        String strReplacePrefixesInTypeRepresentations3 = RenderingUtilsKt.replacePrefixesInTypeRepresentations(lowerRendered, strSubstringBefore$default2 + escape("Array<"), upperRendered, strSubstringBefore$default2 + escape("Array<out "), strSubstringBefore$default2 + escape("Array<(out) "));
        if (strReplacePrefixesInTypeRepresentations3 != null) {
            return strReplacePrefixesInTypeRepresentations3;
        }
        return '(' + lowerRendered + StrPool.DOUBLE_DOT + upperRendered + ')';
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderFqName(@NotNull FqNameUnsafe fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        List<Name> listPathSegments = fqName.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "fqName.pathSegments()");
        return renderFqName(listPathSegments);
    }

    @NotNull
    public String renderMessage(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        int i2 = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i2 == 1) {
            return message;
        }
        if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        return "<i>" + message + "</i>";
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderName(@NotNull Name name, boolean z2) {
        Intrinsics.checkNotNullParameter(name, "name");
        String strEscape = escape(RenderingUtilsKt.render(name));
        if (!getBoldOnlyForNamesInHtml() || getTextFormat() != RenderingFormat.HTML || !z2) {
            return strEscape;
        }
        return "<b>" + strEscape + "</b>";
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderType(@NotNull KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        StringBuilder sb = new StringBuilder();
        renderNormalizedType(sb, getTypeNormalizer().invoke(type));
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public String renderTypeArguments(@NotNull List<? extends TypeProjection> typeArguments) throws IOException {
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(lt());
        appendTypeProjections(sb, typeArguments);
        sb.append(gt());
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public String renderTypeConstructor(@NotNull TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = typeConstructor.mo2086getDeclarationDescriptor();
        if (classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeParameterDescriptor ? true : classifierDescriptorMo2086getDeclarationDescriptor instanceof ClassDescriptor ? true : classifierDescriptorMo2086getDeclarationDescriptor instanceof TypeAliasDescriptor) {
            return renderClassifierName(classifierDescriptorMo2086getDeclarationDescriptor);
        }
        if (classifierDescriptorMo2086getDeclarationDescriptor == null) {
            return typeConstructor instanceof IntersectionTypeConstructor ? ((IntersectionTypeConstructor) typeConstructor).makeDebugNameForIntersectionType(new Function1<KotlinType, Object>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderTypeConstructor.1
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final Object invoke(@NotNull KotlinType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it instanceof StubTypeForBuilderInference ? ((StubTypeForBuilderInference) it).getOriginalTypeVariable() : it;
                }
            }) : typeConstructor.toString();
        }
        throw new IllegalStateException(("Unexpected classifier: " + classifierDescriptorMo2086getDeclarationDescriptor.getClass()).toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderTypeProjection(@NotNull TypeProjection typeProjection) throws IOException {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        StringBuilder sb = new StringBuilder();
        appendTypeProjections(sb, CollectionsKt__CollectionsJVMKt.listOf(typeProjection));
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setAnnotationArgumentsRenderingPolicy(@NotNull AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkNotNullParameter(annotationArgumentsRenderingPolicy, "<set-?>");
        this.options.setAnnotationArgumentsRenderingPolicy(annotationArgumentsRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setClassifierNamePolicy(@NotNull ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkNotNullParameter(classifierNamePolicy, "<set-?>");
        this.options.setClassifierNamePolicy(classifierNamePolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setDebugMode(boolean z2) {
        this.options.setDebugMode(z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setExcludedTypeAnnotationClasses(@NotNull Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setExcludedTypeAnnotationClasses(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setModifiers(@NotNull Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.options.setModifiers(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setParameterNameRenderingPolicy(@NotNull ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkNotNullParameter(parameterNameRenderingPolicy, "<set-?>");
        this.options.setParameterNameRenderingPolicy(parameterNameRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setReceiverAfterName(boolean z2) {
        this.options.setReceiverAfterName(z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setRenderCompanionObjectName(boolean z2) {
        this.options.setRenderCompanionObjectName(z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setStartFromName(boolean z2) {
        this.options.setStartFromName(z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setTextFormat(@NotNull RenderingFormat renderingFormat) {
        Intrinsics.checkNotNullParameter(renderingFormat, "<set-?>");
        this.options.setTextFormat(renderingFormat);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setVerbose(boolean z2) {
        this.options.setVerbose(z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithDefinedIn(boolean z2) {
        this.options.setWithDefinedIn(z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutSuperTypes(boolean z2) {
        this.options.setWithoutSuperTypes(z2);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutTypeParameters(boolean z2) {
        this.options.setWithoutTypeParameters(z2);
    }

    private final String renderFqName(List<Name> list) {
        return escape(RenderingUtilsKt.renderFqName(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderName(DeclarationDescriptor declarationDescriptor, StringBuilder sb, boolean z2) {
        Name name = declarationDescriptor.getName();
        Intrinsics.checkNotNullExpressionValue(name, "descriptor.name");
        sb.append(renderName(name, z2));
    }
}
