package com.easefun.polyv.mediasdk.example.widget.media;

import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.easefun.polyv.mediasdk.player.MediaPlayerProxy;
import com.easefun.polyv.mediasdk.player.TextureMediaPlayer;

/* loaded from: classes3.dex */
public class MediaPlayerCompat {
    public static void deselectTrack(IMediaPlayer iMediaPlayer, int i2) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer == null) {
            return;
        }
        ijkMediaPlayer.deselectTrack(i2);
    }

    public static IjkMediaPlayer getIjkMediaPlayer(IMediaPlayer iMediaPlayer) {
        if (iMediaPlayer == null) {
            return null;
        }
        if (iMediaPlayer instanceof IjkMediaPlayer) {
            return (IjkMediaPlayer) iMediaPlayer;
        }
        if (!(iMediaPlayer instanceof MediaPlayerProxy)) {
            return null;
        }
        MediaPlayerProxy mediaPlayerProxy = (MediaPlayerProxy) iMediaPlayer;
        if (mediaPlayerProxy.getInternalMediaPlayer() instanceof IjkMediaPlayer) {
            return (IjkMediaPlayer) mediaPlayerProxy.getInternalMediaPlayer();
        }
        return null;
    }

    public static String getName(IMediaPlayer iMediaPlayer) {
        if (iMediaPlayer == null) {
            return "null";
        }
        if (!(iMediaPlayer instanceof TextureMediaPlayer)) {
            return iMediaPlayer.getClass().getSimpleName();
        }
        StringBuilder sb = new StringBuilder("TextureMediaPlayer <");
        IMediaPlayer internalMediaPlayer = ((TextureMediaPlayer) iMediaPlayer).getInternalMediaPlayer();
        if (internalMediaPlayer == null) {
            sb.append("null>");
        } else {
            sb.append(internalMediaPlayer.getClass().getSimpleName());
            sb.append(">");
        }
        return sb.toString();
    }

    public static int getSelectedTrack(IMediaPlayer iMediaPlayer, int i2) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer == null) {
            return -1;
        }
        return ijkMediaPlayer.getSelectedTrack(i2);
    }

    public static void selectTrack(IMediaPlayer iMediaPlayer, int i2) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer == null) {
            return;
        }
        ijkMediaPlayer.selectTrack(i2);
    }
}
