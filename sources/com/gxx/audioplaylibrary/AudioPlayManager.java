package com.gxx.audioplaylibrary;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.util.MimeTypes;
import com.gxx.audioplaylibrary.AudioPlayManager;
import com.gxx.audioplaylibrary.broadcastreceiver.HeadsetChangeReceiver;
import com.gxx.audioplaylibrary.inter.OnAudioPlayListener;
import com.gxx.audioplaylibrary.model.AudioVoiceModel;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.am;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u0000 l2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u0006:\u0002klB\u000f\b\u0002\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u00105\u001a\u000206H\u0002J\b\u00107\u001a\u000206H\u0002J\b\u00108\u001a\u000206H\u0002J\u0010\u00109\u001a\u0002062\u0006\u0010:\u001a\u00020\u0017H\u0002J\b\u0010;\u001a\u000206H\u0002J\u0010\u0010<\u001a\u00020\u00172\u0006\u0010=\u001a\u00020>H\u0002J\u0018\u0010?\u001a\u0002062\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0016J\u0010\u0010D\u001a\u0002062\u0006\u0010E\u001a\u00020\"H\u0016J \u0010F\u001a\u00020\u00172\u0006\u0010E\u001a\u00020\"2\u0006\u0010G\u001a\u00020C2\u0006\u0010H\u001a\u00020CH\u0016J\u0010\u0010I\u001a\u0002062\u0006\u0010:\u001a\u00020\u0017H\u0016J\u0010\u0010J\u001a\u0002062\u0006\u0010E\u001a\u00020\"H\u0016J\u0010\u0010K\u001a\u0002062\u0006\u0010E\u001a\u00020\"H\u0016J\u0010\u0010L\u001a\u0002062\u0006\u0010=\u001a\u00020>H\u0017J\u0006\u0010M\u001a\u000206J;\u0010N\u001a\u0002062\u0006\u0010O\u001a\u00020\u000b2\b\u0010P\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010.\u001a\u00020/2\b\u0010Q\u001a\u0004\u0018\u00010\u00172\b\u0010R\u001a\u0004\u0018\u00010*¢\u0006\u0002\u0010SJ\u0018\u0010T\u001a\u0002062\u0006\u0010\u0013\u001a\u00020\u00122\b\u0010R\u001a\u0004\u0018\u00010*J;\u0010T\u001a\u0002062\u0006\u0010U\u001a\u00020V2\b\u0010P\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010.\u001a\u00020/2\b\u0010Q\u001a\u0004\u0018\u00010\u00172\b\u0010R\u001a\u0004\u0018\u00010*¢\u0006\u0002\u0010WJ;\u0010T\u001a\u0002062\u0006\u0010X\u001a\u00020\u000b2\b\u0010P\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010.\u001a\u00020/2\b\u0010Q\u001a\u0004\u0018\u00010\u00172\b\u0010R\u001a\u0004\u0018\u00010*¢\u0006\u0002\u0010SJ\b\u0010Y\u001a\u000206H\u0002J\u0006\u0010Z\u001a\u000206J\u0006\u0010[\u001a\u000206J\b\u0010\\\u001a\u000206H\u0002J\b\u0010]\u001a\u000206H\u0002J\u000e\u0010^\u001a\u0002062\u0006\u0010_\u001a\u00020CJ\u000e\u0010`\u001a\u0002062\u0006\u0010_\u001a\u00020CJ\b\u0010a\u001a\u000206H\u0003J\b\u0010b\u001a\u000206H\u0002J\u000e\u0010c\u001a\u0002062\u0006\u0010d\u001a\u00020CJ\u0015\u0010e\u001a\u0002062\b\u0010Q\u001a\u0004\u0018\u00010\u0017¢\u0006\u0002\u0010fJ\u0006\u0010g\u001a\u000206J\b\u0010h\u001a\u000206H\u0002J\u0012\u0010i\u001a\u0002062\b\u0010E\u001a\u0004\u0018\u00010\"H\u0002J\u0006\u0010j\u001a\u000206R\u000e\u0010\n\u001a\u00020\u000bX\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0018\u00010\rR\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0013\u001a\u0004\u0018\u00010\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178CX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0018R\u000e\u0010\u001a\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010+\u001a\u0004\u0018\u00010\u000b8F¢\u0006\u0006\u001a\u0004\b,\u0010-R$\u00100\u001a\u00020/2\u0006\u0010.\u001a\u00020/8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b1\u00102\"\u0004\b3\u00104¨\u0006m"}, d2 = {"Lcom/gxx/audioplaylibrary/AudioPlayManager;", "Landroid/hardware/SensorEventListener;", "Landroid/media/MediaPlayer$OnCompletionListener;", "Landroid/media/MediaPlayer$OnSeekCompleteListener;", "Landroid/media/MediaPlayer$OnPreparedListener;", "Landroid/media/MediaPlayer$OnErrorListener;", "Lcom/gxx/audioplaylibrary/broadcastreceiver/HeadsetChangeReceiver$OnHeadsetChangeReceiverListener;", MimeTypes.BASE_TYPE_APPLICATION, "Landroid/app/Application;", "(Landroid/app/Application;)V", "TAG", "", "_wakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "afChangeListener", "Landroid/media/AudioManager$OnAudioFocusChangeListener;", "<set-?>", "Lcom/gxx/audioplaylibrary/model/AudioVoiceModel;", "audioVoiceModel", "getAudioVoiceModel", "()Lcom/gxx/audioplaylibrary/model/AudioVoiceModel;", "isHeadphonesPlugged", "", "()Z", "isPlayIng", "mApplication", "mAudioManager", "Landroid/media/AudioManager;", "mHandler", "Landroid/os/Handler;", "mHeadsetChangeReceiver", "Lcom/gxx/audioplaylibrary/broadcastreceiver/HeadsetChangeReceiver;", "mMediaPlayer", "Landroid/media/MediaPlayer;", "mPowerManager", "mSensorManager", "Landroid/hardware/SensorManager;", "mTimer", "Ljava/util/Timer;", "mWeakOnAudioPlayListener", "Ljava/lang/ref/WeakReference;", "Lcom/gxx/audioplaylibrary/inter/OnAudioPlayListener;", "playIngVoiceId", "getPlayIngVoiceId", "()Ljava/lang/String;", "speed", "", "playSpeed", "getPlaySpeed", "()F", "setPlaySpeed", "(F)V", "abandonAudioFocus", "", "changeToEarpiece", "changeToSpeaker", "createEarpieceOrSpeakerMediaPlayer", "isEarpiece", "initAudioPowerSensor", "isProximitySensor", NotificationCompat.CATEGORY_EVENT, "Landroid/hardware/SensorEvent;", "onAccuracyChanged", am.ac, "Landroid/hardware/Sensor;", "accuracy", "", "onCompletion", "mp", "onError", "what", PushConstants.EXTRA, "onHeadsetChangeReceiver", "onPrepared", "onSeekComplete", "onSensorChanged", "pause", "prepareAssetsAsync", "assetsName", "voiceId", "isTelephoneReceiverPlay", "playListener", "(Ljava/lang/String;Ljava/lang/String;FLjava/lang/Boolean;Lcom/gxx/audioplaylibrary/inter/OnAudioPlayListener;)V", "prepareAsync", "file", "Ljava/io/File;", "(Ljava/io/File;Ljava/lang/String;FLjava/lang/Boolean;Lcom/gxx/audioplaylibrary/inter/OnAudioPlayListener;)V", "remoteUrl", "registerHeadsetPlugReceiver", "registerListenerProximity", "releaseAll", "releaseTimer", "resetMediaPlayer", "resume", "position", "seekTo", "setScreenOff", "setScreenOn", "setStreamVolume", "volume", "setTelephoneReceiverPlay", "(Ljava/lang/Boolean;)V", "start", "startTime", "stop", "unregisterListenerProximity", "Builder", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class AudioPlayManager implements SensorEventListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, HeadsetChangeReceiver.OnHeadsetChangeReceiverListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Nullable
    private static AudioPlayManager mAudioPlayManager;

    @NotNull
    private final String TAG;

    @Nullable
    private PowerManager.WakeLock _wakeLock;

    @NotNull
    private final AudioManager.OnAudioFocusChangeListener afChangeListener;

    @Nullable
    private AudioVoiceModel audioVoiceModel;

    @NotNull
    private Application mApplication;

    @Nullable
    private AudioManager mAudioManager;

    @Nullable
    private Handler mHandler;

    @NotNull
    private final HeadsetChangeReceiver mHeadsetChangeReceiver;

    @Nullable
    private MediaPlayer mMediaPlayer;

    @Nullable
    private PowerManager mPowerManager;

    @Nullable
    private SensorManager mSensorManager;

    @Nullable
    private Timer mTimer;

    @Nullable
    private WeakReference<OnAudioPlayListener> mWeakOnAudioPlayListener;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/gxx/audioplaylibrary/AudioPlayManager$Builder;", "", "()V", "mApplication", "Landroid/app/Application;", "build", "Lcom/gxx/audioplaylibrary/AudioPlayManager;", "setApplication", MimeTypes.BASE_TYPE_APPLICATION, "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {

        @Nullable
        private Application mApplication;

        @NotNull
        public final AudioPlayManager build() {
            Companion companion = AudioPlayManager.INSTANCE;
            Application application = this.mApplication;
            Intrinsics.checkNotNull(application);
            AudioPlayManager.mAudioPlayManager = new AudioPlayManager(application, null);
            AudioPlayManager audioPlayManager = AudioPlayManager.mAudioPlayManager;
            Intrinsics.checkNotNull(audioPlayManager);
            return audioPlayManager;
        }

        @NotNull
        public final Builder setApplication(@NotNull Application application) {
            Intrinsics.checkNotNullParameter(application, "application");
            this.mApplication = application;
            return this;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/gxx/audioplaylibrary/AudioPlayManager$Companion;", "", "()V", "mAudioPlayManager", "Lcom/gxx/audioplaylibrary/AudioPlayManager;", "getInstance", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final AudioPlayManager getInstance() {
            AudioPlayManager audioPlayManager = AudioPlayManager.mAudioPlayManager;
            Intrinsics.checkNotNull(audioPlayManager);
            return audioPlayManager;
        }
    }

    @Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"com/gxx/audioplaylibrary/AudioPlayManager$startTime$1", "Ljava/util/TimerTask;", "run", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.gxx.audioplaylibrary.AudioPlayManager$startTime$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05481 extends TimerTask {
        public C05481() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void run$lambda$0(AudioPlayManager this$0, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.mWeakOnAudioPlayListener != null) {
                WeakReference weakReference = this$0.mWeakOnAudioPlayListener;
                Intrinsics.checkNotNull(weakReference);
                if (weakReference.get() == null || this$0.mMediaPlayer == null) {
                    return;
                }
                MediaPlayer mediaPlayer = this$0.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer);
                if (mediaPlayer.isPlaying()) {
                    WeakReference weakReference2 = this$0.mWeakOnAudioPlayListener;
                    Intrinsics.checkNotNull(weakReference2);
                    Object obj = weakReference2.get();
                    Intrinsics.checkNotNull(obj);
                    MediaPlayer mediaPlayer2 = this$0.mMediaPlayer;
                    Intrinsics.checkNotNull(mediaPlayer2);
                    int duration = mediaPlayer2.getDuration();
                    AudioVoiceModel audioVoiceModel = this$0.getAudioVoiceModel();
                    Intrinsics.checkNotNull(audioVoiceModel);
                    ((OnAudioPlayListener) obj).onVoicePlayPosition(i2, duration, audioVoiceModel.getPlayIngVoiceId());
                }
            }
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            final int currentPosition;
            if (AudioPlayManager.this.mMediaPlayer != null) {
                MediaPlayer mediaPlayer = AudioPlayManager.this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer);
                if (!mediaPlayer.isPlaying() || AudioPlayManager.this.getAudioVoiceModel() == null) {
                    return;
                }
                if (AudioPlayManager.this.mMediaPlayer == null) {
                    currentPosition = 0;
                } else {
                    MediaPlayer mediaPlayer2 = AudioPlayManager.this.mMediaPlayer;
                    Intrinsics.checkNotNull(mediaPlayer2);
                    currentPosition = mediaPlayer2.getCurrentPosition();
                }
                Handler handler = AudioPlayManager.this.mHandler;
                if (handler != null) {
                    final AudioPlayManager audioPlayManager = AudioPlayManager.this;
                    handler.post(new Runnable() { // from class: com.gxx.audioplaylibrary.c
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioPlayManager.C05481.run$lambda$0(audioPlayManager, currentPosition);
                        }
                    });
                }
            }
        }
    }

    private AudioPlayManager(Application application) {
        this.TAG = "AudioPlayManager";
        this.mHeadsetChangeReceiver = new HeadsetChangeReceiver();
        this.mApplication = application;
        initAudioPowerSensor();
        registerHeadsetPlugReceiver();
        if (this.mHandler == null) {
            this.mHandler = new Handler(application.getMainLooper());
        }
        this.afChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.gxx.audioplaylibrary.b
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i2) throws IllegalStateException {
                AudioPlayManager.afChangeListener$lambda$0(this.f7081c, i2);
            }
        };
    }

    public /* synthetic */ AudioPlayManager(Application application, DefaultConstructorMarker defaultConstructorMarker) {
        this(application);
    }

    private final void abandonAudioFocus() {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager == null) {
            return;
        }
        Intrinsics.checkNotNull(audioManager);
        audioManager.abandonAudioFocus(this.afChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void afChangeListener$lambda$0(AudioPlayManager this$0, int i2) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i2 == -2) {
            WeakReference<OnAudioPlayListener> weakReference = this$0.mWeakOnAudioPlayListener;
            if (weakReference != null) {
                Intrinsics.checkNotNull(weakReference);
                if (weakReference.get() != null && this$0.audioVoiceModel != null) {
                    WeakReference<OnAudioPlayListener> weakReference2 = this$0.mWeakOnAudioPlayListener;
                    Intrinsics.checkNotNull(weakReference2);
                    OnAudioPlayListener onAudioPlayListener = weakReference2.get();
                    Intrinsics.checkNotNull(onAudioPlayListener);
                    AudioVoiceModel audioVoiceModel = this$0.audioVoiceModel;
                    Intrinsics.checkNotNull(audioVoiceModel);
                    onAudioPlayListener.onVoiceFocusLoss(audioVoiceModel.getPlayIngVoiceId());
                }
            }
            this$0.stop(null);
            return;
        }
        if (i2 != -1) {
            return;
        }
        WeakReference<OnAudioPlayListener> weakReference3 = this$0.mWeakOnAudioPlayListener;
        if (weakReference3 != null) {
            Intrinsics.checkNotNull(weakReference3);
            if (weakReference3.get() != null && this$0.audioVoiceModel != null) {
                WeakReference<OnAudioPlayListener> weakReference4 = this$0.mWeakOnAudioPlayListener;
                Intrinsics.checkNotNull(weakReference4);
                OnAudioPlayListener onAudioPlayListener2 = weakReference4.get();
                Intrinsics.checkNotNull(onAudioPlayListener2);
                AudioVoiceModel audioVoiceModel2 = this$0.audioVoiceModel;
                Intrinsics.checkNotNull(audioVoiceModel2);
                onAudioPlayListener2.onVoiceFocusLoss(audioVoiceModel2.getPlayIngVoiceId());
            }
        }
        this$0.stop(null);
    }

    private final void changeToEarpiece() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        AudioManager audioManager;
        if (this.mMediaPlayer == null || this.audioVoiceModel == null || (audioManager = this.mAudioManager) == null) {
            return;
        }
        Intrinsics.checkNotNull(audioManager);
        if (audioManager.getMode() == 3) {
            return;
        }
        setScreenOff();
        AudioManager audioManager2 = this.mAudioManager;
        Intrinsics.checkNotNull(audioManager2);
        audioManager2.setSpeakerphoneOn(false);
        AudioManager audioManager3 = this.mAudioManager;
        Intrinsics.checkNotNull(audioManager3);
        audioManager3.setMode(3);
        createEarpieceOrSpeakerMediaPlayer(true);
    }

    private final void changeToSpeaker() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        AudioManager audioManager;
        if (this.mMediaPlayer == null || this.audioVoiceModel == null || (audioManager = this.mAudioManager) == null) {
            return;
        }
        Intrinsics.checkNotNull(audioManager);
        if (audioManager.getMode() == 0) {
            return;
        }
        setScreenOn();
        AudioManager audioManager2 = this.mAudioManager;
        Intrinsics.checkNotNull(audioManager2);
        audioManager2.setSpeakerphoneOn(true);
        AudioManager audioManager3 = this.mAudioManager;
        Intrinsics.checkNotNull(audioManager3);
        audioManager3.setMode(0);
        createEarpieceOrSpeakerMediaPlayer(false);
    }

    private final void createEarpieceOrSpeakerMediaPlayer(final boolean isEarpiece) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        MediaPlayer mediaPlayer;
        final int currentPosition;
        int iRequestAudioFocus;
        if (this.mAudioManager == null || (mediaPlayer = this.mMediaPlayer) == null) {
            return;
        }
        try {
            Intrinsics.checkNotNull(mediaPlayer);
            if (mediaPlayer.isPlaying()) {
                MediaPlayer mediaPlayer2 = this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer2);
                currentPosition = mediaPlayer2.getCurrentPosition();
            } else {
                currentPosition = -1;
            }
            releaseTimer();
            resetMediaPlayer();
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 26) {
                AudioManager audioManager = this.mAudioManager;
                Intrinsics.checkNotNull(audioManager);
                iRequestAudioFocus = audioManager.requestAudioFocus(new AudioFocusRequest.Builder(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(14).setContentType(2).build()).setAcceptsDelayedFocusGain(true).setOnAudioFocusChangeListener(this.afChangeListener).build());
            } else {
                AudioManager audioManager2 = this.mAudioManager;
                Intrinsics.checkNotNull(audioManager2);
                iRequestAudioFocus = audioManager2.requestAudioFocus(this.afChangeListener, 3, 2);
            }
            if (iRequestAudioFocus != 1) {
                WeakReference<OnAudioPlayListener> weakReference = this.mWeakOnAudioPlayListener;
                if (weakReference != null) {
                    Intrinsics.checkNotNull(weakReference);
                    if (weakReference.get() != null) {
                        WeakReference<OnAudioPlayListener> weakReference2 = this.mWeakOnAudioPlayListener;
                        Intrinsics.checkNotNull(weakReference2);
                        OnAudioPlayListener onAudioPlayListener = weakReference2.get();
                        Intrinsics.checkNotNull(onAudioPlayListener);
                        AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
                        Intrinsics.checkNotNull(audioVoiceModel);
                        onAudioPlayListener.onVoiceFocusLoss(audioVoiceModel.getPlayIngVoiceId());
                    }
                }
                stop(null);
                resetMediaPlayer();
                return;
            }
            MediaPlayer mediaPlayer3 = new MediaPlayer();
            this.mMediaPlayer = mediaPlayer3;
            if (isEarpiece) {
                if (i2 >= 27) {
                    Intrinsics.checkNotNull(mediaPlayer3);
                    mediaPlayer3.setAudioAttributes(new AudioAttributes.Builder().setContentType(1).setUsage(1).build());
                } else {
                    Intrinsics.checkNotNull(mediaPlayer3);
                    mediaPlayer3.setAudioStreamType(0);
                }
            } else if (i2 >= 27) {
                Intrinsics.checkNotNull(mediaPlayer3);
                mediaPlayer3.setAudioAttributes(new AudioAttributes.Builder().setContentType(2).setUsage(1).build());
            } else {
                Intrinsics.checkNotNull(mediaPlayer3);
                mediaPlayer3.setAudioStreamType(3);
            }
            MediaPlayer mediaPlayer4 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer4);
            mediaPlayer4.setOnCompletionListener(this);
            MediaPlayer mediaPlayer5 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer5);
            mediaPlayer5.setOnErrorListener(this);
            MediaPlayer mediaPlayer6 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer6);
            mediaPlayer6.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() { // from class: com.gxx.audioplaylibrary.AudioPlayManager.createEarpieceOrSpeakerMediaPlayer.1
                @Override // android.media.MediaPlayer.OnSeekCompleteListener
                public void onSeekComplete(@NotNull MediaPlayer mp) throws IllegalStateException {
                    Intrinsics.checkNotNullParameter(mp, "mp");
                    mp.start();
                    AudioVoiceModel audioVoiceModel2 = AudioPlayManager.this.getAudioVoiceModel();
                    Intrinsics.checkNotNull(audioVoiceModel2);
                    if (audioVoiceModel2.getSpeed() == AudioPlayManager.this.getPlaySpeed()) {
                        return;
                    }
                    AudioPlayManager audioPlayManager = AudioPlayManager.this;
                    AudioVoiceModel audioVoiceModel3 = audioPlayManager.getAudioVoiceModel();
                    Intrinsics.checkNotNull(audioVoiceModel3);
                    audioPlayManager.setPlaySpeed(audioVoiceModel3.getSpeed());
                }
            });
            MediaPlayer mediaPlayer7 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer7);
            mediaPlayer7.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.gxx.audioplaylibrary.AudioPlayManager.createEarpieceOrSpeakerMediaPlayer.2
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(@NotNull MediaPlayer mp) throws IllegalStateException {
                    Intrinsics.checkNotNullParameter(mp, "mp");
                    if (!isEarpiece) {
                        mp.seekTo(currentPosition);
                        return;
                    }
                    mp.start();
                    AudioVoiceModel audioVoiceModel2 = AudioPlayManager.this.getAudioVoiceModel();
                    Intrinsics.checkNotNull(audioVoiceModel2);
                    if (audioVoiceModel2.getSpeed() == AudioPlayManager.this.getPlaySpeed()) {
                        return;
                    }
                    AudioPlayManager audioPlayManager = AudioPlayManager.this;
                    AudioVoiceModel audioVoiceModel3 = audioPlayManager.getAudioVoiceModel();
                    Intrinsics.checkNotNull(audioVoiceModel3);
                    audioPlayManager.setPlaySpeed(audioVoiceModel3.getSpeed());
                }
            });
            AudioVoiceModel audioVoiceModel2 = this.audioVoiceModel;
            Intrinsics.checkNotNull(audioVoiceModel2);
            if (audioVoiceModel2.getPlayIngFileUri() != null) {
                MediaPlayer mediaPlayer8 = this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer8);
                Application application = this.mApplication;
                AudioVoiceModel audioVoiceModel3 = this.audioVoiceModel;
                Intrinsics.checkNotNull(audioVoiceModel3);
                Uri playIngFileUri = audioVoiceModel3.getPlayIngFileUri();
                Intrinsics.checkNotNull(playIngFileUri);
                mediaPlayer8.setDataSource(application, playIngFileUri);
            } else {
                AudioVoiceModel audioVoiceModel4 = this.audioVoiceModel;
                Intrinsics.checkNotNull(audioVoiceModel4);
                if (audioVoiceModel4.getPlayIngRemoteUrl() != null) {
                    MediaPlayer mediaPlayer9 = this.mMediaPlayer;
                    Intrinsics.checkNotNull(mediaPlayer9);
                    AudioVoiceModel audioVoiceModel5 = this.audioVoiceModel;
                    Intrinsics.checkNotNull(audioVoiceModel5);
                    mediaPlayer9.setDataSource(audioVoiceModel5.getPlayIngRemoteUrl());
                } else {
                    AudioVoiceModel audioVoiceModel6 = this.audioVoiceModel;
                    Intrinsics.checkNotNull(audioVoiceModel6);
                    if (audioVoiceModel6.getPlayIngAssetsName() != null) {
                        AssetManager assets = this.mApplication.getAssets();
                        AudioVoiceModel audioVoiceModel7 = this.audioVoiceModel;
                        Intrinsics.checkNotNull(audioVoiceModel7);
                        String playIngAssetsName = audioVoiceModel7.getPlayIngAssetsName();
                        Intrinsics.checkNotNull(playIngAssetsName);
                        AssetFileDescriptor assetFileDescriptorOpenFd = assets.openFd(playIngAssetsName);
                        Intrinsics.checkNotNullExpressionValue(assetFileDescriptorOpenFd, "mApplication.assets.open…el!!.playIngAssetsName!!)");
                        MediaPlayer mediaPlayer10 = this.mMediaPlayer;
                        Intrinsics.checkNotNull(mediaPlayer10);
                        mediaPlayer10.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
                    }
                }
            }
            MediaPlayer mediaPlayer11 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer11);
            mediaPlayer11.prepareAsync();
            startTime();
        } catch (IOException e2) {
            e2.printStackTrace();
            unregisterListenerProximity();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            unregisterListenerProximity();
        } catch (IllegalStateException e4) {
            e4.printStackTrace();
            unregisterListenerProximity();
        } catch (SecurityException e5) {
            e5.printStackTrace();
            unregisterListenerProximity();
        }
    }

    private final void initAudioPowerSensor() {
        if (this.mPowerManager == null) {
            Object systemService = this.mApplication.getSystemService("power");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.PowerManager");
            this.mPowerManager = (PowerManager) systemService;
        }
        if (this.mAudioManager == null) {
            Object systemService2 = this.mApplication.getSystemService("audio");
            Intrinsics.checkNotNull(systemService2, "null cannot be cast to non-null type android.media.AudioManager");
            this.mAudioManager = (AudioManager) systemService2;
        }
        if (this.mSensorManager == null) {
            Object systemService3 = this.mApplication.getSystemService(am.ac);
            Intrinsics.checkNotNull(systemService3, "null cannot be cast to non-null type android.hardware.SensorManager");
            this.mSensorManager = (SensorManager) systemService3;
        }
    }

    @SuppressLint({"WrongConstant"})
    private final boolean isHeadphonesPlugged() {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager == null) {
            return false;
        }
        Intrinsics.checkNotNull(audioManager);
        AudioDeviceInfo[] devices = audioManager.getDevices(3);
        Intrinsics.checkNotNullExpressionValue(devices, "mAudioManager!!.getDevic…oManager.GET_DEVICES_ALL)");
        for (AudioDeviceInfo audioDeviceInfo : devices) {
            if (audioDeviceInfo.getType() == 4 || audioDeviceInfo.getType() == 3) {
                return true;
            }
        }
        return false;
    }

    private final boolean isProximitySensor(SensorEvent event) {
        MediaPlayer mediaPlayer;
        if (event.sensor.getType() != 8 || (mediaPlayer = this.mMediaPlayer) == null || this.audioVoiceModel == null) {
            return false;
        }
        Intrinsics.checkNotNull(mediaPlayer);
        if (!mediaPlayer.isPlaying()) {
            return false;
        }
        double d3 = event.values[0];
        SensorManager sensorManager = this.mSensorManager;
        Intrinsics.checkNotNull(sensorManager);
        return d3 < ((double) sensorManager.getDefaultSensor(8).getMaximumRange());
    }

    public static /* synthetic */ void prepareAssetsAsync$default(AudioPlayManager audioPlayManager, String str, String str2, float f2, Boolean bool, OnAudioPlayListener onAudioPlayListener, int i2, Object obj) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if ((i2 & 4) != 0) {
            f2 = 1.0f;
        }
        audioPlayManager.prepareAssetsAsync(str, str2, f2, bool, onAudioPlayListener);
    }

    public static /* synthetic */ void prepareAsync$default(AudioPlayManager audioPlayManager, File file, String str, float f2, Boolean bool, OnAudioPlayListener onAudioPlayListener, int i2, Object obj) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if ((i2 & 4) != 0) {
            f2 = 1.0f;
        }
        audioPlayManager.prepareAsync(file, str, f2, bool, onAudioPlayListener);
    }

    private final void registerHeadsetPlugReceiver() {
        this.mHeadsetChangeReceiver.setOnHeadsetChangeReceiverListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        this.mApplication.registerReceiver(this.mHeadsetChangeReceiver, intentFilter);
    }

    private final void releaseTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            Intrinsics.checkNotNull(timer);
            timer.cancel();
            this.mTimer = null;
        }
    }

    private final void resetMediaPlayer() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            try {
                Intrinsics.checkNotNull(mediaPlayer);
                mediaPlayer.reset();
                MediaPlayer mediaPlayer2 = this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer2);
                mediaPlayer2.release();
                this.mMediaPlayer = null;
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
        }
    }

    @TargetApi(21)
    private final void setScreenOff() {
        PowerManager powerManager = this.mPowerManager;
        if (powerManager == null) {
            return;
        }
        if (this._wakeLock == null) {
            Intrinsics.checkNotNull(powerManager);
            this._wakeLock = powerManager.newWakeLock(32, "gxx:AudioPlayManager");
        }
        PowerManager.WakeLock wakeLock = this._wakeLock;
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    private final void setScreenOn() {
        PowerManager.WakeLock wakeLock = this._wakeLock;
        if (wakeLock != null) {
            wakeLock.setReferenceCounted(false);
            wakeLock.release();
            this._wakeLock = null;
        }
    }

    private final void startTime() {
        releaseTimer();
        if (this.mTimer == null) {
            this.mTimer = new Timer();
        }
        Timer timer = this.mTimer;
        Intrinsics.checkNotNull(timer);
        timer.schedule(new C05481(), 0L, 1000L);
    }

    private final void stop(MediaPlayer mp) throws IllegalStateException {
        if (mp == null) {
            MediaPlayer mediaPlayer = this.mMediaPlayer;
            if (mediaPlayer != null) {
                Intrinsics.checkNotNull(mediaPlayer);
                if (mediaPlayer.isPlaying()) {
                    MediaPlayer mediaPlayer2 = this.mMediaPlayer;
                    Intrinsics.checkNotNull(mediaPlayer2);
                    mediaPlayer2.stop();
                }
            }
        } else if (mp.isPlaying()) {
            mp.stop();
        }
        setScreenOn();
        unregisterListenerProximity();
        abandonAudioFocus();
        releaseTimer();
    }

    @Nullable
    public final AudioVoiceModel getAudioVoiceModel() {
        return this.audioVoiceModel;
    }

    @Nullable
    public final String getPlayIngVoiceId() {
        AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
        if (audioVoiceModel == null) {
            return null;
        }
        Intrinsics.checkNotNull(audioVoiceModel);
        return audioVoiceModel.getPlayIngVoiceId();
    }

    public final float getPlaySpeed() {
        MediaPlayer mediaPlayer;
        if (this.audioVoiceModel == null || (mediaPlayer = this.mMediaPlayer) == null) {
            return 1.0f;
        }
        Intrinsics.checkNotNull(mediaPlayer);
        PlaybackParams playbackParams = mediaPlayer.getPlaybackParams();
        Intrinsics.checkNotNullExpressionValue(playbackParams, "mMediaPlayer!!.getPlaybackParams()");
        return playbackParams.getSpeed();
    }

    public final boolean isPlayIng() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null) {
            return false;
        }
        Intrinsics.checkNotNull(mediaPlayer);
        return mediaPlayer.isPlaying();
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(@NotNull Sensor sensor, int accuracy) {
        Intrinsics.checkNotNullParameter(sensor, "sensor");
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(@NotNull MediaPlayer mp) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(mp, "mp");
        stop(mp);
        WeakReference<OnAudioPlayListener> weakReference = this.mWeakOnAudioPlayListener;
        if (weakReference != null) {
            Intrinsics.checkNotNull(weakReference);
            if (weakReference.get() == null || this.audioVoiceModel == null) {
                return;
            }
            WeakReference<OnAudioPlayListener> weakReference2 = this.mWeakOnAudioPlayListener;
            Intrinsics.checkNotNull(weakReference2);
            OnAudioPlayListener onAudioPlayListener = weakReference2.get();
            Intrinsics.checkNotNull(onAudioPlayListener);
            AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
            Intrinsics.checkNotNull(audioVoiceModel);
            onAudioPlayListener.onVoiceComplete(audioVoiceModel.getPlayIngVoiceId());
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(@NotNull MediaPlayer mp, int what, int extra) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(mp, "mp");
        WeakReference<OnAudioPlayListener> weakReference = this.mWeakOnAudioPlayListener;
        if (weakReference != null) {
            Intrinsics.checkNotNull(weakReference);
            if (weakReference.get() != null && this.audioVoiceModel != null) {
                WeakReference<OnAudioPlayListener> weakReference2 = this.mWeakOnAudioPlayListener;
                Intrinsics.checkNotNull(weakReference2);
                OnAudioPlayListener onAudioPlayListener = weakReference2.get();
                Intrinsics.checkNotNull(onAudioPlayListener);
                AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
                Intrinsics.checkNotNull(audioVoiceModel);
                onAudioPlayListener.onVoiceError(audioVoiceModel.getPlayIngVoiceId(), what, extra);
            }
        }
        stop(mp);
        resetMediaPlayer();
        return true;
    }

    @Override // com.gxx.audioplaylibrary.broadcastreceiver.HeadsetChangeReceiver.OnHeadsetChangeReceiverListener
    public void onHeadsetChangeReceiver(boolean isEarpiece) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (isEarpiece) {
            changeToEarpiece();
        } else {
            changeToSpeaker();
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(@NotNull MediaPlayer mp) {
        Intrinsics.checkNotNullParameter(mp, "mp");
        WeakReference<OnAudioPlayListener> weakReference = this.mWeakOnAudioPlayListener;
        if (weakReference != null) {
            Intrinsics.checkNotNull(weakReference);
            if (weakReference.get() == null || this.audioVoiceModel == null) {
                return;
            }
            WeakReference<OnAudioPlayListener> weakReference2 = this.mWeakOnAudioPlayListener;
            Intrinsics.checkNotNull(weakReference2);
            OnAudioPlayListener onAudioPlayListener = weakReference2.get();
            Intrinsics.checkNotNull(onAudioPlayListener);
            AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
            Intrinsics.checkNotNull(audioVoiceModel);
            onAudioPlayListener.onVoicePrepared(audioVoiceModel.getPlayIngVoiceId());
        }
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(@NotNull MediaPlayer mp) throws IllegalStateException {
        Intrinsics.checkNotNullParameter(mp, "mp");
        mp.start();
        AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
        Intrinsics.checkNotNull(audioVoiceModel);
        if (audioVoiceModel.getSpeed() == getPlaySpeed()) {
            return;
        }
        AudioVoiceModel audioVoiceModel2 = this.audioVoiceModel;
        Intrinsics.checkNotNull(audioVoiceModel2);
        setPlaySpeed(audioVoiceModel2.getSpeed());
    }

    @Override // android.hardware.SensorEventListener
    @TargetApi(11)
    public void onSensorChanged(@NotNull SensorEvent event) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.mSensorManager == null) {
            return;
        }
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (((mediaPlayer == null || mediaPlayer.isPlaying()) ? false : true) || isHeadphonesPlugged()) {
            return;
        }
        AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
        if ((audioVoiceModel != null ? audioVoiceModel.getIsTelephoneReceiverPlay() : null) != null) {
            AudioVoiceModel audioVoiceModel2 = this.audioVoiceModel;
            if (audioVoiceModel2 != null ? Intrinsics.areEqual(audioVoiceModel2.getIsTelephoneReceiverPlay(), Boolean.TRUE) : false) {
                setScreenOff();
                return;
            }
            return;
        }
        if (isProximitySensor(event)) {
            changeToEarpiece();
        } else {
            changeToSpeaker();
        }
    }

    public final void pause() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            Intrinsics.checkNotNull(mediaPlayer);
            if (mediaPlayer.isPlaying()) {
                MediaPlayer mediaPlayer2 = this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer2);
                mediaPlayer2.pause();
            }
        }
    }

    public final void prepareAssetsAsync(@NotNull String assetsName, @Nullable String voiceId, float speed, @Nullable Boolean isTelephoneReceiverPlay, @Nullable OnAudioPlayListener playListener) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(assetsName, "assetsName");
        AudioVoiceModel audioVoiceModel = new AudioVoiceModel();
        audioVoiceModel.setPlayIngVoiceId(voiceId);
        audioVoiceModel.setPlayIngAssetsName(assetsName);
        audioVoiceModel.setSpeed(speed);
        audioVoiceModel.setTelephoneReceiverPlay(isTelephoneReceiverPlay);
        prepareAsync(audioVoiceModel, playListener);
    }

    public final void prepareAsync(@NotNull File file, @Nullable String voiceId, float speed, @Nullable Boolean isTelephoneReceiverPlay, @Nullable OnAudioPlayListener playListener) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(file, "file");
        AudioVoiceModel audioVoiceModel = new AudioVoiceModel();
        audioVoiceModel.setPlayIngFileUri(Uri.fromFile(file));
        audioVoiceModel.setPlayIngVoiceId(voiceId);
        audioVoiceModel.setSpeed(speed);
        audioVoiceModel.setTelephoneReceiverPlay(isTelephoneReceiverPlay);
        prepareAsync(audioVoiceModel, playListener);
    }

    public final void registerListenerProximity() {
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager == null) {
            return;
        }
        Intrinsics.checkNotNull(sensorManager);
        SensorManager sensorManager2 = this.mSensorManager;
        Intrinsics.checkNotNull(sensorManager2);
        sensorManager.registerListener(this, sensorManager2.getDefaultSensor(8), 3);
    }

    public final void releaseAll() throws IllegalStateException {
        WeakReference<OnAudioPlayListener> weakReference = this.mWeakOnAudioPlayListener;
        if (weakReference != null) {
            Intrinsics.checkNotNull(weakReference);
            weakReference.clear();
        }
        stop(null);
        resetMediaPlayer();
        this.audioVoiceModel = null;
    }

    public final void resume(int position) throws IllegalStateException {
        if (this.mMediaPlayer != null) {
            seekTo(position);
        }
    }

    public final void seekTo(int position) throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            Intrinsics.checkNotNull(mediaPlayer);
            mediaPlayer.seekTo(position);
        }
    }

    public final void setPlaySpeed(float f2) {
        if (this.mMediaPlayer == null || this.audioVoiceModel == null || !isPlayIng()) {
            return;
        }
        AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
        Intrinsics.checkNotNull(audioVoiceModel);
        audioVoiceModel.setSpeed(f2);
        try {
            MediaPlayer mediaPlayer = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer);
            PlaybackParams playbackParams = mediaPlayer.getPlaybackParams();
            Intrinsics.checkNotNullExpressionValue(playbackParams, "mMediaPlayer!!.getPlaybackParams()");
            if (f2 <= 0.0f) {
                playbackParams.setSpeed(1.0f);
            } else {
                playbackParams.setSpeed(f2);
            }
            MediaPlayer mediaPlayer2 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer2);
            mediaPlayer2.setPlaybackParams(playbackParams);
        } catch (Exception e2) {
            e2.printStackTrace();
            unregisterListenerProximity();
        }
    }

    public final void setStreamVolume(int volume) {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager == null) {
            return;
        }
        Intrinsics.checkNotNull(audioManager);
        if (audioManager.isSpeakerphoneOn()) {
            AudioManager audioManager2 = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager2);
            audioManager2.setStreamVolume(3, volume, 4);
        } else {
            AudioManager audioManager3 = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager3);
            audioManager3.setStreamVolume(3, volume, 4);
        }
    }

    public final void setTelephoneReceiverPlay(@Nullable Boolean isTelephoneReceiverPlay) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        AudioManager audioManager;
        AudioVoiceModel audioVoiceModel = this.audioVoiceModel;
        if (audioVoiceModel != null) {
            audioVoiceModel.setTelephoneReceiverPlay(isTelephoneReceiverPlay);
        }
        if (isHeadphonesPlugged() || (audioManager = this.mAudioManager) == null) {
            return;
        }
        Intrinsics.checkNotNull(audioManager);
        if (audioManager.getMode() == 0) {
            AudioVoiceModel audioVoiceModel2 = this.audioVoiceModel;
            if (audioVoiceModel2 != null ? Intrinsics.areEqual(audioVoiceModel2.getIsTelephoneReceiverPlay(), Boolean.FALSE) : false) {
                return;
            }
        }
        AudioManager audioManager2 = this.mAudioManager;
        Intrinsics.checkNotNull(audioManager2);
        if (audioManager2.getMode() == 3) {
            AudioVoiceModel audioVoiceModel3 = this.audioVoiceModel;
            if (audioVoiceModel3 != null ? Intrinsics.areEqual(audioVoiceModel3.getIsTelephoneReceiverPlay(), Boolean.TRUE) : false) {
                return;
            }
        }
        Boolean bool = Boolean.TRUE;
        if (Intrinsics.areEqual(isTelephoneReceiverPlay, bool)) {
            setScreenOff();
            AudioManager audioManager3 = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager3);
            audioManager3.setSpeakerphoneOn(false);
            AudioManager audioManager4 = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager4);
            audioManager4.setMode(3);
        } else {
            setScreenOn();
            AudioManager audioManager5 = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager5);
            audioManager5.setSpeakerphoneOn(true);
            AudioManager audioManager6 = this.mAudioManager;
            Intrinsics.checkNotNull(audioManager6);
            audioManager6.setMode(0);
        }
        createEarpieceOrSpeakerMediaPlayer(Intrinsics.areEqual(isTelephoneReceiverPlay, bool));
    }

    public final void start() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            Intrinsics.checkNotNull(mediaPlayer);
            mediaPlayer.start();
        }
    }

    public final void unregisterListenerProximity() {
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager == null) {
            return;
        }
        Intrinsics.checkNotNull(sensorManager);
        SensorManager sensorManager2 = this.mSensorManager;
        Intrinsics.checkNotNull(sensorManager2);
        sensorManager.unregisterListener(this, sensorManager2.getDefaultSensor(8));
    }

    public static /* synthetic */ void prepareAsync$default(AudioPlayManager audioPlayManager, String str, String str2, float f2, Boolean bool, OnAudioPlayListener onAudioPlayListener, int i2, Object obj) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if ((i2 & 4) != 0) {
            f2 = 1.0f;
        }
        audioPlayManager.prepareAsync(str, str2, f2, bool, onAudioPlayListener);
    }

    public final void prepareAsync(@NotNull String remoteUrl, @Nullable String voiceId, float speed, @Nullable Boolean isTelephoneReceiverPlay, @Nullable OnAudioPlayListener playListener) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(remoteUrl, "remoteUrl");
        AudioVoiceModel audioVoiceModel = new AudioVoiceModel();
        audioVoiceModel.setPlayIngVoiceId(voiceId);
        audioVoiceModel.setPlayIngRemoteUrl(remoteUrl);
        audioVoiceModel.setSpeed(speed);
        audioVoiceModel.setTelephoneReceiverPlay(isTelephoneReceiverPlay);
        prepareAsync(audioVoiceModel, playListener);
    }

    public final void prepareAsync(@NotNull AudioVoiceModel audioVoiceModel, @Nullable OnAudioPlayListener playListener) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        int iRequestAudioFocus;
        Intrinsics.checkNotNullParameter(audioVoiceModel, "audioVoiceModel");
        String playIngAssetsName = audioVoiceModel.getPlayIngAssetsName();
        if ((playIngAssetsName == null || playIngAssetsName.length() == 0) && audioVoiceModel.getPlayIngFileUri() == null) {
            String playIngRemoteUrl = audioVoiceModel.getPlayIngRemoteUrl();
            if (playIngRemoteUrl == null || playIngRemoteUrl.length() == 0) {
                return;
            }
        }
        initAudioPowerSensor();
        if (this.mAudioManager == null) {
            return;
        }
        setScreenOn();
        setTelephoneReceiverPlay(audioVoiceModel.getIsTelephoneReceiverPlay());
        resetMediaPlayer();
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 26) {
                AudioManager audioManager = this.mAudioManager;
                Intrinsics.checkNotNull(audioManager);
                iRequestAudioFocus = audioManager.requestAudioFocus(new AudioFocusRequest.Builder(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(14).setContentType(2).build()).setAcceptsDelayedFocusGain(true).setOnAudioFocusChangeListener(this.afChangeListener).build());
            } else {
                AudioManager audioManager2 = this.mAudioManager;
                Intrinsics.checkNotNull(audioManager2);
                iRequestAudioFocus = audioManager2.requestAudioFocus(this.afChangeListener, 3, 2);
            }
            if (iRequestAudioFocus != 1) {
                WeakReference<OnAudioPlayListener> weakReference = this.mWeakOnAudioPlayListener;
                if (weakReference != null) {
                    Intrinsics.checkNotNull(weakReference);
                    if (weakReference.get() != null) {
                        WeakReference<OnAudioPlayListener> weakReference2 = this.mWeakOnAudioPlayListener;
                        Intrinsics.checkNotNull(weakReference2);
                        OnAudioPlayListener onAudioPlayListener = weakReference2.get();
                        Intrinsics.checkNotNull(onAudioPlayListener);
                        onAudioPlayListener.onVoiceFocusLoss(audioVoiceModel.getPlayIngVoiceId());
                        return;
                    }
                    return;
                }
                return;
            }
            registerListenerProximity();
            this.audioVoiceModel = audioVoiceModel;
            this.mWeakOnAudioPlayListener = new WeakReference<>(playListener);
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mMediaPlayer = mediaPlayer;
            if (i2 >= 27) {
                Intrinsics.checkNotNull(mediaPlayer);
                mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(2).setUsage(1).build());
            } else {
                Intrinsics.checkNotNull(mediaPlayer);
                mediaPlayer.setAudioStreamType(3);
            }
            MediaPlayer mediaPlayer2 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer2);
            mediaPlayer2.setLooping(false);
            MediaPlayer mediaPlayer3 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer3);
            mediaPlayer3.setOnPreparedListener(this);
            MediaPlayer mediaPlayer4 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer4);
            mediaPlayer4.setOnSeekCompleteListener(this);
            MediaPlayer mediaPlayer5 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer5);
            mediaPlayer5.setOnCompletionListener(this);
            MediaPlayer mediaPlayer6 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer6);
            mediaPlayer6.setOnErrorListener(this);
            if (audioVoiceModel.getPlayIngFileUri() != null) {
                MediaPlayer mediaPlayer7 = this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer7);
                Application application = this.mApplication;
                Uri playIngFileUri = audioVoiceModel.getPlayIngFileUri();
                Intrinsics.checkNotNull(playIngFileUri);
                mediaPlayer7.setDataSource(application, playIngFileUri);
            } else if (audioVoiceModel.getPlayIngRemoteUrl() != null) {
                MediaPlayer mediaPlayer8 = this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer8);
                mediaPlayer8.setDataSource(audioVoiceModel.getPlayIngRemoteUrl());
            } else if (audioVoiceModel.getPlayIngAssetsName() != null) {
                AssetManager assets = this.mApplication.getAssets();
                String playIngAssetsName2 = audioVoiceModel.getPlayIngAssetsName();
                Intrinsics.checkNotNull(playIngAssetsName2);
                AssetFileDescriptor assetFileDescriptorOpenFd = assets.openFd(playIngAssetsName2);
                Intrinsics.checkNotNullExpressionValue(assetFileDescriptorOpenFd, "mApplication.assets.open…odel.playIngAssetsName!!)");
                MediaPlayer mediaPlayer9 = this.mMediaPlayer;
                Intrinsics.checkNotNull(mediaPlayer9);
                mediaPlayer9.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
            }
            MediaPlayer mediaPlayer10 = this.mMediaPlayer;
            Intrinsics.checkNotNull(mediaPlayer10);
            mediaPlayer10.prepareAsync();
            startTime();
        } catch (Exception e2) {
            e2.printStackTrace();
            releaseAll();
        }
    }
}
