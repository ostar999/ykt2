package com.psychiatrygarden.utils;

import android.content.Context;
import com.psychiatrygarden.db.SharePreferencesUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\u001a$\u0010\u0004\u001a\u00020\u00012\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0001\u001a$\u0010\t\u001a\u00020\u00012\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0001\u001a$\u0010\n\u001a\u00020\u00012\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001\u001a$\u0010\f\u001a\u00020\r2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\u0010\u000e\u001a\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u001a.\u0010\f\u001a\u00020\r2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\u0010\u000e\u001a\u0004\u0018\u00010\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u001a$\u0010\u0010\u001a\u00020\r2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\u0010\u0011\u001a\u0004\u0018\u00010\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"lastLearnChapterKey", "", "lastLearnKey", "playingChapterKey", "readLastLearnChapterId", "courseId", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "defLastLearnVid", "readLastLearnVid", "readPlayingChapterId", "defChapterId", "writeLastLearnVid", "", "lastLearnVid", "chapterId", "writePlayingChapterId", "playingChapter", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CourseDataSpUtilKt {

    @NotNull
    public static final String lastLearnChapterKey = "LastLearnChapterId";

    @NotNull
    public static final String lastLearnKey = "LastLearnCourseVid";

    @NotNull
    public static final String playingChapterKey = "playingChapterId";

    @NotNull
    public static final String readLastLearnChapterId(@Nullable String str, @Nullable Context context, @Nullable String str2) {
        if ((str == null || str.length() == 0) || context == null) {
            return "";
        }
        String strConfig = SharePreferencesUtils.readStrConfig(lastLearnChapterKey + str, context, str2);
        Intrinsics.checkNotNullExpressionValue(strConfig, "readStrConfig(\n         …defLastLearnVid\n        )");
        return strConfig;
    }

    @NotNull
    public static final String readLastLearnVid(@Nullable String str, @Nullable Context context, @Nullable String str2) {
        if ((str == null || str.length() == 0) || context == null) {
            return "";
        }
        String strConfig = SharePreferencesUtils.readStrConfig(lastLearnKey + str, context, str2);
        Intrinsics.checkNotNullExpressionValue(strConfig, "readStrConfig(\n         …defLastLearnVid\n        )");
        return strConfig;
    }

    @NotNull
    public static final String readPlayingChapterId(@Nullable String str, @Nullable Context context, @Nullable String str2) {
        if ((str == null || str.length() == 0) || context == null) {
            return "";
        }
        String strConfig = SharePreferencesUtils.readStrConfig(playingChapterKey + str, context, str2);
        Intrinsics.checkNotNullExpressionValue(strConfig, "readStrConfig(\n         …t, defChapterId\n        )");
        return strConfig;
    }

    public static final void writeLastLearnVid(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable Context context) {
        System.out.println((Object) ("this is writeLastLearnVid: " + str + " --- " + str2 + " -- " + str3));
        if (str == null || str.length() == 0) {
            return;
        }
        if ((str2 == null || str2.length() == 0) || context == null) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(lastLearnKey + str, str2, context);
        SharePreferencesUtils.writeStrConfig(lastLearnChapterKey + str, str3, context);
    }

    public static final void writePlayingChapterId(@Nullable String str, @Nullable String str2, @Nullable Context context) {
        if (str == null || str.length() == 0) {
            return;
        }
        if ((str2 == null || str2.length() == 0) || context == null) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(playingChapterKey + str, str2, context);
    }

    public static final void writeLastLearnVid(@Nullable String str, @Nullable String str2, @Nullable Context context) {
        if (str == null || str.length() == 0) {
            return;
        }
        if ((str2 == null || str2.length() == 0) || context == null) {
            return;
        }
        SharePreferencesUtils.writeStrConfig(lastLearnKey + str, str2, context);
    }
}
