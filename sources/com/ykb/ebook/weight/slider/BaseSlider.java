package com.ykb.ebook.weight.slider;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.TypedArrayKt;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.math.MathUtils;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.ykb.ebook.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt__MathJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0010\t\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\bX\b&\u0018\u0000 \u0089\u00022\u00020\u0001:\u0004\u0089\u0002\u008a\u0002B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010p\u001a\u00020q2\u0006\u0010r\u001a\u00020\u0001J\u001a\u0010s\u001a\u00020q2\u0006\u0010t\u001a\u00020\n2\b\b\u0002\u0010?\u001a\u00020\u0007H\u0002J\u0010\u0010u\u001a\u00020q2\u0006\u0010?\u001a\u00020\u0007H\u0002J\u000e\u0010v\u001a\u00020q2\u0006\u0010w\u001a\u00020xJ \u0010y\u001a\u00020\u00102\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J \u0010~\u001a\u00020\u00102\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J\"\u0010\u007f\u001a\u00020\u00102\u0006\u0010z\u001a\u00020{2\u0007\u0010\u0080\u0001\u001a\u00020/2\u0007\u0010\u0081\u0001\u001a\u00020/H&J!\u0010\u0082\u0001\u001a\u00020\u00102\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J\"\u0010\u0083\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0007\u0010\u0084\u0001\u001a\u00020\u00072\u0006\u0010}\u001a\u00020/H\u0002J\"\u0010\u0085\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0007\u0010\u0084\u0001\u001a\u00020\u00072\u0006\u0010}\u001a\u00020/H\u0002J\"\u0010\u0086\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0007\u0010\u0084\u0001\u001a\u00020\u00072\u0006\u0010}\u001a\u00020/H\u0002J!\u0010\u0087\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J\u0019\u0010\u0088\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0006\u0010}\u001a\u00020/H\u0002J!\u0010\u0089\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J\"\u0010\u008a\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0007\u0010\u0084\u0001\u001a\u00020\u00072\u0006\u0010}\u001a\u00020/H\u0002J#\u0010\u008b\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0007\u0010\u0080\u0001\u001a\u00020/2\u0007\u0010\u0081\u0001\u001a\u00020/H&J\"\u0010\u008c\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0007\u0010\u0084\u0001\u001a\u00020\u00072\u0006\u0010}\u001a\u00020/H\u0002J\u0019\u0010\u008d\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0006\u0010}\u001a\u00020/H\u0002J!\u0010\u008e\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J\t\u0010\u008f\u0001\u001a\u00020qH\u0014J\u0007\u0010\u0090\u0001\u001a\u00020\u0010J\t\u0010\u0091\u0001\u001a\u00020\u0010H\u0002J\u0012\u0010\u0092\u0001\u001a\u00020\u00072\u0007\u0010\u0093\u0001\u001a\u00020\u001bH\u0007J\u0007\u0010\u0094\u0001\u001a\u00020/J\u0007\u0010\u0095\u0001\u001a\u00020/J\u0012\u0010\u0096\u0001\u001a\u00020/2\u0007\u0010\u0097\u0001\u001a\u00020/H\u0002J\u0012\u0010\u0098\u0001\u001a\u00020/2\u0007\u0010\u0099\u0001\u001a\u00020*H\u0002J\u0012\u0010\u009a\u0001\u001a\u00020/2\u0007\u0010\u009b\u0001\u001a\u00020/H\u0002J\u001e\u0010\u009c\u0001\u001a\u00020q2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u00102\n\b\u0002\u0010\u009e\u0001\u001a\u00030\u009f\u0001J\u0019\u0010 \u0001\u001a\u00020q2\u0006\u0010t\u001a\u00020\u001d2\u0006\u0010?\u001a\u00020\u0007H\u0003J\u0012\u0010¡\u0001\u001a\u00020\n2\u0007\u0010¢\u0001\u001a\u00020\nH\u0002J\u0011\u0010£\u0001\u001a\u00020q2\u0006\u0010t\u001a\u00020\nH\u0016J\u001b\u0010¤\u0001\u001a\u00020\u00102\u0007\u0010¥\u0001\u001a\u00020*2\u0007\u0010¦\u0001\u001a\u00020*H\u0002J\t\u0010§\u0001\u001a\u00020\u0010H\u0002J\t\u0010¨\u0001\u001a\u00020qH\u0014J\t\u0010©\u0001\u001a\u00020qH\u0014J\u0011\u0010ª\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{H\u0014J!\u0010«\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J!\u0010¬\u0001\u001a\u00020q2\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020`2\u0006\u0010}\u001a\u00020/H&J\u001b\u0010\u00ad\u0001\u001a\u00020q2\u0007\u0010®\u0001\u001a\u00020\u00072\u0007\u0010¯\u0001\u001a\u00020\u0007H\u0014J\t\u0010°\u0001\u001a\u00020qH\u0016J\u0015\u0010±\u0001\u001a\u00020q2\n\u0010²\u0001\u001a\u0005\u0018\u00010³\u0001H\u0014J\f\u0010´\u0001\u001a\u0005\u0018\u00010³\u0001H\u0014J-\u0010µ\u0001\u001a\u00020q2\u0007\u0010¶\u0001\u001a\u00020\u00072\u0007\u0010·\u0001\u001a\u00020\u00072\u0007\u0010¸\u0001\u001a\u00020\u00072\u0007\u0010¹\u0001\u001a\u00020\u0007H\u0014J\t\u0010º\u0001\u001a\u00020qH&J\t\u0010»\u0001\u001a\u00020qH&J\u0012\u0010¼\u0001\u001a\u00020\u00102\u0007\u0010\u0099\u0001\u001a\u00020*H\u0016J\u001a\u0010½\u0001\u001a\u00020q2\u0006\u00105\u001a\u00020/2\u0007\u0010¾\u0001\u001a\u00020\u0010H&J\u0012\u0010¿\u0001\u001a\u00020/2\t\b\u0002\u0010À\u0001\u001a\u00020/J%\u0010Á\u0001\u001a\u00020q2\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0010\u0010Â\u0001\u001a\u00020q2\u0007\u0010Ã\u0001\u001a\u00020\u0010J\u0010\u0010Ä\u0001\u001a\u00020q2\u0007\u0010Ã\u0001\u001a\u00020\u0010J\u0011\u0010Å\u0001\u001a\u00020q2\b\b\u0001\u0010?\u001a\u00020\u0007J\u000f\u0010Æ\u0001\u001a\u00020q2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000f\u0010Ç\u0001\u001a\u00020q2\u0006\u00100\u001a\u00020/J\u000f\u0010È\u0001\u001a\u00020q2\u0006\u0010t\u001a\u00020\nJ\u0012\u0010È\u0001\u001a\u00020q2\t\b\u0001\u0010É\u0001\u001a\u00020\u0007J\u0010\u0010Ê\u0001\u001a\u00020q2\u0007\u0010Ë\u0001\u001a\u00020/J\u0012\u0010Ì\u0001\u001a\u00020q2\t\b\u0001\u0010Í\u0001\u001a\u00020\u0007J\u0012\u0010Î\u0001\u001a\u00020q2\t\u0010Ï\u0001\u001a\u0004\u0018\u00010\u001bJ\u0010\u0010Ð\u0001\u001a\u00020q2\u0007\u0010Ñ\u0001\u001a\u00020/J\u0012\u0010Ò\u0001\u001a\u00020q2\t\u0010Ó\u0001\u001a\u0004\u0018\u00010FJ\u0010\u0010Ô\u0001\u001a\u00020q2\u0007\u0010Õ\u0001\u001a\u00020\u0010J\u0010\u0010Ö\u0001\u001a\u00020q2\u0007\u0010×\u0001\u001a\u00020/J\u0012\u0010Ø\u0001\u001a\u00020q2\t\u0010Ù\u0001\u001a\u0004\u0018\u00010\u001bJ\u0010\u0010Ú\u0001\u001a\u00020q2\u0007\u0010Û\u0001\u001a\u00020\u001bJ\u0010\u0010Ü\u0001\u001a\u00020q2\u0007\u0010Ý\u0001\u001a\u00020\u0007J!\u0010Þ\u0001\u001a\u00020q2\u0006\u0010J\u001a\u00020\u00072\u0006\u0010=\u001a\u00020\u00072\b\b\u0002\u0010?\u001a\u00020\u0007J\u0010\u0010ß\u0001\u001a\u00020q2\u0007\u0010à\u0001\u001a\u00020\u0010J\u0011\u0010á\u0001\u001a\u00020q2\b\b\u0001\u0010K\u001a\u00020/J\u0010\u0010â\u0001\u001a\u00020q2\u0007\u0010Ù\u0001\u001a\u00020\u001bJ\u0010\u0010ã\u0001\u001a\u00020q2\u0007\u0010Ù\u0001\u001a\u00020\u001bJ\u0012\u0010ä\u0001\u001a\u00020q2\t\b\u0001\u0010Ù\u0001\u001a\u00020\u0007J\u0010\u0010å\u0001\u001a\u00020q2\u0007\u0010æ\u0001\u001a\u00020\u0010J\u0012\u0010ç\u0001\u001a\u00020q2\t\b\u0001\u0010Ù\u0001\u001a\u00020\u0007J\u0010\u0010è\u0001\u001a\u00020q2\u0007\u0010Ý\u0001\u001a\u00020\u0007J\u0010\u0010é\u0001\u001a\u00020q2\u0007\u0010Ã\u0001\u001a\u00020\u0010J\u0010\u0010ê\u0001\u001a\u00020q2\u0007\u0010ë\u0001\u001a\u00020\u0010J\u0010\u0010ì\u0001\u001a\u00020q2\u0007\u0010í\u0001\u001a\u00020\u0007J\u0011\u0010î\u0001\u001a\u00020q2\b\b\u0001\u0010?\u001a\u00020\u0007J\u0010\u0010ï\u0001\u001a\u00020q2\u0007\u0010Ù\u0001\u001a\u00020\u001bJ\u0012\u0010ð\u0001\u001a\u00020q2\t\b\u0002\u0010ñ\u0001\u001a\u00020\u0007J\u0010\u0010ò\u0001\u001a\u00020q2\u0007\u0010ñ\u0001\u001a\u00020\u0007J\u0010\u0010ó\u0001\u001a\u00020q2\u0007\u0010Ù\u0001\u001a\u00020\u001bJ\u0010\u0010ô\u0001\u001a\u00020q2\u0007\u0010Ù\u0001\u001a\u00020\u001bJ\u0019\u0010g\u001a\u00020q2\u0006\u00105\u001a\u00020/2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u0010J\u0007\u0010õ\u0001\u001a\u00020\u0010J\u001e\u0010ö\u0001\u001a\u00020q2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u00102\n\b\u0002\u0010\u009e\u0001\u001a\u00030\u009f\u0001J\u0012\u0010÷\u0001\u001a\u00020/2\u0007\u0010\u009b\u0001\u001a\u00020/H\u0002J\u0012\u0010ø\u0001\u001a\u00020q2\u0007\u0010\u0099\u0001\u001a\u00020*H\u0002J\u0012\u0010ù\u0001\u001a\u00020q2\u0007\u0010\u0099\u0001\u001a\u00020*H\u0002J\u0012\u0010ú\u0001\u001a\u00020q2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u0010J\u0012\u0010û\u0001\u001a\u00020q2\u0007\u0010\u0099\u0001\u001a\u00020*H\u0002J\t\u0010ü\u0001\u001a\u00020qH&J\t\u0010ý\u0001\u001a\u00020qH\u0002J\u0010\u0010þ\u0001\u001a\u00020q2\u0007\u0010ÿ\u0001\u001a\u00020\u0007J\u001c\u0010\u0080\u0002\u001a\u00020q2\u0006\u00105\u001a\u00020/2\t\b\u0002\u0010\u009d\u0001\u001a\u00020\u0010H\u0002J\u0007\u0010\u0081\u0002\u001a\u00020qJ\t\u0010\u0082\u0002\u001a\u00020qH\u0002J\t\u0010\u0083\u0002\u001a\u00020qH\u0002J\t\u0010\u0084\u0002\u001a\u00020qH\u0002J\t\u0010\u0085\u0002\u001a\u00020qH\u0002J0\u0010\u0086\u0002\u001a\u00020q2\u0006\u00105\u001a\u00020/2\u0007\u0010¾\u0001\u001a\u00020\u00102\t\b\u0002\u0010\u0097\u0001\u001a\u00020/2\t\b\u0002\u0010\u0087\u0002\u001a\u00020/H\u0002J\u0007\u0010\u0088\u0002\u001a\u00020\u0010R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010#\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0014\"\u0004\b$\u0010\u0016R\u000e\u0010%\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u00100\u001a\u00020/2\u0006\u0010.\u001a\u00020/@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u000e\u00103\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u00106\u001a\u00020/2\u0006\u00105\u001a\u00020/@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u00102\"\u0004\b8\u00109R\u000e\u0010:\u001a\u00020;X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010@\u001a\u00020\u00072\b\b\u0001\u0010?\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u0010\u0010E\u001a\u0004\u0018\u00010FX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010L\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010\u0014\"\u0004\bN\u0010\u0016R\u000e\u0010O\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020SX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R&\u0010Y\u001a\u00020\u00072\b\b\u0001\u00105\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010B\"\u0004\b[\u0010DR\u000e\u0010\\\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020`X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010c\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010B\"\u0004\be\u0010DR$\u00105\u001a\u00020/2\u0006\u00105\u001a\u00020/@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u00102\"\u0004\bg\u00109R$\u0010h\u001a\u00020/2\u0006\u00105\u001a\u00020/@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u00102\"\u0004\bj\u00109R$\u0010k\u001a\u00020/2\u0006\u00105\u001a\u00020/@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bl\u00102\"\u0004\bm\u00109R\u000e\u0010n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020`X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u008b\u0002"}, d2 = {"Lcom/ykb/ebook/weight/slider/BaseSlider;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "customThumbDrawable", "Landroid/graphics/drawable/Drawable;", "debugPaint", "Landroid/graphics/Paint;", "defaultThumbDrawable", "Lcom/ykb/ebook/weight/slider/DefaultThumbDrawable;", "enableAutoHPadding", "", "enableDrawHalo", "enableHapticFeedback", "getEnableHapticFeedback", "()Z", "setEnableHapticFeedback", "(Z)V", "enableProgressAnim", "getEnableProgressAnim", "setEnableProgressAnim", "haloColor", "Landroid/content/res/ColorStateList;", "haloDrawable", "Landroid/graphics/drawable/RippleDrawable;", "haloPaint", "haloRadius", "hasDirtyData", "inactiveTicksPaint", "inactiveTrackPaint", "isConsecutiveProgress", "setConsecutiveProgress", "isDragging", "isShowTipView", "isTackingStart", "isThumbWithinTrackBounds", "lastTouchEvent", "Landroid/view/MotionEvent;", "progressAnimator", "Landroid/animation/ValueAnimator;", "scaledTouchSlop", "<set-?>", "", "secondaryValue", "getSecondaryValue", "()F", "sliderTouchMode", "sourceViewHeight", "value", "stepSize", "getStepSize", "setStepSize", "(F)V", "thumbAnimation", "Lcom/ykb/ebook/weight/slider/ThumbValueAnimation;", "thumbElevation", "thumbHeight", "thumbOffset", "radius", "thumbRadius", "getThumbRadius", "()I", "setThumbRadius", "(I)V", "thumbText", "", "thumbTextColor", "thumbTextPaint", "thumbVOffset", "thumbWidth", "tickRadius", "tickVisible", "getTickVisible", "setTickVisible", "ticksColor", "ticksColorInactive", "ticksPaint", "tipView", "Lcom/ykb/ebook/weight/slider/TipViewContainer;", "touchDownDiffValue", "touchDownX", "trackColor", "trackColorInactive", "trackCornerRadius", "trackHeight", "getTrackHeight", "setTrackHeight", "trackInnerHPadding", "trackInnerVPadding", "trackPaint", "trackRectF", "Landroid/graphics/RectF;", "trackSecondaryColor", "trackSecondaryPaint", "trackWidth", "getTrackWidth", "setTrackWidth", "getValue", "setValue", "valueFrom", "getValueFrom", "setValueFrom", "valueTo", "getValueTo", "setValueTo", "viewHeight", "viewRectF", "addCustomTipView", "", "view", "adjustCustomThumbDrawableBounds", "drawable", "adjustThumbDrawableBounds", "createTipAnimation", "animator", "Lcom/ykb/ebook/weight/slider/TipViewAnimator;", "dispatchDrawInactiveTrackBefore", "canvas", "Landroid/graphics/Canvas;", "trackRect", "yCenter", "dispatchDrawSecondaryTrackBefore", "dispatchDrawThumbBefore", "cx", "cy", "dispatchDrawTrackBefore", "drawCompatHaloIfNeed", "width", "drawDebugArea", "drawInactiveTrack", "drawInactiveTrackAfter", "drawSecondaryTrack", "drawSecondaryTrackAfter", "drawThumb", "drawThumbAfter", "drawTicks", "drawTrack", "drawTrackAfter", "drawableStateChanged", "enableStepMode", "enableTouch", "getColorForState", "colorStateList", "getThumbCenterX", "getThumbCenterY", "getTouchPosByX", "touchX", "getTouchValue", NotificationCompat.CATEGORY_EVENT, "getValueByTouchPos", "pos", "hideThumb", "animated", "delayMillis", "", "hookRippleRadius", "initializeCustomThumbDrawable", "originalDrawable", "invalidateDrawable", "isClickTouch", "startEvent", "endEvent", "isInVerticalScrollingContainer", "onAttachedToWindow", "onDetachedFromWindow", "onDraw", "onDrawAfter", "onDrawBefore", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onProgressAnimEnd", "onRestoreInstanceState", "state", "Landroid/os/Parcelable;", "onSaveInstanceState", "onSizeChanged", "w", "h", "oldw", "oldh", "onStartTacking", "onStopTacking", "onTouchEvent", "onValueChanged", "fromUser", "percentValue", "v", "processAttributes", "setEnableAutoHPadding", "enable", "setEnableDrawHalo", "setHaloRadius", "setHaloTintList", "setSecondaryValue", "setThumbCustomDrawable", "drawableResId", "setThumbElevation", "elevation", "setThumbShadowColor", "shadowColor", "setThumbStrokeColor", "thumbStrokeColor", "setThumbStrokeWidth", "thumbStrokeWidth", "setThumbText", "text", "setThumbTextBold", "isBold", "setThumbTextSize", DatabaseManager.SIZE, "setThumbTextTintList", "color", "setThumbTintList", "thumbColor", "setThumbVOffset", "offset", "setThumbWidthAndHeight", "setThumbWithinTrackBounds", "isInBounds", "setTickRadius", "setTicksInactiveTintList", "setTicksTintList", "setTipBackground", "setTipTextAutoChange", "isAutoChange", "setTipTextColor", "setTipVerticalOffset", "setTipViewClippingEnabled", "setTipViewVisibility", "visibility", "setTouchMode", "mode", "setTrackCornersRadius", "setTrackInactiveTintList", "setTrackInnerHPadding", "padding", "setTrackInnerVPadding", "setTrackSecondaryTintList", "setTrackTintList", "shouldDrawCompatHalo", "showThumb", "snapStepPos", "startTacking", "stopTacking", "toggleThumbVisibility", "trackTouchEvent", "updateDirtyData", "updateHaloHotspot", "updateTrackWidth", "viewWidth", "updateValue", "updateViewLayout", "validateDirtyData", "validateValue", "validateValueFrom", "validateValueTo", "valueChanged", "touchRawX", "viewHeightChanged", "Companion", "SavedState", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBaseSlider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BaseSlider.kt\ncom/ykb/ebook/weight/slider/BaseSlider\n+ 2 Animator.kt\nandroidx/core/animation/AnimatorKt\n+ 3 Context.kt\nandroidx/core/content/ContextKt\n+ 4 Canvas.kt\nandroidx/core/graphics/CanvasKt\n*L\n1#1,1808:1\n31#2:1809\n94#2,14:1810\n59#3,2:1824\n47#4,8:1826\n*S KotlinDebug\n*F\n+ 1 BaseSlider.kt\ncom/ykb/ebook/weight/slider/BaseSlider\n*L\n265#1:1809\n265#1:1810,14\n276#1:1824,2\n644#1:1826,8\n*E\n"})
/* loaded from: classes8.dex */
public abstract class BaseSlider extends View {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static boolean DEBUG_MODE = false;
    private static final int HALO_ALPHA = 63;
    private static final int HIGH_QUALITY_FLAGS = 5;
    private static final int MODE_DISABLE_CLICK_TOUCH = 2;
    private static final int MODE_DISABLE_TOUCH = 1;
    private static final int MODE_NORMAL = 0;

    @Nullable
    private Drawable customThumbDrawable;

    @NotNull
    private final Paint debugPaint;

    @NotNull
    private final DefaultThumbDrawable defaultThumbDrawable;
    private boolean enableAutoHPadding;
    private boolean enableDrawHalo;
    private boolean enableHapticFeedback;
    private boolean enableProgressAnim;
    private ColorStateList haloColor;

    @Nullable
    private RippleDrawable haloDrawable;

    @NotNull
    private final Paint haloPaint;
    private int haloRadius;
    private boolean hasDirtyData;

    @NotNull
    private final Paint inactiveTicksPaint;

    @NotNull
    private final Paint inactiveTrackPaint;
    private boolean isConsecutiveProgress;
    private boolean isDragging;
    private boolean isShowTipView;
    private boolean isTackingStart;
    private boolean isThumbWithinTrackBounds;

    @Nullable
    private MotionEvent lastTouchEvent;

    @NotNull
    private ValueAnimator progressAnimator;
    private int scaledTouchSlop;
    private float secondaryValue;
    private int sliderTouchMode;
    private int sourceViewHeight;
    private float stepSize;

    @NotNull
    private final ThumbValueAnimation thumbAnimation;
    private float thumbElevation;
    private int thumbHeight;
    private int thumbOffset;
    private int thumbRadius;

    @Nullable
    private String thumbText;
    private ColorStateList thumbTextColor;

    @NotNull
    private final Paint thumbTextPaint;
    private int thumbVOffset;
    private int thumbWidth;
    private float tickRadius;
    private boolean tickVisible;
    private ColorStateList ticksColor;
    private ColorStateList ticksColorInactive;

    @NotNull
    private Paint ticksPaint;

    @NotNull
    private TipViewContainer tipView;
    private float touchDownDiffValue;
    private float touchDownX;
    private ColorStateList trackColor;
    private ColorStateList trackColorInactive;
    private int trackCornerRadius;
    private int trackHeight;
    private int trackInnerHPadding;
    private int trackInnerVPadding;

    @NotNull
    private final Paint trackPaint;

    @NotNull
    private final RectF trackRectF;
    private ColorStateList trackSecondaryColor;

    @NotNull
    private final Paint trackSecondaryPaint;
    private int trackWidth;
    private float value;
    private float valueFrom;
    private float valueTo;
    private int viewHeight;

    @NotNull
    private final RectF viewRectF;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/ykb/ebook/weight/slider/BaseSlider$Companion;", "", "()V", "DEBUG_MODE", "", "getDEBUG_MODE", "()Z", "setDEBUG_MODE", "(Z)V", "HALO_ALPHA", "", "HIGH_QUALITY_FLAGS", "MODE_DISABLE_CLICK_TOUCH", "MODE_DISABLE_TOUCH", "MODE_NORMAL", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean getDEBUG_MODE() {
            return BaseSlider.DEBUG_MODE;
        }

        public final void setDEBUG_MODE(boolean z2) {
            BaseSlider.DEBUG_MODE = z2;
        }
    }

    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r¨\u0006\u0016"}, d2 = {"Lcom/ykb/ebook/weight/slider/BaseSlider$SavedState;", "Landroid/view/View$BaseSavedState;", "superState", "Landroid/os/Parcelable;", "(Landroid/os/Parcelable;)V", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "secondaryValue", "", "getSecondaryValue", "()F", "setSecondaryValue", "(F)V", "value", "getValue", "setValue", "writeToParcel", "", "flags", "", "CREATOR", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class SavedState extends View.BaseSavedState {

        /* renamed from: CREATOR, reason: from kotlin metadata */
        @NotNull
        public static final Companion INSTANCE = new Companion(null);
        private float secondaryValue;
        private float value;

        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/weight/slider/BaseSlider$SavedState$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/ykb/ebook/weight/slider/BaseSlider$SavedState;", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", DatabaseManager.SIZE, "", "(I)[Lcom/ykb/ebook/weight/slider/BaseSlider$SavedState;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        /* renamed from: com.ykb.ebook.weight.slider.BaseSlider$SavedState$CREATOR, reason: from kotlin metadata */
        public static final class Companion implements Parcelable.Creator<SavedState> {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NotNull
            public SavedState createFromParcel(@NotNull Parcel parcel) {
                Intrinsics.checkNotNullParameter(parcel, "parcel");
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            @NotNull
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        public SavedState(@Nullable Parcelable parcelable) {
            super(parcelable);
        }

        public final float getSecondaryValue() {
            return this.secondaryValue;
        }

        public final float getValue() {
            return this.value;
        }

        public final void setSecondaryValue(float f2) {
            this.secondaryValue = f2;
        }

        public final void setValue(float f2) {
            this.value = f2;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NotNull Parcel parcel, int flags) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            super.writeToParcel(parcel, flags);
            parcel.writeFloat(this.value);
            parcel.writeFloat(this.secondaryValue);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SavedState(@NotNull Parcel parcel) {
            super(parcel);
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            this.value = parcel.readFloat();
            this.secondaryValue = parcel.readFloat();
        }
    }

    public /* synthetic */ BaseSlider(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final void adjustCustomThumbDrawableBounds(Drawable drawable, int radius) {
        int i2 = radius * 2;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth == -1 && intrinsicHeight == -1) {
            drawable.setBounds(0, 0, i2, i2);
        } else {
            float fMax = i2 / Math.max(intrinsicWidth, intrinsicHeight);
            drawable.setBounds(0, 0, (int) (intrinsicWidth * fMax), (int) (intrinsicHeight * fMax));
        }
    }

    public static /* synthetic */ void adjustCustomThumbDrawableBounds$default(BaseSlider baseSlider, Drawable drawable, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: adjustCustomThumbDrawableBounds");
        }
        if ((i3 & 2) != 0) {
            i2 = baseSlider.thumbRadius;
        }
        baseSlider.adjustCustomThumbDrawableBounds(drawable, i2);
    }

    private final void adjustThumbDrawableBounds(int radius) {
        int i2 = radius * 2;
        this.defaultThumbDrawable.setBounds(0, 0, i2, i2);
        Drawable drawable = this.customThumbDrawable;
        if (drawable != null) {
            adjustCustomThumbDrawableBounds(drawable, radius);
        }
    }

    private final void drawCompatHaloIfNeed(Canvas canvas, int width, float yCenter) {
        if (shouldDrawCompatHalo() && this.enableDrawHalo) {
            float paddingLeft = getPaddingLeft() + this.trackInnerHPadding + this.thumbOffset + (percentValue(this.value) * (width - (this.thumbOffset * 2)));
            if (getParent() instanceof ViewGroup) {
                ViewParent parent = getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type android.view.ViewGroup");
                ((ViewGroup) parent).setClipChildren(false);
            }
            canvas.drawCircle(paddingLeft, yCenter, this.haloRadius, this.haloPaint);
        }
    }

    private final void drawDebugArea(Canvas canvas, int width, float yCenter) {
        this.trackRectF.set(getPaddingLeft() + 0.0f + this.trackInnerHPadding, yCenter - (this.trackHeight / 2.0f), (width - getPaddingRight()) - this.trackInnerHPadding, yCenter + (this.trackHeight / 2.0f));
        if (DEBUG_MODE) {
            this.debugPaint.setColor(SupportMenu.CATEGORY_MASK);
            float f2 = 1;
            float f3 = f2 + 0.0f;
            canvas.drawRect(f3, f3, canvas.getWidth() - f2, canvas.getHeight() - f2, this.debugPaint);
            this.debugPaint.setColor(-16776961);
            canvas.drawLine(0.0f, canvas.getHeight() / 2.0f, canvas.getWidth(), canvas.getHeight() / 2.0f, this.debugPaint);
            canvas.drawLine(canvas.getWidth() / 2.0f, 0.0f, canvas.getWidth() / 2.0f, canvas.getHeight(), this.debugPaint);
            this.debugPaint.setColor(-16711936);
            float f4 = this.trackRectF.left;
            canvas.drawLine(f4, 0.0f, f4, canvas.getHeight(), this.debugPaint);
            float f5 = this.trackRectF.right;
            canvas.drawLine(f5, 0.0f, f5, canvas.getHeight(), this.debugPaint);
        }
    }

    private final void drawInactiveTrack(Canvas canvas, int width, float yCenter) {
        this.trackRectF.set(getPaddingLeft() + 0.0f + this.trackInnerHPadding, yCenter - (this.trackHeight / 2.0f), (width - getPaddingRight()) - this.trackInnerHPadding, (this.trackHeight / 2.0f) + yCenter);
        if (!dispatchDrawInactiveTrackBefore(canvas, this.trackRectF, yCenter)) {
            int i2 = this.trackCornerRadius;
            float f2 = i2 == -1 ? this.trackHeight / 2.0f : i2;
            canvas.drawRoundRect(this.trackRectF, f2, f2, this.inactiveTrackPaint);
        }
        drawInactiveTrackAfter(canvas, this.trackRectF, yCenter);
    }

    private final void drawSecondaryTrack(Canvas canvas, float yCenter) {
        int paddingLeft = getPaddingLeft() + this.trackInnerHPadding;
        int i2 = this.thumbOffset;
        this.trackRectF.set(getPaddingLeft() + 0.0f + this.trackInnerHPadding, yCenter - (this.trackHeight / 2.0f), paddingLeft + (i2 * 2) + ((this.trackWidth - (i2 * 2)) * percentValue(this.secondaryValue)), (this.trackHeight / 2.0f) + yCenter);
        if (!dispatchDrawSecondaryTrackBefore(canvas, this.trackRectF, yCenter)) {
            int i3 = this.trackCornerRadius;
            float f2 = i3 == -1 ? this.trackHeight / 2.0f : i3;
            if (this.secondaryValue > this.valueFrom) {
                canvas.drawRoundRect(this.trackRectF, f2, f2, this.trackSecondaryPaint);
            }
        }
        drawSecondaryTrackAfter(canvas, this.trackRectF, yCenter);
    }

    private final void drawThumb(Canvas canvas, int width, float yCenter) {
        if (this.thumbAnimation.isThumbHidden()) {
            return;
        }
        Drawable drawable = this.customThumbDrawable;
        if (drawable == null) {
            drawable = this.defaultThumbDrawable;
        }
        float paddingLeft = getPaddingLeft() + this.trackInnerHPadding + this.thumbOffset + (percentValue(this.value) * (width - (this.thumbOffset * 2)));
        float fHeight = (yCenter - (drawable.getBounds().height() / 2.0f)) + this.thumbVOffset;
        float fWidth = paddingLeft - (drawable.getBounds().width() / 2.0f);
        if (!dispatchDrawThumbBefore(canvas, paddingLeft, yCenter)) {
            int iSave = canvas.save();
            canvas.translate(fWidth, fHeight);
            try {
                drawable.draw(canvas);
                canvas.restoreToCount(iSave);
                String str = this.thumbText;
                if (str != null) {
                    canvas.drawText(str, paddingLeft, yCenter - ((this.thumbTextPaint.getFontMetricsInt().bottom + this.thumbTextPaint.getFontMetricsInt().top) / 2), this.thumbTextPaint);
                }
            } catch (Throwable th) {
                canvas.restoreToCount(iSave);
                throw th;
            }
        }
        drawThumbAfter(canvas, paddingLeft, yCenter);
    }

    private final void drawTicks(Canvas canvas, int width, float yCenter) {
        if (enableStepMode() && this.tickVisible) {
            float f2 = (width - (this.thumbOffset * 2)) - (this.tickRadius * 2);
            int i2 = (int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1);
            float f3 = f2 / (i2 - 1);
            float fPercentValue = (percentValue(this.value) * width) + getPaddingLeft() + this.trackInnerHPadding + this.thumbOffset;
            for (int i3 = 0; i3 < i2; i3++) {
                float paddingLeft = getPaddingLeft() + this.trackInnerHPadding + this.thumbOffset;
                float f4 = this.tickRadius;
                float f5 = paddingLeft + f4 + (i3 * f3);
                canvas.drawCircle(f5, yCenter, f4, f5 <= fPercentValue ? this.ticksPaint : this.inactiveTicksPaint);
            }
        }
    }

    private final void drawTrack(Canvas canvas, float yCenter) {
        int paddingLeft = getPaddingLeft() + this.trackInnerHPadding;
        int i2 = this.thumbOffset;
        this.trackRectF.set(getPaddingLeft() + 0.0f + this.trackInnerHPadding, yCenter - (this.trackHeight / 2.0f), paddingLeft + (i2 * 2) + ((this.trackWidth - (i2 * 2)) * percentValue(this.value)), (this.trackHeight / 2.0f) + yCenter);
        if (!dispatchDrawTrackBefore(canvas, this.trackRectF, yCenter)) {
            int i3 = this.trackCornerRadius;
            float f2 = i3 == -1 ? this.trackHeight / 2.0f : i3;
            if (this.value > this.valueFrom) {
                canvas.drawRoundRect(this.trackRectF, f2, f2, this.trackPaint);
            }
        }
        drawTrackAfter(canvas, this.trackRectF, yCenter);
    }

    private final boolean enableTouch() {
        return isEnabled() && this.sliderTouchMode != 1;
    }

    private final float getTouchPosByX(float touchX) {
        return MathUtils.clamp(((touchX - getPaddingLeft()) - this.trackInnerHPadding) / this.trackWidth, 0.0f, 1.0f);
    }

    private final float getTouchValue(MotionEvent event) {
        return getValueByTouchPos(getTouchPosByX(event.getX()));
    }

    private final float getValueByTouchPos(float pos) {
        float fSnapStepPos = snapStepPos(pos);
        float f2 = this.valueTo;
        float f3 = this.valueFrom;
        return (fSnapStepPos * (f2 - f3)) + f3;
    }

    public static /* synthetic */ void hideThumb$default(BaseSlider baseSlider, boolean z2, long j2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: hideThumb");
        }
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        baseSlider.hideThumb(z2, j2);
    }

    @SuppressLint({"ObsoleteSdkInt"})
    private final void hookRippleRadius(RippleDrawable drawable, int radius) {
        drawable.setRadius(radius);
    }

    private final Drawable initializeCustomThumbDrawable(Drawable originalDrawable) {
        Drawable drawableMutate = originalDrawable.mutate();
        Intrinsics.checkNotNullExpressionValue(drawableMutate, "originalDrawable.mutate()");
        adjustCustomThumbDrawableBounds$default(this, drawableMutate, 0, 2, null);
        return drawableMutate;
    }

    private final boolean isClickTouch(MotionEvent startEvent, MotionEvent endEvent) {
        float fAbs = Math.abs(startEvent.getX() - endEvent.getX());
        float fAbs2 = Math.abs(startEvent.getY() - endEvent.getY());
        int i2 = this.scaledTouchSlop;
        return fAbs <= ((float) i2) && fAbs2 <= ((float) i2);
    }

    private final boolean isInVerticalScrollingContainer() {
        ViewParent parent = getParent();
        while (true) {
            if (!(parent instanceof ViewGroup)) {
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            if ((viewGroup.canScrollVertically(1) || viewGroup.canScrollVertically(-1)) && viewGroup.shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void lambda$12$lambda$10(BaseSlider this$0, ValueAnimator this_apply, ValueAnimator it) throws NumberFormatException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(it, "it");
        float f2 = Float.parseFloat(it.getAnimatedValue().toString());
        this$0.setValue(f2);
        this_apply.setInterpolator(new LinearOutSlowInInterpolator());
        valueChanged$default(this$0, f2, this$0.isDragging, 0.0f, 0.0f, 12, null);
        this$0.updateHaloHotspot();
        this$0.postInvalidate();
        this$0.hasDirtyData = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void lambda$9$lambda$8(BaseSlider this$0, ThumbValueAnimation this_apply, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.adjustThumbDrawableBounds((int) (this_apply.getAnimatedValueAbsolute() * this$0.thumbRadius));
        this$0.postInvalidate();
    }

    public static /* synthetic */ float percentValue$default(BaseSlider baseSlider, float f2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: percentValue");
        }
        if ((i2 & 1) != 0) {
            f2 = baseSlider.value;
        }
        return baseSlider.percentValue(f2);
    }

    private final void processAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        int[] NiftySlider = R.styleable.NiftySlider;
        Intrinsics.checkNotNullExpressionValue(NiftySlider, "NiftySlider");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, NiftySlider, defStyleAttr, R.style.Widget_NiftySlider);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)");
        setValueFrom(typedArrayObtainStyledAttributes.getFloat(R.styleable.NiftySlider_android_valueFrom, 0.0f));
        setValueTo(typedArrayObtainStyledAttributes.getFloat(R.styleable.NiftySlider_android_valueTo, 1.0f));
        setValue(typedArrayObtainStyledAttributes.getFloat(R.styleable.NiftySlider_android_value, 0.0f));
        setStepSize(typedArrayObtainStyledAttributes.getFloat(R.styleable.NiftySlider_android_stepSize, 0.0f));
        this.tickVisible = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_ticksVisible, false);
        this.enableHapticFeedback = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_android_hapticFeedbackEnabled, false);
        this.sourceViewHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.NiftySlider_android_layout_height, 0);
        setTrackHeight(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_trackHeight, 0));
        this.enableProgressAnim = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_enableProgressAnim, false);
        this.isConsecutiveProgress = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_isConsecutiveProgress, false);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.NiftySlider_trackColor);
        if (colorStateList == null) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.default_track_color);
        }
        Intrinsics.checkNotNullExpressionValue(colorStateList, "getColorStateList(R.styl…lor\n                    )");
        setTrackTintList(colorStateList);
        ColorStateList colorStateList2 = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.NiftySlider_trackSecondaryColor);
        if (colorStateList2 == null) {
            colorStateList2 = AppCompatResources.getColorStateList(context, R.color.default_track_color);
        }
        Intrinsics.checkNotNullExpressionValue(colorStateList2, "getColorStateList(R.styl…lor\n                    )");
        setTrackSecondaryTintList(colorStateList2);
        ColorStateList colorStateList3 = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.NiftySlider_trackColorInactive);
        if (colorStateList3 == null) {
            colorStateList3 = AppCompatResources.getColorStateList(context, R.color.default_track_inactive_color);
        }
        Intrinsics.checkNotNullExpressionValue(colorStateList3, "getColorStateList(R.styl…lor\n                    )");
        setTrackInactiveTintList(colorStateList3);
        ColorStateList colorStateList4 = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.NiftySlider_ticksColor);
        if (colorStateList4 == null) {
            colorStateList4 = AppCompatResources.getColorStateList(context, R.color.default_ticks_color);
        }
        Intrinsics.checkNotNullExpressionValue(colorStateList4, "getColorStateList(R.styl…lor\n                    )");
        setTicksTintList(colorStateList4);
        ColorStateList colorStateList5 = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.NiftySlider_ticksColorInactive);
        if (colorStateList5 == null) {
            colorStateList5 = AppCompatResources.getColorStateList(context, R.color.default_ticks_inactive_color);
        }
        Intrinsics.checkNotNullExpressionValue(colorStateList5, "getColorStateList(R.styl…lor\n                    )");
        setTicksInactiveTintList(colorStateList5);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_thumbWidth, -1);
        int dimensionPixelOffset2 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_thumbHeight, -1);
        setThumbTintList(TypedArrayKt.getColorStateListOrThrow(typedArrayObtainStyledAttributes, R.styleable.NiftySlider_thumbColor));
        setThumbRadius(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_thumbRadius, 0));
        setThumbWidthAndHeight$default(this, dimensionPixelOffset, dimensionPixelOffset2, 0, 4, null);
        setThumbVOffset(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_thumbVOffset, 0));
        setThumbWithinTrackBounds(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_thumbWithinTrackBounds, false));
        setThumbElevation(typedArrayObtainStyledAttributes.getDimension(R.styleable.NiftySlider_thumbElevation, 0.0f));
        setThumbShadowColor(typedArrayObtainStyledAttributes.getColor(R.styleable.NiftySlider_thumbShadowColor, -7829368));
        setThumbStrokeColor(typedArrayObtainStyledAttributes.getColorStateList(R.styleable.NiftySlider_thumbStrokeColor));
        setThumbStrokeWidth(typedArrayObtainStyledAttributes.getDimension(R.styleable.NiftySlider_thumbStrokeWidth, 0.0f));
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.NiftySlider_thumbText);
        if (string == null) {
            string = "";
        }
        setThumbText(string);
        ColorStateList colorStateList6 = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.NiftySlider_thumbTextColor);
        if (colorStateList6 == null) {
            colorStateList6 = ColorStateList.valueOf(-1);
        }
        setThumbTextTintList(colorStateList6);
        setThumbTextSize(typedArrayObtainStyledAttributes.getDimension(R.styleable.NiftySlider_thumbTextSize, 10.0f));
        setThumbTextBold(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_thumbTextBold, false));
        setEnableAutoHPadding(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_enableAutoHPadding, true));
        setTrackInnerHPadding(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_trackInnerHPadding, -1));
        setTrackInnerVPadding(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_trackInnerVPadding, -1));
        setTrackCornersRadius(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_trackCornersRadius, -1));
        setEnableDrawHalo(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_enableDrawHalo, true));
        setHaloTintList(TypedArrayKt.getColorStateListOrThrow(typedArrayObtainStyledAttributes, R.styleable.NiftySlider_haloColor));
        setHaloRadius(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_haloRadius, 0));
        setTickRadius(typedArrayObtainStyledAttributes.getDimension(R.styleable.NiftySlider_tickRadius, 0.0f));
        setTipViewVisibility(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_tipViewVisible, false));
        setTipVerticalOffset(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.NiftySlider_tipViewVerticalOffset, 0));
        setTipBackground(typedArrayObtainStyledAttributes.getColor(R.styleable.NiftySlider_tipViewBackground, -1));
        setTipTextColor(typedArrayObtainStyledAttributes.getColor(R.styleable.NiftySlider_tipViewTextColor, -16777216));
        setTipTextAutoChange(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_tipTextAutoChange, true));
        setTipViewClippingEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.NiftySlider_isTipViewClippingEnabled, false));
        setTouchMode(typedArrayObtainStyledAttributes.getInt(R.styleable.NiftySlider_sliderTouchMode, 0));
        typedArrayObtainStyledAttributes.recycle();
    }

    public static /* synthetic */ void processAttributes$default(BaseSlider baseSlider, Context context, AttributeSet attributeSet, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: processAttributes");
        }
        if ((i3 & 2) != 0) {
            attributeSet = null;
        }
        baseSlider.processAttributes(context, attributeSet, i2);
    }

    public static /* synthetic */ void setThumbWidthAndHeight$default(BaseSlider baseSlider, int i2, int i3, int i4, int i5, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setThumbWidthAndHeight");
        }
        if ((i5 & 4) != 0) {
            i4 = baseSlider.thumbRadius;
        }
        baseSlider.setThumbWidthAndHeight(i2, i3, i4);
    }

    public static /* synthetic */ void setTrackInnerHPadding$default(BaseSlider baseSlider, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setTrackInnerHPadding");
        }
        if ((i3 & 1) != 0) {
            i2 = -1;
        }
        baseSlider.setTrackInnerHPadding(i2);
    }

    public static /* synthetic */ void setValue$default(BaseSlider baseSlider, float f2, boolean z2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setValue");
        }
        if ((i2 & 2) != 0) {
            z2 = baseSlider.enableProgressAnim;
        }
        baseSlider.setValue(f2, z2);
    }

    public static /* synthetic */ void showThumb$default(BaseSlider baseSlider, boolean z2, long j2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: showThumb");
        }
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        baseSlider.showThumb(z2, j2);
    }

    private final float snapStepPos(float pos) {
        if (!enableStepMode()) {
            return pos;
        }
        return MathKt__MathJVMKt.roundToInt(pos * r0) / ((int) ((this.valueTo - this.valueFrom) / this.stepSize));
    }

    private final void startTacking(MotionEvent event) {
        this.isTackingStart = true;
        onStartTacking();
        this.tipView.show();
    }

    private final void stopTacking(MotionEvent event) {
        if (this.isTackingStart) {
            onStopTacking();
        }
        this.isTackingStart = false;
        this.tipView.hide();
        invalidate();
    }

    public static /* synthetic */ void toggleThumbVisibility$default(BaseSlider baseSlider, boolean z2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toggleThumbVisibility");
        }
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        baseSlider.toggleThumbVisibility(z2);
    }

    private final void trackTouchEvent(MotionEvent event) {
        float touchValue = this.isConsecutiveProgress ? getTouchValue(event) - this.touchDownDiffValue : getTouchValue(event);
        if (this.value == touchValue) {
            return;
        }
        updateValue(touchValue, event.getAction() != 2 && this.enableProgressAnim);
    }

    private final void updateHaloHotspot() {
        if (!this.enableDrawHalo || shouldDrawCompatHalo() || getMeasuredWidth() <= 0 || !(getBackground() instanceof RippleDrawable)) {
            return;
        }
        int paddingLeft = getPaddingLeft() + this.trackInnerHPadding + this.thumbOffset + ((int) (percentValue(this.value) * (this.trackWidth - (this.thumbOffset * 2))));
        int i2 = this.viewHeight / 2;
        Drawable background = getBackground();
        int i3 = this.haloRadius;
        DrawableCompat.setHotspotBounds(background, paddingLeft - i3, i2 - i3, paddingLeft + i3, i2 + i3);
    }

    private final void updateValue(float value, boolean animated) {
        this.hasDirtyData = true;
        float f2 = this.value;
        if (!animated) {
            setValue(value);
            valueChanged$default(this, value, this.isDragging, 0.0f, 0.0f, 12, null);
            updateHaloHotspot();
            postInvalidate();
            return;
        }
        float fAbs = Math.abs(value - f2) / (this.valueTo - this.valueFrom);
        Number numberValueOf = ((double) fAbs) < 0.35d ? Float.valueOf(Math.max(fAbs * 500.0f, 0.0f)) : 300;
        ValueAnimator valueAnimator = this.progressAnimator;
        valueAnimator.cancel();
        valueAnimator.setDuration(numberValueOf.longValue());
        valueAnimator.setFloatValues(f2, value);
        valueAnimator.start();
    }

    public static /* synthetic */ void updateValue$default(BaseSlider baseSlider, float f2, boolean z2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateValue");
        }
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        baseSlider.updateValue(f2, z2);
    }

    private final void validateDirtyData() {
        if (this.hasDirtyData) {
            validateValueFrom();
            validateValueTo();
            validateValue();
            updateDirtyData();
            this.hasDirtyData = false;
        }
    }

    private final void validateValue() {
        setValue(MathUtils.clamp(this.value, this.valueFrom, this.valueTo));
        this.secondaryValue = MathUtils.clamp(this.secondaryValue, this.valueFrom, this.valueTo);
    }

    private final void validateValueFrom() {
        if (this.valueFrom <= this.valueTo) {
            return;
        }
        throw new IllegalStateException("valueFrom(" + this.valueFrom + ") must be smaller than valueTo(" + this.valueTo + ')');
    }

    private final void validateValueTo() {
        if (this.valueTo > this.valueFrom) {
            return;
        }
        throw new IllegalStateException("valueTo(" + this.valueTo + ") must be greater than valueFrom(" + this.valueFrom + ')');
    }

    private final void valueChanged(float value, boolean fromUser, float touchX, float touchRawX) {
        onValueChanged(value, fromUser);
        this.tipView.onLocationChanged(getThumbCenterX(), getThumbCenterY(), value);
    }

    public static /* synthetic */ void valueChanged$default(BaseSlider baseSlider, float f2, boolean z2, float f3, float f4, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: valueChanged");
        }
        if ((i2 & 4) != 0) {
            f3 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f4 = 0.0f;
        }
        baseSlider.valueChanged(f2, z2, f3, f4);
    }

    public final void addCustomTipView(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.tipView.setCustomTipView(view);
    }

    public final void createTipAnimation(@NotNull TipViewAnimator animator) {
        Intrinsics.checkNotNullParameter(animator, "animator");
        this.tipView.setAnimator(animator);
    }

    public abstract boolean dispatchDrawInactiveTrackBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    public abstract boolean dispatchDrawSecondaryTrackBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    public abstract boolean dispatchDrawThumbBefore(@NotNull Canvas canvas, float cx, float cy);

    public abstract boolean dispatchDrawTrackBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    public abstract void drawInactiveTrackAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    public abstract void drawSecondaryTrackAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    public abstract void drawThumbAfter(@NotNull Canvas canvas, float cx, float cy);

    public abstract void drawTrackAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    @Override // android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Paint paint = this.trackPaint;
        ColorStateList colorStateList = this.trackColor;
        ColorStateList colorStateList2 = null;
        if (colorStateList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("trackColor");
            colorStateList = null;
        }
        paint.setColor(getColorForState(colorStateList));
        Paint paint2 = this.trackSecondaryPaint;
        ColorStateList colorStateList3 = this.trackSecondaryColor;
        if (colorStateList3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("trackSecondaryColor");
            colorStateList3 = null;
        }
        paint2.setColor(getColorForState(colorStateList3));
        Paint paint3 = this.ticksPaint;
        ColorStateList colorStateList4 = this.ticksColor;
        if (colorStateList4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ticksColor");
            colorStateList4 = null;
        }
        paint3.setColor(getColorForState(colorStateList4));
        Paint paint4 = this.inactiveTicksPaint;
        ColorStateList colorStateList5 = this.ticksColorInactive;
        if (colorStateList5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ticksColorInactive");
            colorStateList5 = null;
        }
        paint4.setColor(getColorForState(colorStateList5));
        Paint paint5 = this.inactiveTrackPaint;
        ColorStateList colorStateList6 = this.trackColorInactive;
        if (colorStateList6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("trackColorInactive");
            colorStateList6 = null;
        }
        paint5.setColor(getColorForState(colorStateList6));
        if (this.defaultThumbDrawable.isStateful()) {
            this.defaultThumbDrawable.setState(getDrawableState());
        }
        Paint paint6 = this.thumbTextPaint;
        ColorStateList colorStateList7 = this.thumbTextColor;
        if (colorStateList7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("thumbTextColor");
            colorStateList7 = null;
        }
        paint6.setColor(getColorForState(colorStateList7));
        Paint paint7 = this.haloPaint;
        ColorStateList colorStateList8 = this.haloColor;
        if (colorStateList8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("haloColor");
        } else {
            colorStateList2 = colorStateList8;
        }
        paint7.setColor(getColorForState(colorStateList2));
        this.haloPaint.setAlpha(63);
    }

    public final boolean enableStepMode() {
        return this.stepSize > 0.0f;
    }

    @ColorInt
    public final int getColorForState(@NotNull ColorStateList colorStateList) {
        Intrinsics.checkNotNullParameter(colorStateList, "colorStateList");
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    public final boolean getEnableHapticFeedback() {
        return this.enableHapticFeedback;
    }

    public final boolean getEnableProgressAnim() {
        return this.enableProgressAnim;
    }

    public final float getSecondaryValue() {
        return this.secondaryValue;
    }

    public final float getStepSize() {
        return this.stepSize;
    }

    public final float getThumbCenterX() {
        return getPaddingLeft() + this.trackInnerHPadding + this.thumbOffset + (percentValue(this.value) * (this.trackWidth - (this.thumbOffset * 2)));
    }

    public final float getThumbCenterY() {
        return (getMeasuredHeight() / 2.0f) + this.thumbVOffset;
    }

    public final int getThumbRadius() {
        return this.thumbRadius;
    }

    public final boolean getTickVisible() {
        return this.tickVisible;
    }

    public final int getTrackHeight() {
        return this.trackHeight;
    }

    public final int getTrackWidth() {
        return this.trackWidth;
    }

    public final float getValue() {
        return this.value;
    }

    public final float getValueFrom() {
        return this.valueFrom;
    }

    public final float getValueTo() {
        return this.valueTo;
    }

    public final void hideThumb(boolean animated, long delayMillis) {
        this.thumbAnimation.hide(animated, delayMillis);
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(@NotNull Drawable drawable) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        super.invalidateDrawable(drawable);
        invalidate();
    }

    /* renamed from: isConsecutiveProgress, reason: from getter */
    public final boolean getIsConsecutiveProgress() {
        return this.isConsecutiveProgress;
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        if (this.isShowTipView) {
            this.tipView.attachTipView(this);
        }
        super.onAttachedToWindow();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        this.tipView.detachTipView(this);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.hasDirtyData) {
            validateDirtyData();
        }
        float measuredHeight = getMeasuredHeight() / 2.0f;
        int measuredWidth = getMeasuredWidth();
        this.viewRectF.set(getPaddingLeft() + 0.0f + this.trackInnerHPadding, measuredHeight - (this.trackHeight / 2.0f), (measuredWidth - getPaddingRight()) - this.trackInnerHPadding, (this.trackHeight / 2.0f) + measuredHeight);
        onDrawBefore(canvas, this.viewRectF, measuredHeight);
        drawDebugArea(canvas, measuredWidth, measuredHeight);
        drawInactiveTrack(canvas, measuredWidth, measuredHeight);
        drawSecondaryTrack(canvas, measuredHeight);
        drawTrack(canvas, measuredHeight);
        drawTicks(canvas, this.trackWidth, measuredHeight);
        if ((this.isDragging || isFocused()) && isEnabled()) {
            drawCompatHaloIfNeed(canvas, this.trackWidth, measuredHeight);
        }
        drawThumb(canvas, this.trackWidth, measuredHeight);
        onDrawAfter(canvas, this.viewRectF, measuredHeight);
    }

    public abstract void onDrawAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    public abstract void onDrawBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter);

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(this.viewHeight, 1073741824));
    }

    public void onProgressAnimEnd() {
    }

    @Override // android.view.View
    public void onRestoreInstanceState(@Nullable Parcelable state) {
        Intrinsics.checkNotNull(state, "null cannot be cast to non-null type com.ykb.ebook.weight.slider.BaseSlider.SavedState");
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValue(savedState.getValue());
        this.secondaryValue = savedState.getSecondaryValue();
    }

    @Override // android.view.View
    @Nullable
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.setValue(this.value);
        savedState.setSecondaryValue(this.secondaryValue);
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        updateTrackWidth(w2);
        updateHaloHotspot();
    }

    public abstract void onStartTacking();

    public abstract void onStopTacking();

    /* JADX WARN: Removed duplicated region for block: B:38:0x007c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull android.view.MotionEvent r7) {
        /*
            r6 = this;
            java.lang.String r0 = "event"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            boolean r0 = r6.enableTouch()
            r1 = 0
            if (r0 != 0) goto L16
            boolean r0 = r6.isDragging
            if (r0 == 0) goto L15
            r6.isDragging = r1
            r6.stopTacking(r7)
        L15:
            return r1
        L16:
            float r0 = r7.getX()
            int r2 = r6.sliderTouchMode
            r3 = 2
            r4 = 1
            if (r2 != r3) goto L22
            r2 = r4
            goto L23
        L22:
            r2 = r1
        L23:
            int r5 = r7.getAction()
            if (r5 == 0) goto L9e
            if (r5 == r4) goto L7c
            if (r5 == r3) goto L32
            r0 = 3
            if (r5 == r0) goto L7c
            goto Lc3
        L32:
            float r3 = r6.touchDownX
            float r3 = r0 - r3
            float r3 = java.lang.Math.abs(r3)
            int r5 = r6.scaledTouchSlop
            float r5 = (float) r5
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 >= 0) goto L43
            r3 = r4
            goto L44
        L43:
            r3 = r1
        L44:
            if (r3 == 0) goto L4c
            if (r2 == 0) goto L4c
            boolean r2 = r6.isDragging
            if (r2 == 0) goto Lc3
        L4c:
            boolean r2 = r6.isDragging
            if (r2 != 0) goto L63
            boolean r2 = r6.isInVerticalScrollingContainer()
            if (r2 == 0) goto L59
            if (r3 == 0) goto L59
            return r1
        L59:
            android.view.ViewParent r1 = r6.getParent()
            r1.requestDisallowInterceptTouchEvent(r4)
            r6.startTacking(r7)
        L63:
            float r1 = r6.touchDownX
            float r0 = r0 - r1
            float r0 = java.lang.Math.abs(r0)
            int r1 = r6.scaledTouchSlop
            float r1 = (float) r1
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L76
            android.animation.ValueAnimator r0 = r6.progressAnimator
            r0.cancel()
        L76:
            r6.isDragging = r4
            r6.trackTouchEvent(r7)
            goto Lc3
        L7c:
            r6.isDragging = r1
            android.view.MotionEvent r0 = r6.lastTouchEvent
            if (r0 == 0) goto L98
            int r3 = r0.getAction()
            if (r3 != 0) goto L98
            boolean r0 = r6.isClickTouch(r0, r7)
            if (r0 == 0) goto L98
            if (r2 == 0) goto L92
            r1 = r4
            goto L98
        L92:
            r6.startTacking(r7)
            r6.trackTouchEvent(r7)
        L98:
            if (r1 != 0) goto Lc3
            r6.stopTacking(r7)
            goto Lc3
        L9e:
            r6.touchDownX = r0
            float r0 = r6.getTouchValue(r7)
            float r1 = r6.value
            float r0 = r0 - r1
            r6.touchDownDiffValue = r0
            boolean r0 = r6.isInVerticalScrollingContainer()
            if (r0 != 0) goto Lc3
            android.view.ViewParent r0 = r6.getParent()
            r0.requestDisallowInterceptTouchEvent(r4)
            r6.requestFocus()
            if (r2 != 0) goto Lc3
            r6.isDragging = r4
            r6.startTacking(r7)
            r6.trackTouchEvent(r7)
        Lc3:
            boolean r0 = r6.isDragging
            r6.setPressed(r0)
            android.view.MotionEvent r7 = android.view.MotionEvent.obtain(r7)
            r6.lastTouchEvent = r7
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.weight.slider.BaseSlider.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public abstract void onValueChanged(float value, boolean fromUser);

    public final float percentValue(float v2) {
        float f2 = this.valueFrom;
        return (v2 - f2) / (this.valueTo - f2);
    }

    public final void setConsecutiveProgress(boolean z2) {
        this.isConsecutiveProgress = z2;
    }

    public final void setEnableAutoHPadding(boolean enable) {
        this.enableAutoHPadding = enable;
    }

    public final void setEnableDrawHalo(boolean enable) {
        this.enableDrawHalo = enable;
        if (this.haloDrawable == null && enable) {
            setBackground(ContextCompat.getDrawable(getContext(), R.drawable.halo_background));
            Drawable background = getBackground();
            Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.RippleDrawable");
            this.haloDrawable = (RippleDrawable) background;
        }
    }

    public final void setEnableHapticFeedback(boolean z2) {
        this.enableHapticFeedback = z2;
    }

    public final void setEnableProgressAnim(boolean z2) {
        this.enableProgressAnim = z2;
    }

    public final void setHaloRadius(@IntRange(from = 0) @Dimension int radius) {
        if (this.haloRadius == radius) {
            return;
        }
        this.haloRadius = radius;
        if (shouldDrawCompatHalo() || !this.enableDrawHalo || !(getBackground() instanceof RippleDrawable)) {
            postInvalidate();
            return;
        }
        Drawable background = getBackground();
        Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.RippleDrawable");
        hookRippleRadius((RippleDrawable) background, this.haloRadius);
    }

    public final void setHaloTintList(@NotNull ColorStateList haloColor) {
        Intrinsics.checkNotNullParameter(haloColor, "haloColor");
        ColorStateList colorStateList = this.haloColor;
        if (colorStateList != null) {
            if (colorStateList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("haloColor");
                colorStateList = null;
            }
            if (Intrinsics.areEqual(colorStateList, haloColor)) {
                return;
            }
        }
        this.haloColor = haloColor;
        if (!shouldDrawCompatHalo() && (getBackground() instanceof RippleDrawable)) {
            Drawable background = getBackground();
            Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.RippleDrawable");
            ((RippleDrawable) background).setColor(haloColor);
        } else {
            Paint paint = this.haloPaint;
            paint.setColor(getColorForState(haloColor));
            paint.setAlpha(63);
            invalidate();
        }
    }

    public final void setSecondaryValue(float secondaryValue) {
        if (this.secondaryValue == secondaryValue) {
            return;
        }
        this.secondaryValue = secondaryValue;
        this.hasDirtyData = true;
        postInvalidate();
    }

    public final void setStepSize(float f2) {
        if ((this.stepSize == f2) || f2 <= 0.0f) {
            return;
        }
        this.stepSize = f2;
        this.hasDirtyData = true;
        postInvalidate();
    }

    public final void setThumbCustomDrawable(@DrawableRes int drawableResId) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableResId);
        if (drawable != null) {
            setThumbCustomDrawable(drawable);
        }
    }

    public final void setThumbElevation(float elevation) {
        if (elevation > 0.0f) {
            setLayerType(1, null);
        }
        this.defaultThumbDrawable.setElevation(elevation);
        this.thumbElevation = elevation;
        postInvalidate();
    }

    public final void setThumbRadius(@IntRange(from = 0) @Dimension int i2) {
        if (this.thumbRadius == i2) {
            return;
        }
        this.thumbRadius = i2;
        this.defaultThumbDrawable.setCornerSize(i2);
        adjustThumbDrawableBounds(i2);
        updateViewLayout();
    }

    public final void setThumbShadowColor(@ColorInt int shadowColor) {
        this.defaultThumbDrawable.setShadowColor(shadowColor);
    }

    public final void setThumbStrokeColor(@Nullable ColorStateList thumbStrokeColor) {
        this.defaultThumbDrawable.setStrokeColor(thumbStrokeColor);
        postInvalidate();
    }

    public final void setThumbStrokeWidth(float thumbStrokeWidth) {
        this.defaultThumbDrawable.setStrokeWidth(thumbStrokeWidth);
        postInvalidate();
    }

    public final void setThumbText(@Nullable String text) {
        if (Intrinsics.areEqual(this.thumbText, text)) {
            return;
        }
        this.thumbText = text;
        postInvalidate();
    }

    public final void setThumbTextBold(boolean isBold) {
        if (this.thumbTextPaint.isFakeBoldText() != isBold) {
            this.thumbTextPaint.setFakeBoldText(isBold);
            invalidate();
        }
    }

    public final void setThumbTextSize(float size) {
        if (this.thumbTextPaint.getTextSize() == size) {
            return;
        }
        this.thumbTextPaint.setTextSize(size);
        invalidate();
    }

    public final void setThumbTextTintList(@Nullable ColorStateList color) {
        if (color != null) {
            ColorStateList colorStateList = this.thumbTextColor;
            if (colorStateList != null) {
                if (colorStateList == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("thumbTextColor");
                    colorStateList = null;
                }
                if (Intrinsics.areEqual(colorStateList, color)) {
                    return;
                }
            }
            this.thumbTextColor = color;
            this.thumbTextPaint.setColor(getColorForState(color));
            invalidate();
        }
    }

    public final void setThumbTintList(@NotNull ColorStateList thumbColor) {
        Intrinsics.checkNotNullParameter(thumbColor, "thumbColor");
        if (Intrinsics.areEqual(thumbColor, this.defaultThumbDrawable.getFillColor())) {
            return;
        }
        this.defaultThumbDrawable.setFillColor(thumbColor);
        invalidate();
    }

    public final void setThumbVOffset(int offset) {
        if (offset == this.thumbVOffset) {
            return;
        }
        this.thumbVOffset = offset;
        postInvalidate();
    }

    public final void setThumbWidthAndHeight(int thumbWidth, int thumbHeight, int radius) {
        if (this.thumbWidth == thumbWidth && this.thumbHeight == thumbHeight) {
            return;
        }
        if (thumbHeight >= 0 || thumbWidth > 0) {
            if (thumbWidth >= 0) {
                this.thumbWidth = thumbWidth;
            } else {
                this.thumbWidth = this.thumbRadius * 2;
            }
            if (thumbHeight >= 0) {
                this.thumbHeight = thumbHeight;
            } else {
                this.thumbHeight = this.thumbRadius * 2;
            }
            if (radius != this.thumbRadius) {
                this.defaultThumbDrawable.setCornerSize(radius);
            }
            this.defaultThumbDrawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
            updateViewLayout();
        }
    }

    public final void setThumbWithinTrackBounds(boolean isInBounds) {
        this.isThumbWithinTrackBounds = isInBounds;
        int i2 = isInBounds ? this.thumbRadius : 0;
        if (this.thumbOffset == i2) {
            return;
        }
        this.thumbOffset = i2;
        setTrackInnerHPadding$default(this, 0, 1, null);
        updateViewLayout();
    }

    public final void setTickRadius(@Dimension @FloatRange(from = 0.0d) float tickRadius) {
        if (this.tickRadius == tickRadius) {
            return;
        }
        this.tickRadius = tickRadius;
        postInvalidate();
    }

    public final void setTickVisible(boolean z2) {
        this.tickVisible = z2;
    }

    public final void setTicksInactiveTintList(@NotNull ColorStateList color) {
        Intrinsics.checkNotNullParameter(color, "color");
        ColorStateList colorStateList = this.ticksColorInactive;
        if (colorStateList != null) {
            if (colorStateList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ticksColorInactive");
                colorStateList = null;
            }
            if (Intrinsics.areEqual(color, colorStateList)) {
                return;
            }
        }
        this.ticksColorInactive = color;
        Paint paint = this.inactiveTicksPaint;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ticksColorInactive");
            color = null;
        }
        paint.setColor(getColorForState(color));
        invalidate();
    }

    public final void setTicksTintList(@NotNull ColorStateList color) {
        Intrinsics.checkNotNullParameter(color, "color");
        ColorStateList colorStateList = this.ticksColor;
        if (colorStateList != null) {
            if (colorStateList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ticksColor");
                colorStateList = null;
            }
            if (Intrinsics.areEqual(color, colorStateList)) {
                return;
            }
        }
        this.ticksColor = color;
        Paint paint = this.ticksPaint;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ticksColor");
            color = null;
        }
        paint.setColor(getColorForState(color));
        invalidate();
    }

    public final void setTipBackground(@ColorInt int color) {
        this.tipView.setTipBackground(color);
    }

    public final void setTipTextAutoChange(boolean isAutoChange) {
        this.tipView.setTipTextAutoChange(isAutoChange);
    }

    public final void setTipTextColor(@ColorInt int color) {
        this.tipView.setTipTextColor(color);
    }

    public final void setTipVerticalOffset(int offset) {
        if (offset != 0) {
            this.tipView.setVerticalOffset(offset);
        }
    }

    public final void setTipViewClippingEnabled(boolean enable) {
        this.tipView.setClippingEnabled(enable);
    }

    public final void setTipViewVisibility(boolean visibility) {
        if (this.isShowTipView == visibility) {
            return;
        }
        this.isShowTipView = visibility;
        if (visibility) {
            this.tipView.attachTipView(this);
        }
    }

    public final void setTouchMode(int mode) {
        this.sliderTouchMode = mode;
    }

    public final void setTrackCornersRadius(@IntRange(from = 0) @Dimension int radius) {
        if (radius == this.trackCornerRadius) {
            return;
        }
        this.trackCornerRadius = radius;
        postInvalidate();
    }

    public final void setTrackHeight(@IntRange(from = 0) int i2) {
        if (i2 != this.trackHeight) {
            this.trackHeight = i2;
            updateViewLayout();
        }
    }

    public final void setTrackInactiveTintList(@NotNull ColorStateList color) {
        Intrinsics.checkNotNullParameter(color, "color");
        ColorStateList colorStateList = this.trackColorInactive;
        if (colorStateList != null) {
            if (colorStateList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("trackColorInactive");
                colorStateList = null;
            }
            if (Intrinsics.areEqual(color, colorStateList)) {
                return;
            }
        }
        this.trackColorInactive = color;
        Paint paint = this.inactiveTrackPaint;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("trackColorInactive");
            color = null;
        }
        paint.setColor(getColorForState(color));
        invalidate();
    }

    public final void setTrackInnerHPadding(int padding) {
        if (padding == -1) {
            padding = this.enableAutoHPadding ? this.isThumbWithinTrackBounds ? (int) Math.ceil(this.thumbElevation) : this.thumbRadius + ((int) Math.ceil(this.thumbElevation)) : 0;
        }
        if (padding == this.trackInnerHPadding) {
            return;
        }
        this.trackInnerHPadding = padding;
        updateViewLayout();
    }

    public final void setTrackInnerVPadding(int padding) {
        if (padding == -1) {
            padding = (int) Math.ceil(this.thumbElevation);
        }
        if (padding == this.trackInnerVPadding) {
            return;
        }
        this.trackInnerVPadding = padding;
        updateViewLayout();
    }

    public final void setTrackSecondaryTintList(@NotNull ColorStateList color) {
        Intrinsics.checkNotNullParameter(color, "color");
        ColorStateList colorStateList = this.trackSecondaryColor;
        if (colorStateList != null) {
            if (colorStateList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("trackSecondaryColor");
                colorStateList = null;
            }
            if (Intrinsics.areEqual(color, colorStateList)) {
                return;
            }
        }
        this.trackSecondaryColor = color;
        Paint paint = this.trackSecondaryPaint;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("trackSecondaryColor");
            color = null;
        }
        paint.setColor(getColorForState(color));
        invalidate();
    }

    public final void setTrackTintList(@NotNull ColorStateList color) {
        Intrinsics.checkNotNullParameter(color, "color");
        ColorStateList colorStateList = this.trackColor;
        if (colorStateList != null) {
            if (colorStateList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("trackColor");
                colorStateList = null;
            }
            if (Intrinsics.areEqual(color, colorStateList)) {
                return;
            }
        }
        this.trackColor = color;
        Paint paint = this.trackPaint;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("trackColor");
            color = null;
        }
        paint.setColor(getColorForState(color));
        invalidate();
    }

    public final void setTrackWidth(int i2) {
        this.trackWidth = i2;
    }

    public final void setValue(float f2) {
        if (this.value == f2) {
            return;
        }
        this.value = f2;
        this.hasDirtyData = true;
        postInvalidate();
    }

    public final void setValueFrom(float f2) {
        if (this.valueFrom == f2) {
            return;
        }
        this.valueFrom = f2;
        this.hasDirtyData = true;
        postInvalidate();
    }

    public final void setValueTo(float f2) {
        if (this.valueTo == f2) {
            return;
        }
        this.valueTo = f2;
        this.hasDirtyData = true;
        postInvalidate();
    }

    public final boolean shouldDrawCompatHalo() {
        return !(getBackground() instanceof RippleDrawable);
    }

    public final void showThumb(boolean animated, long delayMillis) {
        this.thumbAnimation.show(animated, delayMillis);
    }

    public final void toggleThumbVisibility(boolean animated) {
        this.thumbAnimation.toggle(animated);
    }

    public abstract void updateDirtyData();

    public final void updateTrackWidth(int viewWidth) {
        this.trackWidth = Math.max(((viewWidth - getPaddingLeft()) - getPaddingRight()) - (this.trackInnerHPadding * 2), 0);
    }

    public final void updateViewLayout() {
        updateTrackWidth(getWidth());
        if (viewHeightChanged()) {
            requestLayout();
        } else {
            invalidate();
        }
    }

    public final boolean viewHeightChanged() {
        Rect bounds;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int i2 = this.trackHeight + paddingTop;
        Drawable drawable = this.customThumbDrawable;
        if (drawable == null || (bounds = drawable.getBounds()) == null) {
            bounds = this.defaultThumbDrawable.getBounds();
        }
        int iMax = Math.max(i2, paddingTop + bounds.height() + (this.trackInnerVPadding * 2));
        if (iMax == this.viewHeight) {
            return false;
        }
        this.viewHeight = Math.max(iMax, this.sourceViewHeight);
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseSlider(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Paint paint = new Paint(5);
        paint.setStyle(Paint.Style.FILL);
        this.trackPaint = paint;
        Paint paint2 = new Paint(5);
        paint2.setStyle(Paint.Style.FILL);
        this.trackSecondaryPaint = paint2;
        Paint paint3 = new Paint(5);
        paint3.setStyle(Paint.Style.FILL);
        this.ticksPaint = paint3;
        Paint paint4 = new Paint(5);
        paint4.setStyle(Paint.Style.FILL);
        this.inactiveTicksPaint = paint4;
        Paint paint5 = new Paint(5);
        paint5.setStyle(Paint.Style.FILL);
        this.inactiveTrackPaint = paint5;
        Paint paint6 = new Paint(5);
        paint6.setStyle(Paint.Style.FILL);
        paint6.setTextAlign(Paint.Align.CENTER);
        this.thumbTextPaint = paint6;
        Paint paint7 = new Paint(5);
        paint7.setStyle(Paint.Style.FILL);
        this.haloPaint = paint7;
        Paint paint8 = new Paint();
        paint8.setStyle(Paint.Style.STROKE);
        paint8.setStrokeWidth(1.0f);
        this.debugPaint = paint8;
        this.defaultThumbDrawable = new DefaultThumbDrawable();
        this.thumbWidth = -1;
        this.thumbHeight = -1;
        final ThumbValueAnimation thumbValueAnimation = new ThumbValueAnimation();
        this.thumbAnimation = thumbValueAnimation;
        this.enableDrawHalo = true;
        this.trackRectF = new RectF();
        this.viewRectF = new RectF();
        this.enableAutoHPadding = true;
        this.trackCornerRadius = -1;
        this.tipView = new TipViewContainer(context, null, 0, 6, null);
        this.progressAnimator = new ValueAnimator();
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        processAttributes(context, attributeSet, i2);
        thumbValueAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ykb.ebook.weight.slider.a
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BaseSlider.lambda$9$lambda$8(this.f26523c, thumbValueAnimation, valueAnimator);
            }
        });
        final ValueAnimator valueAnimator = this.progressAnimator;
        valueAnimator.setDuration(300L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ykb.ebook.weight.slider.b
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) throws NumberFormatException {
                BaseSlider.lambda$12$lambda$10(this.f26525c, valueAnimator, valueAnimator2);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() { // from class: com.ykb.ebook.weight.slider.BaseSlider$_init_$lambda$12$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
                this.this$0.onProgressAnimEnd();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(@NotNull Animator animator) {
                Intrinsics.checkNotNullParameter(animator, "animator");
            }
        });
    }

    public final void setThumbCustomDrawable(@NotNull Drawable drawable) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        this.customThumbDrawable = initializeCustomThumbDrawable(drawable);
        postInvalidate();
    }

    public final void setValue(float value, boolean animated) {
        if ((this.value == value) || this.isDragging) {
            return;
        }
        updateValue(value, animated);
    }
}
