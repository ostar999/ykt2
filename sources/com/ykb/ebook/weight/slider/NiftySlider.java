package com.ykb.ebook.weight.slider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt__MathJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\b\u0016\u0018\u00002\u00020\u0001:\u0003OPQB%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJS\u0010&\u001a\u00020\u001a2K\u0010'\u001aG\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u0013J)\u0010(\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJ)\u0010)\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJ)\u0010*\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJS\u0010+\u001a\u00020\u001a2K\u0010'\u001aG\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110%¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u0013JS\u0010,\u001a\u00020\u001a2K\u0010'\u001aG\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u0013J)\u0010-\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJ)\u0010.\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJ)\u0010/\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJS\u00100\u001a\u00020\u001a2K\u0010'\u001aG\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110%¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u0013J \u00101\u001a\u00020\u00182\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J \u00107\u001a\u00020\u00182\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J \u00108\u001a\u00020\u00182\u0006\u00102\u001a\u0002032\u0006\u00109\u001a\u00020%2\u0006\u0010:\u001a\u00020%H\u0016J \u0010;\u001a\u00020\u00182\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J \u0010<\u001a\u00020\u001a2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J \u0010=\u001a\u00020\u001a2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J \u0010>\u001a\u00020\u001a2\u0006\u00102\u001a\u0002032\u0006\u00109\u001a\u00020%2\u0006\u0010:\u001a\u00020%H\u0016J \u0010?\u001a\u00020\u001a2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J \u0010@\u001a\u00020\u001a2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J \u0010A\u001a\u00020\u001a2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020%H\u0016J\b\u0010B\u001a\u00020\u001aH\u0016J\b\u0010C\u001a\u00020\u001aH\u0016J\b\u0010D\u001a\u00020\u001aH\u0016J\u0018\u0010E\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020%2\u0006\u0010\u0019\u001a\u00020\u0018H\u0016JS\u0010F\u001a\u00020\u001a2K\u0010'\u001aG\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u0013J)\u0010G\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJ)\u0010H\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJ)\u0010I\u001a\u00020\u001a2!\u0010'\u001a\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001dJS\u0010J\u001a\u00020\u001a2K\u0010'\u001aG\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110%¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u0013J\u0010\u0010K\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\u0010H\u0007J\u0010\u0010L\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\u001fH\u0007J\u0010\u0010M\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020#H\u0007J\b\u0010N\u001a\u00020\u001aH\u0016R\"\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000RY\u0010\u0011\u001aM\u0012I\u0012G\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u00130\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R/\u0010\u001c\u001a#\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001d0\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R/\u0010 \u001a#\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001d0\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R/\u0010!\u001a#\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u001a0\u001d0\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000RY\u0010$\u001aM\u0012I\u0012G\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0016\u0012\u0013\u0012\u00110%¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u001a0\u00130\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006R"}, d2 = {"Lcom/ykb/ebook/weight/slider/NiftySlider;", "Lcom/ykb/ebook/weight/slider/BaseSlider;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "effect", "Lcom/ykb/ebook/weight/slider/SliderEffect;", "getEffect", "()Lcom/ykb/ebook/weight/slider/SliderEffect;", "setEffect", "(Lcom/ykb/ebook/weight/slider/SliderEffect;)V", "intValueChangeListener", "Lcom/ykb/ebook/weight/slider/NiftySlider$OnIntValueChangeListener;", "intValueChangeListeners", "", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "slider", "value", "", "fromUser", "", "lastChangedValue", "onProgressAnimEndListener", "Lkotlin/Function1;", "sliderTouchListener", "Lcom/ykb/ebook/weight/slider/NiftySlider$OnSliderTouchListener;", "sliderTouchStartListeners", "sliderTouchStopListeners", "valueChangeListener", "Lcom/ykb/ebook/weight/slider/NiftySlider$OnValueChangeListener;", "valueChangeListeners", "", "addOnIntValueChangeListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "addOnProgressAnimEndListener", "addOnSliderTouchStartListener", "addOnSliderTouchStopListener", "addOnValueChangeListener", "clearOnIntValueChangeListener", "clearOnProgressAnimEndListener", "clearOnSliderTouchStartListener", "clearOnSliderTouchStopListener", "clearOnValueChangeListener", "dispatchDrawInactiveTrackBefore", "canvas", "Landroid/graphics/Canvas;", "trackRect", "Landroid/graphics/RectF;", "yCenter", "dispatchDrawSecondaryTrackBefore", "dispatchDrawThumbBefore", "cx", "cy", "dispatchDrawTrackBefore", "drawInactiveTrackAfter", "drawSecondaryTrackAfter", "drawThumbAfter", "drawTrackAfter", "onDrawAfter", "onDrawBefore", "onProgressAnimEnd", "onStartTacking", "onStopTacking", "onValueChanged", "removeOnIntValueChangeListener", "removeOnProgressAnimEndListener", "removeOnSliderTouchStartListener", "removeOnSliderTouchStopListener", "removeOnValueChangeListener", "setOnIntValueChangeListener", "setOnSliderTouchListener", "setOnValueChangeListener", "updateDirtyData", "OnIntValueChangeListener", "OnSliderTouchListener", "OnValueChangeListener", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNiftySlider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NiftySlider.kt\ncom/ykb/ebook/weight/slider/NiftySlider\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,275:1\n1855#2,2:276\n1855#2,2:278\n1855#2,2:280\n1855#2,2:282\n1855#2,2:284\n*S KotlinDebug\n*F\n+ 1 NiftySlider.kt\ncom/ykb/ebook/weight/slider/NiftySlider\n*L\n56#1:276,2\n64#1:278,2\n88#1:280,2\n94#1:282,2\n271#1:284,2\n*E\n"})
/* loaded from: classes8.dex */
public class NiftySlider extends BaseSlider {

