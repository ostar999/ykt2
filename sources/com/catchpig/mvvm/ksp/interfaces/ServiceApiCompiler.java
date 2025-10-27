package com.catchpig.mvvm.ksp.interfaces;

import com.catchpig.mvvm.entity.ServiceParam;
import com.vivo.push.PushClientConstants;
import java.lang.reflect.Type;
import kotlin.Metadata;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Converter;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J&\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u000b"}, d2 = {"Lcom/catchpig/mvvm/ksp/interfaces/ServiceApiCompiler;", "", "getResponseBodyConverter", "Lretrofit2/Converter;", "Lokhttp3/ResponseBody;", PushClientConstants.TAG_CLASS_NAME, "", "type", "Ljava/lang/reflect/Type;", "getServiceParam", "Lcom/catchpig/mvvm/entity/ServiceParam;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface ServiceApiCompiler {
    @Nullable
    Converter<ResponseBody, Object> getResponseBodyConverter(@NotNull String className, @NotNull Type type);

    @NotNull
    ServiceParam getServiceParam(@NotNull String className);
}
