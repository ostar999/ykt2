package kotlinx.serialization.json.internal;

import cn.hutool.core.text.CharPool;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import java.util.ArrayList;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\r\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0010\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002J\u0018\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0018\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004H\u0014J\b\u0010\u001f\u001a\u00020 H&J\u0006\u0010!\u001a\u00020 J\u0010\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020\u0004H\u0003J\u0006\u0010#\u001a\u00020 J\u0018\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\b\u0010&\u001a\u00020\u000fH&J\u001a\u0010'\u001a\u0004\u0018\u00010\u000f2\u0006\u0010(\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020 H&J\b\u0010*\u001a\u00020+H&J\u000e\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+J\u0010\u0010*\u001a\u00020\u001c2\u0006\u0010,\u001a\u00020-H\u0016J\u0006\u0010.\u001a\u00020/J\u0006\u00100\u001a\u00020\u000fJ \u00100\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0005J3\u00101\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020 2!\u00102\u001a\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b4\u0012\b\b5\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u001c03H\u0016J\u0006\u00107\u001a\u00020\u000fJ\u0006\u00108\u001a\u00020\u000fJ\u0018\u00109\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\b\u0010:\u001a\u00020\u001cH\u0016J\u0006\u0010;\u001a\u00020\u001cJ\u0015\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020+H\u0000¢\u0006\u0002\b?J\"\u0010<\u001a\u00020=2\u0006\u0010@\u001a\u00020\u000f2\b\b\u0002\u0010A\u001a\u00020\u00042\b\b\u0002\u0010B\u001a\u00020\u000fJ\u000e\u0010C\u001a\u00020\u001c2\u0006\u0010D\u001a\u00020\u000fJ\u0018\u0010E\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0018\u0010F\u001a\u00020\u00042\u0006\u0010G\u001a\u00020-2\u0006\u0010\u001a\u001a\u00020\u0004H\u0016J\u0018\u0010H\u001a\u00020 2\u0006\u0010)\u001a\u00020 2\u0006\u0010G\u001a\u00020-H\u0002J\u0006\u0010I\u001a\u00020 J\u0010\u0010J\u001a\u00020 2\u0006\u0010K\u001a\u00020-H\u0004J\u0006\u0010L\u001a\u00020+J\u0010\u0010M\u001a\u0004\u0018\u00010\u000f2\u0006\u0010)\u001a\u00020 J\u0010\u0010N\u001a\u00020\u00042\u0006\u0010A\u001a\u00020\u0004H&J1\u0010O\u001a\u00020\u001c2\u0006\u0010P\u001a\u00020 2\b\b\u0002\u0010A\u001a\u00020\u00042\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u000f0QH\u0080\bø\u0001\u0000¢\u0006\u0002\bRJ\u000e\u0010S\u001a\u00020\u001c2\u0006\u0010T\u001a\u00020 J\b\u0010U\u001a\u00020\u0004H\u0016J\u0018\u0010V\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010W\u001a\u00020\u0004H\u0016J\b\u0010X\u001a\u00020\u000fH\u0002J\b\u0010Y\u001a\u00020\u000fH\u0016J\b\u0010Z\u001a\u00020 H&J\u0010\u0010[\u001a\u00020 2\b\b\u0002\u0010\\\u001a\u00020 J\u0010\u0010]\u001a\u00020\u001c2\u0006\u0010,\u001a\u00020-H\u0004J\b\u0010^\u001a\u00020 H\u0002JC\u0010_\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010`\u001a\u00020 2!\u00102\u001a\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b4\u0012\b\b5\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u001c03H\u0002R\u0012\u0010\u0003\u001a\u00020\u00048\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0010\u001a\u00020\u0011X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006a"}, d2 = {"Lkotlinx/serialization/json/internal/AbstractJsonLexer;", "", "()V", "currentPosition", "", "escapedString", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "getEscapedString", "()Ljava/lang/StringBuilder;", "setEscapedString", "(Ljava/lang/StringBuilder;)V", "path", "Lkotlinx/serialization/json/internal/JsonPath;", "peekedString", "", SocialConstants.PARAM_SOURCE, "", "getSource", "()Ljava/lang/CharSequence;", "appendEsc", "startPosition", "appendEscape", "lastPosition", "current", "appendHex", "startPos", "appendRange", "", "fromIndex", "toIndex", "canConsumeValue", "", "consumeBoolean", "start", "consumeBooleanLenient", "consumeBooleanLiteral", "literalSuffix", "consumeKeyString", "consumeLeadingMatchingValue", "keyToMatch", "isLenient", "consumeNextToken", "", "expected", "", "consumeNumericLiteral", "", "consumeString", "consumeStringChunked", "consumeChunk", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "stringChunk", "consumeStringLenient", "consumeStringLenientNotNull", "decodedString", "ensureHaveChars", "expectEof", "fail", "", "expectedToken", "fail$kotlinx_serialization_json", "message", "position", "hint", "failOnUnknownKey", "key", "fromHexChar", "indexOf", "char", "insideString", "isNotEof", "isValidValueStart", am.aF, "peekNextToken", "peekString", "prefetchOrEof", "require", "condition", "Lkotlin/Function0;", "require$kotlinx_serialization_json", "skipElement", "allowLenientStrings", "skipWhitespaces", "substring", "endPos", "takePeeked", "toString", "tryConsumeComma", "tryConsumeNull", "doConsume", "unexpectedToken", "wasUnquotedString", "writeRange", "currentChunkHasEscape", "kotlinx-serialization-json"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAbstractJsonLexer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbstractJsonLexer.kt\nkotlinx/serialization/json/internal/AbstractJsonLexer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,704:1\n1#2:705\n*E\n"})
/* renamed from: kotlinx.serialization.json.internal.AbstractJsonLexer, reason: from toString */
/* loaded from: classes8.dex */
public abstract class JsonReader {

