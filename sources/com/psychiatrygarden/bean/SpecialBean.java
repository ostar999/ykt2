package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0006¢\u0006\u0002\u0010\tJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006HÆ\u0003J\u0011\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0006HÆ\u0003JE\u0010\u0014\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u001b"}, d2 = {"Lcom/psychiatrygarden/bean/SpecialBean;", "", "id", "", "title", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "", "course_list", "Lcom/psychiatrygarden/bean/SpecialItemBean;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getCourse_list", "()Ljava/util/List;", "getCover", "getId", "()Ljava/lang/String;", "getTitle", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class SpecialBean {

    @Nullable
    private final List<SpecialItemBean> course_list;

    @Nullable
    private final List<String> cover;

    @Nullable
    private final String id;

    @Nullable
    private final String title;

    public SpecialBean(@Nullable String str, @Nullable String str2, @Nullable List<String> list, @Nullable List<SpecialItemBean> list2) {
        this.id = str;
        this.title = str2;
        this.cover = list;
        this.course_list = list2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SpecialBean copy$default(SpecialBean specialBean, String str, String str2, List list, List list2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = specialBean.id;
        }
        if ((i2 & 2) != 0) {
            str2 = specialBean.title;
        }
        if ((i2 & 4) != 0) {
            list = specialBean.cover;
        }
        if ((i2 & 8) != 0) {
            list2 = specialBean.course_list;
        }
        return specialBean.copy(str, str2, list, list2);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final List<String> component3() {
        return this.cover;
    }

    @Nullable
    public final List<SpecialItemBean> component4() {
        return this.course_list;
    }

    @NotNull
    public final SpecialBean copy(@Nullable String id, @Nullable String title, @Nullable List<String> cover, @Nullable List<SpecialItemBean> course_list) {
        return new SpecialBean(id, title, cover, course_list);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpecialBean)) {
            return false;
        }
        SpecialBean specialBean = (SpecialBean) other;
        return Intrinsics.areEqual(this.id, specialBean.id) && Intrinsics.areEqual(this.title, specialBean.title) && Intrinsics.areEqual(this.cover, specialBean.cover) && Intrinsics.areEqual(this.course_list, specialBean.course_list);
    }

    @Nullable
    public final List<SpecialItemBean> getCourse_list() {
        return this.course_list;
    }

    @Nullable
    public final List<String> getCover() {
        return this.cover;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.title;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        List<String> list = this.cover;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        List<SpecialItemBean> list2 = this.course_list;
        return iHashCode3 + (list2 != null ? list2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "SpecialBean(id=" + this.id + ", title=" + this.title + ", cover=" + this.cover + ", course_list=" + this.course_list + ')';
    }
}
