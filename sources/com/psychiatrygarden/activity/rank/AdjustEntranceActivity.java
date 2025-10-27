package com.psychiatrygarden.activity.rank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.AdjustRankingActivity;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.MajorBean;
import com.psychiatrygarden.activity.RegisterBean.RegisterDataBean;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTwoActivity;
import com.psychiatrygarden.activity.rank.pop.ComSinglePopWindow;
import com.psychiatrygarden.activity.vip.pop.TypePopwindow;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AdjustEntranceActivity extends BaseActivity implements View.OnClickListener {
    TextView catview;
    TextView kytxt;
    RelativeLayout kyyxview;
    RelativeLayout kyzyview;
    private RelativeLayout mLyDept;
    private RelativeLayout mLyType;
    private TextView mTvDept;
    private TextView mTvType;
    private String scooltxt;
    TextView stxt;
    private List<MajorBean.DataBean> dataMajor2 = new ArrayList();
    private List<RegisterDataBean.DataBean> data = new ArrayList();
    private String REGISTER_EXAMS_UNIVERSITY_ID = "";
    private String REGISTER_EXAMS_MAJOR_ID = "";
    private String target_student_type = "";
    private String target_student_type_str = "";
    private String REGISTER_EXAMS_DEPT_ID = "";
    private String REGISTER_EXAMS_MAJOR_DIR_ID = "";

    private void getMajorData2() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "2");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.majorV2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.AdjustEntranceActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String majorBean) {
                super.onSuccess((AnonymousClass3) majorBean);
                MajorBean majorBean2 = (MajorBean) new Gson().fromJson(majorBean, MajorBean.class);
                if (majorBean2.getCode().equals("200")) {
                    AdjustEntranceActivity.this.dataMajor2 = majorBean2.getData();
                }
            }
        });
    }

    private void getRegisterData1() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getProvinceOrSchool, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.AdjustEntranceActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String registerDataBean) {
                super.onSuccess((AnonymousClass2) registerDataBean);
                RegisterDataBean registerDataBean2 = (RegisterDataBean) new Gson().fromJson(registerDataBean, RegisterDataBean.class);
                if (registerDataBean2.getCode().equals("200")) {
                    AdjustEntranceActivity.this.data = registerDataBean2.getData();
                }
            }
        });
    }

    private void getSelectData() {
        new XPopup.Builder(this).asCustom(new TypePopwindow(this.mContext, new TypePopwindow.TypeChooseClickIml() { // from class: com.psychiatrygarden.activity.rank.b
            @Override // com.psychiatrygarden.activity.vip.pop.TypePopwindow.TypeChooseClickIml
            public final void chooseType(String str) {
                this.f13765a.lambda$getSelectData$1(str);
            }
        })).show();
    }

    private void initView() {
        this.stxt = (TextView) findViewById(R.id.stxt);
        this.kyyxview = (RelativeLayout) findViewById(R.id.kyyxview);
        this.kytxt = (TextView) findViewById(R.id.kytxt);
        this.kyzyview = (RelativeLayout) findViewById(R.id.kyzyview);
        this.catview = (TextView) findViewById(R.id.catview);
        this.mLyType = (RelativeLayout) findViewById(R.id.ly_type);
        this.mTvType = (TextView) findViewById(R.id.tv_type);
        this.mLyDept = (RelativeLayout) findViewById(R.id.ly_dept);
        this.mTvDept = (TextView) findViewById(R.id.tv_dept);
        this.catview.setOnClickListener(this);
        this.kyyxview.setOnClickListener(this);
        this.kyzyview.setOnClickListener(this);
        this.mLyType.setOnClickListener(this);
        this.mLyDept.setOnClickListener(this);
        setTitle("填写志愿");
        new XPopup.Builder(this).asCustom(new ComSinglePopWindow(this, new ComSinglePopWindow.DialogListener() { // from class: com.psychiatrygarden.activity.rank.a
            @Override // com.psychiatrygarden.activity.rank.pop.ComSinglePopWindow.DialogListener
            public final void mDialogListener() {
                this.f13764a.lambda$initView$0();
            }
        }, "每人仅有一次提交数据的机会，请认真填写")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSelectData$1(String str) {
        if (!TextUtils.isEmpty(this.target_student_type) && !this.target_student_type.equals(str)) {
            this.REGISTER_EXAMS_MAJOR_ID = "";
            this.REGISTER_EXAMS_MAJOR_DIR_ID = "";
            this.scooltxt = "";
            this.kytxt.setText("");
        }
        this.target_student_type = str;
        this.mTvType.setText(str);
        if (this.target_student_type.equals("专硕")) {
            this.target_student_type_str = "zhuan";
        } else {
            this.target_student_type_str = "xue";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.rankTrue + getIntent().getExtras().getString("moudle"), true, this.mContext);
    }

    public void getInfoData() {
        getRegisterData1();
        getMajorData2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        initView();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 2) {
            this.REGISTER_EXAMS_DEPT_ID = "";
            this.REGISTER_EXAMS_MAJOR_ID = "";
            this.REGISTER_EXAMS_MAJOR_DIR_ID = "";
            this.REGISTER_EXAMS_UNIVERSITY_ID = data.getStringExtra("area_id");
            String stringExtra = data.getStringExtra("bind_type");
            if (TextUtils.isEmpty(stringExtra) || !stringExtra.equals("2")) {
                this.mLyDept.setVisibility(8);
                this.REGISTER_EXAMS_DEPT_ID = "";
            } else {
                this.mLyDept.setVisibility(0);
            }
            this.stxt.setText(data.getStringExtra("title"));
            this.scooltxt = "";
            this.kytxt.setText("");
            this.mTvDept.setText("");
            return;
        }
        if (requestCode != 5) {
            if (requestCode != 18) {
                return;
            }
            this.REGISTER_EXAMS_MAJOR_ID = "";
            this.REGISTER_EXAMS_MAJOR_DIR_ID = "";
            this.scooltxt = "";
            this.kytxt.setText("");
            this.REGISTER_EXAMS_DEPT_ID = data.getStringExtra("dept_id");
            this.mTvDept.setText(data.getStringExtra("title"));
            return;
        }
        if (this.target_student_type.equals("专硕")) {
            this.target_student_type_str = "zhuan";
        } else {
            this.target_student_type_str = "xue";
        }
        this.REGISTER_EXAMS_MAJOR_ID = data.getStringExtra("major_id");
        this.REGISTER_EXAMS_MAJOR_DIR_ID = data.getStringExtra("major_dir_id");
        String stringExtra2 = data.getStringExtra("title");
        this.scooltxt = stringExtra2;
        this.kytxt.setText(stringExtra2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.catview) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            if (TextUtils.isEmpty(this.REGISTER_EXAMS_UNIVERSITY_ID)) {
                ToastUtil.shortToast(this.mContext, "请选择考研院校");
                return;
            }
            if (this.mLyDept.getVisibility() == 0 && TextUtils.isEmpty(this.REGISTER_EXAMS_DEPT_ID)) {
                ToastUtil.shortToast(this.mContext, "请选择院系所");
                return;
            }
            if (TextUtils.isEmpty(this.target_student_type_str)) {
                ToastUtil.shortToast(this.mContext, "请选择专业类别");
                return;
            } else if (TextUtils.isEmpty(this.REGISTER_EXAMS_MAJOR_ID)) {
                ToastUtil.shortToast(this.mContext, "请选择考研专业");
                return;
            } else {
                putData();
                return;
            }
        }
        if (id == R.id.kyyxview) {
            List<RegisterDataBean.DataBean> list = this.data;
            if (list == null || list.size() == 0) {
                getInfoData();
                ToastUtil.shortToast(this.mContext, "获取信息失败");
                return;
            }
            Intent intent = new Intent(this, (Class<?>) RegisterSelectOneActivity.class);
            intent.putExtra("dataList", (Serializable) this.data);
            intent.putExtra("type", "5");
            intent.putExtra("title", "考研院校");
            intent.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
            startActivityForResult(intent, 2);
            return;
        }
        if (id == R.id.ly_dept) {
            if (TextUtils.isEmpty(this.REGISTER_EXAMS_UNIVERSITY_ID)) {
                ToastUtil.shortToast(this.mContext, "请选择考研院校");
                return;
            }
            Intent intent2 = new Intent(this, (Class<?>) RegisterSelectTwoActivity.class);
            intent2.putExtra("title", "院系所");
            intent2.putExtra("school_id", this.REGISTER_EXAMS_UNIVERSITY_ID);
            intent2.putExtra("type", "3");
            intent2.putExtra("istrue", true);
            startActivityForResult(intent2, 18);
            return;
        }
        if (id == R.id.ly_type) {
            getSelectData();
            return;
        }
        if (id == R.id.kyzyview) {
            if (TextUtils.isEmpty(this.REGISTER_EXAMS_UNIVERSITY_ID)) {
                ToastUtil.shortToast(this.mContext, "请选择考研院校");
                return;
            }
            if (this.mLyDept.getVisibility() == 0 && TextUtils.isEmpty(this.REGISTER_EXAMS_DEPT_ID)) {
                ToastUtil.shortToast(this.mContext, "请选择院系所");
                return;
            }
            if (TextUtils.isEmpty(this.target_student_type_str)) {
                ToastUtil.shortToast(this.mContext, "请选择专业类别");
                return;
            }
            Intent intent3 = new Intent(this, (Class<?>) RegisterSelectOneActivity.class);
            intent3.putExtra("dataList", (Serializable) this.data);
            intent3.putExtra("type", "3");
            intent3.putExtra("title", "考研专业");
            intent3.putExtra("major_type", this.target_student_type_str.equals("zhuan") ? "1" : "2");
            if (TextUtils.isEmpty(this.REGISTER_EXAMS_DEPT_ID)) {
                intent3.putExtra("school_id", this.REGISTER_EXAMS_UNIVERSITY_ID);
            } else {
                intent3.putExtra("dept_id", this.REGISTER_EXAMS_DEPT_ID);
            }
            intent3.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext));
            startActivityForResult(intent3, 5);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String onEventStr) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getInfoData();
    }

    public void putData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("version", getIntent().getExtras().getString("version"));
        ajaxParams.put("type", getIntent().getExtras().getString("type"));
        ajaxParams.put("zhuan_xue", "" + this.target_student_type_str);
        ajaxParams.put("target_school_id", "" + this.REGISTER_EXAMS_UNIVERSITY_ID);
        ajaxParams.put("target_school_department_id", "" + this.REGISTER_EXAMS_DEPT_ID);
        ajaxParams.put("target_major_id", "" + this.REGISTER_EXAMS_MAJOR_ID);
        ajaxParams.put("target_major_direction_id", "" + this.REGISTER_EXAMS_MAJOR_DIR_ID);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.saveAdjustInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.AdjustEntranceActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AdjustEntranceActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                AdjustEntranceActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        Intent intent = new Intent(AdjustEntranceActivity.this, (Class<?>) AdjustRankingActivity.class);
                        intent.putExtra("version", AdjustEntranceActivity.this.getIntent().getExtras().getString("version"));
                        intent.putExtra("type", AdjustEntranceActivity.this.getIntent().getExtras().getString("type"));
                        AdjustEntranceActivity.this.startActivity(intent);
                        AdjustEntranceActivity.this.setResult(-1);
                        AdjustEntranceActivity.this.finish();
                    } else {
                        ToastUtil.shortToast(AdjustEntranceActivity.this.mContext, new JSONObject(s2).optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                AdjustEntranceActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_adjust_entrance);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
