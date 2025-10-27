package kotlin.text;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002¨\u0006\u0014"}, d2 = {"fromInt", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/text/FlagEnum;", "", "value", "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", "input", "", "matchEntire", SessionDescription.ATTR_RANGE, "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRegex.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Regex.kt\nkotlin/text/RegexKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,398:1\n1789#2,3:399\n*S KotlinDebug\n*F\n+ 1 Regex.kt\nkotlin/text/RegexKt\n*L\n19#1:399,3\n*E\n"})
/* loaded from: classes8.dex */
public final class RegexKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final MatchResult findNext(Matcher matcher, int i2, CharSequence charSequence) {
        if (matcher.find(i2)) {
            return new MatcherMatchResult(matcher, charSequence);
        }
        return null;
    }

    private static final /* synthetic */ <T extends Enum<T> & FlagEnum> Set<T> fromInt(final int i2) {
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        EnumSet fromInt$lambda$1 = EnumSet.allOf(Enum.class);
        Intrinsics.checkNotNullExpressionValue(fromInt$lambda$1, "fromInt$lambda$1");
        Intrinsics.needClassReification();
        CollectionsKt__MutableCollectionsKt.retainAll(fromInt$lambda$1, new Function1<T, Boolean>() { // from class: kotlin.text.RegexKt$fromInt$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Boolean; */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Boolean invoke(Enum r3) {
                FlagEnum flagEnum = (FlagEnum) r3;
                return Boolean.valueOf((i2 & flagEnum.getMask()) == flagEnum.getValue());
            }
        });
        Set<T> setUnmodifiableSet = Collections.unmodifiableSet(fromInt$lambda$1);
        Intrinsics.checkNotNullExpressionValue(setUnmodifiableSet, "unmodifiableSet(EnumSet.…mask == it.value }\n    })");
        return setUnmodifiableSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MatchResult matchEntire(Matcher matcher, CharSequence charSequence) {
        if (matcher.matches()) {
            return new MatcherMatchResult(matcher, charSequence);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IntRange range(java.util.regex.MatchResult matchResult) {
        return RangesKt___RangesKt.until(matchResult.start(), matchResult.end());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int toInt(Iterable<? extends FlagEnum> iterable) {
        Iterator<? extends FlagEnum> it = iterable.iterator();
        int value = 0;
        while (it.hasNext()) {
            value |= it.next().getValue();
        }
        return value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IntRange range(java.util.regex.MatchResult matchResult, int i2) {
        return RangesKt___RangesKt.until(matchResult.start(i2), matchResult.end(i2));
    }
}
