package com.psychiatrygarden.activity.online.popwindow;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class QuestionRestoreEditPopWindow extends BottomPopupView {
    private RestoreClickIml restoreClickIml;

    public interface RestoreClickIml {
        void mEditMethod();

        void mReEditMethod();
    }

    public QuestionRestoreEditPopWindow(@NonNull Context context, RestoreClickIml restoreClickIml) {
        super(context);
        this.restoreClickIml = restoreClickIml;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_restore_edit_bottom;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tv_content_yuan);
        TextView textView2 = (TextView) findViewById(R.id.popu_cancel);
        TextView textView3 = (TextView) findViewById(R.id.tv_content_re);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                QuestionRestoreEditPopWindow.this.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                QuestionRestoreEditPopWindow.this.dismiss();
                QuestionRestoreEditPopWindow.this.restoreClickIml.mEditMethod();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                QuestionRestoreEditPopWindow.this.dismiss();
                QuestionRestoreEditPopWindow.this.restoreClickIml.mReEditMethod();
            }
        });
    }
}
