package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.activity.rank.bean.ActivityBean;
import com.psychiatrygarden.activity.rank.bean.RankEntranceBean;
import com.psychiatrygarden.bean.QrDialogBean;
import com.psychiatrygarden.bean.RankingContentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.fragmenthome.AdjustRankingFragment;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.StringUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogShare;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.CommonImgDialog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AdjustRankingActivity extends BaseActivity {
    private int adjustPosition;
    private int adjustPositionAll;
    public ImageView adjustRankQr;
    public ImageView adjustRankShare;
    public String adjustRankTitle;
    public TextView adjustRankTitleTv;
    public String allAdjustRankTitle;
    private RankingContentBean.DataBean allInfo;
    private RankEntranceBean.DataBean.ShareData allShareData;
    private String description2;
    private String description2All;
    private String description3;
    private String description3All;
    private String description4;
    private String description4All;
    private ImageView iv_ranking_back;
    public BaseViewPagerAdapter mBaseView;
    private TextView mBtnShare;
    public CommonNavigator mCommonNavigator;
    private CircleImageView mImgUserHead;
    public ViewPager mQuestionViewpager;
    private TextView mTvNickName;
    public MagicIndicator magicIndicator;
    private int pagePosition;
    public TextView school;
    private RankingContentBean.DataBean schoolInfo;
    private RankEntranceBean.DataBean.ShareData shareData;
    private SpannableStringBuilder zhengqueTxtSpanBuilder;
    public List<BaseViewPagerAdapter.PagerInfo> listviewpage = new ArrayList();
    public String[] mTitleList = new String[2];
    private String bindSchool = "";
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.activity.AdjustRankingActivity.6
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            AdjustRankingActivity.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            AdjustRankingActivity.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    /* renamed from: com.psychiatrygarden.activity.AdjustRankingActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements View.OnClickListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(int i2) {
            RankEntranceBean.DataBean.ShareData shareData = AdjustRankingActivity.this.pagePosition == 0 ? AdjustRankingActivity.this.shareData : AdjustRankingActivity.this.allShareData;
            ActivityBean.DataBean.ShareInfoBean shareInfoBean = new ActivityBean.DataBean.ShareInfoBean();
            shareInfoBean.setShare_url(shareData.getShare_url());
            shareInfoBean.setShare_type(shareData.getShare_type());
            shareInfoBean.setShare_img(shareData.getShare_img());
            shareInfoBean.setShare_content(shareData.getShare_content());
            shareInfoBean.setShare_title(shareData.getShare_title());
            AdjustRankingActivity.this.shareAppControl(i2, shareInfoBean);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            new DialogShare(AdjustRankingActivity.this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.activity.n0
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i2) {
                    this.f13032a.lambda$onClick$0(i2);
                }
            }).show();
        }
    }

    /* renamed from: com.psychiatrygarden.activity.AdjustRankingActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Unit lambda$onSuccess$0(BasicDialog basicDialog) {
            Intent launchIntentForPackage = AdjustRankingActivity.this.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            if (launchIntentForPackage != null) {
                AdjustRankingActivity.this.startActivity(launchIntentForPackage);
            } else {
                ToastUtil.shortToast(AdjustRankingActivity.this, "没有安装微信");
            }
            return Unit.INSTANCE;
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass3) s2);
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    QrDialogBean qrDialogBean = (QrDialogBean) new Gson().fromJson(s2, QrDialogBean.class);
                    boolean z2 = true;
                    CommonImgDialog.Builder dialogQr = new CommonImgDialog.Builder(AdjustRankingActivity.this).setSubTitle(qrDialogBean.getData().getTitle()).setRightText("保存并跳转微信").setConfirmView(R.drawable.shape_app_theme_corners_12).setDialogBg(SkinManager.getCurrentSkinType(AdjustRankingActivity.this) == 1 ? R.color.color_blue_theme_bg : R.color.white_color).setDialogQr(qrDialogBean.getData().getQr_code());
                    if (SkinManager.getCurrentSkinType(AdjustRankingActivity.this) != 1) {
                        z2 = false;
                    }
                    dialogQr.setIsNight(z2).setWxCode(qrDialogBean.getData().getWechat()).setRightClick(new Function1() { // from class: com.psychiatrygarden.activity.o0
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return this.f13067c.lambda$onSuccess$0((BasicDialog) obj);
                        }
                    }).show();
                }
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.AdjustRankingActivity$4, reason: invalid class name */
    public class AnonymousClass4 extends CommonNavigatorAdapter {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            AdjustRankingActivity.this.mQuestionViewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            String[] strArr = AdjustRankingActivity.this.mTitleList;
            if (strArr == null) {
                return 0;
            }
            return strArr.length;
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
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(AdjustRankingActivity.this.mContext, SkinManager.getCurrentSkinType(AdjustRankingActivity.this.mContext) == 1 ? R.color.main_theme_color_night : R.color.main_theme_color)));
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            commonPagerTitleView.setContentView(R.layout.item_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            textView.getLayoutParams().height = ScreenUtil.getPxByDp(context, 44);
            textView.setText(AdjustRankingActivity.this.mTitleList[index]);
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.p0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f13526c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.activity.AdjustRankingActivity.4.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    if (SkinManager.getCurrentSkinType(AdjustRankingActivity.this.mContext) == 1) {
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
                    if (SkinManager.getCurrentSkinType(AdjustRankingActivity.this.mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#7380A9"));
                    } else {
                        textView.setTextColor(Color.parseColor("#141516"));
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    private String getExamTimeStr(String remain) {
        String str;
        String str2;
        int i2 = !TextUtils.isEmpty(remain) ? Integer.parseInt(remain) : 0;
        if (i2 == 0) {
            return "0秒";
        }
        int i3 = i2 / 3600;
        int i4 = i2 % 3600;
        int i5 = i4 / 60;
        int i6 = i4 % 60;
        StringBuilder sb = new StringBuilder();
        String str3 = "";
        if (i3 <= 0) {
            str = "";
        } else {
            str = i3 + "时";
        }
        sb.append(str);
        if (i5 <= 0) {
            str2 = "";
        } else {
            str2 = i5 + "分";
        }
        sb.append(str2);
        if (i6 > 0) {
            str3 = i6 + "秒";
        }
        sb.append(str3);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getQrData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getAdjustQrCodeInfo, new AjaxParams(), new AnonymousClass3());
    }

    private void initMagicIndicator() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setScrollPivotX(0.65f);
        this.mCommonNavigator.setAdapter(new AnonymousClass4());
        this.magicIndicator.setNavigator(this.mCommonNavigator);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), this.listviewpage);
        this.mBaseView = baseViewPagerAdapter;
        this.mQuestionViewpager.setAdapter(baseViewPagerAdapter);
        this.mQuestionViewpager.setOffscreenPageLimit(1);
        ViewPagerHelper.bind(this.magicIndicator, this.mQuestionViewpager);
        this.mQuestionViewpager.setCurrentItem(getIntent().getExtras().getInt("position"));
        final int color = ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 1 ? R.color.person_orange_color_night : R.color.person_orange_color);
        final int color2 = ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 1 ? R.color.third_txt_color_night : R.color.third_txt_color);
        this.mQuestionViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.AdjustRankingActivity.5
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                AdjustRankingActivity.this.pagePosition = i2;
                if (i2 == 0) {
                    AdjustRankingActivity adjustRankingActivity = AdjustRankingActivity.this;
                    adjustRankingActivity.adjustRankTitleTv.setText(adjustRankingActivity.adjustRankTitle);
                    if (AdjustRankingActivity.this.adjustPosition == 0) {
                        AdjustRankingActivity adjustRankingActivity2 = AdjustRankingActivity.this;
                        adjustRankingActivity2.school.setText(adjustRankingActivity2.description2.isEmpty() ? "" : StringUtil.setNumColor(AdjustRankingActivity.this.description2, color, color2, 1.2f));
                        return;
                    } else if (AdjustRankingActivity.this.adjustPosition == 1) {
                        AdjustRankingActivity adjustRankingActivity3 = AdjustRankingActivity.this;
                        adjustRankingActivity3.school.setText(adjustRankingActivity3.description3.isEmpty() ? "" : StringUtil.setNumColor(AdjustRankingActivity.this.description3, color, color2, 1.2f));
                        return;
                    } else {
                        if (AdjustRankingActivity.this.adjustPosition == 2) {
                            AdjustRankingActivity adjustRankingActivity4 = AdjustRankingActivity.this;
                            adjustRankingActivity4.school.setText(adjustRankingActivity4.description4.isEmpty() ? "" : StringUtil.setNumColor(AdjustRankingActivity.this.description4, color, color2, 1.2f));
                            return;
                        }
                        return;
                    }
                }
                AdjustRankingActivity adjustRankingActivity5 = AdjustRankingActivity.this;
                adjustRankingActivity5.adjustRankTitleTv.setText(adjustRankingActivity5.allAdjustRankTitle);
                if (AdjustRankingActivity.this.adjustPositionAll == 0) {
                    AdjustRankingActivity adjustRankingActivity6 = AdjustRankingActivity.this;
                    adjustRankingActivity6.school.setText(adjustRankingActivity6.description2All.isEmpty() ? "" : StringUtil.setNumColor(AdjustRankingActivity.this.description2All, color, color2, 1.2f));
                } else if (AdjustRankingActivity.this.adjustPositionAll == 1) {
                    AdjustRankingActivity adjustRankingActivity7 = AdjustRankingActivity.this;
                    adjustRankingActivity7.school.setText(adjustRankingActivity7.description3All.isEmpty() ? "" : StringUtil.setNumColor(AdjustRankingActivity.this.description3All, color, color2, 1.2f));
                } else if (AdjustRankingActivity.this.adjustPositionAll == 2) {
                    AdjustRankingActivity adjustRankingActivity8 = AdjustRankingActivity.this;
                    adjustRankingActivity8.school.setText(adjustRankingActivity8.description4All.isEmpty() ? "" : StringUtil.setNumColor(AdjustRankingActivity.this.description4All, color, color2, 1.2f));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setListenerForWidget$0(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.bindSchool = getIntent().getStringExtra("bindSchool");
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ly_tabbar);
        this.mImgUserHead = (CircleImageView) findViewById(R.id.img_user_head);
        this.mTvNickName = (TextView) findViewById(R.id.tv_nickname);
        this.magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.mQuestionViewpager = (ViewPager) findViewById(R.id.viewpager);
        this.mBtnShare = (TextView) findViewById(R.id.btn_share);
        this.adjustRankTitleTv = (TextView) findViewById(R.id.adjust_rank_title_tv);
        this.iv_ranking_back = (ImageView) findViewById(R.id.iv_ranking_back);
        this.school = (TextView) findViewById(R.id.school);
        this.adjustRankQr = (ImageView) findViewById(R.id.iv_adjust_ranking_qr);
        this.adjustRankShare = (ImageView) findViewById(R.id.iv_adjust_ranking_share);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        relativeLayout.setLayoutParams(layoutParams);
        this.mTitleList = new String[]{"调剂志愿", "全志愿"};
        for (int i2 = 0; i2 < this.mTitleList.length; i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("position", String.valueOf(i2));
            bundle.putString("version", getIntent().getStringExtra("version"));
            bundle.putString("type", getIntent().getStringExtra("type"));
            this.listviewpage.add(new BaseViewPagerAdapter.PagerInfo(this.mTitleList[0], AdjustRankingFragment.class, bundle));
        }
        initMagicIndicator();
        GlideUtils.loadImage(this, UserConfig.getInstance().getUser().getAvatar(), this.mImgUserHead);
        this.adjustRankQr.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.AdjustRankingActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AdjustRankingActivity.this.getQrData();
            }
        });
        this.adjustRankShare.setOnClickListener(new AnonymousClass2());
    }

    @Subscribe
    public void onEventMainThread(RankingContentBean.DataBean event) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("bindSuccess")) {
            this.bindSchool = "1";
        }
    }

    public void setAdjustRankTitle(String adjustRankTitle) {
        this.adjustRankTitle = adjustRankTitle;
        if (this.pagePosition == 0) {
            this.adjustRankTitleTv.setText(adjustRankTitle);
        }
    }

    public void setAllAdjustRankTitle(String allAdjustRankTitle) {
        this.allAdjustRankTitle = allAdjustRankTitle;
    }

    public void setAllShareInfo(RankEntranceBean.DataBean.ShareData allShareData) {
        this.allShareData = allShareData;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
        setContentView(R.layout.layout_adjust_ranking);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AdjustRankingActivity.lambda$setListenerForWidget$0(view);
            }
        });
        this.iv_ranking_back.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12683c.lambda$setListenerForWidget$1(view);
            }
        });
    }

    public void setNickName(String nickname) {
        this.mTvNickName.setText(nickname);
    }

    public void setShareInfo(RankEntranceBean.DataBean.ShareData shareData) {
        this.shareData = shareData;
    }

    public void setmTvNameInfo(String description2, String description3, String description4, int adjustPosition) {
        int color = ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 1 ? R.color.person_orange_color_night : R.color.person_orange_color);
        int color2 = ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 1 ? R.color.third_txt_color_night : R.color.third_txt_color);
        this.description2 = description2;
        this.description3 = description3;
        this.description4 = description4;
        this.adjustPosition = adjustPosition;
        if (adjustPosition == 0) {
            this.school.setText(description2.isEmpty() ? "" : StringUtil.setNumColor(description2, color, color2, 1.2f));
        } else if (adjustPosition == 1) {
            this.school.setText(description3.isEmpty() ? "" : StringUtil.setNumColor(description3, color, color2, 1.2f));
        } else if (adjustPosition == 2) {
            this.school.setText(description4.isEmpty() ? "" : StringUtil.setNumColor(description4, color, color2, 1.2f));
        }
    }

    public void setmTvNameInfoAll(String description2, String description3, String description4, int adjustPosition) {
        int color = ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 1 ? R.color.person_orange_color_night : R.color.person_orange_color);
        int color2 = ContextCompat.getColor(this, SkinManager.getCurrentSkinType(this) == 1 ? R.color.third_txt_color_night : R.color.third_txt_color);
        this.description2All = description2;
        this.description3All = description3;
        this.description4All = description4;
        this.adjustPositionAll = adjustPosition;
        if (adjustPosition == 0) {
            this.school.setText(description2.isEmpty() ? "" : StringUtil.setNumColor(this.description2All, color, color2, 1.2f));
        } else if (adjustPosition == 1) {
            this.school.setText(description3.isEmpty() ? "" : StringUtil.setNumColor(this.description3All, color, color2, 1.2f));
        } else if (adjustPosition == 2) {
            this.school.setText(description4.isEmpty() ? "" : StringUtil.setNumColor(this.description4All, color, color2, 1.2f));
        }
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
