package com.psychiatrygarden.activity.online;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.aliyun.vod.common.utils.UriUtil;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragment;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.widget.ExportDescriptionPop;
import com.yikaobang.yixue.R;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes5.dex */
public class QuestionRecordingActivity extends BaseActivity {
    private String is_show_number;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
            startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
        } else {
            new XPopup.Builder(this).asCustom(new ExportDescriptionPop(this, getIntent().getExtras())).toggle();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        if (findFragment(QuestionBankNewFragment.class) == null) {
            QuestionBankNewFragment questionBankNewFragment = new QuestionBankNewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("unit_id", "" + getIntent().getExtras().getString("unit_id", ""));
            bundle.putString(UriUtil.QUERY_CATEGORY, "" + getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY));
            bundle.putString("module_type", "" + getIntent().getExtras().getString("module_type"));
            bundle.putString("type", "" + getIntent().getExtras().getString("type"));
            bundle.putString("is_show_number", this.is_show_number);
            bundle.putString("identity_id", "" + getIntent().getExtras().getString("identity_id"));
            questionBankNewFragment.setArguments(bundle);
            loadRootFragment(R.id.fragment, questionBankNewFragment);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.is_show_number = getIntent().getExtras().getString("is_show_number", "");
        setTitles();
        this.mBtnActionbarRight.setText("导出");
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.j1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13432c.lambda$onCreate$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_record);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setTitles() {
        String string = getIntent().getExtras().getString("type");
        if ("error".equals(string)) {
            setTitle("我的错题");
            if ("points".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY)) || "cases".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY)) || "unit".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY))) {
                this.mBtnActionbarRight.setVisibility(8);
                return;
            } else {
                this.mBtnActionbarRight.setVisibility(0);
                return;
            }
        }
        if ("collection".equals(string)) {
            setTitle("我的收藏");
            if ("points".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY)) || "cases".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY)) || "unit".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY))) {
                this.mBtnActionbarRight.setVisibility(8);
                return;
            } else {
                this.mBtnActionbarRight.setVisibility(0);
                return;
            }
        }
        if ("note".equals(string)) {
            setTitle("我的笔记");
            if ("points".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY)) || "cases".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY)) || "unit".equals(getIntent().getExtras().getString(UriUtil.QUERY_CATEGORY))) {
                this.mBtnActionbarRight.setVisibility(8);
                return;
            } else {
                this.mBtnActionbarRight.setVisibility(0);
                return;
            }
        }
        if ("praise".equals(string)) {
            setTitle("我的点赞");
            this.mBtnActionbarRight.setVisibility(8);
        } else if (!ClientCookie.COMMENT_ATTR.equals(string)) {
            this.mBtnActionbarRight.setVisibility(8);
        } else {
            setTitle("我的评论");
            this.mBtnActionbarRight.setVisibility(8);
        }
    }
}
