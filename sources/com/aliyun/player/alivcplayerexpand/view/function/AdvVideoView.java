package com.aliyun.player.alivcplayerexpand.view.function;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSts;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class AdvVideoView extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = "AdvVideoView";
    private SurfaceView mAdvSurfaceView;
    private TextView mAdvTipsTextView;
    private AliPlayer mAdvVideoAliyunVodPlayer;
    private ImageView mBackImageView;
    private OnBackImageViewClickListener mOnBackImageViewClickListener;
    private IPlayer.OnCompletionListener mOutOnCompletionListener;
    private IPlayer.OnErrorListener mOutOnErrorListener;
    private IPlayer.OnInfoListener mOutOnInfoListener;
    private IPlayer.OnLoadingStatusListener mOutOnLoadingStatusListener;
    private IPlayer.OnRenderingStartListener mOutOnRenderingStartListener;
    private IPlayer.OnStateChangedListener mOutOnStateChangedListener;
    private IPlayer.OnPreparedListener mOutPreparedListener;
    private int mPlayerState;

    public static class AdvPlayerOnCompletionListener implements IPlayer.OnCompletionListener {
        private WeakReference<AdvVideoView> weakReference;

        public AdvPlayerOnCompletionListener(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // com.aliyun.player.IPlayer.OnCompletionListener
        public void onCompletion() {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnCompletionListener == null) {
                return;
            }
            advVideoView.mOutOnCompletionListener.onCompletion();
            advVideoView.isShowAdvVideoBackIamgeView(false);
            advVideoView.isShowAdvVideoTipsTextView(false);
        }
    }

    public static class AdvPlayerOnErrorListener implements IPlayer.OnErrorListener {
        private WeakReference<AdvVideoView> weakReference;

        public AdvPlayerOnErrorListener(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // com.aliyun.player.IPlayer.OnErrorListener
        public void onError(ErrorInfo errorInfo) {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnErrorListener == null) {
                return;
            }
            advVideoView.mOutOnErrorListener.onError(errorInfo);
        }
    }

    public static class AdvPlayerOnInfoListener implements IPlayer.OnInfoListener {
        private WeakReference<AdvVideoView> weakReference;

        public AdvPlayerOnInfoListener(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // com.aliyun.player.IPlayer.OnInfoListener
        public void onInfo(InfoBean infoBean) {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnInfoListener == null) {
                return;
            }
            advVideoView.mOutOnInfoListener.onInfo(infoBean);
        }
    }

    public static class AdvPlayerOnLoadingStatusListener implements IPlayer.OnLoadingStatusListener {
        private WeakReference<AdvVideoView> weakReference;

        public AdvPlayerOnLoadingStatusListener(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingBegin() {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnLoadingStatusListener == null) {
                return;
            }
            advVideoView.mOutOnLoadingStatusListener.onLoadingBegin();
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingEnd() {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnLoadingStatusListener == null) {
                return;
            }
            advVideoView.mOutOnLoadingStatusListener.onLoadingEnd();
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingProgress(int i2, float f2) {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnLoadingStatusListener == null) {
                return;
            }
            advVideoView.mOutOnLoadingStatusListener.onLoadingProgress(i2, f2);
        }
    }

    public static class AdvPlayerOnPreparedListener implements IPlayer.OnPreparedListener {
        private WeakReference<AdvVideoView> weakReference;

        public AdvPlayerOnPreparedListener(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // com.aliyun.player.IPlayer.OnPreparedListener
        public void onPrepared() {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutPreparedListener == null) {
                return;
            }
            advVideoView.mOutPreparedListener.onPrepared();
        }
    }

    public static class AdvPlayerOnRenderingStartLitener implements IPlayer.OnRenderingStartListener {
        private WeakReference<AdvVideoView> weakReference;

        public AdvPlayerOnRenderingStartLitener(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // com.aliyun.player.IPlayer.OnRenderingStartListener
        public void onRenderingStart() {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnRenderingStartListener == null) {
                return;
            }
            advVideoView.mOutOnRenderingStartListener.onRenderingStart();
            advVideoView.isShowAdvVideoBackIamgeView(true);
            advVideoView.isShowAdvVideoTipsTextView(true);
        }
    }

    public static class AdvPlayerOnStateChangedListener implements IPlayer.OnStateChangedListener {
        private WeakReference<AdvVideoView> weakReference;

        public AdvPlayerOnStateChangedListener(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // com.aliyun.player.IPlayer.OnStateChangedListener
        public void onStateChanged(int i2) {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mOutOnStateChangedListener == null) {
                return;
            }
            advVideoView.mPlayerState = i2;
            advVideoView.mOutOnStateChangedListener.onStateChanged(i2);
        }
    }

    public static class AdvSurfaceHolderCallback implements SurfaceHolder.Callback {
        private WeakReference<AdvVideoView> weakReference;

        public AdvSurfaceHolderCallback(AdvVideoView advVideoView) {
            this.weakReference = new WeakReference<>(advVideoView);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mAdvVideoAliyunVodPlayer == null) {
                return;
            }
            advVideoView.mAdvVideoAliyunVodPlayer.redraw();
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mAdvVideoAliyunVodPlayer == null) {
                return;
            }
            advVideoView.mAdvVideoAliyunVodPlayer.setDisplay(surfaceHolder);
            advVideoView.mAdvVideoAliyunVodPlayer.redraw();
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            AdvVideoView advVideoView = this.weakReference.get();
            if (advVideoView == null || advVideoView.mAdvVideoAliyunVodPlayer == null) {
                return;
            }
            advVideoView.mAdvVideoAliyunVodPlayer.setDisplay(null);
        }
    }

    public enum IntentPlayVideo {
        MIDDLE_END_ADV_SEEK,
        MIDDLE_ADV_SEEK,
        START_ADV,
        MIDDLE_ADV,
        END_ADV,
        REVERSE_SOURCE,
        NORMAL
    }

    public interface OnBackImageViewClickListener {
        void onBackImageViewClick();
    }

    public enum VideoState {
        VIDEO_ADV,
        VIDEO_SOURCE
    }

    public AdvVideoView(Context context) {
        super(context);
        this.mPlayerState = -1;
        init();
    }

    private void addSubView(View view) {
        addView(view, new RelativeLayout.LayoutParams(-1, -1));
    }

    private void addSubViewByGravityRightTop(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.alivc_common_margin_6);
        layoutParams.rightMargin = (int) getResources().getDimension(R.dimen.alivc_common_margin_4);
        addView(view, layoutParams);
    }

    private void addSubViewByWrapContent(View view) {
        addView(view, new RelativeLayout.LayoutParams(-2, -2));
    }

    private void init() {
        initSurfaceView();
        initBackImagView();
        initAdvTipsView();
        initAdvPlayer();
    }

    private void initAdvPlayer() {
        this.mAdvSurfaceView.getHolder().addCallback(new AdvSurfaceHolderCallback(this));
        AliPlayer aliPlayerCreateAliPlayer = AliPlayerFactory.createAliPlayer(getContext().getApplicationContext());
        this.mAdvVideoAliyunVodPlayer = aliPlayerCreateAliPlayer;
        aliPlayerCreateAliPlayer.setAutoPlay(true);
        this.mAdvVideoAliyunVodPlayer.setOnPreparedListener(new AdvPlayerOnPreparedListener(this));
        this.mAdvVideoAliyunVodPlayer.setOnLoadingStatusListener(new AdvPlayerOnLoadingStatusListener(this));
        this.mAdvVideoAliyunVodPlayer.setOnInfoListener(new AdvPlayerOnInfoListener(this));
        this.mAdvVideoAliyunVodPlayer.setOnRenderingStartListener(new AdvPlayerOnRenderingStartLitener(this));
        this.mAdvVideoAliyunVodPlayer.setOnStateChangedListener(new AdvPlayerOnStateChangedListener(this));
        this.mAdvVideoAliyunVodPlayer.setOnCompletionListener(new AdvPlayerOnCompletionListener(this));
        this.mAdvVideoAliyunVodPlayer.setOnErrorListener(new AdvPlayerOnErrorListener(this));
        this.mAdvVideoAliyunVodPlayer.setDisplay(this.mAdvSurfaceView.getHolder());
    }

    private void initAdvTipsView() {
        TextView textView = new TextView(getContext());
        this.mAdvTipsTextView = textView;
        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.alivc_fillet_bg_shape));
        Resources resources = getContext().getResources();
        int i2 = R.dimen.alivc_common_padding_10;
        int dimension = (int) resources.getDimension(i2);
        Resources resources2 = getContext().getResources();
        int i3 = R.dimen.alivc_common_padding_2;
        this.mAdvTipsTextView.setPadding(dimension, (int) resources2.getDimension(i3), (int) getContext().getResources().getDimension(i2), (int) getContext().getResources().getDimension(i3));
        this.mAdvTipsTextView.setTextSize(14.0f);
        this.mAdvTipsTextView.setTextColor(getResources().getColor(R.color.alivc_common_bg_white));
        this.mAdvTipsTextView.setText(getResources().getString(R.string.alivc_adv_video_tips));
        this.mAdvTipsTextView.setGravity(17);
        this.mAdvTipsTextView.setVisibility(8);
        addSubViewByGravityRightTop(this.mAdvTipsTextView);
    }

    private void initBackImagView() {
        ImageView imageView = new ImageView(getContext());
        this.mBackImageView = imageView;
        imageView.setImageResource(R.drawable.ic_back);
        this.mBackImageView.setPadding(20, 20, 20, 20);
        this.mBackImageView.setVisibility(8);
        addSubViewByWrapContent(this.mBackImageView);
        this.mBackImageView.setOnClickListener(this);
    }

    private void initSurfaceView() {
        SurfaceView surfaceView = new SurfaceView(getContext().getApplicationContext());
        this.mAdvSurfaceView = surfaceView;
        addSubView(surfaceView);
    }

    public int getAdvPlayerState() {
        return this.mPlayerState;
    }

    public SurfaceView getAdvSurfaceView() {
        return this.mAdvSurfaceView;
    }

    public AliPlayer getAdvVideoAliyunVodPlayer() {
        return this.mAdvVideoAliyunVodPlayer;
    }

    public void isShowAdvVideoBackIamgeView(boolean z2) {
        ImageView imageView = this.mBackImageView;
        if (imageView != null) {
            imageView.setVisibility(z2 ? 0 : 8);
        }
    }

    public void isShowAdvVideoTipsTextView(boolean z2) {
        TextView textView = this.mAdvTipsTextView;
        if (textView != null) {
            textView.setVisibility(z2 ? 0 : 8);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnBackImageViewClickListener onBackImageViewClickListener;
        if (view != this.mBackImageView || (onBackImageViewClickListener = this.mOnBackImageViewClickListener) == null) {
            return;
        }
        onBackImageViewClickListener.onBackImageViewClick();
    }

    public void optionPause() {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.pause();
        }
    }

    public void optionPrepare() {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.prepare();
        }
    }

    public void optionSetUrlSource(UrlSource urlSource) {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(urlSource);
        }
    }

    public void optionSetVidAuth(VidAuth vidAuth) {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidAuth);
        }
    }

    public void optionSetVidMps(VidMps vidMps) {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidMps);
        }
    }

    public void optionSetVidSts(VidSts vidSts) {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidSts);
        }
    }

    public void optionStart() {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.start();
            isShowAdvVideoBackIamgeView(true);
            isShowAdvVideoTipsTextView(true);
        }
    }

    public void optionStop() {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.stop();
        }
    }

    public void setAutoPlay(boolean z2) {
        AliPlayer aliPlayer = this.mAdvVideoAliyunVodPlayer;
        if (aliPlayer != null) {
            aliPlayer.setAutoPlay(z2);
        }
    }

    public void setOnBackImageViewClickListener(OnBackImageViewClickListener onBackImageViewClickListener) {
        this.mOnBackImageViewClickListener = onBackImageViewClickListener;
    }

    public void setOutOnCompletionListener(IPlayer.OnCompletionListener onCompletionListener) {
        this.mOutOnCompletionListener = onCompletionListener;
    }

    public void setOutOnErrorListener(IPlayer.OnErrorListener onErrorListener) {
        this.mOutOnErrorListener = onErrorListener;
    }

    public void setOutOnInfoListener(IPlayer.OnInfoListener onInfoListener) {
        this.mOutOnInfoListener = onInfoListener;
    }

    public void setOutOnLoadingStatusListener(IPlayer.OnLoadingStatusListener onLoadingStatusListener) {
        this.mOutOnLoadingStatusListener = onLoadingStatusListener;
    }

    public void setOutOnRenderingStartListener(IPlayer.OnRenderingStartListener onRenderingStartListener) {
        this.mOutOnRenderingStartListener = onRenderingStartListener;
    }

    public void setOutOnStateChangedListener(IPlayer.OnStateChangedListener onStateChangedListener) {
        this.mOutOnStateChangedListener = onStateChangedListener;
    }

    public void setOutPreparedListener(IPlayer.OnPreparedListener onPreparedListener) {
        this.mOutPreparedListener = onPreparedListener;
    }

    public void setSurfaceViewVisibility(int i2) {
        this.mAdvSurfaceView.setVisibility(i2);
    }

    public AdvVideoView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPlayerState = -1;
        init();
    }

    public AdvVideoView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mPlayerState = -1;
        init();
    }
}
