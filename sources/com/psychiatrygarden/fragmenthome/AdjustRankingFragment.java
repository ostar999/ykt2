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
import com.psychiatrygarden.activity.AdjustRankingActivity;
import com.psychiatrygarden.activity.rank.EntranceResultsActivity;
import com.psychiatrygarden.activity.rank.bean.RankEntranceBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.AdjustRankingContentBean;
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
public class AdjustRankingFragment extends BaseFragment {
    private String bindSchool;
    private String examId;
    public BaseViewPagerAdapter mBaseView;
    private TextView mBtnChoose;
    public CommonNavigator mCommonNavigator;
    private LinearLayout mLyNoChoose;
    public ViewPager mQuestionViewpager;
    private AdjustRankingContentBean mRankingContentBean;
    private MagicIndicator magicIndicator;
    private String position;
    private String title;
    public List<String> mTitleList = new ArrayList();
    public List<BaseViewPagerAdapter.PagerInfo> listviewpage = new ArrayList();
    private int viewPagerPosition = 0;
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.fragmenthome.AdjustRankingFragment.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            AdjustRankingFragment.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            AdjustRankingFragment.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.AdjustRankingFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends CommonNavigatorAdapter {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            AdjustRankingFragment.this.mQuestionViewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            List<String> list = AdjustRankingFragment.this.mTitleList;
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
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseFragment) AdjustRankingFragment.this).mContext, SkinManager.getCurrentSkinType(((BaseFragment) AdjustRankingFragment.this).mContext) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.getLayoutParams().height = ScreenUtil.getPxByDp(context, 44);
            textView.setText(AdjustRankingFragment.this.mTitleList.get(index));
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15485c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.AdjustRankingFragment.4.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    if (SkinManager.getCurrentSkinType(((BaseFragment) AdjustRankingFragment.this).mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(((BaseFragment) AdjustRankingFragment.this).mContext) == 1) {
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
        ajaxParams.put("version", getArguments().getString("version"));
        ajaxParams.put("adjust_type", getArguments().getString("position").equals("0") ? "1" : "0");
        ajaxParams.put("type", getArguments().getString("type"));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getAdjustRankList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.AdjustRankingFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str;
                super.onSuccess((AnonymousClass3) s2);
                try {
                    AdjustRankingFragment.this.mRankingContentBean = (AdjustRankingContentBean) new Gson().fromJson(s2, AdjustRankingContentBean.class);
                    if (AdjustRankingFragment.this.mRankingContentBean.getCode() != 200) {
                        AdjustRankingFragment adjustRankingFragment = AdjustRankingFragment.this;
                        adjustRankingFragment.AlertToast(adjustRankingFragment.mRankingContentBean.getMessage());
                        return;
                    }
                    EventBus.getDefault().post(AdjustRankingFragment.this.mRankingContentBean.getData());
                    ArrayList<RankEntranceBean.DataBean.RanksBean> ranks = AdjustRankingFragment.this.mRankingContentBean.getData().getRanks();
                    ArrayList<RankEntranceBean.DataBean.RanksBean> ranks_major = AdjustRankingFragment.this.mRankingContentBean.getData().getRanks_major();
                    ArrayList<RankEntranceBean.DataBean.RanksBean> ranks_major_direction = AdjustRankingFragment.this.mRankingContentBean.getData().getRanks_major_direction();
                    if (ranks != null && !ranks.isEmpty()) {
                        AdjustRankingFragment.this.mTitleList.add("院系所排名");
                        Bundle bundle = new Bundle();
                        bundle.putString("title", AdjustRankingFragment.this.title);
                        bundle.putString("moudle", "0");
                        bundle.putParcelableArrayList("rankList", ranks);
                        AdjustRankingFragment.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("院系所排名", RankingEntranceFrag.class, bundle));
                    }
                    if (ranks_major != null && !ranks_major.isEmpty()) {
                        AdjustRankingFragment.this.mTitleList.add("专业排名");
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("title", AdjustRankingFragment.this.title);
                        bundle2.putString("moudle", "0");
                        bundle2.putParcelableArrayList("rankList", ranks_major);
                        AdjustRankingFragment.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("专业排名", RankingEntranceFrag.class, bundle2));
                    }
                    if (ranks_major_direction != null && !ranks_major_direction.isEmpty()) {
                        AdjustRankingFragment.this.mTitleList.add("专业方向排名");
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("title", AdjustRankingFragment.this.title);
                        bundle3.putString("moudle", "0");
                        bundle3.putParcelableArrayList("rankList", ranks_major_direction);
                        AdjustRankingFragment.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("专业方向排名", RankingEntranceFrag.class, bundle3));
                    }
                    String str2 = (!TextUtils.isEmpty(AdjustRankingFragment.this.mRankingContentBean.getData().getSchool_department_title()) ? AdjustRankingFragment.this.mRankingContentBean.getData().getSchool_department_title() : AdjustRankingFragment.this.mRankingContentBean.getData().getSchool_title()) + "   " + AdjustRankingFragment.this.mRankingContentBean.getData().getZhuan_xue();
                    if (TextUtils.isEmpty(AdjustRankingFragment.this.mRankingContentBean.getData().getMajor_direction_title())) {
                        str = str2 + "   " + AdjustRankingFragment.this.mRankingContentBean.getData().getMajor_title();
                    } else {
                        str = str2 + "   " + AdjustRankingFragment.this.mRankingContentBean.getData().getMajor_direction_title();
                    }
                    ((AdjustRankingActivity) AdjustRankingFragment.this.getActivity()).setNickName(str);
                    if (AdjustRankingFragment.this.getArguments().getString("position").equals("0")) {
                        ((AdjustRankingActivity) AdjustRankingFragment.this.getActivity()).setAdjustRankTitle(AdjustRankingFragment.this.mRankingContentBean.getData().getSub_title());
                        ((AdjustRankingActivity) AdjustRankingFragment.this.getActivity()).setShareInfo(AdjustRankingFragment.this.mRankingContentBean.getData().getShare_info());
                    } else {
                        ((AdjustRankingActivity) AdjustRankingFragment.this.getActivity()).setAllAdjustRankTitle(AdjustRankingFragment.this.mRankingContentBean.getData().getSub_title());
                        ((AdjustRankingActivity) AdjustRankingFragment.this.getActivity()).setAllShareInfo(AdjustRankingFragment.this.mRankingContentBean.getData().getShare_info());
                    }
                    AdjustRankingFragment.this.initMagicIndicator();
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
        if (this.position.equals("0")) {
            ((AdjustRankingActivity) getActivity()).setmTvNameInfo(this.mRankingContentBean.getData().getDescription_2(), this.mRankingContentBean.getData().getDescription_3(), this.mRankingContentBean.getData().getDescription_4(), 0);
        } else {
            ((AdjustRankingActivity) getActivity()).setmTvNameInfoAll(this.mRankingContentBean.getData().getDescription_2(), this.mRankingContentBean.getData().getDescription_3(), this.mRankingContentBean.getData().getDescription_4(), 0);
        }
        this.mQuestionViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.fragmenthome.AdjustRankingFragment.5
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                if (AdjustRankingFragment.this.position.equals("0")) {
                    ((AdjustRankingActivity) AdjustRankingFragment.this.getActivity()).setmTvNameInfo(AdjustRankingFragment.this.mRankingContentBean.getData().getDescription_2(), AdjustRankingFragment.this.mRankingContentBean.getData().getDescription_3(), AdjustRankingFragment.this.mRankingContentBean.getData().getDescription_4(), i2);
                } else {
                    ((AdjustRankingActivity) AdjustRankingFragment.this.getActivity()).setmTvNameInfoAll(AdjustRankingFragment.this.mRankingContentBean.getData().getDescription_2(), AdjustRankingFragment.this.mRankingContentBean.getData().getDescription_3(), AdjustRankingFragment.this.mRankingContentBean.getData().getDescription_4(), i2);
                }
                AdjustRankingFragment.this.viewPagerPosition = i2;
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
        this.position = getArguments().getString("position", "");
        this.title = getArguments().getString("title", "");
        this.examId = getArguments().getString("exam_id", "");
        this.magicIndicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.mQuestionViewpager = (ViewPager) holder.get(R.id.viewpager);
        this.mLyNoChoose = (LinearLayout) holder.get(R.id.ly_no_choose);
        this.mBtnChoose = (TextView) holder.get(R.id.btn_to_choose);
        getAllList();
        this.mLyNoChoose.setVisibility(8);
        this.mBtnChoose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.AdjustRankingFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AdjustRankingFragment.this.toEntranceResultsActivity();
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
        UMImage uMImage = new UMImage(this.mContext, R.drawable.yikaopm);
        try {
            str = NetworkRequestsURL.mockShareUrl + "title=" + this.mRankingContentBean.getData().getTitle() + "&app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "&exam_id=" + getArguments().getString("exam_id") + "&user_id=" + UserConfig.getUserId();
        } catch (Exception e2) {
            e2.printStackTrace();
            str = "";
        }
        UMWeb uMWeb = new UMWeb(str);
        uMWeb.setDescription(this.mRankingContentBean.getData().getShare_info().getShare_content());
        uMWeb.setThumb(uMImage);
        if (i2 != 3) {
            uMWeb.setTitle(this.mRankingContentBean.getData().getShare_info().getShare_title());
        }
        new ShareAction(getActivity()).withMedia(uMWeb).setPlatform(ProjectApp.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}
