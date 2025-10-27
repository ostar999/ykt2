package com.alipay.android.phone.mrpc.core;

import android.os.Looper;
import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.ResetCookie;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public final class z {

    /* renamed from: a, reason: collision with root package name */
    private static final ThreadLocal<Object> f3012a = new ThreadLocal<>();

    /* renamed from: b, reason: collision with root package name */
    private static final ThreadLocal<Map<String, Object>> f3013b = new ThreadLocal<>();

    /* renamed from: c, reason: collision with root package name */
    private byte f3014c = 0;

    /* renamed from: d, reason: collision with root package name */
    private AtomicInteger f3015d = new AtomicInteger();

    /* renamed from: e, reason: collision with root package name */
    private x f3016e;

    public z(x xVar) {
        this.f3016e = xVar;
    }

    public final Object a(Method method, Object[] objArr) {
        if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalThreadStateException("can't in main thread call rpc .");
        }
        OperationType operationType = (OperationType) method.getAnnotation(OperationType.class);
        boolean z2 = method.getAnnotation(ResetCookie.class) != null;
        Type genericReturnType = method.getGenericReturnType();
        method.getAnnotations();
        ThreadLocal<Object> threadLocal = f3012a;
        threadLocal.set(null);
        ThreadLocal<Map<String, Object>> threadLocal2 = f3013b;
        threadLocal2.set(null);
        if (operationType == null) {
            throw new IllegalStateException("OperationType must be set.");
        }
        String strValue = operationType.value();
        int iIncrementAndGet = this.f3015d.incrementAndGet();
        try {
            if (this.f3014c == 0) {
                com.alipay.android.phone.mrpc.core.a.e eVar = new com.alipay.android.phone.mrpc.core.a.e(iIncrementAndGet, strValue, objArr);
                if (threadLocal2.get() != null) {
                    eVar.a(threadLocal2.get());
                }
                byte[] bArr = (byte[]) new j(this.f3016e.a(), method, iIncrementAndGet, strValue, eVar.a(), z2).a();
                threadLocal2.set(null);
                Object objA = new com.alipay.android.phone.mrpc.core.a.d(genericReturnType, bArr).a();
                if (genericReturnType != Void.TYPE) {
                    threadLocal.set(objA);
                }
            }
            return threadLocal.get();
        } catch (RpcException e2) {
            e2.setOperationType(strValue);
            throw e2;
        }
    }
}
