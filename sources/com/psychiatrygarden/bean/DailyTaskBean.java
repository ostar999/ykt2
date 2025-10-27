package com.psychiatrygarden.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lcom/psychiatrygarden/bean/DailyTaskBean;", "", "code", "", "message", "data", "Lcom/psychiatrygarden/bean/DailyTaskCalendarDataBean;", "(Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/DailyTaskCalendarDataBean;)V", "getCode", "()Ljava/lang/String;", "getData", "()Lcom/psychiatrygarden/bean/DailyTaskCalendarDataBean;", "getMessage", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class DailyTaskBean {

    @Nullable
    private final String code;

    @Nullable
    private final DailyTaskCalendarDataBean data;

    @Nullable
    private final String message;

    public DailyTaskBean(@Nullable String str, @Nullable String str2, @Nullable DailyTaskCalendarDataBean dailyTaskCalendarDataBean) {
        this.code = str;
        this.message = str2;
        this.data = dailyTaskCalendarDataBean;
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final DailyTaskCalendarDataBean getData() {
        return this.data;
    }

    @Nullable
    public final String getMessage() {
        return this.message;
    }
}
