package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
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
public class CircleNewFrament extends BaseFragment {
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

    /* renamed from: com.psychiatrygarden.fragmenthome.CircleNewFrament$2, reason: invalid class name */
    public class AnonymousClass2 extends CommonNavigatorAdapter {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            CircleNewFrament.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return CircleNewFrament.this.mDefaultList.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin = CommonUtil.px2dip(CircleNewFrament.this.mContext, 10.0f);
            if (index == CircleNewFrament.this.mDefaultList.size() - 1) {
                layoutParams.rightMargin = CommonUtil.px2dip(CircleNewFrament.this.mContext, 50.0f);
            } else {
                layoutParams.rightMargin = CommonUtil.px2dip(CircleNewFrament.this.mContext, 20.0f);
            }
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setText(((ChannelItem) CircleNewFrament.this.mDefaultList.get(index)).getName());
            colorTransitionPagerTitleView.setLayoutParams(layoutParams);
            if (SkinManager.getCurrentSkinType(CircleNewFrament.this.mContext) == 1) {
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#7380A9"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#B2575C"));
            } else {
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#555555"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#dd594a"));
            }
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.d0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f15527c.lambda$getTitleView$0(index, view);
                }
            });
            return colorTransitionPagerTitleView;
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

    public static CircleNewFrament newInstance(int module_type) {
        Bundle bundle = new Bundle();
        bundle.putInt("module_type", module_type);
        CircleNewFrament circleNewFrament = new CircleNewFrament();
        circleNewFrament.setArguments(bundle);
        return circleNewFrament;
    }

    public void getData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.mgetList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CircleNewFrament.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleNewFrament.this.iv_network_load_fail.setVisibility(0);
                CircleNewFrament.this.line_id.setVisibility(8);
                CircleNewFrament circleNewFrament = CircleNewFrament.this;
                circleNewFrament.mStopAnim(circleNewFrament.load_anim);
                CircleNewFrament.this.load_anim.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    CircleNewFrament.this.mCircleDataBean = (CircleDataBean) new Gson().fromJson(s2, CircleDataBean.class);
                    if (CircleNewFrament.this.mCircleDataBean.getCode().equals("200")) {
                        CircleNewFrament circleNewFrament = CircleNewFrament.this;
                        circleNewFrament._default = circleNewFrament.mCircleDataBean.getData().get_default();
                        CircleNewFrament circleNewFrament2 = CircleNewFrament.this;
                        circleNewFrament2._list = circleNewFrament2.mCircleDataBean.getData().get_list();
                        if (CircleNewFrament.this._default != null && CircleNewFrament.this._default.size() > 0) {
                            CircleNewFrament.this.mDefaultList.clear();
                            for (int i2 = 0; i2 < CircleNewFrament.this._default.size(); i2++) {
                                ChannelItem channelItem = new ChannelItem();
                                channelItem.setId(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) CircleNewFrament.this._default.get(i2)).getId()));
                                channelItem.setName(((CircleDataBean.DataBean.DefaultBean) CircleNewFrament.this._default.get(i2)).getTitle());
                                channelItem.setOrderId(i2);
                                channelItem.setSort(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) CircleNewFrament.this._default.get(i2)).getSort())));
                                channelItem.setSelected(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.DefaultBean) CircleNewFrament.this._default.get(i2)).getIs_default())));
                                channelItem.setToday_topic_num(((CircleDataBean.DataBean.DefaultBean) CircleNewFrament.this._default.get(i2)).getPid());
                                channelItem.setInitials(((CircleDataBean.DataBean.DefaultBean) CircleNewFrament.this._default.get(i2)).getInitials());
                                CircleNewFrament.this.mDefaultList.add(channelItem);
                            }
                        }
                        if (CircleNewFrament.this._list != null && CircleNewFrament.this._list.size() > 0) {
                            CircleNewFrament.this.mUserList.clear();
                            for (int i3 = 0; i3 < CircleNewFrament.this._list.size(); i3++) {
                                ChannelItem channelItem2 = new ChannelItem();
                                channelItem2.setId(Integer.parseInt(((CircleDataBean.DataBean.ListBean) CircleNewFrament.this._list.get(i3)).getId()));
                                channelItem2.setName(((CircleDataBean.DataBean.ListBean) CircleNewFrament.this._list.get(i3)).getTitle());
                                channelItem2.setOrderId(i3);
                                channelItem2.setSort(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.ListBean) CircleNewFrament.this._list.get(i3)).getSort())));
                                channelItem2.setSelected(Integer.valueOf(Integer.parseInt(((CircleDataBean.DataBean.ListBean) CircleNewFrament.this._list.get(i3)).getIs_default())));
                                channelItem2.setToday_topic_num(((CircleDataBean.DataBean.ListBean) CircleNewFrament.this._list.get(i3)).getPid());
                                channelItem2.setInitials(((CircleDataBean.DataBean.ListBean) CircleNewFrament.this._list.get(i3)).getInitials());
                                CircleNewFrament.this.mUserList.add(channelItem2);
                            }
                        }
                        CircleNewFrament.this.iv_network_load_fail.setVisibility(8);
                        CircleNewFrament.this.line_id.setVisibility(0);
                        CircleNewFrament.this.initTabColumn();
                    } else {
                        CircleNewFrament.this.iv_network_load_fail.setVisibility(0);
                        CircleNewFrament circleNewFrament3 = CircleNewFrament.this;
                        circleNewFrament3.mStopAnim(circleNewFrament3.load_anim);
                        CircleNewFrament.this.load_anim.setVisibility(8);
                        CircleNewFrament.this.AlertToast("" + CircleNewFrament.this.mCircleDataBean.getMessage());
                    }
                    SharePreferencesUtils.writeLongConfig(CommonParameter.circlrListnum, Long.valueOf(Long.parseLong(CircleNewFrament.this.mCircleDataBean.getFixedCount())), CircleNewFrament.this.mContext);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CircleNewFrament.this.iv_network_load_fail.setVisibility(0);
                    CircleNewFrament.this.line_id.setVisibility(8);
                    CircleNewFrament circleNewFrament4 = CircleNewFrament.this;
                    circleNewFrament4.mStopAnim(circleNewFrament4.load_anim);
                    CircleNewFrament.this.load_anim.setVisibility(8);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_circlcenew;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.circlehs);
        this.viewpager = (ViewPager) holder.get(R.id.viewpagecircle);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.ll_more_columns);
        this.load_anim = (ImageView) holder.get(R.id.load_anim);
        this.line_id = (LinearLayout) holder.get(R.id.line_id);
        this.iv_network_load_fail = (ImageView) holder.get(R.id.iv_network_load_fail);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15452c.lambda$initViews$0(view);
            }
        });
        this.mItemWidth = CommonUtil.getScreenWidth(this.mContext) / 4;
        ((Animatable) this.load_anim.getDrawable()).start();
        this.iv_network_load_fail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15487c.lambda$initViews$1(view);
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
