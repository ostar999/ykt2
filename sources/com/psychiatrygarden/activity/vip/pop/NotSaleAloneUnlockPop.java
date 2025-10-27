package com.psychiatrygarden.activity.vip.pop;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.bean.ParentCourseBean;
import com.yikaobang.yixue.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000/\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u000b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0010H\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/activity/vip/pop/NotSaleAloneUnlockPop;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "data", "", "Lcom/psychiatrygarden/bean/ParentCourseBean;", "(Landroid/content/Context;Ljava/util/List;)V", "getData", "()Ljava/util/List;", "mAdapter", "com/psychiatrygarden/activity/vip/pop/NotSaleAloneUnlockPop$mAdapter$1", "Lcom/psychiatrygarden/activity/vip/pop/NotSaleAloneUnlockPop$mAdapter$1;", "getImplLayoutId", "", "onCreate", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class NotSaleAloneUnlockPop extends BottomPopupView {

    @NotNull
    private final List<ParentCourseBean> data;

    @NotNull
    private final NotSaleAloneUnlockPop$mAdapter$1 mAdapter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotSaleAloneUnlockPop(@NotNull Context context, @NotNull List<ParentCourseBean> data) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        this.mAdapter = new NotSaleAloneUnlockPop$mAdapter$1(context, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(NotSaleAloneUnlockPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @NotNull
    public final List<ParentCourseBean> getData() {
        return this.data;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_course_not_sale_alone_unlock;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvWays);
        findViewById(R.id.cancelTv).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotSaleAloneUnlockPop.onCreate$lambda$0(this.f14112c, view);
            }
        });
        this.mAdapter.addChildClickViewIds(R.id.tv_to_lock);
        recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setList(this.data);
    }
}
