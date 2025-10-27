package com.psychiatrygarden.activity.chooseSchool;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.activity.chooseSchool.bean.SchoolByMajorBean;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow;
import com.psychiatrygarden.adapter.TargetSchoolListAdapter;
import com.psychiatrygarden.bean.ChooseSchoolFilterBean;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.bean.OnlineServiceBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SchoolByMajorActivity extends BaseActivity implements View.OnClickListener {
    private AppBarLayout appbarlayout;
    private OnlineServiceBean feedback_cs;
    private int height;
    private ImageView imgCollection;
    private String isFollow;
    private ImageView ivAcionbarBack;
    private ImageView ivAreaArrow;
    private ImageView ivSchoolAttributeArrow;
    private ImageView ivSchoolTypeArrow;
    private LinearLayout layoutArea;
    private LinearLayout layoutFilter;
    private LinearLayout layoutSchoolAttribute;
    private LinearLayout layoutSchoolType;
    private LinearLayout llEmpty;
    private LinearLayout llFeedback;
    private LinearLayout lyCollection;
    private View mImgBackground;
    private View mImgBg;
    private TextView mNavTitle;
    private RelativeLayout mTabbar;
    private TextView majorCodeTv;
    private TextView majorTitleTv;
    private TextView majorTypeTv;
    private String major_id;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rvSchoolList;
    private TargetSchoolListAdapter schoolListAdapter;
    private int statusBarHeight;
    private TextView tvArea;
    private TextView tvSchoolAttribute;
    private TextView tvSchoolType;
    private List<FollowSchoolBean.DataBean> schoolList = new ArrayList();
    private List<ForumFilterBean.FilterDataBean> mListArea = new ArrayList();
    private List<ChooseSchoolFilterBean> mListFilterArea = new ArrayList();
    private List<ChooseSchoolFilterBean> mListFilterAttr = new ArrayList();
    private List<ChooseSchoolFilterBean> mListFilterCategory = new ArrayList();
    private List<String> mListFilterAttrSelectId = new ArrayList();
    private List<String> mListFilterCategorySelectId = new ArrayList();
    private List<String> mListFilterAreaSelectId = new ArrayList();
    private String selectedAttribute = "";
    private String selectedCategory = "";
    private int page = 1;
    private int pageSize = 20;
    private String selectedArea = "";

    public static /* synthetic */ int access$1210(SchoolByMajorActivity schoolByMajorActivity) {
        int i2 = schoolByMajorActivity.page;
        schoolByMajorActivity.page = i2 - 1;
        return i2;
    }

    private void addViewCount() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "2");
        ajaxParams.put("id", this.major_id);
        YJYHttpUtils.post(this, NetworkRequestsURL.incrViewCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity.6
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
            }
        });
    }

    private void followOrCancelSchool(final String mIsFollow) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("target_id", this.major_id);
        String str = NetworkRequestsURL.followSchool;
        if (mIsFollow.equals("1")) {
            str = NetworkRequestsURL.cancelFollowSchool;
        }
        ajaxParams.put("type", "2");
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        if (mIsFollow.equals("0")) {
                            SchoolByMajorActivity.this.isFollow = "1";
                            ToastUtil.shortToast(SchoolByMajorActivity.this, "已关注");
                            SchoolByMajorActivity.this.imgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolByMajorActivity.this.mContext) == 1 ? R.mipmap.ic_choose_school_had_collection_night : R.mipmap.ic_choose_school_had_collection);
                        } else {
                            SchoolByMajorActivity.this.isFollow = "0";
                            ToastUtil.shortToast(SchoolByMajorActivity.this, "已取消");
                            SchoolByMajorActivity.this.imgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolByMajorActivity.this.mContext) == 1 ? R.mipmap.ic_choose_school_collection_night : R.mipmap.ic_choose_school_collection);
                        }
                        AliYunLogUtil.getInstance().addLog("0".equals(mIsFollow) ? AliyunEvent.FollowedMajor : AliyunEvent.CancelMajor, SchoolByMajorActivity.this.major_id, SchoolByMajorActivity.this.majorTitleTv.getText().toString());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getFilterData() {
        YJYHttpUtils.get(this, NetworkRequestsURL.chooseSchoolFilterData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity.7
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
                super.onSuccess((AnonymousClass7) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ChooseSchoolFilterBeanList chooseSchoolFilterBeanList = (ChooseSchoolFilterBeanList) new Gson().fromJson(jSONObject.optString("data"), ChooseSchoolFilterBeanList.class);
                        if (chooseSchoolFilterBeanList.getArea() != null) {
                            SchoolByMajorActivity.this.mListFilterArea.addAll(chooseSchoolFilterBeanList.getArea());
                        }
                        if (chooseSchoolFilterBeanList.getAttr() != null) {
                            SchoolByMajorActivity.this.mListFilterAttr.addAll(chooseSchoolFilterBeanList.getAttr());
                        }
                        if (chooseSchoolFilterBeanList.getCategory() != null) {
                            SchoolByMajorActivity.this.mListFilterCategory.addAll(chooseSchoolFilterBeanList.getCategory());
                        }
                    }
                } catch (Exception e2) {
                    Log.e(SchoolByMajorActivity.this.TAG, "onSuccess: " + e2.getMessage());
                }
            }
        });
    }

    private void initRefreshLayout() {
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.z3
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11478c.lambda$initRefreshLayout$4(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.a4
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11187c.lambda$initRefreshLayout$5(refreshLayout);
            }
        });
    }

    private void initViews() {
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        this.rvSchoolList = (RecyclerView) findViewById(R.id.rvSchoolList);
        this.mTabbar = (RelativeLayout) findViewById(R.id.tabbar);
        this.layoutArea = (LinearLayout) findViewById(R.id.layoutArea);
        this.layoutFilter = (LinearLayout) findViewById(R.id.layoutFilter);
        this.majorTitleTv = (TextView) findViewById(R.id.major_title_tv);
        this.majorTypeTv = (TextView) findViewById(R.id.major_type_tv);
        this.majorCodeTv = (TextView) findViewById(R.id.major_code_tv);
        this.imgCollection = (ImageView) findViewById(R.id.img_collection);
        this.lyCollection = (LinearLayout) findViewById(R.id.ly_collection);
        this.ivAcionbarBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.llFeedback = (LinearLayout) findViewById(R.id.ll_feedback);
        this.ivAreaArrow = (ImageView) findViewById(R.id.ivAreaArrow);
        this.tvArea = (TextView) findViewById(R.id.tvArea);
        this.tvSchoolAttribute = (TextView) findViewById(R.id.tvSchoolAttribute);
        this.ivSchoolAttributeArrow = (ImageView) findViewById(R.id.ivSchoolAttributeArrow);
        this.layoutSchoolType = (LinearLayout) findViewById(R.id.layoutSchoolType);
        this.layoutSchoolAttribute = (LinearLayout) findViewById(R.id.layoutSchoolAttribute);
        this.tvSchoolType = (TextView) findViewById(R.id.tvSchoolType);
        this.ivSchoolTypeArrow = (ImageView) findViewById(R.id.ivSchoolTypeArrow);
        this.llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbar);
        this.mNavTitle = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mImgBg = findViewById(R.id.img_bg);
        this.mImgBackground = findViewById(R.id.img_background);
        this.llFeedback.setOnClickListener(this);
        this.layoutArea.setOnClickListener(this);
        this.lyCollection.setOnClickListener(this);
        this.ivAcionbarBack.setOnClickListener(this);
        this.layoutSchoolAttribute.setOnClickListener(this);
        this.layoutSchoolType.setOnClickListener(this);
        this.statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mTabbar.getLayoutParams();
        layoutParams.topMargin = this.statusBarHeight;
        this.mTabbar.setLayoutParams(layoutParams);
        final int iDip2px = UIUtil.dip2px(this.mContext, 44.0d);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.e4
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                this.f11249a.lambda$initViews$1(iDip2px, appBarLayout, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, FollowSchoolBean.DataBean dataBean) {
        SchoolDetailsAct.newIntent(this.mContext, dataBean.getSchool_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$4(RefreshLayout refreshLayout) {
        this.page = 1;
        loadSchoolList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$5(RefreshLayout refreshLayout) {
        this.page++;
        loadSchoolList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(int i2, AppBarLayout appBarLayout, int i3) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (Math.abs(i3) >= i2) {
            if (this.mImgBg.getVisibility() == 0) {
                this.mImgBg.setVisibility(8);
                this.mImgBackground.setVisibility(0);
                this.mNavTitle.setVisibility(0);
                this.layoutFilter.setBackgroundColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? getColor(R.color.new_bg_one_color_night) : getColor(R.color.new_bg_one_color));
                this.mTabbar.setBackgroundColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? getColor(R.color.new_bg_one_color_night) : getColor(R.color.new_bg_one_color));
                StatusBarCompat.setLightStatusBar(this, true);
                return;
            }
            return;
        }
        this.mNavTitle.setVisibility(8);
        if (this.mImgBg.getVisibility() == 8) {
            this.mNavTitle.setVisibility(8);
            this.mImgBackground.setVisibility(8);
            this.mTabbar.setBackgroundColor(0);
            this.mImgBg.setVisibility(0);
            this.layoutFilter.setBackgroundColor(getColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.color.new_bg_two_color_night_new : R.color.new_bg_two_color_new));
            StatusBarCompat.setLightStatusBar(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$2(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$3(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    private void loadSchoolList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", this.pageSize + "");
        ajaxParams.put("major_id", this.major_id);
        if (this.mListFilterAreaSelectId.isEmpty()) {
            this.selectedArea = "";
        } else {
            this.selectedArea = com.psychiatrygarden.activity.q2.a(",", this.mListFilterAreaSelectId);
        }
        if (!this.selectedArea.isEmpty()) {
            ajaxParams.put("area_id", this.selectedArea);
        }
        if (this.mListFilterAttrSelectId.isEmpty()) {
            this.selectedAttribute = "";
        } else {
            this.selectedAttribute = com.psychiatrygarden.activity.q2.a(",", this.mListFilterAttrSelectId);
        }
        if (!this.selectedAttribute.isEmpty()) {
            ajaxParams.put("attr", this.selectedAttribute);
        }
        if (this.mListFilterCategorySelectId.isEmpty()) {
            this.selectedCategory = "";
        } else {
            this.selectedCategory = com.psychiatrygarden.activity.q2.a(",", this.mListFilterCategorySelectId);
        }
        if (!this.selectedCategory.isEmpty()) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, this.selectedCategory);
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.getSchoolListForMajor, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SchoolByMajorActivity.this.hideProgressDialog();
                SchoolByMajorActivity.this.refreshLayout.finishRefresh();
                NewToast.showShort(SchoolByMajorActivity.this, "网络连接失败", 0).show();
                if (SchoolByMajorActivity.this.page == 1) {
                    SchoolByMajorActivity.this.showEmptyView();
                } else {
                    SchoolByMajorActivity.access$1210(SchoolByMajorActivity.this);
                    SchoolByMajorActivity.this.refreshLayout.finishLoadMore(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                if (SchoolByMajorActivity.this.page == 1) {
                    SchoolByMajorActivity.this.showProgressDialog();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                SchoolByMajorActivity.this.hideProgressDialog();
                SchoolByMajorActivity.this.refreshLayout.finishRefresh();
                try {
                    SchoolByMajorBean schoolByMajorBean = (SchoolByMajorBean) new Gson().fromJson(s2, SchoolByMajorBean.class);
                    SchoolByMajorActivity.this.feedback_cs = schoolByMajorBean.getData().getFeedback_cs();
                    if (!"200".equals(schoolByMajorBean.getCode())) {
                        NewToast.showShort(SchoolByMajorActivity.this, schoolByMajorBean.getMessage(), 0).show();
                        if (SchoolByMajorActivity.this.page == 1) {
                            SchoolByMajorActivity.this.showEmptyView();
                            return;
                        } else {
                            SchoolByMajorActivity.access$1210(SchoolByMajorActivity.this);
                            SchoolByMajorActivity.this.refreshLayout.finishLoadMore(false);
                            return;
                        }
                    }
                    if (TextUtils.isEmpty(schoolByMajorBean.getData().getMajor_type())) {
                        SchoolByMajorActivity.this.majorTypeTv.setVisibility(8);
                    } else {
                        SchoolByMajorActivity.this.majorTypeTv.setText(schoolByMajorBean.getData().getMajor_type());
                    }
                    if (TextUtils.isEmpty(schoolByMajorBean.getData().getMajor_code())) {
                        SchoolByMajorActivity.this.majorCodeTv.setVisibility(8);
                        SchoolByMajorActivity.this.majorTitleTv.setText(schoolByMajorBean.getData().getMajor_title());
                        SchoolByMajorActivity.this.mNavTitle.setText(schoolByMajorBean.getData().getMajor_title());
                    } else {
                        SchoolByMajorActivity.this.majorCodeTv.setText(schoolByMajorBean.getData().getMajor_code());
                        SchoolByMajorActivity.this.majorTitleTv.setText(schoolByMajorBean.getData().getMajor_code() + "-" + schoolByMajorBean.getData().getMajor_title());
                        SchoolByMajorActivity.this.mNavTitle.setText(schoolByMajorBean.getData().getMajor_code() + "-" + schoolByMajorBean.getData().getMajor_title());
                    }
                    SchoolByMajorActivity.this.isFollow = schoolByMajorBean.getData().getIs_follow();
                    if (SchoolByMajorActivity.this.isFollow.equals("1")) {
                        SchoolByMajorActivity.this.imgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolByMajorActivity.this.mContext) == 1 ? R.mipmap.ic_choose_school_had_collection_night : R.mipmap.ic_choose_school_had_collection);
                    } else {
                        SchoolByMajorActivity.this.imgCollection.setImageResource(SkinManager.getCurrentSkinType(SchoolByMajorActivity.this.mContext) == 1 ? R.mipmap.ic_choose_school_collection_night : R.mipmap.ic_choose_school_collection);
                    }
                    SchoolByMajorActivity.this.updateUI(schoolByMajorBean);
                    AliYunLogUtil.getInstance().addLog(AliyunEvent.PreviewMajor, SchoolByMajorActivity.this.major_id, schoolByMajorBean.getData().getMajor_title());
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SchoolByMajorActivity.this, "数据解析出错", 0).show();
                    if (SchoolByMajorActivity.this.page == 1) {
                        SchoolByMajorActivity.this.showEmptyView();
                    } else {
                        SchoolByMajorActivity.access$1210(SchoolByMajorActivity.this);
                        SchoolByMajorActivity.this.refreshLayout.finishLoadMore(false);
                    }
                }
            }
        });
    }

    public static Intent newIntent(Context context, String major_id) {
        Intent intent = new Intent(context, (Class<?>) SchoolByMajorActivity.class);
        intent.putExtra("major_id", major_id);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListDataChanged(int index) {
        int i2 = R.drawable.icon_arrow_first_txt_color_bottom;
        if (index == 0) {
            this.tvArea.setTextColor(!this.mListFilterAreaSelectId.isEmpty() ? SkinManager.getThemeColor(this, R.attr.first_txt_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            this.tvArea.setTypeface(this.mListFilterAreaSelectId.isEmpty() ? Typeface.DEFAULT : Typeface.DEFAULT_BOLD);
            this.ivAreaArrow.setImageResource(!this.mListFilterAreaSelectId.isEmpty() ? R.drawable.icon_arrow_first_txt_color_bottom : R.drawable.icon_arrow_second_txt_color_bottom);
        }
        if (index == 1) {
            this.tvSchoolAttribute.setTextColor(!this.mListFilterAttrSelectId.isEmpty() ? SkinManager.getThemeColor(this, R.attr.first_txt_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            this.tvSchoolAttribute.setTypeface(this.mListFilterAttrSelectId.isEmpty() ? Typeface.DEFAULT : Typeface.DEFAULT_BOLD);
            this.ivSchoolAttributeArrow.setImageResource(!this.mListFilterAttrSelectId.isEmpty() ? R.drawable.icon_arrow_first_txt_color_bottom : R.drawable.icon_arrow_second_txt_color_bottom);
        }
        if (index == 2) {
            this.tvSchoolType.setTextColor(!this.mListFilterCategorySelectId.isEmpty() ? SkinManager.getThemeColor(this, R.attr.first_txt_color) : SkinManager.getThemeColor(this, R.attr.second_txt_color));
            this.tvSchoolType.setTypeface(this.mListFilterCategorySelectId.isEmpty() ? Typeface.DEFAULT : Typeface.DEFAULT_BOLD);
            ImageView imageView = this.ivSchoolTypeArrow;
            if (this.mListFilterCategorySelectId.isEmpty()) {
                i2 = R.drawable.icon_arrow_second_txt_color_bottom;
            }
            imageView.setImageResource(i2);
        }
        loadSchoolList();
    }

    private void showAttrChooseDialog(int allViewHeight) {
        for (int i2 = 0; i2 < this.mListFilterAttr.size(); i2++) {
            this.mListFilterAttr.get(i2).setSelected(this.mListFilterAttrSelectId.contains(this.mListFilterAttr.get(i2).getId()));
        }
        showOrHiddenArrow(true, this.ivSchoolAttributeArrow);
        new ChooseAreaFilterPopUpWindow(this, this.layoutArea, allViewHeight, this.mListFilterAttr, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity.2
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                SchoolByMajorActivity schoolByMajorActivity = SchoolByMajorActivity.this;
                schoolByMajorActivity.showOrHiddenArrow(false, schoolByMajorActivity.ivSchoolAttributeArrow);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NonNull List<ChooseSchoolFilterBean> mList) {
                SchoolByMajorActivity schoolByMajorActivity = SchoolByMajorActivity.this;
                schoolByMajorActivity.showOrHiddenArrow(false, schoolByMajorActivity.ivSchoolAttributeArrow);
                SchoolByMajorActivity.this.mListFilterAttrSelectId.clear();
                for (int i3 = 0; i3 < mList.size(); i3++) {
                    if (mList.get(i3).getSelected()) {
                        SchoolByMajorActivity.this.mListFilterAttrSelectId.add(mList.get(i3).getId());
                    }
                }
                SchoolByMajorActivity.this.notifyListDataChanged(1);
            }
        });
    }

    private void showCategoryChooseDialog(int allViewHeight) {
        for (int i2 = 0; i2 < this.mListFilterCategory.size(); i2++) {
            this.mListFilterCategory.get(i2).setSelected(this.mListFilterCategorySelectId.contains(this.mListFilterCategory.get(i2).getId()));
        }
        showOrHiddenArrow(true, this.ivSchoolTypeArrow);
        new ChooseAreaFilterPopUpWindow(this, this.layoutSchoolType, allViewHeight, this.mListFilterCategory, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity.3
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                SchoolByMajorActivity schoolByMajorActivity = SchoolByMajorActivity.this;
                schoolByMajorActivity.showOrHiddenArrow(false, schoolByMajorActivity.ivSchoolTypeArrow);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NonNull List<ChooseSchoolFilterBean> mList) {
                SchoolByMajorActivity schoolByMajorActivity = SchoolByMajorActivity.this;
                schoolByMajorActivity.showOrHiddenArrow(false, schoolByMajorActivity.ivSchoolTypeArrow);
                SchoolByMajorActivity.this.mListFilterCategorySelectId.clear();
                for (int i3 = 0; i3 < mList.size(); i3++) {
                    if (mList.get(i3).getSelected()) {
                        SchoolByMajorActivity.this.mListFilterCategorySelectId.add(mList.get(i3).getId());
                    }
                }
                SchoolByMajorActivity.this.notifyListDataChanged(2);
            }
        });
    }

    private void showDepartmentDialog(int allViewHeight) {
        for (int i2 = 0; i2 < this.mListFilterArea.size(); i2++) {
            this.mListFilterArea.get(i2).setSelected(this.mListFilterAreaSelectId.contains(this.mListFilterArea.get(i2).getId()));
        }
        showOrHiddenArrow(true, this.ivAreaArrow);
        new ChooseAreaFilterPopUpWindow(this, this.layoutArea, allViewHeight, this.mListFilterArea, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolByMajorActivity.1
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                SchoolByMajorActivity schoolByMajorActivity = SchoolByMajorActivity.this;
                schoolByMajorActivity.showOrHiddenArrow(false, schoolByMajorActivity.ivAreaArrow);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NonNull List<ChooseSchoolFilterBean> mList) {
                SchoolByMajorActivity schoolByMajorActivity = SchoolByMajorActivity.this;
                schoolByMajorActivity.showOrHiddenArrow(false, schoolByMajorActivity.ivAreaArrow);
                SchoolByMajorActivity.this.mListFilterAreaSelectId.clear();
                for (int i3 = 0; i3 < mList.size(); i3++) {
                    if (mList.get(i3).getSelected()) {
                        SchoolByMajorActivity.this.mListFilterAreaSelectId.add(mList.get(i3).getId());
                    }
                }
                SchoolByMajorActivity.this.notifyListDataChanged(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView() {
        this.llEmpty.setVisibility(0);
        this.refreshLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.c4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SchoolByMajorActivity.lambda$showOrHiddenArrow$2(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.d4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SchoolByMajorActivity.lambda$showOrHiddenArrow$3(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(SchoolByMajorBean detailBean) {
        if (detailBean.getData().getList() == null || detailBean.getData().getList().isEmpty()) {
            if (this.page != 1) {
                this.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                showEmptyView();
                this.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
        }
        List<FollowSchoolBean.DataBean> list = detailBean.getData().getList();
        if (this.page != 1) {
            this.schoolList.addAll(list);
            this.schoolListAdapter.notifyDataSetChanged();
            if (list.size() < this.pageSize) {
                this.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                this.refreshLayout.finishLoadMore();
                return;
            }
        }
        this.schoolList.clear();
        this.schoolList.addAll(list);
        this.schoolListAdapter.notifyDataSetChanged();
        this.llEmpty.setVisibility(8);
        this.refreshLayout.setVisibility(0);
        if (list.size() < this.pageSize) {
            this.refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            this.refreshLayout.finishLoadMore();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        this.major_id = getIntent().getStringExtra("major_id");
        initViews();
        loadSchoolList();
        initRefreshLayout();
        getFilterData();
        addViewCount();
        this.schoolListAdapter = new TargetSchoolListAdapter(this, this.schoolList);
        this.rvSchoolList.setLayoutManager(new LinearLayoutManager(this));
        this.rvSchoolList.setAdapter(this.schoolListAdapter);
        this.schoolListAdapter.setOnItemClickListener(new TargetSchoolListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.b4
            @Override // com.psychiatrygarden.adapter.TargetSchoolListAdapter.OnItemClickListener
            public final void onItemClick(int i2, FollowSchoolBean.DataBean dataBean) {
                this.f11219a.lambda$init$0(i2, dataBean);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int[] iArr = new int[2];
        this.refreshLayout.getLocationOnScreen(iArr);
        int i2 = iArr[1];
        int screenHeight = (Build.VERSION.SDK_INT <= 30 || ScreenUtil.isTablet(this)) ? CommonUtil.getScreenHeight(this.mContext) : CommonUtil.getScreenHeight(this.mContext) + this.statusBarHeight;
        switch (v2.getId()) {
            case R.id.iv_actionbar_back /* 2131363947 */:
                finish();
                break;
            case R.id.layoutArea /* 2131364377 */:
                showDepartmentDialog(screenHeight - i2);
                break;
            case R.id.layoutSchoolAttribute /* 2131364443 */:
                showAttrChooseDialog(screenHeight - i2);
                break;
            case R.id.layoutSchoolType /* 2131364447 */:
                showCategoryChooseDialog(screenHeight - i2);
                break;
            case R.id.ll_feedback /* 2131364795 */:
                if (this.feedback_cs != null) {
                    AliYunLogUtil.getInstance().addLog(AliyunEvent.EnterpriseWeChatFeedback);
                    CommonUtil.onlineService(this, this.feedback_cs);
                    break;
                }
                break;
            case R.id.ly_collection /* 2131365115 */:
                followOrCancelSchool(this.isFollow);
                break;
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

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        this.page = 1;
        loadSchoolList();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.school_by_major);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
