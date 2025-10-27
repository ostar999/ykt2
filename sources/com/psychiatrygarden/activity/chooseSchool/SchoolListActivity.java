package com.psychiatrygarden.activity.chooseSchool;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow;
import com.psychiatrygarden.adapter.SchoolListMyFollowAdapter;
import com.psychiatrygarden.adapter.TargetSchoolListAdapter;
import com.psychiatrygarden.bean.ChooseSchoolFilterBean;
import com.psychiatrygarden.bean.ChooseSchoolFilterBeanList;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SchoolListActivity extends BaseActivity implements View.OnClickListener {
    private CustomEmptyView emptyView;
    private ImageView ivActionbarBack;
    private ImageView ivAreaArrow;
    private ImageView ivSchoolAttributeArrow;
    private LinearLayout layoutArea;
    private LinearLayout layoutFilter;
    private LinearLayout layoutSchoolAttribute;
    private LinearLayout llEmpty;
    private SchoolListMyFollowAdapter myFollowAdapter;
    private LinearLayout myFollowLl;
    private boolean myFollowNoMore;
    private RecyclerView myFollowRv;
    private int popH;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rvSchoolList;
    private TargetSchoolListAdapter schoolListAdapter;
    private View searchView;
    private int statusBarHeight;
    private ClearEditText topSearch;
    private TextView topSearchTv;
    private TextView tvArea;
    private TextView tvSchoolAttribute;
    private List<ChooseSchoolFilterBean> mListFilterArea = new ArrayList();
    private List<ChooseSchoolFilterBean> mListFilterAttr = new ArrayList();
    private List<String> mListFilterAttrSelectId = new ArrayList();
    private List<String> mListFilterAreaSelectId = new ArrayList();
    private List<FollowSchoolBean.DataBean> myfollowSchoolList = new ArrayList();
    private List<FollowSchoolBean.DataBean> schoolList = new ArrayList();
    private int follow_page = 1;
    private int page = 1;
    private int pageSize = 20;
    private String selectedArea = "";
    private String selectedAttribute = "";
    private String searchKeyword = "";
    private boolean isLoading = false;

    public static /* synthetic */ int access$508(SchoolListActivity schoolListActivity) {
        int i2 = schoolListActivity.follow_page;
        schoolListActivity.follow_page = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$510(SchoolListActivity schoolListActivity) {
        int i2 = schoolListActivity.follow_page;
        schoolListActivity.follow_page = i2 - 1;
        return i2;
    }

    public static /* synthetic */ int access$910(SchoolListActivity schoolListActivity) {
        int i2 = schoolListActivity.page;
        schoolListActivity.page = i2 - 1;
        return i2;
    }

    private void getFilterData() {
        YJYHttpUtils.get(this, NetworkRequestsURL.chooseSchoolFilterData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolListActivity.5
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
                        if (chooseSchoolFilterBeanList.getArea() != null) {
                            SchoolListActivity.this.mListFilterArea.addAll(chooseSchoolFilterBeanList.getArea());
                        }
                        if (chooseSchoolFilterBeanList.getAttr() != null) {
                            SchoolListActivity.this.mListFilterAttr.addAll(chooseSchoolFilterBeanList.getAttr());
                        }
                    }
                } catch (Exception e2) {
                    Log.e(SchoolListActivity.this.TAG, "onSuccess: " + e2.getMessage());
                }
            }
        });
    }

    private void initAdapters() {
        this.myFollowAdapter = new SchoolListMyFollowAdapter(this, this.myfollowSchoolList);
        this.myFollowRv.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.myFollowRv.setAdapter(this.myFollowAdapter);
        this.myFollowRv.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolListActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager == null) {
                    return;
                }
                if (linearLayoutManager.findLastVisibleItemPosition() != linearLayoutManager.getItemCount() - 1 || dx <= 0 || SchoolListActivity.this.myFollowNoMore) {
                    return;
                }
                SchoolListActivity.this.loadMyfollowSchools();
            }
        });
        this.schoolListAdapter = new TargetSchoolListAdapter(this, this.schoolList);
        this.rvSchoolList.setLayoutManager(new LinearLayoutManager(this));
        this.rvSchoolList.setAdapter(this.schoolListAdapter);
        this.schoolListAdapter.setOnItemClickListener(new TargetSchoolListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.q5
            @Override // com.psychiatrygarden.adapter.TargetSchoolListAdapter.OnItemClickListener
            public final void onItemClick(int i2, FollowSchoolBean.DataBean dataBean) {
                this.f11390a.lambda$initAdapters$0(i2, dataBean);
            }
        });
    }

    private void initRefreshLayout() {
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.m5
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11355c.lambda$initRefreshLayout$1(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.n5
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11366c.lambda$initRefreshLayout$2(refreshLayout);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.o5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11374c.lambda$initRefreshLayout$3(view);
            }
        });
    }

    private void initViews() {
        this.ivActionbarBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.topSearch = (ClearEditText) findViewById(R.id.top_search);
        this.topSearchTv = (TextView) findViewById(R.id.top_search_tv);
        this.myFollowRv = (RecyclerView) findViewById(R.id.my_follow_rv);
        this.rvSchoolList = (RecyclerView) findViewById(R.id.rvSchoolList);
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        this.layoutFilter = (LinearLayout) findViewById(R.id.layoutFilter);
        this.layoutArea = (LinearLayout) findViewById(R.id.layoutArea);
        this.layoutSchoolAttribute = (LinearLayout) findViewById(R.id.layoutSchoolAttribute);
        this.tvArea = (TextView) findViewById(R.id.tvArea);
        this.tvSchoolAttribute = (TextView) findViewById(R.id.tvSchoolAttribute);
        this.ivAreaArrow = (ImageView) findViewById(R.id.ivAreaArrow);
        this.ivSchoolAttributeArrow = (ImageView) findViewById(R.id.ivSchoolAttributeArrow);
        this.llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        this.myFollowLl = (LinearLayout) findViewById(R.id.my_follow_ll);
        View viewFindViewById = findViewById(R.id.search_view);
        this.searchView = viewFindViewById;
        viewFindViewById.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolListActivity.1
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public void onOffsetChanged(AppBarLayout appBarLayout2, int verticalOffset) {
                if (SchoolListActivity.this.layoutFilter.getTop() + verticalOffset <= 0) {
                    SchoolListActivity.this.layoutFilter.setBackgroundColor(SchoolListActivity.this.getResources().getColor(SkinManager.getCurrentSkinType(SchoolListActivity.this) == 1 ? R.color.new_bg_one_color_night : R.color.white));
                } else {
                    SchoolListActivity.this.layoutFilter.setBackgroundColor(SchoolListActivity.this.getResources().getColor(SkinManager.getCurrentSkinType(SchoolListActivity.this) == 1 ? R.color.new_bg_two_color_night_new : R.color.new_bg_two_color_new));
                }
            }
        });
        this.statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapters$0(int i2, FollowSchoolBean.DataBean dataBean) {
        SchoolDetailsAct.newIntent(this.mContext, this.schoolList.get(i2).getSchool_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$1(RefreshLayout refreshLayout) {
        this.page = 1;
        loadSchoolList();
        this.follow_page = 1;
        loadMyfollowSchools();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$2(RefreshLayout refreshLayout) {
        this.page++;
        loadSchoolList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$3(View view) {
        this.page = 1;
        loadSchoolList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$4(TextView textView, int i2, KeyEvent keyEvent) {
        this.searchKeyword = textView.getText().toString().trim();
        this.page = 1;
        loadSchoolList();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$5(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$6(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMyfollowSchools() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.follow_page + "");
        ajaxParams.put("page_size", this.pageSize + "");
        YJYHttpUtils.get(this, NetworkRequestsURL.followSchoolList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolListActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SchoolListActivity.this.hideProgressDialog();
                SchoolListActivity.this.isLoading = false;
                NewToast.showShort(SchoolListActivity.this, "网络连接失败", 0).show();
                if (SchoolListActivity.this.follow_page == 1) {
                    SchoolListActivity.this.myFollowLl.setVisibility(8);
                } else {
                    SchoolListActivity.access$510(SchoolListActivity.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                SchoolListActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                SchoolListActivity.this.hideProgressDialog();
                SchoolListActivity.this.isLoading = false;
                try {
                    FollowSchoolBean followSchoolBean = (FollowSchoolBean) new Gson().fromJson(s2, FollowSchoolBean.class);
                    if (!"200".equals(followSchoolBean.getCode())) {
                        NewToast.showShort(SchoolListActivity.this, followSchoolBean.getMessage(), 0).show();
                        if (SchoolListActivity.this.follow_page == 1) {
                            SchoolListActivity.this.myFollowLl.setVisibility(8);
                            return;
                        } else {
                            SchoolListActivity.access$510(SchoolListActivity.this);
                            return;
                        }
                    }
                    if (followSchoolBean.getData() == null || followSchoolBean.getData().isEmpty()) {
                        if (SchoolListActivity.this.follow_page == 1) {
                            SchoolListActivity.this.myFollowLl.setVisibility(8);
                            return;
                        } else {
                            SchoolListActivity.this.myFollowNoMore = true;
                            return;
                        }
                    }
                    SchoolListActivity.this.myFollowLl.setVisibility(0);
                    List<FollowSchoolBean.DataBean> data = followSchoolBean.getData();
                    if (SchoolListActivity.this.follow_page == 1) {
                        SchoolListActivity.this.myfollowSchoolList.clear();
                        SchoolListActivity.this.myfollowSchoolList.addAll(data);
                        SchoolListActivity.this.myFollowAdapter.notifyDataSetChanged();
                        if (data.size() < SchoolListActivity.this.pageSize) {
                            SchoolListActivity.this.myFollowNoMore = true;
                        } else {
                            SchoolListActivity.this.myFollowNoMore = false;
                        }
                    } else {
                        SchoolListActivity.this.myfollowSchoolList.addAll(data);
                        SchoolListActivity.this.myFollowAdapter.notifyDataSetChanged();
                        if (data.size() < SchoolListActivity.this.pageSize) {
                            SchoolListActivity.this.myFollowNoMore = true;
                        } else {
                            SchoolListActivity.this.myFollowNoMore = false;
                        }
                    }
                    SchoolListActivity.access$508(SchoolListActivity.this);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SchoolListActivity.this, "数据解析出错", 0).show();
                    if (SchoolListActivity.this.follow_page == 1) {
                        SchoolListActivity.this.myFollowLl.setVisibility(8);
                    } else {
                        SchoolListActivity.access$510(SchoolListActivity.this);
                    }
                }
            }
        });
    }

    private void loadSchoolList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", this.pageSize + "");
        if (this.mListFilterAttrSelectId.isEmpty()) {
            this.selectedAttribute = "";
        } else {
            this.selectedAttribute = com.psychiatrygarden.activity.q2.a(",", this.mListFilterAttrSelectId);
        }
        if (this.mListFilterAreaSelectId.isEmpty()) {
            this.selectedArea = "";
        } else {
            this.selectedArea = com.psychiatrygarden.activity.q2.a(",", this.mListFilterAreaSelectId);
        }
        if (!this.selectedArea.isEmpty()) {
            ajaxParams.put("area_id", this.selectedArea);
        }
        if (!this.selectedAttribute.isEmpty()) {
            ajaxParams.put("attr", this.selectedAttribute);
        }
        if (!this.searchKeyword.isEmpty()) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, this.searchKeyword);
        }
        YJYHttpUtils.get(this, NetworkRequestsURL.indexForSchoolList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolListActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SchoolListActivity.this.hideProgressDialog();
                SchoolListActivity.this.refreshLayout.finishRefresh();
                NewToast.showShort(SchoolListActivity.this, "网络连接失败", 0).show();
                if (SchoolListActivity.this.page == 1) {
                    SchoolListActivity.this.showEmptyView();
                } else {
                    SchoolListActivity.access$910(SchoolListActivity.this);
                    SchoolListActivity.this.refreshLayout.finishLoadMore(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                if (SchoolListActivity.this.page == 1) {
                    SchoolListActivity.this.showProgressDialog();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                SchoolListActivity.this.hideProgressDialog();
                SchoolListActivity.this.refreshLayout.finishRefresh();
                try {
                    FollowSchoolBean followSchoolBean = (FollowSchoolBean) new Gson().fromJson(s2, FollowSchoolBean.class);
                    if ("200".equals(followSchoolBean.getCode())) {
                        SchoolListActivity.this.updateUI(followSchoolBean);
                    } else {
                        NewToast.showShort(SchoolListActivity.this, followSchoolBean.getMessage(), 0).show();
                        if (SchoolListActivity.this.page == 1) {
                            SchoolListActivity.this.showEmptyView();
                        } else {
                            SchoolListActivity.access$910(SchoolListActivity.this);
                            SchoolListActivity.this.refreshLayout.finishLoadMore(false);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(SchoolListActivity.this, "数据解析出错", 0).show();
                    if (SchoolListActivity.this.page == 1) {
                        SchoolListActivity.this.showEmptyView();
                    } else {
                        SchoolListActivity.access$910(SchoolListActivity.this);
                        SchoolListActivity.this.refreshLayout.finishLoadMore(false);
                    }
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) SchoolListActivity.class);
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
            ImageView imageView = this.ivSchoolAttributeArrow;
            if (this.mListFilterAttrSelectId.isEmpty()) {
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
        new ChooseAreaFilterPopUpWindow(this, this.layoutArea, allViewHeight, this.mListFilterAttr, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolListActivity.7
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                SchoolListActivity schoolListActivity = SchoolListActivity.this;
                schoolListActivity.showOrHiddenArrow(false, schoolListActivity.ivSchoolAttributeArrow);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NonNull List<ChooseSchoolFilterBean> mList) {
                SchoolListActivity schoolListActivity = SchoolListActivity.this;
                schoolListActivity.showOrHiddenArrow(false, schoolListActivity.ivSchoolAttributeArrow);
                SchoolListActivity.this.mListFilterAttrSelectId.clear();
                for (int i3 = 0; i3 < mList.size(); i3++) {
                    if (mList.get(i3).getSelected()) {
                        SchoolListActivity.this.mListFilterAttrSelectId.add(mList.get(i3).getId());
                    }
                }
                SchoolListActivity.this.notifyListDataChanged(1);
            }
        });
    }

    private void showDepartmentDialog(int allViewHeight) {
        for (int i2 = 0; i2 < this.mListFilterArea.size(); i2++) {
            this.mListFilterArea.get(i2).setSelected(this.mListFilterAreaSelectId.contains(this.mListFilterArea.get(i2).getId()));
        }
        showOrHiddenArrow(true, this.ivAreaArrow);
        new ChooseAreaFilterPopUpWindow(this, this.layoutArea, allViewHeight, this.mListFilterArea, new ChooseAreaFilterPopUpWindow.ProjectChooseInterface() { // from class: com.psychiatrygarden.activity.chooseSchool.SchoolListActivity.6
            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemDismissListener() {
                SchoolListActivity schoolListActivity = SchoolListActivity.this;
                schoolListActivity.showOrHiddenArrow(false, schoolListActivity.ivAreaArrow);
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mItemListener(int choosePos, @Nullable ChooseSchoolFilterBean type) {
            }

            @Override // com.psychiatrygarden.activity.chooseSchool.widget.ChooseAreaFilterPopUpWindow.ProjectChooseInterface
            public void mSubmitListener(@NonNull List<ChooseSchoolFilterBean> mList) {
                SchoolListActivity schoolListActivity = SchoolListActivity.this;
                schoolListActivity.showOrHiddenArrow(false, schoolListActivity.ivAreaArrow);
                SchoolListActivity.this.mListFilterAreaSelectId.clear();
                for (int i3 = 0; i3 < mList.size(); i3++) {
                    if (mList.get(i3).getSelected()) {
                        SchoolListActivity.this.mListFilterAreaSelectId.add(mList.get(i3).getId());
                    }
                }
                SchoolListActivity.this.notifyListDataChanged(0);
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
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.k5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SchoolListActivity.lambda$showOrHiddenArrow$5(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.chooseSchool.l5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SchoolListActivity.lambda$showOrHiddenArrow$6(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(FollowSchoolBean detailBean) {
        if (detailBean.getData() == null || detailBean.getData().isEmpty()) {
            if (this.page != 1) {
                this.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                showEmptyView();
                this.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
        }
        List<FollowSchoolBean.DataBean> data = detailBean.getData();
        if (this.page != 1) {
            this.schoolList.addAll(data);
            this.schoolListAdapter.notifyDataSetChanged();
            if (data.size() < this.pageSize) {
                this.refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                this.refreshLayout.finishLoadMore();
                return;
            }
        }
        this.schoolList.clear();
        this.schoolList.addAll(data);
        this.schoolListAdapter.notifyDataSetChanged();
        this.llEmpty.setVisibility(8);
        this.refreshLayout.setVisibility(0);
        if (data.size() < this.pageSize) {
            this.refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            this.refreshLayout.finishLoadMore();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        initViews();
        initAdapters();
        initRefreshLayout();
        loadMyfollowSchools();
        getFilterData();
        loadSchoolList();
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
            case R.id.search_view /* 2131366830 */:
                SearchSchoolOrMajorAct.newIntent(this, 1);
                break;
            case R.id.top_search_tv /* 2131367383 */:
                this.searchKeyword = this.topSearch.getText().toString().trim();
                this.page = 1;
                loadSchoolList();
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        this.page = 1;
        loadSchoolList();
        this.follow_page = 1;
        loadMyfollowSchools();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.school_list_activity);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.ivActionbarBack.setOnClickListener(this);
        this.topSearchTv.setOnClickListener(this);
        this.layoutArea.setOnClickListener(this);
        this.layoutSchoolAttribute.setOnClickListener(this);
        this.topSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.chooseSchool.p5
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f11382c.lambda$setListenerForWidget$4(textView, i2, keyEvent);
            }
        });
    }
}
