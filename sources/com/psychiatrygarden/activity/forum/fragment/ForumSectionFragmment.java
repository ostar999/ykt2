package com.psychiatrygarden.activity.forum.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.HomePageNewActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolInfoActivity;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyResultActivity;
import com.psychiatrygarden.activity.forum.ForumSectionActivity;
import com.psychiatrygarden.activity.forum.bean.ForumInfoBean;
import com.psychiatrygarden.activity.forum.bean.ForumSectionBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForumSectionFragmment extends BaseFragment {
    public BaseQuickAdapter<ForumSectionBean.ListBean, BaseViewHolder> adapter;
    public SmartRefreshLayout mRefresh;
    public RecyclerView recycleview;
    public int page = 1;
    private final List<ForumSectionBean.ListBean> dataSchool = new ArrayList();

    public static ForumSectionFragmment getInstent(String pid, String keywords) {
        ForumSectionFragmment forumSectionFragmment = new ForumSectionFragmment();
        Bundle bundle = new Bundle();
        bundle.putString("pid", "" + pid);
        bundle.putString("keywords", keywords);
        forumSectionFragmment.setArguments(bundle);
        return forumSectionFragmment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page++;
        getForumDetailData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        try {
            if (isLogin() && !this.dataSchool.isEmpty()) {
                if ("1".equals(this.dataSchool.get(i2).getAccess())) {
                    postSelected(this.dataSchool.get(i2).getId());
                } else {
                    getForumInfoData(this.dataSchool.get(i2).getId());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void getForumDetailData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (getArguments() != null) {
            ajaxParams.put("pid", "" + getArguments().getString("pid"));
            if (!"".equals(getArguments().getString("keywords"))) {
                ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "" + getArguments().getString("keywords"));
            }
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumchildrenApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumSectionFragmment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumSectionFragmment forumSectionFragmment = ForumSectionFragmment.this;
                int i2 = forumSectionFragmment.page;
                if (i2 > 1) {
                    forumSectionFragmment.page = i2 - 1;
                    forumSectionFragmment.mRefresh.finishLoadMore();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    ForumSectionBean forumSectionBean = (ForumSectionBean) new Gson().fromJson(s2, ForumSectionBean.class);
                    if (!forumSectionBean.getCode().equals("200")) {
                        ForumSectionFragmment forumSectionFragmment = ForumSectionFragmment.this;
                        int i2 = forumSectionFragmment.page;
                        if (i2 > 1) {
                            forumSectionFragmment.page = i2 - 1;
                            forumSectionFragmment.mRefresh.finishLoadMore();
                            return;
                        }
                        return;
                    }
                    ForumSectionFragmment forumSectionFragmment2 = ForumSectionFragmment.this;
                    if (forumSectionFragmment2.page == 1) {
                        forumSectionFragmment2.dataSchool.clear();
                        ForumSectionFragmment.this.dataSchool.addAll(forumSectionBean.getData());
                    } else if (forumSectionBean.getData().size() > 0) {
                        ForumSectionFragmment.this.dataSchool.addAll(forumSectionBean.getData());
                        ForumSectionFragmment.this.mRefresh.finishLoadMore();
                    } else {
                        ForumSectionFragmment forumSectionFragmment3 = ForumSectionFragmment.this;
                        int i3 = forumSectionFragmment3.page;
                        if (i3 > 1) {
                            forumSectionFragmment3.page = i3 - 1;
                        }
                        forumSectionFragmment3.mRefresh.finishLoadMoreWithNoMoreData();
                    }
                    ForumSectionFragmment.this.adapter.notifyDataSetChanged();
                } catch (Exception unused) {
                    ForumSectionFragmment forumSectionFragmment4 = ForumSectionFragmment.this;
                    int i4 = forumSectionFragmment4.page;
                    if (i4 > 1) {
                        forumSectionFragmment4.page = i4 - 1;
                        forumSectionFragmment4.mRefresh.finishLoadMore();
                    }
                }
            }
        });
    }

    public void getForumInfoData(final String group_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + group_id);
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.getforumdetailApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumSectionFragmment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ForumInfoBean forumInfoBean = (ForumInfoBean) new Gson().fromJson(s2, ForumInfoBean.class);
                    if ("200".equals(forumInfoBean.getCode())) {
                        ForumInfoBean.DataBean data = forumInfoBean.getData();
                        if ("-1".equals(data.getJoin_the_state())) {
                            Intent intent = new Intent(ForumSectionFragmment.this.getActivity(), (Class<?>) CircleSchoolInfoActivity.class);
                            intent.putExtra("group_id", "" + group_id);
                            intent.putExtra("dataBean", data);
                            ForumSectionFragmment.this.startActivity(intent);
                        } else if ("0".equals(data.getJoin_the_state())) {
                            Intent intent2 = new Intent(ForumSectionFragmment.this.getActivity(), (Class<?>) CircleSchoolVerifyResultActivity.class);
                            intent2.putExtra("flag", "0");
                            intent2.putExtra("group_id", "" + group_id);
                            ForumSectionFragmment.this.startActivity(intent2);
                        } else if ("1".equals(data.getJoin_the_state())) {
                            ForumSectionFragmment.this.postSelected(group_id + "");
                        } else {
                            Intent intent3 = new Intent(ForumSectionFragmment.this.getActivity(), (Class<?>) CircleSchoolVerifyResultActivity.class);
                            intent3.putExtra("flag", "2");
                            intent3.putExtra("group_id", "" + group_id);
                            ForumSectionFragmment.this.startActivity(intent3);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getImageData(String stringBuffer, TextView mTextView) {
        try {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer);
            if (getArguments().getString("keywords") != null && !"".equals(getArguments().getString("keywords"))) {
                for (String str : getArguments().getString("keywords").split("\\s+")) {
                    String strReplace = str.replace("\\s+", "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, ContextCompat.getColorStateList(this.mContext, R.color.app_theme_red), null), matcher.start(0), matcher.end(0), 34);
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refreshLayout);
        this.mRefresh = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMore(true);
        this.mRefresh.setEnableRefresh(false);
        this.mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.forum.fragment.w
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12397c.lambda$initViews$0(refreshLayout);
            }
        });
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycleview);
        this.recycleview = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.recycleview.setNestedScrollingEnabled(true);
        this.recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<ForumSectionBean.ListBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ForumSectionBean.ListBean, BaseViewHolder>(R.layout.layout_forum_index_item, this.dataSchool) { // from class: com.psychiatrygarden.activity.forum.fragment.ForumSectionFragmment.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder helper, ForumSectionBean.ListBean item) {
                ImageView imageView = (ImageView) helper.getView(R.id.header);
                TextView textView = (TextView) helper.getView(R.id.name);
                TextView textView2 = (TextView) helper.getView(R.id.detail);
                ImageView imageView2 = (ImageView) helper.getView(R.id.selectedimg);
                if ((ForumSectionFragmment.this.getActivity() instanceof ForumSectionActivity) && "1".equals(item.getSelected())) {
                    imageView2.setVisibility(0);
                } else {
                    imageView2.setVisibility(8);
                }
                if (item.getLogo() == null || "".equals(item.getLogo())) {
                    imageView.setImageResource(R.drawable.schooldefaultimg);
                } else {
                    Glide.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(item.getLogo())).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).error(R.drawable.schooldefaultimg)).into(imageView);
                }
                if (TextUtils.isEmpty(ForumSectionFragmment.this.getArguments() != null ? ForumSectionFragmment.this.getArguments().getString("keywords") : "")) {
                    textView.setText(item.getName());
                } else {
                    ForumSectionFragmment.this.getImageData(item.getName(), textView);
                }
                textView2.setText(String.format(Locale.CHINA, "%s，%s", item.getArticle_count(), item.getComment_count()));
            }
        };
        this.adapter = baseQuickAdapter;
        this.recycleview.setAdapter(baseQuickAdapter);
        this.adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.adapter.addChildClickViewIds(R.id.checkeddown);
        this.adapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.fragment.x
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12398c.lambda$initViews$1(baseQuickAdapter2, view, i2);
            }
        });
        getForumDetailData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (!str.equals("Result_GroupId") || getActivity() == null || (getActivity() instanceof HomePageNewActivity)) {
            return;
        }
        getActivity().finish();
    }

    public void postSelected(final String group_id) {
        ProjectApp.instance().showDialogWindow();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("group_id", "" + group_id);
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.postforumselected, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.fragment.ForumSectionFragmment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                ProjectApp.instance().hideDialogWindow();
                try {
                    if ("200".equals(new JSONObject(s2).optString("code"))) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.GroupId, group_id, ForumSectionFragmment.this.getActivity());
                        if (ForumSectionFragmment.this.getActivity() instanceof HomePageNewActivity) {
                            EventBus.getDefault().post("ResultVeriafer");
                        } else {
                            EventBus.getDefault().post("Result_GroupId");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
