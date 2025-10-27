package com.ykb.ebook.extensions;

import android.graphics.Canvas;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.ykb.ebook.page.canvans_recorder.CanvasRecorder;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a9\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\u0002\b\tH\u0086\bø\u0001\u0000\u001a9\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\u0002\b\tH\u0086\bø\u0001\u0000\u001aA\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\u0002\b\tH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000e"}, d2 = {AliyunLogCommon.SubModule.RECORD, "", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "width", "", "height", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", "Lkotlin/ExtensionFunctionType;", "recordIfNeeded", "", "recordIfNeededThenDraw", "canvas", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCanvasRecorderExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CanvasRecorderExtensions.kt\ncom/ykb/ebook/extensions/CanvasRecorderExtensionsKt\n+ 2 Canvas.kt\nandroidx/core/graphics/CanvasKt\n*L\n1#1,37:1\n18#1,3:38\n21#1,2:44\n24#1,3:49\n12#1,9:59\n21#1,2:71\n24#1,3:76\n14#1:79\n30#2,3:41\n34#2,3:46\n30#2,7:52\n30#2,3:68\n34#2,3:73\n*S KotlinDebug\n*F\n+ 1 CanvasRecorderExtensions.kt\ncom/ykb/ebook/extensions/CanvasRecorderExtensionsKt\n*L\n13#1:38,3\n13#1:44,2\n13#1:49,3\n34#1:59,9\n34#1:71,2\n34#1:76,3\n34#1:79\n13#1:41,3\n13#1:46,3\n20#1:52,7\n34#1:68,3\n34#1:73,3\n*E\n"})
/* loaded from: classes7.dex */
public final class CanvasRecorderExtensionsKt {
    public static final void record(@NotNull CanvasRecorder canvasRecorder, int i2, int i3, @NotNull Function1<? super Canvas, Unit> block) {
        Intrinsics.checkNotNullParameter(canvasRecorder, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            Canvas canvasBeginRecording = canvasRecorder.beginRecording(i2, i3);
            int iSave = canvasBeginRecording.save();
            try {
                block.invoke(canvasBeginRecording);
            } finally {
                InlineMarker.finallyStart(1);
                canvasBeginRecording.restoreToCount(iSave);
                InlineMarker.finallyEnd(1);
            }
        } finally {
            InlineMarker.finallyStart(1);
            canvasRecorder.endRecording();
            InlineMarker.finallyEnd(1);
        }
    }

    public static final boolean recordIfNeeded(@NotNull CanvasRecorder canvasRecorder, int i2, int i3, @NotNull Function1<? super Canvas, Unit> block) {
        Intrinsics.checkNotNullParameter(canvasRecorder, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        if (!canvasRecorder.needRecord()) {
            return false;
        }
        try {
            Canvas canvasBeginRecording = canvasRecorder.beginRecording(i2, i3);
            int iSave = canvasBeginRecording.save();
            try {
                block.invoke(canvasBeginRecording);
                return true;
            } finally {
                InlineMarker.finallyStart(1);
                canvasBeginRecording.restoreToCount(iSave);
                InlineMarker.finallyEnd(1);
            }
        } finally {
            InlineMarker.finallyStart(1);
            canvasRecorder.endRecording();
            InlineMarker.finallyEnd(1);
        }
    }

    public static final void recordIfNeededThenDraw(@NotNull CanvasRecorder canvasRecorder, @NotNull Canvas canvas, int i2, int i3, @NotNull Function1<? super Canvas, Unit> block) {
        Intrinsics.checkNotNullParameter(canvasRecorder, "<this>");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(block, "block");
        if (canvasRecorder.needRecord()) {
            try {
                Canvas canvasBeginRecording = canvasRecorder.beginRecording(i2, i3);
                int iSave = canvasBeginRecording.save();
                try {
                    block.invoke(canvasBeginRecording);
                } finally {
                    InlineMarker.finallyStart(1);
                    canvasBeginRecording.restoreToCount(iSave);
                    InlineMarker.finallyEnd(1);
                }
            } finally {
                InlineMarker.finallyStart(1);
                canvasRecorder.endRecording();
                InlineMarker.finallyEnd(1);
            }
        }
        canvasRecorder.draw(canvas);
    }
}
