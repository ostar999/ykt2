package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.adapter.NewViewPager2Adapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.NestedRecyclerView;
import com.psychiatrygarden.widget.PopKnowledgeChartFilter;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f*\u0002\u0014\u0017\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010'\u001a\u00020\f2\u0006\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020\fH\u0002J\b\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020\u001cH\u0014J\b\u0010-\u001a\u00020+H\u0002J\u001a\u0010.\u001a\u00020+2\u0006\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u00010\u0012H\u0014J\u0012\u00102\u001a\u00020+2\b\u00103\u001a\u0004\u0018\u00010\u0012H\u0016J\u0012\u00104\u001a\u00020+2\b\u00105\u001a\u0004\u0018\u00010\fH\u0016J\u0018\u00106\u001a\u00020+2\u0006\u00107\u001a\u00020\f2\u0006\u0010)\u001a\u00020\fH\u0002J\b\u00108\u001a\u00020+H\u0002J\u000e\u00109\u001a\u00020+2\u0006\u0010:\u001a\u00020\u001cJ\u0010\u0010;\u001a\u00020+2\u0006\u0010:\u001a\u00020\u001cH\u0002R\u001e\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\t0\u0005j\b\u0012\u0004\u0012\u00020\t`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u00010\u0005j\b\u0012\u0004\u0012\u00020\u0001`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0015R\u0010\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0018R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/QuestionKnowledgeChartFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "Landroid/view/View$OnClickListener;", "()V", "chartFilterList", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/ChartFilterBean;", "Lkotlin/collections/ArrayList;", "children", "Lcom/psychiatrygarden/bean/SelectIdentityBean$DataBean;", "defaultFilterMap", "Landroid/util/ArrayMap;", "", "", "fragmentList", "isLoadingFilterData", "", "ivFilter", "Landroid/view/View;", "mAdapter", "com/psychiatrygarden/fragmenthome/QuestionKnowledgeChartFragment$mAdapter$1", "Lcom/psychiatrygarden/fragmenthome/QuestionKnowledgeChartFragment$mAdapter$1;", "mFilterShowAdapter", "com/psychiatrygarden/fragmenthome/QuestionKnowledgeChartFragment$mFilterShowAdapter$1", "Lcom/psychiatrygarden/fragmenthome/QuestionKnowledgeChartFragment$mFilterShowAdapter$1;", "mPagerAdapter", "Lcom/psychiatrygarden/adapter/NewViewPager2Adapter;", "map", "", "marqueeMap", "questionBankId", "rvCategoryChild", "Lcom/psychiatrygarden/widget/NestedRecyclerView;", "rvFilter", "Landroidx/recyclerview/widget/RecyclerView;", "tvDesc", "Landroid/widget/TextView;", "viewPager", "Landroidx/viewpager2/widget/ViewPager2;", "formatFilter", "input", "title", "getFilterData", "", "getLayoutId", com.umeng.socialize.tracker.a.f23806c, "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "onClick", "v", "onEventMainThread", "str", "setAliyunLog", "id", "showFilterPoP", "switch2Chapter", "position", "updateMarqueeFilterShow", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nQuestionKnowledgeChartFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 QuestionKnowledgeChartFragment.kt\ncom/psychiatrygarden/fragmenthome/QuestionKnowledgeChartFragment\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Iterators.kt\nkotlin/collections/CollectionsKt__IteratorsKt\n*L\n1#1,512:1\n1855#2,2:513\n1864#2,3:515\n1559#2:518\n1590#2,4:519\n1855#2,2:526\n2645#2:528\n1864#2,3:530\n1#3:523\n1#3:529\n32#4,2:524\n*S KotlinDebug\n*F\n+ 1 QuestionKnowledgeChartFragment.kt\ncom/psychiatrygarden/fragmenthome/QuestionKnowledgeChartFragment\n*L\n110#1:513,2\n175#1:515,3\n390#1:518\n390#1:519,4\n136#1:526,2\n156#1:528\n156#1:530,3\n156#1:529\n486#1:524,2\n*E\n"})
/* loaded from: classes5.dex */
public final class QuestionKnowledgeChartFragment extends BaseFragment implements View.OnClickListener {
    private boolean isLoadingFilterData;
    private View ivFilter;

    @Nullable
    private NewViewPager2Adapter mPagerAdapter;

