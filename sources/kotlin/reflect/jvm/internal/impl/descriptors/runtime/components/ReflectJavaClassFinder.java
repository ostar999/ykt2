package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaPackage;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public final class ReflectJavaClassFinder implements JavaClassFinder {

    @NotNull
    private final ClassLoader classLoader;

    public ReflectJavaClassFinder(@NotNull ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.classLoader = classLoader;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder
    @Nullable
    public JavaClass findClass(@NotNull JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ClassId classId = request.getClassId();
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "classId.packageFqName");
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "classId.relativeClassName.asString()");
        String strReplace$default = StringsKt__StringsJVMKt.replace$default(strAsString, '.', Typography.dollar, false, 4, (Object) null);
        if (!packageFqName.isRoot()) {
            strReplace$default = packageFqName.asString() + '.' + strReplace$default;
        }
        Class<?> clsTryLoadClass = ReflectJavaClassFinderKt.tryLoadClass(this.classLoader, strReplace$default);
        if (clsTryLoadClass != null) {
            return new ReflectJavaClass(clsTryLoadClass);
        }
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder
    @Nullable
    public JavaPackage findPackage(@NotNull FqName fqName, boolean z2) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return new ReflectJavaPackage(fqName);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder
    @Nullable
    public Set<String> knownClassNamesInPackage(@NotNull FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return null;
    }
}
