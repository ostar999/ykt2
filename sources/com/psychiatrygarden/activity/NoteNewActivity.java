package com.psychiatrygarden.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class NoteNewActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private EditText mEditNote;
    private String mModuleType = "1";

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) throws JSONException {
        if (CommonUtil.isFastClick()) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_type", "" + this.mModuleType);
        if (TextUtils.isEmpty(this.mEditNote.getText().toString())) {
            ajaxParams.put("question_id", "" + getIntent().getExtras().getString("question_id"));
            QuestionDataRequest.getIntance(this).questionClearNoteData(ajaxParams, this);
            return;
        }
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("question_id", getIntent().getExtras().getString("question_id"));
            jSONObject.put("content", this.mEditNote.getText().toString());
            jSONObject.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this, "1"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        jSONArray.put(jSONObject);
        ajaxParams.put("note", jSONArray.toString());
        QuestionDataRequest.getIntance(this).questionSACNoteData(ajaxParams, this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, android.app.Activity
    public void finish() {
        super.finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mModuleType = getIntent().getExtras().getString("module_type");
        ScrollView scrollView = (ScrollView) findViewById(R.id.sv_note);
        this.mEditNote = (EditText) findViewById(R.id.edit_note);
        String string = getIntent().getExtras().getString("notestr");
        if (string != null && !"".equals(string)) {
            this.mEditNote.setText(string);
            this.mEditNote.setSelection(string.length());
        }
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.se
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13828c.lambda$init$0(view);
            }
        });
        scrollView.setVisibility(8);
        this.mEditNote.setVisibility(0);
    }

    public void initView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(18);
        setTitle("笔记");
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("保存");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_note_course_chapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        if (requstUrl.equals(NetworkRequestsURL.getpostNoteApi) || requstUrl.equals(NetworkRequestsURL.clearNoteApi)) {
            EventBus.getDefault().post(new NoteEventBean(this.mEditNote.getText().toString(), true, getIntent().getExtras().getString("question_id")));
            finish();
        }
    }
}
