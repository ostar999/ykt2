package com.catchpig.mvvm.network.factory;

import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.mvvm.network.converter.SerializationRequestBodyConverter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JE\u0010\u0005\u001a\u000e\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J7\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/catchpig/mvvm/network/factory/SerializationConverterFactory;", "Lretrofit2/Converter$Factory;", "serviceClassName", "", "(Ljava/lang/String;)V", "requestBodyConverter", "Lretrofit2/Converter;", "Lokhttp3/RequestBody;", "type", "Ljava/lang/reflect/Type;", "parameterAnnotations", "", "", "methodAnnotations", "retrofit", "Lretrofit2/Retrofit;", "(Ljava/lang/reflect/Type;[Ljava/lang/annotation/Annotation;[Ljava/lang/annotation/Annotation;Lretrofit2/Retrofit;)Lretrofit2/Converter;", "responseBodyConverter", "Lokhttp3/ResponseBody;", "annotations", "(Ljava/lang/reflect/Type;[Ljava/lang/annotation/Annotation;Lretrofit2/Retrofit;)Lretrofit2/Converter;", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SerializationConverterFactory extends Converter.Factory {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String serviceClassName;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/catchpig/mvvm/network/factory/SerializationConverterFactory$Companion;", "", "()V", "create", "Lcom/catchpig/mvvm/network/factory/SerializationConverterFactory;", "serviceClassName", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final SerializationConverterFactory create(@NotNull String serviceClassName) {
            Intrinsics.checkNotNullParameter(serviceClassName, "serviceClassName");
            return new SerializationConverterFactory(serviceClassName);
        }
    }

    public SerializationConverterFactory(@NotNull String serviceClassName) {
        Intrinsics.checkNotNullParameter(serviceClassName, "serviceClassName");
        this.serviceClassName = serviceClassName;
    }

    @JvmStatic
    @NotNull
    public static final SerializationConverterFactory create(@NotNull String str) {
        return INSTANCE.create(str);
    }

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<?, RequestBody> requestBodyConverter(@NotNull Type type, @NotNull Annotation[] parameterAnnotations, @NotNull Annotation[] methodAnnotations, @NotNull Retrofit retrofit) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(parameterAnnotations, "parameterAnnotations");
        Intrinsics.checkNotNullParameter(methodAnnotations, "methodAnnotations");
        Intrinsics.checkNotNullParameter(retrofit, "retrofit");
        return new SerializationRequestBodyConverter(type);
    }

    @Override // retrofit2.Converter.Factory
    @Nullable
    public Converter<ResponseBody, ?> responseBodyConverter(@NotNull Type type, @NotNull Annotation[] annotations, @NotNull Retrofit retrofit) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(retrofit, "retrofit");
        return KotlinMvvmCompiler.INSTANCE.getResponseBodyConverter(this.serviceClassName, type);
    }
}
