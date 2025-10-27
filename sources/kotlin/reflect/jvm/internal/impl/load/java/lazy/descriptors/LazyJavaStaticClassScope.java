package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.sequences.SequencesKt___SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nLazyJavaStaticClassScope.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LazyJavaStaticClassScope.kt\norg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticClassScope\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 5 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,171:1\n1#2:172\n1477#3:173\n1502#3,3:174\n1505#3,3:184\n1549#3:193\n1620#3,3:194\n361#4,7:177\n76#5:187\n96#5,5:188\n*S KotlinDebug\n*F\n+ 1 LazyJavaStaticClassScope.kt\norg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticClassScope\n*L\n112#1:173\n112#1:174,3\n112#1:184,3\n168#1:193\n168#1:194,3\n112#1:177,7\n114#1:187\n114#1:188,5\n*E\n"})
/* loaded from: classes8.dex */
public final class LazyJavaStaticClassScope extends LazyJavaStaticScope {

    @NotNull
    private final JavaClass jClass;

    @NotNull
    private final JavaClassDescriptor ownerDescriptor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaStaticClassScope(@NotNull LazyJavaResolverContext c3, @NotNull JavaClass jClass, @NotNull JavaClassDescriptor ownerDescriptor) {
        super(c3);
        Intrinsics.checkNotNullParameter(c3, "c");
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        this.jClass = jClass;
        this.ownerDescriptor = ownerDescriptor;
    }

    private final <R> Set<R> flatMapJavaStaticSupertypesScopes(final ClassDescriptor classDescriptor, final Set<R> set, final Function1<? super MemberScope, ? extends Collection<? extends R>> function1) {
        DFS.dfs(CollectionsKt__CollectionsJVMKt.listOf(classDescriptor), new DFS.Neighbors() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope$$Lambda$0
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            public Iterable getNeighbors(Object obj) {
                return LazyJavaStaticClassScope.flatMapJavaStaticSupertypesScopes$lambda$6((ClassDescriptor) obj);
            }
        }, new DFS.AbstractNodeHandler<ClassDescriptor, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope.flatMapJavaStaticSupertypesScopes.2
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m2083result();
                return Unit.INSTANCE;
            }

