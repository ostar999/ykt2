package com.yddmi.doctor.pages.me;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.catchpig.mvvm.ext.StringExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.entity.result.SkillHistory;
import com.yddmi.doctor.entity.result.SkillHome;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0016H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/yddmi/doctor/pages/me/PopupHistory;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "m", "Lcom/yddmi/doctor/entity/result/SkillHome;", "exam", "", "(Landroid/content/Context;Lcom/yddmi/doctor/entity/result/SkillHome;Z)V", "adapterPopHistory", "Lcom/yddmi/doctor/pages/me/AdapterPopHistory;", "mData", "mExam", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "timeTipTv", "Landroid/widget/TextView;", "timeTv", "tipTv", "getImplLayoutId", "", "onCreate", "", "viewDataShow", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupHistory extends CenterPopupView {
    private AdapterPopHistory adapterPopHistory;

    @NotNull
    private final SkillHome mData;
    private final boolean mExam;
    private RecyclerView rv;
    private TextView timeTipTv;
    private TextView timeTv;
    private TextView tipTv;

    public /* synthetic */ PopupHistory(Context context, SkillHome skillHome, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, skillHome, (i2 & 4) != 0 ? true : z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupHistory this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    private final void viewDataShow() {
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.me_popup_history;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.adapterPopHistory = new AdapterPopHistory();
        View viewFindViewById = findViewById(R.id.closeTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<TextView>(R.id.closeTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupHistory.onCreate$lambda$0(this.f25977c, view);
            }
        }, 0L, 2, null);
        ((TextView) findViewById(R.id.scoreTv)).setText(String.valueOf(StringExtKt.get2EffectiveNum(this.mData.getScore())));
        View viewFindViewById2 = findViewById(R.id.timeTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.timeTv)");
        this.timeTv = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tipTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tipTv)");
        this.tipTv = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.timeTipTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.timeTipTv)");
        this.timeTipTv = (TextView) viewFindViewById4;
        AdapterPopHistory adapterPopHistory = null;
        if (this.mExam) {
            TextView textView = this.tipTv;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                textView = null;
            }
            textView.setText(getContext().getString(R.string.me_exam_scores));
            TextView textView2 = this.timeTipTv;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("timeTipTv");
                textView2 = null;
            }
            textView2.setText(getContext().getString(R.string.me_exam_time));
        } else {
            TextView textView3 = this.tipTv;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                textView3 = null;
            }
            textView3.setText(getContext().getString(R.string.me_train_scores));
            TextView textView4 = this.timeTipTv;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("timeTipTv");
                textView4 = null;
            }
            textView4.setText(getContext().getString(R.string.me_train_time));
        }
        if (this.mData.getPracticeTime() == null) {
            TextView textView5 = this.timeTv;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("timeTv");
                textView5 = null;
            }
            textView5.setText(DateUtil.second2Time1(-1));
        } else {
            TextView textView6 = this.timeTv;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("timeTv");
                textView6 = null;
            }
            textView6.setText(DateUtil.second2Time1(Integer.parseInt(this.mData.getPracticeTime())));
        }
        View viewFindViewById5 = findViewById(R.id.rv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.rv)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById5;
        this.rv = recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rv");
            recyclerView = null;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(1);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterPopHistory adapterPopHistory2 = this.adapterPopHistory;
        if (adapterPopHistory2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterPopHistory");
            adapterPopHistory2 = null;
        }
        recyclerView.setAdapter(adapterPopHistory2);
        List<SkillHistory> mContentList = this.mData.getMContentList();
        LogExtKt.logd("数据大小" + (mContentList != null ? Integer.valueOf(mContentList.size()) : null), YddConfig.TAG);
        AdapterPopHistory adapterPopHistory3 = this.adapterPopHistory;
        if (adapterPopHistory3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterPopHistory");
        } else {
            adapterPopHistory = adapterPopHistory3;
        }
        adapterPopHistory.set(this.mData.getMContentList());
        viewDataShow();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupHistory(@NonNull @NotNull Context context, @NotNull SkillHome m2, boolean z2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(m2, "m");
        this.mData = m2;
        this.mExam = z2;
    }
}
