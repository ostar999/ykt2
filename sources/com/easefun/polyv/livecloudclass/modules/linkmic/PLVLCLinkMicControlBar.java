package com.easefun.polyv.livecloudclass.modules.linkmic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar;
import com.easefun.polyv.livecloudclass.modules.linkmic.widget.PLVLCLinkMicRingButton;
import com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow;
import com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager;
import com.easefun.polyv.livecommon.module.utils.PLVDialogFactory;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.easefun.polyv.livecommon.ui.widget.PLVNoConsumeTouchEventButton;
import com.easefun.polyv.livecommon.ui.widget.PLVTouchFloatingView;
import com.plv.foundationsdk.component.di.PLVDependManager;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVNetworkUtils;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.linkmic.log.PLVLinkMicTraceLogSender;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class PLVLCLinkMicControlBar extends FrameLayout implements IPLVLCLinkMicControlBar {
    private static final int DELAY_AUTO_HIDE_WHEN_JOINED = 3000;
    private static final int DELAY_AUTO_HIDE_WHEN_NOT_JOINED = 5000;
    private static final int DP_ORIGIN_MARGIN_TOP_PORTRAIT = 466;
    private static final int DURATION_MS_LINK_MIC_OPEN_OFF = 300;
    private static final String TAG = "PLVLCLinkMicControlBar";
    private Disposable autoHideDisposable;
    private int biggestStateWidthPortrait;
    private Button btnCameraFrontBackLandscape;
    private PLVNoConsumeTouchEventButton btnCameraFrontBackPortrait;
    private Button btnCameraOpenLandscape;
    private PLVNoConsumeTouchEventButton btnCameraOpenPortrait;
    private PLVNoConsumeTouchEventButton btnCollapsePortrait;
    private Button btnMicrophoneOpenLandscape;
    private PLVNoConsumeTouchEventButton btnMicrophoneOpenPortrait;
    private Button btnRingActionLandscape;
    private PLVLCLinkMicRingButton btnRingActionPortrait;
    private PLVTouchFloatingView floatingViewPortraitRoot;
    private boolean isCameraFront;
    private boolean isCameraOpen;
    private boolean isMicrophoneOpen;
    private boolean isPortrait;
    private LinearLayout ll4BtnParent;
    private LinearLayout llFunctionBtnParentLand;
    private LinearLayout llLandscapeRoot;
    private int middleStateWidthPortrait;
    private IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener onPLCLinkMicControlBarListener;
    private BroadcastReceiver receiver;
    private int smallStateWidthPortrait;
    private PLVLCLinkMicControllerState state;
    private TextView tvRequestTip;
    private TextView tvRequestTipLandscape;

    /* renamed from: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar$17, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass17 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecloudclass$modules$linkmic$PLVLCLinkMicControlBar$PLVLCLinkMicControllerState;

        static {
            int[] iArr = new int[PLVLCLinkMicControllerState.values().length];
            $SwitchMap$com$easefun$polyv$livecloudclass$modules$linkmic$PLVLCLinkMicControlBar$PLVLCLinkMicControllerState = iArr;
            try {
                iArr[PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecloudclass$modules$linkmic$PLVLCLinkMicControlBar$PLVLCLinkMicControllerState[PLVLCLinkMicControllerState.STATE_JOIN_LINK_MIC_SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecloudclass$modules$linkmic$PLVLCLinkMicControlBar$PLVLCLinkMicControllerState[PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_OPEN_COLLAPSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum PLVLCLinkMicControllerState {
        STATE_TEACHER_LINK_MIC_CLOSE,
        STATE_TEACHER_LINK_MIC_OPEN,
        STATE_TEACHER_LINK_MIC_OPEN_COLLAPSE,
        STATE_REQUESTING_JOIN_LINK_MIC,
        STATE_JOIN_LINK_MIC_SUCCESS,
        STATE_JOIN_LINK_MIC_SUCCESS_COLLAPSE
    }

    public PLVLCLinkMicControlBar(Context context) {
        this(context, null);
    }

    private void animateLinkMicOpenOrClose(boolean z2) {
        if (z2) {
            animateMoveToShowMiddleWidth();
        } else {
            animateMoveToHide();
        }
        tipGradientShowOrHide(z2, 300);
    }

    private void animateMove(float f2) {
        PLVTouchFloatingView pLVTouchFloatingView = this.floatingViewPortraitRoot;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(pLVTouchFloatingView, "translationX", pLVTouchFloatingView.getTranslationX(), f2);
        objectAnimatorOfFloat.setDuration(300L);
        objectAnimatorOfFloat.start();
    }

    private void animateMoveToHide() {
        animateMove(this.biggestStateWidthPortrait);
    }

    private void animateMoveToShowBiggestWidth() {
        animateMove(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateMoveToShowMiddleWidth() {
        animateMove(this.biggestStateWidthPortrait - this.middleStateWidthPortrait);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateMoveToShowSmallestWidth() {
        animateMove(this.biggestStateWidthPortrait - this.smallStateWidthPortrait);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickRingSetting() {
        this.btnRingActionPortrait.setRingOffState();
        animateMoveToShowBiggestWidth();
        this.state = PLVLCLinkMicControllerState.STATE_JOIN_LINK_MIC_SUCCESS;
        startAutoHideCountDown();
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRingOff() {
        setLeaveLinkMic();
        this.btnRingActionLandscape.setBackgroundResource(R.drawable.plvlc_linkmic_iv_ring_up);
        IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener onPLCLinkMicControlBarListener = this.onPLCLinkMicControlBarListener;
        if (onPLCLinkMicControlBarListener != null) {
            onPLCLinkMicControlBarListener.onClickRingOffLinkMic();
        }
        startAutoHideCountDown();
    }

    private void initBroadcastReceiver(final Context context) {
        context.registerReceiver(this.receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(new GenericLifecycleObserver() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.3
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                        context.unregisterReceiver(PLVLCLinkMicControlBar.this.receiver);
                    }
                }
            });
        } else {
            PLVCommonLog.e(TAG, "context not instance of AppCompatActivity, in danger of leak broadcast receiver");
        }
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_linkmic_controller_layout, (ViewGroup) this, true);
        this.floatingViewPortraitRoot = (PLVTouchFloatingView) findViewById(R.id.plvlc_linkmic_controller_floating_view_portrait_root);
        this.btnRingActionPortrait = (PLVLCLinkMicRingButton) findViewById(R.id.plvlc_linkmic_controlBar_btn_ring_action);
        this.tvRequestTip = (TextView) findViewById(R.id.plvlc_linkmic_controlBar_tv_request_tip);
        this.btnCameraOpenPortrait = (PLVNoConsumeTouchEventButton) findViewById(R.id.plvlc_linkmic_controlBar_btn_camera_open);
        this.btnCameraFrontBackPortrait = (PLVNoConsumeTouchEventButton) findViewById(R.id.plvlc_linkmic_controlBar_btn_camera_front_back);
        this.btnMicrophoneOpenPortrait = (PLVNoConsumeTouchEventButton) findViewById(R.id.plvlc_linkmic_controlBar_btn_microphone_open);
        this.btnCollapsePortrait = (PLVNoConsumeTouchEventButton) findViewById(R.id.plvlc_linkmic_controlBar_btn_collapse);
        this.ll4BtnParent = (LinearLayout) findViewById(R.id.plvlc_linkmic_controlBar_ll_4_btn_parent);
        this.llFunctionBtnParentLand = (LinearLayout) findViewById(R.id.plvlc_linkmic_controlBar_ll_function_btn_parent);
        this.btnMicrophoneOpenLandscape = (Button) findViewById(R.id.plvlc_linkmic_controlBar_btn_microphone_open_landscape);
        this.btnCameraOpenLandscape = (Button) findViewById(R.id.plvlc_linkmic_controlBar_btn_camera_open_landscape);
        this.btnCameraFrontBackLandscape = (Button) findViewById(R.id.plvlc_linkmic_controlBar_btn_camera_front_back_landscape);
        this.btnRingActionLandscape = (Button) findViewById(R.id.plvlc_linkmic_controlBar_btn_setting_landscape);
        this.llLandscapeRoot = (LinearLayout) findViewById(R.id.plvlc_linkmic_controlBar_ll_landscape_root);
        this.tvRequestTipLandscape = (TextView) findViewById(R.id.plvlc_linkmic_controlBar_tv_request_tip_landscape);
        this.floatingViewPortraitRoot.setIsInterceptTouchEvent(false);
        this.floatingViewPortraitRoot.enableHorizontalDrag(false);
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.2
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVLCLinkMicControlBar.this.btnRingActionPortrait.getLayoutParams();
                PLVLCLinkMicControlBar pLVLCLinkMicControlBar = PLVLCLinkMicControlBar.this;
                pLVLCLinkMicControlBar.smallStateWidthPortrait = marginLayoutParams.leftMargin + pLVLCLinkMicControlBar.btnRingActionPortrait.getWidth() + marginLayoutParams.rightMargin;
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) PLVLCLinkMicControlBar.this.tvRequestTip.getLayoutParams();
                PLVLCLinkMicControlBar pLVLCLinkMicControlBar2 = PLVLCLinkMicControlBar.this;
                pLVLCLinkMicControlBar2.middleStateWidthPortrait = marginLayoutParams.leftMargin + pLVLCLinkMicControlBar2.btnRingActionPortrait.getWidth() + marginLayoutParams2.leftMargin + PLVLCLinkMicControlBar.this.tvRequestTip.getWidth() + marginLayoutParams2.rightMargin;
                PLVLCLinkMicControlBar pLVLCLinkMicControlBar3 = PLVLCLinkMicControlBar.this;
                pLVLCLinkMicControlBar3.biggestStateWidthPortrait = pLVLCLinkMicControlBar3.floatingViewPortraitRoot.getWidth();
                PLVLCLinkMicControlBar.this.floatingViewPortraitRoot.setTranslationX(PLVLCLinkMicControlBar.this.biggestStateWidthPortrait);
            }
        });
        this.btnRingActionPortrait.setShareTouchEventView(this.floatingViewPortraitRoot);
        this.btnCollapsePortrait.setShareTouchEventView(this.floatingViewPortraitRoot);
        this.btnCameraFrontBackPortrait.setShareTouchEventView(this.floatingViewPortraitRoot);
        this.btnCameraOpenPortrait.setShareTouchEventView(this.floatingViewPortraitRoot);
        this.btnMicrophoneOpenPortrait.setShareTouchEventView(this.floatingViewPortraitRoot);
        setPortraitClickListener();
        setLandscapeClickListener();
        this.floatingViewPortraitRoot.setInitLocation(0, PLVScreenUtils.dip2px(466.0f), 0, 0);
        boolean zIsPortrait = PLVScreenUtils.isPortrait(getContext());
        this.isPortrait = zIsPortrait;
        setOrientation(zIsPortrait);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNetworkDisconnected() {
        if (this.state == PLVLCLinkMicControllerState.STATE_REQUESTING_JOIN_LINK_MIC) {
            handleRingOff();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBtnCameraOpenState(boolean z2) {
        this.isCameraOpen = z2;
        if (z2) {
            PLVNoConsumeTouchEventButton pLVNoConsumeTouchEventButton = this.btnCameraOpenPortrait;
            int i2 = R.drawable.plvlc_linkmic_iv_camera_open;
            pLVNoConsumeTouchEventButton.setBackgroundResource(i2);
            this.btnCameraOpenLandscape.setBackgroundResource(i2);
            this.btnCameraFrontBackPortrait.setEnabled(true);
            this.btnCameraFrontBackLandscape.setEnabled(true);
            PLVNoConsumeTouchEventButton pLVNoConsumeTouchEventButton2 = this.btnCameraFrontBackPortrait;
            int i3 = R.drawable.plvlc_linkmic_iv_camera_front_back_enabled;
            pLVNoConsumeTouchEventButton2.setBackgroundResource(i3);
            this.btnCameraFrontBackLandscape.setBackgroundResource(i3);
            return;
        }
        PLVNoConsumeTouchEventButton pLVNoConsumeTouchEventButton3 = this.btnCameraOpenPortrait;
        int i4 = R.drawable.plvlc_linkmic_iv_camera_close;
        pLVNoConsumeTouchEventButton3.setBackgroundResource(i4);
        this.btnCameraOpenLandscape.setBackgroundResource(i4);
        this.btnCameraFrontBackPortrait.setEnabled(false);
        this.btnCameraFrontBackLandscape.setEnabled(false);
        PLVNoConsumeTouchEventButton pLVNoConsumeTouchEventButton4 = this.btnCameraFrontBackPortrait;
        int i5 = R.drawable.plvlc_linkmic_iv_camera_front_back_disabled;
        pLVNoConsumeTouchEventButton4.setBackgroundResource(i5);
        this.btnCameraFrontBackLandscape.setBackgroundResource(i5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBtnMicrophoneOpenState(boolean z2) {
        this.isMicrophoneOpen = z2;
        if (z2) {
            PLVNoConsumeTouchEventButton pLVNoConsumeTouchEventButton = this.btnMicrophoneOpenPortrait;
            int i2 = R.drawable.plvlc_linkmic_iv_microphone_open;
            pLVNoConsumeTouchEventButton.setBackgroundResource(i2);
            this.btnMicrophoneOpenLandscape.setBackgroundResource(i2);
            return;
        }
        PLVNoConsumeTouchEventButton pLVNoConsumeTouchEventButton2 = this.btnMicrophoneOpenPortrait;
        int i3 = R.drawable.plvlc_linkmic_iv_microphone_close;
        pLVNoConsumeTouchEventButton2.setBackgroundResource(i3);
        this.btnMicrophoneOpenLandscape.setBackgroundResource(i3);
    }

    private void setLandscapeClickListener() {
        this.btnMicrophoneOpenLandscape.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.btnMicrophoneOpenPortrait.performClick();
            }
        });
        this.btnCameraOpenLandscape.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.btnCameraOpenPortrait.performClick();
            }
        });
        this.btnCameraFrontBackLandscape.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.btnCameraFrontBackPortrait.performClick();
            }
        });
        this.btnRingActionLandscape.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.btnRingActionPortrait.performClick();
            }
        });
    }

    private void setOrientation(boolean z2) {
        if (this.state == PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_CLOSE) {
            return;
        }
        if (!z2) {
            this.floatingViewPortraitRoot.setVisibility(8);
            this.llLandscapeRoot.setVisibility(0);
        } else {
            this.floatingViewPortraitRoot.setVisibility(0);
            if (this.state.ordinal() >= PLVLCLinkMicControllerState.STATE_JOIN_LINK_MIC_SUCCESS.ordinal()) {
                clickRingSetting();
            }
            this.llLandscapeRoot.setVisibility(8);
        }
    }

    private void setPortraitClickListener() {
        this.btnRingActionPortrait.setOnLinkMicRingButtonClickListener(new PLVLCLinkMicRingButton.OnPLVLCLinkMicRingButtonClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.8
            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.widget.PLVLCLinkMicRingButton.OnPLVLCLinkMicRingButtonClickListener
            public void onClickRingOff() {
                PLVDialogFactory.createConfirmDialog(PLVLCLinkMicControlBar.this.getContext(), PLVLCLinkMicControlBar.this.getResources().getString(R.string.plv_linkmic_dialog_hang_off_confirm_ask), PLVLCLinkMicControlBar.this.getResources().getString(R.string.plv_linkmic_dialog_hang_off), new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.8.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        PLVLinkMicTraceLogSender pLVLinkMicTraceLogSender = new PLVLinkMicTraceLogSender();
                        pLVLinkMicTraceLogSender.setLogModuleClass(PLVLinkMicELog.class);
                        if (PLVLCLinkMicControlBar.this.state.equals(PLVLCLinkMicControllerState.STATE_REQUESTING_JOIN_LINK_MIC)) {
                            pLVLinkMicTraceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.USER_CANCEL_LINK_MIC, "waitingUserDidCancelLinkMic，state为" + PLVLCLinkMicControlBar.this.state);
                        } else {
                            pLVLinkMicTraceLogSender.submitTraceLog(PLVLinkMicELog.LinkMicTraceLogEvent.USER_CLOSE_LINK_MIC, "joinedUserDidCloseLinkMic，state为" + PLVLCLinkMicControlBar.this.state);
                        }
                        PLVLCLinkMicControlBar.this.handleRingOff();
                        dialogInterface.dismiss();
                    }
                }).show();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.widget.PLVLCLinkMicRingButton.OnPLVLCLinkMicRingButtonClickListener
            public void onClickRingSetting() {
                PLVLCLinkMicControlBar.this.clickRingSetting();
            }

            @Override // com.easefun.polyv.livecloudclass.modules.linkmic.widget.PLVLCLinkMicRingButton.OnPLVLCLinkMicRingButtonClickListener
            public void onClickRingUp() {
                if (!PLVNetworkUtils.isConnected(PLVLCLinkMicControlBar.this.getContext())) {
                    PLVCommonLog.w(PLVLCLinkMicControlBar.TAG, "net work not available");
                    return;
                }
                if (PLVLCLinkMicControlBar.this.toastWhenFloatingPlayerShowing()) {
                    return;
                }
                PLVLCLinkMicControlBar.this.btnRingActionPortrait.setRingOffState();
                TextView textView = PLVLCLinkMicControlBar.this.tvRequestTip;
                int i2 = R.string.plv_linkmic_tip_requesting_link_mic;
                textView.setText(i2);
                PLVLCLinkMicControlBar.this.btnRingActionLandscape.setBackgroundResource(R.drawable.plvlc_linkmic_iv_ring_off);
                PLVLCLinkMicControlBar.this.tvRequestTipLandscape.setText(i2);
                int i3 = AnonymousClass17.$SwitchMap$com$easefun$polyv$livecloudclass$modules$linkmic$PLVLCLinkMicControlBar$PLVLCLinkMicControllerState[PLVLCLinkMicControlBar.this.state.ordinal()];
                if (i3 == 1) {
                    PLVCommonLog.d(PLVLCLinkMicControlBar.TAG, "btnSetting.onClickRingUp->STATE_TEACHER_LINK_MIC_OPEN");
                } else if (i3 == 3) {
                    PLVLCLinkMicControlBar.this.animateMoveToShowMiddleWidth();
                    PLVLCLinkMicControlBar.this.tipGradientShowOrHide(true, 300);
                    PLVCommonLog.d(PLVLCLinkMicControlBar.TAG, "btnSetting.onClickRingUp->STATE_TEACHER_LINK_MIC_OPEN_COLLAPSE");
                }
                PLVLCLinkMicControlBar.this.state = PLVLCLinkMicControllerState.STATE_REQUESTING_JOIN_LINK_MIC;
                ((PLVLCFloatingWindow) PLVDependManager.getInstance().get(PLVLCFloatingWindow.class)).showByUser(false);
                if (PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener != null) {
                    PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener.onClickRingUpLinkMic();
                }
            }
        });
        this.btnCameraOpenPortrait.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.setBtnCameraOpenState(!r2.isCameraOpen);
                if (PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener != null) {
                    PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener.onClickCameraOpenOrClose(!PLVLCLinkMicControlBar.this.isCameraOpen);
                }
            }
        });
        this.btnCameraFrontBackPortrait.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.isCameraFront = !r2.isCameraFront;
                if (PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener != null) {
                    PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener.onClickCameraFrontOfBack(PLVLCLinkMicControlBar.this.isCameraFront);
                }
            }
        });
        this.btnMicrophoneOpenPortrait.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.setBtnMicrophoneOpenState(!r2.isMicrophoneOpen);
                if (PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener != null) {
                    PLVLCLinkMicControlBar.this.onPLCLinkMicControlBarListener.onClickMicroPhoneOpenOrClose(!PLVLCLinkMicControlBar.this.isMicrophoneOpen);
                }
            }
        });
        this.btnCollapsePortrait.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PLVLCLinkMicControlBar.this.animateMoveToShowSmallestWidth();
                PLVLCLinkMicControlBar.this.tipGradientShowOrHide(false, 300);
                PLVLCLinkMicControlBar.this.btnRingActionPortrait.setRingSettingState();
                PLVLCLinkMicControlBar.this.state = PLVLCLinkMicControllerState.STATE_JOIN_LINK_MIC_SUCCESS_COLLAPSE;
                PLVLCLinkMicControlBar.this.startAutoHideCountDown();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAutoHideCountDown() {
        int i2;
        dispose(this.autoHideDisposable);
        PLVLCLinkMicControllerState pLVLCLinkMicControllerState = this.state;
        if (pLVLCLinkMicControllerState == PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_OPEN) {
            i2 = 5000;
        } else if (pLVLCLinkMicControllerState != PLVLCLinkMicControllerState.STATE_JOIN_LINK_MIC_SUCCESS) {
            return;
        } else {
            i2 = 3000;
        }
        this.autoHideDisposable = PLVRxTimer.delay(i2, new Consumer<Object>() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                PLVLCLinkMicControlBar.this.animateMoveToShowSmallestWidth();
                PLVLCLinkMicControlBar.this.tipGradientShowOrHide(false, 300);
                int i3 = AnonymousClass17.$SwitchMap$com$easefun$polyv$livecloudclass$modules$linkmic$PLVLCLinkMicControlBar$PLVLCLinkMicControllerState[PLVLCLinkMicControlBar.this.state.ordinal()];
                if (i3 == 1) {
                    PLVLCLinkMicControlBar.this.state = PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_OPEN_COLLAPSE;
                } else {
                    if (i3 != 2) {
                        return;
                    }
                    PLVLCLinkMicControlBar.this.state = PLVLCLinkMicControllerState.STATE_JOIN_LINK_MIC_SUCCESS_COLLAPSE;
                    PLVLCLinkMicControlBar.this.btnRingActionPortrait.setRingSettingState();
                }
            }
        });
    }

    private void stopAutoHideCountDown() {
        dispose(this.autoHideDisposable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tipGradientShowOrHide(boolean z2, int i2) {
        ObjectAnimator objectAnimatorOfFloat;
        if (z2) {
            this.tvRequestTip.setVisibility(0);
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.tvRequestTip, "alpha", 0.0f, 1.0f);
        } else {
            objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.tvRequestTip, "alpha", 1.0f, 0.0f);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PLVLCLinkMicControlBar.this.tvRequestTip.setVisibility(4);
                    PLVLCLinkMicControlBar.this.tvRequestTip.setAlpha(1.0f);
                }
            });
        }
        objectAnimatorOfFloat.setDuration(i2);
        objectAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean toastWhenFloatingPlayerShowing() {
        if (!PLVFloatingPlayerManager.getInstance().isFloatingWindowShowing()) {
            return false;
        }
        PLVToast.Builder.context(getContext()).setText("小窗播放中，不支持连麦").show();
        return true;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void hide() {
        PLVCommonLog.d(TAG, "hide");
        setVisibility(4);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        int i2 = configuration.orientation;
        this.isPortrait = i2 == 1;
        setOrientation(i2 == 1);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.7
            @Override // java.lang.Runnable
            public void run() {
                PLVLCLinkMicControlBar.this.startAutoHideCountDown();
            }
        });
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void setCameraOpenOrClose(boolean z2) {
        setBtnCameraOpenState(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void setIsAudio(boolean z2) {
        if (z2) {
            this.btnCameraOpenPortrait.setVisibility(8);
            this.btnCameraOpenLandscape.setVisibility(8);
            this.btnCameraFrontBackPortrait.setVisibility(8);
            this.btnCameraFrontBackLandscape.setVisibility(8);
        } else {
            this.btnCameraOpenPortrait.setVisibility(0);
            this.btnCameraOpenLandscape.setVisibility(0);
            this.btnCameraFrontBackPortrait.setVisibility(0);
            this.btnCameraFrontBackLandscape.setVisibility(0);
        }
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.4
            @Override // java.lang.Runnable
            public void run() {
                PLVLCLinkMicControlBar pLVLCLinkMicControlBar = PLVLCLinkMicControlBar.this;
                pLVLCLinkMicControlBar.biggestStateWidthPortrait = pLVLCLinkMicControlBar.floatingViewPortraitRoot.getWidth();
            }
        });
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void setIsTeacherOpenLinkMic(boolean z2) {
        if (!z2) {
            PLVLCLinkMicControllerState pLVLCLinkMicControllerState = this.state;
            PLVLCLinkMicControllerState pLVLCLinkMicControllerState2 = PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_CLOSE;
            if (pLVLCLinkMicControllerState == pLVLCLinkMicControllerState2) {
                return;
            }
            this.state = pLVLCLinkMicControllerState2;
            this.llLandscapeRoot.setVisibility(8);
            stopAutoHideCountDown();
        } else {
            if (this.state != PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_CLOSE) {
                return;
            }
            this.state = PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_OPEN;
            this.btnRingActionPortrait.setRingUpState();
            this.tvRequestTip.setVisibility(0);
            TextView textView = this.tvRequestTip;
            int i2 = R.string.plv_linkmic_tip_request_link_mic;
            textView.setText(i2);
            this.ll4BtnParent.setVisibility(4);
            this.btnRingActionLandscape.setBackgroundResource(R.drawable.plvlc_linkmic_iv_ring_up);
            this.llFunctionBtnParentLand.setVisibility(8);
            this.tvRequestTipLandscape.setVisibility(0);
            this.tvRequestTipLandscape.setText(i2);
            setOrientation(this.isPortrait);
            startAutoHideCountDown();
        }
        animateLinkMicOpenOrClose(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void setJoinLinkMicSuccess() {
        this.state = PLVLCLinkMicControllerState.STATE_JOIN_LINK_MIC_SUCCESS;
        this.isCameraOpen = false;
        this.isCameraFront = true;
        this.isMicrophoneOpen = true;
        animateMoveToShowBiggestWidth();
        this.tvRequestTip.setVisibility(4);
        this.ll4BtnParent.setVisibility(0);
        this.tvRequestTipLandscape.setVisibility(8);
        this.llFunctionBtnParentLand.setVisibility(0);
        startAutoHideCountDown();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void setLeaveLinkMic() {
        this.isCameraOpen = false;
        this.isCameraFront = true;
        this.isMicrophoneOpen = true;
        this.tvRequestTip.setVisibility(0);
        TextView textView = this.tvRequestTip;
        int i2 = R.string.plv_linkmic_tip_request_link_mic;
        textView.setText(i2);
        this.btnRingActionPortrait.setRingUpState();
        this.ll4BtnParent.setVisibility(4);
        this.btnRingActionLandscape.setBackgroundResource(R.drawable.plvlc_linkmic_iv_ring_up);
        this.tvRequestTipLandscape.setVisibility(0);
        this.tvRequestTipLandscape.setText(i2);
        this.llFunctionBtnParentLand.setVisibility(8);
        if (this.state != PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_CLOSE) {
            this.state = PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_OPEN;
            animateMoveToShowMiddleWidth();
            startAutoHideCountDown();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void setMicrophoneOpenOrClose(boolean z2) {
        setBtnMicrophoneOpenState(z2);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void setOnPLCLinkMicControlBarListener(IPLVLCLinkMicControlBar.OnPLCLinkMicControlBarListener onPLCLinkMicControlBarListener) {
        this.onPLCLinkMicControlBarListener = onPLCLinkMicControlBarListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicControlBar
    public void show() {
        PLVCommonLog.d(TAG, "show");
        setVisibility(0);
    }

    public PLVLCLinkMicControlBar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLinkMicControlBar(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.state = PLVLCLinkMicControllerState.STATE_TEACHER_LINK_MIC_CLOSE;
        this.isCameraOpen = false;
        this.isCameraFront = true;
        this.isMicrophoneOpen = true;
        this.receiver = new BroadcastReceiver() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (!TextUtils.isEmpty(action) && action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    if (PLVNetworkUtils.isConnected(context2)) {
                        PLVCommonLog.d(PLVLCLinkMicControlBar.TAG, "net work connected");
                    } else {
                        PLVCommonLog.d(PLVLCLinkMicControlBar.TAG, "net work disconnected");
                        PLVLCLinkMicControlBar.this.onNetworkDisconnected();
                    }
                }
            }
        };
        initView();
        initBroadcastReceiver(context);
    }
}
