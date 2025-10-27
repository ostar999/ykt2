package com.plv.business.api.common.service;

import android.content.Context;
import android.media.AudioManager;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.plv.business.api.common.player.microplayer.PLVCommonVideoView;

/* loaded from: classes4.dex */
public class PLVAudioFocusManager<T extends PLVCommonVideoView> implements AudioManager.OnAudioFocusChangeListener {
    private AudioManager audioManager;
    private boolean isPausedByFocusLossTransient;
    private boolean isPlayingOnPause;
    private T videoView;

    public PLVAudioFocusManager(Context context) {
        this.audioManager = (AudioManager) context.getApplicationContext().getSystemService("audio");
    }

    public void abandonAudioFocus() {
        this.audioManager.abandonAudioFocus(this);
    }

    public void addPlayer(T t2) {
        this.videoView = t2;
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(final int i2) {
        T t2 = this.videoView;
        if (t2 == null) {
            return;
        }
        t2.post(new Runnable() { // from class: com.plv.business.api.common.service.PLVAudioFocusManager.1
            @Override // java.lang.Runnable
            public void run() {
                int i3 = i2;
                if (i3 == -3) {
                    IMediaPlayer mediaPlayer = PLVAudioFocusManager.this.videoView.getMediaPlayer();
                    if (mediaPlayer != null) {
                        mediaPlayer.setVolume(Math.min(0.5f, PLVAudioFocusManager.this.videoView.getPlayerVolume() / 100.0f), Math.min(0.5f, PLVAudioFocusManager.this.videoView.getPlayerVolume() / 100.0f));
                        return;
                    }
                    return;
                }
                if (i3 == -2 || i3 == -1) {
                    PLVAudioFocusManager pLVAudioFocusManager = PLVAudioFocusManager.this;
                    pLVAudioFocusManager.isPlayingOnPause = pLVAudioFocusManager.videoView.isPlaying() || (PLVAudioFocusManager.this.videoView.getSubVideoView() != null && PLVAudioFocusManager.this.videoView.getSubVideoView().isShow());
                    PLVAudioFocusManager.this.videoView.pause(false);
                    PLVAudioFocusManager.this.isPausedByFocusLossTransient = true;
                    return;
                }
                if (i3 != 1) {
                    return;
                }
                if (PLVAudioFocusManager.this.isPausedByFocusLossTransient && PLVAudioFocusManager.this.isPlayingOnPause) {
                    PLVAudioFocusManager.this.videoView.start();
                }
                IMediaPlayer mediaPlayer2 = PLVAudioFocusManager.this.videoView.getMediaPlayer();
                if (mediaPlayer2 != null) {
                    mediaPlayer2.setVolume(PLVAudioFocusManager.this.videoView.getPlayerVolume() / 100.0f, PLVAudioFocusManager.this.videoView.getPlayerVolume() / 100.0f);
                }
                PLVAudioFocusManager.this.isPausedByFocusLossTransient = false;
                PLVAudioFocusManager.this.isPlayingOnPause = false;
            }
        });
    }

    public boolean requestAudioFocus() {
        return this.audioManager.requestAudioFocus(this, 3, 2) == 1;
    }
}
