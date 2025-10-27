package kotlin.reflect.jvm.internal.impl.types.error;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.DefaultBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleCapability;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class ErrorModuleDescriptor implements ModuleDescriptor {

    @NotNull
    public static final ErrorModuleDescriptor INSTANCE = new ErrorModuleDescriptor();

    @NotNull
    private static final List<ModuleDescriptor> allDependencyModules;

    @NotNull
    private static final Set<ModuleDescriptor> allExpectedByModules;

    @NotNull
    private static final KotlinBuiltIns builtIns;

    @NotNull
    private static final List<ModuleDescriptor> expectedByModules;

    @NotNull
    private static final Name stableName;

    static {
        Name nameSpecial = Name.special(ErrorEntity.ERROR_MODULE.getDebugText());
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(ErrorEntity.ERROR_MODULE.debugText)");
        stableName = nameSpecial;
        allDependencyModules = CollectionsKt__CollectionsKt.emptyList();
        expectedByModules = CollectionsKt__CollectionsKt.emptyList();
        allExpectedByModules = SetsKt__SetsKt.emptySet();
        builtIns = DefaultBuiltIns.Companion.getInstance();
    }

    private ErrorModuleDescriptor() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @Nullable
    public <R, D> R accept(@NotNull DeclarationDescriptorVisitor<R, D> visitor, D d3) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
    @NotNull
    public Annotations getAnnotations() {
        return Annotations.Companion.getEMPTY();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor
    @NotNull
    public KotlinBuiltIns getBuiltIns() {
        return builtIns;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor
    @Nullable
    public <T> T getCapability(@NotNull ModuleCapability<T> capability) {
        Intrinsics.checkNotNullParameter(capability, "capability");
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @Nullable
    public DeclarationDescriptor getContainingDeclaration() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor
    @NotNull
    public List<ModuleDescriptor> getExpectedByModules() {
        return expectedByModules;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.Named
    @NotNull
    public Name getName() {
        return getStableName();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @NotNull
    public DeclarationDescriptor getOriginal() {
        return this;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor
    @NotNull
    public PackageViewDescriptor getPackage(@NotNull FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        throw new IllegalStateException("Should not be called!");
    }

    @NotNull
    public Name getStableName() {
        return stableName;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor
    @NotNull
    public Collection<FqName> getSubPackagesOf(@NotNull FqName fqName, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt__CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor
    public boolean shouldSeeInternalsOf(@NotNull ModuleDescriptor targetModule) {
        Intrinsics.checkNotNullParameter(targetModule, "targetModule");
        return false;
    }
}
