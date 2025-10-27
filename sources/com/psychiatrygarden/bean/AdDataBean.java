package com.psychiatrygarden.bean;

import com.psychiatrygarden.utils.CommonParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000bJ\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\tHÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003JQ\u0010\u001b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001f\u001a\u00020 HÖ\u0001J\t\u0010!\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r¨\u0006\""}, d2 = {"Lcom/psychiatrygarden/bean/AdDataBean;", "", "open", "", CommonParameter.ANDROID_OPEN, CommonParameter.EXAM_TIME, CommonParameter.boot_page, "Lcom/psychiatrygarden/bean/BootPageBean;", CommonParameter.notice, "Lcom/psychiatrygarden/bean/NoticeBean;", "question_correction_rules", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/BootPageBean;Lcom/psychiatrygarden/bean/NoticeBean;Ljava/lang/String;)V", "getAndroid_open", "()Ljava/lang/String;", "getBoot_page", "()Lcom/psychiatrygarden/bean/BootPageBean;", "getExam_time", "getNotice", "()Lcom/psychiatrygarden/bean/NoticeBean;", "getOpen", "getQuestion_correction_rules", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class AdDataBean {

    @Nullable
    private final String android_open;

    @Nullable
    private final BootPageBean boot_page;

    @Nullable
    private final String exam_time;

    @Nullable
    private final NoticeBean notice;

    @Nullable
    private final String open;

    @Nullable
    private final String question_correction_rules;

    public AdDataBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable BootPageBean bootPageBean, @Nullable NoticeBean noticeBean, @Nullable String str4) {
        this.open = str;
        this.android_open = str2;
        this.exam_time = str3;
        this.boot_page = bootPageBean;
        this.notice = noticeBean;
        this.question_correction_rules = str4;
    }

    public static /* synthetic */ AdDataBean copy$default(AdDataBean adDataBean, String str, String str2, String str3, BootPageBean bootPageBean, NoticeBean noticeBean, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = adDataBean.open;
        }
        if ((i2 & 2) != 0) {
            str2 = adDataBean.android_open;
        }
        String str5 = str2;
        if ((i2 & 4) != 0) {
            str3 = adDataBean.exam_time;
        }
        String str6 = str3;
        if ((i2 & 8) != 0) {
            bootPageBean = adDataBean.boot_page;
        }
        BootPageBean bootPageBean2 = bootPageBean;
        if ((i2 & 16) != 0) {
            noticeBean = adDataBean.notice;
        }
        NoticeBean noticeBean2 = noticeBean;
        if ((i2 & 32) != 0) {
            str4 = adDataBean.question_correction_rules;
        }
        return adDataBean.copy(str, str5, str6, bootPageBean2, noticeBean2, str4);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getOpen() {
        return this.open;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getAndroid_open() {
        return this.android_open;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getExam_time() {
        return this.exam_time;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final BootPageBean getBoot_page() {
        return this.boot_page;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final NoticeBean getNotice() {
        return this.notice;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getQuestion_correction_rules() {
        return this.question_correction_rules;
    }

    @NotNull
    public final AdDataBean copy(@Nullable String open, @Nullable String android_open, @Nullable String exam_time, @Nullable BootPageBean boot_page, @Nullable NoticeBean notice, @Nullable String question_correction_rules) {
        return new AdDataBean(open, android_open, exam_time, boot_page, notice, question_correction_rules);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AdDataBean)) {
            return false;
        }
        AdDataBean adDataBean = (AdDataBean) other;
        return Intrinsics.areEqual(this.open, adDataBean.open) && Intrinsics.areEqual(this.android_open, adDataBean.android_open) && Intrinsics.areEqual(this.exam_time, adDataBean.exam_time) && Intrinsics.areEqual(this.boot_page, adDataBean.boot_page) && Intrinsics.areEqual(this.notice, adDataBean.notice) && Intrinsics.areEqual(this.question_correction_rules, adDataBean.question_correction_rules);
    }

    @Nullable
    public final String getAndroid_open() {
        return this.android_open;
    }

    @Nullable
    public final BootPageBean getBoot_page() {
        return this.boot_page;
    }

    @Nullable
    public final String getExam_time() {
        return this.exam_time;
    }

    @Nullable
    public final NoticeBean getNotice() {
        return this.notice;
    }

    @Nullable
    public final String getOpen() {
        return this.open;
    }

    @Nullable
    public final String getQuestion_correction_rules() {
        return this.question_correction_rules;
    }

    public int hashCode() {
        String str = this.open;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.android_open;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.exam_time;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        BootPageBean bootPageBean = this.boot_page;
        int iHashCode4 = (iHashCode3 + (bootPageBean == null ? 0 : bootPageBean.hashCode())) * 31;
        NoticeBean noticeBean = this.notice;
        int iHashCode5 = (iHashCode4 + (noticeBean == null ? 0 : noticeBean.hashCode())) * 31;
        String str4 = this.question_correction_rules;
        return iHashCode5 + (str4 != null ? str4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "AdDataBean(open=" + this.open + ", android_open=" + this.android_open + ", exam_time=" + this.exam_time + ", boot_page=" + this.boot_page + ", notice=" + this.notice + ", question_correction_rules=" + this.question_correction_rules + ')';
    }
}
