package com.psychiatrygarden.activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import java.util.Timer;
import java.util.TimerTask;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class NoteCourseChapterActivity extends BaseActivity {
    private EditText mEditNote;
    private String mNoteStatus;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mNoteStatus.equals("0") && this.mEditNote.getText().toString().trim().equals("")) {
            AlertToast("笔记不能为空");
        } else {
            getNoteNewData(this.mEditNote.getText().toString().trim());
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, android.app.Activity
    public void finish() {
        super.finish();
        hideInputMethod();
    }

    public void getNoteNewData(final String note) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("video_id", getIntent().getExtras().getString("course_id"));
        ajaxParams.put("content", note);
        ajaxParams.put("vidteaching_id", "" + getIntent().getExtras().getString("vidteaching_id"));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.addNoteApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.NoteCourseChapterActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    NoteCourseChapterActivity.this.AlertToast("上传评论失败");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    NoteCourseChapterActivity.this.AlertToast(new JSONObject(s2).optString("message"));
                    if (note.equals("")) {
                        NoteCourseChapterActivity.this.setResult(2);
                    } else {
                        NoteCourseChapterActivity.this.setResult(3);
                    }
                    NoteCourseChapterActivity.this.finish();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
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
        ScrollView scrollView = (ScrollView) findViewById(R.id.sv_note);
        this.mEditNote = (EditText) findViewById(R.id.edit_note);
        String string = getIntent().getExtras().getString("notestr");
        String string2 = getIntent().getExtras().getString("noteStatus");
        this.mNoteStatus = string2;
        if (string2.equals("1")) {
            this.mEditNote.setText(string);
            this.mEditNote.setSelection(string.length());
        }
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.re
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13797c.lambda$init$0(view);
            }
        });
        scrollView.setVisibility(8);
        this.mEditNote.setVisibility(0);
        new Timer().schedule(new TimerTask() { // from class: com.psychiatrygarden.activity.NoteCourseChapterActivity.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                ((InputMethodManager) NoteCourseChapterActivity.this.mEditNote.getContext().getSystemService("input_method")).showSoftInput(NoteCourseChapterActivity.this.mEditNote, 0);
            }
        }, 500L);
    }

    public void initView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(18);
        setTitle("视频笔记");
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
        setContentView(R.layout.activity_note_course_chapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
