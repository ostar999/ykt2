package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.psychiatrygarden.interfaceclass.DialogLoadingBtnInterface;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class LoadingProgressDialog extends Dialog {
    private Button mBtnCancelLoading;
    private Context mContext;
    DialogLoadingBtnInterface mLoadingInterface;
    private ProgressBar mProgressbar;
    private TextView mTxtAllSize;
    private TextView mTxtAlreadyLoading;
    private TextView mTxtPercentage;

    public LoadingProgressDialog(Context context, DialogLoadingBtnInterface mLoadingInterface) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        setContentView(R.layout.dialog_loading_progress);
        initView();
        this.mLoadingInterface = mLoadingInterface;
    }

    private void initView() {
        this.mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
        this.mTxtPercentage = (TextView) findViewById(R.id.txt_percentage);
        this.mTxtAlreadyLoading = (TextView) findViewById(R.id.txt_already_loading);
        this.mTxtAllSize = (TextView) findViewById(R.id.txt_all_size);
        this.mBtnCancelLoading = (Button) findViewById(R.id.btn_cancel_loading);
        this.mProgressbar.setIndeterminate(false);
        this.mProgressbar.setProgress(0);
        this.mProgressbar.setMax(100);
        this.mBtnCancelLoading.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.va
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16993c.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.mLoadingInterface.CancelBtnInterface();
    }

    public void setAllSize(String msg) {
        if (msg == null || msg.equals("")) {
            return;
        }
        this.mTxtAllSize.setText(msg);
    }

    public void setAlreadyLoading(String msg) {
        if (msg == null || msg.equals("")) {
            return;
        }
        this.mTxtAlreadyLoading.setText(msg);
    }

    public void setPercentage(String msg) {
        if (msg == null || msg.equals("")) {
            return;
        }
        this.mTxtPercentage.setText(msg);
    }

    public void setProgressbar(int progress) {
        this.mProgressbar.setProgress(progress);
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (this.mContext.getResources().getDisplayMetrics().widthPixels * 9) / 10;
        window.setAttributes(attributes);
        window.setGravity(17);
    }
}
