package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.android.exoplayer2.C;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.bean.CurriculumCategroyBean;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumFragment;
import com.psychiatrygarden.adapter.AllCourseLiveBannerAdapter;
import com.psychiatrygarden.adapter.AllCourseSubjectBannerAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChannelItem;
import com.psychiatrygarden.bean.LiveBannerBean;
import com.psychiatrygarden.bean.LiveBannerListBean;
import com.psychiatrygarden.bean.SpecialData;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CourseRefreshChannelData;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LeftStartLayoutManager;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.WeakHandler;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AllCourseFragment extends BaseFragment {
    public static final String LIVING_FLAG = "living_flag";
    public static final String LIVING_FLAG_NO_HAVE = "living_flag_no_have";
    private static final String TAG = "AllCourseFragment";
    private static final int WHAT_AUTO_SCROLL = 100;
    private static final int WHAT_SCROLL_TO_LIVING = 1;
    private RecyclerView allCourseLiveRecyclerView;
    private AllCourseSubjectBannerAdapter allCourseSubjectBannerAdapter;
    private RecyclerView allCourseSubjectRecyclerView;
    public AppBarLayout appbarlayout;
    private CoordinatorLayout coordinatorLayout;
    private AllCourseLiveBannerAdapter liveBannerAdapter;
    private Context mContext;
    private MagicIndicator mMagicIndicator;
    private SmartRefreshLayout mRefreshLayout;
    public LinearLayout relLogView;
    private CustomEmptyView rlLoading;
    private TextView tvLiveTitle;
    private TextView tvSpecial;
    private ViewPager viewpager;
    private WeakHandler weakHandler;
    private final ArrayList<ChannelItem> mDefaultList = new ArrayList<>();
    private final ArrayMap<Integer, Boolean> noMoreDataMap = new ArrayMap<>();
    private boolean isOpenSend = false;
    private boolean isCloseSend = false;
    private boolean fragmentIsOnStop = false;
    private int targetPos = 0;
    private final int dataLength = 0;
    private List<CurriculumCategroyBean.DataDTO> dataNewList = new ArrayList();
    private boolean isInitTab = false;
    private boolean isLoadTabIng = false;
    private int firstLivingBannerIndex = -1;
    private boolean isFirstEnter = true;
    private int keepLastIndexTab = 0;

    /* renamed from: com.psychiatrygarden.fragmenthome.AllCourseFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends CommonNavigatorAdapter {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            AllCourseFragment.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return AllCourseFragment.this.dataNewList.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(AllCourseFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(((CurriculumCategroyBean.DataDTO) AllCourseFragment.this.dataNewList.get(index)).getTitle());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15578c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.AllCourseFragment.4.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    int themeColor;
                    int themeColor2;
                    textView.setTypeface(Typeface.DEFAULT);
                    if (AllCourseFragment.this.getContext() != null) {
                        themeColor = SkinManager.getThemeColor(AllCourseFragment.this.getContext(), R.attr.app_bg);
                        themeColor2 = SkinManager.getThemeColor(AllCourseFragment.this.getContext(), R.attr.first_text_color);
                    } else {
                        themeColor = 0;
                        themeColor2 = 0;
                    }
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(themeColor);
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(AllCourseFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(themeColor2);
                    textView.setBackground(gradientDrawable);
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onEnter(int index2, int totalCount, float enterPercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onLeave(int index2, int totalCount, float leavePercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onSelected(int index2, int totalCount) {
                    int themeColor;
                    int themeColor2;
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    if (AllCourseFragment.this.getContext() != null) {
                        themeColor = SkinManager.getThemeColor(AllCourseFragment.this.getContext(), R.attr.main_theme_five_deep_color);
                        themeColor2 = SkinManager.getThemeColor(AllCourseFragment.this.getContext(), R.attr.main_theme_color);
                    } else {
                        themeColor = 0;
                        themeColor2 = 0;
                    }
                    gradientDrawable.setColor(themeColor);
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(AllCourseFragment.this.requireContext(), 8.0f));
                    textView.setTextColor(themeColor2);
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getBannerData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getContext()));
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.courseBannerLiving, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AllCourseFragment.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                AllCourseFragment.this.allCourseLiveRecyclerView.setVisibility(8);
                AllCourseFragment.this.tvLiveTitle.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200")) {
                        AllCourseFragment.this.allCourseLiveRecyclerView.setVisibility(8);
                        AllCourseFragment.this.tvLiveTitle.setVisibility(8);
                        return;
                    }
                    LiveBannerListBean liveBannerListBean = (LiveBannerListBean) new Gson().fromJson(jSONObject.optString("data"), LiveBannerListBean.class);
                    if (liveBannerListBean == null || liveBannerListBean.getList() == null || liveBannerListBean.getList().isEmpty()) {
                        AllCourseFragment.this.allCourseLiveRecyclerView.setVisibility(8);
                        AllCourseFragment.this.tvLiveTitle.setVisibility(8);
                        return;
                    }
                    if (liveBannerListBean.getList().size() == 1) {
                        liveBannerListBean.getList().get(0).setItemType(1);
                    } else {
                        for (int i2 = 0; i2 < liveBannerListBean.getList().size(); i2++) {
                            liveBannerListBean.getList().get(i2).setItemType(0);
                        }
                    }
                    AllCourseFragment.this.liveBannerAdapter.setList(liveBannerListBean.getList());
                    AllCourseFragment.this.allCourseLiveRecyclerView.setVisibility(0);
                    AllCourseFragment.this.tvLiveTitle.setText(liveBannerListBean.getTitle());
                    AllCourseFragment.this.tvLiveTitle.setVisibility(0);
                    if (AllCourseFragment.this.getFirstBannerLiveIndex(liveBannerListBean.getList()) > -1) {
                        EventBus.getDefault().post(AllCourseFragment.LIVING_FLAG);
                    } else {
                        EventBus.getDefault().post(AllCourseFragment.LIVING_FLAG_NO_HAVE);
                        AllCourseFragment.this.getFirstBannerWaitLiveIndex(liveBannerListBean.getList());
                    }
                    AllCourseFragment.this.weakHandler.sendEmptyMessageDelayed(1, AllCourseFragment.this.isFirstEnter ? 1500 : 1000);
                    AllCourseFragment.this.isFirstEnter = false;
                } catch (Exception e2) {
                    Log.e("error", e2.getMessage());
                    AllCourseFragment.this.allCourseLiveRecyclerView.setVisibility(8);
                    AllCourseFragment.this.tvLiveTitle.setVisibility(8);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCourseDataTab() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        if (this.isLoadTabIng) {
            return;
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseCategoryTab, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AllCourseFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) throws Resources.NotFoundException {
                super.onFailure(t2, errorNo, strMsg);
                AllCourseFragment.this.mRefreshLayout.finishRefresh(false);
                AllCourseFragment.this.isLoadTabIng = false;
                AllCourseFragment.this.initCourseTabInfoData("");
                AllCourseFragment.this.loadData();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                AllCourseFragment.this.isLoadTabIng = true;
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) throws Resources.NotFoundException {
                super.onSuccess((AnonymousClass2) t2);
                AllCourseFragment.this.isLoadTabIng = false;
                try {
                    AllCourseFragment.this.loadData();
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        AllCourseFragment.this.initCourseTabInfoData(jSONObject.optString("data"));
                    } else {
                        NewToast.showShort(AllCourseFragment.this.getActivity(), jSONObject.optString("message"));
                        AllCourseFragment.this.initCourseTabInfoData("");
                        AllCourseFragment.this.mRefreshLayout.finishRefresh(false);
                    }
                    AllCourseFragment.this.initTabColumn();
                    AllCourseFragment.this.isInitTab = true;
                } catch (Exception unused) {
                    AllCourseFragment.this.mRefreshLayout.finishRefresh(false);
                    AllCourseFragment.this.initCourseTabInfoData("");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCourseSpecial() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getContext()));
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.courseCourseSpecial, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AllCourseFragment.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                Log.d(AllCourseFragment.TAG, "onFailure: " + strMsg);
                AllCourseFragment.this.tvSpecial.setVisibility(8);
                AllCourseFragment.this.allCourseSubjectRecyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                Log.d(AllCourseFragment.TAG, "onStart:--- ");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SpecialData specialData = (SpecialData) new Gson().fromJson(jSONObject.optString("data"), SpecialData.class);
                        if (specialData == null || specialData.getList() == null || specialData.getList().isEmpty()) {
                            AllCourseFragment.this.tvSpecial.setVisibility(8);
                            AllCourseFragment.this.allCourseSubjectRecyclerView.setVisibility(8);
                        } else {
                            AllCourseFragment.this.tvSpecial.setVisibility(0);
                            AllCourseFragment.this.allCourseSubjectRecyclerView.setVisibility(0);
                            AllCourseFragment.this.tvSpecial.setText(specialData.getTitle());
                            AllCourseFragment.this.allCourseSubjectBannerAdapter.setList(specialData.getList());
                        }
                    } else {
                        AllCourseFragment.this.tvSpecial.setVisibility(8);
                        AllCourseFragment.this.allCourseSubjectRecyclerView.setVisibility(8);
                    }
                } catch (Exception e2) {
                    Log.e("Error line-542:", e2.getMessage());
                    AllCourseFragment.this.tvSpecial.setVisibility(8);
                    AllCourseFragment.this.allCourseSubjectRecyclerView.setVisibility(8);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getFirstBannerLiveIndex(List<LiveBannerBean> data) {
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (AllCourseLiveBannerAdapter.getLiveStatus(data.get(i2)) == 0) {
                this.firstLivingBannerIndex = i2;
                return i2;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getFirstBannerWaitLiveIndex(List<LiveBannerBean> data) {
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (AllCourseLiveBannerAdapter.getLiveStatus(data.get(i2)) == 1) {
                this.firstLivingBannerIndex = i2;
                return i2;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabColumn() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        List<CurriculumCategroyBean.DataDTO> list = this.dataNewList;
        if (list == null || list.size() <= 0) {
            this.mMagicIndicator.setVisibility(8);
        } else {
            this.mMagicIndicator.setVisibility(0);
        }
        for (int i2 = 0; i2 < this.dataNewList.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("id", this.dataNewList.get(i2).getId());
            bundle.putString("title", this.dataNewList.get(i2).getTitle());
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.dataNewList.get(i2).getTitle(), CurriculumFragment.class, bundle));
        }
        if (isAdded()) {
            this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList));
            List<CurriculumCategroyBean.DataDTO> list2 = this.dataNewList;
            if (list2 != null && list2.size() > 1 && this.keepLastIndexTab >= this.dataNewList.size()) {
                this.keepLastIndexTab = this.dataNewList.size() - 1;
            }
            CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
            commonNavigator.setSkimOver(true);
            commonNavigator.setAdapter(new AnonymousClass4());
            this.mMagicIndicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
            this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.AllCourseFragment.5
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int state) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int position) {
                    AllCourseFragment.this.mRefreshLayout.resetNoMoreData();
                    Boolean bool = (Boolean) AllCourseFragment.this.noMoreDataMap.get(Integer.valueOf(position));
                    if (bool == null || bool == Boolean.FALSE) {
                        AllCourseFragment.this.mRefreshLayout.resetNoMoreData();
                    } else {
                        AllCourseFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                    AllCourseFragment.this.keepLastIndexTab = position;
                }
            });
            this.viewpager.setCurrentItem(this.keepLastIndexTab);
            this.viewpager.setOffscreenPageLimit(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(AppBarLayout appBarLayout, int i2) {
        if (i2 == 0) {
            if (this.isOpenSend) {
                return;
            }
            this.isOpenSend = true;
            this.isCloseSend = false;
            Log.e("app_bar_layout", "appbar完全展开===>" + i2);
            return;
        }
        if (this.isCloseSend) {
            return;
        }
        this.isCloseSend = true;
        this.isOpenSend = false;
        Log.e("app_bar_layout", "appbar不是完全展开===>" + i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(Message message) {
        LinearLayoutManager linearLayoutManager;
        int i2 = message.what;
        if (i2 == 1) {
            if (this.firstLivingBannerIndex < 0 || (linearLayoutManager = (LinearLayoutManager) this.allCourseLiveRecyclerView.getLayoutManager()) == null) {
                return;
            }
            linearLayoutManager.scrollToPositionWithOffset(this.firstLivingBannerIndex, 0);
            return;
        }
        if (i2 != 100) {
            return;
        }
        if (!this.fragmentIsOnStop) {
            RecyclerView.LayoutManager layoutManager = this.allCourseLiveRecyclerView.getLayoutManager();
            Objects.requireNonNull(layoutManager);
            int iFindFirstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int iFindFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) this.allCourseLiveRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            Log.d("banner", "handlerMessage:  第一个可见的：" + iFindFirstVisibleItemPosition + " --- 第一个完全可见:" + iFindFirstCompletelyVisibleItemPosition + "--上一次target:" + this.targetPos);
            if (iFindFirstVisibleItemPosition < 0) {
                return;
            }
            if (iFindFirstCompletelyVisibleItemPosition == -1) {
                this.targetPos = 0;
            } else {
                int i3 = this.targetPos;
                if (iFindFirstVisibleItemPosition != i3) {
                    this.targetPos = iFindFirstVisibleItemPosition;
                } else if (i3 == 9) {
                    this.targetPos = 0;
                } else {
                    this.targetPos = i3 + 1;
                }
            }
            int i4 = this.targetPos;
            if (i4 == 0) {
                this.allCourseLiveRecyclerView.scrollToPosition(i4);
            } else {
                this.allCourseLiveRecyclerView.smoothScrollToPosition(i4);
            }
        }
        this.weakHandler.sendEmptyMessageDelayed(100, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        this.rlLoading.setVisibility(8);
        this.rlLoading.stopAnim();
        this.mRefreshLayout.setVisibility(0);
        this.coordinatorLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMoreChannelData() {
        EventBus.getDefault().post(new CourseRefreshChannelData(String.valueOf(this.mDefaultList.get(this.viewpager.getCurrentItem()).id), false));
    }

    public static AllCourseFragment newInstance() {
        Bundle bundle = new Bundle();
        AllCourseFragment allCourseFragment = new AllCourseFragment();
        allCourseFragment.setArguments(bundle);
        return allCourseFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshChannelData() {
        EventBus.getDefault().post(new CourseRefreshChannelData(String.valueOf(this.mDefaultList.get(this.viewpager.getCurrentItem()).id), true));
    }

    private void showTabLayout(boolean isShow) {
        MagicIndicator magicIndicator = this.mMagicIndicator;
        if (magicIndicator != null) {
            magicIndicator.setVisibility(isShow ? 0 : 4);
            this.mMagicIndicator.setBackgroundColor(isShow ? -1 : 0);
        }
    }

    public void finishLoadMore(boolean success, int channelId) {
        this.mRefreshLayout.finishLoadMore(success);
        this.noMoreDataMap.put(Integer.valueOf(channelId), Boolean.FALSE);
    }

    public void finishLoadMoreNoMoreData(int channelId) {
        this.noMoreDataMap.put(Integer.valueOf(channelId), Boolean.TRUE);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.mDefaultList.size()) {
                break;
            }
            if (channelId == this.mDefaultList.get(i3).getId()) {
                i2 = i3;
                break;
            }
            i3++;
        }
        if (this.viewpager.getCurrentItem() == i2) {
            this.mRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    public void finishRefresh(boolean success, int channelId) {
        this.mRefreshLayout.finishRefresh(success);
        this.noMoreDataMap.put(Integer.valueOf(channelId), Boolean.FALSE);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_all_course;
    }

    public void initCourseTabInfoData(String mDataStr) throws Resources.NotFoundException {
        if (TextUtils.isEmpty(mDataStr)) {
            return;
        }
        this.dataNewList = (List) new Gson().fromJson(mDataStr, new TypeToken<List<CurriculumCategroyBean.DataDTO>>() { // from class: com.psychiatrygarden.fragmenthome.AllCourseFragment.3
        }.getType());
        for (int i2 = 0; i2 < this.dataNewList.size(); i2++) {
            CurriculumCategroyBean.DataDTO dataDTO = this.dataNewList.get(i2);
            ChannelItem channelItem = new ChannelItem();
            channelItem.setId(Integer.parseInt(dataDTO.getId()));
            channelItem.setName(dataDTO.getTitle());
            this.mDefaultList.add(channelItem);
        }
        initTabColumn();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        SharePreferencesUtils.writeStrConfig(CommonParameter.sortvalue, "1", getActivity());
        this.tvLiveTitle = (TextView) holder.get(R.id.tvLiveTitle);
        this.tvSpecial = (TextView) holder.get(R.id.tvSpecial);
        this.rlLoading = (CustomEmptyView) holder.get(R.id.rl_loading);
        this.mRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.coordinatorLayout = (CoordinatorLayout) holder.get(R.id.ctl_content);
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.circlehs);
        this.viewpager = (ViewPager) holder.get(R.id.viewpagecircle);
        this.appbarlayout = (AppBarLayout) holder.get(R.id.appbarlayout);
        this.relLogView = (LinearLayout) holder.get(R.id.rellogview);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.fragmenthome.d
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f15526a.lambda$initViews$0(appBarLayout, i2);
            }
        });
        this.mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.fragmenthome.AllCourseFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (AllCourseFragment.this.isInitTab) {
                    AllCourseFragment.this.loadMoreChannelData();
                } else {
                    AllCourseFragment.this.mRefreshLayout.finishLoadMore(false);
                }
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                AllCourseFragment.this.isInitTab = false;
                if (AllCourseFragment.this.isInitTab) {
                    AllCourseFragment.this.refreshChannelData();
                } else {
                    AllCourseFragment.this.getCourseDataTab();
                }
                AllCourseFragment.this.getCourseSpecial();
                AllCourseFragment.this.getBannerData();
            }
        });
        this.allCourseLiveRecyclerView = (RecyclerView) holder.get(R.id.allCourseLiveRecyclerView);
        LeftStartLayoutManager leftStartLayoutManager = new LeftStartLayoutManager(getContext());
        leftStartLayoutManager.setOrientation(0);
        this.allCourseLiveRecyclerView.setLayoutManager(leftStartLayoutManager);
        AllCourseLiveBannerAdapter allCourseLiveBannerAdapter = new AllCourseLiveBannerAdapter();
        this.liveBannerAdapter = allCourseLiveBannerAdapter;
        this.allCourseLiveRecyclerView.setAdapter(allCourseLiveBannerAdapter);
        this.allCourseSubjectRecyclerView = (RecyclerView) holder.get(R.id.allCourseSubjectRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        this.allCourseSubjectRecyclerView.setLayoutManager(linearLayoutManager);
        AllCourseSubjectBannerAdapter allCourseSubjectBannerAdapter = new AllCourseSubjectBannerAdapter();
        this.allCourseSubjectBannerAdapter = allCourseSubjectBannerAdapter;
        this.allCourseSubjectRecyclerView.setAdapter(allCourseSubjectBannerAdapter);
        if (getActivity() != null) {
            this.weakHandler = new WeakHandler(getActivity(), new WeakHandler.HandlerCallback() { // from class: com.psychiatrygarden.fragmenthome.e
                @Override // com.psychiatrygarden.utils.WeakHandler.HandlerCallback
                public final void handlerMessage(Message message) {
                    this.f15554a.lambda$initViews$1(message);
                }
            });
        }
        getCourseSpecial();
        getCourseDataTab();
        getBannerData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.weakHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        str.hashCode();
        if (str.equals("isHiddenTabView")) {
            showTabLayout(false);
        } else if (str.equals("isShowTabView")) {
            showTabLayout(true);
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.fragmentIsOnStop = false;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.fragmentIsOnStop = true;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportVisible() {
        super.onSupportVisible();
    }
}
