package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.psychiatrygarden.interfaceclass.BianjiInfaceInput;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes6.dex */
public class DialogAdminInput extends AlertDialog implements View.OnClickListener {
    private BianjiInfaceInput clickListener;
    private Context context;
    private EditText pushcontent;
    String pushcontentStr;
    private EditText pushno;
    String pushnoStr;
    private EditText pushoppn;
    String pushoppnStr;
    private EditText pushtime;
    String pushtimeStr;
    private Window window;

    public DialogAdminInput(Context context, BianjiInfaceInput clickListener, String pushtimeStr, String pushcontentStr, String pushoppnStr, String pushnoStr) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
        this.clickListener = clickListener;
        this.pushtimeStr = pushtimeStr;
        this.pushcontentStr = pushcontentStr;
        this.pushoppnStr = pushoppnStr;
        this.pushnoStr = pushnoStr;
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
        if (v2.getId() == R.id.bianji) {
            if (TextUtils.isEmpty(this.pushtime.getText().toString())) {
                NewToast.showShort(this.context, "评论时间不能为空", 0).show();
                return;
            }
            if (TextUtils.isEmpty(this.pushoppn.getText().toString())) {
                NewToast.showShort(this.context, "支持数不能为空", 0).show();
                return;
            }
            if (TextUtils.isEmpty(this.pushno.getText().toString())) {
                NewToast.showShort(this.context, "反对数不能为空", 0).show();
                return;
            }
            if (this.pushcontent.getText().toString().equals("") || this.pushcontent.getText().toString().trim().equals("")) {
                NewToast.showShort(this.context, "评价内容不能为空或空格", 0).show();
                return;
            }
            if (this.pushcontent.getText().toString().trim().length() < 5) {
                NewToast.showShort(this.context, "评论至少要输入5个字", 0).show();
                return;
            }
            try {
                long time = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(this.pushtime.getText().toString()).getTime() / 1000;
                this.clickListener.mBianjiInfaceInput(time + "", this.pushcontent.getText().toString(), this.pushoppn.getText().toString(), this.pushno.getText().toString());
                View viewPeekDecorView = getWindow().peekDecorView();
                if (viewPeekDecorView != null) {
                    ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(viewPeekDecorView.getWindowToken(), 0);
                }
                dismiss();
            } catch (Exception e2) {
                e2.printStackTrace();
                NewToast.showShort(this.context, "输入信息有误，请按照规定填写！", 0).show();
            }
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bianjiall);
        Window window = getWindow();
        this.window = window;
        window.setWindowAnimations(R.style.popupAnimation);
        this.window.setGravity(17);
        this.window.setLayout(CommonUtil.getScreenWidth(this.context), -2);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(4);
        this.pushtime = (EditText) findViewById(R.id.pushtime);
        this.pushcontent = (EditText) findViewById(R.id.pushcontent);
        this.pushoppn = (EditText) findViewById(R.id.pushoppn);
        this.pushno = (EditText) findViewById(R.id.pushno);
        try {
            this.pushtime.setText(CommonUtil.getDate(this.pushtimeStr));
            this.pushcontent.setText(this.pushcontentStr);
            this.pushoppn.setText(this.pushoppnStr);
            this.pushno.setText(this.pushnoStr);
        } catch (Exception unused) {
            NewToast.showShort(this.context, "数据信息有误，可能不能编辑成功,请联系工作人员", 0).show();
        }
        this.pushcontent.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.DialogAdminInput.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() >= 5000) {
                    NewToast.showShort(DialogAdminInput.this.context, "超出字数限制", 0).show();
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        findViewById(R.id.bianji).setOnClickListener(this);
    }
}
