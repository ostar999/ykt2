package kotlinx.serialization.json.internal;

import com.tencent.open.SocialConstants;
import java.util.Iterator;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0003H\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\bH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\r\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J3\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\b2!\u0010\u0013\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u000f0\u0014H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0016J\b\u0010\u001c\u001a\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001d"}, d2 = {"Lkotlinx/serialization/json/internal/StringJsonLexer;", "Lkotlinx/serialization/json/internal/AbstractJsonLexer;", SocialConstants.PARAM_SOURCE, "", "(Ljava/lang/String;)V", "getSource", "()Ljava/lang/String;", "canConsumeValue", "", "consumeKeyString", "consumeLeadingMatchingValue", "keyToMatch", "isLenient", "consumeNextToken", "", "", "expected", "", "consumeStringChunked", "consumeChunk", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "stringChunk", "prefetchOrEof", "", "position", "skipWhitespaces", "tryConsumeComma", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nStringJsonLexer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringJsonLexer.kt\nkotlinx/serialization/json/internal/StringJsonLexer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,122:1\n1855#2,2:123\n*S KotlinDebug\n*F\n+ 1 StringJsonLexer.kt\nkotlinx/serialization/json/internal/StringJsonLexer\n*L\n101#1:123,2\n*E\n"})
/* loaded from: classes8.dex */
public final class StringJsonLexer extends JsonReader {

    @NotNull
    private final String source;

    public StringJsonLexer(@NotNull String source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.source = source;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    public boolean canConsumeValue() {
        int i2 = this.currentPosition;
        if (i2 == -1) {
            return false;
        }
        while (i2 < getSource().length()) {
            char cCharAt = getSource().charAt(i2);
            if (cCharAt != ' ' && cCharAt != '\n' && cCharAt != '\r' && cCharAt != '\t') {
                this.currentPosition = i2;
                return isValidValueStart(cCharAt);
            }
            i2++;
        }
        this.currentPosition = i2;
        return false;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    @NotNull
    public String consumeKeyString() {
        consumeNextToken('\"');
        int i2 = this.currentPosition;
        int iIndexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) getSource(), '\"', i2, false, 4, (Object) null);
        if (iIndexOf$default == -1) {
            fail$kotlinx_serialization_json((byte) 1);
            throw new KotlinNothingValueException();
        }
        for (int i3 = i2; i3 < iIndexOf$default; i3++) {
            if (getSource().charAt(i3) == '\\') {
                return consumeString(getSource(), this.currentPosition, i3);
            }
        }
        this.currentPosition = iIndexOf$default + 1;
        String strSubstring = getSource().substring(i2, iIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    @Nullable
    public String consumeLeadingMatchingValue(@NotNull String keyToMatch, boolean isLenient) {
        Intrinsics.checkNotNullParameter(keyToMatch, "keyToMatch");
        int i2 = this.currentPosition;
        try {
            if (consumeNextToken() != 6) {
                return null;
            }
            if (!Intrinsics.areEqual(isLenient ? consumeKeyString() : consumeStringLenientNotNull(), keyToMatch)) {
                return null;
            }
            if (consumeNextToken() != 5) {
                return null;
            }
            return isLenient ? consumeString() : consumeStringLenientNotNull();
        } finally {
            this.currentPosition = i2;
        }
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    public byte consumeNextToken() {
        byte bCharToTokenClass;
        String source = getSource();
        do {
            int i2 = this.currentPosition;
            if (i2 == -1 || i2 >= source.length()) {
                return (byte) 10;
            }
            int i3 = this.currentPosition;
            this.currentPosition = i3 + 1;
            bCharToTokenClass = AbstractJsonLexerKt.charToTokenClass(source.charAt(i3));
        } while (bCharToTokenClass == 3);
        return bCharToTokenClass;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    public void consumeStringChunked(boolean isLenient, @NotNull Function1<? super String, Unit> consumeChunk) {
        Intrinsics.checkNotNullParameter(consumeChunk, "consumeChunk");
        Iterator it = StringsKt___StringsKt.chunked(isLenient ? consumeStringLenient() : consumeString(), 16384).iterator();
        while (it.hasNext()) {
            consumeChunk.invoke(it.next());
        }
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    public int prefetchOrEof(int position) {
        if (position < getSource().length()) {
            return position;
        }
        return -1;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    public int skipWhitespaces() {
        char cCharAt;
        int i2 = this.currentPosition;
        if (i2 == -1) {
            return i2;
        }
        while (i2 < getSource().length() && ((cCharAt = getSource().charAt(i2)) == ' ' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == '\t')) {
            i2++;
        }
        this.currentPosition = i2;
        return i2;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    public boolean tryConsumeComma() {
        int iSkipWhitespaces = skipWhitespaces();
        if (iSkipWhitespaces == getSource().length() || iSkipWhitespaces == -1 || getSource().charAt(iSkipWhitespaces) != ',') {
            return false;
        }
        this.currentPosition++;
        return true;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    @NotNull
    public String getSource() {
        return this.source;
    }

    @Override // kotlinx.serialization.json.internal.JsonReader
    public void consumeNextToken(char expected) {
        if (this.currentPosition == -1) {
            unexpectedToken(expected);
        }
        String source = getSource();
        while (this.currentPosition < source.length()) {
            int i2 = this.currentPosition;
            this.currentPosition = i2 + 1;
            char cCharAt = source.charAt(i2);
            if (cCharAt != ' ' && cCharAt != '\n' && cCharAt != '\r' && cCharAt != '\t') {
                if (cCharAt == expected) {
                    return;
                } else {
                    unexpectedToken(expected);
                }
            }
        }
        unexpectedToken(expected);
    }
}
