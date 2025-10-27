package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;
import java.util.Objects;

/* loaded from: classes6.dex */
public class DialogLeaveMessage extends AlertDialog implements View.OnClickListener, DialogInterface.OnDismissListener {
    private final ClickCallBack clickCallBack;
    private String content;
    private final Context context;
    private EditText et_content;
    private String title;
    private TextView tv_dialog_ok;

    public interface ClickCallBack {
        void callback(String message);
    }

    public DialogLeaveMessage(Context context, ClickCallBack clickCallBack, String title, String content) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.clickCallBack = clickCallBack;
        this.title = title;
        this.content = content;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        EditText editText = this.et_content;
        if (editText != null) {
            editText.setFocusable(true);
            this.et_content.setFocusableInTouchMode(true);
            this.et_content.requestFocus();
            ((InputMethodManager) this.context.getSystemService("input_method")).showSoftInput(this.et_content, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOkButtonColor(String txt) {
        if (txt.isEmpty()) {
            this.tv_dialog_ok.setTextColor(this.context.getTheme().obtainStyledAttributes(new int[]{R.attr.forth_txt_color}).getColor(0, -16777216));
        } else {
            this.tv_dialog_ok.setTextColor(this.context.getTheme().obtainStyledAttributes(new int[]{R.attr.first_txt_color}).getColor(0, -16777216));
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        Window window = getWindow();
        Objects.requireNonNull(window);
        View viewPeekDecorView = window.peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        super.dismiss();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_dialog_cancel) {
            dismiss();
        } else {
            if (id != R.id.tv_dialog_ok) {
                return;
            }
            ClickCallBack clickCallBack = this.clickCallBack;
            if (clickCallBack != null) {
                clickCallBack.callback(this.et_content.getText().toString());
            }
            dismiss();
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_note_input_new);
        Window window = getWindow();
        Objects.requireNonNull(window);
        window.setWindowAnimations(R.style.popupAnimation);
        getWindow().setGravity(80);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        TextView textView = (TextView) findViewById(R.id.tv_dialog_cancel);
        this.tv_dialog_ok = (TextView) findViewById(R.id.tv_dialog_ok);
        ((TextView) findViewById(R.id.tv_title)).setText(this.title);
        this.et_content = (EditText) findViewById(R.id.dialog_input_et_comment);
        if (!TextUtils.isEmpty(this.content)) {
            this.et_content.setText(this.content);
            setOkButtonColor(this.content);
        }
        this.et_content.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.DialogLeaveMessage.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                DialogLeaveMessage.this.setOkButtonColor(s2.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.tv_dialog_ok.setOnClickListener(this);
        textView.setOnClickListener(this);
        setScreen();
        this.et_content.post(new Runnable() { // from class: com.psychiatrygarden.widget.t7
            @Override // java.lang.Runnable
            public final void run() {
                this.f16926c.lambda$onCreate$0();
            }
        });
        setOnDismissListener(this);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        dismiss();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public void setScreen() {
        Window window = getWindow();
        Objects.requireNonNull(window);
        window.setLayout(-1, ScreenUtil.getPxByDp(this.context, 250));
    }
}
