package com.luck.picture.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.utils.DateUtils;
import java.io.IOException;

/* loaded from: classes4.dex */
public class AudioPlayDialog extends Dialog implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private final String audioPath;
    private final Handler mHandler;
    public Runnable mRunnable;
    private final MediaPlayer mediaPlayer;
    private final SeekBar musicSeekBar;
    private final TextView tvMusicStatus;
    private final TextView tvMusicTime;
    private final TextView tvMusicTotal;
    private final TextView tvPlayPause;

    public AudioPlayDialog(@NonNull Context context, String str) {
        super(context, R.style.Picture_Theme_Dialog);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mRunnable = new Runnable() { // from class: com.luck.picture.lib.dialog.AudioPlayDialog.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AudioPlayDialog.this.tvMusicTime.setText(DateUtils.formatDurationTime(AudioPlayDialog.this.mediaPlayer.getCurrentPosition()));
                    AudioPlayDialog.this.musicSeekBar.setProgress(AudioPlayDialog.this.mediaPlayer.getCurrentPosition());
                    AudioPlayDialog.this.musicSeekBar.setMax(AudioPlayDialog.this.mediaPlayer.getDuration());
                    AudioPlayDialog.this.tvMusicTotal.setText(DateUtils.formatDurationTime(AudioPlayDialog.this.mediaPlayer.getDuration()));
                    AudioPlayDialog.this.mHandler.postDelayed(AudioPlayDialog.this.mRunnable, 50L);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        this.audioPath = str;
        this.mediaPlayer = new MediaPlayer();
        setContentView(R.layout.ps_audio_play_dialog);
        getWindow().setWindowAnimations(R.style.Picture_Theme_Dialog_AudioStyle);
        this.tvMusicStatus = (TextView) findViewById(R.id.tv_musicStatus);
        this.tvMusicTime = (TextView) findViewById(R.id.tv_musicTime);
        SeekBar seekBar = (SeekBar) findViewById(R.id.music_seek_bar);
        this.musicSeekBar = seekBar;
        this.tvMusicTotal = (TextView) findViewById(R.id.tv_music_total);
        TextView textView = (TextView) findViewById(R.id.tv_play_pause);
        this.tvPlayPause = textView;
        TextView textView2 = (TextView) findViewById(R.id.tv_stop);
        TextView textView3 = (TextView) findViewById(R.id.tv_quit);
        seekBar.setOnSeekBarChangeListener(this);
        textView.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.luck.picture.lib.dialog.AudioPlayDialog.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                AudioPlayDialog.this.mHandler.removeCallbacks(AudioPlayDialog.this.mRunnable);
                AudioPlayDialog.this.mediaPlayer.release();
            }
        });
    }

    private void playAudio() throws IllegalStateException {
        this.musicSeekBar.setProgress(this.mediaPlayer.getCurrentPosition());
        this.musicSeekBar.setMax(this.mediaPlayer.getDuration());
        if (this.mediaPlayer.isPlaying()) {
            TextView textView = this.tvPlayPause;
            textView.setText(textView.getContext().getString(R.string.ps_play_audio));
            this.tvMusicStatus.setText(this.tvPlayPause.getContext().getString(R.string.ps_pause_audio));
            this.mediaPlayer.pause();
            return;
        }
        TextView textView2 = this.tvPlayPause;
        textView2.setText(textView2.getContext().getString(R.string.ps_pause_audio));
        this.tvMusicStatus.setText(this.tvPlayPause.getContext().getString(R.string.ps_play_audio));
        this.mediaPlayer.start();
        this.mHandler.post(this.mRunnable);
    }

    public static void showPlayAudioDialog(Context context, String str) {
        new AudioPlayDialog(context, str).show();
    }

    private void stop(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        try {
            this.mediaPlayer.stop();
            this.mediaPlayer.reset();
            if (PictureMimeType.isContent(str)) {
                this.mediaPlayer.setDataSource(getContext(), Uri.parse(str));
            } else {
                this.mediaPlayer.setDataSource(str);
            }
            this.mediaPlayer.prepare();
            this.mediaPlayer.seekTo(0);
            TextView textView = this.tvMusicStatus;
            textView.setText(textView.getContext().getString(R.string.ps_stop_audio));
            this.tvPlayPause.setText(this.tvMusicStatus.getContext().getString(R.string.ps_play_audio));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        int id = view.getId();
        if (id == R.id.tv_play_pause) {
            playAudio();
            return;
        }
        if (id == R.id.tv_stop) {
            stop(this.audioPath);
        } else if (id == R.id.tv_quit) {
            this.mHandler.removeCallbacks(this.mRunnable);
            stop(this.audioPath);
            dismiss();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) throws IllegalStateException {
        if (z2) {
            this.mediaPlayer.seekTo(i2);
        }
    }

    @Override // android.app.Dialog
    public void onStart() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        super.onStart();
        try {
            if (PictureMimeType.isContent(this.audioPath)) {
                this.mediaPlayer.setDataSource(getContext(), Uri.parse(this.audioPath));
            } else {
                this.mediaPlayer.setDataSource(this.audioPath);
            }
            this.mediaPlayer.prepare();
            this.mediaPlayer.setLooping(true);
            playAudio();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
