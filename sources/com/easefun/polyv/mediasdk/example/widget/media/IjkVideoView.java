package com.easefun.polyv.mediasdk.example.widget.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.easefun.polyv.mediasdk.example.application.Settings;
import com.easefun.polyv.mediasdk.example.services.MediaPlayerService;
import com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView;
import com.easefun.polyv.mediasdk.example.widget.media.IRenderView;
import com.easefun.polyv.mediasdk.gifmaker.GifMaker;
import com.easefun.polyv.mediasdk.player.AndroidMediaPlayer;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkTimedText;
import com.easefun.polyv.mediasdk.player.MediaPlayerProxy;
import com.easefun.polyv.mediasdk.player.TextureMediaPlayer;
import com.easefun.polyv.mediasdk.player.misc.ITrackInfo;
import com.plv.foundationsdk.web.PLVWebview;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class IjkVideoView extends FrameLayout implements MediaController.MediaPlayerControl, IIjkVideoView {
    public static final int RENDER_NONE = 0;
    public static final int RENDER_SURFACE_VIEW = 1;
    public static final int RENDER_TEXTURE_VIEW = 2;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private static List<String> nativeInvokeMessages = new ArrayList();
    private static final int[] s_allAspectRatio = {0, 1, 2, 4, 5};
    private String TAG;
    public int ijkLogLevel;
    private boolean isDrm12Player;
    private byte[] m3u8Data;
    private List<Integer> mAllRenders;
    private Context mAppContext;
    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private IMediaPlayer.OnCompletionListener mCompletionListener;
    private int mCurrentAspectRatio;
    private int mCurrentAspectRatioIndex;
    private int mCurrentBufferPercentage;
    private int mCurrentRender;
    private int mCurrentRenderIndex;
    private int mCurrentState;
    private boolean mEnableBackgroundPlay;
    private IMediaPlayer.OnErrorListener mErrorListener;
    private Map<String, String> mHeaders;
    private IMediaPlayer.OnInfoListener mInfoListener;
    private boolean mIsClearCanvasInStart;
    public boolean mIsLooping;
    private IMediaController mMediaController;
    private IMediaPlayer mMediaPlayer;
    private IMediaPlayer.OnCompletionListener mOnCompletionListener;
    private IMediaPlayer.OnErrorListener mOnErrorListener;
    private IMediaPlayer.OnInfoListener mOnInfoListener;
    private IIjkVideoView.OnIjkMediaPlayerCreateListener mOnPlayerCreateListener;
    private IMediaPlayer.OnPreparedListener mOnPreparedListener;
    private IMediaPlayer.OnSEIRefreshListener mOnSEIRefreshListener;
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private IMediaPlayer.OnTimedTextListener mOnTimedTextListener;
    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private Object[][] mOptionParameters;
    IMediaPlayer.OnPreparedListener mPreparedListener;
    private IRenderView mRenderView;
    private IMediaPlayer.OnSEIRefreshListener mSEIRefreshListener;
    IRenderView.IRenderCallback mSHCallback;
    private IMediaPlayer.OnSeekCompleteListener mSeekCompleteListener;
    private int mSeekWhenPrepared;
    private Settings mSettings;
    IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private IRenderView.ISurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoRotationDegree;
    private int mVideoSarDen;
    private int mVideoSarNum;
    private int mVideoWidth;
    private int minFrames;
    private IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener;
    private OnSurfaceUpdateListener surfaceUpdateListener;

    public interface OnSurfaceUpdateListener {
        void onUpdate();
    }

    public IjkVideoView(Context context) {
        super(context);
        this.TAG = "IjkVideoView";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.minFrames = 50000;
        this.isDrm12Player = false;
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.2
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i2, int i3, int i4, int i5) {
                if (IjkVideoView.this.mOnVideoSizeChangedListener != null) {
                    IjkVideoView.this.mOnVideoSizeChangedListener.onVideoSizeChanged(iMediaPlayer, i2, i3, i4, i5);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                IjkVideoView.this.mVideoSarNum = iMediaPlayer.getVideoSarNum();
                IjkVideoView.this.mVideoSarDen = iMediaPlayer.getVideoSarDen();
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                }
                IjkVideoView.this.requestLayout();
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.3
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer iMediaPlayer) throws IllegalStateException {
                IjkVideoView.this.mCurrentState = 2;
                if (IjkVideoView.this.mOnPreparedListener != null) {
                    IjkVideoView.this.mOnPreparedListener.onPrepared(IjkVideoView.this.mMediaPlayer);
                }
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.setEnabled(true);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                int i2 = IjkVideoView.this.mSeekWhenPrepared;
                if (i2 != 0) {
                    IjkVideoView.this.seekTo(i2);
                }
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    if (IjkVideoView.this.mTargetState == 3) {
                        IjkVideoView.this.start();
                        return;
                    }
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                    if (!IjkVideoView.this.mRenderView.shouldWaitForResize() || (IjkVideoView.this.mSurfaceWidth == IjkVideoView.this.mVideoWidth && IjkVideoView.this.mSurfaceHeight == IjkVideoView.this.mVideoHeight)) {
                        if (IjkVideoView.this.mTargetState == 3) {
                            IjkVideoView.this.start();
                        } else {
                            if (IjkVideoView.this.isPlaying() || i2 != 0) {
                                return;
                            }
                            IjkVideoView.this.getCurrentPosition();
                        }
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.4
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                IjkVideoView.this.mCurrentState = 5;
                IjkVideoView.this.mTargetState = 5;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if (IjkVideoView.this.mOnCompletionListener != null) {
                    IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.5
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i2, int i3) {
                if (IjkVideoView.this.mOnInfoListener != null) {
                    IjkVideoView.this.mOnInfoListener.onInfo(iMediaPlayer, i2, i3);
                }
                if (i2 == 3) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    return true;
                }
                if (i2 == 901) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    return true;
                }
                if (i2 == 902) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    return true;
                }
                if (i2 == 10001) {
                    IjkVideoView.this.mVideoRotationDegree = i3;
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i3);
                    if (IjkVideoView.this.mRenderView == null) {
                        return true;
                    }
                    IjkVideoView.this.mRenderView.setVideoRotation(i3);
                    return true;
                }
                if (i2 == 10002) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    return true;
                }
                switch (i2) {
                    case 700:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    case 701:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_START:");
                        break;
                    case 702:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_END:");
                        break;
                    case 703:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i3);
                        break;
                    default:
                        switch (i2) {
                            case 800:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                                break;
                            case 801:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                                break;
                            case 802:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_METADATA_UPDATE:");
                                break;
                        }
                }
                return true;
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer iMediaPlayer, int i2, int i3) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                Log.d(IjkVideoView.this.TAG, "Error: " + i2 + "," + i3);
                IjkVideoView.this.mCurrentState = -1;
                IjkVideoView.this.mTargetState = -1;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if ((IjkVideoView.this.mOnErrorListener == null || !IjkVideoView.this.mOnErrorListener.onError(IjkVideoView.this.mMediaPlayer, i2, i3)) && IjkVideoView.this.getWindowToken() != null) {
                    IjkVideoView.this.mAppContext.getResources();
                    new AlertDialog.Builder(IjkVideoView.this.getContext()).setMessage(i2 == 200 ? "Invalid progressive playback" : "Unknown").setPositiveButton(PLVWebview.MESSAGE_OK, new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i4) {
                            if (IjkVideoView.this.mOnCompletionListener != null) {
                                IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                            }
                        }
                    }).setCancelable(false).show();
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.7
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i2) {
                IjkVideoView.this.mCurrentBufferPercentage = i2;
                if (IjkVideoView.this.onBufferingUpdateListener != null) {
                    IjkVideoView.this.onBufferingUpdateListener.onBufferingUpdate(iMediaPlayer, i2);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.8
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (IjkVideoView.this.mOnSeekCompleteListener != null) {
                    IjkVideoView.this.mOnSeekCompleteListener.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.mOnTimedTextListener = new IMediaPlayer.OnTimedTextListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.9
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnTimedTextListener
            public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
            }
        };
        this.mSEIRefreshListener = new IMediaPlayer.OnSEIRefreshListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.10
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSEIRefreshListener
            public void onSEIRefresh(IMediaPlayer iMediaPlayer, int i2, byte[] bArr) {
                if (IjkVideoView.this.mOnSEIRefreshListener != null) {
                    IjkVideoView.this.mOnSEIRefreshListener.onSEIRefresh(iMediaPlayer, i2, bArr);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.11
            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i2, int i3, int i4) throws IllegalStateException {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceChanged: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceWidth = i3;
                IjkVideoView.this.mSurfaceHeight = i4;
                boolean z2 = true;
                boolean z3 = IjkVideoView.this.mTargetState == 3;
                if (IjkVideoView.this.mRenderView.shouldWaitForResize() && (IjkVideoView.this.mVideoWidth != i3 || IjkVideoView.this.mVideoHeight != i4)) {
                    z2 = false;
                }
                if (IjkVideoView.this.mMediaPlayer != null && z3 && z2) {
                    if (IjkVideoView.this.mSeekWhenPrepared != 0) {
                        IjkVideoView ijkVideoView = IjkVideoView.this;
                        ijkVideoView.seekTo(ijkVideoView.mSeekWhenPrepared);
                    }
                    IjkVideoView.this.start();
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i2, int i3) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceCreated: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceHolder = iSurfaceHolder;
                if (IjkVideoView.this.mMediaPlayer != null) {
                    IjkVideoView ijkVideoView = IjkVideoView.this;
                    ijkVideoView.bindSurfaceHolder(ijkVideoView.mMediaPlayer, iSurfaceHolder);
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceDestroyed: unmatched render callback\n");
                } else {
                    IjkVideoView.this.mSurfaceHolder = null;
                    IjkVideoView.this.releaseWithoutStop();
                }
            }
        };
        this.mCurrentAspectRatioIndex = 0;
        this.mCurrentAspectRatio = s_allAspectRatio[0];
        this.mAllRenders = new ArrayList();
        this.mCurrentRenderIndex = 0;
        this.mCurrentRender = 0;
        this.mEnableBackgroundPlay = false;
        this.ijkLogLevel = 3;
        initVideoView(context);
    }

    private void attachMediaController() {
        IMediaController iMediaController;
        if (this.mMediaPlayer == null || (iMediaController = this.mMediaController) == null) {
            return;
        }
        iMediaController.setMediaPlayer(this);
        this.mMediaController.setAnchorView(getParent() instanceof View ? (View) getParent() : this);
        this.mMediaController.setEnabled(isInPlaybackState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindSurfaceHolder(IMediaPlayer iMediaPlayer, IRenderView.ISurfaceHolder iSurfaceHolder) {
        if (iMediaPlayer == null) {
            return;
        }
        if (iSurfaceHolder == null) {
            iMediaPlayer.setDisplay(null);
        } else {
            iSurfaceHolder.bindToMediaPlayer(iMediaPlayer);
        }
    }

    public static List<String> getNativeInvokeMessages() {
        return nativeInvokeMessages;
    }

    @NonNull
    public static String getPlayerText(Context context, int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? "N/A" : "Player: IjkExoMediaPlayer" : "Player: IjkMediaPlayer" : "Player: AndroidMediaPlayer";
    }

    @NonNull
    public static String getRenderText(Context context, int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? "N/A" : "Render: TextureView" : "Render: SurfaceView" : "Render: None";
    }

    private void initBackground() {
        this.mEnableBackgroundPlay = this.mSettings.getEnableBackgroundPlay();
        MediaPlayerService.intentToStart(getContext());
        this.mMediaPlayer = MediaPlayerService.getMediaPlayer();
    }

    private void initRenders() {
        this.mAllRenders.clear();
        if (this.mSettings.getEnableSurfaceView()) {
            this.mAllRenders.add(1);
        }
        if (this.mSettings.getEnableTextureView()) {
            this.mAllRenders.add(2);
        }
        if (this.mSettings.getEnableNoView()) {
            this.mAllRenders.add(0);
        }
        if (this.mAllRenders.isEmpty()) {
            this.mAllRenders.add(1);
        }
        this.mCurrentRender = this.mAllRenders.get(this.mCurrentRenderIndex).intValue();
    }

    private void initVideoView(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mAppContext = applicationContext;
        this.mSettings = new Settings(applicationContext);
        initBackground();
        initRenders();
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    private boolean isInPlaybackState() {
        int i2;
        return (this.mMediaPlayer == null || (i2 = this.mCurrentState) == -1 || i2 == 0 || i2 == 1) ? false : true;
    }

    @TargetApi(23)
    private void openVideo() {
        if (this.mUri == null) {
            return;
        }
        release(false);
        ((AudioManager) this.mAppContext.getSystemService("audio")).requestAudioFocus(null, 3, 1);
        try {
            IMediaPlayer iMediaPlayerCreatePlayer = createPlayer(this.mSettings.getPlayer());
            this.mMediaPlayer = iMediaPlayerCreatePlayer;
            IIjkVideoView.OnIjkMediaPlayerCreateListener onIjkMediaPlayerCreateListener = this.mOnPlayerCreateListener;
            if (onIjkMediaPlayerCreateListener != null) {
                onIjkMediaPlayerCreateListener.onIjkPlayerCreate(iMediaPlayerCreatePlayer);
            }
            setRender(this.mSettings.getRenderViewType());
            setOption(this.mMediaPlayer);
            this.mMediaPlayer.setLooping(this.mIsLooping);
            getContext();
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
            this.mMediaPlayer.setOnTimedTextListener(this.mOnTimedTextListener);
            this.mMediaPlayer.setOnSEIRefreshListener(this.mSEIRefreshListener);
            this.mCurrentBufferPercentage = 0;
            String scheme = this.mUri.getScheme();
            if (getIjkMediaPlayer() != null) {
                nativeInvokeMessages.clear();
                getIjkMediaPlayer().setOnNativeInvokeListener(new IjkMediaPlayer.OnNativeInvokeListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.1
                    @Override // com.easefun.polyv.mediasdk.player.IjkMediaPlayer.OnNativeInvokeListener
                    public boolean onNativeInvoke(int i2, Bundle bundle) {
                        IjkVideoView.this.processNativeInvokeMessages(i2, bundle);
                        return false;
                    }
                });
            }
            if (this.isDrm12Player) {
                this.mMediaPlayer.setDataSource(this.mUri.toString(), this.m3u8Data, this.mHeaders);
            } else if (this.mSettings.getUsingMediaDataSource() && (TextUtils.isEmpty(scheme) || scheme.equalsIgnoreCase("file"))) {
                this.mMediaPlayer.setDataSource(new FileMediaDataSource(new File(this.mUri.toString())));
            } else {
                this.mMediaPlayer.setDataSource(this.mAppContext, this.mUri, this.mHeaders);
            }
            this.mMediaPlayer.setAudioStreamType(3);
            postSetKeepScreenOn(true);
            this.mMediaPlayer.prepareAsync();
            this.mCurrentState = 1;
            attachMediaController();
        } catch (IOException e2) {
            Log.w(this.TAG, "Unable to open content: " + this.mUri, e2);
            this.mCurrentState = -1;
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        } catch (IllegalArgumentException e3) {
            Log.w(this.TAG, "Unable to open content: " + this.mUri, e3);
            this.mCurrentState = -1;
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        }
    }

    @TargetApi(11)
    private Bitmap postChange(TextureRenderView textureRenderView, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(textureRenderView.getScaleX(), textureRenderView.getScaleY());
        matrix.postRotate(textureRenderView.getRotation());
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (!bitmap.equals(bitmapCreateBitmap) && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postSetKeepScreenOn(final boolean z2) {
        post(new Runnable() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.12
            @Override // java.lang.Runnable
            public void run() {
                IjkVideoView.this.setKeepScreenOn(z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processNativeInvokeMessages(int i2, Bundle bundle) {
        String str = "ijk onNativeInvoke i: " + i2 + ", bundle: " + bundle;
        if (nativeInvokeMessages.size() >= 5) {
            nativeInvokeMessages.remove(0);
            nativeInvokeMessages.add(str);
        } else {
            nativeInvokeMessages.add(str);
        }
        Log.i(this.TAG, str);
    }

    private void setHeaders(Map<String, String> map) {
        if (map == null) {
            this.mHeaders = new HashMap();
        } else {
            this.mHeaders = map;
        }
    }

    private void setOption(IMediaPlayer iMediaPlayer) {
        if (getIjkMediaPlayer() != null) {
            IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer();
            IjkMediaPlayer.native_setLogLevel(this.ijkLogLevel);
            if (this.mSettings.getUsingMediaCodec()) {
                ijkMediaPlayer.setOption(4, "mediacodec", 1L);
                if (this.mSettings.getUsingMediaCodecAutoRotate()) {
                    ijkMediaPlayer.setOption(4, "mediacodec-auto-rotate", 1L);
                } else {
                    ijkMediaPlayer.setOption(4, "mediacodec-auto-rotate", 0L);
                }
                if (this.mSettings.getMediaCodecHandleResolutionChange()) {
                    ijkMediaPlayer.setOption(4, "mediacodec-handle-resolution-change", 1L);
                } else {
                    ijkMediaPlayer.setOption(4, "mediacodec-handle-resolution-change", 0L);
                }
            } else {
                ijkMediaPlayer.setOption(4, "mediacodec", 0L);
            }
            if (this.mSettings.getUsingOpenSLES()) {
                ijkMediaPlayer.setOption(4, "opensles", 1L);
            } else {
                ijkMediaPlayer.setOption(4, "opensles", 0L);
            }
            String pixelFormat = this.mSettings.getPixelFormat();
            if (TextUtils.isEmpty(pixelFormat)) {
                ijkMediaPlayer.setOption(4, "overlay-format", 842225234L);
            } else {
                ijkMediaPlayer.setOption(4, "overlay-format", pixelFormat);
            }
            ijkMediaPlayer.setOption(1, "http-detect-range-support", 0L);
            ijkMediaPlayer.setOption(4, "soundtouch", 1L);
            ijkMediaPlayer.setOption(1, "allowed_extensions", "ALL");
            ijkMediaPlayer.setOption(1, "dns_cache_clear", 1L);
            ijkMediaPlayer.setOption(4, "min-frames", this.minFrames);
            ijkMediaPlayer.setOption(1, "webrtc_server_address", "overseas-webrtc.liveplay.myqcloud.com");
        }
        Object[][] objArr = this.mOptionParameters;
        if (objArr == null || objArr.length <= 0 || getIjkMediaPlayer() == null) {
            return;
        }
        IjkMediaPlayer ijkMediaPlayer2 = getIjkMediaPlayer();
        int i2 = 0;
        while (true) {
            Object[][] objArr2 = this.mOptionParameters;
            if (i2 >= objArr2.length) {
                return;
            }
            Object[] objArr3 = objArr2[i2];
            if (objArr3[2] instanceof String) {
                int iIntValue = ((Integer) objArr3[0]).intValue();
                Object[] objArr4 = this.mOptionParameters[i2];
                ijkMediaPlayer2.setOption(iIntValue, (String) objArr4[1], (String) objArr4[2]);
            } else {
                ijkMediaPlayer2.setOption(((Integer) objArr3[0]).intValue(), (String) this.mOptionParameters[i2][1], ((Integer) r5[2]).intValue());
            }
            i2++;
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return this.mCanPause;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void cancelClip() {
        IRenderView iRenderView = this.mRenderView;
        if (iRenderView instanceof TextureRenderView) {
            ((TextureRenderView) iRenderView).cancelClip();
        }
    }

    public void clearCanvasInStart(boolean z2) {
        this.mIsClearCanvasInStart = z2;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void clearOptionParameters() {
        this.mOptionParameters = null;
    }

    public void clearUri() {
        this.mUri = null;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IMediaPlayer createPlayer(int i2) {
        IMediaPlayer androidMediaPlayer;
        if (i2 != 1) {
            androidMediaPlayer = null;
            if (i2 != 3 && this.mUri != null) {
                androidMediaPlayer = new IjkMediaPlayer();
            }
        } else {
            androidMediaPlayer = new AndroidMediaPlayer();
        }
        return new TextureMediaPlayer(androidMediaPlayer);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void deselectTrack(int i2) {
        MediaPlayerCompat.deselectTrack(this.mMediaPlayer, i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void enterBackground() throws IllegalStateException {
        MediaPlayerService.setMediaPlayer(this.mMediaPlayer);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getCurrentAspectRatio() {
        return this.mCurrentAspectRatio;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return (int) this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getCurrentState() {
        return this.mCurrentState;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        if (isInPlaybackState()) {
            return (int) this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IjkMediaPlayer getIjkMediaPlayer() {
        IMediaPlayer iMediaPlayer = this.mMediaPlayer;
        if (iMediaPlayer instanceof IjkMediaPlayer) {
            return (IjkMediaPlayer) iMediaPlayer;
        }
        if (iMediaPlayer instanceof MediaPlayerProxy) {
            IMediaPlayer internalMediaPlayer = ((MediaPlayerProxy) iMediaPlayer).getInternalMediaPlayer();
            if (internalMediaPlayer instanceof IjkMediaPlayer) {
                return (IjkMediaPlayer) internalMediaPlayer;
            }
        }
        return null;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IMediaPlayer getMediaPlayer() {
        return this.mMediaPlayer;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public IRenderView getRenderView() {
        return this.mRenderView;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getSelectedTrack(int i2) {
        return MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, i2);
    }

    public Settings getSettings() {
        return this.mSettings;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public float getSpeed() {
        if (this.mMediaPlayer == null || getIjkMediaPlayer() == null) {
            return 0.0f;
        }
        return getIjkMediaPlayer().getSpeed(0.0f);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStateErrorCode() {
        return -1;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStateIdleCode() {
        return 0;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePauseCode() {
        return 4;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePlaybackCompletedCode() {
        return 5;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePlayingCode() {
        return 3;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePreparedCode() {
        return 2;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getStatePreparingCode() {
        return 1;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public SurfaceHolder getSurfaceHolder() {
        IRenderView.ISurfaceHolder iSurfaceHolder = this.mSurfaceHolder;
        if (iSurfaceHolder == null) {
            return null;
        }
        return iSurfaceHolder.getSurfaceHolder();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getTargetState() {
        return this.mTargetState;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public ITrackInfo[] getTrackInfo() {
        IMediaPlayer iMediaPlayer = this.mMediaPlayer;
        if (iMediaPlayer == null) {
            return null;
        }
        return iMediaPlayer.getTrackInfo();
    }

    public long getTrafficStatisticByteCount() {
        if (this.mMediaPlayer == null || getIjkMediaPlayer() == null) {
            return 0L;
        }
        return getIjkMediaPlayer().getTrafficStatisticByteCount();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getVideoHeight() {
        return this.mVideoHeight;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int getVideoWidth() {
        return this.mVideoWidth;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public boolean isInPlaybackStateForwarding() {
        return isInPlaybackState();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void onErrorState() {
        release(false);
        this.mCurrentState = -1;
        this.mTargetState = -1;
        IMediaController iMediaController = this.mMediaController;
        if (iMediaController != null) {
            iMediaController.hide();
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() throws IllegalStateException {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
            postSetKeepScreenOn(false);
        }
        this.mTargetState = 4;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public synchronized void release(boolean z2) {
        IMediaPlayer iMediaPlayer = this.mMediaPlayer;
        if (iMediaPlayer == null) {
            return;
        }
        if ((iMediaPlayer instanceof TextureMediaPlayer) && ((TextureMediaPlayer) iMediaPlayer).getInternalMediaPlayer() == null) {
            return;
        }
        this.mMediaPlayer.reset();
        this.mMediaPlayer.release();
        this.mMediaPlayer = null;
        this.mCurrentState = 0;
        if (z2) {
            this.mTargetState = 0;
        }
        ((AudioManager) this.mAppContext.getSystemService("audio")).abandonAudioFocus(null);
        postSetKeepScreenOn(false);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void releaseWithoutStop() {
        IMediaPlayer iMediaPlayer = this.mMediaPlayer;
        if (iMediaPlayer != null) {
            iMediaPlayer.setDisplay(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void removeRenderView() {
        if (this.mRenderView != null) {
            IMediaPlayer iMediaPlayer = this.mMediaPlayer;
            if (iMediaPlayer != null) {
                iMediaPlayer.setDisplay(null);
            }
            IRenderView iRenderView = this.mRenderView;
            if (iRenderView instanceof TextureRenderView) {
                ((TextureRenderView) iRenderView).setOnSurfaceUpdateListener(null);
            }
            View view = this.mRenderView.getView();
            this.mRenderView.removeRenderCallback(this.mSHCallback);
            this.mRenderView = null;
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void resetLoadCost() {
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void resetVideoURI() {
        IMediaPlayer iMediaPlayer = this.mMediaPlayer;
        if (iMediaPlayer != null) {
            this.mSeekWhenPrepared = 0;
            iMediaPlayer.reset();
            this.mCurrentState = 0;
            this.mTargetState = 0;
            setRender(2);
            setOption(this.mMediaPlayer);
            this.mCurrentBufferPercentage = 0;
            try {
                String scheme = this.mUri.getScheme();
                if (this.mSettings.getUsingMediaDataSource() && (TextUtils.isEmpty(scheme) || scheme.equalsIgnoreCase("file"))) {
                    this.mMediaPlayer.setDataSource(new FileMediaDataSource(new File(this.mUri.toString())));
                } else {
                    this.mMediaPlayer.setDataSource(this.mAppContext, this.mUri, this.mHeaders);
                }
                if (getIjkMediaPlayer() != null) {
                    getIjkMediaPlayer().setOption(1, "protocol_whitelist", "async,cache,crypto,file,http,https,ijkhttphook,ijkinject,ijklivehook,ijklongurl,ijksegment,ijktcphook,pipe,rtp,rtmp,tcp,tls,udp,ijkurlhook,data");
                }
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = 1;
            } catch (IOException e2) {
                Log.w(this.TAG, "Unable to open content: " + this.mUri, e2);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (IllegalArgumentException e3) {
                Log.w(this.TAG, "Unable to open content: " + this.mUri, e3);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            }
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void resume() {
        openVideo();
    }

    @TargetApi(14)
    public Bitmap screenshot(Bitmap bitmap) {
        IRenderView iRenderView = this.mRenderView;
        if (!(iRenderView instanceof TextureRenderView)) {
            return null;
        }
        return postChange((TextureRenderView) this.mRenderView, ((TextureRenderView) iRenderView).getBitmap(bitmap));
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i2) throws IllegalStateException {
        if (!isInPlaybackState()) {
            this.mSeekWhenPrepared = i2;
        } else {
            this.mMediaPlayer.seekTo(i2);
            this.mSeekWhenPrepared = 0;
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void selectTrack(int i2) {
        MediaPlayerCompat.selectTrack(this.mMediaPlayer, i2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setCurrentAspectRatio(int i2) {
        this.mCurrentAspectRatio = i2;
        IRenderView iRenderView = this.mRenderView;
        if (iRenderView != null) {
            iRenderView.setAspectRatio(i2);
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setIjkLogLevel(int i2) {
        this.ijkLogLevel = i2;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setLogTag(String str) {
        this.TAG = str;
    }

    public void setLooping(boolean z2) {
        this.mIsLooping = z2;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setMediaController(IMediaController iMediaController) {
        IMediaController iMediaController2 = this.mMediaController;
        if (iMediaController2 != null) {
            iMediaController2.hide();
        }
        this.mMediaController = iMediaController;
        attachMediaController();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setMinFrames(int i2) {
        this.minFrames = i2;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setMirror(boolean z2) {
        IRenderView iRenderView = this.mRenderView;
        if (iRenderView instanceof TextureRenderView) {
            ((TextureRenderView) iRenderView).setMirror(z2);
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnBufferingListener(IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.onBufferingUpdateListener = onBufferingUpdateListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnIjkMediaPlayerCreateListener(IIjkVideoView.OnIjkMediaPlayerCreateListener onIjkMediaPlayerCreateListener) {
        this.mOnPlayerCreateListener = onIjkMediaPlayerCreateListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnSEIRefreshListener(IMediaPlayer.OnSEIRefreshListener onSEIRefreshListener) {
        this.mOnSEIRefreshListener = onSEIRefreshListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnSurfaceUpdateListener(OnSurfaceUpdateListener onSurfaceUpdateListener) {
        this.surfaceUpdateListener = onSurfaceUpdateListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setOptionParameters(Object[][] objArr) {
        this.mOptionParameters = objArr;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setRender(int i2) {
        if (i2 == 0) {
            setRenderView(null);
            return;
        }
        if (i2 == 1) {
            IRenderView iRenderView = this.mRenderView;
            if (iRenderView == null || !(iRenderView instanceof SurfaceRenderView)) {
                setRenderView(new SurfaceRenderView(getContext()));
                return;
            }
            return;
        }
        if (i2 != 2) {
            Log.e(this.TAG, String.format(Locale.getDefault(), "invalid render %d\n", Integer.valueOf(i2)));
            return;
        }
        TextureRenderView textureRenderView = new TextureRenderView(getContext());
        textureRenderView.setOnSurfaceUpdateListener(this.surfaceUpdateListener);
        if (this.mMediaPlayer != null) {
            textureRenderView.getSurfaceHolder().bindToMediaPlayer(this.mMediaPlayer);
            textureRenderView.setVideoSize(this.mMediaPlayer.getVideoWidth(), this.mMediaPlayer.getVideoHeight());
            textureRenderView.setVideoSampleAspectRatio(this.mMediaPlayer.getVideoSarNum(), this.mMediaPlayer.getVideoSarDen());
            textureRenderView.setAspectRatio(this.mCurrentAspectRatio);
        }
        setRenderView(textureRenderView);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setRenderView(IRenderView iRenderView) {
        int i2;
        int i3;
        removeRenderView();
        if (iRenderView == null) {
            return;
        }
        this.mRenderView = iRenderView;
        iRenderView.setAspectRatio(this.mCurrentAspectRatio);
        int i4 = this.mVideoWidth;
        if (i4 > 0 && (i3 = this.mVideoHeight) > 0) {
            iRenderView.setVideoSize(i4, i3);
        }
        int i5 = this.mVideoSarNum;
        if (i5 > 0 && (i2 = this.mVideoSarDen) > 0) {
            iRenderView.setVideoSampleAspectRatio(i5, i2);
        }
        View view = this.mRenderView.getView();
        view.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        addView(view);
        this.mRenderView.addRenderCallback(this.mSHCallback);
        this.mRenderView.setVideoRotation(this.mVideoRotationDegree);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setSpeed(float f2) {
        if (this.mMediaPlayer == null || getIjkMediaPlayer() == null) {
            return;
        }
        getIjkMediaPlayer().setSpeed(f2);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setTargetState(int i2) {
        this.mTargetState = i2;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoContentPrefixURLString(String str, byte[] bArr, Map<String, String> map) {
        this.isDrm12Player = true;
        this.m3u8Data = bArr;
        this.mUri = Uri.parse(str);
        setHeaders(map);
        this.mSeekWhenPrepared = 0;
        openVideo();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void start() {
        if (isInPlaybackState()) {
            try {
                this.mMediaPlayer.start();
                this.mCurrentState = 3;
                postSetKeepScreenOn(true);
            } catch (IllegalStateException unused) {
            }
        }
        this.mTargetState = 3;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public boolean startClip(int i2) {
        if (!(this.mRenderView instanceof TextureRenderView) || !isInPlaybackState()) {
            return false;
        }
        ((TextureRenderView) this.mRenderView).startClip(i2, getWidth(), getHeight(), this.mCurrentAspectRatio, this.mMediaPlayer);
        return false;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void stopBackgroundPlay() throws IllegalStateException {
        MediaPlayerService.setMediaPlayer(null);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void stopClip(GifMaker.OnGifListener onGifListener) {
        IRenderView iRenderView = this.mRenderView;
        if (iRenderView instanceof TextureRenderView) {
            ((TextureRenderView) iRenderView).stopClip(onGifListener);
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void stopPlayback() throws IllegalStateException {
        IMediaPlayer iMediaPlayer = this.mMediaPlayer;
        if (iMediaPlayer != null) {
            iMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
            ((AudioManager) this.mAppContext.getSystemService("audio")).abandonAudioFocus(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void suspend() {
        release(false);
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int toggleAspectRatio() {
        int i2 = this.mCurrentAspectRatioIndex + 1;
        this.mCurrentAspectRatioIndex = i2;
        int[] iArr = s_allAspectRatio;
        int length = i2 % iArr.length;
        this.mCurrentAspectRatioIndex = length;
        int i3 = iArr[length];
        this.mCurrentAspectRatio = i3;
        IRenderView iRenderView = this.mRenderView;
        if (iRenderView != null) {
            iRenderView.setAspectRatio(i3);
        }
        return this.mCurrentAspectRatio;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int togglePlayer() {
        IMediaPlayer iMediaPlayer = this.mMediaPlayer;
        if (iMediaPlayer != null) {
            iMediaPlayer.release();
        }
        IRenderView iRenderView = this.mRenderView;
        if (iRenderView != null) {
            iRenderView.getView().invalidate();
        }
        openVideo();
        return this.mSettings.getPlayer();
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public int toggleRender() {
        int i2 = this.mCurrentRenderIndex + 1;
        this.mCurrentRenderIndex = i2;
        int size = i2 % this.mAllRenders.size();
        this.mCurrentRenderIndex = size;
        int iIntValue = this.mAllRenders.get(size).intValue();
        this.mCurrentRender = iIntValue;
        setRender(iIntValue);
        return this.mCurrentRender;
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    public void setVideoURI(Uri uri, Map<String, String> map) {
        this.isDrm12Player = false;
        this.mUri = uri;
        setHeaders(map);
        this.mSeekWhenPrepared = 0;
        openVideo();
    }

    @TargetApi(14)
    public Bitmap screenshot(int i2, int i3) {
        IRenderView iRenderView = this.mRenderView;
        if (!(iRenderView instanceof TextureRenderView)) {
            return null;
        }
        return postChange((TextureRenderView) this.mRenderView, ((TextureRenderView) iRenderView).getBitmap(i2, i3));
    }

    @Override // com.easefun.polyv.mediasdk.example.widget.media.IIjkVideoView
    @TargetApi(14)
    public Bitmap screenshot() {
        Bitmap bitmap;
        IRenderView iRenderView = this.mRenderView;
        if (!(iRenderView instanceof TextureRenderView)) {
            return null;
        }
        int i2 = this.mCurrentAspectRatio;
        if (i2 != 1 && i2 != 3) {
            bitmap = ((TextureRenderView) iRenderView).getBitmap();
        } else {
            bitmap = ((TextureRenderView) iRenderView).getBitmap(getWidth(), getHeight());
        }
        return postChange((TextureRenderView) this.mRenderView, bitmap);
    }

    public IjkVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "IjkVideoView";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.minFrames = 50000;
        this.isDrm12Player = false;
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.2
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i2, int i3, int i4, int i5) {
                if (IjkVideoView.this.mOnVideoSizeChangedListener != null) {
                    IjkVideoView.this.mOnVideoSizeChangedListener.onVideoSizeChanged(iMediaPlayer, i2, i3, i4, i5);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                IjkVideoView.this.mVideoSarNum = iMediaPlayer.getVideoSarNum();
                IjkVideoView.this.mVideoSarDen = iMediaPlayer.getVideoSarDen();
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                }
                IjkVideoView.this.requestLayout();
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.3
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer iMediaPlayer) throws IllegalStateException {
                IjkVideoView.this.mCurrentState = 2;
                if (IjkVideoView.this.mOnPreparedListener != null) {
                    IjkVideoView.this.mOnPreparedListener.onPrepared(IjkVideoView.this.mMediaPlayer);
                }
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.setEnabled(true);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                int i2 = IjkVideoView.this.mSeekWhenPrepared;
                if (i2 != 0) {
                    IjkVideoView.this.seekTo(i2);
                }
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    if (IjkVideoView.this.mTargetState == 3) {
                        IjkVideoView.this.start();
                        return;
                    }
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                    if (!IjkVideoView.this.mRenderView.shouldWaitForResize() || (IjkVideoView.this.mSurfaceWidth == IjkVideoView.this.mVideoWidth && IjkVideoView.this.mSurfaceHeight == IjkVideoView.this.mVideoHeight)) {
                        if (IjkVideoView.this.mTargetState == 3) {
                            IjkVideoView.this.start();
                        } else {
                            if (IjkVideoView.this.isPlaying() || i2 != 0) {
                                return;
                            }
                            IjkVideoView.this.getCurrentPosition();
                        }
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.4
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                IjkVideoView.this.mCurrentState = 5;
                IjkVideoView.this.mTargetState = 5;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if (IjkVideoView.this.mOnCompletionListener != null) {
                    IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.5
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i2, int i3) {
                if (IjkVideoView.this.mOnInfoListener != null) {
                    IjkVideoView.this.mOnInfoListener.onInfo(iMediaPlayer, i2, i3);
                }
                if (i2 == 3) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    return true;
                }
                if (i2 == 901) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    return true;
                }
                if (i2 == 902) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    return true;
                }
                if (i2 == 10001) {
                    IjkVideoView.this.mVideoRotationDegree = i3;
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i3);
                    if (IjkVideoView.this.mRenderView == null) {
                        return true;
                    }
                    IjkVideoView.this.mRenderView.setVideoRotation(i3);
                    return true;
                }
                if (i2 == 10002) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    return true;
                }
                switch (i2) {
                    case 700:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    case 701:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_START:");
                        break;
                    case 702:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_END:");
                        break;
                    case 703:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i3);
                        break;
                    default:
                        switch (i2) {
                            case 800:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                                break;
                            case 801:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                                break;
                            case 802:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_METADATA_UPDATE:");
                                break;
                        }
                }
                return true;
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer iMediaPlayer, int i2, int i3) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                Log.d(IjkVideoView.this.TAG, "Error: " + i2 + "," + i3);
                IjkVideoView.this.mCurrentState = -1;
                IjkVideoView.this.mTargetState = -1;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if ((IjkVideoView.this.mOnErrorListener == null || !IjkVideoView.this.mOnErrorListener.onError(IjkVideoView.this.mMediaPlayer, i2, i3)) && IjkVideoView.this.getWindowToken() != null) {
                    IjkVideoView.this.mAppContext.getResources();
                    new AlertDialog.Builder(IjkVideoView.this.getContext()).setMessage(i2 == 200 ? "Invalid progressive playback" : "Unknown").setPositiveButton(PLVWebview.MESSAGE_OK, new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i4) {
                            if (IjkVideoView.this.mOnCompletionListener != null) {
                                IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                            }
                        }
                    }).setCancelable(false).show();
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.7
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i2) {
                IjkVideoView.this.mCurrentBufferPercentage = i2;
                if (IjkVideoView.this.onBufferingUpdateListener != null) {
                    IjkVideoView.this.onBufferingUpdateListener.onBufferingUpdate(iMediaPlayer, i2);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.8
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (IjkVideoView.this.mOnSeekCompleteListener != null) {
                    IjkVideoView.this.mOnSeekCompleteListener.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.mOnTimedTextListener = new IMediaPlayer.OnTimedTextListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.9
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnTimedTextListener
            public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
            }
        };
        this.mSEIRefreshListener = new IMediaPlayer.OnSEIRefreshListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.10
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSEIRefreshListener
            public void onSEIRefresh(IMediaPlayer iMediaPlayer, int i2, byte[] bArr) {
                if (IjkVideoView.this.mOnSEIRefreshListener != null) {
                    IjkVideoView.this.mOnSEIRefreshListener.onSEIRefresh(iMediaPlayer, i2, bArr);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.11
            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i2, int i3, int i4) throws IllegalStateException {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceChanged: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceWidth = i3;
                IjkVideoView.this.mSurfaceHeight = i4;
                boolean z2 = true;
                boolean z3 = IjkVideoView.this.mTargetState == 3;
                if (IjkVideoView.this.mRenderView.shouldWaitForResize() && (IjkVideoView.this.mVideoWidth != i3 || IjkVideoView.this.mVideoHeight != i4)) {
                    z2 = false;
                }
                if (IjkVideoView.this.mMediaPlayer != null && z3 && z2) {
                    if (IjkVideoView.this.mSeekWhenPrepared != 0) {
                        IjkVideoView ijkVideoView = IjkVideoView.this;
                        ijkVideoView.seekTo(ijkVideoView.mSeekWhenPrepared);
                    }
                    IjkVideoView.this.start();
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i2, int i3) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceCreated: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceHolder = iSurfaceHolder;
                if (IjkVideoView.this.mMediaPlayer != null) {
                    IjkVideoView ijkVideoView = IjkVideoView.this;
                    ijkVideoView.bindSurfaceHolder(ijkVideoView.mMediaPlayer, iSurfaceHolder);
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceDestroyed: unmatched render callback\n");
                } else {
                    IjkVideoView.this.mSurfaceHolder = null;
                    IjkVideoView.this.releaseWithoutStop();
                }
            }
        };
        this.mCurrentAspectRatioIndex = 0;
        this.mCurrentAspectRatio = s_allAspectRatio[0];
        this.mAllRenders = new ArrayList();
        this.mCurrentRenderIndex = 0;
        this.mCurrentRender = 0;
        this.mEnableBackgroundPlay = false;
        this.ijkLogLevel = 3;
        initVideoView(context);
    }

    public IjkVideoView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.TAG = "IjkVideoView";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.minFrames = 50000;
        this.isDrm12Player = false;
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.2
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i22, int i3, int i4, int i5) {
                if (IjkVideoView.this.mOnVideoSizeChangedListener != null) {
                    IjkVideoView.this.mOnVideoSizeChangedListener.onVideoSizeChanged(iMediaPlayer, i22, i3, i4, i5);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                IjkVideoView.this.mVideoSarNum = iMediaPlayer.getVideoSarNum();
                IjkVideoView.this.mVideoSarDen = iMediaPlayer.getVideoSarDen();
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                }
                IjkVideoView.this.requestLayout();
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.3
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer iMediaPlayer) throws IllegalStateException {
                IjkVideoView.this.mCurrentState = 2;
                if (IjkVideoView.this.mOnPreparedListener != null) {
                    IjkVideoView.this.mOnPreparedListener.onPrepared(IjkVideoView.this.mMediaPlayer);
                }
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.setEnabled(true);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                int i22 = IjkVideoView.this.mSeekWhenPrepared;
                if (i22 != 0) {
                    IjkVideoView.this.seekTo(i22);
                }
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    if (IjkVideoView.this.mTargetState == 3) {
                        IjkVideoView.this.start();
                        return;
                    }
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                    if (!IjkVideoView.this.mRenderView.shouldWaitForResize() || (IjkVideoView.this.mSurfaceWidth == IjkVideoView.this.mVideoWidth && IjkVideoView.this.mSurfaceHeight == IjkVideoView.this.mVideoHeight)) {
                        if (IjkVideoView.this.mTargetState == 3) {
                            IjkVideoView.this.start();
                        } else {
                            if (IjkVideoView.this.isPlaying() || i22 != 0) {
                                return;
                            }
                            IjkVideoView.this.getCurrentPosition();
                        }
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.4
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                IjkVideoView.this.mCurrentState = 5;
                IjkVideoView.this.mTargetState = 5;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if (IjkVideoView.this.mOnCompletionListener != null) {
                    IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.5
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i22, int i3) {
                if (IjkVideoView.this.mOnInfoListener != null) {
                    IjkVideoView.this.mOnInfoListener.onInfo(iMediaPlayer, i22, i3);
                }
                if (i22 == 3) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    return true;
                }
                if (i22 == 901) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    return true;
                }
                if (i22 == 902) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    return true;
                }
                if (i22 == 10001) {
                    IjkVideoView.this.mVideoRotationDegree = i3;
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i3);
                    if (IjkVideoView.this.mRenderView == null) {
                        return true;
                    }
                    IjkVideoView.this.mRenderView.setVideoRotation(i3);
                    return true;
                }
                if (i22 == 10002) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    return true;
                }
                switch (i22) {
                    case 700:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    case 701:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_START:");
                        break;
                    case 702:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_END:");
                        break;
                    case 703:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i3);
                        break;
                    default:
                        switch (i22) {
                            case 800:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                                break;
                            case 801:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                                break;
                            case 802:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_METADATA_UPDATE:");
                                break;
                        }
                }
                return true;
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer iMediaPlayer, int i22, int i3) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                Log.d(IjkVideoView.this.TAG, "Error: " + i22 + "," + i3);
                IjkVideoView.this.mCurrentState = -1;
                IjkVideoView.this.mTargetState = -1;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if ((IjkVideoView.this.mOnErrorListener == null || !IjkVideoView.this.mOnErrorListener.onError(IjkVideoView.this.mMediaPlayer, i22, i3)) && IjkVideoView.this.getWindowToken() != null) {
                    IjkVideoView.this.mAppContext.getResources();
                    new AlertDialog.Builder(IjkVideoView.this.getContext()).setMessage(i22 == 200 ? "Invalid progressive playback" : "Unknown").setPositiveButton(PLVWebview.MESSAGE_OK, new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i4) {
                            if (IjkVideoView.this.mOnCompletionListener != null) {
                                IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                            }
                        }
                    }).setCancelable(false).show();
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.7
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i22) {
                IjkVideoView.this.mCurrentBufferPercentage = i22;
                if (IjkVideoView.this.onBufferingUpdateListener != null) {
                    IjkVideoView.this.onBufferingUpdateListener.onBufferingUpdate(iMediaPlayer, i22);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.8
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (IjkVideoView.this.mOnSeekCompleteListener != null) {
                    IjkVideoView.this.mOnSeekCompleteListener.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.mOnTimedTextListener = new IMediaPlayer.OnTimedTextListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.9
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnTimedTextListener
            public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
            }
        };
        this.mSEIRefreshListener = new IMediaPlayer.OnSEIRefreshListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.10
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSEIRefreshListener
            public void onSEIRefresh(IMediaPlayer iMediaPlayer, int i22, byte[] bArr) {
                if (IjkVideoView.this.mOnSEIRefreshListener != null) {
                    IjkVideoView.this.mOnSEIRefreshListener.onSEIRefresh(iMediaPlayer, i22, bArr);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.11
            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i22, int i3, int i4) throws IllegalStateException {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceChanged: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceWidth = i3;
                IjkVideoView.this.mSurfaceHeight = i4;
                boolean z2 = true;
                boolean z3 = IjkVideoView.this.mTargetState == 3;
                if (IjkVideoView.this.mRenderView.shouldWaitForResize() && (IjkVideoView.this.mVideoWidth != i3 || IjkVideoView.this.mVideoHeight != i4)) {
                    z2 = false;
                }
                if (IjkVideoView.this.mMediaPlayer != null && z3 && z2) {
                    if (IjkVideoView.this.mSeekWhenPrepared != 0) {
                        IjkVideoView ijkVideoView = IjkVideoView.this;
                        ijkVideoView.seekTo(ijkVideoView.mSeekWhenPrepared);
                    }
                    IjkVideoView.this.start();
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i22, int i3) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceCreated: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceHolder = iSurfaceHolder;
                if (IjkVideoView.this.mMediaPlayer != null) {
                    IjkVideoView ijkVideoView = IjkVideoView.this;
                    ijkVideoView.bindSurfaceHolder(ijkVideoView.mMediaPlayer, iSurfaceHolder);
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceDestroyed: unmatched render callback\n");
                } else {
                    IjkVideoView.this.mSurfaceHolder = null;
                    IjkVideoView.this.releaseWithoutStop();
                }
            }
        };
        this.mCurrentAspectRatioIndex = 0;
        this.mCurrentAspectRatio = s_allAspectRatio[0];
        this.mAllRenders = new ArrayList();
        this.mCurrentRenderIndex = 0;
        this.mCurrentRender = 0;
        this.mEnableBackgroundPlay = false;
        this.ijkLogLevel = 3;
        initVideoView(context);
    }

    @TargetApi(21)
    public IjkVideoView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.TAG = "IjkVideoView";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.minFrames = 50000;
        this.isDrm12Player = false;
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.2
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i22, int i32, int i4, int i5) {
                if (IjkVideoView.this.mOnVideoSizeChangedListener != null) {
                    IjkVideoView.this.mOnVideoSizeChangedListener.onVideoSizeChanged(iMediaPlayer, i22, i32, i4, i5);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                IjkVideoView.this.mVideoSarNum = iMediaPlayer.getVideoSarNum();
                IjkVideoView.this.mVideoSarDen = iMediaPlayer.getVideoSarDen();
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                }
                IjkVideoView.this.requestLayout();
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.3
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer iMediaPlayer) throws IllegalStateException {
                IjkVideoView.this.mCurrentState = 2;
                if (IjkVideoView.this.mOnPreparedListener != null) {
                    IjkVideoView.this.mOnPreparedListener.onPrepared(IjkVideoView.this.mMediaPlayer);
                }
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.setEnabled(true);
                }
                IjkVideoView.this.mVideoWidth = iMediaPlayer.getVideoWidth();
                IjkVideoView.this.mVideoHeight = iMediaPlayer.getVideoHeight();
                int i22 = IjkVideoView.this.mSeekWhenPrepared;
                if (i22 != 0) {
                    IjkVideoView.this.seekTo(i22);
                }
                if (IjkVideoView.this.mVideoWidth == 0 || IjkVideoView.this.mVideoHeight == 0) {
                    if (IjkVideoView.this.mTargetState == 3) {
                        IjkVideoView.this.start();
                        return;
                    }
                    return;
                }
                if (IjkVideoView.this.mRenderView != null) {
                    IjkVideoView.this.mRenderView.setVideoSize(IjkVideoView.this.mVideoWidth, IjkVideoView.this.mVideoHeight);
                    IjkVideoView.this.mRenderView.setVideoSampleAspectRatio(IjkVideoView.this.mVideoSarNum, IjkVideoView.this.mVideoSarDen);
                    if (!IjkVideoView.this.mRenderView.shouldWaitForResize() || (IjkVideoView.this.mSurfaceWidth == IjkVideoView.this.mVideoWidth && IjkVideoView.this.mSurfaceHeight == IjkVideoView.this.mVideoHeight)) {
                        if (IjkVideoView.this.mTargetState == 3) {
                            IjkVideoView.this.start();
                        } else {
                            if (IjkVideoView.this.isPlaying() || i22 != 0) {
                                return;
                            }
                            IjkVideoView.this.getCurrentPosition();
                        }
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.4
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                IjkVideoView.this.mCurrentState = 5;
                IjkVideoView.this.mTargetState = 5;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if (IjkVideoView.this.mOnCompletionListener != null) {
                    IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.5
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i22, int i32) {
                if (IjkVideoView.this.mOnInfoListener != null) {
                    IjkVideoView.this.mOnInfoListener.onInfo(iMediaPlayer, i22, i32);
                }
                if (i22 == 3) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    return true;
                }
                if (i22 == 901) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    return true;
                }
                if (i22 == 902) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    return true;
                }
                if (i22 == 10001) {
                    IjkVideoView.this.mVideoRotationDegree = i32;
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i32);
                    if (IjkVideoView.this.mRenderView == null) {
                        return true;
                    }
                    IjkVideoView.this.mRenderView.setVideoRotation(i32);
                    return true;
                }
                if (i22 == 10002) {
                    Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    return true;
                }
                switch (i22) {
                    case 700:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        break;
                    case 701:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_START:");
                        break;
                    case 702:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BUFFERING_END:");
                        break;
                    case 703:
                        Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i32);
                        break;
                    default:
                        switch (i22) {
                            case 800:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                                break;
                            case 801:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                                break;
                            case 802:
                                Log.d(IjkVideoView.this.TAG, "MEDIA_INFO_METADATA_UPDATE:");
                                break;
                        }
                }
                return true;
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer iMediaPlayer, int i22, int i32) {
                IjkVideoView.this.postSetKeepScreenOn(false);
                Log.d(IjkVideoView.this.TAG, "Error: " + i22 + "," + i32);
                IjkVideoView.this.mCurrentState = -1;
                IjkVideoView.this.mTargetState = -1;
                if (IjkVideoView.this.mMediaController != null) {
                    IjkVideoView.this.mMediaController.hide();
                }
                if ((IjkVideoView.this.mOnErrorListener == null || !IjkVideoView.this.mOnErrorListener.onError(IjkVideoView.this.mMediaPlayer, i22, i32)) && IjkVideoView.this.getWindowToken() != null) {
                    IjkVideoView.this.mAppContext.getResources();
                    new AlertDialog.Builder(IjkVideoView.this.getContext()).setMessage(i22 == 200 ? "Invalid progressive playback" : "Unknown").setPositiveButton(PLVWebview.MESSAGE_OK, new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.6.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i4) {
                            if (IjkVideoView.this.mOnCompletionListener != null) {
                                IjkVideoView.this.mOnCompletionListener.onCompletion(IjkVideoView.this.mMediaPlayer);
                            }
                        }
                    }).setCancelable(false).show();
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.7
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i22) {
                IjkVideoView.this.mCurrentBufferPercentage = i22;
                if (IjkVideoView.this.onBufferingUpdateListener != null) {
                    IjkVideoView.this.onBufferingUpdateListener.onBufferingUpdate(iMediaPlayer, i22);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.8
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                if (IjkVideoView.this.mOnSeekCompleteListener != null) {
                    IjkVideoView.this.mOnSeekCompleteListener.onSeekComplete(iMediaPlayer);
                }
            }
        };
        this.mOnTimedTextListener = new IMediaPlayer.OnTimedTextListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.9
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnTimedTextListener
            public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
            }
        };
        this.mSEIRefreshListener = new IMediaPlayer.OnSEIRefreshListener() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.10
            @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSEIRefreshListener
            public void onSEIRefresh(IMediaPlayer iMediaPlayer, int i22, byte[] bArr) {
                if (IjkVideoView.this.mOnSEIRefreshListener != null) {
                    IjkVideoView.this.mOnSEIRefreshListener.onSEIRefresh(iMediaPlayer, i22, bArr);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.easefun.polyv.mediasdk.example.widget.media.IjkVideoView.11
            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i22, int i32, int i4) throws IllegalStateException {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceChanged: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceWidth = i32;
                IjkVideoView.this.mSurfaceHeight = i4;
                boolean z2 = true;
                boolean z3 = IjkVideoView.this.mTargetState == 3;
                if (IjkVideoView.this.mRenderView.shouldWaitForResize() && (IjkVideoView.this.mVideoWidth != i32 || IjkVideoView.this.mVideoHeight != i4)) {
                    z2 = false;
                }
                if (IjkVideoView.this.mMediaPlayer != null && z3 && z2) {
                    if (IjkVideoView.this.mSeekWhenPrepared != 0) {
                        IjkVideoView ijkVideoView = IjkVideoView.this;
                        ijkVideoView.seekTo(ijkVideoView.mSeekWhenPrepared);
                    }
                    IjkVideoView.this.start();
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i22, int i32) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceCreated: unmatched render callback\n");
                    return;
                }
                IjkVideoView.this.mSurfaceHolder = iSurfaceHolder;
                if (IjkVideoView.this.mMediaPlayer != null) {
                    IjkVideoView ijkVideoView = IjkVideoView.this;
                    ijkVideoView.bindSurfaceHolder(ijkVideoView.mMediaPlayer, iSurfaceHolder);
                }
            }

            @Override // com.easefun.polyv.mediasdk.example.widget.media.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder) {
                if (iSurfaceHolder.getRenderView() != IjkVideoView.this.mRenderView) {
                    Log.e(IjkVideoView.this.TAG, "onSurfaceDestroyed: unmatched render callback\n");
                } else {
                    IjkVideoView.this.mSurfaceHolder = null;
                    IjkVideoView.this.releaseWithoutStop();
                }
            }
        };
        this.mCurrentAspectRatioIndex = 0;
        this.mCurrentAspectRatio = s_allAspectRatio[0];
        this.mAllRenders = new ArrayList();
        this.mCurrentRenderIndex = 0;
        this.mCurrentRender = 0;
        this.mEnableBackgroundPlay = false;
        this.ijkLogLevel = 3;
        initVideoView(context);
    }
}
