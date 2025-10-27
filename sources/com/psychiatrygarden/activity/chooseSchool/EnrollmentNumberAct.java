package com.psychiatrygarden.activity.chooseSchool;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.bean.SchoolEnrollmentBean;
import com.psychiatrygarden.bean.SchoolEnrollmentDepartmentBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.ChooseFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseSchoolFormLeftItemView;
import com.psychiatrygarden.widget.ChooseSchoolScoreLineItemView;
import com.psychiatrygarden.widget.CircleImageView;
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
public class EnrollmentNumberAct extends BaseActivity {
    private int actionbar;
    private String activityId;
    private String mDepartment;
    private ImageView mImgArrow;
    private ImageView mImgArrowThree;
    private ImageView mImgArrowTwo;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private View mLineOne;
    private View mLineThree;
    private View mLineTwo;
    private LinearLayout mLyAddLeftView;
    private LinearLayout mLyAddLeftViewThree;
    private LinearLayout mLyAddLeftViewTwo;
    private LinearLayout mLyAddView;
    private LinearLayout mLyAddViewThree;
    private LinearLayout mLyAddViewTwo;
    private RelativeLayout mLyLine;
    private RelativeLayout mLyLineTwo;
    private LinearLayout mLyMore;
    private LinearLayout mLyMoreThree;
    private LinearLayout mLyMoreTwo;
    private RelativeLayout mLyNumberView;
    private View mLyOpenVipOne;
    private View mLyOpenVipThree;
    private View mLyOpenVipTwo;
    private RelativeLayout mLyScoreView;
    private LinearLayout mLyTag;
    private ChooseSchoolSearchAdp mRankAdp;
    private RecyclerView mRankRecycler;
    private CircleImageView mSchoolHead;
    private String mSchoolId;
    private List<EnrollmentData> mScoreLineOneList;
    private List<EnrollmentData> mScoreLineThreeList;
    private List<EnrollmentData> mScoreLineTwoList;
    private NestedScrollView mScrollView;
    private ChooseSchoolSearchAdp mSearchAdp;
    private RecyclerView mSearchRecycler;
    private TextView mTvMore;
    private TextView mTvMoreThree;
    private TextView mTvMoreTwo;
    private TextView mTvSchoolAddress;
    private TextView mTvSchoolCode;
    private TextView mTvSchoolName;
    private View mViewEmpty;
    private View mViewEmptyThree;
    private View mViewEmptyTwo;
    private View mViewShadow;
    private View mViewShadowTwo;
    private String mYear;
    private HorizontalScrollView scoreLineScroll;
    private HorizontalScrollView scoreLineScrollTwo;
    private boolean isExpandOne = false;
    private boolean isExpandTwo = false;
    private boolean isExpandThree = false;
    private int viewHieght = 0;
    private List<ForumFilterBean.FilterDataBean> yearList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> departmentList = new ArrayList();

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getSchoolEnrollment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.EnrollmentNumberAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    SchoolEnrollmentBean schoolEnrollmentBean = (SchoolEnrollmentBean) new Gson().fromJson(s2, SchoolEnrollmentBean.class);
                    if (!schoolEnrollmentBean.getCode().equals("200") || schoolEnrollmentBean.getData() == null || schoolEnrollmentBean.getData().getResult() == null) {
                        return;
                    }
                    GlideApp.with(EnrollmentNumberAct.this.mContext).load((Object) GlideUtils.generateUrl(schoolEnrollmentBean.getData().getCover())).placeholder(R.mipmap.ic_avatar_def).into(EnrollmentNumberAct.this.mSchoolHead);
                    EnrollmentNumberAct.this.mTvSchoolName.setText(schoolEnrollmentBean.getData().getTitle());
                    EnrollmentNumberAct.this.mLyTag.removeAllViews();
                    int pxByDp = ScreenUtil.getPxByDp((Context) EnrollmentNumberAct.this, 4);
                    int pxByDp2 = ScreenUtil.getPxByDp((Context) EnrollmentNumberAct.this, 1);
                    int i2 = 8;
                    if (schoolEnrollmentBean.getData().getAttr() == null || schoolEnrollmentBean.getData().getAttr().size() <= 0) {
                        EnrollmentNumberAct.this.mLyTag.setVisibility(8);
                    } else {
                        EnrollmentNumberAct.this.mLyTag.setVisibility(0);
                        for (int i3 = 0; i3 < schoolEnrollmentBean.getData().getAttr().size(); i3++) {
                            TextView textView = new TextView(EnrollmentNumberAct.this);
                            textView.setTextSize(10.0f);
                            textView.setText(schoolEnrollmentBean.getData().getAttr().get(i3));
                            textView.setTextColor(SkinManager.getCurrentSkinType(EnrollmentNumberAct.this) == 1 ? EnrollmentNumberAct.this.getColor(R.color.main_theme_color_night) : EnrollmentNumberAct.this.getColor(R.color.main_theme_color));
                            textView.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
                            textView.setBackgroundResource(R.drawable.shape_computer_time_bg);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                            layoutParams.rightMargin = ScreenUtil.getPxByDp((Context) EnrollmentNumberAct.this, 8);
                            textView.setLayoutParams(layoutParams);
                            EnrollmentNumberAct.this.mLyTag.addView(textView);
                        }
                    }
                    if (TextUtils.isEmpty(schoolEnrollmentBean.getData().getCity_title())) {
                        EnrollmentNumberAct.this.mTvSchoolAddress.setVisibility(8);
                    } else {
                        EnrollmentNumberAct.this.mTvSchoolAddress.setVisibility(0);
                        EnrollmentNumberAct.this.mTvSchoolAddress.setText("地址：" + schoolEnrollmentBean.getData().getCity_title());
                    }
                    if (TextUtils.isEmpty(schoolEnrollmentBean.getData().getCode())) {
                        EnrollmentNumberAct.this.mTvSchoolCode.setVisibility(8);
                    } else {
                        EnrollmentNumberAct.this.mTvSchoolCode.setVisibility(0);
                        EnrollmentNumberAct.this.mTvSchoolCode.setText("代码：" + schoolEnrollmentBean.getData().getCode());
                    }
                    EnrollmentNumberAct.this.mScoreLineOneList = schoolEnrollmentBean.getData().getResult();
                    EnrollmentNumberAct.this.mViewEmpty.setVisibility(EnrollmentNumberAct.this.mScoreLineOneList.size() > 1 ? 8 : 0);
                    int size = EnrollmentNumberAct.this.mScoreLineOneList.size() > 5 ? 5 : EnrollmentNumberAct.this.mScoreLineOneList.size();
                    EnrollmentNumberAct.this.mLyMore.setVisibility(EnrollmentNumberAct.this.mScoreLineOneList.size() > 5 ? 0 : 8);
                    View view = EnrollmentNumberAct.this.mLineOne;
                    if (EnrollmentNumberAct.this.mScoreLineOneList.size() <= 5) {
                        i2 = 0;
                    }
                    view.setVisibility(i2);
                    List listSubList = EnrollmentNumberAct.this.mScoreLineOneList.size() > 5 ? EnrollmentNumberAct.this.mScoreLineOneList.subList(0, size + 1) : EnrollmentNumberAct.this.mScoreLineOneList;
                    EnrollmentNumberAct.this.mLyAddLeftView.removeAllViews();
                    for (int i4 = 0; i4 < listSubList.size(); i4++) {
                        ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(EnrollmentNumberAct.this.mContext, true);
                        chooseSchoolFormLeftItemView.setData(i4, (EnrollmentData) listSubList.get(i4), false);
                        EnrollmentNumberAct.this.mLyAddLeftView.addView(chooseSchoolFormLeftItemView);
                    }
                    EnrollmentNumberAct.this.mLyAddView.removeAllViews();
                    for (int i5 = 0; i5 < listSubList.size(); i5++) {
                        ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(EnrollmentNumberAct.this.mContext, true);
                        chooseSchoolScoreLineItemView.setData(i5, (EnrollmentData) listSubList.get(i5), true, false);
                        EnrollmentNumberAct.this.mLyAddView.addView(chooseSchoolScoreLineItemView);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDepartmentSchoolEnrollmentData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        if (!TextUtils.isEmpty(this.mDepartment)) {
            ajaxParams.put("school_department_id", this.mDepartment);
        }
        if (!TextUtils.isEmpty(this.mYear)) {
            ajaxParams.put("year", this.mYear);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getDepartmentSchoolEnrollment, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.EnrollmentNumberAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    SchoolEnrollmentDepartmentBean schoolEnrollmentDepartmentBean = (SchoolEnrollmentDepartmentBean) new Gson().fromJson(s2, SchoolEnrollmentDepartmentBean.class);
                    if (!schoolEnrollmentDepartmentBean.getCode().equals("200") || schoolEnrollmentDepartmentBean.getData() == null) {
                        return;
                    }
                    for (int i2 = 0; i2 < schoolEnrollmentDepartmentBean.getData().size(); i2++) {
                        schoolEnrollmentDepartmentBean.getData().get(i2).setMajor_title(schoolEnrollmentDepartmentBean.getData().get(i2).getSchool_department_title());
                    }
                    EnrollmentNumberAct.this.mScoreLineTwoList = schoolEnrollmentDepartmentBean.getData();
                    int size = EnrollmentNumberAct.this.mScoreLineTwoList.size() > 5 ? 5 : EnrollmentNumberAct.this.mScoreLineTwoList.size();
                    int i3 = 8;
                    EnrollmentNumberAct.this.mViewEmptyTwo.setVisibility(EnrollmentNumberAct.this.mScoreLineTwoList.size() > 1 ? 8 : 0);
                    EnrollmentNumberAct.this.mLyMoreTwo.setVisibility(EnrollmentNumberAct.this.mScoreLineTwoList.size() > 5 ? 0 : 8);
                    View view = EnrollmentNumberAct.this.mLineTwo;
                    if (EnrollmentNumberAct.this.mScoreLineTwoList.size() <= 5) {
                        i3 = 0;
                    }
                    view.setVisibility(i3);
                    List listSubList = EnrollmentNumberAct.this.mScoreLineTwoList.size() > 5 ? EnrollmentNumberAct.this.mScoreLineTwoList.subList(0, size + 1) : EnrollmentNumberAct.this.mScoreLineTwoList;
                    EnrollmentNumberAct.this.mLyAddLeftViewTwo.removeAllViews();
                    for (int i4 = 0; i4 < listSubList.size(); i4++) {
                        ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(EnrollmentNumberAct.this.mContext, false);
                        chooseSchoolFormLeftItemView.setData(i4, (EnrollmentData) listSubList.get(i4), true);
                        EnrollmentNumberAct.this.mLyAddLeftViewTwo.addView(chooseSchoolFormLeftItemView);
                    }
                    EnrollmentNumberAct.this.mLyAddViewTwo.removeAllViews();
                    for (int i5 = 0; i5 < listSubList.size(); i5++) {
                        ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(EnrollmentNumberAct.this.mContext, false);
                        chooseSchoolScoreLineItemView.setData(i5, (EnrollmentData) listSubList.get(i5), true, true);
                        EnrollmentNumberAct.this.mLyAddViewTwo.addView(chooseSchoolScoreLineItemView);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getSearchParamsData(int type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        ajaxParams.put("search_type", type + "");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getRecruitInfoSearchParams, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.EnrollmentNumberAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ChooseSchoolFilterBeanList chooseSchoolFilterBeanList = (ChooseSchoolFilterBeanList) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolFilterBeanList.class);
                        if (chooseSchoolFilterBeanList.getYear() != null && chooseSchoolFilterBeanList.getYear().size() > 0) {
                            EnrollmentNumberAct.this.mYear = chooseSchoolFilterBeanList.getYear().get(1).getId();
                            for (int i2 = 0; i2 < chooseSchoolFilterBeanList.getYear().size(); i2++) {
                                ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
                                filterDataBean.setKey(chooseSchoolFilterBeanList.getYear().get(i2).getId());
                                filterDataBean.setTitle(chooseSchoolFilterBeanList.getYear().get(i2).getTitle());
                                filterDataBean.setSelected(chooseSchoolFilterBeanList.getYear().get(i2).getSelected());
                                EnrollmentNumberAct.this.yearList.add(filterDataBean);
                            }
                        }
                        if (chooseSchoolFilterBeanList.getSchool_department() == null || chooseSchoolFilterBeanList.getSchool_department().size() <= 0) {
                            return;
                        }
                        for (int i3 = 0; i3 < chooseSchoolFilterBeanList.getSchool_department().size(); i3++) {
                            ForumFilterBean.FilterDataBean filterDataBean2 = new ForumFilterBean.FilterDataBean();
                            filterDataBean2.setKey(chooseSchoolFilterBeanList.getSchool_department().get(i3).getId());
                            filterDataBean2.setTitle(chooseSchoolFilterBeanList.getSchool_department().get(i3).getTitle());
                            filterDataBean2.setSelected(chooseSchoolFilterBeanList.getSchool_department().get(i3).getSelected());
                            EnrollmentNumberAct.this.departmentList.add(filterDataBean2);
                        }
                    }
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
    public /* synthetic */ void lambda$init$2(View view, int i2, int i3, int i4, int i5) {
        if (i2 > 20) {
            if (this.mViewShadowTwo.getVisibility() == 8) {
                this.mLyLineTwo.setVisibility(8);
                this.mViewShadowTwo.setVisibility(0);
                return;
            }
            return;
        }
        if (this.mViewShadowTwo.getVisibility() == 0) {
            this.mViewShadowTwo.setVisibility(8);
            this.mLyLineTwo.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openVip$14() {
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, "1", this);
        this.mLyOpenVipOne.setVisibility(8);
        this.mLyOpenVipTwo.setVisibility(8);
        this.mLyOpenVipThree.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        List<EnrollmentData> list = this.mScoreLineOneList;
        if (list == null || list.size() <= 0) {
            return;
        }
        if (this.isExpandOne) {
            for (int childCount = this.mLyAddLeftView.getChildCount() - 1; childCount > 5; childCount--) {
                this.mLyAddLeftView.removeViewAt(childCount);
            }
            for (int childCount2 = this.mLyAddView.getChildCount() - 1; childCount2 > 5; childCount2--) {
                this.mLyAddView.removeViewAt(childCount2);
            }
            this.mTvMore.setText("展开更多");
        } else {
            int childCount3 = this.mLyAddLeftView.getChildCount();
            List<EnrollmentData> list2 = this.mScoreLineOneList;
            List<EnrollmentData> listSubList = list2.subList(childCount3, list2.size());
            for (int i2 = 0; i2 < listSubList.size(); i2++) {
                ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(this.mContext, true);
                chooseSchoolFormLeftItemView.setData(1, listSubList.get(i2), false);
                this.mLyAddLeftView.addView(chooseSchoolFormLeftItemView);
            }
            for (int i3 = 0; i3 < listSubList.size(); i3++) {
                ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(this.mContext, true);
                chooseSchoolScoreLineItemView.setData(1, listSubList.get(i3), false, false);
                this.mLyAddView.addView(chooseSchoolScoreLineItemView);
            }
            this.mTvMore.setText("收起内容");
        }
        boolean z2 = !this.isExpandOne;
        this.isExpandOne = z2;
        showOrHiddenArrow2(z2, this.mImgArrow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        List<EnrollmentData> list = this.mScoreLineTwoList;
        if (list == null || list.size() <= 0) {
            return;
        }
        if (this.isExpandTwo) {
            for (int childCount = this.mLyAddLeftViewTwo.getChildCount() - 1; childCount > 5; childCount--) {
                this.mLyAddLeftViewTwo.removeViewAt(childCount);
            }
            for (int childCount2 = this.mLyAddViewTwo.getChildCount() - 1; childCount2 > 5; childCount2--) {
                this.mLyAddViewTwo.removeViewAt(childCount2);
            }
            this.mTvMoreTwo.setText("展开更多");
        } else {
            int childCount3 = this.mLyAddLeftViewTwo.getChildCount();
            List<EnrollmentData> list2 = this.mScoreLineTwoList;
            List<EnrollmentData> listSubList = list2.subList(childCount3, list2.size());
            for (int i2 = 0; i2 < listSubList.size(); i2++) {
                ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(this.mContext, false);
                chooseSchoolFormLeftItemView.setData(1, listSubList.get(i2), true);
                this.mLyAddLeftViewTwo.addView(chooseSchoolFormLeftItemView);
            }
            for (int i3 = 0; i3 < listSubList.size(); i3++) {
                ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(this.mContext, false);
                chooseSchoolScoreLineItemView.setData(1, listSubList.get(i3), true, true);
                this.mLyAddViewTwo.addView(chooseSchoolScoreLineItemView);
            }
            this.mTvMoreTwo.setText("收起内容");
        }
        boolean z2 = !this.isExpandTwo;
        this.isExpandTwo = z2;
        showOrHiddenArrow2(z2, this.mImgArrowTwo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        List<EnrollmentData> list = this.mScoreLineThreeList;
        if (list == null || list.size() <= 0) {
            return;
        }
        if (this.isExpandThree) {
            for (int childCount = this.mLyAddLeftViewThree.getChildCount() - 1; childCount > 4; childCount--) {
                this.mLyAddLeftViewThree.removeViewAt(childCount);
            }
            for (int childCount2 = this.mLyAddViewThree.getChildCount() - 1; childCount2 > 4; childCount2--) {
                this.mLyAddViewThree.removeViewAt(childCount2);
            }
            this.mTvMoreThree.setText("展开更多");
        } else {
            int childCount3 = this.mLyAddLeftViewThree.getChildCount() - 1;
            List<EnrollmentData> list2 = this.mScoreLineThreeList;
            List<EnrollmentData> listSubList = list2.subList(childCount3, list2.size());
            for (int i2 = 0; i2 < listSubList.size(); i2++) {
                ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(this.mContext, false);
                chooseSchoolFormLeftItemView.setData(1, listSubList.get(i2), false);
                this.mLyAddLeftViewThree.addView(chooseSchoolFormLeftItemView);
            }
            for (int i3 = 0; i3 < listSubList.size(); i3++) {
                ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(this.mContext, false);
                chooseSchoolScoreLineItemView.setData(1, listSubList.get(i3), true, false);
                this.mLyAddViewThree.addView(chooseSchoolScoreLineItemView);
            }
            this.mTvMoreThree.setText("收起内容");
        }
        boolean z2 = !this.isExpandThree;
        this.isExpandThree = z2;
        showOrHiddenArrow2(z2, this.mImgArrowThree);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$10(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$11(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow2$12(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow2$13(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public static void newIntent(Context context, String schoolId) {
        Intent intent = new Intent(context, (Class<?>) EnrollmentNumberAct.class);
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
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l1
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f11343a.lambda$openVip$14();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    EnrollmentNumberAct.lambda$showOrHiddenArrow$10(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.n1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                EnrollmentNumberAct.lambda$showOrHiddenArrow$11(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    private void showOrHiddenArrow2(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(90, 270);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.d1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    EnrollmentNumberAct.lambda$showOrHiddenArrow2$12(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(270, 90);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.e1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                EnrollmentNumberAct.lambda$showOrHiddenArrow2$13(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSearchParams(final int position, View view, int viewHieght, final String type, final ImageView arrowImg, boolean isMajor) {
        List arrayList = new ArrayList();
        type.hashCode();
        if (type.equals("year")) {
            arrayList = this.yearList;
        } else if (type.equals("department")) {
            arrayList = this.departmentList;
        }
        List list = arrayList;
        showOrHiddenArrow(true, arrowImg);
        new ChooseFilterPopuWindow(this, view, viewHieght, list, new ChooseFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.EnrollmentNumberAct.5
            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemDissmissLinsenter() {
                EnrollmentNumberAct.this.showOrHiddenArrow(false, arrowImg);
            }

            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemLinsenter(int choosePos, ForumFilterBean.FilterDataBean data) {
                String key = data.isSelected() ? data.getKey() : "";
                String str = type;
                str.hashCode();
                if (str.equals("year")) {
                    EnrollmentNumberAct enrollmentNumberAct = EnrollmentNumberAct.this;
                    enrollmentNumberAct.updateDataList(enrollmentNumberAct.yearList, data.getKey(), data.isSelected());
                    EnrollmentNumberAct.this.mYear = key;
                    EnrollmentNumberAct.this.showOrHiddenArrow(false, arrowImg);
                } else if (str.equals("department")) {
                    EnrollmentNumberAct enrollmentNumberAct2 = EnrollmentNumberAct.this;
                    enrollmentNumberAct2.updateDataList(enrollmentNumberAct2.departmentList, data.getKey(), data.isSelected());
                    EnrollmentNumberAct.this.mDepartment = key;
                    EnrollmentNumberAct.this.showOrHiddenArrow(false, arrowImg);
                }
                EnrollmentNumberAct.this.mRankAdp.getData().get(position).setSelected(data.isSelected());
                EnrollmentNumberAct.this.mRankAdp.notifyDataSetChanged();
                EnrollmentNumberAct.this.getDepartmentSchoolEnrollmentData();
            }
        }, true, type.equals("department"));
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
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        final View viewFindViewById = findViewById(R.id.tabbar);
        this.mSchoolHead = (CircleImageView) findViewById(R.id.school_head);
        this.mTvSchoolName = (TextView) findViewById(R.id.tv_school_name);
        this.mLyTag = (LinearLayout) findViewById(R.id.ly_tag);
        this.mTvSchoolAddress = (TextView) findViewById(R.id.tv_school_address);
        this.mTvSchoolCode = (TextView) findViewById(R.id.tv_school_code);
        this.mLyAddLeftView = (LinearLayout) findViewById(R.id.ly_add_left_view);
        this.mLyAddView = (LinearLayout) findViewById(R.id.ly_add_view);
        this.scoreLineScroll = (HorizontalScrollView) findViewById(R.id.score_line_scroll);
        this.mViewShadow = findViewById(R.id.line_shadow);
        this.mLyLine = (RelativeLayout) findViewById(R.id.ly_line);
        this.mLyAddLeftViewTwo = (LinearLayout) findViewById(R.id.ly_add_left_view_two);
        this.mLyAddViewTwo = (LinearLayout) findViewById(R.id.ly_add_view_two);
        this.mLyAddLeftViewThree = (LinearLayout) findViewById(R.id.ly_add_left_view_three);
        this.mLyAddViewThree = (LinearLayout) findViewById(R.id.ly_add_view_three);
        this.scoreLineScrollTwo = (HorizontalScrollView) findViewById(R.id.score_line_scroll_two);
        this.mViewShadowTwo = findViewById(R.id.line_shadow_two);
        this.mLyLineTwo = (RelativeLayout) findViewById(R.id.ly_line_two);
        this.mScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        this.mLyMore = (LinearLayout) findViewById(R.id.ly_more);
        this.mLyMoreTwo = (LinearLayout) findViewById(R.id.ly_more_two);
        this.mLyMoreThree = (LinearLayout) findViewById(R.id.ly_more_three);
        this.mLineOne = findViewById(R.id.line_one);
        this.mLineTwo = findViewById(R.id.line_two);
        this.mLineThree = findViewById(R.id.line_three);
        this.mTvMore = (TextView) findViewById(R.id.tv_more);
        this.mTvMoreTwo = (TextView) findViewById(R.id.tv_more_two);
        this.mTvMoreThree = (TextView) findViewById(R.id.tv_more_three);
        this.mRankRecycler = (RecyclerView) findViewById(R.id.rank_recycler);
        this.mSearchRecycler = (RecyclerView) findViewById(R.id.search_recycler);
        this.mLyNumberView = (RelativeLayout) findViewById(R.id.ly_number_view);
        this.mLyScoreView = (RelativeLayout) findViewById(R.id.ly_score_line);
        this.mLyOpenVipOne = findViewById(R.id.ly_open_vip);
        this.mLyOpenVipTwo = findViewById(R.id.ly_open_vip_two);
        this.mLyOpenVipThree = findViewById(R.id.ly_open_vip_three);
        this.mViewEmpty = findViewById(R.id.ly_empty_view);
        this.mViewEmptyTwo = findViewById(R.id.ly_empty_view_two);
        this.mViewEmptyThree = findViewById(R.id.ly_empty_view_three);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow_one);
        this.mImgArrowTwo = (ImageView) findViewById(R.id.img_arrow_two);
        this.mImgArrowThree = (ImageView) findViewById(R.id.img_arrow_three);
        textView.setText("招生人数");
        this.actionbar = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
        layoutParams.topMargin = this.actionbar;
        viewFindViewById.setLayoutParams(layoutParams);
        this.mRankRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        ChooseSchoolSearchAdp chooseSchoolSearchAdp = new ChooseSchoolSearchAdp();
        this.mRankAdp = chooseSchoolSearchAdp;
        this.mRankRecycler.setAdapter(chooseSchoolSearchAdp);
        this.mSearchRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        ChooseSchoolSearchAdp chooseSchoolSearchAdp2 = new ChooseSchoolSearchAdp();
        this.mSearchAdp = chooseSchoolSearchAdp2;
        this.mSearchRecycler.setAdapter(chooseSchoolSearchAdp2);
        ArrayList arrayList = new ArrayList();
        ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
        filterDataBean.setTitle("年份");
        filterDataBean.setType("year");
        arrayList.add(filterDataBean);
        ForumFilterBean.FilterDataBean filterDataBean2 = new ForumFilterBean.FilterDataBean();
        filterDataBean2.setTitle("院系");
        filterDataBean2.setType("department");
        arrayList.add(filterDataBean2);
        this.mRankAdp.setNewInstance(arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(arrayList);
        ForumFilterBean.FilterDataBean filterDataBean3 = new ForumFilterBean.FilterDataBean();
        filterDataBean3.setTitle("专业类别");
        filterDataBean3.setType("type");
        arrayList2.add(filterDataBean3);
        this.mSearchAdp.setNewInstance(arrayList2);
        this.viewHieght = ScreenUtil.getScreenHeight(this) - 400;
        this.mLyTag.removeAllViews();
        int pxByDp = ScreenUtil.getPxByDp((Context) this, 4);
        int pxByDp2 = ScreenUtil.getPxByDp((Context) this, 1);
        for (int i2 = 0; i2 < 4; i2++) {
            TextView textView2 = new TextView(this);
            textView2.setText("标签" + i2);
            textView2.setTextSize(10.0f);
            textView2.setTextColor(getColor(SkinManager.getCurrentSkinType(this) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color));
            textView2.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
            textView2.setBackgroundResource(R.drawable.shape_computer_time_bg);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams2.rightMargin = ScreenUtil.getPxByDp((Context) this, 8);
            textView2.setLayoutParams(layoutParams2);
            this.mLyTag.addView(textView2);
        }
        final int pxByDp3 = ScreenUtil.getPxByDp(this.mContext, 44) + this.actionbar;
        this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a1
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i3, int i4, int i5, int i6) {
                this.f11183a.lambda$init$0(pxByDp3, viewFindViewById, nestedScrollView, i3, i4, i5, i6);
            }
        });
        this.scoreLineScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i3, int i4, int i5, int i6) {
                this.f11216c.lambda$init$1(view, i3, i4, i5, i6);
            }
        });
        this.scoreLineScrollTwo.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.c1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i3, int i4, int i5, int i6) {
                this.f11227c.lambda$init$2(view, i3, i4, i5, i6);
            }
        });
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, this);
        if (TextUtils.isEmpty(strConfig) || !strConfig.equals("1")) {
            this.mLyOpenVipOne.setVisibility(0);
            this.mLyOpenVipTwo.setVisibility(0);
            this.mLyOpenVipThree.setVisibility(0);
            this.activityId = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_ACTIVITY_ID, this);
        } else {
            this.mLyOpenVipOne.setVisibility(8);
            this.mLyOpenVipTwo.setVisibility(8);
            this.mLyOpenVipThree.setVisibility(8);
        }
        getData();
        getDepartmentSchoolEnrollmentData();
        getSearchParamsData(2);
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
        setContentView(R.layout.layout_enrollment_number);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.z0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11476c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11256c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mLyMoreTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.g1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11293c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mLyMoreThree.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.h1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11302c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mRankAdp.setOnItemActionLisenter(new ChooseSchoolSearchAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.chooseSchool.EnrollmentNumberAct.4
            @Override // com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp.OnItemActionLisenter
            public void setItemClickAction(int pos, ForumFilterBean.FilterDataBean item, ImageView imgView) {
                int[] iArr = new int[2];
                EnrollmentNumberAct.this.mLyNumberView.getLocationOnScreen(iArr);
                EnrollmentNumberAct.this.viewHieght = (Build.VERSION.SDK_INT > 30 ? CommonUtil.getScreenHeight(EnrollmentNumberAct.this.mContext) + EnrollmentNumberAct.this.actionbar : CommonUtil.getScreenHeight(EnrollmentNumberAct.this.mContext)) - iArr[1];
                if (CommonUtil.isFastClick()) {
                    return;
                }
                EnrollmentNumberAct enrollmentNumberAct = EnrollmentNumberAct.this;
                enrollmentNumberAct.showSearchParams(pos, enrollmentNumberAct.mRankRecycler, EnrollmentNumberAct.this.viewHieght, item.getType(), imgView, false);
            }
        });
        this.mLyOpenVipOne.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.i1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11312c.lambda$setListenerForWidget$7(view);
            }
        });
        this.mLyOpenVipTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.j1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11326c.lambda$setListenerForWidget$8(view);
            }
        });
        this.mLyOpenVipThree.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.k1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11335c.lambda$setListenerForWidget$9(view);
            }
        });
    }
}
