package com.plv.linkmic.processor;

import com.plv.linkmic.PLVLinkMicConstant;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'BITRATE_STANDARD_16_9_15FPS' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0086\u0001\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0017B7\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000j\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016¨\u0006\u0018"}, d2 = {"Lcom/plv/linkmic/processor/PLVVideoDimensionBitrate;", "", "bitrateType", "", "ratio", "Lcom/plv/linkmic/PLVLinkMicConstant$PushResolutionRatio;", "frameRate", "width", "height", "realBitrate", "(Ljava/lang/String;IILcom/plv/linkmic/PLVLinkMicConstant$PushResolutionRatio;IIII)V", "BITRATE_STANDARD_16_9_15FPS", "BITRATE_STANDARD_16_9_30FPS", "BITRATE_STANDARD_4_3_15FPS", "BITRATE_STANDARD_4_3_30FPS", "BITRATE_HIGH_16_9_15FPS", "BITRATE_HIGH_16_9_30FPS", "BITRATE_HIGH_4_3_15FPS", "BITRATE_HIGH_4_3_30FPS", "BITRATE_SUPER_16_9_15FPS", "BITRATE_SUPER_16_9_30FPS", "BITRATE_SUPER_4_3_15FPS", "BITRATE_SUPER_4_3_30FPS", "Companion", "polyvSDKLinkMic_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class PLVVideoDimensionBitrate {
    private static final /* synthetic */ PLVVideoDimensionBitrate[] $VALUES;
    public static final PLVVideoDimensionBitrate BITRATE_HIGH_16_9_15FPS;
    public static final PLVVideoDimensionBitrate BITRATE_HIGH_16_9_30FPS;
    public static final PLVVideoDimensionBitrate BITRATE_HIGH_4_3_15FPS;
    public static final PLVVideoDimensionBitrate BITRATE_HIGH_4_3_30FPS;
    public static final PLVVideoDimensionBitrate BITRATE_STANDARD_16_9_15FPS;
    public static final PLVVideoDimensionBitrate BITRATE_STANDARD_16_9_30FPS;
    public static final PLVVideoDimensionBitrate BITRATE_STANDARD_4_3_15FPS;
    public static final PLVVideoDimensionBitrate BITRATE_STANDARD_4_3_30FPS;
    public static final PLVVideoDimensionBitrate BITRATE_SUPER_16_9_15FPS;
    public static final PLVVideoDimensionBitrate BITRATE_SUPER_16_9_30FPS;
    public static final PLVVideoDimensionBitrate BITRATE_SUPER_4_3_15FPS;
    public static final PLVVideoDimensionBitrate BITRATE_SUPER_4_3_30FPS;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;

    @JvmField
    public final int bitrateType;

    @JvmField
    public final int frameRate;

    @JvmField
    public final int height;

    @JvmField
    @NotNull
    public final PLVLinkMicConstant.PushResolutionRatio ratio;

    @JvmField
    public final int realBitrate;

    @JvmField
    public final int width;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0007¨\u0006\n"}, d2 = {"Lcom/plv/linkmic/processor/PLVVideoDimensionBitrate$Companion;", "", "()V", "match", "Lcom/plv/linkmic/processor/PLVVideoDimensionBitrate;", "bitrateType", "", "ratio", "Lcom/plv/linkmic/PLVLinkMicConstant$PushResolutionRatio;", "frameRate", "polyvSDKLinkMic_release"}, k = 1, mv = {1, 1, 16})
    /* renamed from: com.plv.linkmic.processor.PLVVideoDimensionBitrate$a, reason: from kotlin metadata */
    public static final class Companion {
        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v2, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r9v3 */
        /* JADX WARN: Type inference failed for: r9v4, types: [com.plv.linkmic.processor.PLVVideoDimensionBitrate] */
        /* JADX WARN: Type inference failed for: r9v5 */
        /* JADX WARN: Type inference failed for: r9v6 */
        private final PLVVideoDimensionBitrate a(int i2, PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
            PLVVideoDimensionBitrate pLVVideoDimensionBitrate;
            PLVVideoDimensionBitrate[] pLVVideoDimensionBitrateArrValues = PLVVideoDimensionBitrate.values();
            ArrayList arrayList = new ArrayList();
            for (PLVVideoDimensionBitrate pLVVideoDimensionBitrate2 : pLVVideoDimensionBitrateArrValues) {
                if (pLVVideoDimensionBitrate2.bitrateType == i2 && pLVVideoDimensionBitrate2.ratio == pushResolutionRatio) {
                    arrayList.add(pLVVideoDimensionBitrate2);
                }
            }
            Iterator it = arrayList.iterator();
            if (it.hasNext()) {
                ?? next = it.next();
                while (it.hasNext()) {
                    PLVVideoDimensionBitrate pLVVideoDimensionBitrate3 = (PLVVideoDimensionBitrate) it.next();
                    next = (PLVVideoDimensionBitrate) next;
                    if (next.frameRate >= pLVVideoDimensionBitrate3.frameRate) {
                        next = pLVVideoDimensionBitrate3;
                    }
                }
                pLVVideoDimensionBitrate = next;
            } else {
                pLVVideoDimensionBitrate = null;
            }
            return pLVVideoDimensionBitrate;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r0v4 */
        /* JADX WARN: Type inference failed for: r0v5, types: [com.plv.linkmic.processor.PLVVideoDimensionBitrate] */
        /* JADX WARN: Type inference failed for: r0v6 */
        /* JADX WARN: Type inference failed for: r0v7 */
        @JvmStatic
        @NotNull
        public final PLVVideoDimensionBitrate match(int bitrateType, @NotNull PLVLinkMicConstant.PushResolutionRatio ratio, int frameRate) {
            PLVVideoDimensionBitrate pLVVideoDimensionBitrate;
            Intrinsics.checkParameterIsNotNull(ratio, "ratio");
            PLVVideoDimensionBitrate[] pLVVideoDimensionBitrateArrValues = PLVVideoDimensionBitrate.values();
            ArrayList arrayList = new ArrayList();
            for (PLVVideoDimensionBitrate pLVVideoDimensionBitrate2 : pLVVideoDimensionBitrateArrValues) {
                if (pLVVideoDimensionBitrate2.bitrateType == bitrateType && pLVVideoDimensionBitrate2.ratio == ratio && pLVVideoDimensionBitrate2.frameRate >= frameRate) {
                    arrayList.add(pLVVideoDimensionBitrate2);
                }
            }
            Iterator it = arrayList.iterator();
            if (it.hasNext()) {
                ?? next = it.next();
                while (it.hasNext()) {
                    PLVVideoDimensionBitrate pLVVideoDimensionBitrate3 = (PLVVideoDimensionBitrate) it.next();
                    next = (PLVVideoDimensionBitrate) next;
                    if (next.frameRate >= pLVVideoDimensionBitrate3.frameRate) {
                        next = pLVVideoDimensionBitrate3;
                    }
                }
                pLVVideoDimensionBitrate = next;
            } else {
                pLVVideoDimensionBitrate = null;
            }
            PLVVideoDimensionBitrate pLVVideoDimensionBitrateA = pLVVideoDimensionBitrate;
            if (pLVVideoDimensionBitrateA == null) {
                pLVVideoDimensionBitrateA = a(bitrateType, ratio);
            }
            return pLVVideoDimensionBitrateA != null ? pLVVideoDimensionBitrateA : PLVVideoDimensionBitrate.BITRATE_STANDARD_16_9_15FPS;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio = PLVLinkMicConstant.PushResolutionRatio.RATIO_16_9;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate = new PLVVideoDimensionBitrate("BITRATE_STANDARD_16_9_15FPS", 0, 1, pushResolutionRatio, 15, 320, 180, 400);
        BITRATE_STANDARD_16_9_15FPS = pLVVideoDimensionBitrate;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate2 = new PLVVideoDimensionBitrate("BITRATE_STANDARD_16_9_30FPS", 1, 1, pushResolutionRatio, 30, 320, 180, 600);
        BITRATE_STANDARD_16_9_30FPS = pLVVideoDimensionBitrate2;
        PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio2 = PLVLinkMicConstant.PushResolutionRatio.RATIO_4_3;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate3 = new PLVVideoDimensionBitrate("BITRATE_STANDARD_4_3_15FPS", 2, 1, pushResolutionRatio2, 15, 240, 180, 250);
        BITRATE_STANDARD_4_3_15FPS = pLVVideoDimensionBitrate3;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate4 = new PLVVideoDimensionBitrate("BITRATE_STANDARD_4_3_30FPS", 3, 1, pushResolutionRatio2, 30, 240, 180, R2.attr.arcTickStrokeWidth);
        BITRATE_STANDARD_4_3_30FPS = pLVVideoDimensionBitrate4;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate5 = new PLVVideoDimensionBitrate("BITRATE_HIGH_16_9_15FPS", 4, 2, pushResolutionRatio, 15, 640, 360, 900);
        BITRATE_HIGH_16_9_15FPS = pLVVideoDimensionBitrate5;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate6 = new PLVVideoDimensionBitrate("BITRATE_HIGH_16_9_30FPS", 5, 2, pushResolutionRatio, 30, 640, 360, 1200);
        BITRATE_HIGH_16_9_30FPS = pLVVideoDimensionBitrate6;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate7 = new PLVVideoDimensionBitrate("BITRATE_HIGH_4_3_15FPS", 6, 2, pushResolutionRatio2, 15, 480, 360, 640);
        BITRATE_HIGH_4_3_15FPS = pLVVideoDimensionBitrate7;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate8 = new PLVVideoDimensionBitrate("BITRATE_HIGH_4_3_30FPS", 7, 2, pushResolutionRatio2, 30, 480, 360, R2.attr.circularflow_defaultRadius);
        BITRATE_HIGH_4_3_30FPS = pLVVideoDimensionBitrate8;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate9 = new PLVVideoDimensionBitrate("BITRATE_SUPER_16_9_15FPS", 8, 3, pushResolutionRatio, 15, 1280, 720, R2.attr.layout_maxWidth);
        BITRATE_SUPER_16_9_15FPS = pLVVideoDimensionBitrate9;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate10 = new PLVVideoDimensionBitrate("BITRATE_SUPER_16_9_30FPS", 9, 3, pushResolutionRatio, 30, 1280, 720, R2.attr.sviptscolor);
        BITRATE_SUPER_16_9_30FPS = pLVVideoDimensionBitrate10;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate11 = new PLVVideoDimensionBitrate("BITRATE_SUPER_4_3_15FPS", 10, 3, pushResolutionRatio2, 15, 960, 720, R2.attr.ic_mine_sign_yellow);
        BITRATE_SUPER_4_3_15FPS = pLVVideoDimensionBitrate11;
        PLVVideoDimensionBitrate pLVVideoDimensionBitrate12 = new PLVVideoDimensionBitrate("BITRATE_SUPER_4_3_30FPS", 11, 3, pushResolutionRatio2, 30, 960, 720, R2.attr.path_percent);
        BITRATE_SUPER_4_3_30FPS = pLVVideoDimensionBitrate12;
        $VALUES = new PLVVideoDimensionBitrate[]{pLVVideoDimensionBitrate, pLVVideoDimensionBitrate2, pLVVideoDimensionBitrate3, pLVVideoDimensionBitrate4, pLVVideoDimensionBitrate5, pLVVideoDimensionBitrate6, pLVVideoDimensionBitrate7, pLVVideoDimensionBitrate8, pLVVideoDimensionBitrate9, pLVVideoDimensionBitrate10, pLVVideoDimensionBitrate11, pLVVideoDimensionBitrate12};
        INSTANCE = new Companion(null);
    }

    private PLVVideoDimensionBitrate(String str, int i2, int i3, PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio, int i4, int i5, int i6, int i7) {
        this.bitrateType = i3;
        this.ratio = pushResolutionRatio;
        this.frameRate = i4;
        this.width = i5;
        this.height = i6;
        this.realBitrate = i7;
    }

    @JvmStatic
    @NotNull
    public static final PLVVideoDimensionBitrate match(int i2, @NotNull PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio, int i3) {
        return INSTANCE.match(i2, pushResolutionRatio, i3);
    }

    public static PLVVideoDimensionBitrate valueOf(String str) {
        return (PLVVideoDimensionBitrate) Enum.valueOf(PLVVideoDimensionBitrate.class, str);
    }

    public static PLVVideoDimensionBitrate[] values() {
        return (PLVVideoDimensionBitrate[]) $VALUES.clone();
    }
}
