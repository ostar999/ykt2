package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobile.auth.gatewayauth.Constant;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.disposables.CompositeDisposable;

/* loaded from: classes5.dex */
public class ComputerMockTestFinishAct extends BaseActivity {
    private CompositeDisposable compositeDisposable;
    private String examId;
    private String file;
    private TextView mBtnStart;
    private ImageView mImgBack;
    private TextView mTvAllTime;
    private TextView mTvDoNumber;
    private TextView mTvDoProgress;
    private TextView mTvLastNumber;
    private TextView mTvLastTime;
    private TextView mTvMockName;
    private TextView mTvNickName;
    private TextView mTvTestNumber;
    private TextView mTvUseTime;
    private String title;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        EventBus.getDefault().post("closePage");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (isLogin()) {
            ComputerModeTwoStatisticsAct.newIntent(this, this.title, this.examId, this.file);
        }
    }

    public static void newIntent(Context context, String examId, String title, String allTime, String doProgress, long useTime, String lastTime, String doNumber, String lastNumber, String file, String number) {
        Intent intent = new Intent(context, (Class<?>) ComputerMockTestFinishAct.class);
        intent.putExtra("titleName", title);
        intent.putExtra("examId", examId);
        intent.putExtra("allTime", allTime);
        intent.putExtra("doProgress", doProgress);
        intent.putExtra("useTime", useTime);
        intent.putExtra("lastTime", lastTime);
        intent.putExtra("file", file);
        intent.putExtra(Constant.LOGIN_ACTIVITY_NUMBER, number);
        intent.putExtra("doNumber", doNumber);
        intent.putExtra("lastNumber", lastNumber);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.title = getIntent().getStringExtra("titleName");
        this.examId = getIntent().getStringExtra("examId");
        String stringExtra = getIntent().getStringExtra("allTime");
        String stringExtra2 = getIntent().getStringExtra("doProgress");
        long longExtra = getIntent().getLongExtra("useTime", 0L);
        String stringExtra3 = getIntent().getStringExtra("lastTime");
        String stringExtra4 = getIntent().getStringExtra("doNumber");
        String stringExtra5 = getIntent().getStringExtra("lastNumber");
        this.file = getIntent().getStringExtra("file");
        this.mTvMockName = (TextView) findViewById(R.id.tv_mock_name);
        this.mImgBack = (ImageView) findViewById(R.id.img_back);
        this.mBtnStart = (TextView) findViewById(R.id.btn_start);
        this.mTvNickName = (TextView) findViewById(R.id.tv_nickname);
        this.mTvTestNumber = (TextView) findViewById(R.id.tv_test_number);
        this.mTvAllTime = (TextView) findViewById(R.id.tv_time);
        this.mTvDoProgress = (TextView) findViewById(R.id.tv_progress);
        this.mTvUseTime = (TextView) findViewById(R.id.tv_use_time);
        this.mTvDoNumber = (TextView) findViewById(R.id.tv_do_number);
        this.mTvLastTime = (TextView) findViewById(R.id.tv_last_time);
        this.mTvLastNumber = (TextView) findViewById(R.id.tv_last_number);
        this.mTvMockName.setText(this.title);
        this.mTvNickName.setText(UserConfig.getInstance().getUser().getNickname());
        this.mTvTestNumber.setText(getIntent().getStringExtra(Constant.LOGIN_ACTIVITY_NUMBER));
        this.mTvAllTime.setText(stringExtra + "分钟");
        this.mTvDoProgress.setText(stringExtra2);
        long j2 = longExtra / 60;
        long j3 = longExtra % 60;
        if (j2 > 0) {
            this.mTvUseTime.setText(j2 + "分" + j3 + "秒");
        } else {
            this.mTvUseTime.setText(j3 + "秒");
        }
        this.mTvDoNumber.setText(stringExtra4);
        this.mTvLastTime.setText(stringExtra3);
        this.mTvLastNumber.setText(stringExtra5);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.compositeDisposable.isDisposed()) {
            return;
        }
        this.compositeDisposable.dispose();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("closePage")) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.layout_computer_mock_finish);
        this.compositeDisposable = new CompositeDisposable();
        getWindow().addFlags(1024);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.q5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13728c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mBtnStart.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.r5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13759c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
