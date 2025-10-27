package com.hyphenate.easeui.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.hyphenate.easeui.R;
import com.yikaobang.yixue.R2;
import java.io.IOException;

/* loaded from: classes4.dex */
public class EasyVideoPlayer extends FrameLayout implements EasyIUserMethods, TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnErrorListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final int UPDATE_INTERVAL = 100;
    private int currentPos;
    private boolean mAutoFullscreen;
    private boolean mAutoPlay;
    private ImageButton mBtnPlayPause;
    private EasyVideoCallback mCallback;
    private View mClickFrame;
    private boolean mControlsDisabled;
    private View mControlsFrame;
    private Handler mHandler;
    private boolean mHideControlsOnPlay;
    private int mInitialPosition;
    private int mInitialTextureHeight;
    private int mInitialTextureWidth;
    private boolean mIsPrepared;
    private TextView mLabelDuration;
    private TextView mLabelPosition;
    private boolean mLoop;
    private Drawable mPauseDrawable;
    private Drawable mPlayDrawable;
    private MediaPlayer mPlayer;
    private EasyVideoProgressCallback mProgressCallback;
    private SeekBar mSeeker;
    private Uri mSource;
    private Surface mSurface;
    private boolean mSurfaceAvailable;
    private TextureView mTextureView;
    private int mThemeColor;
    private final Runnable mUpdateCounters;
    private boolean mWasPlaying;

    public EasyVideoPlayer(Context context) {
        super(context);
        this.mHideControlsOnPlay = true;
        this.mInitialPosition = -1;
        this.mThemeColor = 0;
        this.mAutoFullscreen = false;
        this.mLoop = false;
        this.mUpdateCounters = new Runnable() { // from class: com.hyphenate.easeui.player.EasyVideoPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                if (EasyVideoPlayer.this.mHandler == null || !EasyVideoPlayer.this.mIsPrepared || EasyVideoPlayer.this.mSeeker == null || EasyVideoPlayer.this.mPlayer == null) {
                    return;
                }
                int currentPosition = EasyVideoPlayer.this.mPlayer.getCurrentPosition();
                int duration = EasyVideoPlayer.this.mPlayer.getDuration();
                if ("oppo".equals(Build.BRAND.toLowerCase()) && "OPPO R9sk".equals(Build.MODEL) && currentPosition <= EasyVideoPlayer.this.currentPos) {
                    currentPosition = EasyVideoPlayer.this.currentPos;
                }
                if (currentPosition > duration) {
                    currentPosition = duration;
                }
                EasyVideoPlayer.this.mLabelPosition.setText(Util.getDurationString(currentPosition, false));
                EasyVideoPlayer.this.mLabelDuration.setText(Util.getDurationString(duration, false));
                EasyVideoPlayer.this.mSeeker.setProgress(currentPosition);
                EasyVideoPlayer.this.mSeeker.setMax(duration);
                if (EasyVideoPlayer.this.mProgressCallback != null) {
                    EasyVideoPlayer.this.mProgressCallback.onVideoProgressUpdate(currentPosition, duration);
                }
                if (EasyVideoPlayer.this.mHandler != null) {
                    EasyVideoPlayer.this.mHandler.postDelayed(this, 100L);
                }
            }
        };
        init(context, null);
    }

    private static void LOG(String str, Object... objArr) {
        if (objArr != null) {
            try {
                str = String.format(str, objArr);
            } catch (Exception unused) {
                return;
            }
        }
        Log.d("EasyVideoPlayer", str);
    }

    private void adjustAspectRatio(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        double d3 = i5 / i4;
        int i8 = (int) (i2 * d3);
        if (i3 > i8) {
            i7 = i8;
            i6 = i2;
        } else {
            i6 = (int) (i3 / d3);
            i7 = i3;
        }
        Matrix matrix = new Matrix();
        this.mTextureView.getTransform(matrix);
        matrix.setScale(i6 / i2, i7 / i3);
        matrix.postTranslate((i2 - i6) / 2, (i3 - i7) / 2);
        this.mTextureView.setTransform(matrix);
    }

    private void init(Context context, AttributeSet attributeSet) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setBackgroundColor(-16777216);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.EasyVideoPlayer, 0, 0);
            try {
                String string = typedArrayObtainStyledAttributes.getString(R.styleable.EasyVideoPlayer_easy_source);
                if (string != null && !string.trim().isEmpty()) {
                    this.mSource = Uri.parse(string);
                }
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.EasyVideoPlayer_easy_playDrawable, -1);
                int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.EasyVideoPlayer_easy_pauseDrawable, -1);
                if (resourceId != -1) {
                    this.mPlayDrawable = AppCompatResources.getDrawable(context, resourceId);
                }
                if (resourceId2 != -1) {
                    this.mPauseDrawable = AppCompatResources.getDrawable(context, resourceId2);
                }
                this.mHideControlsOnPlay = typedArrayObtainStyledAttributes.getBoolean(R.styleable.EasyVideoPlayer_easy_hideControlsOnPlay, true);
                this.mAutoPlay = typedArrayObtainStyledAttributes.getBoolean(R.styleable.EasyVideoPlayer_easy_autoPlay, false);
                this.mControlsDisabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.EasyVideoPlayer_easy_disableControls, false);
                this.mThemeColor = typedArrayObtainStyledAttributes.getColor(R.styleable.EasyVideoPlayer_easy_themeColor, Util.resolveColor(context, R.attr.colorPrimary));
                this.mAutoFullscreen = typedArrayObtainStyledAttributes.getBoolean(R.styleable.EasyVideoPlayer_easy_autoFullscreen, false);
                this.mLoop = typedArrayObtainStyledAttributes.getBoolean(R.styleable.EasyVideoPlayer_easy_loop, false);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        } else {
            this.mHideControlsOnPlay = true;
            this.mAutoPlay = false;
            this.mControlsDisabled = false;
            this.mThemeColor = Util.resolveColor(context, R.attr.colorPrimary);
            this.mAutoFullscreen = false;
            this.mLoop = false;
        }
        if (this.mPlayDrawable == null) {
            this.mPlayDrawable = AppCompatResources.getDrawable(context, R.drawable.easy_action_play);
        }
        if (this.mPauseDrawable == null) {
            this.mPauseDrawable = AppCompatResources.getDrawable(context, R.drawable.easy_action_pause);
        }
    }

    private void initClickFrame() {
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.mClickFrame = frameLayout;
        addView(frameLayout, new ViewGroup.LayoutParams(-1, -1));
    }

    private void initControlsFrame() {
        this.mControlsFrame = LayoutInflater.from(getContext()).inflate(R.layout.easy_include_controls, (ViewGroup) this, false);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 80;
        addView(this.mControlsFrame, layoutParams);
        SeekBar seekBar = (SeekBar) this.mControlsFrame.findViewById(R.id.seeker);
        this.mSeeker = seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        TextView textView = (TextView) this.mControlsFrame.findViewById(R.id.position);
        this.mLabelPosition = textView;
        textView.setText(Util.getDurationString(0L, false));
        TextView textView2 = (TextView) this.mControlsFrame.findViewById(R.id.duration);
        this.mLabelDuration = textView2;
        textView2.setText(Util.getDurationString(0L, false));
    }

    private void initPlayButton() {
        this.mBtnPlayPause = new ImageButton(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.mBtnPlayPause.setImageDrawable(this.mPauseDrawable);
        this.mBtnPlayPause.setOnClickListener(this);
        addView(this.mBtnPlayPause, layoutParams);
        this.mBtnPlayPause.setId(R.id.btnPlayPause);
        this.mBtnPlayPause.setVisibility(0);
    }

    private void initTextureView() {
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        TextureView textureView = new TextureView(getContext());
        this.mTextureView = textureView;
        addView(textureView, layoutParams);
        this.mTextureView.setSurfaceTextureListener(this);
    }

    private void invalidateThemeColors() {
        int i2 = Util.isColorDark(this.mThemeColor) ? -1 : -16777216;
        this.mControlsFrame.setBackgroundColor(Util.adjustAlpha(this.mThemeColor, 0.8f));
        tintSelector(this.mBtnPlayPause, i2);
        this.mLabelDuration.setTextColor(i2);
        this.mLabelPosition.setTextColor(i2);
        setTint(this.mSeeker, i2);
        this.mPlayDrawable = tintDrawable(this.mPlayDrawable.mutate(), i2);
        this.mPauseDrawable = tintDrawable(this.mPauseDrawable.mutate(), i2);
    }

    private void prepare() throws IllegalStateException, SecurityException, IllegalArgumentException {
        if (!this.mSurfaceAvailable || this.mSource == null || this.mPlayer == null || this.mIsPrepared) {
            return;
        }
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback != null) {
            easyVideoCallback.onPreparing(this);
        }
        try {
            this.mPlayer.setSurface(this.mSurface);
            setSourceInternal();
        } catch (IOException e2) {
            throwError(e2);
        }
    }

    private void setControlsEnabled(boolean z2) {
        SeekBar seekBar = this.mSeeker;
        if (seekBar == null) {
            return;
        }
        seekBar.setEnabled(z2);
        this.mBtnPlayPause.setEnabled(z2);
        this.mBtnPlayPause.setAlpha(z2 ? 1.0f : 0.4f);
        this.mClickFrame.setEnabled(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(14)
    public void setFullscreen(boolean z2) {
        if (this.mAutoFullscreen) {
            ViewCompat.setFitsSystemWindows(this.mControlsFrame, !z2);
            int i2 = (z2 ? 1 : 0) | R2.attr.ic_info_desc_had_collection;
            if (z2) {
                i2 |= R2.attr.inner_corner_color;
            }
            this.mClickFrame.setSystemUiVisibility(i2);
        }
    }

    private void setSourceInternal() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (this.mSource.getScheme() != null && (this.mSource.getScheme().equals("http") || this.mSource.getScheme().equals("https"))) {
            LOG("Loading web URI: " + this.mSource.toString(), new Object[0]);
            this.mPlayer.setDataSource(this.mSource.toString());
        } else if (this.mSource.getScheme() != null && this.mSource.getScheme().equals("file") && this.mSource.getPath().contains("/android_assets/")) {
            LOG("Loading assets URI: " + this.mSource.toString(), new Object[0]);
            AssetFileDescriptor assetFileDescriptorOpenFd = getContext().getAssets().openFd(this.mSource.toString().replace("file:///android_assets/", ""));
            this.mPlayer.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
            assetFileDescriptorOpenFd.close();
        } else if (this.mSource.getScheme() == null || !this.mSource.getScheme().equals("asset")) {
            LOG("Loading local URI: " + this.mSource.toString(), new Object[0]);
            this.mPlayer.setDataSource(getContext(), this.mSource);
        } else {
            LOG("Loading assets URI: " + this.mSource.toString(), new Object[0]);
            AssetFileDescriptor assetFileDescriptorOpenFd2 = getContext().getAssets().openFd(this.mSource.toString().replace("asset://", ""));
            this.mPlayer.setDataSource(assetFileDescriptorOpenFd2.getFileDescriptor(), assetFileDescriptorOpenFd2.getStartOffset(), assetFileDescriptorOpenFd2.getLength());
            assetFileDescriptorOpenFd2.close();
        }
        this.mPlayer.prepareAsync();
    }

    private static void setTint(@NonNull SeekBar seekBar, @ColorInt int i2) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i2);
        seekBar.setThumbTintList(colorStateListValueOf);
        seekBar.setProgressTintList(colorStateListValueOf);
        seekBar.setSecondaryProgressTintList(colorStateListValueOf);
    }

    private void sourceChanged() throws IllegalStateException, SecurityException, IllegalArgumentException {
        setControlsEnabled(false);
        this.mSeeker.setProgress(0);
        this.mSeeker.setEnabled(false);
        this.mPlayer.reset();
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback != null) {
            easyVideoCallback.onPreparing(this);
        }
        try {
            setSourceInternal();
        } catch (IOException e2) {
            throwError(e2);
        }
    }

    private void throwError(Exception exc) {
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback == null) {
            throw new RuntimeException(exc);
        }
        easyVideoCallback.onError(this, exc);
    }

    private Drawable tintDrawable(@NonNull Drawable drawable, @ColorInt int i2) {
        Drawable drawableWrap = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTint(drawableWrap, i2);
        return drawableWrap;
    }

    private void tintSelector(@NonNull View view, @ColorInt int i2) {
        if (view.getBackground() instanceof RippleDrawable) {
            ((RippleDrawable) view.getBackground()).setColor(ColorStateList.valueOf(Util.adjustAlpha(i2, 0.3f)));
        }
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void disableControls() {
        this.mControlsDisabled = true;
        this.mControlsFrame.setVisibility(8);
        this.mClickFrame.setOnClickListener(null);
        this.mClickFrame.setClickable(false);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void enableControls(boolean z2) {
        this.mControlsDisabled = false;
        if (z2) {
            showControls();
        }
        this.mClickFrame.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.player.EasyVideoPlayer.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EasyVideoPlayer.this.toggleControls();
            }
        });
        this.mClickFrame.setClickable(true);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    @CheckResult
    public int getCurrentPosition() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return -1;
        }
        return mediaPlayer.getCurrentPosition();
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    @CheckResult
    public int getDuration() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return -1;
        }
        return mediaPlayer.getDuration();
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void hideControls() {
        if (this.mControlsDisabled || !isControlsShown() || this.mSeeker == null) {
            return;
        }
        this.mControlsFrame.animate().cancel();
        this.mControlsFrame.setAlpha(1.0f);
        this.mControlsFrame.setVisibility(0);
        this.mControlsFrame.animate().alpha(0.0f).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() { // from class: com.hyphenate.easeui.player.EasyVideoPlayer.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                EasyVideoPlayer.this.setFullscreen(true);
                if (EasyVideoPlayer.this.mControlsFrame != null) {
                    EasyVideoPlayer.this.mControlsFrame.setVisibility(4);
                    EasyVideoPlayer.this.mBtnPlayPause.setVisibility(4);
                }
            }
        }).start();
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    @CheckResult
    public boolean isControlsShown() {
        View view;
        return (this.mControlsDisabled || (view = this.mControlsFrame) == null || view.getAlpha() <= 0.5f) ? false : true;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    @CheckResult
    public boolean isPlaying() {
        MediaPlayer mediaPlayer = this.mPlayer;
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    @CheckResult
    public boolean isPrepared() {
        return this.mPlayer != null && this.mIsPrepared;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i2) {
        LOG("Buffering: %d%%", Integer.valueOf(i2));
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback != null) {
            easyVideoCallback.onBuffering(i2);
        }
        SeekBar seekBar = this.mSeeker;
        if (seekBar != null) {
            if (i2 == 100) {
                seekBar.setSecondaryProgress(0);
            } else {
                seekBar.setSecondaryProgress(seekBar.getMax() * (i2 / 100));
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws IllegalStateException {
        if (view.getId() == R.id.btnPlayPause) {
            if (this.mPlayer.isPlaying()) {
                pause();
                return;
            }
            if (this.mHideControlsOnPlay && !this.mControlsDisabled) {
                hideControls();
            }
            start();
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
        LOG("onCompletion()", new Object[0]);
        if ("oppo".equals(Build.BRAND.toLowerCase()) && "OPPO R9sk".equals(Build.MODEL)) {
            this.currentPos = 0;
        }
        if (this.mLoop) {
            this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.removeCallbacks(this.mUpdateCounters);
            }
            SeekBar seekBar = this.mSeeker;
            seekBar.setProgress(seekBar.getMax());
            showControls();
        } else {
            seekTo(0);
            this.mSeeker.setProgress(0);
            this.mLabelPosition.setText(Util.getDurationString(0L, false));
            this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
            showControls();
        }
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback != null) {
            easyVideoCallback.onCompletion(this);
            if (this.mLoop) {
                this.mCallback.onStarted(this);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LOG("Detached from window", new Object[0]);
        release();
        this.mSeeker = null;
        this.mLabelPosition = null;
        this.mLabelDuration = null;
        this.mBtnPlayPause = null;
        this.mControlsFrame = null;
        this.mClickFrame = null;
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mUpdateCounters);
            this.mHandler = null;
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i2, int i3) {
        String str;
        if (i2 == -38) {
            return false;
        }
        String str2 = "Preparation/playback error (" + i2 + "): ";
        if (i2 == -1010) {
            str = str2 + "Unsupported";
        } else if (i2 == -1007) {
            str = str2 + "Malformed";
        } else if (i2 == -1004) {
            str = str2 + "I/O error";
        } else if (i2 == -110) {
            str = str2 + "Timed out";
        } else if (i2 == 100) {
            str = str2 + "Server died";
        } else if (i2 != 200) {
            str = str2 + "Unknown error";
        } else {
            str = str2 + "Not valid for progressive playback";
        }
        throwError(new Exception(str));
        return false;
    }

    @Override // android.view.View
    public void onFinishInflate() throws IllegalStateException, SecurityException, IllegalArgumentException {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
        setKeepScreenOn(true);
        this.mHandler = new Handler();
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mPlayer = mediaPlayer;
        mediaPlayer.setOnPreparedListener(this);
        this.mPlayer.setOnBufferingUpdateListener(this);
        this.mPlayer.setOnCompletionListener(this);
        this.mPlayer.setOnVideoSizeChangedListener(this);
        this.mPlayer.setOnErrorListener(this);
        this.mPlayer.setAudioStreamType(3);
        this.mPlayer.setLooping(this.mLoop);
        initTextureView();
        initClickFrame();
        initPlayButton();
        initControlsFrame();
        if (this.mControlsDisabled) {
            this.mClickFrame.setOnClickListener(null);
            this.mControlsFrame.setVisibility(8);
        } else {
            this.mClickFrame.setOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.player.EasyVideoPlayer.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    EasyVideoPlayer.this.toggleControls();
                    if (EasyVideoPlayer.this.mCallback != null) {
                        EasyVideoPlayer.this.mCallback.onClickVideoFrame(EasyVideoPlayer.this);
                    }
                }
            });
        }
        invalidateThemeColors();
        setControlsEnabled(false);
        prepare();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
        LOG("onPrepared()", new Object[0]);
        this.mIsPrepared = true;
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback != null) {
            easyVideoCallback.onPrepared(this);
        }
        this.mLabelPosition.setText(Util.getDurationString(0L, false));
        this.mLabelDuration.setText(Util.getDurationString(mediaPlayer.getDuration(), false));
        this.mSeeker.setProgress(0);
        this.mSeeker.setMax(mediaPlayer.getDuration());
        setControlsEnabled(true);
        if (!this.mAutoPlay) {
            this.mPlayer.start();
            this.mPlayer.pause();
            this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
            this.mBtnPlayPause.setVisibility(0);
            return;
        }
        if (!this.mControlsDisabled && this.mHideControlsOnPlay) {
            hideControls();
        }
        start();
        int i2 = this.mInitialPosition;
        if (i2 > 0) {
            seekTo(i2);
            this.mInitialPosition = -1;
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) throws IllegalStateException {
        if (z2) {
            seekTo(i2);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) throws IllegalStateException {
        boolean zIsPlaying = isPlaying();
        this.mWasPlaying = zIsPlaying;
        if (zIsPlaying) {
            this.mPlayer.pause();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) throws IllegalStateException {
        if (this.mWasPlaying) {
            this.mPlayer.start();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) throws IllegalStateException, SecurityException, IllegalArgumentException {
        LOG("Surface texture available: %dx%d", Integer.valueOf(i2), Integer.valueOf(i3));
        this.mInitialTextureWidth = i2;
        this.mInitialTextureHeight = i3;
        this.mSurfaceAvailable = true;
        Surface surface = new Surface(surfaceTexture);
        this.mSurface = surface;
        if (!this.mIsPrepared) {
            prepare();
            return;
        }
        this.mPlayer.setSurface(surface);
        if ("oppo".equals(Build.BRAND.toLowerCase()) && "OPPO R9sk".equals(Build.MODEL)) {
            this.mPlayer.seekTo(getCurrentPosition());
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        LOG("Surface texture destroyed", new Object[0]);
        if ("oppo".equals(Build.BRAND.toLowerCase()) && "OPPO R9sk".equals(Build.MODEL)) {
            this.currentPos = getCurrentPosition();
        }
        this.mSurfaceAvailable = false;
        this.mSurface = null;
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        LOG("Surface texture changed: %dx%d", Integer.valueOf(i2), Integer.valueOf(i3));
        adjustAspectRatio(i2, i3, this.mPlayer.getVideoWidth(), this.mPlayer.getVideoHeight());
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i2, int i3) {
        LOG("Video size changed: %dx%d", Integer.valueOf(i2), Integer.valueOf(i3));
        adjustAspectRatio(this.mInitialTextureWidth, this.mInitialTextureHeight, i2, i3);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void pause() throws IllegalStateException {
        if (this.mPlayer == null || !isPlaying()) {
            return;
        }
        this.mPlayer.pause();
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback != null) {
            easyVideoCallback.onPaused(this);
        }
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        handler.removeCallbacks(this.mUpdateCounters);
        this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void release() {
        this.mIsPrepared = false;
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Throwable unused) {
            }
            this.mPlayer = null;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mUpdateCounters);
            this.mHandler = null;
        }
        LOG("Released player and Handler", new Object[0]);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void reset() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return;
        }
        this.mIsPrepared = false;
        mediaPlayer.reset();
        this.mIsPrepared = false;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void seekTo(@IntRange(from = 0, to = 2147483647L) int i2) throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.seekTo(i2);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setAutoFullscreen(boolean z2) {
        this.mAutoFullscreen = z2;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setAutoPlay(boolean z2) {
        this.mAutoPlay = z2;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setCallback(@NonNull EasyVideoCallback easyVideoCallback) {
        this.mCallback = easyVideoCallback;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setHideControlsOnPlay(boolean z2) {
        this.mHideControlsOnPlay = z2;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setInitialPosition(@IntRange(from = 0, to = 2147483647L) int i2) {
        this.mInitialPosition = i2;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setLoop(boolean z2) {
        this.mLoop = z2;
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(z2);
        }
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setPauseDrawable(@NonNull Drawable drawable) {
        this.mPauseDrawable = drawable;
        if (isPlaying()) {
            this.mBtnPlayPause.setImageDrawable(drawable);
        }
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setPauseDrawableRes(@DrawableRes int i2) {
        setPauseDrawable(AppCompatResources.getDrawable(getContext(), i2));
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setPlayDrawable(@NonNull Drawable drawable) {
        this.mPlayDrawable = drawable;
        if (isPlaying()) {
            return;
        }
        this.mBtnPlayPause.setImageDrawable(drawable);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setPlayDrawableRes(@DrawableRes int i2) {
        setPlayDrawable(AppCompatResources.getDrawable(getContext(), i2));
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setProgressCallback(@NonNull EasyVideoProgressCallback easyVideoProgressCallback) {
        this.mProgressCallback = easyVideoProgressCallback;
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setSource(@NonNull Uri uri) throws IllegalStateException, SecurityException, IllegalArgumentException {
        boolean z2 = this.mSource != null;
        if (z2) {
            stop();
        }
        this.mSource = uri;
        if (this.mPlayer != null) {
            if (z2) {
                sourceChanged();
            } else {
                prepare();
            }
        }
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setThemeColor(@ColorInt int i2) {
        this.mThemeColor = i2;
        invalidateThemeColors();
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setThemeColorRes(@ColorRes int i2) {
        setThemeColor(ContextCompat.getColor(getContext(), i2));
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void setVolume(@FloatRange(from = 0.0d, to = 1.0d) float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3) {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null || !this.mIsPrepared) {
            throw new IllegalStateException("You cannot use setVolume(float, float) until the player is prepared.");
        }
        mediaPlayer.setVolume(f2, f3);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void showControls() {
        if (this.mControlsDisabled || isControlsShown() || this.mSeeker == null) {
            return;
        }
        this.mControlsFrame.animate().cancel();
        this.mControlsFrame.setAlpha(0.0f);
        this.mControlsFrame.setVisibility(0);
        this.mBtnPlayPause.setVisibility(0);
        this.mControlsFrame.animate().alpha(1.0f).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() { // from class: com.hyphenate.easeui.player.EasyVideoPlayer.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (EasyVideoPlayer.this.mAutoFullscreen) {
                    EasyVideoPlayer.this.setFullscreen(false);
                }
            }
        }).start();
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void start() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.start();
        EasyVideoCallback easyVideoCallback = this.mCallback;
        if (easyVideoCallback != null) {
            easyVideoCallback.onStarted(this);
        }
        if (this.mHandler == null) {
            this.mHandler = new Handler();
        }
        this.mHandler.post(this.mUpdateCounters);
        this.mBtnPlayPause.setImageDrawable(this.mPauseDrawable);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void stop() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer == null) {
            return;
        }
        try {
            mediaPlayer.stop();
        } catch (Throwable unused) {
        }
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        handler.removeCallbacks(this.mUpdateCounters);
        this.mBtnPlayPause.setImageDrawable(this.mPlayDrawable);
    }

    @Override // com.hyphenate.easeui.player.EasyIUserMethods
    public void toggleControls() {
        if (this.mControlsDisabled) {
            return;
        }
        if (isControlsShown()) {
            hideControls();
        } else {
            showControls();
        }
    }

    public EasyVideoPlayer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHideControlsOnPlay = true;
        this.mInitialPosition = -1;
        this.mThemeColor = 0;
        this.mAutoFullscreen = false;
        this.mLoop = false;
        this.mUpdateCounters = new Runnable() { // from class: com.hyphenate.easeui.player.EasyVideoPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                if (EasyVideoPlayer.this.mHandler == null || !EasyVideoPlayer.this.mIsPrepared || EasyVideoPlayer.this.mSeeker == null || EasyVideoPlayer.this.mPlayer == null) {
                    return;
                }
                int currentPosition = EasyVideoPlayer.this.mPlayer.getCurrentPosition();
                int duration = EasyVideoPlayer.this.mPlayer.getDuration();
                if ("oppo".equals(Build.BRAND.toLowerCase()) && "OPPO R9sk".equals(Build.MODEL) && currentPosition <= EasyVideoPlayer.this.currentPos) {
                    currentPosition = EasyVideoPlayer.this.currentPos;
                }
                if (currentPosition > duration) {
                    currentPosition = duration;
                }
                EasyVideoPlayer.this.mLabelPosition.setText(Util.getDurationString(currentPosition, false));
                EasyVideoPlayer.this.mLabelDuration.setText(Util.getDurationString(duration, false));
                EasyVideoPlayer.this.mSeeker.setProgress(currentPosition);
                EasyVideoPlayer.this.mSeeker.setMax(duration);
                if (EasyVideoPlayer.this.mProgressCallback != null) {
                    EasyVideoPlayer.this.mProgressCallback.onVideoProgressUpdate(currentPosition, duration);
                }
                if (EasyVideoPlayer.this.mHandler != null) {
                    EasyVideoPlayer.this.mHandler.postDelayed(this, 100L);
                }
            }
        };
        init(context, attributeSet);
    }

    public EasyVideoPlayer(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mHideControlsOnPlay = true;
        this.mInitialPosition = -1;
        this.mThemeColor = 0;
        this.mAutoFullscreen = false;
        this.mLoop = false;
        this.mUpdateCounters = new Runnable() { // from class: com.hyphenate.easeui.player.EasyVideoPlayer.1
            @Override // java.lang.Runnable
            public void run() {
                if (EasyVideoPlayer.this.mHandler == null || !EasyVideoPlayer.this.mIsPrepared || EasyVideoPlayer.this.mSeeker == null || EasyVideoPlayer.this.mPlayer == null) {
                    return;
                }
                int currentPosition = EasyVideoPlayer.this.mPlayer.getCurrentPosition();
                int duration = EasyVideoPlayer.this.mPlayer.getDuration();
                if ("oppo".equals(Build.BRAND.toLowerCase()) && "OPPO R9sk".equals(Build.MODEL) && currentPosition <= EasyVideoPlayer.this.currentPos) {
                    currentPosition = EasyVideoPlayer.this.currentPos;
                }
                if (currentPosition > duration) {
                    currentPosition = duration;
                }
                EasyVideoPlayer.this.mLabelPosition.setText(Util.getDurationString(currentPosition, false));
                EasyVideoPlayer.this.mLabelDuration.setText(Util.getDurationString(duration, false));
                EasyVideoPlayer.this.mSeeker.setProgress(currentPosition);
                EasyVideoPlayer.this.mSeeker.setMax(duration);
                if (EasyVideoPlayer.this.mProgressCallback != null) {
                    EasyVideoPlayer.this.mProgressCallback.onVideoProgressUpdate(currentPosition, duration);
                }
                if (EasyVideoPlayer.this.mHandler != null) {
                    EasyVideoPlayer.this.mHandler.postDelayed(this, 100L);
                }
            }
        };
        init(context, attributeSet);
    }
}
