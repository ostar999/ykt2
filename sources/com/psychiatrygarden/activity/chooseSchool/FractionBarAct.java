package com.psychiatrygarden.activity.chooseSchool;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.psychiatrygarden.bean.ChooseSchoolFilterBean;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.bean.SchoolScoreLineBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
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
public class FractionBarAct extends BaseActivity {
    private int actionbar;
    private String activityId;
    private String mDepartment;
    private ImageView mImgArrow;
    private ImageView mImgArrowTwo;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private View mLineOne;
    private View mLineTwo;
    private LinearLayout mLyAddLeftView;
    private LinearLayout mLyAddLeftViewTwo;
    private LinearLayout mLyAddView;
    private LinearLayout mLyAddViewTwo;
    private RelativeLayout mLyLine;
    private RelativeLayout mLyLineTwo;
    private LinearLayout mLyMore;
    private LinearLayout mLyMoreTwo;
    private View mLyOpenVipOne;
    private View mLyOpenVipTwo;
    private LinearLayout mLyTag;
    private RelativeLayout mLyView;
    private CircleImageView mSchoolHead;
    private String mSchoolId;
    private List<EnrollmentData> mScoreLineOneList;
    private List<EnrollmentData> mScoreLineTwoList;
    private NestedScrollView mScrollView;
    private ChooseSchoolSearchAdp mSearchAdp;
    private RecyclerView mSearchRecycler;
    private TextView mTvMore;
    private TextView mTvMoreTwo;
    private TextView mTvSchoolAddress;
    private TextView mTvSchoolCode;
    private TextView mTvSchoolName;
    private String mType;
    private View mViewEmpty;
    private View mViewEmptyTwo;
    private View mViewShadow;
    private View mViewShadowTwo;
    private String mYear;
    private HorizontalScrollView scoreLineScroll;
    private HorizontalScrollView scoreLineScrollTwo;
    private ChooseSchoolFilterBeanList searchParamsBean;
    private boolean isExpandOne = false;
    private boolean isExpandTwo = false;
    private int viewHieght = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public void getScoreLineData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", this.mSchoolId);
        ajaxParams.put("is_limit", "0");
        if (!TextUtils.isEmpty(this.mDepartment)) {
            ajaxParams.put("school_department_id", this.mDepartment);
        }
        if (!TextUtils.isEmpty(this.mYear)) {
            ajaxParams.put("year", this.mYear);
        }
        if (!TextUtils.isEmpty(this.mType)) {
            ajaxParams.put("major_type", this.mType);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getScoreLine, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.FractionBarAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    SchoolScoreLineBean schoolScoreLineBean = (SchoolScoreLineBean) new Gson().fromJson(s2, SchoolScoreLineBean.class);
                    if (!schoolScoreLineBean.getCode().equals("200") || schoolScoreLineBean.getData() == null) {
                        return;
                    }
                    GlideApp.with(FractionBarAct.this.mContext).load((Object) GlideUtils.generateUrl(schoolScoreLineBean.getData().getCover())).placeholder(R.mipmap.ic_avatar_def).into(FractionBarAct.this.mSchoolHead);
                    FractionBarAct.this.mTvSchoolName.setText(schoolScoreLineBean.getData().getTitle());
                    FractionBarAct.this.mLyTag.removeAllViews();
                    int pxByDp = ScreenUtil.getPxByDp((Context) FractionBarAct.this, 4);
                    int pxByDp2 = ScreenUtil.getPxByDp((Context) FractionBarAct.this, 1);
                    int i2 = 8;
                    if (schoolScoreLineBean.getData().getAttr() == null || schoolScoreLineBean.getData().getAttr().size() <= 0) {
                        FractionBarAct.this.mLyTag.setVisibility(8);
                    } else {
                        FractionBarAct.this.mLyTag.setVisibility(0);
                        for (int i3 = 0; i3 < schoolScoreLineBean.getData().getAttr().size(); i3++) {
                            TextView textView = new TextView(FractionBarAct.this);
                            textView.setTextSize(10.0f);
                            textView.setText(schoolScoreLineBean.getData().getAttr().get(i3));
                            textView.setTextColor(SkinManager.getCurrentSkinType(FractionBarAct.this) == 1 ? FractionBarAct.this.getColor(R.color.main_theme_color_night) : FractionBarAct.this.getColor(R.color.main_theme_color));
                            textView.setPadding(pxByDp, pxByDp2, pxByDp, pxByDp2);
                            textView.setBackgroundResource(R.drawable.shape_computer_time_bg);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                            layoutParams.rightMargin = ScreenUtil.getPxByDp((Context) FractionBarAct.this, 8);
                            textView.setLayoutParams(layoutParams);
                            FractionBarAct.this.mLyTag.addView(textView);
                        }
                    }
                    if (TextUtils.isEmpty(schoolScoreLineBean.getData().getCity_title())) {
                        FractionBarAct.this.mTvSchoolAddress.setVisibility(8);
                    } else {
                        FractionBarAct.this.mTvSchoolAddress.setVisibility(0);
                        FractionBarAct.this.mTvSchoolAddress.setText("地址：" + schoolScoreLineBean.getData().getCity_title());
                    }
                    if (TextUtils.isEmpty(schoolScoreLineBean.getData().getCode())) {
                        FractionBarAct.this.mTvSchoolCode.setVisibility(8);
                    } else {
                        FractionBarAct.this.mTvSchoolCode.setVisibility(0);
                        FractionBarAct.this.mTvSchoolCode.setText("代码：" + schoolScoreLineBean.getData().getCode());
                    }
                    FractionBarAct.this.mScoreLineOneList = schoolScoreLineBean.getData().getSchool_cutoff_score();
                    FractionBarAct.this.mViewEmpty.setVisibility(FractionBarAct.this.mScoreLineOneList.size() > 1 ? 8 : 0);
                    int size = FractionBarAct.this.mScoreLineOneList.size() > 5 ? 5 : FractionBarAct.this.mScoreLineOneList.size();
                    FractionBarAct.this.mLyMore.setVisibility(FractionBarAct.this.mScoreLineOneList.size() > 5 ? 0 : 8);
                    FractionBarAct.this.mLineOne.setVisibility(FractionBarAct.this.mScoreLineOneList.size() > 5 ? 8 : 0);
                    List listSubList = FractionBarAct.this.mScoreLineOneList.subList(0, size);
                    FractionBarAct.this.mLyAddLeftView.removeAllViews();
                    for (int i4 = 0; i4 < listSubList.size(); i4++) {
                        ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(FractionBarAct.this.mContext, true);
                        chooseSchoolFormLeftItemView.setData(i4, (EnrollmentData) listSubList.get(i4), false);
                        FractionBarAct.this.mLyAddLeftView.addView(chooseSchoolFormLeftItemView);
                    }
                    FractionBarAct.this.mLyAddView.removeAllViews();
                    for (int i5 = 0; i5 < listSubList.size(); i5++) {
                        ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(FractionBarAct.this.mContext, true);
                        chooseSchoolScoreLineItemView.setData(i5, (EnrollmentData) listSubList.get(i5), false, false);
                        FractionBarAct.this.mLyAddView.addView(chooseSchoolScoreLineItemView);
                    }
                    FractionBarAct.this.mScoreLineTwoList = schoolScoreLineBean.getData().getSchool_department_cutoff_score();
                    FractionBarAct.this.mViewEmptyTwo.setVisibility(FractionBarAct.this.mScoreLineTwoList.size() > 1 ? 8 : 0);
                    int size2 = FractionBarAct.this.mScoreLineTwoList.size() > 5 ? 5 : FractionBarAct.this.mScoreLineTwoList.size();
                    FractionBarAct.this.mLyMoreTwo.setVisibility(FractionBarAct.this.mScoreLineTwoList.size() > 5 ? 0 : 8);
                    View view = FractionBarAct.this.mLineTwo;
                    if (FractionBarAct.this.mScoreLineTwoList.size() <= 5) {
                        i2 = 0;
                    }
                    view.setVisibility(i2);
                    List listSubList2 = FractionBarAct.this.mScoreLineTwoList.subList(0, size2);
                    FractionBarAct.this.mLyAddLeftViewTwo.removeAllViews();
                    for (int i6 = 0; i6 < listSubList2.size(); i6++) {
                        ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView2 = new ChooseSchoolFormLeftItemView(FractionBarAct.this.mContext, false);
                        chooseSchoolFormLeftItemView2.setData(i6, (EnrollmentData) listSubList2.get(i6), true);
                        FractionBarAct.this.mLyAddLeftViewTwo.addView(chooseSchoolFormLeftItemView2);
                    }
                    FractionBarAct.this.mLyAddViewTwo.removeAllViews();
                    for (int i7 = 0; i7 < listSubList2.size(); i7++) {
                        ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView2 = new ChooseSchoolScoreLineItemView(FractionBarAct.this.mContext, false);
                        chooseSchoolScoreLineItemView2.setData(i7, (EnrollmentData) listSubList2.get(i7), false, true);
                        FractionBarAct.this.mLyAddViewTwo.addView(chooseSchoolScoreLineItemView2);
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
        ajaxParams.put("search_type", "1");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getRecruitInfoSearchParams, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.FractionBarAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        FractionBarAct.this.searchParamsBean = (ChooseSchoolFilterBeanList) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolFilterBeanList.class);
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
    public /* synthetic */ void lambda$openVip$12() {
        SharePreferencesUtils.writeStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, "1", this);
        this.mLyOpenVipOne.setVisibility(8);
        this.mLyOpenVipTwo.setVisibility(8);
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
            for (int childCount = this.mLyAddLeftView.getChildCount() - 1; childCount > 4; childCount--) {
                this.mLyAddLeftView.removeViewAt(childCount);
            }
            for (int childCount2 = this.mLyAddView.getChildCount() - 1; childCount2 > 4; childCount2--) {
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
            for (int childCount = this.mLyAddLeftViewTwo.getChildCount() - 1; childCount > 4; childCount--) {
                this.mLyAddLeftViewTwo.removeViewAt(childCount);
            }
            for (int childCount2 = this.mLyAddViewTwo.getChildCount() - 1; childCount2 > 4; childCount2--) {
                this.mLyAddViewTwo.removeViewAt(childCount2);
            }
            this.mTvMoreTwo.setText("展开更多");
        } else {
            int childCount3 = this.mLyAddLeftViewTwo.getChildCount() - 1;
            List<EnrollmentData> list2 = this.mScoreLineTwoList;
            List<EnrollmentData> listSubList = list2.subList(childCount3, list2.size());
            for (int i2 = 0; i2 < listSubList.size(); i2++) {
                ChooseSchoolFormLeftItemView chooseSchoolFormLeftItemView = new ChooseSchoolFormLeftItemView(this.mContext, false);
                chooseSchoolFormLeftItemView.setData(1, listSubList.get(i2), true);
                this.mLyAddLeftViewTwo.addView(chooseSchoolFormLeftItemView);
            }
            for (int i3 = 0; i3 < listSubList.size(); i3++) {
                ChooseSchoolScoreLineItemView chooseSchoolScoreLineItemView = new ChooseSchoolScoreLineItemView(this.mContext, false);
                chooseSchoolScoreLineItemView.setData(1, listSubList.get(i3), false, true);
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
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        openVip();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$8(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$9(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow2$10(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow2$11(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public static void newIntent(Context context, String schoolId) {
        Intent intent = new Intent(context, (Class<?>) FractionBarAct.class);
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
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.chooseSchool.y1
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f11469a.lambda$openVip$12();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.t1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    FractionBarAct.lambda$showOrHiddenArrow$8(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.u1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FractionBarAct.lambda$showOrHiddenArrow$9(arrowImg, valueAnimator);
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
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.o1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    FractionBarAct.lambda$showOrHiddenArrow2$10(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(270, 90);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.s1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FractionBarAct.lambda$showOrHiddenArrow2$11(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSearchParams(final int position, View view, final String type, final List<ChooseSchoolFilterBean> dataList, final ImageView arrowImg) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
            filterDataBean.setKey(dataList.get(i2).getId());
            filterDataBean.setTitle(dataList.get(i2).getTitle());
            filterDataBean.setSelected(dataList.get(i2).getSelected());
            arrayList.add(filterDataBean);
        }
        showOrHiddenArrow(true, arrowImg);
        new ChooseFilterPopuWindow(this, view, this.viewHieght, arrayList, new ChooseFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.FractionBarAct.4
            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemDissmissLinsenter() {
                FractionBarAct.this.showOrHiddenArrow(false, arrowImg);
            }

            @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
            public void mItemLinsenter(int choosePos, ForumFilterBean.FilterDataBean data) {
                String key;
                int i3 = 0;
                while (true) {
                    if (i3 >= dataList.size()) {
                        break;
                    }
                    if (((ChooseSchoolFilterBean) dataList.get(i3)).getId().equals(data.getKey())) {
                        ((ChooseSchoolFilterBean) dataList.get(i3)).setSelected(data.isSelected());
                        break;
                    }
                    i3++;
                }
                key = data.isSelected() ? data.getKey() : "";
                String str = type;
                str.hashCode();
                switch (str) {
                    case "year":
                        FractionBarAct.this.mYear = key;
                        break;
                    case "category":
                        FractionBarAct.this.mType = key;
                        break;
                    case "department":
                        FractionBarAct.this.mDepartment = key;
                        break;
                }
                FractionBarAct.this.showOrHiddenArrow(false, arrowImg);
                FractionBarAct.this.mSearchAdp.getData().get(position).setSelected(data.isSelected());
                FractionBarAct.this.mSearchAdp.notifyDataSetChanged();
                FractionBarAct.this.getScoreLineData();
            }
        }, true, type.equals("department"));
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
        this.scoreLineScrollTwo = (HorizontalScrollView) findViewById(R.id.score_line_scroll_two);
        this.mViewShadowTwo = findViewById(R.id.line_shadow_two);
        this.mLyLineTwo = (RelativeLayout) findViewById(R.id.ly_line_two);
        this.mScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        this.mLyMore = (LinearLayout) findViewById(R.id.ly_more);
        this.mLyMoreTwo = (LinearLayout) findViewById(R.id.ly_more_two);
        this.mLineOne = findViewById(R.id.line_one);
        this.mLineTwo = findViewById(R.id.line_two);
        this.mTvMore = (TextView) findViewById(R.id.tv_more);
        this.mTvMoreTwo = (TextView) findViewById(R.id.tv_more_two);
        this.mSearchRecycler = (RecyclerView) findViewById(R.id.search_recycler);
        this.mLyView = (RelativeLayout) findViewById(R.id.ly_content_view);
        this.mLyOpenVipOne = findViewById(R.id.ly_open_vip);
        this.mLyOpenVipTwo = findViewById(R.id.ly_open_vip_two);
        this.mViewEmpty = findViewById(R.id.ly_empty_view);
        this.mViewEmptyTwo = findViewById(R.id.ly_empty_view_two);
        this.mImgArrow = (ImageView) findViewById(R.id.img_arrow_one);
        this.mImgArrowTwo = (ImageView) findViewById(R.id.img_arrow_two);
        textView.setText("分数线");
        this.actionbar = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
        layoutParams.topMargin = this.actionbar;
        viewFindViewById.setLayoutParams(layoutParams);
        this.mSearchRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        ChooseSchoolSearchAdp chooseSchoolSearchAdp = new ChooseSchoolSearchAdp();
        this.mSearchAdp = chooseSchoolSearchAdp;
        this.mSearchRecycler.setAdapter(chooseSchoolSearchAdp);
        ArrayList arrayList = new ArrayList();
        ForumFilterBean.FilterDataBean filterDataBean = new ForumFilterBean.FilterDataBean();
        filterDataBean.setTitle("年份");
        filterDataBean.setType("year");
        arrayList.add(filterDataBean);
        ForumFilterBean.FilterDataBean filterDataBean2 = new ForumFilterBean.FilterDataBean();
        filterDataBean2.setTitle("院系");
        filterDataBean2.setType("department");
        arrayList.add(filterDataBean2);
        ForumFilterBean.FilterDataBean filterDataBean3 = new ForumFilterBean.FilterDataBean();
        filterDataBean3.setTitle("专业类别");
        filterDataBean3.setType("type");
        arrayList.add(filterDataBean3);
        this.mSearchAdp.setNewInstance(arrayList);
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_HAVE_PERMISSION, this);
        if (TextUtils.isEmpty(strConfig) || !strConfig.equals("1")) {
            this.mLyOpenVipOne.setVisibility(0);
            this.mLyOpenVipTwo.setVisibility(0);
            this.activityId = SharePreferencesUtils.readStrConfig(CommonParameter.CHOOSE_SCHOOL_ACTIVITY_ID, this);
        } else {
            this.mLyOpenVipOne.setVisibility(8);
            this.mLyOpenVipTwo.setVisibility(8);
        }
        final int pxByDp = ScreenUtil.getPxByDp(this.mContext, 44) + this.actionbar;
        this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.v1
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
                this.f11432a.lambda$init$0(pxByDp, viewFindViewById, nestedScrollView, i2, i3, i4, i5);
            }
        });
        this.scoreLineScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.w1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                this.f11441c.lambda$init$1(view, i2, i3, i4, i5);
            }
        });
        this.scoreLineScrollTwo.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.chooseSchool.x1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                this.f11457c.lambda$init$2(view, i2, i3, i4, i5);
            }
        });
        getScoreLineData();
        getSearchParamsData();
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
        setContentView(R.layout.layout_fraction_bar);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.z1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11477c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11186c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mLyMoreTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.p1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11378c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mSearchAdp.setOnItemActionLisenter(new ChooseSchoolSearchAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.chooseSchool.FractionBarAct.3
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:11:0x004e  */
            @Override // com.psychiatrygarden.activity.chooseSchool.adapter.ChooseSchoolSearchAdp.OnItemActionLisenter
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void setItemClickAction(int r9, com.psychiatrygarden.bean.ForumFilterBean.FilterDataBean r10, android.widget.ImageView r11) {
                /*
                    r8 = this;
                    r0 = 2
                    int[] r1 = new int[r0]
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r2 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    android.widget.RelativeLayout r2 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$1800(r2)
                    r2.getLocationOnScreen(r1)
                    r2 = 1
                    r1 = r1[r2]
                    int r3 = android.os.Build.VERSION.SDK_INT
                    r4 = 30
                    if (r3 <= r4) goto L25
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r3 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    android.content.Context r3 = r3.mContext
                    int r3 = com.psychiatrygarden.utils.CommonUtil.getScreenHeight(r3)
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r4 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    int r4 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$1900(r4)
                    int r3 = r3 + r4
                    goto L2d
                L25:
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r3 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    android.content.Context r3 = r3.mContext
                    int r3 = com.psychiatrygarden.utils.CommonUtil.getScreenHeight(r3)
                L2d:
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r4 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    int r3 = r3 - r1
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$2002(r4, r3)
                    boolean r1 = com.psychiatrygarden.utils.CommonUtil.isFastClick()
                    if (r1 == 0) goto L3a
                    return
                L3a:
                    java.util.ArrayList r1 = new java.util.ArrayList
                    r1.<init>()
                    java.lang.String r3 = r10.getType()
                    r3.hashCode()
                    int r4 = r3.hashCode()
                    r5 = -1
                    switch(r4) {
                        case 3575610: goto L64;
                        case 3704893: goto L59;
                        case 848184146: goto L50;
                        default: goto L4e;
                    }
                L4e:
                    r0 = r5
                    goto L6e
                L50:
                    java.lang.String r2 = "department"
                    boolean r2 = r3.equals(r2)
                    if (r2 != 0) goto L6e
                    goto L4e
                L59:
                    java.lang.String r0 = "year"
                    boolean r0 = r3.equals(r0)
                    if (r0 != 0) goto L62
                    goto L4e
                L62:
                    r0 = r2
                    goto L6e
                L64:
                    java.lang.String r0 = "type"
                    boolean r0 = r3.equals(r0)
                    if (r0 != 0) goto L6d
                    goto L4e
                L6d:
                    r0 = 0
                L6e:
                    switch(r0) {
                        case 0: goto L89;
                        case 1: goto L7e;
                        case 2: goto L73;
                        default: goto L71;
                    }
                L71:
                    r6 = r1
                    goto L94
                L73:
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r0 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    com.psychiatrygarden.bean.ChooseSchoolFilterBeanList r0 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$1700(r0)
                    java.util.List r1 = r0.getSchool_department()
                    goto L71
                L7e:
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r0 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    com.psychiatrygarden.bean.ChooseSchoolFilterBeanList r0 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$1700(r0)
                    java.util.List r1 = r0.getYear()
                    goto L71
                L89:
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r0 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    com.psychiatrygarden.bean.ChooseSchoolFilterBeanList r0 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$1700(r0)
                    java.util.List r1 = r0.getMajor_type()
                    goto L71
                L94:
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct r2 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.this
                    androidx.recyclerview.widget.RecyclerView r4 = com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$2100(r2)
                    java.lang.String r5 = r10.getType()
                    r3 = r9
                    r7 = r11
                    com.psychiatrygarden.activity.chooseSchool.FractionBarAct.access$2200(r2, r3, r4, r5, r6, r7)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.chooseSchool.FractionBarAct.AnonymousClass3.setItemClickAction(int, com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean, android.widget.ImageView):void");
            }
        });
        this.mLyOpenVipOne.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11386c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mLyOpenVipTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.r1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11394c.lambda$setListenerForWidget$7(view);
            }
        });
    }
}
