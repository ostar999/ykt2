package com.luck.picture.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.style.BottomNavBarStyle;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class BottomNavBar extends RelativeLayout implements View.OnClickListener {
    protected OnBottomNavBarListener bottomNavBarListener;
    protected PictureSelectionConfig config;
    private CheckBox originalCheckbox;
    protected TextView tvImageEditor;
    protected TextView tvPreview;

    public static class OnBottomNavBarListener {
        public void onCheckOriginalChange() {
        }

        public void onEditImage() {
        }

        public void onFirstCheckOriginalSelectedChange() {
        }

        public void onPreview() {
        }
    }

    public BottomNavBar(Context context) {
        super(context);
        init();
    }

    private void calculateFileTotalSize() {
        if (!this.config.isOriginalControl) {
            this.originalCheckbox.setText(getContext().getString(R.string.ps_default_original_image));
            return;
        }
        long size = 0;
        for (int i2 = 0; i2 < SelectedManager.getCount(); i2++) {
            size += SelectedManager.getSelectedResult().get(i2).getSize();
        }
        if (size <= 0) {
            this.originalCheckbox.setText(getContext().getString(R.string.ps_default_original_image));
        } else {
            this.originalCheckbox.setText(getContext().getString(R.string.ps_original_image, PictureFileUtils.formatFileSize(size, 2)));
        }
    }

    public void handleLayoutUI() {
    }

    public void inflateLayout() {
        View.inflate(getContext(), R.layout.ps_bottom_nav_bar, this);
    }

    public void init() {
        inflateLayout();
        setClickable(true);
        setFocusable(true);
        this.config = PictureSelectionConfig.getInstance();
        this.tvPreview = (TextView) findViewById(R.id.ps_tv_preview);
        this.tvImageEditor = (TextView) findViewById(R.id.ps_tv_editor);
        this.originalCheckbox = (CheckBox) findViewById(R.id.cb_original);
        this.tvPreview.setOnClickListener(this);
        this.tvImageEditor.setVisibility(8);
        if (this.config.chooseMode == SelectMimeType.ofAudio()) {
            this.tvPreview.setVisibility(8);
        } else {
            this.tvPreview.setVisibility(0);
        }
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
        this.originalCheckbox.setChecked(this.config.isCheckOriginalImage);
        this.originalCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.luck.picture.lib.widget.BottomNavBar.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                BottomNavBar bottomNavBar = BottomNavBar.this;
                bottomNavBar.config.isCheckOriginalImage = z2;
                bottomNavBar.originalCheckbox.setChecked(BottomNavBar.this.config.isCheckOriginalImage);
                OnBottomNavBarListener onBottomNavBarListener = BottomNavBar.this.bottomNavBarListener;
                if (onBottomNavBarListener != null) {
                    onBottomNavBarListener.onCheckOriginalChange();
                    if (z2 && SelectedManager.getCount() == 0) {
                        BottomNavBar.this.bottomNavBarListener.onFirstCheckOriginalSelectedChange();
                    }
                }
            }
        });
        handleLayoutUI();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.bottomNavBarListener != null && view.getId() == R.id.ps_tv_preview) {
            this.bottomNavBarListener.onPreview();
        }
    }

    public void setBottomNavBarStyle() {
        if (this.config.isDirectReturnSingle) {
            setVisibility(8);
            return;
        }
        BottomNavBarStyle bottomBarStyle = PictureSelectionConfig.selectorStyle.getBottomBarStyle();
        if (this.config.isOriginalControl) {
            this.originalCheckbox.setVisibility(0);
            int bottomOriginalDrawableLeft = bottomBarStyle.getBottomOriginalDrawableLeft();
            if (StyleUtils.checkStyleValidity(bottomOriginalDrawableLeft)) {
                this.originalCheckbox.setButtonDrawable(bottomOriginalDrawableLeft);
            }
            String bottomOriginalText = bottomBarStyle.getBottomOriginalText();
            if (StyleUtils.checkTextValidity(bottomOriginalText)) {
                this.originalCheckbox.setText(bottomOriginalText);
            }
            int bottomOriginalTextSize = bottomBarStyle.getBottomOriginalTextSize();
            if (StyleUtils.checkSizeValidity(bottomOriginalTextSize)) {
                this.originalCheckbox.setTextSize(bottomOriginalTextSize);
            }
            int bottomOriginalTextColor = bottomBarStyle.getBottomOriginalTextColor();
            if (StyleUtils.checkStyleValidity(bottomOriginalTextColor)) {
                this.originalCheckbox.setTextColor(bottomOriginalTextColor);
            }
        }
        int bottomNarBarHeight = bottomBarStyle.getBottomNarBarHeight();
        if (StyleUtils.checkSizeValidity(bottomNarBarHeight)) {
            getLayoutParams().height = bottomNarBarHeight;
        } else {
            getLayoutParams().height = DensityUtil.dip2px(getContext(), 46.0f);
        }
        int bottomNarBarBackgroundColor = bottomBarStyle.getBottomNarBarBackgroundColor();
        if (StyleUtils.checkStyleValidity(bottomNarBarBackgroundColor)) {
            setBackgroundColor(bottomNarBarBackgroundColor);
        }
        int bottomPreviewNormalTextColor = bottomBarStyle.getBottomPreviewNormalTextColor();
        if (StyleUtils.checkStyleValidity(bottomPreviewNormalTextColor)) {
            this.tvPreview.setTextColor(bottomPreviewNormalTextColor);
        }
        int bottomPreviewNormalTextSize = bottomBarStyle.getBottomPreviewNormalTextSize();
        if (StyleUtils.checkSizeValidity(bottomPreviewNormalTextSize)) {
            this.tvPreview.setTextSize(bottomPreviewNormalTextSize);
        }
        String bottomPreviewNormalText = bottomBarStyle.getBottomPreviewNormalText();
        if (StyleUtils.checkTextValidity(bottomPreviewNormalText)) {
            this.tvPreview.setText(bottomPreviewNormalText);
        }
        String bottomEditorText = bottomBarStyle.getBottomEditorText();
        if (StyleUtils.checkTextValidity(bottomEditorText)) {
            this.tvImageEditor.setText(bottomEditorText);
        }
        int bottomEditorTextSize = bottomBarStyle.getBottomEditorTextSize();
        if (StyleUtils.checkSizeValidity(bottomEditorTextSize)) {
            this.tvImageEditor.setTextSize(bottomEditorTextSize);
        }
        int bottomEditorTextColor = bottomBarStyle.getBottomEditorTextColor();
        if (StyleUtils.checkStyleValidity(bottomEditorTextColor)) {
            this.tvImageEditor.setTextColor(bottomEditorTextColor);
        }
        int bottomOriginalDrawableLeft2 = bottomBarStyle.getBottomOriginalDrawableLeft();
        if (StyleUtils.checkStyleValidity(bottomOriginalDrawableLeft2)) {
            this.originalCheckbox.setButtonDrawable(bottomOriginalDrawableLeft2);
        }
        String bottomOriginalText2 = bottomBarStyle.getBottomOriginalText();
        if (StyleUtils.checkTextValidity(bottomOriginalText2)) {
            this.originalCheckbox.setText(bottomOriginalText2);
        }
        int bottomOriginalTextSize2 = bottomBarStyle.getBottomOriginalTextSize();
        if (StyleUtils.checkSizeValidity(bottomOriginalTextSize2)) {
            this.originalCheckbox.setTextSize(bottomOriginalTextSize2);
        }
        int bottomOriginalTextColor2 = bottomBarStyle.getBottomOriginalTextColor();
        if (StyleUtils.checkStyleValidity(bottomOriginalTextColor2)) {
            this.originalCheckbox.setTextColor(bottomOriginalTextColor2);
        }
    }

    public void setOnBottomNavBarListener(OnBottomNavBarListener onBottomNavBarListener) {
        this.bottomNavBarListener = onBottomNavBarListener;
    }

    public void setOriginalCheck() {
        this.originalCheckbox.setChecked(this.config.isCheckOriginalImage);
    }

    public void setSelectedChange() {
        calculateFileTotalSize();
        BottomNavBarStyle bottomBarStyle = PictureSelectionConfig.selectorStyle.getBottomBarStyle();
        if (SelectedManager.getCount() <= 0) {
            this.tvPreview.setEnabled(false);
            int bottomPreviewNormalTextColor = bottomBarStyle.getBottomPreviewNormalTextColor();
            if (StyleUtils.checkStyleValidity(bottomPreviewNormalTextColor)) {
                this.tvPreview.setTextColor(bottomPreviewNormalTextColor);
            } else {
                this.tvPreview.setTextColor(ContextCompat.getColor(getContext(), R.color.ps_color_9b));
            }
            String bottomPreviewNormalText = bottomBarStyle.getBottomPreviewNormalText();
            if (StyleUtils.checkTextValidity(bottomPreviewNormalText)) {
                this.tvPreview.setText(bottomPreviewNormalText);
                return;
            } else {
                this.tvPreview.setText(getContext().getString(R.string.ps_preview));
                return;
            }
        }
        this.tvPreview.setEnabled(true);
        int bottomPreviewSelectTextColor = bottomBarStyle.getBottomPreviewSelectTextColor();
        if (StyleUtils.checkStyleValidity(bottomPreviewSelectTextColor)) {
            this.tvPreview.setTextColor(bottomPreviewSelectTextColor);
        } else {
            this.tvPreview.setTextColor(ContextCompat.getColor(getContext(), R.color.ps_color_fa632d));
        }
        String bottomPreviewSelectText = bottomBarStyle.getBottomPreviewSelectText();
        if (!StyleUtils.checkTextValidity(bottomPreviewSelectText)) {
            this.tvPreview.setText(getContext().getString(R.string.ps_preview_num, Integer.valueOf(SelectedManager.getCount())));
        } else if (StyleUtils.checkTextFormatValidity(bottomPreviewSelectText)) {
            this.tvPreview.setText(String.format(bottomPreviewSelectText, Integer.valueOf(SelectedManager.getCount())));
        } else {
            this.tvPreview.setText(bottomPreviewSelectText);
        }
    }

    public BottomNavBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BottomNavBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
