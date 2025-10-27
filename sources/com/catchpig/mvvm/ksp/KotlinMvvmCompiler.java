package com.catchpig.mvvm.ksp;

import android.app.Activity;
import com.catchpig.mvvm.entity.ServiceParam;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.catchpig.mvvm.ksp.interfaces.ActivityCompiler;
import com.catchpig.mvvm.ksp.interfaces.GlobalCompiler;
import com.catchpig.mvvm.ksp.interfaces.ServiceApiCompiler;
import com.catchpig.utils.ext.LogExtKt;
import com.vivo.push.PushClientConstants;
import java.lang.reflect.Type;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ResponseBody;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Converter;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00112\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0004J\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0016\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000e¨\u0006\""}, d2 = {"Lcom/catchpig/mvvm/ksp/KotlinMvvmCompiler;", "", "()V", "TAG", "", "globalCompiler", "Lcom/catchpig/mvvm/ksp/interfaces/GlobalCompiler;", "getGlobalCompiler", "()Lcom/catchpig/mvvm/ksp/interfaces/GlobalCompiler;", "globalCompiler$delegate", "Lkotlin/Lazy;", "serviceApiCompiler", "Lcom/catchpig/mvvm/ksp/interfaces/ServiceApiCompiler;", "getServiceApiCompiler", "()Lcom/catchpig/mvvm/ksp/interfaces/ServiceApiCompiler;", "serviceApiCompiler$delegate", "getResponseBodyConverter", "Lretrofit2/Converter;", "Lokhttp3/ResponseBody;", PushClientConstants.TAG_CLASS_NAME, "type", "Ljava/lang/reflect/Type;", "getServiceParam", "Lcom/catchpig/mvvm/entity/ServiceParam;", "globalConfig", "Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "inject", "", "baseActivity", "Landroid/app/Activity;", "onError", Languages.ANY, "t", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class KotlinMvvmCompiler {

    @NotNull
    private static final String TAG = "KotlinMvvmCompiler";

    @NotNull
    public static final KotlinMvvmCompiler INSTANCE = new KotlinMvvmCompiler();

    /* renamed from: globalCompiler$delegate, reason: from kotlin metadata */
    @NotNull
    private static final Lazy globalCompiler = LazyKt__LazyJVMKt.lazy(new Function0<GlobalCompiler>() { // from class: com.catchpig.mvvm.ksp.KotlinMvvmCompiler$globalCompiler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final GlobalCompiler invoke() throws IllegalAccessException, InstantiationException {
            Object objNewInstance = Class.forName(GlobalCompiler.class.getPackage().getName() + ".Global_Compiler").newInstance();
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type com.catchpig.mvvm.ksp.interfaces.GlobalCompiler");
            return (GlobalCompiler) objNewInstance;
        }
    });

    /* renamed from: serviceApiCompiler$delegate, reason: from kotlin metadata */
    @NotNull
    private static final Lazy serviceApiCompiler = LazyKt__LazyJVMKt.lazy(new Function0<ServiceApiCompiler>() { // from class: com.catchpig.mvvm.ksp.KotlinMvvmCompiler$serviceApiCompiler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final ServiceApiCompiler invoke() throws IllegalAccessException, InstantiationException {
            Object objNewInstance = Class.forName(ServiceApiCompiler.class.getPackage().getName() + ".ServiceApi_Compiler").newInstance();
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type com.catchpig.mvvm.ksp.interfaces.ServiceApiCompiler");
            return (ServiceApiCompiler) objNewInstance;
        }
    });

    private KotlinMvvmCompiler() {
    }

    private final GlobalCompiler getGlobalCompiler() {
        return (GlobalCompiler) globalCompiler.getValue();
    }

    private final ServiceApiCompiler getServiceApiCompiler() {
        return (ServiceApiCompiler) serviceApiCompiler.getValue();
    }

    @Nullable
    public final Converter<ResponseBody, Object> getResponseBodyConverter(@NotNull String className, @NotNull Type type) {
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(type, "type");
        return getServiceApiCompiler().getResponseBodyConverter(className, type);
    }

    @NotNull
    public final ServiceParam getServiceParam(@NotNull String className) {
        Intrinsics.checkNotNullParameter(className, "className");
        return getServiceApiCompiler().getServiceParam(className);
    }

    @NotNull
    public final IGlobalConfig globalConfig() {
        return getGlobalCompiler().getGlobalConfig();
    }

    public final void inject(@NotNull Activity baseActivity) throws IllegalAccessException, InstantiationException {
        Intrinsics.checkNotNullParameter(baseActivity, "baseActivity");
        String name = baseActivity.getClass().getName();
        try {
            Object objNewInstance = Class.forName(name + "_Compiler").newInstance();
            Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type com.catchpig.mvvm.ksp.interfaces.ActivityCompiler");
            ((ActivityCompiler) objNewInstance).inject(baseActivity);
        } catch (ClassNotFoundException unused) {
            LogExtKt.logd(name + ":没有被(com.catchpig.annotation)下编译时注解修饰", TAG);
        }
    }

    public final void onError(@NotNull Object any, @NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(any, "any");
        Intrinsics.checkNotNullParameter(t2, "t");
        getGlobalCompiler().onError(any, t2);
    }
}
