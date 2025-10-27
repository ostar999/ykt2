package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

@GwtIncompatible
/* loaded from: classes4.dex */
final class FuturesGetChecked {
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() { // from class: com.google.common.util.concurrent.FuturesGetChecked.1
        @Override // com.google.common.base.Function
        public Boolean apply(Constructor<?> constructor) {
            return Boolean.valueOf(Arrays.asList(constructor.getParameterTypes()).contains(String.class));
        }
    }).reverse();

    @VisibleForTesting
    public interface GetCheckedTypeValidator {
        void validateClass(Class<? extends Exception> cls);
    }

    @VisibleForTesting
    public static class GetCheckedTypeValidatorHolder {
        static final String CLASS_VALUE_VALIDATOR_NAME = GetCheckedTypeValidatorHolder.class.getName() + "$ClassValueValidator";
        static final GetCheckedTypeValidator BEST_VALIDATOR = getBestValidator();

        @IgnoreJRERequirement
        public enum ClassValueValidator implements GetCheckedTypeValidator {
            INSTANCE;

            private static final ClassValue<Boolean> isValidClass = new ClassValue<Boolean>() { // from class: com.google.common.util.concurrent.FuturesGetChecked.GetCheckedTypeValidatorHolder.ClassValueValidator.1
                @Override // java.lang.ClassValue
                public /* bridge */ /* synthetic */ Boolean computeValue(Class cls) {
                    return computeValue((Class<?>) cls);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.lang.ClassValue
                public Boolean computeValue(Class<?> cls) {
                    FuturesGetChecked.checkExceptionClassValidity(cls.asSubclass(Exception.class));
                    return Boolean.TRUE;
                }
            };

            @Override // com.google.common.util.concurrent.FuturesGetChecked.GetCheckedTypeValidator
            public void validateClass(Class<? extends Exception> cls) {
                isValidClass.get(cls);
            }
        }

        public enum WeakSetValidator implements GetCheckedTypeValidator {
            INSTANCE;

            private static final Set<WeakReference<Class<? extends Exception>>> validClasses = new CopyOnWriteArraySet();

            @Override // com.google.common.util.concurrent.FuturesGetChecked.GetCheckedTypeValidator
            public void validateClass(Class<? extends Exception> cls) {
                Iterator<WeakReference<Class<? extends Exception>>> it = validClasses.iterator();
                while (it.hasNext()) {
                    if (cls.equals(it.next().get())) {
                        return;
                    }
                }
                FuturesGetChecked.checkExceptionClassValidity(cls);
                Set<WeakReference<Class<? extends Exception>>> set = validClasses;
                if (set.size() > 1000) {
                    set.clear();
                }
                set.add(new WeakReference<>(cls));
            }
        }

        public static GetCheckedTypeValidator getBestValidator() {
            try {
                return (GetCheckedTypeValidator) Class.forName(CLASS_VALUE_VALIDATOR_NAME).getEnumConstants()[0];
            } catch (Throwable unused) {
                return FuturesGetChecked.weakSetValidator();
            }
        }
    }

    private FuturesGetChecked() {
    }

    private static GetCheckedTypeValidator bestGetCheckedTypeValidator() {
        return GetCheckedTypeValidatorHolder.BEST_VALIDATOR;
    }

    @VisibleForTesting
    public static void checkExceptionClassValidity(Class<? extends Exception> cls) {
        Preconditions.checkArgument(isCheckedException(cls), "Futures.getChecked exception type (%s) must not be a RuntimeException", cls);
        Preconditions.checkArgument(hasConstructorUsableByGetChecked(cls), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", cls);
    }

    @VisibleForTesting
    public static GetCheckedTypeValidator classValueValidator() {
        return GetCheckedTypeValidatorHolder.ClassValueValidator.INSTANCE;
    }

    @CanIgnoreReturnValue
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls) throws Exception {
        return (V) getChecked(bestGetCheckedTypeValidator(), future, cls);
    }

    private static boolean hasConstructorUsableByGetChecked(Class<? extends Exception> cls) {
        try {
            newWithCause(cls, new Exception());
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @VisibleForTesting
    public static boolean isCheckedException(Class<? extends Exception> cls) {
        return !RuntimeException.class.isAssignableFrom(cls);
    }

    @NullableDecl
    private static <X> X newFromConstructor(Constructor<X> constructor, Throwable th) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] objArr = new Object[parameterTypes.length];
        for (int i2 = 0; i2 < parameterTypes.length; i2++) {
            Class<?> cls = parameterTypes[i2];
            if (cls.equals(String.class)) {
                objArr[i2] = th.toString();
            } else {
                if (!cls.equals(Throwable.class)) {
                    return null;
                }
                objArr[i2] = th;
            }
        }
        try {
            return constructor.newInstance(objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    private static <X extends Exception> X newWithCause(Class<X> cls, Throwable th) {
        Iterator it = preferringStrings(Arrays.asList(cls.getConstructors())).iterator();
        while (it.hasNext()) {
            X x2 = (X) newFromConstructor((Constructor) it.next(), th);
            if (x2 != null) {
                if (x2.getCause() == null) {
                    x2.initCause(th);
                }
                return x2;
            }
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + cls + " in response to chained exception", th);
    }

    private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> list) {
        return (List<Constructor<X>>) WITH_STRING_PARAM_FIRST.sortedCopy(list);
    }

    @VisibleForTesting
    public static GetCheckedTypeValidator weakSetValidator() {
        return GetCheckedTypeValidatorHolder.WeakSetValidator.INSTANCE;
    }

    private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable th, Class<X> cls) throws Exception {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        }
        if (!(th instanceof RuntimeException)) {
            throw newWithCause(cls, th);
        }
        throw new UncheckedExecutionException(th);
    }

    @VisibleForTesting
    @CanIgnoreReturnValue
    public static <V, X extends Exception> V getChecked(GetCheckedTypeValidator getCheckedTypeValidator, Future<V> future, Class<X> cls) throws Exception {
        getCheckedTypeValidator.validateClass(cls);
        try {
            return future.get();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw newWithCause(cls, e2);
        } catch (ExecutionException e3) {
            wrapAndThrowExceptionOrError(e3.getCause(), cls);
            throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> cls, long j2, TimeUnit timeUnit) throws Exception {
        bestGetCheckedTypeValidator().validateClass(cls);
        try {
            return future.get(j2, timeUnit);
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw newWithCause(cls, e2);
        } catch (ExecutionException e3) {
            wrapAndThrowExceptionOrError(e3.getCause(), cls);
            throw new AssertionError();
        } catch (TimeoutException e4) {
            throw newWithCause(cls, e4);
        }
    }
}
