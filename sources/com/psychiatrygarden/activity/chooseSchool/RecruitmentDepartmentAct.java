package com.psychiatrygarden.activity.chooseSchool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.SchoolDepartmentBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class RecruitmentDepartmentAct extends BaseActivity {
    private String activityId;
    private RecruitmentDepartmentAdp mAdapter;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private LinearLayout mLyBottom;
    private View mLyOpenView;
    private LinearLayout mLyTag;
    private RecyclerView mRecycler;
    private CircleImageView mSchoolHead;
    private String mSchoolId;
    private NestedScrollView mScrollView;
    private TextView mTvSchoolAddress;
    private TextView mTvSchoolCode;
    private TextView mTvSchoolName;

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getDepartmentList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.RecruitmentDepartmentAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    SchoolDepartmentBean schoolDepartmentBean = (SchoolDepartmentBean) new Gson().fromJson(s2, SchoolDepartmentBean.class);
                    if (!schoolDepartmentBean.getCode().equals("200") || schoolDepartmentBean.getData() == null) {
                        return;
                    }
                    GlideApp.with(RecruitmentDepartmentAct.this.mContext).load((Object) GlideUtils.generateUrl(schoolDepartmentBean.getData().getCover())).placeholder(R.mipmap.ic_avatar_def).into(RecruitmentDepartmentAct.this.mSchoolHead);
                    RecruitmentDepartmentAct.this.mTvSchoolName.setText(schoolDepartmentBean.getData().getTitle());
                    RecruitmentDepartmentAct.this.mLyTag.removeAllViews();
                    int pxByDp = ScreenUtil.getPxByDp((Context) RecruitmentDepartmentAct.this, 4);
                    int pxByDp2 = ScreenUtil.getPxByDp((Context) RecruitmentDepartmentAct.this, 1);
                    if (schoolDepartmentBean.getData().getAttr() == null || schoolDepartmentBean.getData().getAttr().size() <= 0) {
                        RecruitmentDepartmentAct.this.mLyTag.setVisibility(8);
                    } else {
                        RecruitmentDepartmentAct.this.mLyTag.setVisibility(0);
                        for (int i2 = 0; i2 < schoolDepartmentBean.getData().getAttr().size(); i2++) {
                            TextView textView = new TextView(RecruitmentDepartmentAct.this);
                            textView.setTextSize(10.0f);
                            textView.setText(schoolDepartmentBean.getData().getAttr().get(i2));
                            textView.setTextColor(SkinManager.getCurrentSkinType(RecruitmentDepartmentAct.this) == 1 ? RecruitmentDepartmentAct.this.getColor(R.color.main_theme_color_night) : RecruitmentDepartmentAct.this.getColor(R.color.main_theme_color));
                            textView.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
                            textView.setBackgroundResource(R.drawable.shape_computer_time_bg);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                            layoutParams.rightMargin = ScreenUtil.getPxByDp((Context) RecruitmentDepartmentAct.this, 8);
                            textView.setLayoutParams(layoutParams);
                            RecruitmentDepartmentAct.this.mLyTag.addView(textView);
                        }
                    }
                    if (TextUtils.isEmpty(schoolDepartmentBean.getData().getLocation())) {
                        RecruitmentDepartmentAct.this.mTvSchoolAddress.setVisibility(8);
                    } else {
                        RecruitmentDepartmentAct.this.mTvSchoolAddress.setVisibility(0);
                        RecruitmentDepartmentAct.this.mTvSchoolAddress.setText("地址：" + schoolDepartmentBean.getData().getLocation());
                    }
                    if (TextUtils.isEmpty(schoolDepartmentBean.getData().getCode())) {
                        RecruitmentDepartmentAct.this.mTvSchoolCode.setVisibility(8);
                    } else {
                        RecruitmentDepartmentAct.this.mTvSchoolCode.setVisibility(0);
                        RecruitmentDepartmentAct.this.mTvSchoolCode.setText("代码：" + schoolDepartmentBean.getData().getCode());
                    }
                    RecruitmentDepartmentAct.this.mAdapter.setNewData(schoolDepartmentBean.getData().getDepartment());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, View view, NestedScrollView nestedScrollView, int i3, int i4, int i5, int i6) {
        if (i4 > i2) {
            if (this.mImgBg.getVisibility() == 0) {
                this.mImgBg.setVisibility(8);
                view.setBackgroundColor(getColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.color.new_bg_one_color_night : R.color.new_bg_one_color));
                return;
            }
            return;
        }
        if (this.mImgBg.getVisibility() == 8) {
            view.setBackgroundColor(0);
            this.mImgBg.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        SchoolDepartmentBean.DepartmentData item = this.mAdapter.getItem(i2);
        MajorByDepartmentAct.newIntent(this, this.mSchoolId, item.getId(), item.getTitle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openVip$5() {
        this.mLyOpenView.setVisibility(8);
        this.mLyBottom.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        openVip();
    }

    public static void newIntent(Context context, String schoolId) {
        Intent intent = new Intent(context, (Class<?>) RecruitmentDepartmentAct.class);
        intent.putExtra("schoolId", schoolId);
        context.startActivity(intent);
    }

    private void openVip() {
        if (TextUtils.isEmpty(this.activityId)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, this.activityId);
        MemInterface.getInstance().getMemData((Activity) this, ajaxParams, NetworkRequestsURL.vipApi, 0, false);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.k3
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f11337a.lambda$openVip$5();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mSchoolId = getIntent().getStringExtra("schoolId");
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        final View viewFindViewById = findViewById(R.id.tabbar);
        this.mSchoolHead = (CircleImageView) findViewById(R.id.school_head);
        this.mTvSchoolName = (TextView) findViewById(R.id.tv_school_name);
        this.mLyTag = (LinearLayout) findViewById(R.id.ly_tag);
        this.mTvSchoolAddress = (TextView) findViewById(R.id.tv_school_address);
        this.mTvSchoolCode = (TextView) findViewById(R.id.tv_school_code);
        this.mRecycler = (RecyclerView) findViewById(R.id.recycler);
        this.mLyBottom = (LinearLayout) findViewById(R.id.ly_bottom);
        this.mLyOpenView = findViewById(R.id.ly_open_vip);
        this.mScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        textView.setText("招录院系");
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        viewFindViewById.setLayoutParams(layoutParams);
        RecruitmentDepartmentAdp recruitmentDepartmentAdp = new RecruitmentDepartmentAdp();
        this.mAdapter = recruitmentDepartmentAdp;
        this.mRecycler.setAdapter(recruitmentDepartmentAdp);
        final int pxByDp = ScreenUtil.getPxByDp(this.mContext, 44) + statusBarHeight;
        this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.i3
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
                this.f11318a.lambda$init$0(pxByDp, viewFindViewById, nestedScrollView, i2, i3, i4, i5);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j3
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11328a.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
        getData();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, this);
        if (!TextUtils.isEmpty(strConfig) && strConfig.equals("1")) {
            this.mLyBottom.setVisibility(8);
            this.mLyOpenView.setVisibility(8);
        } else {
            this.mLyBottom.setVisibility(0);
            this.mLyOpenView.setVisibility(0);
            this.activityId = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_ACTIVITY_ID, this);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_recruitment_department);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11345c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mLyBottom.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11353c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyOpenView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.n3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11364c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
