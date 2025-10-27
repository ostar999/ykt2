package com.psychiatrygarden.activity.purchase.activity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.C;
import com.just.agentweb.DefaultWebClient;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DensityUtil;
import com.psychiatrygarden.videoChace.M3u8Server;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.play.UniversalMediaController;
import com.psychiatrygarden.widget.play.UniversalVideoView;
import com.yikaobang.yixue.R;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes5.dex */
public class VideoRePlayActivity extends BaseActivity implements UniversalVideoView.VideoViewCallback, GestureDetector.OnGestureListener, View.OnTouchListener {
    private static final int GESTURE_MODIFY_PROGRESS = 1;
    private static final int GESTURE_MODIFY_VOLUME = 2;
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final float STEP_LIGHT = 2.0f;
    private static final float STEP_PROGRESS = 2.0f;
    private static final float STEP_VOLUME = 2.0f;
    private static String VIDEO_URL = "";
    private static UniversalMediaController mMediaController;
    private AudioManager audiomanager;
    private int cachedHeight;
    private int currentVolume;
    private GestureDetector gestureDetector;
    private ImageView gesture_iv_player_volume;
    private ImageView gesture_iv_progress;
    private RelativeLayout gesture_light_layout;
    private ImageView gesture_light_progress;
    private RelativeLayout gesture_progress_layout;
    private RelativeLayout gesture_volume_layout;
    private TextView geture_tv_light_time;
    private TextView geture_tv_progress_time;
    private TextView geture_tv_volume_percentage;
    private LinearLayout llay_wifi;
    View mBottomLayout;
    private int mSeekPosition;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    private int maxVolume;
    private ImageView play_btn;
    private int maxLight = 255;
    private boolean firstScroll = false;
    private int GESTURE_FLAG = 0;
    private float mBrightness = 60.0f;
    private long palyerCurrentPosition = 0;
    View.OnClickListener mOnclick = new AnonymousClass1();

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.VideoRePlayActivity.2
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
        }
    };

    /* renamed from: com.psychiatrygarden.activity.purchase.activity.VideoRePlayActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(String str, String str2, String str3) {
            Message message = new Message();
            message.what = 2;
            message.obj = str;
            VideoRePlayActivity.this.mHandler.sendMessage(message);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
            }
            switch (v2.getId()) {
                case R.id.bt_comment_play /* 2131362289 */:
                case R.id.bt_list_comment_play /* 2131362297 */:
                    new DialogInput(VideoRePlayActivity.this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.k1
                        @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
                        public final void onclickStringBack(String str, String str2, String str3) {
                            this.f13617a.lambda$onClick$0(str, str2, str3);
                        }
                    }, false).show();
                    break;
                case R.id.play_btn /* 2131365781 */:
                    VideoRePlayActivity.this.setVideoAreaSize();
                    VideoRePlayActivity.mMediaController.llay_loading.setVisibility(0);
                    VideoRePlayActivity.this.play_btn.setVisibility(8);
                    break;
                case R.id.tv_go_play /* 2131368062 */:
                    VideoRePlayActivity.this.llay_wifi.setVisibility(8);
                    VideoRePlayActivity.this.setVideoAreaSize();
                    break;
            }
        }
    }

    private String converLongTimeToStr(long time) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        long j2 = 3600000;
        long j3 = time / j2;
        long j4 = time - (j2 * j3);
        long j5 = 60000;
        long j6 = j4 / j5;
        long j7 = (j4 - (j5 * j6)) / 1000;
        if (j3 < 10) {
            sb = new StringBuilder();
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(j3);
        String string = sb.toString();
        if (j6 < 10) {
            sb2 = new StringBuilder();
            sb2.append("0");
        } else {
            sb2 = new StringBuilder();
            sb2.append("");
        }
        sb2.append(j6);
        String string2 = sb2.toString();
        if (j7 < 10) {
            sb3 = new StringBuilder();
            sb3.append("0");
        } else {
            sb3 = new StringBuilder();
            sb3.append("");
        }
        sb3.append(j7);
        String string3 = sb3.toString();
        if (j3 <= 0) {
            return string2 + ":" + string3;
        }
        return string + ":" + string2 + ":" + string3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(MediaPlayer mediaPlayer) throws IllegalStateException {
        this.mVideoView.seekTo(0);
        mMediaController.llay_loading.setVisibility(4);
        this.play_btn.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVideoAreaSize$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVideoAreaSize$2() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.cachedHeight = (int) ((this.mVideoLayout.getWidth() * 405.0f) / 720.0f);
        ViewGroup.LayoutParams layoutParams = this.mVideoLayout.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = this.cachedHeight;
        this.mVideoLayout.setLayoutParams(layoutParams);
        this.mVideoView.setVideoPath(VIDEO_URL);
        this.mVideoView.requestFocus();
        this.mVideoView.start();
        mMediaController.setTitle("");
        mMediaController.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.i1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13607c.lambda$setVideoAreaSize$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoAreaSize() {
        this.mVideoView.setFullscreen(true);
        this.mVideoLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.purchase.activity.h1
            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
                this.f13602c.lambda$setVideoAreaSize$2();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.gesture_light_layout = (RelativeLayout) findViewById(R.id.gesture_light_layout);
        this.gesture_light_progress = (ImageView) findViewById(R.id.gesture_light_progress);
        this.geture_tv_light_time = (TextView) findViewById(R.id.geture_tv_light_time);
        this.llay_wifi = (LinearLayout) findViewById(R.id.llay_wifi);
        TextView textView = (TextView) findViewById(R.id.tv_go_play);
        this.mVideoLayout = findViewById(R.id.video_layout);
        this.gesture_volume_layout = (RelativeLayout) findViewById(R.id.gesture_volume_layout);
        this.gesture_progress_layout = (RelativeLayout) findViewById(R.id.gesture_progress_layout);
        this.geture_tv_progress_time = (TextView) findViewById(R.id.geture_tv_progress_time);
        this.geture_tv_volume_percentage = (TextView) findViewById(R.id.geture_tv_volume_percentage);
        this.gesture_iv_progress = (ImageView) findViewById(R.id.gesture_iv_progress);
        this.gesture_iv_player_volume = (ImageView) findViewById(R.id.gesture_iv_player_volume);
        GestureDetector gestureDetector = new GestureDetector(this, this);
        this.gestureDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(true);
        AudioManager audioManager = (AudioManager) getSystemService("audio");
        this.audiomanager = audioManager;
        this.maxVolume = audioManager.getStreamMaxVolume(3);
        this.currentVolume = this.audiomanager.getStreamVolume(3);
        this.mBottomLayout = findViewById(R.id.bottom_layout);
        this.mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        UniversalMediaController universalMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mMediaController = universalMediaController;
        this.mVideoView.setMediaController(universalMediaController);
        mMediaController.setVisibility(4);
        this.mVideoView.setVideoViewCallback(this);
        mMediaController.setLongClickable(true);
        mMediaController.setOnTouchListener(this);
        textView.setOnClickListener(this.mOnclick);
        this.play_btn = (ImageView) findViewById(R.id.play_btn);
        this.mVideoLayout = findViewById(R.id.video_layout);
        this.mBottomLayout = findViewById(R.id.bottom_layout);
        this.mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        UniversalMediaController universalMediaController2 = (UniversalMediaController) findViewById(R.id.media_controller);
        mMediaController = universalMediaController2;
        this.mVideoView.setMediaController(universalMediaController2);
        mMediaController.setVisibility(4);
        this.mVideoView.setVideoViewCallback(this);
        String stringExtra = getIntent().getStringExtra("video_url");
        VIDEO_URL = stringExtra;
        if (!stringExtra.contains(DefaultWebClient.HTTP_SCHEME)) {
            M3u8Server.execute();
            VIDEO_URL = String.format(Locale.CHINA, "http://127.0.0.1:%d/%s", 30001, VIDEO_URL);
            setVideoAreaSize();
        } else if (CommonUtil.isWifi(this.mContext)) {
            setVideoAreaSize();
        } else {
            this.llay_wifi.setVisibility(0);
        }
    }

    @Override // com.psychiatrygarden.widget.play.UniversalVideoView.VideoViewCallback
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
    }

    @Override // com.psychiatrygarden.widget.play.UniversalVideoView.VideoViewCallback
    public void onBufferingStart(MediaPlayer mediaPlayer) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setSwipeBackEnable(false);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() throws InterruptedException {
        super.onDestroy();
        this.mSeekPosition = 0;
        M3u8Server.finish();
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent e2) {
        this.firstScroll = true;
        return false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent e12, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent e2) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() throws IllegalStateException {
        super.onPause();
        this.mSeekPosition = this.mVideoView.getCurrentPosition();
        UniversalVideoView universalVideoView = this.mVideoView;
        if (universalVideoView == null || !universalVideoView.isPlaying()) {
            return;
        }
        this.mVideoView.pause();
    }

    @Override // android.app.Activity
    public void onRestoreInstanceState(@NonNull Bundle outState) {
        super.onRestoreInstanceState(outState);
        this.mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SEEK_POSITION_KEY, this.mSeekPosition);
    }

    @Override // com.psychiatrygarden.widget.play.UniversalVideoView.VideoViewCallback
    public void onScaleChange(boolean isFullscreen) {
        if (!isFullscreen) {
            this.mVideoView.setFullscreen(true);
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.mVideoLayout.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        this.mVideoLayout.setLayoutParams(layoutParams);
        this.mBottomLayout.setVisibility(8);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e12, MotionEvent e2, float distanceX, float distanceY) throws IllegalStateException {
        int i2;
        if (this.firstScroll) {
            if (Math.abs(distanceX) >= Math.abs(distanceY)) {
                this.gesture_volume_layout.setVisibility(4);
                this.gesture_light_layout.setVisibility(4);
                this.gesture_progress_layout.setVisibility(0);
                this.GESTURE_FLAG = 1;
            } else {
                float x2 = e12.getX();
                e12.getY();
                e2.getRawY();
                Display defaultDisplay = getWindowManager().getDefaultDisplay();
                int width = defaultDisplay.getWidth();
                defaultDisplay.getHeight();
                double d3 = x2;
                double d4 = width;
                if (d3 > (d4 * 4.0d) / 5.0d) {
                    this.gesture_volume_layout.setVisibility(0);
                    this.gesture_progress_layout.setVisibility(4);
                    this.gesture_light_layout.setVisibility(4);
                    this.GESTURE_FLAG = 2;
                } else if (d3 < d4 / 5.0d) {
                    this.gesture_light_layout.setVisibility(0);
                    this.gesture_progress_layout.setVisibility(4);
                    this.gesture_volume_layout.setVisibility(4);
                    this.GESTURE_FLAG = 2;
                }
            }
        }
        int i3 = this.GESTURE_FLAG;
        if (i3 == 1) {
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                this.palyerCurrentPosition = this.mVideoView.getCurrentPosition();
                if (distanceX >= DensityUtil.dip2px(this, 2.0f)) {
                    this.gesture_iv_progress.setImageResource(R.drawable.souhu_player_backward);
                    if (this.mVideoView.getCurrentPosition() > 3000) {
                        this.palyerCurrentPosition -= C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
                    } else {
                        this.palyerCurrentPosition = C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
                    }
                } else if (distanceX <= (-DensityUtil.dip2px(this, 2.0f))) {
                    this.gesture_iv_progress.setImageResource(R.drawable.souhu_player_forward);
                    if (this.mVideoView.getCurrentPosition() < this.mVideoView.getDuration()) {
                        this.palyerCurrentPosition += C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
                    } else {
                        this.palyerCurrentPosition = this.mVideoView.getDuration() - 10000;
                    }
                }
                this.mVideoView.seekTo(Integer.parseInt(this.palyerCurrentPosition + ""));
            }
            this.geture_tv_progress_time.setText(String.format("%s/%s", converLongTimeToStr(this.palyerCurrentPosition), converLongTimeToStr(this.mVideoView.getDuration())));
        } else if (i3 == 2) {
            float x3 = e12.getX();
            e12.getY();
            e2.getRawY();
            int width2 = getWindowManager().getDefaultDisplay().getWidth();
            boolean z2 = Math.abs(distanceY) > Math.abs(distanceX);
            double d5 = x3;
            double d6 = width2;
            if (d5 > (4.0d * d6) / 5.0d) {
                this.currentVolume = this.audiomanager.getStreamVolume(3);
                if (z2) {
                    if (distanceY >= DensityUtil.dip2px(this, 2.0f)) {
                        int i4 = this.currentVolume;
                        if (i4 < this.maxVolume) {
                            this.currentVolume = i4 + 1;
                        }
                        this.gesture_iv_player_volume.setImageResource(R.drawable.souhu_player_volume);
                    } else if (distanceY <= (-DensityUtil.dip2px(this, 2.0f)) && (i2 = this.currentVolume) > 0) {
                        int i5 = i2 - 1;
                        this.currentVolume = i5;
                        if (i5 == 0) {
                            this.gesture_iv_player_volume.setImageResource(R.drawable.souhu_player_silence);
                        }
                    }
                    this.geture_tv_volume_percentage.setText(String.format(Locale.CHINA, "%d%%", Integer.valueOf((this.currentVolume * 100) / this.maxVolume)));
                    this.audiomanager.setStreamVolume(3, this.currentVolume, 0);
                }
            } else if (d5 < d6 / 5.0d && z2) {
                if (distanceY >= DensityUtil.dip2px(this, 2.0f)) {
                    float f2 = this.mBrightness;
                    if (f2 < this.maxLight) {
                        this.mBrightness = f2 + 15.0f;
                    }
                    this.gesture_light_progress.setImageResource(R.drawable.gesture_light_progress_img);
                } else if (distanceY <= (-DensityUtil.dip2px(this, 2.0f))) {
                    float f3 = this.mBrightness;
                    if (f3 >= 15.0f) {
                        float f4 = f3 - 15.0f;
                        this.mBrightness = f4;
                        if (f4 == 0.0f) {
                            this.gesture_light_progress.setImageResource(R.drawable.gesture_light_progress_kongimg);
                        }
                    }
                }
                this.geture_tv_light_time.setText(String.format(Locale.CHINA, "%d%%", Integer.valueOf((int) ((this.mBrightness * 100.0f) / this.maxLight))));
                setScreenLight((int) this.mBrightness);
            }
        }
        this.firstScroll = false;
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent e2) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e2) {
        return false;
    }

    @Override // com.psychiatrygarden.widget.play.UniversalVideoView.VideoViewCallback
    public void onStart(MediaPlayer mediaPlayer) {
        UniversalVideoView universalVideoView;
        mMediaController.setVisibility(0);
        mMediaController.llay_loading.setVisibility(0);
        try {
            int i2 = this.mSeekPosition;
            if (i2 > 0 && (universalVideoView = this.mVideoView) != null) {
                universalVideoView.seekTo(i2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.play_btn.setVisibility(8);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v2, MotionEvent event) {
        if (event.getAction() == 1) {
            this.GESTURE_FLAG = 0;
            this.gesture_volume_layout.setVisibility(4);
            this.gesture_progress_layout.setVisibility(4);
            this.gesture_light_layout.setVisibility(4);
        }
        return this.gestureDetector.onTouchEvent(event);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_video_play);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.play_btn.setOnClickListener(this.mOnclick);
        this.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.psychiatrygarden.activity.purchase.activity.j1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
                this.f13612c.lambda$setListenerForWidget$0(mediaPlayer);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0003 A[PHI: r0
      0x0003: PHI (r0v4 int) = (r0v0 int), (r0v1 int) binds: [B:3:0x0001, B:6:0x0007] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setScreenLight(int r3) {
        /*
            r2 = this;
            r0 = 1
            if (r3 >= r0) goto L5
        L3:
            r3 = r0
            goto La
        L5:
            r0 = 255(0xff, float:3.57E-43)
            if (r3 <= r0) goto La
            goto L3
        La:
            android.view.Window r0 = r2.getWindow()
            android.view.WindowManager$LayoutParams r0 = r0.getAttributes()
            float r3 = (float) r3
            r1 = 1132396544(0x437f0000, float:255.0)
            float r3 = r3 / r1
            r0.screenBrightness = r3
            android.view.Window r3 = r2.getWindow()
            r3.setAttributes(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.purchase.activity.VideoRePlayActivity.setScreenLight(int):void");
    }

    @Override // com.psychiatrygarden.widget.play.UniversalVideoView.VideoViewCallback
    public void onPause(MediaPlayer mediaPlayer) throws IllegalStateException {
        UniversalVideoView universalVideoView = this.mVideoView;
        if (universalVideoView == null || !universalVideoView.isPlaying()) {
            return;
        }
        mediaPlayer.pause();
    }
}
