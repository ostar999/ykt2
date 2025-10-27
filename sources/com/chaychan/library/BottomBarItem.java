package com.chaychan.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import java.util.Locale;

/* loaded from: classes3.dex */
public class BottomBarItem extends LinearLayout {
    private Context context;
    private int iconHeight;
    private int iconWidth;
    private int itemPadding;
    private String lottieJson;
    private ImageView mImageView;
    private LottieAnimationView mLottieView;
    private TextView mTextView;
    private TextView mTvMsg;
    private TextView mTvNotify;
    private TextView mTvUnread;
    private int marginTop;
    private Drawable msgTextBg;
    private int msgTextColor;
    private int msgTextSize;
    private Drawable normalIcon;
    private Drawable notifyPointBg;
    private boolean openTouchBg;
    private Drawable selectedIcon;
    private String title;
    private int titleNormalColor;
    private int titleSelectedColor;
    private boolean titleTextBold;
    private int titleTextSize;
    private Drawable touchDrawable;
    private int unreadNumThreshold;
    private Drawable unreadTextBg;
    private int unreadTextColor;
    private int unreadTextSize;
    private boolean useLottie;

    public static final class Builder {
        private Context context;
        private int iconHeight;
        private int iconWidth;
        private int itemPadding;
        private String lottieJson;
        private int marginTop;
        private Drawable msgTextBg;
        private int msgTextColor;
        private int msgTextSize;
        private Drawable normalIcon;
        private Drawable notifyPointBg;
        private boolean openTouchBg;
        private Drawable selectedIcon;
        private String title;
        private int titleTextSize;
        private Drawable touchDrawable;
        private int unreadNumThreshold;
        private Drawable unreadTextBg;
        private int unreadTextColor;
        private int unreadTextSize;
        private boolean titleTextBold = false;
        private int titleNormalColor = getColor(R.color.bbl_999999);
        private int titleSelectedColor = getColor(R.color.bbl_ff0000);

        public Builder(Context context) {
            this.context = context;
            this.titleTextSize = UIUtils.sp2px(context, 12.0f);
            this.unreadTextSize = UIUtils.sp2px(context, 10.0f);
            this.msgTextSize = UIUtils.sp2px(context, 6.0f);
            int i2 = R.color.white;
            this.unreadTextColor = getColor(i2);
            this.unreadNumThreshold = 99;
            this.msgTextColor = getColor(i2);
        }

        private int getColor(int i2) {
            return this.context.getResources().getColor(i2);
        }

        public BottomBarItem create(Drawable drawable, Drawable drawable2, String str) {
            this.normalIcon = drawable;
            this.selectedIcon = drawable2;
            this.title = str;
            return new BottomBarItem(this.context).create(this);
        }

        public Builder iconHeight(int i2) {
            this.iconHeight = i2;
            return this;
        }

        public Builder iconWidth(int i2) {
            this.iconWidth = i2;
            return this;
        }

        public Builder itemPadding(int i2) {
            this.itemPadding = i2;
            return this;
        }

        public Builder lottieJson(String str) {
            this.lottieJson = str;
            return this;
        }

        public Builder marginTop(int i2) {
            this.marginTop = i2;
            return this;
        }

        public Builder msgTextBg(Drawable drawable) {
            this.msgTextBg = drawable;
            return this;
        }

        public Builder msgTextColor(int i2) {
            this.msgTextColor = getColor(i2);
            return this;
        }

        public Builder msgTextSize(int i2) {
            this.msgTextSize = UIUtils.sp2px(this.context, i2);
            return this;
        }

        public Builder normalIcon(Drawable drawable) {
            this.normalIcon = drawable;
            return this;
        }

        public Builder notifyPointBg(Drawable drawable) {
            this.notifyPointBg = drawable;
            return this;
        }

        public Builder openTouchBg(boolean z2) {
            this.openTouchBg = z2;
            return this;
        }

        public Builder selectedIcon(Drawable drawable) {
            this.selectedIcon = drawable;
            return this;
        }

        public Builder title(int i2) {
            this.title = this.context.getString(i2);
            return this;
        }

