package com.psychiatrygarden.activity.chooseSchool;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowMajorsBean;
import com.psychiatrygarden.activity.chooseSchool.bean.SchoolMajorsListbean;
import com.psychiatrygarden.adapter.MajorsListAdapter;
import com.psychiatrygarden.bean.ChooseSchoolFilterBean;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.ChooseFilterPopuWindow;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SchoolOpenMajorAct extends BaseActivity {
    private int actionbar;
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private LinearLayout llEmpty;
    private MajorsListAdapter mAdapter;
    private ImageView mImgBack;
    private LinearLayout mLlEmpty;
    private LinearLayout mLyCategory;
    private LinearLayout mLyDepartment;
    private LinearLayout mLyTag;
    private TextView mNavTitle;
    private RecyclerView mRecycler;
    private CircleImageView mSchoolHead;
    private TextView mTvSchoolAddress;
    private TextView mTvSchoolCode;
    private TextView mTvSchoolName;
    private ImageView majorSchoolSelectIv;
    private TextView majorSchoolSelectTv;
    private ImageView majorTypeIv;
    private TextView majorTypeTv;
    private RelativeLayout rellogview;
    private String schoolId;
    private Toolbar toobars1;
    private RelativeLayout toolbars;
    private String typeDescribe;
    private List<ChooseSchoolFilterBean> mListMajorTypeList = new ArrayList();
    private List<ChooseSchoolFilterBean> mListSchoolDepartment = new ArrayList();
    private List<FollowMajorsBean.DataBean> mDetailList = new ArrayList();
    String selectedMajorType = "";
    String selectedDepartmentId = "";

    private void getFilterData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.schoolId);
        YJYHttpUtils.get(this, NetworkRequestsURL.getRecruitInfoSearchParams, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolOpenMajorAct.5
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
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ChooseSchoolFilterBeanList chooseSchoolFilterBeanList = (ChooseSchoolFilterBeanList) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolFilterBeanList.class);
                        if (chooseSchoolFilterBeanList.getMajor_type() != null) {
                            SchoolOpenMajorAct.this.mListMajorTypeList.addAll(chooseSchoolFilterBeanList.getMajor_type());
                        }
                        if (chooseSchoolFilterBeanList.getSchool_department() != null) {
                            SchoolOpenMajorAct.this.mListSchoolDepartment.addAll(chooseSchoolFilterBeanList.getSchool_department());
                        }
                        SchoolOpenMajorAct.this.typeDescribe = chooseSchoolFilterBeanList.getType_describe();
                    }
                } catch (Exception e2) {
                    Log.e(SchoolOpenMajorAct.this.TAG, "onSuccess: " + e2.getMessage());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2) {
        MajorBySchoolAct.newIntent(this, this.schoolId, this.mDetailList.get(i2).getMajor_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(int i2, View view, AppBarLayout appBarLayout, int i3) {
        if (Math.abs(i3) >= i2) {
            if (view.getVisibility() == 0) {
                this.toobars1.setBackgroundColor(SkinManager.getThemeColor(this, R.attr.app_bg));
                view.setVisibility(8);
                return;
            }
            return;
        }
        if (view.getVisibility() == 8) {
            this.toobars1.setBackgroundColor(0);
            view.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        int[] iArr = new int[2];
        this.mRecycler.getLocationOnScreen(iArr);
        showSearchParams((Build.VERSION.SDK_INT > 30 ? CommonUtil.getScreenHeight(this.mContext) + this.actionbar : CommonUtil.getScreenHeight(this.mContext)) - iArr[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        int[] iArr = new int[2];
        this.mRecycler.getLocationOnScreen(iArr);
        showDepartmentDialog((Build.VERSION.SDK_INT > 30 ? CommonUtil.getScreenHeight(this.mContext) + this.actionbar : CommonUtil.getScreenHeight(this.mContext)) - iArr[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$5(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$6(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.schoolId);
        if (!this.selectedMajorType.isEmpty()) {
            ajaxParams.put("major_type", this.selectedMajorType);
        }
        if (!this.selectedDepartmentId.isEmpty()) {
            ajaxParams.put("school_department_id", this.selectedDepartmentId);
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.getOpenMajorList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolOpenMajorAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SchoolOpenMajorAct.this.hideProgressDialog();
                SchoolOpenMajorAct.this.showEmptyView();
                NewToast.showShort(SchoolOpenMajorAct.this, "网络连接失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SchoolOpenMajorAct.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                SchoolOpenMajorAct.this.hideProgressDialog();
                try {
                    SchoolMajorsListbean schoolMajorsListbean = (SchoolMajorsListbean) new Gson().fromJson(s2, SchoolMajorsListbean.class);
                    if ("200".equals(schoolMajorsListbean.getCode())) {
                        SchoolOpenMajorAct.this.updateUI(schoolMajorsListbean);
                    } else {
                        NewToast.showShort(SchoolOpenMajorAct.this, schoolMajorsListbean.getMessage(), 0).show();
                        SchoolOpenMajorAct.this.showEmptyView();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SchoolOpenMajorAct.this, "数据解析出错", 0).show();
                    SchoolOpenMajorAct.this.showEmptyView();
                }
            }
        });
    }

    public static void newIntent(Context context, String schoolId) {
        Intent intent = new Intent(context, (Class<?>) SchoolOpenMajorAct.class);
        intent.putExtra("schoolId", schoolId);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListDataChanged(int index) {
        int i2 = R.drawable.icon_arrow_first_txt_color_bottom;
        if (index == 0) {
            this.majorTypeTv.setTextColor(!this.selectedMajorType.isEmpty() ? SkinManager.getThemeColor(this, R.attr.first_txt_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            this.majorTypeIv.setImageResource(!this.selectedMajorType.isEmpty() ? R.drawable.icon_arrow_first_txt_color_bottom : R.drawable.icon_arrow_second_txt_color_bottom);
        }
        if (index == 1) {
            this.majorSchoolSelectTv.setTextColor(!this.selectedDepartmentId.isEmpty() ? SkinManager.getThemeColor(this, R.attr.first_txt_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            ImageView imageView = this.majorSchoolSelectIv;
            if (this.selectedDepartmentId.isEmpty()) {
                i2 = R.drawable.icon_arrow_second_txt_color_bottom;
            }
            imageView.setImageResource(i2);
        }
        loadData();
    }

    private void showDepartmentDialog(int allViewHeight) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mListSchoolDepartment.size(); i2++) {
            ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
            filterDataBean.setKey(this.mListSchoolDepartment.get(i2).getId());
            filterDataBean.setTitle(this.mListSchoolDepartment.get(i2).getTitle());
            filterDataBean.setSelected(Objects.equals(this.selectedDepartmentId, this.mListSchoolDepartment.get(i2).getId()));
            arrayList.add(filterDataBean);
        }
        showOrHiddenArrow(true, this.majorSchoolSelectIv);
        new ChooseFilterPopuWindow(this, this.mLyCategory, allViewHeight, arrayList, new ChooseFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolOpenMajorAct.3
            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemDissmissLinsenter() {
                SchoolOpenMajorAct schoolOpenMajorAct = SchoolOpenMajorAct.this;
                schoolOpenMajorAct.showOrHiddenArrow(false, schoolOpenMajorAct.majorSchoolSelectIv);
            }

            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemLinsenter(int choosePos, ForumFilterBean.FilterDataBean data) {
                for (int i3 = 0; i3 < SchoolOpenMajorAct.this.mListSchoolDepartment.size(); i3++) {
                    if (((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListSchoolDepartment.get(i3)).getId().equals(data.getKey())) {
                        ((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListSchoolDepartment.get(i3)).setSelected(data.isSelected());
                    } else {
                        ((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListSchoolDepartment.get(i3)).setSelected(false);
                    }
                }
                SchoolOpenMajorAct.this.selectedDepartmentId = data.isSelected() ? ((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListSchoolDepartment.get(choosePos)).getId() : "";
                SchoolOpenMajorAct schoolOpenMajorAct = SchoolOpenMajorAct.this;
                schoolOpenMajorAct.showOrHiddenArrow(false, schoolOpenMajorAct.majorSchoolSelectIv);
                SchoolOpenMajorAct.this.notifyListDataChanged(1);
            }
        }, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView() {
        this.mLlEmpty.setVisibility(0);
        this.mRecycler.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.y5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SchoolOpenMajorAct.lambda$showOrHiddenArrow$5(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.z5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SchoolOpenMajorAct.lambda$showOrHiddenArrow$6(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    private void showSearchParams(int allViewHeight) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mListMajorTypeList.size(); i2++) {
            ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
            filterDataBean.setKey(this.mListMajorTypeList.get(i2).getId());
            filterDataBean.setTitle(this.mListMajorTypeList.get(i2).getTitle());
            filterDataBean.setSelected(Objects.equals(this.selectedMajorType, this.mListMajorTypeList.get(i2).getId()));
            arrayList.add(filterDataBean);
        }
        showOrHiddenArrow(true, this.majorTypeIv);
        new ChooseFilterPopuWindow(this, this.mLyCategory, allViewHeight, arrayList, new ChooseFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolOpenMajorAct.4
            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemDissmissLinsenter() {
                SchoolOpenMajorAct schoolOpenMajorAct = SchoolOpenMajorAct.this;
                schoolOpenMajorAct.showOrHiddenArrow(false, schoolOpenMajorAct.majorTypeIv);
            }

            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemLinsenter(int choosePos, ForumFilterBean.FilterDataBean data) {
                for (int i3 = 0; i3 < SchoolOpenMajorAct.this.mListMajorTypeList.size(); i3++) {
                    if (((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListMajorTypeList.get(i3)).getId().equals(data.getKey())) {
                        ((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListMajorTypeList.get(i3)).setSelected(data.isSelected());
                    } else {
                        ((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListMajorTypeList.get(i3)).setSelected(false);
                    }
                }
                SchoolOpenMajorAct.this.selectedMajorType = data.isSelected() ? ((ChooseSchoolFilterBean) SchoolOpenMajorAct.this.mListMajorTypeList.get(choosePos)).getId() : "";
                SchoolOpenMajorAct schoolOpenMajorAct = SchoolOpenMajorAct.this;
                schoolOpenMajorAct.showOrHiddenArrow(false, schoolOpenMajorAct.majorTypeIv);
                SchoolOpenMajorAct.this.notifyListDataChanged(0);
            }
        }, true);
    }

    private void showTagView(List<String> tagList) {
        this.mLyTag.removeAllViews();
        if (tagList == null || tagList.isEmpty()) {
            this.mLyTag.setVisibility(4);
            return;
        }
        int pxByDp = ScreenUtil.getPxByDp((Context) this, 4);
        int pxByDp2 = ScreenUtil.getPxByDp((Context) this, 1);
        for (int i2 = 0; i2 < tagList.size(); i2++) {
            TextView textView = new TextView(this);
            textView.setText(tagList.get(i2));
            textView.setTextSize(10.0f);
            textView.setTextColor(getColor(SkinManager.getCurrentSkinType(this) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color));
            textView.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
            textView.setBackgroundResource(R.drawable.shape_computer_time_bg);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.rightMargin = ScreenUtil.getPxByDp((Context) this, 8);
            textView.setLayoutParams(layoutParams);
            this.mLyTag.addView(textView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(SchoolMajorsListbean detailBean) {
        showTagView(detailBean.getData().getAttr());
        GlideUtils.loadImage(this, detailBean.getData().getCover(), this.mSchoolHead, R.mipmap.ic_order_default, R.mipmap.ic_order_default);
        this.mTvSchoolName.setText(detailBean.getData().getTitle());
        TextView textView = this.mTvSchoolAddress;
        StringBuilder sb = new StringBuilder();
        sb.append("地址：");
        sb.append(TextUtils.isEmpty(detailBean.getData().getLocation()) ? "--" : detailBean.getData().getLocation());
        textView.setText(sb.toString());
        TextView textView2 = this.mTvSchoolCode;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("代码：");
        sb2.append(TextUtils.isEmpty(detailBean.getData().getCode()) ? "--" : detailBean.getData().getCode());
        textView2.setText(sb2.toString());
        if (detailBean.getData() == null || detailBean.getData().getMajorInfo().isEmpty()) {
            showEmptyView();
            return;
        }
        List<FollowMajorsBean.DataBean> majorInfo = detailBean.getData().getMajorInfo();
        this.mDetailList.clear();
        this.mDetailList.addAll(majorInfo);
        this.mAdapter.notifyDataSetChanged();
        this.mLlEmpty.setVisibility(8);
        this.mRecycler.setVisibility(0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mSchoolHead = (CircleImageView) findViewById(R.id.school_head);
        this.mTvSchoolName = (TextView) findViewById(R.id.tv_school_name);
        this.mLyTag = (LinearLayout) findViewById(R.id.ly_tag);
        this.mTvSchoolAddress = (TextView) findViewById(R.id.tv_school_address);
        this.mTvSchoolCode = (TextView) findViewById(R.id.tv_school_code);
        final View viewFindViewById = findViewById(R.id.img_bg);
        this.mLyCategory = (LinearLayout) findViewById(R.id.ly_category);
        this.mLyDepartment = (LinearLayout) findViewById(R.id.ly_department);
        this.mRecycler = (RecyclerView) findViewById(R.id.recycler);
        this.mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        this.majorTypeTv = (TextView) findViewById(R.id.major_type_tv);
        this.majorTypeIv = (ImageView) findViewById(R.id.major_type_iv);
        this.majorSchoolSelectTv = (TextView) findViewById(R.id.major_school_select_tv);
        this.majorSchoolSelectIv = (ImageView) findViewById(R.id.major_school_select_iv);
        MajorsListAdapter majorsListAdapter = new MajorsListAdapter(this, this.mDetailList);
        this.mAdapter = majorsListAdapter;
        this.mRecycler.setAdapter(majorsListAdapter);
        this.mAdapter.setOnItemClickListener(new MajorsListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a6
            @Override // com.psychiatrygarden.adapter.MajorsListAdapter.OnItemClickListener
            public final void onItemClick(int i2) {
                this.f11189a.lambda$init$0(i2);
            }
        });
        textView.setText("开设专业");
        this.schoolId = getIntent().getStringExtra("schoolId");
        this.actionbar = StatusBarUtil.getStatusBarHeight(this.mContext);
        this.appbarlayout.setSelected(false);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        ((FrameLayout.LayoutParams) layoutParams).height = UIUtil.dip2px(this.mContext, 44.0d) + this.actionbar;
        layoutParams.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams);
        Toolbar.LayoutParams layoutParams2 = new Toolbar.LayoutParams(this.toolbars.getLayoutParams());
        layoutParams2.setMargins(0, this.actionbar, 0, 0);
        this.toolbars.setLayoutParams(layoutParams2);
        CollapsingToolbarLayout.LayoutParams layoutParams3 = (CollapsingToolbarLayout.LayoutParams) this.rellogview.getLayoutParams();
        ((FrameLayout.LayoutParams) layoutParams3).topMargin = this.actionbar + UIUtil.dip2px(this, 68.0d);
        this.rellogview.setLayoutParams(layoutParams3);
        final AppBarLayout.LayoutParams layoutParams4 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolOpenMajorAct.1
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = SchoolOpenMajorAct.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams4).height = measuredHeight;
                SchoolOpenMajorAct.this.collapse.setLayoutParams(layoutParams4);
                Log.d("CollapsingToolbarHeight", "Height: " + measuredHeight);
            }
        });
        final int iDip2px = UIUtil.dip2px(this.mContext, 44.0d);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b6
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f11221a.lambda$init$1(iDip2px, viewFindViewById, appBarLayout, i2);
            }
        });
        loadData();
        getFilterData();
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

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_school_open_major);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.v5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11438c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mLyCategory.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.w5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11444c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyDepartment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.x5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11464c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
