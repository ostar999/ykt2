package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ServiceLoader;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface BuiltInsLoader {

    @NotNull
    public static final Companion Companion = Companion.$$INSTANCE;

    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        @NotNull
        private static final Lazy<BuiltInsLoader> Instance$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<BuiltInsLoader>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader$Companion$Instance$2
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final BuiltInsLoader invoke() {
                ServiceLoader implementations = ServiceLoader.load(BuiltInsLoader.class, BuiltInsLoader.class.getClassLoader());
                Intrinsics.checkNotNullExpressionValue(implementations, "implementations");
                BuiltInsLoader builtInsLoader = (BuiltInsLoader) CollectionsKt___CollectionsKt.firstOrNull(implementations);
                if (builtInsLoader != null) {
                    return builtInsLoader;
                }
                throw new IllegalStateException("No BuiltInsLoader implementation was found. Please ensure that the META-INF/services/ is not stripped from your application and that the Java virtual machine is not running under a security manager");
            }
        });

        private Companion() {
        }

        @NotNull
        public final BuiltInsLoader getInstance() {
            return Instance$delegate.getValue();
        }
    }

    @NotNull
    PackageFragmentProvider createPackageFragmentProvider(@NotNull StorageManager storageManager, @NotNull ModuleDescriptor moduleDescriptor, @NotNull Iterable<? extends ClassDescriptorFactory> iterable, @NotNull PlatformDependentDeclarationFilter platformDependentDeclarationFilter, @NotNull AdditionalClassPartsProvider additionalClassPartsProvider, boolean z2);
}
