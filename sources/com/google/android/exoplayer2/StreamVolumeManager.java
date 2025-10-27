package com.google.android.exoplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
final class StreamVolumeManager {
    private static final String TAG = "StreamVolumeManager";
    private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private static final int VOLUME_FLAGS = 1;
    private final Context applicationContext;
    private final AudioManager audioManager;
    private final Handler eventHandler;
    private final Listener listener;
    private boolean muted;

    @Nullable
    private VolumeChangeReceiver receiver;
    private int streamType;
    private int volume;

    public interface Listener {
        void onStreamTypeChanged(int i2);

        void onStreamVolumeChanged(int i2, boolean z2);
    }

    public final class VolumeChangeReceiver extends BroadcastReceiver {
        private VolumeChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Handler handler = StreamVolumeManager.this.eventHandler;
            final StreamVolumeManager streamVolumeManager = StreamVolumeManager.this;
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.d2
                @Override // java.lang.Runnable
                public final void run() {
                    StreamVolumeManager.access$200(streamVolumeManager);
                }
            });
        }
    }

    public StreamVolumeManager(Context context, Handler handler, Listener listener) {
        Context applicationContext = context.getApplicationContext();
        this.applicationContext = applicationContext;
        this.eventHandler = handler;
        this.listener = listener;
        AudioManager audioManager = (AudioManager) Assertions.checkStateNotNull((AudioManager) applicationContext.getSystemService("audio"));
        this.audioManager = audioManager;
        this.streamType = 3;
        this.volume = getVolumeFromManager(audioManager, 3);
        this.muted = getMutedFromManager(audioManager, this.streamType);
        VolumeChangeReceiver volumeChangeReceiver = new VolumeChangeReceiver();
        try {
            applicationContext.registerReceiver(volumeChangeReceiver, new IntentFilter(VOLUME_CHANGED_ACTION));
            this.receiver = volumeChangeReceiver;
        } catch (RuntimeException e2) {
            Log.w(TAG, "Error registering stream volume receiver", e2);
        }
    }

    public static /* synthetic */ void access$200(StreamVolumeManager streamVolumeManager) {
        streamVolumeManager.updateVolumeAndNotifyIfChanged();
    }

    private static boolean getMutedFromManager(AudioManager audioManager, int i2) {
        return Util.SDK_INT >= 23 ? audioManager.isStreamMute(i2) : getVolumeFromManager(audioManager, i2) == 0;
    }

    private static int getVolumeFromManager(AudioManager audioManager, int i2) {
        try {
            return audioManager.getStreamVolume(i2);
        } catch (RuntimeException e2) {
            StringBuilder sb = new StringBuilder(60);
            sb.append("Could not retrieve stream volume for stream type ");
            sb.append(i2);
            Log.w(TAG, sb.toString(), e2);
            return audioManager.getStreamMaxVolume(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVolumeAndNotifyIfChanged() {
        int volumeFromManager = getVolumeFromManager(this.audioManager, this.streamType);
        boolean mutedFromManager = getMutedFromManager(this.audioManager, this.streamType);
        if (this.volume == volumeFromManager && this.muted == mutedFromManager) {
            return;
        }
        this.volume = volumeFromManager;
        this.muted = mutedFromManager;
        this.listener.onStreamVolumeChanged(volumeFromManager, mutedFromManager);
    }

    public void decreaseVolume() {
        if (this.volume <= getMinVolume()) {
            return;
        }
        this.audioManager.adjustStreamVolume(this.streamType, -1, 1);
        updateVolumeAndNotifyIfChanged();
    }

    public int getMaxVolume() {
        return this.audioManager.getStreamMaxVolume(this.streamType);
    }

    public int getMinVolume() {
        if (Util.SDK_INT >= 28) {
            return this.audioManager.getStreamMinVolume(this.streamType);
        }
        return 0;
    }

    public int getVolume() {
        return this.volume;
    }

    public void increaseVolume() {
        if (this.volume >= getMaxVolume()) {
            return;
        }
        this.audioManager.adjustStreamVolume(this.streamType, 1, 1);
        updateVolumeAndNotifyIfChanged();
    }

    public boolean isMuted() {
        return this.muted;
    }

    public void release() {
        VolumeChangeReceiver volumeChangeReceiver = this.receiver;
        if (volumeChangeReceiver != null) {
            try {
                this.applicationContext.unregisterReceiver(volumeChangeReceiver);
            } catch (RuntimeException e2) {
                Log.w(TAG, "Error unregistering stream volume receiver", e2);
            }
            this.receiver = null;
        }
    }

    public void setMuted(boolean z2) {
        if (Util.SDK_INT >= 23) {
            this.audioManager.adjustStreamVolume(this.streamType, z2 ? -100 : 100, 1);
        } else {
            this.audioManager.setStreamMute(this.streamType, z2);
        }
        updateVolumeAndNotifyIfChanged();
    }

    public void setStreamType(int i2) {
        if (this.streamType == i2) {
            return;
        }
        this.streamType = i2;
        updateVolumeAndNotifyIfChanged();
        this.listener.onStreamTypeChanged(i2);
    }

    public void setVolume(int i2) {
        if (i2 < getMinVolume() || i2 > getMaxVolume()) {
            return;
        }
        this.audioManager.setStreamVolume(this.streamType, i2, 1);
        updateVolumeAndNotifyIfChanged();
    }
}
