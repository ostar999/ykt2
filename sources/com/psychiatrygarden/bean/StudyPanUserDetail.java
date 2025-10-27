package com.psychiatrygarden.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lcom/psychiatrygarden/bean/StudyPanUserDetail;", "", "id", "", "date", "status", "day", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDate", "()Ljava/lang/String;", "getDay", "getId", "getStatus", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class StudyPanUserDetail {

    @Nullable
    private final String date;

    @Nullable
    private final String day;

    @Nullable
    private final String id;

    @Nullable
    private final String status;

    public StudyPanUserDetail(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.id = str;
        this.date = str2;
        this.status = str3;
        this.day = str4;
    }

    @Nullable
    public final String getDate() {
        return this.date;
    }

    @Nullable
    public final String getDay() {
        return this.day;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getStatus() {
        return this.status;
    }
}
