package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.psychiatrygarden.ProjectApp;
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
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SdkConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.InviteOneDialog;
import com.psychiatrygarden.widget.InviteTwoDialog;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ImproveUserInfoAct extends BaseActivity {
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 274;
    private TextView et_select;
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
    private TextView mTvHospitalValue;
    private TextView mTvJob;
    private TextView mTvUniversity;
    private TextView mTvUniversityMajor;
    private TextView mTvWorkSection;
    private TextView mTvWorkTime;
    private RelativeLayout rel_select;
    private RelativeLayout rl_xueli;
    private TextView tv_xueli_major;
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
    View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.7
        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
            }
            switch (v2.getId()) {
                case R.id.bt_complete /* 2131362291 */:
                    if (!TextUtils.isEmpty(ImproveUserInfoAct.this.eduType)) {
                        if (!ImproveUserInfoAct.this.eduType.equals("work")) {
                            if (!ImproveUserInfoAct.this.eduType.equals("work_other")) {
                                if (!ImproveUserInfoAct.this.mTvUniversity.getText().toString().trim().equals("") && !ImproveUserInfoAct.this.mTvUniversity.getText().toString().trim().equals("--")) {
                                    if (!ImproveUserInfoAct.this.mTvUniversityMajor.getText().toString().trim().equals("") && !ImproveUserInfoAct.this.mTvUniversityMajor.getText().toString().trim().equals("--")) {
                                        if (!"".equals(ImproveUserInfoAct.this.eduUUser)) {
                                            if (ImproveUserInfoAct.this.mTvExamsTime.getText().toString().trim().equals("") || ImproveUserInfoAct.this.mTvExamsTime.getText().toString().trim().equals("--")) {
                                                ImproveUserInfoAct.this.AlertToast("请选择入学时间");
                                                break;
                                            }
                                        } else {
                                            ImproveUserInfoAct.this.AlertToast("请选择学历");
                                            break;
                                        }
                                    } else {
                                        ImproveUserInfoAct.this.AlertToast("请选择本科专业");
                                        break;
                                    }
                                } else {
                                    ImproveUserInfoAct.this.AlertToast("请选择本科院校");
                                    break;
                                }
                            } else if (!TextUtils.isEmpty(ImproveUserInfoAct.this.mEtUnit.getText().toString().trim())) {
                                if (!TextUtils.isEmpty(ImproveUserInfoAct.this.mEtDepartment.getText().toString().trim())) {
                                    if (TextUtils.isEmpty(ImproveUserInfoAct.this.mEtDepartment.getText().toString().trim())) {
                                        ImproveUserInfoAct.this.AlertToast("请输入职务");
                                        break;
                                    }
                                } else {
                                    ImproveUserInfoAct.this.AlertToast("请输入所在部门");
                                    break;
                                }
                            } else {
                                ImproveUserInfoAct.this.AlertToast("请输入所在单位");
                                break;
                            }
                        } else if (!ImproveUserInfoAct.this.mTvExamsWorkUnit.getText().toString().trim().equals("") && !ImproveUserInfoAct.this.mTvExamsWorkUnit.getText().toString().trim().equals("--")) {
                            if (!ImproveUserInfoAct.this.mTvWorkSection.getText().toString().trim().equals("") && !ImproveUserInfoAct.this.mTvWorkSection.getText().toString().trim().equals("--")) {
                                if (ImproveUserInfoAct.this.mTvWorkTime.getText().toString().equals("") || ImproveUserInfoAct.this.mTvWorkTime.getText().toString().equals("--")) {
                                    ImproveUserInfoAct.this.AlertToast("请选择工作时间");
                                    break;
                                }
                            } else {
                                ImproveUserInfoAct.this.AlertToast("请选择工作科室");
                                break;
                            }
                        } else {
                            ImproveUserInfoAct.this.AlertToast("请选择工作单位");
                            break;
                        }
                        ImproveUserInfoAct.this.getRegister();
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("请选择个人身份");
                        break;
                    }
                    break;
                case R.id.ly_hospital /* 2131365150 */:
                    if (ImproveUserInfoAct.this.dataHospitol != null) {
                        Intent intent = new Intent(ImproveUserInfoAct.this, (Class<?>) RegisterSelectHospitolActivity.class);
                        intent.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.dataHospitol);
                        intent.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ImproveUserInfoAct.this.mContext));
                        intent.putExtra("title", "工作单位");
                        ImproveUserInfoAct.this.startActivityForResult(intent, 7);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.ly_job /* 2131365167 */:
                    Intent intent2 = new Intent(ImproveUserInfoAct.this, (Class<?>) EducationPublicActivity.class);
                    intent2.putExtra("type", "3");
                    ImproveUserInfoAct.this.startActivityForResult(intent2, 16);
                    break;
                case R.id.rel_select /* 2131366377 */:
                    Intent intent3 = new Intent(ImproveUserInfoAct.this, (Class<?>) EducationPublicActivity.class);
                    intent3.putExtra("type", "1");
                    ImproveUserInfoAct.this.startActivityForResult(intent3, 13);
                    break;
                case R.id.rl_exams_major /* 2131366545 */:
                    if (!ImproveUserInfoAct.this.dataMajor2.isEmpty()) {
                        Intent intent4 = new Intent(ImproveUserInfoAct.this, (Class<?>) RegisterSelectMajorActivity.class);
                        intent4.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.dataMajor2);
                        intent4.putExtra("type", "2");
                        intent4.putExtra("title", "考研专业");
                        intent4.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ImproveUserInfoAct.this.mContext));
                        intent4.putExtra("istrue", true);
                        ImproveUserInfoAct.this.startActivityForResult(intent4, 5);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_exams_time /* 2131366546 */:
                    if (ImproveUserInfoAct.this.datatime != null && ImproveUserInfoAct.this.datatime.size() != 0) {
                        Intent intent5 = new Intent(ImproveUserInfoAct.this, (Class<?>) RegisterSelectTimeActivity.class);
                        intent5.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.datatime);
                        intent5.putExtra("title", "入学时间");
                        ImproveUserInfoAct.this.startActivityForResult(intent5, 3);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_exams_university /* 2131366547 */:
                    if (ImproveUserInfoAct.this.data != null && ImproveUserInfoAct.this.data.size() != 0) {
                        Intent intent6 = new Intent(ImproveUserInfoAct.this, (Class<?>) RegisterSelectOneActivity.class);
                        intent6.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.data);
                        intent6.putExtra("type", "2");
                        intent6.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ImproveUserInfoAct.this.mContext));
                        intent6.putExtra("title", "考研院校");
                        ImproveUserInfoAct.this.startActivityForResult(intent6, 2);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                    break;
                case R.id.rl_exams_work_section /* 2131366548 */:
                    if (ImproveUserInfoAct.this.dataDepartmentList != null) {
                        Intent intent7 = new Intent(ImproveUserInfoAct.this, (Class<?>) RegisterSelectDepartActivity.class);
                        intent7.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.dataDepartmentList);
                        intent7.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ImproveUserInfoAct.this.mContext));
                        intent7.putExtra("title", "工作科室");
                        ImproveUserInfoAct.this.startActivityForResult(intent7, 8);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_exams_work_time /* 2131366549 */:
                    if (ImproveUserInfoAct.this.mWorkTime != null) {
                        Intent intent8 = new Intent(ImproveUserInfoAct.this, (Class<?>) WorkTimeActivity.class);
                        intent8.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.mWorkTime);
                        intent8.putExtra("istrue", true);
                        intent8.putExtra("title", "工作时间");
                        ImproveUserInfoAct.this.startActivityForResult(intent8, 9);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                case R.id.rl_university /* 2131366633 */:
                    if (ImproveUserInfoAct.this.data != null && ImproveUserInfoAct.this.data.size() != 0) {
                        Intent intent9 = new Intent(ImproveUserInfoAct.this, (Class<?>) RegisterSelectOneActivity.class);
                        intent9.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.data);
                        intent9.putExtra("type", "1");
                        intent9.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ImproveUserInfoAct.this.mContext));
                        intent9.putExtra("title", "本科院校");
                        ImproveUserInfoAct.this.startActivityForResult(intent9, 1);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                    break;
                case R.id.rl_university_major /* 2131366634 */:
                    if (ImproveUserInfoAct.this.dataMajor != null && ImproveUserInfoAct.this.dataMajor.size() != 0) {
                        Intent intent10 = new Intent(ImproveUserInfoAct.this, (Class<?>) RegisterSelectMajorActivity.class);
                        intent10.putExtra("dataList", (Serializable) ImproveUserInfoAct.this.dataMajor);
                        intent10.putExtra("type", "1");
                        intent10.putExtra("title", "本科专业");
                        intent10.putExtra("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ImproveUserInfoAct.this.mContext));
                        intent10.putExtra("istrue", true);
                        ImproveUserInfoAct.this.startActivityForResult(intent10, 4);
                        break;
                    } else {
                        ImproveUserInfoAct.this.AlertToast("获取信息失败");
                        break;
                    }
                    break;
                case R.id.rl_work_unit /* 2131366639 */:
                    Intent intent11 = new Intent(ImproveUserInfoAct.this, (Class<?>) EducationPublicActivity.class);
                    intent11.putExtra("type", "1");
                    intent11.putExtra("value", "work");
                    ImproveUserInfoAct.this.startActivityForResult(intent11, 15);
                    break;
                case R.id.rl_xueli /* 2131366640 */:
                    Intent intent12 = new Intent(ImproveUserInfoAct.this, (Class<?>) EducationPublicActivity.class);
                    intent12.putExtra("type", "2");
                    ImproveUserInfoAct.this.startActivityForResult(intent12, 14);
                    break;
            }
        }
    };
    private final List<String> sexdata = new ArrayList();

    private void getExamsTimes() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getEntranceTimeApi, null, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.6
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
                        ImproveUserInfoAct.this.datatime = gradUateTomeBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRegister() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_type", "" + this.eduType);
        ajaxParams.put("beta_version", "0");
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
        ajaxParams.put("mobile", getIntent().getStringExtra(AliyunLogCommon.TERMINAL_TYPE));
        ajaxParams.put(CommonParameter.password, Md5Util.MD5Encode(getIntent().getStringExtra("pwd")));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mLoginUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.8
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ImproveUserInfoAct.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ImproveUserInfoAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str;
                String strOptString;
                String strOptString2;
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.LOGIN_DATABASE_UID, SdkConstant.UMENG_ALIS, ImproveUserInfoAct.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.LOGIN_DATABASE_IS_CHANGE, "1", ImproveUserInfoAct.this.mContext);
                        try {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_UNLOCK_CHECK_POINTS_NUM, jSONObject.optJSONObject("data").optString("share_unlock_check_points"), ImproveUserInfoAct.this.mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_UNLOCK_FIVE_QUESTION_NUM, jSONObject.optJSONObject("data").optString("share_unlock_five_question"), ImproveUserInfoAct.this.mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.SHARE_UNLOCK_PRACTICE_NUM, jSONObject.optJSONObject("data").optString("share_unlock_practice"), ImproveUserInfoAct.this.mContext);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        UserConfig.getInstance().saveUser(((UserInfoBean) new Gson().fromJson(s2, UserInfoBean.class)).getData());
                        SharePreferencesUtils.writeLongConfig(CommonParameter.GRADE_TIME, Long.valueOf(System.currentTimeMillis()), ImproveUserInfoAct.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.TEL, ImproveUserInfoAct.this.getIntent().getStringExtra(AliyunLogCommon.TERMINAL_TYPE), ImproveUserInfoAct.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.password, ImproveUserInfoAct.this.getIntent().getStringExtra("pwd"), ImproveUserInfoAct.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_XUE, jSONObject.optJSONObject("data").optString("unlock"), ImproveUserInfoAct.this.mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.UNLOCK_ZHUAN, jSONObject.optJSONObject("data").optString("unlock"), ImproveUserInfoAct.this.mContext);
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data").optJSONObject("invite");
                        if (jSONObjectOptJSONObject != null) {
                            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("identity_ids");
                            String strOptString3 = jSONObjectOptJSONObject.optString("status");
                            if (Constants.VIA_REPORT_TYPE_SHARE_TO_QQ.equals(strOptString3) || "21".equals(strOptString3)) {
                                ImproveUserInfoAct.this.AlertToast("" + jSONObjectOptJSONObject.optString("message"));
                                Intent intent = new Intent(ImproveUserInfoAct.this, (Class<?>) HomePageNewActivity.class);
                                intent.addFlags(268468224);
                                ImproveUserInfoAct.this.startActivity(intent);
                                ImproveUserInfoAct.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                                ImproveUserInfoAct.this.finish();
                            } else if ("20".equals(strOptString3)) {
                                String strOptString4 = jSONObjectOptJSONObject.optString("view_button");
                                String strOptString5 = jSONObjectOptJSONObject.optString("app_name");
                                String strOptString6 = jSONObjectOptJSONObject.optString("reward_day");
                                String strOptString7 = jSONObjectOptJSONObject.optString("app_id");
                                if (jSONObjectOptJSONObject2 != null) {
                                    String strOptString8 = jSONObjectOptJSONObject2.optString("first_identity_id", "");
                                    strOptString = jSONObjectOptJSONObject2.optString("second_identity_id", "");
                                    strOptString2 = jSONObjectOptJSONObject2.optString("identity_id", "");
                                    str = strOptString8;
                                } else {
                                    str = "";
                                    strOptString = str;
                                    strOptString2 = strOptString;
                                }
                                if ("1".equals(strOptString4)) {
                                    InviteOneDialog.INSTANCE.newInstance(strOptString5, strOptString6, strOptString7, 0, str, strOptString, strOptString2).show(ImproveUserInfoAct.this.getSupportFragmentManager(), "invite");
                                } else if ("0".equals(strOptString4)) {
                                    InviteTwoDialog.INSTANCE.newInstance(strOptString6, strOptString7, 0).show(ImproveUserInfoAct.this.getSupportFragmentManager(), "invite");
                                } else {
                                    Log.e(ImproveUserInfoAct.this.TAG, "view_button字段返回错误，请查看相关接口返回>>>>>>>" + strOptString4);
                                    ImproveUserInfoAct.this.AlertToast(jSONObject.optString("message"));
                                    Intent intent2 = new Intent(ImproveUserInfoAct.this, (Class<?>) HomePageNewActivity.class);
                                    intent2.addFlags(268468224);
                                    ImproveUserInfoAct.this.startActivity(intent2);
                                    ImproveUserInfoAct.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                                    ImproveUserInfoAct.this.finish();
                                }
                            } else {
                                ImproveUserInfoAct.this.AlertToast(jSONObject.optString("message"));
                                Intent intent3 = new Intent(ImproveUserInfoAct.this, (Class<?>) HomePageNewActivity.class);
                                intent3.addFlags(268468224);
                                ImproveUserInfoAct.this.startActivity(intent3);
                                ImproveUserInfoAct.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                                ImproveUserInfoAct.this.finish();
                            }
                        } else {
                            ImproveUserInfoAct.this.AlertToast(jSONObject.optString("message"));
                            Intent intent4 = new Intent(ImproveUserInfoAct.this, (Class<?>) HomePageNewActivity.class);
                            intent4.addFlags(268468224);
                            ImproveUserInfoAct.this.startActivity(intent4);
                            ImproveUserInfoAct.this.overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
                            ImproveUserInfoAct.this.finish();
                        }
                    } else {
                        ImproveUserInfoAct.this.AlertToast("" + jSONObject.optString("message"));
                    }
                    ImproveUserInfoAct.this.hideProgressDialog();
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
    public static /* synthetic */ void lambda$dialog$3(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialog$4(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        ProjectApp.instance().exit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$1(String str, String str2) {
        LogUtils.d("onResponse", str + StrPool.TAB + str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$2(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        dialog(R.string.app_close);
    }

    public static Intent startActivity(Context context, String phone, String pwd) {
        Intent intent = new Intent(context, (Class<?>) ImproveUserInfoAct.class);
        intent.putExtra(AliyunLogCommon.TERMINAL_TYPE, phone);
        intent.putExtra("pwd", pwd);
        return intent;
    }

    public void dialog(int str) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        customDialog.setMessage(this.mContext.getString(str));
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.yc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ImproveUserInfoAct.lambda$dialog$3(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.zc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ImproveUserInfoAct.lambda$dialog$4(customDialog, view);
            }
        });
        customDialog.show();
    }

    public void getDepartmentData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mDepartmentUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.3
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
                        ImproveUserInfoAct.this.dataDepartmentList = registerDepartmentBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getHospitalData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mHospitalUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.1
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
                        ImproveUserInfoAct.this.dataHospitol = registerDataBean.getData();
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
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.majorUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.5
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
                        ImproveUserInfoAct.this.dataMajor = majorBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getRegisterData1() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mShengfen, null, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.4
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
                        ImproveUserInfoAct.this.data = registerDataBean.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getWorkTimeData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mWorkTimeUrl, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ImproveUserInfoAct.2
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
                        ImproveUserInfoAct.this.mWorkTime = workTimeBean.getData();
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
        this.mRlUniversity = (RelativeLayout) findViewById(R.id.rl_university);
        this.mRlUniversityMajor = (RelativeLayout) findViewById(R.id.rl_university_major);
        this.mRlExamsUniversity = (RelativeLayout) findViewById(R.id.rl_exams_university);
        this.mRlExamsMajor = (RelativeLayout) findViewById(R.id.rl_exams_major);
        this.mRlExamsTime = (RelativeLayout) findViewById(R.id.rl_exams_time);
        this.mTvUniversity = (TextView) findViewById(R.id.tv_university);
        this.mTvUniversityMajor = (TextView) findViewById(R.id.tv_university_major);
        this.mTvExamsUniversity = (TextView) findViewById(R.id.tv_exams_university);
        this.mTvExamsMajor = (TextView) findViewById(R.id.tv_exams_major);
        this.mTvExamsTime = (TextView) findViewById(R.id.tv_exams_time);
        this.mBtComplete = (Button) findViewById(R.id.bt_complete);
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
        this.mTvHospitalValue = (TextView) findViewById(R.id.tv_exams_hospital);
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
        if ("work_other".equals(this.eduType)) {
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
            case 7:
                this.mTvHospitalValue.setText(data.getStringExtra("title"));
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
        YJYHttpUtils.post(this, NetworkRequestsURL.accessLogUrl, arrayMap, new Response.Listener() { // from class: com.psychiatrygarden.activity.ad
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                ImproveUserInfoAct.lambda$onEventMainThread$1((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.bd
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                ImproveUserInfoAct.lambda$onEventMainThread$2(volleyError, str);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        dialog(R.string.app_close);
        return true;
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
        setTitle("身份信息完善");
        setContentView(R.layout.layout_improve_user_info);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.cd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11143c.lambda$setContentView$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtComplete.setOnClickListener(this.mOnclick);
        this.mRlUniversity.setOnClickListener(this.mOnclick);
        this.mRlUniversityMajor.setOnClickListener(this.mOnclick);
        this.mRlExamsUniversity.setOnClickListener(this.mOnclick);
        this.mRlExamsMajor.setOnClickListener(this.mOnclick);
        this.mRlExamsTime.setOnClickListener(this.mOnclick);
        this.mRlWorkUnit.setOnClickListener(this.mOnclick);
        this.mRlExamsWorkSection.setOnClickListener(this.mOnclick);
        this.line_xuexiao.setOnClickListener(this.mOnclick);
        this.line_zhuanye.setOnClickListener(this.mOnclick);
        this.rel_select.setOnClickListener(this.mOnclick);
        this.rl_xueli.setOnClickListener(this.mOnclick);
        this.mLyJob.setOnClickListener(this.mOnclick);
        this.lyHospital.setOnClickListener(this.mOnclick);
    }
}
