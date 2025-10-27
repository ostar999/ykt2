package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension({"SMAP\nReflectJavaMember.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReflectJavaMember.kt\norg/jetbrains/kotlin/descriptors/runtime/structure/Java8ParameterNamesLoader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,105:1\n1#2:106\n11335#3:107\n11670#3,3:108\n*S KotlinDebug\n*F\n+ 1 ReflectJavaMember.kt\norg/jetbrains/kotlin/descriptors/runtime/structure/Java8ParameterNamesLoader\n*L\n100#1:107\n100#1:108,3\n*E\n"})
/* loaded from: classes8.dex */
final class Java8ParameterNamesLoader {

    @NotNull
    public static final Java8ParameterNamesLoader INSTANCE = new Java8ParameterNamesLoader();

    @Nullable
    private static Cache cache;

    public static final class Cache {

        @Nullable
        private final Method getName;

        @Nullable
        private final Method getParameters;

        public Cache(@Nullable Method method, @Nullable Method method2) {
            this.getParameters = method;
            this.getName = method2;
        }

        @Nullable
        public final Method getGetName() {
            return this.getName;
        }

        @Nullable
        public final Method getGetParameters() {
            return this.getParameters;
        }
    }

    private Java8ParameterNamesLoader() {
    }

    @NotNull
    public final Cache buildCache(@NotNull Member member) throws NoSuchMethodException, SecurityException {
        Intrinsics.checkNotNullParameter(member, "member");
        Class<?> cls = member.getClass();
        try {
            return new Cache(cls.getMethod("getParameters", new Class[0]), ReflectClassUtilKt.getSafeClassLoader(cls).loadClass("java.lang.reflect.Parameter").getMethod("getName", new Class[0]));
        } catch (NoSuchMethodException unused) {
            return new Cache(null, null);
        }
    }

    @Nullable
    public final List<String> loadParameterNames(@NotNull Member member) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method getName;
        Intrinsics.checkNotNullParameter(member, "member");
        Cache cacheBuildCache = cache;
        if (cacheBuildCache == null) {
            synchronized (this) {
                cacheBuildCache = cache;
                if (cacheBuildCache == null) {
                    cacheBuildCache = INSTANCE.buildCache(member);
                    cache = cacheBuildCache;
                }
            }
        }
        Method getParameters = cacheBuildCache.getGetParameters();
        if (getParameters == null || (getName = cacheBuildCache.getGetName()) == null) {
            return null;
        }
        Object objInvoke = getParameters.invoke(member, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Array<*>");
        Object[] objArr = (Object[]) objInvoke;
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            Object objInvoke2 = getName.invoke(obj, new Object[0]);
            Intrinsics.checkNotNull(objInvoke2, "null cannot be cast to non-null type kotlin.String");
            arrayList.add((String) objInvoke2);
        }
        return arrayList;
    }
}
