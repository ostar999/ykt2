package kotlin.reflect.jvm.internal.impl.name;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension({"SMAP\nStandardClassIds.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StandardClassIds.kt\norg/jetbrains/kotlin/name/StandardClassIdsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,265:1\n1179#2,2:266\n1253#2,4:268\n*S KotlinDebug\n*F\n+ 1 StandardClassIds.kt\norg/jetbrains/kotlin/name/StandardClassIdsKt\n*L\n264#1:266,2\n264#1:268,4\n*E\n"})
/* loaded from: classes8.dex */
public final class StandardClassIdsKt {

    @NotNull
    private static final FqName JAVA_LANG_ANNOTATION_PACKAGE;

    @NotNull
    private static final FqName JAVA_LANG_PACKAGE;

    static {
        FqName fqName = new FqName("java.lang");
        JAVA_LANG_PACKAGE = fqName;
        FqName fqNameChild = fqName.child(Name.identifier("annotation"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "JAVA_LANG_PACKAGE.child(…identifier(\"annotation\"))");
        JAVA_LANG_ANNOTATION_PACKAGE = fqNameChild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId annotationId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_ANNOTATION_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId baseId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId collectionsId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_COLLECTIONS_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId coroutinesId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_COROUTINES_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId enumsId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_ENUMS_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V> Map<V, K> inverseMap(Map<K, ? extends V> map) {
        Set<Map.Entry<K, ? extends V>> setEntrySet = map.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getValue(), entry.getKey());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId primitiveArrayId(Name name) {
        StandardClassIds standardClassIds = StandardClassIds.INSTANCE;
        return new ClassId(standardClassIds.getArray().getPackageFqName(), Name.identifier(name.getIdentifier() + standardClassIds.getArray().getShortClassName().getIdentifier()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId rangesId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_RANGES_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId reflectId(String str) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_REFLECT_PACKAGE(), Name.identifier(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId unsignedId(ClassId classId) {
        return new ClassId(StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE(), Name.identifier('U' + classId.getShortClassName().getIdentifier()));
    }
}
