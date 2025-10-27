package cn.hutool.core.codec;

import cn.hutool.core.codec.Hashids;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import t.y;

/* loaded from: classes.dex */
public class Hashids implements Encoder<long[], String>, Decoder<String, long[]> {
    private static final double GUARD_THRESHOLD = 12.0d;
    private static final int LOTTERY_MOD = 100;
    private static final int MIN_ALPHABET_LENGTH = 16;
    private static final double SEPARATOR_THRESHOLD = 3.5d;
    private final char[] alphabet;
    private final char[] guards;
    private final int minLength;
    private final char[] salt;
    private final char[] separators;
    private final Set<Character> separatorsSet;
    private static final Pattern HEX_VALUES_PATTERN = Pattern.compile("[\\w\\W]{1,12}");
    public static final char[] DEFAULT_ALPHABET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private static final char[] DEFAULT_SEPARATORS = {'c', 'f', 'h', 'i', 's', 't', 'u', 'C', 'F', 'H', 'I', 'S', 'T', 'U'};

    public Hashids(char[] cArr, char[] cArr2, int i2) {
        int iCeil;
        this.minLength = i2;
        char[] cArrCopyOf = Arrays.copyOf(cArr, cArr.length);
        this.salt = cArrCopyOf;
        char[] cArrShuffle = shuffle(filterSeparators(DEFAULT_SEPARATORS, cArr2), cArrCopyOf);
        char[] cArrValidateAndFilterAlphabet = validateAndFilterAlphabet(cArr2, cArrShuffle);
        if ((cArrShuffle.length == 0 || cArrValidateAndFilterAlphabet.length / cArrShuffle.length > 3.5d) && (iCeil = (int) Math.ceil(cArrValidateAndFilterAlphabet.length / 3.5d)) > cArrShuffle.length) {
            int length = iCeil - cArrShuffle.length;
            cArrShuffle = Arrays.copyOf(cArrShuffle, cArrShuffle.length + length);
            System.arraycopy(cArrValidateAndFilterAlphabet, 0, cArrShuffle, cArrShuffle.length - length, length);
            System.arraycopy(cArrValidateAndFilterAlphabet, 0, cArrShuffle, cArrShuffle.length - length, length);
            cArrValidateAndFilterAlphabet = Arrays.copyOfRange(cArrValidateAndFilterAlphabet, length, cArrValidateAndFilterAlphabet.length);
        }
        shuffle(cArrValidateAndFilterAlphabet, cArrCopyOf);
        char[] cArr3 = new char[(int) Math.ceil(cArrValidateAndFilterAlphabet.length / GUARD_THRESHOLD)];
        this.guards = cArr3;
        if (cArr2.length < 3) {
            System.arraycopy(cArrShuffle, 0, cArr3, 0, cArr3.length);
            this.separators = Arrays.copyOfRange(cArrShuffle, cArr3.length, cArrShuffle.length);
            this.alphabet = cArrValidateAndFilterAlphabet;
        } else {
            System.arraycopy(cArrValidateAndFilterAlphabet, 0, cArr3, 0, cArr3.length);
            this.separators = cArrShuffle;
            this.alphabet = Arrays.copyOfRange(cArrValidateAndFilterAlphabet, cArr3.length, cArrValidateAndFilterAlphabet.length);
        }
        this.separatorsSet = (Set) IntStream.range(0, this.separators.length).mapToObj(new IntFunction() { // from class: t.d0
            @Override // java.util.function.IntFunction
            public final Object apply(int i3) {
                return this.f28213a.lambda$new$0(i3);
            }
        }).collect(Collectors.toSet());
    }

    public static Hashids create(char[] cArr) {
        return create(cArr, DEFAULT_ALPHABET, -1);
    }

    private char[] deriveNewAlphabet(char[] cArr, char[] cArr2, char c3) {
        int length = cArr.length;
        char[] cArr3 = new char[length];
        cArr3[0] = c3;
        int i2 = 1;
        int i3 = length - 1;
        if (cArr2.length > 0 && i3 > 0) {
            int iMin = Math.min(cArr2.length, i3);
            System.arraycopy(cArr2, 0, cArr3, 1, iMin);
            i3 -= iMin;
            i2 = 1 + iMin;
        }
        if (i3 > 0) {
            System.arraycopy(cArr, 0, cArr3, i2, i3);
        }
        return shuffle(cArr, cArr3);
    }

