package com.catchpig.mvvm.entity;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import z.a;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J\t\u0010\u001d\u001a\u00020\fHÆ\u0003JQ\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001J\u0013\u0010\u001f\u001a\u00020\f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\"HÖ\u0001J\t\u0010#\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006$"}, d2 = {"Lcom/catchpig/mvvm/entity/ServiceParam;", "", "baseUrl", "", "connectTimeout", "", "readTimeout", "interceptors", "", "Lokhttp3/Interceptor;", "debugInterceptors", "rxJava", "", "(Ljava/lang/String;JJLjava/util/List;Ljava/util/List;Z)V", "getBaseUrl", "()Ljava/lang/String;", "getConnectTimeout", "()J", "getDebugInterceptors", "()Ljava/util/List;", "getInterceptors", "getReadTimeout", "getRxJava", "()Z", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class ServiceParam {

    @NotNull
    private final String baseUrl;
    private final long connectTimeout;

    @NotNull
    private final List<Interceptor> debugInterceptors;

    @NotNull
    private final List<Interceptor> interceptors;
    private final long readTimeout;
    private final boolean rxJava;

    public ServiceParam(@NotNull String baseUrl, long j2, long j3, @NotNull List<Interceptor> interceptors, @NotNull List<Interceptor> debugInterceptors, boolean z2) {
        Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
        Intrinsics.checkNotNullParameter(interceptors, "interceptors");
        Intrinsics.checkNotNullParameter(debugInterceptors, "debugInterceptors");
        this.baseUrl = baseUrl;
        this.connectTimeout = j2;
        this.readTimeout = j3;
        this.interceptors = interceptors;
        this.debugInterceptors = debugInterceptors;
        this.rxJava = z2;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getBaseUrl() {
        return this.baseUrl;
    }

    /* renamed from: component2, reason: from getter */
    public final long getConnectTimeout() {
        return this.connectTimeout;
    }

    /* renamed from: component3, reason: from getter */
    public final long getReadTimeout() {
        return this.readTimeout;
    }

    @NotNull
    public final List<Interceptor> component4() {
        return this.interceptors;
    }

    @NotNull
    public final List<Interceptor> component5() {
        return this.debugInterceptors;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getRxJava() {
        return this.rxJava;
    }

    @NotNull
    public final ServiceParam copy(@NotNull String baseUrl, long connectTimeout, long readTimeout, @NotNull List<Interceptor> interceptors, @NotNull List<Interceptor> debugInterceptors, boolean rxJava) {
        Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
        Intrinsics.checkNotNullParameter(interceptors, "interceptors");
        Intrinsics.checkNotNullParameter(debugInterceptors, "debugInterceptors");
        return new ServiceParam(baseUrl, connectTimeout, readTimeout, interceptors, debugInterceptors, rxJava);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ServiceParam)) {
            return false;
        }
        ServiceParam serviceParam = (ServiceParam) other;
        return Intrinsics.areEqual(this.baseUrl, serviceParam.baseUrl) && this.connectTimeout == serviceParam.connectTimeout && this.readTimeout == serviceParam.readTimeout && Intrinsics.areEqual(this.interceptors, serviceParam.interceptors) && Intrinsics.areEqual(this.debugInterceptors, serviceParam.debugInterceptors) && this.rxJava == serviceParam.rxJava;
    }

    @NotNull
    public final String getBaseUrl() {
        return this.baseUrl;
    }

    public final long getConnectTimeout() {
        return this.connectTimeout;
    }

    @NotNull
    public final List<Interceptor> getDebugInterceptors() {
        return this.debugInterceptors;
    }

    @NotNull
    public final List<Interceptor> getInterceptors() {
        return this.interceptors;
    }

    public final long getReadTimeout() {
        return this.readTimeout;
    }

    public final boolean getRxJava() {
        return this.rxJava;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((this.baseUrl.hashCode() * 31) + a.a(this.connectTimeout)) * 31) + a.a(this.readTimeout)) * 31) + this.interceptors.hashCode()) * 31) + this.debugInterceptors.hashCode()) * 31;
        boolean z2 = this.rxJava;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    @NotNull
    public String toString() {
        return "ServiceParam(baseUrl=" + this.baseUrl + ", connectTimeout=" + this.connectTimeout + ", readTimeout=" + this.readTimeout + ", interceptors=" + this.interceptors + ", debugInterceptors=" + this.debugInterceptors + ", rxJava=" + this.rxJava + ')';
    }
}
