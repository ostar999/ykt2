package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ShareDialogBottom extends AlertDialog implements View.OnClickListener {
    private onDialogShareClickListener clickListener;
    private Context context;
    private Window window;

    public ShareDialogBottom(Context context, onDialogShareClickListener clickListener) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.btn_cancel /* 2131362318 */:
                dismiss();
                break;
            case R.id.btn_qq /* 2131362380 */:
                this.clickListener.onclickIntBack(2);
                dismiss();
                break;
            case R.id.btn_save /* 2131362387 */:
                this.clickListener.onclickIntBack(4);
                dismiss();
                break;
            case R.id.btn_sina /* 2131362399 */:
                this.clickListener.onclickIntBack(3);
                dismiss();
                break;
            case R.id.btn_wechat /* 2131362422 */:
                this.clickListener.onclickIntBack(0);
                dismiss();
                break;
            case R.id.btn_wxcircle /* 2131362425 */:
                this.clickListener.onclickIntBack(1);
                dismiss();
                break;
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_new);
        Window window = getWindow();
        this.window = window;
        window.setWindowAnimations(R.style.popupAnimation);
        this.window.setGravity(80);
        this.window.setLayout(CommonUtil.getScreenWidth(this.context), -2);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        findViewById(R.id.btn_wechat).setOnClickListener(this);
        findViewById(R.id.btn_wxcircle).setOnClickListener(this);
        findViewById(R.id.btn_qq).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_sina).setOnClickListener(this);
        ((TextView) findViewById(R.id.tvSave)).setText("保存文件");
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    public ShareDialogBottom(Context context, onDialogShareClickListener clickListener, boolean flag) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
        this.clickListener = clickListener;
    }
}
