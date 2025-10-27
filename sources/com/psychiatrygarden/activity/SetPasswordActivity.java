package com.psychiatrygarden.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.AlphaButton;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/* loaded from: classes5.dex */
public class SetPasswordActivity extends BaseActivity {
    private AlphaButton btnConfirm;
    private String code;
    private EditText etConfirmPassword;
    private EditText etPassword;
    private Disposable mDisposable;
    private String phone;
    private Drawable enableDrawable = null;
    private Drawable disableDrawable = null;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (this.code == null || this.phone == null) {
            ToastUtil.shortToast(view.getContext(), "手机号或者验证码有误");
            return;
        }
        String string = this.etPassword.getText().toString();
        String string2 = this.etConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
            ToastUtil.shortToast(this, "请输入密码");
            return;
        }
        if (!TextUtils.equals(string, string2)) {
            ToastUtil.shortToast(view.getContext(), "两次输入密码不相同，请重新输入");
            return;
        }
        Intent intent = new Intent();
        intent.setClass(view.getContext(), RegisterInfoActivity.class);
        intent.putExtra("socialite_username", getIntent().getStringExtra("socialite_username"));
        intent.putExtra("mobile", this.phone.trim());
        intent.putExtra(Constants.JumpUrlConstants.URL_KEY_OPENID, getIntent().getStringExtra(Constants.JumpUrlConstants.URL_KEY_OPENID));
        intent.putExtra(CommonParameter.password, string);
        intent.putExtra("code", this.code);
        startActivityForResult(intent, 999);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean lambda$setListenerForWidget$1(CharSequence charSequence, CharSequence charSequence2) throws Exception {
        return Boolean.valueOf(!TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(charSequence2) && TextUtils.equals(charSequence, charSequence2) && charSequence.toString().length() >= 6 && charSequence.toString().length() <= 16 && charSequence2.toString().length() >= 6 && charSequence2.toString().length() <= 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(Boolean bool) throws Exception {
        this.btnConfirm.setEnabled(bool.booleanValue());
        this.btnConfirm.setClickable(bool.booleanValue());
        this.btnConfirm.setBackground(bool.booleanValue() ? this.enableDrawable : this.disableDrawable);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.phone = getIntent().getStringExtra(AliyunLogCommon.TERMINAL_TYPE);
        this.code = getIntent().getStringExtra("code");
        setTitle("设置密码");
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.red_round_coner, R.attr.login_btn_en_disable});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            this.enableDrawable = drawable;
        }
        if (drawable2 != null) {
            this.disableDrawable = drawable2;
            this.btnConfirm.setEnabled(false);
            this.btnConfirm.setBackground(drawable2);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_set_pasword);
        this.etPassword = (EditText) findViewById(R.id.et_password);
        this.etConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        this.btnConfirm = (AlphaButton) findViewById(R.id.btn_confirm);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.btnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.wj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14156c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mDisposable = Observable.combineLatest(RxTextView.textChanges(this.etPassword), RxTextView.textChanges(this.etConfirmPassword), new BiFunction() { // from class: com.psychiatrygarden.activity.xj
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return SetPasswordActivity.lambda$setListenerForWidget$1((CharSequence) obj, (CharSequence) obj2);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.yj
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f14220c.lambda$setListenerForWidget$2((Boolean) obj);
            }
        });
    }
}
