package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.adapter.FollowAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.FollowBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class FollowFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 4912;
    public FollowAdapter adapter;
    private View addFooterView;
    private ListView listView;
    private Disposable mDisposable;
    private SwipeRefreshLayout mSwipeLayput;
    private String searchKeyWord;
    private int page = 1;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.FollowFragment.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 4912) {
                if (!FollowFragment.this.mSwipeLayput.isRefreshing()) {
                    FollowFragment.this.mSwipeLayput.setRefreshing(true);
                }
                FollowFragment.this.page = 1;
                FollowFragment.this.getData();
            }
        }
    };
    private List<FollowBean.DataBean> dataList = new ArrayList();

    /* renamed from: com.psychiatrygarden.fragmenthome.FollowFragment$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            FollowFragment.access$108(FollowFragment.this);
            FollowFragment.this.getData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && FollowFragment.this.listView.getFooterViewsCount() <= 0) {
                FollowFragment.this.listView.addFooterView(FollowFragment.this.addFooterView);
                FollowFragment.this.addFooterView.setVisibility(0);
                if (FollowFragment.this.mSwipeLayput.isRefreshing()) {
                    FollowFragment.this.mSwipeLayput.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.s5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15982c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.FollowFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends AjaxCallBack<String> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(AdapterView adapterView, View view, int i2, long j2) {
            Intent intent = new Intent(FollowFragment.this.getActivity(), (Class<?>) UserCommentInfoActivity.class);
            intent.putExtra("user_id", "" + ((FollowBean.DataBean) FollowFragment.this.dataList.get(i2)).getUser_id());
            FollowFragment.this.startActivity(intent);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            if (FollowFragment.this.mSwipeLayput.isRefreshing()) {
                FollowFragment.this.mSwipeLayput.setRefreshing(false);
            }
            if (FollowFragment.this.page > 1) {
                FollowFragment.access$110(FollowFragment.this);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass3) s2);
            try {
                FollowBean followBean = (FollowBean) new Gson().fromJson(s2, FollowBean.class);
                if (followBean.getCode().equals("200") && followBean.getData() != null) {
                    if (FollowFragment.this.page == 1) {
                        FollowFragment.this.dataList = followBean.getData();
                        FollowFragment.this.adapter = new FollowAdapter(FollowFragment.this.getContext(), FollowFragment.this.dataList);
                        FollowFragment.this.adapter.setFollowClickListener(new FollowAdapter.FollowClickListener() { // from class: com.psychiatrygarden.fragmenthome.FollowFragment.3.1
                            @Override // com.psychiatrygarden.adapter.FollowAdapter.FollowClickListener
                            public void cancelFollow(String userId) {
                                FollowFragment.this.CancelFriends(userId);
                            }

                            @Override // com.psychiatrygarden.adapter.FollowAdapter.FollowClickListener
                            public void followFriend(String userId) {
                                FollowFragment.this.ADDFriendsList(userId);
                            }
                        });
                        FollowFragment.this.listView.setAdapter((ListAdapter) FollowFragment.this.adapter);
                    } else {
                        List<FollowBean.DataBean> data = followBean.getData();
                        FollowFragment.this.listView.removeFooterView(FollowFragment.this.addFooterView);
                        FollowFragment.this.addFooterView.setVisibility(8);
                        if (data == null || data.size() <= 0) {
                            FollowFragment.access$110(FollowFragment.this);
                            ToastUtil.shortToast(((BaseFragment) FollowFragment.this).mContext, "暂无更多数据！");
                        } else {
                            FollowFragment.this.dataList.addAll(data);
                            FollowAdapter followAdapter = FollowFragment.this.adapter;
                            if (followAdapter != null) {
                                followAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    FollowFragment.this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.t5
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                            this.f16007c.lambda$onSuccess$0(adapterView, view, i2, j2);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (FollowFragment.this.page > 1) {
                    FollowFragment.access$110(FollowFragment.this);
                }
            }
            if (FollowFragment.this.mSwipeLayput.isRefreshing()) {
                FollowFragment.this.mSwipeLayput.setRefreshing(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ADDFriendsList(String user_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("from_user_id", UserConfig.getUserId());
        ajaxParams.put("to_user_id", user_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mChatFollow, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.FollowFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                FollowFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                FollowFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                FollowFragment.this.hideProgressDialog();
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        FollowFragment.this.mHandler.sendEmptyMessageDelayed(4912, 500L);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void CancelFriends(String user_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("from_user_id", UserConfig.getUserId());
        ajaxParams.put("to_user_id", user_id);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mChatUnFollow, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.FollowFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                FollowFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                FollowFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                FollowFragment.this.hideProgressDialog();
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        FollowFragment.this.mHandler.sendEmptyMessageDelayed(4912, 500L);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public static /* synthetic */ int access$108(FollowFragment followFragment) {
        int i2 = followFragment.page;
        followFragment.page = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$110(FollowFragment followFragment) {
        int i2 = followFragment.page;
        followFragment.page = i2 - 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(CharSequence charSequence) throws Exception {
        this.page = 1;
        if (TextUtils.isEmpty(charSequence)) {
            this.searchKeyWord = null;
        } else {
            this.searchKeyWord = charSequence.toString();
        }
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1() {
        this.mSwipeLayput.setRefreshing(true);
        getData();
    }

    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        String string = getArguments().getString("target_user_id");
        if (!TextUtils.isEmpty(this.searchKeyWord)) {
            ajaxParams.put("name", this.searchKeyWord);
        }
        if (TextUtils.isEmpty(string)) {
            ajaxParams.put("target_user_id", UserConfig.getUserId());
        } else {
            ajaxParams.put("target_user_id", "" + getArguments().getString("target_user_id"));
        }
        ajaxParams.put("is_follow", "" + getArguments().getString("is_follow"));
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.follerListApi, ajaxParams, new AnonymousClass3());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.mDisposable = RxTextView.textChanges((ClearEditText) holder.get(R.id.et_search)).debounce(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.fragmenthome.q5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f15935c.lambda$initViews$0((CharSequence) obj);
            }
        });
        this.mSwipeLayput = (SwipeRefreshLayout) holder.get(R.id.swipe);
        this.listView = (ListView) holder.get(R.id.listview);
        this.mSwipeLayput.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.mSwipeLayput.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.mSwipeLayput.setColorSchemeResources(android.R.color.holo_red_light);
        } else {
            this.mSwipeLayput.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.mSwipeLayput.setColorSchemeResources(R.color.question_color_night);
        }
        this.mSwipeLayput.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.r5
            @Override // java.lang.Runnable
            public final void run() {
                this.f15964c.lambda$initViews$1();
            }
        });
        this.listView.setOnScrollListener(new AnonymousClass2());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mDisposable.dispose();
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(4912, 500L);
    }
}
