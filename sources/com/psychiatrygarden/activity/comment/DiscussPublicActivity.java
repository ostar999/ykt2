package com.psychiatrygarden.activity.comment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentSeachActivity;
import com.psychiatrygarden.activity.QuestionCommentActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussRuleBean;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.comment.fragment.DiscussFragment;
import com.psychiatrygarden.activity.comment.widget.BottomInputView;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomPagerTitlerView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class DiscussPublicActivity extends BaseActivity implements BottomInputView.BottomInputOnclickIml {
    public BaseViewPagerAdapter adapter;
    public Bundle bundle;
    public DiscussStatus commentEnum;
    private String commentId;
    public CommonNavigator commonNavigator;
    public DiscussRuleBean discussRuleBean;
    public TextView group_nametv;
    public boolean isCommentTrue;
    private String isProhibit;
    public boolean isZantongTrue;
    public LinearLayout linetitle;
    public MagicIndicator magic_indicator;
    public int module_type;
    public String obj_id;
    public BottomInputView rl_topic_detail_bottom;
    public ViewPager viewpager;
    public List<BaseViewPagerAdapter.PagerInfo> listpager = new ArrayList();
    public String comment_type = "2";
    public String target_user_id = "";
    public String only_author = "0";
    public String isShowVideo = "0";
    private boolean isReplyCollection = false;

    /* renamed from: com.psychiatrygarden.activity.comment.DiscussPublicActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends CommonNavigatorAdapter {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            DiscussPublicActivity.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return DiscussPublicActivity.this.discussRuleBean.getData().getLabels().size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CustomPagerTitlerView customPagerTitlerView = new CustomPagerTitlerView(DiscussPublicActivity.this);
            customPagerTitlerView.setPadding(ScreenUtil.getPxByDp(DiscussPublicActivity.this.mContext, 8), ScreenUtil.getPxByDp(DiscussPublicActivity.this.mContext, 3), ScreenUtil.getPxByDp(DiscussPublicActivity.this.mContext, 8), ScreenUtil.getPxByDp(DiscussPublicActivity.this.mContext, 3));
            customPagerTitlerView.setText(DiscussPublicActivity.this.discussRuleBean.getData().getLabels().get(index).getLabel());
            customPagerTitlerView.setTextSize(2, 12.0f);
            if (SkinManager.getCurrentSkinType(DiscussPublicActivity.this) == 1) {
                customPagerTitlerView.setSelectedColor(DiscussPublicActivity.this.getResources().getColor(R.color.white_color02_night));
                customPagerTitlerView.setNormalColor(DiscussPublicActivity.this.getResources().getColor(R.color.dominant_color_night));
            } else {
                customPagerTitlerView.setSelectedColor(DiscussPublicActivity.this.getResources().getColor(R.color.white_color02));
                customPagerTitlerView.setNormalColor(DiscussPublicActivity.this.getResources().getColor(R.color.dominant_color));
            }
            customPagerTitlerView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f11692c.lambda$getTitleView$0(index, view);
                }
            });
            return customPagerTitlerView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        Intent intent = new Intent(this, (Class<?>) QuestionCommentActivity.class);
        intent.putExtra("question_id", this.obj_id + "");
        intent.putExtra("title", "热评");
        intent.putExtra("module_type", this.module_type);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        if (this.only_author.equals("1")) {
            AlertToast("查看全部评论");
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.mTvActionbarRight.setBackground(getResources().getDrawable(R.drawable.stoke_kongwrite));
                this.mTvActionbarRight.setTextColor(getResources().getColor(R.color.white));
            } else {
                this.mTvActionbarRight.setBackground(getResources().getDrawable(R.drawable.stoke_kongwrite_night));
                this.mTvActionbarRight.setTextColor(getResources().getColor(R.color.app_title_color_night));
            }
            this.only_author = "0";
            EventBus.getDefault().post("autoData");
            return;
        }
        AlertToast("只看作者评论");
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mTvActionbarRight.setBackground(getResources().getDrawable(R.drawable.stoke_fullwrite));
            this.mTvActionbarRight.setTextColor(getResources().getColor(R.color.app_theme_red));
        } else {
            this.mTvActionbarRight.setBackground(getResources().getDrawable(R.drawable.stoke_fullwrite_night));
            this.mTvActionbarRight.setTextColor(getResources().getColor(R.color.black));
        }
        this.only_author = "1";
        EventBus.getDefault().post("autoData");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) CommentSeachActivity.class);
        intent.putExtra("question_id", "" + this.obj_id);
        intent.putExtra("module_type", this.module_type);
        startActivity(intent);
    }

    public void changeCommentnum() {
        String str;
        try {
            String tilte = getTilte();
            Matcher matcher = Pattern.compile("[0-9]").matcher(tilte);
            String str2 = "";
            while (matcher.find()) {
                str2 = str2 + tilte.substring(matcher.start(), matcher.end());
            }
            if (str2.equals("")) {
                str = "1";
            } else {
                str = (Integer.parseInt(str2) + 1) + "";
            }
            setTitle("评论（" + str + "）");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void getDiscussData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_type", "" + this.module_type);
        ajaxParams.put("obj_id", "" + this.obj_id);
        YJYHttpUtils.get(this, NetworkRequestsURL.commentRuleApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.comment.DiscussPublicActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    DiscussPublicActivity.this.discussRuleBean = (DiscussRuleBean) new Gson().fromJson(s2, DiscussRuleBean.class);
                    if (DiscussPublicActivity.this.discussRuleBean.getCode().equals("200")) {
                        DiscussPublicActivity.this.initTabView();
                        DiscussPublicActivity.this.setTitle("" + DiscussPublicActivity.this.discussRuleBean.getData().getPageTitle());
                        if (DiscussPublicActivity.this.discussRuleBean.getData().getLabels().size() <= 1) {
                            DiscussPublicActivity.this.linetitle.setVisibility(8);
                        } else {
                            DiscussPublicActivity.this.linetitle.setVisibility(0);
                            DiscussPublicActivity.this.group_nametv.setText(DiscussPublicActivity.this.discussRuleBean.getData().getLabels().get(0).getDescription() + "");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.module_type = getIntent().getExtras().getInt("module_type");
        this.obj_id = getIntent().getExtras().getString("obj_id");
        this.commentId = getIntent().getExtras().getString("comment_id");
        this.commentEnum = (DiscussStatus) getIntent().getExtras().getSerializable("commentEnum");
        String stringExtra = getIntent().getStringExtra("isProhibit");
        this.isProhibit = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            this.isProhibit = "0";
        }
        this.isReplyCollection = getIntent().getExtras().getBoolean("isReplyCollection", false);
        if (this.commentEnum == null) {
            this.commentEnum = DiscussStatus.QuestionBankComments;
        }
        this.comment_type = getIntent().getExtras().getString("comment_type");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linetitle);
        this.linetitle = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11690c.lambda$init$0(view);
            }
        });
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        this.group_nametv = (TextView) findViewById(R.id.group_nametv);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.iv_actionbar_right.setImageResource(R.drawable.csimgnight);
        } else {
            this.iv_actionbar_right.setImageResource(R.drawable.csimg);
        }
        this.mTvActionbarRight.setText("作者");
        this.mTvActionbarRight.setPadding(ScreenUtil.getPxByDp((Context) this, 8), ScreenUtil.getPxByDp((Context) this, 3), ScreenUtil.getPxByDp((Context) this, 8), ScreenUtil.getPxByDp((Context) this, 3));
        this.mTvActionbarRight.setTextSize(2, 10.0f);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mTvActionbarRight.setBackground(getResources().getDrawable(R.drawable.stoke_kongwrite));
            this.mTvActionbarRight.setTextColor(getResources().getColor(R.color.white));
        } else {
            this.mTvActionbarRight.setBackground(getResources().getDrawable(R.drawable.stoke_kongwrite_night));
            this.mTvActionbarRight.setTextColor(getResources().getColor(R.color.app_title_color_night));
        }
        this.mTvActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11691c.lambda$init$1(view);
            }
        });
        BottomInputView bottomInputView = (BottomInputView) findViewById(R.id.rl_topic_detail_bottom);
        this.rl_topic_detail_bottom = bottomInputView;
        bottomInputView.setComment_type(this.comment_type);
        this.rl_topic_detail_bottom.setContext(this);
        this.rl_topic_detail_bottom.setModule_type(this.module_type);
        this.rl_topic_detail_bottom.setObj_id(this.obj_id);
        this.rl_topic_detail_bottom.setCommentEnum(this.commentEnum);
        this.rl_topic_detail_bottom.setIsProhibit(this.isProhibit);
        this.rl_topic_detail_bottom.initView();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 1) {
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    public void initTabView() throws Resources.NotFoundException {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        this.commonNavigator = commonNavigator;
        commonNavigator.setAdapter(new AnonymousClass1());
        this.commonNavigator.setFollowTouch(true);
        this.magic_indicator.setNavigator(this.commonNavigator);
        this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.comment.DiscussPublicActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
                DiscussPublicActivity.this.magic_indicator.onPageScrollStateChanged(state);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                DiscussPublicActivity.this.magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                DiscussPublicActivity.this.magic_indicator.onPageSelected(position);
                DiscussPublicActivity.this.group_nametv.setText(DiscussPublicActivity.this.discussRuleBean.getData().getLabels().get(position).getDescription() + "");
            }
        });
        ViewPagerHelper.bind(this.magic_indicator, this.viewpager);
        int i2 = 0;
        while (i2 < this.discussRuleBean.getData().getLabels().size()) {
            Bundle bundle = new Bundle();
            bundle.putString("obj_id", "" + this.obj_id);
            bundle.putInt("module_type", this.module_type);
            bundle.putString(com.heytap.mcssdk.constant.b.f7191p, "" + this.discussRuleBean.getData().getLabels().get(i2).getValue());
            bundle.putString("comment_type", "" + this.comment_type);
            bundle.putString("target_user_id", "" + this.target_user_id);
            bundle.putString("isShowVideo", "" + this.isShowVideo);
            bundle.putSerializable("commentEnum", this.commentEnum);
            bundle.putInt("count", this.discussRuleBean.getData().getLabels().size() - 1);
            bundle.putBoolean("isLastposition", i2 == this.discussRuleBean.getData().getLabels().size() - 1);
            bundle.putBundle("bundle", this.bundle);
            bundle.putBoolean("isReplyCollection", this.isReplyCollection);
            bundle.putBoolean("noAccess", getIntent().getBooleanExtra("noAccess", false));
            bundle.putString("isProhibit", this.isProhibit);
            bundle.putString("comment_id", this.commentId);
            this.listpager.add(new BaseViewPagerAdapter.PagerInfo("", DiscussFragment.class, bundle));
            i2++;
        }
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this, getSupportFragmentManager(), this.listpager);
        this.adapter = baseViewPagerAdapter;
        this.viewpager.setAdapter(baseViewPagerAdapter);
        this.viewpager.setOffscreenPageLimit(3);
    }

    @Override // com.psychiatrygarden.activity.comment.widget.BottomInputView.BottomInputOnclickIml
    public void mPostSuccessFul() throws Resources.NotFoundException {
        BaseViewPagerAdapter baseViewPagerAdapter;
        if (this.viewpager == null || (baseViewPagerAdapter = this.adapter) == null || baseViewPagerAdapter.getSize() <= 1) {
            return;
        }
        this.viewpager.setCurrentItem(this.adapter.getSize() - 1);
        changeCommentnum();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        BottomInputView bottomInputView = this.rl_topic_detail_bottom;
        if (bottomInputView != null) {
            if (newConfig.orientation == 1) {
                bottomInputView.setVisibility(0);
                if (this.mBaseTheme == 1) {
                    getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
                    return;
                }
                return;
            }
            bottomInputView.setVisibility(8);
            if (this.mBaseTheme == 1) {
                getWindow().setNavigationBarColor(Color.parseColor("#121622"));
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setVisibility(8);
        setTitle("" + getIntent().getExtras().getString("title"));
        if (this.commentEnum.getCode() == 2) {
            this.isCommentTrue = getIntent().getExtras().getBoolean("isCommentTrue");
            this.isZantongTrue = getIntent().getExtras().getBoolean("isZantongTrue");
            this.iv_actionbar_right.setVisibility(0);
            this.rl_topic_detail_bottom.setCommentTrue(this.isCommentTrue);
            this.rl_topic_detail_bottom.setZantongTrue(this.isZantongTrue);
            getDiscussData();
            return;
        }
        if (this.commentEnum.getCode() == 3) {
            this.mTvActionbarRight.setVisibility(0);
        } else if (this.commentEnum.getCode() != 0 && this.commentEnum.getCode() != 1) {
            if (this.commentEnum.getCode() == 5 || this.commentEnum.getCode() == 6 || this.commentEnum.getCode() == 7) {
                this.target_user_id = getIntent().getExtras().getString("target_user_id");
                this.rl_topic_detail_bottom.setVisibility(8);
                if (this.mBaseTheme == 1) {
                    getWindow().setNavigationBarColor(Color.parseColor("#121622"));
                }
            } else if (this.commentEnum.getCode() == 9) {
                this.rl_topic_detail_bottom.setVisibility(8);
                if (this.mBaseTheme == 1) {
                    getWindow().setNavigationBarColor(Color.parseColor("#121622"));
                }
            } else if (this.commentEnum.getCode() == 10) {
                this.mActionBar.hide();
                this.isShowVideo = getIntent().getExtras().getString("isShowVideo");
                Bundle bundleExtra = getIntent().getBundleExtra("bundle");
                this.bundle = bundleExtra;
                this.rl_topic_detail_bottom.setBundle(bundleExtra);
            } else if (this.commentEnum.getCode() == 11) {
                this.isCommentTrue = getIntent().getExtras().getBoolean("isCommentTrue");
                this.isZantongTrue = getIntent().getExtras().getBoolean("isZantongTrue");
                this.rl_topic_detail_bottom.setCommentTrue(this.isCommentTrue);
                this.rl_topic_detail_bottom.setZantongTrue(this.isZantongTrue);
            } else if (this.commentEnum.getCode() == 12) {
                this.iv_actionbar_right.setVisibility(0);
            }
        }
        showFragment();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_discuss_public);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.comment.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11633c.lambda$setListenerForWidget$2(view);
            }
        });
    }

    public void showFragment() {
        try {
            this.discussRuleBean = new DiscussRuleBean();
            DiscussRuleBean.DataDTO dataDTO = new DiscussRuleBean.DataDTO();
            ArrayList arrayList = new ArrayList();
            DiscussRuleBean.DataDTO.LabelsDTO labelsDTO = new DiscussRuleBean.DataDTO.LabelsDTO();
            labelsDTO.setLabel("我的评论");
            labelsDTO.setDescription("我的评论");
            labelsDTO.setValue("");
            arrayList.add(labelsDTO);
            dataDTO.setLabels(arrayList);
            this.discussRuleBean.setData(dataDTO);
            initTabView();
            this.linetitle.setVisibility(8);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
