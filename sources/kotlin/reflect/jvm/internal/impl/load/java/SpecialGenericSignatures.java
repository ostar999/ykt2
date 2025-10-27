package kotlin.reflect.jvm.internal.impl.load.java;

import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nSpecialGenericSignatures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpecialGenericSignatures.kt\norg/jetbrains/kotlin/load/java/SpecialGenericSignatures\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 SignatureBuildingComponents.kt\norg/jetbrains/kotlin/load/kotlin/SignatureBuildingComponentsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,146:1\n1549#2:147\n1620#2,3:148\n1549#2:151\n1620#2,3:152\n1549#2:155\n1620#2,3:156\n1238#2,4:162\n1549#2:166\n1620#2,3:167\n1549#2:170\n1620#2,3:171\n1238#2,4:177\n1549#2:181\n1620#2,3:182\n1549#2:185\n1620#2,3:186\n1208#2,2:189\n1238#2,4:191\n13#3:159\n13#3:174\n457#4:160\n403#4:161\n457#4:175\n403#4:176\n*S KotlinDebug\n*F\n+ 1 SpecialGenericSignatures.kt\norg/jetbrains/kotlin/load/java/SpecialGenericSignatures\n*L\n54#1:147\n54#1:148,3\n56#1:151\n56#1:152,3\n57#1:155\n57#1:156,3\n95#1:162,4\n101#1:166\n101#1:167,3\n102#1:170\n102#1:171,3\n126#1:177,4\n128#1:181\n128#1:182,3\n132#1:185\n132#1:186,3\n133#1:189,2\n133#1:191,4\n60#1:159\n111#1:174\n95#1:160\n95#1:161\n126#1:175\n126#1:176\n*E\n"})
/* loaded from: classes8.dex */
public class SpecialGenericSignatures {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final List<String> ERASED_COLLECTION_PARAMETER_NAMES;

    @NotNull
    private static final List<Companion.NameAndSignature> ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;

    @NotNull
    private static final List<String> ERASED_COLLECTION_PARAMETER_SIGNATURES;

    @NotNull
    private static final Set<Name> ERASED_VALUE_PARAMETERS_SHORT_NAMES;

    @NotNull
    private static final Set<String> ERASED_VALUE_PARAMETERS_SIGNATURES;

    @NotNull
    private static final Map<Companion.NameAndSignature, TypeSafeBarrierDescription> GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP;

    @NotNull
    private static final Map<Name, Name> JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP;

    @NotNull
    private static final Map<Companion.NameAndSignature, Name> NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP;

    @NotNull
    private static final List<Name> ORIGINAL_SHORT_NAMES;

    @NotNull
    private static final Companion.NameAndSignature REMOVE_AT_NAME_AND_SIGNATURE;

    @NotNull
    private static final Map<String, TypeSafeBarrierDescription> SIGNATURE_TO_DEFAULT_VALUES_MAP;

    @NotNull
    private static final Map<String, Name> SIGNATURE_TO_JVM_REPRESENTATION_NAME;

    public static final class Companion {

        public static final class NameAndSignature {

            @NotNull
            private final Name name;

            @NotNull
            private final String signature;

            public NameAndSignature(@NotNull Name name, @NotNull String signature) {
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(signature, "signature");
                this.name = name;
                this.signature = signature;
            }

