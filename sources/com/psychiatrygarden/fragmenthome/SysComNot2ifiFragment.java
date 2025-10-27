package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.MyMessageBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.SkinGrakImagView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SysComNot2ifiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private View addFooterView;
    private ListView listView;
    private CommAdapter<MyMessageBean.DataBean> mCommAdapter;
    private Activity mContext;
    private MyMessageBean mMyMessageBean;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private int pageNum = 1;
    private int pageSize = 20;
    private String last_min_id = "0";

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 272) {
                SysComNot2ifiFragment.this.pageNum = 1;
                SysComNot2ifiFragment.this.getMessage();
            }
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment$4, reason: invalid class name */
    public class AnonymousClass4 implements AbsListView.OnScrollListener {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            SysComNot2ifiFragment.access$008(SysComNot2ifiFragment.this);
            SysComNot2ifiFragment.this.getMessage();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && SysComNot2ifiFragment.this.listView.getFooterViewsCount() <= 0) {
                SysComNot2ifiFragment.this.listView.addFooterView(SysComNot2ifiFragment.this.addFooterView);
                SysComNot2ifiFragment.this.addFooterView.setVisibility(0);
                if (SysComNot2ifiFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    SysComNot2ifiFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.vh
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f16082c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    public static /* synthetic */ int access$008(SysComNot2ifiFragment sysComNot2ifiFragment) {
        int i2 = sysComNot2ifiFragment.pageNum;
        sysComNot2ifiFragment.pageNum = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$010(SysComNot2ifiFragment sysComNot2ifiFragment) {
        int i2 = sysComNot2ifiFragment.pageNum;
        sysComNot2ifiFragment.pageNum = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMessage() {
        AjaxParams ajaxParams = new AjaxParams();
        if (this.pageNum == 1) {
            ajaxParams.put("last_min_id", "0");
        } else {
            ajaxParams.put("last_min_id", this.last_min_id);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mGetMessageListURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    if (SysComNot2ifiFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                        SysComNot2ifiFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                    }
                    SysComNot2ifiFragment.this.AlertToast("请求失败");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code").equals("200")) {
                        SysComNot2ifiFragment.this.AlertToast(jSONObject.getString("message"));
                    } else if (SysComNot2ifiFragment.this.pageNum == 1) {
                        SysComNot2ifiFragment.this.mMyMessageBean = (MyMessageBean) new Gson().fromJson(t2, MyMessageBean.class);
                        SysComNot2ifiFragment.this.mCommAdapter = new CommAdapter<MyMessageBean.DataBean>(SysComNot2ifiFragment.this.mMyMessageBean.getData(), SysComNot2ifiFragment.this.getActivity(), R.layout.adapter_my_message) { // from class: com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment.5.1
                            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                            public void convert(ViewHolder vHolder, MyMessageBean.DataBean dataBean, int position) {
                                TextView textView = (TextView) vHolder.getView(R.id.title);
                                TextView textView2 = (TextView) vHolder.getView(R.id.ctime);
                                TextView textView3 = (TextView) vHolder.getView(R.id.description);
                                TextView textView4 = (TextView) vHolder.getView(R.id.tofrom);
                                SkinGrakImagView skinGrakImagView = (SkinGrakImagView) vHolder.getView(R.id.img);
                                textView.setText(dataBean.getTitle());
                                textView2.setText(dataBean.getTimestr());
                                textView3.setText(dataBean.getContent());
                                textView4.setText(dataBean.getTo_from() + "");
                                if (TextUtils.isEmpty(dataBean.getImg())) {
                                    skinGrakImagView.setVisibility(8);
                                    GlideApp.with(SysComNot2ifiFragment.this.mContext).load(Integer.valueOf(R.drawable.app_icon)).into(skinGrakImagView);
                                } else {
                                    skinGrakImagView.setVisibility(0);
                                    GlideApp.with(SysComNot2ifiFragment.this.mContext).load((Object) GlideUtils.generateUrl(dataBean.getImg())).into(skinGrakImagView);
                                }
                            }
                        };
                        SysComNot2ifiFragment.this.listView.setAdapter((ListAdapter) SysComNot2ifiFragment.this.mCommAdapter);
                        if (SysComNot2ifiFragment.this.mMyMessageBean.getData().size() < 1) {
                            SysComNot2ifiFragment.this.AlertToast(jSONObject.getString("暂无消息"));
                        } else {
                            SysComNot2ifiFragment sysComNot2ifiFragment = SysComNot2ifiFragment.this;
                            sysComNot2ifiFragment.last_min_id = sysComNot2ifiFragment.mMyMessageBean.getData().get(SysComNot2ifiFragment.this.mMyMessageBean.getData().size() - 1).getMessage_id();
                        }
                    } else {
                        SysComNot2ifiFragment.this.listView.removeFooterView(SysComNot2ifiFragment.this.addFooterView);
                        SysComNot2ifiFragment.this.addFooterView.setVisibility(8);
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<MyMessageBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment.5.2
                        }.getType());
                        SysComNot2ifiFragment.this.mMyMessageBean.getData().addAll(list);
                        SysComNot2ifiFragment.this.mCommAdapter.notifyDataSetChanged();
                        if (list.size() < 1) {
                            SysComNot2ifiFragment.this.AlertToast("没有更多了");
                            SysComNot2ifiFragment.access$010(SysComNot2ifiFragment.this);
                        } else {
                            SysComNot2ifiFragment.this.last_min_id = ((MyMessageBean.DataBean) list.get(list.size() - 1)).getMessage_id();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (SysComNot2ifiFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    SysComNot2ifiFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(AdapterView adapterView, View view, int i2, long j2) {
        if (isLogin()) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(this.mMyMessageBean.getData().get(i2).getTarget_params()));
            if ("7".equals(this.mMyMessageBean.getData().get(i2).getTarget_params().getPush_type())) {
                getActivity().finish();
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        quxiaoData();
        this.listView = (ListView) holder.get(R.id.pinnedSectionListView1);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.addFooterView = getActivity().getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getActivity().getResources().getColor(R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment.3
            @Override // java.lang.Runnable
            public void run() {
                SysComNot2ifiFragment.this.mSwipeRefreshLayout.setRefreshing(true);
                SysComNot2ifiFragment.this.pageNum = 1;
                SysComNot2ifiFragment.this.getMessage();
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.uh
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f16055c.lambda$initViews$0(adapterView, view, i2, j2);
            }
        });
        this.listView.setOnScrollListener(new AnonymousClass4());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public void quxiaoData() {
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.setJpushSeeTimeApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SysComNot2ifiFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
            }
        });
    }

    public void setListenerForWidget() {
    }
}
