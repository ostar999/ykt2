package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DialogAcitivtyInput extends AlertDialog implements View.OnClickListener {
    private onDialogClickListener clickListener;
    private Context context;
    private ClearEditText ed_redbook_code;
    private Window window;

    public DialogAcitivtyInput(Context context, onDialogClickListener clickListener) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        View viewPeekDecorView = getWindow().peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        super.dismiss();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.bt_duihuan) {
            if (TextUtils.isEmpty(this.ed_redbook_code.getText().toString())) {
                NewToast.showShort(this.context, "请输入兑换码", 0).show();
                return;
            }
            this.clickListener.onclickStringBack(this.ed_redbook_code.getText().toString(), "", "");
            View viewPeekDecorView = getWindow().peekDecorView();
            if (viewPeekDecorView != null) {
                ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
            }
            dismiss();
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redbookcode);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        this.window = window;
        window.setWindowAnimations(R.style.popupAnimation);
        this.window.setGravity(80);
        this.window.setLayout(CommonUtil.getScreenWidth(this.context), -2);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(4);
        this.ed_redbook_code = (ClearEditText) findViewById(R.id.ed_redbook_code);
        ((AlphaButton) findViewById(R.id.bt_duihuan)).setOnClickListener(this);
    }
}
