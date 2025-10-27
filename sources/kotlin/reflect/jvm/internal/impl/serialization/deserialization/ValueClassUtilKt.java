package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.descriptors.InlineClassRepresentation;
import kotlin.reflect.jvm.internal.impl.descriptors.MultiFieldValueClassRepresentation;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueClassRepresentation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nValueClassUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ValueClassUtil.kt\norg/jetbrains/kotlin/serialization/deserialization/ValueClassUtilKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,46:1\n1549#2:47\n1620#2,3:48\n1549#2:51\n1620#2,3:52\n1549#2:55\n1620#2,3:56\n*S KotlinDebug\n*F\n+ 1 ValueClassUtil.kt\norg/jetbrains/kotlin/serialization/deserialization/ValueClassUtilKt\n*L\n25#1:47\n25#1:48,3\n29#1:51\n29#1:52,3\n32#1:55\n32#1:56,3\n*E\n"})
/* loaded from: classes8.dex */
public final class ValueClassUtilKt {
    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static final <T extends SimpleTypeMarker> ValueClassRepresentation<T> loadValueClassRepresentation(@NotNull ProtoBuf.Class r6, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull Function1<? super ProtoBuf.Type, ? extends T> typeDeserializer, @NotNull Function1<? super Name, ? extends T> typeOfPublicProperty) {
        T tInvoke;
        List<ProtoBuf.Type> multiFieldValueClassUnderlyingTypeList;
        Intrinsics.checkNotNullParameter(r6, "<this>");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
        Intrinsics.checkNotNullParameter(typeOfPublicProperty, "typeOfPublicProperty");
        if (r6.getMultiFieldValueClassUnderlyingNameCount() <= 0) {
            if (!r6.hasInlineClassUnderlyingPropertyName()) {
                return null;
            }
            Name name = NameResolverUtilKt.getName(nameResolver, r6.getInlineClassUnderlyingPropertyName());
            ProtoBuf.Type typeInlineClassUnderlyingType = ProtoTypeTableUtilKt.inlineClassUnderlyingType(r6, typeTable);
            if ((typeInlineClassUnderlyingType != null && (tInvoke = typeDeserializer.invoke(typeInlineClassUnderlyingType)) != null) || (tInvoke = typeOfPublicProperty.invoke(name)) != null) {
                return new InlineClassRepresentation(name, tInvoke);
            }
            throw new IllegalStateException(("cannot determine underlying type for value class " + NameResolverUtilKt.getName(nameResolver, r6.getFqName()) + " with property " + name).toString());
        }
        List<Integer> multiFieldValueClassUnderlyingNameList = r6.getMultiFieldValueClassUnderlyingNameList();
        Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingNameList, "multiFieldValueClassUnderlyingNameList");
        List<Integer> list = multiFieldValueClassUnderlyingNameList;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (Integer it : list) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            arrayList.add(NameResolverUtilKt.getName(nameResolver, it.intValue()));
        }
        Pair pair = TuplesKt.to(Integer.valueOf(r6.getMultiFieldValueClassUnderlyingTypeIdCount()), Integer.valueOf(r6.getMultiFieldValueClassUnderlyingTypeCount()));
        if (Intrinsics.areEqual(pair, TuplesKt.to(Integer.valueOf(arrayList.size()), 0))) {
            List<Integer> multiFieldValueClassUnderlyingTypeIdList = r6.getMultiFieldValueClassUnderlyingTypeIdList();
            Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingTypeIdList, "multiFieldValueClassUnderlyingTypeIdList");
            List<Integer> list2 = multiFieldValueClassUnderlyingTypeIdList;
            multiFieldValueClassUnderlyingTypeList = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (Integer it2 : list2) {
                Intrinsics.checkNotNullExpressionValue(it2, "it");
                multiFieldValueClassUnderlyingTypeList.add(typeTable.get(it2.intValue()));
            }
        } else {
            if (!Intrinsics.areEqual(pair, TuplesKt.to(0, Integer.valueOf(arrayList.size())))) {
                throw new IllegalStateException(("class " + NameResolverUtilKt.getName(nameResolver, r6.getFqName()) + " has illegal multi-field value class representation").toString());
            }
            multiFieldValueClassUnderlyingTypeList = r6.getMultiFieldValueClassUnderlyingTypeList();
        }
        Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingTypeList, "when (typeIdCount to typ…epresentation\")\n        }");
        List<ProtoBuf.Type> list3 = multiFieldValueClassUnderlyingTypeList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it3 = list3.iterator();
        while (it3.hasNext()) {
            arrayList2.add(typeDeserializer.invoke(it3.next()));
        }
        return new MultiFieldValueClassRepresentation(CollectionsKt___CollectionsKt.zip(arrayList, arrayList2));
    }
}
