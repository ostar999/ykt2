package com.plv.thirdpart.litepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.plv.thirdpart.litepal.crud.LitePalSupport;
import com.plv.thirdpart.litepal.crud.async.AverageExecutor;
import com.plv.thirdpart.litepal.crud.async.CountExecutor;
import com.plv.thirdpart.litepal.crud.async.FindExecutor;
import com.plv.thirdpart.litepal.crud.async.FindMultiExecutor;
import com.plv.thirdpart.litepal.crud.async.SaveExecutor;
import com.plv.thirdpart.litepal.crud.async.UpdateOrDeleteExecutor;
import com.plv.thirdpart.litepal.tablemanager.callback.DatabaseListener;
import com.psychiatrygarden.utils.Constants;
import com.umeng.analytics.pro.d;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000Æ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001c\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J$\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J \u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\b\u0010\u0010\u001a\u00020\u0004H\u0007J\u0014\u0010\u0011\u001a\u00020\u00122\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u0006H\u0007J\u001c\u0010\u0013\u001a\n \u000f*\u0004\u0018\u00010\u00140\u00142\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0007J\u0018\u0010\u0013\u001a\n \u000f*\u0004\u0018\u00010\u00140\u00142\u0006\u0010\f\u001a\u00020\u0006H\u0007J\u001c\u0010\u0015\u001a\u00020\u00122\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J1\u0010\u0018\u001a\u00020\u00122\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010\u001bJ-\u0010\u0018\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u00062\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010\u001cJ9\u0010\u001d\u001a\n \u000f*\u0004\u0018\u00010\u001e0\u001e2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010\u001fJ5\u0010\u001d\u001a\n \u000f*\u0004\u0018\u00010\u001e0\u001e2\u0006\u0010\f\u001a\u00020\u00062\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010 J$\u0010!\u001a\n \u000f*\u0004\u0018\u00010\u001e0\u001e2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0006H\u0007J\b\u0010%\u001a\u00020\u0004H\u0007J1\u0010&\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007¢\u0006\u0002\u0010(J9\u0010&\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010)\u001a\u00020#H\u0007¢\u0006\u0002\u0010*JT\u0010+\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010-0,\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010)\u001a\u00020#2\n\u0010.\u001a\u00020/\"\u00020\u0017H\u0007JL\u0010+\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010-0,\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\n\u0010.\u001a\u00020/\"\u00020\u0017H\u0007JT\u00100\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010101\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010)\u001a\u00020#2\n\u0010.\u001a\u00020/\"\u00020\u0017H\u0007JL\u00100\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010101\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\n\u0010.\u001a\u00020/\"\u00020\u0017H\u0007JH\u00102\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007JP\u00102\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010)\u001a\u00020#H\u0007J)\u00104\u001a\n \u000f*\u0004\u0018\u000105052\u0012\u00106\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u001a\"\u00020\u0006H\u0007¢\u0006\u0002\u00107J)\u00108\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u00109J1\u00108\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010)\u001a\u00020#H\u0007¢\u0006\u0002\u0010:J@\u0010;\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007JH\u0010;\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010)\u001a\u00020#H\u0007J)\u0010<\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u00109J1\u0010<\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010)\u001a\u00020#H\u0007¢\u0006\u0002\u0010:J@\u0010=\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007JH\u0010=\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0006\u0010)\u001a\u00020#H\u0007J\b\u0010>\u001a\u00020?H\u0007J\u0010\u0010@\u001a\u00020\u00042\u0006\u0010A\u001a\u00020BH\u0007J9\u0010C\u001a\u00020#\"\u0004\b\u0000\u0010'2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H'0\n2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010DJ\u0018\u0010E\u001a\n \u000f*\u0004\u0018\u00010F0F2\u0006\u0010G\u001a\u00020\u0012H\u0007J \u0010H\u001a\u00020\u0004\"\b\b\u0000\u0010'*\u00020I2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H'0KH\u0007J=\u0010L\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u0010OJ9\u0010L\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u0010PJT\u0010Q\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007JP\u0010Q\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007J=\u0010R\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u0010OJ9\u0010R\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u0010PJT\u0010S\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007JP\u0010S\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007J\u0018\u0010T\u001a\n \u000f*\u0004\u0018\u00010F0F2\u0006\u0010G\u001a\u00020\u0012H\u0007J\u001a\u0010U\u001a\n \u000f*\u0004\u0018\u00010F0F2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006H\u0007J\u0010\u0010V\u001a\u00020\u00042\u0006\u0010W\u001a\u00020XH\u0007J \u0010Y\u001a\u00020#\"\b\b\u0000\u0010'*\u00020I2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H'0KH\u0007J(\u0010Z\u001a\n \u000f*\u0004\u0018\u00010[0[\"\b\b\u0000\u0010'*\u00020I2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H'0KH\u0007J-\u0010\\\u001a\n \u000f*\u0004\u0018\u00010F0F2\u0016\u0010]\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010^J\b\u0010_\u001a\u00020\u0004H\u0007J=\u0010`\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u0010OJ9\u0010`\u001a\n \u000f*\u0004\u0018\u0001H'H'\"\u0004\b\u0000\u0010'2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007¢\u0006\u0002\u0010PJT\u0010a\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007JP\u0010a\u001a&\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H' \u000f*\u0012\u0012\f\u0012\n \u000f*\u0004\u0018\u0001H'H'\u0018\u00010303\"\u0004\b\u0000\u0010'2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010M\u001a\u00020\u00062\f\u0010N\u001a\b\u0012\u0004\u0012\u0002H'0\nH\u0007J$\u0010b\u001a\u00020\u00122\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010c\u001a\u00020d2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J9\u0010e\u001a\u00020\u00122\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010c\u001a\u00020d2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010fJ5\u0010e\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010c\u001a\u00020d2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010gJA\u0010h\u001a\n \u000f*\u0004\u0018\u00010\u001e0\u001e2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010c\u001a\u00020d2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010iJ=\u0010h\u001a\n \u000f*\u0004\u0018\u00010\u001e0\u001e2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010c\u001a\u00020d2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010jJ,\u0010k\u001a\n \u000f*\u0004\u0018\u00010\u001e0\u001e2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010c\u001a\u00020d2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010l\u001a\u00020\u00042\u0006\u0010m\u001a\u00020nH\u0007J\b\u0010o\u001a\u00020\u0004H\u0007J-\u0010p\u001a\n \u000f*\u0004\u0018\u00010F0F2\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u001a\"\u0004\u0018\u00010\u0006H\u0007¢\u0006\u0002\u0010^¨\u0006q"}, d2 = {"Lcom/plv/thirdpart/litepal/LitePal;", "", "()V", "aesKey", "", "key", "", "average", "", "modelClass", "Ljava/lang/Class;", "column", "tableName", "averageAsync", "Lcom/plv/thirdpart/litepal/crud/async/AverageExecutor;", "kotlin.jvm.PlatformType", "beginTransaction", "count", "", "countAsync", "Lcom/plv/thirdpart/litepal/crud/async/CountExecutor;", RequestParameters.SUBRESOURCE_DELETE, "id", "", "deleteAll", "conditions", "", "(Ljava/lang/Class;[Ljava/lang/String;)I", "(Ljava/lang/String;[Ljava/lang/String;)I", "deleteAllAsync", "Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "(Ljava/lang/Class;[Ljava/lang/String;)Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "(Ljava/lang/String;[Ljava/lang/String;)Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "deleteAsync", "deleteDatabase", "", "dbName", "endTransaction", "find", ExifInterface.GPS_DIRECTION_TRUE, "(Ljava/lang/Class;J)Ljava/lang/Object;", "isEager", "(Ljava/lang/Class;JZ)Ljava/lang/Object;", "findAll", "", "", "ids", "", "findAllAsync", "Lcom/plv/thirdpart/litepal/crud/async/FindMultiExecutor;", "findAsync", "Lcom/plv/thirdpart/litepal/crud/async/FindExecutor;", "findBySQL", "Landroid/database/Cursor;", "sql", "([Ljava/lang/String;)Landroid/database/Cursor;", "findFirst", "(Ljava/lang/Class;)Ljava/lang/Object;", "(Ljava/lang/Class;Z)Ljava/lang/Object;", "findFirstAsync", "findLast", "findLastAsync", "getDatabase", "Landroid/database/sqlite/SQLiteDatabase;", "initialize", d.R, "Landroid/content/Context;", "isExist", "(Ljava/lang/Class;[Ljava/lang/String;)Z", "limit", "Lcom/plv/thirdpart/litepal/FluentQuery;", "value", "markAsDeleted", "Lcom/plv/thirdpart/litepal/crud/LitePalSupport;", "collection", "", "max", "columnName", "columnType", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "maxAsync", "min", "minAsync", "offset", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_DETAIL_ORDER, "registerDatabaseListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lcom/plv/thirdpart/litepal/tablemanager/callback/DatabaseListener;", "saveAll", "saveAllAsync", "Lcom/plv/thirdpart/litepal/crud/async/SaveExecutor;", "select", "columns", "([Ljava/lang/String;)Lcom/plv/thirdpart/litepal/FluentQuery;", "setTransactionSuccessful", "sum", "sumAsync", PLVUpdateMicSiteEvent.EVENT_NAME, "values", "Landroid/content/ContentValues;", "updateAll", "(Ljava/lang/Class;Landroid/content/ContentValues;[Ljava/lang/String;)I", "(Ljava/lang/String;Landroid/content/ContentValues;[Ljava/lang/String;)I", "updateAllAsync", "(Ljava/lang/Class;Landroid/content/ContentValues;[Ljava/lang/String;)Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "(Ljava/lang/String;Landroid/content/ContentValues;[Ljava/lang/String;)Lcom/plv/thirdpart/litepal/crud/async/UpdateOrDeleteExecutor;", "updateAsync", "use", "litePalDB", "Lcom/plv/thirdpart/litepal/LitePalDB;", "useDefault", "where", "polyvSDKFoundation_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class LitePal {
    public static final LitePal INSTANCE = new LitePal();

    private LitePal() {
    }

    @JvmStatic
    public static final void aesKey(@NotNull String key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Operator.aesKey(key);
    }

    @JvmStatic
    public static final double average(@NotNull Class<?> modelClass, @NotNull String column) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(column, "column");
        return Operator.average(modelClass, column);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final AverageExecutor averageAsync(@NotNull Class<?> modelClass, @NotNull String column) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(column, "column");
        return Operator.averageAsync(modelClass, column);
    }

    @JvmStatic
    public static final void beginTransaction() {
        Operator.beginTransaction();
    }

    @JvmStatic
    public static final int count(@NotNull Class<?> modelClass) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.count(modelClass);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final CountExecutor countAsync(@NotNull Class<?> modelClass) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.countAsync(modelClass);
    }

    @JvmStatic
    public static final int delete(@NotNull Class<?> modelClass, long id) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.delete(modelClass, id);
    }

    @JvmStatic
    public static final int deleteAll(@NotNull Class<?> modelClass, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.deleteAll(modelClass, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final UpdateOrDeleteExecutor deleteAllAsync(@NotNull Class<?> modelClass, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.deleteAllAsync(modelClass, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final UpdateOrDeleteExecutor deleteAsync(@NotNull Class<?> modelClass, long id) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.deleteAsync(modelClass, id);
    }

    @JvmStatic
    public static final boolean deleteDatabase(@NotNull String dbName) {
        Intrinsics.checkParameterIsNotNull(dbName, "dbName");
        return Operator.deleteDatabase(dbName);
    }

    @JvmStatic
    public static final void endTransaction() {
        Operator.endTransaction();
    }

    @JvmStatic
    public static final <T> T find(@NotNull Class<T> modelClass, long id) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return (T) Operator.find(modelClass, id);
    }

    @JvmStatic
    public static final <T> List<T> findAll(@NotNull Class<T> modelClass, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        return Operator.findAll(modelClass, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindMultiExecutor<T> findAllAsync(@NotNull Class<T> modelClass, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        return Operator.findAllAsync(modelClass, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> findAsync(@NotNull Class<T> modelClass, long id) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.findAsync(modelClass, id);
    }

    @JvmStatic
    public static final Cursor findBySQL(@NotNull String... sql) {
        Intrinsics.checkParameterIsNotNull(sql, "sql");
        return Operator.findBySQL((String[]) Arrays.copyOf(sql, sql.length));
    }

    @JvmStatic
    public static final <T> T findFirst(@NotNull Class<T> modelClass) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return (T) Operator.findFirst(modelClass);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> findFirstAsync(@NotNull Class<T> modelClass) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.findFirstAsync(modelClass);
    }

    @JvmStatic
    public static final <T> T findLast(@NotNull Class<T> modelClass) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return (T) Operator.findLast(modelClass);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> findLastAsync(@NotNull Class<T> modelClass) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.findLastAsync(modelClass);
    }

    @JvmStatic
    @NotNull
    public static final SQLiteDatabase getDatabase() {
        SQLiteDatabase database = Operator.getDatabase();
        Intrinsics.checkExpressionValueIsNotNull(database, "Operator.getDatabase()");
        return database;
    }

    @JvmStatic
    public static final void initialize(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Operator.initialize(context);
    }

    @JvmStatic
    public static final <T> boolean isExist(@NotNull Class<T> modelClass, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.isExist(modelClass, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @JvmStatic
    public static final FluentQuery limit(int value) {
        return Operator.limit(value);
    }

    @JvmStatic
    public static final <T extends LitePalSupport> void markAsDeleted(@NotNull Collection<? extends T> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "collection");
        Operator.markAsDeleted(collection);
    }

    @JvmStatic
    public static final <T> T max(@NotNull Class<?> modelClass, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return (T) Operator.max(modelClass, columnName, columnType);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> maxAsync(@NotNull Class<?> modelClass, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return Operator.maxAsync(modelClass, columnName, columnType);
    }

    @JvmStatic
    public static final <T> T min(@NotNull Class<?> modelClass, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return (T) Operator.min(modelClass, columnName, columnType);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> minAsync(@NotNull Class<?> modelClass, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return Operator.minAsync(modelClass, columnName, columnType);
    }

    @JvmStatic
    public static final FluentQuery offset(int value) {
        return Operator.offset(value);
    }

    @JvmStatic
    public static final FluentQuery order(@Nullable String column) {
        return Operator.order(column);
    }

    @JvmStatic
    public static final void registerDatabaseListener(@NotNull DatabaseListener listener) {
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        Operator.registerDatabaseListener(listener);
    }

    @JvmStatic
    public static final <T extends LitePalSupport> boolean saveAll(@NotNull Collection<? extends T> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "collection");
        return Operator.saveAll(collection);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T extends LitePalSupport> SaveExecutor saveAllAsync(@NotNull Collection<? extends T> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "collection");
        return Operator.saveAllAsync(collection);
    }

    @JvmStatic
    public static final FluentQuery select(@NotNull String... columns) {
        Intrinsics.checkParameterIsNotNull(columns, "columns");
        return Operator.select((String[]) Arrays.copyOf(columns, columns.length));
    }

    @JvmStatic
    public static final void setTransactionSuccessful() {
        Operator.setTransactionSuccessful();
    }

    @JvmStatic
    public static final <T> T sum(@NotNull Class<?> modelClass, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return (T) Operator.sum(modelClass, columnName, columnType);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> sumAsync(@NotNull Class<?> modelClass, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return Operator.sumAsync(modelClass, columnName, columnType);
    }

    @JvmStatic
    public static final int update(@NotNull Class<?> modelClass, @NotNull ContentValues values, long id) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(values, "values");
        return Operator.update(modelClass, values, id);
    }

    @JvmStatic
    public static final int updateAll(@NotNull Class<?> modelClass, @NotNull ContentValues values, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.updateAll(modelClass, values, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final UpdateOrDeleteExecutor updateAllAsync(@NotNull Class<?> modelClass, @NotNull ContentValues values, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.updateAllAsync(modelClass, values, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final UpdateOrDeleteExecutor updateAsync(@NotNull Class<?> modelClass, @NotNull ContentValues values, long id) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(values, "values");
        return Operator.updateAsync(modelClass, values, id);
    }

    @JvmStatic
    public static final void use(@NotNull LitePalDB litePalDB) {
        Intrinsics.checkParameterIsNotNull(litePalDB, "litePalDB");
        Operator.use(litePalDB);
    }

    @JvmStatic
    public static final void useDefault() {
        Operator.useDefault();
    }

    @JvmStatic
    public static final FluentQuery where(@NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.where((String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @JvmStatic
    public static final double average(@NotNull String tableName, @NotNull String column) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(column, "column");
        return Operator.average(tableName, column);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final AverageExecutor averageAsync(@NotNull String tableName, @NotNull String column) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(column, "column");
        return Operator.averageAsync(tableName, column);
    }

    @JvmStatic
    public static final int count(@NotNull String tableName) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        return Operator.count(tableName);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final CountExecutor countAsync(@NotNull String tableName) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        return Operator.countAsync(tableName);
    }

    @JvmStatic
    public static final int deleteAll(@NotNull String tableName, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.deleteAll(tableName, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final UpdateOrDeleteExecutor deleteAllAsync(@NotNull String tableName, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.deleteAllAsync(tableName, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @JvmStatic
    public static final <T> T find(@NotNull Class<T> modelClass, long id, boolean isEager) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return (T) Operator.find(modelClass, id, isEager);
    }

    @JvmStatic
    public static final <T> List<T> findAll(@NotNull Class<T> modelClass, boolean isEager, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        return Operator.findAll(modelClass, isEager, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindMultiExecutor<T> findAllAsync(@NotNull Class<T> modelClass, boolean isEager, @NotNull long... ids) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        Intrinsics.checkParameterIsNotNull(ids, "ids");
        return Operator.findAllAsync(modelClass, isEager, Arrays.copyOf(ids, ids.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> findAsync(@NotNull Class<T> modelClass, long id, boolean isEager) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.findAsync(modelClass, id, isEager);
    }

    @JvmStatic
    public static final <T> T findFirst(@NotNull Class<T> modelClass, boolean isEager) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return (T) Operator.findFirst(modelClass, isEager);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> findFirstAsync(@NotNull Class<T> modelClass, boolean isEager) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.findFirstAsync(modelClass, isEager);
    }

    @JvmStatic
    public static final <T> T findLast(@NotNull Class<T> modelClass, boolean isEager) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return (T) Operator.findLast(modelClass, isEager);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> findLastAsync(@NotNull Class<T> modelClass, boolean isEager) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return Operator.findLastAsync(modelClass, isEager);
    }

    @JvmStatic
    public static final <T> T max(@NotNull String tableName, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return (T) Operator.max(tableName, columnName, columnType);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> maxAsync(@NotNull String tableName, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return Operator.maxAsync(tableName, columnName, columnType);
    }

    @JvmStatic
    public static final <T> T min(@NotNull String tableName, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return (T) Operator.min(tableName, columnName, columnType);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> minAsync(@NotNull String tableName, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return Operator.minAsync(tableName, columnName, columnType);
    }

    @JvmStatic
    public static final <T> T sum(@NotNull String tableName, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return (T) Operator.sum(tableName, columnName, columnType);
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final <T> FindExecutor<T> sumAsync(@NotNull String tableName, @NotNull String columnName, @NotNull Class<T> columnType) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(columnName, "columnName");
        Intrinsics.checkParameterIsNotNull(columnType, "columnType");
        return Operator.sumAsync(tableName, columnName, columnType);
    }

    @JvmStatic
    public static final int updateAll(@NotNull String tableName, @NotNull ContentValues values, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.updateAll(tableName, values, (String[]) Arrays.copyOf(conditions, conditions.length));
    }

    @Deprecated(message = "This method is deprecated and will be removed in the future releases.", replaceWith = @ReplaceWith(expression = "Handle async db operation in your own logic instead.", imports = {}))
    @JvmStatic
    public static final UpdateOrDeleteExecutor updateAllAsync(@NotNull String tableName, @NotNull ContentValues values, @NotNull String... conditions) {
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(conditions, "conditions");
        return Operator.updateAllAsync(tableName, values, (String[]) Arrays.copyOf(conditions, conditions.length));
    }
}
