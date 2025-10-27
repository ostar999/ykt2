package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindExclude;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public class SubpackagesScope extends MemberScopeImpl {

    @NotNull
    private final FqName fqName;

    @NotNull
    private final ModuleDescriptor moduleDescriptor;

    public SubpackagesScope(@NotNull ModuleDescriptor moduleDescriptor, @NotNull FqName fqName) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.moduleDescriptor = moduleDescriptor;
        this.fqName = fqName;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getClassifierNames() {
        return SetsKt__SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (!kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getPACKAGES_MASK())) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        if (this.fqName.isRoot() && kindFilter.getExcludes().contains(DescriptorKindExclude.TopLevelPackages.INSTANCE)) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        Collection<FqName> subPackagesOf = this.moduleDescriptor.getSubPackagesOf(this.fqName, nameFilter);
        ArrayList arrayList = new ArrayList(subPackagesOf.size());
        Iterator<FqName> it = subPackagesOf.iterator();
        while (it.hasNext()) {
            Name nameShortName = it.next().shortName();
            Intrinsics.checkNotNullExpressionValue(nameShortName, "subFqName.shortName()");
            if (nameFilter.invoke(nameShortName).booleanValue()) {
                CollectionsKt.addIfNotNull(arrayList, getPackage(nameShortName));
            }
        }
        return arrayList;
    }

    @Nullable
    public final PackageViewDescriptor getPackage(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (name.isSpecial()) {
            return null;
        }
        ModuleDescriptor moduleDescriptor = this.moduleDescriptor;
        FqName fqNameChild = this.fqName.child(name);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "fqName.child(name)");
        PackageViewDescriptor packageViewDescriptor = moduleDescriptor.getPackage(fqNameChild);
        if (packageViewDescriptor.isEmpty()) {
            return null;
        }
        return packageViewDescriptor;
    }

    @NotNull
    public String toString() {
        return "subpackages of " + this.fqName + " from " + this.moduleDescriptor;
    }
}
