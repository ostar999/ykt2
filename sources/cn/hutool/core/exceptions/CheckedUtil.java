package cn.hutool.core.exceptions;

import cn.hutool.core.lang.func.Func;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.Supplier1;
import cn.hutool.core.lang.func.VoidFunc;
import cn.hutool.core.lang.func.VoidFunc0;
import cn.hutool.core.lang.func.VoidFunc1;
import java.util.Objects;
import w.a;
import w.b;
import w.c;
import w.d;
import w.e;
import w.f;
import w.g;
import w.h;

/* loaded from: classes.dex */
public class CheckedUtil {

    public interface Func0Rt<R> extends Func0<R> {
        @Override // cn.hutool.core.lang.func.Func0
        R call() throws RuntimeException;
    }

    public interface Func1Rt<P, R> extends Func1<P, R> {
        @Override // cn.hutool.core.lang.func.Func1
        R call(P p2) throws RuntimeException;
    }

    public interface FuncRt<P, R> extends Func<P, R> {
        @Override // cn.hutool.core.lang.func.Func
        R call(P... pArr) throws RuntimeException;
    }

    public interface VoidFunc0Rt extends VoidFunc0 {
        @Override // cn.hutool.core.lang.func.VoidFunc0
        void call() throws RuntimeException;
    }

    public interface VoidFunc1Rt<P> extends VoidFunc1<P> {
        @Override // cn.hutool.core.lang.func.VoidFunc1
        void call(P p2) throws RuntimeException;
    }

    public interface VoidFuncRt<P> extends VoidFunc<P> {
        @Override // cn.hutool.core.lang.func.VoidFunc
        void call(P... pArr) throws RuntimeException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$uncheck$2300d7df$1(VoidFunc0 voidFunc0, Supplier1 supplier1) throws RuntimeException {
        try {
            voidFunc0.call();
        } catch (Exception e2) {
            if (supplier1 != null) {
                throw ((RuntimeException) supplier1.get(e2));
            }
            throw new RuntimeException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$uncheck$5732f3b9$1(Func1 func1, Supplier1 supplier1, Object obj) throws RuntimeException {
        try {
            return func1.call(obj);
        } catch (Exception e2) {
            if (supplier1 == null) {
                throw new RuntimeException(e2);
            }
            throw ((RuntimeException) supplier1.get(e2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$uncheck$5b7ace8e$1(VoidFunc voidFunc, Supplier1 supplier1, Object[] objArr) throws RuntimeException {
        try {
            voidFunc.call(objArr);
        } catch (Exception e2) {
            if (supplier1 != null) {
                throw ((RuntimeException) supplier1.get(e2));
            }
            throw new RuntimeException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$uncheck$6c25eb8b$1(Func func, Supplier1 supplier1, Object[] objArr) throws RuntimeException {
        try {
            return func.call(objArr);
        } catch (Exception e2) {
            if (supplier1 == null) {
                throw new RuntimeException(e2);
            }
            throw ((RuntimeException) supplier1.get(e2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$uncheck$a3c5d001$1(VoidFunc0 voidFunc0, RuntimeException runtimeException) throws RuntimeException {
        try {
            voidFunc0.call();
        } catch (Exception e2) {
            if (runtimeException == null) {
                throw new RuntimeException(e2);
            }
            runtimeException.initCause(e2);
            throw runtimeException;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$uncheck$ad313ebc$1(VoidFunc1 voidFunc1, Supplier1 supplier1, Object obj) throws RuntimeException {
        try {
            voidFunc1.call(obj);
        } catch (Exception e2) {
            if (supplier1 != null) {
                throw ((RuntimeException) supplier1.get(e2));
            }
            throw new RuntimeException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$uncheck$e9066ec4$1(Func0 func0, Supplier1 supplier1) throws RuntimeException {
        try {
            return func0.call();
        } catch (Exception e2) {
            if (supplier1 == null) {
                throw new RuntimeException(e2);
            }
            throw ((RuntimeException) supplier1.get(e2));
        }
    }

    public static <P, R> FuncRt<P, R> uncheck(Func<P, R> func) {
        return uncheck(func, new h());
    }

    public static <R> Func0Rt<R> uncheck(Func0<R> func0) {
        return uncheck(func0, new h());
    }

    public static <P, R> Func1Rt<P, R> uncheck(Func1<P, R> func1) {
        return uncheck(func1, new h());
    }

    public static <P> VoidFuncRt<P> uncheck(VoidFunc<P> voidFunc) {
        return uncheck(voidFunc, new h());
    }

    public static VoidFunc0Rt uncheck(VoidFunc0 voidFunc0) {
        return uncheck(voidFunc0, new h());
    }

    public static <P> VoidFunc1Rt<P> uncheck(VoidFunc1<P> voidFunc1) {
        return uncheck(voidFunc1, new h());
    }

    public static <P, R> FuncRt<P, R> uncheck(Func<P, R> func, Supplier1<RuntimeException, Exception> supplier1) {
        Objects.requireNonNull(func, "expression can not be null");
        return new e(func, supplier1);
    }

    public static <R> Func0Rt<R> uncheck(Func0<R> func0, Supplier1<RuntimeException, Exception> supplier1) {
        Objects.requireNonNull(func0, "expression can not be null");
        return new d(func0, supplier1);
    }

    public static <P, R> Func1Rt<P, R> uncheck(Func1<P, R> func1, Supplier1<RuntimeException, Exception> supplier1) {
        Objects.requireNonNull(func1, "expression can not be null");
        return new c(func1, supplier1);
    }

    public static <P> VoidFuncRt<P> uncheck(VoidFunc<P> voidFunc, Supplier1<RuntimeException, Exception> supplier1) {
        Objects.requireNonNull(voidFunc, "expression can not be null");
        return new f(voidFunc, supplier1);
    }

    public static VoidFunc0Rt uncheck(VoidFunc0 voidFunc0, RuntimeException runtimeException) {
        Objects.requireNonNull(voidFunc0, "expression can not be null");
        return new g(voidFunc0, runtimeException);
    }

    public static VoidFunc0Rt uncheck(VoidFunc0 voidFunc0, Supplier1<RuntimeException, Exception> supplier1) {
        Objects.requireNonNull(voidFunc0, "expression can not be null");
        return new a(voidFunc0, supplier1);
    }

    public static <P> VoidFunc1Rt<P> uncheck(VoidFunc1<P> voidFunc1, Supplier1<RuntimeException, Exception> supplier1) {
        Objects.requireNonNull(voidFunc1, "expression can not be null");
        return new b(voidFunc1, supplier1);
    }
}
