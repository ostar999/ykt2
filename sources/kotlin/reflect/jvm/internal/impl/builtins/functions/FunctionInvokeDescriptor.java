package kotlin.reflect.jvm.internal.impl.builtins.functions;

import androidx.exifinterface.media.ExifInterface;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.util.OperatorNameConventions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nFunctionInvokeDescriptor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FunctionInvokeDescriptor.kt\norg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,161:1\n2624#2,3:162\n1549#2:165\n1620#2,3:166\n1726#2,3:169\n1549#2:172\n1620#2,3:173\n1747#2,3:176\n*S KotlinDebug\n*F\n+ 1 FunctionInvokeDescriptor.kt\norg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor\n*L\n63#1:162,3\n64#1:165\n64#1:166,3\n88#1:169,3\n92#1:172\n92#1:173,3\n106#1:176,3\n*E\n"})
/* loaded from: classes8.dex */
public final class FunctionInvokeDescriptor extends SimpleFunctionDescriptorImpl {

    @NotNull
    public static final Factory Factory = new Factory(null);

    @SourceDebugExtension({"SMAP\nFunctionInvokeDescriptor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FunctionInvokeDescriptor.kt\norg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor$Factory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,161:1\n959#2,7:162\n1549#2:169\n1620#2,3:170\n*S KotlinDebug\n*F\n+ 1 FunctionInvokeDescriptor.kt\norg/jetbrains/kotlin/builtins/functions/FunctionInvokeDescriptor$Factory\n*L\n122#1:162,7\n124#1:169\n124#1:170,3\n*E\n"})
    public static final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final ValueParameterDescriptor createValueParameter(FunctionInvokeDescriptor functionInvokeDescriptor, int i2, TypeParameterDescriptor typeParameterDescriptor) {
            String lowerCase;
            String strAsString = typeParameterDescriptor.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "typeParameter.name.asString()");
            if (Intrinsics.areEqual(strAsString, ExifInterface.GPS_DIRECTION_TRUE)) {
                lowerCase = "instance";
            } else if (Intrinsics.areEqual(strAsString, "E")) {
                lowerCase = SocialConstants.PARAM_RECEIVER;
            } else {
                lowerCase = strAsString.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            Annotations empty = Annotations.Companion.getEMPTY();
            Name nameIdentifier = Name.identifier(lowerCase);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(name)");
            SimpleType defaultType = typeParameterDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "typeParameter.defaultType");
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            return new ValueParameterDescriptorImpl(functionInvokeDescriptor, null, i2, empty, nameIdentifier, defaultType, false, false, false, null, NO_SOURCE);
        }

        @NotNull
        public final FunctionInvokeDescriptor create(@NotNull FunctionClassDescriptor functionClass, boolean z2) {
            Intrinsics.checkNotNullParameter(functionClass, "functionClass");
            List<TypeParameterDescriptor> declaredTypeParameters = functionClass.getDeclaredTypeParameters();
            FunctionInvokeDescriptor functionInvokeDescriptor = new FunctionInvokeDescriptor(functionClass, null, CallableMemberDescriptor.Kind.DECLARATION, z2, null);
            ReceiverParameterDescriptor thisAsReceiverParameter = functionClass.getThisAsReceiverParameter();
            List<ReceiverParameterDescriptor> listEmptyList = CollectionsKt__CollectionsKt.emptyList();
            List<? extends TypeParameterDescriptor> listEmptyList2 = CollectionsKt__CollectionsKt.emptyList();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declaredTypeParameters) {
                if (!(((TypeParameterDescriptor) obj).getVariance() == Variance.IN_VARIANCE)) {
                    break;
                }
                arrayList.add(obj);
            }
            Iterable<IndexedValue> iterableWithIndex = CollectionsKt___CollectionsKt.withIndex(arrayList);
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterableWithIndex, 10));
            for (IndexedValue indexedValue : iterableWithIndex) {
                arrayList2.add(FunctionInvokeDescriptor.Factory.createValueParameter(functionInvokeDescriptor, indexedValue.getIndex(), (TypeParameterDescriptor) indexedValue.getValue()));
            }
            functionInvokeDescriptor.initialize((ReceiverParameterDescriptor) null, thisAsReceiverParameter, listEmptyList, listEmptyList2, (List<ValueParameterDescriptor>) arrayList2, (KotlinType) ((TypeParameterDescriptor) CollectionsKt___CollectionsKt.last((List) declaredTypeParameters)).getDefaultType(), Modality.ABSTRACT, DescriptorVisibilities.PUBLIC);
            functionInvokeDescriptor.setHasSynthesizedParameterNames(true);
            return functionInvokeDescriptor;
        }
    }

    private FunctionInvokeDescriptor(DeclarationDescriptor declarationDescriptor, FunctionInvokeDescriptor functionInvokeDescriptor, CallableMemberDescriptor.Kind kind, boolean z2) {
        super(declarationDescriptor, functionInvokeDescriptor, Annotations.Companion.getEMPTY(), OperatorNameConventions.INVOKE, kind, SourceElement.NO_SOURCE);
        setOperator(true);
        setSuspend(z2);
        setHasStableParameterNames(false);
    }

    public /* synthetic */ FunctionInvokeDescriptor(DeclarationDescriptor declarationDescriptor, FunctionInvokeDescriptor functionInvokeDescriptor, CallableMemberDescriptor.Kind kind, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor, functionInvokeDescriptor, kind, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x005d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor replaceParameterNames(java.util.List<kotlin.reflect.jvm.internal.impl.name.Name> r10) {
        /*
            r9 = this;
            java.util.List r0 = r9.getValueParameters()
            int r0 = r0.size()
            int r1 = r10.size()
            int r0 = r0 - r1
            r1 = 1
            java.lang.String r2 = "valueParameters"
            r3 = 0
            if (r0 != 0) goto L5e
            r4 = r10
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            java.util.List r5 = r9.getValueParameters()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r2)
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.util.List r4 = kotlin.collections.CollectionsKt.zip(r4, r5)
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            boolean r5 = r4 instanceof java.util.Collection
            if (r5 == 0) goto L34
            r5 = r4
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L34
        L32:
            r4 = r1
            goto L5b
        L34:
            java.util.Iterator r4 = r4.iterator()
        L38:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L32
            java.lang.Object r5 = r4.next()
            kotlin.Pair r5 = (kotlin.Pair) r5
            java.lang.Object r6 = r5.component1()
            kotlin.reflect.jvm.internal.impl.name.Name r6 = (kotlin.reflect.jvm.internal.impl.name.Name) r6
            java.lang.Object r5 = r5.component2()
            kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r5 = (kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor) r5
            kotlin.reflect.jvm.internal.impl.name.Name r5 = r5.getName()
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r5)
            if (r5 != 0) goto L38
            r4 = r3
        L5b:
            if (r4 == 0) goto L5e
            return r9
        L5e:
            java.util.List r4 = r9.getValueParameters()
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r2)
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            java.util.ArrayList r2 = new java.util.ArrayList
            r5 = 10
            int r5 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r4, r5)
            r2.<init>(r5)
            java.util.Iterator r4 = r4.iterator()
        L76:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto La4
            java.lang.Object r5 = r4.next()
            kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r5 = (kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor) r5
            kotlin.reflect.jvm.internal.impl.name.Name r6 = r5.getName()
            java.lang.String r7 = "it.name"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r7)
            int r7 = r5.getIndex()
            int r8 = r7 - r0
            if (r8 < 0) goto L9c
            java.lang.Object r8 = r10.get(r8)
            kotlin.reflect.jvm.internal.impl.name.Name r8 = (kotlin.reflect.jvm.internal.impl.name.Name) r8
            if (r8 == 0) goto L9c
            r6 = r8
        L9c:
            kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r5 = r5.copy(r9, r6, r7)
            r2.add(r5)
            goto L76
        La4:
            kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor r0 = kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor.EMPTY
            kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl$CopyConfiguration r0 = r9.newCopyBuilder(r0)
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            boolean r4 = r10 instanceof java.util.Collection
            if (r4 == 0) goto Lbb
            r4 = r10
            java.util.Collection r4 = (java.util.Collection) r4
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto Lbb
        Lb9:
            r1 = r3
            goto Ld2
        Lbb:
            java.util.Iterator r10 = r10.iterator()
        Lbf:
            boolean r4 = r10.hasNext()
            if (r4 == 0) goto Lb9
            java.lang.Object r4 = r10.next()
            kotlin.reflect.jvm.internal.impl.name.Name r4 = (kotlin.reflect.jvm.internal.impl.name.Name) r4
            if (r4 != 0) goto Lcf
            r4 = r1
            goto Ld0
        Lcf:
            r4 = r3
        Ld0:
            if (r4 == 0) goto Lbf
        Ld2:
            kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl$CopyConfiguration r10 = r0.setHasSynthesizedParameterNames(r1)
            kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl$CopyConfiguration r10 = r10.setValueParameters(r2)
            kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor r0 = r9.getOriginal()
            kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl$CopyConfiguration r10 = r10.setOriginal(r0)
            java.lang.String r0 = "newCopyBuilder(TypeSubst…   .setOriginal(original)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r0)
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r10 = super.doSubstitute(r10)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionInvokeDescriptor.replaceParameterNames(java.util.List):kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor");
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    @NotNull
    public FunctionDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor functionDescriptor, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name name, @NotNull Annotations annotations, @NotNull SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        return new FunctionInvokeDescriptor(newOwner, (FunctionInvokeDescriptor) functionDescriptor, kind, isSuspend());
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl
    @Nullable
    public FunctionDescriptor doSubstitute(@NotNull FunctionDescriptorImpl.CopyConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        FunctionInvokeDescriptor functionInvokeDescriptor = (FunctionInvokeDescriptor) super.doSubstitute(configuration);
        if (functionInvokeDescriptor == null) {
            return null;
        }
        List<ValueParameterDescriptor> valueParameters = functionInvokeDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "substituted.valueParameters");
        List<ValueParameterDescriptor> list = valueParameters;
        boolean z2 = true;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                KotlinType type = ((ValueParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type, "it.type");
                if (FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type) != null) {
                    z2 = false;
                    break;
                }
            }
        }
        if (z2) {
            return functionInvokeDescriptor;
        }
        List<ValueParameterDescriptor> valueParameters2 = functionInvokeDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "substituted.valueParameters");
        List<ValueParameterDescriptor> list2 = valueParameters2;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            KotlinType type2 = ((ValueParameterDescriptor) it2.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type2, "it.type");
            arrayList.add(FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type2));
        }
        return functionInvokeDescriptor.replaceParameterNames(arrayList);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor
    public boolean isTailrec() {
        return false;
    }
}
