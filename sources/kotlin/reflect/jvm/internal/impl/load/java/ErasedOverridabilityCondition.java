package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nErasedOverridabilityCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ErasedOverridabilityCondition.kt\norg/jetbrains/kotlin/load/java/ErasedOverridabilityCondition\n+ 2 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n*L\n1#1,63:1\n1229#2,2:64\n*S KotlinDebug\n*F\n+ 1 ErasedOverridabilityCondition.kt\norg/jetbrains/kotlin/load/java/ErasedOverridabilityCondition\n*L\n44#1:64,2\n*E\n"})
/* loaded from: classes8.dex */
public final class ErasedOverridabilityCondition implements ExternalOverridabilityCondition {

    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OverridingUtil.OverrideCompatibilityInfo.Result.values().length];
            try {
                iArr[OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.SUCCESS_ONLY;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Result isOverridable(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor classDescriptor) {
        boolean z2;
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (subDescriptor instanceof JavaMethodDescriptor) {
            JavaMethodDescriptor javaMethodDescriptor = (JavaMethodDescriptor) subDescriptor;
            Intrinsics.checkNotNullExpressionValue(javaMethodDescriptor.getTypeParameters(), "subDescriptor.typeParameters");
            if (!(!r0.isEmpty())) {
                OverridingUtil.OverrideCompatibilityInfo basicOverridabilityProblem = OverridingUtil.getBasicOverridabilityProblem(superDescriptor, subDescriptor);
                if ((basicOverridabilityProblem != null ? basicOverridabilityProblem.getResult() : null) != null) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                List<ValueParameterDescriptor> valueParameters = javaMethodDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "subDescriptor.valueParameters");
                Sequence map = SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence(valueParameters), new Function1<ValueParameterDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.ErasedOverridabilityCondition$isOverridable$signatureTypes$1
                    @Override // kotlin.jvm.functions.Function1
                    @NotNull
                    public final KotlinType invoke(ValueParameterDescriptor valueParameterDescriptor) {
                        return valueParameterDescriptor.getType();
                    }
                });
                KotlinType returnType = javaMethodDescriptor.getReturnType();
                Intrinsics.checkNotNull(returnType);
                Sequence sequencePlus = SequencesKt___SequencesKt.plus((Sequence<? extends KotlinType>) map, returnType);
                ReceiverParameterDescriptor extensionReceiverParameter = javaMethodDescriptor.getExtensionReceiverParameter();
                Iterator it = SequencesKt___SequencesKt.plus(sequencePlus, (Iterable) CollectionsKt__CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null)).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    KotlinType kotlinType = (KotlinType) it.next();
                    if ((kotlinType.getArguments().isEmpty() ^ true) && !(kotlinType.unwrap() instanceof RawTypeImpl)) {
                        z2 = true;
                        break;
                    }
                }
                if (z2) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                CallableDescriptor callableDescriptorSubstitute = superDescriptor.substitute(new RawSubstitution(null, 1, null).buildSubstitutor());
                if (callableDescriptorSubstitute == null) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                if (callableDescriptorSubstitute instanceof SimpleFunctionDescriptor) {
                    SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) callableDescriptorSubstitute;
                    Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptor.getTypeParameters(), "erasedSuper.typeParameters");
                    if (!r0.isEmpty()) {
                        callableDescriptorSubstitute = simpleFunctionDescriptor.newCopyBuilder().setTypeParameters(CollectionsKt__CollectionsKt.emptyList()).build();
                        Intrinsics.checkNotNull(callableDescriptorSubstitute);
                    }
                }
                OverridingUtil.OverrideCompatibilityInfo.Result result = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(callableDescriptorSubstitute, subDescriptor, false).getResult();
                Intrinsics.checkNotNullExpressionValue(result, "DEFAULT.isOverridableByW…Descriptor, false).result");
                return WhenMappings.$EnumSwitchMapping$0[result.ordinal()] == 1 ? ExternalOverridabilityCondition.Result.OVERRIDABLE : ExternalOverridabilityCondition.Result.UNKNOWN;
            }
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }
}
