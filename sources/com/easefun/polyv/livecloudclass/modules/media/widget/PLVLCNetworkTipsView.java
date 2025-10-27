package com.easefun.polyv.livecloudclass.modules.media.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.ui.widget.PLVRoundRectGradientTextView;
import com.easefun.polyv.livecommon.ui.widget.roundview.PLVRoundRectLayout;
import com.plv.business.api.common.player.PLVPlayerConstant;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PLVLCNetworkTipsView extends FrameLayout implements View.OnClickListener {
    private static final int HANDLER_MESSAGE_HIDE_CHANGE_LATENCY_TIPS = 1;
    private static final int HANDLER_MESSAGE_HIDE_NET_NOT_GOOD_TIPS = 2;
    private boolean hasShowChangeLatencyTips;
    private boolean hasShowNotGoodTips;
    private boolean isLinkMic;
    private boolean isLowLatency;
    private int lastQuality;
    private final Handler mainHandler;
    private OnClickChangeNormalLatencyListener onClickChangeNormalLatencyListener;
    private PLVRoundRectLayout plvlcLivePlayerNetworkChangeLatencyTipsLayout;
    private TextView plvlcLivePlayerNetworkChangeLatencyTv;
    private ImageView plvlcLivePlayerNetworkCloseBadTipsIv;
    private PLVRoundRectGradientTextView plvlcLivePlayerNetworkNotGoodTipsTv;
    private int qualityCount;

    public interface OnClickChangeNormalLatencyListener {
        void onClickChangeNormalLatency();
    }

    public PLVLCNetworkTipsView(@NonNull Context context) {
        this(context, null);
    }

    private void hide() {
        this.plvlcLivePlayerNetworkNotGoodTipsTv.setVisibility(8);
        this.plvlcLivePlayerNetworkChangeLatencyTipsLayout.setVisibility(8);
        this.mainHandler.removeMessages(1);
        this.mainHandler.removeMessages(2);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_live_player_network_tips_layout, this);
        this.plvlcLivePlayerNetworkNotGoodTipsTv = (PLVRoundRectGradientTextView) findViewById(R.id.plvlc_live_player_network_not_good_tips_tv);
        this.plvlcLivePlayerNetworkChangeLatencyTv = (TextView) findViewById(R.id.plvlc_live_player_network_change_latency_tv);
        this.plvlcLivePlayerNetworkCloseBadTipsIv = (ImageView) findViewById(R.id.plvlc_live_player_network_close_bad_tips_iv);
        this.plvlcLivePlayerNetworkChangeLatencyTipsLayout = (PLVRoundRectLayout) findViewById(R.id.plvlc_live_player_network_change_latency_tips_layout);
        this.plvlcLivePlayerNetworkChangeLatencyTv.setOnClickListener(this);
        this.plvlcLivePlayerNetworkCloseBadTipsIv.setOnClickListener(this);
        hide();
    }

    private void tryShow() {
        if (PLVPlayerConstant.NetQuality.isNoConnection(this.lastQuality)) {
            tryShowNetNotGoodTips();
        } else if (PLVPlayerConstant.NetQuality.isNetPoor(this.lastQuality)) {
            tryShowChangeLatencyTips();
        } else if (PLVPlayerConstant.NetQuality.isNetMiddleOrWorse(this.lastQuality)) {
            tryShowNetNotGoodTips();
        }
    }

    private void tryShowChangeLatencyTips() {
        if (this.hasShowChangeLatencyTips || this.isLinkMic || !this.isLowLatency) {
            return;
        }
        this.mainHandler.removeMessages(1);
        this.plvlcLivePlayerNetworkChangeLatencyTipsLayout.setVisibility(0);
        this.mainHandler.sendEmptyMessageDelayed(1, TimeUnit.SECONDS.toMillis(3L));
        this.hasShowChangeLatencyTips = true;
    }

    private void tryShowNetNotGoodTips() {
        if (this.hasShowNotGoodTips || this.isLinkMic) {
            return;
        }
        this.mainHandler.removeMessages(2);
        this.plvlcLivePlayerNetworkNotGoodTipsTv.setVisibility(0);
        this.mainHandler.sendEmptyMessageDelayed(2, TimeUnit.SECONDS.toMillis(3L));
        this.hasShowNotGoodTips = true;
    }

    public void acceptNetworkQuality(int i2) {
        if (this.lastQuality != i2) {
            this.lastQuality = i2;
            this.qualityCount = 1;
            return;
        }
        int i3 = this.qualityCount + 1;
        this.qualityCount = i3;
        if (i3 >= 3) {
            tryShow();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnClickChangeNormalLatencyListener onClickChangeNormalLatencyListener;
        if (view.getId() == this.plvlcLivePlayerNetworkCloseBadTipsIv.getId()) {
            hide();
        } else {
            if (view.getId() != this.plvlcLivePlayerNetworkChangeLatencyTv.getId() || (onClickChangeNormalLatencyListener = this.onClickChangeNormalLatencyListener) == null) {
                return;
            }
            onClickChangeNormalLatencyListener.onClickChangeNormalLatency();
        }
    }

    public void reset() {
        this.lastQuality = -1;
        this.qualityCount = 0;
        this.hasShowNotGoodTips = false;
        this.hasShowChangeLatencyTips = false;
        hide();
    }

    public void setIsLinkMic(boolean z2) {
        this.isLinkMic = z2;
    }

    public void setIsLowLatency(boolean z2) {
        if (this.isLowLatency != z2) {
            reset();
        }
        this.isLowLatency = z2;
    }

    public void setOnClickChangeNormalLatencyListener(OnClickChangeNormalLatencyListener onClickChangeNormalLatencyListener) {
        this.onClickChangeNormalLatencyListener = onClickChangeNormalLatencyListener;
    }

    public PLVLCNetworkTipsView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCNetworkTipsView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isLinkMic = false;
        this.isLowLatency = PLVLinkMicConfig.getInstance().isLowLatencyWatchEnabled();
        this.lastQuality = -1;
        this.qualityCount = 0;
        this.hasShowNotGoodTips = false;
        this.hasShowChangeLatencyTips = false;
        this.mainHandler = new Handler(Looper.getMainLooper()) { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCNetworkTipsView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i3 = message.what;
                if (i3 == 1) {
                    PLVLCNetworkTipsView.this.plvlcLivePlayerNetworkChangeLatencyTipsLayout.setVisibility(8);
                } else if (i3 == 2) {
                    PLVLCNetworkTipsView.this.plvlcLivePlayerNetworkNotGoodTipsTv.setVisibility(8);
                }
            }
        };
        initView();
    }
}
