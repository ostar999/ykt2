package com.aliyun.player.alivcplayerexpand.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.widget.FrameLayout;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.alivcplayerexpand.widget.IRenderView;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.bean.InfoBean;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.LiveSts;
import com.aliyun.player.source.StsInfo;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidMps;
import com.aliyun.player.source.VidSts;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class AliyunRenderView extends FrameLayout {
    private AliPlayer mAliPlayer;
    private Context mContext;
    private boolean mCurrentEnableHardwareDecoder;
    private IRenderView mIRenderView;
    private IPlayer.OnCompletionListener mOnCompletionListener;
    private IPlayer.OnErrorListener mOnErrorListener;
    private IPlayer.OnInfoListener mOnInfoListener;
    private IPlayer.OnLoadingStatusListener mOnLoadingStatusListener;
    private IPlayer.OnPreparedListener mOnPreparedListener;
    private IPlayer.OnRenderingStartListener mOnRenderingStartListener;
    private IPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private IPlayer.OnSeiDataListener mOnSeiDataListener;
    private IPlayer.OnSnapShotListener mOnSnapShotListener;
    private IPlayer.OnStateChangedListener mOnStateChangedListener;
    private IPlayer.OnSubtitleDisplayListener mOnSubtitleDisplayListener;
    private IPlayer.OnTrackChangedListener mOnTrackChangedListener;
    private IPlayer.OnTrackReadyListener mOnTrackReadyListener;
    private AliPlayer.OnVerifyTimeExpireCallback mOnVerifyTimeExpireCallback;
    private IPlayer.OnVideoRenderedListener mOnVideoRenderedListener;
    private IPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private OnVideoStreamTrackTypeListener mOnVideoStreamTrackTypeListener;
    private Surface mSurface;

    public static class MyRenderViewCallback implements IRenderView.IRenderCallback {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.alivcplayerexpand.widget.IRenderView.IRenderCallback
        public void onSurfaceChanged(int i2, int i3) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView == null || aliyunRenderView.mAliPlayer == null) {
                return;
            }
            aliyunRenderView.mAliPlayer.surfaceChanged();
        }

        @Override // com.aliyun.player.alivcplayerexpand.widget.IRenderView.IRenderCallback
        public void onSurfaceCreate(Surface surface) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView == null || aliyunRenderView.mAliPlayer == null) {
                return;
            }
            aliyunRenderView.mSurface = surface;
            aliyunRenderView.mAliPlayer.setSurface(surface);
        }

        @Override // com.aliyun.player.alivcplayerexpand.widget.IRenderView.IRenderCallback
        public void onSurfaceDestroyed() {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView == null || aliyunRenderView.mAliPlayer == null) {
                return;
            }
            aliyunRenderView.mAliPlayer.setSurface(null);
        }

        private MyRenderViewCallback(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPCompletionListener implements IPlayer.OnCompletionListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnCompletionListener
        public void onCompletion() {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onCompletion();
            }
        }

        private OnAVPCompletionListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPErrorListener implements IPlayer.OnErrorListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnErrorListener
        public void onError(ErrorInfo errorInfo) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onError(errorInfo);
            }
        }

        private OnAVPErrorListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPInfoListener implements IPlayer.OnInfoListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnInfoListener
        public void onInfo(InfoBean infoBean) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onInfo(infoBean);
            }
        }

        private OnAVPInfoListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPLoadingStatusListener implements IPlayer.OnLoadingStatusListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingBegin() {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onLoadingBegin();
            }
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingEnd() {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onLoadingEnd();
            }
        }

        @Override // com.aliyun.player.IPlayer.OnLoadingStatusListener
        public void onLoadingProgress(int i2, float f2) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onLoadingProgress(i2, f2);
            }
        }

        private OnAVPLoadingStatusListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPPreparedListener implements IPlayer.OnPreparedListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnPreparedListener
        public void onPrepared() {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onPrepared();
            }
        }

        private OnAVPPreparedListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPRenderingStartListener implements IPlayer.OnRenderingStartListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnRenderingStartListener
        public void onRenderingStart() {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onRenderingStart();
            }
        }

        private OnAVPRenderingStartListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPSeekCompleteListener implements IPlayer.OnSeekCompleteListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnSeekCompleteListener
        public void onSeekComplete() {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onSeekComplete();
            }
        }

        private OnAVPSeekCompleteListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPSeiDataListener implements IPlayer.OnSeiDataListener {
        private WeakReference<AliyunRenderView> weakReference;

        public OnAVPSeiDataListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }

        @Override // com.aliyun.player.IPlayer.OnSeiDataListener
        public void onSeiData(int i2, byte[] bArr) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onSeiData(i2, bArr);
            }
        }
    }

    public static class OnAVPSnapShotListener implements IPlayer.OnSnapShotListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnSnapShotListener
        public void onSnapShot(Bitmap bitmap, int i2, int i3) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onSnapShot(bitmap, i2, i3);
            }
        }

        private OnAVPSnapShotListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPStateChangedListener implements IPlayer.OnStateChangedListener {
        private WeakReference<AliyunRenderView> weakReference;

        public OnAVPStateChangedListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }

        @Override // com.aliyun.player.IPlayer.OnStateChangedListener
        public void onStateChanged(int i2) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onStateChangedListener(i2);
            }
        }
    }

    public static class OnAVPSubtitleDisplayListener implements IPlayer.OnSubtitleDisplayListener {
        private WeakReference<AliyunRenderView> weakReference;

        public OnAVPSubtitleDisplayListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleExtAdded(int i2, String str) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onSubtitleExtAdded(i2, str);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleHeader(int i2, String str) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.oSubtitleHeader(i2, str);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleHide(int i2, long j2) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onSubtitleHide(i2, j2);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnSubtitleDisplayListener
        public void onSubtitleShow(int i2, long j2, String str) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onSubtitleShow(i2, j2, str);
            }
        }
    }

    public static class OnAVPTrackChangedListener implements IPlayer.OnTrackChangedListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
        public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onChangedFail(trackInfo, errorInfo);
            }
        }

        @Override // com.aliyun.player.IPlayer.OnTrackChangedListener
        public void onChangedSuccess(TrackInfo trackInfo) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onChangedSuccess(trackInfo);
            }
        }

        private OnAVPTrackChangedListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPTrackReadyListener implements IPlayer.OnTrackReadyListener {
        private WeakReference<AliyunRenderView> mRenderViewWeakReference;

        public OnAVPTrackReadyListener(AliyunRenderView aliyunRenderView) {
            this.mRenderViewWeakReference = new WeakReference<>(aliyunRenderView);
        }

        @Override // com.aliyun.player.IPlayer.OnTrackReadyListener
        public void onTrackReady(MediaInfo mediaInfo) {
            AliyunRenderView aliyunRenderView = this.mRenderViewWeakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onTrackReady(mediaInfo);
            }
        }
    }

    public static class OnAVPVerifyStsCallback implements AliPlayer.OnVerifyTimeExpireCallback {
        private WeakReference<AliyunRenderView> weakReference;

        public OnAVPVerifyStsCallback(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }

        @Override // com.aliyun.player.AliPlayer.OnVerifyTimeExpireCallback
        public AliPlayer.Status onVerifyAuth(VidAuth vidAuth) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            return aliyunRenderView != null ? aliyunRenderView.onVerifyAuth(vidAuth) : AliPlayer.Status.Valid;
        }

        @Override // com.aliyun.player.AliPlayer.OnVerifyTimeExpireCallback
        public AliPlayer.Status onVerifySts(StsInfo stsInfo) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            return aliyunRenderView != null ? aliyunRenderView.onVerifySts(stsInfo) : AliPlayer.Status.Valid;
        }
    }

    public static class OnAVPVideoRenderedListener implements IPlayer.OnVideoRenderedListener {
        private WeakReference<AliyunRenderView> weakReference;

        @Override // com.aliyun.player.IPlayer.OnVideoRenderedListener
        public void onVideoRendered(long j2, long j3) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onVideoRendered(j2, j3);
            }
        }

        private OnAVPVideoRenderedListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }
    }

    public static class OnAVPVideoSizeChangedListener implements IPlayer.OnVideoSizeChangedListener {
        private WeakReference<AliyunRenderView> weakReference;

        public OnAVPVideoSizeChangedListener(AliyunRenderView aliyunRenderView) {
            this.weakReference = new WeakReference<>(aliyunRenderView);
        }

        @Override // com.aliyun.player.IPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(int i2, int i3) {
            AliyunRenderView aliyunRenderView = this.weakReference.get();
            if (aliyunRenderView != null) {
                aliyunRenderView.onVideoSizeChanged(i2, i3);
            }
        }
    }

    public interface OnVideoStreamTrackTypeListener {
        void onAudioOnlyType();

        void onVideoOnlyType();
    }

    public enum SurfaceType {
        TEXTURE_VIEW,
        SURFACE_VIEW
    }

    public AliyunRenderView(Context context) {
        super(context);
        this.mCurrentEnableHardwareDecoder = true;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initPlayer();
    }

    private void initListener() {
        this.mIRenderView.addRenderCallback(new MyRenderViewCallback());
    }

    private void initPlayer() {
        this.mAliPlayer = AliPlayerFactory.createAliPlayer(this.mContext.getApplicationContext());
        initPlayerListener();
    }

    private void initPlayerListener() {
        this.mAliPlayer.setOnInfoListener(new OnAVPInfoListener());
        this.mAliPlayer.setOnTrackReadyListener(new OnAVPTrackReadyListener(this));
        this.mAliPlayer.setOnErrorListener(new OnAVPErrorListener());
        this.mAliPlayer.setOnSeiDataListener(new OnAVPSeiDataListener(this));
        this.mAliPlayer.setOnSnapShotListener(new OnAVPSnapShotListener());
        this.mAliPlayer.setOnPreparedListener(new OnAVPPreparedListener());
        this.mAliPlayer.setOnCompletionListener(new OnAVPCompletionListener());
        this.mAliPlayer.setOnTrackChangedListener(new OnAVPTrackChangedListener());
        this.mAliPlayer.setOnSeekCompleteListener(new OnAVPSeekCompleteListener());
        this.mAliPlayer.setOnVideoRenderedListener(new OnAVPVideoRenderedListener());
        this.mAliPlayer.setOnLoadingStatusListener(new OnAVPLoadingStatusListener());
        this.mAliPlayer.setOnRenderingStartListener(new OnAVPRenderingStartListener());
        this.mAliPlayer.setOnVerifyTimeExpireCallback(new OnAVPVerifyStsCallback(this));
        this.mAliPlayer.setOnStateChangedListener(new OnAVPStateChangedListener(this));
        this.mAliPlayer.setOnSubtitleDisplayListener(new OnAVPSubtitleDisplayListener(this));
        this.mAliPlayer.setOnVideoSizeChangedListener(new OnAVPVideoSizeChangedListener(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void oSubtitleHeader(int i2, String str) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleHeader(i2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangedFail(TrackInfo trackInfo, ErrorInfo errorInfo) {
        IPlayer.OnTrackChangedListener onTrackChangedListener = this.mOnTrackChangedListener;
        if (onTrackChangedListener != null) {
            onTrackChangedListener.onChangedFail(trackInfo, errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangedSuccess(TrackInfo trackInfo) {
        IPlayer.OnTrackChangedListener onTrackChangedListener = this.mOnTrackChangedListener;
        if (onTrackChangedListener != null) {
            onTrackChangedListener.onChangedSuccess(trackInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCompletion() {
        IPlayer.OnCompletionListener onCompletionListener = this.mOnCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(ErrorInfo errorInfo) {
        IPlayer.OnErrorListener onErrorListener = this.mOnErrorListener;
        if (onErrorListener != null) {
            onErrorListener.onError(errorInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInfo(InfoBean infoBean) {
        IPlayer.OnInfoListener onInfoListener = this.mOnInfoListener;
        if (onInfoListener != null) {
            onInfoListener.onInfo(infoBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadingBegin() {
        IPlayer.OnLoadingStatusListener onLoadingStatusListener = this.mOnLoadingStatusListener;
        if (onLoadingStatusListener != null) {
            onLoadingStatusListener.onLoadingBegin();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadingEnd() {
        IPlayer.OnLoadingStatusListener onLoadingStatusListener = this.mOnLoadingStatusListener;
        if (onLoadingStatusListener != null) {
            onLoadingStatusListener.onLoadingEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadingProgress(int i2, float f2) {
        IPlayer.OnLoadingStatusListener onLoadingStatusListener = this.mOnLoadingStatusListener;
        if (onLoadingStatusListener != null) {
            onLoadingStatusListener.onLoadingProgress(i2, f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPrepared() {
        IPlayer.OnPreparedListener onPreparedListener = this.mOnPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared();
        }
        if (this.mOnVideoStreamTrackTypeListener != null) {
            TrackInfo trackInfoCurrentTrack = this.mAliPlayer.currentTrack(TrackInfo.Type.TYPE_VIDEO);
            TrackInfo trackInfoCurrentTrack2 = this.mAliPlayer.currentTrack(TrackInfo.Type.TYPE_AUDIO);
            if (trackInfoCurrentTrack == null && trackInfoCurrentTrack2 != null) {
                this.mOnVideoStreamTrackTypeListener.onAudioOnlyType();
            } else {
                if (trackInfoCurrentTrack == null || trackInfoCurrentTrack2 != null) {
                    return;
                }
                this.mOnVideoStreamTrackTypeListener.onVideoOnlyType();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRenderingStart() {
        IPlayer.OnRenderingStartListener onRenderingStartListener = this.mOnRenderingStartListener;
        if (onRenderingStartListener != null) {
            onRenderingStartListener.onRenderingStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSeekComplete() {
        IPlayer.OnSeekCompleteListener onSeekCompleteListener = this.mOnSeekCompleteListener;
        if (onSeekCompleteListener != null) {
            onSeekCompleteListener.onSeekComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSeiData(int i2, byte[] bArr) {
        IPlayer.OnSeiDataListener onSeiDataListener = this.mOnSeiDataListener;
        if (onSeiDataListener != null) {
            onSeiDataListener.onSeiData(i2, bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSnapShot(Bitmap bitmap, int i2, int i3) {
        IPlayer.OnSnapShotListener onSnapShotListener = this.mOnSnapShotListener;
        if (onSnapShotListener != null) {
            onSnapShotListener.onSnapShot(bitmap, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStateChangedListener(int i2) {
        IPlayer.OnStateChangedListener onStateChangedListener = this.mOnStateChangedListener;
        if (onStateChangedListener != null) {
            onStateChangedListener.onStateChanged(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleExtAdded(int i2, String str) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleExtAdded(i2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleHide(int i2, long j2) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleHide(i2, j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubtitleShow(int i2, long j2, String str) {
        IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener = this.mOnSubtitleDisplayListener;
        if (onSubtitleDisplayListener != null) {
            onSubtitleDisplayListener.onSubtitleShow(i2, j2, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTrackReady(MediaInfo mediaInfo) {
        IPlayer.OnTrackReadyListener onTrackReadyListener = this.mOnTrackReadyListener;
        if (onTrackReadyListener != null) {
            onTrackReadyListener.onTrackReady(mediaInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AliPlayer.Status onVerifyAuth(VidAuth vidAuth) {
        AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback = this.mOnVerifyTimeExpireCallback;
        return onVerifyTimeExpireCallback != null ? onVerifyTimeExpireCallback.onVerifyAuth(vidAuth) : AliPlayer.Status.Valid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AliPlayer.Status onVerifySts(StsInfo stsInfo) {
        AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback = this.mOnVerifyTimeExpireCallback;
        return onVerifyTimeExpireCallback != null ? onVerifyTimeExpireCallback.onVerifySts(stsInfo) : AliPlayer.Status.Valid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVideoRendered(long j2, long j3) {
        IPlayer.OnVideoRenderedListener onVideoRenderedListener = this.mOnVideoRenderedListener;
        if (onVideoRenderedListener != null) {
            onVideoRenderedListener.onVideoRendered(j2, j3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVideoSizeChanged(int i2, int i3) {
        IPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = this.mOnVideoSizeChangedListener;
        if (onVideoSizeChangedListener != null) {
            onVideoSizeChangedListener.onVideoSizeChanged(i2, i3);
        }
    }

    public TrackInfo currentTrack(TrackInfo.Type type) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            return aliPlayer.currentTrack(type);
        }
        return null;
    }

    public void enableHardwareDecoder(boolean z2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            this.mCurrentEnableHardwareDecoder = z2;
            aliPlayer.enableHardwareDecoder(z2);
        }
    }

    public AliPlayer getAliPlayer() {
        return this.mAliPlayer;
    }

    public long getDuration() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            return aliPlayer.getDuration();
        }
        return 0L;
    }

    public MediaInfo getMediaInfo() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            return aliPlayer.getMediaInfo();
        }
        return null;
    }

    public IPlayer.MirrorMode getMirrorMode() {
        AliPlayer aliPlayer = this.mAliPlayer;
        return aliPlayer != null ? aliPlayer.getMirrorMode() : IPlayer.MirrorMode.MIRROR_MODE_NONE;
    }

    public PlayerConfig getPlayerConfig() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            return aliPlayer.getConfig();
        }
        return null;
    }

    public IPlayer.RotateMode getRotateModel() {
        AliPlayer aliPlayer = this.mAliPlayer;
        return aliPlayer != null ? aliPlayer.getRotateMode() : IPlayer.RotateMode.ROTATE_0;
    }

    public IPlayer.ScaleMode getScaleModel() {
        AliPlayer aliPlayer = this.mAliPlayer;
        return aliPlayer != null ? aliPlayer.getScaleMode() : IPlayer.ScaleMode.SCALE_ASPECT_FIT;
    }

    public float getVolume() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            return aliPlayer.getVolume();
        }
        return 0.0f;
    }

    public boolean isHardwareDecoder() {
        return this.mCurrentEnableHardwareDecoder;
    }

    public boolean isLoop() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            return aliPlayer.isLoop();
        }
        return false;
    }

    public void pause() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.pause();
        }
    }

    public void prepare() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.prepare();
        }
    }

    public void release() {
        if (this.mAliPlayer != null) {
            stop();
            this.mAliPlayer.setSurface(null);
            this.mAliPlayer.release();
            this.mAliPlayer = null;
        }
        this.mSurface = null;
    }

    public void reload() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.reload();
        }
    }

    public void seekTo(long j2, IPlayer.SeekMode seekMode) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.seekTo(j2, seekMode);
        }
    }

    public void selectExtSubtitle(int i2, boolean z2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.selectExtSubtitle(i2, z2);
        }
    }

    public void selectTrack(int i2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.selectTrack(i2);
        }
    }

    public void setAutoPlay(boolean z2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setAutoPlay(z2);
        }
    }

    public void setCacheConfig(CacheConfig cacheConfig) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setCacheConfig(cacheConfig);
        }
    }

    public void setDataSource(VidSts vidSts) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidSts);
        }
    }

    public void setDefaultBandWidth(int i2) {
        if (this.mAliPlayer != null) {
            Log.e("abc : ", "setDefaultBandWidth: " + i2);
            this.mAliPlayer.setDefaultBandWidth(i2);
        }
    }

    public void setLoop(boolean z2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setLoop(z2);
        }
    }

    public void setMirrorMode(IPlayer.MirrorMode mirrorMode) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setMirrorMode(mirrorMode);
        }
    }

    public void setMute(boolean z2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setMute(z2);
        }
    }

    public void setOnCompletionListener(IPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(IPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(IPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnLoadingStatusListener(IPlayer.OnLoadingStatusListener onLoadingStatusListener) {
        this.mOnLoadingStatusListener = onLoadingStatusListener;
    }

    public void setOnPreparedListener(IPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnRenderingStartListener(IPlayer.OnRenderingStartListener onRenderingStartListener) {
        this.mOnRenderingStartListener = onRenderingStartListener;
    }

    public void setOnSeekCompleteListener(IPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnSeiDataListener(IPlayer.OnSeiDataListener onSeiDataListener) {
        this.mOnSeiDataListener = onSeiDataListener;
    }

    public void setOnSnapShotListener(IPlayer.OnSnapShotListener onSnapShotListener) {
        this.mOnSnapShotListener = onSnapShotListener;
    }

    public void setOnStateChangedListener(IPlayer.OnStateChangedListener onStateChangedListener) {
        this.mOnStateChangedListener = onStateChangedListener;
    }

    public void setOnSubtitleDisplayListener(IPlayer.OnSubtitleDisplayListener onSubtitleDisplayListener) {
        this.mOnSubtitleDisplayListener = onSubtitleDisplayListener;
    }

    public void setOnTrackChangedListener(IPlayer.OnTrackChangedListener onTrackChangedListener) {
        this.mOnTrackChangedListener = onTrackChangedListener;
    }

    public void setOnTrackReadyListenenr(IPlayer.OnTrackReadyListener onTrackReadyListener) {
        this.mOnTrackReadyListener = onTrackReadyListener;
    }

    public void setOnVerifyTimeExpireCallback(AliPlayer.OnVerifyTimeExpireCallback onVerifyTimeExpireCallback) {
        this.mOnVerifyTimeExpireCallback = onVerifyTimeExpireCallback;
    }

    public void setOnVideoRenderedListener(IPlayer.OnVideoRenderedListener onVideoRenderedListener) {
        this.mOnVideoRenderedListener = onVideoRenderedListener;
    }

    public void setOnVideoSizeChangedListener(IPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public void setOnVideoStreamTrackType(OnVideoStreamTrackTypeListener onVideoStreamTrackTypeListener) {
        this.mOnVideoStreamTrackTypeListener = onVideoStreamTrackTypeListener;
    }

    public void setPlayerConfig(PlayerConfig playerConfig) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setConfig(playerConfig);
        }
    }

    public void setRotateModel(IPlayer.RotateMode rotateMode) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setRotateMode(rotateMode);
        }
    }

    public void setScaleModel(IPlayer.ScaleMode scaleMode) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setScaleMode(scaleMode);
        }
    }

    public void setSpeed(float f2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setSpeed(f2);
        }
    }

    public void setSurfaceType(SurfaceType surfaceType) {
        if (surfaceType == SurfaceType.TEXTURE_VIEW) {
            this.mIRenderView = new TextureRenderView(this.mContext);
        } else {
            this.mIRenderView = new SurfaceRenderView(this.mContext);
        }
        initListener();
        addView(this.mIRenderView.getView());
    }

    public void setVolume(float f2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setVolume(f2);
        }
    }

    public void snapshot() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.snapshot();
        }
    }

    public void start() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.start();
        }
    }

    public void stop() {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.stop();
        }
    }

    public void updateAuthInfo(VidAuth vidAuth) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.updateVidAuth(vidAuth);
        }
    }

    public void updateStsInfo(StsInfo stsInfo) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.updateStsInfo(stsInfo);
        }
    }

    @Deprecated
    public TrackInfo currentTrack(int i2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            return aliPlayer.currentTrack(i2);
        }
        return null;
    }

    public void selectTrack(int i2, boolean z2) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.selectTrack(i2, z2);
        }
    }

    public void setDataSource(VidAuth vidAuth) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidAuth);
        }
    }

    public AliyunRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentEnableHardwareDecoder = true;
        init(context);
    }

    public void setDataSource(LiveSts liveSts) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(liveSts);
        }
    }

    public AliyunRenderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mCurrentEnableHardwareDecoder = true;
        init(context);
    }

    public void setDataSource(VidMps vidMps) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(vidMps);
        }
    }

    public void setDataSource(UrlSource urlSource) {
        AliPlayer aliPlayer = this.mAliPlayer;
        if (aliPlayer != null) {
            aliPlayer.setDataSource(urlSource);
        }
    }
}
