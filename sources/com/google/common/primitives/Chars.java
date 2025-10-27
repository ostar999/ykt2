package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import kotlin.jvm.internal.CharCompanionObject;
import okhttp3.internal.ws.WebSocketProtocol;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public final class Chars {
    public static final int BYTES = 2;

    @GwtCompatible
    public static class CharArrayAsList extends AbstractList<Character> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        final char[] array;
        final int end;
        final int start;

        public CharArrayAsList(char[] cArr) {
            this(cArr, 0, cArr.length);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return (obj instanceof Character) && Chars.indexOf(this.array, ((Character) obj).charValue(), this.start, this.end) != -1;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CharArrayAsList)) {
                return super.equals(obj);
            }
            CharArrayAsList charArrayAsList = (CharArrayAsList) obj;
            int size = size();
            if (charArrayAsList.size() != size) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (this.array[this.start + i2] != charArrayAsList.array[charArrayAsList.start + i2]) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            int iHashCode = 1;
            for (int i2 = this.start; i2 < this.end; i2++) {
                iHashCode = (iHashCode * 31) + Chars.hashCode(this.array[i2]);
            }
            return iHashCode;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            int iIndexOf;
            if (!(obj instanceof Character) || (iIndexOf = Chars.indexOf(this.array, ((Character) obj).charValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return iIndexOf - this.start;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            int iLastIndexOf;
            if (!(obj instanceof Character) || (iLastIndexOf = Chars.lastIndexOf(this.array, ((Character) obj).charValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return iLastIndexOf - this.start;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.end - this.start;
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Character> subList(int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, size());
            if (i2 == i3) {
                return Collections.emptyList();
            }
            char[] cArr = this.array;
            int i4 = this.start;
            return new CharArrayAsList(cArr, i2 + i4, i4 + i3);
        }

        public char[] toCharArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 3);
            sb.append('[');
            sb.append(this.array[this.start]);
            int i2 = this.start;
            while (true) {
                i2++;
                if (i2 >= this.end) {
                    sb.append(']');
                    return sb.toString();
                }
                sb.append(", ");
                sb.append(this.array[i2]);
            }
        }

        public CharArrayAsList(char[] cArr, int i2, int i3) {
            this.array = cArr;
            this.start = i2;
            this.end = i3;
        }

        @Override // java.util.AbstractList, java.util.List
        public Character get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            return Character.valueOf(this.array[this.start + i2]);
        }

        @Override // java.util.AbstractList, java.util.List
        public Character set(int i2, Character ch) {
            Preconditions.checkElementIndex(i2, size());
            char[] cArr = this.array;
            int i3 = this.start;
            char c3 = cArr[i3 + i2];
            cArr[i3 + i2] = ((Character) Preconditions.checkNotNull(ch)).charValue();
            return Character.valueOf(c3);
        }
    }

    public enum LexicographicalComparator implements Comparator<char[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Chars.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(char[] cArr, char[] cArr2) {
            int iMin = Math.min(cArr.length, cArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                int iCompare = Chars.compare(cArr[i2], cArr2[i2]);
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            return cArr.length - cArr2.length;
        }
    }

    private Chars() {
    }

    public static List<Character> asList(char... cArr) {
        return cArr.length == 0 ? Collections.emptyList() : new CharArrayAsList(cArr);
    }

    public static char checkedCast(long j2) {
        char c3 = (char) j2;
        Preconditions.checkArgument(((long) c3) == j2, "Out of range: %s", j2);
        return c3;
    }

    public static int compare(char c3, char c4) {
        return c3 - c4;
    }

    public static char[] concat(char[]... cArr) {
        int length = 0;
        for (char[] cArr2 : cArr) {
            length += cArr2.length;
        }
        char[] cArr3 = new char[length];
        int length2 = 0;
        for (char[] cArr4 : cArr) {
            System.arraycopy(cArr4, 0, cArr3, length2, cArr4.length);
            length2 += cArr4.length;
        }
        return cArr3;
    }

    @Beta
    public static char constrainToRange(char c3, char c4, char c5) {
        Preconditions.checkArgument(c4 <= c5, "min (%s) must be less than or equal to max (%s)", c4, c5);
        return c3 < c4 ? c4 : c3 < c5 ? c3 : c5;
    }

    public static boolean contains(char[] cArr, char c3) {
        for (char c4 : cArr) {
            if (c4 == c3) {
                return true;
            }
        }
        return false;
    }

    public static char[] ensureCapacity(char[] cArr, int i2, int i3) {
        Preconditions.checkArgument(i2 >= 0, "Invalid minLength: %s", i2);
        Preconditions.checkArgument(i3 >= 0, "Invalid padding: %s", i3);
        return cArr.length < i2 ? Arrays.copyOf(cArr, i2 + i3) : cArr;
    }

    @GwtIncompatible
    public static char fromByteArray(byte[] bArr) {
        Preconditions.checkArgument(bArr.length >= 2, "array too small: %s < %s", bArr.length, 2);
        return fromBytes(bArr[0], bArr[1]);
    }

    @GwtIncompatible
    public static char fromBytes(byte b3, byte b4) {
        return (char) ((b3 << 8) | (b4 & 255));
    }

    public static int hashCode(char c3) {
        return c3;
    }

    public static int indexOf(char[] cArr, char c3) {
        return indexOf(cArr, c3, 0, cArr.length);
    }

    public static String join(String str, char... cArr) {
        Preconditions.checkNotNull(str);
        int length = cArr.length;
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder((str.length() * (length - 1)) + length);
        sb.append(cArr[0]);
        for (int i2 = 1; i2 < length; i2++) {
            sb.append(str);
            sb.append(cArr[i2]);
        }
        return sb.toString();
    }

    public static int lastIndexOf(char[] cArr, char c3) {
        return lastIndexOf(cArr, c3, 0, cArr.length);
    }

    public static Comparator<char[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static char max(char... cArr) {
        Preconditions.checkArgument(cArr.length > 0);
        char c3 = cArr[0];
        for (int i2 = 1; i2 < cArr.length; i2++) {
            char c4 = cArr[i2];
            if (c4 > c3) {
                c3 = c4;
            }
        }
        return c3;
    }

    public static char min(char... cArr) {
        Preconditions.checkArgument(cArr.length > 0);
        char c3 = cArr[0];
        for (int i2 = 1; i2 < cArr.length; i2++) {
            char c4 = cArr[i2];
            if (c4 < c3) {
                c3 = c4;
            }
        }
        return c3;
    }

    public static void reverse(char[] cArr) {
        Preconditions.checkNotNull(cArr);
        reverse(cArr, 0, cArr.length);
    }

    public static char saturatedCast(long j2) {
        if (j2 > WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            return CharCompanionObject.MAX_VALUE;
        }
        if (j2 < 0) {
            return (char) 0;
        }
        return (char) j2;
    }

    public static void sortDescending(char[] cArr) {
        Preconditions.checkNotNull(cArr);
        sortDescending(cArr, 0, cArr.length);
    }

    public static char[] toArray(Collection<Character> collection) {
        if (collection instanceof CharArrayAsList) {
            return ((CharArrayAsList) collection).toCharArray();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            cArr[i2] = ((Character) Preconditions.checkNotNull(array[i2])).charValue();
        }
        return cArr;
    }

    @GwtIncompatible
    public static byte[] toByteArray(char c3) {
        return new byte[]{(byte) (c3 >> '\b'), (byte) c3};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexOf(char[] cArr, char c3, int i2, int i3) {
        while (i2 < i3) {
            if (cArr[i2] == c3) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lastIndexOf(char[] cArr, char c3, int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            if (cArr[i4] == c3) {
                return i4;
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
    
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOf(char[] r5, char[] r6) {
        /*
            java.lang.String r0 = "array"
            com.google.common.base.Preconditions.checkNotNull(r5, r0)
            java.lang.String r0 = "target"
            com.google.common.base.Preconditions.checkNotNull(r6, r0)
            int r0 = r6.length
            r1 = 0
            if (r0 != 0) goto Lf
            return r1
        Lf:
            r0 = r1
        L10:
            int r2 = r5.length
            int r3 = r6.length
            int r2 = r2 - r3
            int r2 = r2 + 1
            if (r0 >= r2) goto L2a
            r2 = r1
        L18:
            int r3 = r6.length
            if (r2 >= r3) goto L29
            int r3 = r0 + r2
            char r3 = r5[r3]
            char r4 = r6[r2]
            if (r3 == r4) goto L26
            int r0 = r0 + 1
            goto L10
        L26:
            int r2 = r2 + 1
            goto L18
        L29:
            return r0
        L2a:
            r5 = -1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.primitives.Chars.indexOf(char[], char[]):int");
    }

    public static void reverse(char[] cArr, int i2, int i3) {
        Preconditions.checkNotNull(cArr);
        Preconditions.checkPositionIndexes(i2, i3, cArr.length);
        for (int i4 = i3 - 1; i2 < i4; i4--) {
            char c3 = cArr[i2];
            cArr[i2] = cArr[i4];
            cArr[i4] = c3;
            i2++;
        }
    }

    public static void sortDescending(char[] cArr, int i2, int i3) {
        Preconditions.checkNotNull(cArr);
        Preconditions.checkPositionIndexes(i2, i3, cArr.length);
        Arrays.sort(cArr, i2, i3);
        reverse(cArr, i2, i3);
    }
}
