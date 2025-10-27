package com.aliyun.player.alivcplayerexpand.view.more;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class DanmakuSettingView extends LinearLayout {
    public static final int ALPHA_PROGRESS_DEFAULT = 0;
    public static final int REGION_PROGRESS_DEFAULT = 0;
    public static final int SPEED_PROGRESS_DEFAULT = 30;
    private int mAlphProgress;
    private SeekBar mAlphaSeek;
    private TextView mAlphaValueTextView;
    private TextView mDefaultTextView;
    private SeekBar.OnSeekBarChangeListener mOnAlphaSeekBarChangeListener;
    private OnDefaultClickListener mOnDefaultClickListener;
    private SeekBar.OnSeekBarChangeListener mOnRegionSeekBarChangeListener;
    private SeekBar.OnSeekBarChangeListener mOnSpeedSeekBarChangeListener;
    private int mRegionProgress;
    private SeekBar mRegionSeek;
    private TextView mRegionValueTextView;
    private int mSpeedProgress;
    private SeekBar mSpeedSeek;
    private TextView mSpeedValueTextView;
    private View view;

    public interface OnDefaultClickListener {
        void onDefaultClick();
    }

    public DanmakuSettingView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.alivc_dialog_danmaku_setting, (ViewGroup) this, true);
        initView();
        initListener();
        initDefault();
    }

    private void initDefault() {
        this.mAlphaSeek.setProgress(0);
        this.mSpeedSeek.setProgress(30);
        this.mRegionSeek.setProgress(0);
    }

    private void initListener() {
        this.mAlphaSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aliyun.player.alivcplayerexpand.view.more.DanmakuSettingView.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
                DanmakuSettingView.this.mAlphProgress = i2;
                if (DanmakuSettingView.this.mOnAlphaSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnAlphaSeekBarChangeListener.onProgressChanged(seekBar, i2, z2);
                }
                if (DanmakuSettingView.this.mAlphaValueTextView != null) {
                    DanmakuSettingView.this.mAlphaValueTextView.setText(i2 + "%");
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (DanmakuSettingView.this.mOnAlphaSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnAlphaSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (DanmakuSettingView.this.mOnAlphaSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnAlphaSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });
        this.mSpeedSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aliyun.player.alivcplayerexpand.view.more.DanmakuSettingView.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
                DanmakuSettingView.this.mSpeedProgress = i2;
                if (DanmakuSettingView.this.mOnSpeedSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnSpeedSeekBarChangeListener.onProgressChanged(seekBar, i2, z2);
                }
                if (DanmakuSettingView.this.mSpeedValueTextView != null) {
                    DanmakuSettingView.this.mSpeedValueTextView.setText(i2 + "%");
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (DanmakuSettingView.this.mOnSpeedSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnSpeedSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (DanmakuSettingView.this.mOnSpeedSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnSpeedSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });
        this.mRegionSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aliyun.player.alivcplayerexpand.view.more.DanmakuSettingView.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
                DanmakuSettingView.this.mRegionProgress = i2;
                if (DanmakuSettingView.this.mOnRegionSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnRegionSeekBarChangeListener.onProgressChanged(seekBar, i2, z2);
                }
                if (DanmakuSettingView.this.mRegionValueTextView != null) {
                    if (i2 == 0) {
                        DanmakuSettingView.this.mRegionValueTextView.setText(DanmakuSettingView.this.getResources().getString(R.string.alivc_danmaku_position_quarter));
                        return;
                    }
                    if (i2 == 1) {
                        DanmakuSettingView.this.mRegionValueTextView.setText(DanmakuSettingView.this.getResources().getString(R.string.alivc_danmaku_position_half));
                    } else if (i2 == 2) {
                        DanmakuSettingView.this.mRegionValueTextView.setText(DanmakuSettingView.this.getResources().getString(R.string.alivc_danmaku_position_Three_fourths));
                    } else {
                        if (i2 != 3) {
                            return;
                        }
                        DanmakuSettingView.this.mRegionValueTextView.setText(DanmakuSettingView.this.getResources().getString(R.string.alivc_danmaku_position_unlimit));
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (DanmakuSettingView.this.mOnRegionSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnRegionSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (DanmakuSettingView.this.mOnRegionSeekBarChangeListener != null) {
                    DanmakuSettingView.this.mOnRegionSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });
        this.mDefaultTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.more.DanmakuSettingView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DanmakuSettingView.this.mAlphaSeek.setProgress(0);
                DanmakuSettingView.this.mSpeedSeek.setProgress(30);
                DanmakuSettingView.this.mRegionSeek.setProgress(0);
                if (DanmakuSettingView.this.mOnDefaultClickListener != null) {
                    DanmakuSettingView.this.mOnDefaultClickListener.onDefaultClick();
                }
            }
        });
    }

    private void initView() {
        this.mAlphaSeek = (SeekBar) this.view.findViewById(R.id.seek_alpha);
        this.mSpeedSeek = (SeekBar) this.view.findViewById(R.id.seek_speed);
        this.mRegionSeek = (SeekBar) this.view.findViewById(R.id.seek_region);
        this.mDefaultTextView = (TextView) this.view.findViewById(R.id.tv_default);
        this.mAlphaValueTextView = (TextView) this.view.findViewById(R.id.tv_alpha_value);
        this.mSpeedValueTextView = (TextView) this.view.findViewById(R.id.tv_speed_value);
        this.mRegionValueTextView = (TextView) this.view.findViewById(R.id.tv_region_value);
    }

    public void setAlphaProgress(int i2) {
        this.mAlphProgress = i2;
        SeekBar seekBar = this.mAlphaSeek;
        if (seekBar != null) {
            seekBar.setProgress(i2);
        }
    }

    public void setOnAlphaSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnAlphaSeekBarChangeListener = onSeekBarChangeListener;
    }

    public void setOnDefaultListener(OnDefaultClickListener onDefaultClickListener) {
        this.mOnDefaultClickListener = onDefaultClickListener;
    }

    public void setOnRegionSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnRegionSeekBarChangeListener = onSeekBarChangeListener;
    }

    public void setOnSpeedSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSpeedSeekBarChangeListener = onSeekBarChangeListener;
    }

    public void setRegionProgress(int i2) {
        this.mRegionProgress = i2;
        SeekBar seekBar = this.mRegionSeek;
        if (seekBar != null) {
            seekBar.setProgress(i2);
        }
    }

    public void setSpeedProgress(int i2) {
        this.mSpeedProgress = i2;
        SeekBar seekBar = this.mSpeedSeek;
        if (seekBar != null) {
            seekBar.setProgress(i2);
        }
    }

    public DanmakuSettingView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public DanmakuSettingView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context);
    }
}
