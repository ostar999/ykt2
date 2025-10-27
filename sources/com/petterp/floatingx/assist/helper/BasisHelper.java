package com.petterp.floatingx.assist.helper;

import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.CharPool;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.mobile.auth.gatewayauth.Constant;
import com.petterp.floatingx.assist.FxAnimation;
import com.petterp.floatingx.assist.FxBorderMargin;
import com.petterp.floatingx.assist.FxDisplayMode;
import com.petterp.floatingx.assist.FxGravity;
import com.petterp.floatingx.listener.IFxConfigStorage;
import com.petterp.floatingx.listener.IFxScrollListener;
import com.petterp.floatingx.listener.IFxViewLifecycle;
import com.petterp.floatingx.util.FxAdsorbDirection;
import com.petterp.floatingx.util.FxLog;
import com.tencent.connect.common.Constants;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001:\u00016B\u0005¢\u0006\u0002\u0010\u0002J\r\u00100\u001a\u000201H\u0000¢\u0006\u0002\b2J\u0015\u00103\u001a\u0002012\u0006\u00104\u001a\u00020\u001dH\u0000¢\u0006\u0002\b5R\u0012\u0010\u0003\u001a\u00020\u00048\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\b8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\u000b8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00020\b8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0010\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0014\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u00178\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u00020\u00198\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001b8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001c\u001a\u00020\u001d8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001e\u001a\u00020\u001f8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\u0004\u0018\u00010!8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u0004\u0018\u00010#8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\u0004\u0018\u00010%8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u0004\u0018\u00010'8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010(\u001a\u00020)8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010*\u001a\u0004\u0018\u00010+8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\u0004\u0018\u00010-8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010.\u001a\u00020)8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010/\u001a\u00020)8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lcom/petterp/floatingx/assist/helper/BasisHelper;", "", "()V", "adsorbDirection", "Lcom/petterp/floatingx/util/FxAdsorbDirection;", "clickTime", "", "defaultX", "", "defaultY", "displayMode", "Lcom/petterp/floatingx/assist/FxDisplayMode;", "edgeOffset", "enableAnimation", "", "enableAssistLocation", "enableClickListener", "enableDebugLog", "enableEdgeAdsorption", "enableEdgeRebound", "enableFx", "enableSaveDirection", "fxAnimation", "Lcom/petterp/floatingx/assist/FxAnimation;", "fxBorderMargin", "Lcom/petterp/floatingx/assist/FxBorderMargin;", "fxLog", "Lcom/petterp/floatingx/util/FxLog;", "fxLogTag", "", "gravity", "Lcom/petterp/floatingx/assist/FxGravity;", "iFxClickListener", "Landroid/view/View$OnClickListener;", "iFxConfigStorage", "Lcom/petterp/floatingx/listener/IFxConfigStorage;", "iFxScrollListener", "Lcom/petterp/floatingx/listener/IFxScrollListener;", "iFxViewLifecycle", "Lcom/petterp/floatingx/listener/IFxViewLifecycle;", "layoutId", "", "layoutParams", "Landroid/widget/FrameLayout$LayoutParams;", "layoutView", "Landroid/view/View;", "navigationBarHeight", "statsBarHeight", PLVDocumentMarkToolType.CLEAR, "", "clear$floatingx_release", "initLog", Constants.PARAM_SCOPE, "initLog$floatingx_release", "Builder", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public class BasisHelper {

    @JvmField
    public float defaultX;

    @JvmField
    public float defaultY;

    @JvmField
    public float edgeOffset;

    @JvmField
    public boolean enableAnimation;

    @JvmField
    public boolean enableAssistLocation;

    @JvmField
    public boolean enableDebugLog;

    @JvmField
    public boolean enableFx;

    @JvmField
    public boolean enableSaveDirection;

    @JvmField
    @Nullable
    public FxAnimation fxAnimation;

    @JvmField
    @Nullable
    public FxLog fxLog;

    @JvmField
    @Nullable
    public View.OnClickListener iFxClickListener;

    @JvmField
    @Nullable
    public IFxConfigStorage iFxConfigStorage;

    @JvmField
    @Nullable
    public IFxScrollListener iFxScrollListener;

    @JvmField
    @Nullable
    public IFxViewLifecycle iFxViewLifecycle;

    @JvmField
    public int layoutId;

    @JvmField
    @Nullable
    public FrameLayout.LayoutParams layoutParams;

    @JvmField
    @Nullable
    public View layoutView;

    @JvmField
    public int navigationBarHeight;

    @JvmField
    public int statsBarHeight;

    @JvmField
    @NotNull
    public FxGravity gravity = FxGravity.DEFAULT;

    @JvmField
    public long clickTime = 300;

    @JvmField
    @NotNull
    public FxBorderMargin fxBorderMargin = new FxBorderMargin(0.0f, 0.0f, 0.0f, 0.0f, 15, null);

    @JvmField
    @NotNull
    public FxDisplayMode displayMode = FxDisplayMode.Normal;

    @JvmField
    @NotNull
    public FxAdsorbDirection adsorbDirection = FxAdsorbDirection.LEFT_OR_RIGHT;

    @JvmField
    public boolean enableEdgeAdsorption = true;

    @JvmField
    public boolean enableEdgeRebound = true;

    @JvmField
    public boolean enableClickListener = true;

    @JvmField
    @NotNull
    public String fxLogTag = "";

    @Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b;\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u00100\u001a\u000201H\u0002J\r\u00102\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00103J\r\u00104\u001a\u00028\u0001H$¢\u0006\u0002\u00103J\u0013\u00105\u001a\u00028\u00002\u0006\u0010\u001b\u001a\u00020\u001c¢\u0006\u0002\u00106J+\u00107\u001a\u00028\u00002\u0006\u00108\u001a\u00020\u000b2\u0006\u00109\u001a\u00020\u000b2\u0006\u0010:\u001a\u00020\u000b2\u0006\u0010;\u001a\u00020\u000b¢\u0006\u0002\u0010<J\u0013\u0010=\u001a\u00028\u00002\u0006\u0010:\u001a\u00020\u000b¢\u0006\u0002\u0010>J\u0013\u0010?\u001a\u00028\u00002\u0006\u0010@\u001a\u00020\u000e¢\u0006\u0002\u0010AJ\u0013\u0010B\u001a\u00028\u00002\u0006\u0010C\u001a\u00020\u0010¢\u0006\u0002\u0010DJ\u0013\u0010E\u001a\u00028\u00002\u0006\u0010F\u001a\u00020\u000b¢\u0006\u0002\u0010>J\u0013\u0010G\u001a\u00028\u00002\u0006\u0010H\u001a\u00020\u0013¢\u0006\u0002\u0010IJ3\u0010J\u001a\u00028\u00002\b\b\u0002\u00108\u001a\u00020\u000b2\b\b\u0002\u0010:\u001a\u00020\u000b2\b\b\u0002\u00109\u001a\u00020\u000b2\b\b\u0002\u0010;\u001a\u00020\u000b¢\u0006\u0002\u0010<J\u0013\u0010K\u001a\u00028\u00002\u0006\u0010H\u001a\u00020\u0013¢\u0006\u0002\u0010IJ!\u0010L\u001a\u00028\u00002\b\b\u0002\u0010M\u001a\u00020\u00132\b\b\u0002\u0010N\u001a\u00020\u001fH\u0007¢\u0006\u0002\u0010OJ\u0013\u0010P\u001a\u00028\u00002\u0006\u0010H\u001a\u00020\u0013¢\u0006\u0002\u0010IJ\u0015\u0010Q\u001a\u00028\u00002\u0006\u0010H\u001a\u00020\u0013H\u0007¢\u0006\u0002\u0010IJ\u0013\u0010R\u001a\u00028\u00002\u0006\u0010 \u001a\u00020!¢\u0006\u0002\u0010SJ\u0015\u0010T\u001a\u00028\u00002\b\b\u0001\u0010*\u001a\u00020+¢\u0006\u0002\u0010UJ\u0013\u0010V\u001a\u00028\u00002\u0006\u0010W\u001a\u00020/¢\u0006\u0002\u0010XJ\u0013\u0010Y\u001a\u00028\u00002\u0006\u00109\u001a\u00020\u000b¢\u0006\u0002\u0010>J\u0013\u0010Z\u001a\u00028\u00002\u0006\u0010,\u001a\u00020-¢\u0006\u0002\u0010[J\u001f\u0010\\\u001a\u00028\u00002\b\b\u0002\u0010]\u001a\u00020\t2\u0006\u0010^\u001a\u00020)H\u0007¢\u0006\u0002\u0010_J\u0013\u0010`\u001a\u00028\u00002\u0006\u0010;\u001a\u00020\u000b¢\u0006\u0002\u0010>J\u0013\u0010a\u001a\u00028\u00002\u0006\u0010\"\u001a\u00020#¢\u0006\u0002\u0010bJ\u0013\u0010c\u001a\u00028\u00002\u0006\u0010$\u001a\u00020%¢\u0006\u0002\u0010dJ\u0013\u0010e\u001a\u00028\u00002\u0006\u00108\u001a\u00020\u000b¢\u0006\u0002\u0010>J\u0013\u0010f\u001a\u00028\u00002\u0006\u0010&\u001a\u00020'¢\u0006\u0002\u0010gJ\u0013\u0010h\u001a\u00028\u00002\u0006\u0010i\u001a\u00020\u000b¢\u0006\u0002\u0010>J\u0013\u0010j\u001a\u00028\u00002\u0006\u0010k\u001a\u00020\u000b¢\u0006\u0002\u0010>R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010*\u001a\u00020+8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010-X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006l"}, d2 = {"Lcom/petterp/floatingx/assist/helper/BasisHelper$Builder;", ExifInterface.GPS_DIRECTION_TRUE, "B", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "", "()V", "assistLocation", "Lcom/petterp/floatingx/assist/FxBorderMargin;", "clickTime", "", "defaultX", "", "defaultY", "displayMode", "Lcom/petterp/floatingx/assist/FxDisplayMode;", "edgeAdsorbDirection", "Lcom/petterp/floatingx/util/FxAdsorbDirection;", "edgeOffset", "enableAnimation", "", "enableAssistLocation", "enableClickListener", "enableDebugLog", "enableEdgeAdsorption", "enableEdgeRebound", "enableFx", "enableSaveDirection", "fxAnimation", "Lcom/petterp/floatingx/assist/FxAnimation;", "fxBorderMargin", "fxLogTag", "", "gravity", "Lcom/petterp/floatingx/assist/FxGravity;", "iFxConfigStorage", "Lcom/petterp/floatingx/listener/IFxConfigStorage;", "iFxScrollListener", "Lcom/petterp/floatingx/listener/IFxScrollListener;", "iFxViewLifecycle", "Lcom/petterp/floatingx/listener/IFxViewLifecycle;", "ifxClickListener", "Landroid/view/View$OnClickListener;", "layoutId", "", "layoutParams", "Landroid/widget/FrameLayout$LayoutParams;", "layoutView", "Landroid/view/View;", "adtSizeViewDirection", "", "build", "()Lcom/petterp/floatingx/assist/helper/BasisHelper;", "buildHelper", "setAnimationImpl", "(Lcom/petterp/floatingx/assist/FxAnimation;)Ljava/lang/Object;", "setBorderMargin", "t", NotifyType.LIGHTS, "b", "r", "(FFFF)Ljava/lang/Object;", "setBottomBorderMargin", "(F)Ljava/lang/Object;", "setDisplayMode", "mode", "(Lcom/petterp/floatingx/assist/FxDisplayMode;)Ljava/lang/Object;", "setEdgeAdsorbDirection", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "(Lcom/petterp/floatingx/util/FxAdsorbDirection;)Ljava/lang/Object;", "setEdgeOffset", "edge", "setEnableAnimation", Constant.API_PARAMS_KEY_ENABLE, "(Z)Ljava/lang/Object;", "setEnableAssistDirection", "setEnableEdgeAdsorption", "setEnableLog", "isLog", "tag", "(ZLjava/lang/String;)Ljava/lang/Object;", "setEnableScrollOutsideScreen", "setEnableTouch", "setGravity", "(Lcom/petterp/floatingx/assist/FxGravity;)Ljava/lang/Object;", "setLayout", "(I)Ljava/lang/Object;", "setLayoutView", "view", "(Landroid/view/View;)Ljava/lang/Object;", "setLeftBorderMargin", "setManagerParams", "(Landroid/widget/FrameLayout$LayoutParams;)Ljava/lang/Object;", "setOnClickListener", CrashHianalyticsData.TIME, "clickListener", "(JLandroid/view/View$OnClickListener;)Ljava/lang/Object;", "setRightBorderMargin", "setSaveDirectionImpl", "(Lcom/petterp/floatingx/listener/IFxConfigStorage;)Ljava/lang/Object;", "setScrollListener", "(Lcom/petterp/floatingx/listener/IFxScrollListener;)Ljava/lang/Object;", "setTopBorderMargin", "setViewLifecycle", "(Lcom/petterp/floatingx/listener/IFxViewLifecycle;)Ljava/lang/Object;", "setX", "x", "setY", "y", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static abstract class Builder<T, B extends BasisHelper> {
        private float defaultX;
        private float defaultY;
        private float edgeOffset;
        private boolean enableAnimation;
        private boolean enableAssistLocation;
        private boolean enableClickListener;
        private boolean enableDebugLog;
        private boolean enableFx;
        private boolean enableSaveDirection;

        @Nullable
        private FxAnimation fxAnimation;

        @Nullable
        private IFxConfigStorage iFxConfigStorage;

        @Nullable
        private IFxScrollListener iFxScrollListener;

        @Nullable
        private IFxViewLifecycle iFxViewLifecycle;

        @Nullable
        private View.OnClickListener ifxClickListener;

        @LayoutRes
        private int layoutId;

        @Nullable
        private FrameLayout.LayoutParams layoutParams;

        @Nullable
        private View layoutView;
        private long clickTime = 300;

        @NotNull
        private FxGravity gravity = FxGravity.DEFAULT;

        @NotNull
        private FxDisplayMode displayMode = FxDisplayMode.Normal;

        @NotNull
        private FxAdsorbDirection edgeAdsorbDirection = FxAdsorbDirection.LEFT_OR_RIGHT;

        @NotNull
        private FxBorderMargin fxBorderMargin = new FxBorderMargin(0.0f, 0.0f, 0.0f, 0.0f, 15, null);

        @NotNull
        private FxBorderMargin assistLocation = new FxBorderMargin(0.0f, 0.0f, 0.0f, 0.0f, 15, null);

        @NotNull
        private String fxLogTag = "";
        private boolean enableEdgeRebound = true;
        private boolean enableEdgeAdsorption = true;

        @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[FxGravity.values().length];
                iArr[FxGravity.DEFAULT.ordinal()] = 1;
                iArr[FxGravity.LEFT_OR_TOP.ordinal()] = 2;
                iArr[FxGravity.LEFT_OR_BOTTOM.ordinal()] = 3;
                iArr[FxGravity.RIGHT_OR_BOTTOM.ordinal()] = 4;
                iArr[FxGravity.RIGHT_OR_TOP.ordinal()] = 5;
                iArr[FxGravity.RIGHT_OR_CENTER.ordinal()] = 6;
                iArr[FxGravity.LEFT_OR_CENTER.ordinal()] = 7;
                iArr[FxGravity.TOP_OR_CENTER.ordinal()] = 8;
                iArr[FxGravity.BOTTOM_OR_CENTER.ordinal()] = 9;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private final void adtSizeViewDirection() {
            if (this.enableAssistLocation || this.gravity.isDefault()) {
                float f2 = this.enableEdgeRebound ? this.edgeOffset : 0.0f;
                float b3 = this.assistLocation.getB() + this.fxBorderMargin.getB() + f2;
                float t2 = this.assistLocation.getT() + this.fxBorderMargin.getT() + f2;
                float r2 = this.assistLocation.getR() + this.fxBorderMargin.getR() + f2;
                float l2 = this.assistLocation.getL() + this.fxBorderMargin.getL() + f2;
                this.defaultX = 0.0f;
                this.defaultY = 0.0f;
                switch (WhenMappings.$EnumSwitchMapping$0[this.gravity.ordinal()]) {
                    case 1:
                    case 2:
                        this.defaultX = l2;
                        this.defaultY = t2;
                        break;
                    case 3:
                        this.defaultY = -b3;
                        this.defaultX = l2;
                        break;
                    case 4:
                        this.defaultY = -b3;
                        this.defaultX = -r2;
                        break;
                    case 5:
                        this.defaultX = -r2;
                        this.defaultY = t2;
                        break;
                    case 6:
                        this.defaultX = -r2;
                        break;
                    case 7:
                        this.defaultX = l2;
                        break;
                    case 8:
                        this.defaultY = t2;
                        break;
                    case 9:
                        this.defaultY = -b3;
                        break;
                }
            }
        }

        public static /* synthetic */ Object setEnableAssistDirection$default(Builder builder, float f2, float f3, float f4, float f5, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setEnableAssistDirection");
            }
            if ((i2 & 1) != 0) {
                f2 = 0.0f;
            }
            if ((i2 & 2) != 0) {
                f3 = 0.0f;
            }
            if ((i2 & 4) != 0) {
                f4 = 0.0f;
            }
            if ((i2 & 8) != 0) {
                f5 = 0.0f;
            }
            return builder.setEnableAssistDirection(f2, f3, f4, f5);
        }

        public static /* synthetic */ Object setEnableLog$default(Builder builder, boolean z2, String str, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setEnableLog");
            }
            if ((i2 & 1) != 0) {
                z2 = true;
            }
            if ((i2 & 2) != 0) {
                str = "";
            }
            return builder.setEnableLog(z2, str);
        }

        public static /* synthetic */ Object setOnClickListener$default(Builder builder, long j2, View.OnClickListener onClickListener, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setOnClickListener");
            }
            if ((i2 & 1) != 0) {
                j2 = 500;
            }
            return builder.setOnClickListener(j2, onClickListener);
        }

        @NotNull
        public B build() {
            B b3 = (B) buildHelper();
            adtSizeViewDirection();
            b3.enableFx = this.enableFx;
            b3.layoutId = this.layoutId;
            b3.layoutView = this.layoutView;
            b3.gravity = this.gravity;
            b3.clickTime = this.clickTime;
            b3.layoutParams = this.layoutParams;
            b3.fxAnimation = this.fxAnimation;
            b3.displayMode = this.displayMode;
            b3.defaultY = this.defaultY;
            b3.defaultX = this.defaultX;
            b3.edgeOffset = this.edgeOffset;
            b3.fxBorderMargin = this.fxBorderMargin;
            b3.adsorbDirection = this.edgeAdsorbDirection;
            b3.enableAnimation = this.enableAnimation;
            b3.enableEdgeAdsorption = this.enableEdgeAdsorption;
            b3.enableEdgeRebound = this.enableEdgeRebound;
            b3.enableSaveDirection = this.enableSaveDirection;
            b3.enableClickListener = this.enableClickListener;
            b3.enableAssistLocation = this.enableAssistLocation;
            b3.enableDebugLog = this.enableDebugLog;
            b3.fxLogTag = this.fxLogTag;
            b3.iFxScrollListener = this.iFxScrollListener;
            b3.iFxViewLifecycle = this.iFxViewLifecycle;
            b3.iFxConfigStorage = this.iFxConfigStorage;
            b3.iFxClickListener = this.ifxClickListener;
            return b3;
        }

        @NotNull
        public abstract B buildHelper();

        /* JADX WARN: Multi-variable type inference failed */
        public final T setAnimationImpl(@NotNull FxAnimation fxAnimation) {
            Intrinsics.checkNotNullParameter(fxAnimation, "fxAnimation");
            this.fxAnimation = fxAnimation;
            this.enableAnimation = true;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setBorderMargin(float t2, float l2, float b3, float r2) {
            FxBorderMargin fxBorderMargin = this.fxBorderMargin;
            fxBorderMargin.setT(t2);
            fxBorderMargin.setL(l2);
            fxBorderMargin.setB(b3);
            fxBorderMargin.setR(r2);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setBottomBorderMargin(float b3) {
            this.fxBorderMargin.setB(Math.abs(b3));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setDisplayMode(@NotNull FxDisplayMode mode) {
            Intrinsics.checkNotNullParameter(mode, "mode");
            this.displayMode = mode;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setEdgeAdsorbDirection(@NotNull FxAdsorbDirection direction) {
            Intrinsics.checkNotNullParameter(direction, "direction");
            this.edgeAdsorbDirection = direction;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setEdgeOffset(float edge) {
            this.edgeOffset = Math.abs(edge);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setEnableAnimation(boolean isEnable) {
            this.enableAnimation = isEnable;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setEnableAssistDirection(float t2, float b3, float l2, float r2) {
            this.enableAssistLocation = true;
            this.assistLocation.setT(t2);
            this.assistLocation.setB(b3);
            this.assistLocation.setL(l2);
            this.assistLocation.setR(r2);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setEnableEdgeAdsorption(boolean isEnable) {
            this.enableEdgeAdsorption = isEnable;
            return this;
        }

        @JvmOverloads
        public final T setEnableLog() {
            return (T) setEnableLog$default(this, false, null, 3, null);
        }

        @JvmOverloads
        public final T setEnableLog(boolean z2) {
            return (T) setEnableLog$default(this, z2, null, 2, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @JvmOverloads
        public final T setEnableLog(boolean isLog, @NotNull String tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            this.enableDebugLog = isLog;
            this.fxLogTag = tag.length() > 0 ? Intrinsics.stringPlus("-", tag) : "";
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setEnableScrollOutsideScreen(boolean isEnable) {
            this.enableEdgeRebound = isEnable;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Deprecated(message = "已废弃，建议使用 [setDisplayMode()]")
        public final T setEnableTouch(boolean isEnable) {
            this.displayMode = isEnable ? FxDisplayMode.Normal : FxDisplayMode.ClickOnly;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setGravity(@NotNull FxGravity gravity) {
            Intrinsics.checkNotNullParameter(gravity, "gravity");
            this.gravity = gravity;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setLayout(@LayoutRes int layoutId) {
            this.layoutView = null;
            this.layoutId = layoutId;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setLayoutView(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            this.layoutId = 0;
            this.layoutView = view;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setLeftBorderMargin(float l2) {
            this.fxBorderMargin.setL(Math.abs(l2));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setManagerParams(@NotNull FrameLayout.LayoutParams layoutParams) {
            Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
            this.layoutParams = layoutParams;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @JvmOverloads
        public final T setOnClickListener(long time, @NotNull View.OnClickListener clickListener) {
            Intrinsics.checkNotNullParameter(clickListener, "clickListener");
            this.enableClickListener = true;
            this.ifxClickListener = clickListener;
            this.clickTime = time;
            return this;
        }

        @JvmOverloads
        public final T setOnClickListener(@NotNull View.OnClickListener clickListener) {
            Intrinsics.checkNotNullParameter(clickListener, "clickListener");
            return (T) setOnClickListener$default(this, 0L, clickListener, 1, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setRightBorderMargin(float r2) {
            this.fxBorderMargin.setR(Math.abs(r2));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setSaveDirectionImpl(@NotNull IFxConfigStorage iFxConfigStorage) {
            Intrinsics.checkNotNullParameter(iFxConfigStorage, "iFxConfigStorage");
            this.enableSaveDirection = true;
            this.iFxConfigStorage = iFxConfigStorage;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setScrollListener(@NotNull IFxScrollListener iFxScrollListener) {
            Intrinsics.checkNotNullParameter(iFxScrollListener, "iFxScrollListener");
            this.iFxScrollListener = iFxScrollListener;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setTopBorderMargin(float t2) {
            this.fxBorderMargin.setT(Math.abs(t2));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setViewLifecycle(@NotNull IFxViewLifecycle iFxViewLifecycle) {
            Intrinsics.checkNotNullParameter(iFxViewLifecycle, "iFxViewLifecycle");
            this.iFxViewLifecycle = iFxViewLifecycle;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setX(float x2) {
            this.defaultX = x2;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final T setY(float y2) {
            this.defaultY = y2;
            return this;
        }
    }

    public final /* synthetic */ void clear$floatingx_release() {
        this.layoutView = null;
        this.enableFx = false;
        FxAnimation fxAnimation = this.fxAnimation;
        if (fxAnimation == null) {
            return;
        }
        fxAnimation.cancelAnimation();
    }

    public final /* synthetic */ void initLog$floatingx_release(String scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (this.enableDebugLog) {
            this.fxLog = FxLog.INSTANCE.builder(scope + CharPool.DASHED + this.fxLogTag);
        }
    }
}
