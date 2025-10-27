package com.psychiatrygarden.widget.arcseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.psychiatrygarden.widget.arcseekbar.ArcSeekBar;
import com.yikaobang.yixue.R;
import java.text.DecimalFormat;

/* loaded from: classes6.dex */
public class ArcSeekBarX extends LinearLayout {
    private ArcSeekBar arcSeekBar;
    private int height;
    private LineArcSeekBar lineArcSeekBar;
    private LineArcSeekBar lineArcSeekBar2;
    private ImageView mImgTrend;
    private LinearLayout mLyTrend;
    private TextView textView;
    private TextView tvFraction;
    private int width;

    public ArcSeekBarX(Context context) {
        this(context, null);
    }

    private DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBarX);
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_seekbar, (ViewGroup) this, true);
        this.lineArcSeekBar = (LineArcSeekBar) viewInflate.findViewById(R.id.lineArcSeekBar);
        this.arcSeekBar = (ArcSeekBar) viewInflate.findViewById(R.id.arcSeekBar);
        this.lineArcSeekBar2 = (LineArcSeekBar) viewInflate.findViewById(R.id.lineArcSeekBar2);
        this.tvFraction = (TextView) viewInflate.findViewById(R.id.fraction);
        this.mImgTrend = (ImageView) viewInflate.findViewById(R.id.img_trend);
        this.mLyTrend = (LinearLayout) viewInflate.findViewById(R.id.ly_trend);
        this.textView = (TextView) viewInflate.findViewById(R.id.textView);
        initListener();
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == 1) {
                this.width = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == 0) {
                this.height = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
            }
        }
        initView();
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initLayoutParams() {
        int i2 = this.width;
        if (i2 == -1 || i2 == -2) {
            return;
        }
        if (this.lineArcSeekBar.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
            ViewGroup.LayoutParams layoutParams = this.lineArcSeekBar.getLayoutParams();
            layoutParams.width = this.width;
            layoutParams.height = this.height;
            this.lineArcSeekBar.setLayoutParams(layoutParams);
        }
        if (this.arcSeekBar.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
            int iApplyDimension = (int) TypedValue.applyDimension(1, 50.0f, getDisplayMetrics());
            ViewGroup.LayoutParams layoutParams2 = this.arcSeekBar.getLayoutParams();
            layoutParams2.width = this.width - iApplyDimension;
            layoutParams2.height = this.height - iApplyDimension;
            this.arcSeekBar.setLayoutParams(layoutParams2);
        }
        if (this.lineArcSeekBar2.getLayoutParams() instanceof ConstraintLayout.LayoutParams) {
            int iApplyDimension2 = (int) TypedValue.applyDimension(1, 150.0f, getDisplayMetrics());
            ViewGroup.LayoutParams layoutParams3 = this.lineArcSeekBar2.getLayoutParams();
            layoutParams3.width = this.width - iApplyDimension2;
            layoutParams3.height = this.height - iApplyDimension2;
            this.lineArcSeekBar2.setLayoutParams(layoutParams3);
        }
    }

    private void initListener() {
        this.arcSeekBar.setOnChangeListener(new ArcSeekBar.OnChangeListener() { // from class: com.psychiatrygarden.widget.arcseekbar.ArcSeekBarX.1
            @Override // com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.OnChangeListener
            public void onProgressChanged(float progress, float max, boolean fromUser) {
                ArcSeekBarX.this.textView.setText(new DecimalFormat("#.##").format(progress));
            }

            @Override // com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.OnChangeListener
            public void onSingleTapUp() {
            }

            @Override // com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.OnChangeListener
            public void onStartTrackingTouch(boolean isCanDrag) {
            }

            @Override // com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.OnChangeListener
            public void onStopTrackingTouch(boolean isCanDrag) {
            }
        });
    }

    private void initView() {
        initLayoutParams();
    }

    public void setFraction(int fraction, String type) {
        this.tvFraction.setText(String.valueOf(fraction));
        if (type.equals("1")) {
            this.mLyTrend.setBackgroundResource(R.drawable.shape_btn_light_red_radius_8);
            this.mImgTrend.setImageResource(R.drawable.ic_trend_up);
        } else if (!type.equals("2")) {
            this.mLyTrend.setVisibility(8);
        } else {
            this.mLyTrend.setBackgroundResource(R.drawable.shape_tend_down_radius_8);
            this.mImgTrend.setImageResource(R.drawable.ic_trend_down);
        }
    }

    public void setInsideProgress(int progress) {
        this.lineArcSeekBar2.setProgress(progress);
    }

    public void setInsideText(String text) {
        this.lineArcSeekBar2.setArcText(text);
    }

    public void setNormalColor(int noNormalColor) {
        this.lineArcSeekBar.setNormalColor(noNormalColor);
        this.lineArcSeekBar2.setNormalColor(noNormalColor);
        this.arcSeekBar.setNormalColor(noNormalColor);
    }

    public void setOutsideProgress(int progress) {
        this.lineArcSeekBar.setProgress(progress);
    }

    public void setOutsideText(String text) {
        this.lineArcSeekBar.setArcText(text);
    }

    public void setProgress(int progress) {
        this.arcSeekBar.setProgress(progress);
    }

    public void setProgressColor(int... colors) {
        this.arcSeekBar.setProgressColor(colors);
        this.lineArcSeekBar2.setProgressColor(colors);
        this.lineArcSeekBar.setProgressColor(colors);
    }

    public void setProgressMax(int max) {
        this.arcSeekBar.setMax(max);
        this.lineArcSeekBar2.setMax(max);
        this.lineArcSeekBar.setMax(max);
    }

    public ArcSeekBarX(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSeekBarX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
}
