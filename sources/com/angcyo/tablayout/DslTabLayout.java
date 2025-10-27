package com.angcyo.tablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.xiaomi.mipush.sdk.Constants;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt__StringNumberConversionsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0098\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0002°\u0002B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010Ì\u0001\u001a\u00030Í\u00012\u0007\u0010Î\u0001\u001a\u00020\b2\u0007\u0010Ï\u0001\u001a\u00020\bJ\u0007\u0010Ð\u0001\u001a\u00020\bJ\u0007\u0010Ñ\u0001\u001a\u00020\bJ\b\u0010Ò\u0001\u001a\u00030Í\u0001J\u0012\u0010Ó\u0001\u001a\u00030Í\u00012\b\u0010\u0093\u0001\u001a\u00030Ô\u0001J\u001a\u0010Õ\u0001\u001a\u00030Í\u00012\u0007\u0010\u0087\u0001\u001a\u00020\b2\u0007\u0010Ö\u0001\u001a\u00020BJ\n\u0010×\u0001\u001a\u00030Í\u0001H\u0016J(\u0010Ø\u0001\u001a\u00030Í\u00012\u001e\b\u0002\u0010Ù\u0001\u001a\u0017\u0012\u0005\u0012\u00030Æ\u0001\u0012\u0005\u0012\u00030Í\u00010Ú\u0001¢\u0006\u0003\bÛ\u0001J\u0014\u0010Ü\u0001\u001a\u00030Í\u00012\b\u0010Ý\u0001\u001a\u00030Þ\u0001H\u0016J&\u0010ß\u0001\u001a\u00020B2\b\u0010Ý\u0001\u001a\u00030Þ\u00012\u0007\u0010\u0084\u0001\u001a\u00020>2\b\u0010à\u0001\u001a\u00030Á\u0001H\u0014J\n\u0010á\u0001\u001a\u00030â\u0001H\u0014J\u0015\u0010ã\u0001\u001a\u00030â\u00012\t\u0010ä\u0001\u001a\u0004\u0018\u00010\u0005H\u0016J\u0016\u0010ã\u0001\u001a\u00030â\u00012\n\u0010å\u0001\u001a\u0005\u0018\u00010â\u0001H\u0014J\u0011\u0010æ\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u0087\u0001\u001a\u00020\bJ\u0007\u0010ç\u0001\u001a\u00020BJ5\u0010è\u0001\u001a\u00030Í\u00012\u0007\u0010é\u0001\u001a\u00020B2\u0007\u0010ê\u0001\u001a\u00020\b2\u0007\u0010ë\u0001\u001a\u00020\b2\u0007\u0010ì\u0001\u001a\u00020\b2\u0007\u0010í\u0001\u001a\u00020\bJ5\u0010î\u0001\u001a\u00030Í\u00012\u0007\u0010é\u0001\u001a\u00020B2\u0007\u0010ê\u0001\u001a\u00020\b2\u0007\u0010ë\u0001\u001a\u00020\b2\u0007\u0010ì\u0001\u001a\u00020\b2\u0007\u0010í\u0001\u001a\u00020\bJ\u001a\u0010ï\u0001\u001a\u00030Í\u00012\u0007\u0010ð\u0001\u001a\u00020\b2\u0007\u0010ñ\u0001\u001a\u00020\bJ\u001a\u0010ò\u0001\u001a\u00030Í\u00012\u0007\u0010ð\u0001\u001a\u00020\b2\u0007\u0010ñ\u0001\u001a\u00020\bJ\u0099\u0001\u0010ó\u0001\u001a\u00030Í\u00012\u001e\b\u0002\u0010Ù\u0001\u001a\u0017\u0012\u0005\u0012\u00030Æ\u0001\u0012\u0005\u0012\u00030Í\u00010Ú\u0001¢\u0006\u0003\bÛ\u00012o\u0010ô\u0001\u001aj\u0012\u0016\u0012\u00140\b¢\u0006\u000f\b\u0082\u0001\u0012\n\b\u0083\u0001\u0012\u0005\b\b(Î\u0001\u0012\u0016\u0012\u00140\b¢\u0006\u000f\b\u0082\u0001\u0012\n\b\u0083\u0001\u0012\u0005\b\b(Ï\u0001\u0012\u0016\u0012\u00140B¢\u0006\u000f\b\u0082\u0001\u0012\n\b\u0083\u0001\u0012\u0005\b\b(ö\u0001\u0012\u0016\u0012\u00140B¢\u0006\u000f\b\u0082\u0001\u0012\n\b\u0083\u0001\u0012\u0005\b\b(÷\u0001\u0012\u0005\u0012\u00030Í\u00010õ\u0001J\n\u0010ø\u0001\u001a\u00030Í\u0001H\u0014J\n\u0010ù\u0001\u001a\u00030Í\u0001H\u0014J\u0014\u0010ú\u0001\u001a\u00030Í\u00012\b\u0010Ý\u0001\u001a\u00030Þ\u0001H\u0014J\n\u0010û\u0001\u001a\u00030Í\u0001H\u0014J\u0014\u0010ü\u0001\u001a\u00030Í\u00012\b\u0010ý\u0001\u001a\u00030Ô\u0001H\u0016J\u0013\u0010þ\u0001\u001a\u00020B2\b\u0010ÿ\u0001\u001a\u00030\u0080\u0002H\u0016J7\u0010\u0081\u0002\u001a\u00030Í\u00012\u0007\u0010é\u0001\u001a\u00020B2\u0007\u0010ê\u0001\u001a\u00020\b2\u0007\u0010ë\u0001\u001a\u00020\b2\u0007\u0010ì\u0001\u001a\u00020\b2\u0007\u0010í\u0001\u001a\u00020\bH\u0014J\u001c\u0010\u0082\u0002\u001a\u00030Í\u00012\u0007\u0010ð\u0001\u001a\u00020\b2\u0007\u0010ñ\u0001\u001a\u00020\bH\u0014J\u0011\u0010\u0083\u0002\u001a\u00030Í\u00012\u0007\u0010\u0084\u0002\u001a\u00020\bJ$\u0010\u0085\u0002\u001a\u00030Í\u00012\u0007\u0010\u0086\u0002\u001a\u00020\b2\b\u0010\u0087\u0002\u001a\u00030Ô\u00012\u0007\u0010\u0088\u0002\u001a\u00020\bJ\u0011\u0010\u0089\u0002\u001a\u00030Í\u00012\u0007\u0010\u0086\u0002\u001a\u00020\bJ\u0016\u0010\u008a\u0002\u001a\u00030Í\u00012\n\u0010\u0084\u0002\u001a\u0005\u0018\u00010\u008b\u0002H\u0014J\u0013\u0010\u008c\u0002\u001a\u00030Í\u00012\u0007\u0010\u008d\u0002\u001a\u00020\bH\u0016J\f\u0010\u008e\u0002\u001a\u0005\u0018\u00010\u008b\u0002H\u0014J\u0013\u0010\u008f\u0002\u001a\u00020B2\b\u0010\u0090\u0002\u001a\u00030Ô\u0001H\u0016J.\u0010\u0091\u0002\u001a\u00030Í\u00012\u0007\u0010\u0092\u0002\u001a\u00020\b2\u0007\u0010\u0093\u0002\u001a\u00020\b2\u0007\u0010\u0094\u0002\u001a\u00020\b2\u0007\u0010\u0095\u0002\u001a\u00020\bH\u0014J\u0013\u0010\u0096\u0002\u001a\u00020B2\b\u0010\u0097\u0002\u001a\u00030\u0080\u0002H\u0016J\u0015\u0010\u0098\u0002\u001a\u00030Í\u00012\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010>H\u0016J\u0015\u0010\u0099\u0002\u001a\u00030Í\u00012\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010>H\u0016J\b\u0010\u009a\u0002\u001a\u00030Í\u0001J\u001c\u0010\u009b\u0002\u001a\u00030Í\u00012\u0007\u0010\u009c\u0002\u001a\u00020\b2\u0007\u0010\u009d\u0002\u001a\u00020\bH\u0016J'\u0010\u009e\u0002\u001a\u00030Í\u00012\u0007\u0010\u0087\u0001\u001a\u00020\b2\t\b\u0002\u0010\u009f\u0002\u001a\u00020B2\t\b\u0002\u0010÷\u0001\u001a\u00020BJ4\u0010Ê\u0001\u001a\u00030Í\u00012\n\b\u0002\u0010Ù\u0001\u001a\u00030Æ\u00012\u001e\b\u0002\u0010 \u0002\u001a\u0017\u0012\u0005\u0012\u00030Æ\u0001\u0012\u0005\u0012\u00030Í\u00010Ú\u0001¢\u0006\u0003\bÛ\u0001J\u0011\u0010¡\u0002\u001a\u00030Í\u00012\u0007\u0010¢\u0002\u001a\u000201J#\u0010£\u0002\u001a\u00030Í\u00012\u0007\u0010ý\u0001\u001a\u00020\b2\u0007\u0010¤\u0002\u001a\u00020\b2\u0007\u0010¥\u0002\u001a\u00020\bJ\u0011\u0010¦\u0002\u001a\u00030Í\u00012\u0007\u0010§\u0002\u001a\u00020\bJ/\u0010¨\u0002\u001a\u00030Í\u00012\u0007\u0010\u0087\u0001\u001a\u00020\b2\u001c\u0010Ù\u0001\u001a\u0017\u0012\u0005\u0012\u00030\u0088\u0001\u0012\u0005\u0012\u00030Í\u00010Ú\u0001¢\u0006\u0003\bÛ\u0001J\u001d\u0010¨\u0002\u001a\u00030Í\u00012\u0007\u0010\u0087\u0001\u001a\u00020\b2\n\u0010©\u0002\u001a\u0005\u0018\u00010ª\u0002J\n\u0010«\u0002\u001a\u00030Í\u0001H\u0016J\u0013\u0010¬\u0002\u001a\u00020B2\b\u0010\u00ad\u0002\u001a\u00030£\u0001H\u0014J\u001e\u0010®\u0002\u001a\u00030Í\u0001*\u00030Þ\u00012\u000f\u0010ô\u0001\u001a\n\u0012\u0005\u0012\u00030Í\u00010¯\u0002R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0013\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\fR\u001a\u0010\u0016\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\n\"\u0004\b\u0018\u0010\fR\u001a\u0010\u0019\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\n\"\u0004\b\u001b\u0010\fR\u001a\u0010\u001c\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\n\"\u0004\b\u001e\u0010\fR\u001b\u0010\u001f\u001a\u00020 8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0012\u001a\u0004\b!\u0010\"R\u001b\u0010$\u001a\u00020%8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b(\u0010\u0012\u001a\u0004\b&\u0010'R\u0011\u0010)\u001a\u00020*¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\n\"\u0004\b/\u0010\fR\u001c\u00100\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001a\u00106\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\n\"\u0004\b8\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010;\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b<\u0010\nR\u0013\u0010=\u001a\u0004\u0018\u00010>8F¢\u0006\u0006\u001a\u0004\b?\u0010@R\u001a\u0010A\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010D\"\u0004\bI\u0010FR\u001a\u0010J\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010D\"\u0004\bL\u0010FR\u001a\u0010M\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010D\"\u0004\bO\u0010FR\u001a\u0010P\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010D\"\u0004\bR\u0010FR\u001b\u0010S\u001a\u00020T8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bW\u0010\u0012\u001a\u0004\bU\u0010VR\u0011\u0010X\u001a\u00020B8F¢\u0006\u0006\u001a\u0004\bX\u0010DR\u0011\u0010Y\u001a\u00020B8F¢\u0006\u0006\u001a\u0004\bY\u0010DR\u001a\u0010Z\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010D\"\u0004\b\\\u0010FR\u001a\u0010]\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010\n\"\u0004\b_\u0010\fR\u001a\u0010`\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\ba\u0010D\"\u0004\bb\u0010FR\u001c\u0010c\u001a\u0004\u0018\u00010dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010f\"\u0004\bg\u0010hR\u001a\u0010i\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010D\"\u0004\bk\u0010FR\u001a\u0010l\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bm\u0010\n\"\u0004\bn\u0010\fR\u001a\u0010o\u001a\u00020BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010D\"\u0004\bq\u0010FR\u0011\u0010r\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bs\u0010\nR\u0011\u0010t\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bu\u0010\nR\u0011\u0010v\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\bw\u0010\nR\u0011\u0010x\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\by\u0010\nR\u0011\u0010z\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b{\u0010\nR\u0011\u0010|\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b}\u0010\nR\u0011\u0010~\u001a\u00020B8F¢\u0006\u0006\u001a\u0004\b\u007f\u0010DRr\u0010\u0080\u0001\u001aU\u0012\u0016\u0012\u00140>¢\u0006\u000f\b\u0082\u0001\u0012\n\b\u0083\u0001\u0012\u0005\b\b(\u0084\u0001\u0012\u0017\u0012\u00150\u0085\u0001¢\u0006\u000f\b\u0082\u0001\u0012\n\b\u0083\u0001\u0012\u0005\b\b(\u0086\u0001\u0012\u0016\u0012\u00140\b¢\u0006\u000f\b\u0082\u0001\u0012\n\b\u0083\u0001\u0012\u0005\b\b(\u0087\u0001\u0012\u0007\u0012\u0005\u0018\u00010\u0088\u00010\u0081\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0089\u0001\u0010\u008a\u0001\"\u0006\b\u008b\u0001\u0010\u008c\u0001R\u001d\u0010\u008d\u0001\u001a\u00020\bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008e\u0001\u0010\n\"\u0005\b\u008f\u0001\u0010\fR\u001d\u0010\u0090\u0001\u001a\u00020\bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0091\u0001\u0010\n\"\u0005\b\u0092\u0001\u0010\fR0\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0085\u00012\n\u0010\u0093\u0001\u001a\u0005\u0018\u00010\u0085\u0001@FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001\"\u0006\b\u0096\u0001\u0010\u0097\u0001R\"\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0005\u0012\u00030\u0088\u00010\u0099\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001R0\u0010\u009d\u0001\u001a\u0005\u0018\u00010\u009c\u00012\n\u0010\u0093\u0001\u001a\u0005\u0018\u00010\u009c\u0001@FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u009e\u0001\u0010\u009f\u0001\"\u0006\b \u0001\u0010¡\u0001R\"\u0010¢\u0001\u001a\u0005\u0018\u00010£\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b¤\u0001\u0010¥\u0001\"\u0006\b¦\u0001\u0010§\u0001R\u001d\u0010¨\u0001\u001a\u00020\bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b©\u0001\u0010\n\"\u0005\bª\u0001\u0010\fR0\u0010¬\u0001\u001a\u0005\u0018\u00010«\u00012\n\u0010\u0093\u0001\u001a\u0005\u0018\u00010«\u0001@FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u00ad\u0001\u0010®\u0001\"\u0006\b¯\u0001\u0010°\u0001R\u001d\u0010±\u0001\u001a\u00020BX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b²\u0001\u0010D\"\u0005\b³\u0001\u0010FR0\u0010µ\u0001\u001a\u0005\u0018\u00010´\u00012\n\u0010\u0093\u0001\u001a\u0005\u0018\u00010´\u0001@FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b¶\u0001\u0010·\u0001\"\u0006\b¸\u0001\u0010¹\u0001R,\u0010»\u0001\u001a\u00030º\u00012\b\u0010\u0093\u0001\u001a\u00030º\u0001@FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b¼\u0001\u0010½\u0001\"\u0006\b¾\u0001\u0010¿\u0001R \u0010À\u0001\u001a\u00030Á\u0001X\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÂ\u0001\u0010Ã\u0001\"\u0006\bÄ\u0001\u0010Å\u0001R0\u0010Ç\u0001\u001a\u0005\u0018\u00010Æ\u00012\n\u0010\u0093\u0001\u001a\u0005\u0018\u00010Æ\u0001@FX\u0086\u000e¢\u0006\u0012\n\u0000\u001a\u0006\bÈ\u0001\u0010É\u0001\"\u0006\bÊ\u0001\u0010Ë\u0001¨\u0006±\u0002"}, d2 = {"Lcom/angcyo/tablayout/DslTabLayout;", "Landroid/view/ViewGroup;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "_childAllWidthSum", "", "get_childAllWidthSum", "()I", "set_childAllWidthSum", "(I)V", "_gestureDetector", "Landroidx/core/view/GestureDetectorCompat;", "get_gestureDetector", "()Landroidx/core/view/GestureDetectorCompat;", "_gestureDetector$delegate", "Lkotlin/Lazy;", "_layoutDirection", "get_layoutDirection", "set_layoutDirection", "_maxConvexHeight", "get_maxConvexHeight", "set_maxConvexHeight", "_maxFlingVelocity", "get_maxFlingVelocity", "set_maxFlingVelocity", "_minFlingVelocity", "get_minFlingVelocity", "set_minFlingVelocity", "_overScroller", "Landroid/widget/OverScroller;", "get_overScroller", "()Landroid/widget/OverScroller;", "_overScroller$delegate", "_scrollAnimator", "Landroid/animation/ValueAnimator;", "get_scrollAnimator", "()Landroid/animation/ValueAnimator;", "_scrollAnimator$delegate", "_tempRect", "Landroid/graphics/Rect;", "get_tempRect", "()Landroid/graphics/Rect;", "_touchSlop", "get_touchSlop", "set_touchSlop", "_viewPagerDelegate", "Lcom/angcyo/tablayout/ViewPagerDelegate;", "get_viewPagerDelegate", "()Lcom/angcyo/tablayout/ViewPagerDelegate;", "set_viewPagerDelegate", "(Lcom/angcyo/tablayout/ViewPagerDelegate;)V", "_viewPagerScrollState", "get_viewPagerScrollState", "set_viewPagerScrollState", "getAttributeSet", "()Landroid/util/AttributeSet;", "currentItemIndex", "getCurrentItemIndex", "currentItemView", "Landroid/view/View;", "getCurrentItemView", "()Landroid/view/View;", "drawBadge", "", "getDrawBadge", "()Z", "setDrawBadge", "(Z)V", "drawBorder", "getDrawBorder", "setDrawBorder", "drawDivider", "getDrawDivider", "setDrawDivider", "drawHighlight", "getDrawHighlight", "setDrawHighlight", "drawIndicator", "getDrawIndicator", "setDrawIndicator", "dslSelector", "Lcom/angcyo/tablayout/DslSelector;", "getDslSelector", "()Lcom/angcyo/tablayout/DslSelector;", "dslSelector$delegate", "isAnimatorStart", "isLayoutRtl", "itemAutoEquWidth", "getItemAutoEquWidth", "setItemAutoEquWidth", "itemDefaultHeight", "getItemDefaultHeight", "setItemDefaultHeight", "itemEnableSelector", "getItemEnableSelector", "setItemEnableSelector", "itemEquWidthCountRange", "Lkotlin/ranges/IntRange;", "getItemEquWidthCountRange", "()Lkotlin/ranges/IntRange;", "setItemEquWidthCountRange", "(Lkotlin/ranges/IntRange;)V", "itemIsEquWidth", "getItemIsEquWidth", "setItemIsEquWidth", "itemWidth", "getItemWidth", "setItemWidth", "layoutScrollAnim", "getLayoutScrollAnim", "setLayoutScrollAnim", "maxHeight", "getMaxHeight", "maxScrollX", "getMaxScrollX", "maxScrollY", "getMaxScrollY", "maxWidth", "getMaxWidth", "minScrollX", "getMinScrollX", "minScrollY", "getMinScrollY", "needScroll", "getNeedScroll", "onTabBadgeConfig", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "child", "Lcom/angcyo/tablayout/DslTabBadge;", "tabBadge", "index", "Lcom/angcyo/tablayout/TabBadgeConfig;", "getOnTabBadgeConfig", "()Lkotlin/jvm/functions/Function3;", "setOnTabBadgeConfig", "(Lkotlin/jvm/functions/Function3;)V", "orientation", "getOrientation", "setOrientation", "scrollAnimDuration", "getScrollAnimDuration", "setScrollAnimDuration", "value", "getTabBadge", "()Lcom/angcyo/tablayout/DslTabBadge;", "setTabBadge", "(Lcom/angcyo/tablayout/DslTabBadge;)V", "tabBadgeConfigMap", "", "getTabBadgeConfigMap", "()Ljava/util/Map;", "Lcom/angcyo/tablayout/DslTabBorder;", "tabBorder", "getTabBorder", "()Lcom/angcyo/tablayout/DslTabBorder;", "setTabBorder", "(Lcom/angcyo/tablayout/DslTabBorder;)V", "tabConvexBackgroundDrawable", "Landroid/graphics/drawable/Drawable;", "getTabConvexBackgroundDrawable", "()Landroid/graphics/drawable/Drawable;", "setTabConvexBackgroundDrawable", "(Landroid/graphics/drawable/Drawable;)V", "tabDefaultIndex", "getTabDefaultIndex", "setTabDefaultIndex", "Lcom/angcyo/tablayout/DslTabDivider;", "tabDivider", "getTabDivider", "()Lcom/angcyo/tablayout/DslTabDivider;", "setTabDivider", "(Lcom/angcyo/tablayout/DslTabDivider;)V", "tabEnableSelectorMode", "getTabEnableSelectorMode", "setTabEnableSelectorMode", "Lcom/angcyo/tablayout/DslTabHighlight;", "tabHighlight", "getTabHighlight", "()Lcom/angcyo/tablayout/DslTabHighlight;", "setTabHighlight", "(Lcom/angcyo/tablayout/DslTabHighlight;)V", "Lcom/angcyo/tablayout/DslTabIndicator;", "tabIndicator", "getTabIndicator", "()Lcom/angcyo/tablayout/DslTabIndicator;", "setTabIndicator", "(Lcom/angcyo/tablayout/DslTabIndicator;)V", "tabIndicatorAnimationDuration", "", "getTabIndicatorAnimationDuration", "()J", "setTabIndicatorAnimationDuration", "(J)V", "Lcom/angcyo/tablayout/DslTabLayoutConfig;", "tabLayoutConfig", "getTabLayoutConfig", "()Lcom/angcyo/tablayout/DslTabLayoutConfig;", "setTabLayoutConfig", "(Lcom/angcyo/tablayout/DslTabLayoutConfig;)V", "_animateToItem", "", "fromIndex", "toIndex", "_getViewTargetX", "_getViewTargetY", "_onAnimateEnd", "_onAnimateValue", "", "_scrollToTarget", "scrollAnim", "computeScroll", "configTabLayoutConfig", "config", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "draw", "canvas", "Landroid/graphics/Canvas;", "drawChild", "drawingTime", "generateDefaultLayoutParams", "Landroid/view/ViewGroup$LayoutParams;", "generateLayoutParams", "attrs", "p", "getBadgeConfig", "isHorizontal", "layoutHorizontal", "changed", NotifyType.LIGHTS, "t", "r", "b", "layoutVertical", "measureHorizontal", "widthMeasureSpec", "heightMeasureSpec", "measureVertical", "observeIndexChange", "action", "Lkotlin/Function4;", "reselect", "fromUser", "onAttachedToWindow", "onDetachedFromWindow", "onDraw", "onFinishInflate", "onFlingChange", "velocity", "onInterceptTouchEvent", "ev", "Landroid/view/MotionEvent;", "onLayout", "onMeasure", "onPageScrollStateChanged", "state", "onPageScrolled", "position", "positionOffset", "positionOffsetPixels", "onPageSelected", "onRestoreInstanceState", "Landroid/os/Parcelable;", "onRtlPropertiesChanged", "layoutDirection", "onSaveInstanceState", "onScrollChange", "distance", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", NotificationCompat.CATEGORY_EVENT, "onViewAdded", "onViewRemoved", "restoreScroll", "scrollTo", "x", "y", "setCurrentItem", "notify", "doIt", "setupViewPager", "viewPagerDelegate", "startFling", "min", "max", "startScroll", "dv", "updateTabBadge", "badgeText", "", "updateTabLayout", "verifyDrawable", "who", "holdLocation", "Lkotlin/Function0;", "LayoutParams", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslTabLayout extends ViewGroup {
    private int _childAllWidthSum;

    /* renamed from: _gestureDetector$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy _gestureDetector;
    private int _layoutDirection;
    private int _maxConvexHeight;
    private int _maxFlingVelocity;
    private int _minFlingVelocity;

    /* renamed from: _overScroller$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy _overScroller;

    /* renamed from: _scrollAnimator$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy _scrollAnimator;

    @NotNull
    private final Rect _tempRect;
    private int _touchSlop;

    @Nullable
    private ViewPagerDelegate _viewPagerDelegate;
    private int _viewPagerScrollState;

    @Nullable
    private final AttributeSet attributeSet;
    private boolean drawBadge;
    private boolean drawBorder;
    private boolean drawDivider;
    private boolean drawHighlight;
    private boolean drawIndicator;

    /* renamed from: dslSelector$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy dslSelector;
    private boolean itemAutoEquWidth;
    private int itemDefaultHeight;
    private boolean itemEnableSelector;

    @Nullable
    private IntRange itemEquWidthCountRange;
    private boolean itemIsEquWidth;
    private int itemWidth;
    private boolean layoutScrollAnim;

    @NotNull
    private Function3<? super View, ? super DslTabBadge, ? super Integer, TabBadgeConfig> onTabBadgeConfig;
    private int orientation;
    private int scrollAnimDuration;

    @Nullable
    private DslTabBadge tabBadge;

    @NotNull
    private final Map<Integer, TabBadgeConfig> tabBadgeConfigMap;

    @Nullable
    private DslTabBorder tabBorder;

    @Nullable
    private Drawable tabConvexBackgroundDrawable;
    private int tabDefaultIndex;

    @Nullable
    private DslTabDivider tabDivider;
    private boolean tabEnableSelectorMode;

    @Nullable
    private DslTabHighlight tabHighlight;

    @NotNull
    private DslTabIndicator tabIndicator;
    private long tabIndicatorAnimationDuration;

    @Nullable
    private DslTabLayoutConfig tabLayoutConfig;

    public /* synthetic */ DslTabLayout(Context context, AttributeSet attributeSet, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void configTabLayoutConfig$default(DslTabLayout dslTabLayout, Function1 function1, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: configTabLayoutConfig");
        }
        if ((i2 & 1) != 0) {
            function1 = new Function1<DslTabLayoutConfig, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.configTabLayoutConfig.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DslTabLayoutConfig dslTabLayoutConfig) {
                    invoke2(dslTabLayoutConfig);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DslTabLayoutConfig dslTabLayoutConfig) {
                    Intrinsics.checkNotNullParameter(dslTabLayoutConfig, "$this$null");
                }
            };
        }
        dslTabLayout.configTabLayoutConfig(function1);
    }

    private static final void measureHorizontal$measureChild(DslTabLayout dslTabLayout, Ref.IntRef intRef, Ref.IntRef intRef2, int i2, int i3, Ref.IntRef intRef3, Ref.IntRef intRef4, View view, Integer num) {
        int iExactlyMeasure;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type com.angcyo.tablayout.DslTabLayout.LayoutParams");
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int[] iArrCalcLayoutWidthHeight = LibExKt.calcLayoutWidthHeight(dslTabLayout, layoutParams2.getLayoutWidth(), layoutParams2.getLayoutHeight(), intRef.element, intRef2.element, 0, 0);
        if (i2 == 1073741824) {
            iExactlyMeasure = LibExKt.exactlyMeasure((((intRef2.element - dslTabLayout.getPaddingTop()) - dslTabLayout.getPaddingBottom()) - ((FrameLayout.LayoutParams) layoutParams2).topMargin) - ((FrameLayout.LayoutParams) layoutParams2).bottomMargin);
        } else {
            int i4 = iArrCalcLayoutWidthHeight[1];
            if (i4 > 0) {
                intRef2.element = i4;
                iExactlyMeasure = LibExKt.exactlyMeasure(i4);
                intRef2.element += dslTabLayout.getPaddingTop() + dslTabLayout.getPaddingBottom();
            } else {
                iExactlyMeasure = ((FrameLayout.LayoutParams) layoutParams2).height == -1 ? LibExKt.exactlyMeasure(i3) : LibExKt.atmostMeasure(Integer.MAX_VALUE);
            }
        }
        int layoutConvexHeight = layoutParams2.getLayoutConvexHeight();
        int i5 = intRef3.element;
        if (num != null) {
            view.measure(i5, num.intValue());
        } else {
            view.measure(i5, iExactlyMeasure);
        }
        if (layoutConvexHeight > 0) {
            dslTabLayout._maxConvexHeight = Math.max(dslTabLayout._maxConvexHeight, layoutConvexHeight);
            view.measure(intRef3.element, LibExKt.exactlyMeasure(view.getMeasuredHeight() + layoutConvexHeight));
        }
        intRef4.element = Math.max(intRef4.element, view.getMeasuredHeight());
    }

    public static /* synthetic */ void measureHorizontal$measureChild$default(DslTabLayout dslTabLayout, Ref.IntRef intRef, Ref.IntRef intRef2, int i2, int i3, Ref.IntRef intRef3, Ref.IntRef intRef4, View view, Integer num, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: measureHorizontal$measureChild");
        }
        measureHorizontal$measureChild(dslTabLayout, intRef, intRef2, i2, i3, intRef3, intRef4, view, (i4 & 256) != 0 ? null : num);
    }

    private static final void measureVertical$measureChild$22(DslTabLayout dslTabLayout, Ref.IntRef intRef, Ref.IntRef intRef2, Ref.BooleanRef booleanRef, Ref.IntRef intRef3, Ref.IntRef intRef4, View view) {
        int i2;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type com.angcyo.tablayout.DslTabLayout.LayoutParams");
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        layoutParams2.setMarginStart(0);
        layoutParams2.setMarginEnd(0);
        int layoutConvexHeight = layoutParams2.getLayoutConvexHeight();
        dslTabLayout._maxConvexHeight = Math.max(dslTabLayout._maxConvexHeight, layoutConvexHeight);
        int[] iArrCalcLayoutWidthHeight = LibExKt.calcLayoutWidthHeight(dslTabLayout, layoutParams2.getLayoutWidth(), layoutParams2.getLayoutHeight(), intRef.element, intRef2.element, 0, 0);
        booleanRef.element = false;
        if (intRef3.element == -1 && (i2 = iArrCalcLayoutWidthHeight[0]) > 0) {
            intRef.element = i2;
            intRef3.element = LibExKt.exactlyMeasure(i2);
            intRef.element += dslTabLayout.getPaddingStart() + dslTabLayout.getPaddingEnd();
        }
        if (intRef3.element == -1) {
            if (((FrameLayout.LayoutParams) layoutParams2).width == -1) {
                int suggestedMinimumWidth = dslTabLayout.getSuggestedMinimumWidth() > 0 ? dslTabLayout.getSuggestedMinimumWidth() : dslTabLayout.itemDefaultHeight;
                intRef.element = suggestedMinimumWidth;
                intRef3.element = LibExKt.exactlyMeasure(suggestedMinimumWidth);
                intRef.element += dslTabLayout.getPaddingStart() + dslTabLayout.getPaddingEnd();
            } else {
                intRef3.element = LibExKt.atmostMeasure(intRef.element);
                booleanRef.element = true;
            }
        }
        int i3 = intRef4.element;
        if (layoutConvexHeight > 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(intRef3.element) + layoutConvexHeight, View.MeasureSpec.getMode(intRef3.element)), intRef4.element);
        } else {
            view.measure(intRef3.element, i3);
        }
        if (booleanRef.element) {
            int measuredWidth = view.getMeasuredWidth();
            intRef.element = measuredWidth;
            intRef3.element = LibExKt.exactlyMeasure(measuredWidth);
            intRef.element += dslTabLayout.getPaddingStart() + dslTabLayout.getPaddingEnd();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void observeIndexChange$default(DslTabLayout dslTabLayout, Function1 function1, Function4 function4, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: observeIndexChange");
        }
        if ((i2 & 1) != 0) {
            function1 = new Function1<DslTabLayoutConfig, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.observeIndexChange.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DslTabLayoutConfig dslTabLayoutConfig) {
                    invoke2(dslTabLayoutConfig);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DslTabLayoutConfig dslTabLayoutConfig) {
                    Intrinsics.checkNotNullParameter(dslTabLayoutConfig, "$this$null");
                }
            };
        }
        dslTabLayout.observeIndexChange(function1, function4);
    }

    public static /* synthetic */ void setCurrentItem$default(DslTabLayout dslTabLayout, int i2, boolean z2, boolean z3, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setCurrentItem");
        }
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        if ((i3 & 4) != 0) {
            z3 = false;
        }
        dslTabLayout.setCurrentItem(i2, z2, z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void setTabLayoutConfig$default(DslTabLayout dslTabLayout, DslTabLayoutConfig dslTabLayoutConfig, Function1 function1, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setTabLayoutConfig");
        }
        if ((i2 & 1) != 0) {
            dslTabLayoutConfig = new DslTabLayoutConfig(dslTabLayout);
        }
        if ((i2 & 2) != 0) {
            function1 = new Function1<DslTabLayoutConfig, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.setTabLayoutConfig.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DslTabLayoutConfig dslTabLayoutConfig2) {
                    invoke2(dslTabLayoutConfig2);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DslTabLayoutConfig dslTabLayoutConfig2) {
                    Intrinsics.checkNotNullParameter(dslTabLayoutConfig2, "$this$null");
                }
            };
        }
        dslTabLayout.setTabLayoutConfig(dslTabLayoutConfig, function1);
    }

    private static final int startFling$velocity(DslTabLayout dslTabLayout, int i2) {
        return i2 > 0 ? LibExKt.clamp(i2, dslTabLayout._minFlingVelocity, dslTabLayout._maxFlingVelocity) : LibExKt.clamp(i2, -dslTabLayout._maxFlingVelocity, -dslTabLayout._minFlingVelocity);
    }

    public final void _animateToItem(int fromIndex, int toIndex) {
        if (toIndex == fromIndex) {
            return;
        }
        get_scrollAnimator().cancel();
        if (!this.tabIndicator.getIndicatorAnim()) {
            _onAnimateEnd();
            return;
        }
        if (fromIndex < 0) {
            this.tabIndicator.setCurrentIndex(toIndex);
        } else {
            this.tabIndicator.setCurrentIndex(fromIndex);
        }
        this.tabIndicator.set_targetIndex(toIndex);
        if (isInEditMode()) {
            this.tabIndicator.setCurrentIndex(toIndex);
        } else {
            if (this.tabIndicator.getCurrentIndex() == this.tabIndicator.get_targetIndex()) {
                return;
            }
            get_scrollAnimator().setFloatValues(this.tabIndicator.getPositionOffset(), 1.0f);
            get_scrollAnimator().start();
        }
    }

    public final int _getViewTargetX() {
        int indicatorGravity = this.tabIndicator.getIndicatorGravity();
        return indicatorGravity != 1 ? indicatorGravity != 2 ? getPaddingStart() + (LibExKt.getViewDrawWidth(this) / 2) : getMeasuredWidth() - getPaddingEnd() : getPaddingStart();
    }

    public final int _getViewTargetY() {
        int indicatorGravity = this.tabIndicator.getIndicatorGravity();
        return indicatorGravity != 1 ? indicatorGravity != 2 ? getPaddingTop() + (LibExKt.getViewDrawHeight(this) / 2) : getMeasuredHeight() - getPaddingBottom() : getPaddingTop();
    }

    public final void _onAnimateEnd() {
        this.tabIndicator.setCurrentIndex(getDslSelector().getDslSelectIndex());
        DslTabIndicator dslTabIndicator = this.tabIndicator;
        dslTabIndicator.set_targetIndex(dslTabIndicator.getCurrentIndex());
        this.tabIndicator.setPositionOffset(0.0f);
    }

    public final void _onAnimateValue(float value) {
        this.tabIndicator.setPositionOffset(value);
        DslTabLayoutConfig dslTabLayoutConfig = this.tabLayoutConfig;
        if (dslTabLayoutConfig != null) {
            dslTabLayoutConfig.onPageIndexScrolled(this.tabIndicator.getCurrentIndex(), this.tabIndicator.get_targetIndex(), value);
        }
        DslTabLayoutConfig dslTabLayoutConfig2 = this.tabLayoutConfig;
        if (dslTabLayoutConfig2 != null) {
            List<View> visibleViewList = getDslSelector().getVisibleViewList();
            View view = (View) CollectionsKt___CollectionsKt.getOrNull(visibleViewList, this.tabIndicator.get_targetIndex());
            if (view != null) {
                dslTabLayoutConfig2.onPageViewScrolled((View) CollectionsKt___CollectionsKt.getOrNull(visibleViewList, this.tabIndicator.getCurrentIndex()), view, value);
            }
        }
    }

    public final void _scrollToTarget(int index, boolean scrollAnim) {
        int scrollY;
        int measuredHeight;
        int scrollY2;
        int i2;
        if (getNeedScroll()) {
            View view = (View) CollectionsKt___CollectionsKt.getOrNull(getDslSelector().getVisibleViewList(), index);
            if (view == null || ViewCompat.isLaidOut(view)) {
                if (isHorizontal()) {
                    int childTargetX$default = DslTabIndicator.getChildTargetX$default(this.tabIndicator, index, 0, 2, null);
                    int i_getViewTargetX = _getViewTargetX();
                    if (this.tabEnableSelectorMode) {
                        measuredHeight = childTargetX$default - (getMeasuredWidth() / 2);
                        scrollY2 = getScrollX();
                    } else if (isLayoutRtl()) {
                        if (childTargetX$default < i_getViewTargetX) {
                            measuredHeight = childTargetX$default - i_getViewTargetX;
                            scrollY2 = getScrollX();
                        } else {
                            scrollY = getScrollX();
                            i2 = -scrollY;
                        }
                    } else if (childTargetX$default > i_getViewTargetX) {
                        measuredHeight = childTargetX$default - i_getViewTargetX;
                        scrollY2 = getScrollX();
                    } else {
                        scrollY = getScrollX();
                        i2 = -scrollY;
                    }
                    i2 = measuredHeight - scrollY2;
                } else {
                    int childTargetY$default = DslTabIndicator.getChildTargetY$default(this.tabIndicator, index, 0, 2, null);
                    int i_getViewTargetY = _getViewTargetY();
                    if (this.tabEnableSelectorMode) {
                        measuredHeight = childTargetY$default - (getMeasuredHeight() / 2);
                        scrollY2 = getScrollY();
                    } else if (childTargetY$default > i_getViewTargetY) {
                        measuredHeight = childTargetY$default - i_getViewTargetY;
                        scrollY2 = getScrollY();
                    } else if (this.tabIndicator.getIndicatorGravity() != 2 || childTargetY$default >= i_getViewTargetY) {
                        scrollY = getScrollY();
                        i2 = -scrollY;
                    } else {
                        measuredHeight = childTargetY$default - i_getViewTargetY;
                        scrollY2 = getScrollY();
                    }
                    i2 = measuredHeight - scrollY2;
                }
                if (isHorizontal()) {
                    if (!isInEditMode() && scrollAnim) {
                        startScroll(i2);
                        return;
                    } else {
                        get_overScroller().abortAnimation();
                        scrollBy(i2, 0);
                        return;
                    }
                }
                if (!isInEditMode() && scrollAnim) {
                    startScroll(i2);
                } else {
                    get_overScroller().abortAnimation();
                    scrollBy(0, i2);
                }
            }
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        if (get_overScroller().computeScrollOffset()) {
            scrollTo(get_overScroller().getCurrX(), get_overScroller().getCurrY());
            invalidate();
            if (get_overScroller().getCurrX() < getMinScrollX() || get_overScroller().getCurrX() > getMaxScrollX()) {
                get_overScroller().abortAnimation();
            }
        }
    }

    public final void configTabLayoutConfig(@NotNull Function1<? super DslTabLayoutConfig, Unit> config) {
        Intrinsics.checkNotNullParameter(config, "config");
        if (this.tabLayoutConfig == null) {
            setTabLayoutConfig(new DslTabLayoutConfig(this));
        }
        DslTabLayoutConfig dslTabLayoutConfig = this.tabLayoutConfig;
        if (dslTabLayoutConfig != null) {
            config.invoke(dslTabLayoutConfig);
        }
        getDslSelector().updateStyle();
    }

    @Override // android.view.View
    public void draw(@NotNull final Canvas canvas) {
        DslTabBadge dslTabBadge;
        int left;
        int top2;
        int right;
        int bottom;
        DslTabHighlight dslTabHighlight;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        int i2 = 0;
        if (this.drawIndicator) {
            this.tabIndicator.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
        final Drawable drawable = this.tabConvexBackgroundDrawable;
        if (drawable != null) {
            if (isHorizontal()) {
                drawable.setBounds(0, this._maxConvexHeight, getRight() - getLeft(), getBottom() - getTop());
            } else {
                drawable.setBounds(0, 0, getMeasuredWidth() - this._maxConvexHeight, getBottom() - getTop());
            }
            if ((getScrollX() | getScrollY()) == 0) {
                drawable.draw(canvas);
            } else {
                holdLocation(canvas, new Function0<Unit>() { // from class: com.angcyo.tablayout.DslTabLayout$draw$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        drawable.draw(canvas);
                    }
                });
            }
        }
        super.draw(canvas);
        if (this.drawHighlight && (dslTabHighlight = this.tabHighlight) != null) {
            dslTabHighlight.draw(canvas);
        }
        int size = getDslSelector().getVisibleViewList().size();
        if (this.drawDivider) {
            if (!isHorizontal()) {
                DslTabDivider dslTabDivider = this.tabDivider;
                if (dslTabDivider != null) {
                    int paddingStart = getPaddingStart() + dslTabDivider.getDividerMarginLeft();
                    int measuredWidth = (getMeasuredWidth() - getPaddingEnd()) - dslTabDivider.getDividerMarginRight();
                    int i3 = 0;
                    for (Object obj : getDslSelector().getVisibleViewList()) {
                        int i4 = i3 + 1;
                        if (i3 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        View view = (View) obj;
                        if (dslTabDivider.haveBeforeDivider(i3, size)) {
                            int top3 = (view.getTop() - dslTabDivider.getDividerMarginBottom()) - dslTabDivider.getDividerHeight();
                            dslTabDivider.setBounds(paddingStart, top3, measuredWidth, dslTabDivider.getDividerHeight() + top3);
                            dslTabDivider.draw(canvas);
                        }
                        if (dslTabDivider.haveAfterDivider(i3, size)) {
                            int bottom2 = view.getBottom() + dslTabDivider.getDividerMarginTop();
                            dslTabDivider.setBounds(paddingStart, bottom2, measuredWidth, dslTabDivider.getDividerHeight() + bottom2);
                            dslTabDivider.draw(canvas);
                        }
                        i3 = i4;
                    }
                }
            } else if (isLayoutRtl()) {
                DslTabDivider dslTabDivider2 = this.tabDivider;
                if (dslTabDivider2 != null) {
                    int paddingTop = dslTabDivider2.getPaddingTop() + dslTabDivider2.getDividerMarginTop();
                    int measuredHeight = (getMeasuredHeight() - dslTabDivider2.getPaddingBottom()) - dslTabDivider2.getDividerMarginBottom();
                    int i5 = 0;
                    for (Object obj2 : getDslSelector().getVisibleViewList()) {
                        int i6 = i5 + 1;
                        if (i5 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        View view2 = (View) obj2;
                        if (dslTabDivider2.haveBeforeDivider(i5, size)) {
                            int right2 = view2.getRight() + dslTabDivider2.getDividerMarginLeft() + dslTabDivider2.getDividerWidth();
                            dslTabDivider2.setBounds(right2 - dslTabDivider2.getDividerWidth(), paddingTop, right2, measuredHeight);
                            dslTabDivider2.draw(canvas);
                        }
                        if (dslTabDivider2.haveAfterDivider(i5, size)) {
                            int right3 = (view2.getRight() - view2.getMeasuredWidth()) - dslTabDivider2.getDividerMarginRight();
                            dslTabDivider2.setBounds(right3 - dslTabDivider2.getDividerWidth(), paddingTop, right3, measuredHeight);
                            dslTabDivider2.draw(canvas);
                        }
                        i5 = i6;
                    }
                }
            } else {
                DslTabDivider dslTabDivider3 = this.tabDivider;
                if (dslTabDivider3 != null) {
                    int paddingTop2 = dslTabDivider3.getPaddingTop() + dslTabDivider3.getDividerMarginTop();
                    int measuredHeight2 = (getMeasuredHeight() - dslTabDivider3.getPaddingBottom()) - dslTabDivider3.getDividerMarginBottom();
                    int i7 = 0;
                    for (Object obj3 : getDslSelector().getVisibleViewList()) {
                        int i8 = i7 + 1;
                        if (i7 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        View view3 = (View) obj3;
                        if (dslTabDivider3.haveBeforeDivider(i7, size)) {
                            int left2 = (view3.getLeft() - dslTabDivider3.getDividerMarginRight()) - dslTabDivider3.getDividerWidth();
                            dslTabDivider3.setBounds(left2, paddingTop2, dslTabDivider3.getDividerWidth() + left2, measuredHeight2);
                            dslTabDivider3.draw(canvas);
                        }
                        if (dslTabDivider3.haveAfterDivider(i7, size)) {
                            int right4 = view3.getRight() + dslTabDivider3.getDividerMarginLeft();
                            dslTabDivider3.setBounds(right4, paddingTop2, dslTabDivider3.getDividerWidth() + right4, measuredHeight2);
                            dslTabDivider3.draw(canvas);
                        }
                        i7 = i8;
                    }
                }
            }
        }
        if (this.drawBorder) {
            holdLocation(canvas, new Function0<Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.draw.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    DslTabBorder tabBorder = DslTabLayout.this.getTabBorder();
                    if (tabBorder != null) {
                        tabBorder.draw(canvas);
                    }
                }
            });
        }
        if (this.drawIndicator && LibExKt.have(this.tabIndicator.getIndicatorStyle(), 4096)) {
            this.tabIndicator.draw(canvas);
        }
        if (!this.drawBadge || (dslTabBadge = this.tabBadge) == null) {
            return;
        }
        for (Object obj4 : getDslSelector().getVisibleViewList()) {
            int i9 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            View view4 = (View) obj4;
            TabBadgeConfig tabBadgeConfigInvoke = this.onTabBadgeConfig.invoke(view4, dslTabBadge, Integer.valueOf(i2));
            if (tabBadgeConfigInvoke == null || tabBadgeConfigInvoke.getBadgeAnchorChildIndex() < 0) {
                left = view4.getLeft();
                top2 = view4.getTop();
                right = view4.getRight();
                bottom = view4.getBottom();
            } else {
                View childOrNull = LibExKt.getChildOrNull(view4, tabBadgeConfigInvoke.getBadgeAnchorChildIndex());
                if (childOrNull != null) {
                    view4 = childOrNull;
                }
                LibExKt.getLocationInParent(view4, this, this._tempRect);
                Rect rect = this._tempRect;
                left = rect.left;
                top2 = rect.top;
                right = rect.right;
                bottom = rect.bottom;
            }
            if (tabBadgeConfigInvoke != null && tabBadgeConfigInvoke.getBadgeIgnoreChildPadding()) {
                left += view4.getPaddingStart();
                top2 += view4.getPaddingTop();
                right -= view4.getPaddingEnd();
                bottom -= view4.getPaddingBottom();
            }
            dslTabBadge.setBounds(left, top2, right, bottom);
            dslTabBadge.updateOriginDrawable();
            if (dslTabBadge.isInEditMode()) {
                dslTabBadge.setBadgeText(i2 == size + (-1) ? "" : dslTabBadge.getXmlBadgeText());
            }
            dslTabBadge.draw(canvas);
            i2 = i9;
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(@NotNull Canvas canvas, @NotNull View child, long drawingTime) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(child, "child");
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override // android.view.ViewGroup
    @NotNull
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2, 17);
    }

    @Override // android.view.ViewGroup
    @NotNull
    public ViewGroup.LayoutParams generateLayoutParams(@Nullable AttributeSet attrs) {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return new LayoutParams(context, attrs);
    }

    @Nullable
    public final AttributeSet getAttributeSet() {
        return this.attributeSet;
    }

    @NotNull
    public final TabBadgeConfig getBadgeConfig(int index) {
        DslTabBadge dslTabBadge;
        TabBadgeConfig defaultBadgeConfig;
        TabBadgeConfig tabBadgeConfig = this.tabBadgeConfigMap.get(Integer.valueOf(index));
        if (tabBadgeConfig == null && ((dslTabBadge = this.tabBadge) == null || (defaultBadgeConfig = dslTabBadge.getDefaultBadgeConfig()) == null || (tabBadgeConfig = defaultBadgeConfig.copy((2097151 & 1) != 0 ? defaultBadgeConfig.badgeText : null, (2097151 & 2) != 0 ? defaultBadgeConfig.badgeGravity : 0, (2097151 & 4) != 0 ? defaultBadgeConfig.badgeSolidColor : 0, (2097151 & 8) != 0 ? defaultBadgeConfig.badgeStrokeColor : 0, (2097151 & 16) != 0 ? defaultBadgeConfig.badgeStrokeWidth : 0, (2097151 & 32) != 0 ? defaultBadgeConfig.badgeTextColor : 0, (2097151 & 64) != 0 ? defaultBadgeConfig.badgeTextSize : 0.0f, (2097151 & 128) != 0 ? defaultBadgeConfig.badgeCircleRadius : 0, (2097151 & 256) != 0 ? defaultBadgeConfig.badgeRadius : 0, (2097151 & 512) != 0 ? defaultBadgeConfig.badgeOffsetX : 0, (2097151 & 1024) != 0 ? defaultBadgeConfig.badgeOffsetY : 0, (2097151 & 2048) != 0 ? defaultBadgeConfig.badgeCircleOffsetX : 0, (2097151 & 4096) != 0 ? defaultBadgeConfig.badgeCircleOffsetY : 0, (2097151 & 8192) != 0 ? defaultBadgeConfig.badgePaddingLeft : 0, (2097151 & 16384) != 0 ? defaultBadgeConfig.badgePaddingRight : 0, (2097151 & 32768) != 0 ? defaultBadgeConfig.badgePaddingTop : 0, (2097151 & 65536) != 0 ? defaultBadgeConfig.badgePaddingBottom : 0, (2097151 & 131072) != 0 ? defaultBadgeConfig.badgeAnchorChildIndex : 0, (2097151 & 262144) != 0 ? defaultBadgeConfig.badgeIgnoreChildPadding : false, (2097151 & 524288) != 0 ? defaultBadgeConfig.badgeMinHeight : 0, (2097151 & 1048576) != 0 ? defaultBadgeConfig.badgeMinWidth : 0)) == null)) {
            tabBadgeConfig = new TabBadgeConfig(null, 0, 0, 0, 0, 0, 0.0f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 2097151, null);
        }
        return tabBadgeConfig;
    }

    public final int getCurrentItemIndex() {
        return getDslSelector().getDslSelectIndex();
    }

    @Nullable
    public final View getCurrentItemView() {
        return (View) CollectionsKt___CollectionsKt.getOrNull(getDslSelector().getVisibleViewList(), getCurrentItemIndex());
    }

    public final boolean getDrawBadge() {
        return this.drawBadge;
    }

    public final boolean getDrawBorder() {
        return this.drawBorder;
    }

    public final boolean getDrawDivider() {
        return this.drawDivider;
    }

    public final boolean getDrawHighlight() {
        return this.drawHighlight;
    }

    public final boolean getDrawIndicator() {
        return this.drawIndicator;
    }

    @NotNull
    public final DslSelector getDslSelector() {
        return (DslSelector) this.dslSelector.getValue();
    }

    public final boolean getItemAutoEquWidth() {
        return this.itemAutoEquWidth;
    }

    public final int getItemDefaultHeight() {
        return this.itemDefaultHeight;
    }

    public final boolean getItemEnableSelector() {
        return this.itemEnableSelector;
    }

    @Nullable
    public final IntRange getItemEquWidthCountRange() {
        return this.itemEquWidthCountRange;
    }

    public final boolean getItemIsEquWidth() {
        return this.itemIsEquWidth;
    }

    public final int getItemWidth() {
        return this.itemWidth;
    }

    public final boolean getLayoutScrollAnim() {
        return this.layoutScrollAnim;
    }

    public final int getMaxHeight() {
        return this._childAllWidthSum + getPaddingTop() + getPaddingBottom();
    }

    public final int getMaxScrollX() {
        if (!isLayoutRtl() || !isHorizontal()) {
            return Math.max((getMaxWidth() - getMeasuredWidth()) + (this.tabEnableSelectorMode ? LibExKt.getViewDrawWidth(this) / 2 : 0), 0);
        }
        if (this.tabEnableSelectorMode) {
            return LibExKt.getViewDrawWidth(this) / 2;
        }
        return 0;
    }

    public final int getMaxScrollY() {
        return Math.max((getMaxHeight() - getMeasuredHeight()) + (this.tabEnableSelectorMode ? LibExKt.getViewDrawHeight(this) / 2 : 0), 0);
    }

    public final int getMaxWidth() {
        return this._childAllWidthSum + getPaddingStart() + getPaddingEnd();
    }

    public final int getMinScrollX() {
        if (isLayoutRtl() && isHorizontal()) {
            return Math.min(-((getMaxWidth() - getMeasuredWidth()) + (this.tabEnableSelectorMode ? LibExKt.getViewDrawWidth(this) / 2 : 0)), 0);
        }
        if (this.tabEnableSelectorMode) {
            return (-LibExKt.getViewDrawWidth(this)) / 2;
        }
        return 0;
    }

    public final int getMinScrollY() {
        if (this.tabEnableSelectorMode) {
            return (-LibExKt.getViewDrawHeight(this)) / 2;
        }
        return 0;
    }

    public final boolean getNeedScroll() {
        if (this.tabEnableSelectorMode) {
            return true;
        }
        if (isHorizontal()) {
            if (isLayoutRtl()) {
                if (getMinScrollX() < 0) {
                    return true;
                }
            } else if (getMaxScrollX() > 0) {
                return true;
            }
        } else if (getMaxScrollY() > 0) {
            return true;
        }
        return false;
    }

    @NotNull
    public final Function3<View, DslTabBadge, Integer, TabBadgeConfig> getOnTabBadgeConfig() {
        return this.onTabBadgeConfig;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final int getScrollAnimDuration() {
        return this.scrollAnimDuration;
    }

    @Nullable
    public final DslTabBadge getTabBadge() {
        return this.tabBadge;
    }

    @NotNull
    public final Map<Integer, TabBadgeConfig> getTabBadgeConfigMap() {
        return this.tabBadgeConfigMap;
    }

    @Nullable
    public final DslTabBorder getTabBorder() {
        return this.tabBorder;
    }

    @Nullable
    public final Drawable getTabConvexBackgroundDrawable() {
        return this.tabConvexBackgroundDrawable;
    }

    public final int getTabDefaultIndex() {
        return this.tabDefaultIndex;
    }

    @Nullable
    public final DslTabDivider getTabDivider() {
        return this.tabDivider;
    }

    public final boolean getTabEnableSelectorMode() {
        return this.tabEnableSelectorMode;
    }

    @Nullable
    public final DslTabHighlight getTabHighlight() {
        return this.tabHighlight;
    }

    @NotNull
    public final DslTabIndicator getTabIndicator() {
        return this.tabIndicator;
    }

    public final long getTabIndicatorAnimationDuration() {
        return this.tabIndicatorAnimationDuration;
    }

    @Nullable
    public final DslTabLayoutConfig getTabLayoutConfig() {
        return this.tabLayoutConfig;
    }

    public final int get_childAllWidthSum() {
        return this._childAllWidthSum;
    }

    @NotNull
    public final GestureDetectorCompat get_gestureDetector() {
        return (GestureDetectorCompat) this._gestureDetector.getValue();
    }

    public final int get_layoutDirection() {
        return this._layoutDirection;
    }

    public final int get_maxConvexHeight() {
        return this._maxConvexHeight;
    }

    public final int get_maxFlingVelocity() {
        return this._maxFlingVelocity;
    }

    public final int get_minFlingVelocity() {
        return this._minFlingVelocity;
    }

    @NotNull
    public final OverScroller get_overScroller() {
        return (OverScroller) this._overScroller.getValue();
    }

    @NotNull
    public final ValueAnimator get_scrollAnimator() {
        return (ValueAnimator) this._scrollAnimator.getValue();
    }

    @NotNull
    public final Rect get_tempRect() {
        return this._tempRect;
    }

    public final int get_touchSlop() {
        return this._touchSlop;
    }

    @Nullable
    public final ViewPagerDelegate get_viewPagerDelegate() {
        return this._viewPagerDelegate;
    }

    public final int get_viewPagerScrollState() {
        return this._viewPagerScrollState;
    }

    public final void holdLocation(@NotNull Canvas canvas, @NotNull Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(canvas, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        canvas.translate(getScrollX(), getScrollY());
        action.invoke();
        canvas.translate(-getScrollX(), -getScrollY());
    }

    public final boolean isAnimatorStart() {
        return get_scrollAnimator().isStarted();
    }

    public final boolean isHorizontal() {
        return LibExKt.isHorizontal(this.orientation);
    }

    public final boolean isLayoutRtl() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void layoutHorizontal(boolean r10, int r11, int r12, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.angcyo.tablayout.DslTabLayout.layoutHorizontal(boolean, int, int, int, int):void");
    }

    public final void layoutVertical(boolean changed, int l2, int t2, int r2, int b3) {
        int paddingStart;
        int measuredWidth;
        int measuredWidth2;
        DslTabDivider dslTabDivider;
        int paddingTop = getPaddingTop();
        getPaddingStart();
        int dividerHeight = (!this.drawDivider || (dslTabDivider = this.tabDivider) == null) ? 0 : dslTabDivider.getDividerHeight() + dslTabDivider.getDividerMarginTop() + dslTabDivider.getDividerMarginBottom();
        List<View> visibleViewList = getDslSelector().getVisibleViewList();
        int i2 = 0;
        for (Object obj : visibleViewList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            View view = (View) obj;
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type com.angcyo.tablayout.DslTabLayout.LayoutParams");
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            int absoluteGravity = GravityCompat.getAbsoluteGravity(((FrameLayout.LayoutParams) layoutParams2).gravity, 0) & 7;
            int i4 = paddingTop + ((FrameLayout.LayoutParams) layoutParams2).topMargin;
            if (this.drawDivider) {
                DslTabDivider dslTabDivider2 = this.tabDivider;
                if (dslTabDivider2 != null && dslTabDivider2.haveBeforeDivider(i2, visibleViewList.size())) {
                    i4 += dividerHeight;
                }
            }
            if (absoluteGravity == 1) {
                paddingStart = getPaddingStart();
                measuredWidth = ((((getMeasuredWidth() - getPaddingStart()) - getPaddingEnd()) - this._maxConvexHeight) / 2) - (view.getMeasuredWidth() / 2);
            } else if (absoluteGravity != 5) {
                paddingStart = getPaddingLeft();
                measuredWidth = ((FrameLayout.LayoutParams) layoutParams2).leftMargin;
            } else {
                measuredWidth2 = ((getMeasuredWidth() - getPaddingRight()) - view.getMeasuredWidth()) - ((FrameLayout.LayoutParams) layoutParams2).rightMargin;
                view.layout(measuredWidth2, i4, view.getMeasuredWidth() + measuredWidth2, view.getMeasuredHeight() + i4);
                paddingTop = i4 + view.getMeasuredHeight() + ((FrameLayout.LayoutParams) layoutParams2).bottomMargin;
                i2 = i3;
            }
            measuredWidth2 = paddingStart + measuredWidth;
            view.layout(measuredWidth2, i4, view.getMeasuredWidth() + measuredWidth2, view.getMeasuredHeight() + i4);
            paddingTop = i4 + view.getMeasuredHeight() + ((FrameLayout.LayoutParams) layoutParams2).bottomMargin;
            i2 = i3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x02dc A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void measureHorizontal(int r33, int r34) {
        /*
            Method dump skipped, instructions count: 1163
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.angcyo.tablayout.DslTabLayout.measureHorizontal(int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x029b  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x02b3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void measureVertical(int r31, int r32) {
        /*
            Method dump skipped, instructions count: 954
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.angcyo.tablayout.DslTabLayout.measureVertical(int, int):void");
    }

    public final void observeIndexChange(@NotNull final Function1<? super DslTabLayoutConfig, Unit> config, @NotNull final Function4<? super Integer, ? super Integer, ? super Boolean, ? super Boolean, Unit> action) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(action, "action");
        configTabLayoutConfig(new Function1<DslTabLayoutConfig, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.observeIndexChange.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DslTabLayoutConfig dslTabLayoutConfig) {
                invoke2(dslTabLayoutConfig);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull DslTabLayoutConfig configTabLayoutConfig) {
                Intrinsics.checkNotNullParameter(configTabLayoutConfig, "$this$configTabLayoutConfig");
                config.invoke(configTabLayoutConfig);
                final Function4<Integer, Integer, Boolean, Boolean, Unit> function4 = action;
                configTabLayoutConfig.setOnSelectIndexChange(new Function4<Integer, List<? extends Integer>, Boolean, Boolean, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.observeIndexChange.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(4);
                    }

                    @Override // kotlin.jvm.functions.Function4
                    public /* bridge */ /* synthetic */ Unit invoke(Integer num, List<? extends Integer> list, Boolean bool, Boolean bool2) {
                        invoke(num.intValue(), (List<Integer>) list, bool.booleanValue(), bool2.booleanValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(int i2, @NotNull List<Integer> selectIndexList, boolean z2, boolean z3) {
                        Intrinsics.checkNotNullParameter(selectIndexList, "selectIndexList");
                        Function4<Integer, Integer, Boolean, Boolean, Unit> function42 = function4;
                        Integer numValueOf = Integer.valueOf(i2);
                        Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull((List) selectIndexList);
                        function42.invoke(numValueOf, Integer.valueOf(num != null ? num.intValue() : -1), Boolean.valueOf(z2), Boolean.valueOf(z3));
                    }
                });
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onDraw(@NotNull final Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.drawBorder) {
            holdLocation(canvas, new Function0<Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.onDraw.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    DslTabBorder tabBorder = DslTabLayout.this.getTabBorder();
                    if (tabBorder != null) {
                        tabBorder.drawBorderBackground(canvas);
                    }
                }
            });
        }
        if (!this.drawIndicator || LibExKt.have(this.tabIndicator.getIndicatorStyle(), 4096)) {
            return;
        }
        this.tabIndicator.draw(canvas);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void onFlingChange(float velocity) {
        if (getNeedScroll()) {
            if (!this.tabEnableSelectorMode) {
                if (!isHorizontal()) {
                    startFling(-((int) velocity), 0, getMaxHeight());
                    return;
                } else if (isLayoutRtl()) {
                    startFling(-((int) velocity), getMinScrollX(), 0);
                    return;
                } else {
                    startFling(-((int) velocity), 0, getMaxScrollX());
                    return;
                }
            }
            if (isHorizontal() && isLayoutRtl()) {
                if (velocity < 0.0f) {
                    setCurrentItem$default(this, getDslSelector().getDslSelectIndex() - 1, false, false, 6, null);
                    return;
                } else {
                    if (velocity > 0.0f) {
                        setCurrentItem$default(this, getDslSelector().getDslSelectIndex() + 1, false, false, 6, null);
                        return;
                    }
                    return;
                }
            }
            if (velocity < 0.0f) {
                setCurrentItem$default(this, getDslSelector().getDslSelectIndex() + 1, false, false, 6, null);
            } else if (velocity > 0.0f) {
                setCurrentItem$default(this, getDslSelector().getDslSelectIndex() - 1, false, false, 6, null);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0044  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r4) {
        /*
            r3 = this;
            java.lang.String r0 = "ev"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            boolean r0 = r3.getNeedScroll()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L39
            int r0 = r4.getActionMasked()
            if (r0 != 0) goto L21
            android.widget.OverScroller r0 = r3.get_overScroller()
            r0.abortAnimation()
            android.animation.ValueAnimator r0 = r3.get_scrollAnimator()
            r0.cancel()
        L21:
            boolean r0 = r3.isEnabled()
            if (r0 == 0) goto L44
            boolean r0 = super.onInterceptTouchEvent(r4)
            if (r0 != 0) goto L37
            androidx.core.view.GestureDetectorCompat r0 = r3.get_gestureDetector()
            boolean r4 = r0.onTouchEvent(r4)
            if (r4 == 0) goto L44
        L37:
            r4 = r1
            goto L45
        L39:
            boolean r0 = r3.isEnabled()
            if (r0 == 0) goto L44
            boolean r4 = super.onInterceptTouchEvent(r4)
            goto L45
        L44:
            r4 = r2
        L45:
            boolean r0 = r3.isEnabled()
            if (r0 == 0) goto L51
            boolean r0 = r3.itemEnableSelector
            if (r0 == 0) goto L52
            r1 = r4
            goto L52
        L51:
            r1 = r2
        L52:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.angcyo.tablayout.DslTabLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        if (isHorizontal()) {
            layoutHorizontal(changed, l2, t2, r2, b3);
        } else {
            layoutVertical(changed, l2, t2, r2, b3);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getDslSelector().getDslSelectIndex() < 0) {
            setCurrentItem$default(this, this.tabDefaultIndex, false, false, 6, null);
        }
        if (isHorizontal()) {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public final void onPageScrollStateChanged(int state) {
        this._viewPagerScrollState = state;
        if (state == 0) {
            _onAnimateEnd();
            getDslSelector().updateStyle();
        }
    }

    public final void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (isAnimatorStart()) {
            return;
        }
        ViewPagerDelegate viewPagerDelegate = this._viewPagerDelegate;
        if (position < (viewPagerDelegate != null ? viewPagerDelegate.onGetCurrentItem() : 0)) {
            if (this._viewPagerScrollState == 1) {
                this.tabIndicator.setCurrentIndex(position + 1);
                this.tabIndicator.set_targetIndex(position);
            }
            _onAnimateValue(1 - positionOffset);
            return;
        }
        if (this._viewPagerScrollState == 1) {
            this.tabIndicator.setCurrentIndex(position);
            this.tabIndicator.set_targetIndex(position + 1);
        }
        _onAnimateValue(positionOffset);
    }

    public final void onPageSelected(int position) {
        setCurrentItem(position, true, false);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(@Nullable Parcelable state) {
        if (!(state instanceof Bundle)) {
            super.onRestoreInstanceState(state);
            return;
        }
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable("old"));
        this.tabDefaultIndex = bundle.getInt("defaultIndex", this.tabDefaultIndex);
        int i2 = bundle.getInt("currentIndex", -1);
        getDslSelector().setDslSelectIndex(-1);
        if (i2 > 0) {
            setCurrentItem(i2, true, false);
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (layoutDirection != this._layoutDirection) {
            this._layoutDirection = layoutDirection;
            if (this.orientation == 0) {
                requestLayout();
            }
        }
    }

    @Override // android.view.View
    @Nullable
    public Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("old", parcelableOnSaveInstanceState);
        bundle.putInt("defaultIndex", this.tabDefaultIndex);
        bundle.putInt("currentIndex", getCurrentItemIndex());
        return bundle;
    }

    public boolean onScrollChange(float distance) {
        if (!getNeedScroll()) {
            return false;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        if (!this.tabEnableSelectorMode) {
            if (isHorizontal()) {
                scrollBy((int) distance, 0);
            } else {
                scrollBy(0, (int) distance);
            }
        }
        return true;
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        restoreScroll();
        if (getDslSelector().getDslSelectIndex() < 0) {
            setCurrentItem$default(this, this.tabDefaultIndex, false, false, 6, null);
        } else if (get_overScroller().isFinished()) {
            _scrollToTarget(getDslSelector().getDslSelectIndex(), this.layoutScrollAnim);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!isEnabled()) {
            return false;
        }
        if (!getNeedScroll()) {
            return isEnabled() && super.onTouchEvent(event);
        }
        get_gestureDetector().onTouchEvent(event);
        if (event.getActionMasked() == 3 || event.getActionMasked() == 1) {
            getParent().requestDisallowInterceptTouchEvent(false);
        } else if (event.getActionMasked() == 0) {
            get_overScroller().abortAnimation();
        }
        return true;
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(@Nullable View child) {
        super.onViewAdded(child);
        updateTabLayout();
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(@Nullable View child) {
        super.onViewRemoved(child);
        updateTabLayout();
    }

    public final void restoreScroll() {
        if (this.itemIsEquWidth || !getNeedScroll()) {
            if (getScrollX() == 0 && getScrollY() == 0) {
                return;
            }
            scrollTo(0, 0);
        }
    }

    @Override // android.view.View
    public void scrollTo(int x2, int y2) {
        if (isHorizontal()) {
            if (x2 > getMaxScrollX()) {
                super.scrollTo(getMaxScrollX(), 0);
                return;
            } else if (x2 < getMinScrollX()) {
                super.scrollTo(getMinScrollX(), 0);
                return;
            } else {
                super.scrollTo(x2, 0);
                return;
            }
        }
        if (y2 > getMaxScrollY()) {
            super.scrollTo(0, getMaxScrollY());
        } else if (y2 < getMinScrollY()) {
            super.scrollTo(0, getMinScrollY());
        } else {
            super.scrollTo(0, y2);
        }
    }

    public final void setCurrentItem(int index, boolean notify, boolean fromUser) {
        if (getCurrentItemIndex() == index) {
            _scrollToTarget(index, this.tabIndicator.getIndicatorAnim());
        } else {
            DslSelector.selector$default(getDslSelector(), index, true, notify, fromUser, false, 16, null);
        }
    }

    public final void setDrawBadge(boolean z2) {
        this.drawBadge = z2;
    }

    public final void setDrawBorder(boolean z2) {
        this.drawBorder = z2;
    }

    public final void setDrawDivider(boolean z2) {
        this.drawDivider = z2;
    }

    public final void setDrawHighlight(boolean z2) {
        this.drawHighlight = z2;
    }

    public final void setDrawIndicator(boolean z2) {
        this.drawIndicator = z2;
    }

    public final void setItemAutoEquWidth(boolean z2) {
        this.itemAutoEquWidth = z2;
    }

    public final void setItemDefaultHeight(int i2) {
        this.itemDefaultHeight = i2;
    }

    public final void setItemEnableSelector(boolean z2) {
        this.itemEnableSelector = z2;
    }

    public final void setItemEquWidthCountRange(@Nullable IntRange intRange) {
        this.itemEquWidthCountRange = intRange;
    }

    public final void setItemIsEquWidth(boolean z2) {
        this.itemIsEquWidth = z2;
    }

    public final void setItemWidth(int i2) {
        this.itemWidth = i2;
    }

    public final void setLayoutScrollAnim(boolean z2) {
        this.layoutScrollAnim = z2;
    }

    public final void setOnTabBadgeConfig(@NotNull Function3<? super View, ? super DslTabBadge, ? super Integer, TabBadgeConfig> function3) {
        Intrinsics.checkNotNullParameter(function3, "<set-?>");
        this.onTabBadgeConfig = function3;
    }

    public final void setOrientation(int i2) {
        this.orientation = i2;
    }

    public final void setScrollAnimDuration(int i2) {
        this.scrollAnimDuration = i2;
    }

    public final void setTabBadge(@Nullable DslTabBadge dslTabBadge) {
        this.tabBadge = dslTabBadge;
        if (dslTabBadge != null) {
            dslTabBadge.setCallback(this);
        }
        DslTabBadge dslTabBadge2 = this.tabBadge;
        if (dslTabBadge2 != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            dslTabBadge2.initAttribute(context, this.attributeSet);
        }
    }

    public final void setTabBorder(@Nullable DslTabBorder dslTabBorder) {
        this.tabBorder = dslTabBorder;
        if (dslTabBorder != null) {
            dslTabBorder.setCallback(this);
        }
        DslTabBorder dslTabBorder2 = this.tabBorder;
        if (dslTabBorder2 != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            dslTabBorder2.initAttribute(context, this.attributeSet);
        }
    }

    public final void setTabConvexBackgroundDrawable(@Nullable Drawable drawable) {
        this.tabConvexBackgroundDrawable = drawable;
    }

    public final void setTabDefaultIndex(int i2) {
        this.tabDefaultIndex = i2;
    }

    public final void setTabDivider(@Nullable DslTabDivider dslTabDivider) {
        this.tabDivider = dslTabDivider;
        if (dslTabDivider != null) {
            dslTabDivider.setCallback(this);
        }
        DslTabDivider dslTabDivider2 = this.tabDivider;
        if (dslTabDivider2 != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            dslTabDivider2.initAttribute(context, this.attributeSet);
        }
    }

    public final void setTabEnableSelectorMode(boolean z2) {
        this.tabEnableSelectorMode = z2;
    }

    public final void setTabHighlight(@Nullable DslTabHighlight dslTabHighlight) {
        this.tabHighlight = dslTabHighlight;
        if (dslTabHighlight != null) {
            dslTabHighlight.setCallback(this);
        }
        DslTabHighlight dslTabHighlight2 = this.tabHighlight;
        if (dslTabHighlight2 != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            dslTabHighlight2.initAttribute(context, this.attributeSet);
        }
    }

    public final void setTabIndicator(@NotNull DslTabIndicator value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.tabIndicator = value;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        value.initAttribute(context, this.attributeSet);
    }

    public final void setTabIndicatorAnimationDuration(long j2) {
        this.tabIndicatorAnimationDuration = j2;
    }

    public final void setTabLayoutConfig(@Nullable DslTabLayoutConfig dslTabLayoutConfig) {
        this.tabLayoutConfig = dslTabLayoutConfig;
        if (dslTabLayoutConfig != null) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            dslTabLayoutConfig.initAttribute(context, this.attributeSet);
        }
    }

    public final void set_childAllWidthSum(int i2) {
        this._childAllWidthSum = i2;
    }

    public final void set_layoutDirection(int i2) {
        this._layoutDirection = i2;
    }

    public final void set_maxConvexHeight(int i2) {
        this._maxConvexHeight = i2;
    }

    public final void set_maxFlingVelocity(int i2) {
        this._maxFlingVelocity = i2;
    }

    public final void set_minFlingVelocity(int i2) {
        this._minFlingVelocity = i2;
    }

    public final void set_touchSlop(int i2) {
        this._touchSlop = i2;
    }

    public final void set_viewPagerDelegate(@Nullable ViewPagerDelegate viewPagerDelegate) {
        this._viewPagerDelegate = viewPagerDelegate;
    }

    public final void set_viewPagerScrollState(int i2) {
        this._viewPagerScrollState = i2;
    }

    public final void setupViewPager(@NotNull ViewPagerDelegate viewPagerDelegate) {
        Intrinsics.checkNotNullParameter(viewPagerDelegate, "viewPagerDelegate");
        this._viewPagerDelegate = viewPagerDelegate;
    }

    public final void startFling(int velocity, int min, int max) {
        int iStartFling$velocity = startFling$velocity(this, velocity);
        get_overScroller().abortAnimation();
        if (isHorizontal()) {
            get_overScroller().fling(getScrollX(), getScrollY(), iStartFling$velocity, 0, min, max, 0, 0, getMeasuredWidth(), 0);
        } else {
            get_overScroller().fling(getScrollX(), getScrollY(), 0, iStartFling$velocity, 0, 0, min, max, 0, getMeasuredHeight());
        }
        postInvalidate();
    }

    public final void startScroll(int dv) {
        get_overScroller().abortAnimation();
        if (isHorizontal()) {
            get_overScroller().startScroll(getScrollX(), getScrollY(), dv, 0, this.scrollAnimDuration);
        } else {
            get_overScroller().startScroll(getScrollX(), getScrollY(), 0, dv, this.scrollAnimDuration);
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public final void updateTabBadge(int index, @Nullable final String badgeText) {
        updateTabBadge(index, new Function1<TabBadgeConfig, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.updateTabBadge.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(TabBadgeConfig tabBadgeConfig) {
                invoke2(tabBadgeConfig);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull TabBadgeConfig updateTabBadge) {
                Intrinsics.checkNotNullParameter(updateTabBadge, "$this$updateTabBadge");
                updateTabBadge.setBadgeText(badgeText);
            }
        });
    }

    public void updateTabLayout() {
        getDslSelector().updateVisibleList();
        getDslSelector().updateStyle();
        getDslSelector().updateClickListener();
    }

    @Override // android.view.View
    public boolean verifyDrawable(@NotNull Drawable who) {
        Intrinsics.checkNotNullParameter(who, "who");
        return super.verifyDrawable(who) || Intrinsics.areEqual(who, this.tabIndicator);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DslTabLayout(@NotNull final Context context, @Nullable AttributeSet attributeSet) {
        Integer intOrNull;
        Integer intOrNull2;
        Integer intOrNull3;
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.attributeSet = attributeSet;
        this.itemDefaultHeight = LibExKt.getDpi(this) * 40;
        this.itemEnableSelector = true;
        this.itemWidth = -3;
        this.drawIndicator = true;
        this.tabIndicator = new DslTabIndicator(this);
        this.tabIndicatorAnimationDuration = 240L;
        this.tabBadgeConfigMap = new LinkedHashMap();
        this.onTabBadgeConfig = new Function3<View, DslTabBadge, Integer, TabBadgeConfig>() { // from class: com.angcyo.tablayout.DslTabLayout$onTabBadgeConfig$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ TabBadgeConfig invoke(View view, DslTabBadge dslTabBadge, Integer num) {
                return invoke(view, dslTabBadge, num.intValue());
            }

            @NotNull
            public final TabBadgeConfig invoke(@NotNull View view, @NotNull DslTabBadge tabBadge, int i2) {
                Intrinsics.checkNotNullParameter(view, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(tabBadge, "tabBadge");
                TabBadgeConfig badgeConfig = this.this$0.getBadgeConfig(i2);
                if (!this.this$0.isInEditMode()) {
                    tabBadge.updateBadgeConfig(badgeConfig);
                }
                return badgeConfig;
            }
        };
        this.scrollAnimDuration = 250;
        this._tempRect = new Rect();
        this.dslSelector = LazyKt__LazyJVMKt.lazy(new Function0<DslSelector>() { // from class: com.angcyo.tablayout.DslTabLayout$dslSelector$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final DslSelector invoke() {
                DslSelector dslSelector = new DslSelector();
                final DslTabLayout dslTabLayout = this.this$0;
                return dslSelector.install(dslTabLayout, new Function1<DslSelectorConfig, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout$dslSelector$2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DslSelectorConfig dslSelectorConfig) {
                        invoke2(dslSelectorConfig);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull DslSelectorConfig install) {
                        Intrinsics.checkNotNullParameter(install, "$this$install");
                        final DslTabLayout dslTabLayout2 = dslTabLayout;
                        install.setOnStyleItemView(new Function3<View, Integer, Boolean, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.dslSelector.2.1.1
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public /* bridge */ /* synthetic */ Unit invoke(View view, Integer num, Boolean bool) {
                                invoke(view, num.intValue(), bool.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull View itemView, int i2, boolean z2) {
                                Function3<View, Integer, Boolean, Unit> onStyleItemView;
                                Intrinsics.checkNotNullParameter(itemView, "itemView");
                                DslTabLayoutConfig tabLayoutConfig = dslTabLayout2.getTabLayoutConfig();
                                if (tabLayoutConfig == null || (onStyleItemView = tabLayoutConfig.getOnStyleItemView()) == null) {
                                    return;
                                }
                                onStyleItemView.invoke(itemView, Integer.valueOf(i2), Boolean.valueOf(z2));
                            }
                        });
                        final DslTabLayout dslTabLayout3 = dslTabLayout;
                        install.setOnSelectItemView(new Function4<View, Integer, Boolean, Boolean, Boolean>() { // from class: com.angcyo.tablayout.DslTabLayout.dslSelector.2.1.2
                            {
                                super(4);
                            }

                            @Override // kotlin.jvm.functions.Function4
                            public /* bridge */ /* synthetic */ Boolean invoke(View view, Integer num, Boolean bool, Boolean bool2) {
                                return invoke(view, num.intValue(), bool.booleanValue(), bool2.booleanValue());
                            }

                            @NotNull
                            public final Boolean invoke(@NotNull View itemView, int i2, boolean z2, boolean z3) {
                                Function4<View, Integer, Boolean, Boolean, Boolean> onSelectItemView;
                                Intrinsics.checkNotNullParameter(itemView, "itemView");
                                DslTabLayoutConfig tabLayoutConfig = dslTabLayout3.getTabLayoutConfig();
                                return Boolean.valueOf((tabLayoutConfig == null || (onSelectItemView = tabLayoutConfig.getOnSelectItemView()) == null) ? false : onSelectItemView.invoke(itemView, Integer.valueOf(i2), Boolean.valueOf(z2), Boolean.valueOf(z3)).booleanValue());
                            }
                        });
                        final DslTabLayout dslTabLayout4 = dslTabLayout;
                        install.setOnSelectViewChange(new Function4<View, List<? extends View>, Boolean, Boolean, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.dslSelector.2.1.3
                            {
                                super(4);
                            }

                            @Override // kotlin.jvm.functions.Function4
                            public /* bridge */ /* synthetic */ Unit invoke(View view, List<? extends View> list, Boolean bool, Boolean bool2) {
                                invoke(view, list, bool.booleanValue(), bool2.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@Nullable View view, @NotNull List<? extends View> selectViewList, boolean z2, boolean z3) {
                                Function4<View, List<? extends View>, Boolean, Boolean, Unit> onSelectViewChange;
                                Intrinsics.checkNotNullParameter(selectViewList, "selectViewList");
                                DslTabLayoutConfig tabLayoutConfig = dslTabLayout4.getTabLayoutConfig();
                                if (tabLayoutConfig == null || (onSelectViewChange = tabLayoutConfig.getOnSelectViewChange()) == null) {
                                    return;
                                }
                                onSelectViewChange.invoke(view, selectViewList, Boolean.valueOf(z2), Boolean.valueOf(z3));
                            }
                        });
                        final DslTabLayout dslTabLayout5 = dslTabLayout;
                        install.setOnSelectIndexChange(new Function4<Integer, List<? extends Integer>, Boolean, Boolean, Unit>() { // from class: com.angcyo.tablayout.DslTabLayout.dslSelector.2.1.4
                            {
                                super(4);
                            }

                            @Override // kotlin.jvm.functions.Function4
                            public /* bridge */ /* synthetic */ Unit invoke(Integer num, List<? extends Integer> list, Boolean bool, Boolean bool2) {
                                invoke(num.intValue(), (List<Integer>) list, bool.booleanValue(), bool2.booleanValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(int i2, @NotNull List<Integer> selectList, boolean z2, boolean z3) {
                                Function4<Integer, List<Integer>, Boolean, Boolean, Unit> onSelectIndexChange;
                                Intrinsics.checkNotNullParameter(selectList, "selectList");
                                if (dslTabLayout5.getTabLayoutConfig() == null) {
                                    LibExKt.logi("选择:[" + i2 + "]->" + selectList + " reselect:" + z2 + " fromUser:" + z3);
                                }
                                Integer num = (Integer) CollectionsKt___CollectionsKt.lastOrNull((List) selectList);
                                int iIntValue = num != null ? num.intValue() : -1;
                                dslTabLayout5._animateToItem(i2, iIntValue);
                                DslTabLayout dslTabLayout6 = dslTabLayout5;
                                dslTabLayout6._scrollToTarget(iIntValue, dslTabLayout6.getTabIndicator().getIndicatorAnim());
                                dslTabLayout5.postInvalidate();
                                DslTabLayoutConfig tabLayoutConfig = dslTabLayout5.getTabLayoutConfig();
                                if (tabLayoutConfig != null && (onSelectIndexChange = tabLayoutConfig.getOnSelectIndexChange()) != null) {
                                    onSelectIndexChange.invoke(Integer.valueOf(i2), selectList, Boolean.valueOf(z2), Boolean.valueOf(z3));
                                    return;
                                }
                                ViewPagerDelegate viewPagerDelegate = dslTabLayout5.get_viewPagerDelegate();
                                if (viewPagerDelegate != null) {
                                    viewPagerDelegate.onSetCurrentItem(i2, iIntValue, z2, z3);
                                }
                            }
                        });
                    }
                });
            }
        });
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…R.styleable.DslTabLayout)");
        this.itemIsEquWidth = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_item_is_equ_width, this.itemIsEquWidth);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_item_equ_width_count, -1);
        int iIntValue = Integer.MAX_VALUE;
        if (i2 >= 0) {
            this.itemEquWidthCountRange = new IntRange(i2, Integer.MAX_VALUE);
        }
        int i3 = R.styleable.DslTabLayout_tab_item_equ_width_count_range;
        if (typedArrayObtainStyledAttributes.hasValue(i3)) {
            String string = typedArrayObtainStyledAttributes.getString(i3);
            if (string == null || StringsKt__StringsJVMKt.isBlank(string)) {
                this.itemEquWidthCountRange = null;
            } else {
                List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) string, new String[]{Constants.WAVE_SEPARATOR}, false, 0, 6, (Object) null);
                if (LibExKt.size(listSplit$default) >= 2) {
                    String str = (String) CollectionsKt___CollectionsKt.getOrNull(listSplit$default, 0);
                    int iIntValue2 = (str == null || (intOrNull3 = StringsKt__StringNumberConversionsKt.toIntOrNull(str)) == null) ? 0 : intOrNull3.intValue();
                    String str2 = (String) CollectionsKt___CollectionsKt.getOrNull(listSplit$default, 1);
                    if (str2 != null && (intOrNull2 = StringsKt__StringNumberConversionsKt.toIntOrNull(str2)) != null) {
                        iIntValue = intOrNull2.intValue();
                    }
                    this.itemEquWidthCountRange = new IntRange(iIntValue2, iIntValue);
                } else {
                    String str3 = (String) CollectionsKt___CollectionsKt.getOrNull(listSplit$default, 0);
                    this.itemEquWidthCountRange = new IntRange((str3 == null || (intOrNull = StringsKt__StringNumberConversionsKt.toIntOrNull(str3)) == null) ? Integer.MAX_VALUE : intOrNull.intValue(), Integer.MAX_VALUE);
                }
            }
        }
        this.itemAutoEquWidth = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_item_auto_equ_width, this.itemAutoEquWidth);
        this.itemWidth = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_item_width, this.itemWidth);
        this.itemDefaultHeight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_item_default_height, this.itemDefaultHeight);
        this.tabDefaultIndex = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_default_index, this.tabDefaultIndex);
        this.drawIndicator = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_draw_indicator, this.drawIndicator);
        this.drawDivider = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_draw_divider, this.drawDivider);
        this.drawBorder = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_draw_border, this.drawBorder);
        this.drawBadge = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_draw_badge, this.drawBadge);
        this.drawHighlight = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_draw_highlight, this.drawHighlight);
        this.tabEnableSelectorMode = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_enable_selector_mode, this.tabEnableSelectorMode);
        this.tabConvexBackgroundDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.DslTabLayout_tab_convex_background);
        this.orientation = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_orientation, this.orientation);
        this.layoutScrollAnim = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_layout_scroll_anim, this.layoutScrollAnim);
        this.scrollAnimDuration = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_scroll_anim_duration, this.scrollAnimDuration);
        if (isInEditMode()) {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.DslTabLayout_tab_preview_item_layout_id, -1);
            int i4 = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_preview_item_count, 3);
            if (resourceId != -1) {
                for (int i5 = 0; i5 < i4; i5++) {
                    View viewInflate = LibExKt.inflate(this, resourceId, true);
                    if (viewInflate instanceof TextView) {
                        TextView textView = (TextView) viewInflate;
                        CharSequence text = textView.getText();
                        if (text == null || text.length() == 0) {
                            textView.setText("Item " + i5);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append((Object) textView.getText());
                            sb.append('/');
                            sb.append(i5);
                            textView.setText(sb.toString());
                        }
                    }
                }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this._minFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this._maxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        if (this.drawIndicator) {
            this.tabIndicator.initAttribute(context, this.attributeSet);
        }
        if (this.drawBorder) {
            setTabBorder(new DslTabBorder());
        }
        if (this.drawDivider) {
            setTabDivider(new DslTabDivider());
        }
        if (this.drawBadge) {
            setTabBadge(new DslTabBadge());
        }
        if (this.drawHighlight) {
            setTabHighlight(new DslTabHighlight(this));
        }
        setTabLayoutConfig(new DslTabLayoutConfig(this));
        setWillNotDraw(false);
        this._layoutDirection = -1;
        this._overScroller = LazyKt__LazyJVMKt.lazy(new Function0<OverScroller>() { // from class: com.angcyo.tablayout.DslTabLayout$_overScroller$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final OverScroller invoke() {
                return new OverScroller(context);
            }
        });
        this._gestureDetector = LazyKt__LazyJVMKt.lazy(new Function0<GestureDetectorCompat>() { // from class: com.angcyo.tablayout.DslTabLayout$_gestureDetector$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final GestureDetectorCompat invoke() {
                Context context2 = context;
                final DslTabLayout dslTabLayout = this;
                return new GestureDetectorCompat(context2, new GestureDetector.SimpleOnGestureListener() { // from class: com.angcyo.tablayout.DslTabLayout$_gestureDetector$2.1
                    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                    public boolean onFling(@NotNull MotionEvent e12, @NotNull MotionEvent e2, float velocityX, float velocityY) {
                        Intrinsics.checkNotNullParameter(e12, "e1");
                        Intrinsics.checkNotNullParameter(e2, "e2");
                        if (dslTabLayout.isHorizontal()) {
                            if (Math.abs(velocityX) <= dslTabLayout.get_minFlingVelocity()) {
                                return true;
                            }
                            dslTabLayout.onFlingChange(velocityX);
                            return true;
                        }
                        if (Math.abs(velocityY) <= dslTabLayout.get_minFlingVelocity()) {
                            return true;
                        }
                        dslTabLayout.onFlingChange(velocityY);
                        return true;
                    }

                    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                    public boolean onScroll(@NotNull MotionEvent e12, @NotNull MotionEvent e2, float distanceX, float distanceY) {
                        Intrinsics.checkNotNullParameter(e12, "e1");
                        Intrinsics.checkNotNullParameter(e2, "e2");
                        if (dslTabLayout.isHorizontal()) {
                            if (Math.abs(distanceX) > dslTabLayout.get_touchSlop()) {
                                return dslTabLayout.onScrollChange(distanceX);
                            }
                        } else if (Math.abs(distanceY) > dslTabLayout.get_touchSlop()) {
                            return dslTabLayout.onScrollChange(distanceY);
                        }
                        return false;
                    }
                });
            }
        });
        this._scrollAnimator = LazyKt__LazyJVMKt.lazy(new DslTabLayout$_scrollAnimator$2(this));
    }

    @Override // android.view.ViewGroup
    @NotNull
    public ViewGroup.LayoutParams generateLayoutParams(@Nullable ViewGroup.LayoutParams p2) {
        return p2 != null ? new LayoutParams(p2) : generateDefaultLayoutParams();
    }

    public final void updateTabBadge(int index, @NotNull Function1<? super TabBadgeConfig, Unit> config) {
        Intrinsics.checkNotNullParameter(config, "config");
        TabBadgeConfig badgeConfig = getBadgeConfig(index);
        this.tabBadgeConfigMap.put(Integer.valueOf(index), badgeConfig);
        config.invoke(badgeConfig);
        postInvalidate();
    }

    public final void setTabLayoutConfig(@NotNull DslTabLayoutConfig config, @NotNull Function1<? super DslTabLayoutConfig, Unit> doIt) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(doIt, "doIt");
        setTabLayoutConfig(config);
        configTabLayoutConfig(doIt);
    }

    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u0017\b\u0016\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b¢\u0006\u0002\u0010\rB\u001f\b\u0016\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u000b¢\u0006\u0002\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u001a\u0010\u0018\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R\u001a\u0010\u001b\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0012\"\u0004\b\u001d\u0010\u0014R\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0012\"\u0004\b&\u0010\u0014R\u001a\u0010'\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0012\"\u0004\b)\u0010\u0014R\u001a\u0010*\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0012\"\u0004\b,\u0010\u0014R\u001c\u0010-\u001a\u0004\u0018\u00010.X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001c\u00103\u001a\u0004\u0018\u00010.X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00100\"\u0004\b5\u00102R\u001a\u00106\u001a\u000207X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;¨\u0006<"}, d2 = {"Lcom/angcyo/tablayout/DslTabLayout$LayoutParams;", "Landroid/widget/FrameLayout$LayoutParams;", am.aF, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", SocialConstants.PARAM_SOURCE, "Landroid/view/ViewGroup$LayoutParams;", "(Landroid/view/ViewGroup$LayoutParams;)V", "width", "", "height", "(II)V", "gravity", "(III)V", "contentIconViewId", "getContentIconViewId", "()I", "setContentIconViewId", "(I)V", "contentIconViewIndex", "getContentIconViewIndex", "setContentIconViewIndex", "contentTextViewId", "getContentTextViewId", "setContentTextViewId", "contentTextViewIndex", "getContentTextViewIndex", "setContentTextViewIndex", "highlightDrawable", "Landroid/graphics/drawable/Drawable;", "getHighlightDrawable", "()Landroid/graphics/drawable/Drawable;", "setHighlightDrawable", "(Landroid/graphics/drawable/Drawable;)V", "indicatorContentId", "getIndicatorContentId", "setIndicatorContentId", "indicatorContentIndex", "getIndicatorContentIndex", "setIndicatorContentIndex", "layoutConvexHeight", "getLayoutConvexHeight", "setLayoutConvexHeight", "layoutHeight", "", "getLayoutHeight", "()Ljava/lang/String;", "setLayoutHeight", "(Ljava/lang/String;)V", "layoutWidth", "getLayoutWidth", "setLayoutWidth", "weight", "", "getWeight", "()F", "setWeight", "(F)V", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
    public static final class LayoutParams extends FrameLayout.LayoutParams {
        private int contentIconViewId;
        private int contentIconViewIndex;
        private int contentTextViewId;
        private int contentTextViewIndex;

        @Nullable
        private Drawable highlightDrawable;
        private int indicatorContentId;
        private int indicatorContentIndex;
        private int layoutConvexHeight;

        @Nullable
        private String layoutHeight;

        @Nullable
        private String layoutWidth;
        private float weight;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LayoutParams(@NotNull Context c3, @Nullable AttributeSet attributeSet) {
            super(c3, attributeSet);
            Intrinsics.checkNotNullParameter(c3, "c");
            this.indicatorContentIndex = -1;
            this.indicatorContentId = -1;
            this.contentTextViewIndex = -1;
            this.contentTextViewId = -1;
            this.contentIconViewIndex = -1;
            this.contentIconViewId = -1;
            this.weight = -1.0f;
            TypedArray typedArrayObtainStyledAttributes = c3.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout_Layout);
            Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "c.obtainStyledAttributes…able.DslTabLayout_Layout)");
            this.layoutWidth = typedArrayObtainStyledAttributes.getString(R.styleable.DslTabLayout_Layout_layout_tab_width);
            this.layoutHeight = typedArrayObtainStyledAttributes.getString(R.styleable.DslTabLayout_Layout_layout_tab_height);
            this.layoutConvexHeight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_Layout_layout_tab_convex_height, this.layoutConvexHeight);
            this.indicatorContentIndex = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_Layout_layout_tab_indicator_content_index, this.indicatorContentIndex);
            this.indicatorContentId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.DslTabLayout_Layout_layout_tab_indicator_content_id, this.indicatorContentId);
            this.weight = typedArrayObtainStyledAttributes.getFloat(R.styleable.DslTabLayout_Layout_layout_tab_weight, this.weight);
            this.highlightDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.DslTabLayout_Layout_layout_highlight_drawable);
            int i2 = R.styleable.DslTabLayout_Layout_layout_tab_text_view_index;
            this.contentTextViewIndex = typedArrayObtainStyledAttributes.getInt(i2, this.contentTextViewIndex);
            this.contentIconViewIndex = typedArrayObtainStyledAttributes.getInt(i2, this.contentIconViewIndex);
            this.contentTextViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.DslTabLayout_Layout_layout_tab_text_view_id, this.contentTextViewId);
            this.contentIconViewId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.DslTabLayout_Layout_layout_tab_icon_view_id, this.contentIconViewIndex);
            typedArrayObtainStyledAttributes.recycle();
            if (((FrameLayout.LayoutParams) this).gravity == -1) {
                ((FrameLayout.LayoutParams) this).gravity = this.layoutConvexHeight > 0 ? 80 : 17;
            }
        }

        public final int getContentIconViewId() {
            return this.contentIconViewId;
        }

        public final int getContentIconViewIndex() {
            return this.contentIconViewIndex;
        }

        public final int getContentTextViewId() {
            return this.contentTextViewId;
        }

        public final int getContentTextViewIndex() {
            return this.contentTextViewIndex;
        }

        @Nullable
        public final Drawable getHighlightDrawable() {
            return this.highlightDrawable;
        }

        public final int getIndicatorContentId() {
            return this.indicatorContentId;
        }

        public final int getIndicatorContentIndex() {
            return this.indicatorContentIndex;
        }

        public final int getLayoutConvexHeight() {
            return this.layoutConvexHeight;
        }

        @Nullable
        public final String getLayoutHeight() {
            return this.layoutHeight;
        }

        @Nullable
        public final String getLayoutWidth() {
            return this.layoutWidth;
        }

        public final float getWeight() {
            return this.weight;
        }

        public final void setContentIconViewId(int i2) {
            this.contentIconViewId = i2;
        }

        public final void setContentIconViewIndex(int i2) {
            this.contentIconViewIndex = i2;
        }

        public final void setContentTextViewId(int i2) {
            this.contentTextViewId = i2;
        }

        public final void setContentTextViewIndex(int i2) {
            this.contentTextViewIndex = i2;
        }

        public final void setHighlightDrawable(@Nullable Drawable drawable) {
            this.highlightDrawable = drawable;
        }

        public final void setIndicatorContentId(int i2) {
            this.indicatorContentId = i2;
        }

        public final void setIndicatorContentIndex(int i2) {
            this.indicatorContentIndex = i2;
        }

        public final void setLayoutConvexHeight(int i2) {
            this.layoutConvexHeight = i2;
        }

        public final void setLayoutHeight(@Nullable String str) {
            this.layoutHeight = str;
        }

        public final void setLayoutWidth(@Nullable String str) {
            this.layoutWidth = str;
        }

        public final void setWeight(float f2) {
            this.weight = f2;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LayoutParams(@NotNull ViewGroup.LayoutParams source) {
            super(source);
            Intrinsics.checkNotNullParameter(source, "source");
            this.indicatorContentIndex = -1;
            this.indicatorContentId = -1;
            this.contentTextViewIndex = -1;
            this.contentTextViewId = -1;
            this.contentIconViewIndex = -1;
            this.contentIconViewId = -1;
            this.weight = -1.0f;
            if (source instanceof LayoutParams) {
                LayoutParams layoutParams = (LayoutParams) source;
                this.layoutWidth = layoutParams.layoutWidth;
                this.layoutHeight = layoutParams.layoutHeight;
                this.layoutConvexHeight = layoutParams.layoutConvexHeight;
                this.weight = layoutParams.weight;
                this.highlightDrawable = layoutParams.highlightDrawable;
            }
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
            this.indicatorContentIndex = -1;
            this.indicatorContentId = -1;
            this.contentTextViewIndex = -1;
            this.contentTextViewId = -1;
            this.contentIconViewIndex = -1;
            this.contentIconViewId = -1;
            this.weight = -1.0f;
        }

        public LayoutParams(int i2, int i3, int i4) {
            super(i2, i3, i4);
            this.indicatorContentIndex = -1;
            this.indicatorContentId = -1;
            this.contentTextViewIndex = -1;
            this.contentTextViewId = -1;
            this.contentIconViewIndex = -1;
            this.contentIconViewId = -1;
            this.weight = -1.0f;
        }
    }
}
