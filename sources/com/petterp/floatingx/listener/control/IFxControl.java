package com.petterp.floatingx.listener.control;

import android.view.View;
import androidx.annotation.LayoutRes;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.petterp.floatingx.listener.provider.IFxContextProvider;
import com.petterp.floatingx.listener.provider.IFxHolderProvider;
import com.petterp.floatingx.view.FxManagerView;
import com.petterp.floatingx.view.FxViewHolder;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\n\u0010\b\u001a\u0004\u0018\u00010\tH&J\n\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\n\u0010\f\u001a\u0004\u0018\u00010\rH&J\b\u0010\u000e\u001a\u00020\u0007H&J\b\u0010\u000f\u001a\u00020\u0010H&J\u0018\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H&J \u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0010H&J\u0018\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H&J \u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0010H&J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0019H&J\u001a\u0010\u0017\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0010\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u000bH&J\u0010\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001fH&J\u0012\u0010\u001c\u001a\u00020\u00072\b\b\u0001\u0010 \u001a\u00020!H&J\u0010\u0010\"\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020#H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006$"}, d2 = {"Lcom/petterp/floatingx/listener/control/IFxControl;", "", "configControl", "Lcom/petterp/floatingx/listener/control/IFxConfigControl;", "getConfigControl", "()Lcom/petterp/floatingx/listener/control/IFxConfigControl;", "cancel", "", "getManagerView", "Lcom/petterp/floatingx/view/FxManagerView;", "getView", "Landroid/view/View;", "getViewHolder", "Lcom/petterp/floatingx/view/FxViewHolder;", "hide", "isShow", "", "move", "x", "", "y", "useAnimation", "moveByVector", "setClickListener", "clickListener", "Landroid/view/View$OnClickListener;", CrashHianalyticsData.TIME, "", "updateView", "view", d.M, "Lcom/petterp/floatingx/listener/provider/IFxContextProvider;", "resource", "", "updateViewContent", "Lcom/petterp/floatingx/listener/provider/IFxHolderProvider;", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public interface IFxControl {

    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ void setClickListener$default(IFxControl iFxControl, long j2, View.OnClickListener onClickListener, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setClickListener");
            }
            if ((i2 & 1) != 0) {
                j2 = 300;
            }
            iFxControl.setClickListener(j2, onClickListener);
        }
    }

    void cancel();

    @NotNull
    IFxConfigControl getConfigControl();

    @Nullable
    FxManagerView getManagerView();

    @Nullable
    View getView();

    @Nullable
    FxViewHolder getViewHolder();

    void hide();

    boolean isShow();

    void move(float x2, float y2);

    void move(float x2, float y2, boolean useAnimation);

    void moveByVector(float x2, float y2);

    void moveByVector(float x2, float y2, boolean useAnimation);

    void setClickListener(long time, @NotNull View.OnClickListener clickListener);

    void setClickListener(@NotNull View.OnClickListener clickListener);

    void updateView(@LayoutRes int resource);

    void updateView(@NotNull View view);

    void updateView(@NotNull IFxContextProvider provider);

    void updateViewContent(@NotNull IFxHolderProvider provider);
}
