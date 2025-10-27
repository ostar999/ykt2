package com.google.android.exoplayer2;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
final class AudioFocusManager {
    private static final int AUDIO_FOCUS_STATE_HAVE_FOCUS = 1;
    private static final int AUDIO_FOCUS_STATE_LOSS_TRANSIENT = 2;
    private static final int AUDIO_FOCUS_STATE_LOSS_TRANSIENT_DUCK = 3;
    private static final int AUDIO_FOCUS_STATE_NO_FOCUS = 0;
    public static final int PLAYER_COMMAND_DO_NOT_PLAY = -1;
    public static final int PLAYER_COMMAND_PLAY_WHEN_READY = 1;
    public static final int PLAYER_COMMAND_WAIT_FOR_CALLBACK = 0;
    private static final String TAG = "AudioFocusManager";
    private static final float VOLUME_MULTIPLIER_DEFAULT = 1.0f;
    private static final float VOLUME_MULTIPLIER_DUCK = 0.2f;

    @Nullable
    private AudioAttributes audioAttributes;
    private AudioFocusRequest audioFocusRequest;
    private final AudioManager audioManager;
    private int focusGainToRequest;
    private final AudioFocusListener focusListener;

    @Nullable
    private PlayerControl playerControl;
    private boolean rebuildAudioFocusRequest;
    private float volumeMultiplier = 1.0f;
    private int audioFocusState = 0;

    public class AudioFocusListener implements AudioManager.OnAudioFocusChangeListener {
        private final Handler eventHandler;

        public AudioFocusListener(Handler handler) {
            this.eventHandler = handler;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAudioFocusChange$0(int i2) {
            AudioFocusManager.this.handlePlatformAudioFocusChange(i2);
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(final int i2) {
            this.eventHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6742c.lambda$onAudioFocusChange$0(i2);
                }
            });
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerCommand {
    }

    public interface PlayerControl {
        void executePlayerCommand(int i2);

        void setVolumeMultiplier(float f2);
    }

    public AudioFocusManager(Context context, Handler handler, PlayerControl playerControl) {
        this.audioManager = (AudioManager) Assertions.checkNotNull((AudioManager) context.getApplicationContext().getSystemService("audio"));
        this.playerControl = playerControl;
        this.focusListener = new AudioFocusListener(handler);
    }

    private void abandonAudioFocusDefault() {
        this.audioManager.abandonAudioFocus(this.focusListener);
    }

    private void abandonAudioFocusIfHeld() {
        if (this.audioFocusState == 0) {
            return;
        }
        if (Util.SDK_INT >= 26) {
            abandonAudioFocusV26();
        } else {
            abandonAudioFocusDefault();
        }
        setAudioFocusState(0);
    }

    @RequiresApi(26)
    private void abandonAudioFocusV26() {
        AudioFocusRequest audioFocusRequest = this.audioFocusRequest;
        if (audioFocusRequest != null) {
            this.audioManager.abandonAudioFocusRequest(audioFocusRequest);
        }
    }

    private static int convertAudioAttributesToFocusGain(@Nullable AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return 0;
        }
        int i2 = audioAttributes.usage;
        switch (i2) {
            case 0:
                Log.w(TAG, "Specify a proper usage in the audio attributes for audio focus handling. Using AUDIOFOCUS_GAIN by default.");
                return 1;
            case 1:
            case 14:
                return 1;
            case 2:
            case 4:
                return 2;
            case 3:
                return 0;
            case 11:
                if (audioAttributes.contentType == 1) {
                    return 2;
                }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
                return 3;
            case 15:
            default:
                StringBuilder sb = new StringBuilder(37);
                sb.append("Unidentified audio usage: ");
                sb.append(i2);
                Log.w(TAG, sb.toString());
                return 0;
            case 16:
                return Util.SDK_INT >= 19 ? 4 : 2;
        }
    }

