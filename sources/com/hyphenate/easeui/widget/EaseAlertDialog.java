package com.hyphenate.easeui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hyphenate.easeui.R;

/* loaded from: classes4.dex */
public class EaseAlertDialog extends Dialog {
    private Bundle bundle;
    private String msg;
    private boolean showCancel;
    private String title;
    private AlertDialogUser user;

    public interface AlertDialogUser {
        void onResult(boolean z2, Bundle bundle);
    }

    public EaseAlertDialog(Context context, int i2) {
        super(context);
        this.showCancel = false;
        this.title = context.getResources().getString(R.string.prompt);
        this.msg = context.getResources().getString(i2);
        setCanceledOnTouchOutside(true);
    }

    public void onCancel(View view) {
        dismiss();
        AlertDialogUser alertDialogUser = this.user;
        if (alertDialogUser != null) {
            alertDialogUser.onResult(false, this.bundle);
        }
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.ease_alert_dialog);
        Button button = (Button) findViewById(R.id.btn_cancel);
        Button button2 = (Button) findViewById(R.id.btn_ok);
        TextView textView = (TextView) findViewById(R.id.title);
        setTitle(this.title);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.hyphenate.easeui.widget.EaseAlertDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view.getId() == R.id.btn_ok) {
                    EaseAlertDialog.this.onOk(view);
                } else if (view.getId() == R.id.btn_cancel) {
                    EaseAlertDialog.this.onCancel(view);
                }
            }
        };
        button.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        String str = this.title;
        if (str != null) {
            textView.setText(str);
        }
        if (this.showCancel) {
            button.setVisibility(0);
        }
        if (this.msg != null) {
            ((TextView) findViewById(R.id.alert_message)).setText(this.msg);
        }
    }

    public void onOk(View view) {
        dismiss();
        AlertDialogUser alertDialogUser = this.user;
        if (alertDialogUser != null) {
            alertDialogUser.onResult(true, this.bundle);
        }
    }

    public EaseAlertDialog(Context context, String str) {
        super(context);
        this.showCancel = false;
        this.title = context.getResources().getString(R.string.prompt);
        this.msg = str;
        setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, int i2, int i3) {
        super(context);
        this.showCancel = false;
        this.title = context.getResources().getString(i2);
        this.msg = context.getResources().getString(i3);
        setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, String str, String str2) {
        super(context);
        this.showCancel = false;
        this.title = str;
        this.msg = str2;
        setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, int i2, int i3, Bundle bundle, AlertDialogUser alertDialogUser, boolean z2) {
        super(context);
        this.showCancel = false;
        this.title = context.getResources().getString(i2);
        this.msg = context.getResources().getString(i3);
        this.user = alertDialogUser;
        this.bundle = bundle;
        this.showCancel = z2;
        setCanceledOnTouchOutside(true);
    }

    public EaseAlertDialog(Context context, String str, String str2, Bundle bundle, AlertDialogUser alertDialogUser, boolean z2) {
        super(context);
        this.title = str;
        this.msg = str2;
        this.user = alertDialogUser;
        this.bundle = bundle;
        this.showCancel = z2;
        setCanceledOnTouchOutside(true);
    }
}
