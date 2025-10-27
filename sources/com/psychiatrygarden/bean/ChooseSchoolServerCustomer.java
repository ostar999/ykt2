package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/bean/ChooseSchoolServerCustomer;", "", "is_show", "", "feedback", "Lcom/psychiatrygarden/bean/OnlineServiceBean;", "(Ljava/lang/String;Lcom/psychiatrygarden/bean/OnlineServiceBean;)V", "getFeedback", "()Lcom/psychiatrygarden/bean/OnlineServiceBean;", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChooseSchoolServerCustomer {

    @Nullable
    private final OnlineServiceBean feedback;

    @Nullable
    private final String is_show;

    public ChooseSchoolServerCustomer(@Nullable String str, @Nullable OnlineServiceBean onlineServiceBean) {
        this.is_show = str;
        this.feedback = onlineServiceBean;
    }

    public static /* synthetic */ ChooseSchoolServerCustomer copy$default(ChooseSchoolServerCustomer chooseSchoolServerCustomer, String str, OnlineServiceBean onlineServiceBean, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chooseSchoolServerCustomer.is_show;
        }
        if ((i2 & 2) != 0) {
            onlineServiceBean = chooseSchoolServerCustomer.feedback;
        }
        return chooseSchoolServerCustomer.copy(str, onlineServiceBean);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getIs_show() {
        return this.is_show;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final OnlineServiceBean getFeedback() {
        return this.feedback;
    }

    @NotNull
    public final ChooseSchoolServerCustomer copy(@Nullable String is_show, @Nullable OnlineServiceBean feedback) {
        return new ChooseSchoolServerCustomer(is_show, feedback);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChooseSchoolServerCustomer)) {
            return false;
        }
        ChooseSchoolServerCustomer chooseSchoolServerCustomer = (ChooseSchoolServerCustomer) other;
        return Intrinsics.areEqual(this.is_show, chooseSchoolServerCustomer.is_show) && Intrinsics.areEqual(this.feedback, chooseSchoolServerCustomer.feedback);
    }

    @Nullable
    public final OnlineServiceBean getFeedback() {
        return this.feedback;
    }

    public int hashCode() {
        String str = this.is_show;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        OnlineServiceBean onlineServiceBean = this.feedback;
        return iHashCode + (onlineServiceBean != null ? onlineServiceBean.hashCode() : 0);
    }

    @Nullable
    public final String is_show() {
        return this.is_show;
    }

    @NotNull
    public String toString() {
        return "ChooseSchoolServerCustomer(is_show=" + this.is_show + ", feedback=" + this.feedback + ')';
    }
}
