package com.psychiatrygarden.bean;

import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001a\u0018\u00002\u00020\u0001Bs\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0010\"\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u001c\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0010\"\u0004\b\u0019\u0010\u0016R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0010¨\u0006\u001d"}, d2 = {"Lcom/psychiatrygarden/bean/DailyTaskCalendarPlanBean;", "", "id", "", "name", "days", "complete_days", "status", d.f22694p, "type", "description", "completion", "notice_time", "is_notice", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getComplete_days", "()Ljava/lang/String;", "getCompletion", "getDays", "getDescription", "getId", "set_notice", "(Ljava/lang/String;)V", "getName", "getNotice_time", "setNotice_time", "getStart_time", "getStatus", "getType", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class DailyTaskCalendarPlanBean {

    @Nullable
    private final String complete_days;

    @Nullable
    private final String completion;

    @Nullable
    private final String days;

    @Nullable
    private final String description;

    @Nullable
    private final String id;

    @Nullable
    private String is_notice;

    @Nullable
    private final String name;

    @Nullable
    private String notice_time;

    @Nullable
    private final String start_time;

    @Nullable
    private final String status;

    @Nullable
    private final String type;

    public DailyTaskCalendarPlanBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable String str11) {
        this.id = str;
        this.name = str2;
        this.days = str3;
        this.complete_days = str4;
        this.status = str5;
        this.start_time = str6;
        this.type = str7;
        this.description = str8;
        this.completion = str9;
        this.notice_time = str10;
        this.is_notice = str11;
    }

    @Nullable
    public final String getComplete_days() {
        return this.complete_days;
    }

    @Nullable
    public final String getCompletion() {
        return this.completion;
    }

    @Nullable
    public final String getDays() {
        return this.days;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    @Nullable
    public final String getNotice_time() {
        return this.notice_time;
    }

    @Nullable
    public final String getStart_time() {
        return this.start_time;
    }

    @Nullable
    public final String getStatus() {
        return this.status;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: is_notice, reason: from getter */
    public final String getIs_notice() {
        return this.is_notice;
    }

    public final void setNotice_time(@Nullable String str) {
        this.notice_time = str;
    }

    public final void set_notice(@Nullable String str) {
        this.is_notice = str;
    }
}
