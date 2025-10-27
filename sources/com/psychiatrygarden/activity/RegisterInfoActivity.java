package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import cn.hutool.core.text.StrPool;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
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
import com.psychiatrygarden.bean.UserInfoBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.LocationEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.service.YkbLocationService;
import com.psychiatrygarden.utils.CameraUtil;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.InviteOneDialog;
import com.psychiatrygarden.widget.InviteTwoDialog;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class RegisterInfoActivity extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 274;
    private EditText et_nickname;
    private TextView et_select;
    private TextView et_sex;
    private ImageView iv_register_photo;
    private String jobId;
    private LinearLayout line_xuexiao;
    private LinearLayout line_zhuanye;
    private RelativeLayout lyHospital;
    private Button mBtComplete;
    private EditText mEtDepartment;
    private EditText mEtJob;
    private EditText mEtUnit;
    private RelativeLayout mLyJob;
    private LinearLayout mLyWork;
    private LinearLayout mLyWorkOther;
    private RelativeLayout mRelSex;
    private RelativeLayout mRlExamsMajor;
    private RelativeLayout mRlExamsTime;
    private RelativeLayout mRlExamsUniversity;
    private RelativeLayout mRlExamsWorkSection;
    private RelativeLayout mRlUniversity;
    private RelativeLayout mRlUniversityMajor;
    private RelativeLayout mRlWorkUnit;
    private TextView mTvExamsMajor;
    private TextView mTvExamsTime;
    private TextView mTvExamsUniversity;
    private TextView mTvExamsWorkUnit;
    private TextView mTvHospital;
    private TextView mTvJob;
    private TextView mTvUniversity;
    private TextView mTvUniversityMajor;
    private TextView mTvWorkSection;
    private TextView mTvWorkTime;
    private RelativeLayout rel_select;
    private RelativeLayout rl_xueli;
    private TextView tv_xueli_major;
    private String myPhotoPath = "";
    private int uploadimg = 0;
    private List<RegisterDataBean.DataBean> dataHospitol = new ArrayList();
    private List<RegisterDepartmentBean.DataBean> dataDepartmentList = new ArrayList();
    private List<WorkTimeBean.DataBean> mWorkTime = new ArrayList();
    private String avatar_url = "";
    private String eduType = "";
    private String eduUUser = "";
    private List<RegisterDataBean.DataBean> data = new ArrayList();
    private List<MajorBean.DataBean> dataMajor = new ArrayList();
    private final List<MajorBean.DataBean> dataMajor2 = new ArrayList();
    private List<String> datatime = new ArrayList();
    private String REGISTER_UNIVERSITY_ID = "";
    private String REGISTER_UNIVERSITY_MAJOR_ID = "";
    private String REGISTER_EXAMS_TIME = "";
    private String REGISTER_SEX_STR = "";
    private String REGISTER_HOSPITOL_ID = "";
    private String REGISTERDEPART_ID = "";
    private String WORK_TIME_ID = "";
    private int time = 5;
    View.OnClickListener mOnclick = new AnonymousClass7();
    private final List<String> sexdata = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.RegisterInfoActivity$7, reason: invalid class name */
    public class AnonymousClass7 implements View.OnClickListener {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0() {
            ActivityCompat.requestPermissions((Activity) RegisterInfoActivity.this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$1(Bitmap bitmap, float f2) throws IOException {
            RegisterInfoActivity.this.iv_register_photo.setVisibility(0);
            RegisterInfoActivity.this.iv_register_photo.setImageBitmap(bitmap);
            File fileSaveHeadimage2 = CameraUtil.saveHeadimage2(bitmap, System.currentTimeMillis() + ".jpg");
            if (fileSaveHeadimage2 != null) {
                RegisterInfoActivity.this.myPhotoPath = fileSaveHeadimage2.getAbsolutePath();
            }
        }

        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
            }
            switch (v2.getId()) {
                case R.id.bt_complete /* 2131362291 */:
                    if (!RegisterInfoActivity.this.et_nickname.getText().toString().trim().equals("")) {
                        if (!TextUtils.isEmpty(RegisterInfoActivity.this.et_sex.getText().toString())) {
                            if (!TextUtils.isEmpty(RegisterInfoActivity.this.eduType)) {
                                if (!RegisterInfoActivity.this.eduType.equals("work")) {
                                    if (!RegisterInfoActivity.this.eduType.equals("work_other")) {
                                        if (!RegisterInfoActivity.this.mTvUniversity.getText().toString().trim().equals("") && !RegisterInfoActivity.this.mTvUniversity.getText().toString().trim().equals("--")) {
                                            if (!RegisterInfoActivity.this.mTvUniversityMajor.getText().toString().trim().equals("") && !RegisterInfoActivity.this.mTvUniversityMajor.getText().toString().trim().equals("--")) {
                                                if (!"".equals(RegisterInfoActivity.this.eduUUser)) {
                                                    if (RegisterInfoActivity.this.mTvExamsTime.getText().toString().trim().equals("") || RegisterInfoActivity.this.mTvExamsTime.getText().toString().trim().equals("--")) {
                                                        RegisterInfoActivity.this.AlertToast("请选择入学时间");
                                                        break;
                                                    }
                                                } else {
                                                    RegisterInfoActivity.this.AlertToast("请选择学历");
                                                    break;
                                                }
                                            } else {
                                                RegisterInfoActivity.this.AlertToast("请选择本科专业");
                                                break;
                                            }
                                        } else {
                                            RegisterInfoActivity.this.AlertToast("请选择本科院校");
                                            break;
                                        }
                                    } else if (!TextUtils.isEmpty(RegisterInfoActivity.this.mEtUnit.getText().toString().trim())) {
                                        if (!TextUtils.isEmpty(RegisterInfoActivity.this.mEtDepartment.getText().toString().trim())) {
                                            if (!TextUtils.isEmpty(RegisterInfoActivity.this.mEtJob.getText().toString().trim())) {
                                                if (RegisterInfoActivity.this.mTvWorkTime.getText().toString().equals("") || RegisterInfoActivity.this.mTvWorkTime.getText().toString().equals("--")) {
                                                    RegisterInfoActivity.this.AlertToast("请选择工作时间");
                                                    break;
                                                }
                                            } else {
                                                RegisterInfoActivity.this.AlertToast("请输入职务");
                                                break;
                                            }
                                        } else {
                                            RegisterInfoActivity.this.AlertToast("请输入所在部门");
                                            break;
                                        }
                                    } else {
                                        RegisterInfoActivity.this.AlertToast("请输入所在单位");
                                        break;
                                    }
                                } else if (!RegisterInfoActivity.this.mTvExamsWorkUnit.getText().toString().trim().equals("") && !RegisterInfoActivity.this.mTvExamsWorkUnit.getText().toString().trim().equals("--")) {
                                    if (!RegisterInfoActivity.this.mTvWorkSection.getText().toString().trim().equals("") && !RegisterInfoActivity.this.mTvWorkSection.getText().toString().trim().equals("--")) {
                                        if (RegisterInfoActivity.this.mTvWorkTime.getText().toString().equals("") || RegisterInfoActivity.this.mTvWorkTime.getText().toString().equals("--")) {
                                            RegisterInfoActivity.this.AlertToast("请选择工作时间");
                                            break;
                                        }
                                    } else {
                                        RegisterInfoActivity.this.AlertToast("请选择工作科室");
                                        break;
                                    }
                                } else {
                                    RegisterInfoActivity.this.AlertToast("请选择工作单位");
                                    break;
                                }
                                if (!TextUtils.isEmpty(RegisterInfoActivity.this.myPhotoPath)) {
                                    RegisterInfoActivity.this.uploadAvatar();
                                    break;
                                } else {
                                    RegisterInfoActivity.this.getRegister("");
                                    break;
                                }
                            } else {
                                RegisterInfoActivity.this.AlertToast("请选择个人身份");
                                break;
                            }
                        } else {
                            RegisterInfoActivity.this.AlertToast("请选择您的性别");
                            break;
                        }
                    } else {
                        RegisterInfoActivity.this.AlertToast("请输入您的昵称");
                        break;
                    }
                    break;
                case R.id.iv_register_photo /* 2131364203 */:
                    if (!CommonUtil.hasRequiredPermissions(RegisterInfoActivity.this.mContext)) {
                        new XPopup.Builder(RegisterInfoActivity.this.mContext).asCustom(new RequestMediaPermissionPop(RegisterInfoActivity.this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.sh
                            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                            public final void onConfirm() {
                                this.f13921a.lambda$onClick$0();
                            }
                        })).show();
                        break;
                    } else {
                        AndroidImagePicker.getInstance().pickAndCrop(RegisterInfoActivity.this, true, 1000, new AndroidImagePicker.OnImageCropCompleteListener() { // from class: com.psychiatrygarden.activity.th
                            @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImageCropCompleteListener
                            public final void onImageCropComplete(Bitmap bitmap, float f2) throws IOException {
                                this.f13954c.lambda$onClick$1(bitmap, f2);
                            }
                        });
                        break;
                    }
                case R.id.ly_hospital /* 2131365150 */:
                    if (RegisterInfoActivity.this.dataHospitol != null) {
                        Intent intent = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectHospitolActivity.class);
                        intent.putExtra("dataList", (Serializable) RegisterInfoActivity.this.dataHospitol);
                        intent.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, RegisterInfoActivity.this.mContext));
                        intent.putExtra("title", "工作单位");
                        RegisterInfoActivity.this.startActivityForResult(intent, 7);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.ly_job /* 2131365167 */:
                    Intent intent2 = new Intent(RegisterInfoActivity.this, (Class<?>) EducationPublicActivity.class);
                    intent2.putExtra("type", "3");
                    RegisterInfoActivity.this.startActivityForResult(intent2, 16);
                    break;
                case R.id.rel_select /* 2131366377 */:
                    Intent intent3 = new Intent(RegisterInfoActivity.this, (Class<?>) EducationPublicActivity.class);
                    intent3.putExtra("type", "1");
                    RegisterInfoActivity.this.startActivityForResult(intent3, 13);
                    break;
                case R.id.rel_sex /* 2131366378 */:
                    if (RegisterInfoActivity.this.sexdata.size() > 0) {
                        RegisterInfoActivity.this.sexdata.clear();
                    }
                    RegisterInfoActivity.this.sexdata.add("男");
                    RegisterInfoActivity.this.sexdata.add("女");
                    RegisterInfoActivity.this.sexdata.add("保密");
                    Intent intent4 = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectTimeActivity.class);
                    intent4.putExtra("dataList", (Serializable) RegisterInfoActivity.this.sexdata);
                    intent4.putExtra("sextrue", true);
                    intent4.putExtra("title", "性别");
                    intent4.putExtra("name", "" + RegisterInfoActivity.this.et_sex.getText().toString());
                    RegisterInfoActivity.this.startActivityForResult(intent4, 6);
                    break;
                case R.id.rl_exams_major /* 2131366545 */:
                    if (!RegisterInfoActivity.this.dataMajor2.isEmpty()) {
                        Intent intent5 = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectMajorActivity.class);
                        intent5.putExtra("dataList", (Serializable) RegisterInfoActivity.this.dataMajor2);
                        intent5.putExtra("type", "2");
                        intent5.putExtra("title", "考研专业");
                        intent5.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, RegisterInfoActivity.this.mContext));
                        intent5.putExtra("istrue", true);
                        RegisterInfoActivity.this.startActivityForResult(intent5, 5);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_exams_time /* 2131366546 */:
                    if (RegisterInfoActivity.this.datatime != null && RegisterInfoActivity.this.datatime.size() != 0) {
                        Intent intent6 = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectTimeActivity.class);
                        intent6.putExtra("dataList", (Serializable) RegisterInfoActivity.this.datatime);
                        intent6.putExtra("title", "入学时间");
                        RegisterInfoActivity.this.startActivityForResult(intent6, 3);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_exams_university /* 2131366547 */:
                    if (RegisterInfoActivity.this.data != null && RegisterInfoActivity.this.data.size() != 0) {
                        Intent intent7 = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectOneActivity.class);
                        intent7.putExtra("dataList", (Serializable) RegisterInfoActivity.this.data);
                        intent7.putExtra("type", "2");
                        intent7.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, RegisterInfoActivity.this.mContext));
                        intent7.putExtra("title", "考研院校");
                        RegisterInfoActivity.this.startActivityForResult(intent7, 2);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                    break;
                case R.id.rl_exams_work_section /* 2131366548 */:
                    if (RegisterInfoActivity.this.dataDepartmentList != null) {
                        Intent intent8 = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectDepartActivity.class);
                        intent8.putExtra("dataList", (Serializable) RegisterInfoActivity.this.dataDepartmentList);
                        intent8.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, RegisterInfoActivity.this.mContext));
                        intent8.putExtra("title", "工作科室");
                        RegisterInfoActivity.this.startActivityForResult(intent8, 8);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_exams_work_time /* 2131366549 */:
                    if (RegisterInfoActivity.this.mWorkTime != null) {
                        Intent intent9 = new Intent(RegisterInfoActivity.this, (Class<?>) WorkTimeActivity.class);
                        intent9.putExtra("dataList", (Serializable) RegisterInfoActivity.this.mWorkTime);
                        intent9.putExtra("istrue", true);
                        intent9.putExtra("title", "工作时间");
                        RegisterInfoActivity.this.startActivityForResult(intent9, 9);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_university /* 2131366633 */:
                    if (RegisterInfoActivity.this.data != null && RegisterInfoActivity.this.data.size() != 0) {
                        Intent intent10 = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectOneActivity.class);
                        intent10.putExtra("dataList", (Serializable) RegisterInfoActivity.this.data);
                        intent10.putExtra("type", "1");
                        intent10.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, RegisterInfoActivity.this.mContext));
                        intent10.putExtra("title", "本科院校");
                        RegisterInfoActivity.this.startActivityForResult(intent10, 1);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                    break;
                case R.id.rl_university_major /* 2131366634 */:
                    if (RegisterInfoActivity.this.dataMajor != null && RegisterInfoActivity.this.dataMajor.size() != 0) {
                        Intent intent11 = new Intent(RegisterInfoActivity.this, (Class<?>) RegisterSelectMajorActivity.class);
                        intent11.putExtra("dataList", (Serializable) RegisterInfoActivity.this.dataMajor);
                        intent11.putExtra("type", "1");
                        intent11.putExtra("title", "本科专业");
                        intent11.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, RegisterInfoActivity.this.mContext));
                        intent11.putExtra("istrue", true);
                        RegisterInfoActivity.this.startActivityForResult(intent11, 4);
                        break;
                    } else {
                        RegisterInfoActivity.this.AlertToast("获取信息失败");
                        break;
                    }
                    break;
                case R.id.rl_work_unit /* 2131366639 */:
                    Intent intent12 = new Intent(RegisterInfoActivity.this, (Class<?>) EducationPublicActivity.class);
                    intent12.putExtra("type", "1");
                    intent12.putExtra("value", "work");
                    RegisterInfoActivity.this.startActivityForResult(intent12, 15);
                    break;
                case R.id.rl_xueli /* 2131366640 */:
                    Intent intent13 = new Intent(RegisterInfoActivity.this, (Class<?>) EducationPublicActivity.class);
                    intent13.putExtra("type", "2");
                    RegisterInfoActivity.this.startActivityForResult(intent13, 14);
                    break;
            }
        }
    }

    public static /* synthetic */ int access$2408(RegisterInfoActivity registerInfoActivity) {
        int i2 = registerInfoActivity.uploadimg;
        registerInfoActivity.uploadimg = i2 + 1;
        return i2;
    }

    private void getExamsTimes() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getEntranceTimeApi, null, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.6
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
                super.onSuccess((AnonymousClass6) s2);
                try {
                    GradUateTomeBean gradUateTomeBean = (GradUateTomeBean) new Gson().fromJson(s2, GradUateTomeBean.class);
                    if (gradUateTomeBean.getCode().equals("200")) {
                        RegisterInfoActivity.this.datatime = gradUateTomeBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRegister(String avater) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("avatar", avater);
        ajaxParams.put("channel", "10000");
        ajaxParams.put("code", getIntent().getStringExtra("code"));
        ajaxParams.put("nickname", this.et_nickname.getText().toString().replace(" ", ""));
        ajaxParams.put("user_type", "" + this.eduType);
        String stringExtra = getIntent().getStringExtra("socialite_username");
        String stringExtra2 = getIntent().getStringExtra(Constants.JumpUrlConstants.URL_KEY_OPENID);
        if (stringExtra != null || stringExtra2 != null) {
            ajaxParams.put("register_type", "2");
            ajaxParams.put("type", "wechat");
            ajaxParams.put(Constants.JumpUrlConstants.URL_KEY_OPENID, stringExtra2);
            ajaxParams.put("socialite_username", stringExtra);
        }
        if (this.eduType.equals("student") || this.eduType.equals("nurse")) {
            ajaxParams.put("now_id", this.REGISTER_UNIVERSITY_ID);
            ajaxParams.put("now_major_id", this.REGISTER_UNIVERSITY_MAJOR_ID);
            ajaxParams.put("entrance_time", this.REGISTER_EXAMS_TIME);
            ajaxParams.put("education_id", "" + this.eduUUser);
        } else if (this.eduType.equals("work_other")) {
            ajaxParams.put("position", this.mEtJob.getText().toString().trim());
            ajaxParams.put("unit", this.mEtUnit.getText().toString().trim());
            ajaxParams.put("department", this.mEtDepartment.getText().toString().trim());
            ajaxParams.put("work_time_id", "" + this.WORK_TIME_ID);
        } else {
            ajaxParams.put("hospital_id", this.REGISTER_HOSPITOL_ID);
            ajaxParams.put("department_id", this.REGISTERDEPART_ID);
            ajaxParams.put("position_id", this.jobId);
            ajaxParams.put("work_time_id", this.WORK_TIME_ID);
        }
        ajaxParams.put("mobile", getIntent().getStringExtra("mobile"));
        ajaxParams.put(CommonParameter.password, Md5Util.MD5Encode(getIntent().getStringExtra(CommonParameter.password)));
        ajaxParams.put("invite_code", getIntent().getStringExtra("et_register_code"));
        ajaxParams.put(CommonNetImpl.SEX, this.REGISTER_SEX_STR);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mRegisterNewUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.8
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterInfoActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterInfoActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str;
                String str2;
                String strOptString;
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.LOGIN_DATABASE_UID, SdkConstant.UMENG_ALIS, RegisterInfoActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.LOGIN_DATABASE_IS_CHANGE, "1", RegisterInfoActivity.this.mContext);
                        try {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_UNLOCK_CHECK_POINTS_NUM, jSONObject.optJSONObject("data").optString("share_unlock_check_points"), RegisterInfoActivity.this.mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_UNLOCK_FIVE_QUESTION_NUM, jSONObject.optJSONObject("data").optString("share_unlock_five_question"), RegisterInfoActivity.this.mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_UNLOCK_PRACTICE_NUM, jSONObject.optJSONObject("data").optString("share_unlock_practice"), RegisterInfoActivity.this.mContext);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        UserConfig.getInstance().saveUser(((UserInfoBean) new Gson().fromJson(s2, UserInfoBean.class)).getData());
                        SharePreferencesUtils.writeLongConfig(CommonParameter.GRADE_TIME, Long.valueOf(System.currentTimeMillis()), RegisterInfoActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.TEL, RegisterInfoActivity.this.getIntent().getStringExtra("mobile"), RegisterInfoActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.password, RegisterInfoActivity.this.getIntent().getStringExtra(CommonParameter.password), RegisterInfoActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_XUE, jSONObject.optJSONObject("data").optString("unlock"), RegisterInfoActivity.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_ZHUAN, jSONObject.optJSONObject("data").optString("unlock"), RegisterInfoActivity.this.mContext);
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data").optJSONObject("invite");
                        if (jSONObjectOptJSONObject != null) {
                            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("identity_ids");
                            String strOptString2 = jSONObjectOptJSONObject.optString("status");
                            if (Constants.VIA_REPORT_TYPE_SHARE_TO_QQ.equals(strOptString2) || "21".equals(strOptString2)) {
                                RegisterInfoActivity.this.AlertToast("" + jSONObjectOptJSONObject.optString("message"));
                                Intent intent = new Intent(RegisterInfoActivity.this, (Class<?>) HomePageNewActivity.class);
                                intent.addFlags(268468224);
                                RegisterInfoActivity.this.startActivity(intent);
                                RegisterInfoActivity.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                                RegisterInfoActivity.this.finish();
                            } else if ("20".equals(strOptString2)) {
                                String strOptString3 = jSONObjectOptJSONObject.optString("view_button");
                                String strOptString4 = jSONObjectOptJSONObject.optString("app_name");
                                String strOptString5 = jSONObjectOptJSONObject.optString("reward_day");
                                String strOptString6 = jSONObjectOptJSONObject.optString("app_id");
                                if (jSONObjectOptJSONObject2 != null) {
                                    String strOptString7 = jSONObjectOptJSONObject2.optString("first_identity_id", "");
                                    String strOptString8 = jSONObjectOptJSONObject2.optString("second_identity_id", "");
                                    strOptString = jSONObjectOptJSONObject2.optString("identity_id", "");
                                    str = strOptString7;
                                    str2 = strOptString8;
                                } else {
                                    str = "";
                                    str2 = str;
                                    strOptString = str2;
                                }
                                if ("1".equals(strOptString3)) {
                                    InviteOneDialog.INSTANCE.newInstance(strOptString4, strOptString5, strOptString6, 0, str, str2, strOptString).show(RegisterInfoActivity.this.getSupportFragmentManager(), "invite");
                                } else if ("0".equals(strOptString3)) {
                                    InviteTwoDialog.INSTANCE.newInstance(strOptString5, strOptString6, 0).show(RegisterInfoActivity.this.getSupportFragmentManager(), "invite");
                                } else {
                                    Log.e(RegisterInfoActivity.this.TAG, "view_button字段返回错误，请查看相关接口返回>>>>>>>" + strOptString3);
                                    RegisterInfoActivity.this.AlertToast("注册成功");
                                    Intent intent2 = new Intent(RegisterInfoActivity.this, (Class<?>) HomePageNewActivity.class);
                                    intent2.addFlags(268468224);
                                    RegisterInfoActivity.this.startActivity(intent2);
                                    RegisterInfoActivity.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                                    RegisterInfoActivity.this.finish();
                                }
                            } else {
                                RegisterInfoActivity.this.AlertToast("注册成功");
                                Intent intent3 = new Intent(RegisterInfoActivity.this, (Class<?>) HomePageNewActivity.class);
                                intent3.addFlags(268468224);
                                RegisterInfoActivity.this.startActivity(intent3);
                                RegisterInfoActivity.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                                RegisterInfoActivity.this.finish();
                            }
                        } else {
                            RegisterInfoActivity.this.AlertToast("注册成功");
                            Intent intent4 = new Intent(RegisterInfoActivity.this, (Class<?>) HomePageNewActivity.class);
                            intent4.addFlags(268468224);
                            RegisterInfoActivity.this.startActivity(intent4);
                            RegisterInfoActivity.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                            RegisterInfoActivity.this.finish();
                        }
                    } else {
                        RegisterInfoActivity.this.AlertToast("" + jSONObject.optString("message"));
                    }
                    RegisterInfoActivity.this.hideProgressDialog();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    private void initLocationOption() {
        try {
            startService(new Intent(this, (Class<?>) YkbLocationService.class));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$0(String str, String str2) {
        LogUtils.d("onResponse", str + StrPool.TAB + str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$1(VolleyError volleyError, String str) {
    }

    public void getDepartmentData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mDepartmentUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.3
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
                super.onSuccess((AnonymousClass3) s2);
                try {
                    RegisterDepartmentBean registerDepartmentBean = (RegisterDepartmentBean) new Gson().fromJson(s2, RegisterDepartmentBean.class);
                    if (registerDepartmentBean.getCode().equals("200")) {
                        RegisterInfoActivity.this.dataDepartmentList = registerDepartmentBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getHospitalData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mHospitalUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.1
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
                super.onSuccess((AnonymousClass1) s2);
                try {
                    RegisterDataBean registerDataBean = (RegisterDataBean) new Gson().fromJson(s2, RegisterDataBean.class);
                    if (registerDataBean.getCode().equals("200")) {
                        RegisterInfoActivity.this.dataHospitol = registerDataBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getMajorData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.majorUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.5
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
                    MajorBean majorBean = (MajorBean) new Gson().fromJson(s2, MajorBean.class);
                    if (majorBean.getCode().equals("200")) {
                        RegisterInfoActivity.this.dataMajor = majorBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getRegisterData1() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mShengfen, null, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.4
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
                    RegisterDataBean registerDataBean = (RegisterDataBean) new Gson().fromJson(s2, RegisterDataBean.class);
                    if (registerDataBean.getCode().equals("200")) {
                        RegisterInfoActivity.this.data = registerDataBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getWorkTimeData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mWorkTimeUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.2
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
                super.onSuccess((AnonymousClass2) s2);
                try {
                    WorkTimeBean workTimeBean = (WorkTimeBean) new Gson().fromJson(s2, WorkTimeBean.class);
                    if (workTimeBean.getCode().equals("200")) {
                        RegisterInfoActivity.this.mWorkTime = workTimeBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.rel_select = (RelativeLayout) findViewById(R.id.rel_select);
        this.et_select = (TextView) findViewById(R.id.et_select);
        this.rl_xueli = (RelativeLayout) findViewById(R.id.rl_xueli);
        this.tv_xueli_major = (TextView) findViewById(R.id.tv_xueli_major);
        this.mRelSex = (RelativeLayout) findViewById(R.id.rel_sex);
        this.iv_register_photo = (ImageView) findViewById(R.id.iv_register_photo);
        this.mRlUniversity = (RelativeLayout) findViewById(R.id.rl_university);
        this.mRlUniversityMajor = (RelativeLayout) findViewById(R.id.rl_university_major);
        this.mRlExamsUniversity = (RelativeLayout) findViewById(R.id.rl_exams_university);
        this.mRlExamsMajor = (RelativeLayout) findViewById(R.id.rl_exams_major);
        this.mRlExamsTime = (RelativeLayout) findViewById(R.id.rl_exams_time);
        this.et_nickname = (EditText) findViewById(R.id.et_nickname);
        this.mTvUniversity = (TextView) findViewById(R.id.tv_university);
        this.mTvUniversityMajor = (TextView) findViewById(R.id.tv_university_major);
        this.mTvExamsUniversity = (TextView) findViewById(R.id.tv_exams_university);
        this.mTvExamsMajor = (TextView) findViewById(R.id.tv_exams_major);
        this.mTvExamsTime = (TextView) findViewById(R.id.tv_exams_time);
        this.mBtComplete = (Button) findViewById(R.id.bt_complete);
        this.et_sex = (TextView) findViewById(R.id.et_sex);
        this.et_nickname.setGravity(5);
        this.mTvExamsWorkUnit = (TextView) findViewById(R.id.tv_exams_work_unit);
        this.mTvWorkSection = (TextView) findViewById(R.id.tv_work_section);
        this.mTvWorkTime = (TextView) findViewById(R.id.tv_exams_work_time1);
        this.mRlWorkUnit = (RelativeLayout) findViewById(R.id.rl_work_unit);
        this.mRlExamsWorkSection = (RelativeLayout) findViewById(R.id.rl_exams_work_section);
        this.mLyWork = (LinearLayout) findViewById(R.id.ly_work);
        this.mLyWorkOther = (LinearLayout) findViewById(R.id.ly_other);
        this.mEtUnit = (EditText) findViewById(R.id.et_unit);
        this.mEtDepartment = (EditText) findViewById(R.id.et_department);
        this.mEtJob = (EditText) findViewById(R.id.et_job);
        this.mLyJob = (RelativeLayout) findViewById(R.id.ly_job);
        this.mTvJob = (TextView) findViewById(R.id.tv_job);
        this.lyHospital = (RelativeLayout) findViewById(R.id.ly_hospital);
        this.mTvHospital = (TextView) findViewById(R.id.tv_hospital);
        ((RelativeLayout) findViewById(R.id.rl_exams_work_time)).setOnClickListener(this.mOnclick);
        this.line_xuexiao = (LinearLayout) findViewById(R.id.line_xuexiao);
        this.line_zhuanye = (LinearLayout) findViewById(R.id.line_zhuanye);
    }

    public void initView(String userType) {
        if (userType.equals("student") || userType.equals("nurse")) {
            this.line_xuexiao.setVisibility(0);
            this.line_zhuanye.setVisibility(8);
            getRegisterData1();
            getMajorData();
            getExamsTimes();
            return;
        }
        this.line_xuexiao.setVisibility(8);
        this.line_zhuanye.setVisibility(0);
        getHospitalData();
        getDepartmentData();
        getWorkTimeData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
        }
        switch (requestCode) {
            case 1:
                this.REGISTER_UNIVERSITY_ID = data.getStringExtra("area_id");
                this.mTvUniversity.setText(data.getStringExtra("title"));
                break;
            case 2:
                this.mTvExamsUniversity.setText(data.getStringExtra("title"));
                break;
            case 3:
                this.mTvExamsTime.setText(data.getStringExtra("title"));
                this.REGISTER_EXAMS_TIME = data.getStringExtra("title");
                break;
            case 4:
                this.mTvUniversityMajor.setText(data.getStringExtra("title"));
                this.REGISTER_UNIVERSITY_MAJOR_ID = data.getStringExtra("area_id");
                break;
            case 5:
                this.mTvExamsMajor.setText(data.getStringExtra("title"));
                break;
            case 6:
                String stringExtra = data.getStringExtra("title");
                this.et_sex.setText(data.getStringExtra("title"));
                if (!"男".equals(stringExtra)) {
                    if (!"女".equals(stringExtra)) {
                        this.REGISTER_SEX_STR = "0";
                        break;
                    } else {
                        this.REGISTER_SEX_STR = "2";
                        break;
                    }
                } else {
                    this.REGISTER_SEX_STR = "1";
                    break;
                }
            case 7:
                this.mTvHospital.setText(data.getStringExtra("title"));
                this.REGISTER_HOSPITOL_ID = data.getStringExtra("area_id");
                break;
            case 8:
                this.REGISTERDEPART_ID = data.getStringExtra("area_id");
                this.mTvWorkSection.setText(data.getStringExtra("title"));
                break;
            case 9:
                this.WORK_TIME_ID = data.getStringExtra("work_id");
                this.mTvWorkTime.setText(data.getStringExtra("title"));
                break;
            case 13:
                this.eduType = data.getStringExtra("value");
                this.et_select.setText(data.getStringExtra("title"));
                initView(this.eduType);
                break;
            case 14:
                this.eduUUser = data.getStringExtra("value");
                this.tv_xueli_major.setText(data.getStringExtra("title"));
                break;
            case 15:
                this.eduType = data.getStringExtra("value");
                this.mTvExamsWorkUnit.setText(data.getStringExtra("title"));
                if (!this.eduType.equals("work_other")) {
                    this.mLyWork.setVisibility(0);
                    this.mLyWorkOther.setVisibility(8);
                    break;
                } else {
                    this.mLyWork.setVisibility(8);
                    this.mLyWorkOther.setVisibility(0);
                    break;
                }
            case 16:
                this.jobId = data.getStringExtra("value");
                this.mTvJob.setText(data.getStringExtra("title"));
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(LocationEvent event) {
        com.alibaba.fastjson.JSONObject jSONObject = new com.alibaba.fastjson.JSONObject();
        if (event.getLatitude() <= 0.0d || event.getLongitude() <= 0.0d) {
            return;
        }
        jSONObject.put("longitude", (Object) Double.valueOf(event.getLongitude()));
        jSONObject.put("latitude", (Object) Double.valueOf(event.getLatitude()));
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("location", jSONObject.toString());
        YJYHttpUtils.post(this, NetworkRequestsURL.accessLogUrl, arrayMap, new Response.Listener() { // from class: com.psychiatrygarden.activity.qh
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                RegisterInfoActivity.lambda$onEventMainThread$0((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.rh
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                RegisterInfoActivity.lambda$onEventMainThread$1(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {
            if (grantResults[0] == -1) {
                boolean zShouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA);
                boolean zShouldShowRequestPermissionRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE);
                if (zShouldShowRequestPermissionRationale || zShouldShowRequestPermissionRationale2) {
                    return;
                }
                ToastUtil.shortToast(this, "请检查app相机及存储权限是否打开！");
                return;
            }
            return;
        }
        if (274 == requestCode) {
            int i2 = grantResults[0];
            if (i2 != -1) {
                if (i2 != 0) {
                    return;
                }
                try {
                    initLocationOption();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.ACCESS_COARSE_LOCATION)) {
                NewToast.showShort(this, "位置权限被禁用，请在权限管理修改", 0).show();
            }
            try {
                initLocationOption();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle(R.string.user_register);
        setContentView(R.layout.activity_register_info);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtComplete.setOnClickListener(this.mOnclick);
        this.mRlUniversity.setOnClickListener(this.mOnclick);
        this.mRlUniversityMajor.setOnClickListener(this.mOnclick);
        this.mRlExamsUniversity.setOnClickListener(this.mOnclick);
        this.mRlExamsMajor.setOnClickListener(this.mOnclick);
        this.mRlExamsTime.setOnClickListener(this.mOnclick);
        this.iv_register_photo.setOnClickListener(this.mOnclick);
        this.mRelSex.setOnClickListener(this.mOnclick);
        this.mRlWorkUnit.setOnClickListener(this.mOnclick);
        this.mRlExamsWorkSection.setOnClickListener(this.mOnclick);
        this.line_xuexiao.setOnClickListener(this.mOnclick);
        this.line_zhuanye.setOnClickListener(this.mOnclick);
        this.rel_select.setOnClickListener(this.mOnclick);
        this.rl_xueli.setOnClickListener(this.mOnclick);
        this.mLyJob.setOnClickListener(this.mOnclick);
        this.lyHospital.setOnClickListener(this.mOnclick);
    }

    public void uploadAvatar() {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(this.myPhotoPath));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this.mContext, NetworkRequestsURL.mAvatar2URL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.RegisterInfoActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RegisterInfoActivity.this.hideProgressDialog();
                RegisterInfoActivity.this.AlertToast("头像上传失败，请重新上传");
                try {
                    RegisterInfoActivity.access$2408(RegisterInfoActivity.this);
                    if (RegisterInfoActivity.this.uploadimg > 3) {
                        RegisterInfoActivity.this.getRegister("");
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                RegisterInfoActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass9) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        RegisterInfoActivity.this.avatar_url = jSONObject.optString("data");
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                RegisterInfoActivity registerInfoActivity = RegisterInfoActivity.this;
                registerInfoActivity.getRegister(registerInfoActivity.avatar_url);
            }
        });
    }
}
