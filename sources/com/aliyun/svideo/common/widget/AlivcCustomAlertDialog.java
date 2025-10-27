package com.aliyun.svideo.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
public class AlivcCustomAlertDialog extends Dialog {
    private ImageView ivDialogIcon;
    private OnDialogClickListener mDialogClickListener;
    private TextView tvCancel;
    private TextView tvConfirm;
    private TextView tvDialogMessage;

    public static class Builder {
        private String cancel;
        private String confirm;
        private OnDialogClickListener dialogClickListener;
        private Context mContext;
        private String message;
        private int iconId = R.mipmap.icon_delete_tips;
        private boolean noIcon = false;
        private CustomDialogType customDialogType = CustomDialogType.Alert;

        public Builder(Context context) {
            this.mContext = context;
        }

        public AlivcCustomAlertDialog create() {
            AlivcCustomAlertDialog alivcCustomAlertDialog = new AlivcCustomAlertDialog(this.mContext);
            alivcCustomAlertDialog.setContentView(R.layout.alivc_common_dialog_alert_custom);
            alivcCustomAlertDialog.initView();
            this.message = this.mContext.getResources().getString(R.string.alivc_common_note);
            this.confirm = this.mContext.getResources().getString(R.string.alivc_common_confirm);
            this.cancel = this.mContext.getResources().getString(R.string.alivc_common_cancel);
            alivcCustomAlertDialog.tvDialogMessage.setText(this.message);
            alivcCustomAlertDialog.tvCancel.setText(this.cancel);
            alivcCustomAlertDialog.tvConfirm.setText(this.confirm);
            alivcCustomAlertDialog.mDialogClickListener = this.dialogClickListener;
            alivcCustomAlertDialog.setDialogType(this.customDialogType);
            ViewGroup viewGroup = (ViewGroup) alivcCustomAlertDialog.findViewById(R.id.contentWrap);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParams.width = this.mContext.getResources().getDimensionPixelSize(R.dimen.alivc_common_alert_dialog_w);
            if (this.noIcon) {
                alivcCustomAlertDialog.ivDialogIcon.setVisibility(8);
                layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.alivc_common_alert_dialog_h_no_icon);
            } else {
                alivcCustomAlertDialog.ivDialogIcon.setVisibility(0);
                alivcCustomAlertDialog.ivDialogIcon.setImageResource(this.iconId);
                layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.alivc_common_alert_dialog_h);
            }
            viewGroup.setLayoutParams(layoutParams);
            return alivcCustomAlertDialog;
        }

        public Builder setCustomDialogType(CustomDialogType customDialogType) {
            this.customDialogType = customDialogType;
            return this;
        }

        public Builder setDialogClickListener(String str, String str2, OnDialogClickListener onDialogClickListener) {
            if (!TextUtils.isEmpty(str)) {
                this.confirm = str;
            }
            if (!TextUtils.isEmpty(str2)) {
                this.cancel = str2;
            }
            this.dialogClickListener = onDialogClickListener;
            return this;
        }

        public Builder setIconId(int i2) {
            this.iconId = i2;
            return this;
        }

        public Builder setMessage(String str) {
            this.message = str;
            return this;
        }

        public Builder setNoIcon(boolean z2) {
            this.noIcon = z2;
            return this;
        }
    }

    public enum CustomDialogType {
        Alert,
        Confirm
    }

    public interface OnDialogClickListener {
        void onCancel();

        void onConfirm();
    }

    public AlivcCustomAlertDialog(@NonNull Context context) {
        this(context, R.style.TipDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView() {
        this.ivDialogIcon = (ImageView) findViewById(R.id.iv_dialog_icon);
        this.tvDialogMessage = (TextView) findViewById(R.id.tv_dialog_message);
        this.tvCancel = (TextView) findViewById(R.id.tv_cancel);
        TextView textView = (TextView) findViewById(R.id.tv_confirm);
        this.tvConfirm = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.widget.AlivcCustomAlertDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlivcCustomAlertDialog.this.dismiss();
                if (AlivcCustomAlertDialog.this.mDialogClickListener != null) {
                    AlivcCustomAlertDialog.this.mDialogClickListener.onConfirm();
                }
            }
        });
        this.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.widget.AlivcCustomAlertDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlivcCustomAlertDialog.this.dismiss();
                if (AlivcCustomAlertDialog.this.mDialogClickListener != null) {
                    AlivcCustomAlertDialog.this.mDialogClickListener.onCancel();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDialogType(CustomDialogType customDialogType) {
        if (customDialogType == CustomDialogType.Confirm) {
            this.tvCancel.setVisibility(8);
        } else {
            this.tvCancel.setVisibility(0);
        }
    }

    public AlivcCustomAlertDialog(@NonNull Context context, int i2) {
        super(context, i2);
    }

    public AlivcCustomAlertDialog(@NonNull Context context, boolean z2, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z2, onCancelListener);
    }
}
