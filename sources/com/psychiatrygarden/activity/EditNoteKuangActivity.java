package com.psychiatrygarden.activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.NotesBeanBei;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBeanBeiDao;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SdkConstant;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class EditNoteKuangActivity extends BaseActivity {
    private EditText edit_note;
    private NotesBeanBei mNotesBean;
    private long question_id;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mNotesBean == null && this.edit_note.getText().toString().trim().equals("")) {
            AlertToast("笔记不能为空");
            return;
        }
        if (this.edit_note.getText().toString().trim().equals("")) {
            ProjectApp.mDaoSession.getNotesBeanBeiDao().deleteByKey(Long.valueOf(this.question_id));
            ProjectApp.mDaoSession.getSubmitNotesBeanBeiDao().queryBuilder().where(SubmitAnsweredQuestionBeanBeiDao.Properties.Question_app_id.eq(this.question_id + SdkConstant.UMENG_ALIS + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext)), new WhereCondition[0]).buildDelete();
            EventBus.getDefault().post("QuestionDetailActivity_note_delete_Bei");
            EventBus.getDefault().post("SubjectQuestionClearn_bei");
            clearNote();
        }
    }

    public void clearNote() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id + "");
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("module_type", "4");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mClearNoteURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.EditNoteKuangActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                EditNoteKuangActivity.this.finish();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                LogUtils.e(EditNoteKuangActivity.this.TAG, t2);
                try {
                    if (new JSONObject(t2).optString("code").equals("200")) {
                        EditNoteKuangActivity.this.AlertToast("清除成功");
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                EditNoteKuangActivity.this.finish();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, android.app.Activity
    public void finish() {
        super.finish();
        hideInputMethod();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void hideInputMethod() {
        View currentFocus = getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager == null || currentFocus == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.question_id = getIntent().getLongExtra("question_id", 0L);
        this.mNotesBean = ProjectApp.mDaoSession.getNotesBeanBeiDao().load(Long.valueOf(this.question_id));
        this.edit_note = (EditText) findViewById(R.id.edit_note);
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.b9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11102c.lambda$init$0(view);
            }
        });
        NotesBeanBei notesBeanBei = this.mNotesBean;
        if (notesBeanBei != null) {
            this.edit_note.setText(notesBeanBei.getContent());
            this.edit_note.setSelection(this.mNotesBean.getContent().length());
        }
        this.edit_note.setVisibility(0);
        new Timer().schedule(new TimerTask() { // from class: com.psychiatrygarden.activity.EditNoteKuangActivity.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                ((InputMethodManager) EditNoteKuangActivity.this.edit_note.getContext().getSystemService("input_method")).showSoftInput(EditNoteKuangActivity.this.edit_note, 0);
            }
        }, 500L);
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

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_editnote);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
