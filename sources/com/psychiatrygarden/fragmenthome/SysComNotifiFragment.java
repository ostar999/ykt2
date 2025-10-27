package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import com.psychiatrygarden.activity.comment.DiscussPublicActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.MyMessageCommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SysComNotifiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private View addFooterView;
    private CommAdapter<MyMessageCommentBean.DataBean> commAdapter;
    private ListView listView;
    private Context mContext;
    private MyMessageCommentBean mMyMessageBean;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private int module_type;
    private List<MyMessageCommentBean.DataBean> mDataBean = new ArrayList();
    private int pageNum = 1;

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.SysComNotifiFragment.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 != 1) {
                if (i2 != 272) {
                    return;
                }
                SysComNotifiFragment.this.pageNum = 1;
                SysComNotifiFragment.this.getMessage();
                return;
            }
            int i3 = msg.arg1;
            int i4 = msg.arg2;
            Intent intent = new Intent(SysComNotifiFragment.this.mContext, (Class<?>) DiscussPublicActivity.class);
            intent.putExtra("title", "评论我");
            intent.putExtra("comment_type", "2");
            intent.putExtra("module_type", i3);
            intent.putExtra("obj_id", i4 + "");
            intent.putExtra("commentEnum", DiscussStatus.CommentOnMy);
            SysComNotifiFragment.this.startActivity(intent);
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.SysComNotifiFragment$1, reason: invalid class name */
    public class AnonymousClass1 implements AbsListView.OnScrollListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            SysComNotifiFragment.access$208(SysComNotifiFragment.this);
            SysComNotifiFragment.this.getMessage();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && SysComNotifiFragment.this.listView.getFooterViewsCount() <= 0) {
                SysComNotifiFragment.this.listView.addFooterView(SysComNotifiFragment.this.addFooterView);
                SysComNotifiFragment.this.addFooterView.setVisibility(0);
                if (SysComNotifiFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    SysComNotifiFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.yh
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f16150c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    public static /* synthetic */ int access$208(SysComNotifiFragment sysComNotifiFragment) {
        int i2 = sysComNotifiFragment.pageNum;
        sysComNotifiFragment.pageNum = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMessage() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("token", UserConfig.getInstance().getUser().getToken());
        ajaxParams.put("secret", UserConfig.getInstance().getUser().getSecret());
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.pageNum + "");
        ajaxParams.put("break_point", AndroidBaseUtils.GetUTCTime().toString());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mGetreplyMessageURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SysComNotifiFragment.2

            /* renamed from: com.psychiatrygarden.fragmenthome.SysComNotifiFragment$2$1, reason: invalid class name */
            public class AnonymousClass1 extends CommAdapter<MyMessageCommentBean.DataBean> {
                public AnonymousClass1(List mData, Context mcontext, int layoutId) {
                    super(mData, mcontext, layoutId);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(MyMessageCommentBean.DataBean dataBean, View view) {
                    SysComNotifiFragment.this.module_type = dataBean.getModule_type();
                    PublicMethodActivity.getInstance().mCommentMethod(SysComNotifiFragment.this.module_type + "", dataBean.getTarget_params());
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$1(MyMessageCommentBean.DataBean dataBean, View view) {
                    try {
                        SysComNotifiFragment.this.module_type = dataBean.getModule_type();
                        PublicMethodActivity.getInstance().mCommentMethod(SysComNotifiFragment.this.module_type + "", dataBean.getTarget_params());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                public void convert(ViewHolder vHolder, final MyMessageCommentBean.DataBean dataBean, final int position) {
                    try {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(dataBean.getNickname() + "：" + dataBean.getContent());
                        if (SkinManager.getCurrentSkinType(SysComNotifiFragment.this.mContext) == 0) {
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#145FB3")), 0, dataBean.getNickname().length(), 34);
                        } else {
                            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#104C8F")), 0, dataBean.getNickname().length(), 34);
                        }
                        vHolder.setText(R.id.title, dataBean.getTitle()).setText(R.id.ctime, dataBean.getTimestr()).setText(R.id.tofrom, dataBean.getTo_from()).setText(R.id.description, spannableStringBuilder);
                        TextView textView = (TextView) vHolder.getView(R.id.atmostlun);
                        TextView textView2 = (TextView) vHolder.getView(R.id.redyuan);
                        if (dataBean.getModule_type() == 3) {
                            textView.setText("查看原文");
                        } else if (dataBean.getModule_type() == 12 || dataBean.getModule_type() == 16) {
                            textView.setText("查看帖子");
                        } else {
                            textView.setText("查看原题");
                        }
                        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.zh
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f16174c.lambda$convert$0(dataBean, view);
                            }
                        });
                        ((TextView) vHolder.getView(R.id.tofrom)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ai
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f15449c.lambda$convert$1(dataBean, view);
                            }
                        });
                        if (dataBean.getIs_read().equals("0")) {
                            textView2.setVisibility(0);
                        } else {
                            textView2.setVisibility(8);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                try {
                    if (SysComNotifiFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                        SysComNotifiFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                    }
                    NewToast.showShort(SysComNotifiFragment.this.mContext, "请求失败", 0).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code").equals("200")) {
                        NewToast.showShort(SysComNotifiFragment.this.mContext, jSONObject.getString("message") + "", 0).show();
                    } else if (SysComNotifiFragment.this.pageNum == 1) {
                        SysComNotifiFragment.this.mMyMessageBean = (MyMessageCommentBean) new Gson().fromJson(t2, MyMessageCommentBean.class);
                        SysComNotifiFragment sysComNotifiFragment = SysComNotifiFragment.this;
                        sysComNotifiFragment.mDataBean = sysComNotifiFragment.mMyMessageBean.getData();
                        SysComNotifiFragment.this.commAdapter = new AnonymousClass1(SysComNotifiFragment.this.mDataBean, SysComNotifiFragment.this.mContext, R.layout.myadapter);
                        SysComNotifiFragment.this.listView.setAdapter((ListAdapter) SysComNotifiFragment.this.commAdapter);
                    } else {
                        SysComNotifiFragment.this.listView.removeFooterView(SysComNotifiFragment.this.addFooterView);
                        SysComNotifiFragment.this.addFooterView.setVisibility(8);
                        SysComNotifiFragment.this.mMyMessageBean.getData().addAll((List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<MyMessageCommentBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.SysComNotifiFragment.2.2
                        }.getType()));
                        SysComNotifiFragment.this.commAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (SysComNotifiFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    SysComNotifiFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0() {
        this.mSwipeRefreshLayout.setRefreshing(true);
        this.pageNum = 1;
        getMessage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(AdapterView adapterView, View view, int i2, long j2) {
        try {
            this.mDataBean.get(i2).setIs_read("1");
            this.commAdapter.notifyDataSetChanged();
            Message message = new Message();
            message.what = 1;
            message.arg1 = this.mDataBean.get(i2).getModule_type();
            message.arg2 = this.mDataBean.get(i2).getComment_id();
            this.mHandler.sendMessage(message);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void onAttachToContext(Context context) {
        this.mContext = context;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.syscomnotifi;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
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
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.wh
            @Override // java.lang.Runnable
            public final void run() {
                this.f16104c.lambda$initViews$0();
            }
        });
        this.listView.setOnScrollListener(new AnonymousClass1());
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.xh
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f16125c.lambda$initViews$1(adapterView, view, i2, j2);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }
}
