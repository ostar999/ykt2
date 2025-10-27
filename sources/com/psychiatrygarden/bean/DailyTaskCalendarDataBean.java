package com.psychiatrygarden.bean;

import com.heytap.mcssdk.constant.b;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\nR\u0013\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lcom/psychiatrygarden/bean/DailyTaskCalendarDataBean;", "", "study_plan_user", "Lcom/psychiatrygarden/bean/DailyTaskCalendarPlanBean;", "study_plan_user_detail", "", "Lcom/psychiatrygarden/bean/StudyPanUserDetail;", b.f7191p, "", "question_category_id", "(Lcom/psychiatrygarden/bean/DailyTaskCalendarPlanBean;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getQuestion_category_id", "()Ljava/lang/String;", "getRule", "getStudy_plan_user", "()Lcom/psychiatrygarden/bean/DailyTaskCalendarPlanBean;", "getStudy_plan_user_detail", "()Ljava/util/List;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class DailyTaskCalendarDataBean {

    @Nullable
    private final String question_category_id;

    @Nullable
    private final String rule;

    @NotNull
    private final DailyTaskCalendarPlanBean study_plan_user;

    @Nullable
    private final List<StudyPanUserDetail> study_plan_user_detail;

    public DailyTaskCalendarDataBean(@NotNull DailyTaskCalendarPlanBean study_plan_user, @Nullable List<StudyPanUserDetail> list, @Nullable String str, @Nullable String str2) {
        Intrinsics.checkNotNullParameter(study_plan_user, "study_plan_user");
        this.study_plan_user = study_plan_user;
        this.study_plan_user_detail = list;
        this.rule = str;
        this.question_category_id = str2;
    }

    @Nullable
    public final String getQuestion_category_id() {
        return this.question_category_id;
    }

    @Nullable
    public final String getRule() {
        return this.rule;
    }

    @NotNull
    public final DailyTaskCalendarPlanBean getStudy_plan_user() {
        return this.study_plan_user;
    }

    @Nullable
    public final List<StudyPanUserDetail> getStudy_plan_user_detail() {
        return this.study_plan_user_detail;
    }
}
