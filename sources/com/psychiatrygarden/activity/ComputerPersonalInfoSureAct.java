package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ComputerPersonalInfoSureAct extends BaseActivity {
    private String dateEnd;
    private long durationMinute;
    private String examId;
    private String file;
    private TextView mBtnJoinTest;
    private TextView mTvAllScore;
    private TextView mTvNumber;
    private TextView mTvSubject;
    private TextView mTvTestName;
    private TextView mTvUserName;
    private CircleImageView mUserHead;
    private String submitMinute;
    private String title;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        String str = this.title;
        String str2 = this.examId;
        String str3 = this.file;
        ComputerRuleAct.newIntent(this, str, str2, str3, this.dateEnd, this.durationMinute, this.submitMinute, str3);
    }

    public static void newIntent(Context context, String project, String title, String count, String score, String file, String examId, String endTimestamp, long durationMin, String submitMinute) {
        Intent intent = new Intent(context, (Class<?>) ComputerPersonalInfoSureAct.class);
        intent.putExtra("project", project);
        intent.putExtra("title", title);
        intent.putExtra("count", count);
        intent.putExtra("score", score);
        intent.putExtra("file", file);
        intent.putExtra("examId", examId);
        intent.putExtra("dateEnd", endTimestamp);
        intent.putExtra("durationMinute", durationMin);
        intent.putExtra("submitMinute", submitMinute);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("个人信息确认");
        this.title = getIntent().getStringExtra("title");
        this.examId = getIntent().getExtras().getString("examId");
        this.file = getIntent().getExtras().getString("file");
        this.dateEnd = getIntent().getStringExtra("dateEnd");
        this.durationMinute = getIntent().getLongExtra("durationMinute", 0L);
        this.submitMinute = getIntent().getStringExtra("submitMinute");
        this.mUserHead = (CircleImageView) findViewById(R.id.iconheader);
        this.mTvUserName = (TextView) findViewById(R.id.user_name);
        this.mTvSubject = (TextView) findViewById(R.id.tv_subject);
        this.mTvTestName = (TextView) findViewById(R.id.tv_test_name);
        this.mTvNumber = (TextView) findViewById(R.id.tv_number);
        this.mTvAllScore = (TextView) findViewById(R.id.tv_all_score);
        this.mBtnJoinTest = (TextView) findViewById(R.id.btn_join_test);
        this.mTvSubject.setText(getIntent().getStringExtra("project"));
        this.mTvTestName.setText(getIntent().getStringExtra("title"));
        this.mTvNumber.setText(getIntent().getStringExtra("count"));
        this.mTvAllScore.setText(getIntent().getStringExtra("score"));
        GlideUtils.loadImage(this, UserConfig.getInstance().getUser().getAvatar(), this.mUserHead);
        this.mTvUserName.setText(UserConfig.getInstance().getUser().getNickname());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("closePage")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_computer_personal_info_sure);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnJoinTest.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.e7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12274c.lambda$setListenerForWidget$0(view);
            }
        });
    }
}
