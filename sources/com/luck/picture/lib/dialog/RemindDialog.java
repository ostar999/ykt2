package com.luck.picture.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.luck.picture.lib.R;

/* loaded from: classes4.dex */
public class RemindDialog extends Dialog implements View.OnClickListener {
    public RemindDialog(Context context, String str) {
        super(context, R.style.Picture_Theme_Dialog);
        setContentView(R.layout.ps_remind_dialog);
        TextView textView = (TextView) findViewById(R.id.btnOk);
        ((TextView) findViewById(R.id.tv_content)).setText(str);
        textView.setOnClickListener(this);
        setDialogSize();
    }

    private void setDialogSize() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -2;
        attributes.height = -2;
        attributes.gravity = 17;
        getWindow().setWindowAnimations(R.style.PictureThemeDialogWindowStyle);
        getWindow().setAttributes(attributes);
    }

    public static void showTipsDialog(Context context, String str) {
        new RemindDialog(context, str).show();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btnOk) {
            dismiss();
        }
    }
}
