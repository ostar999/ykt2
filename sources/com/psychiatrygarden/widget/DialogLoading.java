package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DialogLoading extends AlertDialog {
    private Context context;
    private TextView dialog_text;
    private int type;

    public DialogLoading(Context context) {
        super(context, R.style.MyDialog);
        this.type = 0;
        this.context = context;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int getType() {
        return this.type;
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download_progress);
        setCancelable(false);
        this.dialog_text = (TextView) findViewById(R.id.dialog_text);
    }

    @Override // android.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getAction() == 0 && this.type != 1) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setMessage(String msg) {
        if (msg != null && !msg.equals("")) {
            this.dialog_text.setVisibility(0);
        }
        this.dialog_text.setText(msg);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void show(String text) {
        try {
            super.show();
            this.dialog_text.setText(text);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setMessage(int msgID) {
        this.dialog_text.setVisibility(0);
        this.dialog_text.setText(msgID);
    }
}
