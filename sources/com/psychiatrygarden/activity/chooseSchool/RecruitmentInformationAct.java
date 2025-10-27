package com.psychiatrygarden.activity.chooseSchool;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.bean.ChooseSchoolServerCustomer;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.bean.SchoolScoreLineBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.ChooseSchoolFormLeftItemView;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.RecruitmentInformationItemView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class RecruitmentInformationAct extends BaseActivity {
    private int actionbar;
    private String activityId;
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private String mBatch;
    private String mDepartment;
    private CustomEmptyView mEmptyView;
    private ImageView mImgBack;
    private LinearLayout mLyAddLeftView;
    private LinearLayout mLyAddView;
    private LinearLayout mLyBottom;
    private NestedScrollView mLyContentView;
    private LinearLayout mLyFeedBack;
    private RelativeLayout mLyFeedBackView;
    private RelativeLayout mLyLine;
    private View mLyOpenView;
    private LinearLayout mLyTag;
    private String mMajorType;
    private TextView mNavTitle;
    private CircleImageView mSchoolHead;
    private String mSchoolId;
    private ChooseSchoolSearchAdp mSearchAdp;
    private RecyclerView mSearchRecycler;
    private TextView mTvSchoolAddress;
    private TextView mTvSchoolCode;
    private TextView mTvSchoolName;
    private String mType;
    private View mViewEmpty;
    private View mViewShadow;
    private String mYear;
    private RelativeLayout rellogview;
    private HorizontalScrollView scoreLineScroll;
    private OnlineServiceBean serviceBean;
    private Toolbar toobars1;
    private RelativeLayout toolbars;
    private String typeDescribe;
    private int viewHieght = 0;
    private List<ForumFilterBean.FilterDataBean> yearList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> departmentList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> majorTypeList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> batchList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> typeList = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(EnrollmentData enrollmentData, View view) {
            RecruitmentInformationAct recruitmentInformationAct = RecruitmentInformationAct.this;
            MajorBySchoolAct.newIntent(recruitmentInformationAct, recruitmentInformationAct.mSchoolId, enrollmentData.getMajor_id(), RecruitmentInformationAct.this.mYear, RecruitmentInformationAct.this.mDepartment, RecruitmentInformationAct.this.mBatch, RecruitmentInformationAct.this.mType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(EnrollmentData enrollmentData, View view) {
            RecruitmentInformationAct recruitmentInformationAct = RecruitmentInformationAct.this;
            MajorBySchoolAct.newIntent(recruitmentInformationAct, recruitmentInformationAct.mSchoolId, enrollmentData.getMajor_id(), RecruitmentInformationAct.this.mYear, RecruitmentInformationAct.this.mDepartment, RecruitmentInformationAct.this.mBatch, RecruitmentInformationAct.this.mType);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            RecruitmentInformationAct.this.mEmptyView.setLoadFileResUi(RecruitmentInformationAct.this);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            SharePreferencesUtils.writeIntConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_FREE_COUNT, SharePreferencesUtils.readIntConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_FREE_COUNT, RecruitmentInformationAct.this.mContext, 0) - 1, RecruitmentInformationAct.this.mContext);
            try {
                SchoolScoreLineBean schoolScoreLineBean = (SchoolScoreLineBean) new Gson().fromJson(s2, SchoolScoreLineBean.class);
                if (!schoolScoreLineBean.getCode().equals("200") || schoolScoreLineBean.getData() == null) {
                    return;
                }
                RecruitmentInformationAct.this.mEmptyView.stopAnim();
                RecruitmentInformationAct.this.mEmptyView.setVisibility(8);
                RecruitmentInformationAct.this.activityId = schoolScoreLineBean.getData().getActivity_id();
                if (TextUtils.isEmpty(schoolScoreLineBean.getData().getIs_pass()) || !schoolScoreLineBean.getData().getIs_pass().equals("1")) {
                    RecruitmentInformationAct.this.mLyOpenView.setVisibility(0);
                    RecruitmentInformationAct.this.mLyBottom.setVisibility(0);
                } else {
                    RecruitmentInformationAct.this.mLyOpenView.setVisibility(8);
                    RecruitmentInformationAct.this.mLyBottom.setVisibility(8);
                }
                GlideApp.with(RecruitmentInformationAct.this.mContext).load((Object) GlideUtils.generateUrl(schoolScoreLineBean.getData().getCover())).placeholder(R.mipmap.ic_avatar_def).into(RecruitmentInformationAct.this.mSchoolHead);
                RecruitmentInformationAct.this.mTvSchoolName.setText(schoolScoreLineBean.getData().getTitle());
                RecruitmentInformationAct.this.mLyTag.removeAllViews();
                int pxByDp = ScreenUtil.getPxByDp((Context) RecruitmentInformationAct.this, 4);
                int pxByDp2 = ScreenUtil.getPxByDp((Context) RecruitmentInformationAct.this, 1);
                if (schoolScoreLineBean.getData().getAttr() == null || schoolScoreLineBean.getData().getAttr().size() <= 0) {
                    RecruitmentInformationAct.this.mLyTag.setVisibility(8);
                } else {
                    RecruitmentInformationAct.this.mLyTag.setVisibility(0);
                    for (int i2 = 0; i2 < schoolScoreLineBean.getData().getAttr().size(); i2++) {
                        TextView textView = new TextView(RecruitmentInformationAct.this);
                        textView.setTextSize(10.0f);
                        textView.setText(schoolScoreLineBean.getData().getAttr().get(i2));
                        textView.setTextColor(SkinManager.getCurrentSkinType(RecruitmentInformationAct.this) == 1 ? RecruitmentInformationAct.this.getColor(R.color.main_theme_color_night) : RecruitmentInformationAct.this.getColor(R.color.main_theme_color));
                        textView.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
                        textView.setBackgroundResource(R.drawable.shape_computer_time_bg);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                        layoutParams.rightMargin = ScreenUtil.getPxByDp((Context) RecruitmentInformationAct.this, 8);
                        textView.setLayoutParams(layoutParams);
                        RecruitmentInformationAct.this.mLyTag.addView(textView);
                    }
                }
                if (TextUtils.isEmpty(schoolScoreLineBean.getData().getCity_title())) {
                    RecruitmentInformationAct.this.mTvSchoolAddress.setVisibility(8);
                } else {
                    RecruitmentInformationAct.this.mTvSchoolAddress.setVisibility(0);
                    RecruitmentInformationAct.this.mTvSchoolAddress.setText("地址：" + schoolScoreLineBean.getData().getCity_title());
                }
                if (TextUtils.isEmpty(schoolScoreLineBean.getData().getCode())) {
                    RecruitmentInformationAct.this.mTvSchoolCode.setVisibility(8);
                } else {
                    RecruitmentInformationAct.this.mTvSchoolCode.setVisibility(0);
                    RecruitmentInformationAct.this.mTvSchoolCode.setText("代码：" + schoolScoreLineBean.getData().getCode());
                }
                RecruitmentInformationAct.this.mLyAddLeftView.removeAllViews();
                for (int i3 = 0; i3 < schoolScoreLineBean.getData().getList().size(); i3++) {
                    final EnrollmentData enrollmentData = schoolScoreLineBean.getData().getList().get(i3);
                    ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(RecruitmentInformationAct.this.mContext, false);
                    chooseSchoolFormLeftItemView.setData(i3, enrollmentData, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.x3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f11458c.lambda$onSuccess$0(enrollmentData, view);
                        }
                    });
                    RecruitmentInformationAct.this.mLyAddLeftView.addView(chooseSchoolFormLeftItemView);
                }
                RecruitmentInformationAct.this.mLyAddView.removeAllViews();
                for (int i4 = 0; i4 < schoolScoreLineBean.getData().getList().size(); i4++) {
                    final EnrollmentData enrollmentData2 = schoolScoreLineBean.getData().getList().get(i4);
                    RecruitmentInformationItemView recruitmentInformationItemView = new RecruitmentInformationItemView(RecruitmentInformationAct.this.mContext, false, false);
                    recruitmentInformationItemView.setData(i4, enrollmentData2, false, true, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.y3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f11470c.lambda$onSuccess$1(enrollmentData2, view);
                        }
                    });
                    RecruitmentInformationAct.this.mLyAddView.addView(recruitmentInformationItemView);
                }
                RecruitmentInformationAct.this.mViewEmpty.setVisibility(schoolScoreLineBean.getData().getList().size() > 1 ? 8 : 0);
            } catch (Exception e2) {
                e2.printStackTrace();
                RecruitmentInformationAct.this.mEmptyView.setLoadFileResUi(RecruitmentInformationAct.this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        if (!TextUtils.isEmpty(this.mYear)) {
            ajaxParams.put("year", this.mYear);
        }
        if (!TextUtils.isEmpty(this.mMajorType)) {
            ajaxParams.put("major_type", this.mMajorType);
        }
        if (!TextUtils.isEmpty(this.mBatch)) {
            ajaxParams.put("batch", this.mBatch);
        }
        if (!TextUtils.isEmpty(this.mType)) {
            ajaxParams.put("type", this.mType);
        }
        if (!TextUtils.isEmpty(this.mDepartment)) {
            ajaxParams.put("school_department_id", this.mDepartment);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getRecruitInfo, ajaxParams, new AnonymousClass2());
    }

    private void getFeedBack() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.chooseSchoolFeedBackCS, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        ChooseSchoolServerCustomer chooseSchoolServerCustomer = (ChooseSchoolServerCustomer) new Gson().fromJson(new JSONObject(s2).optString("data"), ChooseSchoolServerCustomer.class);
                        if (TextUtils.isEmpty(chooseSchoolServerCustomer.is_show()) || !chooseSchoolServerCustomer.is_show().equals("1")) {
                            RecruitmentInformationAct.this.mLyFeedBackView.setVisibility(8);
                        } else {
                            RecruitmentInformationAct.this.mLyFeedBackView.setVisibility(0);
                            RecruitmentInformationAct.this.serviceBean = chooseSchoolServerCustomer.getFeedback();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getSearchParamsData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getRecruitInfoSearchParams, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RecruitmentInformationAct.this.getData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ChooseSchoolFilterBeanList chooseSchoolFilterBeanList = (ChooseSchoolFilterBeanList) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolFilterBeanList.class);
                        RecruitmentInformationAct.this.typeDescribe = chooseSchoolFilterBeanList.getType_describe();
                        if (chooseSchoolFilterBeanList.getYear() != null && chooseSchoolFilterBeanList.getYear().size() > 0) {
                            RecruitmentInformationAct.this.mYear = chooseSchoolFilterBeanList.getYear().get(1).getId();
                            int i2 = 0;
                            while (i2 < chooseSchoolFilterBeanList.getYear().size()) {
                                ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
                                filterDataBean.setKey(chooseSchoolFilterBeanList.getYear().get(i2).getId());
                                filterDataBean.setTitle(chooseSchoolFilterBeanList.getYear().get(i2).getTitle());
                                filterDataBean.setSelected(i2 == 1 || chooseSchoolFilterBeanList.getYear().get(i2).getSelected());
                                RecruitmentInformationAct.this.yearList.add(filterDataBean);
                                i2++;
                            }
                        }
                        if (chooseSchoolFilterBeanList.getSchool_department() != null && chooseSchoolFilterBeanList.getSchool_department().size() > 0) {
                            for (int i3 = 0; i3 < chooseSchoolFilterBeanList.getSchool_department().size(); i3++) {
                                ForumFilterBean.FilterDataBean filterDataBean2 = new ForumFilterBean.FilterDataBean();
                                filterDataBean2.setKey(chooseSchoolFilterBeanList.getSchool_department().get(i3).getId());
                                filterDataBean2.setTitle(chooseSchoolFilterBeanList.getSchool_department().get(i3).getTitle());
                                filterDataBean2.setSelected(chooseSchoolFilterBeanList.getSchool_department().get(i3).getSelected());
                                RecruitmentInformationAct.this.departmentList.add(filterDataBean2);
                            }
                        }
                        if (chooseSchoolFilterBeanList.getMajor_type() != null && chooseSchoolFilterBeanList.getMajor_type().size() > 0) {
                            for (int i4 = 0; i4 < chooseSchoolFilterBeanList.getMajor_type().size(); i4++) {
                                ForumFilterBean.FilterDataBean filterDataBean3 = new ForumFilterBean.FilterDataBean();
                                filterDataBean3.setKey(chooseSchoolFilterBeanList.getMajor_type().get(i4).getId());
                                filterDataBean3.setTitle(chooseSchoolFilterBeanList.getMajor_type().get(i4).getTitle());
                                filterDataBean3.setSelected(chooseSchoolFilterBeanList.getMajor_type().get(i4).getSelected());
                                RecruitmentInformationAct.this.majorTypeList.add(filterDataBean3);
                            }
                        }
                        if (chooseSchoolFilterBeanList.getBatch() != null && chooseSchoolFilterBeanList.getBatch().size() > 0) {
                            for (int i5 = 0; i5 < chooseSchoolFilterBeanList.getBatch().size(); i5++) {
                                ForumFilterBean.FilterDataBean filterDataBean4 = new ForumFilterBean.FilterDataBean();
                                filterDataBean4.setKey(chooseSchoolFilterBeanList.getBatch().get(i5).getId());
                                filterDataBean4.setTitle(chooseSchoolFilterBeanList.getBatch().get(i5).getTitle());
                                filterDataBean4.setSelected(chooseSchoolFilterBeanList.getBatch().get(i5).getSelected());
                                RecruitmentInformationAct.this.batchList.add(filterDataBean4);
                            }
                        }
                        if (chooseSchoolFilterBeanList.getType() != null && chooseSchoolFilterBeanList.getType().size() > 0) {
                            for (int i6 = 0; i6 < chooseSchoolFilterBeanList.getType().size(); i6++) {
                                ForumFilterBean.FilterDataBean filterDataBean5 = new ForumFilterBean.FilterDataBean();
                                filterDataBean5.setKey(chooseSchoolFilterBeanList.getType().get(i6).getId());
                                filterDataBean5.setTitle(chooseSchoolFilterBeanList.getType().get(i6).getTitle());
                                filterDataBean5.setSelected(chooseSchoolFilterBeanList.getType().get(i6).getSelected());
                                RecruitmentInformationAct.this.typeList.add(filterDataBean5);
                            }
                        }
                    }
                    RecruitmentInformationAct.this.getData();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(ImageView imageView, AppBarLayout appBarLayout, int i2) {
        float f2 = i2 * 1.0f;
        this.rellogview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.rellogview.setVisibility(8);
            this.toobars1.setBackgroundColor(SkinManager.getThemeColor(this, R.attr.app_bg));
            imageView.setVisibility(8);
        } else {
            this.rellogview.setVisibility(0);
            this.toobars1.setBackgroundColor(getColor(R.color.transparent));
            imageView.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view, int i2, int i3, int i4, int i5) {
        if (i2 > 20) {
            if (this.mViewShadow.getVisibility() == 8) {
                this.mLyLine.setVisibility(8);
                this.mViewShadow.setVisibility(0);
                return;
            }
            return;
        }
        if (this.mViewShadow.getVisibility() == 0) {
            this.mViewShadow.setVisibility(8);
            this.mLyLine.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openVip$8() {
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, "1", this);
        this.mLyOpenView.setVisibility(8);
        this.mLyBottom.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        if (this.serviceBean != null) {
            AliYunLogUtil.getInstance().addLog(AliyunEvent.EnterpriseWeChatFeedback);
            CommonUtil.onlineService(this, this.serviceBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$6(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$7(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public static void newIntent(Context context, String schoolId) {
        Intent intent = new Intent(context, (Class<?>) RecruitmentInformationAct.class);
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
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q3
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f11388a.lambda$openVip$8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.o3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RecruitmentInformationAct.lambda$showOrHiddenArrow$6(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.p3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                RecruitmentInformationAct.lambda$showOrHiddenArrow$7(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void showSearchParams(final int r14, android.view.View r15, final java.lang.String r16, final android.widget.ImageView r17) {
        /*
            r13 = this;
            r9 = r13
            r0 = r16
            r1 = r17
            r2 = 2
            int[] r3 = new int[r2]
            androidx.core.widget.NestedScrollView r4 = r9.mLyContentView
            r4.getLocationOnScreen(r3)
            r4 = 1
            r3 = r3[r4]
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 30
            if (r5 <= r6) goto L20
            android.content.Context r5 = r9.mContext
            int r5 = com.psychiatrygarden.utils.CommonUtil.getScreenHeight(r5)
            int r6 = r9.actionbar
            int r5 = r5 + r6
            goto L26
        L20:
            android.content.Context r5 = r9.mContext
            int r5 = com.psychiatrygarden.utils.CommonUtil.getScreenHeight(r5)
        L26:
            int r5 = r5 - r3
            r9.viewHieght = r5
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r16.hashCode()
            int r5 = r16.hashCode()
            java.lang.String r6 = "department"
            java.lang.String r7 = "category"
            r8 = -1
            switch(r5) {
                case 3575610: goto L65;
                case 3704893: goto L5a;
                case 50511102: goto L53;
                case 93509434: goto L48;
                case 848184146: goto L3f;
                default: goto L3d;
            }
        L3d:
            r2 = r8
            goto L6f
        L3f:
            boolean r2 = r0.equals(r6)
            if (r2 != 0) goto L46
            goto L3d
        L46:
            r2 = 4
            goto L6f
        L48:
            java.lang.String r2 = "batch"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L51
            goto L3d
        L51:
            r2 = 3
            goto L6f
        L53:
            boolean r5 = r0.equals(r7)
            if (r5 != 0) goto L6f
            goto L3d
        L5a:
            java.lang.String r2 = "year"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L63
            goto L3d
        L63:
            r2 = r4
            goto L6f
        L65:
            java.lang.String r2 = "type"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L6e
            goto L3d
        L6e:
            r2 = 0
        L6f:
            switch(r2) {
                case 0: goto L81;
                case 1: goto L7d;
                case 2: goto L7a;
                case 3: goto L77;
                case 4: goto L74;
                default: goto L72;
            }
        L72:
            r5 = r3
            goto L84
        L74:
            java.util.List<com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean> r2 = r9.departmentList
            goto L7f
        L77:
            java.util.List<com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean> r2 = r9.batchList
            goto L7f
        L7a:
            java.util.List<com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean> r2 = r9.typeList
            goto L7f
        L7d:
            java.util.List<com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean> r2 = r9.yearList
        L7f:
            r5 = r2
            goto L84
        L81:
            java.util.List<com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean> r2 = r9.majorTypeList
            goto L7f
        L84:
            r13.showOrHiddenArrow(r4, r1)
            com.psychiatrygarden.widget.ChooseFilterPopuWindow r2 = new com.psychiatrygarden.widget.ChooseFilterPopuWindow
            int r3 = r9.viewHieght
            com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct$6 r8 = new com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct$6
            r4 = r14
            r8.<init>()
            r10 = 1
            boolean r11 = r0.equals(r6)
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L9f
            java.lang.String r0 = r9.typeDescribe
            goto La1
        L9f:
            java.lang.String r0 = ""
        La1:
            r12 = r0
            r0 = r2
            r1 = r13
            r2 = r15
            r4 = r5
            r5 = r8
            r6 = r10
            r7 = r11
            r8 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct.showSearchParams(int, android.view.View, java.lang.String, android.widget.ImageView):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDataList(List<ForumFilterBean.FilterDataBean> mList, String key, boolean isSelected) {
        for (int i2 = 0; i2 < mList.size(); i2++) {
            if (mList.get(i2).getKey().equals(key)) {
                mList.get(i2).setSelected(isSelected);
            } else {
                mList.get(i2).setSelected(false);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mSchoolId = getIntent().getStringExtra("schoolId");
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        final ImageView imageView = (ImageView) findViewById(R.id.img_bg);
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        findViewById(R.id.tabbar);
        this.mSchoolHead = (CircleImageView) findViewById(R.id.school_head);
        this.mTvSchoolName = (TextView) findViewById(R.id.tv_school_name);
        this.mLyTag = (LinearLayout) findViewById(R.id.ly_tag);
        this.mTvSchoolAddress = (TextView) findViewById(R.id.tv_school_address);
        this.mTvSchoolCode = (TextView) findViewById(R.id.tv_school_code);
        this.mViewShadow = findViewById(R.id.line_shadow);
        this.mLyLine = (RelativeLayout) findViewById(R.id.ly_line);
        this.scoreLineScroll = (HorizontalScrollView) findViewById(R.id.score_line_scroll);
        this.mLyFeedBack = (LinearLayout) findViewById(R.id.ly_feedback);
        this.mLyFeedBackView = (RelativeLayout) findViewById(R.id.ly_feedback_view);
        this.mLyAddLeftView = (LinearLayout) findViewById(R.id.ly_add_left_view);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.mLyOpenView = findViewById(R.id.ly_open_vip);
        this.mLyBottom = (LinearLayout) findViewById(R.id.ly_bottom);
        this.mLyContentView = (NestedScrollView) findViewById(R.id.ly_content);
        this.mSearchRecycler = (RecyclerView) findViewById(R.id.search_recycler);
        this.mViewEmpty = findViewById(R.id.ly_empty_view);
        this.mEmptyView = (CustomEmptyView) findViewById(R.id.empty_view);
        textView.setText("招录信息");
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
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct.1
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = RecruitmentInformationAct.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams4).height = measuredHeight;
                RecruitmentInformationAct.this.collapse.setLayoutParams(layoutParams4);
                RecruitmentInformationAct recruitmentInformationAct = RecruitmentInformationAct.this;
                recruitmentInformationAct.viewHieght = ScreenUtil.getScreenHeight(recruitmentInformationAct) - measuredHeight;
                Log.d("CollapsingToolbarHeight", "Height: " + measuredHeight);
            }
        });
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.r3
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f11396a.lambda$init$0(imageView, appBarLayout, i2);
            }
        });
        this.scoreLineScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s3
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                this.f11404c.lambda$init$1(view, i2, i3, i4, i5);
            }
        });
        this.mSearchRecycler.setLayoutManager(new GridLayoutManager(this, 5));
        ChooseSchoolSearchAdp chooseSchoolSearchAdp = new ChooseSchoolSearchAdp();
        this.mSearchAdp = chooseSchoolSearchAdp;
        this.mSearchRecycler.setAdapter(chooseSchoolSearchAdp);
        this.mEmptyView.restartAnim();
        ArrayList arrayList = new ArrayList();
        ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
        filterDataBean.setTitle("年份");
        filterDataBean.setType("year");
        filterDataBean.setSelected(true);
        arrayList.add(filterDataBean);
        ForumFilterBean.FilterDataBean filterDataBean2 = new ForumFilterBean.FilterDataBean();
        filterDataBean2.setTitle("专业类别");
        filterDataBean2.setType("type");
        arrayList.add(filterDataBean2);
        ForumFilterBean.FilterDataBean filterDataBean3 = new ForumFilterBean.FilterDataBean();
        filterDataBean3.setTitle("批次");
        filterDataBean3.setType("batch");
        arrayList.add(filterDataBean3);
        ForumFilterBean.FilterDataBean filterDataBean4 = new ForumFilterBean.FilterDataBean();
        filterDataBean4.setTitle("录取类别");
        filterDataBean4.setType(UriUtil.QUERY_CATEGORY);
        arrayList.add(filterDataBean4);
        ForumFilterBean.FilterDataBean filterDataBean5 = new ForumFilterBean.FilterDataBean();
        filterDataBean5.setTitle("院系");
        filterDataBean5.setType("department");
        arrayList.add(filterDataBean5);
        this.mSearchAdp.setNewInstance(arrayList);
        getSearchParamsData();
        getFeedBack();
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
        setContentView(R.layout.layout_recruitment_information);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11414c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mLyFeedBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11425c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mSearchAdp.setOnItemActionLisenter(new ChooseSchoolSearchAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.chooseSchool.RecruitmentInformationAct.5
            @Override // com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp.OnItemActionLisenter
            public void setItemClickAction(int pos, ForumFilterBean.FilterDataBean item, ImageView imgView) {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                RecruitmentInformationAct recruitmentInformationAct = RecruitmentInformationAct.this;
                recruitmentInformationAct.showSearchParams(pos, recruitmentInformationAct.mSearchRecycler, item.getType(), imgView);
            }
        });
        this.mLyOpenView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.v3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11436c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mLyBottom.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.w3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11442c.lambda$setListenerForWidget$5(view);
            }
        });
    }
}