    @Nullable
    private SliderEffect<NiftySlider> effect;

    @Nullable
    private OnIntValueChangeListener intValueChangeListener;

    @NotNull
    private List<Function3<NiftySlider, Integer, Boolean, Unit>> intValueChangeListeners;
    private int lastChangedValue;

    @NotNull
    private List<Function1<NiftySlider, Unit>> onProgressAnimEndListener;

    @Nullable
    private OnSliderTouchListener sliderTouchListener;

    @NotNull
    private List<Function1<NiftySlider, Unit>> sliderTouchStartListeners;

    @NotNull
    private List<Function1<NiftySlider, Unit>> sliderTouchStopListeners;

    @Nullable
    private OnValueChangeListener valueChangeListener;

    @NotNull
    private List<Function3<NiftySlider, Float, Boolean, Unit>> valueChangeListeners;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/weight/slider/NiftySlider$OnIntValueChangeListener;", "", "onValueChange", "", "slider", "Lcom/ykb/ebook/weight/slider/NiftySlider;", "value", "", "fromUser", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnIntValueChangeListener {
        void onValueChange(@NotNull NiftySlider slider, int value, boolean fromUser);
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/weight/slider/NiftySlider$OnSliderTouchListener;", "", "onStartTrackingTouch", "", "slider", "Lcom/ykb/ebook/weight/slider/NiftySlider;", "onStopTrackingTouch", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnSliderTouchListener {
        void onStartTrackingTouch(@NotNull NiftySlider slider);

