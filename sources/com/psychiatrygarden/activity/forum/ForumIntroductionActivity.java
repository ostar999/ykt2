package com.psychiatrygarden.activity.forum;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.forum.bean.ForumEditEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForumIntroductionActivity extends BaseActivity {
    public EditText edit_note;
    public ScrollView sv_note;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        getForumeditIntroductionData();
    }

    public void getForumeditIntroductionData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
        ajaxParams.put("introduction", "" + this.edit_note.getText().toString());
        YJYHttpUtils.post(this, NetworkRequestsURL.getforumeditIntroductionApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.ForumIntroductionActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumIntroductionActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ForumIntroductionActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        EventBus.getDefault().post(new ForumEditEvent("editTxt", "" + ForumIntroductionActivity.this.edit_note.getText().toString()));
                        ForumIntroductionActivity.this.finish();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ForumIntroductionActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.edit_note = (EditText) findViewById(R.id.edit_note);
        this.sv_note = (ScrollView) findViewById(R.id.sv_note);
        this.edit_note.setText(getIntent().getExtras().getString("introduction", ""));
        this.sv_note.setVisibility(8);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(18);
        setTitle("版块简介");
        if ("1".equals(getIntent().getExtras().getString("edit_introduction_permission"))) {
            this.mBtnActionbarRight.setVisibility(0);
            this.edit_note.setEnabled(true);
        } else {
            this.mBtnActionbarRight.setVisibility(8);
            this.edit_note.setEnabled(false);
        }
        if ("".equals(getIntent().getExtras().getString("introduction"))) {
            this.mBtnActionbarRight.setText("发布");
        } else {
            this.mBtnActionbarRight.setText("编辑");
        }
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12368c.lambda$onCreate$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_note_course_chapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
