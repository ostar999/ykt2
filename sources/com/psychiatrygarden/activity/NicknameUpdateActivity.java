package com.psychiatrygarden.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.UserRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class NicknameUpdateActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private EditText mEtNickname;
    private TextView mTvNicknameDesc;
    private TextView mTvNicknameInfo;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        String string = this.mEtNickname.getText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtil.shortToast(this, "昵称不能为空");
        } else if (string.length() < 2 || string.length() > 12) {
            ToastUtil.shortToast(this, "昵称不符合字数限制");
        } else {
            UserRequest.getIntance(this).changeUserInfo(string, "", "", this);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        EditText editText = (EditText) findViewById(R.id.et_nickname);
        this.mEtNickname = editText;
        editText.setText(getIntent().getExtras().getString("nickname", ""));
        this.mTvNicknameInfo = (TextView) findViewById(R.id.tv_nickname_info);
        this.mTvNicknameDesc = (TextView) findViewById(R.id.tv_nickname_desc);
        UserRequest.getIntance(this).checkChangeNickname(this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideProgressDialog();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
        showProgressDialog();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mBtnActionbarRight.setText("完成");
        this.mBtnActionbarRight.setVisibility(0);
        setTitle("修改昵称");
        setContentView(R.layout.activity_nickname_update);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.qe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13734c.lambda$setListenerForWidget$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            hideProgressDialog();
            JSONObject jSONObject = new JSONObject(s2);
            if (requstUrl.equals(NetworkRequestsURL.checkChangeNickname)) {
                if (jSONObject.optString("code").equals("200")) {
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if (jSONObjectOptJSONObject == null) {
                        return;
                    }
                    String strOptString = jSONObjectOptJSONObject.optString("hint");
                    Log.e("NicknameUpdateActivity", "hintContent: " + strOptString);
                    if (jSONObjectOptJSONObject.optString("allow").equals("0")) {
                        this.mEtNickname.setKeyListener(null);
                        this.mTvNicknameInfo.setText(strOptString.replace("\\n", "\n"));
                        this.mTvNicknameInfo.setVisibility(0);
                        this.mTvNicknameDesc.setVisibility(8);
                        this.mBtnActionbarRight.setVisibility(8);
                    } else {
                        this.mTvNicknameInfo.setVisibility(0);
                        this.mTvNicknameDesc.setVisibility(0);
                        this.mTvNicknameInfo.setText("注意：");
                        this.mTvNicknameDesc.setText(strOptString.replace("\\n", "\n"));
                    }
                } else {
                    ToastUtil.shortToast(this, jSONObject.optString("message"));
                }
            } else if (requstUrl.equals(NetworkRequestsURL.changeUserInfo)) {
                if (jSONObject.optString("code").equals("200")) {
                    UserConfig.getInstance().getUser().setNickname(this.mEtNickname.getText().toString());
                    UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                    EventBus.getDefault().post("PersonalCenterFragment");
                    Intent intent = new Intent(this, (Class<?>) RegisterInfoActivity.class);
                    intent.putExtra("nickname", this.mEtNickname.getText().toString());
                    setResult(-1, intent);
                    finish();
                } else {
                    ToastUtil.shortToast(this, jSONObject.optString("message"));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
