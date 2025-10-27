package com.easefun.polyv.mediasdk.example.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;

/* loaded from: classes3.dex */
public class MediaPlayerService extends Service {
    private static IMediaPlayer sMediaPlayer;

    public static IMediaPlayer getMediaPlayer() {
        return sMediaPlayer;
    }

    public static void intentToStart(Context context) {
        context.startService(newIntent(context));
    }

    public static void intentToStop(Context context) {
        context.stopService(newIntent(context));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) MediaPlayerService.class);
    }

    public static void setMediaPlayer(IMediaPlayer iMediaPlayer) throws IllegalStateException {
        IMediaPlayer iMediaPlayer2 = sMediaPlayer;
        if (iMediaPlayer2 != null && iMediaPlayer2 != iMediaPlayer) {
            if (iMediaPlayer2.isPlaying()) {
                sMediaPlayer.stop();
            }
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
        sMediaPlayer = iMediaPlayer;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
