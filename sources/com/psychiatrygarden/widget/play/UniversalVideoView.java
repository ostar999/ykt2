package com.psychiatrygarden.widget.play;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.psychiatrygarden.widget.play.OrientationDetector;
import com.psychiatrygarden.widget.play.UniversalMediaController;
import com.yikaobang.yixue.R;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes6.dex */
public class UniversalVideoView extends SurfaceView implements UniversalMediaController.MediaPlayerControl, OrientationDetector.OrientationChangeListener {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private String TAG;
    private int mAudioSession;
    private boolean mAutoRotation;
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private final MediaPlayer.OnCompletionListener mCompletionListener;
    private Context mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private MediaPlayer.OnErrorListener mErrorListener;
    private boolean mFitXY;
    private MediaPlayer.OnInfoListener mInfoListener;
    private UniversalMediaController mMediaController;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private MediaPlayer.OnErrorListener mOnErrorListener;
    private MediaPlayer.OnInfoListener mOnInfoListener;
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private OrientationDetector mOrientationDetector;
    private boolean mPreparedBeforeStart;
    MediaPlayer.OnPreparedListener mPreparedListener;
    SurfaceHolder.Callback mSHCallback;
    private int mSeekWhenPrepared;
    MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoViewLayoutHeight;
    private int mVideoViewLayoutWidth;
    private int mVideoWidth;
    private VideoViewCallback videoViewCallback;

    public interface VideoViewCallback {
        void onBufferingEnd(final MediaPlayer mediaPlayer);

        void onBufferingStart(final MediaPlayer mediaPlayer);

        void onPause(final MediaPlayer mediaPlayer);

        void onScaleChange(boolean isFullscreen);

        void onStart(final MediaPlayer mediaPlayer);
    }

    public UniversalVideoView(Context context) {
        this(context, null);
    }

