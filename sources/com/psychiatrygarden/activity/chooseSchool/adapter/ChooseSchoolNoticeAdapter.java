package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.view.View;
import com.catchpig.mvvm.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.psychiatrygarden.bean.ChooseSchoolNoticeData;
import com.yikaobang.yixue.R;
import com.ykb.ebook.util.DateUtilKt;
import java.util.Calendar;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0002H\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ChooseSchoolNoticeAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChooseSchoolNoticeData;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "convert", "", "holder", "item", "getTimeStr", "", CrashHianalyticsData.TIME, "isCurYear", "", "targetTimestamp", "", "isToday", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolNoticeAdapter extends BaseQuickAdapter<ChooseSchoolNoticeData, BaseViewHolder> {
    public ChooseSchoolNoticeAdapter() {
        super(R.layout.item_choose_school_notice, null, 2, null);
        setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.d
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                ChooseSchoolNoticeAdapter._init_$lambda$0(this.f11196c, baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(ChooseSchoolNoticeAdapter this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        this$0.getItem(i2);
    }

    private final String getTimeStr(String time) {
        long j2 = 1000;
        return isToday(Long.parseLong(time) * j2) ? DateUtilKt.formatLongTimeStampToString(Long.parseLong(time) * j2, DateUtil.TIME_FORMAT_WITH_HM) : isCurYear(Long.parseLong(time) * j2) ? DateUtilKt.formatLongTimeStampToString(Long.parseLong(time) * j2, DateUtil.TIME_FORMAT_MONTH_DAY_HM) : DateUtilKt.formatLongTimeStampToString(Long.parseLong(time) * j2, "yyyy-MM-dd HH:mm");
    }

    public final boolean isCurYear(long targetTimestamp) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(targetTimestamp);
        return calendar.get(1) == calendar2.get(1);
    }

    public final boolean isToday(long targetTimestamp) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(targetTimestamp);
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull ChooseSchoolNoticeData item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        holder.setText(R.id.tvContent, item.getContent());
        String ctime = item.getCtime();
        if (ctime == null || ctime.length() == 0) {
            return;
        }
        String ctime2 = item.getCtime();
        Intrinsics.checkNotNull(ctime2);
        holder.setText(R.id.tvTime, getTimeStr(ctime2));
    }
}
