package com.psychiatrygarden.activity.forum.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
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
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.CircleSearchNewActivity;
import com.psychiatrygarden.activity.circleactivity.CirclePushActivity;
import com.psychiatrygarden.activity.courselist.CourseListChpterActivity;
import com.psychiatrygarden.activity.forum.ForumIntroductionActivity;
import com.psychiatrygarden.activity.forum.ForumSearchInfoActivity;
import com.psychiatrygarden.activity.forum.ForumSectionActivity;
import com.psychiatrygarden.activity.forum.ForumUserListActivity;
import com.psychiatrygarden.activity.forum.GiveLogActivity;
import com.psychiatrygarden.activity.forum.bean.ForumChannelBean;
import com.psychiatrygarden.activity.forum.bean.ForumEditEvent;
import com.psychiatrygarden.activity.forum.bean.ForumIndexBean;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseList2Bean;
import com.psychiatrygarden.bean.HomeTabStatus;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.StackLayout;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ForumIndexFragment extends BaseFragment implements View.OnClickListener {
    private AppBarLayout appBarLayout;
    private LinearLayout bartoolview;
    private ClearEditText clearTxt;
    private ForumInfoBean.DataBean dataBean;
    public String group_id;
    private CircleImageView iv_circle_school;
    private ImageView iv_circle_school_menu;
    private MagicIndicator mMagicIndicator;
    private ImageView netimg;
    private StackLayout sl_school_flag_join;
    private TextView titleTxt;
    private TextView tv_school_info;
    private AppCompatTextView tv_school_name;
    private ViewPager viewpager;
    private List<ForumChannelBean.DataBean> dataChannel = new ArrayList();
    private ForumIndexBean.DataBean.ListBean mForumBean = new ForumIndexBean.DataBean.ListBean();

    /* renamed from: com.psychiatrygarden.activity.forum.fragment.ForumIndexFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends CommonNavigatorAdapter {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            ForumIndexFragment.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return ForumIndexFragment.this.dataChannel.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setMode(2);
            linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 4.0d));
            linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 13.0d));
            linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
            linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
            linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
            if (SkinManager.getCurrentSkinType(((BaseFragment) ForumIndexFragment.this).mContext) == 1) {
                linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseFragment) ForumIndexFragment.this).mContext, R.color.comment_color_night)));
            } else {
                linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseFragment) ForumIndexFragment.this).mContext, R.color.app_theme_red)));
            }
            return linePagerIndicator;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin = CommonUtil.px2dip(((BaseFragment) ForumIndexFragment.this).mContext, 10.0f);
            layoutParams.rightMargin = CommonUtil.dip2px(((BaseFragment) ForumIndexFragment.this).mContext, 20.0f);
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setText(((ForumChannelBean.DataBean) ForumIndexFragment.this.dataChannel.get(index)).getTitle());
            colorTransitionPagerTitleView.setLayoutParams(layoutParams);
            colorTransitionPagerTitleView.setTextSize(2, 14.0f);
            if (SkinManager.getCurrentSkinType(((BaseFragment) ForumIndexFragment.this).mContext) == 1) {
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#39456D"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#6472A1"));
            } else {
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#464646"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#FF1C1A15"));
            }
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f12391c.lambda$getTitleView$0(index, view);
                }
            });
            return colorTransitionPagerTitleView;
        }
    }

    private void addStack(final List<ForumInfoBean.DataBean.LabelBean> tags) {
        if (tags != null) {
            try {
                if (tags.size() <= 0) {
                    return;
                }
                this.sl_school_flag_join.removeAllViews();
                for (final int i2 = 0; i2 < tags.size(); i2++) {
                    View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_forum_tag_item2, (ViewGroup) null);
                    ((TextView) viewInflate.findViewById(R.id.tubName)).setText(tags.get(i2).getLabel());
                    ImageView imageView = (ImageView) viewInflate.findViewById(R.id.tubimg);
                    if ("".equals(tags.get(i2).getIcon())) {
                        imageView.setImageResource(R.drawable.app_icon);
                    } else {
                        GlideApp.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(tags.get(i2).getIcon())).into(imageView);
                    }
                    viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.p
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f12387c.lambda$addStack$7(tags, i2, view);
                        }
                    });
                    this.sl_school_flag_join.addView(viewInflate);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabColumn() throws Resources.NotFoundException {
        List<ForumChannelBean.DataBean> list = this.dataChannel;
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.dataChannel.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("group_id", this.group_id + "");
            bundle.putString("id", this.dataChannel.get(i2).getId());
            bundle.putString("text", this.dataChannel.get(i2).getTitle());
            bundle.putInt("module_type", 16);
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.dataChannel.get(i2).getTitle(), ForumListFragment.class, bundle));
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList));
        this.viewpager.setCurrentItem(0);
        this.viewpager.setOffscreenPageLimit(2);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new AnonymousClass3());
        this.mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addStack$7(List list, int i2, View view) {
        CourseList2Bean.DataBean.DataChildBean obj;
        if (!"2".equals(((ForumInfoBean.DataBean.LabelBean) list.get(i2)).getJump()) || (obj = ((ForumInfoBean.DataBean.LabelBean) list.get(i2)).getObj()) == null || TextUtils.isEmpty(obj.getId())) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CourseListChpterActivity.class);
        intent.putExtra("vidteaching", obj);
        intent.putExtra("isForumTrue", true);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        startActivity(new Intent(getActivity(), (Class<?>) GiveLogActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$2(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.clearTxt.getText().toString().equals("")) {
            Intent intent = new Intent(getActivity(), (Class<?>) CircleSearchNewActivity.class);
            intent.putExtra("editTextData", "" + this.clearTxt.getText().toString());
            intent.putExtra("flagEdit", true);
            intent.putExtra("group_id", "" + this.group_id);
            intent.putExtra("module_type", 16);
            startActivity(intent);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putViewDataShow$5(View view) {
        Intent intent = new Intent(getActivity(), (Class<?>) ForumIntroductionActivity.class);
        intent.putExtra("group_id", "" + this.group_id);
        intent.putExtra("introduction", "" + this.dataBean.getIntroduction());
        intent.putExtra("edit_introduction_permission", "" + this.dataBean.getEdit_introduction_permission());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putViewDataShow$6(AppBarLayout appBarLayout, int i2) {
        this.bartoolview.setAlpha(1.0f - Math.abs((i2 * 1.0f) / appBarLayout.getTotalScrollRange()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDesInfo$4() {
        String str;
        int width = ((((this.tv_school_info.getWidth() - this.tv_school_info.getPaddingLeft()) - this.tv_school_info.getPaddingRight()) / ((int) this.tv_school_info.getTextSize())) * 2) - 11;
        if (width <= this.dataBean.getIntroduction().length() && width >= 0) {
            str = "版块简介：" + this.dataBean.getIntroduction().substring(0, width) + "...";
        } else if ("".equals(this.dataBean.getIntroduction())) {
            str = "版块简介：无";
        } else {
            str = "版块简介：" + this.dataBean.getIntroduction() + "";
        }
        this.tv_school_info.setText(new SpannableStringBuilder(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetWorkImg$3(View view) {
        getData();
    }

    public static ForumIndexFragment newInstance(String group_id) {
        Bundle bundle = new Bundle();
        ForumIndexFragment forumIndexFragment = new ForumIndexFragment();
        bundle.putString("group_id", group_id);
        forumIndexFragment.setArguments(bundle);
        return forumIndexFragment;
    }

    public void getData() {
        getForumInfoData();
    }

    public void getForumInfoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + this.group_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumIndexFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumIndexFragment.this.showNetWorkImg(1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    ForumInfoBean forumInfoBean = (ForumInfoBean) new Gson().fromJson(s2, ForumInfoBean.class);
                    if ("200".equals(forumInfoBean.getCode())) {
                        ForumIndexFragment.this.dataBean = forumInfoBean.getData();
                        ForumIndexFragment.this.mForumBean = new ForumIndexBean.DataBean.ListBean();
                        ForumIndexFragment.this.mForumBean.setName(ForumIndexFragment.this.dataBean.getName());
                        ForumIndexFragment.this.mForumBean.setId(ForumIndexFragment.this.dataBean.getId());
                        ForumIndexFragment.this.mForumBean.setIs_follow(ForumIndexFragment.this.dataBean.getIs_follow());
                        ForumIndexFragment.this.mForumBean.setLogo(ForumIndexFragment.this.dataBean.getLogo());
                        ForumIndexFragment.this.mForumBean.setAccess("1");
                        ForumIndexFragment.this.mForumBean.setArticle_count(ForumIndexFragment.this.dataBean.getArticle_count() + "帖子");
                        ForumIndexFragment.this.mForumBean.setUser_count(ForumIndexFragment.this.dataBean.getUser_count() + "关注");
                        ForumIndexFragment.this.mForumBean.setComment_count(ForumIndexFragment.this.dataBean.getComment_count() + "讨论");
                        ForumIndexFragment.this.mForumBean.setNew_message("0");
                        ForumIndexFragment.this.putViewDataShow();
                        ForumIndexFragment.this.getForumLabelListData();
                    }
                    ForumIndexFragment.this.showNetWorkImg(0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getForumLabelListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + this.group_id);
        ajaxParams.put("type", "list");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumlabellistApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumIndexFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ForumChannelBean forumChannelBean = (ForumChannelBean) new Gson().fromJson(s2, ForumChannelBean.class);
                    if (forumChannelBean.getCode().equals("200")) {
                        ForumIndexFragment.this.dataChannel = forumChannelBean.getData();
                        ForumIndexFragment.this.initTabColumn();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_forum_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws Resources.NotFoundException {
        if (getArguments() != null) {
            this.group_id = getArguments().getString("group_id");
        }
        this.netimg = (ImageView) holder.get(R.id.netimg);
        ImageView imageView = (ImageView) holder.get(R.id.logImgClick);
        imageView.setVisibility(8);
        ((ImageView) holder.get(R.id.back)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12382c.lambda$initViews$0(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12383c.lambda$initViews$1(view);
            }
        });
        this.clearTxt = (ClearEditText) holder.get(R.id.clearTxt);
        ImageView imageView2 = (ImageView) holder.get(R.id.iv_circle_school_search);
        this.tv_school_name = (AppCompatTextView) holder.get(R.id.tv_school_name);
        this.tv_school_info = (TextView) holder.get(R.id.tv_school_info);
        this.iv_circle_school = (CircleImageView) holder.get(R.id.iv_circle_school);
        this.sl_school_flag_join = (StackLayout) holder.get(R.id.sl_school_flag_join);
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.appBarLayout = (AppBarLayout) holder.get(R.id.appBarLayout);
        this.viewpager = (ViewPager) holder.get(R.id.listf);
        this.bartoolview = (LinearLayout) holder.get(R.id.bartoolview);
        FloatingActionButton floatingActionButton = (FloatingActionButton) holder.get(R.id.floatButton);
        this.titleTxt = (TextView) holder.get(R.id.titleTxt);
        if (getActivity() instanceof ForumSearchInfoActivity) {
            if (getActivity() != null) {
                if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                    CommonUtil.mDoDrawable(getActivity(), this.titleTxt, R.drawable.icon_left_back_night, 0);
                } else {
                    CommonUtil.mDoDrawable(getActivity(), this.titleTxt, R.drawable.icon_left_back, 0);
                }
            }
        } else if (getActivity() != null) {
            if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                CommonUtil.mDoDrawable(getActivity(), this.titleTxt, R.mipmap.changeimg_night, 2);
            } else {
                CommonUtil.mDoDrawable(getActivity(), this.titleTxt, R.mipmap.changeimg, 2);
            }
        }
        this.iv_circle_school_menu = (ImageView) holder.get(R.id.iv_circle_school_menu);
        TextView textView = (TextView) holder.get(R.id.followimg);
        this.tv_school_name.setOnClickListener(this);
        textView.setOnClickListener(this);
        this.iv_circle_school_menu.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        this.titleTxt.setOnClickListener(this);
        this.clearTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.forum.fragment.m
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                return this.f12384c.lambda$initViews$2(textView2, i2, keyEvent);
            }
        });
        getData();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (isLogin()) {
            if (id == R.id.tv_school_name || id == R.id.titleTxt) {
                if (getActivity() instanceof ForumSearchInfoActivity) {
                    getActivity().finish();
                    return;
                }
                Intent intent = new Intent(getActivity(), (Class<?>) ForumSectionActivity.class);
                intent.putExtra("parent_id", "1");
                startActivity(intent);
                return;
            }
            if (id == R.id.iv_circle_school_menu) {
                Intent intent2 = new Intent(getActivity(), (Class<?>) ForumUserListActivity.class);
                intent2.putExtra("group_id", "" + this.group_id);
                startActivity(intent2);
                return;
            }
            if (id == R.id.floatButton) {
                Intent intent3 = new Intent(this.mContext, (Class<?>) CirclePushActivity.class);
                intent3.putExtra("group_id", "" + this.group_id);
                intent3.putExtra("htmlContent", "");
                intent3.putExtra("htmlTitle", "");
                intent3.putExtra("module_type", 16);
                startActivity(intent3);
                return;
            }
            if (id == R.id.iv_circle_school_search) {
                Intent intent4 = new Intent(getActivity(), (Class<?>) CircleSearchNewActivity.class);
                intent4.putExtra("editTextData", "");
                intent4.putExtra("flagEdit", false);
                intent4.putExtra("group_id", "" + this.group_id);
                intent4.putExtra("module_type", 16);
                startActivity(intent4);
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if ("Result_GroupId".equals(str)) {
            this.group_id = SharePreferencesUtils.readStrConfig(CommonParameter.GroupId, this.mContext, "");
            getData();
        }
    }

    public void putViewDataShow() {
        ForumInfoBean.DataBean dataBean = this.dataBean;
        if (dataBean != null) {
            if ("0".equals(dataBean.getAudit_join())) {
                this.iv_circle_school_menu.setVisibility(0);
            } else {
                this.iv_circle_school_menu.setVisibility(8);
            }
            this.titleTxt.setText(this.dataBean.getName());
            this.tv_school_name.setText(this.dataBean.getName());
            setDesInfo();
            this.tv_school_info.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.n
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12385c.lambda$putViewDataShow$5(view);
                }
            });
            GlideApp.with(this.iv_circle_school.getContext()).load((Object) GlideUtils.generateUrl(this.dataBean.getLogo())).apply((BaseRequestOptions<?>) new RequestOptions().error(R.drawable.schooldefaultimg)).placeholder(R.drawable.schooldefaultimg).into(this.iv_circle_school);
            if (this.dataBean.getLabels() == null || this.dataBean.getLabels().size() <= 0) {
                this.sl_school_flag_join.setVisibility(8);
                this.bartoolview.setVisibility(8);
                this.titleTxt.setVisibility(0);
                this.appBarLayout.addOnOffsetChangedListener((AppBarLayout.OnOffsetChangedListener) null);
                return;
            }
            this.sl_school_flag_join.setVisibility(0);
            addStack(this.dataBean.getLabels());
            this.bartoolview.setVisibility(0);
            this.titleTxt.setVisibility(0);
            this.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.forum.fragment.o
                @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
                public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                    this.f12386a.lambda$putViewDataShow$6(appBarLayout, i2);
                }
            });
        }
    }

    public void setDesInfo() {
        TextView textView = this.tv_school_info;
        if (textView == null) {
            return;
        }
        textView.post(new Runnable() { // from class: com.psychiatrygarden.activity.forum.fragment.j
            @Override // java.lang.Runnable
            public final void run() {
                this.f12381c.lambda$setDesInfo$4();
            }
        });
    }

    public void showNetWorkImg(int type) {
        int currentSkinType = SkinManager.getCurrentSkinType(this.mContext);
        if (type == 0) {
            this.netimg.setImageResource(currentSkinType == 0 ? R.drawable.icon_empty : R.drawable.icon_empty_night);
        } else {
            this.netimg.setImageResource(currentSkinType == 0 ? R.drawable.icon_net_load_error : R.drawable.icon_net_load_error_night);
        }
        if (!TextUtils.isEmpty(this.titleTxt.getText())) {
            this.netimg.setVisibility(8);
        } else {
            this.netimg.setVisibility(0);
            this.netimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12390c.lambda$showNetWorkImg$3(view);
                }
            });
        }
    }

    public void onEventMainThread(ForumEditEvent forumEditEvent) {
        TextView textView = this.tv_school_info;
        if (textView == null || this.dataBean == null) {
            return;
        }
        textView.setText(forumEditEvent.content);
        this.dataBean.setIntroduction(forumEditEvent.content);
        setDesInfo();
    }

    public void onEventMainThread(HomeTabStatus homeTabStatus) {
        if (homeTabStatus.mEvestr.equals("showInput")) {
            if (8 == homeTabStatus.position) {
                if (this.clearTxt.getVisibility() != 8) {
                    this.clearTxt.setVisibility(8);
                    return;
                }
                return;
            }
            this.clearTxt.setVisibility(0);
        }
    }
}