    private void attachMediaController() {
        UniversalMediaController universalMediaController;
        if (this.mMediaPlayer == null || (universalMediaController = this.mMediaController) == null) {
            return;
        }
        universalMediaController.setMediaPlayer(this);
        this.mMediaController.setEnabled(isInPlaybackState());
        this.mMediaController.hide();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableOrientationDetect() {
        OrientationDetector orientationDetector = this.mOrientationDetector;
        if (orientationDetector != null) {
            orientationDetector.disable();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableOrientationDetect() {
        if (this.mAutoRotation && this.mOrientationDetector == null) {
            OrientationDetector orientationDetector = new OrientationDetector(this.mContext);
            this.mOrientationDetector = orientationDetector;
            orientationDetector.setOrientationChangeListener(this);
            this.mOrientationDetector.enable();
        }
    }

    private void initVideoView() {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    private boolean isInPlaybackState() {
        int i2;
        return (this.mMediaPlayer == null || (i2 = this.mCurrentState) == -1 || i2 == 0 || i2 == 1) ? false : true;
    }

    private void onMeasureFitXY(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.getDefaultSize(this.mVideoWidth, widthMeasureSpec), View.getDefaultSize(this.mVideoHeight, heightMeasureSpec));
    }

    private void onMeasureKeepAspectRatio(int widthMeasureSpec, int heightMeasureSpec) {
        int i2;
        int defaultSize = View.getDefaultSize(this.mVideoWidth, widthMeasureSpec);
        int defaultSize2 = View.getDefaultSize(this.mVideoHeight, heightMeasureSpec);
        if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
            int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
            if (mode == 1073741824 && mode2 == 1073741824) {
                int i3 = this.mVideoWidth;
                int i4 = i3 * size2;
                int i5 = this.mVideoHeight;
                if (i4 < size * i5) {
                    defaultSize = (i3 * size2) / i5;
                    defaultSize2 = size2;
                } else {
                    if (i3 * size2 > size * i5) {
                        defaultSize2 = (i5 * size) / i3;
                        defaultSize = size;
                    }
                    defaultSize = size;
                    defaultSize2 = size2;
                }
            } else if (mode == 1073741824) {
                int i6 = (this.mVideoHeight * size) / this.mVideoWidth;
                if (mode2 != Integer.MIN_VALUE || i6 <= size2) {
                    defaultSize2 = i6;
                    defaultSize = size;
                }
                defaultSize = size;
                defaultSize2 = size2;
            } else {
                if (mode2 == 1073741824) {
                    i2 = (this.mVideoWidth * size2) / this.mVideoHeight;
                    if (mode == Integer.MIN_VALUE && i2 > size) {
                        defaultSize = size;
                    }
                    defaultSize2 = size2;
                } else {
                    int i7 = this.mVideoWidth;
                    int i8 = this.mVideoHeight;
                    if (mode2 != Integer.MIN_VALUE || i8 <= size2) {
                        i2 = i7;
                        size2 = i8;
                    } else {
                        i2 = (size2 * i7) / i8;
                    }
                    if (mode == Integer.MIN_VALUE && i2 > size) {
                        defaultSize2 = (i8 * size) / i7;
                        defaultSize = size;
                    }
                }
                defaultSize = i2;
                defaultSize2 = size2;
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openVideo() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (this.mUri == null || this.mSurfaceHolder == null) {
            return;
        }
        ((AudioManager) this.mContext.getSystemService("audio")).requestAudioFocus(null, 3, 1);
        release(false);
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mMediaPlayer = mediaPlayer;
            int i2 = this.mAudioSession;
            if (i2 != 0) {
                mediaPlayer.setAudioSessionId(i2);
            } else {
                this.mAudioSession = mediaPlayer.getAudioSessionId();
            }
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mCurrentBufferPercentage = 0;
            this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
            this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setScreenOnWhilePlaying(true);
            this.mMediaPlayer.prepareAsync();
            this.mCurrentState = 1;
            attachMediaController();
        } catch (IOException e2) {
            Log.w(this.TAG, "Unable to open content: " + this.mUri, e2);
            this.mCurrentState = -1;
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release(boolean cleartargetstate) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (cleartargetstate) {
                this.mTargetState = 0;
            }
        }
    }

    private void toggleMediaControlsVisibility() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public boolean canPause() {
        return this.mCanPause;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public void closePlayer() {
        release(true);
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public int getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    @Override // android.view.View
    @SuppressLint({"NewApi"})
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(UniversalVideoView.class.getName());
    }

    @Override // android.view.View
    @SuppressLint({"NewApi"})
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(UniversalVideoView.class.getName());
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) throws IllegalStateException {
        boolean z2 = (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) ? false : true;
        if (isInPlaybackState() && z2 && this.mMediaController != null) {
            if (keyCode == 79 || keyCode == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                } else {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            }
            if (keyCode == 126) {
                if (!this.mMediaPlayer.isPlaying()) {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            }
            if (keyCode == 86 || keyCode == 127) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                }
                return true;
            }
            toggleMediaControlsVisibility();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // android.view.SurfaceView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mFitXY) {
            onMeasureFitXY(widthMeasureSpec, heightMeasureSpec);
        } else {
            onMeasureKeepAspectRatio(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override // com.psychiatrygarden.widget.play.OrientationDetector.OrientationChangeListener
    public void onOrientationChanged(int screenOrientation, OrientationDetector.Direction direction) {
        if (this.mAutoRotation) {
            if (direction == OrientationDetector.Direction.PORTRAIT) {
                setFullscreen(false, 1);
                return;
            }
            if (direction == OrientationDetector.Direction.REVERSE_PORTRAIT) {
                setFullscreen(false, 7);
            } else if (direction == OrientationDetector.Direction.LANDSCAPE) {
                setFullscreen(true, 0);
            } else if (direction == OrientationDetector.Direction.REVERSE_LANDSCAPE) {
                setFullscreen(true, 8);
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isInPlaybackState() || this.mMediaController == null) {
            return false;
        }
        toggleMediaControlsVisibility();
        return false;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent ev) {
        if (!isInPlaybackState() || this.mMediaController == null) {
            return false;
        }
        toggleMediaControlsVisibility();
        return false;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public void pause() throws IllegalStateException {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
            VideoViewCallback videoViewCallback = this.videoViewCallback;
            if (videoViewCallback != null) {
                videoViewCallback.onPause(this.mMediaPlayer);
            }
        }
        this.mTargetState = 4;
    }

    public int resolveAdjustedSize(int desiredSize, int measureSpec) {
        return View.getDefaultSize(desiredSize, measureSpec);
    }

    public void resume() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        openVideo();
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public void seekTo(int msec) throws IllegalStateException {
        if (!isInPlaybackState()) {
            this.mSeekWhenPrepared = msec;
        } else {
            this.mMediaPlayer.seekTo(msec);
            this.mSeekWhenPrepared = 0;
        }
    }

    public void setAutoRotation(boolean auto) {
        this.mAutoRotation = auto;
    }

    public void setFitXY(boolean fitXY) {
        this.mFitXY = fitXY;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public void setFullscreen(boolean z2) {
        setFullscreen(z2, !z2 ? 1 : 0);
    }

    public void setMediaController(UniversalMediaController controller) {
        UniversalMediaController universalMediaController = this.mMediaController;
        if (universalMediaController != null) {
            universalMediaController.hide();
        }
        this.mMediaController = controller;
        attachMediaController();
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener l2) {
        this.mOnCompletionListener = l2;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener l2) {
        this.mOnErrorListener = l2;
    }

    public void setOnInfoListener(MediaPlayer.OnInfoListener l2) {
        this.mOnInfoListener = l2;
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l2) {
        this.mOnPreparedListener = l2;
    }

    public void setVideoPath(String path) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        setVideoURI(uri, null);
    }

    public void setVideoViewCallback(VideoViewCallback callback) {
        this.videoViewCallback = callback;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public void start() throws IllegalStateException {
        UniversalMediaController universalMediaController;
        if (!this.mPreparedBeforeStart && (universalMediaController = this.mMediaController) != null) {
            universalMediaController.showLoading();
        }
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
            VideoViewCallback videoViewCallback = this.videoViewCallback;
            if (videoViewCallback != null) {
                videoViewCallback.onStart(this.mMediaPlayer);
            }
        }
        this.mTargetState = 3;
    }

    public void stopPlayback() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
        }
    }

    public void suspend() {
        release(false);
    }

    public UniversalVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override // com.psychiatrygarden.widget.play.UniversalMediaController.MediaPlayerControl
    public void setFullscreen(boolean fullscreen, int screenOrientation) {
        Activity activity = (Activity) this.mContext;
        if (fullscreen) {
            if (this.mVideoViewLayoutWidth == 0 && this.mVideoViewLayoutHeight == 0) {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                this.mVideoViewLayoutWidth = layoutParams.width;
                this.mVideoViewLayoutHeight = layoutParams.height;
            }
            activity.getWindow().addFlags(1024);
            activity.setRequestedOrientation(screenOrientation);
        } else {
            ViewGroup.LayoutParams layoutParams2 = getLayoutParams();
            layoutParams2.width = this.mVideoViewLayoutWidth;
            layoutParams2.height = this.mVideoViewLayoutHeight;
            setLayoutParams(layoutParams2);
            activity.getWindow().clearFlags(1024);
            activity.setRequestedOrientation(screenOrientation);
        }
        this.mMediaController.toggleButtons(fullscreen);
        VideoViewCallback videoViewCallback = this.videoViewCallback;
        if (videoViewCallback != null) {
            videoViewCallback.onScaleChange(fullscreen);
        }
    }

    public void setVideoURI(Uri uri, Map<String, String> headers) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mUri = uri;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    public UniversalVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = "UniversalVideoView";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mFitXY = false;
        this.mAutoRotation = false;
        this.mVideoViewLayoutWidth = 0;
        this.mVideoViewLayoutHeight = 0;
        this.mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() { // from class: com.psychiatrygarden.widget.play.UniversalVideoView.1
            @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                UniversalVideoView.this.mVideoWidth = mp.getVideoWidth();
                UniversalVideoView.this.mVideoHeight = mp.getVideoHeight();
                Log.d(UniversalVideoView.this.TAG, String.format("onVideoSizeChanged width=%d,height=%d", Integer.valueOf(UniversalVideoView.this.mVideoWidth), Integer.valueOf(UniversalVideoView.this.mVideoHeight)));
                if (UniversalVideoView.this.mVideoWidth == 0 || UniversalVideoView.this.mVideoHeight == 0) {
                    return;
                }
                UniversalVideoView.this.getHolder().setFixedSize(UniversalVideoView.this.mVideoWidth, UniversalVideoView.this.mVideoHeight);
                UniversalVideoView.this.requestLayout();
            }
        };
        this.mPreparedListener = new MediaPlayer.OnPreparedListener() { // from class: com.psychiatrygarden.widget.play.UniversalVideoView.2
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mp) throws IllegalStateException {
                UniversalVideoView.this.mCurrentState = 2;
                UniversalVideoView universalVideoView = UniversalVideoView.this;
                universalVideoView.mCanPause = universalVideoView.mCanSeekBack = universalVideoView.mCanSeekForward = true;
                UniversalVideoView.this.mPreparedBeforeStart = true;
                if (UniversalVideoView.this.mMediaController != null) {
                    UniversalVideoView.this.mMediaController.hideLoading();
                }
                if (UniversalVideoView.this.mOnPreparedListener != null) {
                    UniversalVideoView.this.mOnPreparedListener.onPrepared(UniversalVideoView.this.mMediaPlayer);
                }
                if (UniversalVideoView.this.mMediaController != null) {
                    UniversalVideoView.this.mMediaController.setEnabled(true);
                }
                UniversalVideoView.this.mVideoWidth = mp.getVideoWidth();
                UniversalVideoView.this.mVideoHeight = mp.getVideoHeight();
                int i2 = UniversalVideoView.this.mSeekWhenPrepared;
                if (i2 != 0) {
                    UniversalVideoView.this.seekTo(i2);
                }
                if (UniversalVideoView.this.mVideoWidth == 0 || UniversalVideoView.this.mVideoHeight == 0) {
                    if (UniversalVideoView.this.mTargetState == 3) {
                        UniversalVideoView.this.start();
                        return;
                    }
                    return;
                }
                UniversalVideoView.this.getHolder().setFixedSize(UniversalVideoView.this.mVideoWidth, UniversalVideoView.this.mVideoHeight);
                if (UniversalVideoView.this.mSurfaceWidth == UniversalVideoView.this.mVideoWidth && UniversalVideoView.this.mSurfaceHeight == UniversalVideoView.this.mVideoHeight) {
                    if (UniversalVideoView.this.mTargetState == 3) {
                        UniversalVideoView.this.start();
                        if (UniversalVideoView.this.mMediaController != null) {
                            UniversalVideoView.this.mMediaController.show();
                            return;
                        }
                        return;
                    }
                    if (UniversalVideoView.this.isPlaying()) {
                        return;
                    }
                    if ((i2 != 0 || UniversalVideoView.this.getCurrentPosition() > 0) && UniversalVideoView.this.mMediaController != null) {
                        UniversalVideoView.this.mMediaController.show(0);
                    }
                }
            }
        };
        this.mCompletionListener = new MediaPlayer.OnCompletionListener() { // from class: com.psychiatrygarden.widget.play.UniversalVideoView.3
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mp) {
                UniversalVideoView.this.mCurrentState = 5;
                UniversalVideoView.this.mTargetState = 5;
                if (UniversalVideoView.this.mMediaController != null) {
                    boolean zIsPlaying = UniversalVideoView.this.mMediaPlayer.isPlaying();
                    int i2 = UniversalVideoView.this.mCurrentState;
                    UniversalVideoView.this.mMediaController.showComplete();
                    Log.d(UniversalVideoView.this.TAG, String.format("a=%s,b=%d", Boolean.valueOf(zIsPlaying), Integer.valueOf(i2)));
                }
                if (UniversalVideoView.this.mOnCompletionListener != null) {
                    UniversalVideoView.this.mOnCompletionListener.onCompletion(UniversalVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new MediaPlayer.OnInfoListener() { // from class: com.psychiatrygarden.widget.play.UniversalVideoView.4
            /* JADX WARN: Removed duplicated region for block: B:22:0x007c  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x008d A[RETURN] */
            @Override // android.media.MediaPlayer.OnInfoListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onInfo(android.media.MediaPlayer r5, int r6, int r7) {
                /*
                    r4 = this;
                    r0 = 701(0x2bd, float:9.82E-43)
                    r1 = 1
                    r2 = 0
                    if (r6 == r0) goto L40
                    r0 = 702(0x2be, float:9.84E-43)
                    if (r6 == r0) goto Lc
                    r0 = r2
                    goto L74
                Lc:
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    java.lang.String r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$200(r0)
                    java.lang.String r3 = "onInfo MediaPlayer.MEDIA_INFO_BUFFERING_END"
                    android.util.Log.d(r0, r3)
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalVideoView$VideoViewCallback r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1600(r0)
                    if (r0 == 0) goto L2e
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalVideoView$VideoViewCallback r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1600(r0)
                    com.psychiatrygarden.widget.play.UniversalVideoView r3 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    android.media.MediaPlayer r3 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1000(r3)
                    r0.onBufferingEnd(r3)
                L2e:
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalMediaController r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$800(r0)
                    if (r0 == 0) goto L73
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalMediaController r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$800(r0)
                    r0.hideLoading()
                    goto L73
                L40:
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    java.lang.String r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$200(r0)
                    java.lang.String r3 = "onInfo MediaPlayer.MEDIA_INFO_BUFFERING_START"
                    android.util.Log.d(r0, r3)
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalVideoView$VideoViewCallback r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1600(r0)
                    if (r0 == 0) goto L62
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalVideoView$VideoViewCallback r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1600(r0)
                    com.psychiatrygarden.widget.play.UniversalVideoView r3 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    android.media.MediaPlayer r3 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1000(r3)
                    r0.onBufferingStart(r3)
                L62:
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalMediaController r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$800(r0)
                    if (r0 == 0) goto L73
                    com.psychiatrygarden.widget.play.UniversalVideoView r0 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    com.psychiatrygarden.widget.play.UniversalMediaController r0 = com.psychiatrygarden.widget.play.UniversalVideoView.access$800(r0)
                    r0.showLoading()
                L73:
                    r0 = r1
                L74:
                    com.psychiatrygarden.widget.play.UniversalVideoView r3 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    android.media.MediaPlayer$OnInfoListener r3 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1700(r3)
                    if (r3 == 0) goto L8d
                    com.psychiatrygarden.widget.play.UniversalVideoView r3 = com.psychiatrygarden.widget.play.UniversalVideoView.this
                    android.media.MediaPlayer$OnInfoListener r3 = com.psychiatrygarden.widget.play.UniversalVideoView.access$1700(r3)
                    boolean r5 = r3.onInfo(r5, r6, r7)
                    if (r5 != 0) goto L8c
                    if (r0 == 0) goto L8b
                    goto L8c
                L8b:
                    r1 = r2
                L8c:
                    return r1
                L8d:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.play.UniversalVideoView.AnonymousClass4.onInfo(android.media.MediaPlayer, int, int):boolean");
            }
        };
        this.mErrorListener = new MediaPlayer.OnErrorListener() { // from class: com.psychiatrygarden.widget.play.UniversalVideoView.5
            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
                Log.d(UniversalVideoView.this.TAG, "Error: " + framework_err + "," + impl_err);
                UniversalVideoView.this.mCurrentState = -1;
                UniversalVideoView.this.mTargetState = -1;
                if (UniversalVideoView.this.mMediaController != null) {
                    UniversalVideoView.this.mMediaController.showError();
                }
                if (UniversalVideoView.this.mOnErrorListener != null) {
                    UniversalVideoView.this.mOnErrorListener.onError(UniversalVideoView.this.mMediaPlayer, framework_err, impl_err);
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() { // from class: com.psychiatrygarden.widget.play.UniversalVideoView.6
            @Override // android.media.MediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                UniversalVideoView.this.mCurrentBufferPercentage = percent;
            }
        };
        this.mSHCallback = new SurfaceHolder.Callback() { // from class: com.psychiatrygarden.widget.play.UniversalVideoView.7
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder, int format, int w2, int h2) throws IllegalStateException {
                UniversalVideoView.this.mSurfaceWidth = w2;
                UniversalVideoView.this.mSurfaceHeight = h2;
                boolean z2 = UniversalVideoView.this.mTargetState == 3;
                boolean z3 = UniversalVideoView.this.mVideoWidth == w2 && UniversalVideoView.this.mVideoHeight == h2;
                if (UniversalVideoView.this.mMediaPlayer != null && z2 && z3) {
                    if (UniversalVideoView.this.mSeekWhenPrepared != 0) {
                        UniversalVideoView universalVideoView = UniversalVideoView.this;
                        universalVideoView.seekTo(universalVideoView.mSeekWhenPrepared);
                    }
                    UniversalVideoView.this.start();
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                UniversalVideoView.this.mSurfaceHolder = holder;
                UniversalVideoView.this.openVideo();
                UniversalVideoView.this.enableOrientationDetect();
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder) {
                UniversalVideoView.this.mSurfaceHolder = null;
                if (UniversalVideoView.this.mMediaController != null) {
                    UniversalVideoView.this.mMediaController.hide();
                }
                UniversalVideoView.this.release(true);
                UniversalVideoView.this.disableOrientationDetect();
            }
        };
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.UniversalVideoView, 0, 0);
        this.mFitXY = typedArrayObtainStyledAttributes.getBoolean(1, false);
        this.mAutoRotation = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        initVideoView();
    }
}
