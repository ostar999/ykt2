package com.psychiatrygarden.widget.play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.yikaobang.yixue.R;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes6.dex */
public class UniversalMediaController extends FrameLayout {
    private static final int FADE_OUT = 1;
    private static final int HIDE_COMPLETE = 8;
    private static final int HIDE_ERROR = 6;
    private static final int HIDE_LOADING = 4;
    private static final int SHOW_COMPLETE = 7;
    private static final int SHOW_ERROR = 5;
    private static final int SHOW_LOADING = 3;
    private static final int SHOW_PROGRESS = 2;
    private static final int STATE_COMPLETE = 5;
    private static final int STATE_ERROR = 4;
    private static final int STATE_LOADING = 3;
    private static final int STATE_PAUSE = 2;
    private static final int STATE_PLAYING = 1;
    private static final int sDefaultTimeout = 15000;
    private ViewGroup errorLayout;
    boolean handled;
    public LinearLayout llay_loading;
    private ViewGroup loadingLayout;
    public View mBackButton;
    private View mCenterPlayButton;
    private final View.OnClickListener mCenterPlayListener;
    private Context mContext;
    private View mControlLayout;
    private TextView mCurrentTime;
    private boolean mDragging;
    private TextView mEndTime;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler;
    private boolean mIsFullScreen;
    private final View.OnClickListener mPauseListener;
    private MediaPlayerControl mPlayer;
    private ProgressBar mProgress;
    private boolean mScalable;
    private ImageButton mScaleButton;
    private final View.OnClickListener mScaleListener;
    private final SeekBar.OnSeekBarChangeListener mSeekListener;
    private boolean mShowing;
    private int mState;
    private TextView mTitle;
    private View mTitleLayout;
    private final View.OnTouchListener mTouchListener;
    private ImageButton mTurnButton;
    public TextView tv_right_down_state;

    public interface MediaPlayerControl {
        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        void closePlayer();

        int getBufferPercentage();

        int getCurrentPosition();

        int getDuration();

        boolean isPlaying();

        void pause();

        void seekTo(int pos);

        void setFullscreen(boolean fullscreen);

        void setFullscreen(boolean fullscreen, int screenOrientation);

        void start();
    }

