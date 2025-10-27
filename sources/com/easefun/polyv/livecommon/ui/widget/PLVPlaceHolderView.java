package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.player.IPLVPlayErrorView;
import com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor;
import com.plv.foundationsdk.annos.Sp;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVPlaceHolderView extends ConstraintLayout implements IPLVPlayErrorView {
    private static final float IMG_PERCENT_WIDTH_IN_SMALL = 0.6f;
    private static final float PERCENT_WIDTH_IN_MAIN_LAND = 0.38f;
    private static final float PERCENT_WIDTH_IN_MAIN_PORT = 0.48f;
    private static final int TEXT_SIZE_IN_MAIN_LAND = 14;
    private static final int TEXT_SIZE_IN_MAIN_PORT = 12;
    private static final float VERTICAL_BIAS_LAND = 0.72f;
    private static final float VERTICAL_BIAS_PORT = 0.8f;
    private TextView changeLinesTv;
    private boolean isShowChangeLinesView;
    private boolean isShowRefreshView;
    private ImageView ivPlaceholderImg;
    private PLVViewLocationSensor locationSensor;
    private TextView refreshTv;
    private boolean respondLocationSensor;
    private TextView tvPlaceholderText;
    private static final int RIGHT_MARGIN_IN_MAIN_LAND = ConvertUtils.dp2px(24.0f);
    private static final int RIGHT_MARGIN_IN_MAIN_PORT = ConvertUtils.dp2px(16.0f);
    private static final int PADDING_IN_MAIN_LAND = ConvertUtils.dp2px(8.0f);
    private static final int PADDING_IN_MAIN_PORT = ConvertUtils.dp2px(4.0f);

    public PLVPlaceHolderView(@NonNull Context context) {
        this(context, null);
    }

    private void initLocationSensor() {
        this.locationSensor = new PLVViewLocationSensor(this, new PLVViewLocationSensor.OnViewLocationSensorListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView.1
            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onLandscapeBig() {
                if (PLVPlaceHolderView.this.respondLocationSensor) {
                    PLVPlaceHolderView.this.setLandscapeBig();
                }
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onLandscapeSmall() {
                if (PLVPlaceHolderView.this.respondLocationSensor) {
                    PLVPlaceHolderView.this.setLandscapeSmall();
                }
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onPortraitBig() {
                if (PLVPlaceHolderView.this.respondLocationSensor) {
                    PLVPlaceHolderView.this.setPortraitBig();
                }
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onPortraitSmall() {
                if (PLVPlaceHolderView.this.respondLocationSensor) {
                    PLVPlaceHolderView.this.setPortraitSmall();
                }
            }
        });
    }

    private void initView() {
        setBackgroundColor(getResources().getColor(R.color.colorEbonyClay));
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_player_nostream_view, (ViewGroup) this, true);
        this.ivPlaceholderImg = (ImageView) findViewById(R.id.plvlc_iv_placeholder_img);
        this.tvPlaceholderText = (TextView) findViewById(R.id.plvlc_tv_placeholder_text);
        this.changeLinesTv = (TextView) findViewById(R.id.plv_change_lines_tv);
        this.refreshTv = (TextView) findViewById(R.id.plv_refresh_tv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLandscapeBig() {
        post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView.4
            @Override // java.lang.Runnable
            public void run() {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.ivPlaceholderImg.getLayoutParams();
                layoutParams.matchConstraintPercentWidth = 0.38f;
                PLVPlaceHolderView.this.ivPlaceholderImg.setLayoutParams(layoutParams);
                PLVPlaceHolderView.this.tvPlaceholderText.setVisibility(0);
                PLVPlaceHolderView.this.tvPlaceholderText.setTextSize(14.0f);
                ((ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.refreshTv.getLayoutParams()).verticalBias = PLVPlaceHolderView.VERTICAL_BIAS_LAND;
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.changeLinesTv.getLayoutParams();
                layoutParams2.verticalBias = PLVPlaceHolderView.VERTICAL_BIAS_LAND;
                ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin = PLVPlaceHolderView.RIGHT_MARGIN_IN_MAIN_LAND;
                if (PLVPlaceHolderView.this.isShowChangeLinesView) {
                    PLVPlaceHolderView.this.changeLinesTv.setVisibility(0);
                }
                if (PLVPlaceHolderView.this.isShowRefreshView) {
                    PLVPlaceHolderView.this.refreshTv.setVisibility(0);
                }
                PLVPlaceHolderView.this.changeLinesTv.setPadding(0, PLVPlaceHolderView.PADDING_IN_MAIN_LAND, 0, PLVPlaceHolderView.PADDING_IN_MAIN_LAND);
                PLVPlaceHolderView.this.refreshTv.setPadding(0, PLVPlaceHolderView.PADDING_IN_MAIN_LAND, 0, PLVPlaceHolderView.PADDING_IN_MAIN_LAND);
                PLVPlaceHolderView.this.changeLinesTv.setTextSize(14.0f);
                PLVPlaceHolderView.this.refreshTv.setTextSize(14.0f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLandscapeSmall() {
        post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView.5
            @Override // java.lang.Runnable
            public void run() {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.ivPlaceholderImg.getLayoutParams();
                layoutParams.matchConstraintPercentWidth = PLVPlaceHolderView.IMG_PERCENT_WIDTH_IN_SMALL;
                PLVPlaceHolderView.this.ivPlaceholderImg.setLayoutParams(layoutParams);
                PLVPlaceHolderView.this.tvPlaceholderText.setVisibility(8);
                PLVPlaceHolderView.this.changeLinesTv.setVisibility(8);
                PLVPlaceHolderView.this.refreshTv.setVisibility(8);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPortraitBig() {
        post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView.2
            @Override // java.lang.Runnable
            public void run() {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.ivPlaceholderImg.getLayoutParams();
                layoutParams.matchConstraintPercentWidth = PLVPlaceHolderView.PERCENT_WIDTH_IN_MAIN_PORT;
                PLVPlaceHolderView.this.ivPlaceholderImg.setLayoutParams(layoutParams);
                PLVPlaceHolderView.this.tvPlaceholderText.setVisibility(0);
                PLVPlaceHolderView.this.tvPlaceholderText.setTextSize(12.0f);
                ((ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.refreshTv.getLayoutParams()).verticalBias = PLVPlaceHolderView.VERTICAL_BIAS_PORT;
                ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.changeLinesTv.getLayoutParams();
                layoutParams2.verticalBias = PLVPlaceHolderView.VERTICAL_BIAS_PORT;
                ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin = PLVPlaceHolderView.RIGHT_MARGIN_IN_MAIN_PORT;
                if (PLVPlaceHolderView.this.isShowChangeLinesView) {
                    PLVPlaceHolderView.this.changeLinesTv.setVisibility(0);
                }
                if (PLVPlaceHolderView.this.isShowRefreshView) {
                    PLVPlaceHolderView.this.refreshTv.setVisibility(0);
                }
                PLVPlaceHolderView.this.changeLinesTv.setPadding(0, PLVPlaceHolderView.PADDING_IN_MAIN_PORT, 0, PLVPlaceHolderView.PADDING_IN_MAIN_PORT);
                PLVPlaceHolderView.this.refreshTv.setPadding(0, PLVPlaceHolderView.PADDING_IN_MAIN_PORT, 0, PLVPlaceHolderView.PADDING_IN_MAIN_PORT);
                PLVPlaceHolderView.this.changeLinesTv.setTextSize(12.0f);
                PLVPlaceHolderView.this.refreshTv.setTextSize(12.0f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPortraitSmall() {
        post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVPlaceHolderView.3
            @Override // java.lang.Runnable
            public void run() {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) PLVPlaceHolderView.this.ivPlaceholderImg.getLayoutParams();
                layoutParams.matchConstraintPercentWidth = PLVPlaceHolderView.IMG_PERCENT_WIDTH_IN_SMALL;
                PLVPlaceHolderView.this.ivPlaceholderImg.setLayoutParams(layoutParams);
                PLVPlaceHolderView.this.tvPlaceholderText.setVisibility(8);
                PLVPlaceHolderView.this.changeLinesTv.setVisibility(8);
                PLVPlaceHolderView.this.refreshTv.setVisibility(8);
            }
        });
    }

    public void enableRespondLocationSensor(boolean enable) {
        this.respondLocationSensor = enable;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        this.locationSensor.onConfigurationChanged(newConfig);
    }

    @Override // android.view.View
    public void onSizeChanged(final int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        this.locationSensor.onSizeChanged(w2, h2, oldw, oldh);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.IPLVPlayErrorView
    public void setChangeLinesViewVisibility(int visibility) {
        this.changeLinesTv.setVisibility(visibility);
        this.isShowChangeLinesView = visibility == 0;
    }

    public void setOnChangeLinesViewClickListener(View.OnClickListener listener) {
        this.changeLinesTv.setOnClickListener(listener);
    }

    public void setOnRefreshViewClickListener(View.OnClickListener listener) {
        this.refreshTv.setOnClickListener(listener);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.IPLVPlayErrorView
    public void setPlaceHolderImg(@DrawableRes int resId) {
        this.ivPlaceholderImg.setImageResource(resId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.IPLVPlayErrorView
    public void setPlaceHolderText(String text) {
        this.tvPlaceholderText.setText(text);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.IPLVPlayErrorView
    public void setPlaceHolderTextSize(@Sp float size) {
        this.tvPlaceholderText.setTextSize(size);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.IPLVPlayErrorView
    public void setRefreshViewVisibility(int visibility) {
        this.refreshTv.setVisibility(visibility);
        this.isShowRefreshView = visibility == 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.player.IPLVPlayErrorView
    public void setViewVisibility(int visibility) {
        setVisibility(visibility);
    }

    public PLVPlaceHolderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVPlaceHolderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isShowChangeLinesView = false;
        this.isShowRefreshView = false;
        this.respondLocationSensor = true;
        initView();
        initLocationSensor();
    }
}
