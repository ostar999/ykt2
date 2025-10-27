package com.psychiatrygarden.fragmenthome;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.RecommendVideoBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000=\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0014J\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u000fH\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/RecommendVideoFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "()V", "itemId", "", "knowledgeId", "mAdapter", "com/psychiatrygarden/fragmenthome/RecommendVideoFragment$mAdapter$1", "Lcom/psychiatrygarden/fragmenthome/RecommendVideoFragment$mAdapter$1;", "rvVideos", "Landroidx/recyclerview/widget/RecyclerView;", "type", "getLayoutId", "", "initViews", "", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "loadData", "onResume", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRecommendVideoFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecommendVideoFragment.kt\ncom/psychiatrygarden/fragmenthome/RecommendVideoFragment\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,176:1\n1855#2,2:177\n1#3:179\n*S KotlinDebug\n*F\n+ 1 RecommendVideoFragment.kt\ncom/psychiatrygarden/fragmenthome/RecommendVideoFragment\n*L\n116#1:177,2\n*E\n"})
/* loaded from: classes5.dex */
public final class RecommendVideoFragment extends BaseFragment {
    private String knowledgeId;
    private RecyclerView rvVideos;
    private String type;

    @NotNull
    private String itemId = "";

    @NotNull
    private final RecommendVideoFragment$mAdapter$1 mAdapter = new RecommendVideoFragment$mAdapter$1(this);

    private final void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        String str = this.knowledgeId;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("knowledgeId");
            str = null;
        }
        ajaxParams.put("knowledge_id", str);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.recommendVideoList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.RecommendVideoFragment.loadData.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                RecyclerView recyclerView;
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        Object objFromJson = new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends RecommendVideoBean>>() { // from class: com.psychiatrygarden.fragmenthome.RecommendVideoFragment$loadData$1$onSuccess$videoList$1
                        }.getType());
                        Intrinsics.checkNotNullExpressionValue(objFromJson, "Gson().fromJson(data, ob…endVideoBean>>() {}.type)");
                        List list = (List) objFromJson;
                        if (!list.isEmpty()) {
                            RecommendVideoFragment recommendVideoFragment = RecommendVideoFragment.this;
                            Iterator it = list.iterator();
                            while (true) {
                                recyclerView = null;
                                String str2 = null;
                                if (!it.hasNext()) {
                                    break;
                                }
                                RecommendVideoBean recommendVideoBean = (RecommendVideoBean) it.next();
                                StringBuilder sb = new StringBuilder();
                                sb.append(CommonParameter.LAST_RECOMMEND_VIDEO);
                                sb.append(CommonParameter.App_Id);
                                sb.append('_');
                                String str3 = recommendVideoFragment.knowledgeId;
                                if (str3 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("knowledgeId");
                                } else {
                                    str2 = str3;
                                }
                                sb.append(str2);
                                sb.append('_');
                                sb.append(recommendVideoBean.getId());
                                if (!TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(sb.toString(), ((BaseFragment) recommendVideoFragment).mContext))) {
                                    recommendVideoBean.setLastSee(true);
                                }
                            }
                            RecyclerView recyclerView2 = RecommendVideoFragment.this.rvVideos;
                            if (recyclerView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("rvVideos");
                            } else {
                                recyclerView = recyclerView2;
                            }
                            RecommendVideoFragment$mAdapter$1 recommendVideoFragment$mAdapter$1 = RecommendVideoFragment.this.mAdapter;
                            recommendVideoFragment$mAdapter$1.setList(list);
                            recyclerView.setAdapter(recommendVideoFragment$mAdapter$1);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_recomment_video;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Bundle arguments = getArguments();
        String str = null;
        String string = arguments != null ? arguments.getString("knowledge_id", "") : null;
        if (string == null) {
            string = "";
        }
        this.knowledgeId = string;
        Bundle arguments2 = getArguments();
        String string2 = arguments2 != null ? arguments2.getString("type", "") : null;
        this.type = string2 != null ? string2 : "";
        View view = holder.get(R.id.rvVideos);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.rvVideos)");
        this.rvVideos = (RecyclerView) view;
        String str2 = this.knowledgeId;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("knowledgeId");
        } else {
            str = str2;
        }
        if (str.length() > 0) {
            loadData();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (!(!this.mAdapter.getData().isEmpty()) || TextUtils.isEmpty(this.itemId)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(CommonParameter.LAST_RECOMMEND_VIDEO);
        sb.append(CommonParameter.App_Id);
        sb.append('_');
        String str = this.knowledgeId;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("knowledgeId");
            str = null;
        }
        sb.append(str);
        sb.append('_');
        sb.append(this.itemId);
        String lastVid = SharePreferencesUtils.readStrConfig(sb.toString(), this.mContext);
        if (TextUtils.isEmpty(lastVid)) {
            return;
        }
        Intrinsics.checkNotNullExpressionValue(lastVid, "lastVid");
        if (StringsKt__StringsKt.contains$default((CharSequence) lastVid, (CharSequence) StrPool.UNDERLINE, false, 2, (Object) null)) {
            List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) lastVid, new String[]{StrPool.UNDERLINE}, false, 0, 6, (Object) null);
            String str2 = (String) listSplit$default.get(0);
            String str3 = (String) listSplit$default.get(1);
            for (RecommendVideoBean recommendVideoBean : this.mAdapter.getData()) {
                recommendVideoBean.setLastSee(Intrinsics.areEqual(recommendVideoBean.getVid(), str2));
                if (Intrinsics.areEqual(recommendVideoBean.getVid(), str2)) {
                    recommendVideoBean.setSee(str3);
                }
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
