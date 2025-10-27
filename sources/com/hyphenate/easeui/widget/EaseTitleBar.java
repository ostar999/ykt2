package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.hyphenate.easeui.R;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SdkConstant;

/* loaded from: classes4.dex */
public class EaseTitleBar extends RelativeLayout implements View.OnClickListener {
    private Context context;
    protected ImageView leftImage;
    protected RelativeLayout leftLayout;
    private int mArrowColor;
    private int mArrowColorId;
    private OnBackPressListener mBackPressListener;
    private boolean mDisplayHomeAsUpEnabled;
    private int mHeight;
    private OnRightClickListener mOnRightClickListener;
    private int mTitleTextColor;
    private int mWidth;
    protected ImageView rightImage;
    protected RelativeLayout rightLayout;
    protected RelativeLayout titleLayout;
    private TextView titleMenu;
    protected TextView titleView;
    private Toolbar toolbar;

    public interface OnBackPressListener {
        void onBackPress(View view);
    }

    public interface OnRightClickListener {
        void onRightClick(View view);
    }

    public enum TitlePosition {
        Center,
        Left,
        Right
    }

    public EaseTitleBar(Context context) {
        this(context, null);
    }

    public static float dip2px(Context context, float f2) {
        return TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics());
    }

    private void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.ease_widget_title_bar, this);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        this.leftImage = (ImageView) findViewById(R.id.left_image);
        this.rightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        this.rightImage = (ImageView) findViewById(R.id.right_image);
        this.titleView = (TextView) findViewById(R.id.title);
        this.titleLayout = (RelativeLayout) findViewById(R.id.root);
        this.titleMenu = (TextView) findViewById(R.id.right_menu);
        parseStyle(context, attributeSet);
        initToolbar();
    }

    private void initLayout() {
        ViewGroup.LayoutParams layoutParams = this.titleLayout.getLayoutParams();
        layoutParams.height = this.mHeight;
        layoutParams.width = this.mWidth;
        requestLayout();
    }

    private void initToolbar() {
        this.rightLayout.setOnClickListener(this);
        if (this.leftImage.getDrawable() != null) {
            this.leftImage.setVisibility(this.mDisplayHomeAsUpEnabled ? 0 : 8);
            this.leftLayout.setOnClickListener(this);
            return;
        }
        if (getContext() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
            appCompatActivity.setSupportActionBar(this.toolbar);
            if (appCompatActivity.getSupportActionBar() != null) {
                appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(this.mDisplayHomeAsUpEnabled);
                appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            this.toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.widget.EaseTitleBar.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (EaseTitleBar.this.mBackPressListener != null) {
                        EaseTitleBar.this.mBackPressListener.onBackPress(view);
                    }
                }
            });
            int i2 = this.mArrowColorId;
            if (i2 != -1) {
                setToolbarCustomColor(i2);
            } else {
                setToolbarCustomColorDefault(this.mArrowColor);
            }
        }
    }

    private void parseStyle(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SdkConstant.UMENG_ALIS, 0);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.EaseTitleBar);
            int i2 = R.styleable.EaseTitleBar_titleBarTitle;
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(i2, -1);
            if (resourceId != -1) {
                this.titleView.setText(resourceId);
            } else {
                this.titleView.setText(typedArrayObtainStyledAttributes.getString(i2));
            }
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseTitleBar_titleBarLeftImage);
            if (drawable != null) {
                if (sharedPreferences.getInt(CommonParameter.SkinMananer, 0) == 0) {
                    drawable.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                } else {
                    drawable.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
                }
                this.leftImage.setImageDrawable(drawable);
            }
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseTitleBar_titleBarRightImage);
            if (drawable2 != null) {
                if (sharedPreferences.getInt(CommonParameter.SkinMananer, 0) == 0) {
                    drawable2.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
                } else {
                    drawable2.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
                }
                this.rightImage.setImageDrawable(drawable2);
            }
            int i3 = R.styleable.EaseTitleBar_titleBarArrowColor;
            this.mArrowColorId = typedArrayObtainStyledAttributes.getResourceId(i3, -1);
            this.mArrowColor = typedArrayObtainStyledAttributes.getColor(i3, -16777216);
            Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.EaseTitleBar_titleBarMenuResource);
            if (drawable3 != null) {
                this.toolbar.setOverflowIcon(drawable3);
            }
            int i4 = R.styleable.EaseTitleBar_titleBarRightTitle;
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(i4, -1);
            if (resourceId2 != -1) {
                this.titleMenu.setText(resourceId2);
            } else {
                this.titleMenu.setText(typedArrayObtainStyledAttributes.getString(i4));
            }
            this.rightLayout.setVisibility(typedArrayObtainStyledAttributes.getBoolean(R.styleable.EaseTitleBar_titleBarRightVisible, false) ? 0 : 8);
            this.mDisplayHomeAsUpEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.EaseTitleBar_titleBarDisplayHomeAsUpEnabled, true);
            setTitlePosition(typedArrayObtainStyledAttributes.getInteger(R.styleable.EaseTitleBar_titleBarTitlePosition, 0));
            this.titleView.setTextSize(0, typedArrayObtainStyledAttributes.getDimension(R.styleable.EaseTitleBar_titleBarTitleTextSize, (int) sp2px(getContext(), 18.0f)));
            int i5 = R.styleable.EaseTitleBar_titleBarTitleTextColor;
            int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(i5, -1);
            if (resourceId3 != -1) {
                this.mTitleTextColor = ContextCompat.getColor(getContext(), resourceId3);
            } else {
                this.mTitleTextColor = typedArrayObtainStyledAttributes.getColor(i5, ContextCompat.getColor(getContext(), R.color.em_toolbar_color_title));
            }
            this.titleView.setTextColor(this.mTitleTextColor);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void setTitlePosition(int i2) {
        ViewGroup.LayoutParams layoutParams = this.titleView.getLayoutParams();
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            if (i2 == 0) {
                ((RelativeLayout.LayoutParams) layoutParams).addRule(13);
                return;
            }
            if (i2 == 1) {
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
                layoutParams2.addRule(9);
                layoutParams2.addRule(15);
                layoutParams2.addRule(1, this.leftLayout.getId());
                return;
            }
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) layoutParams;
            layoutParams3.addRule(11);
            layoutParams3.addRule(15);
            layoutParams3.addRule(0, this.rightLayout.getId());
            layoutParams3.setMargins(0, 0, (int) dip2px(getContext(), 60.0f), 0);
        }
    }

    public static float sp2px(Context context, float f2) {
        return TypedValue.applyDimension(2, f2, context.getResources().getDisplayMetrics());
    }

    public ImageView getLeftImage() {
        return this.leftImage;
    }

    public RelativeLayout getLeftLayout() {
        return this.leftLayout;
    }

    public ImageView getRightImage() {
        return this.rightImage;
    }

    public RelativeLayout getRightLayout() {
        return this.rightLayout;
    }

    public TextView getRightText() {
        return this.titleMenu;
    }

    public TextView getTitle() {
        return this.titleView;
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnRightClickListener onRightClickListener;
        if (view.getId() == R.id.left_layout) {
            OnBackPressListener onBackPressListener = this.mBackPressListener;
            if (onBackPressListener != null) {
                onBackPressListener.onBackPress(view);
                return;
            }
            return;
        }
        if (view.getId() != R.id.right_layout || (onRightClickListener = this.mOnRightClickListener) == null) {
            return;
        }
        onRightClickListener.onRightClick(view);
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.mWidth = i2;
        this.mHeight = i3;
        initLayout();
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        this.titleLayout.setBackgroundColor(i2);
    }

    public void setLeftImageResource(int i2) {
        this.leftImage.setImageResource(i2);
    }

    public void setLeftLayoutClickListener(View.OnClickListener onClickListener) {
        this.leftLayout.setOnClickListener(onClickListener);
    }

    public void setLeftLayoutVisibility(int i2) {
        this.leftLayout.setVisibility(i2);
    }

    public void setOnBackPressListener(OnBackPressListener onBackPressListener) {
        this.mBackPressListener = onBackPressListener;
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.mOnRightClickListener = onRightClickListener;
    }

    public void setRightImageResource(int i2) {
        this.rightImage.setImageResource(i2);
        this.rightLayout.setVisibility(0);
    }

    public void setRightLayoutClickListener(View.OnClickListener onClickListener) {
        this.rightLayout.setOnClickListener(onClickListener);
    }

    public void setRightLayoutVisibility(int i2) {
        this.rightLayout.setVisibility(i2);
    }

    public void setRightTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.titleMenu.setText(str);
        this.rightLayout.setVisibility(0);
    }

    public void setRightTitleResource(@StringRes int i2) {
        this.titleMenu.setText(getResources().getString(i2));
        this.rightLayout.setVisibility(0);
    }

    public void setTitle(String str) {
        this.titleView.setText(str);
    }

    public void setToolbarCustomColor(@ColorRes int i2) {
        setToolbarCustomColorDefault(ContextCompat.getColor(getContext(), i2));
    }

    public void setToolbarCustomColorDefault(@ColorInt int i2) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        if (drawable != null) {
            drawable.setColorFilter(i2, PorterDuff.Mode.SRC_ATOP);
            if (!(getContext() instanceof AppCompatActivity) || ((AppCompatActivity) getContext()).getSupportActionBar() == null) {
                return;
            }
            ((AppCompatActivity) getContext()).getSupportActionBar().setHomeAsUpIndicator(drawable);
        }
    }

    public EaseTitleBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseTitleBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.context = context;
        init(context, attributeSet);
    }

    public void setTitlePosition(TitlePosition titlePosition) {
        int i2;
        if (titlePosition == TitlePosition.Center) {
            i2 = 0;
        } else {
            i2 = titlePosition == TitlePosition.Left ? 1 : 2;
        }
        setTitlePosition(i2);
    }
}
