package com.yddmi.doctor.app;

import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import kotlin.Metadata;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/app/NoViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "dataChangeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getDataChangeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class NoViewModel extends BaseViewModel {

    @NotNull
    private final MutableStateFlow<Long> dataChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    public final MutableStateFlow<Long> getDataChangeMsf() {
        return this.dataChangeMsf;
    }
}
