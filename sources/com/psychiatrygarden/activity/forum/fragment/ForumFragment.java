package com.psychiatrygarden.activity.forum.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolInfoActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyResultActivity;
import com.psychiatrygarden.activity.forum.ForumListActivity;
import com.psychiatrygarden.activity.forum.ForumSearchActivity;
import com.psychiatrygarden.activity.forum.bean.ForumIndexBean;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.activity.online.SelectIdentityNewActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ForumFragment extends BaseFragment {
    public BaseQuickAdapter<ForumIndexBean.DataBean.ListBean, BaseViewHolder> adapter;
    public SmartRefreshLayout mRefresh;
    public RecyclerView recycleview;
    public RelativeLayout relmy;
    public BaseQuickAdapter<ForumIndexBean.DataBean.ParentBean, BaseViewHolder> schooladapter;
    private TextView tv_filtrateTxt;
    public RecyclerView xuexiaorecy;
    private final List<ForumIndexBean.DataBean.ListBean> fowlist = new ArrayList();
    private final List<ForumIndexBean.DataBean.ParentBean> parentList = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.fowlist.size() <= 0 || this.fowlist.get(i2).getId() == null || "".equals(this.fowlist.get(i2).getId())) {
            return;
        }
        goToDetail(this.fowlist.get(i2).getId(), this.fowlist.get(i2).getAccess());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.parentList.size() <= i2) {
            return;
        }
        for (int i3 = 0; i3 < this.parentList.size(); i3++) {
            this.parentList.get(i3).setSelected(0);
        }
        this.parentList.get(i2).setSelected(1);
        baseQuickAdapter.notifyDataSetChanged();
        showSchoolData(this.parentList.get(i2).getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(RefreshLayout refreshLayout) {
        getForumData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        startActivity(new Intent(getActivity(), (Class<?>) ForumSearchActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(View view) {
        Intent intent = new Intent(getActivity(), (Class<?>) SelectIdentityNewActivity.class);
        intent.putExtra("flag", false);
        intent.putExtra("appbeanname", "");
        startActivity(intent);
    }

    public static ForumFragment newInstance() {
        Bundle bundle = new Bundle();
        ForumFragment forumFragment = new ForumFragment();
        forumFragment.setArguments(bundle);
        return forumFragment;
    }

    public void addFirstSchoolData() {
        ForumIndexBean.DataBean.ListBean listBean = new ForumIndexBean.DataBean.ListBean();
        listBean.setName("未添加我的院校");
        this.fowlist.add(listBean);
    }

    public void getForumData() {
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumindexApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumFragment.this.mRefresh.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    ForumIndexBean forumIndexBean = (ForumIndexBean) new Gson().fromJson(s2, ForumIndexBean.class);
                    if (!forumIndexBean.getCode().equals("200")) {
                        ForumFragment.this.mRefresh.finishRefresh(false);
                        return;
                    }
                    ForumFragment.this.mRefresh.finishRefresh(true);
                    ForumFragment.this.relmy.setVisibility(0);
                    ForumFragment.this.fowlist.clear();
                    List<ForumIndexBean.DataBean.ListBean> list = forumIndexBean.getData().getList();
                    if (list == null || list.size() <= 0) {
                        ForumFragment.this.addFirstSchoolData();
                    } else {
                        ForumFragment.this.fowlist.addAll(forumIndexBean.getData().getList());
                    }
                    ForumFragment.this.adapter.notifyDataSetChanged();
                    List<ForumIndexBean.DataBean.ParentBean> parent = forumIndexBean.getData().getParent();
                    if (parent.size() > 0) {
                        ForumFragment.this.parentList.clear();
                        parent.get(0).setSelected(1);
                        ForumFragment.this.showSchoolData(parent.get(0).getId());
                        ForumFragment.this.parentList.addAll(parent);
                        ForumFragment.this.schooladapter.notifyDataSetChanged();
                    }
                } catch (Exception unused) {
                    ForumFragment.this.mRefresh.finishRefresh(false);
                }
            }
        });
    }

    public void getForumInfoData(final String group_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + group_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ForumFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    ForumInfoBean forumInfoBean = (ForumInfoBean) new Gson().fromJson(s2, ForumInfoBean.class);
                    if ("200".equals(forumInfoBean.getCode())) {
                        ForumInfoBean.DataBean data = forumInfoBean.getData();
                        if ("-1".equals(data.getJoin_the_state())) {
                            Intent intent = new Intent(ForumFragment.this.getActivity(), (Class<?>) CircleSchoolInfoActivity.class);
                            intent.putExtra("group_id", "" + group_id);
                            intent.putExtra("dataBean", data);
                            ForumFragment.this.startActivity(intent);
                        } else if ("0".equals(data.getJoin_the_state())) {
                            Intent intent2 = new Intent(ForumFragment.this.getActivity(), (Class<?>) CircleSchoolVerifyResultActivity.class);
                            intent2.putExtra("flag", "0");
                            intent2.putExtra("group_id", "" + group_id);
                            ForumFragment.this.startActivity(intent2);
                        } else if ("1".equals(data.getJoin_the_state())) {
                            Intent intent3 = new Intent(ForumFragment.this.getActivity(), (Class<?>) ForumListActivity.class);
                            intent3.putExtra("group_id", "" + group_id);
                            ForumFragment.this.startActivity(intent3);
                        } else {
                            Intent intent4 = new Intent(ForumFragment.this.getActivity(), (Class<?>) CircleSchoolVerifyResultActivity.class);
                            intent4.putExtra("flag", "2");
                            intent4.putExtra("group_id", "" + group_id);
                            ForumFragment.this.startActivity(intent4);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ForumFragment.this.hideProgressDialog();
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_forum;
    }

    public void goToDetail(String group_id, String access) {
        if (isLogin()) {
            if (!"1".equals(access)) {
                getForumInfoData(group_id);
                return;
            }
            Intent intent = new Intent(getActivity(), (Class<?>) ForumListActivity.class);
            intent.putExtra("group_id", "" + group_id);
            startActivity(intent);
        }
    }

    public void initTxt() {
        try {
            this.tv_filtrateTxt.setText(String.format(" [%s]", SharePreferencesUtils.readStrConfig(CommonParameter.app_mark, getActivity(), "")));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.mRefresh = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMore(false);
        this.mRefresh.setEnableRefresh(true);
        this.tv_filtrateTxt = (TextView) holder.get(R.id.tv_filtrateTxt);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.line_filtrate);
        TextView textView = (TextView) holder.get(R.id.tv_search);
        this.relmy = (RelativeLayout) holder.get(R.id.relmy);
        this.recycleview = (RecyclerView) holder.get(R.id.recycleview);
        this.xuexiaorecy = (RecyclerView) holder.get(R.id.xuexiaorecy);
        this.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recycleview.setNestedScrollingEnabled(false);
        this.recycleview.setHasFixedSize(true);
        this.xuexiaorecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<ForumIndexBean.DataBean.ListBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ForumIndexBean.DataBean.ListBean, BaseViewHolder>(R.layout.layout_forum_index_item, this.fowlist) { // from class: com.psychiatrygarden.activity.forum.fragment.ForumFragment.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder helper, ForumIndexBean.DataBean.ListBean item) {
                ImageView imageView = (ImageView) helper.getView(R.id.header);
                TextView textView2 = (TextView) helper.getView(R.id.name);
                TextView textView3 = (TextView) helper.getView(R.id.unreadtxt);
                TextView textView4 = (TextView) helper.getView(R.id.detail);
                ((ImageView) helper.getView(R.id.checkeddown)).setVisibility(8);
                textView2.setText(item.getName());
                String id = item.getId();
                int i2 = R.color.fourth_line_backgroup_color;
                if (id == null || "".equals(item.getId())) {
                    textView2.setTextColor(ContextCompat.getColor(((BaseFragment) ForumFragment.this).mContext, R.color.C1C1C1));
                    textView2.setTextSize(2, 14.0f);
                    if (item.getLogo() == null || "".equals(item.getLogo())) {
                        imageView.setImageResource(R.drawable.schooldefaultimg);
                    } else {
                        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(item.getLogo()));
                        RequestOptions requestOptions = new RequestOptions();
                        Context context = imageView.getContext();
                        if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                            i2 = R.color.bg_backgroud_night;
                        }
                        requestBuilderLoad.apply((BaseRequestOptions<?>) requestOptions.placeholder(new ColorDrawable(ContextCompat.getColor(context, i2))).error(R.drawable.schooldefaultimg)).into(imageView);
                    }
                    textView4.setVisibility(8);
                    return;
                }
                textView2.setTextColor(ContextCompat.getColor(((BaseFragment) ForumFragment.this).mContext, R.color.c242424));
                textView2.setTextSize(2, 16.0f);
                textView4.setVisibility(0);
                if (item.getLogo() == null || "".equals(item.getLogo())) {
                    imageView.setImageResource(R.drawable.schooldefaultimg);
                } else {
                    RequestBuilder<Drawable> requestBuilderLoad2 = Glide.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(item.getLogo()));
                    RequestOptions requestOptions2 = new RequestOptions();
                    Context context2 = imageView.getContext();
                    if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                        i2 = R.color.bg_backgroud_night;
                    }
                    requestBuilderLoad2.apply((BaseRequestOptions<?>) requestOptions2.placeholder(new ColorDrawable(ContextCompat.getColor(context2, i2))).error(R.drawable.schooldefaultimg)).into(imageView);
                }
                textView4.setText(String.format("%s，%s，%s", item.getUser_count(), item.getArticle_count(), item.getComment_count()));
                if ("1".equals(item.getAccess())) {
                    if (item.getNew_message() == null || "0".equals(item.getNew_message())) {
                        textView3.setVisibility(8);
                    } else {
                        textView3.setVisibility(0);
                        textView3.setText(item.getNew_message());
                    }
                }
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycleview.setAdapter(baseQuickAdapter);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.e
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12376c.lambda$initViews$0(baseQuickAdapter2, view, i2);
            }
        });
        BaseQuickAdapter<ForumIndexBean.DataBean.ParentBean, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<ForumIndexBean.DataBean.ParentBean, BaseViewHolder>(R.layout.layout_forum_parent, this.parentList) { // from class: com.psychiatrygarden.activity.forum.fragment.ForumFragment.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder helper, ForumIndexBean.DataBean.ParentBean item) {
                TextView textView2 = (TextView) helper.getView(R.id.title);
                TextView textView3 = (TextView) helper.getView(R.id.viewimg);
                RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.forumrel);
                textView2.setText(item.getName());
                if (item.getSelected() == 1) {
                    relativeLayout.setSelected(true);
                    textView2.setSelected(true);
                    textView3.setVisibility(0);
                } else {
                    relativeLayout.setSelected(false);
                    textView2.setSelected(false);
                    textView3.setVisibility(4);
                }
            }
        };
        this.schooladapter = baseQuickAdapter2;
        this.xuexiaorecy.setAdapter(baseQuickAdapter2);
        this.schooladapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.f
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i2) {
                this.f12377c.lambda$initViews$1(baseQuickAdapter3, view, i2);
            }
        });
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.forum.fragment.g
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12378c.lambda$initViews$2(refreshLayout);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12379c.lambda$initViews$3(view);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12380c.lambda$initViews$4(view);
            }
        });
        initTxt();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if ("followTop".equals(str)) {
            getForumData();
        } else if ("exitSchool".equals(str)) {
            this.mRefresh.autoRefresh();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        this.mRefresh.autoRefresh();
    }

    public void showMySchoolData(ForumIndexBean.DataBean.ListBean data) {
        ForumIndexBean.DataBean.ListBean next;
        if (data == null) {
            return;
        }
        Iterator<ForumIndexBean.DataBean.ListBean> it = this.fowlist.iterator();
        if ("1".equals(data.getIs_follow())) {
            do {
                if (it.hasNext()) {
                    next = it.next();
                    if (next.getName().equals("未添加我的院校")) {
                        it.remove();
                    }
                }
                this.fowlist.add(0, data);
            } while (!next.getId().equals(data.getId()));
            return;
        }
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (data.getId().equals(it.next().getId())) {
                it.remove();
                break;
            }
        }
        if (this.fowlist.size() == 0) {
            addFirstSchoolData();
        }
        this.adapter.notifyDataSetChanged();
    }

    public void showSchoolData(String pid) {
        FragmentTransaction fragmentTransactionBeginTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fragment, ForumDetailFragment.getInstent(pid, ""));
        fragmentTransactionBeginTransaction.commitNowAllowingStateLoss();
    }

    public void onEventMainThread(ForumIndexBean.DataBean.ListBean listBean) {
        showMySchoolData(listBean);
    }
}
