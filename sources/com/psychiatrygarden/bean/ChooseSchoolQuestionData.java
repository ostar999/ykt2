package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0006\"\u0004\b\u001e\u0010\bR\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0006\"\u0004\b!\u0010\bR\u001c\u0010\"\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0006\"\u0004\b$\u0010\b¨\u0006%"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolQuestionData;", "Ljava/io/Serializable;", "()V", "ctime", "", "getCtime", "()Ljava/lang/String;", "setCtime", "(Ljava/lang/String;)V", "describe", "getDescribe", "setDescribe", "expand", "", "getExpand", "()Z", "setExpand", "(Z)V", "id", "getId", "setId", "question_list", "", "Lcom/psychiatrygarden/bean/ChooseSchoolQuestionItem;", "getQuestion_list", "()Ljava/util/List;", "setQuestion_list", "(Ljava/util/List;)V", "status", "getStatus", "setStatus", "title", "getTitle", "setTitle", "utime", "getUtime", "setUtime", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ChooseSchoolQuestionData implements Serializable {

    @Nullable
    private String ctime;

    @Nullable
    private String describe;
    private boolean expand;

    @Nullable
    private String id;

    @NotNull
    private List<ChooseSchoolQuestionItem> question_list = new ArrayList();

    @Nullable
    private String status;

    @Nullable
    private String title;

    @Nullable
    private String utime;

    @Nullable
    public final String getCtime() {
        return this.ctime;
    }

    @Nullable
    public final String getDescribe() {
        return this.describe;
    }

    public final boolean getExpand() {
        return this.expand;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final List<ChooseSchoolQuestionItem> getQuestion_list() {
        return this.question_list;
    }

    @Nullable
    public final String getStatus() {
        return this.status;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getUtime() {
        return this.utime;
    }

    public final void setCtime(@Nullable String str) {
        this.ctime = str;
    }

    public final void setDescribe(@Nullable String str) {
        this.describe = str;
    }

    public final void setExpand(boolean z2) {
        this.expand = z2;
    }

    public final void setId(@Nullable String str) {
        this.id = str;
    }

    public final void setQuestion_list(@NotNull List<ChooseSchoolQuestionItem> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.question_list = list;
    }

    public final void setStatus(@Nullable String str) {
        this.status = str;
    }

    public final void setTitle(@Nullable String str) {
        this.title = str;
    }

    public final void setUtime(@Nullable String str) {
        this.utime = str;
    }
}
