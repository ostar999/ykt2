package com.yddmi.doctor.network;

import com.blankj.utilcode.util.BusUtils;
import com.catchpig.annotation.FlowError;
import com.catchpig.mvvm.base.activity.BaseActivity;
import com.catchpig.mvvm.base.fragment.BaseFragment;
import com.catchpig.mvvm.interfaces.IFlowError;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;

@FlowError
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001c\u0010\t\u001a\u00020\u00042\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/network/MessageFlowError;", "Lcom/catchpig/mvvm/interfaces/IFlowError;", "()V", "onBaseActivityError", "", "baseActivity", "Lcom/catchpig/mvvm/base/activity/BaseActivity;", "t", "", "onBaseFragmentError", "baseFragment", "Lcom/catchpig/mvvm/base/fragment/BaseFragment;", "onError", Languages.ANY, "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class MessageFlowError implements IFlowError {
    @Override // com.catchpig.mvvm.interfaces.IFlowError
    public void onBaseActivityError(@NotNull BaseActivity<?> baseActivity, @NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(baseActivity, "baseActivity");
        Intrinsics.checkNotNullParameter(t2, "t");
    }

    @Override // com.catchpig.mvvm.interfaces.IFlowError
    public void onBaseFragmentError(@NotNull BaseFragment<?> baseFragment, @NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(baseFragment, "baseFragment");
        Intrinsics.checkNotNullParameter(t2, "t");
    }

    @Override // com.catchpig.mvvm.interfaces.IFlowError
    public void onError(@NotNull Object any, @NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(any, "any");
        Intrinsics.checkNotNullParameter(t2, "t");
        t2.printStackTrace();
        if (t2 instanceof HttpLogout401Exception) {
            BusUtils.post(GlobalAction.eventLogout401);
        }
        if (t2.getMessage() == null || (any instanceof BaseActivity)) {
            return;
        }
        boolean z2 = any instanceof BaseFragment;
    }
}
