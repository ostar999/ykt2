package com.petterp.floatingx.listener.control;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.mobile.auth.gatewayauth.Constant;
import com.petterp.floatingx.assist.FxAnimation;
import com.petterp.floatingx.assist.FxDisplayMode;
import com.petterp.floatingx.listener.IFxConfigStorage;
import com.petterp.floatingx.listener.IFxScrollListener;
import com.petterp.floatingx.listener.IFxViewLifecycle;
import com.petterp.floatingx.util.FxAdsorbDirection;
import kotlin.Deprecated;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J(\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0006H&J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0018\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H&J\u001a\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H&J\u0012\u0010\u001a\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H'J\u0010\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020 H&J\u0010\u0010!\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\"H&¨\u0006#"}, d2 = {"Lcom/petterp/floatingx/listener/control/IFxConfigControl;", "", "clearLocationStorage", "", "setBorderMargin", "t", "", NotifyType.LIGHTS, "b", "r", "setDisplayMode", "mode", "Lcom/petterp/floatingx/assist/FxDisplayMode;", "setEdgeAdsorbDirection", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lcom/petterp/floatingx/util/FxAdsorbDirection;", "setEdgeOffset", "edgeOffset", "setEnableAnimation", Constant.API_PARAMS_KEY_ENABLE, "", "animationImpl", "Lcom/petterp/floatingx/assist/FxAnimation;", "setEnableClick", "setEnableEdgeAdsorption", "setEnableEdgeRebound", "setEnableSaveDirection", "impl", "Lcom/petterp/floatingx/listener/IFxConfigStorage;", "setEnableTouch", "setScrollListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/petterp/floatingx/listener/IFxScrollListener;", "setViewLifecycleListener", "Lcom/petterp/floatingx/listener/IFxViewLifecycle;", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public interface IFxConfigControl {

    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ void setEnableSaveDirection$default(IFxConfigControl iFxConfigControl, IFxConfigStorage iFxConfigStorage, boolean z2, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setEnableSaveDirection");
            }
            if ((i2 & 2) != 0) {
                z2 = true;
            }
            iFxConfigControl.setEnableSaveDirection(iFxConfigStorage, z2);
        }

        public static /* synthetic */ void setEnableSaveDirection$default(IFxConfigControl iFxConfigControl, boolean z2, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setEnableSaveDirection");
            }
            if ((i2 & 1) != 0) {
                z2 = true;
            }
            iFxConfigControl.setEnableSaveDirection(z2);
        }
    }

    void clearLocationStorage();

    void setBorderMargin(float t2, float l2, float b3, float r2);

    void setDisplayMode(@NotNull FxDisplayMode mode);

    void setEdgeAdsorbDirection(@NotNull FxAdsorbDirection direction);

    void setEdgeOffset(float edgeOffset);

    void setEnableAnimation(boolean isEnable);

    void setEnableAnimation(boolean isEnable, @NotNull FxAnimation animationImpl);

    void setEnableClick(boolean isEnable);

    void setEnableEdgeAdsorption(boolean isEnable);

    void setEnableEdgeRebound(boolean isEnable);

    void setEnableSaveDirection(@NotNull IFxConfigStorage impl, boolean isEnable);

    void setEnableSaveDirection(boolean isEnable);

    @Deprecated(message = "已废弃，建议使用[setDisplayMode()]")
    void setEnableTouch(boolean isEnable);

    void setScrollListener(@NotNull IFxScrollListener listener);

    void setViewLifecycleListener(@NotNull IFxViewLifecycle listener);
}
