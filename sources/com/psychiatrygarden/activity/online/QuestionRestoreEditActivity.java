package com.psychiatrygarden.activity.online;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionRestoreEditActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private EditText et_restore;
    private TextView iv_restore_question;
    private QuestionDetailBean tagQuestionDetailBean;
    private TextView tv_to_next;
    private String restore_analyze = "";
    private String question_id = "";
    private String identity_id = "";
    private String type = "";
    private String rulesUrl = "";

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) ErrorCorrectionRuleActivity.class);
        intent.putExtra("title", "规则");
        intent.putExtra("rulesUrl", this.rulesUrl);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (TextUtils.isEmpty(this.et_restore.getText().toString())) {
            ToastUtil.shortToast(this.mContext, "编辑内容不能为空！");
            return;
        }
        Intent intent = new Intent(this, (Class<?>) QuestionRestoreSubmitActivity.class);
        intent.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, this.restore_analyze);
        intent.putExtra("content", this.et_restore.getText().toString());
        intent.putExtra("question_id", this.question_id);
        intent.putExtra("identity_id", this.identity_id + "");
        intent.putExtra("type", this.type + "");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        showProgressDialog();
        QuestionDataRequest.getIntance(this.mContext).questionInfo(this.question_id, "1", this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("content");
        this.question_id = getIntent().getStringExtra("question_id");
        this.identity_id = getIntent().getStringExtra("identity_id");
        this.type = getIntent().getStringExtra("type");
        this.restore_analyze = getIntent().getStringExtra(CommonParameter.QUESTION_RESTORE_ANALYZE);
        this.mBtnActionbarRight.setText("规则");
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_restore_question = (TextView) findViewById(R.id.iv_restore_question);
        this.tv_to_next = (TextView) findViewById(R.id.tv_to_next);
        this.et_restore = (EditText) findViewById(R.id.et_restore);
        if (this.restore_analyze.equals(CommonParameter.QUESTION_RESTORE)) {
            setTitle("考点还原");
            this.et_restore.setHint("请输入您要编辑的考点还原。");
        } else {
            setTitle("答案解析");
            this.et_restore.setHint("请输入您要编辑的答案解析。");
        }
        if (!TextUtils.isEmpty(stringExtra)) {
            this.et_restore.setText(stringExtra);
            this.et_restore.setSelection(stringExtra.length() - 1);
        }
        if (SkinManager.getCurrentSkinType(this) == 0) {
            findViewById(R.id.iv_bottom_shadow).setVisibility(0);
        } else {
            findViewById(R.id.iv_bottom_shadow).setVisibility(4);
        }
        QuestionDataRequest.getIntance(this.mContext).getCorrectionRules(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) {
        if (EventBusConstant.EVENT_QUESTION_ERROR_CORRECTION_OK.equals(event.getKey())) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
        hideProgressDialog();
        ToastUtil.shortToast(this.mContext, strMsg + "");
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_restore_edit);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.k1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13435c.lambda$setListenerForWidget$0(view);
            }
        });
        this.tv_to_next.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.l1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13439c.lambda$setListenerForWidget$1(view);
            }
        });
        this.iv_restore_question.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.m1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13447c.lambda$setListenerForWidget$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.questionInfoApi)) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                hideProgressDialog();
                if (jSONObject.optInt("code") != 200) {
                    ToastUtil.shortToast(this.mContext, jSONObject.optString("message"));
                    return;
                }
                QuestionDetailBean questionDetailBean = (QuestionDetailBean) new Gson().fromJson(DesUtil.decode(CommonParameter.DES_KEY_VERIFY, jSONObject.optString("data")), QuestionDetailBean.class);
                this.tagQuestionDetailBean = questionDetailBean;
                if (TextUtils.isEmpty(questionDetailBean.getSort())) {
                    this.tagQuestionDetailBean.setSort("1");
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("question_id", this.question_id);
                ajaxParams.put("module_type", "1");
                QuestionDataRequest.getIntance(this.mContext).questionUserAnswer(ajaxParams, this);
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                hideProgressDialog();
                return;
            }
        }
        if (!requstUrl.equals(NetworkRequestsURL.questionUserAnswerApi)) {
            if (requstUrl.equals(NetworkRequestsURL.getCorrectionRulesURL)) {
                try {
                    JSONObject jSONObject2 = new JSONObject(s2);
                    if (jSONObject2.optInt("code") == 200) {
                        String strOptString = jSONObject2.optString("data");
                        this.rulesUrl = strOptString;
                        if (TextUtils.isEmpty(strOptString) || this.rulesUrl.equals(StrPool.EMPTY_JSON)) {
                            this.mBtnActionbarRight.setVisibility(8);
                        } else {
                            this.mBtnActionbarRight.setVisibility(0);
                        }
                    }
                    return;
                } catch (Exception e3) {
                    this.mBtnActionbarRight.setVisibility(8);
                    e3.printStackTrace();
                    return;
                }
            }
            return;
        }
        try {
            hideProgressDialog();
            JSONObject jSONObject3 = new JSONObject(s2);
            if (jSONObject3.optInt("code") != 200) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(this.tagQuestionDetailBean);
                ProjectApp.showQuestionList = new Gson().toJson(arrayList);
                Bundle bundle = new Bundle();
                bundle.putInt("position", 0);
                bundle.putString("module_type", "1");
                bundle.putString("subject_title", this.tagQuestionDetailBean.getChapter_parent_title());
                bundle.putString("chapter_title", this.tagQuestionDetailBean.getChapter_title());
                AnswerQuestionActivity.gotoActivity(this.mContext, bundle);
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            if (jSONObject3.getJSONArray("data").length() > 0) {
                this.tagQuestionDetailBean.setUser_answer(jSONObject3.getJSONArray("data").getJSONObject(0).optString("answer"));
            }
            arrayList2.add(this.tagQuestionDetailBean);
            ProjectApp.showQuestionList = new Gson().toJson(arrayList2);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("position", 0);
            bundle2.putString("module_type", "1");
            bundle2.putString("subject_title", this.tagQuestionDetailBean.getChapter_parent_title());
            bundle2.putString("chapter_title", this.tagQuestionDetailBean.getChapter_title());
            AnswerQuestionActivity.gotoActivity(this.mContext, bundle2);
        } catch (Exception e4) {
            e4.printStackTrace();
            hideProgressDialog();
        }
    }
}
