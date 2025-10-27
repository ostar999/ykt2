package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CopyableThrowable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a2\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005j\u0002`\u0007\"\b\b\u0000\u0010\b*\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\nH\u0002\u001a*\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005j\u0004\u0018\u0001`\u00072\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0002\u001a1\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005j\u0002`\u00072\u0014\b\u0004\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H\u0082\b\u001a!\u0010\u0010\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\u00062\u0006\u0010\u0011\u001a\u0002H\bH\u0000¢\u0006\u0002\u0010\u0012\u001a\u001b\u0010\u0013\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\n2\b\b\u0002\u0010\u0014\u001a\u00020\u0003H\u0082\u0010\u001a\u0018\u0010\u0015\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0016\u001a\u00020\u0003H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0017\"\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¨\u0006\u0018"}, d2 = {"ctorCache", "Lkotlinx/coroutines/internal/CtorCache;", "throwableFields", "", "createConstructor", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/Ctor;", "E", "clz", "Ljava/lang/Class;", "createSafeConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ExceptionsConstructorKt {

    @NotNull
    private static final CtorCache ctorCache;
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);

    static {
        CtorCache ctorCache2;
        try {
            ctorCache2 = FastServiceLoaderKt.getANDROID_DETECTED() ? WeakMapCtorCache.INSTANCE : ClassValueCtorCache.INSTANCE;
        } catch (Throwable unused) {
            ctorCache2 = WeakMapCtorCache.INSTANCE;
        }
        ctorCache = ctorCache2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <E extends Throwable> Function1<Throwable, Throwable> createConstructor(Class<E> cls) {
        ExceptionsConstructorKt$createConstructor$nullResult$1 exceptionsConstructorKt$createConstructor$nullResult$1 = new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createConstructor$nullResult$1
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final Void invoke(@NotNull Throwable th) {
                return null;
            }
        };
        if (throwableFields != fieldsCountOrDefault(cls, 0)) {
            return exceptionsConstructorKt$createConstructor$nullResult$1;
        }
        Iterator it = ArraysKt___ArraysKt.sortedWith(cls.getConstructors(), new Comparator() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createConstructor$$inlined$sortedByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t2, T t3) {
                return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((Constructor) t3).getParameterTypes().length), Integer.valueOf(((Constructor) t2).getParameterTypes().length));
            }
        }).iterator();
        while (it.hasNext()) {
            Function1<Throwable, Throwable> function1CreateSafeConstructor = createSafeConstructor((Constructor) it.next());
            if (function1CreateSafeConstructor != null) {
                return function1CreateSafeConstructor;
            }
        }
        return exceptionsConstructorKt$createConstructor$nullResult$1;
    }

    private static final Function1<Throwable, Throwable> createSafeConstructor(final Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        int length = parameterTypes.length;
        if (length == 0) {
            return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final Throwable invoke(@NotNull Throwable th) {
                    Object objM783constructorimpl;
                    Object objNewInstance;
                    try {
                        Result.Companion companion = Result.INSTANCE;
                        objNewInstance = constructor.newInstance(new Object[0]);
                    } catch (Throwable th2) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th2));
                    }
                    if (objNewInstance == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                    Throwable th3 = (Throwable) objNewInstance;
                    th3.initCause(th);
                    objM783constructorimpl = Result.m783constructorimpl(th3);
                    if (Result.m789isFailureimpl(objM783constructorimpl)) {
                        objM783constructorimpl = null;
                    }
                    return (Throwable) objM783constructorimpl;
                }
            };
        }
        if (length != 1) {
            if (length == 2 && Intrinsics.areEqual(parameterTypes[0], String.class) && Intrinsics.areEqual(parameterTypes[1], Throwable.class)) {
                return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    @Nullable
                    public final Throwable invoke(@NotNull Throwable th) {
                        Object objM783constructorimpl;
                        Object objNewInstance;
                        try {
                            Result.Companion companion = Result.INSTANCE;
                            objNewInstance = constructor.newInstance(th.getMessage(), th);
                        } catch (Throwable th2) {
                            Result.Companion companion2 = Result.INSTANCE;
                            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th2));
                        }
                        if (objNewInstance == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                        }
                        objM783constructorimpl = Result.m783constructorimpl((Throwable) objNewInstance);
                        if (Result.m789isFailureimpl(objM783constructorimpl)) {
                            objM783constructorimpl = null;
                        }
                        return (Throwable) objM783constructorimpl;
                    }
                };
            }
            return null;
        }
        Class<?> cls = parameterTypes[0];
        if (Intrinsics.areEqual(cls, Throwable.class)) {
            return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final Throwable invoke(@NotNull Throwable th) {
                    Object objM783constructorimpl;
                    Object objNewInstance;
                    try {
                        Result.Companion companion = Result.INSTANCE;
                        objNewInstance = constructor.newInstance(th);
                    } catch (Throwable th2) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th2));
                    }
                    if (objNewInstance == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                    objM783constructorimpl = Result.m783constructorimpl((Throwable) objNewInstance);
                    if (Result.m789isFailureimpl(objM783constructorimpl)) {
                        objM783constructorimpl = null;
                    }
                    return (Throwable) objM783constructorimpl;
                }
            };
        }
        if (Intrinsics.areEqual(cls, String.class)) {
            return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @Nullable
                public final Throwable invoke(@NotNull Throwable th) {
                    Object objM783constructorimpl;
                    Object objNewInstance;
                    try {
                        Result.Companion companion = Result.INSTANCE;
                        objNewInstance = constructor.newInstance(th.getMessage());
                    } catch (Throwable th2) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th2));
                    }
                    if (objNewInstance == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                    Throwable th3 = (Throwable) objNewInstance;
                    th3.initCause(th);
                    objM783constructorimpl = Result.m783constructorimpl(th3);
                    if (Result.m789isFailureimpl(objM783constructorimpl)) {
                        objM783constructorimpl = null;
                    }
                    return (Throwable) objM783constructorimpl;
                }
            };
        }
        return null;
    }

    private static final int fieldsCount(Class<?> cls, int i2) {
        do {
            int length = cls.getDeclaredFields().length;
            int i3 = 0;
            for (int i4 = 0; i4 < length; i4++) {
                if (!Modifier.isStatic(r0[i4].getModifiers())) {
                    i3++;
                }
            }
            i2 += i3;
            cls = cls.getSuperclass();
        } while (cls != null);
        return i2;
    }

    public static /* synthetic */ int fieldsCount$default(Class cls, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        return fieldsCount(cls, i2);
    }

    private static final int fieldsCountOrDefault(Class<?> cls, int i2) {
        Object objM783constructorimpl;
        JvmClassMappingKt.getKotlinClass(cls);
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(Integer.valueOf(fieldsCount$default(cls, 0, 1, null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        Integer numValueOf = Integer.valueOf(i2);
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = numValueOf;
        }
        return ((Number) objM783constructorimpl).intValue();
    }

    private static final Function1<Throwable, Throwable> safeCtor(final Function1<? super Throwable, ? extends Throwable> function1) {
        return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt.safeCtor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final Throwable invoke(@NotNull Throwable th) {
                Object objM783constructorimpl;
                Function1<Throwable, Throwable> function12 = function1;
                try {
                    Result.Companion companion = Result.INSTANCE;
                    objM783constructorimpl = Result.m783constructorimpl(function12.invoke(th));
                } catch (Throwable th2) {
                    Result.Companion companion2 = Result.INSTANCE;
                    objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th2));
                }
                if (Result.m789isFailureimpl(objM783constructorimpl)) {
                    objM783constructorimpl = null;
                }
                return (Throwable) objM783constructorimpl;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static final <E extends Throwable> E tryCopyException(@NotNull E e2) {
        Object objM783constructorimpl;
        if (!(e2 instanceof CopyableThrowable)) {
            return (E) ctorCache.get(e2.getClass()).invoke(e2);
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(((CopyableThrowable) e2).createCopy());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = null;
        }
        return (E) objM783constructorimpl;
    }
}
