package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface PackagePartProvider {

    public static final class Empty implements PackagePartProvider {

        @NotNull
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        @Override // kotlin.reflect.jvm.internal.impl.load.kotlin.PackagePartProvider
        @NotNull
        public List<String> findPackageParts(@NotNull String packageFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            return CollectionsKt__CollectionsKt.emptyList();
        }
    }

    @NotNull
    List<String> findPackageParts(@NotNull String str);
}
