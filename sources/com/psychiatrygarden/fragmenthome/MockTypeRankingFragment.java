package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.RankingActivity;
import com.psychiatrygarden.activity.rank.EntranceResultsActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.RankingContentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MockTypeRankingFragment extends BaseFragment {
    private String bindSchool;
    private String examId;
    public BaseViewPagerAdapter mBaseView;
    private TextView mBtnChoose;
    public CommonNavigator mCommonNavigator;
    private LinearLayout mLyNoChoose;
    public ViewPager mQuestionViewpager;
    private RankingContentBean mRankingContentBean;
    private MagicIndicator magicIndicator;
    private int position;
    private String title;
    public List<String> mTitleList = new ArrayList();
    public List<BaseViewPagerAdapter.PagerInfo> listviewpage = new ArrayList();
    private int viewPagerPosition = 0;
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.fragmenthome.MockTypeRankingFragment.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            MockTypeRankingFragment.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            MockTypeRankingFragment.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.MockTypeRankingFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends CommonNavigatorAdapter {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            MockTypeRankingFragment.this.mQuestionViewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            List<String> list = MockTypeRankingFragment.this.mTitleList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 20.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseFragment) MockTypeRankingFragment.this).mContext, SkinManager.getCurrentSkinType(((BaseFragment) MockTypeRankingFragment.this).mContext) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.getLayoutParams().height = ScreenUtil.getPxByDp(context, 44);
            textView.setText(MockTypeRankingFragment.this.mTitleList.get(index));
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.q8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15938c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.MockTypeRankingFragment.4.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    if (SkinManager.getCurrentSkinType(((BaseFragment) MockTypeRankingFragment.this).mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#575F79"));
                    } else {
                        textView.setTextColor(Color.parseColor("#909499"));
                    }
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
                    if (SkinManager.getCurrentSkinType(((BaseFragment) MockTypeRankingFragment.this).mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#7380A9"));
                    } else {
                        textView.setTextColor(Color.parseColor("#141516"));
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    private void getAllList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getArguments().getString("exam_id"));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.gfRanking, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.MockTypeRankingFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    MockTypeRankingFragment.this.mRankingContentBean = (RankingContentBean) new Gson().fromJson(s2, RankingContentBean.class);
                    if (!MockTypeRankingFragment.this.mRankingContentBean.getCode().equals("200")) {
                        MockTypeRankingFragment mockTypeRankingFragment = MockTypeRankingFragment.this;
                        mockTypeRankingFragment.AlertToast(mockTypeRankingFragment.mRankingContentBean.getMessage());
                        return;
                    }
                    MockTypeRankingFragment.this.mRankingContentBean.getData().setAll(false);
                    EventBus.getDefault().post(MockTypeRankingFragment.this.mRankingContentBean.getData());
                    ArrayList<RankingContentBean.DataBean.RankingBean> ranking = MockTypeRankingFragment.this.mRankingContentBean.getData().getRanking();
                    ArrayList<RankingContentBean.DataBean.RankingBean> ranking_major = MockTypeRankingFragment.this.mRankingContentBean.getData().getRanking_major();
                    ArrayList<RankingContentBean.DataBean.RankingBean> ranking_major_direction = MockTypeRankingFragment.this.mRankingContentBean.getData().getRanking_major_direction();
                    MockTypeRankingFragment.this.mRankingContentBean.getData().getScore();
                    if (ranking != null && !ranking.isEmpty()) {
                        MockTypeRankingFragment.this.mTitleList.add("院系所排名");
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", MockTypeRankingFragment.this.position);
                        bundle.putString("title", MockTypeRankingFragment.this.title);
                        bundle.putString("exam_id", MockTypeRankingFragment.this.examId);
                        bundle.putString("bindSchool", MockTypeRankingFragment.this.bindSchool);
                        bundle.putParcelableArrayList("rankingList", ranking);
                        MockTypeRankingFragment.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("院系所排名", MockRankingFrag.class, bundle));
                    }
                    if (ranking_major != null && !ranking_major.isEmpty()) {
                        MockTypeRankingFragment.this.mTitleList.add("专业排名");
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("position", MockTypeRankingFragment.this.position);
                        bundle2.putString("title", MockTypeRankingFragment.this.title);
                        bundle2.putString("exam_id", MockTypeRankingFragment.this.examId);
                        bundle2.putString("bindSchool", MockTypeRankingFragment.this.bindSchool);
                        bundle2.putParcelableArrayList("rankingList", ranking_major);
                        MockTypeRankingFragment.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("专业排名", MockRankingFrag.class, bundle2));
                    }
                    if (ranking_major_direction != null && !ranking_major_direction.isEmpty()) {
                        MockTypeRankingFragment.this.mTitleList.add("专业方向排名");
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("position", MockTypeRankingFragment.this.position);
                        bundle3.putString("title", MockTypeRankingFragment.this.title);
                        bundle3.putString("exam_id", MockTypeRankingFragment.this.examId);
                        bundle3.putString("bindSchool", MockTypeRankingFragment.this.bindSchool);
                        bundle3.putParcelableArrayList("rankingList", ranking_major_direction);
                        MockTypeRankingFragment.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("专业方向排名", MockRankingFrag.class, bundle3));
                    }
                    MockTypeRankingFragment.this.initMagicIndicator();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initMagicIndicator() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setScrollPivotX(0.65f);
        this.mCommonNavigator.setAdjustMode(true);
        this.mCommonNavigator.setAdapter(new AnonymousClass4());
        this.magicIndicator.setNavigator(this.mCommonNavigator);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), this.listviewpage);
        this.mBaseView = baseViewPagerAdapter;
        this.mQuestionViewpager.setAdapter(baseViewPagerAdapter);
        this.mQuestionViewpager.setOffscreenPageLimit(3);
        ViewPagerHelper.bind(this.magicIndicator, this.mQuestionViewpager);
        this.mQuestionViewpager.setCurrentItem(0);
        this.mQuestionViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.MockTypeRankingFragment.5
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                ((RankingActivity) MockTypeRankingFragment.this.getActivity()).setmTvNameInfo(MockTypeRankingFragment.this.mTitleList.get(i2), i2);
                MockTypeRankingFragment.this.viewPagerPosition = i2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toEntranceResultsActivity() {
        Intent intent = new Intent(ProjectApp.instance(), (Class<?>) EntranceResultsActivity.class);
        intent.putExtra("moudle", Constants.VIA_REPORT_TYPE_SET_AVATAR);
        intent.putExtra("type", "mock");
        intent.putExtra("examId", getArguments().getString("exam_id"));
        intent.putExtra("status", "MOCK");
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.layout_mock_type_ranking;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.bindSchool = getArguments().getString("bindSchool", "");
        this.position = getArguments().getInt("position", 1);
        this.title = getArguments().getString("title", "");
        this.examId = getArguments().getString("exam_id", "");
        this.magicIndicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.mQuestionViewpager = (ViewPager) holder.get(R.id.viewpager);
        this.mLyNoChoose = (LinearLayout) holder.get(R.id.ly_no_choose);
        this.mBtnChoose = (TextView) holder.get(R.id.btn_to_choose);
        if (TextUtils.isEmpty(this.bindSchool) || !this.bindSchool.equals("1")) {
            this.mLyNoChoose.setVisibility(0);
        } else {
            getAllList();
            this.mLyNoChoose.setVisibility(8);
        }
        this.mBtnChoose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.MockTypeRankingFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MockTypeRankingFragment.this.toEntranceResultsActivity();
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("bindSuccess")) {
            this.bindSchool = "1";
            this.mLyNoChoose.setVisibility(8);
            getAllList();
        }
    }

    public void shareAppControl(int i2) {
        String str;
        String str2;
        UMImage uMImage = new UMImage(this.mContext, R.drawable.yikaopm);
        String str3 = getArguments().getInt("position") == 1 ? "考研院校排" : "全部院校排";
        int i3 = this.viewPagerPosition;
        String own_rank_3 = "";
        if (i3 == 0) {
            str = this.mRankingContentBean.getData().getOwn_rank() + "/" + this.mRankingContentBean.getData().getNumber_of_test();
        } else if (i3 == 1) {
            str = this.mRankingContentBean.getData().getOwn_rank_2() + "/" + this.mRankingContentBean.getData().getNumber_of_test_2();
        } else if (i3 == 2) {
            str = this.mRankingContentBean.getData().getOwn_rank_3() + "/" + this.mRankingContentBean.getData().getNumber_of_test_3();
        } else {
            str = "";
        }
        String str4 = "得分" + this.mRankingContentBean.getData().getScore() + "，用时" + this.mRankingContentBean.getData().getExam_time() + "" + str3 + "名" + str;
        try {
            str2 = NetworkRequestsURL.mockShareUrl + "title=" + getArguments().getString("title") + "&app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "&exam_id=" + getArguments().getString("exam_id") + "&user_id=" + UserConfig.getUserId();
        } catch (Exception e2) {
            e2.printStackTrace();
            str2 = "";
        }
        int i4 = this.viewPagerPosition;
        if (i4 == 0) {
            own_rank_3 = this.mRankingContentBean.getData().getOwn_rank();
        } else if (i4 == 1) {
            own_rank_3 = this.mRankingContentBean.getData().getOwn_rank_2();
        } else if (i4 == 2) {
            own_rank_3 = this.mRankingContentBean.getData().getOwn_rank_3();
        }
        String str5 = ("我的" + getArguments().getString("title")) + "排名为第" + own_rank_3 + "名";
        UMWeb uMWeb = new UMWeb(str2);
        if (i2 == 3) {
            str4 = str5;
        }
        uMWeb.setDescription(str4);
        uMWeb.setThumb(uMImage);
        if (i2 != 3) {
            uMWeb.setTitle(str5);
        }
        new ShareAction(getActivity()).withMedia(uMWeb).setPlatform(ProjectApp.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}