    private void executePlayerCommand(int i2) {
        PlayerControl playerControl = this.playerControl;
        if (playerControl != null) {
            playerControl.executePlayerCommand(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePlatformAudioFocusChange(int i2) {
        if (i2 == -3 || i2 == -2) {
            if (i2 != -2 && !willPauseWhenDucked()) {
                setAudioFocusState(3);
                return;
            } else {
                executePlayerCommand(0);
                setAudioFocusState(2);
                return;
            }
        }
        if (i2 == -1) {
            executePlayerCommand(-1);
            abandonAudioFocusIfHeld();
        } else if (i2 == 1) {
            setAudioFocusState(1);
            executePlayerCommand(1);
        } else {
            StringBuilder sb = new StringBuilder(38);
            sb.append("Unknown focus change type: ");
            sb.append(i2);
            Log.w(TAG, sb.toString());
        }
    }

    private int requestAudioFocus() {
        if (this.audioFocusState == 1) {
            return 1;
        }
        if ((Util.SDK_INT >= 26 ? requestAudioFocusV26() : requestAudioFocusDefault()) == 1) {
            setAudioFocusState(1);
            return 1;
        }
        setAudioFocusState(0);
        return -1;
    }

    private int requestAudioFocusDefault() {
        return this.audioManager.requestAudioFocus(this.focusListener, Util.getStreamTypeForAudioUsage(((AudioAttributes) Assertions.checkNotNull(this.audioAttributes)).usage), this.focusGainToRequest);
    }

    @RequiresApi(26)
    private int requestAudioFocusV26() {
        AudioFocusRequest audioFocusRequest = this.audioFocusRequest;
        if (audioFocusRequest == null || this.rebuildAudioFocusRequest) {
            this.audioFocusRequest = (audioFocusRequest == null ? new AudioFocusRequest.Builder(this.focusGainToRequest) : new AudioFocusRequest.Builder(this.audioFocusRequest)).setAudioAttributes(((AudioAttributes) Assertions.checkNotNull(this.audioAttributes)).getAudioAttributesV21()).setWillPauseWhenDucked(willPauseWhenDucked()).setOnAudioFocusChangeListener(this.focusListener).build();
            this.rebuildAudioFocusRequest = false;
        }
        return this.audioManager.requestAudioFocus(this.audioFocusRequest);
    }

    private void setAudioFocusState(int i2) {
        if (this.audioFocusState == i2) {
            return;
        }
        this.audioFocusState = i2;
        float f2 = i2 == 3 ? 0.2f : 1.0f;
        if (this.volumeMultiplier == f2) {
            return;
        }
        this.volumeMultiplier = f2;
        PlayerControl playerControl = this.playerControl;
        if (playerControl != null) {
            playerControl.setVolumeMultiplier(f2);
        }
    }

    private boolean shouldAbandonAudioFocusIfHeld(int i2) {
        return i2 == 1 || this.focusGainToRequest != 1;
    }

    private boolean willPauseWhenDucked() {
        AudioAttributes audioAttributes = this.audioAttributes;
        return audioAttributes != null && audioAttributes.contentType == 1;
    }

    @VisibleForTesting
    public AudioManager.OnAudioFocusChangeListener getFocusListener() {
        return this.focusListener;
    }

    public float getVolumeMultiplier() {
        return this.volumeMultiplier;
    }

    public void release() {
        this.playerControl = null;
        abandonAudioFocusIfHeld();
    }

    public void setAudioAttributes(@Nullable AudioAttributes audioAttributes) {
        if (Util.areEqual(this.audioAttributes, audioAttributes)) {
            return;
        }
        this.audioAttributes = audioAttributes;
        int iConvertAudioAttributesToFocusGain = convertAudioAttributesToFocusGain(audioAttributes);
        this.focusGainToRequest = iConvertAudioAttributesToFocusGain;
        boolean z2 = true;
        if (iConvertAudioAttributesToFocusGain != 1 && iConvertAudioAttributesToFocusGain != 0) {
            z2 = false;
        }
        Assertions.checkArgument(z2, "Automatic handling of audio focus is only available for USAGE_MEDIA and USAGE_GAME.");
    }

    public int updateAudioFocus(boolean z2, int i2) {
        if (shouldAbandonAudioFocusIfHeld(i2)) {
            abandonAudioFocusIfHeld();
            return z2 ? 1 : -1;
        }
        if (z2) {
            return requestAudioFocus();
        }
        return -1;
    }
}
