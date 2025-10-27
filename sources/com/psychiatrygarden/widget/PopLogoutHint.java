package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PopLogoutHint extends CenterPopupView implements View.OnClickListener {
    private onDialogOkClickListener onDialogOkClickListener;
    private TextView tv_cancel;
    private TextView tv_ok;

    public interface onDialogOkClickListener {
        void onclickOk();
    }

    public PopLogoutHint(@NonNull Context context, onDialogOkClickListener onDialogOkClickListener2) {
        super(context);
        this.onDialogOkClickListener = onDialogOkClickListener2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        super.doAfterDismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_logout_hint;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getPopupWidth() {
        return XPopupUtils.getWindowWidth(getContext()) - ScreenUtil.getPxByDp(getContext(), 80);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_cancel) {
            dismiss();
        } else {
            if (id != R.id.tv_ok) {
                return;
            }
            this.onDialogOkClickListener.onclickOk();
            dismiss();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        this.tv_ok = (TextView) findViewById(R.id.tv_ok);
        this.tv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.kc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16648c.onClick(view);
            }
        });
        this.tv_ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.kc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16648c.onClick(view);
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }
}
