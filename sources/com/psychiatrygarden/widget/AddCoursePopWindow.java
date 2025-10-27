package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class AddCoursePopWindow extends BottomPopupView {
    private String content;
    private NoteClickIml mNoteClickIml;

    public interface NoteClickIml {
        void mDoClickMethod(String content);
    }

    public AddCoursePopWindow(@NonNull Context context, String content, NoteClickIml mNoteClickIml) {
        super(context);
        this.mNoteClickIml = mNoteClickIml;
        this.content = content;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(EditText editText, View view) {
        String strTrim = editText.getText().toString().trim();
        if (strTrim.length() > 5000) {
            NewToast.showShort(getContext(), "评价内容不能超过5000字", 0).show();
        } else {
            this.mNoteClickIml.mDoClickMethod(strTrim);
            dismiss();
        }
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_video_add_comment_input;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        final EditText editText = (EditText) findViewById(R.id.dialog_input_et_comment);
        final TextView textView = (TextView) findViewById(R.id.pushBtm);
        editText.setText(this.content);
        editText.setSelection(this.content.length());
        textView.setSelected(!TextUtils.isEmpty(this.content));
        editText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.AddCoursePopWindow.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                String string = s2.toString();
                if (string.length() < 1 || string.length() > 5000) {
                    textView.setSelected(false);
                } else {
                    textView.setSelected(true);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16385c.lambda$onCreate$0(editText, view);
            }
        });
    }
}
