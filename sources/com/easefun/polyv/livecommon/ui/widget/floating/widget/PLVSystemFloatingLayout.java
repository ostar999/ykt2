package com.easefun.polyv.livecommon.ui.widget.floating.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.floating.enums.PLVFloatingEnums;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.AppUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import com.yikaobang.yixue.R2;

/* loaded from: classes3.dex */
public class PLVSystemFloatingLayout extends PLVAbsFloatingLayout {
    private final String TAG;
    private final Application.ActivityLifecycleCallbacks callbacks;
    private View contentView;
    private Handler handler;
    private boolean isNeedShow;
    private ViewGroup originContentParentVG;
    protected WindowManager windowManager;
    protected WindowManager.LayoutParams wmLayoutParams;

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVSystemFloatingLayout$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$ShowType;

        static {
            int[] iArr = new int[PLVFloatingEnums.ShowType.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$ShowType = iArr;
            try {
                iArr[PLVFloatingEnums.ShowType.SHOW_ALWAYS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$ShowType[PLVFloatingEnums.ShowType.SHOW_ONLY_BACKGROUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$ShowType[PLVFloatingEnums.ShowType.SHOW_ONLY_FOREGROUND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public PLVSystemFloatingLayout(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachContentViewToWindow() {
        if (this.originContentParentVG == null) {
            ViewGroup viewGroup = (ViewGroup) this.contentView.getParent();
            this.originContentParentVG = viewGroup;
            if (viewGroup != null) {
                viewGroup.removeView(this.contentView);
            }
            addView(this.contentView, -1, -1);
        }
        this.windowManager.addView(this, this.wmLayoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean currentFitShowType() {
        int i2 = AnonymousClass2.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$ShowType[this.showType.ordinal()];
        if (i2 == 1) {
            return true;
        }
        if (i2 == 2) {
            return AppUtils.isAppBackground();
        }
        if (i2 != 3) {
            return false;
        }
        return AppUtils.isAppForeground();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detachContentViewFromWindow() {
        this.windowManager.removeView(this);
    }

    private void initWindowManager() {
        this.windowManager = (WindowManager) Utils.getApp().getSystemService("window");
        this.wmLayoutParams = new WindowManager.LayoutParams();
        Utils.getApp().registerActivityLifecycleCallbacks(this.callbacks);
        if (Build.VERSION.SDK_INT >= 26) {
            this.wmLayoutParams.type = R2.attr.index_change_img;
        } else {
            this.wmLayoutParams.type = 2002;
        }
        WindowManager.LayoutParams layoutParams = this.wmLayoutParams;
        layoutParams.format = 1;
        layoutParams.flags = 262312;
        layoutParams.gravity = 51;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void destroy() {
        Utils.getApp().unregisterActivityLifecycleCallbacks(this.callbacks);
        this.originContentParentVG = null;
        this.contentView = null;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public View getContentView() {
        return this.contentView;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public Point getFloatLocation() {
        return new Point(this.floatingLocationX, this.floatingLocationY);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void hide() {
        this.isNeedShow = false;
        if (isShowing()) {
            detachContentViewFromWindow();
            this.isShowing = false;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setContentView(View view) {
        View view2 = this.contentView;
        if (view2 != null) {
            removeView(view2);
            this.originContentParentVG = null;
        }
        this.contentView = view;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void show(Activity activity) {
        this.isNeedShow = true;
        if (isShowing()) {
            PLVCommonLog.d(this.TAG, "call show window but already show");
        } else if (currentFitShowType()) {
            attachContentViewToWindow();
            this.isShowing = true;
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void updateFloatLocation(int x2, int y2) {
        int iFitInsideScreenX = fitInsideScreenX(x2);
        int iFitInsideScreenY = fitInsideScreenY(y2);
        this.floatingLocationX = iFitInsideScreenX;
        this.floatingLocationY = iFitInsideScreenY;
        WindowManager.LayoutParams layoutParams = this.wmLayoutParams;
        layoutParams.x = iFitInsideScreenX;
        layoutParams.y = iFitInsideScreenY;
        if (this.windowManager == null || !isShowing()) {
            return;
        }
        this.windowManager.updateViewLayout(this, this.wmLayoutParams);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void updateFloatSize(int width, int height) {
        WindowManager.LayoutParams layoutParams = this.wmLayoutParams;
        layoutParams.width = width;
        layoutParams.height = height;
        this.floatWindowWidth = width;
        this.floatWindowHeight = height;
        if (this.windowManager == null || !isShowing()) {
            return;
        }
        this.windowManager.updateViewLayout(this, this.wmLayoutParams);
    }

    public PLVSystemFloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVSystemFloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = getClass().getSimpleName();
        this.handler = new Handler(Looper.getMainLooper());
        this.isNeedShow = false;
        this.callbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVSystemFloatingLayout.1
            /* JADX INFO: Access modifiers changed from: private */
            public void updateContentViewAttach() {
                boolean z2 = PLVSystemFloatingLayout.this.isNeedShow && PLVSystemFloatingLayout.this.currentFitShowType();
                if (z2) {
                    PLVSystemFloatingLayout pLVSystemFloatingLayout = PLVSystemFloatingLayout.this;
                    if (!pLVSystemFloatingLayout.isShowing) {
                        pLVSystemFloatingLayout.attachContentViewToWindow();
                        PLVSystemFloatingLayout.this.isShowing = true;
                        return;
                    }
                }
                if (z2) {
                    return;
                }
                PLVSystemFloatingLayout pLVSystemFloatingLayout2 = PLVSystemFloatingLayout.this;
                if (pLVSystemFloatingLayout2.isShowing) {
                    pLVSystemFloatingLayout2.detachContentViewFromWindow();
                    PLVSystemFloatingLayout.this.isShowing = false;
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                updateContentViewAttach();
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                PLVSystemFloatingLayout.this.handler.removeCallbacksAndMessages(null);
                PLVSystemFloatingLayout.this.handler.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVSystemFloatingLayout.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        updateContentViewAttach();
                    }
                }, 200L);
            }
        };
        initWindowManager();
    }
}
