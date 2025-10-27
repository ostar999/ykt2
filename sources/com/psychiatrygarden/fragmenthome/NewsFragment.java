package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import cn.hutool.core.text.StrPool;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import com.google.gson.Gson;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.WebViewActivity;
import com.psychiatrygarden.adapter.Handoutadapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventSortBean;
import com.psychiatrygarden.bean.HandCollectBean;
import com.psychiatrygarden.bean.HandShareBean;
import com.psychiatrygarden.bean.HandoutImage;
import com.psychiatrygarden.bean.HandoutNewBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.UILoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class NewsFragment extends BaseFragment implements ViewPager.OnPageChangeListener, OnPageClickListener {
    int channel_id;
    private Handoutadapter handoutadapter;
    private List<HandoutImage> imgList;
    private InfiniteIndicator infinite_anim_circle;
    private LinearLayout liner;
    private LinearLayout liner_img;
    private ListView listView;
    private HandoutNewBean mHandoutNewBean;
    private SmartRefreshLayout mSwipeRefreshLayout;
    String text;
    private String times;
    private int page = 1;
    private final List<HandoutNewBean.DataBean.TopBean> listAllData = new ArrayList();
    private final ArrayList<Page> pageViews = new ArrayList<>();

    public static /* synthetic */ int access$012(NewsFragment newsFragment, int i2) {
        int i3 = newsFragment.page + i2;
        newsFragment.page = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(AdapterView adapterView, View view, int i2, long j2) {
        try {
            if (isLogin()) {
                Intent intent = new Intent(getActivity(), (Class<?>) HandoutsInfoActivity.class);
                int i3 = i2 - 1;
                intent.putExtra("cat_id", this.listAllData.get(i3).getCid());
                intent.putExtra("article", this.listAllData.get(i3).getId());
                intent.putExtra("json_path", this.listAllData.get(i3).getJson_path());
                intent.putExtra("html_path", this.listAllData.get(i3).getHtml_path());
                intent.putExtra("h5_path", this.listAllData.get(i3).getH5_path());
                intent.putExtra("is_rich_text", this.listAllData.get(i3).getIs_rich_text());
                intent.putExtra("index", this.listAllData.get(i3).getCid() + StrPool.UNDERLINE + this.listAllData.get(i3).getId());
                startActivity(intent);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEventMainThread$0() {
        getHandoutDataListNew();
        this.times = "";
    }

    public void getHandoutDataListNew() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("cid", this.channel_id + "");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        if (TextUtils.isEmpty(this.times)) {
            ajaxParams.put(CrashHianalyticsData.TIME, "" + (System.currentTimeMillis() / 1000));
        } else {
            ajaxParams.put(CrashHianalyticsData.TIME, "" + this.times);
        }
        ajaxParams.put("sort_type", SharePreferencesUtils.readStrConfig(CommonParameter.sortvalue, getActivity(), "1"));
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mGetInfoListUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.NewsFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (NewsFragment.this.page == 1) {
                    NewsFragment.this.liner_img.setVisibility(0);
                }
                NewsFragment.this.mSwipeRefreshLayout.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    NewsFragment.this.mHandoutNewBean = (HandoutNewBean) new Gson().fromJson(s2, HandoutNewBean.class);
                    if (!NewsFragment.this.mHandoutNewBean.getCode().equals("200")) {
                        if (NewsFragment.this.page != 1) {
                            NewsFragment.this.mSwipeRefreshLayout.finishLoadMore(false);
                            return;
                        } else {
                            NewsFragment.this.liner_img.setVisibility(0);
                            NewsFragment.this.mSwipeRefreshLayout.finishRefresh(false);
                            return;
                        }
                    }
                    if (NewsFragment.this.page == 1) {
                        NewsFragment.this.mSwipeRefreshLayout.finishRefresh(true);
                        NewsFragment.this.listAllData.clear();
                        NewsFragment.this.imgList.clear();
                        NewsFragment newsFragment = NewsFragment.this;
                        newsFragment.imgList = newsFragment.mHandoutNewBean.getData().getSider();
                        if (NewsFragment.this.imgList == null || NewsFragment.this.imgList.size() <= 0) {
                            NewsFragment.this.liner.setVisibility(8);
                        } else {
                            NewsFragment.this.liner.setVisibility(0);
                            for (int i2 = 0; i2 < NewsFragment.this.imgList.size(); i2++) {
                                NewsFragment.this.pageViews.add(new Page(((HandoutImage) NewsFragment.this.imgList.get(i2)).getTitle(), ((HandoutImage) NewsFragment.this.imgList.get(i2)).getCover()));
                            }
                            NewsFragment.this.infinite_anim_circle.notifyDataChange(NewsFragment.this.pageViews);
                        }
                        List<HandoutNewBean.DataBean.TopBean> top2 = NewsFragment.this.mHandoutNewBean.getData().getTop();
                        List<HandoutNewBean.DataBean.TopBean> list = NewsFragment.this.mHandoutNewBean.getData().getList();
                        if (top2 != null && top2.size() > 0) {
                            for (int i3 = 0; i3 < top2.size(); i3++) {
                                top2.get(i3).setZhiding("1");
                            }
                            NewsFragment.this.listAllData.addAll(top2);
                        }
                        if (list != null && list.size() > 0) {
                            NewsFragment.this.listAllData.addAll(list);
                        }
                        if (NewsFragment.this.listAllData.size() > 0) {
                            NewsFragment.this.liner_img.setVisibility(8);
                            NewsFragment.this.listView.setAdapter((ListAdapter) NewsFragment.this.handoutadapter = new Handoutadapter(NewsFragment.this.getActivity(), NewsFragment.this.listAllData));
                        } else {
                            NewsFragment.this.liner_img.setVisibility(0);
                        }
                    } else {
                        NewsFragment.this.mSwipeRefreshLayout.finishLoadMore(true);
                        List<HandoutNewBean.DataBean.TopBean> list2 = NewsFragment.this.mHandoutNewBean.getData().getList();
                        if (list2 != null && list2.size() > 0) {
                            NewsFragment.this.listAllData.addAll(list2);
                            NewsFragment.this.handoutadapter.notifyDataSetChanged();
                        }
                    }
                    NewsFragment newsFragment2 = NewsFragment.this;
                    newsFragment2.times = newsFragment2.mHandoutNewBean.getTime();
                } catch (Exception unused) {
                    if (NewsFragment.this.page == 1) {
                        NewsFragment.this.liner_img.setVisibility(0);
                        NewsFragment.this.mSwipeRefreshLayout.finishRefresh(false);
                    } else {
                        NewsFragment.this.liner_img.setVisibility(8);
                        NewsFragment.this.mSwipeRefreshLayout.finishLoadMore(false);
                    }
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws IllegalAccessException, NoSuchFieldException, Resources.NotFoundException, SecurityException, IllegalArgumentException {
        this.imgList = new ArrayList();
        this.listView = (ListView) holder.get(R.id.pinnedSectionListView1);
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.activity_handoutheader, (ViewGroup) null);
        this.liner = (LinearLayout) viewInflate.findViewById(R.id.liner);
        this.liner_img = (LinearLayout) holder.get(R.id.liner_img);
        this.infinite_anim_circle = (InfiniteIndicator) viewInflate.findViewById(R.id.infinite_anim_circle);
        this.infinite_anim_circle.init(new IndicatorConfiguration.Builder().imageLoader(new UILoader()).isStopWhileTouch(true).onPageChangeListener(this).onPageClickListener(this).direction(0).position(IndicatorConfiguration.IndicatorPosition.Center_Bottom).build());
        this.listView.addHeaderView(viewInflate);
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.mSwipeRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.fragmenthome.NewsFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                NewsFragment.access$012(NewsFragment.this, 1);
                NewsFragment.this.getHandoutDataListNew();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                NewsFragment.this.page = 1;
                NewsFragment.this.getHandoutDataListNew();
            }
        });
        this.times = "";
        this.mSwipeRefreshLayout.autoRefresh();
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.y8
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f16141c.lambda$initViews$1(adapterView, view, i2, j2);
            }
        });
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        this.text = arguments != null ? arguments.getString("text") : "";
        this.channel_id = arguments != null ? arguments.getInt("id", 0) : 0;
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressLint({"NewApi"})
    public void onEventMainThread(HandCollectBean str) {
        for (int i2 = 0; i2 < this.listAllData.size(); i2++) {
            if ((this.listAllData.get(i2).getCid() + StrPool.UNDERLINE + this.listAllData.get(i2).getId()).equals(str.getId())) {
                this.listAllData.get(i2).setIs_focus(str.getIs_collect());
                this.listAllData.get(i2).setAuthor_looked("0");
                this.handoutadapter.notifyDataSetChanged();
            }
        }
    }

    @Override // cn.lightsky.infiniteindicator.OnPageClickListener
    public void onPageClick(int position, Page page) {
        if (!this.imgList.get(position).type.equals("1")) {
            Intent intent = new Intent(this.mContext, (Class<?>) WebViewActivity.class);
            intent.putExtra("title", "").putExtra("url", this.imgList.get(position).json_url);
            Context context = this.mContext;
            if (context != null) {
                context.startActivity(intent);
                return;
            }
            return;
        }
        Intent intent2 = new Intent(getActivity(), (Class<?>) HandoutsInfoActivity.class);
        intent2.putExtra("imagCateValue", true);
        intent2.putExtra("url", this.imgList.get(position).json_url);
        intent2.putExtra("article", this.imgList.get(position).getAid());
        try {
            intent2.putExtra("comm_count", this.imgList.get(position).comment_count);
            intent2.putExtra("is_share", this.imgList.get(position).getIs_share());
            intent2.putExtra("is_read", this.imgList.get(position).getIs_read());
            intent2.putExtra("is_focus", this.imgList.get(position).getIs_focus());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        startActivity(intent2);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
    }

    public void update() {
    }

    public void onEventMainThread(HandShareBean str) {
        for (int i2 = 0; i2 < this.listAllData.size(); i2++) {
            if ((this.listAllData.get(i2).getCid() + StrPool.UNDERLINE + this.listAllData.get(i2).getId()).equals(str.getId())) {
                this.listAllData.get(i2).setIs_read(str.getIs_share());
                this.listAllData.get(i2).setAuthor_looked("0");
                this.handoutadapter.notifyDataSetChanged();
            }
        }
    }

    public void onEventMainThread(EventSortBean mEvent) {
        if (mEvent.getmEveStr().equals(CommonParameter.sortvalue)) {
            getHandoutDataListNew();
            this.times = "";
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("NewsFragment")) {
            new Thread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.x8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16115c.lambda$onEventMainThread$0();
                }
            }).start();
        }
    }
}
