package com.catchpig.mvvm.ksp.interfaces;

import com.catchpig.mvvm.base.activity.BaseActivity;
import com.catchpig.mvvm.base.fragment.BaseFragment;
import com.catchpig.mvvm.interfaces.IFlowError;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.yddmi.doctor.config.MvvmGlobalConfig;
import com.yddmi.doctor.network.MessageFlowError;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\bH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/catchpig/mvvm/ksp/interfaces/Global_Compiler;", "Lcom/catchpig/mvvm/ksp/interfaces/GlobalCompiler;", "()V", "flowErrors", "Ljava/util/ArrayList;", "Lcom/catchpig/mvvm/interfaces/IFlowError;", "Lkotlin/collections/ArrayList;", "globalConfig", "Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "getGlobalConfig", "onError", "", Languages.ANY, "", "t", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nGlobal_Compiler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Global_Compiler.kt\ncom/catchpig/mvvm/ksp/interfaces/Global_Compiler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,35:1\n1855#2,2:36\n*S KotlinDebug\n*F\n+ 1 Global_Compiler.kt\ncom/catchpig/mvvm/ksp/interfaces/Global_Compiler\n*L\n25#1:36,2\n*E\n"})
/* loaded from: classes2.dex */
public final class Global_Compiler implements GlobalCompiler {

    @NotNull
    private final ArrayList<IFlowError> flowErrors;

    @NotNull
    private final IGlobalConfig globalConfig = new MvvmGlobalConfig();

    public Global_Compiler() {
        ArrayList<IFlowError> arrayList = new ArrayList<>();
        this.flowErrors = arrayList;
        arrayList.add(new MessageFlowError());
    }

    @Override // com.catchpig.mvvm.ksp.interfaces.GlobalCompiler
    @NotNull
    public IGlobalConfig getGlobalConfig() {
        return this.globalConfig;
    }

    @Override // com.catchpig.mvvm.ksp.interfaces.GlobalCompiler
    public void onError(@NotNull Object any, @NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(any, "any");
        Intrinsics.checkNotNullParameter(t2, "t");
        for (IFlowError iFlowError : this.flowErrors) {
            iFlowError.onError(any, t2);
            if (any instanceof BaseActivity) {
                iFlowError.onBaseActivityError((BaseActivity) any, t2);
            } else if (any instanceof BaseFragment) {
                iFlowError.onBaseFragmentError((BaseFragment) any, t2);
            }
        }
    }
}
