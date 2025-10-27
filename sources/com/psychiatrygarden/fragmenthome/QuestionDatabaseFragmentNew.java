package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
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
import com.psychiatrygarden.adapter.ViewPageKeyWordAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChannelItem;
import com.psychiatrygarden.bean.CircleDataBean;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshChannelData;
import com.psychiatrygarden.forum.ForumSearchAct;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ViewPager2SlowScrollHelper;
import com.psychiatrygarden.widget.Circle24HotLayout;
import com.psychiatrygarden.widget.CircleKingKongLayout;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class QuestionDatabaseFragmentNew extends BaseFragment {
    private List<CircleDataBean.DataBean.DefaultBean> _default;
    private List<CircleDataBean.DataBean.ListBean> _list;
    public AppBarLayout appbarlayout;
    private CoordinatorLayout ctlContent;
    ViewPager2SlowScrollHelper helperKeywords;
    ViewPager2SlowScrollHelper helperNotice;
    private ViewPageKeyWordAdapter keyWorkAdapter;
    private RelativeLayout keyWorkLayout;
    private TextView mBtnToSearch;
    private CircleDataBean mCircleDataBean;
    private Context mContext;
    private Circle24HotLayout mHotCircleLayout;
    private CircleKingKongLayout mLyGirde;
    private LinearLayout mLyNoticeBanner;
    private MagicIndicator mMagicIndicator;
    private SmartRefreshLayout mRefreshLayout;
    private ViewPageKeyWordAdapter noticeAdapter;
    public LinearLayout rellogview;
    private CustomEmptyView rlLoading;
    private ViewPager2 viewPager2Keywords;
    private ViewPager2 viewPager2Notice;
    private ViewPager viewpager;
    private final ArrayList<ChannelItem> mDefaultList = new ArrayList<>();
    private final ArrayList<ChannelItem> mUserList = new ArrayList<>();
    private final ArrayMap<Integer, Boolean> noMoreDataMap = new ArrayMap<>();
    private boolean isOpenSend = false;
    private boolean isCloseSend = false;
    private boolean fragmentIsOnStop = false;
    private final Handler mNoticeHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (msg.what == 1) {
                QuestionDatabaseFragmentNew.this.updateNotice();
            }
        }
    };
    private final Handler mKeyWordsHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.2
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (msg.what == 1) {
                QuestionDatabaseFragmentNew.this.updateKeyWorkNotice();
            }
        }
    };
    private boolean isInit = false;
    private boolean isInitTab = false;

    /* renamed from: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew$6, reason: invalid class name */
    public class AnonymousClass6 extends CommonNavigatorAdapter {
        public AnonymousClass6() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            QuestionDatabaseFragmentNew.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return QuestionDatabaseFragmentNew.this.mDefaultList.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(QuestionDatabaseFragmentNew.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(((ChannelItem) QuestionDatabaseFragmentNew.this.mDefaultList.get(index)).getName());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.pa
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15915c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.6.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor(SkinManager.getCurrentSkinType(QuestionDatabaseFragmentNew.this.requireContext()) == 1 ? "#0D111D" : "#F9FAFB"));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(QuestionDatabaseFragmentNew.this.requireContext(), 8.0f));
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(QuestionDatabaseFragmentNew.this.requireContext()) == 1 ? "#7380A9" : "#141516"));
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
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor(SkinManager.getCurrentSkinType(QuestionDatabaseFragmentNew.this.requireContext()) == 1 ? "#422A33" : "#FFF1F0"));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(QuestionDatabaseFragmentNew.this.requireContext(), 8.0f));
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(QuestionDatabaseFragmentNew.this.requireContext()) == 1 ? "#B2575C" : "#F95843"));
                    textView.setBackground(gradientDrawable);
                }
            });
            return commonPagerTitleView;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public float getTitleWeight(Context context, int index) {
            return super.getTitleWeight(context, index);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getHotData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("limit", com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO);
        YJYHttpUtils.get(this.mContext.getApplicationContext(), NetworkRequestsURL.hotCircle, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (QuestionDatabaseFragmentNew.this.isInitTab) {
                    QuestionDatabaseFragmentNew.this.refreshChannelData();
                }
                QuestionDatabaseFragmentNew.this.mRefreshLayout.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                boolean z2 = true;
                QuestionDatabaseFragmentNew.this.mRefreshLayout.finishRefresh(true);
                try {
                    CirclrListBean circlrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (!circlrListBean.getCode().equals("200")) {
                        QuestionDatabaseFragmentNew.this.mHotCircleLayout.setVisibility(8);
                        QuestionDatabaseFragmentNew.this.AlertToast(circlrListBean.getMessage());
                    } else if (circlrListBean.getData() == null || circlrListBean.getData().isEmpty()) {
                        QuestionDatabaseFragmentNew.this.mHotCircleLayout.setVisibility(8);
                    } else {
                        QuestionDatabaseFragmentNew.this.mHotCircleLayout.setVisibility(0);
                        Circle24HotLayout circle24HotLayout = QuestionDatabaseFragmentNew.this.mHotCircleLayout;
                        if (circlrListBean.getData().size() <= 5) {
                            z2 = false;
                        }
                        circle24HotLayout.setData(z2, circlrListBean.getData());
                    }
                    if (QuestionDatabaseFragmentNew.this.isInitTab) {
                        QuestionDatabaseFragmentNew.this.refreshChannelData();
                    } else {
                        QuestionDatabaseFragmentNew.this.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTopData() {
        YJYHttpUtils.get(this.mContext.getApplicationContext(), NetworkRequestsURL.forumTopData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    ForumTopBean forumTopBean = (ForumTopBean) new Gson().fromJson(s2, ForumTopBean.class);
                    if (!forumTopBean.getCode().equals("200")) {
                        QuestionDatabaseFragmentNew.this.AlertToast(forumTopBean.getMessage());
                        QuestionDatabaseFragmentNew.this.mBtnToSearch.setVisibility(0);
                        QuestionDatabaseFragmentNew.this.keyWorkLayout.setVisibility(8);
                        return;
                    }
                    if (forumTopBean.getData() != null) {
                        if (forumTopBean.getData().getBbs_module() == null || forumTopBean.getData().getBbs_module().isEmpty()) {
                            SharePreferencesUtils.writeStrConfig("top_grid_data", new Gson().toJson(new ArrayList()), QuestionDatabaseFragmentNew.this.getContext());
                            QuestionDatabaseFragmentNew.this.mLyGirde.setVisibility(8);
                        } else {
                            QuestionDatabaseFragmentNew.this.mLyGirde.setVisibility(0);
                            QuestionDatabaseFragmentNew.this.mLyGirde.setData(forumTopBean.getData().getBbs_module());
                        }
                        if (forumTopBean.getData().getBbs_promotion() == null || forumTopBean.getData().getBbs_promotion().isEmpty()) {
                            QuestionDatabaseFragmentNew.this.viewPager2Notice.setVisibility(8);
                            QuestionDatabaseFragmentNew.this.mLyNoticeBanner.setVisibility(8);
                        } else {
                            QuestionDatabaseFragmentNew.this.mLyNoticeBanner.setVisibility(0);
                            QuestionDatabaseFragmentNew.this.viewPager2Notice.setVisibility(0);
                            QuestionDatabaseFragmentNew questionDatabaseFragmentNew = QuestionDatabaseFragmentNew.this;
                            questionDatabaseFragmentNew.noticeAdapter = new ViewPageKeyWordAdapter(questionDatabaseFragmentNew.getContext(), forumTopBean.getData().getBbs_promotion(), false);
                            QuestionDatabaseFragmentNew.this.viewPager2Notice.setAdapter(QuestionDatabaseFragmentNew.this.noticeAdapter);
                        }
                        if (forumTopBean.getData().getKeywords_recommend() == null || forumTopBean.getData().getKeywords_recommend().isEmpty()) {
                            QuestionDatabaseFragmentNew.this.mBtnToSearch.setVisibility(0);
                            QuestionDatabaseFragmentNew.this.keyWorkLayout.setVisibility(8);
                            return;
                        }
                        QuestionDatabaseFragmentNew.this.mBtnToSearch.setVisibility(8);
                        QuestionDatabaseFragmentNew.this.keyWorkLayout.setVisibility(0);
                        QuestionDatabaseFragmentNew questionDatabaseFragmentNew2 = QuestionDatabaseFragmentNew.this;
                        questionDatabaseFragmentNew2.keyWorkAdapter = new ViewPageKeyWordAdapter(questionDatabaseFragmentNew2.getContext(), forumTopBean.getData().getKeywords_recommend(), true);
                        QuestionDatabaseFragmentNew.this.viewPager2Keywords.setAdapter(QuestionDatabaseFragmentNew.this.keyWorkAdapter);
                        QuestionDatabaseFragmentNew.this.mKeyWordsHandler.sendEmptyMessageDelayed(1, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabColumn() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mDefaultList.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", this.mDefaultList.get(i2).getId());
            bundle.putString("text", this.mDefaultList.get(i2).getName());
            bundle.putInt("module_type", getArguments().getInt("module_type"));
            bundle.putSerializable("tabList", this.mDefaultList);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.mDefaultList.get(i2).getName(), CircleListNewFragment.class, bundle));
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList));
        this.viewpager.setCurrentItem(0);
        this.viewpager.setOffscreenPageLimit(1);
        this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.5
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                QuestionDatabaseFragmentNew.this.mRefreshLayout.resetNoMoreData();
                Boolean bool = (Boolean) QuestionDatabaseFragmentNew.this.noMoreDataMap.get(Integer.valueOf(position));
                if (bool == null || bool == Boolean.FALSE) {
                    QuestionDatabaseFragmentNew.this.mRefreshLayout.resetNoMoreData();
                } else {
                    QuestionDatabaseFragmentNew.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass6());
        this.mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
        this.viewpager.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.la
            @Override // java.lang.Runnable
            public final void run() {
                this.f15811c.lambda$initTabColumn$3();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTabColumn$3() {
        if (this.mDefaultList.isEmpty()) {
            return;
        }
        try {
            this.viewpager.setScrollX(30);
            if (this.viewpager.beginFakeDrag()) {
                this.viewpager.fakeDragBy(20.0f);
                this.viewpager.endFakeDrag();
                this.viewpager.setScrollX(0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(AppBarLayout appBarLayout, int i2) {
        if (i2 == 0) {
            if (this.isOpenSend) {
                return;
            }
            this.mNoticeHandler.sendEmptyMessageDelayed(1, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            this.isOpenSend = true;
            this.isCloseSend = false;
            Log.e("app_bar_layout", "appbar完全展开===>" + i2);
            return;
        }
        if (this.isCloseSend) {
            return;
        }
        this.mNoticeHandler.removeCallbacksAndMessages(null);
        this.isCloseSend = true;
        this.isOpenSend = false;
        Log.e("app_bar_layout", "appbar不是完全展开===>" + i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        this.mLyNoticeBanner.setVisibility(8);
        this.mNoticeHandler.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        ForumSearchAct.newIntent(this.mContext, "", null, 0);
    }

    private void loadMoreChannelData() {
        EventBus.getDefault().post(new RefreshChannelData(String.valueOf(this.mDefaultList.get(this.viewpager.getCurrentItem()).id), false));
    }

    public static QuestionDatabaseFragmentNew newInstance() {
        Bundle bundle = new Bundle();
        QuestionDatabaseFragmentNew questionDatabaseFragmentNew = new QuestionDatabaseFragmentNew();
        questionDatabaseFragmentNew.setArguments(bundle);
        return questionDatabaseFragmentNew;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshChannelData() {
        EventBus.getDefault().post(new RefreshChannelData(String.valueOf(this.mDefaultList.get(this.viewpager.getCurrentItem()).id)));
    }

    private void showTabLayout(boolean isShow) {
        MagicIndicator magicIndicator = this.mMagicIndicator;
        if (magicIndicator != null) {
            magicIndicator.setVisibility(isShow ? 0 : 4);
            this.mMagicIndicator.setBackgroundColor(isShow ? -1 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateKeyWorkNotice() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int currentItem;
        if (this.fragmentIsOnStop) {
            return;
        }
        ViewPageKeyWordAdapter viewPageKeyWordAdapter = this.keyWorkAdapter;
        if (viewPageKeyWordAdapter != null && viewPageKeyWordAdapter.getData() != null && this.keyWorkAdapter.getData().size() > 0 && (currentItem = this.viewPager2Keywords.getCurrentItem() + 1) < this.keyWorkAdapter.getItemCount() && this.keyWorkAdapter.getData().size() > 1) {
            this.helperKeywords.setCurrentItem(currentItem);
        }
        this.mKeyWordsHandler.sendEmptyMessageDelayed(1, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNotice() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int currentItem;
        if (this.fragmentIsOnStop) {
            return;
        }
        ViewPageKeyWordAdapter viewPageKeyWordAdapter = this.noticeAdapter;
        if (viewPageKeyWordAdapter != null && viewPageKeyWordAdapter.getData() != null && this.noticeAdapter.getData().size() > 0 && (currentItem = this.viewPager2Notice.getCurrentItem() + 1) < this.noticeAdapter.getItemCount() && this.noticeAdapter.getData().size() > 1) {
            this.helperNotice.setCurrentItem(currentItem);
        }
        this.mNoticeHandler.sendEmptyMessageDelayed(1, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
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

    public void getData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mgetList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    QuestionDatabaseFragmentNew.this.mCircleDataBean = (CircleDataBean) new Gson().fromJson(s2, CircleDataBean.class);
                    if (QuestionDatabaseFragmentNew.this.mCircleDataBean.getCode().equals("200")) {
                        QuestionDatabaseFragmentNew questionDatabaseFragmentNew = QuestionDatabaseFragmentNew.this;
                        questionDatabaseFragmentNew._default = questionDatabaseFragmentNew.mCircleDataBean.getData().get_default();
                        QuestionDatabaseFragmentNew questionDatabaseFragmentNew2 = QuestionDatabaseFragmentNew.this;
                        questionDatabaseFragmentNew2._list = questionDatabaseFragmentNew2.mCircleDataBean.getData().get_list();
                        if (QuestionDatabaseFragmentNew.this._default != null && QuestionDatabaseFragmentNew.this._default.size() > 0) {
                            QuestionDatabaseFragmentNew.this.mDefaultList.clear();
                            for (int i2 = 0; i2 < QuestionDatabaseFragmentNew.this._default.size(); i2++) {
                                ChannelItem channelItem = new ChannelItem();
                                channelItem.setId(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) QuestionDatabaseFragmentNew.this._default.get(i2)).getId()));
                                channelItem.setName(((CircleDataBean.DataBean.DefaultBean) QuestionDatabaseFragmentNew.this._default.get(i2)).getTitle());
                                channelItem.setOrderId(i2);
                                channelItem.setSort(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) QuestionDatabaseFragmentNew.this._default.get(i2)).getSort())));
                                channelItem.setSelected(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) QuestionDatabaseFragmentNew.this._default.get(i2)).getIs_default())));
                                channelItem.setToday_topic_num(((CircleDataBean.DataBean.DefaultBean) QuestionDatabaseFragmentNew.this._default.get(i2)).getPid());
                                channelItem.setInitials(((CircleDataBean.DataBean.DefaultBean) QuestionDatabaseFragmentNew.this._default.get(i2)).getInitials());
                                QuestionDatabaseFragmentNew.this.mDefaultList.add(channelItem);
                            }
                        }
                        if (QuestionDatabaseFragmentNew.this._list != null && QuestionDatabaseFragmentNew.this._list.size() > 0) {
                            QuestionDatabaseFragmentNew.this.mUserList.clear();
                            for (int i3 = 0; i3 < QuestionDatabaseFragmentNew.this._list.size(); i3++) {
                                ChannelItem channelItem2 = new ChannelItem();
                                channelItem2.setId(Integer.parseInt(((CircleDataBean.DataBean.ListBean) QuestionDatabaseFragmentNew.this._list.get(i3)).getId()));
                                channelItem2.setName(((CircleDataBean.DataBean.ListBean) QuestionDatabaseFragmentNew.this._list.get(i3)).getTitle());
                                channelItem2.setOrderId(i3);
                                channelItem2.setSort(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.ListBean) QuestionDatabaseFragmentNew.this._list.get(i3)).getSort())));
                                channelItem2.setSelected(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.ListBean) QuestionDatabaseFragmentNew.this._list.get(i3)).getIs_default())));
                                channelItem2.setToday_topic_num(((CircleDataBean.DataBean.ListBean) QuestionDatabaseFragmentNew.this._list.get(i3)).getPid());
                                channelItem2.setInitials(((CircleDataBean.DataBean.ListBean) QuestionDatabaseFragmentNew.this._list.get(i3)).getInitials());
                                QuestionDatabaseFragmentNew.this.mUserList.add(channelItem2);
                            }
                        }
                        QuestionDatabaseFragmentNew.this.initTabColumn();
                        QuestionDatabaseFragmentNew.this.isInitTab = true;
                        QuestionDatabaseFragmentNew.this.rlLoading.setVisibility(8);
                        QuestionDatabaseFragmentNew.this.rlLoading.stopAnim();
                        QuestionDatabaseFragmentNew.this.getViewHolder().get(R.id.ly_search).setVisibility(0);
                        QuestionDatabaseFragmentNew.this.mRefreshLayout.setVisibility(0);
                        QuestionDatabaseFragmentNew.this.ctlContent.setVisibility(0);
                    } else {
                        QuestionDatabaseFragmentNew questionDatabaseFragmentNew3 = QuestionDatabaseFragmentNew.this;
                        questionDatabaseFragmentNew3.AlertToast(questionDatabaseFragmentNew3.mCircleDataBean.getMessage());
                    }
                    SharePreferencesUtils.writeLongConfig(CommonParameter.circlrListnum, Long.valueOf(Long.parseLong(QuestionDatabaseFragmentNew.this.mCircleDataBean.getFixedCount())), QuestionDatabaseFragmentNew.this.mContext);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_question_database_new_version;
    }

    public void goFragment() {
    }

    public void initTxt() {
        try {
            SharePreferencesUtils.writeIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT, 6, getActivity());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        getHotData();
        getTopData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        SharePreferencesUtils.writeStrConfig(CommonParameter.sortvalue, "1", getActivity());
        this.mHotCircleLayout = (Circle24HotLayout) holder.get(R.id.hot_circle);
        this.rlLoading = (CustomEmptyView) holder.get(R.id.rl_loading);
        this.mLyGirde = (CircleKingKongLayout) holder.get(R.id.ly_gride);
        ImageView imageView = (ImageView) holder.get(R.id.iv_close);
        this.mLyNoticeBanner = (LinearLayout) holder.get(R.id.ll_text_banner);
        this.viewPager2Keywords = (ViewPager2) holder.get(R.id.viewPager2Keywords);
        ViewPager2 viewPager2 = (ViewPager2) holder.get(R.id.viewPager2Notice);
        this.viewPager2Notice = viewPager2;
        this.helperNotice = new ViewPager2SlowScrollHelper(viewPager2, 1000L);
        this.helperKeywords = new ViewPager2SlowScrollHelper(this.viewPager2Keywords, 1000L);
        this.mRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.ctlContent = (CoordinatorLayout) holder.get(R.id.ctl_content);
        this.mBtnToSearch = (TextView) holder.get(R.id.ly_to_search);
        this.keyWorkLayout = (RelativeLayout) holder.get(R.id.keyWorkLayout);
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.circlehs);
        this.viewpager = (ViewPager) holder.get(R.id.viewpagecircle);
        this.appbarlayout = (AppBarLayout) holder.get(R.id.appbarlayout);
        this.rellogview = (LinearLayout) holder.get(R.id.rellogview);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.fragmenthome.ma
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f15837a.lambda$initViews$0(appBarLayout, i2);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.na
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15870c.lambda$initViews$1(view);
            }
        });
        this.mBtnToSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.oa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15892c.lambda$initViews$2(view);
            }
        });
        this.mRefreshLayout.setEnableLoadMore(false);
        this.mRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionDatabaseFragmentNew.3
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                QuestionDatabaseFragmentNew.this.getTopData();
                QuestionDatabaseFragmentNew.this.getHotData();
                QuestionDatabaseFragmentNew.this.mKeyWordsHandler.removeCallbacksAndMessages(null);
            }
        });
    }

    public void initwriteStatusBar() {
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(requireContext(), R.color.new_bg_one_color), 0);
            getActivity().getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));
        } else {
            StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(requireContext(), R.color.new_bg_one_color_night), 0);
            getActivity().getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mKeyWordsHandler.removeCallbacksAndMessages(null);
        this.mNoticeHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        str.hashCode();
        switch (str) {
            case "refreshForumTopData":
            case "xitongxiaoxi":
                initTxt();
                break;
            case "isHiddenTabView":
                showTabLayout(false);
                break;
            case "isShowTabView":
                showTabLayout(true);
                break;
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isHidden()) {
            return;
        }
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(8192);
        }
        initwriteStatusBar();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getTopData();
        getHotData();
        this.isInit = true;
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
        this.mNoticeHandler.removeCallbacksAndMessages(null);
        this.mKeyWordsHandler.removeCallbacksAndMessages(null);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportVisible() {
        super.onSupportVisible();
        if (this.isInit) {
            this.mKeyWordsHandler.sendEmptyMessageDelayed(1, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            if (this.isOpenSend) {
                this.mNoticeHandler.sendEmptyMessageDelayed(1, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            } else {
                this.mNoticeHandler.removeCallbacksAndMessages(null);
            }
        }
    }
}