        public Builder titleNormalColor(int i2) {
            this.titleNormalColor = getColor(i2);
            return this;
        }

        public Builder titleSelectedColor(int i2) {
            this.titleSelectedColor = getColor(i2);
            return this;
        }

        public Builder titleTextBold(boolean z2) {
            this.titleTextBold = z2;
            return this;
        }

        public Builder titleTextSize(int i2) {
            this.titleTextSize = UIUtils.sp2px(this.context, i2);
            return this;
        }

        public Builder touchDrawable(Drawable drawable) {
            this.touchDrawable = drawable;
            return this;
        }

        public Builder unreadNumThreshold(int i2) {
            this.unreadNumThreshold = i2;
            return this;
        }

        public Builder unreadTextBg(Drawable drawable) {
            this.unreadTextBg = drawable;
            return this;
        }

        public Builder unreadTextColor(int i2) {
            this.unreadTextColor = getColor(i2);
            return this;
        }

        public Builder unreadTextSize(int i2) {
            this.unreadTextSize = UIUtils.sp2px(this.context, i2);
            return this;
        }

        public Builder title(String str) {
            this.title = str;
            return this;
        }

        public BottomBarItem create(int i2, int i3, String str) {
            return create(UIUtils.getDrawable(this.context, i2), UIUtils.getDrawable(this.context, i3), str);
        }
    }

    public BottomBarItem(Context context) {
        super(context);
        this.titleTextBold = false;
        this.titleTextSize = 12;
        this.marginTop = 0;
        this.openTouchBg = false;
        this.unreadTextSize = 10;
        this.unreadNumThreshold = 99;
        this.msgTextSize = 6;
    }

    private void checkValues() {
        boolean z2 = this.useLottie;
        if (!z2 && this.normalIcon == null) {
            throw new IllegalStateException("You have not set the normal icon");
        }
        if (!z2 && this.selectedIcon == null) {
            throw new IllegalStateException("You have not set the selected icon");
        }
        if (this.openTouchBg && this.touchDrawable == null) {
            throw new IllegalStateException("Touch effect is turned on, but touchDrawable is not specified");
        }
        if (this.unreadTextBg == null) {
            this.unreadTextBg = getResources().getDrawable(R.drawable.shape_unread);
        }
        if (this.msgTextBg == null) {
            this.msgTextBg = getResources().getDrawable(R.drawable.shape_msg);
        }
        if (this.notifyPointBg == null) {
            this.notifyPointBg = getResources().getDrawable(R.drawable.shape_notify_point);
        }
    }

    private void init() {
        int i2;
        setOrientation(1);
        setGravity(17);
        View viewInitView = initView();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mImageView.getLayoutParams();
        int i3 = this.iconWidth;
        if (i3 != 0 && (i2 = this.iconHeight) != 0) {
            layoutParams.width = i3;
            layoutParams.height = i2;
        }
        if (this.useLottie) {
            this.mLottieView.setLayoutParams(layoutParams);
            this.mLottieView.setAnimation(this.lottieJson);
            this.mLottieView.setRepeatCount(0);
        } else {
            this.mImageView.setImageDrawable(this.normalIcon);
            this.mImageView.setLayoutParams(layoutParams);
        }
        this.mTextView.setTextSize(0, this.titleTextSize);
        this.mTextView.getPaint().setFakeBoldText(this.titleTextBold);
        this.mTvUnread.setTextSize(0, this.unreadTextSize);
        this.mTvUnread.setTextColor(this.unreadTextColor);
        this.mTvUnread.setBackground(this.unreadTextBg);
        this.mTvMsg.setTextSize(0, this.msgTextSize);
        this.mTvMsg.setTextColor(this.msgTextColor);
        this.mTvMsg.setBackground(this.msgTextBg);
        this.mTvNotify.setBackground(this.notifyPointBg);
        this.mTextView.setTextColor(this.titleNormalColor);
        this.mTextView.setText(this.title);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTextView.getLayoutParams();
        layoutParams2.topMargin = this.marginTop;
        this.mTextView.setLayoutParams(layoutParams2);
        if (this.openTouchBg) {
            setBackground(this.touchDrawable);
        }
        addView(viewInitView);
    }

