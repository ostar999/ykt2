package com.aliyun.svideo.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.aliyun.svideo.common.R;
import com.aliyun.svideo.common.utils.DensityUtils;

/* loaded from: classes2.dex */
public class TipDialog extends Dialog {

    public static class Builder {
        public static final int TYPE_DEFAILD = 0;
        public static final int TYPE_FAIL = 4;
        public static final int TYPE_IMG_ONLY = 5;
        public static final int TYPE_LOADING = 2;
        public static final int TYPE_MESSAGE_ONLY = 1;
        public static final int TYPE_SUCCESS = 3;
        private int iconId;
        private Context mContext;
        private String message = "";
        private int type = 0;

        public Builder(Context context) {
            this.mContext = context;
        }

        private void addImageView(ViewGroup viewGroup, int i2) {
            ImageView imageView = new ImageView(this.mContext);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, i2));
            viewGroup.addView(imageView);
        }

        private void addLoadingView(ViewGroup viewGroup) {
            QMUILoadingView qMUILoadingView = new QMUILoadingView(this.mContext);
            qMUILoadingView.setColor(-1);
            qMUILoadingView.setSize(DensityUtils.dip2px(this.mContext, 32.0f));
            qMUILoadingView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            viewGroup.addView(qMUILoadingView);
        }

        private void addTextView(ViewGroup viewGroup) {
            addTextView(viewGroup, this.message);
        }

        public TipDialog create() {
            TipDialog tipDialog = new TipDialog(this.mContext);
            tipDialog.setCancelable(true);
            tipDialog.setContentView(R.layout.alivc_common_dialog_tip);
            ViewGroup viewGroup = (ViewGroup) tipDialog.findViewById(R.id.contentWrap);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
            int i2 = this.type;
            if (i2 == 0) {
                layoutParams.width = DensityUtils.dip2px(this.mContext, 300.0f);
                layoutParams.height = DensityUtils.dip2px(this.mContext, 180.0f);
                viewGroup.setLayoutParams(layoutParams);
                int i3 = this.iconId;
                if (i3 != 0) {
                    addImageView(viewGroup, i3);
                }
                if (!TextUtils.isEmpty(this.message)) {
                    addTextView(viewGroup);
                }
            } else if (i2 == 1) {
                viewGroup.setBackgroundResource(R.drawable.alivc_dialog_bg_translucent);
                layoutParams.width = DensityUtils.dip2px(this.mContext, 150.0f);
                layoutParams.height = DensityUtils.dip2px(this.mContext, 90.0f);
                viewGroup.setLayoutParams(layoutParams);
                addTextView(viewGroup);
            } else if (i2 == 2) {
                viewGroup.setBackgroundResource(R.drawable.alivc_dialog_bg_translucent);
                layoutParams.width = DensityUtils.dip2px(this.mContext, 150.0f);
                layoutParams.height = DensityUtils.dip2px(this.mContext, 90.0f);
                viewGroup.setLayoutParams(layoutParams);
                addLoadingView(viewGroup);
                if (TextUtils.isEmpty(this.message)) {
                    addTextView(viewGroup, this.mContext.getResources().getString(R.string.alivc_common_loading));
                } else {
                    addTextView(viewGroup);
                }
            } else if (i2 == 3) {
                layoutParams.width = DensityUtils.dip2px(this.mContext, 300.0f);
                layoutParams.height = DensityUtils.dip2px(this.mContext, 180.0f);
                viewGroup.setLayoutParams(layoutParams);
                addImageView(viewGroup, R.mipmap.icon_delete_tips);
                if (TextUtils.isEmpty(this.message)) {
                    addTextView(viewGroup, this.mContext.getResources().getString(R.string.alivc_common_operate_success));
                } else {
                    addTextView(viewGroup);
                }
            } else if (i2 == 4) {
                layoutParams.width = DensityUtils.dip2px(this.mContext, 300.0f);
                layoutParams.height = DensityUtils.dip2px(this.mContext, 180.0f);
                viewGroup.setLayoutParams(layoutParams);
                addImageView(viewGroup, R.mipmap.icon_delete_tips);
                if (TextUtils.isEmpty(this.message)) {
                    addTextView(viewGroup, this.mContext.getResources().getString(R.string.alivc_common_operate_success));
                } else {
                    addTextView(viewGroup);
                }
            } else if (i2 != 5) {
                layoutParams.width = DensityUtils.dip2px(this.mContext, 300.0f);
                layoutParams.height = DensityUtils.dip2px(this.mContext, 180.0f);
                viewGroup.setLayoutParams(layoutParams);
                int i4 = this.iconId;
                if (i4 != 0) {
                    addImageView(viewGroup, i4);
                }
                if (!TextUtils.isEmpty(this.message)) {
                    addTextView(viewGroup);
                }
            } else {
                viewGroup.setBackgroundResource(R.drawable.alivc_dialog_bg_translucent);
                layoutParams.width = DensityUtils.dip2px(this.mContext, 150.0f);
                layoutParams.height = DensityUtils.dip2px(this.mContext, 90.0f);
                viewGroup.setLayoutParams(layoutParams);
                addImageView(viewGroup, this.iconId);
            }
            return tipDialog;
        }

        public Builder setIconId(int i2) {
            this.iconId = i2;
            return this;
        }

        public Builder setMessage(String str) {
            this.message = str;
            return this;
        }

        public Builder setType(int i2) {
            this.type = i2;
            return this;
        }

        private void addTextView(ViewGroup viewGroup, String str) {
            TextView textView = new TextView(this.mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.topMargin = DensityUtils.dip2px(this.mContext, 12.0f);
            textView.setLayoutParams(layoutParams);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(17);
            textView.setMaxLines(2);
            textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.alivc_common_white));
            textView.setTextSize(2, 14.0f);
            textView.setText(str);
            viewGroup.addView(textView);
        }
    }

    public TipDialog(Context context) {
        this(context, R.style.TipDialog);
    }

    public TipDialog(Context context, int i2) {
        super(context, i2);
        setCanceledOnTouchOutside(false);
    }
}
