package com.luck.picture.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class TitleBar extends RelativeLayout implements View.OnClickListener {
    protected PictureSelectionConfig config;
    protected ImageView ivArrow;
    protected ImageView ivDelete;
    protected ImageView ivLeftBack;
    protected RelativeLayout rlAlbumBg;
    protected RelativeLayout titleBarLayout;
    protected OnTitleBarListener titleBarListener;
    protected TextView tvCancel;
    protected MarqueeTextView tvTitle;
    protected View viewAlbumClickArea;
    protected View viewTopStatusBar;

    public static class OnTitleBarListener {
        public void onBackPressed() {
        }

        public void onShowAlbumPopWindow(View view) {
        }

        public void onTitleDoubleClick() {
        }
    }

    public TitleBar(Context context) {
        super(context);
        init();
    }

    public ImageView getImageArrow() {
        return this.ivArrow;
    }

    public ImageView getImageDelete() {
        return this.ivDelete;
    }

    public TextView getTitleCancelView() {
        return this.tvCancel;
    }

    public String getTitleText() {
        return this.tvTitle.getText().toString();
    }

    public void handleLayoutUI() {
    }

    public void inflateLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.ps_title_bar, this);
    }

    public void init() {
        inflateLayout();
        setClickable(true);
        setFocusable(true);
        this.viewTopStatusBar = findViewById(R.id.top_status_bar);
        this.titleBarLayout = (RelativeLayout) findViewById(R.id.rl_title_bar);
        this.ivLeftBack = (ImageView) findViewById(R.id.ps_iv_left_back);
        this.rlAlbumBg = (RelativeLayout) findViewById(R.id.ps_rl_album_bg);
        this.ivDelete = (ImageView) findViewById(R.id.ps_iv_delete);
        this.viewAlbumClickArea = findViewById(R.id.ps_rl_album_click);
        this.tvTitle = (MarqueeTextView) findViewById(R.id.ps_tv_title);
        this.ivArrow = (ImageView) findViewById(R.id.ps_iv_arrow);
        this.tvCancel = (TextView) findViewById(R.id.ps_tv_cancel);
        this.ivLeftBack.setOnClickListener(this);
        this.tvCancel.setOnClickListener(this);
        this.rlAlbumBg.setOnClickListener(this);
        this.titleBarLayout.setOnClickListener(this);
        this.viewAlbumClickArea.setOnClickListener(this);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ps_color_grey));
        this.config = PictureSelectionConfig.getInstance();
        handleLayoutUI();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnTitleBarListener onTitleBarListener;
        int id = view.getId();
        if (id == R.id.ps_iv_left_back || id == R.id.ps_tv_cancel) {
            OnTitleBarListener onTitleBarListener2 = this.titleBarListener;
            if (onTitleBarListener2 != null) {
                onTitleBarListener2.onBackPressed();
                return;
            }
            return;
        }
        if (id == R.id.ps_rl_album_bg || id == R.id.ps_rl_album_click) {
            OnTitleBarListener onTitleBarListener3 = this.titleBarListener;
            if (onTitleBarListener3 != null) {
                onTitleBarListener3.onShowAlbumPopWindow(this);
                return;
            }
            return;
        }
        if (id != R.id.rl_title_bar || (onTitleBarListener = this.titleBarListener) == null) {
            return;
        }
        onTitleBarListener.onTitleDoubleClick();
    }

    public void setOnTitleBarListener(OnTitleBarListener onTitleBarListener) {
        this.titleBarListener = onTitleBarListener;
    }

    public void setTitle(String str) {
        this.tvTitle.setText(str);
    }

    public void setTitleBarStyle() {
        if (this.config.isPreviewFullScreenMode) {
            this.viewTopStatusBar.getLayoutParams().height = DensityUtil.getStatusBarHeight(getContext());
        }
        TitleBarStyle titleBarStyle = PictureSelectionConfig.selectorStyle.getTitleBarStyle();
        int titleBarHeight = titleBarStyle.getTitleBarHeight();
        if (StyleUtils.checkSizeValidity(titleBarHeight)) {
            this.titleBarLayout.getLayoutParams().height = titleBarHeight;
        } else {
            this.titleBarLayout.getLayoutParams().height = DensityUtil.dip2px(getContext(), 48.0f);
        }
        int titleBackgroundColor = titleBarStyle.getTitleBackgroundColor();
        if (StyleUtils.checkStyleValidity(titleBackgroundColor)) {
            setBackgroundColor(titleBackgroundColor);
        }
        int titleLeftBackResource = titleBarStyle.getTitleLeftBackResource();
        if (StyleUtils.checkStyleValidity(titleLeftBackResource)) {
            this.ivLeftBack.setImageResource(titleLeftBackResource);
        }
        String titleDefaultText = titleBarStyle.getTitleDefaultText();
        if (StyleUtils.checkTextValidity(titleDefaultText)) {
            this.tvTitle.setText(titleDefaultText);
        }
        int titleTextSize = titleBarStyle.getTitleTextSize();
        if (StyleUtils.checkSizeValidity(titleTextSize)) {
            this.tvTitle.setTextSize(titleTextSize);
        }
        int titleTextColor = titleBarStyle.getTitleTextColor();
        if (StyleUtils.checkStyleValidity(titleTextColor)) {
            this.tvTitle.setTextColor(titleTextColor);
        }
        if (this.config.isOnlySandboxDir) {
            this.ivArrow.setImageResource(R.drawable.ps_trans_1px);
        } else {
            int titleDrawableRightResource = titleBarStyle.getTitleDrawableRightResource();
            if (StyleUtils.checkStyleValidity(titleDrawableRightResource)) {
                this.ivArrow.setImageResource(titleDrawableRightResource);
            }
        }
        int titleAlbumBackgroundResource = titleBarStyle.getTitleAlbumBackgroundResource();
        if (StyleUtils.checkStyleValidity(titleAlbumBackgroundResource)) {
            this.rlAlbumBg.setBackgroundResource(titleAlbumBackgroundResource);
        }
        if (titleBarStyle.isHideCancelButton()) {
            this.tvCancel.setVisibility(8);
        } else {
            this.tvCancel.setVisibility(0);
            int titleCancelBackgroundResource = titleBarStyle.getTitleCancelBackgroundResource();
            if (StyleUtils.checkStyleValidity(titleCancelBackgroundResource)) {
                this.tvCancel.setBackgroundResource(titleCancelBackgroundResource);
            }
            String titleCancelText = titleBarStyle.getTitleCancelText();
            if (StyleUtils.checkTextValidity(titleCancelText)) {
                this.tvCancel.setText(titleCancelText);
            }
            int titleCancelTextColor = titleBarStyle.getTitleCancelTextColor();
            if (StyleUtils.checkStyleValidity(titleCancelTextColor)) {
                this.tvCancel.setTextColor(titleCancelTextColor);
            }
            int titleCancelTextSize = titleBarStyle.getTitleCancelTextSize();
            if (StyleUtils.checkSizeValidity(titleCancelTextSize)) {
                this.tvCancel.setTextSize(titleCancelTextSize);
            }
        }
        int previewDeleteBackgroundResource = titleBarStyle.getPreviewDeleteBackgroundResource();
        if (StyleUtils.checkStyleValidity(previewDeleteBackgroundResource)) {
            this.ivDelete.setBackgroundResource(previewDeleteBackgroundResource);
        } else {
            this.ivDelete.setBackgroundResource(R.drawable.ps_ic_delete);
        }
    }

    public TitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TitleBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