    public UniversalMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mShowing = true;
        this.mScalable = false;
        this.mIsFullScreen = false;
        this.mState = 3;
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        UniversalMediaController.this.hide();
                        break;
                    case 2:
                        int progress = UniversalMediaController.this.setProgress();
                        if (!UniversalMediaController.this.mDragging && UniversalMediaController.this.mShowing && UniversalMediaController.this.mPlayer != null && UniversalMediaController.this.mPlayer.isPlaying()) {
                            sendMessageDelayed(obtainMessage(2), 1000 - (progress % 1000));
                            break;
                        }
                        break;
                    case 3:
                        UniversalMediaController.this.show();
                        UniversalMediaController.this.showCenterView(R.id.loading_layout);
                        break;
                    case 4:
                    case 6:
                    case 8:
                        UniversalMediaController.this.hide();
                        UniversalMediaController.this.hideCenterView();
                        break;
                    case 5:
                        UniversalMediaController.this.show();
                        UniversalMediaController.this.showCenterView(R.id.error_layout);
                        break;
                }
            }
        };
        this.handled = false;
        this.mTouchListener = new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.play.a
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f16804c.lambda$new$0(view, motionEvent);
            }
        };
        this.mPauseListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (UniversalMediaController.this.mPlayer != null) {
                    UniversalMediaController.this.doPauseResume();
                    UniversalMediaController.this.show(15000);
                }
            }
        };
        this.mScaleListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                UniversalMediaController.this.mIsFullScreen = !r2.mIsFullScreen;
                UniversalMediaController.this.updateScaleButton();
                UniversalMediaController.this.updateBackButton();
                if (UniversalMediaController.this.mPlayer != null) {
                    UniversalMediaController.this.mPlayer.setFullscreen(UniversalMediaController.this.mIsFullScreen);
                }
            }
        };
        this.mCenterPlayListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                UniversalMediaController.this.hideCenterView();
                UniversalMediaController.this.mPlayer.start();
            }
        };
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.5
            int newPosition = 0;
            boolean change = false;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
                if (UniversalMediaController.this.mPlayer == null || !fromuser) {
                    return;
                }
                this.newPosition = (int) ((UniversalMediaController.this.mPlayer.getDuration() * progress) / 1000);
                this.change = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar bar) {
                if (UniversalMediaController.this.mPlayer == null) {
                    return;
                }
                UniversalMediaController.this.show(3600000);
                UniversalMediaController.this.mDragging = true;
                UniversalMediaController.this.mHandler.removeMessages(2);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar bar) {
                if (UniversalMediaController.this.mPlayer == null) {
                    return;
                }
                if (this.change) {
                    UniversalMediaController.this.mPlayer.seekTo(this.newPosition);
                    if (UniversalMediaController.this.mCurrentTime != null) {
                        UniversalMediaController.this.mCurrentTime.setText(UniversalMediaController.this.stringForTime(this.newPosition));
                    }
                }
                UniversalMediaController.this.mDragging = false;
                UniversalMediaController.this.setProgress();
                UniversalMediaController.this.updatePausePlay();
                UniversalMediaController.this.show(15000);
                UniversalMediaController.this.mShowing = true;
                UniversalMediaController.this.mHandler.sendEmptyMessage(2);
            }
        };
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.UniversalMediaController);
        this.mScalable = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        init(context);
    }

    private void disableUnsupportedButtons() {
        MediaPlayerControl mediaPlayerControl;
        try {
            if (this.mTurnButton == null || (mediaPlayerControl = this.mPlayer) == null || mediaPlayerControl.canPause()) {
                return;
            }
            this.mTurnButton.setEnabled(false);
        } catch (IncompatibleClassChangeError unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doPauseResume() {
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.pause();
        } else {
            this.mPlayer.start();
        }
        updatePausePlay();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideCenterView() {
        if (this.mCenterPlayButton.getVisibility() == 0) {
            this.mCenterPlayButton.setVisibility(8);
        }
        if (this.errorLayout.getVisibility() == 0) {
            this.errorLayout.setVisibility(8);
        }
        if (this.loadingLayout.getVisibility() == 0) {
            this.loadingLayout.setVisibility(8);
        }
    }

    private void init(Context context) {
        this.mContext = context;
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.uvv_player_controller, this);
        viewInflate.setOnTouchListener(this.mTouchListener);
        initControllerView(viewInflate);
    }

    private void initControllerView(View v2) {
        this.tv_right_down_state = (TextView) v2.findViewById(R.id.tv_right_down_state);
        this.mTitleLayout = v2.findViewById(R.id.title_part);
        this.mControlLayout = v2.findViewById(R.id.control_layout);
        this.loadingLayout = (ViewGroup) v2.findViewById(R.id.loading_layout);
        this.llay_loading = (LinearLayout) v2.findViewById(R.id.llay_loading);
        this.errorLayout = (ViewGroup) v2.findViewById(R.id.error_layout);
        this.mTurnButton = (ImageButton) v2.findViewById(R.id.turn_button);
        this.mScaleButton = (ImageButton) v2.findViewById(R.id.scale_button);
        this.mCenterPlayButton = v2.findViewById(R.id.center_play_btn);
        this.mBackButton = v2.findViewById(R.id.back_btn);
        ImageButton imageButton = this.mTurnButton;
        if (imageButton != null) {
            imageButton.requestFocus();
            this.mTurnButton.setOnClickListener(this.mPauseListener);
        }
        if (this.mScalable) {
            ImageButton imageButton2 = this.mScaleButton;
            if (imageButton2 != null) {
                imageButton2.setVisibility(0);
                this.mScaleButton.setOnClickListener(this.mScaleListener);
            }
        } else {
            ImageButton imageButton3 = this.mScaleButton;
            if (imageButton3 != null) {
                imageButton3.setVisibility(8);
            }
        }
        View view = this.mCenterPlayButton;
        if (view != null) {
            view.setOnClickListener(this.mCenterPlayListener);
        }
        ProgressBar progressBar = (ProgressBar) v2.findViewById(R.id.seekbar);
        this.mProgress = progressBar;
        if (progressBar != null) {
            if (progressBar instanceof SeekBar) {
                ((SeekBar) progressBar).setOnSeekBarChangeListener(this.mSeekListener);
            }
            this.mProgress.setMax(1000);
        }
        this.mEndTime = (TextView) v2.findViewById(R.id.duration);
        this.mCurrentTime = (TextView) v2.findViewById(R.id.has_played);
        this.mTitle = (TextView) v2.findViewById(R.id.title);
        this.mFormatBuilder = new StringBuilder();
        this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || !this.mShowing) {
            return false;
        }
        hide();
        this.handled = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int setProgress() {
        MediaPlayerControl mediaPlayerControl = this.mPlayer;
        if (mediaPlayerControl == null || this.mDragging) {
            return 0;
        }
        int currentPosition = mediaPlayerControl.getCurrentPosition();
        int duration = this.mPlayer.getDuration();
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            if (duration > 0) {
                progressBar.setProgress((int) ((currentPosition * 1000) / duration));
            }
            this.mProgress.setSecondaryProgress(this.mPlayer.getBufferPercentage() * 10);
        }
        TextView textView = this.mEndTime;
        if (textView != null) {
            textView.setText(stringForTime(duration));
        }
        TextView textView2 = this.mCurrentTime;
        if (textView2 != null) {
            textView2.setText(stringForTime(currentPosition));
        }
        return currentPosition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCenterView(int resId) {
        if (resId == R.id.loading_layout) {
            if (this.loadingLayout.getVisibility() != 0) {
                this.loadingLayout.setVisibility(0);
            }
            if (this.mCenterPlayButton.getVisibility() == 0) {
                this.mCenterPlayButton.setVisibility(8);
            }
            if (this.errorLayout.getVisibility() == 0) {
                this.errorLayout.setVisibility(8);
                return;
            }
            return;
        }
        if (resId == R.id.center_play_btn) {
            if (this.mCenterPlayButton.getVisibility() != 0) {
                this.mCenterPlayButton.setVisibility(0);
            }
            if (this.loadingLayout.getVisibility() == 0) {
                this.loadingLayout.setVisibility(8);
            }
            if (this.errorLayout.getVisibility() == 0) {
                this.errorLayout.setVisibility(8);
                return;
            }
            return;
        }
        if (resId == R.id.error_layout) {
            if (this.errorLayout.getVisibility() != 0) {
                this.errorLayout.setVisibility(0);
            }
            if (this.mCenterPlayButton.getVisibility() == 0) {
                this.mCenterPlayButton.setVisibility(8);
            }
            if (this.loadingLayout.getVisibility() == 0) {
                this.loadingLayout.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String stringForTime(int timeMs) {
        int i2 = timeMs / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        this.mFormatBuilder.setLength(0);
        return i5 > 0 ? this.mFormatter.format("%d:%02d:%02d", Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)).toString() : this.mFormatter.format("%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3)).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePausePlay() {
        MediaPlayerControl mediaPlayerControl = this.mPlayer;
        if (mediaPlayerControl == null || !mediaPlayerControl.isPlaying()) {
            this.mTurnButton.setImageResource(R.drawable.uvv_player_player_btn);
        } else {
            this.mTurnButton.setImageResource(R.drawable.uvv_stop_btn);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        boolean z2 = event.getRepeatCount() == 0 && event.getAction() == 0;
        if (keyCode == 79 || keyCode == 85 || keyCode == 62) {
            if (z2) {
                doPauseResume();
                show(15000);
                ImageButton imageButton = this.mTurnButton;
                if (imageButton != null) {
                    imageButton.requestFocus();
                }
            }
            return true;
        }
        if (keyCode == 126) {
            if (z2 && !this.mPlayer.isPlaying()) {
                this.mPlayer.start();
                updatePausePlay();
                show(15000);
            }
            return true;
        }
        if (keyCode == 86 || keyCode == 127) {
            if (z2 && this.mPlayer.isPlaying()) {
                this.mPlayer.pause();
                updatePausePlay();
                show(15000);
            }
            return true;
        }
        if (keyCode == 25 || keyCode == 24 || keyCode == 164 || keyCode == 27) {
            return super.dispatchKeyEvent(event);
        }
        if (keyCode != 4 && keyCode != 82) {
            show(15000);
            return super.dispatchKeyEvent(event);
        }
        if (z2) {
            hide();
        }
        return true;
    }

    public void hide() {
        if (this.mShowing) {
            this.mHandler.removeMessages(2);
            this.mTitleLayout.setVisibility(8);
            this.mControlLayout.setVisibility(8);
            this.mShowing = false;
        }
    }

    public void hideComplete() {
        this.mHandler.sendEmptyMessage(8);
    }

    public void hideError() {
        this.mHandler.sendEmptyMessage(6);
    }

    public void hideLoading() {
        this.mHandler.sendEmptyMessage(4);
    }

    public boolean isFullScreen() {
        return this.mIsFullScreen;
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != 1) {
            if (action == 3) {
                hide();
            }
        } else if (isShowing()) {
            hide();
        } else {
            this.handled = false;
            show(15000);
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent ev) {
        show(15000);
        return false;
    }

    public void reset() {
        this.mCurrentTime.setText("00:00");
        this.mEndTime.setText("00:00");
        this.mProgress.setProgress(0);
        this.mTurnButton.setImageResource(R.drawable.uvv_player_player_btn);
        setVisibility(0);
        hideLoading();
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        ImageButton imageButton = this.mTurnButton;
        if (imageButton != null) {
            imageButton.setEnabled(enabled);
        }
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setEnabled(enabled);
        }
        if (this.mScalable) {
            this.mScaleButton.setEnabled(enabled);
        }
        this.mBackButton.setEnabled(true);
    }

    public void setMediaPlayer(MediaPlayerControl player) {
        this.mPlayer = player;
        updatePausePlay();
    }

    public void setOnErrorView(int resId) {
        this.errorLayout.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(resId, this.errorLayout, true);
    }

    public void setOnErrorViewClick(View.OnClickListener onClickListener) {
        this.errorLayout.setOnClickListener(onClickListener);
    }

    public void setOnLoadingView(int resId) {
        this.loadingLayout.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(resId, this.loadingLayout, true);
    }

    public void setTitle(String titile) {
        this.mTitle.setText(titile);
    }

    public void show() {
        show(15000);
    }

    public void showComplete() {
        this.mHandler.sendEmptyMessage(7);
    }

    public void showError() {
        this.mHandler.sendEmptyMessage(5);
    }

    public void showLoading() {
        this.mHandler.sendEmptyMessage(3);
    }

    public void toggleButtons(boolean isFullScreen) {
        this.mIsFullScreen = isFullScreen;
        updateScaleButton();
        updateBackButton();
    }

    public void updateBackButton() {
        this.mBackButton.setVisibility(0);
    }

    public void updateScaleButton() {
        if (this.mIsFullScreen) {
            this.mScaleButton.setImageResource(R.drawable.uvv_star_zoom_in);
        } else {
            this.mScaleButton.setImageResource(R.drawable.uvv_player_scale_btn);
        }
    }

    public void show(int timeout) {
        if (!this.mShowing) {
            setProgress();
            ImageButton imageButton = this.mTurnButton;
            if (imageButton != null) {
                imageButton.requestFocus();
            }
            disableUnsupportedButtons();
            this.mShowing = true;
        }
        updatePausePlay();
        updateBackButton();
        if (getVisibility() != 0) {
            setVisibility(0);
        }
        if (this.mTitleLayout.getVisibility() != 0) {
            this.mTitleLayout.setVisibility(0);
        }
        if (this.mControlLayout.getVisibility() != 0) {
            this.mControlLayout.setVisibility(0);
        }
        this.mHandler.sendEmptyMessage(2);
        Message messageObtainMessage = this.mHandler.obtainMessage(1);
        if (timeout != 0) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessageDelayed(messageObtainMessage, timeout);
        }
    }

    public void setOnErrorView(View onErrorView) {
        this.errorLayout.removeAllViews();
        this.errorLayout.addView(onErrorView);
    }

    public void setOnLoadingView(View onLoadingView) {
        this.loadingLayout.removeAllViews();
        this.loadingLayout.addView(onLoadingView);
    }

    public UniversalMediaController(Context context) {
        super(context);
        this.mShowing = true;
        this.mScalable = false;
        this.mIsFullScreen = false;
        this.mState = 3;
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        UniversalMediaController.this.hide();
                        break;
                    case 2:
                        int progress = UniversalMediaController.this.setProgress();
                        if (!UniversalMediaController.this.mDragging && UniversalMediaController.this.mShowing && UniversalMediaController.this.mPlayer != null && UniversalMediaController.this.mPlayer.isPlaying()) {
                            sendMessageDelayed(obtainMessage(2), 1000 - (progress % 1000));
                            break;
                        }
                        break;
                    case 3:
                        UniversalMediaController.this.show();
                        UniversalMediaController.this.showCenterView(R.id.loading_layout);
                        break;
                    case 4:
                    case 6:
                    case 8:
                        UniversalMediaController.this.hide();
                        UniversalMediaController.this.hideCenterView();
                        break;
                    case 5:
                        UniversalMediaController.this.show();
                        UniversalMediaController.this.showCenterView(R.id.error_layout);
                        break;
                }
            }
        };
        this.handled = false;
        this.mTouchListener = new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.play.a
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f16804c.lambda$new$0(view, motionEvent);
            }
        };
        this.mPauseListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (UniversalMediaController.this.mPlayer != null) {
                    UniversalMediaController.this.doPauseResume();
                    UniversalMediaController.this.show(15000);
                }
            }
        };
        this.mScaleListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                UniversalMediaController.this.mIsFullScreen = !r2.mIsFullScreen;
                UniversalMediaController.this.updateScaleButton();
                UniversalMediaController.this.updateBackButton();
                if (UniversalMediaController.this.mPlayer != null) {
                    UniversalMediaController.this.mPlayer.setFullscreen(UniversalMediaController.this.mIsFullScreen);
                }
            }
        };
        this.mCenterPlayListener = new View.OnClickListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                UniversalMediaController.this.hideCenterView();
                UniversalMediaController.this.mPlayer.start();
            }
        };
        this.mSeekListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.psychiatrygarden.widget.play.UniversalMediaController.5
            int newPosition = 0;
            boolean change = false;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
                if (UniversalMediaController.this.mPlayer == null || !fromuser) {
                    return;
                }
                this.newPosition = (int) ((UniversalMediaController.this.mPlayer.getDuration() * progress) / 1000);
                this.change = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar bar) {
                if (UniversalMediaController.this.mPlayer == null) {
                    return;
                }
                UniversalMediaController.this.show(3600000);
                UniversalMediaController.this.mDragging = true;
                UniversalMediaController.this.mHandler.removeMessages(2);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar bar) {
                if (UniversalMediaController.this.mPlayer == null) {
                    return;
                }
                if (this.change) {
                    UniversalMediaController.this.mPlayer.seekTo(this.newPosition);
                    if (UniversalMediaController.this.mCurrentTime != null) {
                        UniversalMediaController.this.mCurrentTime.setText(UniversalMediaController.this.stringForTime(this.newPosition));
                    }
                }
                UniversalMediaController.this.mDragging = false;
                UniversalMediaController.this.setProgress();
                UniversalMediaController.this.updatePausePlay();
                UniversalMediaController.this.show(15000);
                UniversalMediaController.this.mShowing = true;
                UniversalMediaController.this.mHandler.sendEmptyMessage(2);
            }
        };
        init(context);
    }
}
