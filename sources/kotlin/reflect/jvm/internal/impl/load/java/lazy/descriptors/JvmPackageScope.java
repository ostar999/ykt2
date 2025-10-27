package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeKt;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nJvmPackageScope.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JvmPackageScope.kt\norg/jetbrains/kotlin/load/java/lazy/descriptors/JvmPackageScope\n+ 2 scopeUtils.kt\norg/jetbrains/kotlin/util/collectionUtils/ScopeUtilsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,109:1\n92#2,14:110\n60#2,5:124\n60#2,5:129\n60#2,5:134\n10664#3,5:139\n10664#3,5:144\n*S KotlinDebug\n*F\n+ 1 JvmPackageScope.kt\norg/jetbrains/kotlin/load/java/lazy/descriptors/JvmPackageScope\n*L\n58#1:110,14\n63#1:124,5\n68#1:129,5\n74#1:134,5\n76#1:139,5\n80#1:144,5\n*E\n"})
/* loaded from: classes8.dex */
public final class JvmPackageScope implements MemberScope {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmPackageScope.class), "kotlinScopes", "getKotlinScopes()[Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;"))};

    /* renamed from: c, reason: collision with root package name */
    @NotNull
    private final LazyJavaResolverContext f27626c;

    @NotNull
    private final LazyJavaPackageScope javaScope;

    @NotNull
    private final NotNullLazyValue kotlinScopes$delegate;

    @NotNull
    private final LazyJavaPackageFragment packageFragment;

    public JvmPackageScope(@NotNull LazyJavaResolverContext c3, @NotNull JavaPackage jPackage, @NotNull LazyJavaPackageFragment packageFragment) {
        Intrinsics.checkNotNullParameter(c3, "c");
        Intrinsics.checkNotNullParameter(jPackage, "jPackage");
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        this.f27626c = c3;
        this.packageFragment = packageFragment;
        this.javaScope = new LazyJavaPackageScope(c3, jPackage, packageFragment);
        this.kotlinScopes$delegate = c3.getStorageManager().createLazyValue(new Function0<MemberScope[]>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JvmPackageScope$kotlinScopes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final MemberScope[] invoke() {
                Collection<KotlinJvmBinaryClass> collectionValues = this.this$0.packageFragment.getBinaryClasses$descriptors_jvm().values();
                JvmPackageScope jvmPackageScope = this.this$0;
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = collectionValues.iterator();
                while (it.hasNext()) {
                    MemberScope memberScopeCreateKotlinPackagePartScope = jvmPackageScope.f27626c.getComponents().getDeserializedDescriptorResolver().createKotlinPackagePartScope(jvmPackageScope.packageFragment, (KotlinJvmBinaryClass) it.next());
                    if (memberScopeCreateKotlinPackagePartScope != null) {
                        arrayList.add(memberScopeCreateKotlinPackagePartScope);
                    }
                }
                return (MemberScope[]) ScopeUtilsKt.listOfNonEmptyScopes(arrayList).toArray(new MemberScope[0]);
            }
        });
    }

    private final MemberScope[] getKotlinScopes() {
        return (MemberScope[]) StorageKt.getValue(this.kotlinScopes$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @Nullable
    public Set<Name> getClassifierNames() {
        Set<Name> setFlatMapClassifierNamesOrNull = MemberScopeKt.flatMapClassifierNamesOrNull(ArraysKt___ArraysKt.asIterable(getKotlinScopes()));
        if (setFlatMapClassifierNamesOrNull == null) {
            return null;
        }
        setFlatMapClassifierNamesOrNull.addAll(this.javaScope.getClassifierNames());
        return setFlatMapClassifierNamesOrNull;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @Nullable
    /* renamed from: getContributedClassifier */
    public ClassifierDescriptor mo2088getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo2092recordLookup(name, location);
        ClassDescriptor classDescriptorMo2088getContributedClassifier = this.javaScope.mo2088getContributedClassifier(name, location);
        if (classDescriptorMo2088getContributedClassifier != null) {
            return classDescriptorMo2088getContributedClassifier;
        }
        ClassifierDescriptor classifierDescriptor = null;
        for (MemberScope memberScope : getKotlinScopes()) {
            ClassifierDescriptor classifierDescriptorMo2088getContributedClassifier = memberScope.mo2088getContributedClassifier(name, location);
            if (classifierDescriptorMo2088getContributedClassifier != null) {
                if (!(classifierDescriptorMo2088getContributedClassifier instanceof ClassifierDescriptorWithTypeParameters) || !((ClassifierDescriptorWithTypeParameters) classifierDescriptorMo2088getContributedClassifier).isExpect()) {
                    return classifierDescriptorMo2088getContributedClassifier;
                }
                if (classifierDescriptor == null) {
                    classifierDescriptor = classifierDescriptorMo2088getContributedClassifier;
                }
            }
        }
        return classifierDescriptor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] kotlinScopes = getKotlinScopes();
        Collection<DeclarationDescriptor> contributedDescriptors = lazyJavaPackageScope.getContributedDescriptors(kindFilter, nameFilter);
        for (MemberScope memberScope : kotlinScopes) {
            contributedDescriptors = ScopeUtilsKt.concat(contributedDescriptors, memberScope.getContributedDescriptors(kindFilter, nameFilter));
        }
        return contributedDescriptors == null ? SetsKt__SetsKt.emptySet() : contributedDescriptors;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo2092recordLookup(name, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] kotlinScopes = getKotlinScopes();
        Collection<? extends SimpleFunctionDescriptor> contributedFunctions = lazyJavaPackageScope.getContributedFunctions(name, location);
        int length = kotlinScopes.length;
        int i2 = 0;
        Collection collection = contributedFunctions;
        while (i2 < length) {
            Collection collectionConcat = ScopeUtilsKt.concat(collection, kotlinScopes[i2].getContributedFunctions(name, location));
            i2++;
            collection = collectionConcat;
        }
        return collection == null ? SetsKt__SetsKt.emptySet() : collection;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo2092recordLookup(name, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] kotlinScopes = getKotlinScopes();
        Collection<? extends PropertyDescriptor> contributedVariables = lazyJavaPackageScope.getContributedVariables(name, location);
        int length = kotlinScopes.length;
        int i2 = 0;
        Collection collection = contributedVariables;
        while (i2 < length) {
            Collection collectionConcat = ScopeUtilsKt.concat(collection, kotlinScopes[i2].getContributedVariables(name, location));
            i2++;
            collection = collectionConcat;
        }
        return collection == null ? SetsKt__SetsKt.emptySet() : collection;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getFunctionNames() {
        MemberScope[] kotlinScopes = getKotlinScopes();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : kotlinScopes) {
            CollectionsKt__MutableCollectionsKt.addAll(linkedHashSet, memberScope.getFunctionNames());
        }
        linkedHashSet.addAll(this.javaScope.getFunctionNames());
        return linkedHashSet;
    }

    @NotNull
    public final LazyJavaPackageScope getJavaScope$descriptors_jvm() {
        return this.javaScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getVariableNames() {
        MemberScope[] kotlinScopes = getKotlinScopes();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberScope memberScope : kotlinScopes) {
            CollectionsKt__MutableCollectionsKt.addAll(linkedHashSet, memberScope.getVariableNames());
        }
        linkedHashSet.addAll(this.javaScope.getVariableNames());
        return linkedHashSet;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    /* renamed from: recordLookup */
    public void mo2092recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(this.f27626c.getComponents().getLookupTracker(), location, this.packageFragment, name);
    }

    @NotNull
    public String toString() {
        return "scope for " + this.packageFragment;
    }
}
