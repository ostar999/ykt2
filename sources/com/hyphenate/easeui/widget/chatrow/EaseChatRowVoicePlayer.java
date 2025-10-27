package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.EaseIM;
import java.io.IOException;

/* loaded from: classes4.dex */
public class EaseChatRowVoicePlayer {
    private static final String TAG = "EaseChatRowVoicePlayer";
    private static EaseChatRowVoicePlayer instance;
    private AudioManager audioManager;
    private final Context baseContext;
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener onCompletionListener;
    private String playingId;

    private EaseChatRowVoicePlayer(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.baseContext = applicationContext;
        this.audioManager = (AudioManager) applicationContext.getSystemService("audio");
        this.mediaPlayer = new MediaPlayer();
    }

    public static EaseChatRowVoicePlayer getInstance(Context context) {
        if (instance == null) {
            synchronized (EaseChatRowVoicePlayer.class) {
                if (instance == null) {
                    instance = new EaseChatRowVoicePlayer(context);
                }
            }
        }
        return instance;
    }

    private void setSpeaker() {
        if (EaseIM.getInstance().getSettingsProvider().isSpeakerOpened()) {
            this.audioManager.setMode(0);
            this.audioManager.setSpeakerphoneOn(true);
            this.mediaPlayer.setAudioStreamType(2);
        } else {
            this.audioManager.setSpeakerphoneOn(false);
            this.audioManager.setMode(2);
            this.mediaPlayer.setAudioStreamType(0);
        }
    }

    public String getCurrentPlayingId() {
        return this.playingId;
    }

    public MediaPlayer getPlayer() {
        return this.mediaPlayer;
    }

    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }

    public void play(EMMessage eMMessage, MediaPlayer.OnCompletionListener onCompletionListener) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (eMMessage.getBody() instanceof EMVoiceMessageBody) {
            if (this.mediaPlayer.isPlaying()) {
                stop();
            }
            this.playingId = eMMessage.getMsgId();
            this.onCompletionListener = onCompletionListener;
            try {
                setSpeaker();
                this.mediaPlayer.setDataSource(this.baseContext, ((EMVoiceMessageBody) eMMessage.getBody()).getLocalUri());
                this.mediaPlayer.prepare();
                this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRowVoicePlayer.1
                    @Override // android.media.MediaPlayer.OnCompletionListener
                    public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
                        EaseChatRowVoicePlayer.this.stop();
                        EaseChatRowVoicePlayer.this.playingId = null;
                        EaseChatRowVoicePlayer.this.onCompletionListener = null;
                    }
                });
                this.mediaPlayer.start();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void release() {
        this.onCompletionListener = null;
    }

    public void stop() throws IllegalStateException {
        this.mediaPlayer.stop();
        this.mediaPlayer.reset();
        MediaPlayer.OnCompletionListener onCompletionListener = this.onCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(this.mediaPlayer);
        }
    }
}