    @Nullable
    private String questionBankId;
    private NestedRecyclerView rvCategoryChild;
    private RecyclerView rvFilter;
    private TextView tvDesc;
    private ViewPager2 viewPager;

    @NotNull
    private final ArrayList<SelectIdentityBean.DataBean> children = new ArrayList<>();

    @NotNull
    private final ArrayList<ChartFilterBean> chartFilterList = new ArrayList<>();

    @NotNull
    private final ArrayList<BaseFragment> fragmentList = new ArrayList<>();

    @NotNull
    private final ArrayMap<Integer, List<String>> map = new ArrayMap<>();

    @NotNull
    private final ArrayMap<Integer, String> marqueeMap = new ArrayMap<>();

    @NotNull
    private final QuestionKnowledgeChartFragment$mAdapter$1 mAdapter = new BaseQuickAdapter<SelectIdentityBean.DataBean, BaseViewHolder>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeChartFragment$mAdapter$1
        {
            super(R.layout.item_question_category_child, null, 2, null);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull SelectIdentityBean.DataBean item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            holder.setGone(R.id.tv_column_tag, TextUtils.isEmpty(item.getLabel()) || item.isSelect()).setText(R.id.tv_column_tag, item.getLabel());
            TextView textView = (TextView) holder.getView(R.id.tv_column_name);
            String title = item.getTitle();
            if (title == null) {
                title = item.getName();
            }
            textView.setText(title);
            textView.setSelected(item.isSelect());
            TypedArray typedArrayObtainStyledAttributes = ((BaseFragment) this.this$0).mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_knowledge_chart_lock});
            Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "mContext.theme.obtainSty…ic_knowledge_chart_lock))");
            if (item.isSelect()) {
                typedArrayObtainStyledAttributes = ((BaseFragment) this.this$0).mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.ic_knowledge_chart_lock_select});
                Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "mContext.theme.obtainSty…ledge_chart_lock_select))");
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(!Intrinsics.areEqual("0", item.getHas_permission()) ? null : typedArrayObtainStyledAttributes.getDrawable(0), (Drawable) null, (Drawable) null, (Drawable) null);
            typedArrayObtainStyledAttributes.recycle();
            int layoutPosition = holder.getLayoutPosition();
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
            if (layoutPosition == 0) {
                ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = SizeUtil.dp2px(((BaseFragment) this.this$0).mContext, 12);
            } else {
                SelectIdentityBean.DataBean dataBean = getData().get(layoutPosition - 1);
                if (true ^ TextUtils.isEmpty(dataBean.getLabel())) {
                    ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = 0;
                    if (dataBean.isSelect()) {
                        ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = SizeUtil.dp2px(((BaseFragment) this.this$0).mContext, 12);
                    }
                } else {
                    ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = SizeUtil.dp2px(((BaseFragment) this.this$0).mContext, 12);
                }
            }
            holder.itemView.setLayoutParams(layoutParams2);
        }
    };

    @NotNull
    private final QuestionKnowledgeChartFragment$mFilterShowAdapter$1 mFilterShowAdapter = new BaseQuickAdapter<String, BaseViewHolder>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeChartFragment$mFilterShowAdapter$1
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull String item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            View view = holder.itemView;
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
            ((TextView) view).setText(item);
        }
    };

    @NotNull
    private final ArrayMap<String, List<String>> defaultFilterMap = new ArrayMap<>();

    /* JADX INFO: Access modifiers changed from: private */
    public final String formatFilter(String input, String title) {
        List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) input, new char[]{'/'}, false, 0, 6, (Object) null);
        List list = listSplit$default;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            String strRemoveSuffix = (String) obj;
            if (i2 < CollectionsKt__CollectionsKt.getLastIndex(listSplit$default) && StringsKt__StringsJVMKt.endsWith$default(strRemoveSuffix, title, false, 2, null)) {
                strRemoveSuffix = StringsKt__StringsKt.removeSuffix(strRemoveSuffix, (CharSequence) title);
            }
            arrayList.add(strRemoveSuffix);
            i2 = i3;
        }
        return CollectionsKt___CollectionsKt.joinToString$default(arrayList, "/", null, null, 0, null, null, 62, null);
    }

    private final void getFilterData() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.questionChartFilter;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_category_id", this.questionBankId);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.get(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeChartFragment.getFilterData.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionKnowledgeChartFragment.this.isLoadingFilterData = false;
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                QuestionKnowledgeChartFragment.this.isLoadingFilterData = false;
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            jSONObjectOptJSONObject = new JSONObject();
                        }
                        String strOptString = jSONObjectOptJSONObject.optString("avatar");
                        String strOptString2 = jSONObjectOptJSONObject.optString("describe");
                        String strOptString3 = jSONObjectOptJSONObject.optString("knowledge_img_dark");
                        Intrinsics.checkNotNullExpressionValue(strOptString3, "data.optString(\"knowledge_img_dark\")");
                        String strOptString4 = jSONObjectOptJSONObject.optString("knowledge_img");
                        Intrinsics.checkNotNullExpressionValue(strOptString4, "data.optString(\"knowledge_img\")");
                        String strOptString5 = jSONObjectOptJSONObject.optString("detail_img");
                        Intrinsics.checkNotNullExpressionValue(strOptString5, "data.optString(\"detail_img\")");
                        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionKnowledgeChartFragment.this).mContext);
                        if (!TextUtils.isEmpty(strOptString5)) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_OPTION_DETAIL_IMG + strConfig, strOptString5, ((BaseFragment) QuestionKnowledgeChartFragment.this).mContext);
                        }
                        if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2)) {
                            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_TEXT, strOptString2, ((BaseFragment) QuestionKnowledgeChartFragment.this).mContext);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_ICON, strOptString, ((BaseFragment) QuestionKnowledgeChartFragment.this).mContext);
                        }
                        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC, strOptString4, ((BaseFragment) QuestionKnowledgeChartFragment.this).mContext);
                        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC_DARK, strOptString3, ((BaseFragment) QuestionKnowledgeChartFragment.this).mContext);
                        List<ChartFilterBean> list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("list"), new TypeToken<List<? extends ChartFilterBean>>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeChartFragment$getFilterData$2$onSuccess$list$1
                        }.getType());
                        List list2 = list;
                        if (!(list2 == null || list2.isEmpty())) {
                            Intrinsics.checkNotNullExpressionValue(list, "list");
                            for (ChartFilterBean chartFilterBean : list) {
                                List<ChartFilterBean.ChartFilterValue> value = chartFilterBean.getValue();
                                Intrinsics.checkNotNullExpressionValue(value, "it.value");
                                Iterator<T> it = value.iterator();
                                while (it.hasNext()) {
                                    ((ChartFilterBean.ChartFilterValue) it.next()).setType(chartFilterBean.getType());
                                }
                            }
                            QuestionKnowledgeChartFragment.this.chartFilterList.addAll(list);
                            SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_LIST, new Gson().toJson(QuestionKnowledgeChartFragment.this.chartFilterList), ((BaseFragment) QuestionKnowledgeChartFragment.this).mContext);
                        }
                        QuestionKnowledgeChartFragment.this.showFilterPoP();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0126  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void initData() {
        /*
            Method dump skipped, instructions count: 485
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.QuestionKnowledgeChartFragment.initData():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initData$lambda$5(QuestionKnowledgeChartFragment this$0, BaseQuickAdapter adapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        if (this$0.isLogin()) {
            this$0.updateMarqueeFilterShow(i2);
            ViewPager2 viewPager2 = this$0.viewPager;
            ViewPager2 viewPager22 = null;
            if (viewPager2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager2 = null;
            }
            if (i2 != viewPager2.getCurrentItem()) {
                ViewPager2 viewPager23 = this$0.viewPager;
                if (viewPager23 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                } else {
                    viewPager22 = viewPager23;
                }
                viewPager22.setCurrentItem(i2, false);
                int i3 = 0;
                for (Object obj : this$0.mAdapter.getData()) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    ((SelectIdentityBean.DataBean) obj).setSelect(i3 == i2);
                    i3 = i4;
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setAliyunLog(String id, String title) {
        AliyunEvent aliyunEvent = AliyunEvent.VisitColumn;
        String key = aliyunEvent.getKey();
        String value = aliyunEvent.getValue();
        CommonUtil.addLog(key, value, System.currentTimeMillis() + "", "", "[\"" + id + "\"]", "[\"" + title + "\"]", "", "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showFilterPoP() {
        ArrayList<BaseFragment> arrayList = this.fragmentList;
        ViewPager2 viewPager2 = this.viewPager;
        ViewPager2 viewPager22 = null;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager2 = null;
        }
        BaseFragment baseFragment = arrayList.get(viewPager2.getCurrentItem());
        Intrinsics.checkNotNull(baseFragment, "null cannot be cast to non-null type com.psychiatrygarden.fragmenthome.QuestionKnowledgeListFragment");
        final QuestionKnowledgeListFragment questionKnowledgeListFragment = (QuestionKnowledgeListFragment) baseFragment;
        XPopup.Builder builder = new XPopup.Builder(this.mContext);
        Context mContext = this.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        ArrayList<ChartFilterBean> arrayList2 = this.chartFilterList;
        QuestionKnowledgeChartFragment$mAdapter$1 questionKnowledgeChartFragment$mAdapter$1 = this.mAdapter;
        ViewPager2 viewPager23 = this.viewPager;
        if (viewPager23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
        } else {
            viewPager22 = viewPager23;
        }
        builder.asCustom(new PopKnowledgeChartFilter(mContext, arrayList2, questionKnowledgeChartFragment$mAdapter$1.getItem(viewPager22.getCurrentItem()).getDescribe(), questionKnowledgeListFragment.getFilterDataMap(), new PopKnowledgeChartFilter.ConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeChartFragment.showFilterPoP.1
            @Override // com.psychiatrygarden.widget.PopKnowledgeChartFilter.ConfirmListener
            public void onConfirm(@NotNull Map<String, String> params) throws JSONException, IOException {
                ViewPager2 viewPager24;
                Intrinsics.checkNotNullParameter(params, "params");
                questionKnowledgeListFragment.loadData(params, !params.isEmpty());
                this.defaultFilterMap.clear();
                Object obj = null;
                if (!params.isEmpty()) {
                    ArrayList arrayList3 = new ArrayList();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        this.defaultFilterMap.put(entry.getKey(), StringsKt__StringsKt.split$default((CharSequence) entry.getValue(), new String[]{","}, false, 0, 6, (Object) null));
                        ArrayList<ChartFilterBean> arrayList4 = this.chartFilterList;
                        QuestionKnowledgeChartFragment questionKnowledgeChartFragment = this;
                        for (ChartFilterBean chartFilterBean : arrayList4) {
                            if (Intrinsics.areEqual(chartFilterBean.getType(), entry.getKey())) {
                                int i2 = 0;
                                if (StringsKt__StringsKt.contains$default((CharSequence) entry.getValue(), (CharSequence) ",", false, 2, obj)) {
                                    List<String> listSplit$default = StringsKt__StringsKt.split$default((CharSequence) entry.getValue(), new String[]{","}, false, 0, 6, (Object) null);
                                    ArrayList arrayList5 = new ArrayList();
                                    for (String str : listSplit$default) {
                                        List<ChartFilterBean.ChartFilterValue> value = chartFilterBean.getValue();
                                        Intrinsics.checkNotNullExpressionValue(value, "c.value");
                                        int i3 = i2;
                                        for (Object obj2 : value) {
                                            int i4 = i3 + 1;
                                            if (i3 < 0) {
                                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                            }
                                            ChartFilterBean.ChartFilterValue chartFilterValue = (ChartFilterBean.ChartFilterValue) obj2;
                                            if (Intrinsics.areEqual(chartFilterValue.getKey(), str)) {
                                                arrayList5.add(chartFilterValue.getName() + chartFilterValue.getTitle());
                                            }
                                            i3 = i4;
                                            i2 = 0;
                                        }
                                    }
                                    String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList5, "/", null, null, 0, null, null, 62, null);
                                    arrayList3.add(questionKnowledgeChartFragment.formatFilter(strJoinToString$default, String.valueOf(strJoinToString$default.charAt(strJoinToString$default.length() - 1))));
                                } else {
                                    List<ChartFilterBean.ChartFilterValue> value2 = chartFilterBean.getValue();
                                    Intrinsics.checkNotNullExpressionValue(value2, "c.value");
                                    for (ChartFilterBean.ChartFilterValue chartFilterValue2 : value2) {
                                        if (Intrinsics.areEqual(chartFilterValue2.getKey(), entry.getValue())) {
                                            arrayList3.add(chartFilterValue2.getName() + chartFilterValue2.getTitle());
                                        }
                                    }
                                }
                            }
                            obj = null;
                        }
                    }
                    setList(CollectionsKt___CollectionsKt.distinct(arrayList3));
                    ArrayMap arrayMap = this.map;
                    ViewPager2 viewPager25 = this.viewPager;
                    if (viewPager25 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                        viewPager25 = null;
                    }
                    arrayMap.put(Integer.valueOf(viewPager25.getCurrentItem()), arrayList3);
                } else {
                    this.defaultFilterMap.clear();
                    setList(new ArrayList());
                    ArrayMap arrayMap2 = this.map;
                    ViewPager2 viewPager26 = this.viewPager;
                    if (viewPager26 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                        viewPager26 = null;
                    }
                    arrayMap2.put(Integer.valueOf(viewPager26.getCurrentItem()), new ArrayList());
                }
                if (!getData().isEmpty()) {
                    RecyclerView recyclerView = this.rvFilter;
                    if (recyclerView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                        recyclerView = null;
                    }
                    ViewExtensionsKt.visible(recyclerView);
                    TextView textView = this.tvDesc;
                    if (textView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                        textView = null;
                    }
                    ViewExtensionsKt.gone(textView);
                    JSONObject jSONObject = new JSONObject();
                    for (Map.Entry<String, String> entry2 : params.entrySet()) {
                        jSONObject.put(entry2.getKey(), entry2.getValue());
                    }
                    questionKnowledgeListFragment.writeFilterStr(CollectionsKt___CollectionsKt.joinToString$default(getData(), ",", null, null, 0, null, null, 62, null));
                    QuestionKnowledgeListFragment questionKnowledgeListFragment2 = questionKnowledgeListFragment;
                    String string = jSONObject.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "obj.toString()");
                    questionKnowledgeListFragment2.writeFilterValue(string);
                } else {
                    RecyclerView recyclerView2 = this.rvFilter;
                    if (recyclerView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                        recyclerView2 = null;
                    }
                    ViewExtensionsKt.gone(recyclerView2);
                    QuestionKnowledgeChartFragment$mAdapter$1 questionKnowledgeChartFragment$mAdapter$12 = this.mAdapter;
                    ViewPager2 viewPager27 = this.viewPager;
                    if (viewPager27 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                        viewPager27 = null;
                    }
                    if (!TextUtils.isEmpty(questionKnowledgeChartFragment$mAdapter$12.getItem(viewPager27.getCurrentItem()).getDescribe())) {
                        TextView textView2 = this.tvDesc;
                        if (textView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                            textView2 = null;
                        }
                        ViewExtensionsKt.visible(textView2);
                    }
                    questionKnowledgeListFragment.writeFilterStr("");
                    questionKnowledgeListFragment.writeFilterValue("");
                }
                questionKnowledgeListFragment.setFilterDataMap(this.defaultFilterMap);
                QuestionKnowledgeChartFragment questionKnowledgeChartFragment2 = this;
                ViewPager2 viewPager28 = questionKnowledgeChartFragment2.viewPager;
                if (viewPager28 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                    viewPager24 = null;
                } else {
                    viewPager24 = viewPager28;
                }
                questionKnowledgeChartFragment2.updateMarqueeFilterShow(viewPager24.getCurrentItem());
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switch2Chapter$lambda$3(QuestionKnowledgeChartFragment this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewPager2 viewPager2 = this$0.viewPager;
        if (viewPager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
            viewPager2 = null;
        }
        viewPager2.setCurrentItem(i2);
        this$0.updateMarqueeFilterShow(i2);
        for (BaseFragment baseFragment : this$0.fragmentList) {
            if (baseFragment instanceof QuestionKnowledgeListFragment) {
                ((QuestionKnowledgeListFragment) baseFragment).scroll2SpecifiedChapter();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateMarqueeFilterShow(int position) {
        List<String> arrayList = this.map.get(Integer.valueOf(position));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        TextView textView = null;
        if (arrayList.isEmpty()) {
            RecyclerView recyclerView = this.rvFilter;
            if (recyclerView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                recyclerView = null;
            }
            ViewExtensionsKt.gone(recyclerView);
        } else {
            RecyclerView recyclerView2 = this.rvFilter;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                recyclerView2 = null;
            }
            ViewExtensionsKt.visible(recyclerView2);
            setList(arrayList);
        }
        String str = this.marqueeMap.get(Integer.valueOf(position));
        if (str == null) {
            str = "";
        }
        if ((str.length() == 0) || (!getData().isEmpty())) {
            TextView textView2 = this.tvDesc;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
            } else {
                textView = textView2;
            }
            ViewExtensionsKt.gone(textView);
            return;
        }
        TextView textView3 = this.tvDesc;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
            textView3 = null;
        }
        ViewExtensionsKt.visible(textView3);
        TextView textView4 = this.tvDesc;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
            textView4 = null;
        }
        textView4.setText(str);
        TextView textView5 = this.tvDesc;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
            textView5 = null;
        }
        textView5.requestFocus();
        TextView textView6 = this.tvDesc;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
        } else {
            textView = textView6;
        }
        textView.setSelected(true);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_home_question_knowledge_chart;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.rvCategoryChild);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.rvCategoryChild)");
        this.rvCategoryChild = (NestedRecyclerView) view;
        View view2 = holder.get(R.id.rvFilter);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.rvFilter)");
        this.rvFilter = (RecyclerView) view2;
        View view3 = holder.get(R.id.viewpager);
        Intrinsics.checkNotNullExpressionValue(view3, "holder.get(R.id.viewpager)");
        this.viewPager = (ViewPager2) view3;
        View view4 = holder.get(R.id.iv_filter);
        Intrinsics.checkNotNullExpressionValue(view4, "holder.get(R.id.iv_filter)");
        this.ivFilter = view4;
        ViewPager2 viewPager2 = null;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivFilter");
            view4 = null;
        }
        view4.setOnClickListener(this);
        View view5 = holder.get(R.id.tv_kc_desc);
        Intrinsics.checkNotNullExpressionValue(view5, "holder.get(R.id.tv_kc_desc)");
        this.tvDesc = (TextView) view5;
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("children");
            this.questionBankId = arguments.getString("question_bank_id");
            if (!TextUtils.isEmpty(string)) {
                Collection<? extends SelectIdentityBean.DataBean> arrayList = (List) new Gson().fromJson(string, new TypeToken<List<? extends SelectIdentityBean.DataBean>>() { // from class: com.psychiatrygarden.fragmenthome.QuestionKnowledgeChartFragment$initViews$1$data$1
                }.getType());
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                Collection<? extends SelectIdentityBean.DataBean> collection = arrayList;
                if (!collection.isEmpty()) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((SelectIdentityBean.DataBean) it.next()).setQuestion_bank_id(this.questionBankId);
                    }
                    this.children.clear();
                    this.children.addAll(collection);
                    initData();
                }
            }
        }
        RecyclerView recyclerView = this.rvFilter;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
            recyclerView = null;
        }
        recyclerView.setAdapter(this.mFilterShowAdapter);
        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, "", this.mContext);
        SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_STR, "", this.mContext);
        NestedRecyclerView nestedRecyclerView = this.rvCategoryChild;
        if (nestedRecyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvCategoryChild");
            nestedRecyclerView = null;
        }
        ViewPager2 viewPager22 = this.viewPager;
        if (viewPager22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPager");
        } else {
            viewPager2 = viewPager22;
        }
        nestedRecyclerView.bindViewPager2(viewPager2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@Nullable View v2) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (!this.chartFilterList.isEmpty()) {
            showFilterPoP();
        } else {
            if (this.isLoadingFilterData) {
                return;
            }
            this.isLoadingFilterData = true;
            getFilterData();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(@Nullable String str) throws JSONException {
        super.onEventMainThread(str);
        if (Intrinsics.areEqual("refreshKnowledgeFilter", str)) {
            String filterShowStr = SharePreferencesUtils.readStrConfig(CommonParameter.KNOWLEDGE_FILTER_STR, this.mContext);
            String filterValue = SharePreferencesUtils.readStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, this.mContext);
            if (TextUtils.isEmpty(filterShowStr)) {
                RecyclerView recyclerView = this.rvFilter;
                if (recyclerView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                    recyclerView = null;
                }
                QuestionKnowledgeChartFragment$mFilterShowAdapter$1 questionKnowledgeChartFragment$mFilterShowAdapter$1 = this.mFilterShowAdapter;
                questionKnowledgeChartFragment$mFilterShowAdapter$1.setList(new ArrayList());
                recyclerView.setAdapter(questionKnowledgeChartFragment$mFilterShowAdapter$1);
                TextView textView = this.tvDesc;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                    textView = null;
                }
                ViewExtensionsKt.gone(textView);
                RecyclerView recyclerView2 = this.rvFilter;
                if (recyclerView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                    recyclerView2 = null;
                }
                ViewExtensionsKt.gone(recyclerView2);
                ArrayMap<Integer, List<String>> arrayMap = this.map;
                ViewPager2 viewPager2 = this.viewPager;
                if (viewPager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                    viewPager2 = null;
                }
                arrayMap.put(Integer.valueOf(viewPager2.getCurrentItem()), new ArrayList());
                this.defaultFilterMap.clear();
                ViewPager2 viewPager22 = this.viewPager;
                if (viewPager22 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                    viewPager22 = null;
                }
                updateMarqueeFilterShow(viewPager22.getCurrentItem());
            } else {
                Intrinsics.checkNotNullExpressionValue(filterShowStr, "filterShowStr");
                List<String> listSplit$default = StringsKt__StringsKt.split$default((CharSequence) filterShowStr, new String[]{","}, false, 0, 6, (Object) null);
                RecyclerView recyclerView3 = this.rvFilter;
                if (recyclerView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                    recyclerView3 = null;
                }
                QuestionKnowledgeChartFragment$mFilterShowAdapter$1 questionKnowledgeChartFragment$mFilterShowAdapter$12 = this.mFilterShowAdapter;
                questionKnowledgeChartFragment$mFilterShowAdapter$12.setList(listSplit$default);
                recyclerView3.setAdapter(questionKnowledgeChartFragment$mFilterShowAdapter$12);
                TextView textView2 = this.tvDesc;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                    textView2 = null;
                }
                ViewExtensionsKt.gone(textView2);
                RecyclerView recyclerView4 = this.rvFilter;
                if (recyclerView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rvFilter");
                    recyclerView4 = null;
                }
                ViewExtensionsKt.visible(recyclerView4);
                ArrayMap<Integer, List<String>> arrayMap2 = this.map;
                ViewPager2 viewPager23 = this.viewPager;
                if (viewPager23 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                    viewPager23 = null;
                }
                arrayMap2.put(Integer.valueOf(viewPager23.getCurrentItem()), listSplit$default);
            }
            ArrayList<BaseFragment> arrayList = this.fragmentList;
            ViewPager2 viewPager24 = this.viewPager;
            if (viewPager24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("viewPager");
                viewPager24 = null;
            }
            BaseFragment baseFragment = arrayList.get(viewPager24.getCurrentItem());
            QuestionKnowledgeListFragment questionKnowledgeListFragment = baseFragment instanceof QuestionKnowledgeListFragment ? (QuestionKnowledgeListFragment) baseFragment : null;
            if (questionKnowledgeListFragment == null) {
                return;
            }
            if (TextUtils.isEmpty(filterValue)) {
                SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_VALUE, "", this.mContext);
                SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_STR, "", this.mContext);
                questionKnowledgeListFragment.setFilterDataMap(new ArrayMap<>());
                questionKnowledgeListFragment.loadData(new ArrayMap(), false);
                questionKnowledgeListFragment.writeFilterStr("");
                questionKnowledgeListFragment.writeFilterValue("");
                return;
            }
            try {
                this.defaultFilterMap.clear();
                JSONObject jSONObject = new JSONObject(filterValue);
                ArrayMap arrayMap3 = new ArrayMap();
                if (jSONObject.length() > 0) {
                    Iterator<String> keys = jSONObject.keys();
                    Intrinsics.checkNotNullExpressionValue(keys, "keys");
                    while (keys.hasNext()) {
                        String next = keys.next();
                        ArrayMap<String, List<String>> arrayMap4 = this.defaultFilterMap;
                        String string = jSONObject.getString(next);
                        Intrinsics.checkNotNullExpressionValue(string, "obj.getString(key)");
                        arrayMap4.put(next, StringsKt__StringsKt.split$default((CharSequence) string, new String[]{","}, false, 0, 6, (Object) null));
                        arrayMap3.put(next, jSONObject.getString(next));
                    }
                    questionKnowledgeListFragment.loadData(arrayMap3, true);
                    Intrinsics.checkNotNullExpressionValue(filterShowStr, "filterShowStr");
                    questionKnowledgeListFragment.writeFilterStr(filterShowStr);
                    Intrinsics.checkNotNullExpressionValue(filterValue, "filterValue");
                    questionKnowledgeListFragment.writeFilterValue(filterValue);
                    questionKnowledgeListFragment.setFilterDataMap(this.defaultFilterMap);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void switch2Chapter(final int position) {
        if (this.viewPager != null) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.jb
                @Override // java.lang.Runnable
                public final void run() {
                    QuestionKnowledgeChartFragment.switch2Chapter$lambda$3(this.f15692c, position);
                }
            }, 500L);
        }
    }
}
