package com.psychiatrygarden.activity.rank.pop;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ComSinglePopWindow extends CenterPopupView {
    public String content;
    public DialogListener mDialogListener;

    public interface DialogListener {
        void mDialogListener();
    }

    public ComSinglePopWindow(@NonNull Context context, DialogListener mDialogListener, String content) {
        super(context);
        this.mDialogListener = mDialogListener;
        this.content = content;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_comsingle_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.mclick);
        ((TextView) findViewById(R.id.text)).setText(this.content);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.pop.ComSinglePopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ComSinglePopWindow.this.dismiss();
                ComSinglePopWindow.this.mDialogListener.mDialogListener();
            }
        });
    }
}
