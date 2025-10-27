package com.psychiatrygarden.activity.courselist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0014J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u0012\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0011H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/psychiatrygarden/activity/courselist/CourseDownloadFmt;", "Lcom/psychiatrygarden/baseview/BaseFragment;", "Landroid/view/View$OnClickListener;", "()V", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "selectAll", "", "tvEdit", "Landroid/widget/TextView;", "getLayoutId", "", "initViews", "", "holder", "Lcom/psychiatrygarden/baseview/ViewHolder;", "root", "Landroid/view/View;", "onClick", "v", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseDownloadFmt extends BaseFragment implements View.OnClickListener {
    private RecyclerView rvList;
    private boolean selectAll;
    private TextView tvEdit;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initViews$lambda$0(CourseDownloadFmt this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity activity = this$0.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fmt_course_download;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(@NotNull ViewHolder holder, @Nullable View root) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        ((ImageView) holder.get(R.id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseDownloadFmt.initViews$lambda$0(this.f12138c, view);
            }
        });
        View view = holder.get(R.id.tv_edit);
        Intrinsics.checkNotNullExpressionValue(view, "holder.get(R.id.tv_edit)");
        TextView textView = (TextView) view;
        this.tvEdit = textView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvEdit");
            textView = null;
        }
        textView.setOnClickListener(this);
        View view2 = holder.get(R.id.rvList);
        Intrinsics.checkNotNullExpressionValue(view2, "holder.get(R.id.rvList)");
        this.rvList = (RecyclerView) view2;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@Nullable View v2) {
        boolean z2 = false;
        if (v2 != null && v2.getId() == R.id.tv_edit) {
            z2 = true;
        }
        if (z2) {
            this.selectAll = !this.selectAll;
            TextView textView = this.tvEdit;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvEdit");
                textView = null;
            }
            textView.setText(this.selectAll ? "取消全选" : "全选");
        }
    }
}
