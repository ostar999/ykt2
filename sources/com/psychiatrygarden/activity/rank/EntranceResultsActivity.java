package com.psychiatrygarden.activity.rank;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.plv.livescenes.log.upload.PLVDocumentUploadELog;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.RegisterBean.MajorBean;
import com.psychiatrygarden.activity.RegisterBean.RegisterDataBean;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectOneActivity;
import com.psychiatrygarden.activity.RegisterCommon.RegisterSelectTwoActivity;
import com.psychiatrygarden.activity.rank.pop.ComSinglePopWindow;
import com.psychiatrygarden.activity.vip.pop.TypePopwindow;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.EntranceNoPopWindow;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class EntranceResultsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView addimg;
    TextView catview;
    private ImageView close;
    EditText edwy;
    EditText edzye;
    EditText edzyy;
    EditText edzz;
    TextView kytxt;
    RelativeLayout kyyxview;
    RelativeLayout kyzyview;
    private RelativeLayout mLyDept;
    private RelativeLayout mLyType;
    private TextView mTvDept;
    private TextView mTvType;
    TextView nktxt;
    private String scooltxt;
    private LinearLayout scoreView;
    TextView stxt;
    private RelativeLayout trlview;
    TextView txds;
    private RelativeLayout zhuanyeyi;
    private TextView zyy;
    private List<MajorBean.DataBean> dataMajor2 = new ArrayList();
    private List<RegisterDataBean.DataBean> data = new ArrayList();
    private String REGISTER_EXAMS_UNIVERSITY_ID = "";
    private String REGISTER_EXAMS_MAJOR_ID = "";
    private String target_student_type = "";
    private String target_student_type_str = "";
    private String REGISTER_EXAMS_DEPT_ID = "";
    private String REGISTER_EXAMS_MAJOR_DIR_ID = "";
    private String results_photo = "";
    private final TextWatcher mTextWatcher = new TextWatcher() { // from class: com.psychiatrygarden.activity.rank.EntranceResultsActivity.6
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s2) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s2, int start, int before, int count) {
            boolean z2 = SkinManager.getCurrentSkinType(EntranceResultsActivity.this) == 1;
            if (EntranceResultsActivity.this.edzz.getText().toString().trim().equals("") || EntranceResultsActivity.this.edwy.getText().toString().trim().equals("") || EntranceResultsActivity.this.edzyy.getText().toString().trim().equals("")) {
                EntranceResultsActivity.this.catview.setBackgroundResource(R.color.linetype1);
                EntranceResultsActivity entranceResultsActivity = EntranceResultsActivity.this;
                entranceResultsActivity.catview.setTextColor(entranceResultsActivity.mContext.getResources().getColor(z2 ? R.color.first_txt_color_night : R.color.first_txt_color));
            } else {
                EntranceResultsActivity.this.catview.setBackgroundResource(z2 ? R.color.main_theme_color_night : R.color.main_theme_color);
                EntranceResultsActivity entranceResultsActivity2 = EntranceResultsActivity.this;
                entranceResultsActivity2.catview.setTextColor(entranceResultsActivity2.mContext.getResources().getColor(R.color.white));
                EntranceResultsActivity entranceResultsActivity3 = EntranceResultsActivity.this;
                entranceResultsActivity3.catview.setTextColor(entranceResultsActivity3.mContext.getResources().getColor(z2 ? R.color.new_bg_one_color_night : R.color.new_bg_one_color));
            }
        }
    };

    private void getGradeFromData() {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        if (Constants.VIA_REPORT_TYPE_SET_AVATAR.equals(getIntent().getExtras().getString("moudle"))) {
            if ("haveimg".equals(getIntent().getExtras().getString("type"))) {
                ajaxParams.put("version", "2");
            } else {
                ajaxParams.put("version", "1");
            }
            str = NetworkRequestsURL.gradeformRankApi;
        } else {
            ajaxParams.put("version", "2");
            str = NetworkRequestsURL.gradeformApi;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.EntranceResultsActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        EntranceResultsActivity.this.REGISTER_EXAMS_UNIVERSITY_ID = jSONObject.optJSONObject("data").optString("target_school_id");
                        EntranceResultsActivity.this.REGISTER_EXAMS_MAJOR_ID = jSONObject.optJSONObject("data").optString("target_major_id");
                        EntranceResultsActivity.this.REGISTER_EXAMS_MAJOR_DIR_ID = jSONObject.optJSONObject("data").optString("target_major_direction_id");
                        EntranceResultsActivity.this.REGISTER_EXAMS_DEPT_ID = jSONObject.optJSONObject("data").optString("target_school_department_id");
                        EntranceResultsActivity.this.target_student_type_str = jSONObject.optJSONObject("data").optString("zhuan_xue");
                        if ("zhuan".equals(EntranceResultsActivity.this.target_student_type_str)) {
                            EntranceResultsActivity.this.target_student_type = "专硕";
                        } else {
                            EntranceResultsActivity.this.target_student_type = "学硕";
                        }
                        EntranceResultsActivity.this.stxt.setText(String.format("%s", jSONObject.optJSONObject("data").optString("target_school_name")));
                        EntranceResultsActivity entranceResultsActivity = EntranceResultsActivity.this;
                        entranceResultsActivity.kytxt.setText(String.format("%s,%s", entranceResultsActivity.target_student_type, jSONObject.optJSONObject("data").optString("target_major_name")));
                        EntranceResultsActivity.this.results_photo = jSONObject.optJSONObject("data").optString("photo");
                        GlideApp.with(EntranceResultsActivity.this.mContext).load((Object) GlideUtils.generateUrl(EntranceResultsActivity.this.results_photo)).into(EntranceResultsActivity.this.addimg);
                        if ("2".equals(EntranceResultsActivity.this.getIntent().getExtras().getString("status"))) {
                            EntranceResultsActivity.this.close.setVisibility(0);
                        } else {
                            EntranceResultsActivity.this.close.setVisibility(4);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        YJYHttpUtils.postImage(this, NetworkRequestsURL.stockApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.EntranceResultsActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(EntranceResultsActivity.this.mContext, "上传失败！", 0).show();
                EntranceResultsActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                EntranceResultsActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    try {
                        if (new JSONObject(s2).optString("code").equals("200")) {
                            EntranceResultsActivity.this.results_photo = new JSONObject(s2).optJSONObject("data").optString("url");
                            GlideApp.with(EntranceResultsActivity.this.mContext).load((Object) GlideUtils.generateUrl(EntranceResultsActivity.this.results_photo)).into(EntranceResultsActivity.this.addimg);
                            EntranceResultsActivity.this.close.setVisibility(0);
                        }
                        NewToast.showShort(EntranceResultsActivity.this.mContext, "上传成功", 0).show();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } finally {
                    EntranceResultsActivity.this.hideProgressDialog();
                }
            }
        });
    }

    private void getMajorData2() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "2");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.majorV2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.EntranceResultsActivity.4
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
                super.onSuccess((AnonymousClass4) majorBean);
                MajorBean majorBean2 = (MajorBean) new Gson().fromJson(majorBean, MajorBean.class);
                if (majorBean2.getCode().equals("200")) {
                    EntranceResultsActivity.this.dataMajor2 = majorBean2.getData();
                }
            }
        });
    }

    private void getRegisterData1() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getProvinceOrSchool, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.EntranceResultsActivity.3
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
                super.onSuccess((AnonymousClass3) registerDataBean);
                RegisterDataBean registerDataBean2 = (RegisterDataBean) new Gson().fromJson(registerDataBean, RegisterDataBean.class);
                if (registerDataBean2.getCode().equals("200")) {
                    EntranceResultsActivity.this.data = registerDataBean2.getData();
                }
            }
        });
    }

    private void getSelectData() {
        new XPopup.Builder(this).asCustom(new TypePopwindow(this.mContext, new TypePopwindow.TypeChooseClickIml() { // from class: com.psychiatrygarden.activity.rank.f
            @Override // com.psychiatrygarden.activity.vip.pop.TypePopwindow.TypeChooseClickIml
            public final void chooseType(String str) {
                this.f13769a.lambda$getSelectData$3(str);
            }
        })).show();
    }

    private void getShare2Data() {
        if ("OTHER".equals(getIntent().getExtras().getString("status")) || "MOCK".equals(getIntent().getExtras().getString("status"))) {
            return;
        }
        if ("2".equals(getIntent().getExtras().getString("status"))) {
            new XPopup.Builder(this).asCustom(new EntranceNoPopWindow(this)).toggle();
        } else if ("new".equals(getIntent().getExtras().getString("type"))) {
            this.catview.setText("请等待审核...");
            this.catview.setBackgroundResource(R.color.linetype1);
            this.catview.setTextColor(this.mContext.getResources().getColor(R.color.gray_font_new));
        }
        getGradeFromData();
    }

    private void initView() {
        this.addimg = (ImageView) findViewById(R.id.addimg);
        this.trlview = (RelativeLayout) findViewById(R.id.trlview);
        this.scoreView = (LinearLayout) findViewById(R.id.scoreView);
        this.close = (ImageView) findViewById(R.id.close);
        this.txds = (TextView) findViewById(R.id.txds);
        this.zhuanyeyi = (RelativeLayout) findViewById(R.id.zhuanyeyi);
        this.zyy = (TextView) findViewById(R.id.zyy);
        this.nktxt = (TextView) findViewById(R.id.nktxt);
        this.stxt = (TextView) findViewById(R.id.stxt);
        this.kyyxview = (RelativeLayout) findViewById(R.id.kyyxview);
        this.kytxt = (TextView) findViewById(R.id.kytxt);
        this.kyzyview = (RelativeLayout) findViewById(R.id.kyzyview);
        this.catview = (TextView) findViewById(R.id.catview);
        this.edzz = (EditText) findViewById(R.id.edzz);
        this.edwy = (EditText) findViewById(R.id.edwy);
        this.edzyy = (EditText) findViewById(R.id.edzyy);
        this.edzye = (EditText) findViewById(R.id.edzye);
        this.mLyType = (RelativeLayout) findViewById(R.id.ly_type);
        this.mTvType = (TextView) findViewById(R.id.tv_type);
        this.mLyDept = (RelativeLayout) findViewById(R.id.ly_dept);
        this.mTvDept = (TextView) findViewById(R.id.tv_dept);
        this.catview.setOnClickListener(this);
        this.kyyxview.setOnClickListener(this);
        this.kyzyview.setOnClickListener(this);
        this.mLyType.setOnClickListener(this);
        this.mLyDept.setOnClickListener(this);
        this.addimg.setOnClickListener(this);
        this.close.setOnClickListener(this);
        this.zhuanyeyi = (RelativeLayout) findViewById(R.id.zhuanyeyi);
        this.zyy = (TextView) findViewById(R.id.zyy);
        this.nktxt.setText(String.format("%s", UserConfig.getInstance().getUser().getNickname()));
        this.edzz.addTextChangedListener(this.mTextWatcher);
        this.edwy.addTextChangedListener(this.mTextWatcher);
        this.edzyy.addTextChangedListener(this.mTextWatcher);
        initData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSelectData$3(String str) {
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
            this.zhuanyeyi.setVisibility(8);
            this.zyy.setText("临床医学综合能力");
        } else {
            this.target_student_type_str = "xue";
            this.zhuanyeyi.setVisibility(0);
            this.zyy.setText("专业一");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$0() {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.rankTrue + getIntent().getExtras().getString("moudle"), true, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1() {
        ActivityCompat.requestPermissions((Activity) this.mContext, Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.CAMERA, Permission.READ_MEDIA_IMAGES, Permission.READ_EXTERNAL_STORAGE} : new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        String str = ((ImageItem) list.get(0)).path;
        String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        if (mimeTypeFromFile.equalsIgnoreCase("IMAGE/WEBP")) {
            AlertToast("不支持此文件格式，请选择其它图片上传！");
        } else if (ImageFactory.getImageSize(str) > 10.0f) {
            NewToast.showShort(this.mContext, "请选择小于10M的图片上传", 0).show();
        } else {
            getImageData(str);
        }
    }

    public void getInfoData() {
        getRegisterData1();
        getMajorData2();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        initView();
        getShare2Data();
    }

    public void initData() {
        if (Constants.VIA_REPORT_TYPE_SET_AVATAR.equals(getIntent().getExtras().getString("moudle"))) {
            setTitle("我的考研成绩");
            this.txds.setText("上传成绩单");
            this.scoreView.setVisibility(0);
            this.trlview.setVisibility(0);
            if ("haveimg".equals(getIntent().getExtras().getString("type")) || "new".equals(getIntent().getExtras().getString("type"))) {
                this.catview.setText("提交");
            } else {
                this.catview.setText("查看排名");
            }
            if ("haveimg".equals(getIntent().getExtras().getString("type"))) {
                this.trlview.setVisibility(0);
            } else if ("mock".equals(getIntent().getExtras().getString("type"))) {
                setTitle("考研院校");
                this.trlview.setVisibility(8);
                this.scoreView.setVisibility(8);
                this.catview.setText("确定");
                this.catview.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                this.catview.setBackgroundResource(R.drawable.shape_all_main_color_0);
            } else {
                this.trlview.setVisibility(8);
            }
        } else {
            setTitle("考研志愿");
            this.txds.setText("上传志愿截图");
            this.scoreView.setVisibility(8);
            if ("new".equals(getIntent().getExtras().getString("type"))) {
                this.trlview.setVisibility(0);
                this.catview.setText("提交审核");
            } else {
                this.trlview.setVisibility(8);
                this.catview.setText("查看排名");
            }
        }
        new XPopup.Builder(this).asCustom(new ComSinglePopWindow(this, new ComSinglePopWindow.DialogListener() { // from class: com.psychiatrygarden.activity.rank.g
            @Override // com.psychiatrygarden.activity.rank.pop.ComSinglePopWindow.DialogListener
            public final void mDialogListener() {
                this.f13770a.lambda$initData$0();
            }
        }, "每人仅有一次提交数据的机会，请认真填写")).show();
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
            this.zhuanyeyi.setVisibility(8);
            this.zyy.setText("临床医学综合能力");
        } else {
            this.target_student_type_str = "xue";
            this.zhuanyeyi.setVisibility(0);
            this.zyy.setText("专业一");
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
            putData();
            return;
        }
        if (id == R.id.kyyxview) {
            if ("new".equals(getIntent().getExtras().getString("type")) && "0".equals(getIntent().getExtras().getString("status"))) {
                return;
            }
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
        if (id != R.id.kyzyview) {
            if (id != R.id.addimg) {
                if (id == R.id.close) {
                    this.results_photo = "";
                    this.close.setVisibility(4);
                    this.addimg.setImageResource(R.drawable.rpmscimg);
                    return;
                }
                return;
            }
            if ("new".equals(getIntent().getExtras().getString("type")) && "0".equals(getIntent().getExtras().getString("status"))) {
                return;
            }
            if (!"".equals(this.results_photo)) {
                ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(this.mContext).setSingleSrcView(this.addimg, this.results_photo).setXPopupImageLoader(new ImageLoaderUtilsCustom());
                xPopupImageLoader.popupInfo = new PopupInfo();
                xPopupImageLoader.show();
                return;
            } else if (!CommonUtil.hasRequiredPermissions(this.mContext)) {
                new XPopup.Builder(this.mContext).asCustom(new RequestMediaPermissionPop(this.mContext, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.rank.d
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f13767a.lambda$onClick$1();
                    }
                })).show();
                return;
            } else {
                AndroidImagePicker.getInstance().setSelectLimit(1);
                AndroidImagePicker.getInstance().pickMulti(this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.rank.e
                    @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                    public final void onImagePickComplete(List list2) {
                        this.f13768a.lambda$onClick$2(list2);
                    }
                });
                return;
            }
        }
        if ("new".equals(getIntent().getExtras().getString("type")) && "0".equals(getIntent().getExtras().getString("status"))) {
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
        String str;
        if ("new".equals(getIntent().getExtras().getString("type")) && "0".equals(getIntent().getExtras().getString("status"))) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_school_id", "" + this.REGISTER_EXAMS_UNIVERSITY_ID);
        ajaxParams.put("target_major_id", "" + this.REGISTER_EXAMS_MAJOR_ID);
        ajaxParams.put("target_major_direction_id", "" + this.REGISTER_EXAMS_MAJOR_DIR_ID);
        ajaxParams.put("zhuan_xue", "" + this.target_student_type_str);
        ajaxParams.put("target_school_department_id", "" + this.REGISTER_EXAMS_DEPT_ID);
        if (Constants.VIA_REPORT_TYPE_SET_AVATAR.equals(getIntent().getExtras().getString("moudle"))) {
            ajaxParams.put("politics", "" + this.edzz.getText().toString());
            ajaxParams.put("english", "" + this.edwy.getText().toString());
            ajaxParams.put("major_1", "" + this.edzyy.getText().toString());
            ajaxParams.put("major_2", "" + this.edzye.getText().toString());
            str = NetworkRequestsURL.gradeApi;
            if ("haveimg".equals(getIntent().getExtras().getString("type"))) {
                if ("".equals(this.results_photo)) {
                    ToastUtil.shortToast(this, "请上传成绩单截图！");
                    return;
                } else {
                    ajaxParams.put("results_photo", this.results_photo);
                    ajaxParams.put("version", "2");
                }
            } else if ("mock".equals(getIntent().getExtras().getString("type"))) {
                ajaxParams.put("exam_id", getIntent().getExtras().getString("examId"));
                ajaxParams.remove("politics");
                ajaxParams.remove("english");
                ajaxParams.remove("major_1");
                ajaxParams.remove("major_2");
                str = NetworkRequestsURL.bindUserSchool;
            } else {
                ajaxParams.put("version", "1");
            }
        } else {
            if (!"new".equals(getIntent().getExtras().getString("type"))) {
                ajaxParams.put("version", "1");
            } else if ("".equals(this.results_photo)) {
                ToastUtil.shortToast(this, "请上传志愿截图！");
                return;
            } else {
                ajaxParams.put("photo", this.results_photo);
                ajaxParams.put("version", "2");
            }
            str = NetworkRequestsURL.gradeApi2;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.rank.EntranceResultsActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                EntranceResultsActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                EntranceResultsActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    if (!new JSONObject(s2).optString("code").equals("200")) {
                        ToastUtil.shortToast(EntranceResultsActivity.this.mContext, new JSONObject(s2).optString("message"));
                    } else {
                        if ("new".equals(EntranceResultsActivity.this.getIntent().getExtras().getString("type"))) {
                            EntranceResultsActivity.this.finish();
                            return;
                        }
                        if ("mock".equals(EntranceResultsActivity.this.getIntent().getExtras().getString("type"))) {
                            EventBus.getDefault().post("bindSuccess");
                            EntranceResultsActivity.this.finish();
                        } else if ("haveimg".equals(EntranceResultsActivity.this.getIntent().getExtras().getString("type")) || "new".equals(EntranceResultsActivity.this.getIntent().getExtras().getString("type"))) {
                            EventBus.getDefault().post(PLVDocumentUploadELog.DocumentUploadEvent.UPLOAD_SUCCESS);
                            EntranceResultsActivity.this.finish();
                        } else {
                            EventBus.getDefault().post(PLVDocumentUploadELog.DocumentUploadEvent.UPLOAD_SUCCESS);
                            UserConfig.getInstance().getUser().setTarget_id(EntranceResultsActivity.this.REGISTER_EXAMS_UNIVERSITY_ID);
                            UserConfig.getInstance().getUser().setTarget_name(EntranceResultsActivity.this.stxt.getText().toString());
                            UserConfig.getInstance().getUser().setTarget_major_id(EntranceResultsActivity.this.REGISTER_EXAMS_MAJOR_ID);
                            UserConfig.getInstance().getUser().setTarget_major_name(EntranceResultsActivity.this.scooltxt);
                            UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                            Intent intent = new Intent(EntranceResultsActivity.this, (Class<?>) RankEntranceActivity.class);
                            intent.putExtra("moudle", "" + EntranceResultsActivity.this.getIntent().getExtras().getString("moudle"));
                            intent.putExtra("type", "" + EntranceResultsActivity.this.getIntent().getExtras().getString("type"));
                            EntranceResultsActivity.this.startActivity(intent);
                            EntranceResultsActivity.this.finish();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                EntranceResultsActivity.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_entrance_results);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
