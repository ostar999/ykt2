package com.aliyun.player.alivcplayerexpand.view.download;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class AlivcDialog extends Dialog {
    private Button btnCancel;
    private Button btnConfirm;
    private String cancelStr;
    private String confirmStr;
    private int dialogIcon;
    private ImageView ivDialogIcon;
    private String messageStr;
    private onCancelOnclickListener onCancelOnclickListener;
    private onConfirmClickListener onConfirmClickListener;
    private TextView tvMessage;

    public interface onCancelOnclickListener {
        void onCancel();
    }

    public interface onConfirmClickListener {
        void onConfirm();
    }

    public AlivcDialog(@NonNull Context context) {
        super(context);
    }

    private void initData() {
        this.ivDialogIcon.setBackgroundResource(this.dialogIcon);
        String str = this.messageStr;
        if (str != null) {
            this.tvMessage.setText(str);
        }
        String str2 = this.confirmStr;
        if (str2 != null) {
            this.btnConfirm.setText(str2);
        }
        String str3 = this.cancelStr;
        if (str3 != null) {
            this.btnCancel.setText(str3);
        }
    }

    private void initEvent() {
        this.btnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AlivcDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AlivcDialog.this.onConfirmClickListener != null) {
                    AlivcDialog.this.onConfirmClickListener.onConfirm();
                }
            }
        });
        this.btnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.download.AlivcDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AlivcDialog.this.onCancelOnclickListener != null) {
                    AlivcDialog.this.onCancelOnclickListener.onCancel();
                }
            }
        });
    }

    private void initView() {
        this.btnConfirm = (Button) findViewById(R.id.yes);
        this.btnCancel = (Button) findViewById(R.id.no);
        this.ivDialogIcon = (ImageView) findViewById(R.id.iv_dialog_icon);
        this.tvMessage = (TextView) findViewById(R.id.tv_message);
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.alivc_dialog_delete);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    public void setDialogIcon(int i2) {
        this.dialogIcon = i2;
    }

    public void setMessage(String str) {
        this.messageStr = str;
    }

    public void setOnCancelOnclickListener(String str, onCancelOnclickListener oncancelonclicklistener) {
        if (str != null) {
            this.cancelStr = str;
        }
        this.onCancelOnclickListener = oncancelonclicklistener;
    }

    public void setOnConfirmclickListener(String str, onConfirmClickListener onconfirmclicklistener) {
        if (str != null) {
            this.confirmStr = str;
        }
        this.onConfirmClickListener = onconfirmclicklistener;
    }

    public AlivcDialog(@NonNull Context context, int i2) {
        super(context, i2);
    }

    public AlivcDialog(@NonNull Context context, boolean z2, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z2, onCancelListener);
    }
}