    private void initAttrs(TypedArray typedArray) {
        this.normalIcon = typedArray.getDrawable(R.styleable.BottomBarItem_iconNormal);
        this.selectedIcon = typedArray.getDrawable(R.styleable.BottomBarItem_iconSelected);
        this.title = typedArray.getString(R.styleable.BottomBarItem_itemText);
        this.titleTextBold = typedArray.getBoolean(R.styleable.BottomBarItem_itemTextBold, this.titleTextBold);
        this.titleTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomBarItem_itemTextSize, UIUtils.sp2px(this.context, this.titleTextSize));
        this.titleNormalColor = typedArray.getColor(R.styleable.BottomBarItem_textColorNormal, UIUtils.getColor(this.context, R.color.bbl_999999));
        this.titleSelectedColor = typedArray.getColor(R.styleable.BottomBarItem_textColorSelected, UIUtils.getColor(this.context, R.color.bbl_ff0000));
        this.marginTop = typedArray.getDimensionPixelSize(R.styleable.BottomBarItem_itemMarginTop, UIUtils.dip2Px(this.context, this.marginTop));
        this.openTouchBg = typedArray.getBoolean(R.styleable.BottomBarItem_openTouchBg, this.openTouchBg);
        this.touchDrawable = typedArray.getDrawable(R.styleable.BottomBarItem_touchDrawable);
        this.iconWidth = typedArray.getDimensionPixelSize(R.styleable.BottomBarItem_iconWidth, 0);
        this.iconHeight = typedArray.getDimensionPixelSize(R.styleable.BottomBarItem_iconHeight, 0);
        this.itemPadding = typedArray.getDimensionPixelSize(R.styleable.BottomBarItem_itemPadding, 0);
        this.unreadTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomBarItem_unreadTextSize, UIUtils.sp2px(this.context, this.unreadTextSize));
        int i2 = R.styleable.BottomBarItem_unreadTextColor;
        Context context = this.context;
        int i3 = R.color.white;
        this.unreadTextColor = typedArray.getColor(i2, UIUtils.getColor(context, i3));
        this.unreadTextBg = typedArray.getDrawable(R.styleable.BottomBarItem_unreadTextBg);
        this.msgTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomBarItem_msgTextSize, UIUtils.sp2px(this.context, this.msgTextSize));
        this.msgTextColor = typedArray.getColor(R.styleable.BottomBarItem_msgTextColor, UIUtils.getColor(this.context, i3));
        this.msgTextBg = typedArray.getDrawable(R.styleable.BottomBarItem_msgTextBg);
        this.notifyPointBg = typedArray.getDrawable(R.styleable.BottomBarItem_notifyPointBg);
        this.unreadNumThreshold = typedArray.getInteger(R.styleable.BottomBarItem_unreadThreshold, this.unreadNumThreshold);
        this.lottieJson = typedArray.getString(R.styleable.BottomBarItem_lottieJson);
        this.useLottie = !TextUtils.isEmpty(r5);
    }

    @NonNull
    private View initView() {
        View viewInflate = View.inflate(this.context, R.layout.item_bottom_bar, null);
        int i2 = this.itemPadding;
        if (i2 != 0) {
            viewInflate.setPadding(i2, i2, i2, i2);
        }
        this.mImageView = (ImageView) viewInflate.findViewById(R.id.iv_icon);
        this.mLottieView = (LottieAnimationView) viewInflate.findViewById(R.id.lottieView);
        this.mTvUnread = (TextView) viewInflate.findViewById(R.id.tv_unred_num);
        this.mTvMsg = (TextView) viewInflate.findViewById(R.id.tv_msg);
        this.mTvNotify = (TextView) viewInflate.findViewById(R.id.tv_point);
        this.mTextView = (TextView) viewInflate.findViewById(R.id.tv_text);
        this.mImageView.setVisibility(this.useLottie ? 8 : 0);
        this.mLottieView.setVisibility(this.useLottie ? 0 : 8);
        return viewInflate;
    }

    private void setTvVisible(TextView textView) {
        this.mTvUnread.setVisibility(8);
        this.mTvMsg.setVisibility(8);
        this.mTvNotify.setVisibility(8);
        textView.setVisibility(0);
    }

    public BottomBarItem create(Builder builder) {
        this.context = builder.context;
        this.normalIcon = builder.normalIcon;
        this.selectedIcon = builder.selectedIcon;
        this.title = builder.title;
        this.titleTextBold = builder.titleTextBold;
        this.titleTextSize = builder.titleTextSize;
        this.titleNormalColor = builder.titleNormalColor;
        this.titleSelectedColor = builder.titleSelectedColor;
        this.marginTop = builder.marginTop;
        this.openTouchBg = builder.openTouchBg;
        this.touchDrawable = builder.touchDrawable;
        this.iconWidth = builder.iconWidth;
        this.iconHeight = builder.iconHeight;
        this.itemPadding = builder.itemPadding;
        this.unreadTextSize = builder.unreadTextSize;
        this.unreadTextColor = builder.unreadTextColor;
        this.unreadTextBg = builder.unreadTextBg;
        this.unreadNumThreshold = builder.unreadNumThreshold;
        this.msgTextSize = builder.msgTextSize;
        this.msgTextColor = builder.msgTextColor;
        this.msgTextBg = builder.msgTextBg;
        this.notifyPointBg = builder.notifyPointBg;
        this.lottieJson = builder.lottieJson;
        checkValues();
        init();
        return this;
    }

    public ImageView getImageView() {
        return this.mImageView;
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    public int getUnreadNumThreshold() {
        return this.unreadNumThreshold;
    }

    public void hideMsg() {
        this.mTvMsg.setVisibility(8);
    }

    public void hideNotify() {
        this.mTvNotify.setVisibility(8);
    }

    public void refreshTab(boolean z2) {
        setSelected(z2);
        refreshTab();
    }

    public void setMsg(String str) {
        setTvVisible(this.mTvMsg);
        this.mTvMsg.setText(str);
    }

    public void setNormalIcon(Drawable drawable) {
        this.normalIcon = drawable;
        refreshTab();
    }

    public void setSelectedIcon(Drawable drawable) {
        this.selectedIcon = drawable;
        refreshTab();
    }

    public void setUnreadNum(int i2) {
        setTvVisible(this.mTvUnread);
        if (i2 <= 0) {
            this.mTvUnread.setVisibility(8);
            return;
        }
        int i3 = this.unreadNumThreshold;
        if (i2 <= i3) {
            this.mTvUnread.setText(String.valueOf(i2));
        } else {
            this.mTvUnread.setText(String.format(Locale.CHINA, "%d+", Integer.valueOf(i3)));
        }
    }

    public void setUnreadNumThreshold(int i2) {
        this.unreadNumThreshold = i2;
    }

    public void showNotify() {
        setTvVisible(this.mTvNotify);
    }

    public void refreshTab() {
        if (this.useLottie) {
            if (isSelected()) {
                this.mLottieView.playAnimation();
            } else {
                this.mLottieView.cancelAnimation();
                this.mLottieView.setProgress(0.0f);
            }
        } else {
            this.mImageView.setImageDrawable(isSelected() ? this.selectedIcon : this.normalIcon);
        }
        this.mTextView.setTextColor(isSelected() ? this.titleSelectedColor : this.titleNormalColor);
    }

    public void setNormalIcon(int i2) {
        setNormalIcon(UIUtils.getDrawable(this.context, i2));
    }

    public void setSelectedIcon(int i2) {
        setSelectedIcon(UIUtils.getDrawable(this.context, i2));
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.titleTextBold = false;
        this.titleTextSize = 12;
        this.marginTop = 0;
        this.openTouchBg = false;
        this.unreadTextSize = 10;
        this.unreadNumThreshold = 99;
        this.msgTextSize = 6;
        this.context = context;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomBarItem);
        initAttrs(typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        checkValues();
        init();
    }
}
