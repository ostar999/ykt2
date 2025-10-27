package kotlin.reflect.jvm.internal.impl.load.java;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension({"SMAP\nBuiltinSpecialProperties.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BuiltinSpecialProperties.kt\norg/jetbrains/kotlin/load/java/BuiltinSpecialProperties\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,42:1\n1549#2:43\n1620#2,3:44\n1490#2:47\n1520#2,3:48\n1523#2,3:58\n1238#2,4:63\n1549#2:67\n1620#2,3:68\n361#3,7:51\n442#3:61\n392#3:62\n*S KotlinDebug\n*F\n+ 1 BuiltinSpecialProperties.kt\norg/jetbrains/kotlin/load/java/BuiltinSpecialProperties\n*L\n27#1:43\n27#1:44,3\n28#1:47\n28#1:48,3\n28#1:58,3\n29#1:63,4\n34#1:67\n34#1:68,3\n28#1:51,7\n29#1:61\n29#1:62\n*E\n"})
/* loaded from: classes8.dex */
public final class BuiltinSpecialProperties {

    @NotNull
    private static final Map<Name, List<Name>> GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP;

    @NotNull
    public static final BuiltinSpecialProperties INSTANCE = new BuiltinSpecialProperties();

    @NotNull
    private static final Map<FqName, Name> PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP;

    @NotNull
    private static final Set<FqName> SPECIAL_FQ_NAMES;

    @NotNull
    private static final Set<Name> SPECIAL_SHORT_NAMES;

    static {
        FqNameUnsafe fqNameUnsafe = StandardNames.FqNames._enum;
        FqName fqName = StandardNames.FqNames.map;
        Map<FqName, Name> mapMapOf = MapsKt__MapsKt.mapOf(TuplesKt.to(BuiltinSpecialPropertiesKt.childSafe(fqNameUnsafe, "name"), Name.identifier("name")), TuplesKt.to(BuiltinSpecialPropertiesKt.childSafe(fqNameUnsafe, "ordinal"), Name.identifier("ordinal")), TuplesKt.to(BuiltinSpecialPropertiesKt.child(StandardNames.FqNames.collection, DatabaseManager.SIZE), Name.identifier(DatabaseManager.SIZE)), TuplesKt.to(BuiltinSpecialPropertiesKt.child(fqName, DatabaseManager.SIZE), Name.identifier(DatabaseManager.SIZE)), TuplesKt.to(BuiltinSpecialPropertiesKt.childSafe(StandardNames.FqNames.charSequence, SessionDescription.ATTR_LENGTH), Name.identifier(SessionDescription.ATTR_LENGTH)), TuplesKt.to(BuiltinSpecialPropertiesKt.child(fqName, "keys"), Name.identifier("keySet")), TuplesKt.to(BuiltinSpecialPropertiesKt.child(fqName, "values"), Name.identifier("values")), TuplesKt.to(BuiltinSpecialPropertiesKt.child(fqName, "entries"), Name.identifier("entrySet")));
        PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP = mapMapOf;
        Set<Map.Entry<FqName, Name>> setEntrySet = mapMapOf.entrySet();
        ArrayList<Pair> arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            arrayList.add(new Pair(((FqName) entry.getKey()).shortName(), entry.getValue()));
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Pair pair : arrayList) {
            Name name = (Name) pair.getSecond();
            Object arrayList2 = linkedHashMap.get(name);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                linkedHashMap.put(name, arrayList2);
            }
            ((List) arrayList2).add((Name) pair.getFirst());
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(linkedHashMap.size()));
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            linkedHashMap2.put(entry2.getKey(), CollectionsKt___CollectionsKt.distinct((Iterable) entry2.getValue()));
        }
        GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP = linkedHashMap2;
        Set<FqName> setKeySet = PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP.keySet();
        SPECIAL_FQ_NAMES = setKeySet;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setKeySet, 10));
        Iterator<T> it2 = setKeySet.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((FqName) it2.next()).shortName());
        }
        SPECIAL_SHORT_NAMES = CollectionsKt___CollectionsKt.toSet(arrayList3);
    }

    private BuiltinSpecialProperties() {
    }

    @NotNull
    public final Map<FqName, Name> getPROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP() {
        return PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP;
    }

    @NotNull
    public final List<Name> getPropertyNameCandidatesBySpecialGetterName(@NotNull Name name1) {
        Intrinsics.checkNotNullParameter(name1, "name1");
        List<Name> list = GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP.get(name1);
        return list == null ? CollectionsKt__CollectionsKt.emptyList() : list;
    }

    @NotNull
    public final Set<FqName> getSPECIAL_FQ_NAMES() {
        return SPECIAL_FQ_NAMES;
    }

    @NotNull
    public final Set<Name> getSPECIAL_SHORT_NAMES() {
        return SPECIAL_SHORT_NAMES;
    }
}
