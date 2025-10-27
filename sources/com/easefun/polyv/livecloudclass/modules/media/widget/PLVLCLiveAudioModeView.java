package com.easefun.polyv.livecloudclass.modules.media.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor;
import com.easefun.polyv.livescenes.video.api.IPolyvLiveAudioModeView;
import com.plv.foundationsdk.utils.PLVScreenUtils;

/* loaded from: classes3.dex */
public class PLVLCLiveAudioModeView extends ConstraintLayout implements IPolyvLiveAudioModeView {
    private static final int DP_TV_HEIGHT_LAND = 30;
    private static final int DP_TV_HEIGHT_PORT = 24;
    private static final int DP_TV_MARGIN_BOTTOM_LAND = 16;
    private static final int DP_TV_WIDTH_LAND = 96;
    private static final int DP_TV_WIDTH_PORT = 80;
    private static final float IMG_PERCENT_WIDTH_IN_SMALL = 0.6f;
    private static final float PERCENT_WIDTH_IN_MAIN_LAND = 0.38f;
    private static final float PERCENT_WIDTH_IN_MAIN_PORT_AUDIO_MODE = 0.44f;
    private static final String TAG = "PLVLCLiveAudioModeView";
    private ImageView ivAudioModeImg;
    private PLVViewLocationSensor locationSensor;
    private OnChangeVideoModeListener onChangeVideoModeListener;
    private TextView tvPlaceholderAudioModePlayVideo;

    public interface OnChangeVideoModeListener {
        void onClickPlayVideo();
    }

    public PLVLCLiveAudioModeView(@NonNull Context context) {
        this(context, null);
    }

    private void initLocationSensor() {
        this.locationSensor = new PLVViewLocationSensor(this, new PLVViewLocationSensor.OnViewLocationSensorListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView.2
            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onLandscapeBig() {
                PLVLCLiveAudioModeView.this.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVLCLiveAudioModeView.this.setLandscapeBig();
                    }
                });
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onLandscapeSmall() {
                PLVLCLiveAudioModeView.this.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVLCLiveAudioModeView.this.setLandscapeSmall();
                    }
                });
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onPortraitBig() {
                PLVLCLiveAudioModeView.this.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView.2.4
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVLCLiveAudioModeView.this.setPortraitBig();
                    }
                });
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onPortraitSmall() {
                PLVLCLiveAudioModeView.this.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView.2.3
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVLCLiveAudioModeView.this.setPortraitSmall();
                    }
                });
            }
        });
    }

    private void initView() {
        setBackgroundColor(getResources().getColor(R.color.colorEbonyClay));
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_player_audio_mode_view, this);
        this.ivAudioModeImg = (ImageView) findViewById(R.id.plvlc_iv_audio_mode_img);
        TextView textView = (TextView) findViewById(R.id.plvlc_tv_placeholder_audio_mode_play_video);
        this.tvPlaceholderAudioModePlayVideo = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLiveAudioModeView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PLVLCLiveAudioModeView.this.onChangeVideoModeListener != null) {
                    PLVLCLiveAudioModeView.this.onChangeVideoModeListener.onClickPlayVideo();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLandscapeBig() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.ivAudioModeImg.getLayoutParams();
        layoutParams.matchConstraintPercentWidth = 0.38f;
        this.ivAudioModeImg.setLayoutParams(layoutParams);
        this.tvPlaceholderAudioModePlayVideo.setVisibility(0);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.tvPlaceholderAudioModePlayVideo.getLayoutParams();
        marginLayoutParams.bottomMargin = PLVScreenUtils.dip2px(16.0f);
        marginLayoutParams.width = PLVScreenUtils.dip2px(96.0f);
        marginLayoutParams.height = PLVScreenUtils.dip2px(30.0f);
        this.tvPlaceholderAudioModePlayVideo.setLayoutParams(marginLayoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLandscapeSmall() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.ivAudioModeImg.getLayoutParams();
        layoutParams.matchConstraintPercentWidth = IMG_PERCENT_WIDTH_IN_SMALL;
        this.ivAudioModeImg.setLayoutParams(layoutParams);
        this.tvPlaceholderAudioModePlayVideo.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPortraitBig() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.ivAudioModeImg.getLayoutParams();
        layoutParams.matchConstraintPercentWidth = PERCENT_WIDTH_IN_MAIN_PORT_AUDIO_MODE;
        this.ivAudioModeImg.setLayoutParams(layoutParams);
        this.tvPlaceholderAudioModePlayVideo.setVisibility(0);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.tvPlaceholderAudioModePlayVideo.getLayoutParams();
        marginLayoutParams.bottomMargin = 0;
        marginLayoutParams.width = PLVScreenUtils.dip2px(80.0f);
        marginLayoutParams.height = PLVScreenUtils.dip2px(24.0f);
        this.tvPlaceholderAudioModePlayVideo.setLayoutParams(marginLayoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPortraitSmall() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.ivAudioModeImg.getLayoutParams();
        layoutParams.matchConstraintPercentWidth = IMG_PERCENT_WIDTH_IN_SMALL;
        this.ivAudioModeImg.setLayoutParams(layoutParams);
        this.tvPlaceholderAudioModePlayVideo.setVisibility(8);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveAudioModeView
    public View getRoot() {
        return this;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.locationSensor.onConfigurationChanged(configuration);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveAudioModeView
    public void onHide() {
        setVisibility(8);
    }

    @Override // com.plv.livescenes.video.api.IPLVLiveAudioModeView
    public void onShow() {
        setVisibility(0);
        requestLayout();
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.locationSensor.onSizeChanged(i2, i3, i4, i5);
    }

    @Override // android.view.View
    public void onVisibilityChanged(@NonNull View view, int i2) {
        super.onVisibilityChanged(view, i2);
    }

    public void setOnChangeVideoModeListener(OnChangeVideoModeListener onChangeVideoModeListener) {
        this.onChangeVideoModeListener = onChangeVideoModeListener;
    }

    public PLVLCLiveAudioModeView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLiveAudioModeView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        setVisibility(4);
        initView();
        initLocationSensor();
    }
}
