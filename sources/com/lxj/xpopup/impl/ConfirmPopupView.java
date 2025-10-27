package com.lxj.xpopup.impl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;

/* loaded from: classes4.dex */
public class ConfirmPopupView extends CenterPopupView implements View.OnClickListener {
    OnCancelListener cancelListener;
    CharSequence cancelText;
    OnConfirmListener confirmListener;
    CharSequence confirmText;
    CharSequence content;
    View divider1;
    View divider2;
    EditText et_input;
    CharSequence hint;
    public boolean isHideCancel;
    CharSequence title;
    TextView tv_cancel;
    TextView tv_confirm;
    TextView tv_content;
    TextView tv_title;

    public ConfirmPopupView(@NonNull Context context, int i2) {
        super(context);
        this.isHideCancel = false;
        this.bindLayoutId = i2;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public void applyDarkTheme() {
        super.applyDarkTheme();
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i2 = R.color._xpopup_white_color;
        textView.setTextColor(resources.getColor(i2));
        this.tv_content.setTextColor(getResources().getColor(i2));
        this.tv_cancel.setTextColor(getResources().getColor(i2));
        this.tv_confirm.setTextColor(getResources().getColor(i2));
        View view = this.divider1;
        if (view != null) {
            view.setBackgroundColor(getResources().getColor(R.color._xpopup_list_dark_divider));
        }
        View view2 = this.divider2;
        if (view2 != null) {
            view2.setBackgroundColor(getResources().getColor(R.color._xpopup_list_dark_divider));
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public void applyLightTheme() {
        super.applyLightTheme();
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i2 = R.color._xpopup_content_color;
        textView.setTextColor(resources.getColor(i2));
        this.tv_content.setTextColor(getResources().getColor(i2));
        this.tv_cancel.setTextColor(Color.parseColor("#666666"));
        this.tv_confirm.setTextColor(XPopup.getPrimaryColor());
        View view = this.divider1;
        if (view != null) {
            view.setBackgroundColor(getResources().getColor(R.color._xpopup_list_divider));
        }
        View view2 = this.divider2;
        if (view2 != null) {
            view2.setBackgroundColor(getResources().getColor(R.color._xpopup_list_divider));
        }
    }

    public TextView getCancelTextView() {
        return (TextView) findViewById(R.id.tv_cancel);
    }

    public TextView getConfirmTextView() {
        return (TextView) findViewById(R.id.tv_confirm);
    }

    public TextView getContentTextView() {
        return (TextView) findViewById(R.id.tv_content);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        int i2 = this.bindLayoutId;
        return i2 != 0 ? i2 : R.layout._xpopup_center_impl_confirm;
    }

    public TextView getTitleTextView() {
        return (TextView) findViewById(R.id.tv_title);
    }

    @Override // android.view.View.OnClickListener
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
            OnConfirmListener onConfirmListener = this.confirmListener;
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm();
            }
            if (this.popupInfo.autoDismiss.booleanValue()) {
                dismiss();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_content = (TextView) findViewById(R.id.tv_content);
        this.tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        this.tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        this.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        this.et_input = (EditText) findViewById(R.id.et_input);
        this.divider1 = findViewById(R.id.xpopup_divider1);
        this.divider2 = findViewById(R.id.xpopup_divider2);
        this.tv_cancel.setOnClickListener(this);
        this.tv_confirm.setOnClickListener(this);
        if (TextUtils.isEmpty(this.title)) {
            this.tv_title.setVisibility(8);
        } else {
            this.tv_title.setText(this.title);
        }
        if (TextUtils.isEmpty(this.content)) {
            this.tv_content.setVisibility(8);
        } else {
            this.tv_content.setText(this.content);
        }
        if (!TextUtils.isEmpty(this.cancelText)) {
            this.tv_cancel.setText(this.cancelText);
        }
        if (!TextUtils.isEmpty(this.confirmText)) {
            this.tv_confirm.setText(this.confirmText);
        }
        if (this.isHideCancel) {
            this.tv_cancel.setVisibility(8);
            View view = this.divider2;
            if (view != null) {
                view.setVisibility(8);
            }
        }
        applyTheme();
    }

    public ConfirmPopupView setCancelText(CharSequence charSequence) {
        this.cancelText = charSequence;
        return this;
    }

    public ConfirmPopupView setConfirmText(CharSequence charSequence) {
        this.confirmText = charSequence;
        return this;
    }

    public ConfirmPopupView setListener(OnConfirmListener onConfirmListener, OnCancelListener onCancelListener) {
        this.cancelListener = onCancelListener;
        this.confirmListener = onConfirmListener;
        return this;
    }

    public ConfirmPopupView setTitleContent(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.title = charSequence;
        this.content = charSequence2;
        this.hint = charSequence3;
        return this;
    }
}
