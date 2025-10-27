package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaLoadingKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaRecordComponent;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nDeclaredMemberIndex.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DeclaredMemberIndex.kt\norg/jetbrains/kotlin/load/java/lazy/descriptors/ClassDeclaredMemberIndex\n+ 2 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,66:1\n970#2:67\n999#2,3:68\n1002#2,3:78\n674#2:81\n704#2,4:82\n1137#2,3:95\n1137#2,3:98\n361#3,7:71\n766#4:86\n857#4,2:87\n1194#4,2:89\n1222#4,4:91\n*S KotlinDebug\n*F\n+ 1 DeclaredMemberIndex.kt\norg/jetbrains/kotlin/load/java/lazy/descriptors/ClassDeclaredMemberIndex\n*L\n52#1:67\n52#1:68,3\n52#1:78,3\n53#1:81\n53#1:82,4\n57#1:95,3\n60#1:98,3\n52#1:71,7\n54#1:86\n54#1:87,2\n54#1:89,2\n54#1:91,4\n*E\n"})
/* loaded from: classes8.dex */
public class ClassDeclaredMemberIndex implements DeclaredMemberIndex {

    @NotNull
    private final Map<Name, JavaRecordComponent> components;

    @NotNull
    private final Map<Name, JavaField> fields;

    @NotNull
    private final JavaClass jClass;

    @NotNull
    private final Function1<JavaMember, Boolean> memberFilter;

    @NotNull
    private final Function1<JavaMethod, Boolean> methodFilter;

    @NotNull
    private final Map<Name, List<JavaMethod>> methods;

    /* JADX WARN: Multi-variable type inference failed */
    public ClassDeclaredMemberIndex(@NotNull JavaClass jClass, @NotNull Function1<? super JavaMember, Boolean> memberFilter) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(memberFilter, "memberFilter");
        this.jClass = jClass;
        this.memberFilter = memberFilter;
        Function1<JavaMethod, Boolean> function1 = new Function1<JavaMethod, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.ClassDeclaredMemberIndex$methodFilter$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Boolean invoke(@NotNull JavaMethod m2) {
                Intrinsics.checkNotNullParameter(m2, "m");
                return Boolean.valueOf(((Boolean) this.this$0.memberFilter.invoke(m2)).booleanValue() && !JavaLoadingKt.isObjectMethodInInterface(m2));
            }
        };
        this.methodFilter = function1;
        Sequence sequenceFilter = SequencesKt___SequencesKt.filter(CollectionsKt___CollectionsKt.asSequence(jClass.getMethods()), function1);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : sequenceFilter) {
            Name name = ((JavaMethod) obj).getName();
            Object arrayList = linkedHashMap.get(name);
            if (arrayList == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(name, arrayList);
            }
            ((List) arrayList).add(obj);
        }
        this.methods = linkedHashMap;
        Sequence sequenceFilter2 = SequencesKt___SequencesKt.filter(CollectionsKt___CollectionsKt.asSequence(this.jClass.getFields()), this.memberFilter);
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj2 : sequenceFilter2) {
            linkedHashMap2.put(((JavaField) obj2).getName(), obj2);
        }
        this.fields = linkedHashMap2;
        Collection<JavaRecordComponent> recordComponents = this.jClass.getRecordComponents();
        Function1<JavaMember, Boolean> function12 = this.memberFilter;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj3 : recordComponents) {
            if (((Boolean) function12.invoke(obj3)).booleanValue()) {
                arrayList2.add(obj3);
            }
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (Object obj4 : arrayList2) {
            linkedHashMap3.put(((JavaRecordComponent) obj4).getName(), obj4);
        }
        this.components = linkedHashMap3;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex
    @Nullable
    public JavaField findFieldByName(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.fields.get(name);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex
    @NotNull
    public Collection<JavaMethod> findMethodsByName(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        List<JavaMethod> listEmptyList = this.methods.get(name);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        return listEmptyList;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex
    @Nullable
    public JavaRecordComponent findRecordComponentByName(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.components.get(name);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex
    @NotNull
    public Set<Name> getFieldNames() {
        Sequence sequenceFilter = SequencesKt___SequencesKt.filter(CollectionsKt___CollectionsKt.asSequence(this.jClass.getFields()), this.memberFilter);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((JavaField) it.next()).getName());
        }
        return linkedHashSet;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex
    @NotNull
    public Set<Name> getMethodNames() {
        Sequence sequenceFilter = SequencesKt___SequencesKt.filter(CollectionsKt___CollectionsKt.asSequence(this.jClass.getMethods()), this.methodFilter);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((JavaMethod) it.next()).getName());
        }
        return linkedHashSet;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex
    @NotNull
    public Set<Name> getRecordComponentNames() {
        return this.components.keySet();
    }
}
