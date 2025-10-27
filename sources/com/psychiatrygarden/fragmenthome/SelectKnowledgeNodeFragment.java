package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChapterNodeBean;
import com.psychiatrygarden.bean.KnowledgeNode;
import com.psychiatrygarden.bean.NodeRedoBean;
import com.psychiatrygarden.event.RedoMultiKnowledgeEvent;
import com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import de.greenrobot.event.EventBus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000}\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0011H\u0002J\b\u0010-\u001a\u00020\u0011H\u0014J\b\u0010.\u001a\u00020+H\u0002J\u001a\u0010/\u001a\u00020+2\u0006\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u00103\u001a\u00020+H\u0002J\b\u00104\u001a\u00020+H\u0002J(\u00105\u001a\u00020+2\u000e\u00106\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u0003072\u0006\u00108\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u0011H\u0016J\b\u0010\u001c\u001a\u00020+H\u0002J\b\u00109\u001a\u00020+H\u0002J\b\u0010:\u001a\u00020+H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u001e\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u00020 0\fj\b\u0012\u0004\u0012\u00020 `\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010!\u001a\u0012\u0012\u0004\u0012\u00020 0\fj\b\u0012\u0004\u0012\u00020 `\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020 0\fj\b\u0012\u0004\u0012\u00020 `\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020%X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020%X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "Lcom/chad/library/adapter/base/listener/OnItemChildClickListener;", "()V", "emptyView", "Lcom/psychiatrygarden/widget/CustomEmptyView;", "ivCloseBack", "Landroid/widget/ImageView;", "mAdapter", "com/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$mAdapter$1", "Lcom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$mAdapter$1;", "mDataList", "Ljava/util/ArrayList;", "Lcom/psychiatrygarden/bean/ChapterNodeBean;", "Lkotlin/collections/ArrayList;", "mViewMap", "Landroid/util/ArrayMap;", "", "Landroid/view/View;", "nodeBottomBg", "Landroid/graphics/drawable/Drawable;", "nodeFullBg", "nodeMiddleBg", "nodeTopBg", "notSelectIcon", "partSelectIcon", "rvChapters", "Landroidx/recyclerview/widget/RecyclerView;", "selectAll", "", "selectIcon", "selectedChapterIdList", "", "selectedNodeIdList", "selectedParentIdList", "studyPlanSelect", "title", "Landroid/widget/TextView;", "treeId", "tvConfirm", "tvSelectAll", "type", "clickItem", "", "position", "getLayoutId", com.umeng.socialize.tracker.a.f23806c, "initViews", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "loadChapterData", "loadData", "onItemChildClick", "adapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "view", "unSelectAll", "updateTitle", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSelectKnowledgeNodeFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SelectKnowledgeNodeFragment.kt\ncom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,723:1\n1#2:724\n766#3:725\n857#3,2:726\n766#3:728\n857#3,2:729\n1855#3,2:731\n1774#3,4:733\n1747#3,3:737\n766#3:740\n857#3,2:741\n1855#3:743\n766#3:744\n857#3,2:745\n1855#3,2:747\n1856#3:749\n766#3:750\n857#3,2:751\n1864#3,3:753\n1864#3,3:756\n766#3:759\n857#3,2:760\n1774#3,4:762\n766#3:766\n857#3,2:767\n1864#3,2:769\n766#3:771\n857#3,2:772\n1864#3,3:774\n1866#3:777\n766#3:778\n857#3,2:779\n1774#3,4:781\n766#3:785\n857#3,2:786\n766#3:788\n857#3,2:789\n350#3,7:791\n1855#3,2:798\n1855#3,2:800\n1774#3,4:802\n1774#3,4:806\n1774#3,4:810\n766#3:814\n857#3,2:815\n766#3:817\n857#3,2:818\n1855#3,2:820\n766#3:822\n857#3,2:823\n1855#3:825\n766#3:826\n857#3,2:827\n1855#3,2:829\n1856#3:831\n1774#3,4:832\n*S KotlinDebug\n*F\n+ 1 SelectKnowledgeNodeFragment.kt\ncom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment\n*L\n344#1:725\n344#1:726,2\n345#1:728\n345#1:729,2\n348#1:731,2\n356#1:733,4\n358#1:737,3\n366#1:740\n366#1:741,2\n368#1:743\n374#1:744\n374#1:745,2\n375#1:747,2\n368#1:749\n394#1:750\n394#1:751,2\n397#1:753,3\n408#1:756,3\n412#1:759\n412#1:760,2\n413#1:762,4\n439#1:766\n439#1:767,2\n440#1:769,2\n444#1:771\n444#1:772,2\n445#1:774,3\n440#1:777\n458#1:778\n458#1:779,2\n459#1:781,4\n474#1:785\n474#1:786,2\n475#1:788\n475#1:789,2\n476#1:791,7\n544#1:798,2\n552#1:800,2\n560#1:802,4\n566#1:806,4\n567#1:810,4\n106#1:814\n106#1:815,2\n110#1:817\n110#1:818,2\n114#1:820,2\n126#1:822\n126#1:823,2\n128#1:825\n131#1:826\n131#1:827,2\n132#1:829,2\n128#1:831\n147#1:832,4\n*E\n"})
/* loaded from: classes5.dex */
public final class SelectKnowledgeNodeFragment extends BaseFragment implements OnItemChildClickListener {
    private CustomEmptyView emptyView;
    private ImageView ivCloseBack;

    @NotNull
    private final SelectKnowledgeNodeFragment$mAdapter$1 mAdapter;

    @NotNull
    private final ArrayList<ChapterNodeBean> mDataList;

    @NotNull
    private final ArrayMap<Integer, View> mViewMap;
    private Drawable nodeBottomBg;
    private Drawable nodeFullBg;
    private Drawable nodeMiddleBg;
    private Drawable nodeTopBg;
    private Drawable notSelectIcon;
    private Drawable partSelectIcon;
    private RecyclerView rvChapters;
    private boolean selectAll;
    private Drawable selectIcon;

    @NotNull
    private final ArrayList<String> selectedChapterIdList;

    @NotNull
    private final ArrayList<String> selectedNodeIdList;

    @NotNull
    private final ArrayList<String> selectedParentIdList;
    private boolean studyPlanSelect;
    private TextView title;

    @NotNull
    private String treeId;
    private TextView tvConfirm;
    private TextView tvSelectAll;

    @NotNull
    private String type;

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0002H\u0016¨\u0006\f"}, d2 = {"com/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$loadChapterData$1", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", "str", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nSelectKnowledgeNodeFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SelectKnowledgeNodeFragment.kt\ncom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$loadChapterData$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,723:1\n1855#2:724\n1855#2:725\n1855#2,2:726\n1856#2:728\n1856#2:729\n*S KotlinDebug\n*F\n+ 1 SelectKnowledgeNodeFragment.kt\ncom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$loadChapterData$1\n*L\n588#1:724\n595#1:725\n603#1:726,2\n595#1:728\n588#1:729\n*E\n"})
    /* renamed from: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$loadChapterData$1, reason: invalid class name */
    public static final class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onFailure$lambda$5(SelectKnowledgeNodeFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.studyPlanSelect) {
                this$0.loadData();
            } else {
                this$0.loadChapterData();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$3(SelectKnowledgeNodeFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Iterator it = this$0.mViewMap.entrySet().iterator();
            while (it.hasNext()) {
                Object key = ((Map.Entry) it.next()).getKey();
                Intrinsics.checkNotNullExpressionValue(key, "entry.key");
                this$0.clickItem(((Number) key).intValue());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$4(SelectKnowledgeNodeFragment this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.studyPlanSelect) {
                this$0.loadData();
            } else {
                this$0.loadChapterData();
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            CustomEmptyView customEmptyView = SelectKnowledgeNodeFragment.this.emptyView;
            CustomEmptyView customEmptyView2 = null;
            if (customEmptyView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView = null;
            }
            final SelectKnowledgeNodeFragment selectKnowledgeNodeFragment = SelectKnowledgeNodeFragment.this;
            customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.vc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SelectKnowledgeNodeFragment.AnonymousClass1.onFailure$lambda$5(selectKnowledgeNodeFragment, view);
                }
            });
            CustomEmptyView customEmptyView3 = SelectKnowledgeNodeFragment.this.emptyView;
            if (customEmptyView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView2 = customEmptyView3;
            }
            customEmptyView2.setIsShowReloadBtn(true, "点击重新加载");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            super.onSuccess((AnonymousClass1) str);
            CustomEmptyView customEmptyView = SelectKnowledgeNodeFragment.this.emptyView;
            CustomEmptyView customEmptyView2 = null;
            if (customEmptyView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView = null;
            }
            customEmptyView.stopAnim();
            CustomEmptyView customEmptyView3 = SelectKnowledgeNodeFragment.this.emptyView;
            if (customEmptyView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView3 = null;
            }
            ViewExtensionsKt.gone(customEmptyView3);
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (Intrinsics.areEqual("200", jSONObject.optString("code"))) {
                    SelectKnowledgeNodeFragment.this.mDataList.clear();
                    Iterable<NodeRedoBean> arrayList = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<? extends NodeRedoBean>>() { // from class: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$loadChapterData$1$onSuccess$dataList$1
                    }.getType());
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    SelectKnowledgeNodeFragment selectKnowledgeNodeFragment = SelectKnowledgeNodeFragment.this;
                    for (NodeRedoBean nodeRedoBean : arrayList) {
                        ChapterNodeBean chapterNodeBean = new ChapterNodeBean();
                        chapterNodeBean.setNodeLevel(1);
                        chapterNodeBean.setName(nodeRedoBean.getName());
                        chapterNodeBean.setId(nodeRedoBean.getId());
                        List<NodeRedoBean> children = nodeRedoBean.getChildren();
                        if (!(children == null || children.isEmpty())) {
                            selectKnowledgeNodeFragment.mDataList.add(chapterNodeBean);
                        }
                        List<NodeRedoBean> children2 = nodeRedoBean.getChildren();
                        if (children2 != null) {
                            Intrinsics.checkNotNullExpressionValue(children2, "children");
                            for (NodeRedoBean nodeRedoBean2 : children2) {
                                ChapterNodeBean chapterNodeBean2 = new ChapterNodeBean();
                                chapterNodeBean2.setParentId(nodeRedoBean.getId());
                                chapterNodeBean2.setNodeLevel(2);
                                chapterNodeBean2.setName(nodeRedoBean2.getName());
                                chapterNodeBean2.setId(nodeRedoBean2.getId());
                                List<NodeRedoBean> children3 = nodeRedoBean2.getChildren();
                                if (!(children3 == null || children3.isEmpty())) {
                                    selectKnowledgeNodeFragment.mDataList.add(chapterNodeBean2);
                                }
                                List<NodeRedoBean> children4 = nodeRedoBean2.getChildren();
                                if (children4 != null) {
                                    Intrinsics.checkNotNullExpressionValue(children4, "children");
                                    for (NodeRedoBean nodeRedoBean3 : children4) {
                                        ChapterNodeBean chapterNodeBean3 = new ChapterNodeBean();
                                        chapterNodeBean3.setNodeLevel(3);
                                        chapterNodeBean3.setParentId(nodeRedoBean2.getId());
                                        chapterNodeBean3.setName(nodeRedoBean3.getName());
                                        chapterNodeBean3.setId(nodeRedoBean3.getId());
                                        chapterNodeBean3.setChapter_id(nodeRedoBean.getId());
                                        selectKnowledgeNodeFragment.mDataList.add(chapterNodeBean3);
                                    }
                                }
                            }
                        }
                    }
                    setList(SelectKnowledgeNodeFragment.this.mDataList);
                    final SelectKnowledgeNodeFragment selectKnowledgeNodeFragment2 = SelectKnowledgeNodeFragment.this;
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.wc
                        @Override // java.lang.Runnable
                        public final void run() {
                            SelectKnowledgeNodeFragment.AnonymousClass1.onSuccess$lambda$3(selectKnowledgeNodeFragment2);
                        }
                    }, 300L);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                CustomEmptyView customEmptyView4 = SelectKnowledgeNodeFragment.this.emptyView;
                if (customEmptyView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                    customEmptyView4 = null;
                }
                final SelectKnowledgeNodeFragment selectKnowledgeNodeFragment3 = SelectKnowledgeNodeFragment.this;
                customEmptyView4.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.xc
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SelectKnowledgeNodeFragment.AnonymousClass1.onSuccess$lambda$4(selectKnowledgeNodeFragment3, view);
                    }
                });
                CustomEmptyView customEmptyView5 = SelectKnowledgeNodeFragment.this.emptyView;
                if (customEmptyView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                } else {
                    customEmptyView2 = customEmptyView5;
                }
                customEmptyView2.setIsShowReloadBtn(true, "点击重新加载");
            }
        }
    }

    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J$\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0002H\u0016¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$loadData$2", "Lnet/tsz/afinal/http/AjaxCallBack;", "", "onFailure", "", "t", "", "errorNo", "", "strMsg", "onSuccess", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nSelectKnowledgeNodeFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SelectKnowledgeNodeFragment.kt\ncom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$loadData$2\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,723:1\n1855#2:724\n1855#2:725\n1855#2,2:726\n1856#2:728\n1856#2:729\n*S KotlinDebug\n*F\n+ 1 SelectKnowledgeNodeFragment.kt\ncom/psychiatrygarden/fragmenthome/SelectKnowledgeNodeFragment$loadData$2\n*L\n658#1:724\n668#1:725\n679#1:726,2\n668#1:728\n658#1:729\n*E\n"})
    /* renamed from: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$loadData$2, reason: invalid class name */
    public static final class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onSuccess$lambda$3(SelectKnowledgeNodeFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Iterator it = this$0.mViewMap.entrySet().iterator();
            while (it.hasNext()) {
                Object key = ((Map.Entry) it.next()).getKey();
                Intrinsics.checkNotNullExpressionValue(key, "entry.key");
                this$0.clickItem(((Number) key).intValue());
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            CustomEmptyView customEmptyView = SelectKnowledgeNodeFragment.this.emptyView;
            CustomEmptyView customEmptyView2 = null;
            if (customEmptyView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView = null;
            }
            customEmptyView.stopAnim();
            CustomEmptyView customEmptyView3 = SelectKnowledgeNodeFragment.this.emptyView;
            if (customEmptyView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView2 = customEmptyView3;
            }
            ViewExtensionsKt.gone(customEmptyView2);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(@NotNull String t2) {
            String strOptString;
            Intrinsics.checkNotNullParameter(t2, "t");
            super.onSuccess((AnonymousClass2) t2);
            CustomEmptyView customEmptyView = SelectKnowledgeNodeFragment.this.emptyView;
            CustomEmptyView customEmptyView2 = null;
            if (customEmptyView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
                customEmptyView = null;
            }
            customEmptyView.stopAnim();
            CustomEmptyView customEmptyView3 = SelectKnowledgeNodeFragment.this.emptyView;
            if (customEmptyView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyView");
            } else {
                customEmptyView2 = customEmptyView3;
            }
            ViewExtensionsKt.gone(customEmptyView2);
            try {
                JSONObject jSONObject = new JSONObject(t2);
                if (!Intrinsics.areEqual("200", jSONObject.optString("code")) || (strOptString = jSONObject.optString("data")) == null) {
                    return;
                }
                List<KnowledgeNode> dataList = (List) new Gson().fromJson(strOptString, new TypeToken<List<? extends KnowledgeNode>>() { // from class: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$loadData$2$onSuccess$dataList$1
                }.getType());
                Intrinsics.checkNotNullExpressionValue(dataList, "dataList");
                SelectKnowledgeNodeFragment selectKnowledgeNodeFragment = SelectKnowledgeNodeFragment.this;
                for (KnowledgeNode knowledgeNode : dataList) {
                    ChapterNodeBean chapterNodeBean = new ChapterNodeBean();
                    chapterNodeBean.setId(knowledgeNode.getId());
                    chapterNodeBean.setName(knowledgeNode.getName());
                    chapterNodeBean.setExpand(false);
                    chapterNodeBean.setNodeLevel(1);
                    List<KnowledgeNode.KnowledgeNodeChild> children = knowledgeNode.getChildren();
                    if (!(children == null || children.isEmpty())) {
                        selectKnowledgeNodeFragment.mDataList.add(chapterNodeBean);
                        List<KnowledgeNode.KnowledgeNodeChild> children2 = knowledgeNode.getChildren();
                        Intrinsics.checkNotNullExpressionValue(children2, "it.children");
                        for (KnowledgeNode.KnowledgeNodeChild knowledgeNodeChild : children2) {
                            ChapterNodeBean chapterNodeBean2 = new ChapterNodeBean();
                            chapterNodeBean2.setId(knowledgeNodeChild.getId());
                            chapterNodeBean2.setName(knowledgeNodeChild.getName());
                            chapterNodeBean2.setParentId(knowledgeNode.getId());
                            chapterNodeBean2.setVisible(false);
                            chapterNodeBean2.setExpand(false);
                            chapterNodeBean2.setNodeLevel(2);
                            List<KnowledgeNode.KnowledgeNodeChild> children3 = knowledgeNodeChild.getChildren();
                            if (!(children3 == null || children3.isEmpty())) {
                                selectKnowledgeNodeFragment.mDataList.add(chapterNodeBean2);
                                List<KnowledgeNode.KnowledgeNodeChild> children4 = knowledgeNodeChild.getChildren();
                                Intrinsics.checkNotNullExpressionValue(children4, "chapterNode.children");
                                for (KnowledgeNode.KnowledgeNodeChild knowledgeNodeChild2 : children4) {
                                    ChapterNodeBean chapterNodeBean3 = new ChapterNodeBean();
                                    chapterNodeBean3.setId(knowledgeNodeChild2.getId());
                                    chapterNodeBean3.setVisible(false);
                                    chapterNodeBean3.setName(knowledgeNodeChild2.getName());
                                    chapterNodeBean3.setParentId(knowledgeNodeChild.getId());
                                    chapterNodeBean3.setChapter_id(chapterNodeBean2.getParentId());
                                    chapterNodeBean3.setNodeLevel(3);
                                    selectKnowledgeNodeFragment.mDataList.add(chapterNodeBean3);
                                }
                            }
                        }
                    }
                }
                setList(SelectKnowledgeNodeFragment.this.mDataList);
                final SelectKnowledgeNodeFragment selectKnowledgeNodeFragment2 = SelectKnowledgeNodeFragment.this;
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.yc
                    @Override // java.lang.Runnable
                    public final void run() {
                        SelectKnowledgeNodeFragment.AnonymousClass2.onSuccess$lambda$3(selectKnowledgeNodeFragment2);
                    }
                }, 300L);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$mAdapter$1] */
    public SelectKnowledgeNodeFragment() {
        final ArrayList<ChapterNodeBean> arrayList = new ArrayList<>();
        this.mDataList = arrayList;
        this.selectedParentIdList = new ArrayList<>();
        this.selectedChapterIdList = new ArrayList<>();
        this.selectedNodeIdList = new ArrayList<>();
        this.mViewMap = new ArrayMap<>();
        this.type = "";
        this.treeId = "";
        this.mAdapter = new BaseMultiItemQuickAdapter<ChapterNodeBean, BaseViewHolder>(arrayList) { // from class: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$mAdapter$1
            {
                addItemType(1, R.layout.item_select_node_parent);
                addItemType(2, R.layout.item_select_node_child);
                addItemType(3, R.layout.item_select_node_child_node);
                addChildClickViewIds(R.id.iv_select, R.id.iv_exp_col, R.id.tv_title);
            }

            private final Pair<Drawable, Boolean> getChildNodeBg(String parentId, String id) {
                int i2;
                Pair<Drawable, Boolean> pair;
                ArrayList arrayList2 = this.this$0.mDataList;
                int i3 = 0;
                if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
                    i2 = 0;
                } else {
                    Iterator it = arrayList2.iterator();
                    i2 = 0;
                    while (it.hasNext()) {
                        if (Intrinsics.areEqual(((ChapterNodeBean) it.next()).getParentId(), parentId) && (i2 = i2 + 1) < 0) {
                            CollectionsKt__CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                Drawable drawable = null;
                if (i2 == 1) {
                    Drawable drawable2 = this.this$0.nodeFullBg;
                    if (drawable2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("nodeFullBg");
                    } else {
                        drawable = drawable2;
                    }
                    return new Pair<>(drawable, Boolean.FALSE);
                }
                ArrayList arrayList3 = this.this$0.mDataList;
                ArrayList arrayList4 = new ArrayList();
                for (Object obj : arrayList3) {
                    if (Intrinsics.areEqual(((ChapterNodeBean) obj).getParentId(), parentId)) {
                        arrayList4.add(obj);
                    }
                }
                Iterator it2 = arrayList4.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        i3 = -1;
                        break;
                    }
                    if (Intrinsics.areEqual(((ChapterNodeBean) it2.next()).getId(), id)) {
                        break;
                    }
                    i3++;
                }
                if (i2 == 2) {
                    if (i3 == 0) {
                        Drawable drawable3 = this.this$0.nodeTopBg;
                        if (drawable3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("nodeTopBg");
                        } else {
                            drawable = drawable3;
                        }
                        pair = new Pair<>(drawable, Boolean.TRUE);
                    } else {
                        Drawable drawable4 = this.this$0.nodeBottomBg;
                        if (drawable4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("nodeBottomBg");
                        } else {
                            drawable = drawable4;
                        }
                        pair = new Pair<>(drawable, Boolean.FALSE);
                    }
                } else if (i3 == 0) {
                    Drawable drawable5 = this.this$0.nodeTopBg;
                    if (drawable5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("nodeTopBg");
                    } else {
                        drawable = drawable5;
                    }
                    pair = new Pair<>(drawable, Boolean.TRUE);
                } else if (i3 == arrayList4.size() - 1) {
                    Drawable drawable6 = this.this$0.nodeBottomBg;
                    if (drawable6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("nodeBottomBg");
                    } else {
                        drawable = drawable6;
                    }
                    pair = new Pair<>(drawable, Boolean.FALSE);
                } else {
                    Drawable drawable7 = this.this$0.nodeMiddleBg;
                    if (drawable7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("nodeMiddleBg");
                    } else {
                        drawable = drawable7;
                    }
                    pair = new Pair<>(drawable, Boolean.TRUE);
                }
                return pair;
            }

            private final void updateSelect(ChapterNodeBean item, ImageView ivSelect) {
                Drawable drawable = null;
                if (item.getNodeLevel() != 3) {
                    ivSelect.setSelected(item.isSelect());
                    if (!item.isSelect()) {
                        Drawable drawable2 = this.this$0.notSelectIcon;
                        if (drawable2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("notSelectIcon");
                        } else {
                            drawable = drawable2;
                        }
                        ivSelect.setImageDrawable(drawable);
                    } else if (item.isSelectAll()) {
                        Drawable drawable3 = this.this$0.selectIcon;
                        if (drawable3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("selectIcon");
                        } else {
                            drawable = drawable3;
                        }
                        ivSelect.setImageDrawable(drawable);
                    } else {
                        Drawable drawable4 = this.this$0.partSelectIcon;
                        if (drawable4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("partSelectIcon");
                        } else {
                            drawable = drawable4;
                        }
                        ivSelect.setImageDrawable(drawable);
                    }
                } else {
                    ivSelect.setSelected(item.isSelect());
                    if (item.isSelect()) {
                        Drawable drawable5 = this.this$0.selectIcon;
                        if (drawable5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("selectIcon");
                        } else {
                            drawable = drawable5;
                        }
                        ivSelect.setImageDrawable(drawable);
                    } else {
                        Drawable drawable6 = this.this$0.notSelectIcon;
                        if (drawable6 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("notSelectIcon");
                        } else {
                            drawable = drawable6;
                        }
                        ivSelect.setImageDrawable(drawable);
                    }
                }
                LogUtils.e("updateSelect", "title = " + item.getName() + " select = " + item.isSelect() + " selectAll = " + item.isSelectAll());
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, Object obj, List list) {
                convert(baseViewHolder, (ChapterNodeBean) obj, (List<? extends Object>) list);
            }

            public void convert(@NotNull BaseViewHolder holder, @NotNull ChapterNodeBean item, @NotNull List<? extends Object> payloads) {
                Context context;
                int i2;
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                Intrinsics.checkNotNullParameter(payloads, "payloads");
                super.convert((SelectKnowledgeNodeFragment$mAdapter$1) holder, (BaseViewHolder) item, payloads);
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                if (!item.isVisible() && item.getNodeLevel() != 1) {
                    layoutParams.height = 0;
                } else {
                    View view = holder.itemView;
                    Intrinsics.checkNotNullExpressionValue(view, "holder.itemView");
                    ViewExtensionsKt.visible(view);
                    if (item.getNodeLevel() != 3) {
                        context = ((BaseFragment) this.this$0).mContext;
                        i2 = 58;
                    } else {
                        context = ((BaseFragment) this.this$0).mContext;
                        i2 = 60;
                    }
                    layoutParams.height = SizeUtil.dp2px(context, i2);
                }
                holder.itemView.setLayoutParams(layoutParams);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder holder, @NotNull ChapterNodeBean item) {
                Context context;
                int i2;
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                holder.setText(R.id.tv_title, item.getName());
                holder.setIsRecyclable(false);
                ImageView imageView = (ImageView) holder.getView(R.id.iv_select);
                ImageView imageView2 = (ImageView) holder.getView(R.id.iv_exp_col);
                if (item.getNodeLevel() == 3) {
                    View view = holder.itemView;
                    String parentId = item.getParentId();
                    Intrinsics.checkNotNullExpressionValue(parentId, "item.parentId");
                    String id = item.getId();
                    Intrinsics.checkNotNullExpressionValue(id, "item.id");
                    view.setBackground(getChildNodeBg(parentId, id).getFirst());
                    String parentId2 = item.getParentId();
                    Intrinsics.checkNotNullExpressionValue(parentId2, "item.parentId");
                    String id2 = item.getId();
                    Intrinsics.checkNotNullExpressionValue(id2, "item.id");
                    holder.setVisible(R.id.line, getChildNodeBg(parentId2, id2).getSecond().booleanValue());
                    if (item.isSelect()) {
                        LogUtils.d("convert", item.getName());
                    }
                }
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                if (!item.isVisible() && item.getNodeLevel() != 1) {
                    layoutParams.height = 0;
                } else {
                    View view2 = holder.itemView;
                    Intrinsics.checkNotNullExpressionValue(view2, "holder.itemView");
                    ViewExtensionsKt.visible(view2);
                    if (item.getNodeLevel() != 3) {
                        context = ((BaseFragment) this.this$0).mContext;
                        i2 = 58;
                    } else {
                        context = ((BaseFragment) this.this$0).mContext;
                        i2 = 60;
                    }
                    layoutParams.height = SizeUtil.dp2px(context, i2);
                }
                holder.itemView.setLayoutParams(layoutParams);
                if (item.getNodeLevel() == 2) {
                    holder.setVisible(R.id.line, !item.isExpand());
                }
                if (item.getNodeLevel() != 3) {
                    if (item.isExpand()) {
                        if (SkinManager.getCurrentSkinType(((BaseFragment) this.this$0).mContext) == 1) {
                            imageView2.setImageDrawable(((BaseFragment) this.this$0).mContext.getDrawable(R.drawable.icon_top_arrow_night));
                        } else {
                            imageView2.setImageDrawable(((BaseFragment) this.this$0).mContext.getDrawable(R.drawable.icon_top_arrow_main_theme_color));
                        }
                    } else if (SkinManager.getCurrentSkinType(((BaseFragment) this.this$0).mContext) == 1) {
                        imageView2.setImageDrawable(((BaseFragment) this.this$0).mContext.getDrawable(R.drawable.icon_bottom_arrow_night));
                    } else {
                        imageView2.setImageDrawable(((BaseFragment) this.this$0).mContext.getDrawable(R.drawable.icon_bottom_arrow_day));
                    }
                }
                if (this.this$0.selectedNodeIdList.contains(item.getId())) {
                    this.this$0.mViewMap.put(Integer.valueOf(holder.getLayoutPosition()), holder.itemView);
                    this.this$0.selectedNodeIdList.remove(item.getId());
                }
                if (this.this$0.selectedChapterIdList.contains(item.getId())) {
                    this.this$0.mViewMap.put(Integer.valueOf(holder.getLayoutPosition()), holder.itemView);
                    this.this$0.selectedChapterIdList.remove(item.getId());
                }
                if (this.this$0.selectedParentIdList.contains(item.getId())) {
                    this.this$0.mViewMap.put(Integer.valueOf(holder.getLayoutPosition()), holder.itemView);
                    this.this$0.selectedParentIdList.remove(item.getId());
                }
                updateSelect(item, imageView);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void clickItem(int position) {
        int i2;
        int i3;
        Object next;
        ChapterNodeBean chapterNodeBean = (ChapterNodeBean) getItem(position);
        int nodeLevel = chapterNodeBean.getNodeLevel();
        if (nodeLevel != 1) {
            Object obj = null;
            if (nodeLevel != 2) {
                chapterNodeBean.setSelect(!chapterNodeBean.isSelect());
                notifyItemChanged(position);
                ArrayList<ChapterNodeBean> arrayList = this.mDataList;
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : arrayList) {
                    if (Intrinsics.areEqual(((ChapterNodeBean) obj2).getParentId(), chapterNodeBean.getParentId())) {
                        arrayList2.add(obj2);
                    }
                }
                if (arrayList2.isEmpty()) {
                    i3 = 0;
                } else {
                    Iterator it = arrayList2.iterator();
                    i3 = 0;
                    while (it.hasNext()) {
                        if (((ChapterNodeBean) it.next()).isSelect() && (i3 = i3 + 1) < 0) {
                            CollectionsKt__CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                Iterator<T> it2 = this.mDataList.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        next = it2.next();
                        if (Intrinsics.areEqual(((ChapterNodeBean) next).getId(), chapterNodeBean.getParentId())) {
                            break;
                        }
                    } else {
                        next = null;
                        break;
                    }
                }
                ChapterNodeBean chapterNodeBean2 = (ChapterNodeBean) next;
                if (chapterNodeBean2 == null) {
                    return;
                }
                if (i3 == 0) {
                    chapterNodeBean2.setSelect(false);
                } else if (arrayList2.size() == i3) {
                    chapterNodeBean2.setSelect(true);
                    chapterNodeBean2.setSelectAll(true);
                } else {
                    chapterNodeBean2.setSelect(true);
                    chapterNodeBean2.setSelectAll(false);
                }
                int iIndexOf = this.mDataList.indexOf(chapterNodeBean2);
                if (iIndexOf != -1) {
                    notifyItemChanged(iIndexOf);
                }
                ArrayList<ChapterNodeBean> arrayList3 = this.mDataList;
                ArrayList arrayList4 = new ArrayList();
                for (Object obj3 : arrayList3) {
                    ChapterNodeBean chapterNodeBean3 = (ChapterNodeBean) obj3;
                    if (Intrinsics.areEqual(chapterNodeBean3.getChapter_id(), chapterNodeBean.getChapter_id()) && chapterNodeBean3.isSelect()) {
                        arrayList4.add(obj3);
                    }
                }
                ArrayList<ChapterNodeBean> arrayList5 = this.mDataList;
                ArrayList arrayList6 = new ArrayList();
                for (Object obj4 : arrayList5) {
                    if (Intrinsics.areEqual(((ChapterNodeBean) obj4).getChapter_id(), chapterNodeBean.getChapter_id())) {
                        arrayList6.add(obj4);
                    }
                }
                Iterator<ChapterNodeBean> it3 = this.mDataList.iterator();
                int i4 = 0;
                while (true) {
                    if (!it3.hasNext()) {
                        i4 = -1;
                        break;
                    } else if (Intrinsics.areEqual(it3.next().getId(), chapterNodeBean.getChapter_id())) {
                        break;
                    } else {
                        i4++;
                    }
                }
                Iterator<T> it4 = this.mDataList.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        break;
                    }
                    Object next2 = it4.next();
                    if (Intrinsics.areEqual(((ChapterNodeBean) next2).getId(), chapterNodeBean.getChapter_id())) {
                        obj = next2;
                        break;
                    }
                }
                ChapterNodeBean chapterNodeBean4 = (ChapterNodeBean) obj;
                if (chapterNodeBean4 == null) {
                    return;
                }
                if (arrayList6.size() == arrayList4.size()) {
                    if (i4 != -1) {
                        chapterNodeBean4.setSelect(true);
                        chapterNodeBean4.setSelectAll(true);
                    }
                } else if (!arrayList4.isEmpty()) {
                    chapterNodeBean4.setSelect(true);
                    chapterNodeBean4.setSelectAll(false);
                } else {
                    chapterNodeBean4.setSelect(false);
                    chapterNodeBean4.setSelectAll(false);
                }
                notifyItemChanged(i4);
            } else {
                ArrayList<ChapterNodeBean> arrayList7 = this.mDataList;
                ArrayList arrayList8 = new ArrayList();
                for (Object obj5 : arrayList7) {
                    if (Intrinsics.areEqual(((ChapterNodeBean) obj5).getParentId(), chapterNodeBean.getId())) {
                        arrayList8.add(obj5);
                    }
                }
                if (!chapterNodeBean.isSelect() || chapterNodeBean.isSelectAll()) {
                    chapterNodeBean.setSelect(!chapterNodeBean.isSelect());
                    chapterNodeBean.setSelectAll(chapterNodeBean.isSelect());
                    notifyItemChanged(position, 999);
                    int i5 = 0;
                    for (Object obj6 : arrayList8) {
                        int i6 = i5 + 1;
                        if (i5 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        ((ChapterNodeBean) obj6).setSelect(chapterNodeBean.isSelect());
                        i5 = i6;
                    }
                } else {
                    int i7 = 0;
                    for (Object obj7 : arrayList8) {
                        int i8 = i7 + 1;
                        if (i7 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        ((ChapterNodeBean) obj7).setSelect(true);
                        i7 = i8;
                    }
                    chapterNodeBean.setSelect(true);
                    chapterNodeBean.setSelectAll(true);
                    notifyItemChanged(position);
                }
                ArrayList<ChapterNodeBean> arrayList9 = this.mDataList;
                ArrayList arrayList10 = new ArrayList();
                for (Object obj8 : arrayList9) {
                    ChapterNodeBean chapterNodeBean5 = (ChapterNodeBean) obj8;
                    if (Intrinsics.areEqual(chapterNodeBean.getParentId(), chapterNodeBean5.getParentId()) && chapterNodeBean5.getNodeLevel() == 2) {
                        arrayList10.add(obj8);
                    }
                }
                ArrayList<ChapterNodeBean> arrayList11 = this.mDataList;
                if ((arrayList11 instanceof Collection) && arrayList11.isEmpty()) {
                    i2 = 0;
                } else {
                    i2 = 0;
                    for (ChapterNodeBean chapterNodeBean6 : arrayList11) {
                        if ((Intrinsics.areEqual(chapterNodeBean.getParentId(), chapterNodeBean6.getParentId()) && chapterNodeBean6.getNodeLevel() == 2 && chapterNodeBean6.isSelect() && chapterNodeBean6.isSelectAll()) && (i2 = i2 + 1) < 0) {
                            CollectionsKt__CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                Iterator<T> it5 = this.mDataList.iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        break;
                    }
                    Object next3 = it5.next();
                    if (Intrinsics.areEqual(((ChapterNodeBean) next3).getId(), chapterNodeBean.getParentId())) {
                        obj = next3;
                        break;
                    }
                }
                ChapterNodeBean chapterNodeBean7 = (ChapterNodeBean) obj;
                if (chapterNodeBean7 != null) {
                    int iIndexOf2 = this.mDataList.indexOf(chapterNodeBean7);
                    if (arrayList10.size() == i2) {
                        chapterNodeBean7.setSelect(true);
                        chapterNodeBean7.setSelectAll(true);
                    } else if (i2 > 0) {
                        chapterNodeBean7.setSelect(true);
                        chapterNodeBean7.setSelectAll(false);
                        notifyItemChanged(iIndexOf2, 999);
                    } else if (i2 == 0) {
                        chapterNodeBean7.setSelect(false);
                        chapterNodeBean7.setSelectAll(false);
                    }
                    notifyItemChanged(iIndexOf2, 999);
                }
                notifyItemRangeChanged(position + 1, position + arrayList8.size(), 999);
            }
        } else {
            chapterNodeBean.setSelect(!chapterNodeBean.isSelect());
            chapterNodeBean.setSelectAll(chapterNodeBean.isSelect());
            notifyItemChanged(position);
            ArrayList<ChapterNodeBean> arrayList12 = this.mDataList;
            ArrayList arrayList13 = new ArrayList();
            for (Object obj9 : arrayList12) {
                if (Intrinsics.areEqual(((ChapterNodeBean) obj9).getParentId(), chapterNodeBean.getId())) {
                    arrayList13.add(obj9);
                }
            }
            int i9 = 0;
            for (Object obj10 : arrayList13) {
                int i10 = i9 + 1;
                if (i9 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                ChapterNodeBean chapterNodeBean8 = (ChapterNodeBean) obj10;
                chapterNodeBean8.setSelect(chapterNodeBean.isSelect());
                chapterNodeBean8.setSelectAll(chapterNodeBean.isSelect());
                notifyItemChanged(this.mDataList.indexOf(chapterNodeBean8), 999);
                ArrayList<ChapterNodeBean> arrayList14 = this.mDataList;
                ArrayList arrayList15 = new ArrayList();
                for (Object obj11 : arrayList14) {
                    if (Intrinsics.areEqual(((ChapterNodeBean) obj11).getParentId(), chapterNodeBean8.getId())) {
                        arrayList15.add(obj11);
                    }
                }
                int i11 = 0;
                for (Object obj12 : arrayList15) {
                    int i12 = i11 + 1;
                    if (i11 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    ChapterNodeBean chapterNodeBean9 = (ChapterNodeBean) obj12;
                    chapterNodeBean9.setSelect(chapterNodeBean8.isSelect());
                    notifyItemChanged(this.mDataList.indexOf(chapterNodeBean9), 999);
                    i11 = i12;
                }
                i9 = i10;
            }
        }
        updateTitle();
    }

    private final void initData() {
        RecyclerView recyclerView = this.rvChapters;
        TextView textView = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvChapters");
            recyclerView = null;
        }
        recyclerView.setHasFixedSize(true);
        RecyclerView recyclerView2 = this.rvChapters;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvChapters");
            recyclerView2 = null;
        }
        recyclerView2.setItemViewCacheSize(20);
        RecyclerView recyclerView3 = this.rvChapters;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvChapters");
            recyclerView3 = null;
        }
        SelectKnowledgeNodeFragment$mAdapter$1 selectKnowledgeNodeFragment$mAdapter$1 = this.mAdapter;
        selectKnowledgeNodeFragment$mAdapter$1.setList(this.mDataList);
        recyclerView3.setAdapter(selectKnowledgeNodeFragment$mAdapter$1);
        RecyclerView recyclerView4 = this.rvChapters;
        if (recyclerView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvChapters");
            recyclerView4 = null;
        }
        recyclerView4.setItemAnimator(null);
        setOnItemChildClickListener(this);
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.rc
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                SelectKnowledgeNodeFragment.initData$lambda$14(this.f15971c, baseQuickAdapter, view, i2);
            }
        });
        Bundle arguments = getArguments();
        this.studyPlanSelect = arguments != null ? arguments.getBoolean("studyPlanSelect") : false;
        Bundle arguments2 = getArguments();
        String string = arguments2 != null ? arguments2.getString(KnowledgeQuestionListFragment.EXTRA_TREE_ID) : null;
        if (string == null) {
            string = "";
        }
        this.treeId = string;
        Bundle arguments3 = getArguments();
        String string2 = arguments3 != null ? arguments3.getString("type") : null;
        if (string2 == null) {
            string2 = "";
        }
        this.type = string2;
        Bundle arguments4 = getArguments();
        if (arguments4 != null) {
            String string3 = arguments4.getString("part_ids");
            String str = string3 == null ? "" : string3;
            Intrinsics.checkNotNullExpressionValue(str, "it.getString(\"part_ids\") ?: \"\"");
            String string4 = arguments4.getString("chapter_ids");
            if (string4 == null) {
                string4 = "";
            }
            Intrinsics.checkNotNullExpressionValue(string4, "it.getString(\"chapter_ids\") ?: \"\"");
            String string5 = arguments4.getString("node_ids");
            if (string5 == null) {
                string5 = "";
            }
            Intrinsics.checkNotNullExpressionValue(string5, "it.getString(\"node_ids\") ?: \"\"");
            if (str.length() > 0) {
                if (StringsKt__StringsKt.contains$default((CharSequence) str, (CharSequence) ",", false, 2, (Object) null)) {
                    this.selectedParentIdList.addAll(StringsKt__StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null));
                } else {
                    this.selectedParentIdList.add(str);
                }
            }
            if (string4.length() > 0) {
                if (StringsKt__StringsKt.contains$default((CharSequence) string4, (CharSequence) ",", false, 2, (Object) null)) {
                    this.selectedChapterIdList.addAll(StringsKt__StringsKt.split$default((CharSequence) string4, new String[]{","}, false, 0, 6, (Object) null));
                } else {
                    this.selectedChapterIdList.add(string4);
                }
            }
            if (string5.length() > 0) {
                if (StringsKt__StringsKt.contains$default((CharSequence) string5, (CharSequence) ",", false, 2, (Object) null)) {
                    this.selectedNodeIdList.addAll(StringsKt__StringsKt.split$default((CharSequence) string5, new String[]{","}, false, 0, 6, (Object) null));
                } else {
                    this.selectedNodeIdList.add(string5);
                }
            }
        }
        if (this.studyPlanSelect) {
            TextView textView2 = this.tvConfirm;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvConfirm");
                textView2 = null;
            }
            textView2.setText("确定");
            loadData();
        } else if (!TextUtils.isEmpty(this.type) && !TextUtils.isEmpty(this.treeId)) {
            loadChapterData();
        }
        if (this.studyPlanSelect) {
            TextView textView3 = this.title;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("title");
            } else {
                textView = textView3;
            }
            textView.setText("章节选择");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void initData$lambda$14(SelectKnowledgeNodeFragment this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        if (((ChapterNodeBean) this$0.mAdapter.getItem(i2)).getNodeLevel() == 3) {
            this$0.clickItem(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$0(SelectKnowledgeNodeFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean z2 = !this$0.selectAll;
        this$0.selectAll = z2;
        if (z2) {
            this$0.selectAll();
        } else {
            this$0.unSelectAll();
        }
        TextView textView = this$0.tvSelectAll;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectAll");
            textView = null;
        }
        textView.setText(this$0.selectAll ? "取消全选" : "全选");
        this$0.updateTitle();
        this$0.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$1(SelectKnowledgeNodeFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity activity = this$0.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$12(SelectKnowledgeNodeFragment this$0, View view) throws IOException {
        Object next;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int i2 = 0;
        if (!this$0.studyPlanSelect) {
            ArrayList<ChapterNodeBean> arrayList = this$0.mDataList;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                ChapterNodeBean chapterNodeBean = (ChapterNodeBean) obj;
                if (chapterNodeBean.getNodeLevel() == 3 && chapterNodeBean.isSelect()) {
                    arrayList2.add(obj);
                }
            }
            EventBus.getDefault().post(new RedoMultiKnowledgeEvent(CollectionsKt___CollectionsKt.joinToString$default(arrayList2, ",", null, null, 0, null, new Function1<ChapterNodeBean, CharSequence>() { // from class: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$initViews$3$redoIds$2
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final CharSequence invoke(@NotNull ChapterNodeBean it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String id = it.getId();
                    Intrinsics.checkNotNullExpressionValue(id, "it.id");
                    return id;
                }
            }, 30, null)));
            FragmentActivity activity = this$0.getActivity();
            if (activity != null) {
                activity.finish();
                return;
            }
            return;
        }
        ArrayList<ChapterNodeBean> arrayList3 = this$0.mDataList;
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : arrayList3) {
            ChapterNodeBean chapterNodeBean2 = (ChapterNodeBean) obj2;
            if (chapterNodeBean2.getNodeLevel() == 1 && chapterNodeBean2.isSelectAll()) {
                arrayList4.add(obj2);
            }
        }
        String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList4, ",", null, null, 0, null, new Function1<ChapterNodeBean, CharSequence>() { // from class: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment$initViews$3$partIds$2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull ChapterNodeBean it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String id = it.getId();
                Intrinsics.checkNotNullExpressionValue(id, "it.id");
                return id;
            }
        }, 30, null);
        ArrayList arrayList5 = new ArrayList();
        Iterator<T> it = this$0.mDataList.iterator();
        while (true) {
            Object obj3 = null;
            if (!it.hasNext()) {
                break;
            }
            ChapterNodeBean chapterNodeBean3 = (ChapterNodeBean) it.next();
            if (chapterNodeBean3.getNodeLevel() == 2 && chapterNodeBean3.isSelect() && chapterNodeBean3.isSelectAll()) {
                Iterator<T> it2 = this$0.mDataList.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Object next2 = it2.next();
                    if (Intrinsics.areEqual(((ChapterNodeBean) next2).getId(), chapterNodeBean3.getParentId())) {
                        obj3 = next2;
                        break;
                    }
                }
                ChapterNodeBean chapterNodeBean4 = (ChapterNodeBean) obj3;
                if (chapterNodeBean4 != null && chapterNodeBean4.isSelect() && !chapterNodeBean4.isSelectAll()) {
                    arrayList5.add(chapterNodeBean3.getId());
                    LogUtils.d("study_plan_chapter_add", chapterNodeBean3.getName());
                }
            }
        }
        String strJoinToString$default2 = CollectionsKt___CollectionsKt.joinToString$default(arrayList5, ",", null, null, 0, null, null, 62, null);
        ArrayList<ChapterNodeBean> arrayList6 = this$0.mDataList;
        ArrayList<ChapterNodeBean> arrayList7 = new ArrayList();
        for (Object obj4 : arrayList6) {
            ChapterNodeBean chapterNodeBean5 = (ChapterNodeBean) obj4;
            if (chapterNodeBean5.getNodeLevel() == 2 && chapterNodeBean5.isSelect() && !chapterNodeBean5.isSelectAll()) {
                arrayList7.add(obj4);
            }
        }
        ArrayList arrayList8 = new ArrayList();
        for (ChapterNodeBean chapterNodeBean6 : arrayList7) {
            Iterator<T> it3 = this$0.mDataList.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    next = null;
                    break;
                }
                next = it3.next();
                ChapterNodeBean chapterNodeBean7 = (ChapterNodeBean) next;
                if (Intrinsics.areEqual(chapterNodeBean7.getId(), chapterNodeBean6.getParentId()) && chapterNodeBean7.isSelect() && !chapterNodeBean7.isSelectAll()) {
                    break;
                }
            }
            if (((ChapterNodeBean) next) != null) {
                ArrayList<ChapterNodeBean> arrayList9 = this$0.mDataList;
                ArrayList<ChapterNodeBean> arrayList10 = new ArrayList();
                for (Object obj5 : arrayList9) {
                    ChapterNodeBean chapterNodeBean8 = (ChapterNodeBean) obj5;
                    if (Intrinsics.areEqual(chapterNodeBean8.getParentId(), chapterNodeBean6.getId()) && chapterNodeBean8.isSelect()) {
                        arrayList10.add(obj5);
                    }
                }
                for (ChapterNodeBean chapterNodeBean9 : arrayList10) {
                    arrayList8.add(chapterNodeBean9.getId());
                    LogUtils.d("study_plan_node_add", chapterNodeBean9.getName());
                }
            }
        }
        String strJoinToString$default3 = CollectionsKt___CollectionsKt.joinToString$default(arrayList8, ",", null, null, 0, null, null, 62, null);
        LogUtils.d("study_plan", "全选的栏目id=" + strJoinToString$default + ",栏目下部分全选的章节id=" + strJoinToString$default2 + ",仅选择的节点id=" + strJoinToString$default3);
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(strJoinToString$default)) {
            intent.putExtra("part_ids", strJoinToString$default);
        }
        if (!TextUtils.isEmpty(strJoinToString$default2)) {
            intent.putExtra("chapter_ids", strJoinToString$default2);
        }
        if (!TextUtils.isEmpty(strJoinToString$default3)) {
            intent.putExtra("node_ids", strJoinToString$default3);
        }
        ArrayList<ChapterNodeBean> arrayList11 = this$0.mDataList;
        if (!(arrayList11 instanceof Collection) || !arrayList11.isEmpty()) {
            int i3 = 0;
            for (ChapterNodeBean chapterNodeBean10 : arrayList11) {
                if ((chapterNodeBean10.getNodeLevel() == 3 && chapterNodeBean10.isSelect()) && (i3 = i3 + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                }
            }
            i2 = i3;
        }
        intent.putExtra("selectNodeCount", String.valueOf(i2));
        FragmentActivity activity2 = this$0.getActivity();
        if (activity2 != null) {
            activity2.setResult(-1, intent);
        }
        FragmentActivity activity3 = this$0.getActivity();
        if (activity3 != null) {
            activity3.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadChapterData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", this.type);
        ajaxParams.put(KnowledgeQuestionListFragment.EXTRA_TREE_ID, this.treeId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.redoKnowledgeTree, ajaxParams, new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadData() {
        Context context = this.mContext;
        String str = NetworkRequestsURL.studyPlanNodeParams;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("field", "knowledge_tree");
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AnonymousClass2());
    }

    private final void selectAll() {
        for (ChapterNodeBean chapterNodeBean : this.mDataList) {
            chapterNodeBean.setSelectAll(chapterNodeBean.getNodeLevel() != 3);
            chapterNodeBean.setSelect(true);
        }
    }

    private final void unSelectAll() {
        for (ChapterNodeBean chapterNodeBean : this.mDataList) {
            chapterNodeBean.setSelectAll(false);
            chapterNodeBean.setSelect(false);
        }
    }

    private final void updateTitle() {
        int i2;
        int i3;
        int i4;
        ArrayList<ChapterNodeBean> arrayList = this.mDataList;
        if ((arrayList instanceof Collection) && arrayList.isEmpty()) {
            i2 = 0;
        } else {
            i2 = 0;
            for (ChapterNodeBean chapterNodeBean : arrayList) {
                if ((chapterNodeBean.isSelect() && chapterNodeBean.getNodeLevel() == 3) && (i2 = i2 + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                }
            }
        }
        TextView textView = null;
        if (this.studyPlanSelect) {
            TextView textView2 = this.title;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("title");
                textView2 = null;
            }
            textView2.setText("已选择" + i2 + (char) 33410);
        } else {
            TextView textView3 = this.title;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("title");
                textView3 = null;
            }
            textView3.setText("已选择" + i2 + "个考点");
        }
        TextView textView4 = this.tvConfirm;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvConfirm");
            textView4 = null;
        }
        textView4.setEnabled(i2 > 0);
        ArrayList<ChapterNodeBean> arrayList2 = this.mDataList;
        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
            i3 = 0;
        } else {
            Iterator<T> it = arrayList2.iterator();
            i3 = 0;
            while (it.hasNext()) {
                if ((((ChapterNodeBean) it.next()).getNodeLevel() == 3) && (i3 = i3 + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                }
            }
        }
        ArrayList<ChapterNodeBean> arrayList3 = this.mDataList;
        if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
            i4 = 0;
        } else {
            i4 = 0;
            for (ChapterNodeBean chapterNodeBean2 : arrayList3) {
                if ((chapterNodeBean2.getNodeLevel() == 3 && chapterNodeBean2.isSelect()) && (i4 = i4 + 1) < 0) {
                    CollectionsKt__CollectionsKt.throwCountOverflow();
                }
            }
        }
        this.selectAll = i3 == i4;
        TextView textView5 = this.tvSelectAll;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectAll");
        } else {
            textView = textView5;
        }
        textView.setText(this.selectAll ? "取消全选" : "全选");
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_select_knowledge_node;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        View view = holder.get(R.id.tv_confirm);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.tv_confirm)");
        this.tvConfirm = (TextView) view;
        View view2 = holder.get(R.id.iv_close_back);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.iv_close_back)");
        this.ivCloseBack = (ImageView) view2;
        View view3 = holder.get(R.id.title);
        Intrinsics.checkNotNullExpressionValue(view3, "holder.get(R.id.title)");
        this.title = (TextView) view3;
        View view4 = holder.get(R.id.tv_select_all);
        Intrinsics.checkNotNullExpressionValue(view4, "holder.get(R.id.tv_select_all)");
        this.tvSelectAll = (TextView) view4;
        View view5 = holder.get(R.id.rvChapters);
        Intrinsics.checkNotNullExpressionValue(view5, "holder.get(R.id.rvChapters)");
        this.rvChapters = (RecyclerView) view5;
        View view6 = holder.get(R.id.empty_view);
        Intrinsics.checkNotNullExpressionValue(view6, "holder.get(R.id.empty_view)");
        this.emptyView = (CustomEmptyView) view6;
        TypedArray typedArrayObtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(new int[]{R.attr.download_new_not_select, R.attr.download_select});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "mContext.theme.obtainSty… R.attr.download_select))");
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Intrinsics.checkNotNull(drawable);
        this.notSelectIcon = drawable;
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        Intrinsics.checkNotNull(drawable2);
        this.selectIcon = drawable2;
        Drawable drawable3 = this.mContext.getDrawable(R.drawable.download_part_select);
        Intrinsics.checkNotNull(drawable3);
        this.partSelectIcon = drawable3;
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            Drawable drawable4 = this.mContext.getDrawable(R.drawable.download_part_select_night);
            Intrinsics.checkNotNull(drawable4);
            this.partSelectIcon = drawable4;
        }
        Drawable drawable5 = this.mContext.getDrawable(R.drawable.bg_course_download_child_top);
        Intrinsics.checkNotNull(drawable5);
        this.nodeTopBg = drawable5;
        Drawable drawable6 = this.mContext.getDrawable(R.drawable.bg_course_download_child_middle);
        Intrinsics.checkNotNull(drawable6);
        this.nodeMiddleBg = drawable6;
        Drawable drawable7 = this.mContext.getDrawable(R.drawable.bg_course_download_child_bottom);
        Intrinsics.checkNotNull(drawable7);
        this.nodeBottomBg = drawable7;
        Drawable drawable8 = this.mContext.getDrawable(R.drawable.bg_course_download_node_3);
        Intrinsics.checkNotNull(drawable8);
        this.nodeFullBg = drawable8;
        typedArrayObtainStyledAttributes.recycle();
        TextView textView = this.tvSelectAll;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectAll");
            textView = null;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.sc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view7) {
                SelectKnowledgeNodeFragment.initViews$lambda$0(this.f15989c, view7);
            }
        });
        ImageView imageView = this.ivCloseBack;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivCloseBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.tc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view7) {
                SelectKnowledgeNodeFragment.initViews$lambda$1(this.f16015c, view7);
            }
        });
        TextView textView3 = this.tvConfirm;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvConfirm");
        } else {
            textView2 = textView3;
        }
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.uc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view7) throws IOException {
                SelectKnowledgeNodeFragment.initViews$lambda$12(this.f16048c, view7);
            }
        });
        initData();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:65:0x013e  */
    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onItemChildClick(@org.jetbrains.annotations.NotNull com.chad.library.adapter.base.BaseQuickAdapter<?, ?> r8, @org.jetbrains.annotations.NotNull android.view.View r9, int r10) {
        /*
            Method dump skipped, instructions count: 516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.SelectKnowledgeNodeFragment.onItemChildClick(com.chad.library.adapter.base.BaseQuickAdapter, android.view.View, int):void");
    }
}
