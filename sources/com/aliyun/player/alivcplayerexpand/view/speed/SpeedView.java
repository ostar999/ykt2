package com.aliyun.player.alivcplayerexpand.view.speed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.theme.ITheme;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;

/* loaded from: classes2.dex */
public class SpeedView extends RelativeLayout implements ITheme {
    private static final String TAG = "SpeedView";
    private boolean animEnd;
    private Animation hideAnim;
    private View.OnClickListener mClickListener;
    private View mMainSpeedView;
    private RadioButton mNormalBtn;
    private OnSpeedClickListener mOnSpeedClickListener;
    private RadioButton mOneHalfTimeBtn;
    private RadioButton mOneQrtTimeBtn;
    private AliyunScreenMode mScreenMode;
    private boolean mSpeedChanged;
    private int mSpeedDrawable;
    private int mSpeedTextColor;
    private TextView mSpeedTip;
    private SpeedValue mSpeedValue;
    private RadioButton mTwoTimeBtn;
    private Animation showAnim;

    public class MyLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private AliyunScreenMode lastLayoutMode;

        private MyLayoutListener() {
            this.lastLayoutMode = null;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (SpeedView.this.mMainSpeedView.getVisibility() != 0 || this.lastLayoutMode == SpeedView.this.mScreenMode) {
                return;
            }
            SpeedView speedView = SpeedView.this;
            speedView.setScreenMode(speedView.mScreenMode);
            this.lastLayoutMode = SpeedView.this.mScreenMode;
        }
    }

    public interface OnSpeedClickListener {
        void onHide();

        void onSpeedClick(SpeedValue speedValue);
    }

    public enum SpeedValue {
        Normal,
        OneQuartern,
        OneHalf,
        Twice
    }

    public SpeedView(Context context) {
        super(context);
        this.animEnd = true;
        this.mOnSpeedClickListener = null;
        this.mSpeedChanged = false;
        this.mSpeedDrawable = R.drawable.alivc_speed_dot_blue;
        this.mSpeedTextColor = R.color.alivc_blue;
        this.mClickListener = new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SpeedView.this.mOnSpeedClickListener == null) {
                    return;
                }
                if (view == SpeedView.this.mNormalBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.Normal);
                    return;
                }
                if (view == SpeedView.this.mOneQrtTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.OneQuartern);
                } else if (view == SpeedView.this.mOneHalfTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.OneHalf);
                } else if (view == SpeedView.this.mTwoTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.Twice);
                }
            }
        };
        init();
    }

    private void hide() {
        if (this.mMainSpeedView.getVisibility() == 0) {
            this.mMainSpeedView.startAnimation(this.hideAnim);
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_speed, (ViewGroup) this, true);
        View viewFindViewById = findViewById(R.id.speed_view);
        this.mMainSpeedView = viewFindViewById;
        viewFindViewById.setVisibility(4);
        this.mOneQrtTimeBtn = (RadioButton) findViewById(R.id.one_quartern);
        this.mNormalBtn = (RadioButton) findViewById(R.id.normal);
        this.mOneHalfTimeBtn = (RadioButton) findViewById(R.id.one_half);
        this.mTwoTimeBtn = (RadioButton) findViewById(R.id.two);
        TextView textView = (TextView) findViewById(R.id.speed_tip);
        this.mSpeedTip = textView;
        textView.setVisibility(4);
        this.mOneQrtTimeBtn.setOnClickListener(this.mClickListener);
        this.mNormalBtn.setOnClickListener(this.mClickListener);
        this.mOneHalfTimeBtn.setOnClickListener(this.mClickListener);
        this.mTwoTimeBtn.setOnClickListener(this.mClickListener);
        this.showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_speed_show);
        this.hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_speed_hide);
        this.showAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SpeedView.this.animEnd = true;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                SpeedView.this.animEnd = false;
                SpeedView.this.mMainSpeedView.setVisibility(0);
            }
        });
        this.hideAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SpeedView.this.mMainSpeedView.setVisibility(4);
                if (SpeedView.this.mOnSpeedClickListener != null) {
                    SpeedView.this.mOnSpeedClickListener.onHide();
                }
                if (SpeedView.this.mSpeedChanged) {
                    SpeedView.this.mSpeedTip.setText(SpeedView.this.getContext().getString(R.string.alivc_speed_tips, SpeedView.this.mSpeedValue == SpeedValue.OneQuartern ? SpeedView.this.getResources().getString(R.string.alivc_speed_optf_times) : SpeedView.this.mSpeedValue == SpeedValue.Normal ? SpeedView.this.getResources().getString(R.string.alivc_speed_one_times) : SpeedView.this.mSpeedValue == SpeedValue.OneHalf ? SpeedView.this.getResources().getString(R.string.alivc_speed_opt_times) : SpeedView.this.mSpeedValue == SpeedValue.Twice ? SpeedView.this.getResources().getString(R.string.alivc_speed_twice_times) : ""));
                    SpeedView.this.mSpeedTip.setVisibility(0);
                    SpeedView.this.mSpeedTip.postDelayed(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SpeedView.this.mSpeedTip.setVisibility(4);
                        }
                    }, 1000L);
                }
                SpeedView.this.animEnd = true;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                SpeedView.this.animEnd = false;
            }
        });
        setSpeed(SpeedValue.Normal);
        getViewTreeObserver().addOnGlobalLayoutListener(new MyLayoutListener());
    }

    private void setRadioButtonTheme(RadioButton radioButton) {
        if (radioButton.isChecked()) {
            radioButton.setCompoundDrawablesWithIntrinsicBounds(0, this.mSpeedDrawable, 0, 0);
            radioButton.setTextColor(ContextCompat.getColor(getContext(), this.mSpeedTextColor));
        } else {
            radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            radioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.alivc_common_font_white_light));
        }
    }

    private void updateBtnTheme() {
        setRadioButtonTheme(this.mNormalBtn);
        setRadioButtonTheme(this.mOneQrtTimeBtn);
        setRadioButtonTheme(this.mOneHalfTimeBtn);
        setRadioButtonTheme(this.mTwoTimeBtn);
    }

    private void updateSpeedCheck() {
        this.mOneQrtTimeBtn.setChecked(this.mSpeedValue == SpeedValue.OneQuartern);
        this.mNormalBtn.setChecked(this.mSpeedValue == SpeedValue.Normal);
        this.mOneHalfTimeBtn.setChecked(this.mSpeedValue == SpeedValue.OneHalf);
        this.mTwoTimeBtn.setChecked(this.mSpeedValue == SpeedValue.Twice);
        updateBtnTheme();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mMainSpeedView.getVisibility() != 0 || !this.animEnd) {
            return super.onTouchEvent(motionEvent);
        }
        hide();
        return true;
    }

    public void setOnSpeedClickListener(OnSpeedClickListener onSpeedClickListener) {
        this.mOnSpeedClickListener = onSpeedClickListener;
    }

    public void setScreenMode(AliyunScreenMode aliyunScreenMode) {
        ViewGroup.LayoutParams layoutParams = this.mMainSpeedView.getLayoutParams();
        if (aliyunScreenMode == AliyunScreenMode.Small) {
            layoutParams.width = getWidth();
            layoutParams.height = getHeight();
        } else if (aliyunScreenMode == AliyunScreenMode.Full) {
            if (((AliyunVodPlayerView) getParent()).getLockPortraitMode() == null) {
                layoutParams.width = getWidth() / 2;
            } else {
                layoutParams.width = getWidth();
            }
            layoutParams.height = getHeight();
        }
        this.mScreenMode = aliyunScreenMode;
        this.mMainSpeedView.setLayoutParams(layoutParams);
    }

    public void setSpeed(SpeedValue speedValue) {
        if (speedValue == null) {
            return;
        }
        if (this.mSpeedValue != speedValue) {
            this.mSpeedValue = speedValue;
            this.mSpeedChanged = true;
            updateSpeedCheck();
        } else {
            this.mSpeedChanged = false;
        }
        hide();
    }

    @Override // com.aliyun.player.alivcplayerexpand.theme.ITheme
    public void setTheme(Theme theme) {
        int i2 = R.drawable.alivc_speed_dot_blue;
        this.mSpeedDrawable = i2;
        int i3 = R.color.alivc_blue;
        this.mSpeedTextColor = i3;
        if (theme == Theme.Blue) {
            this.mSpeedDrawable = i2;
            this.mSpeedTextColor = i3;
        } else if (theme == Theme.Green) {
            this.mSpeedDrawable = R.drawable.alivc_speed_dot_green;
            this.mSpeedTextColor = R.color.alivc_green;
        } else if (theme == Theme.Orange) {
            this.mSpeedDrawable = R.drawable.alivc_speed_dot_orange;
            this.mSpeedTextColor = R.color.alivc_orange;
        } else if (theme == Theme.Red) {
            this.mSpeedDrawable = R.drawable.alivc_speed_dot_red;
            this.mSpeedTextColor = R.color.alivc_red;
        }
        updateBtnTheme();
    }

    public void show(AliyunScreenMode aliyunScreenMode) {
        setScreenMode(aliyunScreenMode);
        this.mMainSpeedView.startAnimation(this.showAnim);
    }

    public SpeedView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animEnd = true;
        this.mOnSpeedClickListener = null;
        this.mSpeedChanged = false;
        this.mSpeedDrawable = R.drawable.alivc_speed_dot_blue;
        this.mSpeedTextColor = R.color.alivc_blue;
        this.mClickListener = new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SpeedView.this.mOnSpeedClickListener == null) {
                    return;
                }
                if (view == SpeedView.this.mNormalBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.Normal);
                    return;
                }
                if (view == SpeedView.this.mOneQrtTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.OneQuartern);
                } else if (view == SpeedView.this.mOneHalfTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.OneHalf);
                } else if (view == SpeedView.this.mTwoTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.Twice);
                }
            }
        };
        init();
    }

    public SpeedView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.animEnd = true;
        this.mOnSpeedClickListener = null;
        this.mSpeedChanged = false;
        this.mSpeedDrawable = R.drawable.alivc_speed_dot_blue;
        this.mSpeedTextColor = R.color.alivc_blue;
        this.mClickListener = new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.speed.SpeedView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SpeedView.this.mOnSpeedClickListener == null) {
                    return;
                }
                if (view == SpeedView.this.mNormalBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.Normal);
                    return;
                }
                if (view == SpeedView.this.mOneQrtTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.OneQuartern);
                } else if (view == SpeedView.this.mOneHalfTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.OneHalf);
                } else if (view == SpeedView.this.mTwoTimeBtn) {
                    SpeedView.this.mOnSpeedClickListener.onSpeedClick(SpeedValue.Twice);
                }
            }
        };
        init();
    }
}
