package cn.hutool.core.text;

import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Function;

/* loaded from: classes.dex */
public class StrJoiner implements Appendable, Serializable {
    private static final long serialVersionUID = 1;
    private Appendable appendable;
    private CharSequence delimiter;
    private String emptyResult;
    private boolean hasContent;
    private NullMode nullMode;
    private CharSequence prefix;
    private CharSequence suffix;
    private boolean wrapElement;

    /* renamed from: cn.hutool.core.text.StrJoiner$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$core$text$StrJoiner$NullMode;

        static {
            int[] iArr = new int[NullMode.values().length];
            $SwitchMap$cn$hutool$core$text$StrJoiner$NullMode = iArr;
            try {
                iArr[NullMode.IGNORE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$core$text$StrJoiner$NullMode[NullMode.TO_EMPTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$hutool$core$text$StrJoiner$NullMode[NullMode.NULL_STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum NullMode {
        IGNORE,
        TO_EMPTY,
        NULL_STRING
    }

    public StrJoiner(CharSequence charSequence) {
        this(null, charSequence);
    }

    private void checkHasContent(Appendable appendable) {
        if (appendable instanceof CharSequence) {
            CharSequence charSequence = (CharSequence) appendable;
            if (charSequence.length() <= 0 || !CharSequenceUtil.endWith(charSequence, this.delimiter)) {
                return;
            }
            this.hasContent = true;
            return;
        }
        String string = appendable.toString();
        if (!CharSequenceUtil.isNotEmpty(string) || CharSequenceUtil.endWith(string, this.delimiter)) {
            return;
        }
        this.hasContent = true;
    }

    public static StrJoiner of(StrJoiner strJoiner) {
        StrJoiner strJoiner2 = new StrJoiner(strJoiner.delimiter, strJoiner.prefix, strJoiner.suffix);
        strJoiner2.wrapElement = strJoiner.wrapElement;
        strJoiner2.nullMode = strJoiner.nullMode;
        strJoiner2.emptyResult = strJoiner.emptyResult;
        return strJoiner2;
    }

    private Appendable prepare() throws IOException {
        if (this.hasContent) {
            this.appendable.append(this.delimiter);
        } else {
            if (this.appendable == null) {
                this.appendable = new StringBuilder();
            }
            if (!this.wrapElement && CharSequenceUtil.isNotEmpty(this.prefix)) {
                this.appendable.append(this.prefix);
            }
            this.hasContent = true;
        }
        return this.appendable;
    }

    public int length() {
        Appendable appendable = this.appendable;
        if (appendable != null) {
            return appendable.toString().length() + this.suffix.length();
        }
        String str = this.emptyResult;
        if (str == null) {
            return -1;
        }
        return str.length();
    }

    public StrJoiner merge(StrJoiner strJoiner) throws IOException {
        if (strJoiner != null && strJoiner.appendable != null) {
            String string = strJoiner.toString();
            if (strJoiner.wrapElement) {
                append((CharSequence) string);
            } else {
                append((CharSequence) string, this.prefix.length(), string.length());
            }
        }
        return this;
    }

    public StrJoiner setDelimiter(CharSequence charSequence) {
        this.delimiter = charSequence;
        return this;
    }

    public StrJoiner setEmptyResult(String str) {
        this.emptyResult = str;
        return this;
    }

    public StrJoiner setNullMode(NullMode nullMode) {
        this.nullMode = nullMode;
        return this;
    }

    public StrJoiner setPrefix(CharSequence charSequence) {
        this.prefix = charSequence;
        return this;
    }

    public StrJoiner setSuffix(CharSequence charSequence) {
        this.suffix = charSequence;
        return this;
    }

    public StrJoiner setWrapElement(boolean z2) {
        this.wrapElement = z2;
        return this;
    }

    public String toString() {
        Appendable appendable = this.appendable;
        if (appendable == null) {
            return this.emptyResult;
        }
        String string = appendable.toString();
        if (this.wrapElement || !CharSequenceUtil.isNotEmpty(this.suffix)) {
            return string;
        }
        return string + ((Object) this.suffix);
    }

    public StrJoiner(Appendable appendable, CharSequence charSequence) {
        this(appendable, charSequence, null, null);
    }

    public StrJoiner(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this(null, charSequence, charSequence2, charSequence3);
    }

    public StrJoiner(Appendable appendable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        this.nullMode = NullMode.NULL_STRING;
        this.emptyResult = "";
        if (appendable != null) {
            this.appendable = appendable;
            checkHasContent(appendable);
        }
        this.delimiter = charSequence;
        this.prefix = charSequence2;
        this.suffix = charSequence3;
    }

    public StrJoiner append(Object obj) {
        if (obj == null) {
            append((CharSequence) null);
        } else if (ArrayUtil.isArray(obj)) {
            append((Iterator) new ArrayIter(obj));
        } else if (obj instanceof Iterator) {
            append((Iterator) obj);
        } else if (obj instanceof Iterable) {
            append(((Iterable) obj).iterator());
        } else {
            append((CharSequence) ObjectUtil.toString(obj));
        }
        return this;
    }

    public static StrJoiner of(CharSequence charSequence) {
        return new StrJoiner(charSequence);
    }

    public static StrJoiner of(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return new StrJoiner(charSequence, charSequence2, charSequence3);
    }

    public <T> StrJoiner append(T[] tArr) {
        return tArr == null ? this : append((Iterator) new ArrayIter((Object[]) tArr));
    }

    public <T> StrJoiner append(Iterator<T> it) {
        if (it != null) {
            while (it.hasNext()) {
                append(it.next());
            }
        }
        return this;
    }

    public <T> StrJoiner append(T[] tArr, Function<T, ? extends CharSequence> function) {
        return append((Iterator) new ArrayIter((Object[]) tArr), (Function) function);
    }

    public <E> StrJoiner append(Iterable<E> iterable, Function<? super E, ? extends CharSequence> function) {
        return append(IterUtil.getIter((Iterable) iterable), function);
    }

    public <E> StrJoiner append(Iterator<E> it, Function<? super E, ? extends CharSequence> function) {
        if (it != null) {
            while (it.hasNext()) {
                append((CharSequence) function.apply(it.next()));
            }
        }
        return this;
    }

    @Override // java.lang.Appendable
    public StrJoiner append(CharSequence charSequence) {
        return append(charSequence, 0, CharSequenceUtil.length(charSequence));
    }

    @Override // java.lang.Appendable
    public StrJoiner append(CharSequence charSequence, int i2, int i3) throws IOException {
        if (charSequence == null) {
            int i4 = AnonymousClass1.$SwitchMap$cn$hutool$core$text$StrJoiner$NullMode[this.nullMode.ordinal()];
            if (i4 == 1) {
                return this;
            }
            if (i4 == 2) {
                charSequence = "";
            } else if (i4 == 3) {
                charSequence = "null";
                i3 = 4;
            }
        }
        try {
            Appendable appendablePrepare = prepare();
            if (this.wrapElement && CharSequenceUtil.isNotEmpty(this.prefix)) {
                appendablePrepare.append(this.prefix);
            }
            appendablePrepare.append(charSequence, i2, i3);
            if (this.wrapElement && CharSequenceUtil.isNotEmpty(this.suffix)) {
                appendablePrepare.append(this.suffix);
            }
            return this;
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    @Override // java.lang.Appendable
    public StrJoiner append(char c3) {
        return append((CharSequence) String.valueOf(c3));
    }
}
