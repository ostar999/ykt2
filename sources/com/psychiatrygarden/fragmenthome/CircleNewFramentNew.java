package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.circleactivity.CircleChannelActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChannelItem;
import com.psychiatrygarden.bean.CircleChannelBean;
import com.psychiatrygarden.bean.CircleDataBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class CircleNewFramentNew extends BaseFragment {
    private List<CircleDataBean.DataBean.DefaultBean> _default;
    private List<CircleDataBean.DataBean.ListBean> _list;
    private ImageView iv_network_load_fail;
    private LinearLayout line_id;
    private ImageView load_anim;
    private BaseViewPagerAdapter mBaseView;
    private CircleDataBean mCircleDataBean;
    private CommonNavigator mCommonNavigator;
    private Context mContext;
    private MagicIndicator mMagicIndicator;
    private ViewPager viewpager;
    private ArrayList<ChannelItem> mDefaultList = new ArrayList<>();
    private ArrayList<ChannelItem> mUserList = new ArrayList<>();
    private int mItemWidth = 0;

    /* renamed from: com.psychiatrygarden.fragmenthome.CircleNewFramentNew$2, reason: invalid class name */
    public class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            CircleNewFramentNew.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return CircleNewFramentNew.this.mDefaultList.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(CircleNewFramentNew.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_forum_tab);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.item_question_column);
            textView.setText(((ChannelItem) CircleNewFramentNew.this.mDefaultList.get(index)).getName());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.g0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15605c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.CircleNewFramentNew.2.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor(SkinManager.getCurrentSkinType(CircleNewFramentNew.this.requireContext()) == 1 ? "#0D111D" : "#F9FAFB"));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(CircleNewFramentNew.this.requireContext(), 8.0f));
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(CircleNewFramentNew.this.requireContext()) == 1 ? "#7380A9" : "#141516"));
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
                    gradientDrawable.setColor(Color.parseColor(SkinManager.getCurrentSkinType(CircleNewFramentNew.this.requireContext()) == 1 ? "#422A33" : "#FFF1F0"));
                    gradientDrawable.setCornerRadius(CommonUtil.dip2px(CircleNewFramentNew.this.requireContext(), 8.0f));
                    textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(CircleNewFramentNew.this.requireContext()) == 1 ? "#B2575C" : "#F95843"));
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
    public void initTabColumn() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mDefaultList.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", this.mDefaultList.get(i2).getId());
            bundle.putString("text", this.mDefaultList.get(i2).getName());
            bundle.putInt("module_type", getArguments().getInt("module_type"));
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.mDefaultList.get(i2).getName(), CircleListNewFragment.class, bundle));
        }
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList);
        this.mBaseView = baseViewPagerAdapter;
        this.viewpager.setAdapter(baseViewPagerAdapter);
        this.viewpager.setCurrentItem(0);
        this.viewpager.setOffscreenPageLimit(2);
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        this.mCommonNavigator = commonNavigator;
        commonNavigator.setSkimOver(true);
        this.mCommonNavigator.setAdapter(new AnonymousClass2());
        this.mMagicIndicator.setNavigator(this.mCommonNavigator);
        ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
        mStopAnim(this.load_anim);
        this.load_anim.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        if (isLogin()) {
            ArrayList<ChannelItem> arrayList = this.mDefaultList;
            if (arrayList == null || arrayList.size() <= 0) {
                AlertToast("数据异常请先刷新数据！");
                return;
            }
            Intent intent = new Intent(this.mContext, (Class<?>) CircleChannelActivity.class);
            intent.putExtra("mDefaultList", this.mDefaultList);
            intent.putExtra("mUserList", this.mUserList);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        getData();
    }

    public static CircleNewFramentNew newInstance(int module_type) {
        Bundle bundle = new Bundle();
        bundle.putInt("module_type", module_type);
        CircleNewFramentNew circleNewFramentNew = new CircleNewFramentNew();
        circleNewFramentNew.setArguments(bundle);
        return circleNewFramentNew;
    }

    public void getData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mgetList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CircleNewFramentNew.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleNewFramentNew.this.iv_network_load_fail.setVisibility(0);
                CircleNewFramentNew.this.line_id.setVisibility(8);
                CircleNewFramentNew circleNewFramentNew = CircleNewFramentNew.this;
                circleNewFramentNew.mStopAnim(circleNewFramentNew.load_anim);
                CircleNewFramentNew.this.load_anim.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    CircleNewFramentNew.this.mCircleDataBean = (CircleDataBean) new Gson().fromJson(s2, CircleDataBean.class);
                    if (CircleNewFramentNew.this.mCircleDataBean.getCode().equals("200")) {
                        CircleNewFramentNew circleNewFramentNew = CircleNewFramentNew.this;
                        circleNewFramentNew._default = circleNewFramentNew.mCircleDataBean.getData().get_default();
                        CircleNewFramentNew circleNewFramentNew2 = CircleNewFramentNew.this;
                        circleNewFramentNew2._list = circleNewFramentNew2.mCircleDataBean.getData().get_list();
                        if (CircleNewFramentNew.this._default != null && CircleNewFramentNew.this._default.size() > 0) {
                            CircleNewFramentNew.this.mDefaultList.clear();
                            for (int i2 = 0; i2 < CircleNewFramentNew.this._default.size(); i2++) {
                                ChannelItem channelItem = new ChannelItem();
                                channelItem.setId(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) CircleNewFramentNew.this._default.get(i2)).getId()));
                                channelItem.setName(((CircleDataBean.DataBean.DefaultBean) CircleNewFramentNew.this._default.get(i2)).getTitle());
                                channelItem.setOrderId(i2);
                                channelItem.setSort(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) CircleNewFramentNew.this._default.get(i2)).getSort())));
                                channelItem.setSelected(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) CircleNewFramentNew.this._default.get(i2)).getIs_default())));
                                channelItem.setToday_topic_num(((CircleDataBean.DataBean.DefaultBean) CircleNewFramentNew.this._default.get(i2)).getPid());
                                channelItem.setInitials(((CircleDataBean.DataBean.DefaultBean) CircleNewFramentNew.this._default.get(i2)).getInitials());
                                CircleNewFramentNew.this.mDefaultList.add(channelItem);
                            }
                        }
                        if (CircleNewFramentNew.this._list != null && CircleNewFramentNew.this._list.size() > 0) {
                            CircleNewFramentNew.this.mUserList.clear();
                            for (int i3 = 0; i3 < CircleNewFramentNew.this._list.size(); i3++) {
                                ChannelItem channelItem2 = new ChannelItem();
                                channelItem2.setId(Integer.parseInt(((CircleDataBean.DataBean.ListBean) CircleNewFramentNew.this._list.get(i3)).getId()));
                                channelItem2.setName(((CircleDataBean.DataBean.ListBean) CircleNewFramentNew.this._list.get(i3)).getTitle());
                                channelItem2.setOrderId(i3);
                                channelItem2.setSort(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.ListBean) CircleNewFramentNew.this._list.get(i3)).getSort())));
                                channelItem2.setSelected(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.ListBean) CircleNewFramentNew.this._list.get(i3)).getIs_default())));
                                channelItem2.setToday_topic_num(((CircleDataBean.DataBean.ListBean) CircleNewFramentNew.this._list.get(i3)).getPid());
                                channelItem2.setInitials(((CircleDataBean.DataBean.ListBean) CircleNewFramentNew.this._list.get(i3)).getInitials());
                                CircleNewFramentNew.this.mUserList.add(channelItem2);
                            }
                        }
                        CircleNewFramentNew.this.iv_network_load_fail.setVisibility(8);
                        CircleNewFramentNew.this.line_id.setVisibility(0);
                        CircleNewFramentNew.this.initTabColumn();
                    } else {
                        CircleNewFramentNew.this.iv_network_load_fail.setVisibility(0);
                        CircleNewFramentNew circleNewFramentNew3 = CircleNewFramentNew.this;
                        circleNewFramentNew3.mStopAnim(circleNewFramentNew3.load_anim);
                        CircleNewFramentNew.this.load_anim.setVisibility(8);
                        CircleNewFramentNew.this.AlertToast("" + CircleNewFramentNew.this.mCircleDataBean.getMessage());
                    }
                    SharePreferencesUtils.writeLongConfig(CommonParameter.circlrListnum, Long.valueOf(Long.parseLong(CircleNewFramentNew.this.mCircleDataBean.getFixedCount())), CircleNewFramentNew.this.mContext);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CircleNewFramentNew.this.iv_network_load_fail.setVisibility(0);
                    CircleNewFramentNew.this.line_id.setVisibility(8);
                    CircleNewFramentNew circleNewFramentNew4 = CircleNewFramentNew.this;
                    circleNewFramentNew4.mStopAnim(circleNewFramentNew4.load_anim);
                    CircleNewFramentNew.this.load_anim.setVisibility(8);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_circle_new;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.circlehs);
        this.viewpager = (ViewPager) holder.get(R.id.viewpagecircle);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.ll_more_columns);
        this.load_anim = (ImageView) holder.get(R.id.load_anim);
        this.line_id = (LinearLayout) holder.get(R.id.line_id);
        this.iv_network_load_fail = (ImageView) holder.get(R.id.iv_network_load_fail);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15555c.lambda$initViews$0(view);
            }
        });
        this.mItemWidth = CommonUtil.getScreenWidth(this.mContext) / 4;
        ((Animatable) this.load_anim.getDrawable()).start();
        this.iv_network_load_fail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15580c.lambda$initViews$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public void onEventMainThread(CircleChannelBean mCircleChannelBean) {
        if (mCircleChannelBean.getTag().equals(EventBusConstant.EVENT_Circle_CHANGE)) {
            this.mDefaultList.clear();
            this.mDefaultList = (ArrayList) mCircleChannelBean.getUserChannelList();
            this.mUserList.clear();
            this.mUserList = (ArrayList) mCircleChannelBean.getOtherChannelList();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mDefaultList.size(); i2++) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", this.mDefaultList.get(i2).getId());
                bundle.putString("text", this.mDefaultList.get(i2).getName());
                bundle.putInt("module_type", getArguments().getInt("module_type"));
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.mDefaultList.get(i2).getName(), CircleListNewFragment.class, bundle));
            }
            this.mCommonNavigator.notifyDataSetChanged();
            this.mBaseView.setPageInfo(arrayList);
            this.mBaseView.notifyDataSetChanged();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(final String str) {
        if (str.equals("isReploadData")) {
            this.mDefaultList.clear();
            getData();
        }
    }
}