        void onStopTrackingTouch(@NotNull NiftySlider slider);
    }

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lcom/ykb/ebook/weight/slider/NiftySlider$OnValueChangeListener;", "", "onValueChange", "", "slider", "Lcom/ykb/ebook/weight/slider/NiftySlider;", "value", "", "fromUser", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnValueChangeListener {
        void onValueChange(@NotNull NiftySlider slider, float value, boolean fromUser);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public NiftySlider(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public NiftySlider(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ NiftySlider(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    public final void addOnIntValueChangeListener(@NotNull Function3<? super NiftySlider, ? super Integer, ? super Boolean, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.intValueChangeListeners.add(listener);
    }

    public final void addOnProgressAnimEndListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onProgressAnimEndListener.add(listener);
    }

    public final void addOnSliderTouchStartListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.sliderTouchStartListeners.add(listener);
    }

    public final void addOnSliderTouchStopListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.sliderTouchStopListeners.add(listener);
    }

    public final void addOnValueChangeListener(@NotNull Function3<? super NiftySlider, ? super Float, ? super Boolean, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.valueChangeListeners.add(listener);
    }

    public final void clearOnIntValueChangeListener(@NotNull Function3<? super NiftySlider, ? super Integer, ? super Boolean, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.intValueChangeListeners.add(listener);
    }

    public final void clearOnProgressAnimEndListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onProgressAnimEndListener.clear();
    }

    public final void clearOnSliderTouchStartListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.sliderTouchStartListeners.clear();
    }

    public final void clearOnSliderTouchStopListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.sliderTouchStopListeners.clear();
    }

    public final void clearOnValueChangeListener(@NotNull Function3<? super NiftySlider, ? super Float, ? super Boolean, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.valueChangeListeners.add(listener);
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public boolean dispatchDrawInactiveTrackBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            return sliderEffect.dispatchDrawInactiveTrackBefore(this, canvas, trackRect, yCenter);
        }
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public boolean dispatchDrawSecondaryTrackBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            return sliderEffect.dispatchDrawSecondaryTrackBefore(this, canvas, trackRect, yCenter);
        }
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public boolean dispatchDrawThumbBefore(@NotNull Canvas canvas, float cx, float cy) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            return sliderEffect.dispatchDrawThumbBefore(this, canvas, cx, cy);
        }
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public boolean dispatchDrawTrackBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            return sliderEffect.dispatchDrawTrackBefore(this, canvas, trackRect, yCenter);
        }
        return false;
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void drawInactiveTrackAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.drawInactiveTrackAfter(this, canvas, trackRect, yCenter);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void drawSecondaryTrackAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.drawSecondaryTrackAfter(this, canvas, trackRect, yCenter);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void drawThumbAfter(@NotNull Canvas canvas, float cx, float cy) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.drawThumbAfter(this, canvas, cx, cy);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void drawTrackAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.drawTrackAfter(this, canvas, trackRect, yCenter);
        }
    }

    @Nullable
    public final SliderEffect<NiftySlider> getEffect() {
        return this.effect;
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void onDrawAfter(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.onDrawAfter(canvas, trackRect, yCenter);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void onDrawBefore(@NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.onDrawBefore(canvas, trackRect, yCenter);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void onProgressAnimEnd() {
        super.onProgressAnimEnd();
        Iterator<T> it = this.onProgressAnimEndListener.iterator();
        while (it.hasNext()) {
            ((Function1) it.next()).invoke(this);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void onStartTacking() {
        OnSliderTouchListener onSliderTouchListener = this.sliderTouchListener;
        if (onSliderTouchListener != null) {
            onSliderTouchListener.onStartTrackingTouch(this);
        }
        Iterator<T> it = this.sliderTouchStartListeners.iterator();
        while (it.hasNext()) {
            ((Function1) it.next()).invoke(this);
        }
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.onStartTacking(this);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void onStopTacking() {
        OnSliderTouchListener onSliderTouchListener = this.sliderTouchListener;
        if (onSliderTouchListener != null) {
            onSliderTouchListener.onStopTrackingTouch(this);
        }
        Iterator<T> it = this.sliderTouchStopListeners.iterator();
        while (it.hasNext()) {
            ((Function1) it.next()).invoke(this);
        }
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.onStopTacking(this);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void onValueChanged(float value, boolean fromUser) {
        if (getEnableHapticFeedback() && fromUser && enableStepMode()) {
            performHapticFeedback(1);
        }
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(value);
        if (this.lastChangedValue != iRoundToInt) {
            this.lastChangedValue = iRoundToInt;
            OnIntValueChangeListener onIntValueChangeListener = this.intValueChangeListener;
            if (onIntValueChangeListener != null) {
                onIntValueChangeListener.onValueChange(this, iRoundToInt, fromUser);
            }
            Iterator<T> it = this.intValueChangeListeners.iterator();
            while (it.hasNext()) {
                ((Function3) it.next()).invoke(this, Integer.valueOf(iRoundToInt), Boolean.valueOf(fromUser));
            }
        }
        OnValueChangeListener onValueChangeListener = this.valueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, value, fromUser);
        }
        Iterator<T> it2 = this.valueChangeListeners.iterator();
        while (it2.hasNext()) {
            ((Function3) it2.next()).invoke(this, Float.valueOf(value), Boolean.valueOf(fromUser));
        }
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.onValueChanged(this, value, fromUser);
        }
    }

    public final void removeOnIntValueChangeListener(@NotNull Function3<? super NiftySlider, ? super Integer, ? super Boolean, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.intValueChangeListeners.add(listener);
    }

    public final void removeOnProgressAnimEndListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onProgressAnimEndListener.remove(listener);
    }

    public final void removeOnSliderTouchStartListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.sliderTouchStartListeners.remove(listener);
    }

    public final void removeOnSliderTouchStopListener(@NotNull Function1<? super NiftySlider, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.sliderTouchStopListeners.remove(listener);
    }

    public final void removeOnValueChangeListener(@NotNull Function3<? super NiftySlider, ? super Float, ? super Boolean, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.valueChangeListeners.add(listener);
    }

    public final void setEffect(@Nullable SliderEffect<NiftySlider> sliderEffect) {
        this.effect = sliderEffect;
    }

    @Deprecated(message = "use addOnIntValueChangeListener instead")
    public final void setOnIntValueChangeListener(@NotNull OnIntValueChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.intValueChangeListener = listener;
    }

    @Deprecated(message = "use addOnSliderTouchStartListener | addOnSliderTouchStopListener instead")
    public final void setOnSliderTouchListener(@NotNull OnSliderTouchListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.sliderTouchListener = listener;
    }

    @Deprecated(message = "use addOnValueChangeListener instead")
    public final void setOnValueChangeListener(@NotNull OnValueChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.valueChangeListener = listener;
    }

    @Override // com.ykb.ebook.weight.slider.BaseSlider
    public void updateDirtyData() {
        SliderEffect<NiftySlider> sliderEffect = this.effect;
        if (sliderEffect != null) {
            sliderEffect.updateDirtyData();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public NiftySlider(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.valueChangeListeners = new ArrayList();
        this.intValueChangeListeners = new ArrayList();
        this.sliderTouchStartListeners = new ArrayList();
        this.sliderTouchStopListeners = new ArrayList();
        this.onProgressAnimEndListener = new ArrayList();
        this.lastChangedValue = -1;
    }
}
