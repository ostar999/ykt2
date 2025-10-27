package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ImplicitClassReceiver;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.util.MemberKindCheck;
import kotlin.reflect.jvm.internal.impl.util.ReturnsCheck;
import kotlin.reflect.jvm.internal.impl.util.ValueParameterCountCheck;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class OperatorChecks extends AbstractModifierChecks {

    @NotNull
    public static final OperatorChecks INSTANCE = new OperatorChecks();

    @NotNull
    private static final List<Checks> checks;

    static {
        Name name = OperatorNameConventions.GET;
        MemberKindCheck.MemberOrExtension memberOrExtension = MemberKindCheck.MemberOrExtension.INSTANCE;
        Check[] checkArr = {memberOrExtension, new ValueParameterCountCheck.AtLeast(1)};
        Name name2 = OperatorNameConventions.SET;
        Check[] checkArr2 = {memberOrExtension, new ValueParameterCountCheck.AtLeast(2)};
        Name name3 = OperatorNameConventions.GET_VALUE;
        NoDefaultAndVarargsCheck noDefaultAndVarargsCheck = NoDefaultAndVarargsCheck.INSTANCE;
        IsKPropertyCheck isKPropertyCheck = IsKPropertyCheck.INSTANCE;
        Name name4 = OperatorNameConventions.CONTAINS;
        ValueParameterCountCheck.SingleValueParameter singleValueParameter = ValueParameterCountCheck.SingleValueParameter.INSTANCE;
        ReturnsCheck.ReturnsBoolean returnsBoolean = ReturnsCheck.ReturnsBoolean.INSTANCE;
        Name name5 = OperatorNameConventions.ITERATOR;
        ValueParameterCountCheck.NoValueParameters noValueParameters = ValueParameterCountCheck.NoValueParameters.INSTANCE;
        checks = CollectionsKt__CollectionsKt.listOf((Object[]) new Checks[]{new Checks(name, checkArr, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(name2, checkArr2, new Function1<FunctionDescriptor, String>() { // from class: kotlin.reflect.jvm.internal.impl.util.OperatorChecks$checks$1
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final String invoke(@NotNull FunctionDescriptor $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                List<ValueParameterDescriptor> valueParameters = $receiver.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "valueParameters");
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt___CollectionsKt.lastOrNull((List) valueParameters);
                boolean z2 = false;
                if (valueParameterDescriptor != null) {
                    if (!DescriptorUtilsKt.declaresOrInheritsDefaultValue(valueParameterDescriptor) && valueParameterDescriptor.getVarargElementType() == null) {
                        z2 = true;
                    }
                }
                OperatorChecks operatorChecks = OperatorChecks.INSTANCE;
                if (z2) {
                    return null;
                }
                return "last parameter should not have a default value or be a vararg";
            }
        }), new Checks(name3, new Check[]{memberOrExtension, noDefaultAndVarargsCheck, new ValueParameterCountCheck.AtLeast(2), isKPropertyCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.SET_VALUE, new Check[]{memberOrExtension, noDefaultAndVarargsCheck, new ValueParameterCountCheck.AtLeast(3), isKPropertyCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.PROVIDE_DELEGATE, new Check[]{memberOrExtension, noDefaultAndVarargsCheck, new ValueParameterCountCheck.Equals(2), isKPropertyCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.INVOKE, new Check[]{memberOrExtension}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(name4, new Check[]{memberOrExtension, singleValueParameter, noDefaultAndVarargsCheck, returnsBoolean}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(name5, new Check[]{memberOrExtension, noValueParameters}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.NEXT, new Check[]{memberOrExtension, noValueParameters}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.HAS_NEXT, new Check[]{memberOrExtension, noValueParameters, returnsBoolean}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.RANGE_TO, new Check[]{memberOrExtension, singleValueParameter, noDefaultAndVarargsCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.RANGE_UNTIL, new Check[]{memberOrExtension, singleValueParameter, noDefaultAndVarargsCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.EQUALS, new Check[]{MemberKindCheck.Member.INSTANCE}, new Function1<FunctionDescriptor, String>() { // from class: kotlin.reflect.jvm.internal.impl.util.OperatorChecks$checks$2
            private static final boolean invoke$isAny(DeclarationDescriptor declarationDescriptor) {
                return (declarationDescriptor instanceof ClassDescriptor) && KotlinBuiltIns.isAny((ClassDescriptor) declarationDescriptor);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final String invoke(@NotNull FunctionDescriptor $receiver) {
                boolean z2;
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                OperatorChecks operatorChecks = OperatorChecks.INSTANCE;
                DeclarationDescriptor containingDeclaration = $receiver.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "containingDeclaration");
                boolean z3 = true;
                if (!invoke$isAny(containingDeclaration)) {
                    Collection<? extends FunctionDescriptor> overriddenDescriptors = $receiver.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "overriddenDescriptors");
                    Collection<? extends FunctionDescriptor> collection = overriddenDescriptors;
                    if (collection.isEmpty()) {
                        z2 = false;
                        if (!z2 && !DescriptorUtilKt.isTypedEqualsInValueClass($receiver)) {
                            z3 = false;
                        }
                    } else {
                        Iterator<T> it = collection.iterator();
                        while (it.hasNext()) {
                            DeclarationDescriptor containingDeclaration2 = ((FunctionDescriptor) it.next()).getContainingDeclaration();
                            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "it.containingDeclaration");
                            if (invoke$isAny(containingDeclaration2)) {
                                z2 = true;
                                break;
                            }
                        }
                        z2 = false;
                        if (!z2) {
                            z3 = false;
                        }
                    }
                }
                if (z3) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("must override ''equals()'' in Any");
                DeclarationDescriptor containingDeclaration3 = $receiver.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration3, "containingDeclaration");
                if (InlineClassesUtilsKt.isValueClass(containingDeclaration3)) {
                    DescriptorRenderer descriptorRenderer = DescriptorRenderer.SHORT_NAMES_IN_TYPES;
                    DeclarationDescriptor containingDeclaration4 = $receiver.getContainingDeclaration();
                    Intrinsics.checkNotNull(containingDeclaration4, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    SimpleType defaultType = ((ClassDescriptor) containingDeclaration4).getDefaultType();
                    Intrinsics.checkNotNullExpressionValue(defaultType, "containingDeclaration as…ssDescriptor).defaultType");
                    sb.append(" or define ''equals(other: " + descriptorRenderer.renderType(TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType)) + "): Boolean''");
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
                return string;
            }
        }), new Checks(OperatorNameConventions.COMPARE_TO, new Check[]{memberOrExtension, ReturnsCheck.ReturnsInt.INSTANCE, singleValueParameter, noDefaultAndVarargsCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.BINARY_OPERATION_NAMES, new Check[]{memberOrExtension, singleValueParameter, noDefaultAndVarargsCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.SIMPLE_UNARY_OPERATION_NAMES, new Check[]{memberOrExtension, noValueParameters}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(CollectionsKt__CollectionsKt.listOf((Object[]) new Name[]{OperatorNameConventions.INC, OperatorNameConventions.DEC}), new Check[]{memberOrExtension}, new Function1<FunctionDescriptor, String>() { // from class: kotlin.reflect.jvm.internal.impl.util.OperatorChecks$checks$3
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final String invoke(@NotNull FunctionDescriptor $receiver) {
                boolean zIsSubtypeOf;
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                ReceiverParameterDescriptor dispatchReceiverParameter = $receiver.getDispatchReceiverParameter();
                if (dispatchReceiverParameter == null) {
                    dispatchReceiverParameter = $receiver.getExtensionReceiverParameter();
                }
                OperatorChecks operatorChecks = OperatorChecks.INSTANCE;
                boolean z2 = false;
                if (dispatchReceiverParameter != null) {
                    KotlinType returnType = $receiver.getReturnType();
                    if (returnType != null) {
                        KotlinType type = dispatchReceiverParameter.getType();
                        Intrinsics.checkNotNullExpressionValue(type, "receiver.type");
                        zIsSubtypeOf = TypeUtilsKt.isSubtypeOf(returnType, type);
                    } else {
                        zIsSubtypeOf = false;
                    }
                    if (zIsSubtypeOf || operatorChecks.incDecCheckForExpectClass($receiver, dispatchReceiverParameter)) {
                        z2 = true;
                    }
                }
                if (z2) {
                    return null;
                }
                return "receiver must be a supertype of the return type";
            }
        }), new Checks(OperatorNameConventions.ASSIGNMENT_OPERATIONS, new Check[]{memberOrExtension, ReturnsCheck.ReturnsUnit.INSTANCE, singleValueParameter, noDefaultAndVarargsCheck}, (Function1) null, 4, (DefaultConstructorMarker) null), new Checks(OperatorNameConventions.COMPONENT_REGEX, new Check[]{memberOrExtension, noValueParameters}, (Function1) null, 4, (DefaultConstructorMarker) null)});
    }

    private OperatorChecks() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean incDecCheckForExpectClass(FunctionDescriptor functionDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor) {
        ClassId classId;
        KotlinType returnType;
        ReceiverValue value = receiverParameterDescriptor.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "receiver.value");
        if (!(value instanceof ImplicitClassReceiver)) {
            return false;
        }
        ClassDescriptor classDescriptor = ((ImplicitClassReceiver) value).getClassDescriptor();
        if (!classDescriptor.isExpect() || (classId = DescriptorUtilsKt.getClassId(classDescriptor)) == null) {
            return false;
        }
        ClassifierDescriptor classifierDescriptorFindClassifierAcrossModuleDependencies = FindClassInModuleKt.findClassifierAcrossModuleDependencies(DescriptorUtilsKt.getModule(classDescriptor), classId);
        TypeAliasDescriptor typeAliasDescriptor = classifierDescriptorFindClassifierAcrossModuleDependencies instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) classifierDescriptorFindClassifierAcrossModuleDependencies : null;
        if (typeAliasDescriptor == null || (returnType = functionDescriptor.getReturnType()) == null) {
            return false;
        }
        return TypeUtilsKt.isSubtypeOf(returnType, typeAliasDescriptor.getExpandedType());
    }

    @Override // kotlin.reflect.jvm.internal.impl.util.AbstractModifierChecks
    @NotNull
    public List<Checks> getChecks$descriptors() {
        return checks;
    }
}
