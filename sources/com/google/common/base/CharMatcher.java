package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import java.util.BitSet;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;

@GwtCompatible(emulated = true)
/* loaded from: classes3.dex */
public abstract class CharMatcher implements Predicate<Character> {
    private static final int DISTINCT_CHARS = 65536;

    public static final class And extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        public And(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.first = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.second = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return this.first.matches(c3) && this.second.matches(c3);
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.first.setBits(bitSet2);
            BitSet bitSet3 = new BitSet();
            this.second.setBits(bitSet3);
            bitSet2.and(bitSet3);
            bitSet.or(bitSet2);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.and(" + this.first + ", " + this.second + ")";
        }
    }

    public static final class Any extends NamedFastMatcher {
        static final Any INSTANCE = new Any();

        private Any() {
            super("CharMatcher.any()");
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher
        public String collapseFrom(CharSequence charSequence, char c3) {
            return charSequence.length() == 0 ? "" : String.valueOf(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public int countIn(CharSequence charSequence) {
            return charSequence.length();
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence) {
            return charSequence.length() == 0 ? -1 : 0;
        }

        @Override // com.google.common.base.CharMatcher
        public int lastIndexIn(CharSequence charSequence) {
            return charSequence.length() - 1;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return true;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        @Override // com.google.common.base.CharMatcher.FastMatcher, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.none();
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override // com.google.common.base.CharMatcher
        public String removeFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, char c3) {
            char[] cArr = new char[charSequence.length()];
            Arrays.fill(cArr, c3);
            return new String(cArr);
        }

        @Override // com.google.common.base.CharMatcher
        public String trimFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence, int i2) {
            int length = charSequence.length();
            Preconditions.checkPositionIndex(i2, length);
            if (i2 == length) {
                return -1;
            }
            return i2;
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            StringBuilder sb = new StringBuilder(charSequence.length() * charSequence2.length());
            for (int i2 = 0; i2 < charSequence.length(); i2++) {
                sb.append(charSequence2);
            }
            return sb.toString();
        }
    }

    public static final class AnyOf extends CharMatcher {
        private final char[] chars;

        public AnyOf(CharSequence charSequence) {
            char[] charArray = charSequence.toString().toCharArray();
            this.chars = charArray;
            Arrays.sort(charArray);
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return Arrays.binarySearch(this.chars, c3) >= 0;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            for (char c3 : this.chars) {
                bitSet.set(c3);
            }
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
            for (char c3 : this.chars) {
                sb.append(CharMatcher.showCharacter(c3));
            }
            sb.append("\")");
            return sb.toString();
        }
    }

    public static final class Ascii extends NamedFastMatcher {
        static final Ascii INSTANCE = new Ascii();

        public Ascii() {
            super("CharMatcher.ascii()");
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return c3 <= 127;
        }
    }

    @GwtIncompatible
    public static final class BitSetMatcher extends NamedFastMatcher {
        private final BitSet table;

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return this.table.get(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public void setBits(BitSet bitSet) {
            bitSet.or(this.table);
        }

        private BitSetMatcher(BitSet bitSet, String str) {
            super(str);
            this.table = bitSet.length() + 64 < bitSet.size() ? (BitSet) bitSet.clone() : bitSet;
        }
    }

    public static final class BreakingWhitespace extends CharMatcher {
        static final CharMatcher INSTANCE = new BreakingWhitespace();

        private BreakingWhitespace() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            if (c3 != ' ' && c3 != 133 && c3 != 5760) {
                if (c3 == 8199) {
                    return false;
                }
                if (c3 != 8287 && c3 != 12288 && c3 != 8232 && c3 != 8233) {
                    switch (c3) {
                        case '\t':
                        case '\n':
                        case 11:
                        case '\f':
                        case '\r':
                            break;
                        default:
                            if (c3 < 8192 || c3 > 8202) {
                                break;
                            }
                            break;
                    }
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }
    }

    public static final class Digit extends RangesMatcher {
        static final Digit INSTANCE = new Digit();
        private static final String ZEROES = "0٠۰߀०০੦૦୦௦౦೦൦෦๐໐༠၀႐០᠐᥆᧐᪀᪐᭐᮰᱀᱐꘠꣐꤀꧐꧰꩐꯰０";

        private Digit() {
            super("CharMatcher.digit()", zeroes(), nines());
        }

        private static char[] nines() {
            char[] cArr = new char[37];
            for (int i2 = 0; i2 < 37; i2++) {
                cArr[i2] = (char) (ZEROES.charAt(i2) + '\t');
            }
            return cArr;
        }

        private static char[] zeroes() {
            return ZEROES.toCharArray();
        }
    }

    public static abstract class FastMatcher extends CharMatcher {
        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }

        @Override // com.google.common.base.CharMatcher
        public final CharMatcher precomputed() {
            return this;
        }
    }

    public static final class ForPredicate extends CharMatcher {
        private final Predicate<? super Character> predicate;

        public ForPredicate(Predicate<? super Character> predicate) {
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return this.predicate.apply(Character.valueOf(c3));
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.forPredicate(" + this.predicate + ")";
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        public boolean apply(Character ch) {
            return this.predicate.apply(Preconditions.checkNotNull(ch));
        }
    }

    public static final class InRange extends FastMatcher {
        private final char endInclusive;
        private final char startInclusive;

        public InRange(char c3, char c4) {
            Preconditions.checkArgument(c4 >= c3);
            this.startInclusive = c3;
            this.endInclusive = c4;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return this.startInclusive <= c3 && c3 <= this.endInclusive;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            bitSet.set(this.startInclusive, this.endInclusive + 1);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.inRange('" + CharMatcher.showCharacter(this.startInclusive) + "', '" + CharMatcher.showCharacter(this.endInclusive) + "')";
        }
    }

    public static final class Invisible extends RangesMatcher {
        static final Invisible INSTANCE = new Invisible();
        private static final String RANGE_ENDS = "  \u00ad\u0605\u061c\u06dd\u070f\u08e2\u1680\u180e\u200f \u2064\u206f\u3000\uf8ff\ufeff\ufffb";
        private static final String RANGE_STARTS = "\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u08e2\u1680\u180e\u2000\u2028\u205f\u2066\u3000\ud800\ufeff\ufff9";

        private Invisible() {
            super("CharMatcher.invisible()", RANGE_STARTS.toCharArray(), RANGE_ENDS.toCharArray());
        }
    }

    public static final class Is extends FastMatcher {
        private final char match;

        public Is(char c3) {
            this.match = c3;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? this : CharMatcher.none();
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return c3 == this.match;
        }

        @Override // com.google.common.base.CharMatcher.FastMatcher, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.isNot(this.match);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? charMatcher : super.or(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, char c3) {
            return charSequence.toString().replace(this.match, c3);
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            bitSet.set(this.match);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.is('" + CharMatcher.showCharacter(this.match) + "')";
        }
    }

    public static final class IsEither extends FastMatcher {
        private final char match1;
        private final char match2;

        public IsEither(char c3, char c4) {
            this.match1 = c3;
            this.match2 = c4;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return c3 == this.match1 || c3 == this.match2;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            bitSet.set(this.match1);
            bitSet.set(this.match2);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.anyOf(\"" + CharMatcher.showCharacter(this.match1) + CharMatcher.showCharacter(this.match2) + "\")";
        }
    }

    public static final class IsNot extends FastMatcher {
        private final char match;

        public IsNot(char c3) {
            this.match = c3;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? super.and(charMatcher) : charMatcher;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return c3 != this.match;
        }

        @Override // com.google.common.base.CharMatcher.FastMatcher, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.is(this.match);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.match) ? CharMatcher.any() : this;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            bitSet.set(0, this.match);
            bitSet.set(this.match + 1, 65536);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.isNot('" + CharMatcher.showCharacter(this.match) + "')";
        }
    }

    public static final class JavaDigit extends CharMatcher {
        static final JavaDigit INSTANCE = new JavaDigit();

        private JavaDigit() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return Character.isDigit(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaDigit()";
        }
    }

    public static final class JavaIsoControl extends NamedFastMatcher {
        static final JavaIsoControl INSTANCE = new JavaIsoControl();

        private JavaIsoControl() {
            super("CharMatcher.javaIsoControl()");
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return c3 <= 31 || (c3 >= 127 && c3 <= 159);
        }
    }

    public static final class JavaLetter extends CharMatcher {
        static final JavaLetter INSTANCE = new JavaLetter();

        private JavaLetter() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return Character.isLetter(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaLetter()";
        }
    }

    public static final class JavaLetterOrDigit extends CharMatcher {
        static final JavaLetterOrDigit INSTANCE = new JavaLetterOrDigit();

        private JavaLetterOrDigit() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return Character.isLetterOrDigit(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }
    }

    public static final class JavaLowerCase extends CharMatcher {
        static final JavaLowerCase INSTANCE = new JavaLowerCase();

        private JavaLowerCase() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return Character.isLowerCase(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }
    }

    public static final class JavaUpperCase extends CharMatcher {
        static final JavaUpperCase INSTANCE = new JavaUpperCase();

        private JavaUpperCase() {
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return Character.isUpperCase(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }
    }

    public static abstract class NamedFastMatcher extends FastMatcher {
        private final String description;

        public NamedFastMatcher(String str) {
            this.description = (String) Preconditions.checkNotNull(str);
        }

        @Override // com.google.common.base.CharMatcher
        public final String toString() {
            return this.description;
        }
    }

    public static class Negated extends CharMatcher {
        final CharMatcher original;

        public Negated(CharMatcher charMatcher) {
            this.original = (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public int countIn(CharSequence charSequence) {
            return charSequence.length() - this.original.countIn(charSequence);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return !this.original.matches(c3);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            return this.original.matchesNoneOf(charSequence);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            return this.original.matchesAllOf(charSequence);
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return this.original;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.original.setBits(bitSet2);
            bitSet2.flip(0, 65536);
            bitSet.or(bitSet2);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return this.original + ".negate()";
        }
    }

    public static class NegatedFastMatcher extends Negated {
        public NegatedFastMatcher(CharMatcher charMatcher) {
            super(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher
        public final CharMatcher precomputed() {
            return this;
        }
    }

    public static final class None extends NamedFastMatcher {
        static final None INSTANCE = new None();

        private None() {
            super("CharMatcher.none()");
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher and(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override // com.google.common.base.CharMatcher
        public String collapseFrom(CharSequence charSequence, char c3) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public int countIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 0;
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        @Override // com.google.common.base.CharMatcher
        public int lastIndexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return false;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesAllOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matchesNoneOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        @Override // com.google.common.base.CharMatcher.FastMatcher, com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.any();
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher or(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        @Override // com.google.common.base.CharMatcher
        public String removeFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, char c3) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String trimFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String trimLeadingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public String trimTrailingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence, int i2) {
            Preconditions.checkPositionIndex(i2, charSequence.length());
            return -1;
        }

        @Override // com.google.common.base.CharMatcher
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            Preconditions.checkNotNull(charSequence2);
            return charSequence.toString();
        }
    }

    public static final class Or extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        public Or(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.first = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.second = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return this.first.matches(c3) || this.second.matches(c3);
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            this.first.setBits(bitSet);
            this.second.setBits(bitSet);
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return "CharMatcher.or(" + this.first + ", " + this.second + ")";
        }
    }

    public static class RangesMatcher extends CharMatcher {
        private final String description;
        private final char[] rangeEnds;
        private final char[] rangeStarts;

        public RangesMatcher(String str, char[] cArr, char[] cArr2) {
            this.description = str;
            this.rangeStarts = cArr;
            this.rangeEnds = cArr2;
            Preconditions.checkArgument(cArr.length == cArr2.length);
            int i2 = 0;
            while (i2 < cArr.length) {
                Preconditions.checkArgument(cArr[i2] <= cArr2[i2]);
                int i3 = i2 + 1;
                if (i3 < cArr.length) {
                    Preconditions.checkArgument(cArr2[i2] < cArr[i3]);
                }
                i2 = i3;
            }
        }

        @Override // com.google.common.base.CharMatcher, com.google.common.base.Predicate
        @Deprecated
        public /* bridge */ /* synthetic */ boolean apply(Character ch) {
            return super.apply(ch);
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            int iBinarySearch = Arrays.binarySearch(this.rangeStarts, c3);
            if (iBinarySearch >= 0) {
                return true;
            }
            int i2 = (~iBinarySearch) - 1;
            return i2 >= 0 && c3 <= this.rangeEnds[i2];
        }

        @Override // com.google.common.base.CharMatcher
        public String toString() {
            return this.description;
        }
    }

    public static final class SingleWidth extends RangesMatcher {
        static final SingleWidth INSTANCE = new SingleWidth();

        private SingleWidth() {
            super("CharMatcher.singleWidth()", "\u0000־א׳\u0600ݐ\u0e00Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ\u0e7f₯℺﷿\ufeffￜ".toCharArray());
        }
    }

    @VisibleForTesting
    public static final class Whitespace extends NamedFastMatcher {
        static final int MULTIPLIER = 1682554634;
        static final String TABLE = "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001  \f\u2009\u3000\u2004\u3000\u3000\u2028\n \u3000";
        static final int SHIFT = Integer.numberOfLeadingZeros(31);
        static final Whitespace INSTANCE = new Whitespace();

        public Whitespace() {
            super("CharMatcher.whitespace()");
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c3) {
            return TABLE.charAt((MULTIPLIER * c3) >>> SHIFT) == c3;
        }

        @Override // com.google.common.base.CharMatcher
        @GwtIncompatible
        public void setBits(BitSet bitSet) {
            for (int i2 = 0; i2 < 32; i2++) {
                bitSet.set(TABLE.charAt(i2));
            }
        }
    }

    public static CharMatcher any() {
        return Any.INSTANCE;
    }

    public static CharMatcher anyOf(CharSequence charSequence) {
        int length = charSequence.length();
        return length != 0 ? length != 1 ? length != 2 ? new AnyOf(charSequence) : isEither(charSequence.charAt(0), charSequence.charAt(1)) : is(charSequence.charAt(0)) : none();
    }

    public static CharMatcher ascii() {
        return Ascii.INSTANCE;
    }

    public static CharMatcher breakingWhitespace() {
        return BreakingWhitespace.INSTANCE;
    }

    @Deprecated
    public static CharMatcher digit() {
        return Digit.INSTANCE;
    }

    private String finishCollapseFrom(CharSequence charSequence, int i2, int i3, char c3, StringBuilder sb, boolean z2) {
        while (i2 < i3) {
            char cCharAt = charSequence.charAt(i2);
            if (!matches(cCharAt)) {
                sb.append(cCharAt);
                z2 = false;
            } else if (!z2) {
                sb.append(c3);
                z2 = true;
            }
            i2++;
        }
        return sb.toString();
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        return predicate instanceof CharMatcher ? (CharMatcher) predicate : new ForPredicate(predicate);
    }

    public static CharMatcher inRange(char c3, char c4) {
        return new InRange(c3, c4);
    }

    @Deprecated
    public static CharMatcher invisible() {
        return Invisible.INSTANCE;
    }

    public static CharMatcher is(char c3) {
        return new Is(c3);
    }

    private static IsEither isEither(char c3, char c4) {
        return new IsEither(c3, c4);
    }

    public static CharMatcher isNot(char c3) {
        return new IsNot(c3);
    }

    @GwtIncompatible
    private static boolean isSmall(int i2, int i3) {
        return i2 <= 1023 && i3 > (i2 * 4) * 16;
    }

    @Deprecated
    public static CharMatcher javaDigit() {
        return JavaDigit.INSTANCE;
    }

    public static CharMatcher javaIsoControl() {
        return JavaIsoControl.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetter() {
        return JavaLetter.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.INSTANCE;
    }

    public static CharMatcher none() {
        return None.INSTANCE;
    }

    public static CharMatcher noneOf(CharSequence charSequence) {
        return anyOf(charSequence).negate();
    }

    @GwtIncompatible
    private static CharMatcher precomputedPositive(int i2, BitSet bitSet, String str) {
        if (i2 == 0) {
            return none();
        }
        if (i2 == 1) {
            return is((char) bitSet.nextSetBit(0));
        }
        if (i2 != 2) {
            return isSmall(i2, bitSet.length()) ? SmallCharMatcher.from(bitSet, str) : new BitSetMatcher(bitSet, str);
        }
        char cNextSetBit = (char) bitSet.nextSetBit(0);
        return isEither(cNextSetBit, (char) bitSet.nextSetBit(cNextSetBit + 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String showCharacter(char c3) {
        char[] cArr = {'\\', 'u', 0, 0, 0, 0};
        for (int i2 = 0; i2 < 4; i2++) {
            cArr[5 - i2] = BinTools.hex.charAt(c3 & 15);
            c3 = (char) (c3 >> 4);
        }
        return String.copyValueOf(cArr);
    }

    @Deprecated
    public static CharMatcher singleWidth() {
        return SingleWidth.INSTANCE;
    }

    public static CharMatcher whitespace() {
        return Whitespace.INSTANCE;
    }

    public CharMatcher and(CharMatcher charMatcher) {
        return new And(this, charMatcher);
    }

    public String collapseFrom(CharSequence charSequence, char c3) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (matches(cCharAt)) {
                if (cCharAt != c3 || (i2 != length - 1 && matches(charSequence.charAt(i2 + 1)))) {
                    StringBuilder sb = new StringBuilder(length);
                    sb.append(charSequence, 0, i2);
                    sb.append(c3);
                    return finishCollapseFrom(charSequence, i2 + 1, length, c3, sb, true);
                }
                i2++;
            }
            i2++;
        }
        return charSequence.toString();
    }

    public int countIn(CharSequence charSequence) {
        int i2 = 0;
        for (int i3 = 0; i3 < charSequence.length(); i3++) {
            if (matches(charSequence.charAt(i3))) {
                i2++;
            }
        }
        return i2;
    }

    public int indexIn(CharSequence charSequence) {
        return indexIn(charSequence, 0);
    }

    public int lastIndexIn(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (matches(charSequence.charAt(length))) {
                return length;
            }
        }
        return -1;
    }

    public abstract boolean matches(char c3);

    public boolean matchesAllOf(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesAnyOf(CharSequence charSequence) {
        return !matchesNoneOf(charSequence);
    }

    public boolean matchesNoneOf(CharSequence charSequence) {
        return indexIn(charSequence) == -1;
    }

    public CharMatcher negate() {
        return new Negated(this);
    }

    public CharMatcher or(CharMatcher charMatcher) {
        return new Or(this, charMatcher);
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    @GwtIncompatible
    public CharMatcher precomputedInternal() {
        String strSubstring;
        BitSet bitSet = new BitSet();
        setBits(bitSet);
        int iCardinality = bitSet.cardinality();
        if (iCardinality * 2 <= 65536) {
            return precomputedPositive(iCardinality, bitSet, toString());
        }
        bitSet.flip(0, 65536);
        int i2 = 65536 - iCardinality;
        final String string = toString();
        if (string.endsWith(".negate()")) {
            strSubstring = string.substring(0, string.length() - 9);
        } else {
            strSubstring = string + ".negate()";
        }
        return new NegatedFastMatcher(precomputedPositive(i2, bitSet, strSubstring)) { // from class: com.google.common.base.CharMatcher.1
            @Override // com.google.common.base.CharMatcher.Negated, com.google.common.base.CharMatcher
            public String toString() {
                return string;
            }
        };
    }

    public String removeFrom(CharSequence charSequence) {
        String string = charSequence.toString();
        int iIndexIn = indexIn(string);
        if (iIndexIn == -1) {
            return string;
        }
        char[] charArray = string.toCharArray();
        int i2 = 1;
        while (true) {
            iIndexIn++;
            while (iIndexIn != charArray.length) {
                if (matches(charArray[iIndexIn])) {
                    break;
                }
                charArray[iIndexIn - i2] = charArray[iIndexIn];
                iIndexIn++;
            }
            return new String(charArray, 0, iIndexIn - i2);
            i2++;
        }
    }

    public String replaceFrom(CharSequence charSequence, char c3) {
        String string = charSequence.toString();
        int iIndexIn = indexIn(string);
        if (iIndexIn == -1) {
            return string;
        }
        char[] charArray = string.toCharArray();
        charArray[iIndexIn] = c3;
        while (true) {
            iIndexIn++;
            if (iIndexIn >= charArray.length) {
                return new String(charArray);
            }
            if (matches(charArray[iIndexIn])) {
                charArray[iIndexIn] = c3;
            }
        }
    }

    public String retainFrom(CharSequence charSequence) {
        return negate().removeFrom(charSequence);
    }

    @GwtIncompatible
    public void setBits(BitSet bitSet) {
        for (int i2 = 65535; i2 >= 0; i2--) {
            if (matches((char) i2)) {
                bitSet.set(i2);
            }
        }
    }

    public String toString() {
        return super.toString();
    }

    public String trimAndCollapseFrom(CharSequence charSequence, char c3) {
        int length = charSequence.length();
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < length && matches(charSequence.charAt(i3))) {
            i3++;
        }
        int i4 = i2;
        while (i4 > i3 && matches(charSequence.charAt(i4))) {
            i4--;
        }
        if (i3 == 0 && i4 == i2) {
            return collapseFrom(charSequence, c3);
        }
        int i5 = i4 + 1;
        return finishCollapseFrom(charSequence, i3, i5, c3, new StringBuilder(i5 - i3), false);
    }

    public String trimFrom(CharSequence charSequence) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length && matches(charSequence.charAt(i2))) {
            i2++;
        }
        int i3 = length - 1;
        while (i3 > i2 && matches(charSequence.charAt(i3))) {
            i3--;
        }
        return charSequence.subSequence(i2, i3 + 1).toString();
    }

    public String trimLeadingFrom(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!matches(charSequence.charAt(i2))) {
                return charSequence.subSequence(i2, length).toString();
            }
        }
        return "";
    }

    public String trimTrailingFrom(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return charSequence.subSequence(0, length + 1).toString();
            }
        }
        return "";
    }

    @Override // com.google.common.base.Predicate
    @Deprecated
    public boolean apply(Character ch) {
        return matches(ch.charValue());
    }

    public int indexIn(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        Preconditions.checkPositionIndex(i2, length);
        while (i2 < length) {
            if (matches(charSequence.charAt(i2))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence2.length();
        if (length == 0) {
            return removeFrom(charSequence);
        }
        int i2 = 0;
        if (length == 1) {
            return replaceFrom(charSequence, charSequence2.charAt(0));
        }
        String string = charSequence.toString();
        int iIndexIn = indexIn(string);
        if (iIndexIn == -1) {
            return string;
        }
        int length2 = string.length();
        StringBuilder sb = new StringBuilder(((length2 * 3) / 2) + 16);
        do {
            sb.append((CharSequence) string, i2, iIndexIn);
            sb.append(charSequence2);
            i2 = iIndexIn + 1;
            iIndexIn = indexIn(string, i2);
        } while (iIndexIn != -1);
        sb.append((CharSequence) string, i2, length2);
        return sb.toString();
    }
}
