package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import cn.hutool.core.util.RandomUtil;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

@SuppressLint({"ViewConstructor"})
/* loaded from: classes6.dex */
public class InputCodeExamPopup extends CenterPopupView {
    private AppCompatEditText etExamCode;
    private final ExamCodeConfirmListener listener;

    public interface ExamCodeConfirmListener {
        void onConfirm(String code);
    }

    public InputCodeExamPopup(@NonNull Context context, ExamCodeConfirmListener l2) {
        super(context);
        this.listener = l2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        String string = this.etExamCode.getText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtil.shortToast(getContext(), getContext().getString(R.string.pls_input_exam_code));
            return;
        }
        ExamCodeConfirmListener examCodeConfirmListener = this.listener;
        if (examCodeConfirmListener != null) {
            examCodeConfirmListener.onConfirm(string);
            dismiss();
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_popup_input_exam_code;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.etExamCode = (AppCompatEditText) findViewById(R.id.et_exam_code);
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.da
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16405c.lambda$onCreate$0(view);
            }
        });
        this.etExamCode.setKeyListener(DigitsKeyListener.getInstance(RandomUtil.BASE_NUMBER));
        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ea
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16435c.lambda$onCreate$1(view);
            }
        });
    }
}
