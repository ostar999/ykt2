package com.easefun.polyv.mediasdk.example.widget.media;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;
import android.widget.TableLayout;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.easefun.polyv.mediasdk.player.MediaPlayerProxy;
import com.easefun.polyv.mediasdk.player.R;
import java.util.Locale;

/* loaded from: classes3.dex */
public class InfoHudViewHolder {
    private static final int MSG_UPDATE_HUD = 1;
    private IMediaPlayer mMediaPlayer;
    private TableLayoutBinder mTableLayoutBinder;
    private SparseArray<View> mRowMap = new SparseArray<>();
    private long mLoadCost = 0;
    private long mSeekCost = 0;
    private Handler mHandler = new Handler() { // from class: com.easefun.polyv.mediasdk.example.widget.media.InfoHudViewHolder.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            IMediaPlayer internalMediaPlayer;
            if (message.what == 1 && InfoHudViewHolder.this.mMediaPlayer != null) {
                IjkMediaPlayer ijkMediaPlayer = InfoHudViewHolder.this.mMediaPlayer instanceof IjkMediaPlayer ? (IjkMediaPlayer) InfoHudViewHolder.this.mMediaPlayer : ((InfoHudViewHolder.this.mMediaPlayer instanceof MediaPlayerProxy) && (internalMediaPlayer = ((MediaPlayerProxy) InfoHudViewHolder.this.mMediaPlayer).getInternalMediaPlayer()) != null && (internalMediaPlayer instanceof IjkMediaPlayer)) ? (IjkMediaPlayer) internalMediaPlayer : null;
                if (ijkMediaPlayer == null) {
                    return;
                }
                int videoDecoder = ijkMediaPlayer.getVideoDecoder();
                if (videoDecoder == 1) {
                    InfoHudViewHolder.this.setRowValue(R.string.vdec, "avcodec");
                } else if (videoDecoder != 2) {
                    InfoHudViewHolder.this.setRowValue(R.string.vdec, "");
                } else {
                    InfoHudViewHolder.this.setRowValue(R.string.vdec, "MediaCodec");
                }
                float videoOutputFramesPerSecond = ijkMediaPlayer.getVideoOutputFramesPerSecond();
                float videoDecodeFramesPerSecond = ijkMediaPlayer.getVideoDecodeFramesPerSecond();
                InfoHudViewHolder infoHudViewHolder = InfoHudViewHolder.this;
                int i2 = R.string.fps;
                Locale locale = Locale.US;
                infoHudViewHolder.setRowValue(i2, String.format(locale, "%.2f / %.2f", Float.valueOf(videoDecodeFramesPerSecond), Float.valueOf(videoOutputFramesPerSecond)));
                long videoCachedDuration = ijkMediaPlayer.getVideoCachedDuration();
                long audioCachedDuration = ijkMediaPlayer.getAudioCachedDuration();
                long videoCachedBytes = ijkMediaPlayer.getVideoCachedBytes();
                long audioCachedBytes = ijkMediaPlayer.getAudioCachedBytes();
                long tcpSpeed = ijkMediaPlayer.getTcpSpeed();
                long bitRate = ijkMediaPlayer.getBitRate();
                long seekLoadDuration = ijkMediaPlayer.getSeekLoadDuration();
                InfoHudViewHolder.this.setRowValue(R.string.v_cache, String.format(locale, "%s, %s", InfoHudViewHolder.formatedDurationMilli(videoCachedDuration), InfoHudViewHolder.formatedSize(videoCachedBytes)));
                InfoHudViewHolder.this.setRowValue(R.string.a_cache, String.format(locale, "%s, %s", InfoHudViewHolder.formatedDurationMilli(audioCachedDuration), InfoHudViewHolder.formatedSize(audioCachedBytes)));
                InfoHudViewHolder infoHudViewHolder2 = InfoHudViewHolder.this;
                infoHudViewHolder2.setRowValue(R.string.load_cost, String.format(locale, "%d ms", Long.valueOf(infoHudViewHolder2.mLoadCost)));
                InfoHudViewHolder infoHudViewHolder3 = InfoHudViewHolder.this;
                infoHudViewHolder3.setRowValue(R.string.seek_cost, String.format(locale, "%d ms", Long.valueOf(infoHudViewHolder3.mSeekCost)));
                InfoHudViewHolder.this.setRowValue(R.string.seek_load_cost, String.format(locale, "%d ms", Long.valueOf(seekLoadDuration)));
                InfoHudViewHolder.this.setRowValue(R.string.tcp_speed, String.format(locale, "%s", InfoHudViewHolder.formatedSpeed(tcpSpeed, 1000L)));
                InfoHudViewHolder.this.setRowValue(R.string.bit_rate, String.format(locale, "%.2f kbs", Float.valueOf(bitRate / 1000.0f)));
                InfoHudViewHolder.this.mHandler.removeMessages(1);
                InfoHudViewHolder.this.mHandler.sendEmptyMessageDelayed(1, 500L);
            }
        }
    };

    public InfoHudViewHolder(Context context, TableLayout tableLayout) {
        this.mTableLayoutBinder = new TableLayoutBinder(context, tableLayout);
    }

    private void appendRow(int i2) {
        this.mRowMap.put(i2, this.mTableLayoutBinder.appendRow2(i2, (String) null));
    }

    private void appendSection(int i2) {
        this.mTableLayoutBinder.appendSection(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatedDurationMilli(long j2) {
        return j2 >= 1000 ? String.format(Locale.US, "%.2f sec", Float.valueOf(j2 / 1000.0f)) : String.format(Locale.US, "%d msec", Long.valueOf(j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatedSize(long j2) {
        return j2 >= 100000 ? String.format(Locale.US, "%.2f MB", Float.valueOf((j2 / 1000.0f) / 1000.0f)) : j2 >= 100 ? String.format(Locale.US, "%.1f KB", Float.valueOf(j2 / 1000.0f)) : String.format(Locale.US, "%d B", Long.valueOf(j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatedSpeed(long j2, long j3) {
        if (j3 <= 0 || j2 <= 0) {
            return "0 B/s";
        }
        float f2 = (j2 * 1000.0f) / j3;
        return f2 >= 1000000.0f ? String.format(Locale.US, "%.2f MB/s", Float.valueOf((f2 / 1000.0f) / 1000.0f)) : f2 >= 1000.0f ? String.format(Locale.US, "%.1f KB/s", Float.valueOf(f2 / 1000.0f)) : String.format(Locale.US, "%d B/s", Long.valueOf((long) f2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRowValue(int i2, String str) {
        View view = this.mRowMap.get(i2);
        if (view != null) {
            this.mTableLayoutBinder.setValueText(view, str);
        } else {
            this.mRowMap.put(i2, this.mTableLayoutBinder.appendRow2(i2, str));
        }
    }

    public void setMediaPlayer(IMediaPlayer iMediaPlayer) {
        this.mMediaPlayer = iMediaPlayer;
        if (iMediaPlayer != null) {
            this.mHandler.sendEmptyMessageDelayed(1, 500L);
        } else {
            this.mHandler.removeMessages(1);
        }
    }

    public void updateLoadCost(long j2) {
        this.mLoadCost = j2;
    }

    public void updateSeekCost(long j2) {
        this.mSeekCost = j2;
    }
}
