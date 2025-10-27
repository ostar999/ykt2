package com.psychiatrygarden.bean;

import com.psychiatrygarden.utils.Constants;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u001a\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bj\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\t¢\u0006\u0002\u0010\nJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001d\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bj\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\tHÆ\u0003JW\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\u001c\b\u0002\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bj\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR%\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bj\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u001e"}, d2 = {"Lcom/psychiatrygarden/bean/CourseTeacher;", "", "id", "", "name", "avatar", "identity", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;)V", "getAvatar", "()Ljava/lang/String;", "getId", "getIdentity", "getLabel", "()Ljava/util/ArrayList;", "getName", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CourseTeacher {

    @Nullable
    private final String avatar;

    @Nullable
    private final String id;

    @Nullable
    private final String identity;

    @Nullable
    private final ArrayList<String> label;

    @Nullable
    private final String name;

    public CourseTeacher(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable ArrayList<String> arrayList) {
        this.id = str;
        this.name = str2;
        this.avatar = str3;
        this.identity = str4;
        this.label = arrayList;
    }

    public static /* synthetic */ CourseTeacher copy$default(CourseTeacher courseTeacher, String str, String str2, String str3, String str4, ArrayList arrayList, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = courseTeacher.id;
        }
        if ((i2 & 2) != 0) {
            str2 = courseTeacher.name;
        }
        String str5 = str2;
        if ((i2 & 4) != 0) {
            str3 = courseTeacher.avatar;
        }
        String str6 = str3;
        if ((i2 & 8) != 0) {
            str4 = courseTeacher.identity;
        }
        String str7 = str4;
        if ((i2 & 16) != 0) {
            arrayList = courseTeacher.label;
        }
        return courseTeacher.copy(str, str5, str6, str7, arrayList);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getAvatar() {
        return this.avatar;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getIdentity() {
        return this.identity;
    }

    @Nullable
    public final ArrayList<String> component5() {
        return this.label;
    }

    @NotNull
    public final CourseTeacher copy(@Nullable String id, @Nullable String name, @Nullable String avatar, @Nullable String identity, @Nullable ArrayList<String> label) {
        return new CourseTeacher(id, name, avatar, identity, label);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CourseTeacher)) {
            return false;
        }
        CourseTeacher courseTeacher = (CourseTeacher) other;
        return Intrinsics.areEqual(this.id, courseTeacher.id) && Intrinsics.areEqual(this.name, courseTeacher.name) && Intrinsics.areEqual(this.avatar, courseTeacher.avatar) && Intrinsics.areEqual(this.identity, courseTeacher.identity) && Intrinsics.areEqual(this.label, courseTeacher.label);
    }

    @Nullable
    public final String getAvatar() {
        return this.avatar;
    }

    @Nullable
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final String getIdentity() {
        return this.identity;
    }

    @Nullable
    public final ArrayList<String> getLabel() {
        return this.label;
    }

    @Nullable
    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.name;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.avatar;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.identity;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        ArrayList<String> arrayList = this.label;
        return iHashCode4 + (arrayList != null ? arrayList.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "CourseTeacher(id=" + this.id + ", name=" + this.name + ", avatar=" + this.avatar + ", identity=" + this.identity + ", label=" + this.label + ')';
    }
}
