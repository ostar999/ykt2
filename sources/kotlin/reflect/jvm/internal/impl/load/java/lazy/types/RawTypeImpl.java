package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeAttributes;
import kotlin.reflect.jvm.internal.impl.types.TypeParameterUpperBoundEraser;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension({"SMAP\nRawType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RawType.kt\norg/jetbrains/kotlin/load/java/lazy/types/RawTypeImpl\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,98:1\n1726#2,3:99\n1549#2:102\n1620#2,3:103\n*S KotlinDebug\n*F\n+ 1 RawType.kt\norg/jetbrains/kotlin/load/java/lazy/types/RawTypeImpl\n*L\n80#1:99,3\n61#1:102\n61#1:103,3\n*E\n"})
/* loaded from: classes8.dex */
public final class RawTypeImpl extends FlexibleType implements RawType {
    private RawTypeImpl(SimpleType simpleType, SimpleType simpleType2, boolean z2) {
        super(simpleType, simpleType2);
        if (z2) {
            return;
        }
        KotlinTypeChecker.DEFAULT.isSubtypeOf(simpleType, simpleType2);
    }

    private static final boolean render$onlyOutDiffers(String str, String str2) {
        return Intrinsics.areEqual(str, StringsKt__StringsKt.removePrefix(str2, (CharSequence) "out ")) || Intrinsics.areEqual(str2, "*");
    }

    private static final List<String> render$renderArguments(DescriptorRenderer descriptorRenderer, KotlinType kotlinType) {
        List<TypeProjection> arguments = kotlinType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(descriptorRenderer.renderTypeProjection((TypeProjection) it.next()));
        }
        return arrayList;
    }

    private static final String render$replaceArgs(String str, String str2) {
        if (!StringsKt__StringsKt.contains$default((CharSequence) str, Typography.less, false, 2, (Object) null)) {
            return str;
        }
        return StringsKt__StringsKt.substringBefore$default(str, Typography.less, (String) null, 2, (Object) null) + Typography.less + str2 + Typography.greater + StringsKt__StringsKt.substringAfterLast$default(str, Typography.greater, (String) null, 2, (Object) null);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    @NotNull
    public SimpleType getDelegate() {
        return getLowerBound();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public MemberScope getMemberScope() {
        ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = getConstructor().mo2086getDeclarationDescriptor();
        TypeParameterUpperBoundEraser typeParameterUpperBoundEraser = null;
        byte b3 = 0;
        ClassDescriptor classDescriptor = classifierDescriptorMo2086getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo2086getDeclarationDescriptor : null;
        if (classDescriptor != null) {
            MemberScope memberScope = classDescriptor.getMemberScope(new RawSubstitution(typeParameterUpperBoundEraser, 1, b3 == true ? 1 : 0));
            Intrinsics.checkNotNullExpressionValue(memberScope, "classDescriptor.getMemberScope(RawSubstitution())");
            return memberScope;
        }
        throw new IllegalStateException(("Incorrect classifier: " + getConstructor().mo2086getDeclarationDescriptor()).toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    @NotNull
    public String render(@NotNull DescriptorRenderer renderer, @NotNull DescriptorRendererOptions options) throws IOException {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(options, "options");
        String strRenderType = renderer.renderType(getLowerBound());
        String strRenderType2 = renderer.renderType(getUpperBound());
        if (options.getDebugMode()) {
            return "raw (" + strRenderType + StrPool.DOUBLE_DOT + strRenderType2 + ')';
        }
        if (getUpperBound().getArguments().isEmpty()) {
            return renderer.renderFlexibleType(strRenderType, strRenderType2, TypeUtilsKt.getBuiltIns(this));
        }
        List<String> listRender$renderArguments = render$renderArguments(renderer, getLowerBound());
        List<String> listRender$renderArguments2 = render$renderArguments(renderer, getUpperBound());
        List<String> list = listRender$renderArguments;
        String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(list, ", ", null, null, 0, null, new Function1<String, CharSequence>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl$render$newArgs$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "(raw) " + it;
            }
        }, 30, null);
        List listZip = CollectionsKt___CollectionsKt.zip(list, listRender$renderArguments2);
        boolean z2 = true;
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            Iterator it = listZip.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pair pair = (Pair) it.next();
                if (!render$onlyOutDiffers((String) pair.getFirst(), (String) pair.getSecond())) {
                    z2 = false;
                    break;
                }
            }
        }
        if (z2) {
            strRenderType2 = render$replaceArgs(strRenderType2, strJoinToString$default);
        }
        String strRender$replaceArgs = render$replaceArgs(strRenderType, strJoinToString$default);
        return Intrinsics.areEqual(strRender$replaceArgs, strRenderType2) ? strRender$replaceArgs : renderer.renderFlexibleType(strRender$replaceArgs, strRenderType2, TypeUtilsKt.getBuiltIns(this));
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public RawTypeImpl makeNullableAsSpecified(boolean z2) {
        return new RawTypeImpl(getLowerBound().makeNullableAsSpecified(z2), getUpperBound().makeNullableAsSpecified(z2));
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public RawTypeImpl replaceAttributes(@NotNull TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new RawTypeImpl(getLowerBound().replaceAttributes(newAttributes), getUpperBound().replaceAttributes(newAttributes));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RawTypeImpl(@NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
        this(lowerBound, upperBound, false);
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public FlexibleType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinTypeRefineType = kotlinTypeRefiner.refineType((KotlinTypeMarker) getLowerBound());
        Intrinsics.checkNotNull(kotlinTypeRefineType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        KotlinType kotlinTypeRefineType2 = kotlinTypeRefiner.refineType((KotlinTypeMarker) getUpperBound());
        Intrinsics.checkNotNull(kotlinTypeRefineType2, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        return new RawTypeImpl((SimpleType) kotlinTypeRefineType, (SimpleType) kotlinTypeRefineType2, true);
    }
}