    @JvmField
    protected int currentPosition;

    @Nullable
    private String peekedString;

    @JvmField
    @NotNull
    public final JsonPath path = new JsonPath();

    @NotNull
    private StringBuilder escapedString = new StringBuilder();

    private final int appendEsc(int startPosition) {
        int iPrefetchOrEof = prefetchOrEof(startPosition);
        if (iPrefetchOrEof == -1) {
            fail$default(this, "Expected escape sequence to continue, got EOF", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        int i2 = iPrefetchOrEof + 1;
        char cCharAt = getSource().charAt(iPrefetchOrEof);
        if (cCharAt == 'u') {
            return appendHex(getSource(), i2);
        }
        char cEscapeToChar = AbstractJsonLexerKt.escapeToChar(cCharAt);
        if (cEscapeToChar != 0) {
            this.escapedString.append(cEscapeToChar);
            return i2;
        }
        fail$default(this, "Invalid escaped char '" + cCharAt + CharPool.SINGLE_QUOTE, 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    private final int appendEscape(int lastPosition, int current) {
        appendRange(lastPosition, current);
        return appendEsc(current + 1);
    }

    private final int appendHex(CharSequence source, int startPos) {
        int i2 = startPos + 4;
        if (i2 < source.length()) {
            this.escapedString.append((char) ((fromHexChar(source, startPos) << 12) + (fromHexChar(source, startPos + 1) << 8) + (fromHexChar(source, startPos + 2) << 4) + fromHexChar(source, startPos + 3)));
            return i2;
        }
        this.currentPosition = startPos;
        ensureHaveChars();
        if (this.currentPosition + 4 < source.length()) {
            return appendHex(source, this.currentPosition);
        }
        fail$default(this, "Unexpected EOF during unicode escape", 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    private final void consumeBooleanLiteral(String literalSuffix, int current) {
        if (getSource().length() - current < literalSuffix.length()) {
            fail$default(this, "Unexpected end of boolean literal", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        int length = literalSuffix.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (literalSuffix.charAt(i2) != (getSource().charAt(current + i2) | ' ')) {
                fail$default(this, "Expected valid boolean literal prefix, but had '" + consumeStringLenient() + CharPool.SINGLE_QUOTE, 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
        }
        this.currentPosition = current + literalSuffix.length();
    }

    private final String decodedString(int lastPosition, int currentPosition) {
        appendRange(lastPosition, currentPosition);
        String string = this.escapedString.toString();
        Intrinsics.checkNotNullExpressionValue(string, "escapedString.toString()");
        this.escapedString.setLength(0);
        return string;
    }

    public static /* synthetic */ Void fail$default(JsonReader jsonReader, String str, int i2, String str2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: fail");
        }
        if ((i3 & 2) != 0) {
            i2 = jsonReader.currentPosition;
        }
        if ((i3 & 4) != 0) {
            str2 = "";
        }
        return jsonReader.fail(str, i2, str2);
    }

    private final int fromHexChar(CharSequence source, int currentPosition) {
        char cCharAt = source.charAt(currentPosition);
        if ('0' <= cCharAt && cCharAt < ':') {
            return cCharAt - '0';
        }
        char c3 = 'a';
        if (!('a' <= cCharAt && cCharAt < 'g')) {
            c3 = 'A';
            if (!('A' <= cCharAt && cCharAt < 'G')) {
                fail$default(this, "Invalid toHexChar char '" + cCharAt + "' in unicode escape", 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
        }
        return (cCharAt - c3) + 10;
    }

    private final boolean insideString(boolean isLenient, char c3) {
        if (isLenient) {
            if (AbstractJsonLexerKt.charToTokenClass(c3) == 0) {
                return true;
            }
        } else if (c3 != '\"') {
            return true;
        }
        return false;
    }

    public static /* synthetic */ void require$kotlinx_serialization_json$default(JsonReader jsonReader, boolean z2, int i2, Function0 message, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: require");
        }
        if ((i3 & 2) != 0) {
            i2 = jsonReader.currentPosition;
        }
        int i4 = i2;
        Intrinsics.checkNotNullParameter(message, "message");
        if (z2) {
            return;
        }
        fail$default(jsonReader, (String) message.invoke(), i4, null, 4, null);
        throw new KotlinNothingValueException();
    }

    private final String takePeeked() {
        String str = this.peekedString;
        Intrinsics.checkNotNull(str);
        this.peekedString = null;
        return str;
    }

    public static /* synthetic */ boolean tryConsumeNull$default(JsonReader jsonReader, boolean z2, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: tryConsumeNull");
        }
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        return jsonReader.tryConsumeNull(z2);
    }

    private final boolean wasUnquotedString() {
        return getSource().charAt(this.currentPosition - 1) != '\"';
    }

    private final void writeRange(int fromIndex, int toIndex, boolean currentChunkHasEscape, Function1<? super String, Unit> consumeChunk) {
        if (currentChunkHasEscape) {
            consumeChunk.invoke(decodedString(fromIndex, toIndex));
        } else {
            consumeChunk.invoke(substring(fromIndex, toIndex));
        }
    }

    public void appendRange(int fromIndex, int toIndex) {
        this.escapedString.append(getSource(), fromIndex, toIndex);
    }

    public abstract boolean canConsumeValue();

    public final boolean consumeBoolean() {
        return consumeBoolean(skipWhitespaces());
    }

    public final boolean consumeBooleanLenient() {
        boolean z2;
        int iSkipWhitespaces = skipWhitespaces();
        if (iSkipWhitespaces == getSource().length()) {
            fail$default(this, "EOF", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        if (getSource().charAt(iSkipWhitespaces) == '\"') {
            iSkipWhitespaces++;
            z2 = true;
        } else {
            z2 = false;
        }
        boolean zConsumeBoolean = consumeBoolean(iSkipWhitespaces);
        if (z2) {
            if (this.currentPosition == getSource().length()) {
                fail$default(this, "EOF", 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
            if (getSource().charAt(this.currentPosition) != '\"') {
                fail$default(this, "Expected closing quotation mark", 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
            this.currentPosition++;
        }
        return zConsumeBoolean;
    }

    @NotNull
    public abstract String consumeKeyString();

    @Nullable
    public abstract String consumeLeadingMatchingValue(@NotNull String keyToMatch, boolean isLenient);

    public abstract byte consumeNextToken();

    public final byte consumeNextToken(byte expected) {
        byte bConsumeNextToken = consumeNextToken();
        if (bConsumeNextToken == expected) {
            return bConsumeNextToken;
        }
        fail$kotlinx_serialization_json(expected);
        throw new KotlinNothingValueException();
    }

    public final long consumeNumericLiteral() {
        boolean z2;
        int iPrefetchOrEof = prefetchOrEof(skipWhitespaces());
        if (iPrefetchOrEof >= getSource().length() || iPrefetchOrEof == -1) {
            fail$default(this, "EOF", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        if (getSource().charAt(iPrefetchOrEof) == '\"') {
            iPrefetchOrEof++;
            if (iPrefetchOrEof == getSource().length()) {
                fail$default(this, "EOF", 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
            z2 = true;
        } else {
            z2 = false;
        }
        int i2 = iPrefetchOrEof;
        long j2 = 0;
        boolean z3 = true;
        boolean z4 = false;
        while (z3) {
            char cCharAt = getSource().charAt(i2);
            if (cCharAt != '-') {
                if (AbstractJsonLexerKt.charToTokenClass(cCharAt) != 0) {
                    break;
                }
                i2++;
                z3 = i2 != getSource().length();
                int i3 = cCharAt - '0';
                if (!(i3 >= 0 && i3 < 10)) {
                    fail$default(this, "Unexpected symbol '" + cCharAt + "' in numeric literal", 0, null, 6, null);
                    throw new KotlinNothingValueException();
                }
                j2 = (j2 * 10) - i3;
                if (j2 > 0) {
                    fail$default(this, "Numeric value overflow", 0, null, 6, null);
                    throw new KotlinNothingValueException();
                }
            } else {
                if (i2 != iPrefetchOrEof) {
                    fail$default(this, "Unexpected symbol '-' in numeric literal", 0, null, 6, null);
                    throw new KotlinNothingValueException();
                }
                i2++;
                z4 = true;
            }
        }
        if (iPrefetchOrEof == i2 || (z4 && iPrefetchOrEof == i2 - 1)) {
            fail$default(this, "Expected numeric literal", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        if (z2) {
            if (!z3) {
                fail$default(this, "EOF", 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
            if (getSource().charAt(i2) != '\"') {
                fail$default(this, "Expected closing quotation mark", 0, null, 6, null);
                throw new KotlinNothingValueException();
            }
            i2++;
        }
        this.currentPosition = i2;
        if (z4) {
            return j2;
        }
        if (j2 != Long.MIN_VALUE) {
            return -j2;
        }
        fail$default(this, "Numeric value overflow", 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    @NotNull
    public final String consumeString() {
        return this.peekedString != null ? takePeeked() : consumeKeyString();
    }

    public void consumeStringChunked(boolean isLenient, @NotNull Function1<? super String, Unit> consumeChunk) {
        int i2;
        int iPrefetchOrEof;
        Intrinsics.checkNotNullParameter(consumeChunk, "consumeChunk");
        byte bPeekNextToken = peekNextToken();
        if (!isLenient || bPeekNextToken == 0) {
            if (!isLenient) {
                consumeNextToken('\"');
            }
            int i3 = this.currentPosition;
            char cCharAt = getSource().charAt(i3);
            boolean z2 = false;
            int i4 = i3;
            while (insideString(isLenient, cCharAt)) {
                if (isLenient || cCharAt != '\\') {
                    int i5 = i4 + 1;
                    i2 = i3;
                    iPrefetchOrEof = i5;
                } else {
                    iPrefetchOrEof = prefetchOrEof(appendEscape(i3, i4));
                    z2 = true;
                    i2 = iPrefetchOrEof;
                }
                if (iPrefetchOrEof >= getSource().length()) {
                    writeRange(i2, iPrefetchOrEof, z2, consumeChunk);
                    int iPrefetchOrEof2 = prefetchOrEof(iPrefetchOrEof);
                    if (iPrefetchOrEof2 == -1) {
                        fail$default(this, "EOF", iPrefetchOrEof2, null, 4, null);
                        throw new KotlinNothingValueException();
                    }
                    z2 = false;
                    i3 = iPrefetchOrEof2;
                    i4 = i3;
                } else {
                    int i6 = i2;
                    i4 = iPrefetchOrEof;
                    i3 = i6;
                }
                cCharAt = getSource().charAt(i4);
            }
            writeRange(i3, i4, z2, consumeChunk);
            this.currentPosition = i4;
            if (isLenient) {
                return;
            }
            consumeNextToken('\"');
        }
    }

    @NotNull
    public final String consumeStringLenient() {
        if (this.peekedString != null) {
            return takePeeked();
        }
        int iSkipWhitespaces = skipWhitespaces();
        if (iSkipWhitespaces >= getSource().length() || iSkipWhitespaces == -1) {
            fail$default(this, "EOF", iSkipWhitespaces, null, 4, null);
            throw new KotlinNothingValueException();
        }
        byte bCharToTokenClass = AbstractJsonLexerKt.charToTokenClass(getSource().charAt(iSkipWhitespaces));
        if (bCharToTokenClass == 1) {
            return consumeString();
        }
        if (bCharToTokenClass != 0) {
            fail$default(this, "Expected beginning of the string, but got " + getSource().charAt(iSkipWhitespaces), 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        boolean z2 = false;
        while (AbstractJsonLexerKt.charToTokenClass(getSource().charAt(iSkipWhitespaces)) == 0) {
            iSkipWhitespaces++;
            if (iSkipWhitespaces >= getSource().length()) {
                appendRange(this.currentPosition, iSkipWhitespaces);
                int iPrefetchOrEof = prefetchOrEof(iSkipWhitespaces);
                if (iPrefetchOrEof == -1) {
                    this.currentPosition = iSkipWhitespaces;
                    return decodedString(0, 0);
                }
                iSkipWhitespaces = iPrefetchOrEof;
                z2 = true;
            }
        }
        String strSubstring = !z2 ? substring(this.currentPosition, iSkipWhitespaces) : decodedString(this.currentPosition, iSkipWhitespaces);
        this.currentPosition = iSkipWhitespaces;
        return strSubstring;
    }

    @NotNull
    public final String consumeStringLenientNotNull() {
        String strConsumeStringLenient = consumeStringLenient();
        if (!Intrinsics.areEqual(strConsumeStringLenient, "null") || !wasUnquotedString()) {
            return strConsumeStringLenient;
        }
        fail$default(this, "Unexpected 'null' value instead of string literal", 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    public void ensureHaveChars() {
    }

    public final void expectEof() {
        if (consumeNextToken() == 10) {
            return;
        }
        fail$default(this, "Expected EOF after parsing, but had " + getSource().charAt(this.currentPosition - 1) + " instead", 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    @NotNull
    public final Void fail(@NotNull String message, int position, @NotNull String hint) {
        String str;
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(hint, "hint");
        if (hint.length() == 0) {
            str = "";
        } else {
            str = '\n' + hint;
        }
        throw JsonExceptionsKt.JsonDecodingException(position, message + " at path: " + this.path.getPath() + str, getSource());
    }

    @NotNull
    public final Void fail$kotlinx_serialization_json(byte expectedToken) {
        fail$default(this, "Expected " + (expectedToken == 1 ? "quotation mark '\"'" : expectedToken == 4 ? "comma ','" : expectedToken == 5 ? "colon ':'" : expectedToken == 6 ? "start of the object '{'" : expectedToken == 7 ? "end of the object '}'" : expectedToken == 8 ? "start of the array '['" : expectedToken == 9 ? "end of the array ']'" : "valid token") + ", but had '" + ((this.currentPosition == getSource().length() || this.currentPosition <= 0) ? "EOF" : String.valueOf(getSource().charAt(this.currentPosition - 1))) + "' instead", this.currentPosition - 1, null, 4, null);
        throw new KotlinNothingValueException();
    }

    public final void failOnUnknownKey(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        fail("Encountered an unknown key '" + key + CharPool.SINGLE_QUOTE, StringsKt__StringsKt.lastIndexOf$default((CharSequence) substring(0, this.currentPosition), key, 0, false, 6, (Object) null), AbstractJsonLexerKt.ignoreUnknownKeysHint);
        throw new KotlinNothingValueException();
    }

    @NotNull
    public final StringBuilder getEscapedString() {
        return this.escapedString;
    }

    @NotNull
    public abstract CharSequence getSource();

    public int indexOf(char c3, int startPos) {
        return StringsKt__StringsKt.indexOf$default(getSource(), c3, startPos, false, 4, (Object) null);
    }

    public final boolean isNotEof() {
        return peekNextToken() != 10;
    }

    public final boolean isValidValueStart(char c3) {
        return !(((c3 == '}' || c3 == ']') || c3 == ':') || c3 == ',');
    }

    public final byte peekNextToken() {
        CharSequence source = getSource();
        int i2 = this.currentPosition;
        while (true) {
            int iPrefetchOrEof = prefetchOrEof(i2);
            if (iPrefetchOrEof == -1) {
                this.currentPosition = iPrefetchOrEof;
                return (byte) 10;
            }
            char cCharAt = source.charAt(iPrefetchOrEof);
            if (cCharAt != ' ' && cCharAt != '\n' && cCharAt != '\r' && cCharAt != '\t') {
                this.currentPosition = iPrefetchOrEof;
                return AbstractJsonLexerKt.charToTokenClass(cCharAt);
            }
            i2 = iPrefetchOrEof + 1;
        }
    }

    @Nullable
    public final String peekString(boolean isLenient) {
        String strConsumeString;
        byte bPeekNextToken = peekNextToken();
        if (isLenient) {
            if (bPeekNextToken != 1 && bPeekNextToken != 0) {
                return null;
            }
            strConsumeString = consumeStringLenient();
        } else {
            if (bPeekNextToken != 1) {
                return null;
            }
            strConsumeString = consumeString();
        }
        this.peekedString = strConsumeString;
        return strConsumeString;
    }

    public abstract int prefetchOrEof(int position);

    public final void require$kotlinx_serialization_json(boolean condition, int position, @NotNull Function0<String> message) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (condition) {
            return;
        }
        fail$default(this, message.invoke(), position, null, 4, null);
        throw new KotlinNothingValueException();
    }

    public final void setEscapedString(@NotNull StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "<set-?>");
        this.escapedString = sb;
    }

    public final void skipElement(boolean allowLenientStrings) {
        ArrayList arrayList = new ArrayList();
        byte bPeekNextToken = peekNextToken();
        if (bPeekNextToken != 8 && bPeekNextToken != 6) {
            consumeStringLenient();
            return;
        }
        while (true) {
            byte bPeekNextToken2 = peekNextToken();
            boolean z2 = true;
            if (bPeekNextToken2 != 1) {
                if (bPeekNextToken2 != 8 && bPeekNextToken2 != 6) {
                    z2 = false;
                }
                if (z2) {
                    arrayList.add(Byte.valueOf(bPeekNextToken2));
                } else if (bPeekNextToken2 == 9) {
                    if (((Number) CollectionsKt___CollectionsKt.last((List) arrayList)).byteValue() != 8) {
                        throw JsonExceptionsKt.JsonDecodingException(this.currentPosition, "found ] instead of } at path: " + this.path, getSource());
                    }
                    CollectionsKt__MutableCollectionsKt.removeLast(arrayList);
                } else if (bPeekNextToken2 == 7) {
                    if (((Number) CollectionsKt___CollectionsKt.last((List) arrayList)).byteValue() != 6) {
                        throw JsonExceptionsKt.JsonDecodingException(this.currentPosition, "found } instead of ] at path: " + this.path, getSource());
                    }
                    CollectionsKt__MutableCollectionsKt.removeLast(arrayList);
                } else if (bPeekNextToken2 == 10) {
                    fail$default(this, "Unexpected end of input due to malformed JSON during ignoring unknown keys", 0, null, 6, null);
                    throw new KotlinNothingValueException();
                }
                consumeNextToken();
                if (arrayList.size() == 0) {
                    return;
                }
            } else if (allowLenientStrings) {
                consumeStringLenient();
            } else {
                consumeKeyString();
            }
        }
    }

    public int skipWhitespaces() {
        int iPrefetchOrEof;
        char cCharAt;
        int i2 = this.currentPosition;
        while (true) {
            iPrefetchOrEof = prefetchOrEof(i2);
            if (iPrefetchOrEof == -1 || !((cCharAt = getSource().charAt(iPrefetchOrEof)) == ' ' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == '\t')) {
                break;
            }
            i2 = iPrefetchOrEof + 1;
        }
        this.currentPosition = iPrefetchOrEof;
        return iPrefetchOrEof;
    }

    @NotNull
    public String substring(int startPos, int endPos) {
        return getSource().subSequence(startPos, endPos).toString();
    }

    @NotNull
    public String toString() {
        return "JsonReader(source='" + ((Object) getSource()) + "', currentPosition=" + this.currentPosition + ')';
    }

    public abstract boolean tryConsumeComma();

    public final boolean tryConsumeNull(boolean doConsume) {
        int iPrefetchOrEof = prefetchOrEof(skipWhitespaces());
        int length = getSource().length() - iPrefetchOrEof;
        if (length < 4 || iPrefetchOrEof == -1) {
            return false;
        }
        for (int i2 = 0; i2 < 4; i2++) {
            if ("null".charAt(i2) != getSource().charAt(iPrefetchOrEof + i2)) {
                return false;
            }
        }
        if (length > 4 && AbstractJsonLexerKt.charToTokenClass(getSource().charAt(iPrefetchOrEof + 4)) == 0) {
            return false;
        }
        if (!doConsume) {
            return true;
        }
        this.currentPosition = iPrefetchOrEof + 4;
        return true;
    }

    public final void unexpectedToken(char expected) {
        int i2 = this.currentPosition - 1;
        this.currentPosition = i2;
        if (i2 >= 0 && expected == '\"' && Intrinsics.areEqual(consumeStringLenient(), "null")) {
            fail("Expected string literal but 'null' literal was found", this.currentPosition - 4, AbstractJsonLexerKt.coerceInputValuesHint);
            throw new KotlinNothingValueException();
        }
        fail$kotlinx_serialization_json(AbstractJsonLexerKt.charToTokenClass(expected));
        throw new KotlinNothingValueException();
    }

    private final boolean consumeBoolean(int start) {
        int iPrefetchOrEof = prefetchOrEof(start);
        if (iPrefetchOrEof >= getSource().length() || iPrefetchOrEof == -1) {
            fail$default(this, "EOF", 0, null, 6, null);
            throw new KotlinNothingValueException();
        }
        int i2 = iPrefetchOrEof + 1;
        int iCharAt = getSource().charAt(iPrefetchOrEof) | ' ';
        if (iCharAt == 102) {
            consumeBooleanLiteral("alse", i2);
            return false;
        }
        if (iCharAt == 116) {
            consumeBooleanLiteral("rue", i2);
            return true;
        }
        fail$default(this, "Expected valid boolean literal prefix, but had '" + consumeStringLenient() + CharPool.SINGLE_QUOTE, 0, null, 6, null);
        throw new KotlinNothingValueException();
    }

    public void consumeNextToken(char expected) {
        ensureHaveChars();
        CharSequence source = getSource();
        int i2 = this.currentPosition;
        while (true) {
            int iPrefetchOrEof = prefetchOrEof(i2);
            if (iPrefetchOrEof != -1) {
                int i3 = iPrefetchOrEof + 1;
                char cCharAt = source.charAt(iPrefetchOrEof);
                if (cCharAt != ' ' && cCharAt != '\n' && cCharAt != '\r' && cCharAt != '\t') {
                    this.currentPosition = i3;
                    if (cCharAt == expected) {
                        return;
                    } else {
                        unexpectedToken(expected);
                    }
                }
                i2 = i3;
            } else {
                this.currentPosition = iPrefetchOrEof;
                unexpectedToken(expected);
                return;
            }
        }
    }

    @NotNull
    public final String consumeString(@NotNull CharSequence source, int startPosition, int current) {
        String strDecodedString;
        int iPrefetchOrEof;
        Intrinsics.checkNotNullParameter(source, "source");
        char cCharAt = source.charAt(current);
        boolean z2 = false;
        while (cCharAt != '\"') {
            if (cCharAt == '\\') {
                iPrefetchOrEof = prefetchOrEof(appendEscape(startPosition, current));
                if (iPrefetchOrEof == -1) {
                    fail$default(this, "EOF", iPrefetchOrEof, null, 4, null);
                    throw new KotlinNothingValueException();
                }
            } else {
                current++;
                if (current >= source.length()) {
                    appendRange(startPosition, current);
                    iPrefetchOrEof = prefetchOrEof(current);
                    if (iPrefetchOrEof == -1) {
                        fail$default(this, "EOF", iPrefetchOrEof, null, 4, null);
                        throw new KotlinNothingValueException();
                    }
                } else {
                    continue;
                    cCharAt = source.charAt(current);
                }
            }
            z2 = true;
            startPosition = iPrefetchOrEof;
            current = startPosition;
            cCharAt = source.charAt(current);
        }
        if (!z2) {
            strDecodedString = substring(startPosition, current);
        } else {
            strDecodedString = decodedString(startPosition, current);
        }
        this.currentPosition = current + 1;
        return strDecodedString;
    }
}
