package com.psychiatrygarden.activity.rank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.svideo.common.utils.DensityUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.timepicker.TimeModel;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.rank.RankEntranceActivity;
import com.psychiatrygarden.activity.rank.bean.ActivityBean;
import com.psychiatrygarden.activity.rank.bean.RankEntranceBean;
import com.psychiatrygarden.activity.rank.pop.ToastPopWindow;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.QrDialogBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.RankingEntranceFrag;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StringUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogShare;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.CommonImgDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class RankEntranceActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String activityId;
    private View addFooterView;
    public ListView listview;
    public CommonNavigator mCommonNavigator;
    private SwipeRefreshLayout mSwipeLayput;
    public CommAdapter<RankEntranceBean.DataBean.RanksBean> madapter;
    public MagicIndicator magicIndicator;
    public ArrayList<RankEntranceBean.DataBean.RanksBean> mreanks;
    public CircleImageView mycenter_icon;
    public ArrayList<RankEntranceBean.DataBean.RanksBean> ranksMajorDirectionList;
    public ArrayList<RankEntranceBean.DataBean.RanksBean> ranksMajorList;
    public TextView school;
    public TextView tvTiaoji;
    public TextView tv_username;
    public ViewPager viewpager;
    public int page = 1;
    public ArrayList<String> mTitleList = new ArrayList<>();
    public List<BaseViewPagerAdapter.PagerInfo> listviewpage = new ArrayList();
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.rank.RankEntranceActivity.2
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
            RankEntranceActivity.this.AlertToast("用户取消分享");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            RankEntranceActivity.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            RankEntranceActivity.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.activity.rank.RankEntranceActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Unit lambda$onSuccess$0(BasicDialog basicDialog) {
            Intent launchIntentForPackage = RankEntranceActivity.this.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            if (launchIntentForPackage != null) {
                RankEntranceActivity.this.startActivity(launchIntentForPackage);
            } else {
                ToastUtil.shortToast(RankEntranceActivity.this, "没有安装微信");
            }
            return Unit.INSTANCE;
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass1) s2);
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    QrDialogBean qrDialogBean = (QrDialogBean) new Gson().fromJson(s2, QrDialogBean.class);
                    boolean z2 = true;
                    CommonImgDialog.Builder dialogQr = new CommonImgDialog.Builder(RankEntranceActivity.this).setSubTitle(qrDialogBean.getData().getTitle()).setRightText("保存并跳转微信").setConfirmView(R.drawable.shape_app_theme_corners_12).setDialogBg(SkinManager.getCurrentSkinType(RankEntranceActivity.this) == 1 ? R.color.color_blue_theme_bg : R.color.white_color).setDialogQr(qrDialogBean.getData().getQr_code());
                    if (SkinManager.getCurrentSkinType(RankEntranceActivity.this) != 1) {
                        z2 = false;
                    }
                    dialogQr.setIsNight(z2).setWxCode(qrDialogBean.getData().getWechat()).setRightClick(new Function1() { // from class: com.psychiatrygarden.activity.rank.n
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return this.f13778c.lambda$onSuccess$0((BasicDialog) obj);
                        }
                    }).show();
                }
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.rank.RankEntranceActivity$3, reason: invalid class name */
    public class AnonymousClass3 implements AbsListView.OnScrollListener {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            RankEntranceActivity rankEntranceActivity = RankEntranceActivity.this;
            rankEntranceActivity.page++;
            rankEntranceActivity.getData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && RankEntranceActivity.this.listview.getFooterViewsCount() <= 0) {
                if (RankEntranceActivity.this.mSwipeLayput.isRefreshing()) {
                    RankEntranceActivity.this.mSwipeLayput.setRefreshing(false);
                    return;
                }
                RankEntranceActivity rankEntranceActivity = RankEntranceActivity.this;
                rankEntranceActivity.listview.addFooterView(rankEntranceActivity.addFooterView);
                RankEntranceActivity.this.addFooterView.setVisibility(0);
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.rank.o
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f13779c.lambda$onScrollStateChanged$0();
                    }
                }, 1000L);
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.rank.RankEntranceActivity$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {

        /* renamed from: com.psychiatrygarden.activity.rank.RankEntranceActivity$4$1, reason: invalid class name */
        public class AnonymousClass1 extends CommAdapter<RankEntranceBean.DataBean.RanksBean> {
            public AnonymousClass1(List mData, Context mcontext, int layoutId) {
                super(mData, mcontext, layoutId);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$convert$0(RankEntranceBean.DataBean.RanksBean ranksBean, View view) {
                new XPopup.Builder(RankEntranceActivity.this.mContext).hasShadowBg(Boolean.FALSE).asCustom(new ToastPopWindow(RankEntranceActivity.this.mContext, ranksBean.getTotal(), ranksBean.getGrade_info(), "", ranksBean.getStatus())).show();
            }

            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, final RankEntranceBean.DataBean.RanksBean ranksBean, int position) {
                CircleImageView circleImageView = (CircleImageView) vHolder.getView(R.id.iv_header_img);
                Glide.with(RankEntranceActivity.this.mContext).load((Object) GlideUtils.generateUrl(ranksBean.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(circleImageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(circleImageView);
                ((TextView) vHolder.getView(R.id.tv_ranking_name)).setText(String.format("%s", ranksBean.getNickname()));
                TextView textView = (TextView) vHolder.getView(R.id.tv_ranking_grade);
                if (RankEntranceActivity.this.getIntent().getExtras().getString("moudle").equals(Constants.VIA_REPORT_TYPE_WPA_STATE)) {
                    textView.setText(String.format("%s", ranksBean.getGrade_info()));
                    textView.setCompoundDrawables(null, null, null, null);
                } else {
                    textView.setText(String.format("%s", ranksBean.getTotal()));
                    textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.s
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13786c.lambda$convert$0(ranksBean, view);
                        }
                    });
                }
                CircleImageView circleImageView2 = (CircleImageView) vHolder.getView(R.id.circleimg);
                TextView textView2 = (TextView) vHolder.getView(R.id.tv_ranking_num);
                int i2 = position + 1;
                if (i2 == 1) {
                    circleImageView2.setVisibility(0);
                    textView2.setVisibility(8);
                    circleImageView2.setImageResource(R.drawable.rankone);
                } else if (i2 == 2) {
                    circleImageView2.setVisibility(0);
                    textView2.setVisibility(8);
                    circleImageView2.setImageResource(R.drawable.ranktwo);
                } else if (i2 != 3) {
                    circleImageView2.setVisibility(8);
                    textView2.setVisibility(0);
                    textView2.setText(String.format(Locale.CHINA, TimeModel.NUMBER_FORMAT, Integer.valueOf(i2)));
                } else {
                    circleImageView2.setVisibility(0);
                    textView2.setVisibility(8);
                    circleImageView2.setImageResource(R.drawable.rankthree);
                }
            }
        }

        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(RankEntranceBean rankEntranceBean, int i2) {
            ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
            shareInfoBean.setShare_url(rankEntranceBean.getData().getShare_info().getShare_url());
            shareInfoBean.setShare_type(rankEntranceBean.getData().getShare_info().getShare_type());
            shareInfoBean.setShare_img(rankEntranceBean.getData().getShare_info().getShare_img());
            shareInfoBean.setShare_content(rankEntranceBean.getData().getDescription_2());
            shareInfoBean.setShare_title(rankEntranceBean.getData().getDescription_1());
            RankEntranceActivity.this.shareAppControl(i2, shareInfoBean);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(final RankEntranceBean rankEntranceBean, View view) {
            if (rankEntranceBean.getData().getShare_info() == null) {
                ToastUtil.shortToast(RankEntranceActivity.this.mContext, "内容加载失败");
            } else {
                new DialogShare(RankEntranceActivity.this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.rank.p
                    @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                    public final void onclickIntBack(int i2) {
                        this.f13780a.lambda$onSuccess$0(rankEntranceBean, i2);
                    }
                }).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$2(View view) {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            String str;
            super.onSuccess((AnonymousClass4) s2);
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    if ("new".equals(RankEntranceActivity.this.getIntent().getExtras().getString("type"))) {
                        if ("0".equals(new JSONObject(s2).optJSONObject("data").optString("status")) || "2".equals(new JSONObject(s2).optJSONObject("data").optString("status"))) {
                            if (RankEntranceActivity.this.mSwipeLayput.isRefreshing()) {
                                RankEntranceActivity.this.mSwipeLayput.setRefreshing(false);
                            }
                            Intent intent = new Intent(RankEntranceActivity.this, (Class<?>) EntranceResultsActivity.class);
                            intent.putExtra("moudle", "" + RankEntranceActivity.this.getIntent().getExtras().getString("moudle"));
                            intent.putExtra("type", "" + RankEntranceActivity.this.getIntent().getExtras().getString("type"));
                            intent.putExtra("examId", RankEntranceActivity.this.getIntent().getExtras().getString("examId"));
                            intent.putExtra(PushConstants.INTENT_ACTIVITY_NAME, "" + new JSONObject(s2).optJSONObject("data").optString(PushConstants.INTENT_ACTIVITY_NAME));
                            intent.putExtra("status", new JSONObject(s2).optJSONObject("data").optString("status") + "");
                            RankEntranceActivity.this.startActivity(intent);
                            RankEntranceActivity.this.finish();
                            return;
                        }
                    } else if ("haveimg".equals(RankEntranceActivity.this.getIntent().getExtras().getString("type")) && ("0".equals(new JSONObject(s2).optJSONObject("data").optString("status")) || "2".equals(new JSONObject(s2).optJSONObject("data").optString("status")))) {
                        if (RankEntranceActivity.this.mSwipeLayput.isRefreshing()) {
                            RankEntranceActivity.this.mSwipeLayput.setRefreshing(false);
                        }
                        RankEntranceActivity.this.AlertToast(new JSONObject(s2).optString("message"));
                        RankEntranceActivity.this.finish();
                        return;
                    }
                    RankEntranceActivity.this.setTitle(new JSONObject(s2).optJSONObject("data").optString("title"));
                    final RankEntranceBean rankEntranceBean = (RankEntranceBean) new Gson().fromJson(new JSONObject(s2).toString(), RankEntranceBean.class);
                    ((BaseActivity) RankEntranceActivity.this).iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.q
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f13784c.lambda$onSuccess$1(rankEntranceBean, view);
                        }
                    });
                    RankEntranceActivity rankEntranceActivity = RankEntranceActivity.this;
                    if (rankEntranceActivity.page == 1) {
                        rankEntranceActivity.mreanks = rankEntranceBean.getData().getRanks();
                        String description_2 = rankEntranceBean.getData().getDescription_2();
                        String str2 = (!TextUtils.isEmpty(rankEntranceBean.getData().getSchool_department_title()) ? rankEntranceBean.getData().getSchool_department_title() : rankEntranceBean.getData().getSchool_title()) + "   " + rankEntranceBean.getData().getZhuan_xue();
                        if (TextUtils.isEmpty(rankEntranceBean.getData().getMajor_direction_title())) {
                            str = str2 + "   " + rankEntranceBean.getData().getMajor_title();
                        } else {
                            str = str2 + "   " + rankEntranceBean.getData().getMajor_direction_title();
                        }
                        RankEntranceActivity.this.tv_username.setText(str);
                        RankEntranceActivity.this.school.setText(StringUtil.setNumColor(description_2, SkinManager.getCurrentSkinType(RankEntranceActivity.this) == 1 ? ContextCompat.getColor(RankEntranceActivity.this, R.color.person_orange_color_night) : ContextCompat.getColor(RankEntranceActivity.this, R.color.person_orange_color), ContextCompat.getColor(SkinManager.getCurrentSkinType(RankEntranceActivity.this) == 1 ? RankEntranceActivity.this : RankEntranceActivity.this, R.color.third_txt_color_night), 1.2f));
                        RankEntranceActivity.this.school.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.r
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                RankEntranceActivity.AnonymousClass4.lambda$onSuccess$2(view);
                            }
                        });
                        RankEntranceActivity rankEntranceActivity2 = RankEntranceActivity.this;
                        RankEntranceActivity rankEntranceActivity3 = RankEntranceActivity.this;
                        rankEntranceActivity2.madapter = new AnonymousClass1(rankEntranceActivity3.mreanks, rankEntranceActivity3.mContext, R.layout.layout_rankentrance_provider);
                        RankEntranceActivity rankEntranceActivity4 = RankEntranceActivity.this;
                        rankEntranceActivity4.listview.setAdapter((ListAdapter) rankEntranceActivity4.madapter);
                    } else {
                        rankEntranceActivity.listview.removeFooterView(rankEntranceActivity.addFooterView);
                        RankEntranceActivity.this.addFooterView.setVisibility(8);
                        if (rankEntranceBean.getData().getRanks().size() > 0) {
                            RankEntranceActivity.this.mreanks.addAll(rankEntranceBean.getData().getRanks());
                            RankEntranceActivity.this.madapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.shortToast(RankEntranceActivity.this.mContext, "" + new JSONObject(s2).optString("message"));
                        }
                    }
                } else if (new JSONObject(s2).optString("code").equals("403")) {
                    Intent intent2 = new Intent(RankEntranceActivity.this, (Class<?>) EntranceResultsActivity.class);
                    intent2.putExtra("moudle", "" + RankEntranceActivity.this.getIntent().getExtras().getString("moudle"));
                    intent2.putExtra("type", "" + RankEntranceActivity.this.getIntent().getExtras().getString("type"));
                    intent2.putExtra("examId", RankEntranceActivity.this.getIntent().getExtras().getString("examId"));
                    intent2.putExtra(PushConstants.INTENT_ACTIVITY_NAME, "" + new JSONObject(s2).optJSONObject("data").optString(PushConstants.INTENT_ACTIVITY_NAME));
                    intent2.putExtra("status", "OTHER");
                    RankEntranceActivity.this.startActivity(intent2);
                    RankEntranceActivity.this.finish();
                }
                if (RankEntranceActivity.this.mSwipeLayput.isRefreshing()) {
                    RankEntranceActivity.this.mSwipeLayput.setRefreshing(false);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.rank.RankEntranceActivity$5, reason: invalid class name */
    public class AnonymousClass5 extends AjaxCallBack<String> {

        /* renamed from: com.psychiatrygarden.activity.rank.RankEntranceActivity$5$1, reason: invalid class name */
        public class AnonymousClass1 extends CommonNavigatorAdapter {
            public AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
                RankEntranceActivity.this.viewpager.setCurrentItem(i2);
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public int getCount() {
                ArrayList<String> arrayList = RankEntranceActivity.this.mTitleList;
                if (arrayList == null) {
                    return 0;
                }
                return arrayList.size();
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
                linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(RankEntranceActivity.this.mContext, SkinManager.getCurrentSkinType(RankEntranceActivity.this.mContext) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color)));
                return linePagerIndicator;
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.item_question_column);
                final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
                textView.getLayoutParams().height = ScreenUtil.getPxByDp(context, 44);
                textView.setText(RankEntranceActivity.this.mTitleList.get(index));
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.w
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws Resources.NotFoundException {
                        this.f13792c.lambda$getTitleView$0(index, view);
                    }
                });
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.rank.RankEntranceActivity.5.1.1
                    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onDeselected(int index2, int totalCount) {
                        textView.setTypeface(Typeface.DEFAULT);
                        if (SkinManager.getCurrentSkinType(RankEntranceActivity.this.mContext) == 1) {
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
                        if (SkinManager.getCurrentSkinType(RankEntranceActivity.this.mContext) == 1) {
                            textView.setTextColor(Color.parseColor("#7380A9"));
                        } else {
                            textView.setTextColor(Color.parseColor("#141516"));
                        }
                    }
                });
                return commonPagerTitleView;
            }
        }

        public AnonymousClass5() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(RankEntranceBean rankEntranceBean, int i2) {
            ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
            shareInfoBean.setShare_url(rankEntranceBean.getData().getShare_info().getShare_url());
            shareInfoBean.setShare_type(rankEntranceBean.getData().getShare_info().getShare_type());
            shareInfoBean.setShare_img(rankEntranceBean.getData().getShare_info().getShare_img());
            shareInfoBean.setShare_content(rankEntranceBean.getData().getDescription_2());
            shareInfoBean.setShare_title(rankEntranceBean.getData().getDescription_1());
            RankEntranceActivity.this.shareAppControl(i2, shareInfoBean);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(final RankEntranceBean rankEntranceBean, View view) {
            if (rankEntranceBean.getData().getShare_info() == null) {
                ToastUtil.shortToast(RankEntranceActivity.this.mContext, "内容加载失败");
            } else {
                new DialogShare(RankEntranceActivity.this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.rank.t
                    @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                    public final void onclickIntBack(int i2) {
                        this.f13788a.lambda$onSuccess$0(rankEntranceBean, i2);
                    }
                }).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onSuccess$2(View view) {
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            CharSequence charSequence;
            String str;
            super.onSuccess((AnonymousClass5) s2);
            try {
                if (!new JSONObject(s2).optString("code").equals("200")) {
                    if (new JSONObject(s2).optString("code").equals("403")) {
                        Intent intent = new Intent(RankEntranceActivity.this, (Class<?>) EntranceResultsActivity.class);
                        intent.putExtra("moudle", "" + RankEntranceActivity.this.getIntent().getExtras().getString("moudle"));
                        intent.putExtra("type", "" + RankEntranceActivity.this.getIntent().getExtras().getString("type"));
                        intent.putExtra("examId", RankEntranceActivity.this.getIntent().getExtras().getString("examId"));
                        intent.putExtra(PushConstants.INTENT_ACTIVITY_NAME, "" + new JSONObject(s2).optJSONObject("data").optString(PushConstants.INTENT_ACTIVITY_NAME));
                        intent.putExtra("status", "OTHER");
                        RankEntranceActivity.this.startActivity(intent);
                        RankEntranceActivity.this.finish();
                        return;
                    }
                    return;
                }
                if ("new".equals(RankEntranceActivity.this.getIntent().getExtras().getString("type"))) {
                    if ("0".equals(new JSONObject(s2).optJSONObject("data").optString("status")) || "2".equals(new JSONObject(s2).optJSONObject("data").optString("status"))) {
                        Intent intent2 = new Intent(RankEntranceActivity.this, (Class<?>) EntranceResultsActivity.class);
                        intent2.putExtra("moudle", "" + RankEntranceActivity.this.getIntent().getExtras().getString("moudle"));
                        intent2.putExtra("type", "" + RankEntranceActivity.this.getIntent().getExtras().getString("type"));
                        intent2.putExtra("examId", RankEntranceActivity.this.getIntent().getExtras().getString("examId"));
                        intent2.putExtra(PushConstants.INTENT_ACTIVITY_NAME, "" + new JSONObject(s2).optJSONObject("data").optString(PushConstants.INTENT_ACTIVITY_NAME));
                        intent2.putExtra("status", new JSONObject(s2).optJSONObject("data").optString("status") + "");
                        RankEntranceActivity.this.startActivity(intent2);
                        RankEntranceActivity.this.finish();
                        return;
                    }
                } else if ("haveimg".equals(RankEntranceActivity.this.getIntent().getExtras().getString("type")) && ("0".equals(new JSONObject(s2).optJSONObject("data").optString("status")) || "2".equals(new JSONObject(s2).optJSONObject("data").optString("status")))) {
                    RankEntranceActivity.this.AlertToast(new JSONObject(s2).optString("message"));
                    RankEntranceActivity.this.finish();
                    return;
                }
                RankEntranceActivity.this.setTitle(new JSONObject(s2).optJSONObject("data").optString("title"));
                final RankEntranceBean rankEntranceBean = (RankEntranceBean) new Gson().fromJson(new JSONObject(s2).toString(), RankEntranceBean.class);
                ((BaseActivity) RankEntranceActivity.this).iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.u
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13790c.lambda$onSuccess$1(rankEntranceBean, view);
                    }
                });
                RankEntranceActivity rankEntranceActivity = RankEntranceActivity.this;
                if (rankEntranceActivity.page == 1) {
                    final String description_2 = rankEntranceBean.getData().getDescription_2();
                    final String description_3 = rankEntranceBean.getData().getDescription_3();
                    final String description_4 = rankEntranceBean.getData().getDescription_4();
                    RankEntranceActivity.this.mreanks = rankEntranceBean.getData().getRanks();
                    RankEntranceActivity.this.ranksMajorList = rankEntranceBean.getData().getRanks_major();
                    RankEntranceActivity.this.ranksMajorDirectionList = rankEntranceBean.getData().getRanks_major_direction();
                    if (RankEntranceActivity.this.mreanks.isEmpty()) {
                        charSequence = "";
                    } else {
                        RankEntranceActivity.this.mTitleList.add("院系所排名");
                        Bundle bundle = new Bundle();
                        bundle.putString("moudle", RankEntranceActivity.this.getIntent().getExtras().getString("moudle"));
                        bundle.putString("type", RankEntranceActivity.this.getIntent().getExtras().getString("type"));
                        bundle.putString("examId", RankEntranceActivity.this.getIntent().getExtras().getString("examId"));
                        bundle.putParcelableArrayList("rankList", RankEntranceActivity.this.mreanks);
                        charSequence = "";
                        RankEntranceActivity.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("院系所排名", RankingEntranceFrag.class, bundle));
                    }
                    if (!RankEntranceActivity.this.ranksMajorList.isEmpty()) {
                        RankEntranceActivity.this.mTitleList.add("专业排名");
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("moudle", RankEntranceActivity.this.getIntent().getExtras().getString("moudle"));
                        bundle2.putString("type", RankEntranceActivity.this.getIntent().getExtras().getString("type"));
                        bundle2.putString("examId", RankEntranceActivity.this.getIntent().getExtras().getString("examId"));
                        bundle2.putParcelableArrayList("rankList", RankEntranceActivity.this.ranksMajorList);
                        RankEntranceActivity.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("专业排名", RankingEntranceFrag.class, bundle2));
                    }
                    if (!RankEntranceActivity.this.ranksMajorDirectionList.isEmpty()) {
                        RankEntranceActivity.this.mTitleList.add("专业方向排名");
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("moudle", RankEntranceActivity.this.getIntent().getExtras().getString("moudle"));
                        bundle3.putString("type", RankEntranceActivity.this.getIntent().getExtras().getString("type"));
                        bundle3.putString("examId", RankEntranceActivity.this.getIntent().getExtras().getString("examId"));
                        bundle3.putParcelableArrayList("rankList", RankEntranceActivity.this.ranksMajorDirectionList);
                        RankEntranceActivity.this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo("专业方向排名", RankingEntranceFrag.class, bundle3));
                    }
                    RankEntranceActivity.this.mCommonNavigator = new CommonNavigator(RankEntranceActivity.this);
                    RankEntranceActivity.this.mCommonNavigator.setScrollPivotX(0.65f);
                    RankEntranceActivity.this.mCommonNavigator.setAdjustMode(true);
                    RankEntranceActivity.this.mCommonNavigator.setAdapter(new AnonymousClass1());
                    RankEntranceActivity rankEntranceActivity2 = RankEntranceActivity.this;
                    rankEntranceActivity2.magicIndicator.setNavigator(rankEntranceActivity2.mCommonNavigator);
                    RankEntranceActivity rankEntranceActivity3 = RankEntranceActivity.this;
                    RankEntranceActivity.this.viewpager.setAdapter(new BaseViewPagerAdapter(rankEntranceActivity3.mContext, rankEntranceActivity3.getSupportFragmentManager(), RankEntranceActivity.this.listviewpage));
                    RankEntranceActivity.this.viewpager.setOffscreenPageLimit(3);
                    RankEntranceActivity rankEntranceActivity4 = RankEntranceActivity.this;
                    ViewPagerHelper.bind(rankEntranceActivity4.magicIndicator, rankEntranceActivity4.viewpager);
                    RankEntranceActivity.this.viewpager.setCurrentItem(0);
                    final int color = SkinManager.getCurrentSkinType(RankEntranceActivity.this) == 1 ? ContextCompat.getColor(RankEntranceActivity.this, R.color.person_orange_color_night) : ContextCompat.getColor(RankEntranceActivity.this, R.color.person_orange_color);
                    final int color2 = SkinManager.getCurrentSkinType(RankEntranceActivity.this) == 1 ? ContextCompat.getColor(RankEntranceActivity.this, R.color.third_txt_color_night) : ContextCompat.getColor(RankEntranceActivity.this, R.color.third_txt_color);
                    RankEntranceActivity.this.school.setText(description_2.isEmpty() ? charSequence : StringUtil.setNumColor(description_2, color, color2, 1.2f));
                    RankEntranceActivity.this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.rank.RankEntranceActivity.5.2
                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageScrollStateChanged(int i2) {
                        }

                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageScrolled(int i2, float v2, int i12) {
                        }

                        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                        public void onPageSelected(int i2) {
                            if (i2 == 0) {
                                RankEntranceActivity.this.school.setText(description_2.isEmpty() ? "" : StringUtil.setNumColor(description_2, color, color2, 1.2f));
                            } else if (i2 == 1) {
                                RankEntranceActivity.this.school.setText(description_3.isEmpty() ? "" : StringUtil.setNumColor(description_3, color, color2, 1.2f));
                            } else if (i2 == 2) {
                                RankEntranceActivity.this.school.setText(description_4.isEmpty() ? "" : StringUtil.setNumColor(description_4, color, color2, 1.2f));
                            }
                        }
                    });
                    String str2 = (!TextUtils.isEmpty(rankEntranceBean.getData().getSchool_department_title()) ? rankEntranceBean.getData().getSchool_department_title() : rankEntranceBean.getData().getSchool_title()) + "   " + rankEntranceBean.getData().getZhuan_xue();
                    if (TextUtils.isEmpty(rankEntranceBean.getData().getMajor_direction_title())) {
                        str = str2 + "   " + rankEntranceBean.getData().getMajor_title();
                    } else {
                        str = str2 + "   " + rankEntranceBean.getData().getMajor_direction_title();
                    }
                    RankEntranceActivity.this.tv_username.setText(str);
                    RankEntranceActivity.this.school.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.v
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            RankEntranceActivity.AnonymousClass5.lambda$onSuccess$2(view);
                        }
                    });
                } else {
                    rankEntranceActivity.addFooterView.setVisibility(8);
                }
                if (rankEntranceBean.getData().getAdjust_info() != null) {
                    RankEntranceActivity.this.activityId = rankEntranceBean.getData().getAdjust_info().getActivity();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put(DatabaseManager.SIZE, "200");
        if (com.tencent.connect.common.Constants.VIA_REPORT_TYPE_WPA_STATE.equals(getIntent().getExtras().getString("moudle"))) {
            String str = NetworkRequestsURL.rank2Api2;
            if ("new".equals(getIntent().getExtras().getString("type"))) {
                ajaxParams.put("version", "2");
            } else {
                ajaxParams.put("version", "1");
            }
            this.mSwipeLayput.setVisibility(0);
            this.magicIndicator.setVisibility(8);
            this.viewpager.setVisibility(8);
            getStatisticsList(str, ajaxParams);
            return;
        }
        String str2 = NetworkRequestsURL.rank2Api;
        if ("haveimg".equals(getIntent().getExtras().getString("type"))) {
            ajaxParams.put("version", "2");
        } else {
            ajaxParams.put("version", "1");
        }
        this.mSwipeLayput.setVisibility(8);
        this.magicIndicator.setVisibility(0);
        this.viewpager.setVisibility(0);
        getRankList(str2, ajaxParams);
    }

    private void getQrData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getAdjustQrCodeInfo, new AjaxParams(), new AnonymousClass1());
    }

    private void getRankList(String url, AjaxParams params) {
        YJYHttpUtils.post(this.mContext, url, params, new AnonymousClass5());
    }

    private void getStatisticsList(String url, AjaxParams params) {
        YJYHttpUtils.post(this.mContext, url, params, new AnonymousClass4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4() {
        this.mSwipeLayput.setRefreshing(true);
        this.page = 1;
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRefresh$5() {
        this.page = 1;
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        getQrData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1() {
        Intent intent = new Intent(this, (Class<?>) AdjustListActivity.class);
        intent.putExtra("type", getIntent().getExtras().getString("type"));
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$2(View view) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(PushConstants.INTENT_ACTIVITY_NAME, this.activityId);
        MemInterface.getInstance().setDisType(1);
        MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.rank.i
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f13773a.lambda$setContentView$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGrade$3(AlertDialog alertDialog, View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.isTrueCeshi, false, this.mContext);
        alertDialog.dismiss();
    }

    private void showGrade() {
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        alertDialogCreate.show();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        Window window = alertDialogCreate.getWindow();
        window.setGravity(17);
        window.setContentView(R.layout.show_review_material);
        WindowManager.LayoutParams attributes = alertDialogCreate.getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        alertDialogCreate.getWindow().setAttributes(attributes);
        TextView textView = (TextView) alertDialogCreate.findViewById(R.id.tv_ok);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13771c.lambda$showGrade$3(alertDialogCreate, view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mSwipeLayput = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.mSwipeLayput.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mSwipeLayput.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.app_bg));
            this.mSwipeLayput.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeLayput.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.app_bg_night));
            this.mSwipeLayput.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mycenter_icon = (CircleImageView) findViewById(R.id.mycenter_icon);
        this.magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.tv_username = (TextView) findViewById(R.id.tv_username);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.school = (TextView) findViewById(R.id.school);
        this.listview = (ListView) findViewById(R.id.listview);
        Glide.with(this.mContext).load((Object) GlideUtils.generateUrl(UserConfig.getInstance().getUser().getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(this, SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(this.mycenter_icon);
        this.mSwipeLayput.post(new Runnable() { // from class: com.psychiatrygarden.activity.rank.j
            @Override // java.lang.Runnable
            public final void run() {
                this.f13774c.lambda$init$4();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mSwipeLayput.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.rank.m
            @Override // java.lang.Runnable
            public final void run() {
                this.f13777c.lambda$onRefresh$5();
            }
        }, 1000L);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_rank_entrance);
        this.tvTiaoji = (TextView) findViewById(R.id.tv_tiaoji);
        if (com.tencent.connect.common.Constants.VIA_REPORT_TYPE_WPA_STATE.equals(getIntent().getExtras().getString("moudle"))) {
            setTitle(((CommonUtil.getYear() + 1) + "").substring(r0.length() - 2) + "考研志愿统计");
        } else {
            setTitle((CommonUtil.getYear() + "").substring(r0.length() - 2) + "考研志愿排名");
            this.tvTiaoji.setVisibility(0);
        }
        boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
        this.tvTiaoji.setTextColor(getResources().getColor(z2 ? R.color.new_bg_two_color_night : R.color.new_bg_two_color));
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setVisibility(0);
        this.iv_actionbar_right.setImageResource(z2 ? R.drawable.zhuanfaceshi_night : R.drawable.zhuanfaceshi);
        this.iv_right_img.setVisibility(0);
        this.iv_right_img.setImageResource(z2 ? R.drawable.qr_icon_night : R.drawable.qr_icon);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.iv_right_img.getLayoutParams();
        marginLayoutParams.setMarginEnd(DensityUtils.dip2px(this, 40.0f));
        this.iv_right_img.setLayoutParams(marginLayoutParams);
        if (!SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_ADJUST_GRADE, false, this)) {
            showGrade();
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_ADJUST_GRADE, true, this);
        }
        this.iv_right_img.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13775c.lambda$setContentView$0(view);
            }
        });
        this.tvTiaoji.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.rank.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13776c.lambda$setContentView$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.listview.setOnScrollListener(new AnonymousClass3());
    }

    public void shareAppControl(int i2, ActivityBean.DataBean.ShareInfoBean shareInfoBean) {
        String strTrim = shareInfoBean.getShare_url().trim();
        String share_title = shareInfoBean.getShare_title();
        String share_content = shareInfoBean.getShare_content();
        UMImage uMImage = shareInfoBean.getShare_img().equals("") ? new UMImage(this.mContext, R.drawable.ic_launcher) : new UMImage(this.mContext, shareInfoBean.getShare_img());
        if (i2 == 3) {
            UMWeb uMWeb = new UMWeb(strTrim);
            uMWeb.setTitle(share_title);
            uMWeb.setDescription(strTrim);
            uMWeb.setThumb(uMImage);
            new ShareAction(this).withMedia(uMWeb).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
            return;
        }
        UMWeb uMWeb2 = new UMWeb(strTrim);
        uMWeb2.setTitle(share_title);
        uMWeb2.setDescription(share_content);
        uMWeb2.setThumb(uMImage);
        new ShareAction(this).withMedia(uMWeb2).setPlatform(BaseActivity.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}
