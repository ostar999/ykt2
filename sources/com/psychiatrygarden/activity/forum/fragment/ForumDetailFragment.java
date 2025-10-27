package com.psychiatrygarden.activity.forum.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.FixedPreloadSizeProvider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolInfoActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyResultActivity;
import com.psychiatrygarden.activity.forum.ForumListActivity;
import com.psychiatrygarden.activity.forum.bean.ForumChildrenBean;
import com.psychiatrygarden.activity.forum.bean.ForumIndexBean;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ForumDetailFragment extends BaseFragment {
    public BaseQuickAdapter<ForumIndexBean.DataBean.ListBean, BaseViewHolder> adapter;
    public SmartRefreshLayout mRefresh;
    public RecyclerView recycleview;
    private final List<ForumIndexBean.DataBean.ListBean> dataSchool = new ArrayList();
    public int page = 1;

    public class MyPreloadModelProvider implements ListPreloader.PreloadModelProvider<String> {
        private MyPreloadModelProvider() {
        }

        @Override // com.bumptech.glide.ListPreloader.PreloadModelProvider
        @NonNull
        public List<String> getPreloadItems(int position) {
            String logo = ((ForumIndexBean.DataBean.ListBean) ForumDetailFragment.this.dataSchool.get(position)).getLogo();
            return TextUtils.isEmpty(logo) ? Collections.emptyList() : Collections.singletonList(logo);
        }

        @Override // com.bumptech.glide.ListPreloader.PreloadModelProvider
        @Nullable
        public RequestBuilder<Drawable> getPreloadRequestBuilder(@NonNull String url) {
            return Glide.with(((BaseFragment) ForumDetailFragment.this).mContext).load((Object) GlideUtils.generateUrl(url)).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(new ColorDrawable(ContextCompat.getColor(ForumDetailFragment.this.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).error(R.drawable.schooldefaultimg));
        }
    }

    public static ForumDetailFragment getInstent(String pid, String keywords) {
        ForumDetailFragment forumDetailFragment = new ForumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pid", "" + pid);
        bundle.putString("keywords", keywords);
        forumDetailFragment.setArguments(bundle);
        return forumDetailFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page++;
        getForumDetailData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (view.getId() == R.id.checkeddown && isLogin()) {
            if (this.dataSchool.get(i2) == null) {
                ToastUtil.shortToast(getActivity(), "数据异常不支持操作！");
                return;
            }
            if ("1".equals(this.dataSchool.get(i2).getIs_follow())) {
                this.dataSchool.get(i2).setIs_follow("0");
            } else {
                this.dataSchool.get(i2).setIs_follow("1");
            }
            baseQuickAdapter.notifyItemChanged(i2, 0);
            EventBus.getDefault().post(this.dataSchool.get(i2));
            getFollowData(this.dataSchool.get(i2).getId() + "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        goToDetail(this.dataSchool.get(i2).getId(), this.dataSchool.get(i2).getAccess());
    }

    public void getFollowData(String group_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("pid", "" + getArguments().getString("pid"));
        ajaxParams.put("group_id", group_id + "");
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.getforumfollowApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumDetailFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
            }
        });
    }

    public void getForumDetailData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("pid", "" + getArguments().getString("pid"));
        if (!"".equals(getArguments().getString("keywords"))) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "" + getArguments().getString("keywords"));
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumchildrenApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumDetailFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumDetailFragment forumDetailFragment = ForumDetailFragment.this;
                int i2 = forumDetailFragment.page;
                if (i2 > 1) {
                    forumDetailFragment.page = i2 - 1;
                    forumDetailFragment.mRefresh.finishLoadMore();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    ForumChildrenBean forumChildrenBean = (ForumChildrenBean) new Gson().fromJson(s2, ForumChildrenBean.class);
                    if (!forumChildrenBean.getCode().equals("200")) {
                        ForumDetailFragment forumDetailFragment = ForumDetailFragment.this;
                        int i2 = forumDetailFragment.page;
                        if (i2 > 1) {
                            forumDetailFragment.page = i2 - 1;
                            forumDetailFragment.mRefresh.finishLoadMore();
                            return;
                        }
                        return;
                    }
                    ForumDetailFragment forumDetailFragment2 = ForumDetailFragment.this;
                    if (forumDetailFragment2.page == 1) {
                        forumDetailFragment2.dataSchool.clear();
                        ForumDetailFragment.this.dataSchool.addAll(forumChildrenBean.getData());
                    } else if (forumChildrenBean.getData().size() > 0) {
                        ForumDetailFragment.this.dataSchool.addAll(forumChildrenBean.getData());
                        ForumDetailFragment.this.mRefresh.finishLoadMore();
                    } else {
                        ForumDetailFragment forumDetailFragment3 = ForumDetailFragment.this;
                        int i3 = forumDetailFragment3.page;
                        if (i3 > 1) {
                            forumDetailFragment3.page = i3 - 1;
                        }
                        forumDetailFragment3.mRefresh.finishLoadMoreWithNoMoreData();
                    }
                    ForumDetailFragment.this.adapter.notifyDataSetChanged();
                } catch (Exception unused) {
                    ForumDetailFragment forumDetailFragment4 = ForumDetailFragment.this;
                    int i4 = forumDetailFragment4.page;
                    if (i4 > 1) {
                        forumDetailFragment4.page = i4 - 1;
                        forumDetailFragment4.mRefresh.finishLoadMore();
                    }
                }
            }
        });
    }

    public void getForumInfoData(final String group_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + group_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumDetailFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumDetailFragment.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ForumDetailFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ForumInfoBean forumInfoBean = (ForumInfoBean) new Gson().fromJson(s2, ForumInfoBean.class);
                    if ("200".equals(forumInfoBean.getCode())) {
                        ForumInfoBean.DataBean data = forumInfoBean.getData();
                        if ("-1".equals(data.getJoin_the_state())) {
                            Intent intent = new Intent(ForumDetailFragment.this.getActivity(), (Class<?>) CircleSchoolInfoActivity.class);
                            intent.putExtra("group_id", "" + group_id);
                            intent.putExtra("dataBean", data);
                            ForumDetailFragment.this.startActivity(intent);
                        } else if ("0".equals(data.getJoin_the_state())) {
                            Intent intent2 = new Intent(ForumDetailFragment.this.getActivity(), (Class<?>) CircleSchoolVerifyResultActivity.class);
                            intent2.putExtra("flag", "0");
                            intent2.putExtra("group_id", "" + group_id);
                            ForumDetailFragment.this.startActivity(intent2);
                        } else if ("1".equals(data.getJoin_the_state())) {
                            Intent intent3 = new Intent(ForumDetailFragment.this.getActivity(), (Class<?>) ForumListActivity.class);
                            intent3.putExtra("group_id", "" + group_id);
                            ForumDetailFragment.this.startActivity(intent3);
                        } else {
                            Intent intent4 = new Intent(ForumDetailFragment.this.getActivity(), (Class<?>) CircleSchoolVerifyResultActivity.class);
                            intent4.putExtra("flag", "2");
                            intent4.putExtra("group_id", "" + group_id);
                            ForumDetailFragment.this.startActivity(intent4);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ForumDetailFragment.this.hideProgressDialog();
            }
        });
    }

    public void getImageData(String stringBuffer, TextView mTextView) {
        try {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            if (getArguments().getString("keywords") != null && !"".equals(getArguments().getString("keywords"))) {
                for (String str : getArguments().getString("keywords").split("\\s+")) {
                    String strReplace = str.replace("\\s+", "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, this.mContext.getResources().getColorStateList(R.color.app_theme_red), null), matcher.start(0), matcher.end(0), 34);
                        }
                    }
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_forum_detail;
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.mRefresh = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMore(true);
        this.mRefresh.setEnableRefresh(false);
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.forum.fragment.b
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12373c.lambda$initViews$0(refreshLayout);
            }
        });
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.recycleview.setNestedScrollingEnabled(true);
        this.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recycleview.addOnScrollListener(new RecyclerViewPreloader(Glide.with(this.mContext), new MyPreloadModelProvider(), new FixedPreloadSizeProvider(ScreenUtil.getPxByDp(this.mContext, 50), ScreenUtil.getPxByDp(this.mContext, 50)), 20));
        BaseQuickAdapter<ForumIndexBean.DataBean.ListBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ForumIndexBean.DataBean.ListBean, BaseViewHolder>(R.layout.layout_forum_index_item, this.dataSchool) { // from class: com.psychiatrygarden.activity.forum.fragment.ForumDetailFragment.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ForumIndexBean.DataBean.ListBean item) {
                ImageView imageView = (ImageView) helper.getView(R.id.header);
                TextView textView = (TextView) helper.getView(R.id.name);
                TextView textView2 = (TextView) helper.getView(R.id.detail);
                ((ImageView) helper.getView(R.id.checkeddown)).setSelected("1".equals(item.getIs_follow()));
                if (item.getLogo() == null || "".equals(item.getLogo())) {
                    imageView.setImageResource(R.drawable.schooldefaultimg);
                } else {
                    Glide.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(item.getLogo())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.schooldefaultimg).error(R.drawable.schooldefaultimg)).into(imageView);
                }
                if ("".equals(ForumDetailFragment.this.getArguments().getString("keywords"))) {
                    textView.setText(item.getName());
                } else {
                    ForumDetailFragment.this.getImageData(item.getName(), textView);
                }
                textView2.setText(item.getUser_count());
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycleview.setAdapter(baseQuickAdapter);
        this.adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.adapter.addChildClickViewIds(R.id.checkeddown);
        this.adapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.c
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12374c.lambda$initViews$1(baseQuickAdapter2, view, i2);
            }
        });
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.d
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12375c.lambda$initViews$2(baseQuickAdapter2, view, i2);
            }
        });
        getForumDetailData();
    }

    public void onEventMainThread(ForumIndexBean.DataBean.ListBean listBean) {
        if (listBean != null) {
            for (int i2 = 0; i2 < this.dataSchool.size(); i2++) {
                if (listBean.getId().equals(this.dataSchool.get(i2).getId())) {
                    this.dataSchool.get(i2).setIs_follow(listBean.getIs_follow());
                    BaseQuickAdapter<ForumIndexBean.DataBean.ListBean, BaseViewHolder> baseQuickAdapter = this.adapter;
                    if (baseQuickAdapter != null) {
                        baseQuickAdapter.notifyItemChanged(i2, 0);
                        return;
                    }
                    return;
                }
            }
        }
    }
}
