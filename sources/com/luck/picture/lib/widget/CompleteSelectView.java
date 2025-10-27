package com.luck.picture.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.style.BottomNavBarStyle;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ValueOf;

/* loaded from: classes4.dex */
public class CompleteSelectView extends LinearLayout {
    private PictureSelectionConfig config;
    private Animation numberChangeAnimation;
    private TextView tvComplete;
    private TextView tvSelectNum;

    public CompleteSelectView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflateLayout();
        setOrientation(0);
        this.tvSelectNum = (TextView) findViewById(R.id.ps_tv_select_num);
        this.tvComplete = (TextView) findViewById(R.id.ps_tv_complete);
        setGravity(16);
        this.numberChangeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.ps_anim_modal_in);
        this.config = PictureSelectionConfig.getInstance();
    }

    public void inflateLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.ps_complete_selected_layout, this);
    }

    public void setCompleteSelectViewStyle() {
        PictureSelectorStyle pictureSelectorStyle = PictureSelectionConfig.selectorStyle;
        SelectMainStyle selectMainStyle = pictureSelectorStyle.getSelectMainStyle();
        if (StyleUtils.checkStyleValidity(selectMainStyle.getSelectNormalBackgroundResources())) {
            setBackgroundResource(selectMainStyle.getSelectNormalBackgroundResources());
        }
        String selectNormalText = selectMainStyle.getSelectNormalText();
        if (StyleUtils.checkTextValidity(selectNormalText)) {
            if (StyleUtils.checkTextTwoFormatValidity(selectNormalText)) {
                this.tvComplete.setText(String.format(selectNormalText, Integer.valueOf(SelectedManager.getCount()), Integer.valueOf(this.config.maxSelectNum)));
            } else {
                this.tvComplete.setText(selectNormalText);
            }
        }
        int selectNormalTextSize = selectMainStyle.getSelectNormalTextSize();
        if (StyleUtils.checkSizeValidity(selectNormalTextSize)) {
            this.tvComplete.setTextSize(selectNormalTextSize);
        }
        int selectNormalTextColor = selectMainStyle.getSelectNormalTextColor();
        if (StyleUtils.checkStyleValidity(selectNormalTextColor)) {
            this.tvComplete.setTextColor(selectNormalTextColor);
        }
        BottomNavBarStyle bottomBarStyle = pictureSelectorStyle.getBottomBarStyle();
        if (bottomBarStyle.isCompleteCountTips()) {
            int bottomSelectNumResources = bottomBarStyle.getBottomSelectNumResources();
            if (StyleUtils.checkStyleValidity(bottomSelectNumResources)) {
                this.tvSelectNum.setBackgroundResource(bottomSelectNumResources);
            }
            int bottomSelectNumTextSize = bottomBarStyle.getBottomSelectNumTextSize();
            if (StyleUtils.checkSizeValidity(bottomSelectNumTextSize)) {
                this.tvSelectNum.setTextSize(bottomSelectNumTextSize);
            }
            int bottomSelectNumTextColor = bottomBarStyle.getBottomSelectNumTextColor();
            if (StyleUtils.checkStyleValidity(bottomSelectNumTextColor)) {
                this.tvSelectNum.setTextColor(bottomSelectNumTextColor);
            }
        }
    }

    public void setSelectedChange(boolean z2) {
        PictureSelectorStyle pictureSelectorStyle = PictureSelectionConfig.selectorStyle;
        SelectMainStyle selectMainStyle = pictureSelectorStyle.getSelectMainStyle();
        if (SelectedManager.getCount() > 0) {
            setEnabled(true);
            int selectBackgroundResources = selectMainStyle.getSelectBackgroundResources();
            if (StyleUtils.checkStyleValidity(selectBackgroundResources)) {
                setBackgroundResource(selectBackgroundResources);
            } else {
                setBackgroundResource(R.drawable.ps_trans_1px);
            }
            String selectText = selectMainStyle.getSelectText();
            if (!StyleUtils.checkTextValidity(selectText)) {
                this.tvComplete.setText(getContext().getString(R.string.ps_completed));
            } else if (StyleUtils.checkTextTwoFormatValidity(selectText)) {
                this.tvComplete.setText(String.format(selectText, Integer.valueOf(SelectedManager.getCount()), Integer.valueOf(this.config.maxSelectNum)));
            } else {
                this.tvComplete.setText(selectText);
            }
            int selectTextSize = selectMainStyle.getSelectTextSize();
            if (StyleUtils.checkSizeValidity(selectTextSize)) {
                this.tvComplete.setTextSize(selectTextSize);
            }
            int selectTextColor = selectMainStyle.getSelectTextColor();
            if (StyleUtils.checkStyleValidity(selectTextColor)) {
                this.tvComplete.setTextColor(selectTextColor);
            } else {
                this.tvComplete.setTextColor(ContextCompat.getColor(getContext(), R.color.ps_color_fa632d));
            }
            if (!pictureSelectorStyle.getBottomBarStyle().isCompleteCountTips()) {
                this.tvSelectNum.setVisibility(8);
                return;
            }
            this.tvSelectNum.setVisibility(0);
            this.tvSelectNum.setText(ValueOf.toString(Integer.valueOf(SelectedManager.getCount())));
            this.tvSelectNum.startAnimation(this.numberChangeAnimation);
            return;
        }
        if (z2 && selectMainStyle.isCompleteSelectRelativeTop()) {
            setEnabled(true);
            int selectBackgroundResources2 = selectMainStyle.getSelectBackgroundResources();
            if (StyleUtils.checkStyleValidity(selectBackgroundResources2)) {
                setBackgroundResource(selectBackgroundResources2);
            } else {
                setBackgroundResource(R.drawable.ps_trans_1px);
            }
            int selectTextColor2 = selectMainStyle.getSelectTextColor();
            if (StyleUtils.checkStyleValidity(selectTextColor2)) {
                this.tvComplete.setTextColor(selectTextColor2);
            } else {
                this.tvComplete.setTextColor(ContextCompat.getColor(getContext(), R.color.ps_color_9b));
            }
        } else {
            setEnabled(false);
            int selectNormalBackgroundResources = selectMainStyle.getSelectNormalBackgroundResources();
            if (StyleUtils.checkStyleValidity(selectNormalBackgroundResources)) {
                setBackgroundResource(selectNormalBackgroundResources);
            } else {
                setBackgroundResource(R.drawable.ps_trans_1px);
            }
            int selectNormalTextColor = selectMainStyle.getSelectNormalTextColor();
            if (StyleUtils.checkStyleValidity(selectNormalTextColor)) {
                this.tvComplete.setTextColor(selectNormalTextColor);
            } else {
                this.tvComplete.setTextColor(ContextCompat.getColor(getContext(), R.color.ps_color_9b));
            }
        }
        this.tvSelectNum.setVisibility(8);
        String selectNormalText = selectMainStyle.getSelectNormalText();
        if (!StyleUtils.checkTextValidity(selectNormalText)) {
            this.tvComplete.setText(getContext().getString(R.string.ps_please_select));
        } else if (StyleUtils.checkTextTwoFormatValidity(selectNormalText)) {
            this.tvComplete.setText(String.format(selectNormalText, Integer.valueOf(SelectedManager.getCount()), Integer.valueOf(this.config.maxSelectNum)));
        } else {
            this.tvComplete.setText(selectNormalText);
        }
        int selectNormalTextSize = selectMainStyle.getSelectNormalTextSize();
        if (StyleUtils.checkSizeValidity(selectNormalTextSize)) {
            this.tvComplete.setTextSize(selectNormalTextSize);
        }
    }

    public CompleteSelectView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public CompleteSelectView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
