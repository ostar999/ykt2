package com.easefun.polyv.livecloudclass.modules.media.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.businesssdk.api.common.player.IPolyvBaseVideoView;
import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayType;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackVideoView;
import com.easefun.polyv.livescenes.video.PolyvLiveVideoView;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import java.util.Locale;

/* loaded from: classes3.dex */
public class PLVLCVideoLoadingLayout extends FrameLayout {
    private static final int DP_LOADING_VIEW_WIDTH_BIG = 36;
    private static final int DP_LOADING_VIEW_WIDTH_SMALL = 28;
    private static final int DP_TEXT_SIZE_BIG = 12;
    private static final int DP_TEXT_SIZE_SMALL = 10;
    private Handler handler;
    private ProgressBar loadingProgress;
    private TextView loadingSpeed;
    private IPolyvBaseVideoView videoView;
    private PLVViewLocationSensor viewLocationSensor;

    public PLVLCVideoLoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    private void acceptVisibilityChange(int i2) {
        this.handler.removeCallbacksAndMessages(null);
        if (i2 == 0) {
            this.handler.sendEmptyMessage(1);
        } else {
            this.loadingSpeed.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatedSpeed(long j2, long j3) {
        if (j3 <= 0 || j2 <= 0) {
            return "0 B/S";
        }
        float f2 = (j2 * 1000.0f) / j3;
        return f2 >= 1000000.0f ? String.format(Locale.US, "%.2f MB/S", Float.valueOf((f2 / 1000.0f) / 1000.0f)) : f2 >= 1000.0f ? String.format(Locale.US, "%.2f KB/S", Float.valueOf(f2 / 1000.0f)) : String.format(Locale.US, "%d B/S", Long.valueOf((long) f2));
    }

    private void initLocationSensor() {
        this.viewLocationSensor = new PLVViewLocationSensor(this, new PLVViewLocationSensor.OnViewLocationSensorListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCVideoLoadingLayout.2
            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onLandscapeBig() {
                PLVLCVideoLoadingLayout.this.loadingSpeed.setTextSize(12.0f);
                PLVLCVideoLoadingLayout.this.loadingProgress.getLayoutParams().width = PLVScreenUtils.dip2px(36.0f);
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onLandscapeSmall() {
                PLVLCVideoLoadingLayout.this.loadingSpeed.setTextSize(10.0f);
                PLVLCVideoLoadingLayout.this.loadingProgress.getLayoutParams().width = PLVScreenUtils.dip2px(28.0f);
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onPortraitBig() {
                PLVLCVideoLoadingLayout.this.loadingSpeed.setTextSize(12.0f);
                PLVLCVideoLoadingLayout.this.loadingProgress.getLayoutParams().width = PLVScreenUtils.dip2px(36.0f);
            }

            @Override // com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.OnViewLocationSensorListener
            public void onPortraitSmall() {
                PLVLCVideoLoadingLayout.this.loadingSpeed.setTextSize(10.0f);
                PLVLCVideoLoadingLayout.this.loadingProgress.getLayoutParams().width = PLVScreenUtils.dip2px(28.0f);
            }
        });
    }

    private void initView() {
        this.loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        this.loadingSpeed = (TextView) findViewById(R.id.loading_speed);
    }

    public void bindVideoView(IPolyvBaseVideoView iPolyvBaseVideoView) {
        this.videoView = iPolyvBaseVideoView;
    }

    public void destroy() {
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        acceptVisibilityChange(getVisibility());
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.viewLocationSensor.onConfigurationChanged(configuration);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.viewLocationSensor.onSizeChanged(i2, i3, i4, i5);
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        acceptVisibilityChange(i2);
    }

    public PLVLCVideoLoadingLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCVideoLoadingLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.handler = new Handler(Looper.getMainLooper()) { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCVideoLoadingLayout.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    if ((PLVLCVideoLoadingLayout.this.videoView instanceof PolyvLiveVideoView) || ((PLVLCVideoLoadingLayout.this.videoView instanceof PolyvPlaybackVideoView) && ((PolyvPlaybackVideoView) PLVLCVideoLoadingLayout.this.videoView).getPlayType() == PolyvPlayType.ONLINE_PLAY)) {
                        long tcpSpeed = PLVLCVideoLoadingLayout.this.videoView.getTcpSpeed();
                        if (tcpSpeed >= 0) {
                            PLVLCVideoLoadingLayout.this.loadingSpeed.setVisibility(0);
                            PLVLCVideoLoadingLayout.this.loadingSpeed.setText(PLVLCVideoLoadingLayout.formatedSpeed(tcpSpeed, 1000L));
                            PLVLCVideoLoadingLayout.this.handler.sendEmptyMessageDelayed(1, 500L);
                        }
                    }
                }
            }
        };
        LayoutInflater.from(context).inflate(R.layout.plvlc_player_video_loading_layout, this);
        initView();
        initLocationSensor();
    }
}