    private char[] filterSeparators(final char[] cArr, final char[] cArr2) {
        final Set set = (Set) IntStream.range(0, cArr2.length).mapToObj(new IntFunction() { // from class: t.f0
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return Hashids.lambda$filterSeparators$13(cArr2, i2);
            }
        }).collect(Collectors.toSet());
        Stream streamMapToObj = IntStream.range(0, cArr.length).mapToObj(new IntFunction() { // from class: t.g0
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return Hashids.lambda$filterSeparators$14(cArr, i2);
            }
        });
        set.getClass();
        return ((String) streamMapToObj.filter(new Predicate() { // from class: t.h0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return set.contains((Character) obj);
            }
        }).map(new Function() { // from class: t.e0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Hashids.lambda$filterSeparators$15((Character) obj);
            }
        }).collect(Collectors.joining())).toCharArray();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Character lambda$decode$4(int i2) {
        return Character.valueOf(this.guards[i2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$decode$5(Set set, String str, int i2) {
        return set.contains(Character.valueOf(str.charAt(i2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$decodeToHex$3(StringBuilder sb, String str) {
        sb.append((CharSequence) str, 1, str.length());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long lambda$encode$1(long[] jArr, long j2, long j3) {
        long j4 = jArr[(int) j3];
        if (j4 >= 0) {
            return j2 + (j4 % (j3 + 100));
        }
        throw new IllegalArgumentException("invalid number: " + j4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$encode$2(char[] cArr, char c3, StringBuilder sb, long[] jArr, int i2) {
        deriveNewAlphabet(cArr, this.salt, c3);
        translate(jArr[i2], cArr, sb, sb.length());
        if (i2 == 0) {
            sb.insert(0, c3);
        }
        if (i2 + 1 < jArr.length) {
            long jCharAt = jArr[i2] % (sb.charAt(r0) + 1);
            sb.append(this.separators[(int) (jCharAt % r11.length)]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Character lambda$filterSeparators$13(char[] cArr, int i2) {
        return Character.valueOf(cArr[i2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Character lambda$filterSeparators$14(char[] cArr, int i2) {
        return Character.valueOf(cArr[i2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String lambda$filterSeparators$15(Character ch) {
        return Character.toString(ch.charValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Character lambda$new$0(int i2) {
        return Character.valueOf(this.separators[i2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$translate$10(Character ch) {
        throw new IllegalArgumentException("Invalid alphabet for hash");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object[] lambda$translate$6(char[] cArr, int i2) {
        return new Object[]{Character.valueOf(cArr[i2]), Integer.valueOf(i2)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Character lambda$translate$7(Object[] objArr) {
        return (Character) objArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$translate$8(Object[] objArr) {
        return (Integer) objArr[1];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$translate$9(Integer num, Integer num2) {
        return num == null ? num2 : num;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Character lambda$validateAndFilterAlphabet$11(char[] cArr, int i2) {
        return Character.valueOf(cArr[i2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$validateAndFilterAlphabet$12(char[] cArr, Set set, Set set2, int i2) {
        char c3 = cArr[i2];
        if (c3 == ' ') {
            throw new IllegalArgumentException(String.format("alphabet must not contain spaces: index %d", Integer.valueOf(i2)));
        }
        Character chValueOf = Character.valueOf(c3);
        if (set.contains(chValueOf)) {
            return;
        }
        set2.add(chValueOf);
    }

    private char[] shuffle(char[] cArr, char[] cArr2) {
        int length = cArr.length - 1;
        int i2 = 0;
        int i3 = 0;
        while (cArr2.length > 0 && length > 0) {
            int length2 = i2 % cArr2.length;
            char c3 = cArr2[length2];
            i3 += c3;
            int i4 = ((c3 + length2) + i3) % length;
            char c4 = cArr[i4];
            cArr[i4] = cArr[length];
            cArr[length] = c4;
            length--;
            i2 = length2 + 1;
        }
        return cArr;
    }

    private StringBuilder translate(long j2, char[] cArr, StringBuilder sb, int i2) {
        do {
            sb.insert(i2, cArr[(int) (j2 % cArr.length)]);
            j2 /= cArr.length;
        } while (j2 > 0);
        return sb;
    }

    private char[] validateAndFilterAlphabet(final char[] cArr, final char[] cArr2) {
        int i2 = 0;
        if (cArr.length < 16) {
            throw new IllegalArgumentException(String.format("alphabet must contain at least %d unique characters: %d", 16, Integer.valueOf(cArr.length)));
        }
        final LinkedHashSet linkedHashSet = new LinkedHashSet(cArr.length);
        final Set set = (Set) IntStream.range(0, cArr2.length).mapToObj(new IntFunction() { // from class: t.p0
            @Override // java.util.function.IntFunction
            public final Object apply(int i3) {
                return Hashids.lambda$validateAndFilterAlphabet$11(cArr2, i3);
            }
        }).collect(Collectors.toSet());
        IntStream.range(0, cArr.length).forEach(new IntConsumer() { // from class: t.q0
            @Override // java.util.function.IntConsumer
            public final void accept(int i3) {
                Hashids.lambda$validateAndFilterAlphabet$12(cArr, set, linkedHashSet, i3);
            }
        });
        char[] cArr3 = new char[linkedHashSet.size()];
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            cArr3[i2] = ((Character) it.next()).charValue();
            i2++;
        }
        return cArr3;
    }

    public String decodeToHex(String str) {
        if (str == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        Arrays.stream(decode(str)).mapToObj(new LongFunction() { // from class: t.i0
            @Override // java.util.function.LongFunction
            public final Object apply(long j2) {
                return Long.toHexString(j2);
            }
        }).forEach(new Consumer() { // from class: t.j0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Hashids.lambda$decodeToHex$3(sb, (String) obj);
            }
        });
        return sb.toString();
    }

    public String encodeFromHex(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("0x") || str.startsWith("0X")) {
            str = str.substring(2);
        }
        LongStream longStreamEmpty = LongStream.empty();
        Matcher matcher = HEX_VALUES_PATTERN.matcher(str);
        while (matcher.find()) {
            longStreamEmpty = LongStream.concat(longStreamEmpty, LongStream.of(new BigInteger("1" + matcher.group(), 16).longValue()));
        }
        return encode(longStreamEmpty.toArray());
    }

    public static Hashids create(char[] cArr, int i2) {
        return create(cArr, DEFAULT_ALPHABET, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a2  */
    @Override // cn.hutool.core.codec.Decoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long[] decode(final java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.codec.Hashids.decode(java.lang.String):long[]");
    }

    @Override // cn.hutool.core.codec.Encoder
    public String encode(final long... jArr) {
        if (jArr == null) {
            return null;
        }
        char[] cArr = this.alphabet;
        final char[] cArrCopyOf = Arrays.copyOf(cArr, cArr.length);
        long jReduce = LongStream.range(0L, jArr.length).reduce(0L, new LongBinaryOperator() { // from class: t.z
            @Override // java.util.function.LongBinaryOperator
            public final long applyAsLong(long j2, long j3) {
                return Hashids.lambda$encode$1(jArr, j2, j3);
            }
        });
        final char c3 = cArrCopyOf[(int) (jReduce % cArrCopyOf.length)];
        final StringBuilder sb = new StringBuilder();
        IntStream.range(0, jArr.length).forEach(new IntConsumer() { // from class: t.a0
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                this.f28205a.lambda$encode$2(cArrCopyOf, c3, sb, jArr, i2);
            }
        });
        if (this.minLength > sb.length()) {
            sb.insert(0, this.guards[(int) ((c3 + jReduce) % r15.length)]);
            if (this.minLength > sb.length()) {
                char[] cArr2 = this.guards;
                sb.append(cArr2[(int) ((jReduce + sb.charAt(2)) % cArr2.length)]);
            }
        }
        int i2 = this.minLength;
        int length = sb.length();
        while (true) {
            i2 -= length;
            while (i2 > 0) {
                shuffle(cArrCopyOf, Arrays.copyOf(cArrCopyOf, cArrCopyOf.length));
                int length2 = cArrCopyOf.length / 2;
                int length3 = sb.length();
                if (i2 > cArrCopyOf.length) {
                    int i3 = (cArrCopyOf.length % 2 == 0 ? 0 : 1) + length2;
                    sb.insert(0, cArrCopyOf, length2, i3);
                    sb.insert(i3 + length3, cArrCopyOf, 0, length2);
                    length = cArrCopyOf.length;
                } else {
                    int iA = length2 + y.a((cArrCopyOf.length + sb.length()) - this.minLength, 2);
                    int length4 = cArrCopyOf.length - iA;
                    sb.insert(0, cArrCopyOf, iA, length4);
                    sb.insert(length4 + length3, cArrCopyOf, 0, i2 - length4);
                    i2 = 0;
                }
            }
            return sb.toString();
        }
    }

    public static Hashids create(char[] cArr, char[] cArr2, int i2) {
        return new Hashids(cArr, cArr2, i2);
    }

    private long translate(char[] cArr, final char[] cArr2) {
        Map map = (Map) IntStream.range(0, cArr2.length).mapToObj(new IntFunction() { // from class: t.l0
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return Hashids.lambda$translate$6(cArr2, i2);
            }
        }).collect(Collectors.groupingBy(new Function() { // from class: t.m0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Hashids.lambda$translate$7((Object[]) obj);
            }
        }, Collectors.mapping(new Function() { // from class: t.n0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Hashids.lambda$translate$8((Object[]) obj);
            }
        }, Collectors.reducing(null, new BinaryOperator() { // from class: t.o0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Hashids.lambda$translate$9((Integer) obj, (Integer) obj2);
            }
        }))));
        long jIntValue = 0;
        for (int i2 = 0; i2 < cArr.length; i2++) {
            jIntValue += ((Integer) map.computeIfAbsent(Character.valueOf(cArr[i2]), new Function() { // from class: t.k0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Hashids.lambda$translate$10((Character) obj);
                }
            })).intValue() * ((long) Math.pow(cArr2.length, (cArr.length - i2) - 1));
        }
        return jIntValue;
    }
}
