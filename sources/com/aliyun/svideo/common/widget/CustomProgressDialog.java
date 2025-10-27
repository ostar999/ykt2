package com.aliyun.svideo.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
public class CustomProgressDialog extends Dialog {
    private Context context;
    private int mCurrentProgress;
    private int mMaxProgress;
    private TextView mMessage;
    private TextView mProgress;
    private ProgressBar mProgressBar;
    private String mStringMessage;
    private String mStringTitle;
    private TextView mTitle;

    public CustomProgressDialog(Context context, String str, String str2) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        this.mStringTitle = str;
        this.mStringMessage = str2;
    }

    private void init() {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.alivc_common_dialog_progress, (ViewGroup) null, false);
        this.mTitle = (TextView) viewInflate.findViewById(R.id.tv_dialog_title);
        this.mMessage = (TextView) viewInflate.findViewById(R.id.tv_dialog_message);
        this.mProgressBar = (ProgressBar) viewInflate.findViewById(R.id.pb_dialog_progress);
        this.mProgress = (TextView) viewInflate.findViewById(R.id.tv_dialog_progress);
        String str = this.mStringTitle;
        if (str != null) {
            this.mTitle.setText(str);
        }
        String str2 = this.mStringMessage;
        if (str2 != null) {
            this.mMessage.setText(str2);
        }
        int i2 = this.mCurrentProgress;
        if (i2 != 0) {
            this.mProgressBar.setProgress(i2);
        }
        int i3 = this.mMaxProgress;
        if (i3 != 0) {
            this.mProgressBar.setMax(i3);
        } else {
            this.mProgressBar.setMax(100);
        }
        setProgress(this.mCurrentProgress);
        setContentView(viewInflate);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
        attributes.width = displayMetrics.widthPixels;
        attributes.height = displayMetrics.heightPixels;
        attributes.gravity = 49;
        window.setAttributes(attributes);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        init();
    }

    public void setMaxProgress(int i2) {
        this.mMaxProgress = i2;
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setMax(i2);
        }
    }

    public void setMessage(String str) {
        this.mStringMessage = str;
        TextView textView = this.mMessage;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setProgress(int i2) {
        this.mCurrentProgress = i2;
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setProgress(i2);
            this.mProgress.setText(((this.mCurrentProgress * 100) / this.mMaxProgress) + "%");
        }
    }

    public void setTitle(String str) {
        this.mStringTitle = str;
        TextView textView = this.mTitle;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
    }

    public CustomProgressDialog(Context context) {
        this(context, "", "");
    }
}
