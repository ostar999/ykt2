package com.easefun.polyv.livecommon.module.modules.interact;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2;
import com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractAnswer;
import com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractBulletin;
import com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractCommonControl;
import com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractLottery;
import com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractQuestionnaire;
import com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractSignIn;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livescenes.feature.interact.PLVInteractWebView;
import com.plv.livescenes.feature.interact.PLVInteractAppAbs;
import com.plv.socket.event.interact.PLVShowPushCardEvent;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.KeyboardUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

@Deprecated
/* loaded from: classes3.dex */
public class PLVInteractLayout extends FrameLayout implements IPLVInteractLayout {
    private static final boolean LOAD_WEB_URL = true;
    private FrameLayout flContainer;
    private PLVInteractBulletin interactBulletin;
    private PLVInteractLottery interactLottery;

    @Nullable
    private PLVInteractWebView interactWebView;
    private ImageView ivClose;
    private LinearLayout ll;
    private ScrollView scroll;

    public class PolyvAnswerKeyboardHelper {
        private View bottomPlaceHolder;
        private View mChildOfContent;
        private int usableHeightPrevious;

        private int computeUsableHeight() {
            Rect rect = new Rect();
            this.mChildOfContent.getWindowVisibleDisplayFrame(rect);
            return rect.bottom - rect.top;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @SuppressLint({"ClickableViewAccessibility"})
        public void possiblyResizeChildOfContent() {
            int iComputeUsableHeight;
            if (PLVInteractLayout.this.isVisible() && (iComputeUsableHeight = computeUsableHeight()) != this.usableHeightPrevious) {
                int height = this.mChildOfContent.getRootView().getHeight();
                int i2 = height - iComputeUsableHeight;
                if (i2 > height / 4) {
                    PLVInteractLayout.this.scroll.setOnTouchListener(null);
                    PLVInteractLayout.this.scroll.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.PolyvAnswerKeyboardHelper.2
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVInteractLayout.this.scroll.fullScroll(130);
                        }
                    });
                    if (ScreenUtils.isPortrait()) {
                        return;
                    }
                    if (this.bottomPlaceHolder == null) {
                        this.bottomPlaceHolder = new View(PLVInteractLayout.this.getContext());
                    }
                    if (this.bottomPlaceHolder.getParent() == null) {
                        PLVInteractLayout.this.ll.addView(this.bottomPlaceHolder, -1, i2 - 100);
                    }
                    PLVInteractLayout.this.ll.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.PolyvAnswerKeyboardHelper.3
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVInteractLayout.this.scroll.fullScroll(130);
                        }
                    });
                } else {
                    if (PLVInteractLayout.this.ll.indexOfChild(this.bottomPlaceHolder) > 0) {
                        PLVInteractLayout.this.ll.removeView(this.bottomPlaceHolder);
                    }
                    PLVInteractLayout.this.scroll.setOnTouchListener(new View.OnTouchListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.PolyvAnswerKeyboardHelper.4
                        @Override // android.view.View.OnTouchListener
                        public boolean onTouch(View v2, MotionEvent event) {
                            return true;
                        }
                    });
                }
                this.usableHeightPrevious = iComputeUsableHeight;
            }
        }

        private PolyvAnswerKeyboardHelper(Activity activity) {
            View childAt = ((FrameLayout) activity.findViewById(R.id.content)).getChildAt(0);
            this.mChildOfContent = childAt;
            childAt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.PolyvAnswerKeyboardHelper.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    PolyvAnswerKeyboardHelper.this.possiblyResizeChildOfContent();
                }
            });
        }
    }

    public PLVInteractLayout(@NonNull Context context) {
        this(context, null);
    }

    private void handleKeyboardOrientation() {
        if (getContext() instanceof Activity) {
            final Activity activity = (Activity) getContext();
            post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.1
                @Override // java.lang.Runnable
                public void run() {
                    new PolyvAnswerKeyboardHelper(activity);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hide() {
        this.flContainer.setVisibility(8);
        PLVOrientationManager.getInstance().unlockOrientation();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(com.easefun.polyv.livecommon.R.layout.plv_interact_layout, (ViewGroup) this, true);
        this.interactWebView = (PLVInteractWebView) findViewById(com.easefun.polyv.livecommon.R.id.plvlc_interact_web);
        this.ll = (LinearLayout) findViewById(com.easefun.polyv.livecommon.R.id.plvlc_interact_ll);
        this.ivClose = (ImageView) findViewById(com.easefun.polyv.livecommon.R.id.plvlc_interact_iv_close);
        this.scroll = (ScrollView) findViewById(com.easefun.polyv.livecommon.R.id.plvlc_interact_scroll);
        this.flContainer = (FrameLayout) findViewById(com.easefun.polyv.livecommon.R.id.plvlc_interact_fl_container);
        setClickListener();
        handleKeyboardOrientation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isVisible() {
        return this.flContainer.isShown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockToPortrait() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) {
            return;
        }
        if (topActivity.getRequestedOrientation() != 1) {
            PLVOrientationManager.getInstance().unlockOrientation();
            PLVOrientationManager.getInstance().setPortrait(topActivity);
        }
        PLVOrientationManager.getInstance().lockOrientation();
    }

    private void setClickListener() {
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVInteractLayout.this.onBackPress();
            }
        });
    }

    private void setupInteractApp() {
        if (this.interactWebView == null) {
            return;
        }
        PLVInteractCommonControl pLVInteractCommonControl = new PLVInteractCommonControl();
        pLVInteractCommonControl.setOnInteractCommonControlListener(new PLVInteractCommonControl.OnInteractCommonControlListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.2
            @Override // com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractCommonControl.OnInteractCommonControlListener
            public void onWebViewHide() {
                PLVInteractLayout.this.hide();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractCommonControl.OnInteractCommonControlListener
            public void onWebViewLoadFinished() {
                PLVInteractLayout.this.ivClose.setVisibility(4);
            }
        });
        pLVInteractCommonControl.setOnShowListener(new PLVInteractAppAbs.OnInteractAppShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.3
            @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs.OnInteractAppShowListener
            public void onShow() {
                PLVInteractLayout.this.show();
            }
        });
        PLVInteractAnswer pLVInteractAnswer = new PLVInteractAnswer();
        pLVInteractAnswer.setOnShowListener(new PLVInteractAppAbs.OnInteractAppShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.4
            @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs.OnInteractAppShowListener
            public void onShow() {
                PLVInteractLayout.this.lockToPortrait();
                PLVInteractLayout.this.show();
            }
        });
        PLVInteractQuestionnaire pLVInteractQuestionnaire = new PLVInteractQuestionnaire();
        pLVInteractQuestionnaire.setOnShowListener(new PLVInteractAppAbs.OnInteractAppShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.5
            @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs.OnInteractAppShowListener
            public void onShow() {
                PLVInteractLayout.this.lockToPortrait();
                PLVInteractLayout.this.show();
            }
        });
        PLVInteractLottery pLVInteractLottery = new PLVInteractLottery(pLVInteractCommonControl);
        this.interactLottery = pLVInteractLottery;
        pLVInteractLottery.setOnShowListener(new PLVInteractAppAbs.OnInteractAppShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.6
            @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs.OnInteractAppShowListener
            public void onShow() {
                PLVInteractLayout.this.lockToPortrait();
                PLVInteractLayout.this.show();
            }
        });
        PLVInteractSignIn pLVInteractSignIn = new PLVInteractSignIn();
        pLVInteractSignIn.setOnShowListener(new PLVInteractAppAbs.OnInteractAppShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.7
            @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs.OnInteractAppShowListener
            public void onShow() {
                PLVInteractLayout.this.show();
            }
        });
        PLVInteractBulletin pLVInteractBulletin = new PLVInteractBulletin();
        this.interactBulletin = pLVInteractBulletin;
        pLVInteractBulletin.setOnShowListener(new PLVInteractAppAbs.OnInteractAppShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.8
            @Override // com.plv.livescenes.feature.interact.PLVInteractAppAbs.OnInteractAppShowListener
            public void onShow() {
                PLVInteractLayout.this.show();
            }
        });
        this.interactBulletin.setOnPLVInteractBulletinListener(new PLVInteractBulletin.OnPLVInteractBulletinListener() { // from class: com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout.9
            @Override // com.easefun.polyv.livecommon.module.modules.interact.app.PLVInteractBulletin.OnPLVInteractBulletinListener
            public void onBulletinDelete() {
                PLVInteractLayout.this.hide();
            }
        });
        this.interactWebView.addInteractApp(pLVInteractCommonControl);
        this.interactWebView.addInteractApp(pLVInteractAnswer);
        this.interactWebView.addInteractApp(pLVInteractQuestionnaire);
        this.interactWebView.addInteractApp(this.interactLottery);
        this.interactWebView.addInteractApp(pLVInteractSignIn);
        this.interactWebView.addInteractApp(this.interactBulletin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show() {
        if (this.interactWebView == null) {
            return;
        }
        KeyboardUtils.hideSoftInput(this);
        this.interactWebView.requestFocus();
        this.flContainer.setVisibility(0);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void destroy() {
        PLVInteractWebView pLVInteractWebView = this.interactWebView;
        if (pLVInteractWebView != null) {
            pLVInteractWebView.removeAllViews();
            ViewParent parent = this.interactWebView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.interactWebView);
            }
            this.interactWebView.destroy();
            this.interactWebView = null;
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void init(IPLVLiveRoomDataManager liveRoomDataManager) {
        init(liveRoomDataManager, null);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public boolean onBackPress() {
        if (this.interactLottery.onBackPress()) {
            return true;
        }
        if (!isVisible()) {
            return false;
        }
        hide();
        return true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void onCallDynamicFunction(String event) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void setOnOpenInsideWebViewListener(PLVInteractLayout2.OnOpenInsideWebViewListener listener) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void showBulletin() {
        PLVInteractBulletin pLVInteractBulletin = this.interactBulletin;
        if (pLVInteractBulletin != null) {
            pLVInteractBulletin.showBulletin();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void showCardPush(PLVShowPushCardEvent showPushCardEvent) {
    }

    public PLVInteractLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout
    public void init(IPLVLiveRoomDataManager liveRoomDataManager, @Nullable PLVLiveScene scene) {
        PLVInteractWebView pLVInteractWebView = this.interactWebView;
        if (pLVInteractWebView == null) {
            return;
        }
        pLVInteractWebView.loadWeb();
        setupInteractApp();
    }

    public PLVInteractLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
