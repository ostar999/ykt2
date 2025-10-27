package com.catchpig.mvvm.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public class LSettingItem extends RelativeLayout {
    private boolean mChecked;
    private ImageView mIvLeftIcon;
    private ImageView mIvRightIcon;
    private Drawable mLeftIcon;
    private int mLeftIconSzie;
    private String mLeftText;
    private OnLSettingItemClick mOnLSettingItemClick;
    private Drawable mRightIcon;
    private AppCompatCheckBox mRightIcon_check;
    private SwitchCompat mRightIcon_switch;
    private FrameLayout mRightLayout;
    private int mRightStyle;
    private int mRightTextColor;
    private float mRightTextSize;
    private RelativeLayout mRootLayout;
    private int mTextColor;
    private int mTextSize;
    private TextView mTvLeftText;
    private TextView mTvRightText;
    private View mUnderLine;
    private View mView;
    private int underLineColor;

    public interface OnLSettingItemClick {
        void click(boolean z2);
    }

    public LSettingItem(Context context) {
        this(context, null);
    }

    private void initView(Context context) {
        View viewInflate = View.inflate(context, R.layout.view_setting, this);
        this.mView = viewInflate;
        this.mRootLayout = (RelativeLayout) viewInflate.findViewById(R.id.rootLayout);
        this.mUnderLine = this.mView.findViewById(R.id.underline);
        this.mTvLeftText = (TextView) this.mView.findViewById(R.id.tv_lefttext);
        this.mTvRightText = (TextView) this.mView.findViewById(R.id.tv_righttext);
        this.mIvLeftIcon = (ImageView) this.mView.findViewById(R.id.iv_lefticon);
        this.mIvRightIcon = (ImageView) this.mView.findViewById(R.id.iv_righticon);
        this.mRightLayout = (FrameLayout) this.mView.findViewById(R.id.rightlayout);
        this.mRightIcon_check = (AppCompatCheckBox) this.mView.findViewById(R.id.rightcheck);
        this.mRightIcon_switch = (SwitchCompat) this.mView.findViewById(R.id.rightswitch);
    }

    private void switchRightStyle(int i2) {
        if (i2 == 0) {
            this.mIvRightIcon.setVisibility(0);
            this.mRightIcon_check.setVisibility(8);
            this.mRightIcon_switch.setVisibility(8);
        } else if (i2 == 1) {
            this.mRightLayout.setVisibility(4);
            this.mRightLayout.getLayoutParams().width = 38;
        } else if (i2 == 2) {
            this.mIvRightIcon.setVisibility(8);
            this.mRightIcon_check.setVisibility(0);
            this.mRightIcon_switch.setVisibility(8);
        } else {
            if (i2 != 3) {
                return;
            }
            this.mIvRightIcon.setVisibility(8);
            this.mRightIcon_check.setVisibility(8);
            this.mRightIcon_switch.setVisibility(0);
        }
    }

    public void clickOn() throws Resources.NotFoundException {
        int i2 = this.mRightStyle;
        if (i2 == 0 || i2 == 1) {
            OnLSettingItemClick onLSettingItemClick = this.mOnLSettingItemClick;
            if (onLSettingItemClick != null) {
                onLSettingItemClick.click(this.mChecked);
                return;
            }
            return;
        }
        if (i2 == 2) {
            AppCompatCheckBox appCompatCheckBox = this.mRightIcon_check;
            appCompatCheckBox.setChecked(true ^ appCompatCheckBox.isChecked());
            this.mChecked = this.mRightIcon_check.isChecked();
        } else {
            if (i2 != 3) {
                return;
            }
            SwitchCompat switchCompat = this.mRightIcon_switch;
            switchCompat.setChecked(true ^ switchCompat.isChecked());
            this.mChecked = this.mRightIcon_check.isChecked();
        }
    }

    public void getCustomStyle(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LSettingView);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.LSettingView_leftPadding) {
                int dimension = (int) typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                if (dimension > 0) {
                    this.mRootLayout.setPadding(dimension, 0, 0, 0);
                }
            } else if (index == R.styleable.LSettingView_leftText) {
                String string = typedArrayObtainStyledAttributes.getString(index);
                this.mLeftText = string;
                this.mTvLeftText.setText(string);
            } else if (index == R.styleable.LSettingView_leftIcon) {
                Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(index);
                this.mLeftIcon = drawable;
                if (drawable != null) {
                    this.mIvLeftIcon.setImageDrawable(drawable);
                    this.mIvLeftIcon.setVisibility(0);
                }
            } else if (index == R.styleable.LSettingView_leftIconSize) {
                this.mLeftIconSzie = (int) typedArrayObtainStyledAttributes.getDimension(index, 16.0f);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mIvLeftIcon.getLayoutParams();
                int i3 = this.mLeftIconSzie;
                layoutParams.width = i3;
                layoutParams.height = i3;
                this.mIvLeftIcon.setLayoutParams(layoutParams);
            } else if (index == R.styleable.LSettingView_leftTextMarginLeft) {
                int dimension2 = (int) typedArrayObtainStyledAttributes.getDimension(index, 8.0f);
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mTvLeftText.getLayoutParams();
                layoutParams2.leftMargin = dimension2;
                this.mTvLeftText.setLayoutParams(layoutParams2);
            } else if (index == R.styleable.LSettingView_rightIcon) {
                Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(index);
                this.mRightIcon = drawable2;
                this.mIvRightIcon.setImageDrawable(drawable2);
            } else if (index == R.styleable.LSettingView_LtextSize) {
                this.mTvLeftText.setTextSize(typedArrayObtainStyledAttributes.getFloat(index, 16.0f));
            } else if (index == R.styleable.LSettingView_LtextColor) {
                int color = typedArrayObtainStyledAttributes.getColor(index, -3355444);
                this.mTextColor = color;
                this.mTvLeftText.setTextColor(color);
            } else if (index == R.styleable.LSettingView_rightStyle) {
                this.mRightStyle = typedArrayObtainStyledAttributes.getInt(index, 0);
            } else if (index == R.styleable.LSettingView_isShowUnderLine) {
                if (!typedArrayObtainStyledAttributes.getBoolean(index, true)) {
                    this.mUnderLine.setVisibility(8);
                }
            } else if (index == R.styleable.LSettingView_underLineColor) {
                int color2 = typedArrayObtainStyledAttributes.getColor(index, -7829368);
                this.underLineColor = color2;
                this.mUnderLine.setBackgroundColor(color2);
            } else if (index == R.styleable.LSettingView_underLineLeftMargin) {
                int dimension3 = (int) typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                if (dimension3 != 0) {
                    RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mUnderLine.getLayoutParams();
                    layoutParams3.leftMargin = dimension3;
                    this.mUnderLine.setLayoutParams(layoutParams3);
                }
            } else if (index == R.styleable.LSettingView_underLineRightMargin) {
                int dimension4 = (int) typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
                if (dimension4 != 0) {
                    RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.mUnderLine.getLayoutParams();
                    layoutParams4.rightMargin = dimension4;
                    this.mUnderLine.setLayoutParams(layoutParams4);
                }
            } else if (index == R.styleable.LSettingView_isShowRightText) {
                if (typedArrayObtainStyledAttributes.getBoolean(index, false)) {
                    this.mTvRightText.setVisibility(0);
                }
            } else if (index == R.styleable.LSettingView_rightText) {
                this.mTvRightText.setText(typedArrayObtainStyledAttributes.getString(index));
            } else if (index == R.styleable.LSettingView_rightTextSize) {
                float f2 = typedArrayObtainStyledAttributes.getFloat(index, 14.0f);
                this.mRightTextSize = f2;
                this.mTvRightText.setTextSize(f2);
            } else if (index == R.styleable.LSettingView_rightTextColor) {
                int color3 = typedArrayObtainStyledAttributes.getColor(index, -7829368);
                this.mRightTextColor = color3;
                this.mTvRightText.setTextColor(color3);
            } else if (index == R.styleable.LSettingView_rightTextPaddingRight) {
                this.mTvRightText.setPadding(0, 0, (int) typedArrayObtainStyledAttributes.getDimension(index, 6.0f), 0);
            } else if (index == R.styleable.LSettingView_rightLayoutMarginRight) {
                int dimension5 = (int) typedArrayObtainStyledAttributes.getDimension(index, 8.0f);
                RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.mRightLayout.getLayoutParams();
                layoutParams5.rightMargin = dimension5;
                this.mRightLayout.setLayoutParams(layoutParams5);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public RelativeLayout getmRootLayout() {
        return this.mRootLayout;
    }

    public void setLeftText(String str) {
        this.mTvLeftText.setText(str);
    }

    public void setRightText(String str) {
        this.mTvRightText.setText(str);
    }

    public void setmOnLSettingItemClick(OnLSettingItemClick onLSettingItemClick) {
        this.mOnLSettingItemClick = onLSettingItemClick;
    }

    public LSettingItem(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LSettingItem(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mRightStyle = 0;
        initView(context);
        getCustomStyle(context, attributeSet);
        switchRightStyle(this.mRightStyle);
        this.mRootLayout.setOnClickListener(new View.OnClickListener() { // from class: com.catchpig.mvvm.widget.LSettingItem.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws Resources.NotFoundException {
                LSettingItem.this.clickOn();
            }
        });
        this.mRightIcon_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.catchpig.mvvm.widget.LSettingItem.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                if (LSettingItem.this.mOnLSettingItemClick != null) {
                    LSettingItem.this.mOnLSettingItemClick.click(z2);
                }
            }
        });
        this.mRightIcon_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.catchpig.mvvm.widget.LSettingItem.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                if (LSettingItem.this.mOnLSettingItemClick != null) {
                    LSettingItem.this.mOnLSettingItemClick.click(z2);
                }
            }
        });
    }
}
