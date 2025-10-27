package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DialogShare extends AlertDialog implements View.OnClickListener {
    private onDialogShareClickListener clickListener;
    private Context context;
    private boolean flag;
    private int mShowCopy;
    private boolean onlyWeiChat;
    private Window window;

    public DialogShare(Context context, onDialogShareClickListener clickListener) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.mShowCopy = 0;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.yejianmoshi) {
            this.clickListener.onclickIntBack(4);
            dismiss();
        }
        if (id == R.id.zitishezhi) {
            this.clickListener.onclickIntBack(5);
            dismiss();
            return;
        }
        switch (id) {
            case R.id.dialog_share_btn_close /* 2131362916 */:
                dismiss();
                break;
            case R.id.dialog_share_btn_copy /* 2131362917 */:
                this.clickListener.onclickIntBack(6);
                dismiss();
                break;
            case R.id.dialog_share_btn_qq /* 2131362918 */:
                this.clickListener.onclickIntBack(2);
                dismiss();
                break;
            case R.id.dialog_share_btn_wb /* 2131362919 */:
                this.clickListener.onclickIntBack(3);
                dismiss();
                break;
            case R.id.dialog_share_btn_wechat /* 2131362920 */:
                this.clickListener.onclickIntBack(0);
                dismiss();
                break;
            case R.id.dialog_share_btn_wxcircle /* 2131362921 */:
                this.clickListener.onclickIntBack(1);
                dismiss();
                break;
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        Window window = getWindow();
        this.window = window;
        window.setGravity(80);
        this.window.setLayout(CommonUtil.getScreenWidth(this.context), -2);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        findViewById(R.id.dialog_share_btn_wxcircle).setOnClickListener(this);
        findViewById(R.id.dialog_share_btn_qq).setOnClickListener(this);
        findViewById(R.id.dialog_share_btn_wechat).setOnClickListener(this);
        findViewById(R.id.dialog_share_btn_copy).setOnClickListener(this);
        findViewById(R.id.dialog_share_btn_wb).setOnClickListener(this);
        findViewById(R.id.dialog_share_btn_close).setOnClickListener(this);
        findViewById(R.id.yejianmoshi).setOnClickListener(this);
        findViewById(R.id.zitishezhi).setOnClickListener(this);
        if (this.flag) {
            findViewById(R.id.view_line).setVisibility(0);
            findViewById(R.id.line_two).setVisibility(0);
        } else {
            findViewById(R.id.view_line).setVisibility(8);
            findViewById(R.id.line_two).setVisibility(8);
        }
        if (this.mShowCopy == 1) {
            findViewById(R.id.line_three).setVisibility(0);
        } else {
            findViewById(R.id.line_three).setVisibility(8);
        }
        if (this.onlyWeiChat) {
            findViewById(R.id.dialog_share_btn_wxcircle).setVisibility(8);
            findViewById(R.id.dialog_share_btn_wb).setVisibility(8);
            findViewById(R.id.dialog_share_btn_qq).setVisibility(8);
        }
        if (SkinManager.getCurrentSkinType(this.context) == 1) {
            ((TextView) findViewById(R.id.yejianmoshi)).setText("日间模式");
        } else {
            ((TextView) findViewById(R.id.yejianmoshi)).setText("夜间模式");
        }
    }

    public DialogShare(Context context, onDialogShareClickListener clickListener, boolean flag) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.mShowCopy = 0;
        this.context = context;
        this.clickListener = clickListener;
        this.flag = flag;
    }

    public DialogShare(Context context, onDialogShareClickListener clickListener, boolean flag, boolean onlyWeiChat) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.mShowCopy = 0;
        this.context = context;
        this.clickListener = clickListener;
        this.flag = flag;
        this.onlyWeiChat = onlyWeiChat;
    }

    public DialogShare(Context context, onDialogShareClickListener clickListener, int showCopy) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
        this.clickListener = clickListener;
        this.mShowCopy = showCopy;
    }
}
