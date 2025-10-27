package com.aliyun.player.alivcplayerexpand.view.softinput;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.svideo.common.base.BaseDialogFragment;

/* loaded from: classes2.dex */
public class SoftInputDialogFragment extends BaseDialogFragment {
    private EditText mEditText;
    private OnBarrageSendClickListener mOnBarrageSendClickListener;
    private TextView mTextView;

    public interface OnBarrageSendClickListener {
        void onBarrageSendClick(String str);
    }

    private void initView() {
        this.mEditText.findFocus();
        this.mEditText.setFocusable(true);
        this.mEditText.setFocusableInTouchMode(true);
        this.mEditText.requestFocus();
        this.mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
        this.mEditText.setImeOptions(268435456);
        this.mEditText.setSingleLine(true);
        showSoftInput(this.mEditText);
        this.mEditText.addTextChangedListener(new TextWatcher() { // from class: com.aliyun.player.alivcplayerexpand.view.softinput.SoftInputDialogFragment.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                Editable text = SoftInputDialogFragment.this.mEditText.getText();
                String strTrim = text.toString().trim();
                int selectionEnd = Selection.getSelectionEnd(text);
                int i5 = 0;
                for (int i6 = 0; i6 < strTrim.length(); i6++) {
                    char cCharAt = strTrim.charAt(i6);
                    i5 = (cCharAt < ' ' || cCharAt > 'z') ? i5 + 2 : i5 + 1;
                    if (i5 > 40) {
                        SoftInputDialogFragment.this.mEditText.setText(strTrim.substring(0, i6));
                        Editable text2 = SoftInputDialogFragment.this.mEditText.getText();
                        if (selectionEnd > text2.length()) {
                            selectionEnd = text2.length();
                        }
                        Selection.setSelection(text2, selectionEnd);
                    }
                }
            }
        });
        this.mTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.softinput.SoftInputDialogFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SoftInputDialogFragment.this.mOnBarrageSendClickListener != null) {
                    SoftInputDialogFragment.this.mOnBarrageSendClickListener.onBarrageSendClick(SoftInputDialogFragment.this.mEditText.getText().toString());
                }
            }
        });
    }

    public static SoftInputDialogFragment newInstance() {
        return new SoftInputDialogFragment();
    }

    private void showSoftInput(EditText editText) {
        ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(editText, 0);
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public void bindView(View view) {
        this.mTextView = (TextView) view.findViewById(R.id.alivc_tv_input_send);
        this.mEditText = (EditText) view.findViewById(R.id.alivc_et_input_danmu);
        initView();
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getLayoutRes() {
        return R.layout.alivc_softinput_send_danmaku;
    }

    public void setOnBarrageSendClickListener(OnBarrageSendClickListener onBarrageSendClickListener) {
        this.mOnBarrageSendClickListener = onBarrageSendClickListener;
    }
}