            /* renamed from: result, reason: collision with other method in class */
            public void m2083result() {
            }

            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public boolean beforeChildren(@NotNull ClassDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (current == classDescriptor) {
                    return true;
                }
                MemberScope staticScope = current.getStaticScope();
                Intrinsics.checkNotNullExpressionValue(staticScope, "current.staticScope");
                if (!(staticScope instanceof LazyJavaStaticScope)) {
                    return true;
                }
                set.addAll((Collection) function1.invoke(staticScope));
                return false;
            }
        });
        return set;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable flatMapJavaStaticSupertypesScopes$lambda$6(ClassDescriptor classDescriptor) {
        Collection<KotlinType> collectionMo2087getSupertypes = classDescriptor.getTypeConstructor().mo2087getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo2087getSupertypes, "it.typeConstructor.supertypes");
        return SequencesKt___SequencesKt.asIterable(SequencesKt___SequencesKt.mapNotNull(CollectionsKt___CollectionsKt.asSequence(collectionMo2087getSupertypes), new Function1<KotlinType, ClassDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope$flatMapJavaStaticSupertypesScopes$1$1
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final ClassDescriptor invoke(KotlinType kotlinType) {
                ClassifierDescriptor classifierDescriptorMo2086getDeclarationDescriptor = kotlinType.getConstructor().mo2086getDeclarationDescriptor();
                if (classifierDescriptorMo2086getDeclarationDescriptor instanceof ClassDescriptor) {
                    return (ClassDescriptor) classifierDescriptorMo2086getDeclarationDescriptor;
                }
                return null;
            }
        }));
    }

    private final PropertyDescriptor getRealOriginal(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor.getKind().isReal()) {
            return propertyDescriptor;
        }
        Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "this.overriddenDescriptors");
        Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(collection, 10));
        for (PropertyDescriptor it : collection) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            arrayList.add(getRealOriginal(it));
        }
        return (PropertyDescriptor) CollectionsKt___CollectionsKt.single(CollectionsKt___CollectionsKt.distinct(arrayList));
    }

    private final Set<SimpleFunctionDescriptor> getStaticFunctionsFromJavaSuperClasses(Name name, ClassDescriptor classDescriptor) {
        LazyJavaStaticClassScope parentJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(classDescriptor);
        return parentJavaStaticClassScope == null ? SetsKt__SetsKt.emptySet() : CollectionsKt___CollectionsKt.toSet(parentJavaStaticClassScope.getContributedFunctions(name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS));
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public Set<Name> computeClassNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt__SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public Set<Name> computeFunctionNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Set<Name> mutableSet = CollectionsKt___CollectionsKt.toMutableSet(getDeclaredMemberIndex().invoke().getMethodNames());
        LazyJavaStaticClassScope parentJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(getOwnerDescriptor());
        Set<Name> functionNames = parentJavaStaticClassScope != null ? parentJavaStaticClassScope.getFunctionNames() : null;
        if (functionNames == null) {
            functionNames = SetsKt__SetsKt.emptySet();
        }
        mutableSet.addAll(functionNames);
        if (this.jClass.isEnum()) {
            mutableSet.addAll(CollectionsKt__CollectionsKt.listOf((Object[]) new Name[]{StandardNames.ENUM_VALUE_OF, StandardNames.ENUM_VALUES}));
        }
        mutableSet.addAll(getC().getComponents().getSyntheticPartsProvider().getStaticFunctionNames(getC(), getOwnerDescriptor()));
        return mutableSet;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    public void computeImplicitlyDeclaredFunctions(@NotNull Collection<SimpleFunctionDescriptor> result, @NotNull Name name) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name, "name");
        getC().getComponents().getSyntheticPartsProvider().generateStaticFunctions(getC(), getOwnerDescriptor(), name, result);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    public void computeNonDeclaredFunctions(@NotNull Collection<SimpleFunctionDescriptor> result, @NotNull Name name) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name, "name");
        Collection<? extends SimpleFunctionDescriptor> collectionResolveOverridesForStaticMembers = DescriptorResolverUtils.resolveOverridesForStaticMembers(name, getStaticFunctionsFromJavaSuperClasses(name, getOwnerDescriptor()), result, getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
        Intrinsics.checkNotNullExpressionValue(collectionResolveOverridesForStaticMembers, "resolveOverridesForStati…rridingUtil\n            )");
        result.addAll(collectionResolveOverridesForStaticMembers);
        if (this.jClass.isEnum()) {
            if (Intrinsics.areEqual(name, StandardNames.ENUM_VALUE_OF)) {
                SimpleFunctionDescriptor simpleFunctionDescriptorCreateEnumValueOfMethod = DescriptorFactory.createEnumValueOfMethod(getOwnerDescriptor());
                Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorCreateEnumValueOfMethod, "createEnumValueOfMethod(ownerDescriptor)");
                result.add(simpleFunctionDescriptorCreateEnumValueOfMethod);
            } else if (Intrinsics.areEqual(name, StandardNames.ENUM_VALUES)) {
                SimpleFunctionDescriptor simpleFunctionDescriptorCreateEnumValuesMethod = DescriptorFactory.createEnumValuesMethod(getOwnerDescriptor());
                Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorCreateEnumValuesMethod, "createEnumValuesMethod(ownerDescriptor)");
                result.add(simpleFunctionDescriptorCreateEnumValuesMethod);
            }
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticScope, kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    public void computeNonDeclaredProperties(@NotNull final Name name, @NotNull Collection<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        Set setFlatMapJavaStaticSupertypesScopes = flatMapJavaStaticSupertypesScopes(getOwnerDescriptor(), new LinkedHashSet(), new Function1<MemberScope, Collection<? extends PropertyDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope$computeNonDeclaredProperties$propertiesFromSupertypes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<? extends PropertyDescriptor> invoke(@NotNull MemberScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getContributedVariables(name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            }
        });
        if (!result.isEmpty()) {
            Collection<? extends PropertyDescriptor> collectionResolveOverridesForStaticMembers = DescriptorResolverUtils.resolveOverridesForStaticMembers(name, setFlatMapJavaStaticSupertypesScopes, result, getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
            Intrinsics.checkNotNullExpressionValue(collectionResolveOverridesForStaticMembers, "resolveOverridesForStati…ingUtil\n                )");
            result.addAll(collectionResolveOverridesForStaticMembers);
        } else {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : setFlatMapJavaStaticSupertypesScopes) {
                PropertyDescriptor realOriginal = getRealOriginal((PropertyDescriptor) obj);
                Object arrayList = linkedHashMap.get(realOriginal);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(realOriginal, arrayList);
                }
                ((List) arrayList).add(obj);
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Collection collectionResolveOverridesForStaticMembers2 = DescriptorResolverUtils.resolveOverridesForStaticMembers(name, (Collection) ((Map.Entry) it.next()).getValue(), result, getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
                Intrinsics.checkNotNullExpressionValue(collectionResolveOverridesForStaticMembers2, "resolveOverridesForStati…ingUtil\n                )");
                CollectionsKt__MutableCollectionsKt.addAll(arrayList2, collectionResolveOverridesForStaticMembers2);
            }
            result.addAll(arrayList2);
        }
        if (this.jClass.isEnum() && Intrinsics.areEqual(name, StandardNames.ENUM_ENTRIES)) {
            CollectionsKt.addIfNotNull(result, DescriptorFactory.createEnumEntriesProperty(getOwnerDescriptor()));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public Set<Name> computePropertyNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Set<Name> mutableSet = CollectionsKt___CollectionsKt.toMutableSet(getDeclaredMemberIndex().invoke().getFieldNames());
        flatMapJavaStaticSupertypesScopes(getOwnerDescriptor(), mutableSet, new Function1<MemberScope, Collection<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope$computePropertyNames$1$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<Name> invoke(@NotNull MemberScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getVariableNames();
            }
        });
        if (this.jClass.isEnum()) {
            mutableSet.add(StandardNames.ENUM_ENTRIES);
        }
        return mutableSet;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @Nullable
    /* renamed from: getContributedClassifier */
    public ClassifierDescriptor mo2088getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public ClassDeclaredMemberIndex computeMemberIndex() {
        return new ClassDeclaredMemberIndex(this.jClass, new Function1<JavaMember, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope.computeMemberIndex.1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Boolean invoke(@NotNull JavaMember it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.isStatic());
            }
        });
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public JavaClassDescriptor getOwnerDescriptor() {
        return this.ownerDescriptor;
    }
}
