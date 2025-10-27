package com.lxj.xpopup.core;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.BlurAnimator;
import com.lxj.xpopup.animator.EmptyAnimator;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.animator.ShadowBgAnimator;
import com.lxj.xpopup.animator.TranslateAlphaAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BasePopupView extends FrameLayout implements LifecycleObserver, LifecycleOwner {
    private final Runnable attachTask;
    protected BlurAnimator blurAnimator;
    public FullScreenDialog dialog;
    Runnable dismissWithRunnable;
    protected Runnable doAfterDismissTask;
    protected Runnable doAfterShowTask;
    protected Handler handler;
    private boolean hasModifySoftMode;
    private boolean hasMoveUp;
    private final Runnable initTask;
    protected boolean isCreated;
    protected LifecycleRegistry lifecycleRegistry;
    protected PopupAnimator popupContentAnimator;
    public PopupInfo popupInfo;
    public PopupStatus popupStatus;
    private int preSoftMode;
    protected ShadowBgAnimator shadowBgAnimator;
    private ShowSoftInputTask showSoftInputTask;
    private final int touchSlop;

    /* renamed from: x, reason: collision with root package name */
    private float f8900x;

    /* renamed from: y, reason: collision with root package name */
    private float f8901y;

    /* renamed from: com.lxj.xpopup.core.BasePopupView$9, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.ScaleAlphaFromCenter.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightTop.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightBottom.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromLeft.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromTop.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromRight.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromBottom.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromLeft.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromTop.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromRight.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromBottom.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeft.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftTop.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromTop.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightTop.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRight.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightBottom.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromBottom.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftBottom.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.NoAnimation.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
        }
    }

    public class BackPressListener implements View.OnKeyListener {
        public BackPressListener() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i2, KeyEvent keyEvent) {
            return BasePopupView.this.processKeyEvent(i2, keyEvent);
        }
    }

    public static class ShowSoftInputTask implements Runnable {
        View focusView;

        public ShowSoftInputTask(View view) {
            this.focusView = view;
        }

        @Override // java.lang.Runnable
        public void run() {
            View view = this.focusView;
            if (view != null) {
                KeyboardUtils.showSoftInput(view);
            }
        }
    }

    public BasePopupView(@NonNull Context context) {
        super(context);
        this.popupStatus = PopupStatus.Dismiss;
        this.isCreated = false;
        this.hasModifySoftMode = false;
        this.preSoftMode = -1;
        this.hasMoveUp = false;
        this.handler = new Handler(Looper.getMainLooper());
        this.attachTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.1
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.attachToHost();
                KeyboardUtils.registerSoftInputChangedListener(BasePopupView.this.getHostWindow(), BasePopupView.this, new KeyboardUtils.OnSoftInputChangedListener() { // from class: com.lxj.xpopup.core.BasePopupView.1.1
                    @Override // com.lxj.xpopup.util.KeyboardUtils.OnSoftInputChangedListener
                    public void onSoftInputChanged(int i2) {
                        XPopupCallback xPopupCallback;
                        BasePopupView.this.onKeyboardHeightChange(i2);
                        BasePopupView basePopupView = BasePopupView.this;
                        PopupInfo popupInfo = basePopupView.popupInfo;
                        if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
                            xPopupCallback.onKeyBoardStateChanged(basePopupView, i2);
                        }
                        if (i2 == 0) {
                            XPopupUtils.moveDown(BasePopupView.this);
                            BasePopupView.this.hasMoveUp = false;
                            return;
                        }
                        BasePopupView basePopupView2 = BasePopupView.this;
                        if ((basePopupView2 instanceof PartShadowPopupView) && basePopupView2.popupStatus == PopupStatus.Showing) {
                            return;
                        }
                        XPopupUtils.moveUpToKeyboard(i2, basePopupView2);
                        BasePopupView.this.hasMoveUp = true;
                    }
                });
                BasePopupView.this.init();
            }
        };
        this.initTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.2
            @Override // java.lang.Runnable
            public void run() {
                if (BasePopupView.this.getHostWindow() == null) {
                    return;
                }
                BasePopupView basePopupView = BasePopupView.this;
                XPopupCallback xPopupCallback = basePopupView.popupInfo.xPopupCallback;
                if (xPopupCallback != null) {
                    xPopupCallback.beforeShow(basePopupView);
                }
                BasePopupView.this.beforeShow();
                BasePopupView.this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                BasePopupView basePopupView2 = BasePopupView.this;
                if (!(basePopupView2 instanceof FullScreenPopupView)) {
                    basePopupView2.focusAndProcessBackPress();
                }
                BasePopupView basePopupView3 = BasePopupView.this;
                if ((basePopupView3 instanceof AttachPopupView) || (basePopupView3 instanceof BubbleAttachPopupView) || (basePopupView3 instanceof PositionPopupView) || (basePopupView3 instanceof PartShadowPopupView)) {
                    return;
                }
                basePopupView3.initAnimator();
                BasePopupView.this.doShowAnimation();
                BasePopupView.this.doAfterShow();
            }
        };
        this.doAfterShowTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.3
            @Override // java.lang.Runnable
            public void run() {
                XPopupCallback xPopupCallback;
                BasePopupView basePopupView = BasePopupView.this;
                basePopupView.popupStatus = PopupStatus.Show;
                basePopupView.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                BasePopupView.this.onShow();
                BasePopupView basePopupView2 = BasePopupView.this;
                if (basePopupView2 instanceof FullScreenPopupView) {
                    basePopupView2.focusAndProcessBackPress();
                }
                BasePopupView basePopupView3 = BasePopupView.this;
                PopupInfo popupInfo = basePopupView3.popupInfo;
                if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
                    xPopupCallback.onShow(basePopupView3);
                }
                if (BasePopupView.this.getHostWindow() == null || XPopupUtils.getDecorViewInvisibleHeight(BasePopupView.this.getHostWindow()) <= 0 || BasePopupView.this.hasMoveUp) {
                    return;
                }
                XPopupUtils.moveUpToKeyboard(XPopupUtils.getDecorViewInvisibleHeight(BasePopupView.this.getHostWindow()), BasePopupView.this);
            }
        };
        this.doAfterDismissTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.8
            @Override // java.lang.Runnable
            public void run() {
                View viewFindViewById;
                BasePopupView basePopupView = BasePopupView.this;
                basePopupView.popupStatus = PopupStatus.Dismiss;
                basePopupView.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                PopupInfo popupInfo = BasePopupView.this.popupInfo;
                if (popupInfo == null) {
                    return;
                }
                if (popupInfo.autoOpenSoftInput.booleanValue()) {
                    BasePopupView basePopupView2 = BasePopupView.this;
                    if (basePopupView2 instanceof PartShadowPopupView) {
                        KeyboardUtils.hideSoftInput(basePopupView2);
                    }
                }
                BasePopupView.this.onDismiss();
                XPopup.longClickPoint = null;
                BasePopupView basePopupView3 = BasePopupView.this;
                XPopupCallback xPopupCallback = basePopupView3.popupInfo.xPopupCallback;
                if (xPopupCallback != null) {
                    xPopupCallback.onDismiss(basePopupView3);
                }
                Runnable runnable = BasePopupView.this.dismissWithRunnable;
                if (runnable != null) {
                    runnable.run();
                    BasePopupView.this.dismissWithRunnable = null;
                }
                BasePopupView basePopupView4 = BasePopupView.this;
                PopupInfo popupInfo2 = basePopupView4.popupInfo;
                if (popupInfo2.isRequestFocus && popupInfo2.isViewMode && basePopupView4.getWindowDecorView() != null && (viewFindViewById = BasePopupView.this.getWindowDecorView().findViewById(R.id.content)) != null) {
                    viewFindViewById.setFocusable(true);
                    viewFindViewById.setFocusableInTouchMode(true);
                }
                BasePopupView.this.detachFromHost();
            }
        };
        if (context instanceof Application) {
            throw new IllegalArgumentException("XPopup的Context必须是Activity类型！");
        }
        this.lifecycleRegistry = new LifecycleRegistry(this);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setId(View.generateViewId());
        View viewInflate = LayoutInflater.from(context).inflate(getInnerLayoutId(), (ViewGroup) this, false);
        viewInflate.setAlpha(0.0f);
        addView(viewInflate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachToHost() {
        if (this.popupInfo == null) {
            throw new IllegalArgumentException("如果弹窗对象是复用的，则不要设置isDestroyOnDismiss(true)");
        }
        if (getContext() instanceof FragmentActivity) {
            ((FragmentActivity) getContext()).getLifecycle().addObserver(this);
        }
        if (!this.popupInfo.isViewMode) {
            if (this.dialog == null) {
                this.dialog = new FullScreenDialog(getContext()).setContent(this);
            }
            this.dialog.show();
        } else {
            ViewGroup viewGroup = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
            View viewFindViewById = viewGroup.findViewById(R.id.navigationBarBackground);
            int measuredHeight = viewFindViewById != null ? viewFindViewById.getMeasuredHeight() : 0;
            if (getParent() != null) {
                ((ViewGroup) getParent()).removeView(this);
            }
            viewGroup.addView(this, new FrameLayout.LayoutParams(-1, viewGroup.getMeasuredHeight() - measuredHeight));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detachFromHost() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.isViewMode) {
            FullScreenDialog fullScreenDialog = this.dialog;
            if (fullScreenDialog != null) {
                fullScreenDialog.dismiss();
                return;
            }
            return;
        }
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
    }

    private void passClickThrough(MotionEvent motionEvent) {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.isClickThrough) {
            return;
        }
        if (!popupInfo.isViewMode) {
            ((Activity) getContext()).dispatchTouchEvent(motionEvent);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != this) {
                childAt.dispatchTouchEvent(motionEvent);
            }
        }
    }

    public void applyDarkTheme() {
    }

    public void applyLightTheme() {
    }

    public void beforeDismiss() {
    }

    public void beforeShow() {
    }

    public void delayDismiss(long j2) {
        if (j2 < 0) {
            j2 = 0;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.7
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.dismiss();
            }
        }, j2);
    }

    public void delayDismissWith(long j2, Runnable runnable) {
        this.dismissWithRunnable = runnable;
        delayDismiss(j2);
    }

    public void destroy() {
        View view;
        View view2;
        View view3;
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null) {
            popupInfo.atView = null;
            popupInfo.watchView = null;
            popupInfo.xPopupCallback = null;
            PopupAnimator popupAnimator = popupInfo.customAnimator;
            if (popupAnimator != null && (view3 = popupAnimator.targetView) != null) {
                view3.animate().cancel();
            }
            if (this.popupInfo.isViewMode) {
                tryRemoveFragments();
            }
            if (this.popupInfo.isDestroyOnDismiss) {
                this.popupInfo = null;
            }
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog != null) {
            fullScreenDialog.contentView = null;
            this.dialog = null;
        }
        ShadowBgAnimator shadowBgAnimator = this.shadowBgAnimator;
        if (shadowBgAnimator != null && (view2 = shadowBgAnimator.targetView) != null) {
            view2.animate().cancel();
        }
        BlurAnimator blurAnimator = this.blurAnimator;
        if (blurAnimator == null || (view = blurAnimator.targetView) == null) {
            return;
        }
        view.animate().cancel();
        Bitmap bitmap = this.blurAnimator.decorBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.blurAnimator.decorBitmap.recycle();
        this.blurAnimator.decorBitmap = null;
    }

    public void dismiss() {
        XPopupCallback xPopupCallback;
        this.handler.removeCallbacks(this.attachTask);
        this.handler.removeCallbacks(this.initTask);
        PopupStatus popupStatus = this.popupStatus;
        PopupStatus popupStatus2 = PopupStatus.Dismissing;
        if (popupStatus == popupStatus2 || popupStatus == PopupStatus.Dismiss) {
            return;
        }
        this.popupStatus = popupStatus2;
        clearFocus();
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
            xPopupCallback.beforeDismiss(this);
        }
        beforeDismiss();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        doDismissAnimation();
        doAfterDismiss();
    }

    public void dismissOrHideSoftInput() {
        if (KeyboardUtils.sDecorViewInvisibleHeightPre == 0) {
            dismiss();
        } else {
            KeyboardUtils.hideSoftInput(this);
        }
    }

    public void dismissWith(Runnable runnable) {
        this.dismissWithRunnable = runnable;
        dismiss();
    }

    public void doAfterDismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.autoOpenSoftInput.booleanValue() && !(this instanceof PartShadowPopupView)) {
            KeyboardUtils.hideSoftInput(this);
        }
        this.handler.removeCallbacks(this.doAfterDismissTask);
        this.handler.postDelayed(this.doAfterDismissTask, getAnimationDuration());
    }

    public void doAfterShow() {
        this.handler.removeCallbacks(this.doAfterShowTask);
        this.handler.postDelayed(this.doAfterShowTask, getAnimationDuration());
    }

    public void doDismissAnimation() {
        BlurAnimator blurAnimator;
        ShadowBgAnimator shadowBgAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.hasShadowBg.booleanValue() && !this.popupInfo.hasBlurBg.booleanValue() && (shadowBgAnimator = this.shadowBgAnimator) != null) {
            shadowBgAnimator.animateDismiss();
        } else if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateDismiss();
        }
        PopupAnimator popupAnimator = this.popupContentAnimator;
        if (popupAnimator != null) {
            popupAnimator.animateDismiss();
        }
    }

    public void doShowAnimation() {
        BlurAnimator blurAnimator;
        ShadowBgAnimator shadowBgAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.hasShadowBg.booleanValue() && !this.popupInfo.hasBlurBg.booleanValue() && (shadowBgAnimator = this.shadowBgAnimator) != null) {
            shadowBgAnimator.animateShow();
        } else if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateShow();
        }
        PopupAnimator popupAnimator = this.popupContentAnimator;
        if (popupAnimator != null) {
            popupAnimator.animateShow();
        }
    }

    public void focusAndProcessBackPress() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.isRequestFocus) {
            return;
        }
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();
        if (Build.VERSION.SDK_INT >= 28) {
            addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.lxj.xpopup.core.BasePopupView.4
                @Override // android.view.View.OnUnhandledKeyEventListener
                public boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                    return BasePopupView.this.processKeyEvent(keyEvent.getKeyCode(), keyEvent);
                }
            });
        } else {
            setOnKeyListener(new BackPressListener());
        }
        ArrayList arrayList = new ArrayList();
        XPopupUtils.findAllEditText(arrayList, (ViewGroup) getPopupContentView());
        if (arrayList.size() <= 0) {
            if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
                showSoftInput(this);
                return;
            }
            return;
        }
        if (this.popupInfo.isViewMode) {
            this.preSoftMode = getHostWindow().getAttributes().softInputMode;
            getHostWindow().setSoftInputMode(16);
            this.hasModifySoftMode = true;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            EditText editText = (EditText) arrayList.get(i2);
            if (Build.VERSION.SDK_INT >= 28) {
                editText.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.lxj.xpopup.core.BasePopupView.5
                    @Override // android.view.View.OnUnhandledKeyEventListener
                    public boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent) {
                        return BasePopupView.this.processKeyEvent(keyEvent.getKeyCode(), keyEvent);
                    }
                });
            } else if (!XPopupUtils.hasSetKeyListener(editText)) {
                editText.setOnKeyListener(new BackPressListener());
            }
            if (i2 == 0) {
                PopupInfo popupInfo2 = this.popupInfo;
                if (popupInfo2.autoFocusEditText) {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
                        showSoftInput(editText);
                    }
                } else if (popupInfo2.autoOpenSoftInput.booleanValue()) {
                    showSoftInput(this);
                }
            }
        }
    }

    public PopupAnimator genAnimatorByPopupType() {
        PopupAnimation popupAnimation;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || (popupAnimation = popupInfo.popupAnimation) == null) {
            return null;
        }
        switch (AnonymousClass9.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[popupAnimation.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 6:
            case 7:
            case 8:
            case 9:
                return new TranslateAlphaAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 10:
            case 11:
            case 12:
            case 13:
                return new TranslateAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 22:
                return new EmptyAnimator(getPopupContentView(), getAnimationDuration());
            default:
                return null;
        }
    }

    public int getAnimationDuration() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        if (popupInfo.popupAnimation == PopupAnimation.NoAnimation) {
            return 1;
        }
        int i2 = popupInfo.animationDuration;
        return i2 >= 0 ? i2 : XPopup.getAnimationDuration() + 1;
    }

    public Window getHostWindow() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.isViewMode) {
            return ((Activity) getContext()).getWindow();
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog == null) {
            return null;
        }
        return fullScreenDialog.getWindow();
    }

    public int getImplLayoutId() {
        return -1;
    }

    public abstract int getInnerLayoutId();

    public List<String> getInternalFragmentNames() {
        return null;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    public Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    public int getMaxHeight() {
        return this.popupInfo.maxHeight;
    }

    public int getMaxWidth() {
        return this.popupInfo.maxWidth;
    }

    public PopupAnimator getPopupAnimator() {
        return null;
    }

    public View getPopupContentView() {
        return getChildAt(0);
    }

    public int getPopupHeight() {
        return this.popupInfo.popupHeight;
    }

    public View getPopupImplView() {
        return ((ViewGroup) getPopupContentView()).getChildAt(0);
    }

    public int getPopupWidth() {
        return this.popupInfo.popupWidth;
    }

    public int getShadowBgColor() {
        int i2;
        PopupInfo popupInfo = this.popupInfo;
        return (popupInfo == null || (i2 = popupInfo.shadowBgColor) == 0) ? XPopup.getShadowBgColor() : i2;
    }

    public int getStatusBarBgColor() {
        int i2;
        PopupInfo popupInfo = this.popupInfo;
        return (popupInfo == null || (i2 = popupInfo.statusBarBgColor) == 0) ? XPopup.getStatusBarBgColor() : i2;
    }

    public View getWindowDecorView() {
        if (getHostWindow() == null) {
            return null;
        }
        return (ViewGroup) getHostWindow().getDecorView();
    }

    public void init() {
        if (this.shadowBgAnimator == null) {
            this.shadowBgAnimator = new ShadowBgAnimator(this, getAnimationDuration(), getShadowBgColor());
        }
        if (this.popupInfo.hasBlurBg.booleanValue()) {
            BlurAnimator blurAnimator = new BlurAnimator(this, getShadowBgColor());
            this.blurAnimator = blurAnimator;
            blurAnimator.hasShadowBg = this.popupInfo.hasShadowBg.booleanValue();
            this.blurAnimator.decorBitmap = XPopupUtils.view2Bitmap(XPopupUtils.context2Activity(this).getWindow().getDecorView());
        }
        if ((this instanceof AttachPopupView) || (this instanceof BubbleAttachPopupView) || (this instanceof PartShadowPopupView) || (this instanceof PositionPopupView) || !this.isCreated) {
            initPopupContent();
        }
        if (!this.isCreated) {
            this.isCreated = true;
            onCreate();
            this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            XPopupCallback xPopupCallback = this.popupInfo.xPopupCallback;
            if (xPopupCallback != null) {
                xPopupCallback.onCreated(this);
            }
        }
        this.handler.postDelayed(this.initTask, 10L);
    }

    public void initAnimator() {
        BlurAnimator blurAnimator;
        getPopupContentView().setAlpha(1.0f);
        PopupAnimator popupAnimator = this.popupInfo.customAnimator;
        if (popupAnimator != null) {
            this.popupContentAnimator = popupAnimator;
            popupAnimator.targetView = getPopupContentView();
        } else {
            PopupAnimator popupAnimatorGenAnimatorByPopupType = genAnimatorByPopupType();
            this.popupContentAnimator = popupAnimatorGenAnimatorByPopupType;
            if (popupAnimatorGenAnimatorByPopupType == null) {
                this.popupContentAnimator = getPopupAnimator();
            }
        }
        if (this.popupInfo.hasShadowBg.booleanValue()) {
            this.shadowBgAnimator.initAnimator();
        }
        if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.initAnimator();
        }
        PopupAnimator popupAnimator2 = this.popupContentAnimator;
        if (popupAnimator2 != null) {
            popupAnimator2.initAnimator();
        }
    }

    public void initPopupContent() {
    }

    public boolean isDismiss() {
        return this.popupStatus == PopupStatus.Dismiss;
    }

    public boolean isShow() {
        return this.popupStatus != PopupStatus.Dismiss;
    }

    public void onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        onDetachedFromWindow();
        detachFromHost();
        destroy();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeCallbacksAndMessages(null);
        if (this.popupInfo != null) {
            if (getWindowDecorView() != null) {
                KeyboardUtils.removeLayoutChangeListener(getHostWindow(), this);
            }
            if (this.popupInfo.isViewMode && this.hasModifySoftMode) {
                getHostWindow().setSoftInputMode(this.preSoftMode);
                this.hasModifySoftMode = false;
            }
            if (this.popupInfo.isDestroyOnDismiss) {
                destroy();
            }
        }
        if (getContext() != null && (getContext() instanceof FragmentActivity)) {
            ((FragmentActivity) getContext()).getLifecycle().removeObserver(this);
        }
        this.popupStatus = PopupStatus.Dismiss;
        this.showSoftInputTask = null;
        this.hasMoveUp = false;
    }

    public void onDismiss() {
    }

    public void onKeyboardHeightChange(int i2) {
    }

    public void onShow() {
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        PopupInfo popupInfo;
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        getPopupContentView().getGlobalVisibleRect(rect);
        if (!XPopupUtils.isInRect(motionEvent.getX(), motionEvent.getY(), rect)) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.f8900x = motionEvent.getX();
                this.f8901y = motionEvent.getY();
                passClickThrough(motionEvent);
            } else if (action == 1 || action == 2 || action == 3) {
                float fSqrt = (float) Math.sqrt(Math.pow(motionEvent.getX() - this.f8900x, 2.0d) + Math.pow(motionEvent.getY() - this.f8901y, 2.0d));
                if (!XPopupUtils.isInRect(motionEvent.getX(), motionEvent.getY(), rect2)) {
                    passClickThrough(motionEvent);
                }
                if (fSqrt < this.touchSlop && (popupInfo = this.popupInfo) != null && popupInfo.isDismissOnTouchOutside.booleanValue()) {
                    dismiss();
                    getPopupImplView().getGlobalVisibleRect(rect2);
                }
                this.f8900x = 0.0f;
                this.f8901y = 0.0f;
            }
        }
        return true;
    }

    public boolean processKeyEvent(int i2, KeyEvent keyEvent) {
        PopupInfo popupInfo;
        XPopupCallback xPopupCallback;
        if (i2 != 4 || keyEvent.getAction() != 1 || (popupInfo = this.popupInfo) == null) {
            return false;
        }
        if (popupInfo.isDismissOnBackPressed.booleanValue() && ((xPopupCallback = this.popupInfo.xPopupCallback) == null || !xPopupCallback.onBackPressed(this))) {
            dismissOrHideSoftInput();
        }
        return true;
    }

    public BasePopupView show() {
        PopupInfo popupInfo;
        PopupStatus popupStatus;
        PopupStatus popupStatus2;
        FullScreenDialog fullScreenDialog;
        Activity activityContext2Activity = XPopupUtils.context2Activity(this);
        if (activityContext2Activity != null && !activityContext2Activity.isFinishing() && (popupInfo = this.popupInfo) != null && (popupStatus = this.popupStatus) != (popupStatus2 = PopupStatus.Showing) && popupStatus != PopupStatus.Dismissing) {
            this.popupStatus = popupStatus2;
            if (popupInfo.isRequestFocus) {
                KeyboardUtils.hideSoftInput(activityContext2Activity.getWindow());
            }
            if (!this.popupInfo.isViewMode && (fullScreenDialog = this.dialog) != null && fullScreenDialog.isShowing()) {
                return this;
            }
            this.handler.post(this.attachTask);
        }
        return this;
    }

    public void showSoftInput(View view) {
        if (this.popupInfo != null) {
            ShowSoftInputTask showSoftInputTask = this.showSoftInputTask;
            if (showSoftInputTask == null) {
                this.showSoftInputTask = new ShowSoftInputTask(view);
            } else {
                this.handler.removeCallbacks(showSoftInputTask);
            }
            this.handler.postDelayed(this.showSoftInputTask, 10L);
        }
    }

    public void smartDismiss() {
        this.handler.post(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.6
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.delayDismiss(r0.getAnimationDuration() + 50);
            }
        });
    }

    public void toggle() {
        if (isShow()) {
            dismiss();
        } else {
            show();
        }
    }

    public void tryRemoveFragments() {
        if (getContext() instanceof FragmentActivity) {
            FragmentManager supportFragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
            List<Fragment> fragments = supportFragmentManager.getFragments();
            List<String> internalFragmentNames = getInternalFragmentNames();
            if (fragments == null || fragments.size() <= 0 || internalFragmentNames == null) {
                return;
            }
            for (int i2 = 0; i2 < fragments.size(); i2++) {
                if (internalFragmentNames.contains(fragments.get(i2).getClass().getSimpleName())) {
                    supportFragmentManager.beginTransaction().remove(fragments.get(i2)).commitAllowingStateLoss();
                }
            }
        }
    }
}
