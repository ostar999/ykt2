package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.HomeInfoListAct;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.HomeInfoItemBean;
import com.psychiatrygarden.forum.experience.SearchExperienceAct;
import com.psychiatrygarden.forum.experience.SearchExperienceAdp;
import com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.utils.VerticalImageSpan;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000M\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001\u0006\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0014J\u001a\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u000bH\u0014J\b\u0010\u001b\u001a\u00020\u0011H\u0002J\b\u0010\u001c\u001a\u00020\u0011H\u0002J\b\u0010\u001d\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/NewHomeBottomListFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "addMoreView", "", "circleAdapter", "com/psychiatrygarden/fragmenthome/NewHomeBottomListFragment$circleAdapter$1", "Lcom/psychiatrygarden/fragmenthome/NewHomeBottomListFragment$circleAdapter$1;", "itemClickListener", "Lcom/chad/library/adapter/base/listener/OnItemClickListener;", "moreView", "Landroid/view/View;", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "type", "", "getImageData", "", "stringBuffer", "Ljava/lang/StringBuffer;", "mTextView", "Landroid/widget/TextView;", "getLayoutId", "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "loadCircleData", "loadHandoutData", "loadInfoData", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class NewHomeBottomListFragment extends BaseFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_HANDOUT = 2;
    public static final int TYPE_INFO = 3;
    private boolean addMoreView;
    private View moreView;
    private RecyclerView rvList;
    private int type = 1;

    @NotNull
    private final NewHomeBottomListFragment$circleAdapter$1 circleAdapter = new NewHomeBottomListFragment$circleAdapter$1(this);

    @NotNull
    private final OnItemClickListener itemClickListener = new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.r8
        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
        public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            NewHomeBottomListFragment.itemClickListener$lambda$1(this.f15966c, baseQuickAdapter, view, i2);
        }
    };

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/NewHomeBottomListFragment$Companion;", "", "()V", "TYPE_CIRCLE", "", "TYPE_HANDOUT", "TYPE_INFO", "newInstance", "Lcom/psychiatrygarden/fragmenthome/NewHomeBottomListFragment;", "type", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nNewHomeBottomListFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NewHomeBottomListFragment.kt\ncom/psychiatrygarden/fragmenthome/NewHomeBottomListFragment$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,503:1\n1#2:504\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final NewHomeBottomListFragment newInstance(int type) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
            NewHomeBottomListFragment newHomeBottomListFragment = new NewHomeBottomListFragment();
            newHomeBottomListFragment.setArguments(bundle);
            return newHomeBottomListFragment;
        }
    }

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"com/psychiatrygarden/fragmenthome/NewHomeBottomListFragment$loadInfoData$2", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", AliyunVodHttpCommon.Format.FORMAT_JSON, "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment$loadInfoData$2, reason: invalid class name */
    public static final class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$0(NewHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1 mAdapter, NewHomeBottomListFragment this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(mAdapter, "$mAdapter");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            HomeInfoItemBean item = mAdapter.getItem(i2);
            this$0.startActivity(new Intent().setClass(((BaseFragment) this$0).mContext, WebLongSaveActivity.class).putExtra("web_url", item.getUrl()).putExtra("title", item.getTitle()));
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@NotNull String json) {
            View view;
            Intrinsics.checkNotNullParameter(json, "json");
            try {
                JSONObject jSONObject = new JSONObject(json);
                if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                    List dataList = (List) new Gson().fromJson(jSONObject.optJSONObject("data").optString("list"), new TypeToken<List<? extends HomeInfoItemBean>>() { // from class: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment$loadInfoData$2$onSuccess$dataList$1
                    }.getType());
                    List list = dataList;
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    final NewHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1 newHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1 = new NewHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1(NewHomeBottomListFragment.this);
                    RecyclerView recyclerView = null;
                    if (!NewHomeBottomListFragment.this.addMoreView && dataList.size() >= 10) {
                        NewHomeBottomListFragment.this.addMoreView = true;
                        View view2 = NewHomeBottomListFragment.this.moreView;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("moreView");
                            view = null;
                        } else {
                            view = view2;
                        }
                        BaseQuickAdapter.addFooterView$default(newHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1, view, 0, 0, 6, null);
                    }
                    RecyclerView recyclerView2 = NewHomeBottomListFragment.this.rvList;
                    if (recyclerView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvList");
                    } else {
                        recyclerView = recyclerView2;
                    }
                    recyclerView.setAdapter(newHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1);
                    newHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1.setEmptyView(new CustomEmptyView(((BaseFragment) NewHomeBottomListFragment.this).mContext, 0, "暂无数据"));
                    if (dataList.size() <= 10) {
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                    } else {
                        dataList = dataList.subList(0, 10);
                    }
                    newHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1.setList(dataList);
                    final NewHomeBottomListFragment newHomeBottomListFragment = NewHomeBottomListFragment.this;
                    newHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.v8
                        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                        public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view3, int i2) {
                            NewHomeBottomListFragment.AnonymousClass2.onSuccess$lambda$0(newHomeBottomListFragment$loadInfoData$2$onSuccess$mAdapter$1, newHomeBottomListFragment, baseQuickAdapter, view3, i2);
                        }
                    });
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getImageData(StringBuffer stringBuffer, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(stringBuffer.toString());
            while (matcher.find()) {
                String group = matcher.group();
                Intrinsics.checkNotNullExpressionValue(group, "group");
                if (StringsKt__StringsKt.contains$default((CharSequence) group, (CharSequence) "http", false, 2, (Object) null)) {
                    String strSubstring = group.substring(1, group.length() - 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                    spannableStringBuilder.setSpan(new VerticalImageSpan(new URLImageParser(mTextView, this.mContext, (int) textSize).getDrawable(strSubstring)), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$0(NewHomeBottomListFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int i2 = this$0.type;
        if (i2 == 1) {
            EventBus.getDefault().post("jumpForum");
            return;
        }
        if (i2 == 2) {
            SearchExperienceAct.newIntent(this$0.mContext);
        } else {
            if (i2 != 3) {
                return;
            }
            HomeInfoListAct.Companion companion = HomeInfoListAct.INSTANCE;
            Context mContext = this$0.mContext;
            Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
            companion.navigationToHomeInfoListActivity(mContext, "1");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void itemClickListener$lambda$1(NewHomeBottomListFragment this$0, BaseQuickAdapter adapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        if (this$0.isLogin()) {
            Object item = adapter.getItem(i2);
            Intrinsics.checkNotNull(item, "null cannot be cast to non-null type com.psychiatrygarden.bean.CirclrListBean.DataBean");
            CirclrListBean.DataBean dataBean = (CirclrListBean.DataBean) item;
            String id = dataBean.getId();
            if (id != null) {
                Intent intent = new Intent(this$0.mContext, (Class<?>) HandoutsInfoActivity.class);
                intent.putExtra("article", id);
                intent.putExtra("json_path", dataBean.getJson_path());
                intent.putExtra("html_path", dataBean.getHtml_path());
                intent.putExtra("h5_path", dataBean.getH5_path());
                intent.putExtra("is_rich_text", dataBean.getIs_rich_text());
                intent.putExtra("app_id", dataBean.getApp_id());
                this$0.startActivity(intent);
            }
        }
    }

    private final void loadCircleData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "-1");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("limit", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.articleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment.loadCircleData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                List listSubList;
                View view;
                Intrinsics.checkNotNullParameter(json, "json");
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    RecyclerView recyclerView = null;
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        RecyclerView recyclerView2 = NewHomeBottomListFragment.this.rvList;
                        if (recyclerView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvList");
                        } else {
                            recyclerView = recyclerView2;
                        }
                        NewHomeBottomListFragment$circleAdapter$1 newHomeBottomListFragment$circleAdapter$1 = NewHomeBottomListFragment.this.circleAdapter;
                        newHomeBottomListFragment$circleAdapter$1.setEmptyView(new CustomEmptyView(((BaseFragment) NewHomeBottomListFragment.this).mContext, 0, "暂无数据"));
                        newHomeBottomListFragment$circleAdapter$1.setList(new ArrayList());
                        recyclerView.setAdapter(newHomeBottomListFragment$circleAdapter$1);
                        return;
                    }
                    List circleList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends CirclrListBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment$loadCircleData$1$onSuccess$circleList$1
                    }.getType());
                    List list = circleList;
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    RecyclerView recyclerView3 = NewHomeBottomListFragment.this.rvList;
                    if (recyclerView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvList");
                        recyclerView3 = null;
                    }
                    NewHomeBottomListFragment$circleAdapter$1 newHomeBottomListFragment$circleAdapter$12 = NewHomeBottomListFragment.this.circleAdapter;
                    final NewHomeBottomListFragment newHomeBottomListFragment = NewHomeBottomListFragment.this;
                    if (circleList.size() > 10) {
                        listSubList = circleList.subList(0, 10);
                    } else {
                        Intrinsics.checkNotNullExpressionValue(circleList, "circleList");
                        listSubList = circleList;
                    }
                    newHomeBottomListFragment$circleAdapter$12.setList(listSubList);
                    if (circleList.size() >= 10 && !newHomeBottomListFragment.addMoreView) {
                        newHomeBottomListFragment.addMoreView = true;
                        View view2 = newHomeBottomListFragment.moreView;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("moreView");
                            view = null;
                        } else {
                            view = view2;
                        }
                        BaseQuickAdapter.addFooterView$default(newHomeBottomListFragment$circleAdapter$12, view, 0, 0, 6, null);
                    }
                    newHomeBottomListFragment$circleAdapter$12.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment$loadCircleData$1$onSuccess$1$1
                        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                        public void onItemClick(@NotNull BaseQuickAdapter<?, ?> adapter, @NotNull View view3, int position) {
                            Intrinsics.checkNotNullParameter(adapter, "adapter");
                            Intrinsics.checkNotNullParameter(view3, "view");
                            Object item = adapter.getItem(position);
                            Intrinsics.checkNotNull(item, "null cannot be cast to non-null type com.psychiatrygarden.bean.CirclrListBean.DataBean");
                            CirclrListBean.DataBean dataBean = (CirclrListBean.DataBean) item;
                            if (newHomeBottomListFragment.isLogin()) {
                                if (Intrinsics.areEqual("1", dataBean.getNo_access())) {
                                    newHomeBottomListFragment.startActivity(new Intent(newHomeBottomListFragment.getActivity(), (Class<?>) MemberCenterActivity.class));
                                    return;
                                }
                                if (!Intrinsics.areEqual(dataBean.getId(), dataBean.getExp_id())) {
                                    Intent intent = new Intent(((BaseFragment) newHomeBottomListFragment).mContext, (Class<?>) CircleInfoActivity.class);
                                    intent.putExtra("channel_id", "-1");
                                    intent.putExtra("article_id", dataBean.getId());
                                    intent.putExtra("module_type", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR);
                                    intent.putExtra("commentCount", dataBean.getComment_count());
                                    newHomeBottomListFragment.startActivity(intent);
                                    return;
                                }
                                Intent intent2 = new Intent(newHomeBottomListFragment.getActivity(), (Class<?>) HandoutsInfoActivity.class);
                                intent2.putExtra("cat_id", dataBean.getCid());
                                intent2.putExtra("article", dataBean.getId());
                                intent2.putExtra("json_path", dataBean.getJson_path());
                                intent2.putExtra("html_path", dataBean.getHtml_path());
                                intent2.putExtra("h5_path", dataBean.getH5_path());
                                intent2.putExtra("is_rich_text", dataBean.getIs_rich_text());
                                intent2.putExtra("index", 0);
                                intent2.putExtra("app_id", dataBean.getApp_id());
                                newHomeBottomListFragment.startActivity(intent2);
                            }
                        }
                    });
                    recyclerView3.setAdapter(newHomeBottomListFragment$circleAdapter$12);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void loadHandoutData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("search_type", "2");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("limit", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put("code", "exp");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "");
        ajaxParams.put("search_app_id", "");
        ajaxParams.put("ranking", "");
        ajaxParams.put("time_range", "");
        ajaxParams.put("time_sort", "");
        ajaxParams.put("score_type", "");
        ajaxParams.put("ctime", "desc");
        ajaxParams.put("test_time", "");
        ajaxParams.put("u_cid", "");
        ajaxParams.put("p_cid", "");
        ajaxParams.put("m_cid", "");
        ajaxParams.put("file_type", "");
        ajaxParams.put("category_id", "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.searchArticleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment.loadHandoutData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                RecyclerView recyclerView = NewHomeBottomListFragment.this.rvList;
                if (recyclerView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvList");
                    recyclerView = null;
                }
                SearchExperienceAdp searchExperienceAdp = new SearchExperienceAdp();
                NewHomeBottomListFragment newHomeBottomListFragment = NewHomeBottomListFragment.this;
                searchExperienceAdp.setList(new ArrayList());
                searchExperienceAdp.setEmptyView(new CustomEmptyView(((BaseFragment) newHomeBottomListFragment).mContext, 0, "暂无数据"));
                recyclerView.setAdapter(searchExperienceAdp);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String json) {
                List listSubList;
                View view;
                Intrinsics.checkNotNullParameter(json, "json");
                RecyclerView recyclerView = null;
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (!Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        RecyclerView recyclerView2 = NewHomeBottomListFragment.this.rvList;
                        if (recyclerView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rvList");
                            recyclerView2 = null;
                        }
                        SearchExperienceAdp searchExperienceAdp = new SearchExperienceAdp();
                        NewHomeBottomListFragment newHomeBottomListFragment = NewHomeBottomListFragment.this;
                        searchExperienceAdp.setList(new ArrayList());
                        searchExperienceAdp.setEmptyView(new CustomEmptyView(((BaseFragment) newHomeBottomListFragment).mContext, 0, "暂无数据"));
                        recyclerView2.setAdapter(searchExperienceAdp);
                        return;
                    }
                    List dataList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends CirclrListBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.NewHomeBottomListFragment$loadHandoutData$1$onSuccess$dataList$1
                    }.getType());
                    List list = dataList;
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    RecyclerView recyclerView3 = NewHomeBottomListFragment.this.rvList;
                    if (recyclerView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvList");
                        recyclerView3 = null;
                    }
                    SearchExperienceAdp searchExperienceAdp2 = new SearchExperienceAdp(true);
                    NewHomeBottomListFragment newHomeBottomListFragment2 = NewHomeBottomListFragment.this;
                    if (dataList.size() <= 10) {
                        Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                        listSubList = dataList;
                    } else {
                        listSubList = dataList.subList(0, 10);
                    }
                    searchExperienceAdp2.setList(listSubList);
                    searchExperienceAdp2.setOnItemClickListener(newHomeBottomListFragment2.itemClickListener);
                    searchExperienceAdp2.setEmptyView(new CustomEmptyView(((BaseFragment) newHomeBottomListFragment2).mContext, 0, "暂无数据"));
                    if (dataList.size() >= 10 && !newHomeBottomListFragment2.addMoreView) {
                        newHomeBottomListFragment2.addMoreView = true;
                        View view2 = newHomeBottomListFragment2.moreView;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("moreView");
                            view = null;
                        } else {
                            view = view2;
                        }
                        BaseQuickAdapter.addFooterView$default(searchExperienceAdp2, view, 0, 0, 6, null);
                    }
                    recyclerView3.setAdapter(searchExperienceAdp2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    RecyclerView recyclerView4 = NewHomeBottomListFragment.this.rvList;
                    if (recyclerView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvList");
                    } else {
                        recyclerView = recyclerView4;
                    }
                    SearchExperienceAdp searchExperienceAdp3 = new SearchExperienceAdp();
                    NewHomeBottomListFragment newHomeBottomListFragment3 = NewHomeBottomListFragment.this;
                    searchExperienceAdp3.setList(new ArrayList());
                    searchExperienceAdp3.setEmptyView(new CustomEmptyView(((BaseFragment) newHomeBottomListFragment3).mContext, 0, "暂无数据"));
                    recyclerView.setAdapter(searchExperienceAdp3);
                }
            }
        });
    }

    private final void loadInfoData() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.newHomeInfo;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.get(context, str, ajaxParams, new AnonymousClass2());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_new_home_bottom_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.rvList);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.rvList)");
        this.rvList = (RecyclerView) view;
        Bundle arguments = getArguments();
        this.type = arguments != null ? arguments.getInt("type", 1) : 1;
        View view2 = null;
        View viewInflate = View.inflate(this.mContext, R.layout.home_new_style_bottom_list_more_footer_view, null);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(mContext, R.layo…t_more_footer_view, null)");
        this.moreView = viewInflate;
        int i2 = this.type;
        if (i2 == 1) {
            loadCircleData();
        } else if (i2 == 2) {
            loadHandoutData();
        } else if (i2 == 3) {
            loadInfoData();
        }
        View view3 = this.moreView;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("moreView");
        } else {
            view2 = view3;
        }
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.s8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                NewHomeBottomListFragment.initViews$lambda$0(this.f15985c, view4);
            }
        });
    }
}
