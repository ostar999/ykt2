package com.psychiatrygarden.activity.circleactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.GradUateTomeBean;
import com.psychiatrygarden.activity.RegisterBean.MajorBean;
import com.psychiatrygarden.activity.RegisterBean.RegisterDataBean;
import com.psychiatrygarden.activity.RegisterBean.RegisterDepartmentBean;
import com.psychiatrygarden.activity.RegisterBean.WorkTimeBean;
import com.psychiatrygarden.activity.RegisterCommon.EducationPublicActivity;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectDepartActivity;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectHospitolActivity;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectMajorActivity;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTimeActivity;
import com.psychiatrygarden.activity.RegisterCommon.WorkTimeActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CircleSchoolVerifyActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 274;
    private RelativeLayout OtherCertificates;
    private String REGISTER_EXAMS_TIME;
    public ActivityResultLauncher activityResultLauncher;
    private LinearLayout danweiLiner;
    private RelativeLayout diploma;
    public String eduType;
    public String eduUUser;
    private LinearLayout education;
    private TextView educationTxt;
    public RelativeLayout employeeCardRel;
    private LinearLayout enrollmentYear;
    private ImageView iv_back;
    private ImageView iv_verify_card_bg;
    private String jobId;
    private LinearLayout keshiLiner;
    private LinearLayout ll_one;
    private LinearLayout ll_three;
    private LinearLayout ll_two;
    private LinearLayout lyHospital;
    private EditText mEtDepartment;
    private EditText mEtJob;
    private EditText mEtUnit;
    private ImageView mImgDelete;
    private LinearLayout mLyJob;
    private LinearLayout mLyWork;
    private LinearLayout mLyWorkOther;
    private TextView mTvHospital;
    private TextView mTvJob;
    private LinearLayout major;
    private TextView majortxt;
    private RelativeLayout schoolid;
    private LinearLayout schoolname;
    private TextView schooltxt;
    private LinearLayout singlepeopleliner;
    private TextView singlepeopletxt;
    private RelativeLayout studentId;
    public LinearLayout studentVerical;
    private LinearLayout studentliner;
    private TextView tv_contact_service;
    private TextView tv_exams_work_section;
    private TextView tv_exams_work_time;
    private TextView tv_next;
    private TextView tv_work_unit;
    private TextView verpostTxt;
    public RelativeLayout vocationalCertificateRel;
    private LinearLayout workLiner;
    public LinearLayout workVerical;
    private TextView xuezhitxt;
    private LinearLayout xuezhiview;
    private String REGISTER_UNIVERSITY_ID = "";
    private String REGISTER_UNIVERSITY_MAJOR_ID = "";
    private String educationid = "";
    private String yearid = "";
    private String xuezhiid = "";
    private String REGISTER_HOSPITOL_ID = "";
    private String REGISTERDEPART_ID = "";
    private String WORK_TIME_ID = "";
    private String REGISTER_HOSPITOL_NAME = "";
    private String REGISTERDEPART_NAME = "";
    private String WORK_TIME_NAME = "";
    private String student = "";
    private String results_photo = "";
    private final Handler mLocationHandler = new Handler();
    private int time = 5;

    private void getExamsTimes() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getEntranceTimeApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass12) s2);
                try {
                    GradUateTomeBean gradUateTomeBean = (GradUateTomeBean) new Gson().fromJson(s2, GradUateTomeBean.class);
                    if (gradUateTomeBean.getCode().equals("200")) {
                        List<String> data = gradUateTomeBean.getData();
                        if (data == null || data.size() == 0) {
                            CircleSchoolVerifyActivity.this.AlertToast("获取信息失败");
                        } else {
                            Intent intent = new Intent(CircleSchoolVerifyActivity.this, (Class<?>) RegisterSelectTimeActivity.class);
                            intent.putExtra("dataList", (Serializable) data);
                            intent.putExtra("title", "入学时间");
                            CircleSchoolVerifyActivity.this.startActivityForResult(intent, 3);
                        }
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.student = "working";
        showOtherView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        this.student = "doctor";
        showOtherView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        getDepartmentData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        getWorkTimeData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(View view) {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            GlideApp.with(this.mContext).load(Integer.valueOf(R.mipmap.verify_card_bg)).override(Integer.MIN_VALUE).into(this.iv_verify_card_bg);
        } else {
            GlideApp.with(this.mContext).load(Integer.valueOf(R.mipmap.verify_card_bg_night)).override(Integer.MIN_VALUE).into(this.iv_verify_card_bg);
        }
        this.results_photo = "";
        this.mImgDelete.setVisibility(8);
        this.verpostTxt.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(View view) {
        Intent intent = new Intent(this, (Class<?>) EducationPublicActivity.class);
        intent.putExtra("type", "1");
        startActivityForResult(intent, 13);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$7() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(ActivityResult activityResult) {
        if (activityResult.getResultCode() == -1) {
            this.eduUUser = activityResult.getData().getStringExtra("value");
            this.educationTxt.setText(activityResult.getData().getStringExtra("title"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void workOtherUi() {
        if (TextUtils.isEmpty(this.mEtUnit.getText().toString().trim()) || TextUtils.isEmpty(this.mEtDepartment.getText().toString().trim()) || TextUtils.isEmpty(this.mEtJob.getText().toString().trim()) || TextUtils.isEmpty(this.tv_exams_work_time.getText().toString().trim())) {
            this.tv_next.setVisibility(8);
        } else {
            this.tv_next.setVisibility(0);
        }
    }

    public void getDepartmentData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mDepartmentUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    RegisterDepartmentBean registerDepartmentBean = (RegisterDepartmentBean) new Gson().fromJson(s2, RegisterDepartmentBean.class);
                    if (registerDepartmentBean.getCode().equals("200")) {
                        List<RegisterDepartmentBean.DataBean> data = registerDepartmentBean.getData();
                        if (data == null) {
                            CircleSchoolVerifyActivity.this.AlertToast("获取信息失败");
                            return;
                        }
                        Intent intent = new Intent(CircleSchoolVerifyActivity.this, (Class<?>) RegisterSelectDepartActivity.class);
                        intent.putExtra("dataList", (Serializable) data);
                        intent.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CircleSchoolVerifyActivity.this));
                        intent.putExtra("title", "工作科室");
                        CircleSchoolVerifyActivity.this.startActivityForResult(intent, 8);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void getHospitalData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mHospitalUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass13) s2);
                try {
                    RegisterDataBean registerDataBean = (RegisterDataBean) new Gson().fromJson(s2, RegisterDataBean.class);
                    if (registerDataBean.getCode().equals("200")) {
                        List<RegisterDataBean.DataBean> data = registerDataBean.getData();
                        if (data == null) {
                            CircleSchoolVerifyActivity.this.AlertToast("获取信息失败");
                            return;
                        }
                        Intent intent = new Intent(CircleSchoolVerifyActivity.this, (Class<?>) RegisterSelectHospitolActivity.class);
                        intent.putExtra("dataList", (Serializable) data);
                        intent.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CircleSchoolVerifyActivity.this));
                        intent.putExtra("title", "工作单位");
                        CircleSchoolVerifyActivity.this.startActivityForResult(intent, 7);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("image", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this, NetworkRequestsURL.getforumuploadImageApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(CircleSchoolVerifyActivity.this.mContext, "上传失败！", 0).show();
                CircleSchoolVerifyActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CircleSchoolVerifyActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    try {
                        if (new JSONObject(s2).optString("code").equals("200")) {
                            CircleSchoolVerifyActivity.this.results_photo = new JSONObject(s2).optJSONObject("data").optString("url");
                            if (SkinManager.getCurrentSkinType(CircleSchoolVerifyActivity.this) == 0) {
                                GlideApp.with(CircleSchoolVerifyActivity.this.mContext).load((Object) GlideUtils.generateUrl(CircleSchoolVerifyActivity.this.results_photo)).centerCrop().override(Integer.MIN_VALUE).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.mipmap.verify_card_bg)).into(CircleSchoolVerifyActivity.this.iv_verify_card_bg);
                            } else {
                                GlideApp.with(CircleSchoolVerifyActivity.this.mContext).load((Object) GlideUtils.generateUrl(CircleSchoolVerifyActivity.this.results_photo)).centerCrop().override(Integer.MIN_VALUE).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.mipmap.verify_card_bg_night)).into(CircleSchoolVerifyActivity.this.iv_verify_card_bg);
                            }
                            CircleSchoolVerifyActivity.this.verpostTxt.setVisibility(8);
                            CircleSchoolVerifyActivity.this.tv_next.setTextColor(Color.parseColor("#ffffffff"));
                            CircleSchoolVerifyActivity.this.tv_next.setBackgroundResource(R.drawable.ffe55140_25);
                            CircleSchoolVerifyActivity.this.mImgDelete.setVisibility(0);
                        } else {
                            NewToast.showShort(CircleSchoolVerifyActivity.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } finally {
                    CircleSchoolVerifyActivity.this.hideProgressDialog();
                }
            }
        });
    }

    public void getMajorData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.majorUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.11
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
                super.onSuccess((AnonymousClass11) majorBean);
                try {
                    MajorBean majorBean2 = (MajorBean) new Gson().fromJson(majorBean, MajorBean.class);
                    if (majorBean2.getCode().equals("200")) {
                        Intent intent = new Intent(CircleSchoolVerifyActivity.this, (Class<?>) RegisterSelectMajorActivity.class);
                        intent.putExtra("dataList", (Serializable) majorBean2.getData());
                        intent.putExtra("type", "1");
                        intent.putExtra("title", "专业");
                        intent.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CircleSchoolVerifyActivity.this.mContext));
                        intent.putExtra("istrue", true);
                        CircleSchoolVerifyActivity.this.startActivityForResult(intent, 4);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getRegisterData1() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mShengfen, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.10
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
                super.onSuccess((AnonymousClass10) registerDataBean);
                try {
                    RegisterDataBean registerDataBean2 = (RegisterDataBean) new Gson().fromJson(registerDataBean, RegisterDataBean.class);
                    if (registerDataBean2.getCode().equals("200")) {
                        Intent intent = new Intent(CircleSchoolVerifyActivity.this, (Class<?>) RegisterSelectOneActivity.class);
                        intent.putExtra("dataList", (Serializable) registerDataBean2.getData());
                        intent.putExtra("type", "1");
                        intent.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CircleSchoolVerifyActivity.this.mContext));
                        intent.putExtra("title", "院校");
                        CircleSchoolVerifyActivity.this.startActivityForResult(intent, 1);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getUserInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", UserConfig.getUserId());
        YJYHttpUtils.get(this, NetworkRequestsURL.getUserInfoApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if ("200".equals(jSONObject.optString("code"))) {
                        CircleSchoolVerifyActivity.this.educationTxt.setText(jSONObjectOptJSONObject.optString("education_name") + "");
                        CircleSchoolVerifyActivity.this.eduUUser = jSONObjectOptJSONObject.optString("education_id");
                        CircleSchoolVerifyActivity.this.eduType = jSONObjectOptJSONObject.optString("user_type");
                        CircleSchoolVerifyActivity.this.tv_exams_work_time.setText(jSONObjectOptJSONObject.optString("work_time_name"));
                        if ("student".equals(CircleSchoolVerifyActivity.this.eduType) || "nurse".equals(CircleSchoolVerifyActivity.this.eduType)) {
                            CircleSchoolVerifyActivity.this.singlepeopletxt.setText("在校学生");
                        } else if ("work".equals(CircleSchoolVerifyActivity.this.eduType)) {
                            CircleSchoolVerifyActivity.this.singlepeopletxt.setText("医疗行业从业者");
                            CircleSchoolVerifyActivity.this.tv_work_unit.setText("医院");
                            CircleSchoolVerifyActivity.this.mTvHospital.setText(jSONObjectOptJSONObject.optString("hospital_name"));
                            CircleSchoolVerifyActivity.this.tv_exams_work_section.setText(jSONObjectOptJSONObject.optString("department_name"));
                            CircleSchoolVerifyActivity.this.mTvJob.setText(jSONObjectOptJSONObject.optString("position_name"));
                        } else if ("work_other".equals(CircleSchoolVerifyActivity.this.eduType)) {
                            CircleSchoolVerifyActivity.this.singlepeopletxt.setText("医疗行业从业者");
                            CircleSchoolVerifyActivity.this.tv_work_unit.setText("其他");
                            CircleSchoolVerifyActivity.this.mEtUnit.setText(jSONObjectOptJSONObject.optString("hospital_name"));
                            CircleSchoolVerifyActivity.this.mEtDepartment.setText(jSONObjectOptJSONObject.optString("department_name"));
                            CircleSchoolVerifyActivity.this.mEtJob.setText(jSONObjectOptJSONObject.optString("position_name"));
                            CircleSchoolVerifyActivity.this.workOtherUi();
                        } else {
                            CircleSchoolVerifyActivity.this.singlepeopletxt.setText("");
                        }
                        CircleSchoolVerifyActivity.this.initView(CircleSchoolVerifyActivity.this.eduType + "");
                        CircleSchoolVerifyActivity.this.REGISTER_UNIVERSITY_ID = jSONObjectOptJSONObject.optString("now_id");
                        CircleSchoolVerifyActivity.this.REGISTER_UNIVERSITY_MAJOR_ID = jSONObjectOptJSONObject.optString("now_major_id");
                        CircleSchoolVerifyActivity.this.REGISTER_HOSPITOL_ID = jSONObjectOptJSONObject.optString("hospital_id");
                        CircleSchoolVerifyActivity.this.REGISTERDEPART_ID = jSONObjectOptJSONObject.optString("department_id");
                        CircleSchoolVerifyActivity.this.WORK_TIME_ID = jSONObjectOptJSONObject.optString("work_time_id");
                        CircleSchoolVerifyActivity.this.jobId = jSONObjectOptJSONObject.optString("position");
                        CircleSchoolVerifyActivity.this.REGISTER_EXAMS_TIME = jSONObjectOptJSONObject.optString("entrance_time");
                        CircleSchoolVerifyActivity.this.schooltxt.setText(jSONObjectOptJSONObject.optString("now_name"));
                        CircleSchoolVerifyActivity.this.majortxt.setText(jSONObjectOptJSONObject.optString("now_major_name"));
                        CircleSchoolVerifyActivity.this.xuezhitxt.setText(jSONObjectOptJSONObject.optString("entrance_time"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getWorkTimeData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mWorkTimeUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    WorkTimeBean workTimeBean = (WorkTimeBean) new Gson().fromJson(s2, WorkTimeBean.class);
                    if (workTimeBean.getCode().equals("200")) {
                        List<WorkTimeBean.DataBean> data = workTimeBean.getData();
                        if (data == null) {
                            CircleSchoolVerifyActivity.this.AlertToast("获取信息失败");
                            return;
                        }
                        Intent intent = new Intent(CircleSchoolVerifyActivity.this, (Class<?>) WorkTimeActivity.class);
                        intent.putExtra("dataList", (Serializable) data);
                        intent.putExtra("istrue", true);
                        intent.putExtra("title", "工作时间");
                        CircleSchoolVerifyActivity.this.startActivityForResult(intent, 9);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.ll_one = (LinearLayout) findViewById(R.id.ll_one);
        this.ll_two = (LinearLayout) findViewById(R.id.ll_two);
        this.ll_three = (LinearLayout) findViewById(R.id.ll_three);
        this.tv_next = (TextView) findViewById(R.id.tv_next);
        this.studentVerical = (LinearLayout) findViewById(R.id.studentVerical);
        this.workVerical = (LinearLayout) findViewById(R.id.workVerical);
        this.mImgDelete = (ImageView) findViewById(R.id.shanchuimg);
        this.employeeCardRel = (RelativeLayout) findViewById(R.id.employeeCardRel);
        this.vocationalCertificateRel = (RelativeLayout) findViewById(R.id.VocationalCertificateRel);
        this.employeeCardRel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.w2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11613c.lambda$init$1(view);
            }
        });
        this.vocationalCertificateRel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.x2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11617c.lambda$init$2(view);
            }
        });
        this.keshiLiner = (LinearLayout) findViewById(R.id.keshiLiner);
        this.danweiLiner = (LinearLayout) findViewById(R.id.danweiLiner);
        this.keshiLiner.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.y2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11621c.lambda$init$3(view);
            }
        });
        this.danweiLiner.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.z2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11625c.lambda$init$4(view);
            }
        });
        this.tv_work_unit = (TextView) findViewById(R.id.tv_work_unit);
        this.tv_exams_work_section = (TextView) findViewById(R.id.tv_exams_work_section);
        this.tv_exams_work_time = (TextView) findViewById(R.id.tv_exams_work_time);
        this.studentliner = (LinearLayout) findViewById(R.id.studentliner);
        this.workLiner = (LinearLayout) findViewById(R.id.workLiner);
        this.singlepeopletxt = (TextView) findViewById(R.id.singlepeopletxt);
        this.singlepeopleliner = (LinearLayout) findViewById(R.id.singlepeopleliner);
        this.schoolname = (LinearLayout) findViewById(R.id.schoolname);
        this.education = (LinearLayout) findViewById(R.id.education);
        this.major = (LinearLayout) findViewById(R.id.major);
        this.enrollmentYear = (LinearLayout) findViewById(R.id.enrollmentYear);
        this.lyHospital = (LinearLayout) findViewById(R.id.ly_hospital);
        this.mTvHospital = (TextView) findViewById(R.id.tv_hospital);
        this.schooltxt = (TextView) findViewById(R.id.schooltxt);
        this.majortxt = (TextView) findViewById(R.id.majortxt);
        this.educationTxt = (TextView) findViewById(R.id.educationTxt);
        this.verpostTxt = (TextView) findViewById(R.id.verpostTxt);
        this.iv_verify_card_bg = (ImageView) findViewById(R.id.iv_verify_card_bg);
        this.xuezhiview = (LinearLayout) findViewById(R.id.xuezhiview);
        this.xuezhitxt = (TextView) findViewById(R.id.xuezhitxt);
        this.mLyJob = (LinearLayout) findViewById(R.id.ly_job);
        this.mTvJob = (TextView) findViewById(R.id.tv_job);
        this.studentId = (RelativeLayout) findViewById(R.id.studentId);
        this.schoolid = (RelativeLayout) findViewById(R.id.schoolid);
        this.diploma = (RelativeLayout) findViewById(R.id.diploma);
        this.OtherCertificates = (RelativeLayout) findViewById(R.id.OtherCertificates);
        this.mLyWork = (LinearLayout) findViewById(R.id.ly_work);
        this.mLyWorkOther = (LinearLayout) findViewById(R.id.ly_other);
        this.mEtUnit = (EditText) findViewById(R.id.et_unit);
        this.mEtDepartment = (EditText) findViewById(R.id.et_department);
        this.mEtJob = (EditText) findViewById(R.id.et_job);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.verpostTxt.setTextColor(getColor(R.color.white));
        }
        this.mImgDelete.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.a3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11492c.lambda$init$5(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_contact_service);
        this.tv_contact_service = textView;
        textView.setText(Html.fromHtml("联系客服：<font color='#4092E5'>QQ群864453282</font>"));
        getUserInfo();
        this.singlepeopleliner.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.b3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11498c.lambda$init$6(view);
            }
        });
        this.mEtUnit.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                CircleSchoolVerifyActivity.this.workOtherUi();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.mEtDepartment.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                CircleSchoolVerifyActivity.this.workOtherUi();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        this.mEtJob.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                CircleSchoolVerifyActivity.this.workOtherUi();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
    }

    public void initView(String userType) {
        if (userType.equals("student") || userType.equals("nurse")) {
            this.studentliner.setVisibility(0);
            this.workLiner.setVisibility(8);
        } else {
            if ("work".equals(userType)) {
                this.studentliner.setVisibility(8);
                this.workLiner.setVisibility(0);
                this.mLyWork.setVisibility(0);
                this.mLyWorkOther.setVisibility(8);
                return;
            }
            this.studentliner.setVisibility(8);
            this.workLiner.setVisibility(0);
            this.mLyWork.setVisibility(8);
            this.mLyWorkOther.setVisibility(0);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
        }
        if (requestCode == 1) {
            this.REGISTER_UNIVERSITY_ID = data.getStringExtra("area_id");
            this.schooltxt.setText(data.getStringExtra("title"));
            showNextView();
            return;
        }
        if (requestCode == 3) {
            this.xuezhitxt.setText(data.getStringExtra("title"));
            this.REGISTER_EXAMS_TIME = data.getStringExtra("title");
            showNextView();
            return;
        }
        if (requestCode == 4) {
            this.majortxt.setText(data.getStringExtra("title"));
            this.REGISTER_UNIVERSITY_MAJOR_ID = data.getStringExtra("area_id");
            showNextView();
            return;
        }
        if (requestCode == 7) {
            this.mTvHospital.setText(data.getStringExtra("title"));
            this.REGISTER_HOSPITOL_ID = data.getStringExtra("area_id");
            this.REGISTER_HOSPITOL_NAME = data.getStringExtra("title");
            showNextView();
            return;
        }
        if (requestCode == 8) {
            this.REGISTERDEPART_ID = data.getStringExtra("area_id");
            this.tv_exams_work_section.setText(data.getStringExtra("title"));
            this.REGISTERDEPART_NAME = data.getStringExtra("title");
            showNextView();
            return;
        }
        if (requestCode == 9) {
            this.WORK_TIME_ID = data.getStringExtra("work_id");
            this.tv_exams_work_time.setText(data.getStringExtra("title"));
            this.WORK_TIME_NAME = data.getStringExtra("title");
            showNextView();
            return;
        }
        switch (requestCode) {
            case 13:
                this.eduType = data.getStringExtra("value");
                this.singlepeopletxt.setText(data.getStringExtra("title"));
                initView(this.eduType);
                showNextView();
                break;
            case 14:
                this.eduType = data.getStringExtra("value");
                this.tv_work_unit.setText(data.getStringExtra("title"));
                if (this.eduType.equals("work_other")) {
                    this.mLyWork.setVisibility(8);
                    this.mLyWorkOther.setVisibility(0);
                } else {
                    this.mLyWork.setVisibility(0);
                    this.mLyWorkOther.setVisibility(8);
                }
                showNextView();
                break;
            case 15:
                this.jobId = data.getStringExtra("value");
                this.mTvJob.setText(data.getStringExtra("title"));
                break;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.OtherCertificates /* 2131361832 */:
                this.student = "other";
                showOtherView();
                break;
            case R.id.diploma /* 2131362927 */:
                this.student = "graduation";
                showOtherView();
                break;
            case R.id.education /* 2131363028 */:
                Intent intent = new Intent(this, (Class<?>) EducationPublicActivity.class);
                intent.putExtra("type", "2");
                this.activityResultLauncher.launch(intent);
                break;
            case R.id.enrollmentYear /* 2131363081 */:
                Intent intent2 = new Intent(this, (Class<?>) EducationPublicActivity.class);
                intent2.putExtra("type", "1");
                intent2.putExtra("value", "work");
                startActivityForResult(intent2, 14);
                break;
            case R.id.iv_back /* 2131364002 */:
                showDetailView();
                break;
            case R.id.iv_verify_card_bg /* 2131364279 */:
                if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
                    new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.circleactivity.c3
                        @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                        public final void onConfirm() {
                            this.f11502a.lambda$onClick$7();
                        }
                    })).show();
                    break;
                } else {
                    AndroidImagePicker.getInstance().setSelectLimit(1);
                    AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.7
                        @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                        public void onImagePickComplete(List<ImageItem> items) {
                            if (items == null || items.size() <= 0) {
                                return;
                            }
                            String str = items.get(0).path;
                            String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(str, options);
                            if (mimeTypeFromFile.toUpperCase().equals("IMAGE/WEBP")) {
                                CircleSchoolVerifyActivity.this.AlertToast("不支持此文件格式，请选择其它图片上传！");
                            } else if (ImageFactory.getImageSize(str) > 10.0f) {
                                NewToast.showShort(CircleSchoolVerifyActivity.this.mContext, "请选择小于10M的图片上传", 0).show();
                            } else {
                                CircleSchoolVerifyActivity.this.getImageData(str);
                            }
                        }
                    });
                    break;
                }
            case R.id.ly_hospital /* 2131365150 */:
                getHospitalData();
                break;
            case R.id.ly_job /* 2131365167 */:
                Intent intent3 = new Intent(this, (Class<?>) EducationPublicActivity.class);
                intent3.putExtra("type", "3");
                startActivityForResult(intent3, 15);
                break;
            case R.id.major /* 2131365352 */:
                getMajorData();
                break;
            case R.id.schoolid /* 2131366783 */:
                this.student = "campus";
                showOtherView();
                break;
            case R.id.schoolname /* 2131366784 */:
                getRegisterData1();
                break;
            case R.id.studentId /* 2131367073 */:
                this.student = "student";
                showOtherView();
                break;
            case R.id.tv_next /* 2131368276 */:
                if (this.ll_one.getVisibility() != 0) {
                    if (this.ll_two.getVisibility() != 0) {
                        if (this.ll_three.getVisibility() == 0) {
                            this.tv_next.setText("提交认证");
                            putauthentication();
                            break;
                        }
                    } else {
                        this.tv_next.setText("下一步");
                        this.ll_two.setVisibility(8);
                        this.ll_three.setVisibility(0);
                        if ("".equals(this.results_photo)) {
                            this.tv_next.setTextColor(Color.parseColor("#C7C7C7"));
                            this.tv_next.setBackgroundResource(R.drawable.fff2f2f2_25);
                            break;
                        }
                    }
                } else {
                    this.ll_one.setVisibility(8);
                    this.ll_two.setVisibility(0);
                    this.tv_next.setVisibility(8);
                    this.tv_next.setText("下一步");
                    if (!"student".equals(this.eduType) && !"nurse".equals(this.eduType)) {
                        if (!"work".equals(this.eduType)) {
                            if ("work_other".equals(this.eduType)) {
                                this.studentVerical.setVisibility(8);
                                this.workVerical.setVisibility(0);
                                this.vocationalCertificateRel.setVisibility(8);
                                break;
                            }
                        } else {
                            this.studentVerical.setVisibility(8);
                            this.workVerical.setVisibility(0);
                            this.vocationalCertificateRel.setVisibility(0);
                            break;
                        }
                    } else {
                        this.studentVerical.setVisibility(0);
                        this.workVerical.setVisibility(8);
                        break;
                    }
                }
                break;
            case R.id.xuezhiview /* 2131369228 */:
                getExamsTimes();
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("身份验证");
        this.activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.psychiatrygarden.activity.circleactivity.u2
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f11601a.lambda$onCreate$0((ActivityResult) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mLocationHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        showDetailView();
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (274 == requestCode && grantResults[0] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.ACCESS_COARSE_LOCATION)) {
            NewToast.showShort(this, "位置权限被禁用，请在权限管理修改", 0).show();
        }
    }

    public void putauthentication() {
        if ("".equals(this.results_photo)) {
            ToastUtil.shortToast(this, "请上传证件照片！");
            return;
        }
        if ("".equals(this.student)) {
            ToastUtil.shortToast(this, "请选择上传的证件类型！");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "" + this.student);
        ajaxParams.put("user_type", "" + this.eduType);
        if ("student".equals(this.eduType) || this.eduType.equals("nurse")) {
            ajaxParams.put("now_id", "" + this.REGISTER_UNIVERSITY_ID);
            ajaxParams.put("now_major_id", "" + this.REGISTER_UNIVERSITY_MAJOR_ID);
            ajaxParams.put("education_id", "" + this.eduUUser);
            ajaxParams.put("entrance_time", "" + this.REGISTER_EXAMS_TIME);
        } else if ("work".equals(this.eduType)) {
            ajaxParams.put("hospital_id", "" + this.REGISTER_HOSPITOL_ID);
            ajaxParams.put("department_id", "" + this.REGISTERDEPART_ID);
            ajaxParams.put("position_id", this.jobId);
            ajaxParams.put("work_time_id", "" + this.WORK_TIME_ID);
        } else if ("work_other".equals(this.eduType)) {
            ajaxParams.put("position", this.mEtJob.getText().toString().trim());
            ajaxParams.put("unit", this.mEtUnit.getText().toString().trim());
            ajaxParams.put("department", this.mEtDepartment.getText().toString().trim());
            ajaxParams.put("work_time_id", "" + this.WORK_TIME_ID);
        }
        ajaxParams.put("authentication_data", "" + this.results_photo);
        YJYHttpUtils.post(this, NetworkRequestsURL.getforumauthenticationApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(CircleSchoolVerifyActivity.this.mContext, "上传失败！", 0).show();
                CircleSchoolVerifyActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                CircleSchoolVerifyActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    try {
                        if (new JSONObject(s2).optString("code").equals("200")) {
                            Intent intent = new Intent(CircleSchoolVerifyActivity.this, (Class<?>) CircleSchoolVerifyResultActivity.class);
                            intent.putExtra("flag", "0");
                            CircleSchoolVerifyActivity.this.startActivity(intent);
                            CircleSchoolVerifyActivity.this.finish();
                        }
                        NewToast.showShort(CircleSchoolVerifyActivity.this.mContext, new JSONObject(s2).optString("message"), 0).show();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } finally {
                    CircleSchoolVerifyActivity.this.hideProgressDialog();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_circle_school_verify);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.xuezhiview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.iv_back.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.tv_next.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.tv_contact_service.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.schoolname.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.education.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.major.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.enrollmentYear.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.lyHospital.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.studentId.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.schoolid.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.diploma.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.OtherCertificates.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.iv_verify_card_bg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
        this.mLyJob.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11607c.onClick(view);
            }
        });
    }

    public void showDetailView() {
        if (this.ll_one.getVisibility() == 0) {
            showNextView();
            this.tv_next.setText("下一步");
            finish();
            return;
        }
        if (this.ll_two.getVisibility() == 0) {
            this.ll_two.setVisibility(8);
            this.ll_one.setVisibility(0);
            this.tv_next.setTextColor(Color.parseColor("#ffffffff"));
            this.tv_next.setBackgroundResource(R.drawable.ffe55140_25);
            this.tv_next.setVisibility(0);
            return;
        }
        if (this.ll_three.getVisibility() == 0) {
            this.ll_three.setVisibility(8);
            this.ll_two.setVisibility(0);
            this.tv_next.setVisibility(8);
            if (SkinManager.getCurrentSkinType(this) == 0) {
                GlideApp.with(this.mContext).load(Integer.valueOf(R.mipmap.verify_card_bg)).override(Integer.MIN_VALUE).into(this.iv_verify_card_bg);
            } else {
                GlideApp.with(this.mContext).load(Integer.valueOf(R.mipmap.verify_card_bg_night)).override(Integer.MIN_VALUE).into(this.iv_verify_card_bg);
            }
        }
    }

    public void showNextView() {
        if (this.ll_one.getVisibility() == 0) {
            if ("student".equals(this.eduType) || "nurse".equals(this.eduType)) {
                if (TextUtils.isEmpty(this.schooltxt.getText().toString()) || TextUtils.isEmpty(this.majortxt.getText().toString()) || TextUtils.isEmpty(this.educationTxt.getText().toString()) || TextUtils.isEmpty(this.xuezhitxt.getText().toString())) {
                    this.tv_next.setVisibility(8);
                    return;
                } else {
                    this.tv_next.setVisibility(0);
                    return;
                }
            }
            if ("work".equals(this.eduType)) {
                if (TextUtils.isEmpty(this.tv_work_unit.getText().toString().trim()) || TextUtils.isEmpty(this.tv_exams_work_section.getText().toString().trim()) || TextUtils.isEmpty(this.tv_exams_work_time.getText().toString().trim())) {
                    this.tv_next.setVisibility(8);
                    return;
                } else {
                    this.tv_next.setVisibility(0);
                    return;
                }
            }
            if ("work_other".equals(this.eduType)) {
                if (TextUtils.isEmpty(this.mEtUnit.getText().toString().trim()) || TextUtils.isEmpty(this.mEtDepartment.getText().toString().trim()) || TextUtils.isEmpty(this.mEtJob.getText().toString().trim()) || TextUtils.isEmpty(this.tv_exams_work_time.getText().toString().trim())) {
                    this.tv_next.setVisibility(8);
                } else {
                    this.tv_next.setVisibility(0);
                }
            }
        }
    }

    public void showOtherView() {
        this.ll_two.setVisibility(8);
        this.ll_three.setVisibility(0);
        if ("".equals(this.results_photo)) {
            this.tv_next.setTextColor(Color.parseColor("#C7C7C7"));
            this.tv_next.setBackgroundResource(R.drawable.fff2f2f2_25);
        }
        this.tv_next.setVisibility(0);
        this.tv_next.setText("提交认证");
    }
}
