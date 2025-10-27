package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.Random;

/* loaded from: classes5.dex */
public class ComputerMockTestLoginAct extends BaseActivity {
    private String dateEnd;
    private long durationMinute;
    private String examId;
    private String file;
    private TextView mBtnBack;
    private TextView mBtnLogin;
    private EditText mEtTestNumber;
    private ImageView mImgBack;
    private TextView mTvMockName;
    private String subjectName;
    private String submitMinute;
    private String title;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        String strTrim = this.mEtTestNumber.getText().toString().trim();
        if (TextUtils.isEmpty(strTrim)) {
            ToastUtil.shortToast(this, "请输入准考证号");
        } else {
            ComputerMockTestLoginSureAct.newIntent(this, this.examId, this.title, this.dateEnd, this.durationMinute, this.submitMinute, this.file, strTrim, this.subjectName);
        }
    }

    public static void newIntent(Context context, String examId, String title, String dateEnd, long durationMin, String submitMinute, String file, String project) {
        Intent intent = new Intent(context, (Class<?>) ComputerMockTestLoginAct.class);
        intent.putExtra("titleName", title);
        intent.putExtra("examId", examId);
        intent.putExtra("dateEnd", dateEnd);
        intent.putExtra("durationMinute", durationMin);
        intent.putExtra("submitMinute", submitMinute);
        intent.putExtra("file", file);
        intent.putExtra("project", project);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.title = getIntent().getStringExtra("titleName");
        this.examId = getIntent().getStringExtra("examId");
        this.dateEnd = getIntent().getStringExtra("dateEnd");
        this.durationMinute = getIntent().getLongExtra("durationMinute", 0L);
        this.file = getIntent().getStringExtra("file");
        this.submitMinute = getIntent().getStringExtra("submitMinute");
        this.subjectName = getIntent().getStringExtra("project");
        this.mTvMockName = (TextView) findViewById(R.id.tv_mock_name);
        this.mImgBack = (ImageView) findViewById(R.id.img_back);
        this.mBtnBack = (TextView) findViewById(R.id.btn_back);
        this.mBtnLogin = (TextView) findViewById(R.id.btn_login);
        this.mEtTestNumber = (EditText) findViewById(R.id.et_test_number);
        this.mTvMockName.setText(this.title);
        int iNextInt = new Random().nextInt(6) + 1;
        this.mEtTestNumber.setText(iNextInt + "");
        this.mEtTestNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), new InputFilter() { // from class: com.psychiatrygarden.activity.ComputerMockTestLoginAct.1
            @Override // android.text.InputFilter
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end <= start || source.toString().matches("[a-zA-Z0-9]*")) {
                    return null;
                }
                return "";
            }
        }});
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
        setContentView(R.layout.layout_computer_mock_login);
        getWindow().addFlags(1024);
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.s5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13819c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mBtnBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.t5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13943c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnLogin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.u5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13976c.lambda$setListenerForWidget$2(view);
            }
        });
    }
}
