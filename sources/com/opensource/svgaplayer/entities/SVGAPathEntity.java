package com.opensource.svgaplayer.entities;

import android.graphics.Path;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.opensource.svgaplayer.BuildConfig;
import java.util.StringTokenizer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006J \u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/opensource/svgaplayer/entities/SVGAPathEntity;", "", "originValue", "", "(Ljava/lang/String;)V", "cachedPath", "Landroid/graphics/Path;", "replacedValue", "buildPath", "", "toPath", "operate", "finalPath", "method", AliyunLogKey.KEY_ARGS, "Ljava/util/StringTokenizer;", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAPathEntity {
    private Path cachedPath;
    private final String replacedValue;

    public SVGAPathEntity(@NotNull String originValue) {
        Intrinsics.checkParameterIsNotNull(originValue, "originValue");
        this.replacedValue = StringsKt__StringsKt.contains$default((CharSequence) originValue, (CharSequence) ",", false, 2, (Object) null) ? StringsKt__StringsJVMKt.replace$default(originValue, ",", " ", false, 4, (Object) null) : originValue;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x011e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void operate(android.graphics.Path r16, java.lang.String r17, java.util.StringTokenizer r18) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.opensource.svgaplayer.entities.SVGAPathEntity.operate(android.graphics.Path, java.lang.String, java.util.StringTokenizer):void");
    }

    public final void buildPath(@NotNull Path toPath) throws NumberFormatException {
        Intrinsics.checkParameterIsNotNull(toPath, "toPath");
        Path path = this.cachedPath;
        if (path != null) {
            toPath.set(path);
            return;
        }
        Path path2 = new Path();
        StringTokenizer stringTokenizer = new StringTokenizer(this.replacedValue, "MLHVCSQRAZmlhvcsqraz", true);
        String str = "";
        while (stringTokenizer.hasMoreTokens()) {
            String segment = stringTokenizer.nextToken();
            Intrinsics.checkExpressionValueIsNotNull(segment, "segment");
            if (!(segment.length() == 0)) {
                if (SVGAPathEntityKt.VALID_METHODS.contains(segment)) {
                    if (Intrinsics.areEqual(segment, "Z") || Intrinsics.areEqual(segment, "z")) {
                        operate(path2, segment, new StringTokenizer("", ""));
                    }
                    str = segment;
                } else {
                    operate(path2, str, new StringTokenizer(segment, " "));
                }
            }
        }
        this.cachedPath = path2;
        toPath.set(path2);
    }
}
