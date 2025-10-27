package com.psychiatrygarden.activity;

import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.NotesBean;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.SubmitAnsweredQuestionBeanDao;
import com.psychiatrygarden.bean.SubmitNotesBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class EditNoteActivity extends BaseActivity {
    private EditText edit_note;
    Handler mHandler = new Handler(new Handler.Callback() { // from class: com.psychiatrygarden.activity.a9
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return this.f10987c.lambda$new$0(message);
        }
    });
    private NotesBean mNotesBean;
    private long question_id;

    private void chaneData() throws SQLException {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mNotesBean == null && this.edit_note.getText().toString().trim().equals("")) {
            AlertToast("笔记不能为空");
            return;
        }
        if (this.edit_note.getText().toString().trim().equals("")) {
            ProjectApp.mDaoSession.getNotesBeanDao().deleteByKey(Long.valueOf(this.question_id));
            ProjectApp.mDaoSession.getSubmitNotesBeanDao().queryBuilder().where(SubmitAnsweredQuestionBeanDao.Properties.Question_app_id.eq(this.question_id + SdkConstant.UMENG_ALIS + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext)), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
            EventBus.getDefault().post("QuestionDetailActivity_note_delete" + this.question_id);
            this.mHandler.sendEmptyMessage(2);
            return;
        }
        QuestionInfoBean questionInfoBeanLoad = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().load(Long.valueOf(this.question_id));
        ProjectApp.mDaoSession.getNotesBeanDao().insertOrReplace(new NotesBean(Long.valueOf(this.question_id), questionInfoBeanLoad.getChapter_parent_id(), questionInfoBeanLoad.getChapter_id(), questionInfoBeanLoad.getYear(), questionInfoBeanLoad.getS_num(), questionInfoBeanLoad.getNumber_number(), this.edit_note.getText().toString(), questionInfoBeanLoad.getMedia_img(), questionInfoBeanLoad.getIsxueshuo(), questionInfoBeanLoad.getIszhuanshuo(), questionInfoBeanLoad.getIsgaopinkaodian(), questionInfoBeanLoad.getIs_practice(), questionInfoBeanLoad.getUnit(), questionInfoBeanLoad.getPart_id(), questionInfoBeanLoad.getPart_parent_id(), questionInfoBeanLoad.getPart_num_am(), questionInfoBeanLoad.getPart_num_pm(), questionInfoBeanLoad.getType()));
        ProjectApp.mDaoSession.getSubmitNotesBeanDao().insertOrReplace(new SubmitNotesBean(Long.valueOf(this.question_id), this.edit_note.getText().toString(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.question_id + SdkConstant.UMENG_ALIS + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext)));
        EventBus.getDefault().post("QuestionDetailActivity_note_add" + this.question_id);
        finish();
        AlertToast("保存成功");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.mHandler.sendEmptyMessage(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(Message message) throws SQLException {
        int i2 = message.what;
        if (i2 == 1) {
            chaneData();
            return false;
        }
        if (i2 != 2) {
            return false;
        }
        clearNote();
        return false;
    }

    public void clearNote() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id + "");
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mClearNoteURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.EditNoteActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                EditNoteActivity.this.finish();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    if (new JSONObject(t2).optString("code").equals("200")) {
                        EditNoteActivity.this.AlertToast("清除成功");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                EditNoteActivity.this.finish();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, android.app.Activity
    public void finish() {
        super.finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.question_id = getIntent().getLongExtra("question_id", 0L);
        this.mNotesBean = ProjectApp.mDaoSession.getNotesBeanDao().load(Long.valueOf(this.question_id));
        this.edit_note = (EditText) findViewById(R.id.edit_note);
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.z8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14239c.lambda$init$1(view);
            }
        });
        NotesBean notesBean = this.mNotesBean;
        if (notesBean != null) {
            this.edit_note.setText(notesBean.getContent());
            this.edit_note.setSelection(this.mNotesBean.getContent().length());
        }
        getWindow().setSoftInputMode(5);
        this.edit_note.setVisibility(0);
    }

    public void initView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("笔记");
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("保存");
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
        } else {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this.mContext, R.color.question_color_night));
        }
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
