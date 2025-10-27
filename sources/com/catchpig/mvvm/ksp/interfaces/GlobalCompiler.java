package com.catchpig.mvvm.ksp.interfaces;

import com.catchpig.mvvm.interfaces.IGlobalConfig;
import kotlin.Metadata;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lcom/catchpig/mvvm/ksp/interfaces/GlobalCompiler;", "", "getGlobalConfig", "Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "onError", "", Languages.ANY, "t", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface GlobalCompiler {
    @NotNull
    IGlobalConfig getGlobalConfig();

    void onError(@NotNull Object any, @NotNull Throwable t2);
}
