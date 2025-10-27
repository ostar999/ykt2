package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B)\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/bean/DailyDateData;", "", "message", "", "data", "", "Lcom/psychiatrygarden/bean/DateTabBean;", "code", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getData", "()Ljava/util/List;", "getMessage", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class DailyDateData {

    @Nullable
    private final String code;

    @Nullable
    private final List<DateTabBean> data;

    @Nullable
    private final String message;

    public DailyDateData(@Nullable String str, @Nullable List<DateTabBean> list, @Nullable String str2) {
        this.message = str;
        this.data = list;
        this.code = str2;
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    @Nullable
    public final List<DateTabBean> getData() {
        return this.data;
    }

    @Nullable
    public final String getMessage() {
        return this.message;
    }
}
