package com.aliyun.player.alivcplayerexpand.view.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class ShowMoreView extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Context context;
    private RadioGroup loopPlayRadioGroup;
    private OnBarrageButtonClickListener mOnBarrageButtonClickListener;
    private OnDownloadButtonClickListener mOnDownloadButtonClickListener;
    private OnLightSeekChangeListener mOnLightSeekChangeListener;
    private OnLoopCheckedChangedListener mOnLoopCheckedChangedListener;
    private OnScaleModeCheckedChangedListener mOnScaleModeCheckedChangedListener;
    private OnScreenCastButtonClickListener mOnScreenCastButtonClickListener;
    private OnSpeedCheckedChangedListener mOnSpeedCheckedChangedListener;
    private OnVoiceSeekChangeListener mOnVoiceSeekChangeListener;
    private AliyunShowMoreValue moreValue;
    private RadioGroup rgSpeed;
    private RadioGroup scaleModelRadioGroup;
    private SeekBar seekLight;
    private SeekBar seekVoice;
    private TextView tvBarrage;
    private TextView tvCastScreen;
    private TextView tvDonwload;

    public interface OnBarrageButtonClickListener {
        void onBarrageClick();
    }

    public interface OnDownloadButtonClickListener {
        void onDownloadClick();
    }

    public interface OnLightSeekChangeListener {
        void onProgress(SeekBar seekBar, int i2, boolean z2);

        void onStart(SeekBar seekBar);

        void onStop(SeekBar seekBar);
    }

    public interface OnLoopCheckedChangedListener {
        void onLoopChanged(RadioGroup radioGroup, int i2);
    }

    public interface OnScaleModeCheckedChangedListener {
        void onScaleModeChanged(RadioGroup radioGroup, int i2);
    }

    public interface OnScreenCastButtonClickListener {
        void onScreenCastClick();
    }

    public interface OnSpeedCheckedChangedListener {
        void onSpeedChanged(RadioGroup radioGroup, int i2);
    }

    public interface OnVoiceSeekChangeListener {
        void onProgress(SeekBar seekBar, int i2, boolean z2);

        void onStart(SeekBar seekBar);

        void onStop(SeekBar seekBar);
    }

    public ShowMoreView(Context context, AliyunShowMoreValue aliyunShowMoreValue) {
        super(context);
        this.context = context;
        this.moreValue = aliyunShowMoreValue;
        init();
    }

    private void addListener() {
        this.tvDonwload.setOnClickListener(this);
        this.tvCastScreen.setOnClickListener(this);
        this.tvBarrage.setOnClickListener(this);
        this.rgSpeed.setOnCheckedChangeListener(this);
        this.loopPlayRadioGroup.setOnCheckedChangeListener(this);
        this.scaleModelRadioGroup.setOnCheckedChangeListener(this);
        this.seekLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aliyun.player.alivcplayerexpand.view.more.ShowMoreView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
                if (ShowMoreView.this.mOnLightSeekChangeListener != null) {
                    ShowMoreView.this.mOnLightSeekChangeListener.onProgress(seekBar, i2, z2);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (ShowMoreView.this.mOnLightSeekChangeListener != null) {
                    ShowMoreView.this.mOnLightSeekChangeListener.onStart(seekBar);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (ShowMoreView.this.mOnLightSeekChangeListener != null) {
                    ShowMoreView.this.mOnLightSeekChangeListener.onStop(seekBar);
                }
            }
        });
        this.seekVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aliyun.player.alivcplayerexpand.view.more.ShowMoreView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
                if (ShowMoreView.this.mOnVoiceSeekChangeListener != null) {
                    ShowMoreView.this.mOnVoiceSeekChangeListener.onProgress(seekBar, i2, z2);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (ShowMoreView.this.mOnVoiceSeekChangeListener != null) {
                    ShowMoreView.this.mOnVoiceSeekChangeListener.onStart(seekBar);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (ShowMoreView.this.mOnVoiceSeekChangeListener != null) {
                    ShowMoreView.this.mOnVoiceSeekChangeListener.onStop(seekBar);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void configViews() {
        /*
            r5 = this;
            com.aliyun.player.alivcplayerexpand.view.more.AliyunShowMoreValue r0 = r5.moreValue
            if (r0 != 0) goto L5
            return
        L5:
            android.widget.SeekBar r1 = r5.seekLight
            int r0 = r0.getScreenBrightness()
            r1.setProgress(r0)
            android.widget.SeekBar r0 = r5.seekVoice
            com.aliyun.player.alivcplayerexpand.view.more.AliyunShowMoreValue r1 = r5.moreValue
            int r1 = r1.getVolume()
            r0.setProgress(r1)
            com.aliyun.player.alivcplayerexpand.view.more.AliyunShowMoreValue r0 = r5.moreValue
            float r0 = r0.getSpeed()
            r1 = 1061158912(0x3f400000, float:0.75)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r2 = 2
            r3 = 0
            r4 = 1
            if (r1 != 0) goto L2a
            r0 = r3
            goto L48
        L2a:
            r1 = 0
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 != 0) goto L31
        L2f:
            r0 = r4
            goto L48
        L31:
            r1 = 1067450368(0x3fa00000, float:1.25)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 != 0) goto L39
            r0 = r2
            goto L48
        L39:
            r1 = 1069547520(0x3fc00000, float:1.5)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 != 0) goto L41
            r0 = 3
            goto L48
        L41:
            r1 = 1073741824(0x40000000, float:2.0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 != 0) goto L2f
            r0 = 4
        L48:
            android.widget.RadioGroup r1 = r5.rgSpeed
            android.view.View r0 = r1.getChildAt(r0)
            int r0 = r0.getId()
            r1.check(r0)
            com.aliyun.player.alivcplayerexpand.view.more.AliyunShowMoreValue r0 = r5.moreValue
            com.aliyun.player.IPlayer$ScaleMode r0 = r0.getScaleMode()
            com.aliyun.player.IPlayer$ScaleMode r1 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FIT
            if (r0 != r1) goto L61
        L5f:
            r2 = r3
            goto L6b
        L61:
            com.aliyun.player.IPlayer$ScaleMode r1 = com.aliyun.player.IPlayer.ScaleMode.SCALE_ASPECT_FILL
            if (r0 != r1) goto L67
            r2 = r4
            goto L6b
        L67:
            com.aliyun.player.IPlayer$ScaleMode r1 = com.aliyun.player.IPlayer.ScaleMode.SCALE_TO_FILL
            if (r0 != r1) goto L5f
        L6b:
            android.widget.RadioGroup r0 = r5.scaleModelRadioGroup
            android.view.View r1 = r0.getChildAt(r2)
            int r1 = r1.getId()
            r0.check(r1)
            android.widget.RadioGroup r0 = r5.loopPlayRadioGroup
            com.aliyun.player.alivcplayerexpand.view.more.AliyunShowMoreValue r1 = r5.moreValue
            boolean r1 = r1.isLoop()
            r1 = r1 ^ r4
            android.view.View r1 = r0.getChildAt(r1)
            int r1 = r1.getId()
            r0.check(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.alivcplayerexpand.view.more.ShowMoreView.configViews():void");
    }

    private void findAllViews(View view) {
        this.seekLight = (SeekBar) view.findViewById(R.id.seek_light);
        this.seekVoice = (SeekBar) view.findViewById(R.id.seek_voice);
        this.tvDonwload = (TextView) view.findViewById(R.id.tv_download);
        this.tvCastScreen = (TextView) view.findViewById(R.id.tv_cast_screen);
        this.tvBarrage = (TextView) view.findViewById(R.id.tv_barrage);
        this.rgSpeed = (RadioGroup) findViewById(R.id.alivc_rg_speed);
        this.loopPlayRadioGroup = (RadioGroup) view.findViewById(R.id.alivc_rg_loop);
        this.scaleModelRadioGroup = (RadioGroup) view.findViewById(R.id.alivc_rg_scale_model);
        configViews();
        addListener();
    }

    private void init() {
        findAllViews(LayoutInflater.from(this.context).inflate(R.layout.alivc_dialog_more, (ViewGroup) this, true));
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i2) {
        OnSpeedCheckedChangedListener onSpeedCheckedChangedListener = this.mOnSpeedCheckedChangedListener;
        if (onSpeedCheckedChangedListener != null && radioGroup == this.rgSpeed) {
            onSpeedCheckedChangedListener.onSpeedChanged(radioGroup, i2);
        }
        OnScaleModeCheckedChangedListener onScaleModeCheckedChangedListener = this.mOnScaleModeCheckedChangedListener;
        if (onScaleModeCheckedChangedListener != null && radioGroup == this.scaleModelRadioGroup) {
            onScaleModeCheckedChangedListener.onScaleModeChanged(radioGroup, i2);
        }
        OnLoopCheckedChangedListener onLoopCheckedChangedListener = this.mOnLoopCheckedChangedListener;
        if (onLoopCheckedChangedListener == null || radioGroup != this.loopPlayRadioGroup) {
            return;
        }
        onLoopCheckedChangedListener.onLoopChanged(radioGroup, i2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnBarrageButtonClickListener onBarrageButtonClickListener;
        int id = view.getId();
        if (id == R.id.tv_download) {
            OnDownloadButtonClickListener onDownloadButtonClickListener = this.mOnDownloadButtonClickListener;
            if (onDownloadButtonClickListener != null) {
                onDownloadButtonClickListener.onDownloadClick();
                return;
            }
            return;
        }
        if (id == R.id.tv_cast_screen) {
            OnScreenCastButtonClickListener onScreenCastButtonClickListener = this.mOnScreenCastButtonClickListener;
            if (onScreenCastButtonClickListener != null) {
                onScreenCastButtonClickListener.onScreenCastClick();
                return;
            }
            return;
        }
        if (id != R.id.tv_barrage || (onBarrageButtonClickListener = this.mOnBarrageButtonClickListener) == null) {
            return;
        }
        onBarrageButtonClickListener.onBarrageClick();
    }

    public void setBrightness(int i2) {
        SeekBar seekBar = this.seekLight;
        if (seekBar != null) {
            seekBar.setProgress(i2);
        }
    }

    public void setOnBarrageButtonClickListener(OnBarrageButtonClickListener onBarrageButtonClickListener) {
        this.mOnBarrageButtonClickListener = onBarrageButtonClickListener;
    }

    public void setOnDownloadButtonClickListener(OnDownloadButtonClickListener onDownloadButtonClickListener) {
        this.mOnDownloadButtonClickListener = onDownloadButtonClickListener;
    }

    public void setOnLightSeekChangeListener(OnLightSeekChangeListener onLightSeekChangeListener) {
        this.mOnLightSeekChangeListener = onLightSeekChangeListener;
    }

    public void setOnLoopCheckedChangedListener(OnLoopCheckedChangedListener onLoopCheckedChangedListener) {
        this.mOnLoopCheckedChangedListener = onLoopCheckedChangedListener;
    }

    public void setOnScaleModeCheckedChangedListener(OnScaleModeCheckedChangedListener onScaleModeCheckedChangedListener) {
        this.mOnScaleModeCheckedChangedListener = onScaleModeCheckedChangedListener;
    }

    public void setOnScreenCastButtonClickListener(OnScreenCastButtonClickListener onScreenCastButtonClickListener) {
        this.mOnScreenCastButtonClickListener = onScreenCastButtonClickListener;
    }

    public void setOnSpeedCheckedChangedListener(OnSpeedCheckedChangedListener onSpeedCheckedChangedListener) {
        this.mOnSpeedCheckedChangedListener = onSpeedCheckedChangedListener;
    }

    public void setOnVoiceSeekChangeListener(OnVoiceSeekChangeListener onVoiceSeekChangeListener) {
        this.mOnVoiceSeekChangeListener = onVoiceSeekChangeListener;
    }

    public void setVoiceVolume(float f2) {
        SeekBar seekBar = this.seekVoice;
        if (seekBar != null) {
            seekBar.setProgress((int) (f2 * 100.0f));
        }
    }
}
