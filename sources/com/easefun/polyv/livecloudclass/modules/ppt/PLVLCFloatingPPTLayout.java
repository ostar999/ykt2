package com.easefun.polyv.livecloudclass.modules.ppt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout;
import com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVLiveFloatingContract;
import com.easefun.polyv.livecommon.module.modules.ppt.presenter.PLVLiveFloatingPresenter;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.widget.PLVTouchFloatingView;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVLCFloatingPPTLayout extends FrameLayout implements IPLVLiveFloatingContract.IPLVLiveFloatingView, IPLVLCFloatingPPTLayout {
    private static final int DP_FLOATING_PPT_HEIGHT_LAND = 114;
    private static final int DP_FLOATING_PPT_HEIGHT_PORT = 85;
    private static final int DP_FLOATING_PPT_WIDTH_LAND = 202;
    private static final int DP_FLOATING_PPT_WIDTH_PORT = 150;
    private static final int DP_ORIGIN_MARGIN_RIGHT_LANDSCAPE = 16;
    private static final int DP_ORIGIN_MARGIN_TOP_LANDSCAPE = 16;
    private static final int DP_ORIGIN_MARGIN_TOP_PORTRAIT = 373;
    private static final String TAG = "PLVLCFloatingPPTLayout";
    private PLVTouchFloatingView floatingView;
    private boolean isLowLatencyWatch;
    private boolean isServerEnablePPT;
    private ImageView ivClose;
    private IPLVLCFloatingPPTLayout.IPLVOnClickCloseFloatingView onClickCloseListener;
    private View.OnClickListener onFloatingViewClickListener;
    private IPLVLCPPTView pptView;
    private IPLVLiveFloatingContract.IPLVLiveFloatingPresenter presenter;
    private PLVSwitchViewAnchorLayout switchViewAnchorLayout;
    private TextView tvTeacherName;

    public PLVLCFloatingPPTLayout(@NonNull Context context) {
        this(context, null);
    }

    private void initData() {
        PLVLiveFloatingPresenter pLVLiveFloatingPresenter = new PLVLiveFloatingPresenter();
        this.presenter = pLVLiveFloatingPresenter;
        pLVLiveFloatingPresenter.init(this);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_ppt_floating_layout, (ViewGroup) this, true);
        this.pptView = (IPLVLCPPTView) findViewById(R.id.plvlc_ppt_ppt_view);
        this.floatingView = (PLVTouchFloatingView) findViewById(R.id.plvlc_ppt_floating_view);
        this.switchViewAnchorLayout = (PLVSwitchViewAnchorLayout) findViewById(R.id.plvlc_ppt_switch_view_anchor);
        this.tvTeacherName = (TextView) findViewById(R.id.plvlc_ppt_teacher_name);
        this.ivClose = (ImageView) findViewById(R.id.plvlc_ppt_iv_close);
        this.floatingView.setInitLocation(Math.min(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()) - PLVScreenUtils.dip2px(150.0f), PLVScreenUtils.dip2px(373.0f), (Math.max(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()) - PLVScreenUtils.dip2px(202.0f)) - PLVScreenUtils.dip2px(16.0f), PLVScreenUtils.dip2px(16.0f));
        this.floatingView.setIsInterceptTouchEvent(false);
        if (PLVScreenUtils.isLandscape(getContext())) {
            setLandscape();
        } else {
            setPortrait();
        }
        setClickListener();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void setClickListener() {
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCFloatingPPTLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PLVLCFloatingPPTLayout.this.onClickCloseListener != null) {
                    PLVLCFloatingPPTLayout.this.onClickCloseListener.onClickCloseFloatingView();
                }
            }
        });
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCFloatingPPTLayout.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (PLVLCFloatingPPTLayout.this.onFloatingViewClickListener == null) {
                    return true;
                }
                PLVLCFloatingPPTLayout.this.onFloatingViewClickListener.onClick(PLVLCFloatingPPTLayout.this.floatingView);
                return true;
            }
        });
        this.floatingView.setOnTouchListener(new View.OnTouchListener() { // from class: com.easefun.polyv.livecloudclass.modules.ppt.PLVLCFloatingPPTLayout.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    private void setLandscape() {
        ViewGroup.LayoutParams layoutParams = this.floatingView.getLayoutParams();
        layoutParams.width = PLVScreenUtils.dip2px(202.0f);
        layoutParams.height = PLVScreenUtils.dip2px(114.0f);
        this.floatingView.setLayoutParams(layoutParams);
        this.ivClose.setVisibility(8);
    }

    private void setPortrait() {
        ViewGroup.LayoutParams layoutParams = this.floatingView.getLayoutParams();
        layoutParams.width = PLVScreenUtils.dip2px(150.0f);
        layoutParams.height = PLVScreenUtils.dip2px(85.0f);
        this.floatingView.setLayoutParams(layoutParams);
        this.ivClose.setVisibility(0);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public void destroy() {
        this.presenter.destroy();
        this.pptView.destroy();
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public PLVSwitchViewAnchorLayout getPPTSwitchView() {
        return this.switchViewAnchorLayout;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public IPLVLCPPTView getPPTView() {
        if (this.pptView == null) {
            PLVCommonLog.w(TAG, "getPPTView return null");
        }
        return this.pptView;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public void hide() {
        PLVCommonLog.d(TAG, "hide");
        setVisibility(8);
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public boolean isPPTInFloatingLayout() {
        return this.floatingView.findViewById(R.id.plvlc_ppt_ppt_view) != null;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        if (configuration.orientation == 2) {
            setLandscape();
        } else {
            setPortrait();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public void setIsLowLatencyWatch(boolean z2) {
        this.isLowLatencyWatch = z2;
        if (getPPTView() != null) {
            getPPTView().setIsLowLatencyWatch(z2);
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public void setOnClickCloseListener(IPLVLCFloatingPPTLayout.IPLVOnClickCloseFloatingView iPLVOnClickCloseFloatingView) {
        this.onClickCloseListener = iPLVOnClickCloseFloatingView;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public void setOnFloatingViewClickListener(View.OnClickListener onClickListener) {
        this.onFloatingViewClickListener = onClickListener;
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public void setServerEnablePPT(boolean z2) {
        this.isServerEnablePPT = z2;
        if (z2) {
            show();
        } else {
            hide();
        }
    }

    @Override // com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout
    public void show() {
        PLVCommonLog.d(TAG, "show");
        boolean z2 = PLVLinkMicConfig.getInstance().isLowLatencyPureRtcWatch() && this.isLowLatencyWatch;
        if (!this.isServerEnablePPT || z2) {
            return;
        }
        setVisibility(0);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.ppt.contract.IPLVLiveFloatingContract.IPLVLiveFloatingView
    public void updateTeacherInfo(String str, String str2) {
        this.tvTeacherName.setText(str + "-" + str2);
    }

    public PLVLCFloatingPPTLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCFloatingPPTLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isServerEnablePPT = false;
        this.isLowLatencyWatch = PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled();
        initView();
        initData();
    }
}
