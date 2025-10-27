package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension({"SMAP\ntypeEnhancementUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 typeEnhancementUtils.kt\norg/jetbrains/kotlin/load/java/typeEnhancement/TypeEnhancementUtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n1#2:62\n1#2:73\n1#2:86\n1#2:99\n1603#3,9:63\n1855#3:72\n1856#3:74\n1612#3:75\n1603#3,9:76\n1855#3:85\n1856#3:87\n1612#3:88\n1603#3,9:89\n1855#3:98\n1856#3:100\n1612#3:101\n1747#3,3:102\n*S KotlinDebug\n*F\n+ 1 typeEnhancementUtils.kt\norg/jetbrains/kotlin/load/java/typeEnhancement/TypeEnhancementUtilsKt\n*L\n41#1:73\n43#1:86\n45#1:99\n41#1:63,9\n41#1:72\n41#1:74\n41#1:75\n43#1:76,9\n43#1:85\n43#1:87\n43#1:88\n45#1:89,9\n45#1:98\n45#1:100\n45#1:101\n54#1:102,3\n*E\n"})
/* loaded from: classes8.dex */
public final class TypeEnhancementUtilsKt {
    /* JADX WARN: Removed duplicated region for block: B:50:0x00db  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers computeQualifiersForOverride(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r6, @org.jetbrains.annotations.NotNull java.util.Collection<kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers> r7, boolean r8, boolean r9, boolean r10) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "superQualifiers"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r1 = r7.iterator()
        L15:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L2b
            java.lang.Object r2 = r1.next()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r2 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers) r2
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r2 = getNullabilityForErrors(r2)
            if (r2 == 0) goto L15
            r0.add(r2)
            goto L15
        L2b:
            java.util.Set r0 = kotlin.collections.CollectionsKt.toSet(r0)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r1 = getNullabilityForErrors(r6)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r0 = select(r0, r1, r8)
            if (r0 != 0) goto L65
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Iterator r2 = r7.iterator()
        L42:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L58
            java.lang.Object r3 = r2.next()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r3 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers) r3
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = r3.getNullability()
            if (r3 == 0) goto L42
            r1.add(r3)
            goto L42
        L58:
            java.util.Set r1 = kotlin.collections.CollectionsKt.toSet(r1)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r2 = r6.getNullability()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r1 = select(r1, r2, r8)
            goto L66
        L65:
            r1 = r0
        L66:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r3 = r7.iterator()
        L6f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L85
            java.lang.Object r4 = r3.next()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r4 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers) r4
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r4 = r4.getMutability()
            if (r4 == 0) goto L6f
            r2.add(r4)
            goto L6f
        L85:
            java.util.Set r2 = kotlin.collections.CollectionsKt.toSet(r2)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.MUTABLE
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r4 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.READ_ONLY
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r5 = r6.getMutability()
            java.lang.Object r8 = select(r2, r3, r4, r5, r8)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r8 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier) r8
            r2 = 0
            r3 = 1
            r4 = 0
            if (r1 == 0) goto Lab
            if (r10 != 0) goto La7
            if (r9 == 0) goto La5
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r9 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NULLABLE
            if (r1 != r9) goto La5
            goto La7
        La5:
            r9 = r4
            goto La8
        La7:
            r9 = r3
        La8:
            if (r9 != 0) goto Lab
            r2 = r1
        Lab:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r9 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NOT_NULL
            if (r2 != r9) goto Ldb
            boolean r6 = r6.getDefinitelyNotNull()
            if (r6 != 0) goto Ld9
            r6 = r7
            java.util.Collection r6 = (java.util.Collection) r6
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto Lc0
        Lbe:
            r6 = r4
            goto Ld7
        Lc0:
            java.util.Iterator r6 = r7.iterator()
        Lc4:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto Lbe
            java.lang.Object r7 = r6.next()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r7 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers) r7
            boolean r7 = r7.getDefinitelyNotNull()
            if (r7 == 0) goto Lc4
            r6 = r3
        Ld7:
            if (r6 == 0) goto Ldb
        Ld9:
            r6 = r3
            goto Ldc
        Ldb:
            r6 = r4
        Ldc:
            if (r2 == 0) goto Le1
            if (r0 == r1) goto Le1
            goto Le2
        Le1:
            r3 = r4
        Le2:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r7 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers
            r7.<init>(r2, r8, r6, r3)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementUtilsKt.computeQualifiersForOverride(kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers, java.util.Collection, boolean, boolean, boolean):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
    }

    private static final NullabilityQualifier getNullabilityForErrors(JavaTypeQualifiers javaTypeQualifiers) {
        if (javaTypeQualifiers.isNullabilityQualifierForWarning()) {
            return null;
        }
        return javaTypeQualifiers.getNullability();
    }

    public static final boolean hasEnhancedNullability(@NotNull TypeSystemCommonBackendContext typeSystemCommonBackendContext, @NotNull KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
        return typeSystemCommonBackendContext.hasAnnotation(type, ENHANCED_NULLABILITY_ANNOTATION);
    }

    private static final <T> T select(Set<? extends T> set, T t2, T t3, T t4, boolean z2) {
        Set<? extends T> set2;
        if (!z2) {
            if (t4 != null && (set2 = CollectionsKt___CollectionsKt.toSet(SetsKt___SetsKt.plus((Set<? extends Object>) set, t4))) != null) {
                set = set2;
            }
            return (T) CollectionsKt___CollectionsKt.singleOrNull(set);
        }
        T t5 = set.contains(t2) ? t2 : set.contains(t3) ? t3 : null;
        if (Intrinsics.areEqual(t5, t2) && Intrinsics.areEqual(t4, t3)) {
            return null;
        }
        return t4 == null ? t5 : t4;
    }

    private static final NullabilityQualifier select(Set<? extends NullabilityQualifier> set, NullabilityQualifier nullabilityQualifier, boolean z2) {
        NullabilityQualifier nullabilityQualifier2 = NullabilityQualifier.FORCE_FLEXIBILITY;
        return nullabilityQualifier == nullabilityQualifier2 ? nullabilityQualifier2 : (NullabilityQualifier) select(set, NullabilityQualifier.NOT_NULL, NullabilityQualifier.NULLABLE, nullabilityQualifier, z2);
    }
}
