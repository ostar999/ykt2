package com.psychiatrygarden.fragmenthome.knowledge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment;
import com.psychiatrygarden.utils.LogUtils;
import com.yikaobang.yixue.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 &2\u00020\u00012\u00020\u0002:\u0001&B\u0005¢\u0006\u0002\u0010\u0003J&\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0014\u0010\u001a\u001a\u00020\u00192\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0012\u0010\u001e\u001a\u00020\u00192\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\u0012\u0010!\u001a\u00020\u00192\b\u0010\"\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010#\u001a\u00020\u0019H\u0002J\b\u0010$\u001a\u00020\u0019H\u0016J\b\u0010%\u001a\u00020\u0019H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/knowledge/KnowledgeListEditZuTiActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Landroid/view/View$OnClickListener;", "()V", "ALL_SELECT", "", "CANCEL_ALL_SELECT", "baseQuestionBankRedo", "", "childFragment", "Lcom/psychiatrygarden/fragmenthome/knowledge/KnowledgeListEditZuTiFragment;", "downTv", "Landroid/widget/TextView;", "selectList", "selectListStr", "studyPlanSelect", "treeId", "tvExportAllSelect", "tvExportTitle", "convertData", "Lkotlin/Triple;", "dataList", "", "Lcom/psychiatrygarden/bean/KnowledgeChartNodeBean;", "init", "", "initSelectedData", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "selectAll", "setContentView", "setListenerForWidget", "Companion", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nKnowledgeListEditZuTiActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KnowledgeListEditZuTiActivity.kt\ncom/psychiatrygarden/fragmenthome/knowledge/KnowledgeListEditZuTiActivity\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,217:1\n766#2:218\n857#2,2:219\n1855#2:221\n1856#2:223\n766#2:224\n857#2,2:225\n1855#2:227\n766#2:228\n857#2,2:229\n1855#2,2:231\n1856#2:233\n1855#2,2:234\n1855#2:236\n1726#2,3:237\n1774#2,4:240\n1726#2,3:244\n1774#2,4:247\n1856#2:251\n1#3:222\n*S KotlinDebug\n*F\n+ 1 KnowledgeListEditZuTiActivity.kt\ncom/psychiatrygarden/fragmenthome/knowledge/KnowledgeListEditZuTiActivity\n*L\n65#1:218\n65#1:219,2\n69#1:221\n69#1:223\n84#1:224\n84#1:225,2\n86#1:227\n89#1:228\n89#1:229,2\n90#1:231,2\n86#1:233\n107#1:234,2\n115#1:236\n117#1:237,3\n118#1:240,4\n122#1:244,3\n123#1:247,4\n115#1:251\n*E\n"})
/* loaded from: classes5.dex */
public final class KnowledgeListEditZuTiActivity extends BaseActivity implements View.OnClickListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private final String ALL_SELECT = "全选";

    @NotNull
    private final String CANCEL_ALL_SELECT = "取消全选";
    private boolean baseQuestionBankRedo;

    @Nullable
    private KnowledgeListEditZuTiFragment childFragment;
    private TextView downTv;

    @Nullable
    private String selectList;

    @Nullable
    private String selectListStr;
    private boolean studyPlanSelect;

    @Nullable
    private String treeId;
    private TextView tvExportAllSelect;
    private TextView tvExportTitle;

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b¨\u0006\n"}, d2 = {"Lcom/psychiatrygarden/fragmenthome/knowledge/KnowledgeListEditZuTiActivity$Companion;", "", "()V", "gotToEditKnowledgeZuTi", "", com.umeng.analytics.pro.d.R, "Landroid/app/Activity;", "treeId", "", "strings", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void gotToEditKnowledgeZuTi(@NotNull Activity context, @NotNull String treeId, @NotNull String strings) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(treeId, "treeId");
            Intrinsics.checkNotNullParameter(strings, "strings");
            Intent intent = new Intent(context, (Class<?>) KnowledgeListEditZuTiActivity.class);
            intent.putExtra("treeId", treeId);
            intent.putExtra("listData", strings);
            context.startActivityForResult(intent, 1001);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$14(KnowledgeListEditZuTiActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$15(KnowledgeListEditZuTiActivity this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = this$0.childFragment;
        Intrinsics.checkNotNull(knowledgeListEditZuTiFragment);
        boolean z2 = i2 < knowledgeListEditZuTiFragment.getNoteAllCount();
        TextView textView = this$0.tvExportAllSelect;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvExportAllSelect");
            textView = null;
        }
        textView.setText(z2 ? this$0.ALL_SELECT : this$0.CANCEL_ALL_SELECT);
        if (i2 == 0) {
            TextView textView3 = this$0.tvExportTitle;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvExportTitle");
                textView3 = null;
            }
            textView3.setText("考点选择");
            if (this$0.studyPlanSelect || this$0.baseQuestionBankRedo) {
                TextView textView4 = this$0.tvExportTitle;
                if (textView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvExportTitle");
                    textView4 = null;
                }
                textView4.setText("章节选择");
            }
        } else {
            TextView textView5 = this$0.tvExportTitle;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvExportTitle");
                textView5 = null;
            }
            textView5.setText("已选择 " + i2 + " 个考点");
            if (this$0.studyPlanSelect || this$0.baseQuestionBankRedo) {
                TextView textView6 = this$0.tvExportTitle;
                if (textView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvExportTitle");
                    textView6 = null;
                }
                textView6.setText("已选择" + i2 + "个章节");
            }
        }
        TextView textView7 = this$0.downTv;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("downTv");
        } else {
            textView2 = textView7;
        }
        textView2.setEnabled(i2 > 0);
    }

    private final void selectAll() {
        if (this.childFragment != null) {
            TextView textView = this.tvExportAllSelect;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvExportAllSelect");
                textView = null;
            }
            if (Intrinsics.areEqual(this.ALL_SELECT, textView.getText().toString())) {
                KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = this.childFragment;
                Intrinsics.checkNotNull(knowledgeListEditZuTiFragment);
                knowledgeListEditZuTiFragment.selectOperaAll(Boolean.TRUE);
            } else {
                KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment2 = this.childFragment;
                Intrinsics.checkNotNull(knowledgeListEditZuTiFragment2);
                knowledgeListEditZuTiFragment2.selectOperaAll(Boolean.FALSE);
            }
        }
    }

    @NotNull
    public final Triple<String, String, String> convertData(@NotNull List<? extends KnowledgeChartNodeBean> dataList) throws IOException {
        Object next;
        Intrinsics.checkNotNullParameter(dataList, "dataList");
        List<? extends KnowledgeChartNodeBean> list = dataList;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next2 = it.next();
            KnowledgeChartNodeBean knowledgeChartNodeBean = (KnowledgeChartNodeBean) next2;
            if (knowledgeChartNodeBean.getLevel() == 1 && knowledgeChartNodeBean.isSelectAll()) {
                z = true;
            }
            if (z) {
                arrayList.add(next2);
            }
        }
        String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, 0, null, new Function1<KnowledgeChartNodeBean, CharSequence>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiActivity$convertData$partIds$2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull KnowledgeChartNodeBean it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                String id = it2.getId();
                Intrinsics.checkNotNullExpressionValue(id, "it.id");
                return id;
            }
        }, 30, null);
        if (strJoinToString$default == null || strJoinToString$default.length() == 0) {
            strJoinToString$default = "";
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<T> it2 = list.iterator();
        while (true) {
            Object obj = null;
            if (!it2.hasNext()) {
                break;
            }
            KnowledgeChartNodeBean knowledgeChartNodeBean2 = (KnowledgeChartNodeBean) it2.next();
            if (knowledgeChartNodeBean2.getLevel() == 2 && knowledgeChartNodeBean2.isChildSelect() && knowledgeChartNodeBean2.isSelectAll()) {
                Iterator<T> it3 = list.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    Object next3 = it3.next();
                    if (Intrinsics.areEqual(((KnowledgeChartNodeBean) next3).getId(), knowledgeChartNodeBean2.getParentId())) {
                        obj = next3;
                        break;
                    }
                }
                KnowledgeChartNodeBean knowledgeChartNodeBean3 = (KnowledgeChartNodeBean) obj;
                if (knowledgeChartNodeBean3 != null && knowledgeChartNodeBean3.isChildSelect() && !knowledgeChartNodeBean3.isSelectAll()) {
                    arrayList2.add(knowledgeChartNodeBean2.getId());
                    LogUtils.d("study_plan_chapter_add", knowledgeChartNodeBean2.getName());
                }
            }
        }
        String strJoinToString$default2 = CollectionsKt___CollectionsKt.joinToString$default(arrayList2, ",", null, null, 0, null, null, 62, null);
        if (strJoinToString$default2 == null || strJoinToString$default2.length() == 0) {
            strJoinToString$default2 = "";
        }
        ArrayList<KnowledgeChartNodeBean> arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            KnowledgeChartNodeBean knowledgeChartNodeBean4 = (KnowledgeChartNodeBean) obj2;
            if (knowledgeChartNodeBean4.getLevel() == 2 && knowledgeChartNodeBean4.isChildSelect() && !knowledgeChartNodeBean4.isSelectAll()) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (KnowledgeChartNodeBean knowledgeChartNodeBean5 : arrayList3) {
            Iterator<T> it4 = list.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    next = null;
                    break;
                }
                next = it4.next();
                KnowledgeChartNodeBean knowledgeChartNodeBean6 = (KnowledgeChartNodeBean) next;
                if (Intrinsics.areEqual(knowledgeChartNodeBean6.getId(), knowledgeChartNodeBean5.getParentId()) && knowledgeChartNodeBean6.isChildSelect() && !knowledgeChartNodeBean6.isSelectAll()) {
                    break;
                }
            }
            if (((KnowledgeChartNodeBean) next) != null) {
                ArrayList<KnowledgeChartNodeBean> arrayList5 = new ArrayList();
                for (Object obj3 : list) {
                    KnowledgeChartNodeBean knowledgeChartNodeBean7 = (KnowledgeChartNodeBean) obj3;
                    if (Intrinsics.areEqual(knowledgeChartNodeBean7.getParentId(), knowledgeChartNodeBean5.getId()) && knowledgeChartNodeBean7.isSelect()) {
                        arrayList5.add(obj3);
                    }
                }
                for (KnowledgeChartNodeBean knowledgeChartNodeBean8 : arrayList5) {
                    arrayList4.add(knowledgeChartNodeBean8.getId());
                    LogUtils.d("study_plan_node_add", knowledgeChartNodeBean8.getName());
                }
            }
        }
        String strJoinToString$default3 = CollectionsKt___CollectionsKt.joinToString$default(arrayList4, ",", null, null, 0, null, null, 62, null);
        String str = strJoinToString$default3 == null || strJoinToString$default3.length() == 0 ? "" : strJoinToString$default3;
        LogUtils.d("study_plan", "全选的栏目id=" + strJoinToString$default + ",栏目下部分全选的章节id=" + strJoinToString$default2 + ",仅选择的节点id=" + str);
        return new Triple<>(strJoinToString$default, strJoinToString$default2, str);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Intent intent = getIntent();
        if (intent != null) {
            this.treeId = intent.getStringExtra("treeId");
            this.selectList = intent.getStringExtra("listData");
            boolean booleanExtra = intent.getBooleanExtra("studyPlanSelect", false);
            this.studyPlanSelect = booleanExtra;
            if (booleanExtra) {
                this.selectList = intent.getStringExtra("selectChildIdList");
            }
            this.baseQuestionBankRedo = getIntent().getBooleanExtra("baseQuestionBankRedo", false);
        }
        String stringExtra = intent.getStringExtra("type");
        ImageView imageView = (ImageView) findViewById(R.id.iv_knowledge_edit_back);
        View viewFindViewById = findViewById(R.id.tv_export_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv_export_title)");
        this.tvExportTitle = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.downTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.downTv)");
        this.downTv = (TextView) viewFindViewById2;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeListEditZuTiActivity.init$lambda$14(this.f15759c, view);
            }
        });
        View viewFindViewById3 = findViewById(R.id.tv_export_all_select);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_export_all_select)");
        TextView textView = (TextView) viewFindViewById3;
        this.tvExportAllSelect = textView;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvExportAllSelect");
            textView = null;
        }
        textView.setOnClickListener(this);
        TextView textView3 = this.downTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("downTv");
        } else {
            textView2 = textView3;
        }
        textView2.setOnClickListener(this);
        this.childFragment = new KnowledgeListEditZuTiFragment();
        if (this.studyPlanSelect && !TextUtils.isEmpty(this.selectList)) {
            this.selectListStr = intent.getStringExtra("selectChildIdList");
            Gson gson = new Gson();
            String str = this.selectList;
            Intrinsics.checkNotNull(str);
            this.selectList = gson.toJson(StringsKt__StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null));
        }
        KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = this.childFragment;
        Intrinsics.checkNotNull(knowledgeListEditZuTiFragment);
        Bundle bundle = knowledgeListEditZuTiFragment.getBundle(this.treeId, this.selectList);
        if (!TextUtils.isEmpty(stringExtra)) {
            bundle.putString("type", stringExtra);
        }
        bundle.putBoolean("studyPlanSelect", this.studyPlanSelect);
        if (this.baseQuestionBankRedo) {
            bundle.putAll(intent.getExtras());
        }
        KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment2 = this.childFragment;
        Intrinsics.checkNotNull(knowledgeListEditZuTiFragment2);
        knowledgeListEditZuTiFragment2.setArguments(bundle);
        if (findFragment(KnowledgeListEditZuTiFragment.class) == null) {
            KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment3 = this.childFragment;
            Intrinsics.checkNotNull(knowledgeListEditZuTiFragment3);
            loadRootFragment(R.id.fragmentKnowledgeEdit, knowledgeListEditZuTiFragment3);
        } else {
            replaceFragment(this.childFragment, false);
        }
        KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment4 = this.childFragment;
        Intrinsics.checkNotNull(knowledgeListEditZuTiFragment4);
        knowledgeListEditZuTiFragment4.setSelectNumChangeListener(new KnowledgeListEditZuTiFragment.SelectNumChangeListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.k
            @Override // com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.SelectNumChangeListener
            public final void selectNum(int i2) {
                KnowledgeListEditZuTiActivity.init$lambda$15(this.f15761a, i2);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0158  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initSelectedData(@org.jetbrains.annotations.NotNull java.util.List<? extends com.psychiatrygarden.bean.KnowledgeChartNodeBean> r12) {
        /*
            Method dump skipped, instructions count: 405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiActivity.initSelectedData(java.util.List):void");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@NotNull View v2) {
        Intrinsics.checkNotNullParameter(v2, "v");
        int id = v2.getId();
        if (id != R.id.downTv) {
            if (id != R.id.tv_export_all_select) {
                return;
            }
            selectAll();
        } else {
            KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = this.childFragment;
            Intrinsics.checkNotNull(knowledgeListEditZuTiFragment);
            knowledgeListEditZuTiFragment.btnCommit();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_knowlegde_list_edit_zu_ti);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
