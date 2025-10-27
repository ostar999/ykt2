package kotlin.reflect.jvm.internal.impl.resolve;

import MTT.ThirdAppInfoNew;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension({"SMAP\noverridingUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 overridingUtils.kt\norg/jetbrains/kotlin/resolve/OverridingUtilsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,84:1\n1620#2,3:85\n847#2,2:88\n*S KotlinDebug\n*F\n+ 1 overridingUtils.kt\norg/jetbrains/kotlin/resolve/OverridingUtilsKt\n*L\n40#1:85,3\n72#1:88,2\n*E\n"})
/* loaded from: classes8.dex */
public final class OverridingUtilsKt {
    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <H> Collection<H> selectMostSpecificInEachOverridableGroup(@NotNull Collection<? extends H> collection, @NotNull Function1<? super H, ? extends CallableDescriptor> descriptorByHandle) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(descriptorByHandle, "descriptorByHandle");
        if (collection.size() <= 1) {
            return collection;
        }
        LinkedList linkedList = new LinkedList(collection);
        SmartSet smartSetCreate = SmartSet.Companion.create();
        while (!linkedList.isEmpty()) {
            Object objFirst = CollectionsKt___CollectionsKt.first((List<? extends Object>) linkedList);
            final SmartSet smartSetCreate2 = SmartSet.Companion.create();
            Collection<ThirdAppInfoNew> collectionExtractMembersOverridableInBothWays = OverridingUtil.extractMembersOverridableInBothWays(objFirst, linkedList, descriptorByHandle, new Function1<H, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt$selectMostSpecificInEachOverridableGroup$overridableGroup$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                    invoke2((OverridingUtilsKt$selectMostSpecificInEachOverridableGroup$overridableGroup$1<H>) obj);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(H it) {
                    SmartSet<H> smartSet = smartSetCreate2;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    smartSet.add(it);
                }
            });
            Intrinsics.checkNotNullExpressionValue(collectionExtractMembersOverridableInBothWays, "conflictedHandles = Smar…nflictedHandles.add(it) }");
            if (collectionExtractMembersOverridableInBothWays.size() == 1 && smartSetCreate2.isEmpty()) {
                Object objSingle = CollectionsKt___CollectionsKt.single(collectionExtractMembersOverridableInBothWays);
                Intrinsics.checkNotNullExpressionValue(objSingle, "overridableGroup.single()");
                smartSetCreate.add(objSingle);
            } else {
                ThirdAppInfoNew thirdAppInfoNew = (Object) OverridingUtil.selectMostSpecificMember(collectionExtractMembersOverridableInBothWays, descriptorByHandle);
                Intrinsics.checkNotNullExpressionValue(thirdAppInfoNew, "selectMostSpecificMember…roup, descriptorByHandle)");
                CallableDescriptor callableDescriptorInvoke = descriptorByHandle.invoke(thirdAppInfoNew);
                for (ThirdAppInfoNew it : collectionExtractMembersOverridableInBothWays) {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    if (!OverridingUtil.isMoreSpecific(callableDescriptorInvoke, descriptorByHandle.invoke(it))) {
                        smartSetCreate2.add(it);
                    }
                }
                if (!smartSetCreate2.isEmpty()) {
                    smartSetCreate.addAll(smartSetCreate2);
                }
                smartSetCreate.add(thirdAppInfoNew);
            }
        }
        return smartSetCreate;
    }
}
