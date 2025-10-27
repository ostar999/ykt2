package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.utils.KeyboardInputUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class UrlLinkActivity extends Dialog implements View.OnClickListener {
    private TextView btn_negative;
    private TextView btn_positive;
    private Context context;
    private String id;
    private ClearEditText linkText;
    private ClearEditText linkURL;
    private OnDialogClickListener mOnDialogClickListener;
    private RelativeLayout relView;
    private String url;
    private String urlname;
    private View viewRoot;
    private Window window;

    public interface OnDialogClickListener {
        void onConfirmButtonClick(String id, String name, String url);
    }

    public UrlLinkActivity(final Context context, OnDialogClickListener mOnDialogClickListener, String id, String url, String urlname) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
        this.mOnDialogClickListener = mOnDialogClickListener;
        this.id = id;
        this.url = url;
        this.urlname = urlname;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.activity_linkurl, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        this.linkURL = (ClearEditText) viewFindViewById.findViewById(R.id.linkURL);
        this.linkText = (ClearEditText) this.viewRoot.findViewById(R.id.linkText);
        if (!TextUtils.isEmpty(url)) {
            this.linkURL.setText(url);
        }
        if (!TextUtils.isEmpty(urlname)) {
            this.linkText.setText(urlname);
        }
        this.btn_negative = (TextView) this.viewRoot.findViewById(R.id.btn_negative);
        this.btn_positive = (TextView) this.viewRoot.findViewById(R.id.btn_positive);
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(urlname)) {
            this.btn_positive.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#7380A9" : "#141516"));
        }
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.aj
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16325c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        this.btn_positive.setOnClickListener(this);
        this.btn_negative.setOnClickListener(this);
        this.linkText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.UrlLinkActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (UrlLinkActivity.this.linkText.getText().toString().trim().length() <= 0 || UrlLinkActivity.this.linkURL.getText().toString().trim().length() <= 0) {
                    UrlLinkActivity.this.btn_positive.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(UrlLinkActivity.this.getContext()) == 1 ? "#454C64" : "#C2C6CB"));
                } else {
                    UrlLinkActivity.this.btn_positive.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(UrlLinkActivity.this.getContext()) == 1 ? "#7380A9" : "#141516"));
                }
            }
        });
        this.linkURL.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.UrlLinkActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                if (UrlLinkActivity.this.linkText.getText().toString().trim().length() <= 0 || UrlLinkActivity.this.linkURL.getText().toString().trim().length() <= 0) {
                    UrlLinkActivity.this.btn_positive.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(UrlLinkActivity.this.getContext()) == 1 ? "#454C64" : "#C2C6CB"));
                } else {
                    UrlLinkActivity.this.btn_positive.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(UrlLinkActivity.this.getContext()) == 1 ? "#7380A9" : "#141516"));
                }
            }
        });
        this.linkURL.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.bj
            @Override // java.lang.Runnable
            public final void run() {
                this.f16352c.lambda$new$1(context);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(12);
        this.viewRoot.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Context context) {
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
        KeyboardInputUtil.showKeyBoard(context);
    }

    public void CommOkData() {
        View viewPeekDecorView = getWindow().peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        dismiss();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        View viewPeekDecorView = getWindow().peekDecorView();
        if (viewPeekDecorView != null) {
            ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
        }
        super.dismiss();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() == R.id.btn_positive) {
            String strTrim = this.linkText.getText().toString().trim();
            if (TextUtils.isEmpty(this.linkURL.getText().toString().trim())) {
                ToastUtil.shortToast(getContext(), "请输入链接地址");
                return;
            } else {
                if (TextUtils.isEmpty(strTrim)) {
                    ToastUtil.shortToast(getContext(), "请输入链接名称");
                    return;
                }
                this.mOnDialogClickListener.onConfirmButtonClick(this.id, this.linkText.getText().toString(), this.linkURL.getText().toString());
            }
        }
        CommOkData();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        getWindow().setAttributes(attributes);
        setCanceledOnTouchOutside(false);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}
