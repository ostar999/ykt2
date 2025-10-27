package com.plv.thirdpart.litepal.extension;

import androidx.exifinterface.media.ExifInterface;
import com.plv.thirdpart.litepal.FluentQuery;
import com.plv.thirdpart.litepal.crud.async.FindExecutor;
import com.plv.thirdpart.litepal.crud.async.FindMultiExecutor;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u001d\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0086\b\u001a\u0015\u0010\u0006\u001a\u00020\u0007\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0086\b\u001a\u001b\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0086\b\u001a#\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0086\b\u001a9\u0010\f\u001a&\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0002H\u0002 \u000e*\u0012\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010\r0\r\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0087\b\u001aA\u0010\f\u001a&\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0002H\u0002 \u000e*\u0012\u0012\f\u0012\n \u000e*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010\r0\r\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u001c\u0010\u000f\u001a\u0004\u0018\u0001H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u0010\u001a$\u0010\u000f\u001a\u0004\u0018\u0001H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0086\b¢\u0006\u0002\u0010\u0011\u001a\u001b\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0013\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0087\b\u001a\u001c\u0010\u0014\u001a\u0004\u0018\u0001H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u0010\u001a$\u0010\u0014\u001a\u0004\u0018\u0001H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0086\b¢\u0006\u0002\u0010\u0011\u001a*\u0010\u0015\u001a\u0002H\u0016\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010\u0016\u0018\u0001*\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\u0018\u001a*\u0010\u0015\u001a\u0002H\u0016\"\u0006\b\u0000\u0010\u0016\u0018\u0001*\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\u001a\u001a*\u0010\u001b\u001a\u0002H\u0016\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010\u0016\u0018\u0001*\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\u0018\u001a*\u0010\u001b\u001a\u0002H\u0016\"\u0006\b\u0000\u0010\u0016\u0018\u0001*\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\u001a\u001a*\u0010\u001c\u001a\u0002H\u0016\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010\u0016\u0018\u0001*\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\u0018\u001a*\u0010\u001c\u001a\u0002H\u0016\"\u0006\b\u0000\u0010\u0016\u0018\u0001*\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u0010\u001a¨\u0006\u001d"}, d2 = {"average", "", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/plv/thirdpart/litepal/FluentQuery;", "column", "", "count", "", "find", "", "isEager", "", "findAsync", "Lcom/plv/thirdpart/litepal/crud/async/FindMultiExecutor;", "kotlin.jvm.PlatformType", "findFirst", "(Lcom/plv/thirdpart/litepal/FluentQuery;)Ljava/lang/Object;", "(Lcom/plv/thirdpart/litepal/FluentQuery;Z)Ljava/lang/Object;", "findFirstAsync", "Lcom/plv/thirdpart/litepal/crud/async/FindExecutor;", "findLast", "max", "R", "columnName", "(Lcom/plv/thirdpart/litepal/FluentQuery;Ljava/lang/String;)Ljava/lang/Object;", "tableName", "(Lcom/plv/thirdpart/litepal/FluentQuery;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", "min", "sum", "polyvSDKFoundation_release"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class FluentQueryKt {
    public static final /* synthetic */ <T> double average(@NotNull FluentQuery average, @NotNull String column) {
        Intrinsics.checkParameterIsNotNull(average, "$this$average");
        Intrinsics.checkParameterIsNotNull(column, "column");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return average.average(Object.class, column);
    }

    public static final /* synthetic */ <T> int count(@NotNull FluentQuery count) {
        Intrinsics.checkParameterIsNotNull(count, "$this$count");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return count.count(Object.class);
    }

    @NotNull
    public static final /* synthetic */ <T> List<T> find(@NotNull FluentQuery find) {
        Intrinsics.checkParameterIsNotNull(find, "$this$find");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        List<T> listFind = find.find(Object.class);
        Intrinsics.checkExpressionValueIsNotNull(listFind, "find(T::class.java)");
        return listFind;
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindMultiExecutor<T> findAsync(@NotNull FluentQuery findAsync) {
        Intrinsics.checkParameterIsNotNull(findAsync, "$this$findAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return findAsync.findAsync(Object.class);
    }

    @Nullable
    public static final /* synthetic */ <T> T findFirst(@NotNull FluentQuery findFirst) {
        Intrinsics.checkParameterIsNotNull(findFirst, "$this$findFirst");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) findFirst.findFirst(Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @NotNull
    public static final /* synthetic */ <T> FindExecutor<T> findFirstAsync(@NotNull FluentQuery findFirstAsync) {
        Intrinsics.checkParameterIsNotNull(findFirstAsync, "$this$findFirstAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        FindExecutor<T> findExecutorFindFirstAsync = findFirstAsync.findFirstAsync(Object.class);
        Intrinsics.checkExpressionValueIsNotNull(findExecutorFindFirstAsync, "findFirstAsync(T::class.java)");
        return findExecutorFindFirstAsync;
    }

    @Nullable
    public static final /* synthetic */ <T> T findLast(@NotNull FluentQuery findLast) {
        Intrinsics.checkParameterIsNotNull(findLast, "$this$findLast");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) findLast.findLast(Object.class);
    }

    public static final /* synthetic */ <T, R> R max(@NotNull FluentQuery max, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(max, "$this$max");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) max.max(Object.class, columnName, Object.class);
    }

    public static final /* synthetic */ <T, R> R min(@NotNull FluentQuery min, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(min, "$this$min");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) min.min(Object.class, columnName, Object.class);
    }

    public static final /* synthetic */ <T, R> R sum(@NotNull FluentQuery sum, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) sum.sum(Object.class, columnName, Object.class);
    }

    @NotNull
    public static final /* synthetic */ <T> List<T> find(@NotNull FluentQuery find, boolean z2) {
        Intrinsics.checkParameterIsNotNull(find, "$this$find");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        List<T> listFind = find.find(Object.class, z2);
        Intrinsics.checkExpressionValueIsNotNull(listFind, "find(T::class.java, isEager)");
        return listFind;
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindMultiExecutor<T> findAsync(@NotNull FluentQuery findAsync, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findAsync, "$this$findAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return findAsync.findAsync(Object.class, z2);
    }

    @Nullable
    public static final /* synthetic */ <T> T findFirst(@NotNull FluentQuery findFirst, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findFirst, "$this$findFirst");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) findFirst.findFirst(Object.class, z2);
    }

    @Nullable
    public static final /* synthetic */ <T> T findLast(@NotNull FluentQuery findLast, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findLast, "$this$findLast");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) findLast.findLast(Object.class, z2);
    }

    public static final /* synthetic */ <R> R max(@NotNull FluentQuery max, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(max, "$this$max");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) max.max(tableName, columnName, Object.class);
    }

    public static final /* synthetic */ <R> R min(@NotNull FluentQuery min, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(min, "$this$min");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) min.min(tableName, columnName, Object.class);
    }

    public static final /* synthetic */ <R> R sum(@NotNull FluentQuery sum, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) sum.sum(tableName, columnName, Object.class);
    }
}
