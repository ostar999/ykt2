package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomMasterTable;
import androidx.viewpager2.widget.ViewPager2;
import cn.hutool.core.lang.RegexPool;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.socket.user.PLVSocketUserConstant;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.QuestionWrongCollectionNoteActivity;
import com.psychiatrygarden.activity.SearchQuestionActivity;
import com.psychiatrygarden.activity.knowledge.KnowledgeMapActivity;
import com.psychiatrygarden.activity.mine.report.StudyReportAct;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.activity.online.fragment.NormalQuestionBankFragment;
import com.psychiatrygarden.adapter.BannerQuestionAdsAdapter;
import com.psychiatrygarden.adapter.KingKongAreaItemAdapter;
import com.psychiatrygarden.adapter.NewViewPager2Adapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.KingKongItem;
import com.psychiatrygarden.bean.NewHomeKingKongItem;
import com.psychiatrygarden.bean.NewHomeTodayStatisticsBean;
import com.psychiatrygarden.bean.QuestionCategoryBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.bean.WeekOrMonthReportBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.QuestionBankRefreshEvent;
import com.psychiatrygarden.event.RedDotEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.ranking.StatisticsMainActivity;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.HeaderScrollView;
import com.psychiatrygarden.widget.ScoreCircleView;
import com.psychiatrygarden.widget.ShowVideoDialog;
import com.psychiatrygarden.widget.ViewPager2Indicator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import kotlin.Pair;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionHomeNewFragment extends BaseFragment {
    private static final int POSITION_CENTER = 2;
    private static final int POSITION_LEFT = 1;
    private static final int POSITION_RIGHT = 3;
    private static final int TYPE_MOCK_EXAM = 2;
    private static final int TYPE_QUESTION_SHEET = 1;
    private static final int TYPE_SELF_COMBINE_QUESTION = 3;
    private boolean adStart;
    private Runnable adSwitchRunnable;
    private List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> ads;
    public CustomDialog cd;
    private boolean closeAd;
    private boolean currentCombineQuestion;
    private ViewGroup guideOverlay;
    private View guideView;
    private boolean hasBannerAd;
    private HomepageSmallAdBean homepageSmallAdBean;
    private ImageSwitcher imageSwitcher;
    private boolean isLoadStudyReport;
    private boolean isRefresh;
    private View ivKnowledgeExplore;
    private ImageView iv_delete;
    private AnimationDrawable mAnimationDrawable;
    private ImageView mImgHidden;
    private NewViewPager2Adapter mViewPagerAdapter;
    private SmartRefreshLayout refreshLayout;
    private RelativeLayout rl_buy_book;
    private RecyclerView rvCategory;
    private HeaderScrollView scrollView;
    private String selectId;
    private boolean showKnowledgeTask;
    private ViewPager2Indicator treeIndicator;
    private ViewPager2 viewpager;
    private ViewPager2 vpKingKong;
    private final Map<Integer, View> kingkongItemsViews = new ArrayMap();
    private final Map<Integer, Pair<Integer, Integer>> guideViewMap = new ArrayMap();
    private final Map<Integer, Integer> guideViewPositionMap = new ArrayMap();
    private final Map<Integer, Integer> guideItemPositionMap = new ArrayMap();
    private List<SelectIdentityBean.DataBean> children = new ArrayList();
    private int currentIndex = 0;
    private List<Fragment> mFragmentList = new ArrayList();
    private int totalItemCount = 0;
    private int currentSelectTab = 0;
    private boolean isGuideLayout = false;
    private final BaseQuickAdapter<List<NewHomeKingKongItem>, BaseViewHolder> mAdapter = new BaseQuickAdapter<List<NewHomeKingKongItem>, BaseViewHolder>(R.layout.home_kk_tab) { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.2
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, List<NewHomeKingKongItem> item) {
            RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.rvKingKong);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5, 1, false));
            BaseQuickAdapter adapter = QuestionHomeNewFragment.this.getAdapter();
            adapter.setList(item);
            recyclerView.setAdapter(adapter);
        }
    };
    private final BaseQuickAdapter<QuestionCategoryBean, BaseViewHolder> mCategoryAdapter = new BaseQuickAdapter<QuestionCategoryBean, BaseViewHolder>(R.layout.item_question_category_new) { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.9
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, QuestionCategoryBean item) {
            TypedArray typedArrayObtainStyledAttributes = ((BaseFragment) QuestionHomeNewFragment.this).mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.first_txt_color, R.attr.third_txt_color});
            holder.setText(R.id.tv_column_name, item.getTitle()).setVisible(R.id.img_choose, item.isSelect()).setVisible(R.id.tv_column_tag, (TextUtils.isEmpty(item.getLabel()) || holder.getLayoutPosition() == QuestionHomeNewFragment.this.viewpager.getCurrentItem()) ? false : true).setText(R.id.tv_column_tag, item.getLabel()).setTextColor(R.id.tv_column_name, item.isSelect() ? typedArrayObtainStyledAttributes.getColor(0, ((BaseFragment) QuestionHomeNewFragment.this).mContext.getColor(R.color.first_txt_color)) : typedArrayObtainStyledAttributes.getColor(1, ((BaseFragment) QuestionHomeNewFragment.this).mContext.getColor(R.color.third_txt_color)));
            TextView textView = (TextView) holder.getView(R.id.tv_column_name);
            textView.setTextSize(item.isSelect() ? 16.0f : 14.0f);
            textView.setTypeface(item.isSelect() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            typedArrayObtainStyledAttributes.recycle();
        }
    };
    private final View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.12
        @Override // android.view.View.OnClickListener
        @SuppressLint({"NonConstantResourceId"})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_hidden /* 2131363732 */:
                    AnimUtil.slideIn(QuestionHomeNewFragment.this.rl_buy_book, QuestionHomeNewFragment.this.mImgHidden, 0);
                    QuestionHomeNewFragment.this.closeAd = false;
                    if (QuestionHomeNewFragment.this.adSwitchRunnable != null) {
                        QuestionHomeNewFragment.this.imageSwitcher.postDelayed(QuestionHomeNewFragment.this.adSwitchRunnable, 1000L);
                        break;
                    }
                    break;
                case R.id.iv_delete /* 2131364055 */:
                    AnimUtil.slideOut(QuestionHomeNewFragment.this.rl_buy_book, QuestionHomeNewFragment.this.mImgHidden, view.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
                    SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME, Long.valueOf(System.currentTimeMillis()), ((BaseFragment) QuestionHomeNewFragment.this).mContext);
                    if (!QuestionHomeNewFragment.this.closeAd) {
                        QuestionHomeNewFragment.this.closeAd = true;
                        if (QuestionHomeNewFragment.this.adSwitchRunnable != null) {
                            QuestionHomeNewFragment.this.imageSwitcher.removeCallbacks(QuestionHomeNewFragment.this.adSwitchRunnable);
                            break;
                        }
                    }
                    break;
                case R.id.rl_buy_book /* 2131366510 */:
                    if (QuestionHomeNewFragment.this.rl_buy_book.getVisibility() == 0) {
                        QuestionHomeNewFragment.this.gotoActivity();
                        break;
                    }
                    break;
                case R.id.tv_search /* 2131368539 */:
                    if (QuestionHomeNewFragment.this.isLogin()) {
                        QuestionHomeNewFragment.this.startActivity(new Intent(QuestionHomeNewFragment.this.getActivity(), (Class<?>) SearchQuestionActivity.class));
                        break;
                    }
                    break;
            }
        }
    };
    boolean persent_fragment_show = false;

    /* renamed from: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2) {
            QuestionHomeNewFragment.this.checkShowGuide();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            QuestionHomeNewFragment.this.scrollView.scrollTo(0, 0);
            for (int i2 = 0; i2 < QuestionHomeNewFragment.this.mAdapter.getData().size(); i2++) {
                QuestionHomeNewFragment.this.checkAddGuideView("29", i2);
                QuestionHomeNewFragment.this.checkAddGuideView("38", i2);
                QuestionHomeNewFragment.this.checkAddGuideView("41", i2);
                QuestionHomeNewFragment.this.checkAddGuideView(RoomMasterTable.DEFAULT_ID, i2);
            }
            QuestionHomeNewFragment.this.checkShowGuide();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            QuestionHomeNewFragment.this.treeIndicator.setVisibility(8);
            QuestionHomeNewFragment.this.initTabColumn();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass1) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (TextUtils.equals(jSONObject.optString("code"), "200")) {
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<NewHomeKingKongItem>>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.1.1
                    }.getType());
                    if (!list.isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        QuestionHomeNewFragment.this.guideItemPositionMap.clear();
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            NewHomeKingKongItem newHomeKingKongItem = (NewHomeKingKongItem) list.get(i2);
                            String push_type = newHomeKingKongItem.getPush_type();
                            if (TextUtils.isEmpty(push_type)) {
                                push_type = newHomeKingKongItem.getJpush_type();
                            }
                            if (RoomMasterTable.DEFAULT_ID.equals(push_type)) {
                                QuestionHomeNewFragment.this.guideItemPositionMap.put(2, Integer.valueOf(i2));
                            } else if ("41".equals(push_type)) {
                                QuestionHomeNewFragment.this.guideItemPositionMap.put(2, Integer.valueOf(i2));
                            } else if ("29".equals(push_type)) {
                                QuestionHomeNewFragment.this.guideItemPositionMap.put(1, Integer.valueOf(i2));
                            } else if ("38".equals(push_type)) {
                                QuestionHomeNewFragment.this.guideItemPositionMap.put(3, Integer.valueOf(i2));
                            }
                        }
                        if (list.size() <= 5) {
                            arrayList.add(0, list);
                        } else if (list.size() > 10) {
                            int i3 = 0;
                            while (i3 < list.size()) {
                                int i4 = i3 + 10;
                                arrayList.add(list.subList(i3, Math.min(list.size(), i4)));
                                i3 = i4;
                            }
                        } else if (list.size() < 7) {
                            arrayList.add(list.subList(0, 5));
                            arrayList.add(list.subList(5, list.size()));
                        } else {
                            arrayList.add(list);
                        }
                        QuestionHomeNewFragment.this.totalItemCount = list.size();
                        if (list.size() == 6 || list.size() > 10) {
                            QuestionHomeNewFragment.this.treeIndicator.setVisibility(0);
                            QuestionHomeNewFragment.this.treeIndicator.bindViewPager2(QuestionHomeNewFragment.this.vpKingKong, new ViewPager2Indicator.PageSelectListener() { // from class: com.psychiatrygarden.fragmenthome.gb
                                @Override // com.psychiatrygarden.widget.ViewPager2Indicator.PageSelectListener
                                public final void onPageSelect(int i5) {
                                    this.f15625a.lambda$onSuccess$0(i5);
                                }
                            });
                        } else {
                            QuestionHomeNewFragment.this.treeIndicator.setVisibility(8);
                        }
                        QuestionHomeNewFragment.this.guideViewMap.clear();
                        QuestionHomeNewFragment.this.guideViewPositionMap.clear();
                        QuestionHomeNewFragment.this.mAdapter.setList(arrayList);
                        QuestionHomeNewFragment.this.vpKingKong.setAdapter(QuestionHomeNewFragment.this.mAdapter);
                        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.hb
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f15643c.lambda$onSuccess$1();
                            }
                        }, 300L);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            QuestionHomeNewFragment.this.initTabColumn();
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends BaseQuickAdapter<NewHomeKingKongItem, BaseViewHolder> {
        final /* synthetic */ int val$itemWidth;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(int layoutResId, final int val$itemWidth) {
            super(layoutResId);
            this.val$itemWidth = val$itemWidth;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(NewHomeKingKongItem newHomeKingKongItem, View view) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            if (TextUtils.isEmpty(newHomeKingKongItem.getPush_type())) {
                newHomeKingKongItem.setPush_type(newHomeKingKongItem.getJpush_type());
            }
            if ("44".equals(newHomeKingKongItem.getPush_type())) {
                List data = QuestionHomeNewFragment.this.mCategoryAdapter.getData();
                int i2 = -1;
                for (int i3 = 0; i3 < data.size(); i3++) {
                    String type = ((QuestionCategoryBean) data.get(i3)).getType();
                    if ("1".equals(((QuestionCategoryBean) data.get(i3)).getType())) {
                        i2 = i3;
                    }
                    ((QuestionCategoryBean) data.get(i3)).setSelect("1".equals(type));
                }
                if (i2 == -1) {
                    return;
                }
                QuestionHomeNewFragment.this.mCategoryAdapter.notifyDataSetChanged();
                QuestionHomeNewFragment.this.scroll2KnowledgeChartTab(i2, 0, 1000);
            } else {
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(newHomeKingKongItem));
            }
            String str = "[\"" + newHomeKingKongItem.getId() + "\"]";
            String str2 = "[\"" + newHomeKingKongItem.getName() + "\"]";
            AliyunEvent aliyunEvent = AliyunEvent.VisitModule;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, "", "2");
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, final NewHomeKingKongItem item) {
            if (((View) QuestionHomeNewFragment.this.kingkongItemsViews.get(Integer.valueOf(holder.getLayoutPosition()))) == null) {
                QuestionHomeNewFragment.this.kingkongItemsViews.put(Integer.valueOf(holder.getLayoutPosition()), holder.itemView);
            }
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = holder.getLayoutPosition() > 4 ? SizeUtil.dp2px(((BaseFragment) QuestionHomeNewFragment.this).mContext, 8) : 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = this.val$itemWidth;
            holder.itemView.setLayoutParams(layoutParams);
            holder.setText(R.id.tv_title, item.getName());
            View view = holder.getView(R.id.tv_label);
            LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_live);
            ImageView imageView = (ImageView) holder.getView(R.id.img_live);
            int iDp2px = SizeUtil.dp2px(((BaseFragment) QuestionHomeNewFragment.this).mContext, 5);
            int iDp2px2 = SizeUtil.dp2px(((BaseFragment) QuestionHomeNewFragment.this).mContext, 11);
            if (TextUtils.isEmpty(item.getIs_live_broadcast()) || !item.getIs_live_broadcast().equals("1")) {
                holder.setGone(R.id.tv_label, item.getLabel() == null || item.getLabel().isEmpty());
                holder.getView(R.id.tv_label).setSelected(SkinManager.getCurrentSkinType(((BaseFragment) QuestionHomeNewFragment.this).mContext) == 1);
                holder.setText(R.id.tv_label, item.getLabel());
                linearLayout.setVisibility(8);
                if (item.getLabel() == null || item.getLabel().length() != 1) {
                    view.setPadding(iDp2px, view.getPaddingTop(), iDp2px, view.getPaddingBottom());
                } else {
                    view.setPadding(iDp2px2, view.getPaddingTop(), iDp2px2, view.getPaddingBottom());
                }
            } else {
                imageView.setBackgroundResource(SkinManager.getCurrentSkinType(((BaseFragment) QuestionHomeNewFragment.this).mContext) == 1 ? R.drawable.live_calendar_living_animation_night : R.drawable.live_calendar_living_animation);
                ((AnimationDrawable) imageView.getBackground()).start();
                linearLayout.setVisibility(0);
                view.setVisibility(8);
            }
            final ImageView imageView2 = (ImageView) holder.getView(R.id.iv_icon);
            if (!TextUtils.isEmpty(item.getIcon())) {
                imageView2.setOutlineProvider(new ViewOutlineProvider() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.3.1
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view2, Outline outline) {
                        outline.setRoundRect(0, 0, view2.getWidth(), view2.getHeight(), SizeUtil.dp2px(((BaseFragment) QuestionHomeNewFragment.this).mContext, 12));
                    }
                });
                imageView2.setClipToOutline(true);
                if (item.getIcon().matches("(?i).*\\.gif$")) {
                    Glide.with(((BaseFragment) QuestionHomeNewFragment.this).mContext).asGif().load(item.getIcon()).into((RequestBuilder<GifDrawable>) new CustomTarget<GifDrawable>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.3.2
                        @Override // com.bumptech.glide.request.target.Target
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override // com.bumptech.glide.request.target.Target
                        public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                            onResourceReady((GifDrawable) resource, (Transition<? super GifDrawable>) transition);
                        }

                        public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                            imageView2.setImageDrawable(resource);
                            resource.setLoopCount(-1);
                            resource.start();
                        }
                    });
                } else {
                    GlideUtils.loadImage(((BaseFragment) QuestionHomeNewFragment.this).mContext, item.getIcon(), imageView2);
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ib
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f15668c.lambda$convert$0(item, view2);
                }
            });
        }
    }

    public static /* synthetic */ int access$2704(QuestionHomeNewFragment questionHomeNewFragment) {
        int i2 = questionHomeNewFragment.currentIndex + 1;
        questionHomeNewFragment.currentIndex = i2;
        return i2;
    }

    private float calculateGuideViewX(int targetPosition, int screenWidth, float density, float guideViewWidth) {
        float f2 = density * 10.0f;
        float f3 = screenWidth;
        float f4 = (targetPosition + 0.5f) * (f3 / 5.0f);
        if (targetPosition > 1) {
            f2 = targetPosition == 2 ? guideViewWidth / 2.0f : guideViewWidth - f2;
        }
        float fDp2px = (f4 - f2) - (targetPosition == 2 ? 0 : SizeUtil.dp2px(ProjectApp.instance(), 7));
        if (targetPosition > 2) {
            fDp2px += SizeUtil.dp2px(ProjectApp.instance(), 15);
        }
        return Math.max(0.0f, Math.min(fDp2px, f3 - guideViewWidth));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAddGuideView(String type, int pagePosition) {
        List<Integer> listCheckShowGuide = checkShowGuide(type, pagePosition);
        int i2 = ProjectApp.instance().getResources().getDisplayMetrics().widthPixels;
        for (Integer num : listCheckShowGuide) {
            int iIntValue = num.intValue() % 5;
            int i3 = 2;
            int i4 = (iIntValue == 0 || iIntValue == 1) ? 1 : (iIntValue == 3 || iIntValue == 4) ? 3 : 2;
            if ("29".equals(type)) {
                i3 = 1;
            } else if (!RoomMasterTable.DEFAULT_ID.equals(type) && !"41".equals(type)) {
                i3 = 3;
            }
            int iCalculateGuideViewX = (int) calculateGuideViewX(iIntValue, i2, ProjectApp.instance().getResources().getDisplayMetrics().density, SizeUtil.dp2px(ProjectApp.instance(), i3 != 3 ? 200 : 220) * 1.0f);
            this.guideViewPositionMap.put(Integer.valueOf(i3), Integer.valueOf(i4));
            int iDp2px = this.hasBannerAd ? SizeUtil.dp2px(ProjectApp.instance(), 114) : 0;
            int iDp2px2 = SizeUtil.dp2px(ProjectApp.instance(), 74);
            int iDp2px3 = iDp2px + iDp2px2 + SizeUtil.dp2px(ProjectApp.instance(), 10);
            int iIntValue2 = (num.intValue() % 10) / 5;
            if (iIntValue2 != 0 && this.totalItemCount > 6 && iIntValue2 == 1) {
                iDp2px3 += SizeUtil.dp2px(ProjectApp.instance(), 8) + iDp2px2;
            }
            this.guideViewMap.put(Integer.valueOf(i3), new Pair<>(Integer.valueOf(iCalculateGuideViewX), Integer.valueOf(iDp2px3)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkShowChartGuideVideo(QuestionCategoryBean item) {
        if (TextUtils.isEmpty(item.getVid()) || SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_CHART_GUIDE, false, this.mContext)) {
            return;
        }
        new ShowVideoDialog(item.getVid(), null, false, null, new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.fragmenthome.qa
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f15941c.lambda$checkShowChartGuideVideo$14();
            }
        }).showDialog(this.mContext, getActivity().getWindow().getDecorView());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkShowGuide() {
        Pair<Integer, Integer> pair = this.guideViewMap.get(1);
        if (pair == null && (pair = this.guideViewMap.get(2)) == null) {
            pair = this.guideViewMap.get(3);
        }
        if (pair == null || showGuide(1) || showGuide(2)) {
            return;
        }
        showGuide(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BaseQuickAdapter<NewHomeKingKongItem, BaseViewHolder> getAdapter() {
        return new AnonymousClass3(R.layout.item_home_king_kong_child, getResources().getDisplayMetrics().widthPixels / 5);
    }

    private void getIntentData(String type, Context mContext) {
        if (this.children.isEmpty() || TextUtils.isEmpty(UserConfig.getUserId())) {
            return;
        }
        if ("error".equals(type)) {
            pointCount(mContext, "5");
        } else if ("collection".equals(type)) {
            pointCount(mContext, "7");
        } else if ("note".equals(type)) {
            pointCount(mContext, Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        } else if ("praise".equals(type)) {
            pointCount(mContext, Constants.VIA_REPORT_TYPE_JOININ_GROUP);
        } else if (ClientCookie.COMMENT_ATTR.equals(type)) {
            pointCount(mContext, Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
        }
        for (SelectIdentityBean.DataBean dataBean : this.mCategoryAdapter.getItem(this.viewpager.getCurrentItem()).getChildren()) {
            if (dataBean.isSelect()) {
                QuestionWrongCollectionNoteActivity.INSTANCE.navigationToQuestionWrongCollectionNote(mContext, type, dataBean.getIdentity_id(), dataBean.getModule_type(), dataBean.getCategory(), dataBean.getExport_func_identity_id(), "");
            }
        }
    }

    private void getQuestionCategory() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance()));
        YJYHttpUtils.get(ProjectApp.instance(), NetworkRequestsURL.questionCategory, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionHomeNewFragment.this.showKnowledgeTask = false;
                QuestionHomeNewFragment.this.initTabColumn();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.questionCategoryData, jSONObject.optString("data"), ProjectApp.instance());
                        QuestionHomeNewFragment.this.initTabColumn();
                    }
                } catch (Exception unused) {
                    QuestionHomeNewFragment.this.showKnowledgeTask = false;
                    QuestionHomeNewFragment.this.initTabColumn();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBanner(final RelativeLayout mRlQuestionTopAd, final Context mContext) {
        final HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_BANNER_AD, mContext, ""), HomepageSmallAdBean.DataDTO.DataAd.class);
        if (dataAd == null || dataAd.getAds().isEmpty()) {
            mRlQuestionTopAd.setVisibility(8);
            return;
        }
        this.hasBannerAd = true;
        if ((SharePreferencesUtils.readLongConfig("DISMESS_TIME_QUESTION_TOP_AD", mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig("DISMESS_TIME_QUESTION_TOP_AD", mContext, 0L).longValue()) / 1000) - dataAd.getTime_interval() : 0L) < 0) {
            return;
        }
        mRlQuestionTopAd.setVisibility(0);
        Banner banner = (Banner) mRlQuestionTopAd.findViewById(R.id.banner);
        ImageView imageView = (ImageView) mRlQuestionTopAd.findViewById(R.id.iv_ad_close);
        if (dataAd.getForce().equals("1")) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.eb
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuestionHomeNewFragment.lambda$initBanner$10(mContext, mRlQuestionTopAd, view);
                }
            });
        }
        int pxByDp = mContext.getResources().getDisplayMetrics().widthPixels - ScreenUtil.getPxByDp(mContext, 40);
        int i2 = pxByDp / 3;
        if (AndroidBaseUtils.isPad(mContext) && AndroidBaseUtils.isCurOriLand(mContext)) {
            i2 = pxByDp / 5;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mContext.getResources().getDisplayMetrics().widthPixels, i2);
        layoutParams.setMargins(0, ScreenUtil.getPxByDp(mContext, 10), 0, 0);
        banner.setLayoutParams(layoutParams);
        banner.addBannerLifecycleObserver(this).setLoopTime(5000L).setAdapter(new BannerQuestionAdsAdapter(dataAd.getAds())).setPageTransformer(new AlphaPageTransformer()).setIndicator(new CircleIndicator(mContext)).setIndicatorRadius(ScreenUtil.getPxByDp(mContext, 4)).setIndicatorNormalColor(Color.parseColor("#88FFFFFF")).setIndicatorSelectedColor(ContextCompat.getColor(mContext, R.color.app_theme_red));
        banner.setOnBannerListener(new OnBannerListener() { // from class: com.psychiatrygarden.fragmenthome.fb
            @Override // com.youth.banner.listener.OnBannerListener
            public final void OnBannerClick(Object obj, int i3) {
                this.f15594a.lambda$initBanner$11(dataAd, mContext, (HomepageSmallAdBean.DataDTO.DataAd.AdsDTO) obj, i3);
            }
        });
    }

    private void initCategory() {
        boolean z2;
        BaseFragment normalQuestionBankFragment;
        this.mFragmentList.clear();
        final List<QuestionCategoryBean> data = this.mCategoryAdapter.getData();
        ArrayList arrayList = new ArrayList();
        List<SelectIdentityBean.DataBean> children = data.get(0).getChildren();
        children.get(0).setSelect(true);
        this.children = children;
        this.showKnowledgeTask = false;
        Iterator<QuestionCategoryBean> it = data.iterator();
        while (true) {
            if (it.hasNext()) {
                if ("1".equals(it.next().getType())) {
                    z2 = true;
                    break;
                }
            } else {
                z2 = false;
                break;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (QuestionCategoryBean questionCategoryBean : data) {
            Bundle bundle = new Bundle();
            bundle.putString("children", new Gson().toJson(questionCategoryBean.getChildren()));
            bundle.putString("question_bank_id", questionCategoryBean.getId());
            if ("1".equals(questionCategoryBean.getType())) {
                normalQuestionBankFragment = new QuestionKnowledgeChartFragment();
                normalQuestionBankFragment.setArguments(bundle);
                SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_CHART_ID, questionCategoryBean.getId(), this.mContext);
                this.showKnowledgeTask = true;
            } else {
                normalQuestionBankFragment = new NormalQuestionBankFragment();
                if (!z2) {
                    bundle.putBoolean("hasKnowledgeTab", false);
                }
                normalQuestionBankFragment.setArguments(bundle);
            }
            arrayList.add(normalQuestionBankFragment);
            questionCategoryBean.getChildren().get(0).setSelect(true);
        }
        for (int i2 = 0; i2 < data.size(); i2++) {
            String id = data.get(i2).getId();
            if (id == null || !id.matches(RegexPool.NUMBERS)) {
                arrayList2.add(Long.valueOf(i2));
            } else {
                arrayList2.add(Long.valueOf(Long.parseLong(id)));
            }
        }
        this.mFragmentList.addAll(arrayList);
        this.viewpager.setSaveEnabled(false);
        this.viewpager.setOffscreenPageLimit(1);
        NewViewPager2Adapter newViewPager2Adapter = new NewViewPager2Adapter(this.mFragmentList, arrayList2, getChildFragmentManager(), getLifecycle());
        this.mViewPagerAdapter = newViewPager2Adapter;
        this.viewpager.setAdapter(newViewPager2Adapter);
        this.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.10
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                int i3 = 0;
                while (i3 < data.size()) {
                    ((QuestionCategoryBean) data.get(i3)).setSelect(i3 == position);
                    if (((QuestionCategoryBean) data.get(i3)).isSelect()) {
                        QuestionHomeNewFragment.this.selectId = ((QuestionCategoryBean) data.get(i3)).getId();
                        QuestionHomeNewFragment.this.children = ((QuestionCategoryBean) data.get(i3)).getChildren();
                    }
                    i3++;
                }
                QuestionHomeNewFragment.this.mCategoryAdapter.notifyDataSetChanged();
                QuestionHomeNewFragment questionHomeNewFragment = QuestionHomeNewFragment.this;
                questionHomeNewFragment.checkShowChartGuideVideo((QuestionCategoryBean) questionHomeNewFragment.mCategoryAdapter.getItem(position));
            }
        });
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext);
        int i3 = 0;
        while (true) {
            if (i3 >= data.size()) {
                break;
            }
            QuestionCategoryBean questionCategoryBean2 = data.get(i3);
            if (TextUtils.equals(questionCategoryBean2.getId(), this.selectId)) {
                this.viewpager.setCurrentItem(i3, false);
                SharePreferencesUtils.writeStrConfig(CommonParameter.CURRENT_QUESTION_CATEGORY_ID + strConfig, questionCategoryBean2.getId(), this.mContext);
                break;
            }
            i3++;
        }
        if (this.showKnowledgeTask) {
            if (this.isLoadStudyReport) {
                updateLayoutParams();
            } else {
                loadEstimateInfo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initKingKong() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionKingKongArea, new AjaxParams(), new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initKingKongArea(LinearLayout llKingKongArea) {
        llKingKongArea.setVisibility(0);
        RecyclerView recyclerView = (RecyclerView) llKingKongArea.findViewById(R.id.rvKingKong);
        if (recyclerView.getAdapter() == null) {
            final KingKongAreaItemAdapter kingKongAreaItemAdapter = new KingKongAreaItemAdapter(R.layout.item_kingkong_area);
            ArrayList arrayList = new ArrayList();
            arrayList.add(KingKongItem.WRONG_QUESTION);
            arrayList.add(KingKongItem.COLLECT_QUESTION);
            arrayList.add(KingKongItem.NOTE_QUESTION);
            arrayList.add(KingKongItem.COMMENT_QUESTION);
            arrayList.add(KingKongItem.LIKE_QUESTION);
            recyclerView.setAdapter(kingKongAreaItemAdapter);
            kingKongAreaItemAdapter.setList(arrayList);
            kingKongAreaItemAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.ua
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f16045c.lambda$initKingKongArea$12(kingKongAreaItemAdapter, baseQuickAdapter, view, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initStatistics(int learnTime, int doQuestionNum, String correctionRatio, String updateTime) {
        int i2 = learnTime / 60;
        int i3 = learnTime % 60;
        int iSp2px = SizeUtil.sp2px(this.mContext, 12);
        int iSp2px2 = SizeUtil.sp2px(this.mContext, 16);
        ((TextView) getViewHolder().get(R.id.tv_update_time)).setText("更新时间 " + updateTime);
        TextView textView = (TextView) getViewHolder().get(R.id.tv_learn_time);
        getViewHolder().get(R.id.ll_bottom).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ta
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16013c.lambda$initStatistics$9(view);
            }
        });
        TextView textView2 = (TextView) getViewHolder().get(R.id.tv_do_num);
        TextView textView3 = (TextView) getViewHolder().get(R.id.tv_correction_percent);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (i2 > 0) {
            spannableStringBuilder.append((CharSequence) String.valueOf(i2));
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(iSp2px2), 0, spannableStringBuilder.length(), 18);
            spannableStringBuilder.append((CharSequence) "h").setSpan(new AbsoluteSizeSpan(iSp2px), spannableStringBuilder.length(), spannableStringBuilder.length(), 17);
        }
        spannableStringBuilder.append((CharSequence) String.valueOf(i3)).setSpan(new AbsoluteSizeSpan(iSp2px2), spannableStringBuilder.length(), spannableStringBuilder.length(), 17);
        spannableStringBuilder.append((CharSequence) "m").setSpan(new AbsoluteSizeSpan(iSp2px), spannableStringBuilder.length(), spannableStringBuilder.length(), 17);
        textView.setText(spannableStringBuilder);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(String.valueOf(doQuestionNum));
        spannableStringBuilder2.setSpan(new AbsoluteSizeSpan(iSp2px2), 0, spannableStringBuilder2.length(), 18);
        spannableStringBuilder2.append((CharSequence) "题").setSpan(new AbsoluteSizeSpan(iSp2px), spannableStringBuilder2.length() - 1, spannableStringBuilder2.length(), 18);
        textView2.setText(spannableStringBuilder2);
        if (correctionRatio != null) {
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(correctionRatio);
            spannableStringBuilder3.setSpan(new AbsoluteSizeSpan(iSp2px2), 0, spannableStringBuilder3.length(), 18);
            spannableStringBuilder3.append((CharSequence) "%").setSpan(new AbsoluteSizeSpan(iSp2px), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 18);
            textView3.setText(spannableStringBuilder3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSwitcher() {
        HomepageSmallAdBean homepageSmallAdBean = this.homepageSmallAdBean;
        if (homepageSmallAdBean == null || this.imageSwitcher == null) {
            this.rl_buy_book.setVisibility(8);
            return;
        }
        List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> ads = homepageSmallAdBean.getData().getLower_right_corner_ad().getAds();
        this.ads = ads;
        if (ads == null || ads.size() <= 0) {
            this.rl_buy_book.setVisibility(8);
            return;
        }
        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME, this.mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME, this.mContext, 0L).longValue()) / 1000) - this.homepageSmallAdBean.getData().getLower_right_corner_ad().getTime_interval() : 0L) < 0) {
            this.rl_buy_book.setVisibility(8);
        }
        if (this.imageSwitcher.getChildCount() > 0) {
            this.imageSwitcher.removeAllViews();
            this.currentIndex = 0;
        }
        if (this.ads.size() > 1) {
            this.imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_right_in));
            this.imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
        }
        this.imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() { // from class: com.psychiatrygarden.fragmenthome.xa
            @Override // android.widget.ViewSwitcher.ViewFactory
            public final View makeView() {
                return this.f16117a.lambda$initSwitcher$8();
            }
        });
        loadAdSwitcher();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkShowChartGuideVideo$14() {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_CHART_GUIDE, true, this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initBanner$10(Context context, RelativeLayout relativeLayout, View view) {
        SharePreferencesUtils.writeLongConfig("DISMESS_TIME_QUESTION_TOP_AD", Long.valueOf(System.currentTimeMillis()), context);
        relativeLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBanner$11(HomepageSmallAdBean.DataDTO.DataAd dataAd, Context context, HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO, int i2) {
        if (dataAd.getAds().isEmpty()) {
            return;
        }
        pointCount(context, "1");
        try {
            HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO2 = dataAd.getAds().get(i2);
            if (!"44".equals(adsDTO2.getExtra().getPush_type())) {
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(adsDTO2.getExtra()));
                return;
            }
            List<QuestionCategoryBean> data = this.mCategoryAdapter.getData();
            int i3 = -1;
            for (int i4 = 0; i4 < data.size(); i4++) {
                String type = data.get(i4).getType();
                if ("1".equals(data.get(i4).getType())) {
                    i3 = i4;
                }
                data.get(i4).setSelect("1".equals(type));
            }
            if (i3 == -1) {
                return;
            }
            this.mCategoryAdapter.notifyDataSetChanged();
            scroll2KnowledgeChartTab(i3, 0, 1000);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initKingKongArea$12(KingKongAreaItemAdapter kingKongAreaItemAdapter, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (TextUtils.isEmpty(UserConfig.getUserId())) {
            startActivity(new Intent(getContext(), (Class<?>) LoginActivity.class));
        } else {
            getIntentData(kingKongAreaItemAdapter.getItem(i2).getType(), getContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initStatistics$9(View view) {
        if (isLogin()) {
            StatisticsMainActivity.INSTANCE.navigationToStatisticsMain(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ View lambda$initSwitcher$8() {
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return imageView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTabColumn$13(List list, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (list.size() == 1) {
            return;
        }
        this.viewpager.setCurrentItem(i2, false);
        List<QuestionCategoryBean> data = this.mCategoryAdapter.getData();
        int i3 = 0;
        while (i3 < data.size()) {
            data.get(i3).setSelect(i3 == i2);
            if (data.get(i3).isSelect()) {
                this.children = data.get(i3).getChildren();
            }
            i3++;
        }
        this.rvCategory.scrollToPosition(i2);
        this.mCategoryAdapter.notifyDataSetChanged();
        checkShowChartGuideVideo(this.mCategoryAdapter.getItem(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$initViews$0(View view, MotionEvent motionEvent) {
        return view.getVisibility() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(final RefreshLayout refreshLayout) {
        if (this.children.isEmpty()) {
            SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
            Objects.requireNonNull(refreshLayout);
            smartRefreshLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.sa
                @Override // java.lang.Runnable
                public final void run() {
                    refreshLayout.finishRefresh();
                }
            }, 300L);
            return;
        }
        this.isRefresh = true;
        initKingKong();
        loadStatistics();
        getQuestionCategory();
        SmartRefreshLayout smartRefreshLayout2 = this.refreshLayout;
        Objects.requireNonNull(refreshLayout);
        smartRefreshLayout2.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.sa
            @Override // java.lang.Runnable
            public final void run() {
                refreshLayout.finishRefresh();
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        if (!CommonUtil.isFastClick() && isLogin()) {
            StatisticsMainActivity.INSTANCE.navigationToStatisticsMain(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        if (isLogin() && this.showKnowledgeTask) {
            KnowledgeMapActivity.INSTANCE.navigationToKnowledgeMap(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(View view) {
        if (isLogin()) {
            StudyReportAct.newIntent(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSupportVisible$6(List list, String str, int i2) {
        List<SelectIdentityBean.DataBean> children;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            QuestionCategoryBean questionCategoryBean = (QuestionCategoryBean) it.next();
            if ("1".equals(questionCategoryBean.getType()) && (children = questionCategoryBean.getChildren()) != null && !children.isEmpty()) {
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i4 >= children.size()) {
                        break;
                    }
                    if (TextUtils.equals(children.get(i4).getId(), str)) {
                        while (true) {
                            if (i3 < i2) {
                                Fragment fragment = this.mFragmentList.get(i3);
                                if (fragment instanceof QuestionKnowledgeChartFragment) {
                                    ((QuestionKnowledgeChartFragment) fragment).switch2Chapter(i4);
                                    break;
                                }
                                i3++;
                            }
                        }
                    } else {
                        if ("-1".equals(str)) {
                            int i5 = 0;
                            while (true) {
                                if (i5 < i2) {
                                    Fragment fragment2 = this.mFragmentList.get(i5);
                                    if (fragment2 instanceof QuestionKnowledgeChartFragment) {
                                        ((QuestionKnowledgeChartFragment) fragment2).switch2Chapter(0);
                                        break;
                                    }
                                    i5++;
                                }
                            }
                        }
                        i4++;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scroll2KnowledgeChartTab$7(int i2, int i3) {
        this.viewpager.setCurrentItem(i2, true);
        Rect rect = new Rect();
        this.rvCategory.getDrawingRect(rect);
        this.scrollView.offsetDescendantRectToMyCoords(this.rvCategory, rect);
        this.scrollView.smoothScrollTo(0, rect.top - this.scrollView.getPaddingTop(), i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGuide$5(int i2, View view) {
        this.guideViewMap.remove(Integer.valueOf(i2));
        this.guideViewPositionMap.remove(Integer.valueOf(i2));
        if (i2 == 3) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_SELF_COMBINE_QUESTION_TIP, true, this.mContext);
            if (!showGuide(1) && !showGuide(2)) {
                this.guideOverlay.setVisibility(8);
                EventBus.getDefault().post("dismiss_home_bot_mask");
            }
        } else if (i2 == 2) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_MOCK_EXAM_TIP, true, this.mContext);
            if (!showGuide(1) && !showGuide(3)) {
                this.guideOverlay.setVisibility(8);
                EventBus.getDefault().post("dismiss_home_bot_mask");
            }
        } else if (i2 == 1) {
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.SHOW_ANSWER_SHEET_TIP, true, this.mContext);
            if (!showGuide(2) && !showGuide(3)) {
                this.guideOverlay.setVisibility(8);
                EventBus.getDefault().post("dismiss_home_bot_mask");
            }
        }
        if (this.guideViewMap.isEmpty()) {
            this.guideOverlay.setVisibility(8);
            EventBus.getDefault().post("dismiss_home_bot_mask");
            this.refreshLayout.setEnableRefresh(true);
        }
    }

    private void loadAdSwitcher() {
        if (this.adSwitchRunnable == null) {
            Runnable runnable = new Runnable() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.6
                @Override // java.lang.Runnable
                public void run() {
                    if (QuestionHomeNewFragment.this.ads != null) {
                        QuestionHomeNewFragment questionHomeNewFragment = QuestionHomeNewFragment.this;
                        int iAccess$2704 = 0;
                        if (questionHomeNewFragment.ads.size() != 1 && QuestionHomeNewFragment.this.currentIndex != QuestionHomeNewFragment.this.ads.size() - 1) {
                            iAccess$2704 = QuestionHomeNewFragment.access$2704(QuestionHomeNewFragment.this);
                        }
                        questionHomeNewFragment.currentIndex = iAccess$2704;
                        if (QuestionHomeNewFragment.this.getActivity() != null && QuestionHomeNewFragment.this.currentIndex >= 0 && QuestionHomeNewFragment.this.currentIndex < QuestionHomeNewFragment.this.ads.size()) {
                            Glide.with(QuestionHomeNewFragment.this.getActivity()).load((Object) GlideUtils.generateUrl(((HomepageSmallAdBean.DataDTO.DataAd.AdsDTO) QuestionHomeNewFragment.this.ads.get(QuestionHomeNewFragment.this.currentIndex)).getImg())).override(ScreenUtil.getPxByDp(QuestionHomeNewFragment.this.getContext(), 100), ScreenUtil.getPxByDp(QuestionHomeNewFragment.this.getContext(), 100)).placeholder(R.drawable.app_icon).into((RequestBuilder) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.6.1
                                @Override // com.bumptech.glide.request.target.Target
                                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                                    onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
                                }

                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    QuestionHomeNewFragment.this.imageSwitcher.setImageDrawable(resource);
                                    QuestionHomeNewFragment.this.rl_buy_book.setVisibility(0);
                                    if (QuestionHomeNewFragment.this.homepageSmallAdBean.getData().getLower_right_corner_ad().getForce().equals("1")) {
                                        QuestionHomeNewFragment.this.iv_delete.setVisibility(8);
                                    } else {
                                        QuestionHomeNewFragment.this.iv_delete.setVisibility(0);
                                    }
                                }
                            });
                        }
                        if (QuestionHomeNewFragment.this.ads.size() > 1) {
                            QuestionHomeNewFragment.this.imageSwitcher.postDelayed(this, 5000L);
                        }
                    }
                }
            };
            this.adSwitchRunnable = runnable;
            this.imageSwitcher.postDelayed(runnable, 1000L);
        }
        this.adStart = true;
    }

    private void loadEstimateInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "1");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyReport, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionHomeNewFragment.this.showKnowledgeTask = false;
                QuestionHomeNewFragment.this.updateLayoutParams();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws Throwable {
                super.onSuccess((AnonymousClass4) s2);
                QuestionHomeNewFragment.this.isLoadStudyReport = true;
                try {
                    WeekOrMonthReportBean weekOrMonthReportBean = (WeekOrMonthReportBean) new Gson().fromJson(s2, WeekOrMonthReportBean.class);
                    if (!weekOrMonthReportBean.getCode().equals("200") || weekOrMonthReportBean.getData() == null || weekOrMonthReportBean.getData().getEstimated_score_detail() == null) {
                        return;
                    }
                    WeekOrMonthReportBean.EstimatedScoreDataBean estimated_score_detail = weekOrMonthReportBean.getData().getEstimated_score_detail();
                    estimated_score_detail.getPass_score();
                    estimated_score_detail.getYesterday_score();
                    String today_score = estimated_score_detail.getToday_score();
                    String all_score = estimated_score_detail.getAll_score();
                    estimated_score_detail.getTrend();
                    if (today_score == null) {
                        today_score = "0";
                    }
                    if (all_score == null) {
                        all_score = "0";
                    }
                    ((ScoreCircleView) QuestionHomeNewFragment.this.getViewHolder().get(R.id.scoreView)).setScore(Integer.parseInt(today_score), Integer.parseInt(all_score));
                    QuestionHomeNewFragment.this.updateLayoutParams();
                } catch (Exception e2) {
                    if (TextUtils.isEmpty(e2.getMessage())) {
                        return;
                    }
                    LogUtils.w("Waring", e2.getMessage());
                }
            }
        });
    }

    private void loadStatistics() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.newHomeTodayStatistics, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals(jSONObject.optString("code"), "200")) {
                        NewHomeTodayStatisticsBean newHomeTodayStatisticsBean = (NewHomeTodayStatisticsBean) new Gson().fromJson(jSONObject.optString("data"), NewHomeTodayStatisticsBean.class);
                        QuestionHomeNewFragment.this.initStatistics(Integer.parseInt(newHomeTodayStatisticsBean.getDuration_days()), Integer.parseInt(newHomeTodayStatisticsBean.getQuestion_all_num()), newHomeTodayStatisticsBean.getRight_count(), newHomeTodayStatisticsBean.getRefresh_time());
                        ((ScoreCircleView) QuestionHomeNewFragment.this.getViewHolder().get(R.id.scoreView)).setScore(Integer.parseInt(newHomeTodayStatisticsBean.getToday_score()), Integer.parseInt(newHomeTodayStatisticsBean.getAll_score()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static QuestionHomeNewFragment newInstance() {
        Bundle bundle = new Bundle();
        QuestionHomeNewFragment questionHomeNewFragment = new QuestionHomeNewFragment();
        questionHomeNewFragment.setArguments(bundle);
        return questionHomeNewFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scroll2KnowledgeChartTab(final int tabPosition, int postDelay, final int duration) {
        this.scrollView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.va
            @Override // java.lang.Runnable
            public final void run() {
                this.f16072c.lambda$scroll2KnowledgeChartTab$7(tabPosition, duration);
            }
        }, postDelay);
    }

    private boolean showGuide(final int guideType) {
        Pair<Integer, Integer> pair = this.guideViewMap.get(Integer.valueOf(guideType));
        Integer num = this.guideViewPositionMap.get(Integer.valueOf(guideType));
        Integer num2 = this.guideItemPositionMap.get(Integer.valueOf(guideType));
        int currentItem = this.vpKingKong.getCurrentItem();
        if (pair == null || num == null || num2 == null) {
            return false;
        }
        if (this.totalItemCount == 6 && currentItem == 0 && num2.intValue() == 5) {
            this.guideOverlay.setVisibility(8);
            EventBus.getDefault().post("dismiss_home_bot_mask");
            this.refreshLayout.setEnableRefresh(true);
            return false;
        }
        if (this.totalItemCount > 10) {
            if (num2.intValue() >= (currentItem + 1) * 10) {
                this.guideOverlay.setVisibility(8);
                EventBus.getDefault().post("dismiss_home_bot_mask");
                this.refreshLayout.setEnableRefresh(true);
                return false;
            }
            if ((num2.intValue() + 1) / 10 != currentItem) {
                this.guideOverlay.setVisibility(8);
                EventBus.getDefault().post("dismiss_home_bot_mask");
                this.refreshLayout.setEnableRefresh(true);
                return false;
            }
        }
        TextView textView = (TextView) this.guideView.findViewById(R.id.tv_content);
        LinearLayout linearLayout = (LinearLayout) this.guideView.findViewById(R.id.ll_content);
        textView.setText(guideType == 2 ? "模考移到这里了" : guideType == 1 ? "题单移到这里了" : "自主组题移到这里了");
        View viewFindViewById = this.guideView.findViewById(R.id.iv_down_triangle);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
        layoutParams.leftMargin = 0;
        layoutParams.rightMargin = 0;
        ViewGroup.LayoutParams layoutParams2 = linearLayout.getLayoutParams();
        boolean z2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams;
        if (z2) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams2;
            marginLayoutParams.leftMargin = 0;
            linearLayout.setLayoutParams(marginLayoutParams);
        }
        if (num.intValue() == 1) {
            layoutParams.gravity = 3;
            layoutParams.leftMargin = SizeUtil.dp2px(this.mContext, 12);
        } else if (num.intValue() == 2) {
            layoutParams.gravity = 17;
        } else if (num.intValue() == 3) {
            layoutParams.rightMargin = SizeUtil.dp2px(this.mContext, 12);
            layoutParams.gravity = GravityCompat.END;
            if (z2) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
                marginLayoutParams2.leftMargin = SizeUtil.dp2px(this.mContext, 10);
                linearLayout.setLayoutParams(marginLayoutParams2);
            }
        }
        viewFindViewById.setLayoutParams(layoutParams);
        this.guideView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.wa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16097c.lambda$showGuide$5(guideType, view);
            }
        });
        if (this.persent_fragment_show) {
            this.guideOverlay.setVisibility(0);
            this.guideView.setVisibility(0);
            this.guideOverlay.setBackgroundColor(getResources().getColor(R.color.dialog_transparent_bg));
            EventBus.getDefault().post("show_home_bot_mask");
            this.refreshLayout.setEnableRefresh(false);
        }
        this.guideView.setX(pair.getFirst().intValue());
        this.guideView.setY(pair.getSecond().intValue());
        return true;
    }

    private void stopAdSwitcher() {
        this.adStart = false;
        Runnable runnable = this.adSwitchRunnable;
        if (runnable != null) {
            this.imageSwitcher.removeCallbacks(runnable);
            this.adSwitchRunnable = null;
        }
        this.rl_buy_book.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLayoutParams() {
        if (this.showKnowledgeTask) {
            AnimationDrawable animationDrawable = this.mAnimationDrawable;
            if (animationDrawable != null) {
                animationDrawable.start();
            }
        } else {
            AnimationDrawable animationDrawable2 = this.mAnimationDrawable;
            if (animationDrawable2 != null && animationDrawable2.isRunning()) {
                this.mAnimationDrawable.stop();
            }
        }
        this.ivKnowledgeExplore.setVisibility(this.showKnowledgeTask ? 0 : 8);
        ((ScoreCircleView) getViewHolder().get(R.id.scoreView)).setVisibility(this.showKnowledgeTask ? 0 : 8);
    }

    public boolean checkCurrentIsCombineQuestion() {
        int currentItem = this.viewpager.getCurrentItem();
        if (this.children.size() > 0) {
            return "0".equals(this.children.get(currentItem).getIdentity_id());
        }
        return false;
    }

    public void getHomePageSmall() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.homePageSmallAdApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                QuestionHomeNewFragment.this.initKingKong();
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                try {
                    QuestionHomeNewFragment.this.homepageSmallAdBean = (HomepageSmallAdBean) new Gson().fromJson(json, HomepageSmallAdBean.class);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_BANNER_AD, new Gson().toJson(QuestionHomeNewFragment.this.homepageSmallAdBean.getData().getBanner()), QuestionHomeNewFragment.this.getActivity());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_CARD_AD, new Gson().toJson(QuestionHomeNewFragment.this.homepageSmallAdBean.getData().getBlock_ad()), QuestionHomeNewFragment.this.getActivity());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_QUESTION_AD, new Gson().toJson(QuestionHomeNewFragment.this.homepageSmallAdBean.getData().getQuestion_ad()), QuestionHomeNewFragment.this.getActivity());
                    if (!ProjectApp.newHomeStyle) {
                        QuestionHomeNewFragment questionHomeNewFragment = QuestionHomeNewFragment.this;
                        questionHomeNewFragment.initBanner((RelativeLayout) questionHomeNewFragment.getViewHolder().get(R.id.rl_question_top_ad), QuestionHomeNewFragment.this.getContext());
                    }
                    QuestionHomeNewFragment questionHomeNewFragment2 = QuestionHomeNewFragment.this;
                    questionHomeNewFragment2.initKingKongArea((LinearLayout) questionHomeNewFragment2.getViewHolder().get(R.id.ll_question_top));
                    if (!ProjectApp.newHomeStyle) {
                        QuestionHomeNewFragment.this.initSwitcher();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                QuestionHomeNewFragment.this.initKingKong();
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_new_qh;
    }

    public void getPersonData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("app_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity()));
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mSwitchAppUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionHomeNewFragment.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.complete_user_info, jSONObject.optJSONObject("data").optString(CommonParameter.complete_user_info, "0"), ((BaseFragment) QuestionHomeNewFragment.this).mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.JingyanListtime_time, "0", QuestionHomeNewFragment.this.getActivity());
                        UserConfig.getInstance().getUser().setAdmin(jSONObject.optJSONObject("data").optString(PLVSocketUserConstant.ROLE_ADMIN, "0"));
                        UserConfig.getInstance().saveUser(UserConfig.getInstance().getUser());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void gotoActivity() {
        HomepageSmallAdBean homepageSmallAdBean = this.homepageSmallAdBean;
        if (homepageSmallAdBean == null || homepageSmallAdBean.getData().getLower_right_corner_ad().getAds().size() <= 0) {
            return;
        }
        try {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(this.ads.get(this.currentIndex).getExtra()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void initTabColumn() {
        final List<QuestionCategoryBean> questionCategoryList = CommonUtil.getQuestionCategoryList(this.mContext);
        if (!questionCategoryList.isEmpty()) {
            if (!this.mCategoryAdapter.getData().isEmpty()) {
                this.mCategoryAdapter.setList(new ArrayList());
                this.mCategoryAdapter.notifyDataSetChanged();
            }
            ListIterator<QuestionCategoryBean> listIterator = questionCategoryList.listIterator();
            while (listIterator.hasNext()) {
                QuestionCategoryBean next = listIterator.next();
                if (next.getChildren() == null || next.getChildren().isEmpty()) {
                    listIterator.remove();
                }
                String str = this.selectId;
                if (str == null) {
                    this.selectId = next.getId();
                    questionCategoryList.get(0).setSelect(true);
                } else if (TextUtils.equals(str, next.getId())) {
                    this.selectId = next.getId();
                    next.setSelect(true);
                } else {
                    next.setSelect(false);
                }
            }
            this.rvCategory.setAdapter(this.mCategoryAdapter);
            if (questionCategoryList.size() == 1) {
                this.rvCategory.setVisibility(8);
            } else {
                this.rvCategory.setVisibility(0);
            }
            this.mCategoryAdapter.setList(questionCategoryList);
            initCategory();
        }
        this.mCategoryAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.ra
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15968c.lambda$initTabColumn$13(questionCategoryList, baseQuickAdapter, view, i2);
            }
        });
        this.isRefresh = false;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.rvCategory = (RecyclerView) holder.get(R.id.rvCategory);
        this.guideView = holder.get(R.id.root);
        ViewGroup viewGroup = (ViewGroup) holder.get(R.id.guide_overlay);
        this.guideOverlay = viewGroup;
        viewGroup.setVisibility(0);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.treeIndicator = (ViewPager2Indicator) holder.get(R.id.tree_indicator);
        this.vpKingKong = (ViewPager2) holder.get(R.id.vpKingKong);
        this.iv_delete = (ImageView) holder.get(R.id.iv_delete);
        this.rl_buy_book = (RelativeLayout) holder.get(R.id.rl_buy_book);
        this.imageSwitcher = (ImageSwitcher) holder.get(R.id.imageSwitcher);
        this.mImgHidden = (ImageView) holder.get(R.id.img_hidden);
        this.iv_delete.setOnClickListener(this.mOnclick);
        this.rl_buy_book.setOnClickListener(this.mOnclick);
        this.scrollView = (HeaderScrollView) holder.get(R.id.scrollView);
        this.mImgHidden.setOnClickListener(this.mOnclick);
        this.viewpager = (ViewPager2) holder.get(R.id.viewpager);
        View view = holder.get(R.id.iv_knowledge_explore);
        this.ivKnowledgeExplore = view;
        this.mAnimationDrawable = (AnimationDrawable) view.getBackground();
        this.guideOverlay.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.fragmenthome.ya
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return QuestionHomeNewFragment.lambda$initViews$0(view2, motionEvent);
            }
        });
        this.treeIndicator.setVisibility(8);
        ImageView imageView = (ImageView) holder.get(R.id.ic_fire);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            imageView.setImageResource(R.drawable.ic_fire_decoration_night);
        }
        if (TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext, ""))) {
            showSelectPopWindowFrist();
        } else {
            loadStatistics();
            getHomePageSmall();
        }
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.za
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f16165c.lambda$initViews$1(refreshLayout);
            }
        });
        holder.get(R.id.ll_today_statistics).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ab
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f15441c.lambda$initViews$2(view2);
            }
        });
        this.ivKnowledgeExplore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.bb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f15476c.lambda$initViews$3(view2);
            }
        });
        this.refreshLayout.setEnableRefresh(false);
        getViewHolder().get(R.id.scoreView).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.cb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f15508c.lambda$initViews$4(view2);
            }
        });
    }

    public boolean isCurrentCombineQuestion() {
        return this.currentCombineQuestion;
    }

    @Subscribe
    public void onEventMainThread(QuestionBankRefreshEvent e2) {
        this.refreshLayout.finishRefresh(e2.isSuccess());
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            this.persent_fragment_show = false;
            LogUtils.e("question_com_fragment", "题库隐藏===》");
            return;
        }
        this.persent_fragment_show = true;
        if (this.guideViewMap.isEmpty()) {
            this.guideOverlay.setVisibility(8);
            EventBus.getDefault().post("dismiss_home_bot_mask");
            this.refreshLayout.setEnableRefresh(true);
        } else {
            checkShowGuide();
        }
        LogUtils.e("question_com_fragment", "题库显示===》");
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onSupportVisible() {
        super.onSupportVisible();
        this.persent_fragment_show = true;
        if (this.guideViewMap.size() > 0) {
            checkShowGuide();
        } else {
            this.guideOverlay.setVisibility(8);
            EventBus.getDefault().post("dismiss_home_bot_mask");
            this.refreshLayout.setEnableRefresh(true);
        }
        if (this.mContext == null || this.mCategoryAdapter.getData().isEmpty() || !SharePreferencesUtils.readBooleanConfig(CommonParameter.JUMP_2_KNOWLEDGE_CHART_TAG, false, this.mContext)) {
            return;
        }
        final List<QuestionCategoryBean> data = this.mCategoryAdapter.getData();
        final int size = this.mFragmentList.size();
        int i2 = -1;
        for (int i3 = 0; i3 < data.size(); i3++) {
            data.get(i3).setSelect("1".equals(data.get(i3).getType()));
            if (data.get(i3).isSelect()) {
                i2 = i3;
            }
        }
        if (i2 == -1) {
            return;
        }
        scroll2KnowledgeChartTab(i2, 500, 1500);
        final String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.KNOWLEDGE_PART_ID, this.mContext);
        if (!TextUtils.isEmpty(strConfig)) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.db
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15543c.lambda$onSupportVisible$6(data, strConfig, size);
                }
            }, 1000L);
            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_PART_ID, "", this.mContext);
            SharePreferencesUtils.writeStrConfig("KNOWLEDGE_CHAPTER_ID", "", this.mContext);
            this.mCategoryAdapter.notifyDataSetChanged();
        }
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.JUMP_2_KNOWLEDGE_CHART_TAG, false, this.mContext);
    }

    public void showSelectPopWindowFrist() {
        Intent intent = new Intent(getActivity(), (Class<?>) SelectIdentityNewActivity.class);
        intent.putExtra("flag", false);
        intent.putExtra("appbeanname", "");
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(RedDotEvent e2) {
        if (e2.isStart()) {
            loadAdSwitcher();
        } else {
            stopAdSwitcher();
            this.currentCombineQuestion = true;
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String mEveStr) {
        if (mEveStr.equals("tv_filtrate")) {
            showSelectPopWindowFrist();
        }
        if (mEveStr.equals("shareClick")) {
            getQuestionCategory();
        }
        if (mEveStr.equals("personData")) {
            getPersonData();
        }
        mEveStr.equals("upIndexList");
        if (mEveStr.equals("xitongxiaoxi")) {
            getHomePageSmall();
        }
        if ("LoginSuccess".equals(mEveStr)) {
            this.refreshLayout.autoRefresh();
        }
        if (EventBusConstant.EVENT_REFRESH_DAILY_TASK.equals(mEveStr)) {
            initKingKong();
        }
    }

    private List<Integer> checkShowGuide(String type, int position) {
        List<NewHomeKingKongItem> item = this.mAdapter.getItem(position);
        ArrayList arrayList = new ArrayList();
        int size = item.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            NewHomeKingKongItem newHomeKingKongItem = item.get(i2);
            if (!TextUtils.equals(type, TextUtils.isEmpty(newHomeKingKongItem.getPush_type()) ? newHomeKingKongItem.getJpush_type() : newHomeKingKongItem.getPush_type())) {
                i2++;
            } else if (TextUtils.equals(type, "29")) {
                if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_ANSWER_SHEET_TIP, false, ProjectApp.instance())) {
                    arrayList.add(Integer.valueOf(i2));
                }
            } else if (!TextUtils.equals(type, RoomMasterTable.DEFAULT_ID) && !TextUtils.equals(type, "41")) {
                if (TextUtils.equals(type, "38") && !SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_SELF_COMBINE_QUESTION_TIP, false, ProjectApp.instance())) {
                    arrayList.add(Integer.valueOf(i2));
                }
            } else if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.SHOW_MOCK_EXAM_TIP, false, ProjectApp.instance())) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        return arrayList;
    }
}
