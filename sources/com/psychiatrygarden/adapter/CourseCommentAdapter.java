package com.psychiatrygarden.adapter;

import android.util.ArrayMap;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.purchase.beans.CourseCommentBean;
import com.psychiatrygarden.bean.MoreContentCache;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0002H\u0014R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/adapter/CourseCommentAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/activity/purchase/beans/CourseCommentBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "contentMap", "Landroid/util/ArrayMap;", "", "Lcom/psychiatrygarden/bean/MoreContentCache;", "expColMap", "", "lineCountMap", "lineIndexMap", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseCommentAdapter extends BaseQuickAdapter<CourseCommentBean, BaseViewHolder> {

    @NotNull
    private final ArrayMap<Integer, MoreContentCache> contentMap;

    @NotNull
    private final ArrayMap<Integer, Boolean> expColMap;

    @NotNull
    private final ArrayMap<Integer, Integer> lineCountMap;

    @NotNull
    private final ArrayMap<Integer, Integer> lineIndexMap;

    public CourseCommentAdapter() {
        super(R.layout.item_course_comment, null, 2, null);
        this.expColMap = new ArrayMap<>();
        this.contentMap = new ArrayMap<>();
        this.lineCountMap = new ArrayMap<>();
        this.lineIndexMap = new ArrayMap<>();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0161  */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convert(@org.jetbrains.annotations.NotNull final com.chad.library.adapter.base.viewholder.BaseViewHolder r8, @org.jetbrains.annotations.NotNull com.psychiatrygarden.activity.purchase.beans.CourseCommentBean r9) {
        /*
            Method dump skipped, instructions count: 489
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.adapter.CourseCommentAdapter.convert(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.activity.purchase.beans.CourseCommentBean):void");
    }
}
