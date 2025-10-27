package com.petterp.floatingx.impl.control;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.mobile.auth.gatewayauth.Constant;
import com.petterp.floatingx.assist.FxAnimation;
import com.petterp.floatingx.assist.FxBorderMargin;
import com.petterp.floatingx.assist.FxDisplayMode;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.listener.IFxConfigStorage;
import com.petterp.floatingx.listener.IFxScrollListener;
import com.petterp.floatingx.listener.IFxViewLifecycle;
import com.petterp.floatingx.listener.control.IFxConfigControl;
import com.petterp.floatingx.listener.control.IFxControl;
import com.petterp.floatingx.listener.provider.IFxContextProvider;
import com.petterp.floatingx.listener.provider.IFxHolderProvider;
import com.petterp.floatingx.util.FxAdsorbDirection;
import com.petterp.floatingx.util.FxLog;
import com.petterp.floatingx.view.FxManagerView;
import com.petterp.floatingx.view.FxViewHolder;
import com.umeng.analytics.pro.d;
import java.lang.ref.WeakReference;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0002J\b\u0010\u001e\u001a\u00020\u001aH\u0016J\b\u0010\u001f\u001a\u00020\u001aH\u0004J\b\u0010 \u001a\u00020\u001aH\u0016J\n\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\b\u0010#\u001a\u00020\u001aH\u0004J\u0012\u0010#\u001a\u00020\u001a2\b\u0010$\u001a\u0004\u0018\u00010\u0014H\u0014J\n\u0010%\u001a\u0004\u0018\u00010\u0014H\u0004J\n\u0010&\u001a\u0004\u0018\u00010\u0016H\u0016J\n\u0010'\u001a\u0004\u0018\u00010\u0016H\u0004J\n\u0010(\u001a\u0004\u0018\u00010)H\u0016J\n\u0010*\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010+\u001a\u00020\u001aH\u0016J\b\u0010,\u001a\u00020\u001aH\u0014J\b\u0010-\u001a\u00020\u001aH\u0004J\b\u0010.\u001a\u00020/H\u0016J\u0018\u00100\u001a\u00020\u001a2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000202H\u0016J \u00100\u001a\u00020\u001a2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u00020/H\u0016J\u0018\u00105\u001a\u00020\u001a2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000202H\u0016J \u00105\u001a\u00020\u001a2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u00020/H\u0016J\b\u00106\u001a\u00020\u001aH\u0014J(\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u0002022\u0006\u00109\u001a\u0002022\u0006\u0010:\u001a\u0002022\u0006\u0010;\u001a\u000202H\u0016J\u0010\u0010<\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020>H\u0016J\u0018\u0010<\u001a\u00020\u001a2\u0006\u0010?\u001a\u00020\u001c2\u0006\u0010=\u001a\u00020>H\u0016J\u0015\u0010@\u001a\u00020\u001a2\u0006\u0010A\u001a\u00020\u0014H\u0000¢\u0006\u0002\bBJ\u0010\u0010C\u001a\u00020\u001a2\u0006\u0010D\u001a\u00020EH\u0016J\u0010\u0010F\u001a\u00020\u001a2\u0006\u0010G\u001a\u00020HH\u0016J\u0010\u0010I\u001a\u00020\u001a2\u0006\u0010J\u001a\u000202H\u0016J\u0010\u0010K\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020/H\u0016J\u0018\u0010K\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020/2\u0006\u0010M\u001a\u00020NH\u0016J\u0010\u0010O\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020/H\u0016J\u0010\u0010P\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020/H\u0016J\u0010\u0010Q\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020/H\u0016J\u0018\u0010R\u001a\u00020\u001a2\u0006\u0010S\u001a\u00020T2\u0006\u0010L\u001a\u00020/H\u0016J\u0010\u0010R\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020/H\u0016J\u0010\u0010U\u001a\u00020\u001a2\u0006\u0010L\u001a\u00020/H\u0016J\u0010\u0010V\u001a\u00020\u001a2\u0006\u0010W\u001a\u00020XH\u0016J\u0010\u0010Y\u001a\u00020\u001a2\u0006\u0010W\u001a\u00020ZH\u0016J\u0010\u0010[\u001a\u00020\u001a2\u0006\u0010\\\u001a\u00020/H\u0004J\u0012\u0010]\u001a\u00020\u001a2\b\b\u0003\u0010^\u001a\u00020_H\u0014J\u0010\u0010`\u001a\u00020\u001a2\u0006\u0010a\u001a\u00020)H\u0016J\u0010\u0010`\u001a\u00020\u001a2\u0006\u0010b\u001a\u00020cH\u0016J\u0012\u0010`\u001a\u00020\u001a2\b\b\u0001\u0010d\u001a\u00020_H\u0016J\u0010\u0010e\u001a\u00020\u001a2\u0006\u0010b\u001a\u00020fH\u0016J\f\u0010g\u001a\u00020\u001a*\u00020\u0016H\u0004R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0014\u0010\f\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000b\u001a\u0004\b\u0010\u0010\tR\u0016\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006h"}, d2 = {"Lcom/petterp/floatingx/impl/control/FxBasisControlImpl;", "Lcom/petterp/floatingx/listener/control/IFxControl;", "Lcom/petterp/floatingx/listener/control/IFxConfigControl;", "helper", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "(Lcom/petterp/floatingx/assist/helper/BasisHelper;)V", "cancelAnimationRunnable", "Ljava/lang/Runnable;", "getCancelAnimationRunnable", "()Ljava/lang/Runnable;", "cancelAnimationRunnable$delegate", "Lkotlin/Lazy;", "configControl", "getConfigControl", "()Lcom/petterp/floatingx/listener/control/IFxConfigControl;", "hideAnimationRunnable", "getHideAnimationRunnable", "hideAnimationRunnable$delegate", "mContainer", "Ljava/lang/ref/WeakReference;", "Landroid/view/ViewGroup;", "managerView", "Lcom/petterp/floatingx/view/FxManagerView;", "viewHolder", "Lcom/petterp/floatingx/view/FxViewHolder;", "animatorCallback", "", "long", "", "runnable", "cancel", "clearContainer", "clearLocationStorage", d.R, "Landroid/content/Context;", "detach", TtmlNode.RUBY_CONTAINER, "getContainerGroup", "getManagerView", "getOrInitManagerView", "getView", "Landroid/view/View;", "getViewHolder", "hide", "initManager", "initManagerView", "isShow", "", "move", "x", "", "y", "useAnimation", "moveByVector", "reset", "setBorderMargin", "t", NotifyType.LIGHTS, "b", "r", "setClickListener", "clickListener", "Landroid/view/View$OnClickListener;", CrashHianalyticsData.TIME, "setContainerGroup", "viewGroup", "setContainerGroup$floatingx_release", "setDisplayMode", "mode", "Lcom/petterp/floatingx/assist/FxDisplayMode;", "setEdgeAdsorbDirection", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lcom/petterp/floatingx/util/FxAdsorbDirection;", "setEdgeOffset", "edgeOffset", "setEnableAnimation", Constant.API_PARAMS_KEY_ENABLE, "animationImpl", "Lcom/petterp/floatingx/assist/FxAnimation;", "setEnableClick", "setEnableEdgeAdsorption", "setEnableEdgeRebound", "setEnableSaveDirection", "impl", "Lcom/petterp/floatingx/listener/IFxConfigStorage;", "setEnableTouch", "setScrollListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/petterp/floatingx/listener/IFxScrollListener;", "setViewLifecycleListener", "Lcom/petterp/floatingx/listener/IFxViewLifecycle;", "updateEnableStatus", "newStatus", "updateMangerView", TtmlNode.TAG_LAYOUT, "", "updateView", "view", d.M, "Lcom/petterp/floatingx/listener/provider/IFxContextProvider;", "resource", "updateViewContent", "Lcom/petterp/floatingx/listener/provider/IFxHolderProvider;", "show", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public class FxBasisControlImpl implements IFxControl, IFxConfigControl {

    /* renamed from: cancelAnimationRunnable$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy cancelAnimationRunnable;

    @NotNull
    private final BasisHelper helper;

    /* renamed from: hideAnimationRunnable$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy hideAnimationRunnable;

    @Nullable
    private WeakReference<ViewGroup> mContainer;

    @Nullable
    private FxManagerView managerView;

    @Nullable
    private FxViewHolder viewHolder;

    public FxBasisControlImpl(@NotNull BasisHelper helper) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        this.helper = helper;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        this.cancelAnimationRunnable = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<Runnable>() { // from class: com.petterp.floatingx.impl.control.FxBasisControlImpl$special$$inlined$lazyLoad$default$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Runnable invoke() {
                final FxBasisControlImpl fxBasisControlImpl = this.this$0;
                return new Runnable() { // from class: com.petterp.floatingx.impl.control.FxBasisControlImpl$cancelAnimationRunnable$2$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        fxBasisControlImpl.reset();
                    }
                };
            }
        });
        this.hideAnimationRunnable = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<Runnable>() { // from class: com.petterp.floatingx.impl.control.FxBasisControlImpl$special$$inlined$lazyLoad$default$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Runnable invoke() {
                final FxBasisControlImpl fxBasisControlImpl = this.this$0;
                return new Runnable() { // from class: com.petterp.floatingx.impl.control.FxBasisControlImpl$hideAnimationRunnable$2$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        fxBasisControlImpl.detach();
                    }
                };
            }
        });
    }

    private final void animatorCallback(long j2, Runnable runnable) {
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.removeCallbacks(runnable);
        fxManagerView.postDelayed(runnable, j2);
    }

    private final Runnable getCancelAnimationRunnable() {
        return (Runnable) this.cancelAnimationRunnable.getValue();
    }

    private final Runnable getHideAnimationRunnable() {
        return (Runnable) this.hideAnimationRunnable.getValue();
    }

    public static /* synthetic */ void updateMangerView$default(FxBasisControlImpl fxBasisControlImpl, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateMangerView");
        }
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        fxBasisControlImpl.updateMangerView(i2);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void cancel() {
        if (this.managerView == null && this.viewHolder == null) {
            return;
        }
        if (isShow()) {
            BasisHelper basisHelper = this.helper;
            if (basisHelper.enableAnimation && basisHelper.fxAnimation != null) {
                FxManagerView fxManagerView = this.managerView;
                if (fxManagerView != null) {
                    fxManagerView.removeCallbacks(getCancelAnimationRunnable());
                }
                FxAnimation fxAnimation = this.helper.fxAnimation;
                Intrinsics.checkNotNull(fxAnimation);
                animatorCallback(fxAnimation.toEndAnimator$floatingx_release(this.managerView), getCancelAnimationRunnable());
                return;
            }
        }
        reset();
    }

    public final void clearContainer() {
        WeakReference<ViewGroup> weakReference = this.mContainer;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.mContainer = null;
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void clearLocationStorage() {
        IFxConfigStorage iFxConfigStorage = this.helper.iFxConfigStorage;
        if (iFxConfigStorage == null) {
            return;
        }
        iFxConfigStorage.clear();
    }

    @Nullable
    public Context context() {
        FxLog fxLog;
        ViewGroup viewGroup;
        WeakReference<ViewGroup> weakReference = this.mContainer;
        Context context = null;
        if (weakReference != null && (viewGroup = weakReference.get()) != null) {
            context = viewGroup.getContext();
        }
        if (context == null && (fxLog = this.helper.fxLog) != null) {
            fxLog.e("context = null,check your rule!");
        }
        return context;
    }

    public void detach(@Nullable ViewGroup container) {
        if (this.managerView == null || container == null) {
            return;
        }
        FxLog fxLog = this.helper.fxLog;
        if (fxLog != null) {
            fxLog.d("fxView-lifecycle-> code->removeView");
        }
        IFxViewLifecycle iFxViewLifecycle = this.helper.iFxViewLifecycle;
        if (iFxViewLifecycle != null) {
            iFxViewLifecycle.postDetached();
        }
        container.removeView(this.managerView);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    @NotNull
    public IFxConfigControl getConfigControl() {
        return this;
    }

    @Nullable
    public final ViewGroup getContainerGroup() {
        WeakReference<ViewGroup> weakReference = this.mContainer;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    @Nullable
    public FxManagerView getManagerView() {
        return this.managerView;
    }

    @Nullable
    public final FxManagerView getOrInitManagerView() {
        if (this.managerView == null) {
            initManagerView();
        }
        return this.managerView;
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    @Nullable
    public View getView() {
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return null;
        }
        return fxManagerView.get_childFxView();
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    @Nullable
    public FxViewHolder getViewHolder() {
        return this.viewHolder;
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void hide() {
        FxAnimation fxAnimation;
        if (isShow()) {
            updateEnableStatus(false);
            BasisHelper basisHelper = this.helper;
            if (!basisHelper.enableAnimation || (fxAnimation = basisHelper.fxAnimation) == null) {
                detach();
                return;
            }
            Intrinsics.checkNotNull(fxAnimation);
            if (fxAnimation.endJobIsRunning$floatingx_release()) {
                FxLog fxLog = this.helper.fxLog;
                if (fxLog == null) {
                    return;
                }
                fxLog.d("fxView->Animation ,endAnimation Executing, cancel this operation!");
                return;
            }
            FxLog fxLog2 = this.helper.fxLog;
            if (fxLog2 != null) {
                fxLog2.d("fxView->Animation ,endAnimation Running");
            }
            FxManagerView fxManagerView = this.managerView;
            if (fxManagerView != null) {
                fxManagerView.removeCallbacks(getHideAnimationRunnable());
            }
            FxAnimation fxAnimation2 = this.helper.fxAnimation;
            Intrinsics.checkNotNull(fxAnimation2);
            animatorCallback(fxAnimation2.toEndAnimator$floatingx_release(this.managerView), getHideAnimationRunnable());
        }
    }

    public void initManager() {
        Context context = context();
        if (context == null) {
            return;
        }
        FxManagerView fxManagerViewInit$floatingx_release = new FxManagerView(context, 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0).init$floatingx_release(this.helper);
        this.managerView = fxManagerViewInit$floatingx_release;
        View view = fxManagerViewInit$floatingx_release != null ? fxManagerViewInit$floatingx_release.get_childFxView() : null;
        if (view == null) {
            return;
        }
        this.viewHolder = new FxViewHolder(view);
        IFxViewLifecycle iFxViewLifecycle = this.helper.iFxViewLifecycle;
        if (iFxViewLifecycle == null) {
            return;
        }
        iFxViewLifecycle.initView(view);
        FxViewHolder fxViewHolder = this.viewHolder;
        Intrinsics.checkNotNull(fxViewHolder);
        iFxViewLifecycle.initView(fxViewHolder);
    }

    public final void initManagerView() {
        BasisHelper basisHelper = this.helper;
        if (basisHelper.layoutId == 0 && basisHelper.layoutView == null) {
            throw new RuntimeException("The layout id cannot be 0 ,and layoutView==null");
        }
        ViewGroup containerGroup = getContainerGroup();
        if (containerGroup != null) {
            containerGroup.removeView(this.managerView);
        }
        initManager();
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public boolean isShow() {
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView != null) {
            Intrinsics.checkNotNull(fxManagerView);
            if (ViewCompat.isAttachedToWindow(fxManagerView)) {
                FxManagerView fxManagerView2 = this.managerView;
                Intrinsics.checkNotNull(fxManagerView2);
                if (fxManagerView2.getVisibility() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void move(float x2, float y2) {
        move(x2, y2, true);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void moveByVector(float x2, float y2) {
        moveByVector(x2, y2, true);
    }

    public /* synthetic */ void reset() {
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView != null) {
            fxManagerView.removeCallbacks(getHideAnimationRunnable());
        }
        FxManagerView fxManagerView2 = this.managerView;
        if (fxManagerView2 != null) {
            fxManagerView2.removeCallbacks(getCancelAnimationRunnable());
        }
        WeakReference<ViewGroup> weakReference = this.mContainer;
        detach(weakReference == null ? null : weakReference.get());
        this.managerView = null;
        this.viewHolder = null;
        this.helper.clear$floatingx_release();
        clearContainer();
        FxLog fxLog = this.helper.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.d("fxView-lifecycle-> code->cancelFx");
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setBorderMargin(float t2, float l2, float b3, float r2) {
        FxBorderMargin fxBorderMargin = this.helper.fxBorderMargin;
        fxBorderMargin.setT(t2);
        fxBorderMargin.setL(l2);
        fxBorderMargin.setB(b3);
        fxBorderMargin.setR(r2);
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.moveToEdge$floatingx_release();
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void setClickListener(long time, @NotNull View.OnClickListener clickListener) {
        Intrinsics.checkNotNullParameter(clickListener, "clickListener");
        BasisHelper basisHelper = this.helper;
        basisHelper.clickTime = time;
        basisHelper.enableClickListener = true;
        basisHelper.iFxClickListener = clickListener;
    }

    public final void setContainerGroup$floatingx_release(@NotNull ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        this.mContainer = new WeakReference<>(viewGroup);
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setDisplayMode(@NotNull FxDisplayMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.helper.displayMode = mode;
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.updateDisplayMode$floatingx_release();
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEdgeAdsorbDirection(@NotNull FxAdsorbDirection direction) {
        Intrinsics.checkNotNullParameter(direction, "direction");
        this.helper.adsorbDirection = direction;
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.moveToEdge$floatingx_release();
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEdgeOffset(float edgeOffset) {
        this.helper.edgeOffset = edgeOffset;
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.moveToEdge$floatingx_release();
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableAnimation(boolean isEnable, @NotNull FxAnimation animationImpl) {
        Intrinsics.checkNotNullParameter(animationImpl, "animationImpl");
        BasisHelper basisHelper = this.helper;
        basisHelper.enableAnimation = isEnable;
        basisHelper.fxAnimation = animationImpl;
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableClick(boolean isEnable) {
        this.helper.enableClickListener = isEnable;
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableEdgeAdsorption(boolean isEnable) {
        this.helper.enableEdgeAdsorption = isEnable;
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.moveToEdge$floatingx_release();
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableEdgeRebound(boolean isEnable) {
        this.helper.enableEdgeRebound = isEnable;
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.moveToEdge$floatingx_release();
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableSaveDirection(@NotNull IFxConfigStorage impl, boolean isEnable) {
        Intrinsics.checkNotNullParameter(impl, "impl");
        BasisHelper basisHelper = this.helper;
        basisHelper.iFxConfigStorage = impl;
        basisHelper.enableSaveDirection = isEnable;
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableTouch(boolean isEnable) {
        setDisplayMode(isEnable ? FxDisplayMode.Normal : FxDisplayMode.ClickOnly);
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setScrollListener(@NotNull IFxScrollListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.helper.iFxScrollListener = listener;
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setViewLifecycleListener(@NotNull IFxViewLifecycle listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.helper.iFxViewLifecycle = listener;
    }

    public final /* synthetic */ void show(FxManagerView fxManagerView) {
        Intrinsics.checkNotNullParameter(fxManagerView, "<this>");
        this.helper.enableFx = true;
        fxManagerView.setVisibility(0);
        BasisHelper basisHelper = this.helper;
        FxAnimation fxAnimation = basisHelper.fxAnimation;
        if (fxAnimation != null && basisHelper.enableAnimation) {
            if (fxAnimation.fromJobIsRunning$floatingx_release()) {
                FxLog fxLog = this.helper.fxLog;
                if (fxLog == null) {
                    return;
                }
                fxLog.d("fxView->Animation ,startAnimation Executing, cancel this operation!");
                return;
            }
            FxLog fxLog2 = this.helper.fxLog;
            if (fxLog2 != null) {
                fxLog2.d("fxView->Animation ,startAnimation Executing, cancel this operation.");
            }
            fxAnimation.fromStartAnimator$floatingx_release(fxManagerView);
        }
    }

    public final void updateEnableStatus(boolean newStatus) {
        BasisHelper basisHelper = this.helper;
        if (basisHelper.enableFx == newStatus) {
            return;
        }
        basisHelper.enableFx = newStatus;
    }

    public void updateMangerView(@LayoutRes int layout) {
        this.helper.layoutId = layout;
        if (getContainerGroup() == null) {
            throw new NullPointerException("FloatingX window The parent container cannot be null!");
        }
        FxManagerView fxManagerView = this.managerView;
        float x2 = fxManagerView == null ? 0.0f : fxManagerView.getX();
        FxManagerView fxManagerView2 = this.managerView;
        float y2 = fxManagerView2 != null ? fxManagerView2.getY() : 0.0f;
        initManagerView();
        FxManagerView fxManagerView3 = this.managerView;
        if (fxManagerView3 != null) {
            fxManagerView3.restoreLocation$floatingx_release(x2, y2);
        }
        ViewGroup containerGroup = getContainerGroup();
        if (containerGroup == null) {
            return;
        }
        containerGroup.addView(this.managerView);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void updateView(@LayoutRes int resource) {
        if (resource == 0) {
            throw new IllegalArgumentException("resource cannot be 0!");
        }
        this.helper.layoutView = null;
        updateMangerView(resource);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void updateViewContent(@NotNull IFxHolderProvider provider) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        FxViewHolder fxViewHolder = this.viewHolder;
        if (fxViewHolder == null) {
            return;
        }
        provider.apply(fxViewHolder);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void move(float x2, float y2, boolean useAnimation) {
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.moveLocation$floatingx_release(x2, y2, useAnimation);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void moveByVector(float x2, float y2, boolean useAnimation) {
        FxManagerView fxManagerView = this.managerView;
        if (fxManagerView == null) {
            return;
        }
        fxManagerView.moveLocationByVector$floatingx_release(x2, y2, useAnimation);
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableAnimation(boolean isEnable) {
        this.helper.enableAnimation = isEnable;
    }

    @Override // com.petterp.floatingx.listener.control.IFxConfigControl
    public void setEnableSaveDirection(boolean isEnable) {
        this.helper.enableSaveDirection = isEnable;
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void setClickListener(@NotNull View.OnClickListener clickListener) {
        Intrinsics.checkNotNullParameter(clickListener, "clickListener");
        setClickListener(0L, clickListener);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void updateView(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.helper.layoutView = view;
        updateMangerView(0);
    }

    public final void detach() {
        ViewGroup containerGroup = getContainerGroup();
        if (containerGroup == null) {
            return;
        }
        detach(containerGroup);
    }

    @Override // com.petterp.floatingx.listener.control.IFxControl
    public void updateView(@NotNull IFxContextProvider provider) {
        Intrinsics.checkNotNullParameter(provider, "provider");
        View viewBuild = provider.build(context());
        Intrinsics.checkNotNullExpressionValue(viewBuild, "provider.build(context())");
        updateView(viewBuild);
    }
}
