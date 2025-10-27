package com.plv.thirdpart.litepal.extension;

import android.content.ContentValues;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.plv.thirdpart.litepal.LitePal;
import com.plv.thirdpart.litepal.crud.LitePalSupport;
import com.plv.thirdpart.litepal.crud.async.AverageExecutor;
import com.plv.thirdpart.litepal.crud.async.CountExecutor;
import com.plv.thirdpart.litepal.crud.async.FindExecutor;
import com.plv.thirdpart.litepal.crud.async.FindMultiExecutor;
import com.plv.thirdpart.litepal.crud.async.UpdateOrDeleteExecutor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0082\u0001\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u001d\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0086\b\u001a%\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0087\b\u001a\u0015\u0010\t\u001a\u00020\n\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0086\b\u001a\u001d\u0010\u000b\u001a\n \b*\u0004\u0018\u00010\f0\f\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0087\b\u001a\u001d\u0010\r\u001a\u00020\n\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0086\b\u001a2\u0010\u0010\u001a\u00020\n\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u0012\"\u0004\u0018\u00010\u0005H\u0086\b¢\u0006\u0002\u0010\u0013\u001a:\u0010\u0014\u001a\n \b*\u0004\u0018\u00010\u00150\u0015\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u0012\"\u0004\u0018\u00010\u0005H\u0087\b¢\u0006\u0002\u0010\u0016\u001a%\u0010\u0017\u001a\n \b*\u0004\u0018\u00010\u00150\u0015\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a$\u0010\u0018\u001a\u0004\u0018\u0001H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0086\b¢\u0006\u0002\u0010\u0019\u001a2\u0010\u0018\u001a\n \b*\u0004\u0018\u0001H\u0002H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0086\b¢\u0006\u0002\u0010\u001c\u001aM\u0010\u001d\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010\u001f0\u001e\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001b2\n\u0010 \u001a\u00020!\"\u00020\u000fH\u0086\b\u001aE\u0010\u001d\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010\u001f0\u001e\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\n\u0010 \u001a\u00020!\"\u00020\u000fH\u0086\b\u001aM\u0010\"\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010#0#\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001b2\n\u0010 \u001a\u00020!\"\u00020\u000fH\u0087\b\u001aE\u0010\"\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010#0#\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\n\u0010 \u001a\u00020!\"\u00020\u000fH\u0087\b\u001aA\u0010$\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a2\u0010$\u001a\n \b*\u0004\u0018\u0001H\u0002H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0087\b¢\u0006\u0002\u0010\u001c\u001a\"\u0010&\u001a\n \b*\u0004\u0018\u0001H\u0002H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010'\u001a*\u0010&\u001a\n \b*\u0004\u0018\u0001H\u0002H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0086\b¢\u0006\u0002\u0010(\u001a9\u0010)\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0087\b\u001aA\u0010)\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0087\b\u001a\"\u0010*\u001a\n \b*\u0004\u0018\u0001H\u0002H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0086\b¢\u0006\u0002\u0010'\u001a*\u0010*\u001a\n \b*\u0004\u0018\u0001H\u0002H\u0002\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0086\b¢\u0006\u0002\u0010(\u001a9\u0010+\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003H\u0087\b\u001aA\u0010+\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H\u0002H\u0002\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0087\b\u001a2\u0010,\u001a\u00020\u001b\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u0012\"\u0004\u0018\u00010\u0005H\u0086\b¢\u0006\u0002\u0010-\u001a2\u0010.\u001a\n \b*\u0004\u0018\u0001H/H/\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010/\u0018\u0001*\u00020\u00032\u0006\u00100\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u00101\u001a2\u0010.\u001a\n \b*\u0004\u0018\u0001H/H/\"\u0006\b\u0000\u0010/\u0018\u0001*\u00020\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u00103\u001aI\u00104\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010/\u0018\u0001*\u00020\u00032\u0006\u00100\u001a\u00020\u0005H\u0087\b\u001aI\u00104\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/\u0018\u00010%0%\"\u0006\b\u0000\u0010/\u0018\u0001*\u00020\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005H\u0087\b\u001a2\u00105\u001a\n \b*\u0004\u0018\u0001H/H/\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010/\u0018\u0001*\u00020\u00032\u0006\u00100\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u00101\u001a2\u00105\u001a\n \b*\u0004\u0018\u0001H/H/\"\u0006\b\u0000\u0010/\u0018\u0001*\u00020\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u00103\u001aI\u00106\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010/\u0018\u0001*\u00020\u00032\u0006\u00100\u001a\u00020\u0005H\u0087\b\u001aI\u00106\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/\u0018\u00010%0%\"\u0006\b\u0000\u0010/\u0018\u0001*\u00020\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005H\u0087\b\u001a\u0018\u00107\u001a\u00020\u001b*\u00020\u00032\f\u00108\u001a\b\u0012\u0004\u0012\u00020\u001b09\u001a\u001a\u0010:\u001a\u00020\u001b\"\b\b\u0000\u0010\u0002*\u00020;*\b\u0012\u0004\u0012\u0002H\u00020<\u001a2\u0010=\u001a\n \b*\u0004\u0018\u0001H/H/\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010/\u0018\u0001*\u00020\u00032\u0006\u00100\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u00101\u001a2\u0010=\u001a\n \b*\u0004\u0018\u0001H/H/\"\u0006\b\u0000\u0010/\u0018\u0001*\u00020\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005H\u0086\b¢\u0006\u0002\u00103\u001aI\u0010>\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/\u0018\u00010%0%\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0006\b\u0001\u0010/\u0018\u0001*\u00020\u00032\u0006\u00100\u001a\u00020\u0005H\u0087\b\u001aI\u0010>\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/ \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u0001H/H/\u0018\u00010%0%\"\u0006\b\u0000\u0010/\u0018\u0001*\u00020\u00032\u0006\u00102\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u0005H\u0087\b\u001a%\u0010?\u001a\u00020\n\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010@\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086\b\u001a:\u0010B\u001a\u00020\n\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010@\u001a\u00020A2\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u0012\"\u0004\u0018\u00010\u0005H\u0086\b¢\u0006\u0002\u0010C\u001aB\u0010D\u001a\n \b*\u0004\u0018\u00010\u00150\u0015\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010@\u001a\u00020A2\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00050\u0012\"\u0004\u0018\u00010\u0005H\u0087\b¢\u0006\u0002\u0010E\u001a-\u0010F\u001a\n \b*\u0004\u0018\u00010\u00150\u0015\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0006\u0010@\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b¨\u0006G"}, d2 = {"average", "", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/plv/thirdpart/litepal/LitePal;", "column", "", "averageAsync", "Lcom/plv/thirdpart/litepal/crud/async/AverageExecutor;", "kotlin.jvm.PlatformType", "count", "", "countAsync", "Lcom/plv/thirdpart/litepal/crud/async/CountExecutor;", RequestParameters.SUBRESOURCE_DELETE, "id", "", "deleteAll", "conditions", "", "(Lcom/plv/thirdpart/litepal/LitePal;[Ljava/lang/String;)I", "deleteAllAsync", "Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "(Lcom/plv/thirdpart/litepal/LitePal;[Ljava/lang/String;)Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "deleteAsync", "find", "(Lcom/plv/thirdpart/litepal/LitePal;J)Ljava/lang/Object;", "isEager", "", "(Lcom/plv/thirdpart/litepal/LitePal;JZ)Ljava/lang/Object;", "findAll", "", "", "ids", "", "findAllAsync", "Lcom/plv/thirdpart/litepal/crud/async/FindMultiExecutor;", "findAsync", "Lcom/plv/thirdpart/litepal/crud/async/FindExecutor;", "findFirst", "(Lcom/plv/thirdpart/litepal/LitePal;)Ljava/lang/Object;", "(Lcom/plv/thirdpart/litepal/LitePal;Z)Ljava/lang/Object;", "findFirstAsync", "findLast", "findLastAsync", "isExist", "(Lcom/plv/thirdpart/litepal/LitePal;[Ljava/lang/String;)Z", "max", "R", "columnName", "(Lcom/plv/thirdpart/litepal/LitePal;Ljava/lang/String;)Ljava/lang/Object;", "tableName", "(Lcom/plv/thirdpart/litepal/LitePal;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;", "maxAsync", "min", "minAsync", "runInTransaction", "block", "Lkotlin/Function0;", "saveAll", "Lcom/plv/thirdpart/litepal/crud/LitePalSupport;", "", "sum", "sumAsync", PLVUpdateMicSiteEvent.EVENT_NAME, "values", "Landroid/content/ContentValues;", "updateAll", "(Lcom/plv/thirdpart/litepal/LitePal;Landroid/content/ContentValues;[Ljava/lang/String;)I", "updateAllAsync", "(Lcom/plv/thirdpart/litepal/LitePal;Landroid/content/ContentValues;[Ljava/lang/String;)Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "updateAsync", "polyvSDKFoundation_release"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class LitePalKt {
    public static final /* synthetic */ <T> double average(@NotNull LitePal average, @NotNull String column) {
        Intrinsics.checkParameterIsNotNull(average, "$this$average");
        Intrinsics.checkParameterIsNotNull(column, "column");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.average((Class<?>) Object.class, column);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> AverageExecutor averageAsync(@NotNull LitePal averageAsync, @NotNull String column) {
        Intrinsics.checkParameterIsNotNull(averageAsync, "$this$averageAsync");
        Intrinsics.checkParameterIsNotNull(column, "column");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.averageAsync((Class<?>) Object.class, column);
    }

    public static final /* synthetic */ <T> int count(@NotNull LitePal count) {
        Intrinsics.checkParameterIsNotNull(count, "$this$count");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.count((Class<?>) Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> CountExecutor countAsync(@NotNull LitePal countAsync) {
        Intrinsics.checkParameterIsNotNull(countAsync, "$this$countAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.countAsync((Class<?>) Object.class);
    }

    public static final /* synthetic */ <T> int delete(@NotNull LitePal delete, long j2) {
        Intrinsics.checkParameterIsNotNull(delete, "$this$delete");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.delete(Object.class, j2);
    }

    public static final /* synthetic */ <T> int deleteAll(@NotNull LitePal deleteAll, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(deleteAll, "$this$deleteAll");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.deleteAll((Class<?>) Object.class, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> UpdateOrDeleteExecutor deleteAllAsync(@NotNull LitePal deleteAllAsync, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(deleteAllAsync, "$this$deleteAllAsync");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.deleteAllAsync((Class<?>) Object.class, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> UpdateOrDeleteExecutor deleteAsync(@NotNull LitePal deleteAsync, long j2) {
        Intrinsics.checkParameterIsNotNull(deleteAsync, "$this$deleteAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.deleteAsync(Object.class, j2);
    }

    @Nullable
    public static final /* synthetic */ <T> T find(@NotNull LitePal find, long j2) {
        Intrinsics.checkParameterIsNotNull(find, "$this$find");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) LitePal.find(Object.class, j2);
    }

    public static final /* synthetic */ <T> List<T> findAll(@NotNull LitePal findAll, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(findAll, "$this$findAll");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findAll(Object.class, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindMultiExecutor<T> findAllAsync(@NotNull LitePal findAllAsync, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(findAllAsync, "$this$findAllAsync");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findAllAsync(Object.class, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindExecutor<T> findAsync(@NotNull LitePal findAsync, long j2) {
        Intrinsics.checkParameterIsNotNull(findAsync, "$this$findAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findAsync(Object.class, j2);
    }

    public static final /* synthetic */ <T> T findFirst(@NotNull LitePal findFirst) {
        Intrinsics.checkParameterIsNotNull(findFirst, "$this$findFirst");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) LitePal.findFirst(Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindExecutor<T> findFirstAsync(@NotNull LitePal findFirstAsync) {
        Intrinsics.checkParameterIsNotNull(findFirstAsync, "$this$findFirstAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findFirstAsync(Object.class);
    }

    public static final /* synthetic */ <T> T findLast(@NotNull LitePal findLast) {
        Intrinsics.checkParameterIsNotNull(findLast, "$this$findLast");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) LitePal.findLast(Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindExecutor<T> findLastAsync(@NotNull LitePal findLastAsync) {
        Intrinsics.checkParameterIsNotNull(findLastAsync, "$this$findLastAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findLastAsync(Object.class);
    }

    public static final /* synthetic */ <T> boolean isExist(@NotNull LitePal isExist, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(isExist, "$this$isExist");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.isExist(Object.class, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    public static final /* synthetic */ <T, R> R max(@NotNull LitePal max, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(max, "$this$max");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) LitePal.max((Class<?>) Object.class, columnName, Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T, R> FindExecutor<R> maxAsync(@NotNull LitePal maxAsync, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(maxAsync, "$this$maxAsync");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return LitePal.maxAsync((Class<?>) Object.class, columnName, Object.class);
    }

    public static final /* synthetic */ <T, R> R min(@NotNull LitePal min, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(min, "$this$min");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) LitePal.min((Class<?>) Object.class, columnName, Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T, R> FindExecutor<R> minAsync(@NotNull LitePal minAsync, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(minAsync, "$this$minAsync");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return LitePal.minAsync((Class<?>) Object.class, columnName, Object.class);
    }

    public static final synchronized boolean runInTransaction(@NotNull LitePal runInTransaction, @NotNull Function0<Boolean> block) {
        boolean zBooleanValue;
        Intrinsics.checkParameterIsNotNull(runInTransaction, "$this$runInTransaction");
        Intrinsics.checkParameterIsNotNull(block, "block");
        LitePal.beginTransaction();
        try {
            zBooleanValue = block.invoke().booleanValue();
        } catch (Exception unused) {
            zBooleanValue = false;
        }
        if (zBooleanValue) {
            LitePal.setTransactionSuccessful();
        }
        LitePal.endTransaction();
        return zBooleanValue;
    }

    public static final <T extends LitePalSupport> boolean saveAll(@NotNull Collection<? extends T> saveAll) {
        Intrinsics.checkParameterIsNotNull(saveAll, "$this$saveAll");
        return LitePal.saveAll(saveAll);
    }

    public static final /* synthetic */ <T, R> R sum(@NotNull LitePal sum, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) LitePal.sum((Class<?>) Object.class, columnName, Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T, R> FindExecutor<R> sumAsync(@NotNull LitePal sumAsync, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(sumAsync, "$this$sumAsync");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        Intrinsics.reifiedOperationMarker(4, "R");
        return LitePal.sumAsync((Class<?>) Object.class, columnName, Object.class);
    }

    public static final /* synthetic */ <T> int update(@NotNull LitePal update, @NotNull ContentValues values, long j2) {
        Intrinsics.checkParameterIsNotNull(update, "$this$update");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.update(Object.class, values, j2);
    }

    public static final /* synthetic */ <T> int updateAll(@NotNull LitePal updateAll, @NotNull ContentValues values, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(updateAll, "$this$updateAll");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.updateAll((Class<?>) Object.class, values, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> UpdateOrDeleteExecutor updateAllAsync(@NotNull LitePal updateAllAsync, @NotNull ContentValues values, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(updateAllAsync, "$this$updateAllAsync");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.updateAllAsync((Class<?>) Object.class, values, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> UpdateOrDeleteExecutor updateAsync(@NotNull LitePal updateAsync, @NotNull ContentValues values, long j2) {
        Intrinsics.checkParameterIsNotNull(updateAsync, "$this$updateAsync");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.updateAsync(Object.class, values, j2);
    }

    public static final /* synthetic */ <T> T find(@NotNull LitePal find, long j2, boolean z2) {
        Intrinsics.checkParameterIsNotNull(find, "$this$find");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) LitePal.find(Object.class, j2, z2);
    }

    public static final /* synthetic */ <T> List<T> findAll(@NotNull LitePal findAll, boolean z2, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(findAll, "$this$findAll");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findAll(Object.class, z2, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindMultiExecutor<T> findAllAsync(@NotNull LitePal findAllAsync, boolean z2, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(findAllAsync, "$this$findAllAsync");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findAllAsync(Object.class, z2, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> T findAsync(@NotNull LitePal findAsync, long j2, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findAsync, "$this$findAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) LitePal.find(Object.class, j2, z2);
    }

    public static final /* synthetic */ <T> T findFirst(@NotNull LitePal findFirst, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findFirst, "$this$findFirst");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) LitePal.findFirst(Object.class, z2);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindExecutor<T> findFirstAsync(@NotNull LitePal findFirstAsync, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findFirstAsync, "$this$findFirstAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findFirstAsync(Object.class, z2);
    }

    public static final /* synthetic */ <T> T findLast(@NotNull LitePal findLast, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findLast, "$this$findLast");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) LitePal.findLast(Object.class, z2);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <T> FindExecutor<T> findLastAsync(@NotNull LitePal findLastAsync, boolean z2) {
        Intrinsics.checkParameterIsNotNull(findLastAsync, "$this$findLastAsync");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return LitePal.findLastAsync(Object.class, z2);
    }

    public static final /* synthetic */ <R> R max(@NotNull LitePal max, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(max, "$this$max");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) LitePal.max(tableName, columnName, Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <R> FindExecutor<R> maxAsync(@NotNull LitePal maxAsync, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(maxAsync, "$this$maxAsync");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return LitePal.maxAsync(tableName, columnName, Object.class);
    }

    public static final /* synthetic */ <R> R min(@NotNull LitePal min, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(min, "$this$min");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) LitePal.min(tableName, columnName, Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <R> FindExecutor<R> minAsync(@NotNull LitePal minAsync, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(minAsync, "$this$minAsync");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return LitePal.minAsync(tableName, columnName, Object.class);
    }

    public static final /* synthetic */ <R> R sum(@NotNull LitePal sum, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(sum, "$this$sum");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return (R) LitePal.sum(tableName, columnName, Object.class);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    public static final /* synthetic */ <R> FindExecutor<R> sumAsync(@NotNull LitePal sumAsync, @NotNull String tableName, @NotNull String columnName) {
        Intrinsics.checkParameterIsNotNull(sumAsync, "$this$sumAsync");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.reifiedOperationMarker(4, "R");
        return LitePal.sumAsync(tableName, columnName, Object.class);
    }
}
