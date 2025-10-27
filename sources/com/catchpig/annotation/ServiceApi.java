package com.catchpig.annotation;

import com.catchpig.annotation.interfaces.SerializationConverter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import okhttp3.Interceptor;

@Target({ElementType.TYPE})
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0087\u0002\u0018\u00002\u00020\u0001Bn\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u0016\b\u0002\u0010\n\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\f0\u00050\u000b\u0012\u0016\b\u0002\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\f0\u00050\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000fR\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0010R\u000f\u0010\u0007\u001a\u00020\b¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0011R\u001d\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\f0\u00050\u000b¢\u0006\u0006\u001a\u0004\b\r\u0010\u0012R\u001d\u0010\n\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\f0\u00050\u000b¢\u0006\u0006\u001a\u0004\b\n\u0010\u0012R\u000f\u0010\t\u001a\u00020\b¢\u0006\u0006\u001a\u0004\b\t\u0010\u0011R\u001f\u0010\u0004\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00060\u0005¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0013R\u000f\u0010\u000e\u001a\u00020\u000f¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0014¨\u0006\u0015"}, d2 = {"Lcom/catchpig/annotation/ServiceApi;", "", "baseUrl", "", "responseConverter", "Lkotlin/reflect/KClass;", "Lcom/catchpig/annotation/interfaces/SerializationConverter;", "connectTimeout", "", "readTimeout", "interceptors", "", "Lokhttp3/Interceptor;", "debugInterceptors", "rxJava", "", "()Ljava/lang/String;", "()J", "()[Ljava/lang/Class;", "()Ljava/lang/Class;", "()Z", "annotation"}, k = 1, mv = {1, 8, 0}, xi = 48)
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.CLASS})
@Retention(RetentionPolicy.RUNTIME)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
/* loaded from: classes2.dex */
public @interface ServiceApi {
    String baseUrl();

    long connectTimeout() default 5000;

    Class<? extends Interceptor>[] debugInterceptors() default {};

    Class<? extends Interceptor>[] interceptors() default {};

    long readTimeout() default 5000;

    Class<? extends SerializationConverter<?, ?>> responseConverter();

    boolean rxJava() default false;
}
