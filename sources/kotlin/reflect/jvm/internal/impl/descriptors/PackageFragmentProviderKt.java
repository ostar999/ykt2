package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class PackageFragmentProviderKt {
    public static final void collectPackageFragmentsOptimizedIfPossible(@NotNull PackageFragmentProvider packageFragmentProvider, @NotNull FqName fqName, @NotNull Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        if (packageFragmentProvider instanceof PackageFragmentProviderOptimized) {
            ((PackageFragmentProviderOptimized) packageFragmentProvider).collectPackageFragments(fqName, packageFragments);
        } else {
            packageFragments.addAll(packageFragmentProvider.getPackageFragments(fqName));
        }
    }

    public static final boolean isEmpty(@NotNull PackageFragmentProvider packageFragmentProvider, @NotNull FqName fqName) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return packageFragmentProvider instanceof PackageFragmentProviderOptimized ? ((PackageFragmentProviderOptimized) packageFragmentProvider).isEmpty(fqName) : packageFragments(packageFragmentProvider, fqName).isEmpty();
    }

    @NotNull
    public static final List<PackageFragmentDescriptor> packageFragments(@NotNull PackageFragmentProvider packageFragmentProvider, @NotNull FqName fqName) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ArrayList arrayList = new ArrayList();
        collectPackageFragmentsOptimizedIfPossible(packageFragmentProvider, fqName, arrayList);
        return arrayList;
    }
}
