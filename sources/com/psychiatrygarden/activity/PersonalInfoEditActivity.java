package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTimeActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.UserRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CameraUtil;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.KeyboardInputUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.IdentityAuthPopWindow;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PersonalInfoEditActivity extends BaseActivity implements QuestionDataCallBack<String> {
    private String REGISTER_SEX_STR;
    private String eduType;
    private String mAvatarUrlNew;
    private ImageView mIvPhoto;
    private LinearLayout mLineXuexiao;
    private LinearLayout mLineZhuanye;
    private LinearLayout mLlEmail;
    private TextView mTvEmail;
    private TextView mTvExamsMajor;
    private TextView mTvExamsTime;
    private TextView mTvExamsUniversity;
    private TextView mTvExamsWorkSection;
    private TextView mTvExamsWorkTime;
    private TextView mTvNickname;
    private TextView mTvSex;
    private TextView mTvTiku;
    private TextView mTvTitleHospital;
    private TextView mTvTitleJob;
    private TextView mTvTitleWorkSection1;
    private TextView mTvUniversity;
    private TextView mTvUniversityMajor;
    private TextView mTvWorkUnit;
    String sexStr;
    private TextView tvHospital;
    private TextView tvJob;
    private TextView tv_edu_str2;
    private int mType = 1;
    private String myPhotoPath = "";
    View.OnClickListener mOnclick = new AnonymousClass1();
    private final List<String> sexdata = new ArrayList();
    private boolean isAuthPassed = false;
    private String complete_user_info = "0";

    /* renamed from: com.psychiatrygarden.activity.PersonalInfoEditActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0() {
            ActivityCompat.requestPermissions((Activity) PersonalInfoEditActivity.this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$1(Bitmap bitmap, float f2) {
            PersonalInfoEditActivity.this.mIvPhoto.setVisibility(0);
            PersonalInfoEditActivity.this.mIvPhoto.setImageBitmap(bitmap);
            PersonalInfoEditActivity.this.myPhotoPath = CameraUtil.saveHeadimage2(bitmap, System.currentTimeMillis() + ".jpg").getAbsolutePath();
            if (PersonalInfoEditActivity.this.myPhotoPath != null) {
                PersonalInfoEditActivity.this.uploadAvatar(new File(PersonalInfoEditActivity.this.myPhotoPath));
            }
        }

        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
            }
            switch (v2.getId()) {
                case R.id.btn_actionbar_right /* 2131362309 */:
                    PersonalInfoEditActivity.this.saveOrmodify();
                    break;
                case R.id.ll_email /* 2131364786 */:
                    if (!TextUtils.isEmpty(UserConfig.getInstance().getUser().getEmail())) {
                        PersonalInfoEditActivity.this.startActivity(new Intent(PersonalInfoEditActivity.this, (Class<?>) ModifyMailboxActivity.class));
                        break;
                    } else {
                        Intent intent = new Intent(PersonalInfoEditActivity.this, (Class<?>) AccountLogoutConfirmActivity.class);
                        intent.putExtra("gotoType", 3);
                        PersonalInfoEditActivity.this.startActivity(intent);
                        break;
                    }
                case R.id.llay_exams_time /* 2131364953 */:
                case R.id.llay_exams_university /* 2131364954 */:
                case R.id.llay_university /* 2131364979 */:
                case R.id.llay_university_major /* 2131364980 */:
                case R.id.ly_hospital /* 2131365150 */:
                case R.id.ly_job /* 2131365167 */:
                case R.id.rl_exams_work_section /* 2131366548 */:
                case R.id.rl_exams_work_time /* 2131366549 */:
                case R.id.rl_select_tiku /* 2131366616 */:
                case R.id.rl_work_unit /* 2131366639 */:
                    if (PersonalInfoEditActivity.this.mType == 2) {
                        if (!PersonalInfoEditActivity.this.isAuthPassed) {
                            PersonalInfoEditActivity.this.startActivity(new Intent(PersonalInfoEditActivity.this, (Class<?>) CircleSchoolVerifyActivity.class));
                            break;
                        } else {
                            PersonalInfoEditActivity.this.goToPath();
                            break;
                        }
                    }
                    break;
                case R.id.llay_nickname /* 2131364961 */:
                    Intent intent2 = new Intent(PersonalInfoEditActivity.this, (Class<?>) NicknameUpdateActivity.class);
                    intent2.putExtra("nickname", PersonalInfoEditActivity.this.mTvNickname.getText().toString());
                    PersonalInfoEditActivity.this.startActivityForResult(intent2, 17);
                    break;
                case R.id.llay_photo /* 2131364963 */:
                    if (!CommonUtil.hasRequiredPermissions(PersonalInfoEditActivity.this.mContext)) {
                        new XPopup.Builder(PersonalInfoEditActivity.this.mContext).asCustom(new RequestMediaPermissionPop(PersonalInfoEditActivity.this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.ye
                            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                            public final void onConfirm() {
                                this.f14215a.lambda$onClick$0();
                            }
                        })).show();
                        break;
                    } else {
                        try {
                            if (PersonalInfoEditActivity.this.mType == 2) {
                                AndroidImagePicker.getInstance().pickAndCrop(PersonalInfoEditActivity.this, true, 1000, new AndroidImagePicker.OnImageCropCompleteListener() { // from class: com.psychiatrygarden.activity.ze
                                    @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImageCropCompleteListener
                                    public final void onImageCropComplete(Bitmap bitmap, float f2) {
                                        this.f14247c.lambda$onClick$1(bitmap, f2);
                                    }
                                });
                                break;
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                    break;
                case R.id.llay_sex /* 2131364972 */:
                    if (PersonalInfoEditActivity.this.mType == 2) {
                        if (PersonalInfoEditActivity.this.sexdata.size() > 0) {
                            PersonalInfoEditActivity.this.sexdata.clear();
                        }
                        PersonalInfoEditActivity.this.sexdata.add("男");
                        PersonalInfoEditActivity.this.sexdata.add("女");
                        PersonalInfoEditActivity.this.sexdata.add("保密");
                        Intent intent3 = new Intent(PersonalInfoEditActivity.this, (Class<?>) RegisterSelectTimeActivity.class);
                        intent3.putExtra("dataList", (Serializable) PersonalInfoEditActivity.this.sexdata);
                        intent3.putExtra("sextrue", true);
                        intent3.putExtra("title", "性别");
                        intent3.putExtra("name", "" + PersonalInfoEditActivity.this.mTvSex.getText().toString());
                        PersonalInfoEditActivity.this.startActivityForResult(intent3, 6);
                        break;
                    }
                    break;
                case R.id.ly_phone /* 2131365216 */:
                    Intent intent4 = new Intent();
                    intent4.setClass(PersonalInfoEditActivity.this.mContext, IdentityConfirmActivity.class);
                    intent4.putExtra("identityType", 0);
                    PersonalInfoEditActivity.this.startActivity(intent4);
                    break;
                case R.id.rl_modify_pwd /* 2131366582 */:
                    if (PersonalInfoEditActivity.this.mType == 2) {
                        Intent intent5 = new Intent();
                        intent5.setClass(PersonalInfoEditActivity.this.mContext, ForgetPwdPhoneActivity.class);
                        intent5.putExtra("type", 2);
                        PersonalInfoEditActivity.this.startActivity(intent5);
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialog$0(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dialog$1(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        ProjectApp.instance().exit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goToPath$4() {
        startActivity(new Intent(this, (Class<?>) CircleSchoolVerifyActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        if (this.mType == 2) {
            if (this.isAuthPassed) {
                goToPath();
            } else {
                startActivity(new Intent(this, (Class<?>) CircleSchoolVerifyActivity.class));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveOrmodify() {
        if ("student".equals(this.eduType)) {
            if (TextUtils.isEmpty(this.mTvUniversity.getText().toString()) || TextUtils.isEmpty(this.mTvExamsTime.getText().toString()) || TextUtils.isEmpty(this.mTvUniversityMajor.getText().toString())) {
                AlertToast("请补全个人资料");
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(this.mTvWorkUnit.getText().toString()) || TextUtils.isEmpty(this.mTvExamsWorkSection.getText().toString()) || TextUtils.isEmpty(this.mTvExamsWorkTime.getText().toString())) {
            AlertToast("请补全个人资料");
        }
    }

    public void chageStatus(String avaUrl, String sexstr) {
        UserRequest.getIntance(this).changeUserInfo("", avaUrl, sexstr, this);
    }

    public void changeFirstView() {
        this.mType = 2;
        this.mBtnActionbarRight.setText("保存");
        findViewById(R.id.iv_arrow1).setVisibility(0);
        findViewById(R.id.iv_arrow2).setVisibility(0);
        findViewById(R.id.iv_arrow3).setVisibility(0);
        findViewById(R.id.iv_arrow4).setVisibility(0);
        findViewById(R.id.iv_arrow5).setVisibility(0);
        findViewById(R.id.iv_arrow6).setVisibility(0);
        findViewById(R.id.iv_arrow7).setVisibility(0);
        findViewById(R.id.iv_arrow8).setVisibility(0);
        findViewById(R.id.iv_arrow9).setVisibility(0);
        findViewById(R.id.iv_arrow11).setVisibility(0);
        findViewById(R.id.iv_arrow12).setVisibility(0);
        findViewById(R.id.iv_arrow13).setVisibility(0);
        findViewById(R.id.iv_arrowedu).setVisibility(0);
        findViewById(R.id.iv_arrow_hospital).setVisibility(0);
        findViewById(R.id.iv_job).setVisibility(0);
    }

    public void dialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        customDialog.setMessage(this.mContext.getString(R.string.app_close));
        customDialog.setNegativeBtn(R.string.huayucancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.te
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PersonalInfoEditActivity.lambda$dialog$0(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.huayuok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ue
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PersonalInfoEditActivity.lambda$dialog$1(customDialog, view);
            }
        });
        customDialog.show();
    }

    public void getUserInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_user_id", UserConfig.getUserId());
        YJYHttpUtils.get(this, NetworkRequestsURL.getUserInfoApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.PersonalInfoEditActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if (!"200".equals(jSONObject.optString("code")) || jSONObjectOptJSONObject == null) {
                        return;
                    }
                    PersonalInfoEditActivity.this.tv_edu_str2.setText(String.format("%s", jSONObjectOptJSONObject.optString("education_name")));
                    PersonalInfoEditActivity.this.eduType = jSONObjectOptJSONObject.optString("user_type");
                    if ("student".equals(PersonalInfoEditActivity.this.eduType) || "nurse".equals(PersonalInfoEditActivity.this.eduType)) {
                        PersonalInfoEditActivity.this.mTvTiku.setText("在校学生");
                    } else if ("work".equals(PersonalInfoEditActivity.this.eduType) || "work_other".equals(PersonalInfoEditActivity.this.eduType)) {
                        PersonalInfoEditActivity.this.mTvTiku.setText("医疗行业从业者");
                    } else {
                        PersonalInfoEditActivity.this.mTvTiku.setText("");
                    }
                    PersonalInfoEditActivity.this.mAvatarUrlNew = jSONObjectOptJSONObject.optString("avatar");
                    PersonalInfoEditActivity.this.initView(PersonalInfoEditActivity.this.eduType + "");
                    PersonalInfoEditActivity.this.REGISTER_SEX_STR = jSONObjectOptJSONObject.optString(CommonNetImpl.SEX);
                    GlideUtils.loadImage(PersonalInfoEditActivity.this.mContext, jSONObjectOptJSONObject.optString("avatar"), PersonalInfoEditActivity.this.mIvPhoto);
                    PersonalInfoEditActivity.this.mTvNickname.setText(jSONObjectOptJSONObject.optString("nickname"));
                    PersonalInfoEditActivity.this.mTvSex.setText(jSONObjectOptJSONObject.optString("str_sex"));
                    PersonalInfoEditActivity.this.mTvUniversity.setText(jSONObjectOptJSONObject.optString("now_name"));
                    PersonalInfoEditActivity.this.mTvUniversityMajor.setText(jSONObjectOptJSONObject.optString("now_major_name"));
                    PersonalInfoEditActivity.this.mTvExamsTime.setText(jSONObjectOptJSONObject.optString("entrance_time"));
                    if (PersonalInfoEditActivity.this.eduType.equals("work_other")) {
                        PersonalInfoEditActivity.this.mTvWorkUnit.setText("其他");
                        PersonalInfoEditActivity.this.mTvTitleHospital.setText("单位");
                        PersonalInfoEditActivity.this.mTvTitleWorkSection1.setText("部门");
                    } else if (PersonalInfoEditActivity.this.eduType.equals("work")) {
                        PersonalInfoEditActivity.this.mTvWorkUnit.setText("医院");
                        PersonalInfoEditActivity.this.mTvTitleHospital.setText("医院");
                        PersonalInfoEditActivity.this.mTvTitleWorkSection1.setText("科室");
                    }
                    PersonalInfoEditActivity.this.tvHospital.setText(jSONObjectOptJSONObject.optString("hospital_name"));
                    PersonalInfoEditActivity.this.mTvExamsWorkSection.setText(jSONObjectOptJSONObject.optString("department_name"));
                    PersonalInfoEditActivity.this.mTvExamsWorkTime.setText(jSONObjectOptJSONObject.optString("work_time_name"));
                    PersonalInfoEditActivity.this.complete_user_info = jSONObjectOptJSONObject.optString(CommonParameter.complete_user_info);
                    PersonalInfoEditActivity.this.tvJob.setText(jSONObjectOptJSONObject.optString("position_name"));
                    String strOptString = jSONObjectOptJSONObject.optString("is_authentication");
                    String strOptString2 = jSONObjectOptJSONObject.optString("identity");
                    if (strOptString.equals("1") || strOptString2.equals("1")) {
                        PersonalInfoEditActivity.this.isAuthPassed = true;
                    } else {
                        PersonalInfoEditActivity.this.isAuthPassed = false;
                    }
                    PersonalInfoEditActivity.this.changeFirstView();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void goToPath() {
        new XPopup.Builder(this).asCustom(new IdentityAuthPopWindow(this, new IdentityAuthPopWindow.IdentityAuthClickIml() { // from class: com.psychiatrygarden.activity.ve
            @Override // com.psychiatrygarden.widget.IdentityAuthPopWindow.IdentityAuthClickIml
            public final void mIdentityClick() {
                this.f14027a.lambda$goToPath$4();
            }
        })).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        ((LinearLayout) findViewById(R.id.rel_edu)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.we
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14146c.lambda$init$2(view);
            }
        });
        this.tv_edu_str2 = (TextView) findViewById(R.id.tv_edu_str2);
        this.mIvPhoto = (ImageView) findViewById(R.id.iv_photo);
        this.mTvNickname = (TextView) findViewById(R.id.tv_nickname);
        this.mLlEmail = (LinearLayout) findViewById(R.id.ll_email);
        this.mTvEmail = (TextView) findViewById(R.id.tv_email);
        this.mTvSex = (TextView) findViewById(R.id.tv_sex);
        TextView textView = (TextView) findViewById(R.id.tv_phone);
        String str = SharePreferencesUtils.readStrConfig(CommonParameter.TEL, this) + "";
        try {
            if (str.length() > 7) {
                textView.setText(String.format("%s****%s", str.substring(0, 3), str.substring(7)));
            } else {
                textView.setText(str);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            textView.setText(str);
        }
        String email = UserConfig.getInstance().getUser().getEmail();
        try {
            if (TextUtils.isEmpty(email)) {
                this.mTvEmail.setText("待完善");
            } else {
                String[] strArrSplit = email.split("@");
                this.mTvEmail.setText(String.format("%s*********%s", strArrSplit[0].substring(0, 2), strArrSplit[1]));
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            this.mTvEmail.setText(email);
        }
        this.mTvUniversity = (TextView) findViewById(R.id.tv_university);
        this.mTvUniversityMajor = (TextView) findViewById(R.id.tv_university_major);
        this.mTvExamsUniversity = (TextView) findViewById(R.id.tv_exams_university);
        this.mTvExamsMajor = (TextView) findViewById(R.id.tv_exams_major);
        this.mTvExamsTime = (TextView) findViewById(R.id.tv_exams_time);
        this.mLineZhuanye = (LinearLayout) findViewById(R.id.line_zhuanye);
        this.mLineXuexiao = (LinearLayout) findViewById(R.id.line_xuexiao);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rl_work_unit);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.rl_exams_work_section);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.rl_exams_work_time);
        this.tvHospital = (TextView) findViewById(R.id.tv_hospital);
        this.tvJob = (TextView) findViewById(R.id.tv_job);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.ly_job);
        this.mTvTitleHospital = (TextView) findViewById(R.id.tv_title_hospital);
        this.mTvTitleWorkSection1 = (TextView) findViewById(R.id.tv_exams_work_section1);
        this.mTvTitleJob = (TextView) findViewById(R.id.tv_title_job);
        linearLayout.setOnClickListener(this.mOnclick);
        linearLayout2.setOnClickListener(this.mOnclick);
        linearLayout3.setOnClickListener(this.mOnclick);
        linearLayout4.setOnClickListener(this.mOnclick);
        this.mTvWorkUnit = (TextView) findViewById(R.id.tv_work_unit);
        this.mTvExamsWorkSection = (TextView) findViewById(R.id.tv_exams_work_section);
        this.mTvExamsWorkTime = (TextView) findViewById(R.id.tv_exams_work_time);
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.xe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14181c.lambda$init$3(view);
            }
        });
        this.mTvTiku = (TextView) findViewById(R.id.tv_tiku);
        getUserInfo();
    }

    public void initView(String userType) {
        if (userType.equals("student") || userType.equals("nurse")) {
            this.mLineXuexiao.setVisibility(0);
            this.mLineZhuanye.setVisibility(8);
        } else if ("work".equals(userType)) {
            this.mLineXuexiao.setVisibility(8);
            this.mLineZhuanye.setVisibility(0);
        } else if ("work_other".equals(userType)) {
            this.mLineXuexiao.setVisibility(8);
            this.mLineZhuanye.setVisibility(0);
        } else {
            this.mLineXuexiao.setVisibility(8);
            this.mLineZhuanye.setVisibility(8);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
        }
        if (requestCode == 13) {
            this.eduType = data.getStringExtra("value");
            this.mTvTiku.setText(data.getStringExtra("title"));
            initView(this.eduType);
            return;
        }
        if (requestCode == 14) {
            this.tv_edu_str2.setText(data.getStringExtra("title"));
            return;
        }
        if (requestCode == 17) {
            this.mTvNickname.setText(data.getStringExtra("nickname"));
            return;
        }
        switch (requestCode) {
            case 1:
                this.mTvUniversity.setText(data.getStringExtra("title"));
                break;
            case 2:
                this.mTvExamsUniversity.setText(data.getStringExtra("title"));
                break;
            case 3:
                this.mTvExamsTime.setText(data.getStringExtra("title"));
                break;
            case 4:
                this.mTvUniversityMajor.setText(data.getStringExtra("title"));
                break;
            case 5:
                this.mTvExamsMajor.setText(data.getStringExtra("title"));
                break;
            case 6:
                this.sexStr = data.getStringExtra("title");
                this.mTvSex.setText(data.getStringExtra("title"));
                if (this.sexStr.equals("男")) {
                    this.REGISTER_SEX_STR = "1";
                } else if (this.sexStr.equals("女")) {
                    this.REGISTER_SEX_STR = "2";
                } else {
                    this.REGISTER_SEX_STR = "0";
                }
                chageStatus("", this.REGISTER_SEX_STR);
                break;
            case 7:
                this.mTvWorkUnit.setText(data.getStringExtra("title"));
                break;
            case 8:
                this.mTvExamsWorkSection.setText(data.getStringExtra("title"));
                break;
            case 9:
                this.mTvExamsWorkTime.setText(data.getStringExtra("title"));
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(EventBusConstant.EVENT_EMAIL_CHANGE_SUCCESS)) {
            String email = UserConfig.getInstance().getUser().getEmail();
            try {
                if (TextUtils.isEmpty(email)) {
                    this.mTvEmail.setText("待完善");
                } else {
                    String[] strArrSplit = email.split("@");
                    this.mTvEmail.setText(String.format("%s*********%s", strArrSplit[0].substring(0, 2), strArrSplit[1]));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                this.mTvEmail.setText(email);
            }
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        if ("1".equals(this.complete_user_info)) {
            dialog();
            return true;
        }
        finish();
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults.length > 0 && grantResults[0] == -1) {
            boolean zShouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.CAMERA);
            boolean zShouldShowRequestPermissionRationale2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE);
            if (zShouldShowRequestPermissionRationale || zShouldShowRequestPermissionRationale2) {
                return;
            }
            ToastUtil.shortToast(this, "请检查app相机及存储权限是否打开！");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        KeyboardInputUtil.showKeyBoard(this);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mBtnActionbarRight.setVisibility(8);
        setTitle("个人资料");
        setContentView(R.layout.activity_personal_info_edit);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnActionbarRight.setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_photo).setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_nickname).setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_sex).setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_university).setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_university_major).setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_exams_university).setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_exams_major).setOnClickListener(this.mOnclick);
        findViewById(R.id.llay_exams_time).setOnClickListener(this.mOnclick);
        findViewById(R.id.rl_modify_pwd).setOnClickListener(this.mOnclick);
        findViewById(R.id.rl_select_tiku).setOnClickListener(this.mOnclick);
        findViewById(R.id.ly_phone).setOnClickListener(this.mOnclick);
        findViewById(R.id.ly_hospital).setOnClickListener(this.mOnclick);
        this.mLlEmail.setOnClickListener(this.mOnclick);
    }

    public void uploadAvatar(File headFile) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", headFile);
            YJYHttpUtils.postImage(this.mContext, NetworkRequestsURL.mAvatar2URL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.PersonalInfoEditActivity.2
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    PersonalInfoEditActivity.this.AlertToast(strMsg);
                    PersonalInfoEditActivity.this.hideProgressDialog();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onStart() {
                    super.onStart();
                    PersonalInfoEditActivity.this.showProgressDialog();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String t2) {
                    super.onSuccess((AnonymousClass2) t2);
                    try {
                        JSONObject jSONObject = new JSONObject(t2);
                        if (jSONObject.optString("code").equals("200")) {
                            PersonalInfoEditActivity.this.mAvatarUrlNew = jSONObject.optString("data");
                            PersonalInfoEditActivity personalInfoEditActivity = PersonalInfoEditActivity.this;
                            personalInfoEditActivity.chageStatus(personalInfoEditActivity.mAvatarUrlNew, "");
                            PersonalInfoEditActivity.this.AlertToast("上传成功");
                        } else {
                            PersonalInfoEditActivity.this.AlertToast(jSONObject.optString("message"));
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    PersonalInfoEditActivity.this.hideProgressDialog();
                }
            });
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            hideProgressDialog();
            JSONObject jSONObject = new JSONObject(s2);
            if (jSONObject.optString("code").equals("200")) {
                EventBus.getDefault().post("PersonalCenterFragment");
            } else {
                ToastUtil.shortToast(this, jSONObject.optString("message"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