            public boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof NameAndSignature)) {
                    return false;
                }
                NameAndSignature nameAndSignature = (NameAndSignature) obj;
                return Intrinsics.areEqual(this.name, nameAndSignature.name) && Intrinsics.areEqual(this.signature, nameAndSignature.signature);
            }

            @NotNull
            public final Name getName() {
                return this.name;
            }

            @NotNull
            public final String getSignature() {
                return this.signature;
            }

            public int hashCode() {
                return (this.name.hashCode() * 31) + this.signature.hashCode();
            }

            @NotNull
            public String toString() {
                return "NameAndSignature(name=" + this.name + ", signature=" + this.signature + ')';
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final NameAndSignature method(String str, String str2, String str3, String str4) {
            Name nameIdentifier = Name.identifier(str2);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(name)");
            return new NameAndSignature(nameIdentifier, SignatureBuildingComponents.INSTANCE.signature(str, str2 + '(' + str3 + ')' + str4));
        }

        @Nullable
        public final Name getBuiltinFunctionNamesByJvmName(@NotNull Name name) {
            Intrinsics.checkNotNullParameter(name, "name");
            return getJVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP().get(name);
        }

        @NotNull
        public final List<String> getERASED_COLLECTION_PARAMETER_SIGNATURES() {
            return SpecialGenericSignatures.ERASED_COLLECTION_PARAMETER_SIGNATURES;
        }

        @NotNull
        public final Set<Name> getERASED_VALUE_PARAMETERS_SHORT_NAMES() {
            return SpecialGenericSignatures.ERASED_VALUE_PARAMETERS_SHORT_NAMES;
        }

        @NotNull
        public final Set<String> getERASED_VALUE_PARAMETERS_SIGNATURES() {
            return SpecialGenericSignatures.ERASED_VALUE_PARAMETERS_SIGNATURES;
        }

        @NotNull
        public final Map<Name, Name> getJVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP() {
            return SpecialGenericSignatures.JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP;
        }

        @NotNull
        public final List<Name> getORIGINAL_SHORT_NAMES() {
            return SpecialGenericSignatures.ORIGINAL_SHORT_NAMES;
        }

        @NotNull
        public final NameAndSignature getREMOVE_AT_NAME_AND_SIGNATURE() {
            return SpecialGenericSignatures.REMOVE_AT_NAME_AND_SIGNATURE;
        }

        @NotNull
        public final Map<String, TypeSafeBarrierDescription> getSIGNATURE_TO_DEFAULT_VALUES_MAP() {
            return SpecialGenericSignatures.SIGNATURE_TO_DEFAULT_VALUES_MAP;
        }

        @NotNull
        public final Map<String, Name> getSIGNATURE_TO_JVM_REPRESENTATION_NAME() {
            return SpecialGenericSignatures.SIGNATURE_TO_JVM_REPRESENTATION_NAME;
        }

        public final boolean getSameAsRenamedInJvmBuiltin(@NotNull Name name) {
            Intrinsics.checkNotNullParameter(name, "<this>");
            return getORIGINAL_SHORT_NAMES().contains(name);
        }

        @NotNull
        public final SpecialSignatureInfo getSpecialSignatureInfo(@NotNull String builtinSignature) {
            Intrinsics.checkNotNullParameter(builtinSignature, "builtinSignature");
            return getERASED_COLLECTION_PARAMETER_SIGNATURES().contains(builtinSignature) ? SpecialSignatureInfo.ONE_COLLECTION_PARAMETER : ((TypeSafeBarrierDescription) MapsKt__MapsKt.getValue(getSIGNATURE_TO_DEFAULT_VALUES_MAP(), builtinSignature)) == TypeSafeBarrierDescription.NULL ? SpecialSignatureInfo.OBJECT_PARAMETER_GENERIC : SpecialSignatureInfo.OBJECT_PARAMETER_NON_GENERIC;
        }
    }

    public enum SpecialSignatureInfo {
        ONE_COLLECTION_PARAMETER("Ljava/util/Collection<+Ljava/lang/Object;>;", false),
        OBJECT_PARAMETER_NON_GENERIC(null, true),
        OBJECT_PARAMETER_GENERIC("Ljava/lang/Object;", true);

        private final boolean isObjectReplacedWithTypeParameter;

        @Nullable
        private final String valueParametersSignature;

        SpecialSignatureInfo(String str, boolean z2) {
            this.valueParametersSignature = str;
            this.isObjectReplacedWithTypeParameter = z2;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class TypeSafeBarrierDescription {

        @Nullable
        private final Object defaultValue;
        public static final TypeSafeBarrierDescription NULL = new TypeSafeBarrierDescription(PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL, 0, null);
        public static final TypeSafeBarrierDescription INDEX = new TypeSafeBarrierDescription("INDEX", 1, -1);
        public static final TypeSafeBarrierDescription FALSE = new TypeSafeBarrierDescription("FALSE", 2, Boolean.FALSE);
        public static final TypeSafeBarrierDescription MAP_GET_OR_DEFAULT = new MAP_GET_OR_DEFAULT("MAP_GET_OR_DEFAULT", 3);
        private static final /* synthetic */ TypeSafeBarrierDescription[] $VALUES = $values();

        public static final class MAP_GET_OR_DEFAULT extends TypeSafeBarrierDescription {
            /* JADX WARN: Illegal instructions before constructor call */
            public MAP_GET_OR_DEFAULT(String str, int i2) {
                DefaultConstructorMarker defaultConstructorMarker = null;
                super(str, i2, defaultConstructorMarker, defaultConstructorMarker);
            }
        }

        private static final /* synthetic */ TypeSafeBarrierDescription[] $values() {
            return new TypeSafeBarrierDescription[]{NULL, INDEX, FALSE, MAP_GET_OR_DEFAULT};
        }

        private TypeSafeBarrierDescription(String str, int i2, Object obj) {
            this.defaultValue = obj;
        }

        public /* synthetic */ TypeSafeBarrierDescription(String str, int i2, Object obj, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i2, obj);
        }

        public static TypeSafeBarrierDescription valueOf(String str) {
            return (TypeSafeBarrierDescription) Enum.valueOf(TypeSafeBarrierDescription.class, str);
        }

        public static TypeSafeBarrierDescription[] values() {
            return (TypeSafeBarrierDescription[]) $VALUES.clone();
        }
    }

    static {
        Set<String> of = SetsKt__SetsKt.setOf((Object[]) new String[]{"containsAll", "removeAll", "retainAll"});
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(of, 10));
        for (String str : of) {
            Companion companion = Companion;
            String desc = JvmPrimitiveType.BOOLEAN.getDesc();
            Intrinsics.checkNotNullExpressionValue(desc, "BOOLEAN.desc");
            arrayList.add(companion.method("java/util/Collection", str, "Ljava/util/Collection;", desc));
        }
        ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES = arrayList;
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((Companion.NameAndSignature) it.next()).getSignature());
        }
        ERASED_COLLECTION_PARAMETER_SIGNATURES = arrayList3;
        List<Companion.NameAndSignature> list = ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList4.add(((Companion.NameAndSignature) it2.next()).getName().asString());
        }
        ERASED_COLLECTION_PARAMETER_NAMES = arrayList4;
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        Companion companion2 = Companion;
        String strJavaUtil = signatureBuildingComponents.javaUtil("Collection");
        JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.BOOLEAN;
        String desc2 = jvmPrimitiveType.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc2, "BOOLEAN.desc");
        Companion.NameAndSignature nameAndSignatureMethod = companion2.method(strJavaUtil, "contains", "Ljava/lang/Object;", desc2);
        TypeSafeBarrierDescription typeSafeBarrierDescription = TypeSafeBarrierDescription.FALSE;
        String strJavaUtil2 = signatureBuildingComponents.javaUtil("Collection");
        String desc3 = jvmPrimitiveType.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc3, "BOOLEAN.desc");
        String strJavaUtil3 = signatureBuildingComponents.javaUtil("Map");
        String desc4 = jvmPrimitiveType.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc4, "BOOLEAN.desc");
        String strJavaUtil4 = signatureBuildingComponents.javaUtil("Map");
        String desc5 = jvmPrimitiveType.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc5, "BOOLEAN.desc");
        String strJavaUtil5 = signatureBuildingComponents.javaUtil("Map");
        String desc6 = jvmPrimitiveType.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc6, "BOOLEAN.desc");
        Companion.NameAndSignature nameAndSignatureMethod2 = companion2.method(signatureBuildingComponents.javaUtil("Map"), "get", "Ljava/lang/Object;", "Ljava/lang/Object;");
        TypeSafeBarrierDescription typeSafeBarrierDescription2 = TypeSafeBarrierDescription.NULL;
        String strJavaUtil6 = signatureBuildingComponents.javaUtil("List");
        JvmPrimitiveType jvmPrimitiveType2 = JvmPrimitiveType.INT;
        String desc7 = jvmPrimitiveType2.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc7, "INT.desc");
        Companion.NameAndSignature nameAndSignatureMethod3 = companion2.method(strJavaUtil6, "indexOf", "Ljava/lang/Object;", desc7);
        TypeSafeBarrierDescription typeSafeBarrierDescription3 = TypeSafeBarrierDescription.INDEX;
        String strJavaUtil7 = signatureBuildingComponents.javaUtil("List");
        String desc8 = jvmPrimitiveType2.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc8, "INT.desc");
        Map<Companion.NameAndSignature, TypeSafeBarrierDescription> mapMapOf = MapsKt__MapsKt.mapOf(TuplesKt.to(nameAndSignatureMethod, typeSafeBarrierDescription), TuplesKt.to(companion2.method(strJavaUtil2, PLVRemoveMicSiteEvent.EVENT_NAME, "Ljava/lang/Object;", desc3), typeSafeBarrierDescription), TuplesKt.to(companion2.method(strJavaUtil3, "containsKey", "Ljava/lang/Object;", desc4), typeSafeBarrierDescription), TuplesKt.to(companion2.method(strJavaUtil4, "containsValue", "Ljava/lang/Object;", desc5), typeSafeBarrierDescription), TuplesKt.to(companion2.method(strJavaUtil5, PLVRemoveMicSiteEvent.EVENT_NAME, "Ljava/lang/Object;Ljava/lang/Object;", desc6), typeSafeBarrierDescription), TuplesKt.to(companion2.method(signatureBuildingComponents.javaUtil("Map"), "getOrDefault", "Ljava/lang/Object;Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.MAP_GET_OR_DEFAULT), TuplesKt.to(nameAndSignatureMethod2, typeSafeBarrierDescription2), TuplesKt.to(companion2.method(signatureBuildingComponents.javaUtil("Map"), PLVRemoveMicSiteEvent.EVENT_NAME, "Ljava/lang/Object;", "Ljava/lang/Object;"), typeSafeBarrierDescription2), TuplesKt.to(nameAndSignatureMethod3, typeSafeBarrierDescription3), TuplesKt.to(companion2.method(strJavaUtil7, "lastIndexOf", "Ljava/lang/Object;", desc8), typeSafeBarrierDescription3));
        GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP = mapMapOf;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(mapMapOf.size()));
        Iterator<T> it3 = mapMapOf.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            linkedHashMap.put(((Companion.NameAndSignature) entry.getKey()).getSignature(), entry.getValue());
        }
        SIGNATURE_TO_DEFAULT_VALUES_MAP = linkedHashMap;
        Set setPlus = SetsKt___SetsKt.plus((Set) GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP.keySet(), (Iterable) ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES);
        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setPlus, 10));
        Iterator it4 = setPlus.iterator();
        while (it4.hasNext()) {
            arrayList5.add(((Companion.NameAndSignature) it4.next()).getName());
        }
        ERASED_VALUE_PARAMETERS_SHORT_NAMES = CollectionsKt___CollectionsKt.toSet(arrayList5);
        ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setPlus, 10));
        Iterator it5 = setPlus.iterator();
        while (it5.hasNext()) {
            arrayList6.add(((Companion.NameAndSignature) it5.next()).getSignature());
        }
        ERASED_VALUE_PARAMETERS_SIGNATURES = CollectionsKt___CollectionsKt.toSet(arrayList6);
        Companion companion3 = Companion;
        JvmPrimitiveType jvmPrimitiveType3 = JvmPrimitiveType.INT;
        String desc9 = jvmPrimitiveType3.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc9, "INT.desc");
        Companion.NameAndSignature nameAndSignatureMethod4 = companion3.method("java/util/List", "removeAt", desc9, "Ljava/lang/Object;");
        REMOVE_AT_NAME_AND_SIGNATURE = nameAndSignatureMethod4;
        SignatureBuildingComponents signatureBuildingComponents2 = SignatureBuildingComponents.INSTANCE;
        String strJavaLang = signatureBuildingComponents2.javaLang("Number");
        String desc10 = JvmPrimitiveType.BYTE.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc10, "BYTE.desc");
        String strJavaLang2 = signatureBuildingComponents2.javaLang("Number");
        String desc11 = JvmPrimitiveType.SHORT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc11, "SHORT.desc");
        String strJavaLang3 = signatureBuildingComponents2.javaLang("Number");
        String desc12 = jvmPrimitiveType3.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc12, "INT.desc");
        String strJavaLang4 = signatureBuildingComponents2.javaLang("Number");
        String desc13 = JvmPrimitiveType.LONG.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc13, "LONG.desc");
        String strJavaLang5 = signatureBuildingComponents2.javaLang("Number");
        String desc14 = JvmPrimitiveType.FLOAT.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc14, "FLOAT.desc");
        String strJavaLang6 = signatureBuildingComponents2.javaLang("Number");
        String desc15 = JvmPrimitiveType.DOUBLE.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc15, "DOUBLE.desc");
        String strJavaLang7 = signatureBuildingComponents2.javaLang("CharSequence");
        String desc16 = jvmPrimitiveType3.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc16, "INT.desc");
        String desc17 = JvmPrimitiveType.CHAR.getDesc();
        Intrinsics.checkNotNullExpressionValue(desc17, "CHAR.desc");
        Map<Companion.NameAndSignature, Name> mapMapOf2 = MapsKt__MapsKt.mapOf(TuplesKt.to(companion3.method(strJavaLang, "toByte", "", desc10), Name.identifier("byteValue")), TuplesKt.to(companion3.method(strJavaLang2, "toShort", "", desc11), Name.identifier("shortValue")), TuplesKt.to(companion3.method(strJavaLang3, "toInt", "", desc12), Name.identifier("intValue")), TuplesKt.to(companion3.method(strJavaLang4, "toLong", "", desc13), Name.identifier("longValue")), TuplesKt.to(companion3.method(strJavaLang5, "toFloat", "", desc14), Name.identifier("floatValue")), TuplesKt.to(companion3.method(strJavaLang6, "toDouble", "", desc15), Name.identifier("doubleValue")), TuplesKt.to(nameAndSignatureMethod4, Name.identifier(PLVRemoveMicSiteEvent.EVENT_NAME)), TuplesKt.to(companion3.method(strJavaLang7, "get", desc16, desc17), Name.identifier("charAt")));
        NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP = mapMapOf2;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(mapMapOf2.size()));
        Iterator<T> it6 = mapMapOf2.entrySet().iterator();
        while (it6.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it6.next();
            linkedHashMap2.put(((Companion.NameAndSignature) entry2.getKey()).getSignature(), entry2.getValue());
        }
        SIGNATURE_TO_JVM_REPRESENTATION_NAME = linkedHashMap2;
        Set<Companion.NameAndSignature> setKeySet = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.keySet();
        ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setKeySet, 10));
        Iterator<T> it7 = setKeySet.iterator();
        while (it7.hasNext()) {
            arrayList7.add(((Companion.NameAndSignature) it7.next()).getName());
        }
        ORIGINAL_SHORT_NAMES = arrayList7;
        Set<Map.Entry<Companion.NameAndSignature, Name>> setEntrySet = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.entrySet();
        ArrayList<Pair> arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it8 = setEntrySet.iterator();
        while (it8.hasNext()) {
            Map.Entry entry3 = (Map.Entry) it8.next();
            arrayList8.add(new Pair(((Companion.NameAndSignature) entry3.getKey()).getName(), entry3.getValue()));
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList8, 10)), 16));
        for (Pair pair : arrayList8) {
            linkedHashMap3.put((Name) pair.getSecond(), (Name) pair.getFirst());
        }
        JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP = linkedHashMap3;
    }
}
