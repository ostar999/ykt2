package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/psychiatrygarden/bean/CourseDirectoryCombineBean;", "", "package_text", "", "package_list", "", "Lcom/psychiatrygarden/activity/courselist/bean/CurriculumItemBean$DataDTO;", "(Ljava/lang/String;Ljava/util/List;)V", "getPackage_list", "()Ljava/util/List;", "getPackage_text", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CourseDirectoryCombineBean {

    @SerializedName("package")
    @NotNull
    private final List<CurriculumItemBean.DataDTO> package_list;

    @NotNull
    private final String package_text;

    /* JADX WARN: Multi-variable type inference failed */
    public CourseDirectoryCombineBean(@NotNull String package_text, @NotNull List<? extends CurriculumItemBean.DataDTO> package_list) {
        Intrinsics.checkNotNullParameter(package_text, "package_text");
        Intrinsics.checkNotNullParameter(package_list, "package_list");
        this.package_text = package_text;
        this.package_list = package_list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CourseDirectoryCombineBean copy$default(CourseDirectoryCombineBean courseDirectoryCombineBean, String str, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = courseDirectoryCombineBean.package_text;
        }
        if ((i2 & 2) != 0) {
            list = courseDirectoryCombineBean.package_list;
        }
        return courseDirectoryCombineBean.copy(str, list);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getPackage_text() {
        return this.package_text;
    }

    @NotNull
    public final List<CurriculumItemBean.DataDTO> component2() {
        return this.package_list;
    }

    @NotNull
    public final CourseDirectoryCombineBean copy(@NotNull String package_text, @NotNull List<? extends CurriculumItemBean.DataDTO> package_list) {
        Intrinsics.checkNotNullParameter(package_text, "package_text");
        Intrinsics.checkNotNullParameter(package_list, "package_list");
        return new CourseDirectoryCombineBean(package_text, package_list);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CourseDirectoryCombineBean)) {
            return false;
        }
        CourseDirectoryCombineBean courseDirectoryCombineBean = (CourseDirectoryCombineBean) other;
        return Intrinsics.areEqual(this.package_text, courseDirectoryCombineBean.package_text) && Intrinsics.areEqual(this.package_list, courseDirectoryCombineBean.package_list);
    }

    @NotNull
    public final List<CurriculumItemBean.DataDTO> getPackage_list() {
        return this.package_list;
    }

    @NotNull
    public final String getPackage_text() {
        return this.package_text;
    }

    public int hashCode() {
        return (this.package_text.hashCode() * 31) + this.package_list.hashCode();
    }

    @NotNull
    public String toString() {
        return "CourseDirectoryCombineBean(package_text=" + this.package_text + ", package_list=" + this.package_list + ')';
    }
}
