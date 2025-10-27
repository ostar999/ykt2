package com.lxj.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class InputConfirmPopupView extends ConfirmPopupView implements View.OnClickListener {
    OnCancelListener cancelListener;
    OnInputConfirmListener inputConfirmListener;
    public CharSequence inputContent;

    public InputConfirmPopupView(@NonNull Context context, int i2) {
        super(context, i2);
    }

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public void applyDarkTheme() {
        super.applyDarkTheme();
        this.et_input.setHintTextColor(Color.parseColor("#888888"));
        this.et_input.setTextColor(Color.parseColor("#dddddd"));
    }

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public void applyLightTheme() {
        super.applyLightTheme();
        this.et_input.setHintTextColor(Color.parseColor("#888888"));
        this.et_input.setTextColor(Color.parseColor("#333333"));
    }

    public EditText getEditText() {
        return this.et_input;
    }

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.tv_cancel) {
            OnCancelListener onCancelListener = this.cancelListener;
            if (onCancelListener != null) {
                onCancelListener.onCancel();
            }
            dismiss();
            return;
        }
        if (view == this.tv_confirm) {
            OnInputConfirmListener onInputConfirmListener = this.inputConfirmListener;
            if (onInputConfirmListener != null) {
                onInputConfirmListener.onConfirm(this.et_input.getText().toString().trim());
            }
            if (this.popupInfo.autoDismiss.booleanValue()) {
                dismiss();
            }
        }
    }

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.et_input.setVisibility(0);
        if (!TextUtils.isEmpty(this.hint)) {
            this.et_input.setHint(this.hint);
        }
        if (!TextUtils.isEmpty(this.inputContent)) {
            this.et_input.setText(this.inputContent);
            this.et_input.setSelection(this.inputContent.length());
        }
        XPopupUtils.setCursorDrawableColor(this.et_input, XPopup.getPrimaryColor());
        this.et_input.post(new Runnable() { // from class: com.lxj.xpopup.impl.InputConfirmPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                InputConfirmPopupView.this.et_input.setBackgroundDrawable(XPopupUtils.createSelector(XPopupUtils.createBitmapDrawable(InputConfirmPopupView.this.getResources(), InputConfirmPopupView.this.et_input.getMeasuredWidth(), Color.parseColor("#888888")), XPopupUtils.createBitmapDrawable(InputConfirmPopupView.this.getResources(), InputConfirmPopupView.this.et_input.getMeasuredWidth(), XPopup.getPrimaryColor())));
            }
        });
    }

    public void setListener(OnInputConfirmListener onInputConfirmListener, OnCancelListener onCancelListener) {
        this.cancelListener = onCancelListener;
        this.inputConfirmListener = onInputConfirmListener;
    }
}
